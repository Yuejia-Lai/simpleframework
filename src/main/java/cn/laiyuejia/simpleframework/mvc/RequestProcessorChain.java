package cn.laiyuejia.simpleframework.mvc;

import cn.laiyuejia.simpleframework.mvc.processor.RequestProcessor;
import cn.laiyuejia.simpleframework.mvc.render.DefaultResultRender;
import cn.laiyuejia.simpleframework.mvc.render.InternalErrorResultRender;
import cn.laiyuejia.simpleframework.mvc.render.ResultRender;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;

/**
 * 责任链模式执行注册的请求处理器
 * 委派特定的Render实例对处理后的结果进行渲染
 */
@Data
@Slf4j
public class RequestProcessorChain {

    //请求处理迭代器
    private Iterator<RequestProcessor> requestProcessorIterator;

    //
    private HttpServletRequest request;

    private HttpServletResponse response;

    private String requestMethod;

    private String requestPath;

    private int responseCode;

    //请求结果渲染器
    private ResultRender resultRender;


    public RequestProcessorChain(Iterator<RequestProcessor> iterator, HttpServletRequest req, HttpServletResponse resp) {
        this.requestProcessorIterator = iterator;
        this.request = req;
        this.response = resp;
        this.requestMethod = req.getMethod();
        this.requestPath = req.getPathInfo();
        this.responseCode = HttpServletResponse.SC_OK;
    }

    public void doRequestProcessorChain() {
        //通过迭代器遍历注册的请求处理器实现类列表
        try {
            while(requestProcessorIterator.hasNext()){
                //直到请求处理器执行后返回false为止
                if(!requestProcessorIterator.next().process(this)){
                    break;
                }
            }
        }catch (Exception e){
            //出现异常则交由异常渲染器处理
            this.resultRender = new InternalErrorResultRender();
            log.error("调用requestProcessorChain时出现异常：",e);
        }
    }

    public void doRender() {
        //如果请求实现类均未选择合适的渲染器，使用默认渲染器
        if(this.resultRender == null){
            this.resultRender = new DefaultResultRender();
        }
        //调用渲染器执行渲染
        try {
            this.resultRender.render(this);
        } catch (Exception e) {
            log.error("使用渲染器出现异常：",e);
            throw new RuntimeException(e);
        }
    }
}
