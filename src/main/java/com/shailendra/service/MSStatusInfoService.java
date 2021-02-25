package com.shailendra.service;

import com.shailendra.dto.ServiceInfoDTO;
import com.shailendra.model.MicroserviceStatusInfo;
import com.shailendra.model.PingInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Shailendra Chauhan
 * <p>
 * This class is responsible for managing status info for all the registered MS.
 */
@Service
public class MSStatusInfoService {

    @Autowired
    private MicroserviceStatusInfo statusInfo;

    /**
     * Add last ping info to the list.
     *
     * @param serviceInfo service info
     * @param pingInfo    ping info
     */
    public void addStatusInfo(ServiceInfoDTO serviceInfo, PingInfo pingInfo) {
        List<PingInfo> pingInfoList = getPingInfoList(serviceInfo);
        serviceInfo.setStatus(pingInfo.getStatus());
        pingInfoList.add(pingInfo);
    }


    private List<PingInfo> getPingInfoList(ServiceInfoDTO serviceInfoDTO) {
        List<PingInfo> pingInfoList = statusInfo.getStatusHashMap().get(serviceInfoDTO);
        if (Objects.nonNull(pingInfoList) && !pingInfoList.isEmpty()) {
            return pingInfoList;
        }

        statusInfo.getStatusHashMap().put(serviceInfoDTO, new ArrayList<>());
        return statusInfo.getStatusHashMap().get(serviceInfoDTO);
    }

    /**
     * Gets status info list.
     *
     * @param serviceInfo service info
     * @return List of all the status info
     */
    public List<PingInfo> getStatusInfo(ServiceInfoDTO serviceInfo) {
        return getPingInfoList(serviceInfo);
    }

    public void removeStatus(ServiceInfoDTO serviceInfo){
        statusInfo.getStatusHashMap().remove(serviceInfo);
    }
}
