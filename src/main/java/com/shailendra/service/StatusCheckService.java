package com.shailendra.service;

import com.shailendra.dto.ServiceInfoDTO;
import com.shailendra.model.PingInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;

import static com.shailendra.constants.MicroserviceStatus.DOWN;
import static com.shailendra.constants.MicroserviceStatus.UP;

/**
 * @author Shailendra Chauhan
 * <p>
 * This class is responsible for running health check for all the MS.
 */
@Service
public class StatusCheckService {

    private static final Logger LOG = LoggerFactory.getLogger(StatusCheckService.class);

    @Autowired
    private MSStatusInfoService msStatusInfoService;

    @Autowired
    private ApiService apiService;

    /**
     * This method by default will run in every 10 seconds.
     * Can also be set using property health.check.cron.
     * <p>
     * Method will iterate over the list of registered microservice.
     *
     * @throws IOException
     */
    @Scheduled(cron = "${health.check.cron:0/10 * * * * ?}")
    private void healthCheckScheduler() {
        LOG.info("Checking health status");

        //To-Do: Every health check should run independently(It will require locking)
        //Implement circuit breaker
        apiService.getAllRegisteredServices()
                .stream()
                .forEach(this::checkHealthForService);
    }

    /**
     * For the given MS, this method will make the call.
     * If the response code is 200, the ping will be considered as {@link com.shailendra.constants.MicroserviceStatus#UP}
     * If the response code is anything other than 200, it will set the ping info to {@link com.shailendra.constants.MicroserviceStatus#DOWN}
     * <p>
     * It will call {@link MSStatusInfoService#addStatusInfo} to update the list of statuses.
     *
     * @param serviceInfoDTO
     */
    private void checkHealthForService(ServiceInfoDTO serviceInfoDTO) {
        try {
            URL url = new URL(serviceInfoDTO.getUrl());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            LOG.info("Service {} returned with response code {}", serviceInfoDTO.getUrl(), connection.getResponseCode());
            PingInfo pingInfo = new PingInfo();

            pingInfo.setStatus(200 == connection.getResponseCode() ? UP : DOWN);
            pingInfo.setTime(LocalDateTime.now());

            msStatusInfoService.addStatusInfo(serviceInfoDTO, pingInfo);

        } catch (IOException ex) {
            LOG.info("Exception occurred while getting status info for service {}", serviceInfoDTO.getUrl(), ex);
            PingInfo pingInfo = new PingInfo();
            pingInfo.setStatus(DOWN);
            pingInfo.setTime(LocalDateTime.now());

            msStatusInfoService.addStatusInfo(serviceInfoDTO, pingInfo);
        }
    }
}
