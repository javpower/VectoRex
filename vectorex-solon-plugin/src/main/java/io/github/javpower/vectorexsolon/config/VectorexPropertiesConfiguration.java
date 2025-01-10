package io.github.javpower.vectorexsolon.config;

import lombok.Data;
import org.noear.solon.annotation.Configuration;
import org.noear.solon.annotation.Inject;

import java.util.List;

/**
 * @author cxc
 */
@Data
@Inject("${vectorex}")
@Configuration
public class VectorexPropertiesConfiguration {

    private boolean enable;

    private String uri;
    private List<String> packages;
}
