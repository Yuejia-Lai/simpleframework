package cn.laiyuejia.simpleframework.mvc.type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * 封装待执行的Controller及其方法实例和参数的映射
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ControllerMethod {

    private Class<?> controllerClass;

    private Method invokeMethod;

    //方法参数名称以及其参数类型
    private Map<String,Class<?>> methodParameters;
}
