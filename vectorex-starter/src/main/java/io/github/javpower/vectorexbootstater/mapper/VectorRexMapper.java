package io.github.javpower.vectorexbootstater.mapper;

import io.github.javpower.vectorexbootstater.util.VectorRexSpringUtils;
import io.github.javpower.vectorexcore.VectoRexClient;


public class VectorRexMapper<T> extends BaseVectoRexMapper<T> {

    @Override
    public VectoRexClient getClient() {
        return VectorRexSpringUtils.getBean(VectoRexClient.class);
    }

}
