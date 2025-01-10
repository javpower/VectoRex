package io.github.javpower.vectorextest;

import io.github.javpower.vectorexcore.util.GsonUtil;
import io.github.javpower.vectorexsolon.core.VectoRexResult;
import io.github.javpower.vectorextest.mapper.FaceMapper;
import io.github.javpower.vectorextest.model.Face;
import lombok.extern.slf4j.Slf4j;
import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Get;
import org.noear.solon.annotation.Inject;
import org.noear.solon.annotation.Mapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
public class DemoController {
    @Inject
    FaceMapper mapper;
    @Mapping("/test")
    @Get
    public void hello() {
        face();
    }

    private void face(){
        List<VectoRexResult<Face>> query = mapper.queryWrapper().query();
        System.out.printf(GsonUtil.toJson(query));
        ////
        Face face=new Face();
        List<Float> vector = new ArrayList();
        for (int i = 0; i < 128; i++) {
            vector.add((float) (Math.random() * 100)); // 这里仅作为示例使用随机数
        }
        face.setId(1l);
        face.setVector(vector);
        face.setName("张三");
        mapper.insert(face);

    }
}