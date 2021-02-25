package com.shailendra.dto;

import com.shailendra.constants.MicroserviceStatus;
import org.springframework.lang.NonNull;

import java.util.Objects;
import java.util.UUID;

import static com.shailendra.constants.MicroserviceStatus.DOWN;

/**
 * @author Shailendra Chauhan
 * <p>
 * Holds the url, Name and given/auto-generated id for any registered micro-service.
 */
public class ServiceInfoDTO {

    private String id;

    private String url;

    private String name;

    private MicroserviceStatus status;

    public ServiceInfoDTO() {
    }

    public ServiceInfoDTO(@NonNull String url) {
        this.id = UUID.randomUUID().toString();
        this.url = url;
        this.status = DOWN;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ServiceInfoDTO)) {
            return false;
        }
        ServiceInfoDTO serviceInfo = (ServiceInfoDTO) obj;

        boolean isIdEquals = Objects.nonNull(this.id) && this.id.equals(serviceInfo.id);
        boolean isurlEquals = Objects.nonNull(this.url) && this.url.equals(serviceInfo.url);

        return isIdEquals || isurlEquals;
    }

    @Override
    public int hashCode() {
        return url.hashCode();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MicroserviceStatus getStatus() {
        return status;
    }

    public void setStatus(MicroserviceStatus status) {
        this.status = status;
    }
}
