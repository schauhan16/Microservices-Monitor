package com.shailendra.util;

import com.shailendra.model.PingInfo;
import com.shailendra.model.StatusSummary;

import java.util.ArrayList;
import java.util.List;

import static com.shailendra.constants.MicroserviceStatus.DOWN;
import static com.shailendra.constants.MicroserviceStatus.UP;

/**
 * @author Shailendra Chauhan
 * <p>
 * This class is responsible to calculate the Availability/Unavailability info.
 * The algo to get the desired result for all the ping should be implemented in this class.
 */
public class AvailabilityCalculator {

    /**
     * Calculate summary status summary.
     * <p>
     * Method will be calculating the % of availability/unavailability.
     * Implemented basic formula of ((no of availability/unavailability * 100) / total of records).
     * <p>
     * This formula/implementation needs modification if time needs to be consider to calculate the %.
     *
     * @param pingInfoList list of ping info
     * @return the status summary
     */
    public static StatusSummary calculateSummary(List<PingInfo> pingInfoList) {
        //Todo : Throw an exception of insufficient info
        if (pingInfoList.isEmpty()) {
            return new StatusSummary();
        }
        //To avoid any changes in pingInfo during this calculation
        List<PingInfo> tempList = new ArrayList<>(pingInfoList);

        long upCount = tempList.stream()
                .filter(pingInfo -> UP.equals(pingInfo.getStatus()))
                .count();

        long downCount = tempList.stream()
                .filter(pingInfo -> DOWN.equals(pingInfo.getStatus()))
                .count();

        //Time difference between first and last call
//        LocalDateTime startTime = tempList.get(0).getTime();
//        LocalDateTime endTime = tempList.get(tempList.size() - 1).getTime();
//        long totalTime = startTime.until(endTime, ChronoUnit.SECONDS);

        StatusSummary statusSummary = new StatusSummary();
        statusSummary.setTotalAvailability(getPercentage(upCount, tempList.size()));
        statusSummary.setTotalUnavailability(getPercentage(downCount, tempList.size()));
        return statusSummary;
    }

    private static String getPercentage(long dividend, long divisor) {
        long percentage = (dividend * 100) / divisor;
        return String.valueOf(percentage);
    }
}
