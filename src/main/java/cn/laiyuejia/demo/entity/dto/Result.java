package cn.laiyuejia.demo.entity.dto;

import lombok.Data;

@Data
public class Result<T> {

    private int code;

    private String message;

    private T data;

    public Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <E> Result<E> ok(E data){
        return new Result<>(200,"成功！",data);
    }
}
