package org.devignite.reportBuilder;

import lombok.SneakyThrows;
import org.devignite.reportBuilder.builders.abstractions.IReportBuilder;
import org.devignite.reportBuilder.builders.excel.ExcelUsingRawXmlBuilder;
import org.devignite.reportBuilder.dataProvider.DatasourceType;
import org.devignite.reportBuilder.dataProvider.abstractions.DataProviderSettings;
import org.devignite.reportBuilder.dataProvider.abstractions.IDataSource;
import org.devignite.reportBuilder.dataProvider.mongodb.MongoDataSource;
import org.devignite.reportBuilder.model.ReportMetadata;
import org.springframework.beans.BeansException;
import org.springframework.beans.InvalidPropertyException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ReportComponentFactory implements ApplicationContextAware {
    private ApplicationContext ctx;

    public IReportBuilder createReportBuilder(ReportMetadata manifest) {
        if (manifest.getOutputFormat().equalsIgnoreCase("xlsx"))
//            return ctx.getAutowireCapableBeanFactory().createBean(ExcelReportBuilder.class);
            return ctx.getAutowireCapableBeanFactory().createBean(ExcelUsingRawXmlBuilder.class);

        throw new InvalidPropertyException(ReportMetadata.class, "outputFormat", "Report format not supported");
    }

    @SneakyThrows
    public IDataSource createDataProvider(DatasourceType datasourceType, DataProviderSettings settings) {
        switch (datasourceType) {
            case MONGO:
                IDataSource dataSource = ctx.getAutowireCapableBeanFactory().createBean(MongoDataSource.class);
                dataSource.initSettings(settings);
                return dataSource;
            default:
                throw new UnsupportedOperationException("Unsupported DataSource");
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }
}
