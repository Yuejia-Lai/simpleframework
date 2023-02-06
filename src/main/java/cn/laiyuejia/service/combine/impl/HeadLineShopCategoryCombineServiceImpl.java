package cn.laiyuejia.service.combine.impl;

import cn.laiyuejia.entity.bo.HeadLine;
import cn.laiyuejia.entity.bo.ShopCategory;
import cn.laiyuejia.entity.dto.MainPageInfoDTO;
import cn.laiyuejia.entity.dto.Result;
import cn.laiyuejia.service.combine.HeadLineShopCategoryCombineService;
import cn.laiyuejia.service.solo.HeadLineService;
import cn.laiyuejia.service.solo.ShopCategoryService;

import java.util.List;

public class HeadLineShopCategoryCombineServiceImpl implements HeadLineShopCategoryCombineService {

    private HeadLineService headLineService;

    private ShopCategoryService shopCategoryService;

    @Override
    public Result<MainPageInfoDTO> getMainPageInfo() {
        HeadLine headLine = new HeadLine();
        headLine.setEnableStatus(1);
        Result<List<HeadLine>> headLineResult = headLineService.queryHeadLine(headLine, 1, 4);
        ShopCategory shopCategory = new ShopCategory();
        Result<List<ShopCategory>> shopCategoryResult = shopCategoryService.queryShopCategory(shopCategory, 1, 100);

        Result<MainPageInfoDTO> result = mergeMainPageInfoResult(headLineResult,shopCategoryResult);
        return result;
    }

    private Result<MainPageInfoDTO> mergeMainPageInfoResult(Result<List<HeadLine>> headLineResult, Result<List<ShopCategory>> shopCategoryResult) {
        return null;
    }
}
