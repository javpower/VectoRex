package io.github.javpower.vectorexserver.exception;

import io.github.javpower.vectorexcore.util.GsonUtil;
import io.github.javpower.vectorexserver.response.ServerResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


@Slf4j
@ControllerAdvice
public class CommonExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(value = {CommonException.class})
    public ServerResponse myError(CommonException e) {
        log.error(GsonUtil.toJson(e.getServerResponse()));
        return e.getServerResponse();
    }


    @ResponseBody
    @ExceptionHandler(value = {Exception.class})
    public ServerResponse globalError(HttpServletRequest request, Exception e) {
        // 输出控制台打印日志
        log.error(e.getMessage());
        // 返回前端提示信息
        return ServerResponse.createByError(500, "未知错误！");
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ServerResponse throwCustomException(MethodArgumentNotValidException methodArgumentNotValidException){
        log.error(methodArgumentNotValidException.getMessage());
        return ServerResponse.createByError("非法参数",methodArgumentNotValidException.getBindingResult().getFieldError().getDefaultMessage());
    }


}
