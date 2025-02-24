package io.github.javpower.vectorexsolon.service;

import io.github.javpower.vectorexcore.VectoRexClient;
import io.github.javpower.vectorexcore.model.VectoRexProperties;
import io.github.javpower.vectorexcore.service.AbstractClientBuilder;
import io.github.javpower.vectorexsolon.config.VectorexPropertiesConfiguration;
import org.noear.solon.annotation.Bean;
import org.noear.solon.annotation.Configuration;
import org.noear.solon.core.bean.LifecycleBean;

/**
 * @author cxc
 */
@Configuration
public class VectoRexInit extends AbstractClientBuilder implements LifecycleBean {

    @Bean
    public VectoRexClient init(VectorexPropertiesConfiguration vectoRexPropertiesConfiguration) {
        VectoRexProperties vectoRexProperties = new VectoRexProperties();
        // 手动复制属性，因为没有 BeanUtils
        vectoRexProperties.setEnable(vectoRexPropertiesConfiguration.isEnable());
        vectoRexProperties.setUri(vectoRexPropertiesConfiguration.getUri());
        vectoRexProperties.setPackages(vectoRexPropertiesConfiguration.getPackages());
        super.setProperties(vectoRexProperties);
        super.initialize();
        return getClient();
    }

    @Override
    public void start() throws Throwable {

    }

    @Override
    public void stop() throws Throwable {
    }
}
