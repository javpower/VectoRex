package io.github.javpower.vectorexserver.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.HeaderParameter;
import io.swagger.v3.oas.models.parameters.Parameter;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author gc.x
 * @date 2022/10/27
 **/
@Configuration
public class OpenApiConfig {

    private static final String TOKEN = "token";


    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("vectorex")
                        .description("vectorex")
                        .termsOfService("vectorex")
                        .contact(new Contact().name("").url("").email(""))
                        .version("1.0.0"))
                .externalDocs(new ExternalDocumentation().description("").url(""));
    }

    @Bean
    public GroupedOpenApi systemApi() {
        String[] packagedToMatch = {"io.github.javpower.vectorexserver.controller"};
        Parameter tokenParameter = new HeaderParameter().name(TOKEN).schema(new StringSchema()._default("").name(TOKEN));
        return GroupedOpenApi.builder()
                .group("vectorex doc")
                .addOperationCustomizer((operation, handlerMethod) -> operation.addParametersItem(tokenParameter))
                .pathsToMatch("/**")
                .packagesToScan(packagedToMatch).build();
    }

}
