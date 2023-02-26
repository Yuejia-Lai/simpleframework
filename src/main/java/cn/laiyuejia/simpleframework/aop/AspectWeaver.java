package cn.laiyuejia.simpleframework.aop;

import cn.laiyuejia.simpleframework.aop.annotation.Aspect;
import cn.laiyuejia.simpleframework.aop.annotation.Order;
import cn.laiyuejia.simpleframework.aop.aspect.AspectInfo;
import cn.laiyuejia.simpleframework.aop.aspect.DefaultAspect;
import cn.laiyuejia.simpleframework.core.BeanContainer;
import cn.laiyuejia.simpleframework.util.ValidationUtil;

import java.lang.annotation.Annotation;
import java.util.*;

public class AspectWeaver {

    private BeanContainer beanContainer;

    public AspectWeaver(){
        this.beanContainer = BeanContainer.getInstance();
    }

//    public void doAOP(){
//        //获取所有切面类
//        Set<Class<?>> aspectSet = beanContainer.getClassesByAnnotation(Aspect.class);
//        //将切面类按照不同的目标进行区分
//        Map<Class<? extends Annotation>, List<AspectInfo>> categorizedMap = new HashMap<>();
//        if(!ValidationUtil.isEmpty(aspectSet)){
//            for(Class<?> aspectClass : aspectSet){
//                if(verifyAspect(aspectClass)){
//                    categorizedAspect(categorizedMap,aspectClass);
//                }else{
//                    throw new RuntimeException("AOP类未被@Aspect或者@Order修饰，或者没有继承自DefaultAspect，或者Aspect的值是@Aspect");
//                }
//            }
//        } else {
//            return;
//        }
//        //按照不同织入目标织入逻辑
//        if(!ValidationUtil.isEmpty(categorizedMap)){
//            for(Class<? extends Annotation> category : categorizedMap.keySet()){
//                weaveByCategory(category,categorizedMap.get(category));
//            }
//        }else {
//            return;
//        }
//    }

    public void doAOP(){
//      获取所有切面类
        Set<Class<?>> aspectSet = beanContainer.getClassesByAnnotation(Aspect.class);
        if(ValidationUtil.isEmpty(aspectSet)) return;
        //封装AspectInfoList
        List<AspectInfo> aspectInfoList = packAspectInfoList(aspectSet);
        //遍历容器中的类，粗筛
        Set<Class<?>> classSet = beanContainer.getClasses();
        for(Class<?> targetClass : classSet){
            //排除aspectClass
            if(targetClass.isAnnotationPresent(Aspect.class)){
                continue;
            }
            List<AspectInfo> roughMatchedAspectList = collectRoughMatchedAspectListForSpecificClass(aspectInfoList,targetClass);
            //尝试进行aspect织入
            wrapIfNecessary(roughMatchedAspectList,targetClass);
        }

    }

    private void wrapIfNecessary(List<AspectInfo> roughMatchedAspectList, Class<?> targetClass) {
        if(ValidationUtil.isEmpty(roughMatchedAspectList)) return;
        AspectListExecutor aspectListExecutor = new AspectListExecutor(targetClass,roughMatchedAspectList);
        Object proxyBean = ProxyCreator.createProxy(targetClass, aspectListExecutor);
        beanContainer.addBean(targetClass,proxyBean);
    }

    private List<AspectInfo> collectRoughMatchedAspectListForSpecificClass(List<AspectInfo> aspectInfoList, Class<?> targetClass) {
        List<AspectInfo> roughMatchedAspectList = new ArrayList<>();
        for(AspectInfo aspectInfo : aspectInfoList){
            //粗筛
            if(aspectInfo.getPointcutLocator().roughMatches(targetClass)){
                roughMatchedAspectList.add(aspectInfo);
            }
        }
        return roughMatchedAspectList;
    }

    private List<AspectInfo> packAspectInfoList(Set<Class<?>> aspectSet) {
        List<AspectInfo> aspectInfoList = new ArrayList<>();
        for(Class<?> aspectClass : aspectSet){
            if(vertifyAspect(aspectClass)){
                Order orderTag = aspectClass.getAnnotation(Order.class);
                Aspect aspectTag = aspectClass.getAnnotation(Aspect.class);
                DefaultAspect defaultAspect = (DefaultAspect) beanContainer.getBean(aspectClass);
                PointcutLocator pointcutLocator = new PointcutLocator(aspectTag.pointcut());
                AspectInfo aspectInfo = new AspectInfo(orderTag.value(), defaultAspect,pointcutLocator);
                aspectInfoList.add(aspectInfo);
            }else{
                throw new RuntimeException("切面类未标注@Aspect或者@Oeder注解，或者未继承DefaultAspect");
            }
        }
        return aspectInfoList;
    }

    private boolean vertifyAspect(Class<?> aspectClass) {
        return aspectClass.isAnnotationPresent(Aspect.class)
                && aspectClass.isAnnotationPresent(Order.class)
                && DefaultAspect.class.isAssignableFrom(aspectClass);
    }

//    private void weaveByCategory(Class<? extends Annotation> category, List<AspectInfo> aspectInfos) {
//        //获取代理类集合
//        Set<Class<?>> classSet = beanContainer.getClassesByAnnotation(category);
//        if(ValidationUtil.isEmpty(classSet)){
//            return;
//        }else{
//            //为每个被代理类生成动态代理实例
//            for(Class<?> targetClass : classSet){
//                AspectListExecutor aspectListExecutor = new AspectListExecutor(targetClass,aspectInfos);
//                Object proxy = ProxyCreator.createProxy(targetClass, aspectListExecutor);
//                //将动态代理对象添加到容器，取代未被代理的类实例
//                beanContainer.addBean(targetClass,proxy);
//            }
//        }
//    }

//    //按切面类根据不同织入目标进行区分
//    private void categorizedAspect(Map<Class<? extends Annotation>, List<AspectInfo>> categorizedMap, Class<?> aspectClass) {
//        Order orderTag = aspectClass.getAnnotation(Order.class);
//        Aspect aspectTag = aspectClass.getAnnotation(Aspect.class);
//        DefaultAspect defaultAspect = (DefaultAspect) beanContainer.getBean(aspectClass);
//        AspectInfo aspectInfo = new AspectInfo(orderTag.value(),defaultAspect);
//        if(!categorizedMap.containsKey(aspectTag.value())){
//            List<AspectInfo> aspectInfoList = new LinkedList<>();
//            aspectInfoList.add(aspectInfo);
//            categorizedMap.put(aspectTag.value(),aspectInfoList);
//        }else{
//            List<AspectInfo> aspectInfoList = categorizedMap.get(aspectTag.value());
//            aspectInfoList.add(aspectInfo);
//        }
//    }
//
//    //验证aspect类是否含有@Aspect和@Order标签，同时，必须继承自DefaultAspect.class
//    //@Aspect对象不能是本身
//    private boolean verifyAspect(Class<?> aspectClass) {
//        return aspectClass.isAnnotationPresent(Aspect.class)
//                && aspectClass.isAnnotationPresent(Order.class)
//                && DefaultAspect.class.isAssignableFrom(aspectClass)
//                && aspectClass.getAnnotation(Aspect.class).value() != Aspect.class;
//
//    }
}
