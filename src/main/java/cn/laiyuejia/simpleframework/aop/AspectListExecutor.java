package cn.laiyuejia.simpleframework.aop;

import cn.laiyuejia.simpleframework.aop.annotation.Aspect;
import cn.laiyuejia.simpleframework.aop.aspect.AspectInfo;
import cn.laiyuejia.simpleframework.util.ValidationUtil;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class AspectListExecutor  implements MethodInterceptor {

    private Class<?> targetClass;

    private List<AspectInfo> aspectInfoList;

    public AspectListExecutor(Class<?> targetClass,List<AspectInfo> aspectInfoList){
        this.targetClass = targetClass;
        this.aspectInfoList = sortAspectInfoList(aspectInfoList);
    }

    private List<AspectInfo> sortAspectInfoList(List<AspectInfo> aspectInfoList) {
        Collections.sort(aspectInfoList, new Comparator<AspectInfo>() {
            @Override
            public int compare(AspectInfo o1, AspectInfo o2) {
                return o1.getOrderIndex()- o2.getOrderIndex();
            }
        });
        return aspectInfoList;
    }


    /**
     * 定义横切逻辑
     * @param o
     * @param method
     * @param args
     * @param methodProxy
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        Object returnValue = null;
        collectAccurateMatchedAspectList(method);
        if(ValidationUtil.isEmpty(aspectInfoList)) {
            returnValue = methodProxy.invokeSuper(o,args);
            return returnValue;
        }
        //order顺序执行所有aspectbefore方法
        invokeBeforeAdvice(method,args);
        try {
            //执行被代理类方法
            returnValue = methodProxy.invokeSuper(o,args);
            //正常返回：order降序执行afterReturning
            invokeAfterReturningAdvices(method,args,returnValue);
        }catch (Exception e){
            //抛出异常：order降序执行afterThrowing
            invokeAfterThrowingAdvices(method,args,e);
        }
        return returnValue;
    }

    private void collectAccurateMatchedAspectList(Method method) {
        if(ValidationUtil.isEmpty(aspectInfoList)) return;
        //foreach不支持动态移除元素，改用迭代器
        Iterator<AspectInfo> it = aspectInfoList.iterator();
        while(it.hasNext()){
            AspectInfo aspectInfo = it.next();
            if(!aspectInfo.getPointcutLocator().accurateMatches(method)){
                it.remove();
            }
        }
    }

    private void invokeAfterThrowingAdvices(Method method, Object[] args, Exception e) throws Throwable{
        for(int i= aspectInfoList.size()-1;i>=0;i--){
            aspectInfoList.get(i).getDefaultAspect().afterThrowing(targetClass,method,args,e);
        }
    }

    private Object invokeAfterReturningAdvices(Method method, Object[] args, Object returnValue) {
        Object result = null;
        for(int i= aspectInfoList.size()-1;i>=0;i--){
            result = aspectInfoList.get(i).getDefaultAspect().afterReturning(targetClass,method,args,returnValue);
        }
        return result;
    }

    private void invokeBeforeAdvice(Method method, Object[] args) throws Throwable {
        for(AspectInfo aspectInfo: aspectInfoList){
            aspectInfo.getDefaultAspect().before(targetClass,method,args);
        }
    }
}
