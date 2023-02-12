package cn.laiyuejia.simpleframework.mvc.render;

import cn.laiyuejia.simpleframework.mvc.RequestProcessorChain;

/**
 * 默认渲染器
 */
public class DefaultResultRender implements ResultRender{
    @Override
    public void render(RequestProcessorChain requestProcessorChain) throws Exception {
        requestProcessorChain.getResponse().setStatus(requestProcessorChain.getResponseCode());
    }
}
