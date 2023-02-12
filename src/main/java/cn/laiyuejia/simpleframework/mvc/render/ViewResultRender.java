package cn.laiyuejia.simpleframework.mvc.render;

import cn.laiyuejia.simpleframework.mvc.RequestProcessorChain;
import cn.laiyuejia.simpleframework.mvc.type.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 页面渲染器
 */
public class ViewResultRender implements ResultRender{

    private ModelAndView modelAndView;

    public ViewResultRender(Object mv) {
        if(mv instanceof ModelAndView){
            this.modelAndView = (ModelAndView) mv;
        } else if (mv instanceof String) {
            this.modelAndView = new ModelAndView().setView((String) mv);
        }else{
            throw new RuntimeException("非法请求结果类型！");
        }
    }


    @Override
    public void render(RequestProcessorChain requestProcessorChain) throws Exception {
        HttpServletRequest request = requestProcessorChain.getRequest();
        HttpServletResponse response = requestProcessorChain.getResponse();
        String path = modelAndView.getView();
        Map<String,Object> model = modelAndView.getModel();
        for(Map.Entry<String,Object> entry : model.entrySet()){
            request.setAttribute(entry.getKey(), entry.getValue());
        }
        request.getRequestDispatcher("/templates/"+path).forward(request,response);
    }


}
