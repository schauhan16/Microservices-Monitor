package com.shailendra.service;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.http.Fault;
import com.shailendra.controller.APIController;
import com.shailendra.dto.ServiceInfoDTO;
import com.shailendra.main.MonitoringApplication;
import com.shailendra.model.MicroserviceStatusInfo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.shailendra.constants.MicroserviceStatus.DOWN;
import static com.shailendra.constants.MicroserviceStatus.UP;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * @author Shailendra Chauhan
 */

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = MonitoringApplication.class)
@AutoConfigureWireMock
@SpringBootTest
public class MonitoringServiceIT {

    private static final Logger LOGGER = LoggerFactory.getLogger(MonitoringServiceIT.class);

    private ServiceInfoDTO downService = new ServiceInfoDTO("http://shchauha02-mac.local:8080/demo/status2");
    private ServiceInfoDTO upService = new ServiceInfoDTO("http://shchauha02-mac.local:8080/demo/status");

    @Autowired
    private APIController apiController;

    @Autowired
    private MicroserviceStatusInfo microserviceStatusInfo;

    @Before
    public void setup() {
        WireMock.stubFor(WireMock.get("/demo/status").willReturn(ok()));
        WireMock.stubFor(WireMock.get("/demo/status2").willReturn(aResponse().withFault(Fault.CONNECTION_RESET_BY_PEER)));
    }

    @After
    public void unregisterServices() {
        apiController.deRegisterService(upService);
        apiController.deRegisterService(downService);
    }

    @Test
    public void testForUpService() throws InterruptedException {
        apiController.registerService(upService);

        Thread.sleep(45000);

        assertTrue(apiController.getStatusInfo(upService).size() > 3);
        assertEquals(1, apiController.getAllRegisteredServices().size());
        assertEquals("0 %", apiController.getUnavailabilityInfo(upService).getTotalUnavailability());
        assertEquals("100 %", apiController.getUnavailabilityInfo(upService).getTotalAvailability());
        ServiceInfoDTO returnedService = apiController.getAllRegisteredServices().stream()
                .filter(service -> service.equals(upService))
                .findFirst().get();

        assertEquals(UP, returnedService.getStatus());
    }

    @Test
    public void testForDownService() throws InterruptedException {
        apiController.registerService(downService);
        Thread.sleep(45000);

        assertTrue(apiController.getStatusInfo(downService).size() > 3);
        assertEquals(1, apiController.getAllRegisteredServices().size());
        assertEquals("100 %", apiController.getUnavailabilityInfo(downService).getTotalUnavailability());
        ServiceInfoDTO returnedService = apiController.getAllRegisteredServices().stream()
                .filter(service -> service.equals(downService))
                .findFirst().get();
        assertEquals(DOWN, returnedService.getStatus());
    }

    @Test
    public void testServiceDownWhichCameUp() throws InterruptedException {
        apiController.registerService(downService);

        Thread.sleep(45000);

        assertTrue(apiController.getStatusInfo(downService).size() > 3);
        assertEquals(1, apiController.getAllRegisteredServices().size());
        assertEquals("100 %", apiController.getUnavailabilityInfo(downService).getTotalUnavailability());
        assertEquals("0 %", apiController.getUnavailabilityInfo(downService).getTotalAvailability());
        ServiceInfoDTO returnedService = apiController.getAllRegisteredServices().stream()
                .filter(service -> service.equals(downService))
                .findFirst().get();
        assertEquals(DOWN, returnedService.getStatus());


        //the downService is up and running now. Should have UP in status now
        WireMock.stubFor(WireMock.get("/demo/status2").willReturn(ok()));

        Thread.sleep(45000);

        assertTrue(apiController.getStatusInfo(downService).size() > 5);

        returnedService = apiController.getAllRegisteredServices().stream()
                .filter(service -> service.equals(downService))
                .findFirst().get();

        assertNotEquals("100 %", apiController.getUnavailabilityInfo(downService).getTotalUnavailability());
        assertNotEquals("0 %", apiController.getUnavailabilityInfo(downService).getTotalAvailability());
        assertEquals(UP, returnedService.getStatus());
    }
}
