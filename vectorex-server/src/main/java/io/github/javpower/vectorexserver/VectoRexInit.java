package io.github.javpower.vectorexserver;

import io.github.javpower.vectorex.keynote.VectorDB;
import io.github.javpower.vectorexcore.VectoRexClient;
import io.github.javpower.vectorexcore.entity.VectoRexEntity;
import io.github.javpower.vectorexserver.service.DashboardService;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VectoRexInit  implements InitializingBean, DisposableBean {

    private VectoRexClient vectoRexDbClient;
    private DashboardService dashboardService;
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
    public DashboardService dashboardService() {
        return dashboardService;
    }

    public void handler() {
        List<VectoRexEntity> collections = vectoRexDbClient.getCollections();
        for (VectoRexEntity collection : collections) {
            vectoRexDbClient.createCollection(collection);
        }
        dashboardService=new DashboardService(VectorDB.mapDBManager.getDb());
    }

}