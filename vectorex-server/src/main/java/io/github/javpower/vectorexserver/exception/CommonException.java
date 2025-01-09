package io.github.javpower.vectorexserver.exception;


import io.github.javpower.vectorexserver.response.ServerResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 自定义异常处理
 */
@Slf4j
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommonException extends RuntimeException {

    private Throwable error;

    private ServerResponse serverResponse;

    public static CommonException create(ServerResponse responseEntity) {
        CommonException exception = new CommonException();
        exception.serverResponse = responseEntity;
        return exception;
    }

    public static CommonException create(Throwable error, ServerResponse responseEntity) {
        log.error(getTrace(error));
        CommonException exception = new CommonException();
        exception.serverResponse = responseEntity;
        exception.error = error;
        return exception;
    }


    public static String getTrace(Throwable t) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        t.printStackTrace(writer);
        StringBuffer buffer = stringWriter.getBuffer();
        return buffer.toString();
    }
}
