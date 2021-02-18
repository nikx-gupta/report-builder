package org.devignite.reportBuilder.services;

import org.devignite.reportBuilder.ReportComponentFactory;
import org.devignite.reportBuilder.builders.ExcelReportBuilder;
import org.devignite.reportBuilder.dataProvider.abstractions.IDataRow;
import org.devignite.reportBuilder.dataProvider.abstractions.IDataSet;
import org.devignite.reportBuilder.dataProvider.abstractions.IDataSource;
import org.devignite.reportBuilder.model.ColumnFormat;
import org.devignite.reportBuilder.model.ReportMetadata;
import org.devignite.reportBuilder.repository.ReportManifestRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.matches;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReportServiceTest {

    @Mock
    ReportManifestRepository reportManifestRepository;

    @Mock
    ReportComponentFactory componentFactory;

    @InjectMocks
    ReportService reportService;

    @Test
    public void generateReport() {
        ReportMetadata testReport = createMockReportManifest();
        IDataSource mockSource = createMockSource();

//        Optional mockOptional = mock(Optional.class);
        when(reportManifestRepository.findById(matches("testReport"))).thenReturn(Optional.of(testReport));
        // when(mockOptional.get()).thenReturn(testReport);

        when(componentFactory.createReportBuilder(any())).thenReturn(new ExcelReportBuilder());
        when(componentFactory.createDataProvider(any(), any())).thenReturn(mockSource);

        ReportResponse resp = reportService.generateReport("testReport");
        Assert.assertNotNull(resp);
        Assert.assertNotNull(resp.getContent());
    }

    private IDataSource createMockSource() {
        IDataSource ds = mock(IDataSource.class);
        IDataSet dt = mock(IDataSet.class);
        IDataRow dr = mock(IDataRow.class);

        when(dr.getCellValue(matches("NO_SOURCE_FIELD"))).thenReturn("");
        when(dr.getCellValue(matches("DATE_FIELD"))).thenReturn(new Date(2020, 10, 02, 10, 10));
        when(dr.getCellValue(matches("DATE_FIELD_SIMPLE"))).thenReturn(new Date(2020, 10, 02, 10, 10));

        when(ds.loadData()).thenReturn(dt);
        when(dt.getRowCount()).thenReturn(1);
        when(dt.getRow(any())).thenReturn(dr);
        // when(dr.getColumnSize()).thenReturn(3);

        return ds;
    }

    private ReportMetadata createMockReportManifest() {
        ReportMetadata reportMetadata = new ReportMetadata();
        reportMetadata.setTitle("testReport");
        reportMetadata.setOutputFormat("xlsx");

        List<ColumnFormat> columns = new ArrayList<>();
        ColumnFormat colWithNoSourceField = new ColumnFormat();
        colWithNoSourceField.setSourceFieldName("");
        colWithNoSourceField.setOutputName("NO_SOURCE_FIELD");
        columns.add(colWithNoSourceField);

        ColumnFormat colDateCustom = new ColumnFormat();
        colDateCustom.setSourceFieldName("DATE_FIELD");
        colDateCustom.setOutputName("DATE_FIELD_CUSTOM");
        colDateCustom.setOutputValueFormat("DT:dd/MM/yyyy HH:mm");
        columns.add(colDateCustom);

        ColumnFormat colDate = new ColumnFormat();
        colDate.setSourceFieldName("DATE_FIELD_SIMPLE");
        colDate.setOutputName("DATE_FIELD_SIMPLE");
        columns.add(colDate);

        reportMetadata.setColumns(columns);
        return reportMetadata;
    }
}