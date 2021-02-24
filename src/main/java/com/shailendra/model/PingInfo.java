package com.shailendra.model;

import com.shailendra.constants.MicroserviceStatus;

import java.time.LocalDateTime;

/**
 * @author Shailendra Chauhan
 */
public class PingInfo {

    private LocalDateTime time;
    private MicroserviceStatus status;

    public PingInfo() {
    }

    public PingInfo(LocalDateTime time, MicroserviceStatus status) {
        this.time = time;
        this.status = status;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public MicroserviceStatus getStatus() {
        return status;
    }

    public void setStatus(MicroserviceStatus status) {
        this.status = status;
    }
}
