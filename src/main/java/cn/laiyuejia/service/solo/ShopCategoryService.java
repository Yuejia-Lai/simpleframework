package cn.laiyuejia.service.solo;

import cn.laiyuejia.entity.bo.ShopCategory;
import cn.laiyuejia.entity.dto.Result;

import java.util.List;

public interface ShopCategoryService {

    Result<Boolean> addShopCategory(ShopCategory shopCategory);

    Result<Boolean> removeShopCategory(int shopCategoryId);

    Result<Boolean> modifyShopCategory(ShopCategory shopCategory);

    Result<ShopCategory> queryShopCategoryById(int shopCategoryId);

    Result<List<ShopCategory>> queryShopCategory(ShopCategory shopCategoryCondition, int page, int size);
}
