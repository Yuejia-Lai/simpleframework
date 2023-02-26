package cn.laiyuejia.simpleframework.mvc.processor.impl;

import cn.laiyuejia.simpleframework.core.BeanContainer;
import cn.laiyuejia.simpleframework.mvc.RequestProcessorChain;
import cn.laiyuejia.simpleframework.mvc.annotation.RequestMapping;
import cn.laiyuejia.simpleframework.mvc.annotation.RequestParam;
import cn.laiyuejia.simpleframework.mvc.annotation.ResponseBody;
import cn.laiyuejia.simpleframework.mvc.processor.RequestProcessor;
import cn.laiyuejia.simpleframework.mvc.render.impl.JsonResultRender;
import cn.laiyuejia.simpleframework.mvc.render.impl.ResourceNotFoundResultRender;
import cn.laiyuejia.simpleframework.mvc.render.ResultRender;
import cn.laiyuejia.simpleframework.mvc.render.impl.ViewResultRender;
import cn.laiyuejia.simpleframework.mvc.type.ControllerMethod;
import cn.laiyuejia.simpleframework.mvc.type.RequestPathInfo;
import cn.laiyuejia.simpleframework.util.ConverterUtil;
import cn.laiyuejia.simpleframework.util.ValidationUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class ControllerRequestProcessor implements RequestProcessor {

    private BeanContainer beanContainer;

    private Map<RequestPathInfo, ControllerMethod> pathControllerMethodMap = new ConcurrentHashMap<>();

    public ControllerRequestProcessor() {
        this.beanContainer = BeanContainer.getInstance();
        Set<Class<?>> requestMappingSet = beanContainer.getClassesByAnnotation(RequestMapping.class);
        initPathControllerMethodMap(requestMappingSet);
    }

    private void initPathControllerMethodMap(Set<Class<?>> requestMappingSet) {
        if (ValidationUtil.isEmpty(requestMappingSet)) return;
        for (Class<?> clazz : requestMappingSet) {
            //获取类上的路径作为一级路径
            RequestMapping requestMapping = clazz.getAnnotation(RequestMapping.class);
            String basePath = requestMapping.value();
            if (!basePath.startsWith("/")) {
                basePath = "/" + basePath;
            }
            //获取被标记的方法注解属性值，作为二级路径
            Method[] declaredMethods = clazz.getDeclaredMethods();
            if (ValidationUtil.isEmpty(declaredMethods)) {
                continue;
            }
            for (Method method : declaredMethods) {
                if (method.isAnnotationPresent(RequestMapping.class)) {
                    RequestMapping methodAnnotation = method.getAnnotation(RequestMapping.class);
                    String methodPath = methodAnnotation.value();
                    if (!methodPath.startsWith("/")) {
                        methodPath = "/" + methodPath;
                    }
                    String url = basePath + methodPath;
                    //解析方法里被@requestParam标记的参数
                    //获取属性值作为参数名，并获取参数类型建立映射
                    Map<String, Class<?>> methodParams = new HashMap<>();
                    Parameter[] parameters = method.getParameters();
                    if (!ValidationUtil.isEmpty(parameters)) {
                        for (Parameter parameter : parameters) {
                            RequestParam param = parameter.getAnnotation(RequestParam.class);
                            //简化实现，controller里所有的参数都需要标注注解
                            if (param == null) {
                                throw new RuntimeException("参数必须有@RequestParam注解");
                            }
                            methodParams.put(param.value(), parameter.getType());
                        }
                    }
                    //获取到的信息封装成RequestPathInfRequestInfo实例和ControllerMethod实例，加入映射表中
                    String httpMethod = String.valueOf(methodAnnotation.method());
                    RequestPathInfo requestPathInfo = new RequestPathInfo(httpMethod, url);
                    if (this.pathControllerMethodMap.containsKey(requestPathInfo)) {
                        log.error("请求路径重复！", requestPathInfo.getHttpPath(), clazz.getName(), method.getName());
                    }
                    ControllerMethod controllerMethod = new ControllerMethod(clazz, method, methodParams);
                    this.pathControllerMethodMap.put(requestPathInfo, controllerMethod);
                }
            }
        }
    }

    @Override
    public boolean process(RequestProcessorChain requestProcessorChain){
        //解析HttpServletRequest，获取请求路径，方法，并获得对应的ControllerMethod实例
        String method = requestProcessorChain.getRequestMethod();
        String path = requestProcessorChain.getRequestPath();
        ControllerMethod controllerMethod = this.pathControllerMethodMap.get(new RequestPathInfo(method, path));
        if (controllerMethod == null) {
            requestProcessorChain.setResultRender(new ResourceNotFoundResultRender(method, path));
            return false;
        }
        //解析请求参数，并传递给ControllerMethod实例去执行
        Object result = invokeControllerMethod(controllerMethod, requestProcessorChain.getRequest());
        //处理结果选择对应的Render渲染
        setResultRender(result, controllerMethod, requestProcessorChain);
        return true;
    }

    private void setResultRender(Object result, ControllerMethod controllerMethod, RequestProcessorChain requestProcessorChain) {
        if (result == null) {
            return;
        }
        ResultRender resultRender;
        boolean isJson = controllerMethod.getInvokeMethod().isAnnotationPresent(ResponseBody.class);
        if (isJson) {
            resultRender = new JsonResultRender(result);
        } else {
            resultRender = new ViewResultRender(result);
        }
        requestProcessorChain.setResultRender(resultRender);
    }

    private Object invokeControllerMethod(ControllerMethod controllerMethod, HttpServletRequest request) {
        //从请求获取参数名以及对应的值
        Map<String, String> requestParamMap = new HashMap<>();
        Map<String, String[]> parameterMap = request.getParameterMap();
        for (Map.Entry<String, String[]> parameter : parameterMap.entrySet()) {
            if (!ValidationUtil.isEmpty(parameter.getValue())) {
                requestParamMap.put(parameter.getKey(), parameter.getValue()[0]);
            }
        }
        //根据获取的参数名或者对应的值，以及controllerMethod里的参数和类型的映射关系，去实例化出方法
        List<Object> methodParams = new ArrayList<>();
        Map<String, Class<?>> methodParamMap = controllerMethod.getMethodParameters();
        for (String paramName : methodParamMap.keySet()) {
            Class<?> type = methodParamMap.get(paramName);
            String requestValue = requestParamMap.get(paramName);
            Object value;
            if (requestValue == null) {
                //请求参数里的值转换成适配参数类型的空值
                value = ConverterUtil.primitiveNull(type);
            } else {
                value = ConverterUtil.convert(type, requestValue);
            }
            methodParams.add(value);
        }
        //执行方法获得返回结果
        Object containerBean = beanContainer.getBean(controllerMethod.getControllerClass());
        Method invokeMethod = controllerMethod.getInvokeMethod();
        invokeMethod.setAccessible(true);
        Object result;
        try {

            if (methodParams.size() == 0) {
                result = invokeMethod.invoke(containerBean);
            } else {
                result = invokeMethod.invoke(containerBean, methodParams.toArray());
            }
        } catch (InvocationTargetException e) {
            System.out.println("抛出错误！");
            throw new RuntimeException(e.getTargetException());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
