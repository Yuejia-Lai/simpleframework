package cn.laiyuejia.simpleframework.mvc.render;

import cn.laiyuejia.simpleframework.mvc.RequestProcessorChain;

public interface ResultRender {

    void render(RequestProcessorChain requestProcessorChain) throws Exception;
}
