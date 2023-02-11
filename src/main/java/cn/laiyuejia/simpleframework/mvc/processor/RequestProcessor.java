package cn.laiyuejia.simpleframework.mvc.processor;

import cn.laiyuejia.simpleframework.mvc.RequestProcessorChain;

/**
 * 请求执行器
 */
public interface RequestProcessor {

    boolean process(RequestProcessorChain requestProcessorChain) throws Exception;



}
