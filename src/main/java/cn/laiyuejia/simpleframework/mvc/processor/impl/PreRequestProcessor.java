package cn.laiyuejia.simpleframework.mvc.processor.impl;

import cn.laiyuejia.simpleframework.mvc.RequestProcessorChain;
import cn.laiyuejia.simpleframework.mvc.processor.RequestProcessor;

/**
 * 请求处理，预编码以及路径处理
 */
public class PreRequestProcessor implements RequestProcessor {

    @Override
    public boolean process(RequestProcessorChain requestProcessorChain) throws Exception {
        // 设置请求编码，将其统一设置成UTF-8
        requestProcessorChain.getRequest().setCharacterEncoding("UTF-8");
        // 将请求路径末尾的/剔除，为后续匹配Controller请求路径做准备
        // （一般Controller的处理路径是/aaa/bbb，所以如果传入的路径结尾是/aaa/bbb/，
        // 就需要处理成/aaa/bbb）
        String requestPath = requestProcessorChain.getRequestPath();
        //http://localhost:8080/simpleframework requestPath="/"
        if(requestPath.length() > 1 && requestPath.endsWith("/")){
            requestProcessorChain.setRequestPath(requestPath.substring(0, requestPath.length() - 1));
        }
        System.out.println("【INFO】preprocess request "+requestProcessorChain.getRequestMethod()+"  "+requestProcessorChain.getRequestPath());
        return true;
    }
}
