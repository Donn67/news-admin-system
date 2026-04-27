package com.iov.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {
    private Integer code;//业务状态码  0-成功  1-失败
    private String message;
    private T data;


    public static <E> Result<E> success(E data) {
        return new Result<>(0, "操作成功", data);
    }


    public static Result success() {
        return new Result(200, "操作成功", null);
    }
    public static Result fail(String message){
        return new Result(400,message,null);
    }
    public static Result error(String message) {
        return new Result(500, message, null);
    }
}
