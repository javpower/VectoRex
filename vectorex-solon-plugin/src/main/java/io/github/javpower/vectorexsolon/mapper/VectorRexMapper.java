package io.github.javpower.vectorexsolon.mapper;

import io.github.javpower.vectorexcore.VectoRexClient;
import org.noear.solon.Solon;

/**
 * @author cxc
 */
public class VectorRexMapper<T> extends BaseVectoRexMapper<T> {

    @Override
    public VectoRexClient getClient() {
        return Solon.context().getBean(VectoRexClient.class);
    }
}
