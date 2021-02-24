package com.shailendra.controller;

import com.shailendra.dto.ServiceInfoDTO;
import com.shailendra.model.PingInfo;
import com.shailendra.model.StatusSummary;
import com.shailendra.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Shailendra Chauhan
 * <p>
 * API controller for montior specific APIs
 */

//@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/monitor")
public class APIController {

    @Autowired
    private ApiService apiService;

    /**
     * Register new micro-service.
     *
     * @param serviceInfo Service Info object
     */
    @PostMapping("/register")
    public void registerService(@RequestBody ServiceInfoDTO serviceInfo) {
        apiService.registerService(serviceInfo);
    }

    /**
     * De-register micro-service.
     *
     * @param serviceInfo Service info object
     */
    @PostMapping("/deregister")
    public void deRegisterService(@RequestBody ServiceInfoDTO serviceInfo) {
        apiService.deRegisterService(serviceInfo);
    }

    /**
     * Gets all registered micro-services.
     *
     * @return List of registered services
     */
    @GetMapping("/all")
    public List<ServiceInfoDTO> getAllRegisteredServices() {
        return apiService.getAllRegisteredServices();
    }


    /**
     * List of PingInfo object having status and time of each ping made by schedular
     *
     * @param serviceInfo Service Info object
     * @return List of Ping Info
     */
    @PostMapping("/statusInfo")
    public List<PingInfo> getStatusInfo(@RequestBody ServiceInfoDTO serviceInfo) {
        return apiService.getStatusInfo(serviceInfo);
    }

    /**
     * Summary of any micro-service availability. Return in percentage
     *
     * @param serviceInfo service info object
     * @return Status summary object
     */
    @PostMapping("/statusInfo/summary")
    public StatusSummary getUnavailabilityInfo(@RequestBody ServiceInfoDTO serviceInfo) {
        return apiService.getUnavailabilityInfo(serviceInfo);
    }

    /**
     * Reset unavailability info.
     *
     * @param serviceInfo service info
     */
    @PostMapping("/statusInfo/reset")
    public void resetUnavailabilityInfo(@RequestBody ServiceInfoDTO serviceInfo) {
        apiService.resetUnavailabilityInfo(serviceInfo);
    }
}
