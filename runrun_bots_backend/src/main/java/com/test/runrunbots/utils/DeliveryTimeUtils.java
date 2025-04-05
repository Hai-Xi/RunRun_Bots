package com.test.runrunbots.utils;

import java.time.LocalDateTime;  
import java.time.DayOfWeek;  
import java.time.LocalDate;  
import java.time.LocalTime;  
import java.util.Arrays;  
import java.util.List;  

/**  
 * 配送时间工具类  
 * 用于计算订单的预估到达时间等相关功能
 *
 *
 * 1.工具类设计原则：
 * 所有方法都是静态的 (static)，便于直接调用。
 * 参数设计灵活，可以根据业务需求选择不同版本的方法。
 *
 * 2.算法准确性：
 * 在实际应用中，预估到达时间的计算应基于历史数据、物流经验或实际路线规划。
 * 可能需要对接第三方 API 如高德地图、百度地图等计算准确距离和时间。
 *
 * 3.调用方式：
 * 在业务层（如 OrderService）中调用工具类。
 * 始终使用工具类来保持一致的计算逻辑，避免逻辑散布在不同位置。
 *
 *
 */  
public class DeliveryTimeUtils {  

    // 默认加急配送天数  
    private static final int DEFAULT_EXPRESS_DAYS = 1;  
    
    // 默认标准配送天数  
    private static final int DEFAULT_STANDARD_DAYS = 3;  
    
    // 默认远程地区配送天数  
    private static final int DEFAULT_REMOTE_DAYS = 5;  
    
    // 假日列表（可以从配置或数据库中读取）  
    private static final List<LocalDate> HOLIDAYS = Arrays.asList(  
            LocalDate.of(2025, 1, 1),  // 元旦  
            LocalDate.of(2025, 5, 1)   // 劳动节
            // ...
    );  

    /**  
     * 计算预估到达时间  
     * 基本实现：当前时间 + 默认配送天数  
     *  
     * @return 预估到达时间  
     */  
    public static LocalDateTime calculateEstimatedArrivalTime() {  
        return LocalDateTime.now().plusDays(DEFAULT_STANDARD_DAYS);  
    }  

    /**  
     * 根据配送方式计算预估到达时间  
     *  
     * @param deliveryType 配送方式：EXPRESS（加急）, STANDARD（标准）, ECONOMIC（经济）  
     * @return 预估到达时间  
     */  
    public static LocalDateTime calculateEstimatedArrivalTime(String deliveryType) {  
        LocalDateTime now = LocalDateTime.now();  
        
        switch (deliveryType.toUpperCase()) {  
            case "EXPRESS":  
                return now.plusDays(DEFAULT_EXPRESS_DAYS);  
            case "STANDARD":  
                return now.plusDays(DEFAULT_STANDARD_DAYS);  
            case "ECONOMIC":  
                return now.plusDays(DEFAULT_REMOTE_DAYS);  
            default:  
                return now.plusDays(DEFAULT_STANDARD_DAYS);  
        }  
    }  

    /**  
     * 更复杂的预估到达时间计算  
     * 考虑多种因素：配送距离、配送方式、节假日影响等  
     *  
     * @param distance 配送距离（公里）  
     * @param deliveryType 配送方式  
     * @param isRemoteArea 是否偏远地区  
     * @return 预估到达时间  
     */  
    public static LocalDateTime calculateEstimatedArrivalTime(  
            double distance,   
            String deliveryType,   
            boolean isRemoteArea) {  
            
        LocalDateTime estimatedTime = LocalDateTime.now();  
        
        // 1. 基础配送时间计算（基于距离）  
        int baseDays = calculateBaseDaysByDistance(distance);  
        
        // 2. 考虑配送方式影响  
        if ("EXPRESS".equalsIgnoreCase(deliveryType)) {  
            baseDays = Math.max(1, baseDays - 1); // 加急至少减少1天  
        } else if ("ECONOMIC".equalsIgnoreCase(deliveryType)) {  
            baseDays += 1; // 经济配送增加1天  
        }  
        
        // 3. 考虑偏远地区影响  
        if (isRemoteArea) {  
            baseDays += 2; // 偏远地区额外增加2天  
        }  
        
        // 4. 添加基础天数  
        estimatedTime = estimatedTime.plusDays(baseDays);  
        
        // 5. 避开节假日和周末（如果需要）  
        estimatedTime = skipHolidaysAndWeekends(estimatedTime);  
        
        // 6. 设置配送到达时间（假设都在下午6点前送达）  
        if (estimatedTime.getHour() >= 18) {  
            estimatedTime = estimatedTime.plusDays(1).withHour(18).withMinute(0);  
        } else {  
            estimatedTime = estimatedTime.withHour(18).withMinute(0);  
        }  
        
        return estimatedTime;  
    }  
    
    /**  
     * 根据配送距离计算基础配送天数  
     */  
    private static int calculateBaseDaysByDistance(double distance) {  
        if (distance <= 50) {  
            return 1; // 50公里内当天送达  
        } else if (distance <= 200) {  
            return 2; // 50-200公里2天送达  
        } else if (distance <= 500) {  
            return 3; // 200-500公里3天送达  
        } else {  
            return 4; // 500公里以上4天送达  
        }  
    }  
    
    /**  
     * 跳过周末和节假日  
     */  
    private static LocalDateTime skipHolidaysAndWeekends(LocalDateTime dateTime) {  
        LocalDate date = dateTime.toLocalDate();  
        
        // 如果是周末或节假日，则顺延到下一个工作日  
        while (isWeekend(date) || isHoliday(date)) {  
            date = date.plusDays(1);  
        }  
        
        return LocalDateTime.of(date, dateTime.toLocalTime());  
    }  
    
    /**  
     * 判断是否为周末  
     */  
    private static boolean isWeekend(LocalDate date) {  
        DayOfWeek dayOfWeek = date.getDayOfWeek();  
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;  
    }  
    
    /**  
     * 判断是否为节假日  
     */  
    private static boolean isHoliday(LocalDate date) {  
        return HOLIDAYS.contains(date);  
    }  
}  