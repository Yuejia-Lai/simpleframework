package cn.laiyuejia.simpleframework.inject;

import cn.laiyuejia.demo.controller.frontend.MainPageController;
import cn.laiyuejia.simpleframework.core.BeanContainer;
import org.junit.Assert;
import org.junit.Test;

public class DependencyInjectorTest {

    @Test
    public void doIocTest(){
        BeanContainer beanContainer = BeanContainer.getInstance();
        beanContainer.loadBeans("cn.laiyuejia.demo");
        Assert.assertEquals(true,beanContainer.isLoaded());
        MainPageController controller = (MainPageController) beanContainer.getBean(MainPageController.class);
        Assert.assertEquals(true,controller instanceof MainPageController);
        Assert.assertEquals(null,controller.getHeadLineShopCategoryCombineService());
        new DependencyInjector().doIoc();
        Assert.assertNotEquals(null,controller.getHeadLineShopCategoryCombineService());

    }
}
