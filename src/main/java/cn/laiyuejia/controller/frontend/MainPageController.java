package cn.laiyuejia.controller.frontend;

import cn.laiyuejia.entity.dto.MainPageInfoDTO;
import cn.laiyuejia.entity.dto.Result;
import cn.laiyuejia.service.combine.HeadLineShopCategoryCombineService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainPageController {
    private HeadLineShopCategoryCombineService headLineShopCategoryCombineService;

    public Result<MainPageInfoDTO> getMainPageInfo(HttpServletRequest req, HttpServletResponse resp){
        return headLineShopCategoryCombineService.getMainPageInfo();
    }
}
