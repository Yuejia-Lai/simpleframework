package cn.laiyuejia.demo.entity.dto;

import cn.laiyuejia.demo.entity.bo.HeadLine;
import cn.laiyuejia.demo.entity.bo.ShopCategory;
import lombok.Data;

import java.util.List;

@Data
public class MainPageInfoDTO {
    private List<HeadLine> headLineList;

    private List<ShopCategory> shopCategoryList;
}
