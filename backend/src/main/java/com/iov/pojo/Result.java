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
        return new Result<>(5000, "操作完了", data);
    }


    public static Result success() {
        return new Result(5000, "操作完了", null);
    }
    public static Result fail(String message){
        return new Result(5001,message,null);
    }
    public static Result error(String message) {
        return new Result(5002, message, null);
    }
}
