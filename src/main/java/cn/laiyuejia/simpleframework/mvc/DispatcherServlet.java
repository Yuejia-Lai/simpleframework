package cn.laiyuejia.simpleframework.mvc;

import cn.laiyuejia.simpleframework.aop.AspectWeaver;
import cn.laiyuejia.simpleframework.core.BeanContainer;
import cn.laiyuejia.simpleframework.inject.DependencyInjector;
import cn.laiyuejia.simpleframework.mvc.processor.RequestProcessor;
import cn.laiyuejia.simpleframework.mvc.processor.impl.ControllerRequestProcessor;
import cn.laiyuejia.simpleframework.mvc.processor.impl.JspRequestProcessor;
import cn.laiyuejia.simpleframework.mvc.processor.impl.PreRequestProcessor;
import cn.laiyuejia.simpleframework.mvc.processor.impl.StaticResourceRequestProcessor;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/*")
public class DispatcherServlet extends HttpServlet {

    List<RequestProcessor> PROCESSOR = new ArrayList<>();

    @Override
    public void init() {
        //初始化容器
        BeanContainer beanContainer = BeanContainer.getInstance();
        beanContainer.loadBeans("cn.laiyuejia");
        new AspectWeaver().doAOP();
        new DependencyInjector().doIoc();
        //初始化请求处理责任链
        PROCESSOR.add(new PreRequestProcessor());
        PROCESSOR.add(new StaticResourceRequestProcessor(getServletContext()));
        PROCESSOR.add(new JspRequestProcessor(getServletContext()));
        PROCESSOR.add(new ControllerRequestProcessor());
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //创建责任链对象实例
        RequestProcessorChain requestProcessorChain = new RequestProcessorChain(PROCESSOR.iterator(),req,resp);
        //通过责任链模式来依次调用请求处理器对请求进行处理
        requestProcessorChain.doRequestProcessorChain();
        //对结果进行渲染
        requestProcessorChain.doRender();
    }

    @Override
    public void destroy() {

    }
}





















