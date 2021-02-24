package com.shailendra.model;

import com.shailendra.dto.ServiceInfoDTO;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

/**
 * @author Shailendra Chauhan
 */
@Component
public class MicroserviceStatusInfo {

    private HashMap<ServiceInfoDTO, List<PingInfo>> statusHashMap = new HashMap<>();

    public HashMap<ServiceInfoDTO, List<PingInfo>> getStatusHashMap() {
        return statusHashMap;
    }
}
