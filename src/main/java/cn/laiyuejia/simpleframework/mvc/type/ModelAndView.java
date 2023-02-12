package cn.laiyuejia.simpleframework.mvc.type;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class ModelAndView {

    private String view;

    private Map<String,Object> model = new HashMap<>();

    public ModelAndView setView(String view) {
        this.view = view;
        return this;
    }

    public ModelAndView addViewData(String attributeName , Object attributeValue){
        model.put(attributeName,attributeValue);
        return this;
    }
}
