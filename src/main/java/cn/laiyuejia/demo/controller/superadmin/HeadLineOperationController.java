package cn.laiyuejia.demo.controller.superadmin;

import cn.laiyuejia.demo.entity.bo.HeadLine;
import cn.laiyuejia.demo.entity.dto.Result;
import cn.laiyuejia.demo.service.solo.HeadLineService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class HeadLineOperationController {
    private HeadLineService headLineService;

    Result<Boolean> addHeadLine(HttpServletRequest req, HttpServletResponse resp){
        return headLineService.addHeadLine(new HeadLine());
    }

    Result<Boolean> removeHeadLine(HttpServletRequest req, HttpServletResponse resp){
        return headLineService.removeHeadLine(1);
    }

    Result<Boolean> modifyHeadLine(HttpServletRequest req, HttpServletResponse resp){
        return headLineService.modifyHeadLine(new HeadLine());
    }

    Result<HeadLine> queryHeadLineById(HttpServletRequest req, HttpServletResponse resp){
        return headLineService.queryHeadLineById(1);
    }

    Result<List<HeadLine>> queryHeadLine(HeadLine headLineCondition, int page, int size){
        return headLineService.queryHeadLine(null,1,100);
    }
}
