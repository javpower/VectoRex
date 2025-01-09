package io.github.javpower.vectorexserver.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "vectorex")
public class VectorRex {
    private String username;
    private String password;
    private String secretKey;

}
