package cn.laiyuejia.demo.service.combine.impl;

import cn.laiyuejia.demo.entity.bo.HeadLine;
import cn.laiyuejia.demo.entity.bo.ShopCategory;
import cn.laiyuejia.demo.entity.dto.MainPageInfoDTO;
import cn.laiyuejia.demo.entity.dto.Result;
import cn.laiyuejia.demo.service.combine.HeadLineShopCategoryCombineService;
import cn.laiyuejia.demo.service.solo.HeadLineService;
import cn.laiyuejia.demo.service.solo.ShopCategoryService;
import cn.laiyuejia.simpleframework.core.annotation.Service;
import cn.laiyuejia.simpleframework.inject.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Service
@Slf4j
public class HeadLineShopCategoryCombineServiceImpl implements HeadLineShopCategoryCombineService {

    @Autowired
    private HeadLineService headLineService;

    @Autowired
    private ShopCategoryService shopCategoryService;

    @Override
    public Result<MainPageInfoDTO> getMainPageInfo() {
        log.info("getMainPageInfo方法被执行！");
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
