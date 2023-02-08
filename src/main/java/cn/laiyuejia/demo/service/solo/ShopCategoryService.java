package cn.laiyuejia.demo.service.solo;

import cn.laiyuejia.demo.entity.bo.ShopCategory;
import cn.laiyuejia.demo.entity.dto.Result;

import java.util.List;

public interface ShopCategoryService {

    Result<Boolean> addShopCategory(ShopCategory shopCategory);

    Result<Boolean> removeShopCategory(int shopCategoryId);

    Result<Boolean> modifyShopCategory(ShopCategory shopCategory);

    Result<ShopCategory> queryShopCategoryById(int shopCategoryId);

    Result<List<ShopCategory>> queryShopCategory(ShopCategory shopCategoryCondition, int page, int size);
}
