package cn.laiyuejia.simpleframework.core;

import cn.laiyuejia.demo.controller.frontend.MainPageController;
import cn.laiyuejia.demo.controller.superadmin.HeadLineOperationController;
import cn.laiyuejia.demo.service.combine.HeadLineShopCategoryCombineService;
import cn.laiyuejia.demo.service.combine.impl.HeadLineShopCategoryCombineServiceImpl;
import cn.laiyuejia.simpleframework.core.annotation.Controller;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BeanContainerTest {

    private static BeanContainer beanContainer;

    @BeforeClass
    public static void init(){
        beanContainer = BeanContainer.getInstance();
    }

    @Test
    public void test1_loadBeanTest(){
        Assert.assertEquals(false,beanContainer.isLoaded());
        beanContainer.loadBeans("cn.laiyuejia.demo");
        Assert.assertEquals(7,beanContainer.size());
        Assert.assertEquals(true,beanContainer.isLoaded());
    }

    @Test
    public void test2_getBeanTest(){
        MainPageController controller = (MainPageController) beanContainer.getBean(MainPageController.class);
        Assert.assertEquals(true,controller instanceof MainPageController);
        HeadLineOperationController headLineOperationController
                = (HeadLineOperationController) beanContainer.getBean(HeadLineOperationController.class);
        Assert.assertEquals(null,headLineOperationController);
    }

    @Test
    public void test3_getClassesByAnnotationTest(){
        Assert.assertEquals(true,beanContainer.isLoaded());
        Assert.assertEquals(2,beanContainer.getClassesByAnnotation(Controller.class).size());
    }

    @Test
    public void test4_getClassesBySuperTest(){
        Assert.assertEquals(true,beanContainer.isLoaded());
        Assert.assertEquals(true,
                beanContainer.getClassesBySupper(HeadLineShopCategoryCombineService.class).contains(HeadLineShopCategoryCombineServiceImpl.class));
        System.out.println(beanContainer.getClassesBySupper(HeadLineShopCategoryCombineService.class));
    }


}
