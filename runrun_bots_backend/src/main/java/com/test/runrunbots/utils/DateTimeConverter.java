package com.test.runrunbots.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;  
import java.util.Date;  

public class DateTimeConverter {  

    /**  
     * 将 LocalDateTime 转换为 Date  
     *  
     * @param localDateTime 需要转换的 LocalDateTime  
     * @return 转换后的 Date 对象  
     */  
    public static Date toDate(LocalDateTime localDateTime) {  
        if (localDateTime == null) {  
            throw new IllegalArgumentException("LocalDateTime is null, cannot convert to Date.");  
        }  
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());  
    }  

    /**  
     * 将 Date 转换为 LocalDateTime  
     *  
     * @param date 需要转换的 Date  
     * @return 转换后的 LocalDateTime 对象  
     */  
    public static LocalDateTime toLocalDateTime(Date date) {  
        if (date == null) {  
            throw new IllegalArgumentException("Date is null, cannot convert to LocalDateTime.");  
        }  
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();  
    }  
}  