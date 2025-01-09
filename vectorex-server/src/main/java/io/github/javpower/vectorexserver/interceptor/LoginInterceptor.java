package io.github.javpower.vectorexserver.interceptor;


import io.github.javpower.vectorexserver.annotation.IgnoreLogin;
import io.github.javpower.vectorexserver.exception.CommonException;
import io.github.javpower.vectorexserver.response.ServerResponse;
import io.github.javpower.vectorexserver.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static io.github.javpower.vectorexserver.response.ResponseCode.NO_AUTH;

/**
 * @author gc.x
 * @date 2022/6/26
 **/
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    private TokenUtil tokenUtil;
    public LoginInterceptor(TokenUtil tokenUtil) {
        this.tokenUtil = tokenUtil;
    }
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestUrl = request.getRequestURI();
        System.out.println("requestUrl = " + requestUrl);
        // 如果访问的是控制器
        if (handler instanceof HandlerMethod) {
            // 排除不需要登录验证的路径 路径配置 注解
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            IgnoreLogin ignoreLogin = handlerMethod.getMethodAnnotation(IgnoreLogin.class);
            if (ignoreLogin != null) {
                return true;
            }
            String token = TokenUtil.getToken(request);
            if (StringUtils.isBlank(token)) {
                throw CommonException.create(ServerResponse.createByError(NO_AUTH.getCode(),"token不能为空"));
            }
            //校验 token
            if(!tokenUtil.validateToken(token)){
                throw CommonException.create(ServerResponse.createByError(NO_AUTH.getCode(),"无效token"));
            }
        }
        return true;
    }


}
