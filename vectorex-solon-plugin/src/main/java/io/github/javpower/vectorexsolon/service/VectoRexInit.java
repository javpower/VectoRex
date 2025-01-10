package io.github.javpower.vectorexsolon.service;

import io.github.javpower.vectorexcore.VectoRexClient;
import io.github.javpower.vectorexcore.model.VectoRexProperties;
import io.github.javpower.vectorexcore.service.AbstractClientBuilder;
import io.github.javpower.vectorexsolon.config.VectorexPropertiesConfiguration;
import org.noear.solon.annotation.Bean;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;
import org.noear.solon.core.bean.LifecycleBean;
import org.springframework.beans.BeanUtils;

/**
 * @author cxc
 */
@Component
public class VectoRexInit extends AbstractClientBuilder implements LifecycleBean {

    @Inject
    private VectorexPropertiesConfiguration vectoRexPropertiesConfiguration;

    // 添加无参构造函数
    public VectoRexInit() {

    }

    private VectoRexClient client;




    public void initialize() {
        VectoRexProperties vectoRexProperties = new VectoRexProperties();
        // 手动复制属性，因为没有 BeanUtils
        BeanUtils.copyProperties(vectoRexPropertiesConfiguration, vectoRexProperties);

        super.setProperties(vectoRexProperties);
        super.initialize();
        client = getClient();
    }

    @Bean
    public VectoRexClient vectoRexClient() {
        return client;
    }

    public void start() throws Throwable {
        initialize();
    }

    public void stop() throws Throwable {
//        LifecycleBean.super.stop();
    }
}
