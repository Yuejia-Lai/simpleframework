package cn.laiyuejia.entity.dto;

import cn.laiyuejia.entity.bo.HeadLine;
import cn.laiyuejia.entity.bo.ShopCategory;
import lombok.Data;

import java.util.List;

@Data
public class MainPageInfoDTO {
    private List<HeadLine> headLineList;

    private List<ShopCategory> shopCategoryList;
}
