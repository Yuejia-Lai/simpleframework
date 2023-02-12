package cn.laiyuejia.demo.controller.frontend;

import cn.laiyuejia.demo.entity.bo.HeadLine;
import cn.laiyuejia.demo.entity.dto.MainPageInfoDTO;
import cn.laiyuejia.demo.entity.dto.Result;
import cn.laiyuejia.demo.service.combine.HeadLineShopCategoryCombineService;
import cn.laiyuejia.simpleframework.core.annotation.Controller;
import cn.laiyuejia.simpleframework.inject.annotation.Autowired;
import cn.laiyuejia.simpleframework.mvc.annotation.RequestMapping;
import cn.laiyuejia.simpleframework.mvc.annotation.RequestParam;
import cn.laiyuejia.simpleframework.mvc.annotation.ResponseBody;
import cn.laiyuejia.simpleframework.mvc.type.ModelAndView;
import cn.laiyuejia.simpleframework.mvc.type.RequestMethod;
import lombok.Getter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
@Getter
@RequestMapping("/main")
public class MainPageController {

    @Autowired
    private HeadLineShopCategoryCombineService headLineShopCategoryCombineService;

    public Result<MainPageInfoDTO> getMainPageInfo(HttpServletRequest req, HttpServletResponse resp){
        return headLineShopCategoryCombineService.getMainPageInfo();
    }

    @RequestMapping(value = "/test1",method = RequestMethod.GET)
    public void getTest(){
        return;
    }

    @RequestMapping(value = "/test2",method = RequestMethod.GET)
    public void getTest2(){
        System.out.println("调用test2方法");
        throw new RuntimeException("test error");
    }

    @RequestMapping(value = "/test3",method = RequestMethod.GET)
    @ResponseBody
    public Result queryList(){
        List<HeadLine> headLineList = new ArrayList<>();
        HeadLine headLine1 = new HeadLine();
        headLine1.setLineId(1L);
        headLine1.setLineName("头条1");
        headLine1.setLineLink("www.baidu.com");
        headLine1.setLineImg("头条图片1地址");
        headLineList.add(headLine1);
        HeadLine headLine2 = new HeadLine();
        headLine2.setLineId(2L);
        headLine2.setLineName("头条2");
        headLine2.setLineLink("www.google.com");
        headLine2.setLineImg("头条图片2地址");
        headLineList.add(headLine2);
        return Result.ok(headLineList);
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public ModelAndView add(@RequestParam("lineName") String lineName
            , @RequestParam("lineLink") String lineLink
            , @RequestParam("lineImg") String lineImg
            , @RequestParam("priority") Integer priority){
        HeadLine headLine = new HeadLine();
        headLine.setLineLink(lineLink);
        headLine.setLineImg(lineImg);
        headLine.setLineName(lineName);
        headLine.setPriority(priority);
        System.out.println(headLine);
        Result<Boolean> result = new Result<>(200,"请求成功",true);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setView("addheadline.jsp").addViewData("result",result);
        return modelAndView;
    }
}
