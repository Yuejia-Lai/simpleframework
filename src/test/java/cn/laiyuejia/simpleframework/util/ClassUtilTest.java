package cn.laiyuejia.simpleframework.util;

import org.junit.Assert;
import org.junit.Test;

import java.util.Set;

public class ClassUtilTest {

    @Test
    public void extractPackageClassTest(){
        Set<Class<?>> classSet = ClassUtil.extractPackageClass("cn.laiyuejia.demo.controller");
        System.out.println(classSet);
        Assert.assertEquals(4,classSet.size());
    }
}
