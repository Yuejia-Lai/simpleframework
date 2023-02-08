package cn.laiyuejia.demo.service.combine.impl;

import cn.laiyuejia.demo.entity.bo.HeadLine;
import cn.laiyuejia.demo.entity.bo.ShopCategory;
import cn.laiyuejia.demo.entity.dto.MainPageInfoDTO;
import cn.laiyuejia.demo.entity.dto.Result;
import cn.laiyuejia.demo.service.combine.HeadLineShopCategoryCombineService;
import cn.laiyuejia.demo.service.solo.HeadLineService;
import cn.laiyuejia.demo.service.solo.ShopCategoryService;
import cn.laiyuejia.simpleframework.core.annotation.Service;

import java.util.List;

@Service
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
