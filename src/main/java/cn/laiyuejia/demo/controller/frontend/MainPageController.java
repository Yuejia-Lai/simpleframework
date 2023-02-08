package cn.laiyuejia.demo.controller.frontend;

import cn.laiyuejia.demo.entity.dto.MainPageInfoDTO;
import cn.laiyuejia.demo.entity.dto.Result;
import cn.laiyuejia.demo.service.combine.HeadLineShopCategoryCombineService;
import cn.laiyuejia.simpleframework.core.annotation.Controller;
import cn.laiyuejia.simpleframework.inject.annotation.Autowired;
import lombok.Getter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@Getter
public class MainPageController {

    @Autowired
    private HeadLineShopCategoryCombineService headLineShopCategoryCombineService;

    public Result<MainPageInfoDTO> getMainPageInfo(HttpServletRequest req, HttpServletResponse resp){
        return headLineShopCategoryCombineService.getMainPageInfo();
    }
}
