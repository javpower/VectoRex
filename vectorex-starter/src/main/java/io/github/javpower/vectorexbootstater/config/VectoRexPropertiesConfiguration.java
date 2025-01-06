package io.github.javpower.vectorexbootstater.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author xgc
 **/
@Data
@Component
@ConfigurationProperties(prefix = "vectorex")
public class VectoRexPropertiesConfiguration {
    private boolean enable;
    private String uri;
    private List<String> packages;

}