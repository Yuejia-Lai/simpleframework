package cn.laiyuejia.simpleframework.aop;

import cn.laiyuejia.demo.controller.frontend.MainPageController;
import cn.laiyuejia.simpleframework.core.BeanContainer;
import cn.laiyuejia.simpleframework.inject.DependencyInjector;
import org.junit.Test;

public class AspectWeaverTest {

    @Test
    public void doAOPTest(){
        BeanContainer beanContainer = BeanContainer.getInstance();
        beanContainer.loadBeans("cn.laiyuejia.demo");
        new AspectWeaver().doAOP();
        new DependencyInjector().doIoc();
        MainPageController mainPageController = (MainPageController) beanContainer.getBean(MainPageController.class);
        mainPageController.getMainPageInfo(null,null);
    }
}
