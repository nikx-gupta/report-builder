package org.devignite.reportBuilder;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ReportBuilderFactory implements ApplicationContextAware {
    private ApplicationContext ctx;

    public IReportBuilder createReportBuilder(ReportManifest manifest) {
        if (manifest.getOutputFormat() == "XLSX")
            return ctx.getBean(ExcelReportBuilder.class);

        return ctx.getBean(ExcelReportBuilder.class);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }
}
