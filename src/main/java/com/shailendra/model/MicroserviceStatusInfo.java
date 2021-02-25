package com.shailendra.model;

import com.shailendra.dto.ServiceInfoDTO;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Shailendra Chauhan
 */
@Component
public class MicroserviceStatusInfo {

    private HashMap<ServiceInfoDTO, List<PingInfo>> statusHashMap = new HashMap<>();

    public Map<ServiceInfoDTO, List<PingInfo>> getStatusHashMap() {
        return statusHashMap;
    }
}
