package io.github.javpower.vectorexserver;

import io.github.javpower.vectorex.keynote.VectorDB;
import io.github.javpower.vectorexcore.VectoRexClient;
import io.github.javpower.vectorexcore.entity.VectoRexEntity;
import io.github.javpower.vectorexserver.config.VectorRex;
import io.github.javpower.vectorexserver.service.SysBizService;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VectoRexInit  implements InitializingBean, DisposableBean {
    private VectoRexClient vectoRexDbClient;
    private SysBizService sysBizService;
    @Autowired
    private VectorRex vectorRex;
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
    @Bean
    public SysBizService dashboardService() {
        return sysBizService;
    }

    public void handler() {
        List<VectoRexEntity> collections = vectoRexDbClient.getCollections();
        for (VectoRexEntity collection : collections) {
            vectoRexDbClient.createCollection(collection);
        }
        sysBizService =new SysBizService(VectorDB.mapDBManager.getDb());
        if (!sysBizService.existUser(vectorRex.getUsername())) {
            sysBizService.addUser(vectorRex.getUsername(),vectorRex.getPassword());
        }
    }

}