package ink.songsong.mall.controller.advice;/*
 *
 * @ClassName AdviceController
 * @Author shensongpeng
 * @Date 2021/6/21 :10:12
 * @Version 1.0
 * */

import ink.songsong.mall.common.api.CommonResult;
import ink.songsong.mall.common.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AdviceController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = Exception.class)
    public CommonResult exceptionHandler(Exception e) {
        if (e instanceof BusinessException) {
            return CommonResult.failed("请求有误"+e.getMessage());
        }
        return CommonResult.failed();
    }
}
