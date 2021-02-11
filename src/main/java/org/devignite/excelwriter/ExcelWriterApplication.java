package org.devignite.excelwriter;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ExcelWriterApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExcelWriterApplication.class, args);
    }

    @Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI().info(new Info().title("Excel Writer").version("1.0"));
    }
}