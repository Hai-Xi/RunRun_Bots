package com.test.runrunbots.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;  
import java.util.Date;

/**
 *
 * 使用了系统默认时区：ZoneId.systemDefault()。
 * 如果需要指定别的时区，可以修改方法中的 ZoneId，以满足不同地区的需求。例如：
 * ```java
 * ZoneId specificZoneId = ZoneId.of("UTC");
 * ```
 *
 * | 特性 | Date | LocalDateTime |
 * |------|------|---------------|
 * | **可变性** | 可变（非线程安全） | 不可变（线程安全） |
 * | **时区信息** | 隐式使用系统默认时区 | 不包含时区信息 |
 * | **精度** | 毫秒 | 纳秒 |
 * | **支持的操作** | 有限 | 丰富（加减日期时间、格式化等） |
 * | **线程安全** | 否 | 是 |
 * | **API 易用性** | 较差 | 优秀 |
 *
 *
 * Date 是 Java 1.0 版本的日期时间 API，API 设计不够完善，使用较为复杂。
 * LocalDateTime 是 Java 8 引入的新 API，设计更合理，功能更强大，线程安全。
 * 项目优先考虑使用 LocalDateTime，尤其是对日期时间有较多操作的场景。
 * 兼容旧版本的项目可能需要同时使用两者，可通过工具类做好转换。
 *
 *
 */
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