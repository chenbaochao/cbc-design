package com.cbc.design.common.exception;

import com.cbc.design.common.ResponseBean;
import com.cbc.design.common.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by cbc on 2018/3/30.
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = RepeatSubmitException.class)
    public ResponseBean handlerRepeatSubmitException(RepeatSubmitException e){
        log.error("异常信息为：{}",e);
        return ResultUtil.error(e.getCode(),e.getMessage());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseBean handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("异常信息为：{}",e);
        List<FieldError> errors = e.getBindingResult().getFieldErrors();
        String message = errors.stream().map(u -> u.getField() + ": " + u.getDefaultMessage()).collect(Collectors.joining(","));
        return ResultUtil.error(HttpStatus.BAD_REQUEST.value(),message);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseBean handValidationException(ValidationException e){
        log.error("异常信息为：{}");
        return ResultUtil.error(e.getCode(),e.getMessage());
    }
}