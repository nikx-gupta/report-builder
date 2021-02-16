package org.devignite.reportBuilder;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReportBuilderConfiguration {

    @Bean
    public MongoReportSource reportSource(){
        return new MongoReportSource();
    }
}
