package cn.laiyuejia.simpleframework.mvc.render.impl;

import cn.laiyuejia.simpleframework.mvc.RequestProcessorChain;
import cn.laiyuejia.simpleframework.mvc.render.ResultRender;
import com.google.gson.Gson;

import java.io.PrintWriter;

/**
 * Json渲染器
 */
public class JsonResultRender implements ResultRender {

    private Object jsonData;

    public JsonResultRender(Object result) {
        this.jsonData = result;
    }

    @Override
    public void render(RequestProcessorChain requestProcessorChain) throws Exception {
        //设置响应头
        requestProcessorChain.getResponse().setContentType("application/json");
        requestProcessorChain.getResponse().setCharacterEncoding("UTF-8");
        //响应流写入经过gson格式化后的结果
        try (  PrintWriter writer = requestProcessorChain.getResponse().getWriter()){
            Gson gson = new Gson();
            writer.write(gson.toJson(jsonData));
            writer.flush();
        }

    }
}
