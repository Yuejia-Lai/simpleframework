package cn.laiyuejia.demo.service.solo;

import cn.laiyuejia.demo.entity.bo.HeadLine;
import cn.laiyuejia.demo.entity.dto.Result;

import java.util.List;

public interface HeadLineService {

    Result<Boolean> addHeadLine(HeadLine headLine);

    Result<Boolean> removeHeadLine(int headLineId);

    Result<Boolean> modifyHeadLine(HeadLine headLine);

    Result<HeadLine> queryHeadLineById(int headLineId);

    Result<List<HeadLine>> queryHeadLine(HeadLine headLineCondition,int page,int size);


}
