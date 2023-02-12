package cn.laiyuejia.simpleframework.mvc.processor.impl;

import cn.laiyuejia.simpleframework.mvc.RequestProcessorChain;
import cn.laiyuejia.simpleframework.mvc.processor.RequestProcessor;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;

public class JspRequestProcessor implements RequestProcessor {

    private static final String JSP_SERVLET = "jsp";
    private static final String JSP_RESOURCE_PREFIX = "/templates/";

    private RequestDispatcher jspServlet;

    public JspRequestProcessor(ServletContext servletContext){
        this.jspServlet = servletContext.getNamedDispatcher(JSP_SERVLET);
        if(null == jspServlet){
            throw new RuntimeException("没有找到jsp servlet");
        }
    }
    @Override
    public boolean process(RequestProcessorChain requestProcessorChain) throws Exception {
        if(isJspResource(requestProcessorChain.getRequestPath())){
            jspServlet.forward(requestProcessorChain.getRequest(), requestProcessorChain.getResponse());
            return false;
        }
        return true;
    }

    private boolean isJspResource(String requestPath) {
        return requestPath.startsWith(JSP_RESOURCE_PREFIX);
    }

}
