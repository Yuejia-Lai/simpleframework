package cn.laiyuejia.simpleframework.core;

import cn.laiyuejia.simpleframework.core.annotation.Component;
import cn.laiyuejia.simpleframework.core.annotation.Controller;
import cn.laiyuejia.simpleframework.core.annotation.Repository;
import cn.laiyuejia.simpleframework.core.annotation.Service;
import cn.laiyuejia.simpleframework.util.ClassUtil;
import cn.laiyuejia.simpleframework.util.ValidationUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BeanContainer {

    //保存被标记的目标对象
    private final Map<Class<?>,Object> beanMap = new ConcurrentHashMap();

    private static final List<Class<? extends Annotation>> BEAN_ANNOTATION
            = Arrays.asList(Component.class, Controller.class, Repository.class, Service.class);

    //获取bean容器
    public static BeanContainer getInstance(){
        return ContainerHolder.HOLDER.instance;
    }

    //单例模式
    private enum ContainerHolder{
        HOLDER;
        private BeanContainer instance;
        ContainerHolder(){
            instance = new BeanContainer();
        }
    }

    //判断容器是否被加载过
    private boolean loaded = false;

    //是否已经加载过bean
    public boolean isLoaded(){
        return loaded;
    }

    //返回beanMap大小
    public int size(){
        return beanMap.size();
    }

    //扫描加载所有bean
    public synchronized void loadBeans(String packageName){
        //判断bean容器是否被加载过
        if(isLoaded()){
            log.warn("Bean容器已经被加载过！");
            return;
        }
        Set<Class<?>> classSet = ClassUtil.extractPackageClass(packageName);
        if(ValidationUtil.isEmpty(classSet)){
            log.warn("指定包下未找到任何类!");
            return;
        }
        for(Class<?> clazz : classSet){
            for(Class<? extends Annotation> annotation : BEAN_ANNOTATION ){
                if(clazz.isAnnotationPresent(annotation)){
                    beanMap.put(clazz,ClassUtil.newInstance(clazz));
                }
            }
        }
        loaded = true;
    }

    //添加一个class对象及其bean实例
    public Object addBean(Class<?> clazz,Object bean){
        return beanMap.put(clazz,bean);
    }

    //删除某个bean及其实例
    public Object removeBean(Class<?> clazz){
        return beanMap.remove(clazz);
    }

    //根据class对象获取实例
    public Object getBean(Class<?> clazz){
        return beanMap.get(clazz);
    }

    //获取所有class对象集合
    public Set<Class<?>> getClasses(){
        return beanMap.keySet();
    }

    //获取所有bean
    public Set<Object> getBeans(){
        return new HashSet<>(beanMap.values());
    }

    //根据注解获取class对象集合
    public Set<Class<?>> getClassesByAnnotation(Class<? extends Annotation> annotation){
        //获取所有class对象
        Set<Class<?>> keySet = this.getClasses();
        if(ValidationUtil.isEmpty(keySet)){
            log.warn("nothing in beanMap");
            return null;
        }
        //筛选注解标注的class对象
        Set<Class<?>> classSet = new HashSet<>();
        for(Class<?> clazz : keySet){
            if(clazz.isAnnotationPresent(annotation)){
                classSet.add(clazz);
            }
        }
        return classSet.size()>0 ? classSet : null;
    }

    //根据接口或者父类获取实现类或者子类的class集合，不包括本身
    public Set<Class<?>> getClassesBySupper(Class<?> interfaceOrClass){
        //获取所有class对象
        Set<Class<?>> keySet = this.getClasses();
        if(ValidationUtil.isEmpty(keySet)){
            log.warn("nothing in beanMap");
            return null;
        }
        //筛选接口或者父类标注的class对象
        Set<Class<?>> classSet = new HashSet<>();
        for(Class<?> clazz : keySet){
            if(interfaceOrClass.isAssignableFrom(clazz) && !clazz.equals(interfaceOrClass)){
                classSet.add(clazz);
            }
        }
        return classSet.size()>0 ? classSet : null;
    }

}
