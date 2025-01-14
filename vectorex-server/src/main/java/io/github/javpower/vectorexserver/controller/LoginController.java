package io.github.javpower.vectorexserver.controller;

import io.github.javpower.vectorexserver.annotation.IgnoreLogin;
import io.github.javpower.vectorexserver.req.LoginUser;
import io.github.javpower.vectorexserver.response.ServerResponse;
import io.github.javpower.vectorexserver.service.SysBizService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vectorex")
@Tag(name = "登录", description = "用于接口权限校验")
public class LoginController {
    @Autowired
    private SysBizService sysBizService;

    @PostMapping("/login")
    @IgnoreLogin
    public ServerResponse login(@RequestBody LoginUser req) {
        if (sysBizService.verifyUser(req.getUsername(), req.getPassword())) {
            return ServerResponse.createBySuccess("操作成功", sysBizService.generateToken(req.getUsername(),req.getPassword()));
        }
       return ServerResponse.createByError("密码错误");
    }

    @PostMapping("/change/password")
    public ServerResponse changePassword(@RequestBody LoginUser req) {
        if (sysBizService.existUser(req.getUsername())) {
            sysBizService.addUser(req.getUsername(), req.getPassword());
            return ServerResponse.createBySuccess("操作成功", sysBizService.generateToken(req.getUsername(),req.getPassword()));
        }
        return ServerResponse.createByError("用户不存在");
    }

    @PostMapping("/logout")
    public ServerResponse logout() throws Exception {
        sysBizService.delToken();
        return ServerResponse.createBySuccess("登出成功");
    }

}