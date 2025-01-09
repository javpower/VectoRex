package io.github.javpower.vectorexserver.controller;

import io.github.javpower.vectorexserver.annotation.IgnoreLogin;
import io.github.javpower.vectorexserver.config.VectorRex;
import io.github.javpower.vectorexserver.req.LoginUser;
import io.github.javpower.vectorexserver.response.ServerResponse;
import io.github.javpower.vectorexserver.util.TokenUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/vectorex")
@Tag(name = "登录", description = "用于接口权限校验")
public class LoginController {
    @Autowired
    private VectorRex user;

    @PostMapping("/login")
    @IgnoreLogin
    public ServerResponse login(@RequestBody LoginUser req) {
       if(Objects.equals(req.getUsername(),user.getUsername())&&Objects.equals(req.getPassword(),user.getPassword())){
           return ServerResponse.createBySuccess("操作成功",TokenUtil.generateToken(req.getUsername(),req.getPassword()));
       }
       return ServerResponse.createByError("密码错误");
    }

}