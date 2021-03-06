package org.devignite.reportBuilder;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(scanBasePackages = "org.devignite.*")
@EnableMongoRepositories(basePackages = "org.devignite.*")
public class ReportBuilderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReportBuilderApplication.class, args);
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(new Info().title("Excel Writer").version("1.0"));
    }
}
