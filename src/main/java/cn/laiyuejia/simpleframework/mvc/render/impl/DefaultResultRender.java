package cn.laiyuejia.simpleframework.mvc.render.impl;

import cn.laiyuejia.simpleframework.mvc.RequestProcessorChain;
import cn.laiyuejia.simpleframework.mvc.render.ResultRender;

/**
 * 默认渲染器
 */
public class DefaultResultRender implements ResultRender {
    @Override
    public void render(RequestProcessorChain requestProcessorChain) throws Exception {
        requestProcessorChain.getResponse().setStatus(requestProcessorChain.getResponseCode());
    }
}
