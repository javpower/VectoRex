package io.github.javpower.vectorexserver.interceptor;

import io.github.javpower.vectorexserver.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author gc.x
 * @date 2022/6/26
 **/
@Slf4j
public class RefreshTokenInterceptor implements HandlerInterceptor {
    private TokenUtil tokenUtil;
    public RefreshTokenInterceptor(TokenUtil tokenUtil) {
        this.tokenUtil = tokenUtil;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = TokenUtil.getToken(request);
        if(token!=null){
            tokenUtil.reToken(token);
        }
        return true;
    }

}
