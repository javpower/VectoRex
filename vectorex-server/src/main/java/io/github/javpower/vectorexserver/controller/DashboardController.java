package io.github.javpower.vectorexserver.controller;

import io.github.javpower.vectorexserver.response.ServerResponse;
import io.github.javpower.vectorexserver.service.VectoRexService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/vectorex")
@Slf4j
@Tag(name = "仪表盘管理", description = "用于仪表盘数据的接口")
public class DashboardController {
    @Autowired
    private VectoRexService vectoRexService;

    @PostMapping("/dashboard")
    @Operation(summary = "仪表盘展示数据")
    public ServerResponse<Map<String, Object>> dashboard() {
        Map<String, Object> dashboard = vectoRexService.getDashboard();
        return ServerResponse.createBySuccess("操作成功",dashboard);
    }


}
