package cn.laiyuejia.demo.aspect;


import cn.laiyuejia.simpleframework.aop.annotation.Aspect;
import cn.laiyuejia.simpleframework.aop.annotation.Order;
import cn.laiyuejia.simpleframework.aop.aspect.DefaultAspect;
import cn.laiyuejia.simpleframework.core.annotation.Controller;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

@Slf4j
@Aspect(pointcut = "execution(* cn.laiyuejia.demo.controller..*.*(..))")
@Order(value = 0)
public class ControllerTimeCalculatorAspect extends DefaultAspect {

    private long timestampCache;

    @Override
    public void before(Class<?> targetClass, Method method, Object[] args) throws Throwable {
        log.info("开始计时，执行的类是[{}],执行的方法是[{}]，参数是[{}]",targetClass.getName(),method.getName(),args);
        this.timestampCache = System.currentTimeMillis();
    }

    @Override
    public Object afterReturning(Class<?> targetClass, Method method, Object[] args, Object returnValue) {
        long endTime = System.currentTimeMillis();
        long costTime = endTime - this.timestampCache;
        log.info("结束计时，执行的类是[{}],执行的方法是[{}]，参数是[{}],返回值是[{}]，时间为[{}]"
                ,targetClass.getName(),method.getName(),args,returnValue,costTime);
        return returnValue;
    }
}
