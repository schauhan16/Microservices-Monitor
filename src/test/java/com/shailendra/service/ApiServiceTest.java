package com.shailendra.service;

import com.shailendra.constants.MicroserviceStatus;
import com.shailendra.dto.ServiceInfoDTO;
import com.shailendra.model.PingInfo;
import com.shailendra.model.StatusSummary;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.shailendra.constants.MicroserviceStatus.DOWN;
import static com.shailendra.constants.MicroserviceStatus.UP;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@RunWith(SpringJUnit4ClassRunner.class)
public class ApiServiceTest {

    @Spy
    @InjectMocks
    private ApiService apiService;

    @Spy
    private MSStatusInfoService statusInfoService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    private List<PingInfo> createPingInfoList() {
        List<PingInfo> pingInfoList = new ArrayList<>();
        pingInfoList.add(new PingInfo(LocalDateTime.now(), UP));
        pingInfoList.add(new PingInfo(LocalDateTime.now(), UP));
        pingInfoList.add(new PingInfo(LocalDateTime.now(), DOWN));
        pingInfoList.add(new PingInfo(LocalDateTime.now(), UP));

        return pingInfoList;
    }

    @Test
    public void registerService() {
        apiService.registerService(new ServiceInfoDTO("demoURl"));
        assertEquals(1, apiService.getAllRegisteredServices().size());
        assertNotNull(apiService.getAllRegisteredServices().get(0).getId());
        assertEquals(DOWN, apiService.getAllRegisteredServices().get(0).getStatus());
    }

    @Test
    public void deRegisterService() {
        apiService.registerService(new ServiceInfoDTO("demoURl"));
        assertEquals(1, apiService.getAllRegisteredServices().size());
        assertNotNull(apiService.getAllRegisteredServices().get(0).getId());

        apiService.deRegisterService(new ServiceInfoDTO("demoURl"));
        assertEquals(0, apiService.getAllRegisteredServices().size());
    }

    @Test
    public void getAllRegisteredServices() {
        apiService.registerService(new ServiceInfoDTO("demoURl"));
        assertEquals(1, apiService.getAllRegisteredServices().size());
        assertNotNull(apiService.getAllRegisteredServices().get(0).getId());

        apiService.registerService(new ServiceInfoDTO("demoURl2"));
        assertEquals(2, apiService.getAllRegisteredServices().size());
    }

    @Test
    public void getUnavailabilityInfo() {
        ServiceInfoDTO serviceInfo = new ServiceInfoDTO("demoURl");
        doReturn(createPingInfoList())
                .when(statusInfoService)
                .getStatusInfo(serviceInfo);

        StatusSummary statusSummary = apiService.getUnavailabilityInfo(new ServiceInfoDTO("demoURl"));

        assertNotNull(statusSummary);
        assertEquals("25 %", statusSummary.getTotalUnavailability());
        assertEquals("75 %", statusSummary.getTotalAvailability());
    }

    @Test
    public void resetUnavailabilityInfo() {
        //ToDo
    }
}
