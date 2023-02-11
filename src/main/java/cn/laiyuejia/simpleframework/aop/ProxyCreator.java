package cn.laiyuejia.simpleframework.aop;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

public class ProxyCreator {
    public static Object createProxy(Class<?> targetClass, MethodInterceptor methodInterceptor){
        Object proxy = Enhancer.create(targetClass, methodInterceptor);
        return proxy;
    }
}
