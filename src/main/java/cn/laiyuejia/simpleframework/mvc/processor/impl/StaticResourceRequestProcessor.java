package cn.laiyuejia.simpleframework.mvc.processor.impl;

import cn.laiyuejia.simpleframework.mvc.RequestProcessorChain;
import cn.laiyuejia.simpleframework.mvc.processor.RequestProcessor;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;

public class StaticResourceRequestProcessor implements RequestProcessor {

    private static final String DEFAULT_TOMCAT_SERVLET = "default";
    private static final String STATIC_RESOURCE_PREFIX = "/static/";
    //tomcat默认请求派发器RequestDispatcher的名称
    RequestDispatcher defaultDispatcher;
    public StaticResourceRequestProcessor(ServletContext servletContext) {
        this.defaultDispatcher = servletContext.getNamedDispatcher(DEFAULT_TOMCAT_SERVLET);
        if(defaultDispatcher == null)
            throw new RuntimeException("There is no default tomcat servlet");
        System.out.println("【INFO】The default servlet for static resource is "+DEFAULT_TOMCAT_SERVLET);
    }


    @Override
    public boolean process(RequestProcessorChain requestProcessorChain) throws Exception {
        //1.通过请求路径判断是否是请求的静态资源 webapp/static
        if (isStaticResource(requestProcessorChain.getRequestPath())) {
            //2.如果是静态资源，则将请求转发给default servlet处理
            defaultDispatcher.forward(requestProcessorChain.getRequest(), requestProcessorChain.getResponse());
            return false;
        }
        return true;
    }

    //通过请求路径前缀（目录）是否为静态资源 /static/
    public boolean isStaticResource(String path){
        return path.startsWith(STATIC_RESOURCE_PREFIX);
    }
}
