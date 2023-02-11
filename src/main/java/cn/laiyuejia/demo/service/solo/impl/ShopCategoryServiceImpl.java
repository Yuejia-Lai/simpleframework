package cn.laiyuejia.demo.service.solo.impl;

import cn.laiyuejia.demo.entity.bo.ShopCategory;
import cn.laiyuejia.demo.entity.dto.Result;
import cn.laiyuejia.demo.service.solo.ShopCategoryService;
import cn.laiyuejia.simpleframework.core.annotation.Service;

import java.util.List;

@Service
public class ShopCategoryServiceImpl implements ShopCategoryService {

    @Override
    public Result<Boolean> addShopCategory(ShopCategory shopCategory) {
        return null;
    }

    @Override
    public Result<Boolean> removeShopCategory(int shopCategoryId) {
        return null;
    }

    @Override
    public Result<Boolean> modifyShopCategory(ShopCategory shopCategory) {
        return null;
    }

    @Override
    public Result<ShopCategory> queryShopCategoryById(int shopCategoryId) {
        return null;
    }

    @Override
    public Result<List<ShopCategory>> queryShopCategory(ShopCategory shopCategoryCondition, int page, int size) {
        return null;
    }
}
