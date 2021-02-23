package org.devignite.reportBuilder;

import org.devignite.reportBuilder.controller.ReportMetadataController;
import org.devignite.reportBuilder.model.ReportMetadata;
import org.devignite.reportBuilder.repository.ReportManifestRepository;
import org.devignite.reportBuilder.services.ReportMetadataService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.InvalidPropertyException;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReportMetadataControllerTest {

    @Mock
    ReportManifestRepository manifestRepository;

    @InjectMocks
    ReportMetadataService reportMetadataService;

    ReportMetadataController reportController;

    @Before
    public void setup() throws IllegalAccessException {
        MockitoAnnotations.openMocks(this);
        reportController = new ReportMetadataController();
        mockInnerClass(reportController, reportMetadataService);
    }

    @Test
    public void add() {
        Mockito.clearInvocations(manifestRepository);
        ReportMetadata reportMetadata = new ReportMetadata();
        reportMetadata.setTitle("something");
        when(manifestRepository.existsById(anyString())).thenReturn(false);
        when(manifestRepository.insert(any(ReportMetadata.class))).thenReturn(reportMetadata);

        Assert.assertNotNull(reportController.post(reportMetadata));

        try {
            Mockito.clearInvocations(manifestRepository);
            when(manifestRepository.existsById(anyString())).thenReturn(true);
            reportController.post(reportMetadata);
            Assert.fail();
        } catch (InvalidPropertyException ex) {

        }
    }

    @Test
    public void put() {
        Mockito.clearInvocations(manifestRepository);
        ReportMetadata reportMetadata = new ReportMetadata();
        reportMetadata.setTitle("something");

        when(manifestRepository.existsById(anyString())).thenReturn(true);
        when(manifestRepository.save(any())).thenReturn(reportMetadata);
        Assert.assertNotNull(reportController.put(reportMetadata));

        try {
            Mockito.clearInvocations(manifestRepository);
            when(manifestRepository.existsById(anyString())).thenReturn(false);
            reportController.put(reportMetadata);
            Assert.fail();
        } catch (InvalidPropertyException ex) {

        }
    }

    @Test
    public void get() {
        Mockito.clearInvocations(manifestRepository);
        ReportMetadata reportMetadata = new ReportMetadata();
        when(manifestRepository.findById(any())).thenReturn(Optional.of(reportMetadata));
        Assert.assertNotNull(reportController.get("testReport"));
    }

    @Test
    public void delete() {
        Mockito.clearInvocations(manifestRepository);
        ReportMetadata reportMetadata = new ReportMetadata();
        reportMetadata.setTitle("something");

        doNothing().when(manifestRepository).deleteById(any());
        when(manifestRepository.existsById(anyString())).thenReturn(true);
        Assert.assertNotNull(reportController.delete("something"));

        try {
            Mockito.clearInvocations(manifestRepository);
            when(manifestRepository.existsById(anyString())).thenReturn(false);
            reportController.delete("something");
            Assert.fail();
        } catch (IllegalArgumentException ex) {

        }
    }

    public void mockInnerClass(Object obj, Object target) throws IllegalAccessException {
        Field f1 = Arrays.stream(obj.getClass().getDeclaredFields())
                .filter(x -> x.getType().getCanonicalName().equalsIgnoreCase(target.getClass().getName()))
                .findFirst().get();
        f1.setAccessible(true);
        f1.set(obj, target);
    }
}