package com.iov.exception;

import com.iov.pojo.Result;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public Result handleBusinessException(BusinessException be){
        be.printStackTrace();
        return Result.fail(StringUtils.hasLength(be.getMessage())? be.getMessage() : "処理失敗");
    }
    @ExceptionHandler(Exception.class)
    public Result handleAll(Exception e) {
        e.printStackTrace();
        return Result.error(StringUtils.hasLength(e.getMessage())? e.getMessage() :"システムエラー");
    }
}
