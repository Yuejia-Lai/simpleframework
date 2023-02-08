package cn.laiyuejia.simpleframework.inject;

import cn.laiyuejia.simpleframework.core.BeanContainer;
import cn.laiyuejia.simpleframework.inject.annotation.Autowired;
import cn.laiyuejia.simpleframework.util.ClassUtil;
import cn.laiyuejia.simpleframework.util.ValidationUtil;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.Set;

@Slf4j
public class DependencyInjector {

    private BeanContainer beanContainer;

    public DependencyInjector(){
        beanContainer = BeanContainer.getInstance();
    }

    //执行Ioc
    public void doIoc(){
        //遍历容器中所有class对象
        if(ValidationUtil.isEmpty(beanContainer.getClasses())){
            log.warn("容器为空，注入失败！");
            return;
        }
        for(Class<?> clazz : beanContainer.getClasses()){
            //根据class对象获取所有成员变量
            Field[] fields = clazz.getDeclaredFields();
            if(ValidationUtil.isEmpty(fields)){
                continue;
            }
            for(Field field : fields){
                //找出被标记的成员变量，并获取类型
                if(field.isAnnotationPresent(Autowired.class)){
                    Autowired autowired = field.getAnnotation(Autowired.class);
                    String value = autowired.value();
                    Class<?> fieldClass = field.getType();
                    //容器里找到对应类型的实例
                    Object fieldValue = getFieldInstance(fieldClass,value);
                    if(fieldValue ==null){
                        throw new RuntimeException("无法获取对应类型的实例："+fieldClass.getName()+" "+value);
                    }
                    //通过反射注入
                    Object targetBean = beanContainer.getBean(clazz);
                    ClassUtil.setField(field,targetBean,fieldValue);
                }
            }
        }
    }

    //根据class对象在容器里获取实例或者实现类
    private Object getFieldInstance(Class<?> fieldClass,String autowiredValue) {
        Object fieldValue = beanContainer.getBean(fieldClass);
        if(fieldValue !=null){
            return fieldValue;
        }
        Class<?> implementClass = getImplementClass(fieldClass,autowiredValue);
        if(implementClass !=null){
            return beanContainer.getBean(implementClass);
        }
        return null;
    }

    private Class<?> getImplementClass(Class<?> fieldClass,String autowiredValue) {
        Set<Class<?>> classes = beanContainer.getClassesBySupper(fieldClass);
        if(!ValidationUtil.isEmpty(classes)){
            if(ValidationUtil.isEmpty(autowiredValue)){
                if(classes.size()==1){
                    return classes.iterator().next();
                }else{
                    throw new RuntimeException(
                            fieldClass.getName()+"有多个实现类," +"请选择一个实现类："+classes);
                }
            }
            for(Class<?> clazz : classes){
                if(autowiredValue.equals(clazz.getSimpleName())){
                    return clazz;
                }
            }
        }
        return null;
    }
}
