package cn.laiyuejia.controller.superadmin;

import cn.laiyuejia.entity.bo.ShopCategory;
import cn.laiyuejia.entity.dto.Result;
import cn.laiyuejia.service.solo.ShopCategoryService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShopCategoryOperationController {

    private ShopCategoryService shopCategoryService;

    Result<Boolean> addShopCategory(HttpServletRequest req, HttpServletResponse resp){
        return shopCategoryService.addShopCategory(new ShopCategory());
    }

    Result<Boolean> removeShopCategory(HttpServletRequest req, HttpServletResponse resp){
        return shopCategoryService.removeShopCategory(1);
    }

    Result<Boolean> modifyShopCategory(HttpServletRequest req, HttpServletResponse resp){
        return shopCategoryService.modifyShopCategory(new ShopCategory());
    }

    Result<ShopCategory> queryShopCategoryById(HttpServletRequest req, HttpServletResponse resp){
        return shopCategoryService.queryShopCategoryById(1);
    }

    Result<List<ShopCategory>> queryShopCategory(ShopCategory shopCategoryCondition, int page, int size){
        return shopCategoryService.queryShopCategory(null,1,100);
    }
}
