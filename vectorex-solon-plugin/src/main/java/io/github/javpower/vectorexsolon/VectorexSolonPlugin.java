package io.github.javpower.vectorexsolon;

import io.github.javpower.vectorexsolon.config.VectorexPropertiesConfiguration;
import io.github.javpower.vectorexsolon.service.VectoRexInit;
import org.noear.solon.core.AppContext;
import org.noear.solon.core.Plugin;

/**
 * @author cxc
 */
public class VectorexSolonPlugin implements Plugin {


    public void start(AppContext context) throws Throwable {

         // 插件启动时的初始化逻辑
        context.beanMake(VectorexPropertiesConfiguration.class);

        context.beanMake(VectoRexInit.class);
    }


}
