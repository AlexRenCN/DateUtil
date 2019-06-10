package com.alex.dateutil;

import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 *
 * JDK1.8日期工具类
 *
 * 注意：
 *
 * 1.如果使用LocalDate类型去操作时分秒，则会出现异常
 *
 * 2.或者使用LocalTime类型去操作日期，则会出现异常
 *
 * 3.LocalTime转化为具有时分秒的时间格式时，会自动补全为格林威治时间的时间（00：00：00）
 *
 * 4.LocalTime转化为具有年月日的时间格式时，会自动补全为格林威治时间的日期（1970：01：01）
 *
 * 5.如果准备获取两个日期间的微秒差/纳秒差时，请考虑到会出现long类型溢出
 *
 * 6.取日差的时候是根据日数进行判断，即2000-01-01 23:59:59 和2000-01-02 00:00:00虽然差了1秒但是也差了1日
 *
 * @author zheng.ren01
 */
public class DateUtil {

    private static String PATTERN_YYYY_MM_DD_HH_MM_SS_SSS = "yyyy-MM-dd HH:mm:ss.SSS";

    private static String PATTERN_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    private static String PATTERN_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    private static String PATTERN_YYYYMMDD = "yyyyMMdd";

    private static String PATTERN_YYYY_MM_DD = "yyyy-MM-dd";

    private static String PATTERN_HHMMDD = "HHmmss";

    private static String PATTERN_HH_MM_DD = "HH:mm:ss";

    private static ZoneId ZONE = ZoneId.of("Asia/Shanghai");

    public static DateTimeFormatter YYYY_MM_DD_HH_MM_SS_SSS = DateTimeFormatter.ofPattern(PATTERN_YYYY_MM_DD_HH_MM_SS_SSS).withZone(ZONE);

    public static DateTimeFormatter YYYY_MM_DD_HH_MM_SS = DateTimeFormatter.ofPattern(PATTERN_YYYY_MM_DD_HH_MM_SS).withZone(ZONE);

    public static DateTimeFormatter YYYYMMDDHHMMSS = DateTimeFormatter.ofPattern(PATTERN_YYYYMMDDHHMMSS).withZone(ZONE);

    public static DateTimeFormatter YYYYMMDD = DateTimeFormatter.ofPattern(PATTERN_YYYYMMDD).withZone(ZONE);

    public static DateTimeFormatter YYYY_MM_DD = DateTimeFormatter.ofPattern(PATTERN_YYYY_MM_DD).withZone(ZONE);

    public static DateTimeFormatter HHMMDD = DateTimeFormatter.ofPattern(PATTERN_HHMMDD).withZone(ZONE);

    public static DateTimeFormatter HH_MM_DD = DateTimeFormatter.ofPattern(PATTERN_HH_MM_DD).withZone(ZONE);

    public static final LocalDate GREENWICH_MEAN_TIME=LocalDate.of(1970,1,1);
    /**
     * Instant 转 java.util.Date
     *
     * @param instant
     * @return
     */
    public static Date instantToDateConverter(Instant instant) {
        Optional.ofNullable(instant).orElseThrow(() -> new RuntimeException("instant can not be null"));
        return Date.from(instant);
    }
    /**
     * LocalDateTime 转 java.util.Date
     *
     * @param localDateTime
     * @return
     */
    public static Date localDateTimeToDateConverter(LocalDateTime localDateTime) {
        Optional.ofNullable(localDateTime).orElseThrow(() -> new RuntimeException("localDateTime can not be null"));
        Instant instant = localDateTime.atZone(ZONE).toInstant();
        return instantToDateConverter(instant);
    }

    /**
     * LocalDate 转 java.util.Date
     *
     * @param localDate
     * @return
     */
    public static Date localDateToDateConverter(LocalDate localDate) {
        Optional.ofNullable(localDate).orElseThrow(() -> new RuntimeException("localDate can not be null"));
        Instant instant = localDate.atStartOfDay(ZONE).toInstant();
        return instantToDateConverter(instant);
    }


    /**
     * LocalDate 转 LocalDateTime
     *
     * @param localDate
     * @return
     */
    public static LocalDateTime localDateToLocalDateTimeConverter(LocalDate localDate) {
        Optional.ofNullable(localDate).orElseThrow(() -> new RuntimeException("localDate can not be null"));
        return localDate.atStartOfDay();
    }

    /**
     * LocalTime 转 LocalDateTime
     *
     * @param localTime
     * @return
     */
    public static LocalDateTime localTimeToLocalDateTimeConverter(LocalTime localTime) {
        Optional.ofNullable(localTime).orElseThrow(() -> new RuntimeException("localTime can not be null"));
        return localTime.atDate(GREENWICH_MEAN_TIME);
    }

    /**
     * java.util.Date 转 Instant
     *
     * @param date
     * @return
     */
    public static Instant dateToInstantConverter(Date date) {
        Optional.ofNullable(date).orElseThrow(() -> new RuntimeException("date can not be null"));
        return date.toInstant();
    }

    /**
     * java.util.Date 转 LocalDateTime
     *
     * @param date
     * @return
     */
    public static LocalDateTime dateToLocalDateTimeConverter(Date date) {
        Optional.ofNullable(date).orElseThrow(() -> new RuntimeException("date can not be null"));
        return LocalDateTime.ofInstant(dateToInstantConverter(date), ZONE);
    }

    /**
     * java.util.Date 转 LocalDate
     *
     * @param date
     * @return
     */
    public static LocalDate dateToLocalDateConverter(Date date) {
        Optional.ofNullable(date).orElseThrow(() -> new RuntimeException("date can not be null"));
        return dateToLocalDateTimeConverter(date).toLocalDate();
    }

    /**
     * java.util.Date 转 LocalTime
     *
     * @param date
     * @return
     */
    public static LocalTime dateToLocalTimeConverter(Date date) {
        Optional.ofNullable(date).orElseThrow(() -> new RuntimeException("date can not be null"));
        return dateToLocalDateTimeConverter(date).toLocalTime();
    }

    /**
     * LocalDateTime 转 Instant
     *
     * @param localDateTime
     * @return
     */
    public static Instant localDateTimeToInstantConverter(LocalDateTime localDateTime) {
        Optional.ofNullable(localDateTime).orElseThrow(() -> new RuntimeException("localDateTime can not be null"));
        return localDateTime.atZone(ZONE).toInstant();
    }

    /**
     * Instant 转 LocalDateTime
     *
     * @param instant
     * @return
     */
    public static LocalDateTime instantToLocalDateTimeConverter(Instant instant) {
        Optional.ofNullable(instant).orElseThrow(() -> new RuntimeException("instant can not be null"));
        return instant.atZone(ZONE).toLocalDateTime();
    }

    /**
     * LocalDateTime 转 LocalDate
     *
     * @param localDateTime
     * @return
     */
    public static LocalDate localDateTimeToLocalDateConverter(LocalDateTime localDateTime) {
        Optional.ofNullable(localDateTime).orElseThrow(() -> new RuntimeException("localDateTime can not be null"));
        return localDateTime.atZone(ZONE).toLocalDate();
    }

    /**
     * LocalDateTime 转 LocalTime
     *
     * @param localDateTime
     * @return
     */
    public static LocalTime localDateTimeToLocalTimeConverter(LocalDateTime localDateTime) {
        Optional.ofNullable(localDateTime).orElseThrow(() -> new RuntimeException("localDateTime can not be null"));
        return localDateTime.atZone(ZONE).toLocalTime();
    }

    /**
     * java.util.Date格式化
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String format(Date date, DateTimeFormatter pattern) {
        Optional.ofNullable(date).orElseThrow(() -> new RuntimeException("date can not be null"));
        Optional.ofNullable(pattern).orElseThrow(() -> new RuntimeException("pattern can not be null"));
        return format(dateToLocalDateTimeConverter(date), pattern);
    }

    /**
     * Instant格式化
     *
     * @param instant
     * @param pattern
     * @return
     */
    public static String format(Instant instant, DateTimeFormatter pattern) {
        Optional.ofNullable(instant).orElseThrow(() -> new RuntimeException("instant can not be null"));
        Optional.ofNullable(pattern).orElseThrow(() -> new RuntimeException("pattern can not be null"));
        return pattern.format(instant);
    }

    /**
     * LocalDateTime格式化
     *
     * @param localDateTime
     * @param pattern
     * @return
     */
    public static String format(LocalDateTime localDateTime, DateTimeFormatter pattern) {
        Optional.ofNullable(localDateTime).orElseThrow(() -> new RuntimeException("localDateTime can not be null"));
        Optional.ofNullable(pattern).orElseThrow(() -> new RuntimeException("pattern can not be null"));
        return pattern.format(localDateTime);
    }

    /**
     * LocalDate格式化
     *
     * @param localDate
     * @param pattern
     * @return
     */
    public static String format(LocalDate localDate, DateTimeFormatter pattern) {
        Optional.ofNullable(localDate).orElseThrow(() -> new RuntimeException("localDate can not be null"));
        Optional.ofNullable(pattern).orElseThrow(() -> new RuntimeException("pattern can not be null"));
        return format(localDateToLocalDateTimeConverter(localDate),pattern);
    }

    /**
     * LocalTime格式化
     *
     * @param localTime
     * @param pattern
     * @return
     */
    public static String format(LocalTime localTime, DateTimeFormatter pattern) {
        Optional.ofNullable(localTime).orElseThrow(() -> new RuntimeException("localTime can not be null"));
        Optional.ofNullable(pattern).orElseThrow(() -> new RuntimeException("pattern can not be null"));
        return format(localTimeToLocalDateTimeConverter(localTime),pattern);
    }

    /**
     * 反格式化为java.util.Date
     *
     * @param text
     * @param pattern
     * @return
     */
    public static Date formatDate(String text, DateTimeFormatter pattern) {
        if (StringUtils.isEmpty(text)) {
            throw new RuntimeException("can not format String :" + text);
        }

        Optional.ofNullable(pattern).orElseThrow(() -> new RuntimeException("pattern can not be null"));
        return localDateTimeToDateConverter(formatLocalDateTime(text, pattern));
    }

    /**
     * 反格式化为Instant
     *
     * @param text
     * @param pattern
     * @return
     */
    public static Instant formatInstant(String text, DateTimeFormatter pattern) {
        if (StringUtils.isEmpty(text)) {
            throw new RuntimeException("can not format String :" + text);
        }
        Optional.ofNullable(pattern).orElseThrow(() -> new RuntimeException("pattern can not be null"));
        return localDateTimeToInstantConverter(formatLocalDateTime(text, pattern));
    }

    /**
     * 反格式化LocalDateTime
     *
     * @param text
     * @param pattern
     * @return
     */
    public static LocalDateTime formatLocalDateTime(String text, DateTimeFormatter pattern) {
        if (StringUtils.isEmpty(text)) {
            throw new RuntimeException("can not format String :" + text);
        }
        Optional.ofNullable(pattern).orElseThrow(() -> new RuntimeException("pattern can not be null"));
        TemporalAccessor temporalAccessor= pattern.parse(text);
        try {
            Field date=temporalAccessor.getClass().getDeclaredField("date");
            date.setAccessible(true);
            if(!Optional.ofNullable(date.get(temporalAccessor)).isPresent()){
                date.set(temporalAccessor, GREENWICH_MEAN_TIME);
            }
            Field time=temporalAccessor.getClass().getDeclaredField("time");
            time.setAccessible(true);
            if(!Optional.ofNullable(time.get(temporalAccessor)).isPresent()){
                time.set(temporalAccessor, LocalTime.MIN);
            }
            return LocalDateTime.from(temporalAccessor);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("format error :"+e.getMessage());
        }
    }


    /**
     * 反格式化LocalDate
     *
     * @param text
     * @param pattern
     * @return
     */
    public static LocalDate formatLocalDate(String text, DateTimeFormatter pattern) {
        if (StringUtils.isEmpty(text)) {
            throw new RuntimeException("can not format String :" + text);
        }
        Optional.ofNullable(pattern).orElseThrow(() -> new RuntimeException("pattern can not be null"));
        try {
            return LocalDate.parse(text, pattern);
        }catch (DateTimeParseException exception){
            throw new RuntimeException("why pattern does not have yyyymmdd? ["+ pattern+"]");
        }
    }

    /**
     * 反格式化LocalTime
     *
     * @param text
     * @param pattern
     * @return
     */
    public static LocalTime formatLocalTime(String text, DateTimeFormatter pattern) {
        if (StringUtils.isEmpty(text)) {
            throw new RuntimeException("can not format String :" + text);
        }
        Optional.ofNullable(pattern).orElseThrow(() -> new RuntimeException("pattern can not be null"));
        try {
            return LocalTime.parse(text, pattern);
        }catch (DateTimeParseException exception){
            throw new RuntimeException("why pattern does not have hhmmdd? ["+ pattern+"]");
        }
    }

    /**
     * 从java.util.Date获得年份
     *
     * @param date
     * @return
     */
    public static int getYear(Date date) {
        Optional.ofNullable(date).orElseThrow(() -> new RuntimeException("date can not be null"));
        return getYear(dateToLocalDateTimeConverter(date));
    }

    /**
     * 从Instant获得年份
     *
     * @param instant
     * @return
     */
    public static int getYear(Instant instant) {
        Optional.ofNullable(instant).orElseThrow(() -> new RuntimeException("instant can not be null"));
        return getYear(instantToLocalDateTimeConverter(instant));
    }

    /**
     * 从LocalDateTime获得年份
     *
     * @param localDateTime
     * @return
     */
    public static int getYear(LocalDateTime localDateTime) {
        Optional.ofNullable(localDateTime).orElseThrow(() -> new RuntimeException("localDateTime can not be null"));
        return localDateTime.getYear();
    }

    /**
     * 从LocalDate获得年份
     *
     * @param localDate
     * @return
     */
    public static int getYear(LocalDate localDate) {
        Optional.ofNullable(localDate).orElseThrow(() -> new RuntimeException("localDate can not be null"));
        return localDate.getYear();
    }

    /**
     * 从java.util.Date获得月份
     *
     * @param date
     * @return
     */
    public static int getMonth(Date date) {
        Optional.ofNullable(date).orElseThrow(() -> new RuntimeException("date can not be null"));
        return getMonth(dateToLocalDateTimeConverter(date));
    }

    /**
     * 从Instant获得月份
     *
     * @param instant
     * @return
     */
    public static int getMonth(Instant instant) {
        Optional.ofNullable(instant).orElseThrow(() -> new RuntimeException("instant can not be null"));
        return getMonth(instantToLocalDateTimeConverter(instant));
    }

    /**
     * 从LocalDateTime获得月份
     *
     * @param localDateTime
     * @return
     */
    public static int getMonth(LocalDateTime localDateTime) {
        Optional.ofNullable(localDateTime).orElseThrow(() -> new RuntimeException("localDateTime can not be null"));
        return localDateTime.getMonth().getValue();
    }

    /**
     * 从LocalDate获得月份
     *
     * @param localDate
     * @return
     */
    public static int getMonth(LocalDate localDate) {
        Optional.ofNullable(localDate).orElseThrow(() -> new RuntimeException("localDate can not be null"));
        return localDate.getMonth().getValue();
    }

    /**
     * 从java.util.Date获得是当月几号
     *
     * @param date
     * @return
     */
    public static int getDayOfMonth(Date date) {
        Optional.ofNullable(date).orElseThrow(() -> new RuntimeException("date can not be null"));
        return getDayOfMonth(dateToLocalDateTimeConverter(date));
    }

    /**
     * 从Instant获得是当月几号
     *
     * @param instant
     * @return
     */
    public static int getDayOfMonth(Instant instant) {
        Optional.ofNullable(instant).orElseThrow(() -> new RuntimeException("instant can not be null"));
        return getDayOfMonth(instantToLocalDateTimeConverter(instant));
    }

    /**
     * 从LocalDateTime获得是当月几号
     *
     * @param localDateTime
     * @return
     */
    public static int getDayOfMonth(LocalDateTime localDateTime) {
        Optional.ofNullable(localDateTime).orElseThrow(() -> new RuntimeException("localDateTime can not be null"));
        return localDateTime.getDayOfMonth();
    }

    /**
     * 从LocalDate获得是当月几号
     *
     * @param localDate
     * @return
     */
    public static int getDayOfMonth(LocalDate localDate) {
        Optional.ofNullable(localDate).orElseThrow(() -> new RuntimeException("localDate can not be null"));
        return localDate.getDayOfMonth();
    }

    /**
     * 从java.util.Date获取小时
     *
     * @param date
     * @return
     */
    public static int getHour(Date date) {
        Optional.ofNullable(date).orElseThrow(() -> new RuntimeException("date can not be null"));
        return getHour(dateToLocalDateTimeConverter(date));
    }

    /**
     * 从Instant获取小时
     *
     * @param instant
     * @return
     */
    public static int getHour(Instant instant) {
        Optional.ofNullable(instant).orElseThrow(() -> new RuntimeException("instant can not be null"));
        return getHour(instantToLocalDateTimeConverter(instant));
    }

    /**
     * 从LocalDateTime获取小时
     *
     * @param localDateTime
     * @return
     */
    public static int getHour(LocalDateTime localDateTime) {
        Optional.ofNullable(localDateTime).orElseThrow(() -> new RuntimeException("localDateTime can not be null"));
        return localDateTime.getHour();
    }

    /**
     * 从LocalTime获取小时
     *
     * @param localTime
     * @return
     */
    public static int getHour(LocalTime localTime) {
        Optional.ofNullable(localTime).orElseThrow(() -> new RuntimeException("localTime can not be null"));
        return localTime.getHour();
    }

    /**
     * 从java.util.Date获取分钟
     *
     * @param date
     * @return
     */
    public static int getMinute(Date date) {
        Optional.ofNullable(date).orElseThrow(() -> new RuntimeException("date can not be null"));
        return getMinute(dateToLocalDateTimeConverter(date));
    }

    /**
     * 从Instant获取分钟
     *
     * @param instant
     * @return
     */
    public static int getMinute(Instant instant) {
        Optional.ofNullable(instant).orElseThrow(() -> new RuntimeException("instant can not be null"));
        return getMinute(instantToLocalDateTimeConverter(instant));
    }

    /**
     * 从LocalDateTime获取分钟
     *
     * @param localDateTime
     * @return
     */
    public static int getMinute(LocalDateTime localDateTime) {
        Optional.ofNullable(localDateTime).orElseThrow(() -> new RuntimeException("localDateTime can not be null"));
        return localDateTime.getMinute();
    }

    /**
     * 从LocalTime获取分钟
     *
     * @param localTime
     * @return
     */
    public static int getMinute(LocalTime localTime) {
        Optional.ofNullable(localTime).orElseThrow(() -> new RuntimeException("localTime can not be null"));
        return localTime.getMinute();
    }

    /**
     * 从java.util.Date获取秒数
     *
     * @param date
     * @return
     */
    public static int getSecond(Date date) {
        Optional.ofNullable(date).orElseThrow(() -> new RuntimeException("date can not be null"));
        return getMinute(dateToLocalDateTimeConverter(date));
    }

    /**
     * 从Instant获取秒数
     *
     * @param instant
     * @return
     */
    public static int getSecond(Instant instant) {
        Optional.ofNullable(instant).orElseThrow(() -> new RuntimeException("instant can not be null"));
        return getSecond(instantToLocalDateTimeConverter(instant));
    }

    /**
     * 从LocalDateTime获取秒数
     *
     * @param localDateTime
     * @return
     */
    public static int getSecond(LocalDateTime localDateTime) {
        Optional.ofNullable(localDateTime).orElseThrow(() -> new RuntimeException("localDateTime can not be null"));
        return localDateTime.getSecond();
    }

    /**
     * 从LocalTime获取秒数
     *
     * @param localTime
     * @return
     */
    public static int getSecond(LocalTime localTime) {
        Optional.ofNullable(localTime).orElseThrow(() -> new RuntimeException("localTime can not be null"));
        return localTime.getSecond();
    }

    /**
     * 获取两个日期的差值
     *
     * @param source
     * @param target
     * @param timeUnit
     * @return
     */
    public static long between(Date source, Date target, TimeUnit timeUnit) {
        Optional.ofNullable(source).orElseThrow(() -> new RuntimeException("source can not be null"));
        Optional.ofNullable(target).orElseThrow(() -> new RuntimeException("target can not be null"));
        Optional.ofNullable(timeUnit).orElseThrow(() -> new RuntimeException("timeUnit can not be null"));
        return doBetween(dateToLocalDateTimeConverter(source), dateToLocalDateTimeConverter(target), timeUnit);
    }

    /**
     * 获取两个日期的差值
     *
     * @param source
     * @param target
     * @param timeUnit
     * @return
     */
    public static long between(Instant source, Instant target, TimeUnit timeUnit) {
        Optional.ofNullable(source).orElseThrow(() -> new RuntimeException("source can not be null"));
        Optional.ofNullable(target).orElseThrow(() -> new RuntimeException("target can not be null"));
        Optional.ofNullable(timeUnit).orElseThrow(() -> new RuntimeException("timeUnit can not be null"));
        return doBetween(instantToLocalDateTimeConverter(source), instantToLocalDateTimeConverter(target), timeUnit);
    }

    /**
     * 获取两个日期的差值
     *
     * @param source
     * @param target
     * @param timeUnit
     * @return
     */
    public static long between(LocalDateTime source, LocalDateTime target, TimeUnit timeUnit) {
        Optional.ofNullable(source).orElseThrow(() -> new RuntimeException("source can not be null"));
        Optional.ofNullable(target).orElseThrow(() -> new RuntimeException("target can not be null"));
        Optional.ofNullable(timeUnit).orElseThrow(() -> new RuntimeException("timeUnit can not be null"));
        return doBetween(source, target, timeUnit);
    }

    /**
     * 获取两个日期的差值
     *
     * @param source
     * @param target
     * @param timeUnit
     * @return
     */
    public static long between(LocalDate source, LocalDate target, TimeUnit timeUnit) {
        Optional.ofNullable(source).orElseThrow(() -> new RuntimeException("source can not be null"));
        Optional.ofNullable(target).orElseThrow(() -> new RuntimeException("target can not be null"));
        Optional.ofNullable(timeUnit).orElseThrow(() -> new RuntimeException("timeUnit can not be null"));
        if(TimeUnit.DAYS!=timeUnit){
            throw new RuntimeException("LocalDate can not get between with :"+timeUnit.name());
        }
        return doBetween(localDateToLocalDateTimeConverter(source), localDateToLocalDateTimeConverter(target), timeUnit);
    }

    /**
     * 获取两个日期的差值
     *
     * @param source
     * @param target
     * @param timeUnit
     * @return
     */
    public static long between(LocalTime source, LocalTime target, TimeUnit timeUnit) {
        Optional.ofNullable(source).orElseThrow(() -> new RuntimeException("source can not be null"));
        Optional.ofNullable(target).orElseThrow(() -> new RuntimeException("target can not be null"));
        Optional.ofNullable(timeUnit).orElseThrow(() -> new RuntimeException("timeUnit can not be null"));
        if(TimeUnit.DAYS==timeUnit){
            throw new RuntimeException("LocalDate can not get between with :"+timeUnit.name());
        }
        return doBetween(localTimeToLocalDateTimeConverter(source), localTimeToLocalDateTimeConverter(target), timeUnit);
    }

    /**
     * 获取日期距离当前的差值
     *
     * @param source
     * @param timeUnit
     * @return
     */
    public static long betweenNow(Date source, TimeUnit timeUnit) {
        Optional.ofNullable(source).orElseThrow(() -> new RuntimeException("source can not be null"));
        Optional.ofNullable(timeUnit).orElseThrow(() -> new RuntimeException("timeUnit can not be null"));
        return between(dateToLocalDateTimeConverter(source), LocalDateTime.now(), timeUnit);
    }

    /**
     * 获取日期距离当前的差值
     *
     * @param source
     * @param timeUnit
     * @return
     */
    public static long betweenNow(Instant source, TimeUnit timeUnit) {
        Optional.ofNullable(source).orElseThrow(() -> new RuntimeException("source can not be null"));
        Optional.ofNullable(timeUnit).orElseThrow(() -> new RuntimeException("timeUnit can not be null"));
        return between(source, Instant.now(), timeUnit);
    }

    /**
     * 获取日期距离当前的差值
     *
     * @param source
     * @param timeUnit
     * @return
     */
    public static long betweenNow(LocalDateTime source, TimeUnit timeUnit) {
        Optional.ofNullable(source).orElseThrow(() -> new RuntimeException("source can not be null"));
        Optional.ofNullable(timeUnit).orElseThrow(() -> new RuntimeException("timeUnit can not be null"));
        return between(source, LocalDateTime.now(), timeUnit);
    }

    /**
     * 获取日期距离当前的差值
     *
     * @param source
     * @param timeUnit
     * @return
     */
    public static long betweenNow(LocalDate source, TimeUnit timeUnit) {
        Optional.ofNullable(source).orElseThrow(() -> new RuntimeException("source can not be null"));
        Optional.ofNullable(timeUnit).orElseThrow(() -> new RuntimeException("timeUnit can not be null"));
        return between(source, LocalDate.now(), timeUnit);
    }

    /**
     * 获取日期距离当前的差值
     *
     * @param source
     * @param timeUnit
     * @return
     */
    public static long betweenNow(LocalTime source, TimeUnit timeUnit) {
        Optional.ofNullable(source).orElseThrow(() -> new RuntimeException("source can not be null"));
        Optional.ofNullable(timeUnit).orElseThrow(() -> new RuntimeException("timeUnit can not be null"));
        return between(source, LocalTime.now(), timeUnit);
    }

    /**
     * 获取两个日期的差值
     *
     * @param source
     * @param target
     * @param timeUnit
     * @return
     */
    private static long doBetween(LocalDateTime source, LocalDateTime target, TimeUnit timeUnit) {
        Duration duration = Duration.between(source, target);
        switch (timeUnit) {
            case DAYS:
                return Period.between(source.toLocalDate(), target.toLocalDate()).getDays();
            case HOURS:
                return duration.toHours();
            case MINUTES:
                return duration.toMinutes();
            case MILLISECONDS:
                return duration.toMillis();
            case SECONDS:
                return duration.toMillis() / 1000;
            case NANOSECONDS:
                return duration.toNanos();
            case MICROSECONDS:
                return duration.toMillis() * 1000;
            default:
                throw new RuntimeException("unknown timeUnit:" + timeUnit.name());
        }
    }

    /**
     * 在基础时间上增加
     *
     * @param source
     * @param amount
     * @param timeUnit
     * @return
     */
    public static Date plus(Date source, long amount, TimeUnit timeUnit) {
        Optional.ofNullable(source).orElseThrow(() -> new RuntimeException("source can not be null"));
        Optional.ofNullable(amount).orElseThrow(() -> new RuntimeException("amount can not be null"));
        Optional.ofNullable(timeUnit).orElseThrow(() -> new RuntimeException("timeUnit can not be null"));
        LocalDateTime localDateTime = plus(dateToLocalDateTimeConverter(source), amount, timeUnit);
        return localDateTimeToDateConverter(localDateTime);
    }

    /**
     * 在基础时间上增加
     *
     * @param source
     * @param amount
     * @param timeUnit
     * @return
     */
    public static Instant plus(Instant source, long amount, TimeUnit timeUnit) {
        Optional.ofNullable(source).orElseThrow(() -> new RuntimeException("source can not be null"));
        Optional.ofNullable(amount).orElseThrow(() -> new RuntimeException("amount can not be null"));
        Optional.ofNullable(timeUnit).orElseThrow(() -> new RuntimeException("timeUnit can not be null"));
        return (Instant) doPlus(source, amount, timeUnit);
    }

    /**
     * 在基础时间上增加
     *
     * @param source
     * @param amount
     * @param timeUnit
     * @return
     */
    public static LocalDateTime plus(LocalDateTime source, long amount, TimeUnit timeUnit) {
        Optional.ofNullable(source).orElseThrow(() -> new RuntimeException("source can not be null"));
        Optional.ofNullable(amount).orElseThrow(() -> new RuntimeException("amount can not be null"));
        Optional.ofNullable(timeUnit).orElseThrow(() -> new RuntimeException("timeUnit can not be null"));
        return (LocalDateTime) doPlus(source, amount, timeUnit);
    }

    /**
     * 在基础时间上增加
     *
     * @param source
     * @param amount
     * @param timeUnit
     * @return
     */
    public static LocalDate plus(LocalDate source, long amount, TimeUnit timeUnit) {
        Optional.ofNullable(source).orElseThrow(() -> new RuntimeException("source can not be null"));
        Optional.ofNullable(amount).orElseThrow(() -> new RuntimeException("amount can not be null"));
        Optional.ofNullable(timeUnit).orElseThrow(() -> new RuntimeException("timeUnit can not be null"));
        if(TimeUnit.DAYS!=timeUnit){
            throw new RuntimeException("LocalDate can not plus with :"+timeUnit.name());
        }
        return (LocalDate) doPlus(source, amount, timeUnit);
    }

    /**
     * 在基础时间上增加
     *
     * @param source
     * @param amount
     * @param timeUnit
     * @return
     */
    public static LocalTime plus(LocalTime source, long amount, TimeUnit timeUnit) {
        Optional.ofNullable(source).orElseThrow(() -> new RuntimeException("source can not be null"));
        Optional.ofNullable(amount).orElseThrow(() -> new RuntimeException("amount can not be null"));
        Optional.ofNullable(timeUnit).orElseThrow(() -> new RuntimeException("timeUnit can not be null"));
        if(TimeUnit.DAYS==timeUnit){
            throw new RuntimeException("LocalDate can not plus with :"+timeUnit.name());
        }
        return (LocalTime) doPlus(source, amount, timeUnit);
    }

    /**
     * 在当前时间上增加
     *
     * @param amount
     * @param timeUnit
     * @return
     */
    public static <T> T plusNow(long amount, TimeUnit timeUnit, Class<T> tClass) {
        Optional.ofNullable(amount).orElseThrow(() -> new RuntimeException("amount can not be null"));
        Optional.ofNullable(timeUnit).orElseThrow(() -> new RuntimeException("timeUnit can not be null"));
        Optional.ofNullable(tClass).orElseThrow(() -> new RuntimeException("tClass can not be null"));
        if (tClass.getName().equals(Date.class.getName())) {
            return (T) plus(new Date(), amount, timeUnit);
        } else if (tClass.getName().equals(Instant.class.getName())) {
            return (T) plus(Instant.now(), amount, timeUnit);
        } else if (tClass.getName().equals(LocalDateTime.class.getName())) {
            return (T) plus(Instant.now(), amount, timeUnit);
        } else if (tClass.getName().equals(LocalDate.class.getName())) {
            return (T) plus(Instant.now(), amount, timeUnit);
        } else if (tClass.getName().equals(LocalTime.class.getName())) {
            return (T) plus(Instant.now(), amount, timeUnit);
        } else {
            throw new RuntimeException("can not plus now with class:" + tClass.getName());
        }
    }

    /**
     * 在基础时间上增加
     *
     * @param source
     * @param amount
     * @param timeUnit
     * @param <T>
     * @return
     */
    private static <T extends TemporalAccessor> Temporal doPlus(Temporal source, long amount, TimeUnit timeUnit) {
        switch (timeUnit) {
            case DAYS:
                return source.plus(amount, ChronoUnit.DAYS);
            case HOURS:
                return source.plus(amount, ChronoUnit.HOURS);
            case MINUTES:
                return source.plus(amount, ChronoUnit.MINUTES);
            case MILLISECONDS:
                return source.plus(amount, ChronoUnit.MILLIS);
            case SECONDS:
                return source.plus(amount * 1000, ChronoUnit.MILLIS);
            case NANOSECONDS:
                return source.plus(amount, ChronoUnit.NANOS);
            case MICROSECONDS:
                return source.plus(amount / 1000, ChronoUnit.MILLIS);
            default:
                throw new RuntimeException("unknown timeUnit:" + timeUnit.name());
        }
    }

    /**
     * 在基础时间上减少
     *
     * @param source
     * @param amount
     * @param timeUnit
     * @return
     */
    public static Date minus(Date source, long amount, TimeUnit timeUnit) {
        Optional.ofNullable(source).orElseThrow(() -> new RuntimeException("source can not be null"));
        Optional.ofNullable(amount).orElseThrow(() -> new RuntimeException("amount can not be null"));
        Optional.ofNullable(timeUnit).orElseThrow(() -> new RuntimeException("timeUnit can not be null"));
        LocalDateTime localDateTime = minus(dateToLocalDateTimeConverter(source), amount, timeUnit);
        return localDateTimeToDateConverter(localDateTime);
    }

    /**
     * 在基础时间上减少
     *
     * @param source
     * @param amount
     * @param timeUnit
     * @return
     */
    public static Instant minus(Instant source, long amount, TimeUnit timeUnit) {
        Optional.ofNullable(source).orElseThrow(() -> new RuntimeException("source can not be null"));
        Optional.ofNullable(amount).orElseThrow(() -> new RuntimeException("amount can not be null"));
        Optional.ofNullable(timeUnit).orElseThrow(() -> new RuntimeException("timeUnit can not be null"));
        return (Instant) doMinus(source, amount, timeUnit);
    }

    /**
     * 在基础时间上减少
     *
     * @param source
     * @param amount
     * @param timeUnit
     * @return
     */
    public static LocalDateTime minus(LocalDateTime source, long amount, TimeUnit timeUnit) {
        Optional.ofNullable(source).orElseThrow(() -> new RuntimeException("source can not be null"));
        Optional.ofNullable(amount).orElseThrow(() -> new RuntimeException("amount can not be null"));
        Optional.ofNullable(timeUnit).orElseThrow(() -> new RuntimeException("timeUnit can not be null"));
        return (LocalDateTime) doMinus(source, amount, timeUnit);
    }

    /**
     * 在基础时间上减少
     *
     * @param source
     * @param amount
     * @param timeUnit
     * @return
     */
    public static LocalDate minus(LocalDate source, long amount, TimeUnit timeUnit) {
        Optional.ofNullable(source).orElseThrow(() -> new RuntimeException("source can not be null"));
        Optional.ofNullable(amount).orElseThrow(() -> new RuntimeException("amount can not be null"));
        Optional.ofNullable(timeUnit).orElseThrow(() -> new RuntimeException("timeUnit can not be null"));
        if(TimeUnit.DAYS!=timeUnit){
            throw new RuntimeException("LocalDate can not minus with :"+timeUnit.name());
        }
        return (LocalDate) doMinus(source, amount, timeUnit);
    }

    /**
     * 在基础时间上减少
     *
     * @param source
     * @param amount
     * @param timeUnit
     * @return
     */
    public static LocalTime minus(LocalTime source, long amount, TimeUnit timeUnit) {
        Optional.ofNullable(source).orElseThrow(() -> new RuntimeException("source can not be null"));
        Optional.ofNullable(amount).orElseThrow(() -> new RuntimeException("amount can not be null"));
        Optional.ofNullable(timeUnit).orElseThrow(() -> new RuntimeException("timeUnit can not be null"));
        if(TimeUnit.DAYS==timeUnit){
            throw new RuntimeException("LocalDate can not minus with :"+timeUnit.name());
        }
        return (LocalTime) doMinus(source, amount, timeUnit);
    }

    /**
     * 在当前时间上增加
     *
     * @param amount
     * @param timeUnit
     * @return
     */
    public static <T> T minusNow(long amount, TimeUnit timeUnit, Class<T> tClass) {
        Optional.ofNullable(amount).orElseThrow(() -> new RuntimeException("amount can not be null"));
        Optional.ofNullable(timeUnit).orElseThrow(() -> new RuntimeException("timeUnit can not be null"));
        Optional.ofNullable(tClass).orElseThrow(() -> new RuntimeException("tClass can not be null"));
        if (tClass.getName().equals(Date.class.getName())) {
            return (T) minus(new Date(), amount, timeUnit);
        } else if (tClass.getName().equals(Instant.class.getName())) {
            return (T) minus(Instant.now(), amount, timeUnit);
        } else if (tClass.getName().equals(LocalDateTime.class.getName())) {
            return (T) minus(Instant.now(), amount, timeUnit);
        } else if (tClass.getName().equals(LocalDate.class.getName())) {
            return (T) minus(Instant.now(), amount, timeUnit);
        } else if (tClass.getName().equals(LocalTime.class.getName())) {
            return (T) minus(Instant.now(), amount, timeUnit);
        } else {
            throw new RuntimeException("can not minus now with class:" + tClass.getName());
        }
    }

    /**
     * 在基础时间上减少
     *
     * @param source
     * @param amount
     * @param timeUnit
     * @param <T>
     * @return
     */
    private static <T extends TemporalAccessor> Temporal doMinus(Temporal source, long amount, TimeUnit timeUnit) {
        switch (timeUnit) {
            case DAYS:
                return source.minus(amount, ChronoUnit.DAYS);
            case HOURS:
                return source.minus(amount, ChronoUnit.HOURS);
            case MINUTES:
                return source.minus(amount, ChronoUnit.MINUTES);
            case MILLISECONDS:
                return source.minus(amount, ChronoUnit.MILLIS);
            case SECONDS:
                return source.minus(amount * 1000, ChronoUnit.MILLIS);
            case NANOSECONDS:
                return source.minus(amount, ChronoUnit.NANOS);
            case MICROSECONDS:
                return source.minus(amount / 1000, ChronoUnit.MILLIS);
            default:
                throw new RuntimeException("unknown timeUnit:" + timeUnit.name());
        }
    }
}