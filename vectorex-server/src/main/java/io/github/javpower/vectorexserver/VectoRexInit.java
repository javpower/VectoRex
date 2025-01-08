package io.github.javpower.vectorexserver;

import io.github.javpower.vectorexcore.VectoRexClient;
import io.github.javpower.vectorexcore.entity.VectoRexEntity;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VectoRexInit  implements InitializingBean, DisposableBean {

    private VectoRexClient vectoRexDbClient;
    @Override
    public void afterPropertiesSet() {
        initialize();
        handler();
    }
    @Override
    public void destroy() throws Exception {

    }
    public void initialize() {
        vectoRexDbClient = new VectoRexClient(null);
    }
    @Bean
    public VectoRexClient vectoRexClient() {
        return vectoRexDbClient;
    }

    //初始化操作
    public void handler() {
        List<VectoRexEntity> collections = vectoRexDbClient.getCollections();
        for (VectoRexEntity collection : collections) {
            vectoRexDbClient.createCollection(collection);
        }
    }

}