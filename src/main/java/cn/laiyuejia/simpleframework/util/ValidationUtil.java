package cn.laiyuejia.simpleframework.util;

import com.sun.org.apache.bcel.internal.generic.PUSH;

import java.util.Collection;
import java.util.Map;

public class ValidationUtil {

    //集合判空
    public static boolean isEmpty(Collection<?> collection){
        return collection==null || collection.isEmpty();
    }

    public static boolean isEmpty(String str){
        return str==null ||"".equals(str);
    }

    public static boolean isEmpty(Object[] obj){
        return obj==null || obj.length==0;
    }

    public static boolean isEmpty(Map<?,?> map){
        return map==null || map.isEmpty();
    }
}
