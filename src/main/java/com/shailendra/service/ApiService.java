package com.shailendra.service;

import com.shailendra.dto.ServiceInfoDTO;
import com.shailendra.model.PingInfo;
import com.shailendra.model.StatusSummary;
import com.shailendra.util.AvailabilityCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * The type Api service.
 */
@Service
public class ApiService {

    private List<ServiceInfoDTO> serviceList = new ArrayList<>();

    @Autowired
    private MSStatusInfoService statusInfoService;

    /**
     * Instantiates a new Api service.
     */
    public ApiService() {
//        serviceList.add(new ServiceInfoDTO("http://shchauha02-mac.local:8080/demo/status"));
    }

    /**
     * Register service.
     *
     * @param serviceInfo the service info
     */
    public void registerService(ServiceInfoDTO serviceInfo) {
        if (Objects.isNull(serviceInfo.getId())) {
            serviceInfo.setId(UUID.randomUUID().toString());
        }
        if (Objects.isNull(serviceInfo.getName())) {
            serviceInfo.setName(serviceInfo.getURL());
        }
        serviceList.add(serviceInfo);
    }

    /**
     * De register service.
     *
     * @param serviceInfo the service info
     */
    public void deRegisterService(ServiceInfoDTO serviceInfo) {
        serviceList.remove(serviceInfo);
    }

    /**
     * Gets all registered services.
     *
     * @return the all registered services
     */
    public List<ServiceInfoDTO> getAllRegisteredServices() {
        return serviceList;
    }


    /**
     * Gets status info.
     *
     * @param serviceInfo the service info
     * @return the status info
     */
    public List<PingInfo> getStatusInfo(ServiceInfoDTO serviceInfo) {
        return statusInfoService.getStatusInfo(serviceInfo);
    }

    /**
     * Gets unavailability info.
     *
     * @param serviceInfo the service info
     * @return the unavailability info
     */
    public StatusSummary getUnavailabilityInfo(ServiceInfoDTO serviceInfo) {
        List<PingInfo> pingInfoList = statusInfoService.getStatusInfo(serviceInfo);

        return AvailabilityCalculator.calculateSummary(pingInfoList);
    }

    /**
     * Reset unavailability info.
     *
     * @param serviceInfo the service info
     */
    public void resetUnavailabilityInfo(ServiceInfoDTO serviceInfo) {
        List<PingInfo> pingInfoList = statusInfoService.getStatusInfo(serviceInfo);
        pingInfoList.clear();
    }
}
