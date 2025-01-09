package io.github.javpower.vectorexserver.req;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class LoginUser {
    private String username;
    private String password;
}
