package io.github.javpower.vectorexbootstater.service;

import io.github.javpower.vectorexbootstater.config.VectoRexPropertiesConfiguration;
import io.github.javpower.vectorexcore.VectoRexClient;
import io.github.javpower.vectorexcore.model.VectoRexProperties;
import io.github.javpower.vectorexcore.service.AbstractClientBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class VectoRexInit extends AbstractClientBuilder implements InitializingBean, DisposableBean {

    private final VectoRexPropertiesConfiguration vectoRexPropertiesConfiguration;

    private VectoRexClient client;

    public VectoRexInit(VectoRexPropertiesConfiguration vectoRexPropertiesConfiguration) {
        this.vectoRexPropertiesConfiguration = vectoRexPropertiesConfiguration;
    }

    @Override
    public void afterPropertiesSet() {
        initialize();
    }
    @Override
    public void destroy() throws Exception {

    }
    public void initialize() {
        VectoRexProperties vectoRexProperties = new VectoRexProperties();
        BeanUtils.copyProperties(vectoRexPropertiesConfiguration, vectoRexProperties);
        super.setProperties(vectoRexProperties);
        super.initialize();
        client = getClient();
    }

    @Bean
    public VectoRexClient vectoRexClient() {
        return client;
    }




}