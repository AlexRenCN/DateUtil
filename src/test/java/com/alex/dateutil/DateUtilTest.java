package com.alex.dateutil;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class DateUtilTest {
    //普通测试数据
    private List<Date> DATE_LIST = new ArrayList<>(10);

    private List<Instant> INSTANT_LIST = new ArrayList<>(10);

    private List<LocalDateTime> LOCAL_DATE_TIME_LIST = new ArrayList<>(10);

    private List<LocalDate> LOCAL_DATE_LIST = new ArrayList<>(10);

    private List<LocalTime> LOCAL_TIME_LIST = new ArrayList<>(10);


    //偏移量测试数据
    private List<Date> DATE_OFFSET_LIST = new ArrayList<>(10);

    private List<Instant> INSTANT_OFFSET_LIST = new ArrayList<>(10);

    private List<LocalDateTime> LOCAL_DATE_TIME_OFFSET_LIST = new ArrayList<>(10);

    private List<LocalDate> LOCAL_DATE_OFFSET_LIST = new ArrayList<>(10);

    private List<LocalTime> LOCAL_TIME_OFFSET_LIST = new ArrayList<>(10);


    //偏移量测试数据
    private List<Date> DATE_BETWEEN_LIST = new ArrayList<>(10);

    private List<Instant> INSTANT_BETWEEN_LIST = new ArrayList<>(10);

    private List<LocalDateTime> LOCAL_DATE_TIME_BETWEEN_LIST = new ArrayList<>(10);

    private List<LocalDate> LOCAL_DATE_BETWEEN_LIST = new ArrayList<>(10);

    private List<LocalTime> LOCAL_TIME_BETWEEN_LIST = new ArrayList<>(10);

    private ExecutorService EXECUTOR = Executors.newFixedThreadPool(5);

    private ZoneId ZONE = ZoneId.systemDefault();

    @Before
    public void before() {
        for (int a = 1; a < 11; a++) {
            LocalDateTime localDateTime = LocalDateTime.of(a, a, a, a, a, a);
            LOCAL_DATE_TIME_LIST.add(localDateTime);
            Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
            INSTANT_LIST.add(instant);
            Date date = Date.from(instant);
            DATE_LIST.add(date);
            LocalDate localDate = localDateTime.toLocalDate();
            LOCAL_DATE_LIST.add(localDate);
            LocalTime localTime = localDateTime.toLocalTime();
            LOCAL_TIME_LIST.add(localTime);


            LocalDateTime localDateTimeOffset = LocalDateTime.of(1990 + a, 1, 2, 3, 4, 5);
            LOCAL_DATE_TIME_OFFSET_LIST.add(localDateTimeOffset);
            Instant instantOffset = localDateTimeOffset.atZone(ZoneId.systemDefault()).toInstant();
            INSTANT_OFFSET_LIST.add(instantOffset);
            Date dateOffset = Date.from(instantOffset);
            DATE_OFFSET_LIST.add(dateOffset);
            LocalDate localDateOffset = localDateTimeOffset.toLocalDate();
            LOCAL_DATE_OFFSET_LIST.add(localDateOffset);
            LocalTime localTimeOffset = localDateTimeOffset.toLocalTime();
            LOCAL_TIME_OFFSET_LIST.add(localTimeOffset);


            LocalDateTime localDateTimeBetween = LocalDateTime.of(2019, 6, a, 0, 0, 0);
            LOCAL_DATE_TIME_BETWEEN_LIST.add(localDateTimeBetween);
            Instant instantBetween = localDateTimeBetween.atZone(ZoneId.systemDefault()).toInstant();
            INSTANT_BETWEEN_LIST.add(instantBetween);
            Date dateBetween = Date.from(instantBetween);
            DATE_BETWEEN_LIST.add(dateBetween);
            LocalDate localDateBetween = localDateTimeBetween.toLocalDate();
            LOCAL_DATE_BETWEEN_LIST.add(localDateBetween);
            LocalTime localTimeBetween = LocalTime.of(a,0,0);
            LOCAL_TIME_BETWEEN_LIST.add(localTimeBetween);
        }
    }

    @After
    public void after() throws InterruptedException {
        Thread.sleep(1000);
    }

    public class InstantToDateRunnable implements Runnable {
        private Instant instant;

        public InstantToDateRunnable(Instant instant) {
            this.instant = instant;
        }

        @Override
        public void run() {
            try {
                Date date = DateUtil.instantToDateConverter(instant);
                System.out.println("task【1】instantToDate,Thread id= "
                        + Thread.currentThread().getId()
                        + " param= "
                        + instant.atZone(ZONE).toString()
                        + " ,result="
                        + DateUtil.format(date, DateUtil.YYYY_MM_DD_HH_MM_SS));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Instant 转 java.util.Date
     */
    @Test
    public void instantToDateConverter() throws InterruptedException {
        for (int i = 0; i < INSTANT_LIST.size(); i++) {
            EXECUTOR.submit(new InstantToDateRunnable(INSTANT_LIST.get(i)));
        }
    }

    public class LocalDateTimeToDateRunnable implements Runnable {
        private LocalDateTime localDateTime;

        public LocalDateTimeToDateRunnable(LocalDateTime localDateTime) {
            this.localDateTime = localDateTime;
        }

        @Override
        public void run() {
            try {
                Date date = DateUtil.localDateTimeToDateConverter(localDateTime);
                System.out.println("task【2】LocalDateTimeToDate,Thread id= "
                        + Thread.currentThread().getId()
                        + " param= "
                        + localDateTime.atZone(ZONE).toString()
                        + " ,result="
                        + DateUtil.format(date, DateUtil.YYYY_MM_DD_HH_MM_SS));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * LocalDateTime 转 java.util.Date
     */
    @Test
    public void localDateTimeToDateConverter() {
        for (int i = 0; i < LOCAL_DATE_TIME_LIST.size(); i++) {
            EXECUTOR.submit(new LocalDateTimeToDateRunnable(LOCAL_DATE_TIME_LIST.get(i)));
        }
    }

    public class LocalDateToDateRunnable implements Runnable {
        private LocalDate localDate;

        public LocalDateToDateRunnable(LocalDate localDate) {
            this.localDate = localDate;
        }

        @Override
        public void run() {
            try {
                Date date = DateUtil.localDateToDateConverter(localDate);
                System.out.println("task【3】localDateToDateConverter,Thread id= "
                        + Thread.currentThread().getId()
                        + " param= "
                        + localDate.toString()
                        + " ,result="
                        + DateUtil.format(date, DateUtil.YYYY_MM_DD_HH_MM_SS));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * LocalDate 转 java.util.Date
     */
    @Test
    public void localDateToDateConverter() {
        for (int i = 0; i < LOCAL_DATE_LIST.size(); i++) {
            EXECUTOR.submit(new LocalDateToDateRunnable(LOCAL_DATE_LIST.get(i)));
        }
    }

    public class LocalDateToLocalDateTimeRunnable implements Runnable {
        private LocalDate localDate;

        public LocalDateToLocalDateTimeRunnable(LocalDate localDate) {
            this.localDate = localDate;
        }

        @Override
        public void run() {
            try {
                LocalDateTime localDateTime = DateUtil.localDateToLocalDateTimeConverter(localDate);
                System.out.println("task【3】localDateToLocalDateTimeConverter,Thread id= "
                        + Thread.currentThread().getId()
                        + " param= "
                        + localDate.toString()
                        + " ,result="
                        + DateUtil.format(localDateTime, DateUtil.YYYY_MM_DD_HH_MM_SS));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void localDateToLocalDateTimeConverter() {
        for (int i = 0; i < LOCAL_DATE_LIST.size(); i++) {
            EXECUTOR.submit(new LocalDateToDateRunnable(LOCAL_DATE_LIST.get(i)));
        }
    }

    public class LocalTimeToLocalDateTimeRunnable implements Runnable {
        private LocalTime localTime;

        public LocalTimeToLocalDateTimeRunnable(LocalTime localTime) {
            this.localTime = localTime;
        }

        @Override
        public void run() {
            try {
                LocalDateTime localDateTime = DateUtil.localTimeToLocalDateTimeConverter(localTime);
                System.out.println("task【3】localTimeToLocalDateTimeConverter,Thread id= "
                        + Thread.currentThread().getId()
                        + " param= "
                        + localTime.toString()
                        + " ,result="
                        + DateUtil.format(localDateTime, DateUtil.YYYY_MM_DD_HH_MM_SS));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void localTimeToLocalDateTimeConverter() {
        for (int i = 0; i < LOCAL_TIME_LIST.size(); i++) {
            EXECUTOR.submit(new LocalTimeToLocalDateTimeRunnable(LOCAL_TIME_LIST.get(i)));
        }
    }

    public class DateToInstantConverterRunnable implements Runnable {
        private Date date;

        public DateToInstantConverterRunnable(Date date) {
            this.date = date;
        }

        @Override
        public void run() {
            try {
                Instant instant = DateUtil.dateToInstantConverter(date);
                System.out.println("task【4】dateToInstantConverter,Thread id= "
                        + Thread.currentThread().getId()
                        + " param= "
                        + DateUtil.format(date, DateUtil.YYYY_MM_DD_HH_MM_SS)
                        + " ,result="
                        + instant.atZone(ZONE).toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * java.util.Date 转 Instant
     */
    @Test
    public void dateToInstantConverter() {
        for (int i = 0; i < DATE_LIST.size(); i++) {
            EXECUTOR.submit(new DateToInstantConverterRunnable(DATE_LIST.get(i)));
        }
    }

    public class DateToLocalDateTimeRunnable implements Runnable {
        private Date date;

        public DateToLocalDateTimeRunnable(Date date) {
            this.date = date;
        }

        @Override
        public void run() {
            try {
                LocalDateTime localDateTime = DateUtil.dateToLocalDateTimeConverter(date);
                System.out.println("task【5】dateToLocalDateTimeConverter,Thread id= "
                        + Thread.currentThread().getId()
                        + " param= "
                        + DateUtil.format(date, DateUtil.YYYY_MM_DD_HH_MM_SS)
                        + " ,result="
                        + localDateTime.atZone(ZONE).toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * java.util.Date 转 LocalDateTime
     */
    @Test
    public void dateToLocalDateTimeConverter() {
        for (int i = 0; i < DATE_LIST.size(); i++) {
            EXECUTOR.submit(new DateToLocalDateTimeRunnable(DATE_LIST.get(i)));
        }
    }

    public class DateToLocalDateRunnable implements Runnable {
        private Date date;

        public DateToLocalDateRunnable(Date date) {
            this.date = date;
        }

        @Override
        public void run() {
            try {
                LocalDate localDate = DateUtil.dateToLocalDateConverter(date);
                System.out.println("task【6】localDate,Thread id= "
                        + Thread.currentThread().getId()
                        + " param= "
                        + DateUtil.format(date, DateUtil.YYYY_MM_DD_HH_MM_SS)
                        + " ,result="
                        + localDate.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * java.util.Date 转 LocalDate
     */
    @Test
    public void dateToLocalDateConverter() {
        for (int i = 0; i < DATE_LIST.size(); i++) {
            EXECUTOR.submit(new DateToLocalDateTimeRunnable(DATE_LIST.get(i)));
        }
    }

    public class DateToLocalTimeRunnable implements Runnable {
        private Date date;

        public DateToLocalTimeRunnable(Date date) {
            this.date = date;
        }

        @Override
        public void run() {
            try {
                LocalTime localTime = DateUtil.dateToLocalTimeConverter(date);
                System.out.println("task【7】localTime,Thread id= "
                        + Thread.currentThread().getId()
                        + " param= "
                        + DateUtil.format(date, DateUtil.YYYY_MM_DD_HH_MM_SS)
                        + " ,result="
                        + localTime.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * java.util.Date 转 LocalTime
     */
    @Test
    public void dateToLocalTimeConverter() {
        for (int i = 0; i < DATE_LIST.size(); i++) {
            EXECUTOR.submit(new DateToLocalTimeRunnable(DATE_LIST.get(i)));
        }
    }

    public class LocalDateTimeToInstantRunnable implements Runnable {
        private LocalDateTime localDateTime;

        public LocalDateTimeToInstantRunnable(LocalDateTime localDateTime) {
            this.localDateTime = localDateTime;
        }

        @Override
        public void run() {
            try {
                Instant instant = DateUtil.localDateTimeToInstantConverter(localDateTime);
                System.out.println("task【8】dateToLocalTimeConverter,Thread id= "
                        + Thread.currentThread().getId()
                        + " param= "
                        + localDateTime.atZone(ZONE).toString()
                        + " ,result="
                        + instant.atZone(ZONE).toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * LocalDateTime 转 Instant
     */
    @Test
    public void localDateTimeToInstantConverter() {
        for (int i = 0; i < LOCAL_DATE_TIME_LIST.size(); i++) {
            EXECUTOR.submit(new LocalDateTimeToInstantRunnable(LOCAL_DATE_TIME_LIST.get(i)));
        }
    }

    public class InstantToLocalDateTimeRunnable implements Runnable {
        private Instant instant;

        public InstantToLocalDateTimeRunnable(Instant instant) {
            this.instant = instant;
        }

        @Override
        public void run() {
            try {
                LocalDateTime localDateTime = DateUtil.instantToLocalDateTimeConverter(instant);
                System.out.println("task【9】instantToLocalDateTimeConverter,Thread id= "
                        + Thread.currentThread().getId()
                        + " param= "
                        + instant.atZone(ZONE).toString()
                        + " ,result="
                        + localDateTime.atZone(ZONE).toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Instant 转 LocalDateTime
     */
    @Test
    public void instantToLocalDateTimeConverter() {
        for (int i = 0; i < INSTANT_LIST.size(); i++) {
            EXECUTOR.submit(new InstantToLocalDateTimeRunnable(INSTANT_LIST.get(i)));
        }
    }

    public class localDateTimeToLocalDateRunnable implements Runnable {
        private LocalDateTime localDateTime;

        public localDateTimeToLocalDateRunnable(LocalDateTime localDateTime) {
            this.localDateTime = localDateTime;
        }

        @Override
        public void run() {
            try {
                LocalDate localDate = DateUtil.localDateTimeToLocalDateConverter(localDateTime);
                System.out.println("task【10】localDateTimeToLocalDateConverter,Thread id= "
                        + Thread.currentThread().getId()
                        + " param= "
                        + localDateTime.atZone(ZONE).toString()
                        + " ,result="
                        + localDate.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * LocalDateTime 转 LocalDate
     */
    @Test
    public void localDateTimeToLocalDateConverter() {
        for (int i = 0; i < LOCAL_DATE_TIME_LIST.size(); i++) {
            EXECUTOR.submit(new localDateTimeToLocalDateRunnable(LOCAL_DATE_TIME_LIST.get(i)));
        }
    }

    public class localDateTimeToLocalTimeRunnable implements Runnable {
        private LocalDateTime localDateTime;

        public localDateTimeToLocalTimeRunnable(LocalDateTime localDateTime) {
            this.localDateTime = localDateTime;
        }

        @Override
        public void run() {
            try {
                LocalTime localTime = DateUtil.localDateTimeToLocalTimeConverter(localDateTime);
                System.out.println("task【11】localDateTimeToLocalTimeConverter,Thread id= "
                        + Thread.currentThread().getId()
                        + " param= "
                        + localDateTime.atZone(ZONE).toString()
                        + " ,result="
                        + localTime.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * LocalDateTime 转 LocalTime
     */
    @Test
    public void localDateTimeToLocalTimeConverter() {
        for (int i = 0; i < LOCAL_DATE_TIME_LIST.size(); i++) {
            EXECUTOR.submit(new localDateTimeToLocalTimeRunnable(LOCAL_DATE_TIME_LIST.get(i)));
        }
    }

    public class DateFormatRunnable implements Runnable {
        private Date date;

        public DateFormatRunnable(Date date) {
            this.date = date;
        }

        @Override
        public void run() {
            try {
                System.out.println("task【12】format,Thread id= "
                        + Thread.currentThread().getId()
                        + " ,result= "
                        + DateUtil.format(date, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS)
                        + "\r\n"
                        + DateUtil.format(date, DateUtil.YYYY_MM_DD_HH_MM_SS)
                        + "\r\n"
                        + DateUtil.format(date, DateUtil.YYYYMMDDHHMMSS)
                        + "\r\n"
                        + DateUtil.format(date, DateUtil.YYYYMMDD)
                        + "\r\n"
                        + DateUtil.format(date, DateUtil.YYYY_MM_DD)
                        + "\r\n"
                        + DateUtil.format(date, DateUtil.HHMMDD)
                        + "\r\n"
                        + DateUtil.format(date, DateUtil.HH_MM_DD)
                        + "\r\n");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * java.util.Date格式化
     */
    @Test
    public void dateFormat() {
        for (int i = 0; i < DATE_LIST.size(); i++) {
            EXECUTOR.submit(new DateFormatRunnable(DATE_LIST.get(i)));
        }
    }

    public class InstantFormatRunnable implements Runnable {
        private Instant instant;

        public InstantFormatRunnable(Instant instant) {
            this.instant = instant;
        }

        @Override
        public void run() {
            try {
                System.out.println("task【13】format,Thread id= "
                        + Thread.currentThread().getId()
                        + " ,result= "
                        + DateUtil.format(instant, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS)
                        + "\r\n"
                        + DateUtil.format(instant, DateUtil.YYYY_MM_DD_HH_MM_SS)
                        + "\r\n"
                        + DateUtil.format(instant, DateUtil.YYYYMMDDHHMMSS)
                        + "\r\n"
                        + DateUtil.format(instant, DateUtil.YYYYMMDD)
                        + "\r\n"
                        + DateUtil.format(instant, DateUtil.YYYY_MM_DD)
                        + "\r\n"
                        + DateUtil.format(instant, DateUtil.HHMMDD)
                        + "\r\n"
                        + DateUtil.format(instant, DateUtil.HH_MM_DD)
                        + "\r\n"
                );

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Instant格式化
     */
    @Test
    public void instantFormat() {
        for (int i = 0; i < INSTANT_LIST.size(); i++) {
            EXECUTOR.submit(new InstantFormatRunnable(INSTANT_LIST.get(i)));
        }
    }

    public class LocalDateTimeFormatRunnable implements Runnable {
        private LocalDateTime localDateTime;

        public LocalDateTimeFormatRunnable(LocalDateTime localDateTime) {
            this.localDateTime = localDateTime;
        }

        @Override
        public void run() {
            try {
                System.out.println("task【14】format,Thread id= "
                        + Thread.currentThread().getId()
                        + " ,result= "
                        + DateUtil.format(localDateTime, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS)
                        + "\r\n"
                        + DateUtil.format(localDateTime, DateUtil.YYYY_MM_DD_HH_MM_SS)
                        + "\r\n"
                        + DateUtil.format(localDateTime, DateUtil.YYYYMMDDHHMMSS)
                        + "\r\n"
                        + DateUtil.format(localDateTime, DateUtil.YYYYMMDD)
                        + "\r\n"
                        + DateUtil.format(localDateTime, DateUtil.YYYY_MM_DD)
                        + "\r\n"
                        + DateUtil.format(localDateTime, DateUtil.HHMMDD)
                        + "\r\n"
                        + DateUtil.format(localDateTime, DateUtil.HH_MM_DD)
                        + "\r\n"
                );

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * LocalDateTime格式化
     */
    @Test
    public void localDateTimeFormat() {
        for (int i = 0; i < LOCAL_DATE_TIME_LIST.size(); i++) {
            EXECUTOR.submit(new LocalDateTimeFormatRunnable(LOCAL_DATE_TIME_LIST.get(i)));
        }
    }

    public class LocalDateFormatRunnable implements Runnable {
        private LocalDate localDate;

        public LocalDateFormatRunnable(LocalDate localDate) {
            this.localDate = localDate;
        }

        @Override
        public void run() {
            try {
                System.out.println("task【15】format,Thread id= "
                        + Thread.currentThread().getId()
                        + " ,result= "
                        + DateUtil.format(localDate, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS)
                        + "\r\n"
                        + DateUtil.format(localDate, DateUtil.YYYY_MM_DD_HH_MM_SS)
                        + "\r\n"
                        + DateUtil.format(localDate, DateUtil.YYYYMMDDHHMMSS)
                        + "\r\n"
                        + DateUtil.format(localDate, DateUtil.YYYYMMDD)
                        + "\r\n"
                        + DateUtil.format(localDate, DateUtil.YYYY_MM_DD)
                        + "\r\n"
                        + DateUtil.format(localDate, DateUtil.HHMMDD)
                        + "\r\n"
                        + DateUtil.format(localDate, DateUtil.HH_MM_DD)
                        + "\r\n"
                );

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * LocalDate格式化
     */
    @Test
    public void localDateFormat() {
        for (int i = 0; i < LOCAL_DATE_LIST.size(); i++) {
            EXECUTOR.submit(new LocalDateFormatRunnable(LOCAL_DATE_LIST.get(i)));
        }
    }

    public class LocalTimeFormatRunnable implements Runnable {
        private LocalTime localTime;

        public LocalTimeFormatRunnable(LocalTime localTime) {
            this.localTime = localTime;
        }

        @Override
        public void run() {
            try {
                System.out.println("task【16】format,Thread id= "
                        + Thread.currentThread().getId()
                        + " ,result= "
                        + DateUtil.format(localTime, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS)
                        + "\r\n"
                        + DateUtil.format(localTime, DateUtil.YYYY_MM_DD_HH_MM_SS)
                        + "\r\n"
                        + DateUtil.format(localTime, DateUtil.YYYYMMDDHHMMSS)
                        + "\r\n"
                        + DateUtil.format(localTime, DateUtil.YYYYMMDD)
                        + "\r\n"
                        + DateUtil.format(localTime, DateUtil.YYYY_MM_DD)
                        + "\r\n"
                        + DateUtil.format(localTime, DateUtil.HHMMDD)
                        + "\r\n"
                        + DateUtil.format(localTime, DateUtil.HH_MM_DD)
                        + "\r\n"
                );

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * LocalTime格式化
     */
    @Test
    public void localTimeFormat() {
        for (int i = 0; i < LOCAL_TIME_LIST.size(); i++) {
            EXECUTOR.submit(new LocalTimeFormatRunnable(LOCAL_TIME_LIST.get(i)));
        }
    }

    public class FormatDateRunnable implements Runnable {
        private int anInt;

        public FormatDateRunnable(int anInt) {
            this.anInt = anInt;
        }

        @Override
        public void run() {
            try {
                Date date;

                String YYYY_MM_DD_HH_MM_SS_SSS = "" + anInt + anInt + anInt + anInt + "-" + 0 + anInt + "-" + 0 + anInt + " " + 0 + anInt + ":" + 0 + anInt + ":" + 0 + anInt + "." + anInt + anInt + anInt;
                date = DateUtil.formatDate(YYYY_MM_DD_HH_MM_SS_SSS, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS);
                System.out.println("task【17】【" + Thread.currentThread().getId() + "】formatDate," + YYYY_MM_DD_HH_MM_SS_SSS + " ~~ " + DateUtil.format(date, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));

                String YYYY_MM_DD_HH_MM_SS = "" + anInt + anInt + anInt + anInt + "-" + 0 + anInt + "-" + 0 + anInt + " " + 0 + anInt + ":" + 0 + anInt + ":" + 0 + anInt;
                date = DateUtil.formatDate(YYYY_MM_DD_HH_MM_SS, DateUtil.YYYY_MM_DD_HH_MM_SS);
                System.out.println("task【17】【" + Thread.currentThread().getId() + "】formatDate," + YYYY_MM_DD_HH_MM_SS + " ~~ " + DateUtil.format(date, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));

                String YYYYMMDDHHMMSS = "" + anInt + anInt + anInt + anInt + 0 + anInt + 0 + anInt + 0 + anInt + 0 + anInt + 0 + anInt;
                date = DateUtil.formatDate(YYYYMMDDHHMMSS, DateUtil.YYYYMMDDHHMMSS);
                System.out.println("task【17】【" + Thread.currentThread().getId() + "】formatDate," + YYYYMMDDHHMMSS + " ~~ " + DateUtil.format(date, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));


                String YYYYMMDD = "" + anInt + anInt + anInt + anInt + 0 + anInt + 0 + anInt;
                date = DateUtil.formatDate(YYYYMMDD, DateUtil.YYYYMMDD);
                System.out.println("task【17】【" + Thread.currentThread().getId() + "】formatDate," + YYYYMMDD + " ~~ " + DateUtil.format(date, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));

                String YYYY_MM_DD = "" + anInt + anInt + anInt + anInt + "-" + 0 + anInt + "-" + 0 + anInt;
                date = DateUtil.formatDate(YYYY_MM_DD, DateUtil.YYYY_MM_DD);
                System.out.println("task【17】【" + Thread.currentThread().getId() + "】formatDate," + YYYY_MM_DD + " ~~ " + DateUtil.format(date, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));

                String HHMMDD = "" + 0 + anInt + 0 + anInt + 0 + anInt;
                date = DateUtil.formatDate(HHMMDD, DateUtil.HHMMDD);
                System.out.println("task【17】【" + Thread.currentThread().getId() + "】formatDate," + HHMMDD + " ~~ " + DateUtil.format(date, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));

                String HH_MM_DD = "" + 0 + anInt + ":" + 0 + anInt + ":" + 0 + anInt;
                date = DateUtil.formatDate(HH_MM_DD, DateUtil.HH_MM_DD);
                System.out.println("task【17】【" + Thread.currentThread().getId() + "】formatDate," + HH_MM_DD + " ~~ " + DateUtil.format(date, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 反格式化为java.util.Date
     */
    @Test
    public void formatDate() throws InterruptedException {
        for (int i = 1; i < 9; i++) {
            EXECUTOR.submit(new FormatDateRunnable(i));
        }
    }

    public class FormatInstantRunnable implements Runnable {
        private Instant instant;

        public FormatInstantRunnable(Instant instant) {
            this.instant = instant;
        }

        @Override
        public void run() {
            try {
                Instant result;
                String YYYY_MM_DD_HH_MM_SS_SSS = DateUtil.format(instant, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS);
                result = DateUtil.formatInstant(YYYY_MM_DD_HH_MM_SS_SSS, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS);
                System.out.println("task【18】【" + Thread.currentThread().getId() + "】formatInstant: " + YYYY_MM_DD_HH_MM_SS_SSS + "  ~~  " + DateUtil.format(result, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));

                String YYYY_MM_DD_HH_MM_SS = DateUtil.format(instant, DateUtil.YYYY_MM_DD_HH_MM_SS);
                result = DateUtil.formatInstant(YYYY_MM_DD_HH_MM_SS, DateUtil.YYYY_MM_DD_HH_MM_SS);
                System.out.println("task【18】【" + Thread.currentThread().getId() + "】formatInstant" + YYYY_MM_DD_HH_MM_SS + "  ~~  " + DateUtil.format(result, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));

                String YYYYMMDDHHMMSS = DateUtil.format(instant, DateUtil.YYYYMMDDHHMMSS);
                result = DateUtil.formatInstant(YYYYMMDDHHMMSS, DateUtil.YYYYMMDDHHMMSS);
                System.out.println("task【18】【" + Thread.currentThread().getId() + "】formatInstant: " + YYYYMMDDHHMMSS + "  ~~  " + DateUtil.format(result, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));

                String YYYYMMDD = DateUtil.format(instant, DateUtil.YYYYMMDD);
                result = DateUtil.formatInstant(YYYYMMDD, DateUtil.YYYYMMDD);
                System.out.println("task【18】【" + Thread.currentThread().getId() + "】formatInstant: " + YYYYMMDD + "  ~~  " + DateUtil.format(result, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));

                String YYYY_MM_DD = DateUtil.format(instant, DateUtil.YYYY_MM_DD);
                result = DateUtil.formatInstant(YYYY_MM_DD, DateUtil.YYYY_MM_DD);
                System.out.println("task【18】【" + Thread.currentThread().getId() + "】formatInstant: " + YYYY_MM_DD + "  ~~  " + DateUtil.format(result, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));

                String HHMMDD = DateUtil.format(instant, DateUtil.HHMMDD);
                result = DateUtil.formatInstant(HHMMDD, DateUtil.HHMMDD);
                System.out.println("task【18】【" + Thread.currentThread().getId() + "】formatInstant: " + HHMMDD + "  ~~  " + DateUtil.format(result, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));

                String HH_MM_DD = DateUtil.format(instant, DateUtil.HH_MM_DD);
                result = DateUtil.formatInstant(HH_MM_DD, DateUtil.HH_MM_DD);
                System.out.println("task【18】【" + Thread.currentThread().getId() + "】formatInstant: " + HH_MM_DD + "  ~~  " + DateUtil.format(result, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 反格式化为Instant
     */
    @Test
    public void formatInstant() throws InterruptedException {
        for (int i = 1; i < INSTANT_LIST.size(); i++) {
            EXECUTOR.submit(new FormatInstantRunnable(INSTANT_LIST.get(i)));
        }
    }

    public class FormatLocalDateTimeRunnable implements Runnable {
        private LocalDateTime localDateTime;

        public FormatLocalDateTimeRunnable(LocalDateTime localDateTime) {
            this.localDateTime = localDateTime;
        }

        @Override
        public void run() {
            try {
                LocalDateTime result;
                String YYYY_MM_DD_HH_MM_SS_SSS = DateUtil.format(localDateTime, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS);
                result = DateUtil.formatLocalDateTime(YYYY_MM_DD_HH_MM_SS_SSS, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS);
                System.out.println("task【19】【" + Thread.currentThread().getId() + "】formatLocalDateTime: " + YYYY_MM_DD_HH_MM_SS_SSS + "  ~~  " + DateUtil.format(result, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));

                String YYYY_MM_DD_HH_MM_SS = DateUtil.format(localDateTime, DateUtil.YYYY_MM_DD_HH_MM_SS);
                result = DateUtil.formatLocalDateTime(YYYY_MM_DD_HH_MM_SS, DateUtil.YYYY_MM_DD_HH_MM_SS);
                System.out.println("task【19】【" + Thread.currentThread().getId() + "】formatLocalDateTime" + YYYY_MM_DD_HH_MM_SS + "  ~~  " + DateUtil.format(result, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));

                String YYYYMMDDHHMMSS = DateUtil.format(localDateTime, DateUtil.YYYYMMDDHHMMSS);
                result = DateUtil.formatLocalDateTime(YYYYMMDDHHMMSS, DateUtil.YYYYMMDDHHMMSS);
                System.out.println("task【19】【" + Thread.currentThread().getId() + "】formatLocalDateTime: " + YYYYMMDDHHMMSS + "  ~~  " + DateUtil.format(result, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));

                String YYYYMMDD = DateUtil.format(localDateTime, DateUtil.YYYYMMDD);
                result = DateUtil.formatLocalDateTime(YYYYMMDD, DateUtil.YYYYMMDD);
                System.out.println("task【19】【" + Thread.currentThread().getId() + "】formatLocalDateTime: " + YYYYMMDD + "  ~~  " + DateUtil.format(result, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));

                String YYYY_MM_DD = DateUtil.format(localDateTime, DateUtil.YYYY_MM_DD);
                result = DateUtil.formatLocalDateTime(YYYY_MM_DD, DateUtil.YYYY_MM_DD);
                System.out.println("task【19】【" + Thread.currentThread().getId() + "】formatLocalDateTime: " + YYYY_MM_DD + "  ~~  " + DateUtil.format(result, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));

                String HHMMDD = DateUtil.format(localDateTime, DateUtil.HHMMDD);
                result = DateUtil.formatLocalDateTime(HHMMDD, DateUtil.HHMMDD);
                System.out.println("task【19】【" + Thread.currentThread().getId() + "】formatLocalDateTime: " + HHMMDD + "  ~~  " + DateUtil.format(result, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));

                String HH_MM_DD = DateUtil.format(localDateTime, DateUtil.HH_MM_DD);
                result = DateUtil.formatLocalDateTime(HH_MM_DD, DateUtil.HH_MM_DD);
                System.out.println("task【19】【" + Thread.currentThread().getId() + "】formatLocalDateTime: " + HH_MM_DD + "  ~~  " + DateUtil.format(result, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 反格式化LocalDateTime
     */
    @Test
    public void formatLocalDateTime() throws InterruptedException {
        for (int i = 1; i < LOCAL_DATE_TIME_LIST.size(); i++) {
            EXECUTOR.submit(new FormatLocalDateTimeRunnable(LOCAL_DATE_TIME_LIST.get(i)));
        }
    }

    public class FormatLocalDateRunnable implements Runnable {
        private LocalDate localDate;

        public FormatLocalDateRunnable(LocalDate localDate) {
            this.localDate = localDate;
        }

        @Override
        public void run() {
            LocalDate result;
            try {
                String YYYY_MM_DD_HH_MM_SS_SSS = DateUtil.format(localDate, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS);
                result = DateUtil.formatLocalDate(YYYY_MM_DD_HH_MM_SS_SSS, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS);
                System.out.println("task【20】【" + Thread.currentThread().getId() + "】formatInstant: " + YYYY_MM_DD_HH_MM_SS_SSS + "  ~~  " + DateUtil.format(result, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                String YYYY_MM_DD_HH_MM_SS = DateUtil.format(localDate, DateUtil.YYYY_MM_DD_HH_MM_SS);
                result = DateUtil.formatLocalDate(YYYY_MM_DD_HH_MM_SS, DateUtil.YYYY_MM_DD_HH_MM_SS);
                System.out.println("task【20】【" + Thread.currentThread().getId() + "】formatLocalDate" + YYYY_MM_DD_HH_MM_SS + "  ~~  " + DateUtil.format(result, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                String YYYYMMDDHHMMSS = DateUtil.format(localDate, DateUtil.YYYYMMDDHHMMSS);
                result = DateUtil.formatLocalDate(YYYYMMDDHHMMSS, DateUtil.YYYYMMDDHHMMSS);
                System.out.println("task【20】【" + Thread.currentThread().getId() + "】formatLocalDate: " + YYYYMMDDHHMMSS + "  ~~  " + DateUtil.format(result, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                String YYYYMMDD = DateUtil.format(localDate, DateUtil.YYYYMMDD);
                result = DateUtil.formatLocalDate(YYYYMMDD, DateUtil.YYYYMMDD);
                System.out.println("task【20】【" + Thread.currentThread().getId() + "】formatLocalDate: " + YYYYMMDD + "  ~~  " + DateUtil.format(result, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                String YYYY_MM_DD = DateUtil.format(localDate, DateUtil.YYYY_MM_DD);
                result = DateUtil.formatLocalDate(YYYY_MM_DD, DateUtil.YYYY_MM_DD);
                System.out.println("task【20】【" + Thread.currentThread().getId() + "】formatLocalDate: " + YYYY_MM_DD + "  ~~  " + DateUtil.format(result, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                String HH_MM_DD = DateUtil.format(localDate, DateUtil.HH_MM_DD);
                result = DateUtil.formatLocalDate(HH_MM_DD, DateUtil.HH_MM_DD);
                System.out.println("task【20】【" + Thread.currentThread().getId() + "】formatLocalDate: " + HH_MM_DD + "  ~~  " + DateUtil.format(result, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));

            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                String HHMMDD = DateUtil.format(localDate, DateUtil.HHMMDD);
                result = DateUtil.formatLocalDate(HHMMDD, DateUtil.HHMMDD);
                System.out.println("task【20】【" + Thread.currentThread().getId() + "】formatLocalDate: " + HHMMDD + "  ~~  " + DateUtil.format(result, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 反格式化LocalDate
     */
    @Test
    public void formatLocalDate() {
        for (int i = 1; i < LOCAL_DATE_LIST.size(); i++) {
            EXECUTOR.submit(new FormatLocalDateRunnable(LOCAL_DATE_LIST.get(i)));
        }
    }

    public class FormatLocalTimeRunnable implements Runnable {
        private LocalTime localTime;

        public FormatLocalTimeRunnable(LocalTime localTime) {
            this.localTime = localTime;
        }

        @Override
        public void run() {
            LocalTime result;
            try {
                String YYYY_MM_DD_HH_MM_SS_SSS = DateUtil.format(localTime, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS);
                result = DateUtil.formatLocalTime(YYYY_MM_DD_HH_MM_SS_SSS, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS);
                System.out.println("task【20】【" + Thread.currentThread().getId() + "】formatInstant: " + YYYY_MM_DD_HH_MM_SS_SSS + "  ~~  " + DateUtil.format(result, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                String YYYY_MM_DD_HH_MM_SS = DateUtil.format(localTime, DateUtil.YYYY_MM_DD_HH_MM_SS);
                result = DateUtil.formatLocalTime(YYYY_MM_DD_HH_MM_SS, DateUtil.YYYY_MM_DD_HH_MM_SS);
                System.out.println("task【20】【" + Thread.currentThread().getId() + "】formatLocalDate" + YYYY_MM_DD_HH_MM_SS + "  ~~  " + DateUtil.format(result, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                String YYYYMMDDHHMMSS = DateUtil.format(localTime, DateUtil.YYYYMMDDHHMMSS);
                result = DateUtil.formatLocalTime(YYYYMMDDHHMMSS, DateUtil.YYYYMMDDHHMMSS);
                System.out.println("task【20】【" + Thread.currentThread().getId() + "】formatLocalDate: " + YYYYMMDDHHMMSS + "  ~~  " + DateUtil.format(result, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {

                String HH_MM_DD = DateUtil.format(localTime, DateUtil.HH_MM_DD);
                result = DateUtil.formatLocalTime(HH_MM_DD, DateUtil.HH_MM_DD);
                System.out.println("task【20】【" + Thread.currentThread().getId() + "】formatLocalDate: " + HH_MM_DD + "  ~~  " + DateUtil.format(result, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                String HHMMDD = DateUtil.format(localTime, DateUtil.HHMMDD);
                result = DateUtil.formatLocalTime(HHMMDD, DateUtil.HHMMDD);
                System.out.println("task【20】【" + Thread.currentThread().getId() + "】formatLocalDate: " + HHMMDD + "  ~~  " + DateUtil.format(result, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));


            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                String YYYYMMDD = DateUtil.format(localTime, DateUtil.YYYYMMDD);
                result = DateUtil.formatLocalTime(YYYYMMDD, DateUtil.YYYYMMDD);
                System.out.println("task【20】【" + Thread.currentThread().getId() + "】formatLocalDate: " + YYYYMMDD + "  ~~  " + DateUtil.format(result, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));

            } catch (Exception e) {
                e.printStackTrace();
            }
            try {

                String YYYY_MM_DD = DateUtil.format(localTime, DateUtil.YYYY_MM_DD);
                result = DateUtil.formatLocalTime(YYYY_MM_DD, DateUtil.YYYY_MM_DD);
                System.out.println("task【20】【" + Thread.currentThread().getId() + "】formatLocalDate: " + YYYY_MM_DD + "  ~~  " + DateUtil.format(result, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 反格式化LocalTime
     */
    @Test
    public void formatLocalTime() {
        for (int i = 1; i < LOCAL_TIME_LIST.size(); i++) {
            EXECUTOR.submit(new FormatLocalTimeRunnable(LOCAL_TIME_LIST.get(i)));
        }
    }

    public class GetYearDateRunnable implements Runnable {
        private Date date;

        public GetYearDateRunnable(Date date) {
            this.date = date;
        }

        @Override
        public void run() {
            try {
                System.out.println("task【21】【" + Thread.currentThread().getId() + "】getYearDate: " + DateUtil.getYear(date) + "  ~~  " + DateUtil.format(date, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 从java.util.Date获得年份
     */
    @Test
    public void getYearDate() {
        for (int i = 1; i < DATE_OFFSET_LIST.size(); i++) {
            EXECUTOR.submit(new GetYearDateRunnable(DATE_OFFSET_LIST.get(i)));
        }
    }

    public class GetYearInstantRunnable implements Runnable {
        private Instant instant;

        public GetYearInstantRunnable(Instant instant) {
            this.instant = instant;
        }

        @Override
        public void run() {
            try {
                System.out.println("task【21】【" + Thread.currentThread().getId() + "】GetYearInstant: " + DateUtil.getYear(instant) + "  ~~  " + DateUtil.format(instant, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 从Instant获得年份
     */
    @Test
    public void getYearInstant() {
        for (int i = 1; i < INSTANT_OFFSET_LIST.size(); i++) {
            EXECUTOR.submit(new GetYearInstantRunnable(INSTANT_OFFSET_LIST.get(i)));
        }
    }

    public class GetYearLocalDateTimeRunnable implements Runnable {
        private LocalDateTime localDateTime;

        public GetYearLocalDateTimeRunnable(LocalDateTime localDateTime) {
            this.localDateTime = localDateTime;
        }

        @Override
        public void run() {
            try {
                System.out.println("task【21】【" + Thread.currentThread().getId() + "】LocalDateTime: " + DateUtil.getYear(localDateTime) + "  ~~  " + DateUtil.format(localDateTime, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 从LocalDateTime获得年份
     */
    @Test
    public void getYearLocalDateTime() {
        for (int i = 1; i < LOCAL_DATE_TIME_OFFSET_LIST.size(); i++) {
            EXECUTOR.submit(new GetYearLocalDateTimeRunnable(LOCAL_DATE_TIME_OFFSET_LIST.get(i)));
        }
    }

    public class GetYearLocalDateRunnable implements Runnable {
        private LocalDate localDate;

        public GetYearLocalDateRunnable(LocalDate localDate) {
            this.localDate = localDate;
        }

        @Override
        public void run() {
            try {
                System.out.println("task【21】【" + Thread.currentThread().getId() + "】getYearLocalDate: " + DateUtil.getYear(localDate) + "  ~~  " + DateUtil.format(localDate, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 从LocalDate获得年份
     */
    @Test
    public void getYearLocalDate() {
        for (int i = 1; i < LOCAL_DATE_OFFSET_LIST.size(); i++) {
            EXECUTOR.submit(new GetYearLocalDateRunnable(LOCAL_DATE_OFFSET_LIST.get(i)));
        }
    }

    public class GetMonthDateRunnable implements Runnable {
        private Date date;

        public GetMonthDateRunnable(Date date) {
            this.date = date;
        }

        @Override
        public void run() {
            try {
                System.out.println("task【21】【" + Thread.currentThread().getId() + "】GetMonthDateRunnable: " + DateUtil.getMonth(date) + "  ~~  " + DateUtil.format(date, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 从java.util.Date获得月份
     */
    @Test
    public void getMonthDate() {
        for (int i = 1; i < DATE_OFFSET_LIST.size(); i++) {
            EXECUTOR.submit(new GetMonthDateRunnable(DATE_OFFSET_LIST.get(i)));
        }
    }

    public class GetMonthInstantRunnable implements Runnable {
        private Instant instant;

        public GetMonthInstantRunnable(Instant instant) {
            this.instant = instant;
        }

        @Override
        public void run() {
            try {
                System.out.println("task【21】【" + Thread.currentThread().getId() + "】GetMonthInstantRunnable: " + DateUtil.getMonth(instant) + "  ~~  " + DateUtil.format(instant, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 从Instant获得月份
     */
    @Test
    public void getMonthInstant() {
        for (int i = 1; i < INSTANT_OFFSET_LIST.size(); i++) {
            EXECUTOR.submit(new GetMonthInstantRunnable(INSTANT_OFFSET_LIST.get(i)));
        }
    }

    public class GetMonthLocalDateTimeRunnable implements Runnable {
        private LocalDateTime localDateTime;

        public GetMonthLocalDateTimeRunnable(LocalDateTime localDateTime) {
            this.localDateTime = localDateTime;
        }

        @Override
        public void run() {
            try {
                System.out.println("task【21】【" + Thread.currentThread().getId() + "】GetMonthInstantRunnable: " + DateUtil.getMonth(localDateTime) + "  ~~  " + DateUtil.format(localDateTime, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 从LocalDateTime获得月份
     */
    @Test
    public void getMonthLocalDateTime() {
        for (int i = 1; i < LOCAL_DATE_TIME_OFFSET_LIST.size(); i++) {
            EXECUTOR.submit(new GetMonthLocalDateTimeRunnable(LOCAL_DATE_TIME_OFFSET_LIST.get(i)));
        }
    }

    public class GetMonthLocalDateRunnable implements Runnable {
        private LocalDate localDate;

        public GetMonthLocalDateRunnable(LocalDate localDate) {
            this.localDate = localDate;
        }

        @Override
        public void run() {
            try {
                System.out.println("task【21】【" + Thread.currentThread().getId() + "】getMonthLocalDate: " + DateUtil.getMonth(localDate) + "  ~~  " + DateUtil.format(localDate, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 从LocalDate获得月份
     */
    @Test
    public void getMonthLocalDate() {
        for (int i = 1; i < LOCAL_DATE_OFFSET_LIST.size(); i++) {
            EXECUTOR.submit(new GetMonthLocalDateRunnable(LOCAL_DATE_OFFSET_LIST.get(i)));
        }
    }

    public class GetDayOfMonthDateRunnable implements Runnable {
        private Date date;

        public GetDayOfMonthDateRunnable(Date date) {
            this.date = date;
        }

        @Override
        public void run() {
            try {
                System.out.println("task【21】【" + Thread.currentThread().getId() + "】getDayOfMonthDate: " + DateUtil.getDayOfMonth(date) + "  ~~  " + DateUtil.format(date, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 从java.util.Date获得是当月几号
     */
    @Test
    public void getDayOfMonthDate() {
        for (int i = 1; i < DATE_OFFSET_LIST.size(); i++) {
            EXECUTOR.submit(new GetDayOfMonthDateRunnable(DATE_OFFSET_LIST.get(i)));
        }
    }

    public class GetDayOfMonthInstantRunnable implements Runnable {
        private Instant instant;

        public GetDayOfMonthInstantRunnable(Instant instant) {
            this.instant = instant;
        }

        @Override
        public void run() {
            try {
                System.out.println("task【21】【" + Thread.currentThread().getId() + "】getDayOfMonthInstant: " + DateUtil.getDayOfMonth(instant) + "  ~~  " + DateUtil.format(instant, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 从Instant获得是当月几号
     */
    @Test
    public void getDayOfMonthInstant() {
        for (int i = 1; i < INSTANT_OFFSET_LIST.size(); i++) {
            EXECUTOR.submit(new GetDayOfMonthInstantRunnable(INSTANT_OFFSET_LIST.get(i)));
        }
    }

    public class GetDayOfMonthLocalDateTimeRunnable implements Runnable {
        private LocalDateTime localDateTime;

        public GetDayOfMonthLocalDateTimeRunnable(LocalDateTime localDateTime) {
            this.localDateTime = localDateTime;
        }

        @Override
        public void run() {
            try {
                System.out.println("task【21】【" + Thread.currentThread().getId() + "】getDayOfMonthLocalDateTime: " + DateUtil.getDayOfMonth(localDateTime) + "  ~~  " + DateUtil.format(localDateTime, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 从LocalDateTime获得是当月几号
     */
    @Test
    public void getDayOfMonthLocalDateTime() {
        for (int i = 1; i < LOCAL_DATE_TIME_OFFSET_LIST.size(); i++) {
            EXECUTOR.submit(new GetDayOfMonthLocalDateTimeRunnable(LOCAL_DATE_TIME_OFFSET_LIST.get(i)));
        }
    }

    public class GetDayOfMonthLocalDateRunnable implements Runnable {
        private LocalDate localDate;

        public GetDayOfMonthLocalDateRunnable(LocalDate localDate) {
            this.localDate = localDate;
        }

        @Override
        public void run() {
            try {
                System.out.println("task【21】【" + Thread.currentThread().getId() + "】getDayOfMonthLocalDateTime: " + DateUtil.getDayOfMonth(localDate) + "  ~~  " + DateUtil.format(localDate, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 从LocalDate获得是当月几号
     */
    @Test
    public void getDayOfMonthLocalDate() {
        for (int i = 1; i < LOCAL_DATE_OFFSET_LIST.size(); i++) {
            EXECUTOR.submit(new GetDayOfMonthLocalDateRunnable(LOCAL_DATE_OFFSET_LIST.get(i)));
        }
    }

    public class GetHourDateRunnable implements Runnable {
        private Date date;

        public GetHourDateRunnable(Date date) {
            this.date = date;
        }

        @Override
        public void run() {
            try {
                System.out.println("task【21】【" + Thread.currentThread().getId() + "】getHourDate: " + DateUtil.getHour(date) + "  ~~  " + DateUtil.format(date, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 从java.util.Date获取小时
     */
    @Test
    public void getHourDate() {
        for (int i = 1; i < DATE_OFFSET_LIST.size(); i++) {
            EXECUTOR.submit(new GetHourDateRunnable(DATE_OFFSET_LIST.get(i)));
        }
    }

    public class GetHourInstantRunnable implements Runnable {
        private Instant instant;

        public GetHourInstantRunnable(Instant instant) {
            this.instant = instant;
        }

        @Override
        public void run() {
            try {
                System.out.println("task【21】【" + Thread.currentThread().getId() + "】getHourInstant: " + DateUtil.getHour(instant) + "  ~~  " + DateUtil.format(instant, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 从Instant获取小时
     */
    @Test
    public void getHourInstant() {
        for (int i = 1; i < INSTANT_OFFSET_LIST.size(); i++) {
            EXECUTOR.submit(new GetHourInstantRunnable(INSTANT_OFFSET_LIST.get(i)));
        }
    }

    public class GetHourLocalDateTimeRunnable implements Runnable {
        private LocalDateTime localDateTime;

        public GetHourLocalDateTimeRunnable(LocalDateTime localDateTime) {
            this.localDateTime = localDateTime;
        }

        @Override
        public void run() {
            try {
                System.out.println("task【21】【" + Thread.currentThread().getId() + "】getHourLocalDateTime: " + DateUtil.getHour(localDateTime) + "  ~~  " + DateUtil.format(localDateTime, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 从LocalDateTime获取小时
     */
    @Test
    public void getHourLocalDateTime() {
        for (int i = 1; i < LOCAL_DATE_TIME_OFFSET_LIST.size(); i++) {
            EXECUTOR.submit(new GetHourLocalDateTimeRunnable(LOCAL_DATE_TIME_OFFSET_LIST.get(i)));
        }
    }

    public class GetHourLocalTimeRunnable implements Runnable {
        private LocalTime localTime;

        public GetHourLocalTimeRunnable(LocalTime localTime) {
            this.localTime = localTime;
        }

        @Override
        public void run() {
            try {
                System.out.println("task【21】【" + Thread.currentThread().getId() + "】getHourLocalTime: " + DateUtil.getHour(localTime) + "  ~~  " + DateUtil.format(localTime, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 从LocalTime获取小时
     */
    @Test
    public void getHourLocalTime() {
        for (int i = 1; i < LOCAL_TIME_OFFSET_LIST.size(); i++) {
            EXECUTOR.submit(new GetHourLocalTimeRunnable(LOCAL_TIME_OFFSET_LIST.get(i)));
        }
    }

    public class GetMinuteDateRunnable implements Runnable {
        private Date date;

        public GetMinuteDateRunnable(Date date) {
            this.date = date;
        }

        @Override
        public void run() {
            try {
                System.out.println("task【21】【" + Thread.currentThread().getId() + "】getMinuteDate: " + DateUtil.getMinute(date) + "  ~~  " + DateUtil.format(date, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 从java.util.Date获取分钟
     */
    @Test
    public void getMinuteDate() {
        for (int i = 1; i < DATE_OFFSET_LIST.size(); i++) {
            EXECUTOR.submit(new GetMinuteDateRunnable(DATE_OFFSET_LIST.get(i)));
        }
    }

    public class GetMinuteInstantRunnable implements Runnable {
        private Instant instant;

        public GetMinuteInstantRunnable(Instant instant) {
            this.instant = instant;
        }

        @Override
        public void run() {
            try {
                System.out.println("task【21】【" + Thread.currentThread().getId() + "】getMinuteDate: " + DateUtil.getMinute(instant) + "  ~~  " + DateUtil.format(instant, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 从Instant获取分钟
     */
    @Test
    public void getMinuteInstant() {
        for (int i = 1; i < INSTANT_OFFSET_LIST.size(); i++) {
            EXECUTOR.submit(new GetMinuteInstantRunnable(INSTANT_OFFSET_LIST.get(i)));
        }
    }

    public class GetMinuteLocalDateTimeRunnable implements Runnable {
        private LocalDateTime localDateTime;

        public GetMinuteLocalDateTimeRunnable(LocalDateTime localDateTime) {
            this.localDateTime = localDateTime;
        }

        @Override
        public void run() {
            try {
                System.out.println("task【21】【" + Thread.currentThread().getId() + "】getMinuteLocalDateTime: " + DateUtil.getMinute(localDateTime) + "  ~~  " + DateUtil.format(localDateTime, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 从LocalDateTime获取分钟
     */
    @Test
    public void getMinuteLocalDateTime() {
        for (int i = 1; i < LOCAL_DATE_TIME_OFFSET_LIST.size(); i++) {
            EXECUTOR.submit(new GetMinuteLocalDateTimeRunnable(LOCAL_DATE_TIME_OFFSET_LIST.get(i)));
        }
    }

    public class GetMinuteLocalTimeRunnable implements Runnable {
        private LocalTime localTime;

        public GetMinuteLocalTimeRunnable(LocalTime localTime) {
            this.localTime = localTime;
        }

        @Override
        public void run() {
            try {
                System.out.println("task【21】【" + Thread.currentThread().getId() + "】getMinuteLocalTime: " + DateUtil.getMinute(localTime) + "  ~~  " + DateUtil.format(localTime, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 从LocalTime获取分钟
     */
    @Test
    public void getMinuteLocalTime() {
        for (int i = 1; i < LOCAL_TIME_OFFSET_LIST.size(); i++) {
            EXECUTOR.submit(new GetMinuteLocalTimeRunnable(LOCAL_TIME_OFFSET_LIST.get(i)));
        }
    }

    public class GetSecondDateRunnable implements Runnable {
        private Date date;

        public GetSecondDateRunnable(Date date) {
            this.date = date;
        }

        @Override
        public void run() {
            try {
                System.out.println("task【21】【" + Thread.currentThread().getId() + "】getSecondDate: " + DateUtil.getSecond(date) + "  ~~  " + DateUtil.format(date, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 从java.util.Date获取秒数
     */
    @Test
    public void getSecondDate() {
        for (int i = 1; i < DATE_OFFSET_LIST.size(); i++) {
            EXECUTOR.submit(new GetSecondDateRunnable(DATE_OFFSET_LIST.get(i)));
        }
    }

    public class GetSecondInstantRunnable implements Runnable {
        private Instant instant;

        public GetSecondInstantRunnable(Instant instant) {
            this.instant = instant;
        }

        @Override
        public void run() {
            try {
                System.out.println("task【21】【" + Thread.currentThread().getId() + "】getSecondInstant: " + DateUtil.getSecond(instant) + "  ~~  " + DateUtil.format(instant, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 从Instant获取秒数
     */
    @Test
    public void getSecondInstant() {
        for (int i = 1; i < INSTANT_OFFSET_LIST.size(); i++) {
            EXECUTOR.submit(new GetSecondInstantRunnable(INSTANT_OFFSET_LIST.get(i)));
        }
    }

    public class GetSecondLocalDateTimeRunnable implements Runnable {
        private LocalDateTime localDateTime;

        public GetSecondLocalDateTimeRunnable(LocalDateTime localDateTime) {
            this.localDateTime = localDateTime;
        }

        @Override
        public void run() {
            try {
                System.out.println("task【21】【" + Thread.currentThread().getId() + "】getSecondDate: " + DateUtil.getSecond(localDateTime) + "  ~~  " + DateUtil.format(localDateTime, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 从LocalDateTime获取秒数
     */
    @Test
    public void getSecondLocalDateTime() {
        for (int i = 1; i < LOCAL_DATE_TIME_OFFSET_LIST.size(); i++) {
            EXECUTOR.submit(new GetSecondLocalDateTimeRunnable(LOCAL_DATE_TIME_OFFSET_LIST.get(i)));
        }
    }

    public class GetSecondLocalTimeRunnable implements Runnable {
        private LocalTime localTime;

        public GetSecondLocalTimeRunnable(LocalTime localTime) {
            this.localTime = localTime;
        }

        @Override
        public void run() {
            try {
                System.out.println("task【21】【" + Thread.currentThread().getId() + "】getSecondLocalTime: " + DateUtil.getSecond(localTime) + "  ~~  " + DateUtil.format(localTime, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 从LocalTime获取秒数
     */
    @Test
    public void getSecondLocalTime() {
        for (int i = 1; i < LOCAL_TIME_OFFSET_LIST.size(); i++) {
            EXECUTOR.submit(new GetSecondLocalTimeRunnable(LOCAL_TIME_OFFSET_LIST.get(i)));
        }
    }

    public class BetweenDateRunnable implements Runnable {

        private Date source;

        private Date target;


        public BetweenDateRunnable(Date source, Date target) {
            this.source = source;
            this.target = target;
        }

        @Override
        public void run() {
            try {
                System.out.println("task【21】【" + Thread.currentThread().getId() + "】betweenDate: " + DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " ~~ " + DateUtil.format(target, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " = " + DateUtil.between(source, target, TimeUnit.DAYS) + "日");
                System.out.println("task【21】【" + Thread.currentThread().getId() + "】betweenDate: " + DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " ~~ " + DateUtil.format(target, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " = " + DateUtil.between(source, target, TimeUnit.HOURS) + "小时");
                System.out.println("task【21】【" + Thread.currentThread().getId() + "】betweenDate: " + DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " ~~ " + DateUtil.format(target, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " = " + DateUtil.between(source, target, TimeUnit.MINUTES) + "分钟");
                System.out.println("task【21】【" + Thread.currentThread().getId() + "】betweenDate: " + DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " ~~ " + DateUtil.format(target, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " = " + DateUtil.between(source, target, TimeUnit.SECONDS) + "秒");
                System.out.println("task【21】【" + Thread.currentThread().getId() + "】betweenDate: " + DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " ~~ " + DateUtil.format(target, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " = " + DateUtil.between(source, target, TimeUnit.MILLISECONDS) + "毫秒");
                System.out.println("task【21】【" + Thread.currentThread().getId() + "】betweenDate: " + DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " ~~ " + DateUtil.format(target, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " = " + DateUtil.between(source, target, TimeUnit.MICROSECONDS) + "微秒");
                System.out.println("task【21】【" + Thread.currentThread().getId() + "】betweenDate: " + DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " ~~ " + DateUtil.format(target, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " = " + DateUtil.between(source, target, TimeUnit.NANOSECONDS) + "纳秒");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取两个日期的差值
     */
    @Test
    public void betweenDate() {
        for (int i = 0; i < DATE_BETWEEN_LIST.size() - 1; i++) {
            EXECUTOR.submit(new BetweenDateRunnable(DATE_BETWEEN_LIST.get(i), DATE_BETWEEN_LIST.get(i + 1)));
        }
    }

    public class BetweenInstantRunnable implements Runnable {

        private Instant source;

        private Instant target;


        public BetweenInstantRunnable(Instant source, Instant target) {
            this.source = source;
            this.target = target;
        }

        @Override
        public void run() {
            try {
                System.out.println("task【21】【" + Thread.currentThread().getId() + "】betweenInstant: " + DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " ~~ " + DateUtil.format(target, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " = " + DateUtil.between(source, target, TimeUnit.DAYS) + "日");
                System.out.println("task【21】【" + Thread.currentThread().getId() + "】betweenInstant: " + DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " ~~ " + DateUtil.format(target, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " = " + DateUtil.between(source, target, TimeUnit.HOURS) + "小时");
                System.out.println("task【21】【" + Thread.currentThread().getId() + "】betweenInstant: " + DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " ~~ " + DateUtil.format(target, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " = " + DateUtil.between(source, target, TimeUnit.MINUTES) + "分钟");
                System.out.println("task【21】【" + Thread.currentThread().getId() + "】betweenInstant: " + DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " ~~ " + DateUtil.format(target, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " = " + DateUtil.between(source, target, TimeUnit.SECONDS) + "秒");
                System.out.println("task【21】【" + Thread.currentThread().getId() + "】betweenInstant: " + DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " ~~ " + DateUtil.format(target, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " = " + DateUtil.between(source, target, TimeUnit.MILLISECONDS) + "毫秒");
                System.out.println("task【21】【" + Thread.currentThread().getId() + "】betweenInstant: " + DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " ~~ " + DateUtil.format(target, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " = " + DateUtil.between(source, target, TimeUnit.MICROSECONDS) + "微秒");
                System.out.println("task【21】【" + Thread.currentThread().getId() + "】betweenInstant: " + DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " ~~ " + DateUtil.format(target, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " = " + DateUtil.between(source, target, TimeUnit.NANOSECONDS) + "纳秒");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取两个日期的差值
     */
    @Test
    public void betweenInstant() {
        for (int i = 0; i < INSTANT_BETWEEN_LIST.size() - 1; i++) {
            EXECUTOR.submit(new BetweenInstantRunnable(INSTANT_BETWEEN_LIST.get(i), INSTANT_BETWEEN_LIST.get(i + 1)));
        }
    }

    public class BetweenLocalDateTimeRunnable implements Runnable {

        private LocalDateTime source;

        private LocalDateTime target;


        public BetweenLocalDateTimeRunnable(LocalDateTime source, LocalDateTime target) {
            this.source = source;
            this.target = target;
        }

        @Override
        public void run() {
            try {
                System.out.println("task【21】【" + Thread.currentThread().getId() + "】betweenLocalDateTime: " + DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " ~~ " + DateUtil.format(target, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " = " + DateUtil.between(source, target, TimeUnit.DAYS) + "日");
                System.out.println("task【21】【" + Thread.currentThread().getId() + "】betweenLocalDateTime: " + DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " ~~ " + DateUtil.format(target, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " = " + DateUtil.between(source, target, TimeUnit.HOURS) + "小时");
                System.out.println("task【21】【" + Thread.currentThread().getId() + "】betweenLocalDateTime: " + DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " ~~ " + DateUtil.format(target, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " = " + DateUtil.between(source, target, TimeUnit.MINUTES) + "分钟");
                System.out.println("task【21】【" + Thread.currentThread().getId() + "】betweenLocalDateTime: " + DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " ~~ " + DateUtil.format(target, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " = " + DateUtil.between(source, target, TimeUnit.SECONDS) + "秒");
                System.out.println("task【21】【" + Thread.currentThread().getId() + "】betweenLocalDateTime: " + DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " ~~ " + DateUtil.format(target, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " = " + DateUtil.between(source, target, TimeUnit.MILLISECONDS) + "毫秒");
                System.out.println("task【21】【" + Thread.currentThread().getId() + "】betweenLocalDateTime: " + DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " ~~ " + DateUtil.format(target, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " = " + DateUtil.between(source, target, TimeUnit.MICROSECONDS) + "微秒");
                System.out.println("task【21】【" + Thread.currentThread().getId() + "】betweenLocalDateTime: " + DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " ~~ " + DateUtil.format(target, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " = " + DateUtil.between(source, target, TimeUnit.NANOSECONDS) + "纳秒");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取两个日期的差值
     */
    @Test
    public void betweenLocalDateTime() {
        for (int i = 0; i < LOCAL_DATE_TIME_BETWEEN_LIST.size() - 1; i++) {
            EXECUTOR.submit(new BetweenLocalDateTimeRunnable(LOCAL_DATE_TIME_BETWEEN_LIST.get(i), LOCAL_DATE_TIME_BETWEEN_LIST.get(i + 1)));
        }
    }

    public class BetweenLocalDateRunnable implements Runnable {

        private LocalDate source;

        private LocalDate target;


        public BetweenLocalDateRunnable(LocalDate source, LocalDate target) {
            this.source = source;
            this.target = target;
        }

        @Override
        public void run() {
            try {
                System.out.println("task【21】【" + Thread.currentThread().getId() + "】betweenLocalDate: " + DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " ~~ " + DateUtil.format(target, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " = " + DateUtil.between(source, target, TimeUnit.DAYS) + "日");
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                System.out.println("task【21】【" + Thread.currentThread().getId() + "】betweenLocalDate: " + DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " ~~ " + DateUtil.format(target, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " = " + DateUtil.between(source, target, TimeUnit.HOURS) + "小时");
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                System.out.println("task【21】【" + Thread.currentThread().getId() + "】betweenLocalDate: " + DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " ~~ " + DateUtil.format(target, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " = " + DateUtil.between(source, target, TimeUnit.MINUTES) + "分钟");

            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                System.out.println("task【21】【" + Thread.currentThread().getId() + "】betweenLocalDate: " + DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " ~~ " + DateUtil.format(target, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " = " + DateUtil.between(source, target, TimeUnit.SECONDS) + "秒");
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                System.out.println("task【21】【" + Thread.currentThread().getId() + "】betweenLocalDate: " + DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " ~~ " + DateUtil.format(target, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " = " + DateUtil.between(source, target, TimeUnit.MILLISECONDS) + "毫秒");
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                System.out.println("task【21】【" + Thread.currentThread().getId() + "】betweenLocalDate: " + DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " ~~ " + DateUtil.format(target, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " = " + DateUtil.between(source, target, TimeUnit.MICROSECONDS) + "微秒");
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {

                System.out.println("task【21】【" + Thread.currentThread().getId() + "】betweenLocalDate: " + DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " ~~ " + DateUtil.format(target, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " = " + DateUtil.between(source, target, TimeUnit.NANOSECONDS) + "纳秒");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取两个日期的差值
     */
    @Test
    public void betweenLocalDate() {
        for (int i = 0; i < LOCAL_DATE_BETWEEN_LIST.size() - 1; i++) {
            EXECUTOR.submit(new BetweenLocalDateRunnable(LOCAL_DATE_BETWEEN_LIST.get(i), LOCAL_DATE_BETWEEN_LIST.get(i + 1)));
        }
    }

    public class BetweenLocalTimeRunnable implements Runnable {

        private LocalTime source;

        private LocalTime target;


        public BetweenLocalTimeRunnable(LocalTime source, LocalTime target) {
            this.source = source;
            this.target = target;
        }

        @Override
        public void run() {
            try {
                System.out.println("task【21】【" + Thread.currentThread().getId() + "】betweenLocalTime: " + DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " ~~ " + DateUtil.format(target, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " = " + DateUtil.between(source, target, TimeUnit.DAYS) + "日");
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                System.out.println("task【21】【" + Thread.currentThread().getId() + "】betweenLocalTime: " + DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " ~~ " + DateUtil.format(target, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " = " + DateUtil.between(source, target, TimeUnit.HOURS) + "小时");
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                System.out.println("task【21】【" + Thread.currentThread().getId() + "】betweenLocalTime: " + DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " ~~ " + DateUtil.format(target, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " = " + DateUtil.between(source, target, TimeUnit.MINUTES) + "分钟");
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                System.out.println("task【21】【" + Thread.currentThread().getId() + "】betweenLocalTime: " + DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " ~~ " + DateUtil.format(target, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " = " + DateUtil.between(source, target, TimeUnit.SECONDS) + "秒");
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                System.out.println("task【21】【" + Thread.currentThread().getId() + "】betweenLocalTime: " + DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " ~~ " + DateUtil.format(target, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " = " + DateUtil.between(source, target, TimeUnit.MILLISECONDS) + "毫秒");
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                System.out.println("task【21】【" + Thread.currentThread().getId() + "】betweenLocalTime: " + DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " ~~ " + DateUtil.format(target, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " = " + DateUtil.between(source, target, TimeUnit.MICROSECONDS) + "微秒");
                System.out.println("task【21】【" + Thread.currentThread().getId() + "】betweenLocalTime: " + DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " ~~ " + DateUtil.format(target, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " = " + DateUtil.between(source, target, TimeUnit.NANOSECONDS) + "纳秒");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取两个日期的差值
     */
    @Test
    public void betweenLocalTime() {
        for (int i = 0; i < LOCAL_TIME_BETWEEN_LIST.size() - 1; i++) {
            EXECUTOR.submit(new BetweenLocalTimeRunnable(LOCAL_TIME_BETWEEN_LIST.get(i), LOCAL_TIME_BETWEEN_LIST.get(i + 1)));
        }
    }

    public class BetweenNowDateRunnable implements Runnable {

        private Date source;

        private Date target;


        public BetweenNowDateRunnable(Date source) {
            this.source = source;
            this.target = new Date();
        }

        @Override
        public void run() {
            try {
                System.out.println("task【21】【" + Thread.currentThread().getId() + "】betweenDate: " + DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " ~~ " + DateUtil.format(target, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " = " + DateUtil.between(source, target, TimeUnit.DAYS) + "日");
                System.out.println("task【21】【" + Thread.currentThread().getId() + "】betweenDate: " + DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " ~~ " + DateUtil.format(target, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " = " + DateUtil.between(source, target, TimeUnit.HOURS) + "小时");
                System.out.println("task【21】【" + Thread.currentThread().getId() + "】betweenDate: " + DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " ~~ " + DateUtil.format(target, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " = " + DateUtil.between(source, target, TimeUnit.MINUTES) + "分钟");
                System.out.println("task【21】【" + Thread.currentThread().getId() + "】betweenDate: " + DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " ~~ " + DateUtil.format(target, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " = " + DateUtil.between(source, target, TimeUnit.SECONDS) + "秒");
                System.out.println("task【21】【" + Thread.currentThread().getId() + "】betweenDate: " + DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " ~~ " + DateUtil.format(target, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " = " + DateUtil.between(source, target, TimeUnit.MILLISECONDS) + "毫秒");
                System.out.println("task【21】【" + Thread.currentThread().getId() + "】betweenDate: " + DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " ~~ " + DateUtil.format(target, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " = " + DateUtil.between(source, target, TimeUnit.MICROSECONDS) + "微秒");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取日期距离当前的差值
     */
    @Test
    public void betweenNowDate() {
        for (int i = 1; i < DATE_BETWEEN_LIST.size(); i++) {
            EXECUTOR.submit(new BetweenNowDateRunnable(DATE_BETWEEN_LIST.get(i)));
        }
    }

    public class BetweenNowInstantRunnable implements Runnable {

        private Instant source;

        private Instant target;


        public BetweenNowInstantRunnable(Instant source) {
            this.source = source;
            this.target = Instant.now();
        }

        @Override
        public void run() {
            try {
                System.out.println("task【21】【" + Thread.currentThread().getId() + "】betweenNowInstant: " + DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " ~~ " + DateUtil.format(target, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " = " + DateUtil.between(source, target, TimeUnit.DAYS) + "日");
                System.out.println("task【21】【" + Thread.currentThread().getId() + "】betweenNowInstant: " + DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " ~~ " + DateUtil.format(target, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " = " + DateUtil.between(source, target, TimeUnit.HOURS) + "小时");
                System.out.println("task【21】【" + Thread.currentThread().getId() + "】betweenNowInstant: " + DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " ~~ " + DateUtil.format(target, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " = " + DateUtil.between(source, target, TimeUnit.MINUTES) + "分钟");
                System.out.println("task【21】【" + Thread.currentThread().getId() + "】betweenNowInstant: " + DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " ~~ " + DateUtil.format(target, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " = " + DateUtil.between(source, target, TimeUnit.SECONDS) + "秒");
                System.out.println("task【21】【" + Thread.currentThread().getId() + "】betweenNowInstant: " + DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " ~~ " + DateUtil.format(target, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " = " + DateUtil.between(source, target, TimeUnit.MILLISECONDS) + "毫秒");
                System.out.println("task【21】【" + Thread.currentThread().getId() + "】betweenNowInstant: " + DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " ~~ " + DateUtil.format(target, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " = " + DateUtil.between(source, target, TimeUnit.MICROSECONDS) + "微秒");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取日期距离当前的差值
     */
    @Test
    public void betweenNowInstant() {
        for (int i = 1; i < INSTANT_BETWEEN_LIST.size(); i++) {
            EXECUTOR.submit(new BetweenNowInstantRunnable(INSTANT_BETWEEN_LIST.get(i)));
        }
    }

    public class BetweenNowLocalDateTimeRunnable implements Runnable {

        private LocalDateTime source;

        private LocalDateTime target;


        public BetweenNowLocalDateTimeRunnable(LocalDateTime source) {
            this.source = source;
            this.target = LocalDateTime.now();
        }

        @Override
        public void run() {
            try {
                System.out.println("task【21】【" + Thread.currentThread().getId() + "】betweenNowLocalDateTime: " + DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " ~~ " + DateUtil.format(target, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " = " + DateUtil.between(source, target, TimeUnit.DAYS) + "日");
                System.out.println("task【21】【" + Thread.currentThread().getId() + "】betweenNowLocalDateTime: " + DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " ~~ " + DateUtil.format(target, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " = " + DateUtil.between(source, target, TimeUnit.HOURS) + "小时");
                System.out.println("task【21】【" + Thread.currentThread().getId() + "】betweenNowLocalDateTime: " + DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " ~~ " + DateUtil.format(target, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " = " + DateUtil.between(source, target, TimeUnit.MINUTES) + "分钟");
                System.out.println("task【21】【" + Thread.currentThread().getId() + "】betweenNowLocalDateTime: " + DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " ~~ " + DateUtil.format(target, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " = " + DateUtil.between(source, target, TimeUnit.SECONDS) + "秒");
                System.out.println("task【21】【" + Thread.currentThread().getId() + "】betweenNowLocalDateTime: " + DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " ~~ " + DateUtil.format(target, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " = " + DateUtil.between(source, target, TimeUnit.MILLISECONDS) + "毫秒");
                System.out.println("task【21】【" + Thread.currentThread().getId() + "】betweenNowLocalDateTime: " + DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " ~~ " + DateUtil.format(target, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " = " + DateUtil.between(source, target, TimeUnit.MICROSECONDS) + "微秒");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取日期距离当前的差值
     */
    @Test
    public void betweenNowLocalDateTime() {
        for (int i = 1; i < LOCAL_DATE_TIME_BETWEEN_LIST.size(); i++) {
            EXECUTOR.submit(new BetweenNowLocalDateTimeRunnable(LOCAL_DATE_TIME_BETWEEN_LIST.get(i)));
        }
    }

    public class BetweenNowLocalDateRunnable implements Runnable {

        private LocalDate source;

        private LocalDate target;


        public BetweenNowLocalDateRunnable(LocalDate source) {
            this.source = source;
            this.target = LocalDate.now();
        }

        @Override
        public void run() {
            try {
                System.out.println("task【21】【" + Thread.currentThread().getId() + "】betweenNowLocalDate: " + DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " ~~ " + DateUtil.format(target, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " = " + DateUtil.between(source, target, TimeUnit.DAYS) + "日");
                System.out.println("task【21】【" + Thread.currentThread().getId() + "】betweenNowLocalDate: " + DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " ~~ " + DateUtil.format(target, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " = " + DateUtil.between(source, target, TimeUnit.HOURS) + "小时");
                System.out.println("task【21】【" + Thread.currentThread().getId() + "】betweenNowLocalDate: " + DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " ~~ " + DateUtil.format(target, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " = " + DateUtil.between(source, target, TimeUnit.MINUTES) + "分钟");
                System.out.println("task【21】【" + Thread.currentThread().getId() + "】betweenNowLocalDate: " + DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " ~~ " + DateUtil.format(target, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " = " + DateUtil.between(source, target, TimeUnit.SECONDS) + "秒");
                System.out.println("task【21】【" + Thread.currentThread().getId() + "】betweenNowLocalDate: " + DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " ~~ " + DateUtil.format(target, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " = " + DateUtil.between(source, target, TimeUnit.MILLISECONDS) + "毫秒");
                System.out.println("task【21】【" + Thread.currentThread().getId() + "】betweenNowLocalDate: " + DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " ~~ " + DateUtil.format(target, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " = " + DateUtil.between(source, target, TimeUnit.MICROSECONDS) + "微秒");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取日期距离当前的差值
     */
    @Test
    public void betweenNowLocalDate() {
        for (int i = 1; i < LOCAL_DATE_BETWEEN_LIST.size(); i++) {
            EXECUTOR.submit(new BetweenNowLocalDateRunnable(LOCAL_DATE_BETWEEN_LIST.get(i)));
        }
    }

    public class BetweenNowLocalTimeRunnable implements Runnable {

        private LocalTime source;

        private LocalTime target;


        public BetweenNowLocalTimeRunnable(LocalTime source) {
            this.source = source;
            this.target = LocalTime.now();
        }

        @Override
        public void run() {
            try {
                System.out.println("task【21】【" + Thread.currentThread().getId() + "】betweenNowLocalTime: " + DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " ~~ " + DateUtil.format(target, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " = " + DateUtil.between(source, target, TimeUnit.DAYS) + "日");
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                System.out.println("task【21】【" + Thread.currentThread().getId() + "】betweenNowLocalTime: " + DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " ~~ " + DateUtil.format(target, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " = " + DateUtil.between(source, target, TimeUnit.MILLISECONDS) + "毫秒");
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                System.out.println("task【21】【" + Thread.currentThread().getId() + "】betweenNowLocalTime: " + DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " ~~ " + DateUtil.format(target, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " = " + DateUtil.between(source, target, TimeUnit.MICROSECONDS) + "微秒");
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                System.out.println("task【21】【" + Thread.currentThread().getId() + "】betweenNowLocalTime: " + DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " ~~ " + DateUtil.format(target, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " = " + DateUtil.between(source, target, TimeUnit.HOURS) + "小时");
                System.out.println("task【21】【" + Thread.currentThread().getId() + "】betweenNowLocalTime: " + DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " ~~ " + DateUtil.format(target, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " = " + DateUtil.between(source, target, TimeUnit.MINUTES) + "分钟");
                System.out.println("task【21】【" + Thread.currentThread().getId() + "】betweenNowLocalTime: " + DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " ~~ " + DateUtil.format(target, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " = " + DateUtil.between(source, target, TimeUnit.SECONDS) + "秒");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取日期距离当前的差值
     */
    @Test
    public void betweenNowLocalTime() {
        for (int i = 1; i < LOCAL_TIME_BETWEEN_LIST.size(); i++) {
            EXECUTOR.submit(new BetweenNowLocalTimeRunnable(LOCAL_TIME_BETWEEN_LIST.get(i)));
        }
    }

    public class PlusDateRunnable implements Runnable {

        private Date source;

        public PlusDateRunnable(Date source) {
            this.source = source;
        }

        @Override
        public void run() {
            try {
                Date date;
                date = DateUtil.plus(source, 1, TimeUnit.DAYS);
                System.out.println(DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " 1天，" + DateUtil.format(date, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));

                date = DateUtil.plus(source, 1, TimeUnit.HOURS);
                System.out.println(DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " 1小时，" + DateUtil.format(date, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));

                date = DateUtil.plus(source, 1, TimeUnit.MINUTES);
                System.out.println(DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " 1分钟，" + DateUtil.format(date, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));

                date = DateUtil.plus(source, 1, TimeUnit.SECONDS);
                System.out.println(DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " 1秒，" + DateUtil.format(date, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));

                date = DateUtil.plus(source, 1, TimeUnit.MILLISECONDS);
                System.out.println(DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " 1毫秒，" + DateUtil.format(date, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));

                date = DateUtil.plus(source, 1, TimeUnit.MICROSECONDS);
                System.out.println(DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " 1微秒，" + DateUtil.format(date, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 在基础时间上增加
     */
    @Test
    public void plusDate() {
        for (int i = 1; i < DATE_OFFSET_LIST.size(); i++) {
            EXECUTOR.submit(new PlusDateRunnable(DATE_OFFSET_LIST.get(i)));
        }
    }

    public class PlusInstantRunnable implements Runnable {

        private Instant source;

        public PlusInstantRunnable(Instant source) {
            this.source = source;
        }

        @Override
        public void run() {
            try {
                Instant result;
                result = DateUtil.plus(source, 1, TimeUnit.DAYS);
                System.out.println(DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " 1天，" + DateUtil.format(result, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));

                result = DateUtil.plus(source, 1, TimeUnit.HOURS);
                System.out.println(DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " 1小时，" + DateUtil.format(result, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));

                result = DateUtil.plus(source, 1, TimeUnit.MINUTES);
                System.out.println(DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " 1分钟，" + DateUtil.format(result, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));

                result = DateUtil.plus(source, 1, TimeUnit.SECONDS);
                System.out.println(DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " 1秒，" + DateUtil.format(result, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));

                result = DateUtil.plus(source, 1, TimeUnit.MILLISECONDS);
                System.out.println(DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " 1毫秒，" + DateUtil.format(result, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));

                result = DateUtil.plus(source, 1, TimeUnit.MICROSECONDS);
                System.out.println(DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " 1微秒，" + DateUtil.format(result, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 在基础时间上增加
     */
    @Test
    public void plusInstant() {
        for (int i = 1; i < INSTANT_OFFSET_LIST.size(); i++) {
            EXECUTOR.submit(new PlusInstantRunnable(INSTANT_OFFSET_LIST.get(i)));
        }
    }

    public class PlusLocalDateTimeRunnable implements Runnable {

        private LocalDateTime source;

        public PlusLocalDateTimeRunnable(LocalDateTime source) {
            this.source = source;
        }

        @Override
        public void run() {
            try {
                LocalDateTime result;
                result = DateUtil.plus(source, 1, TimeUnit.DAYS);
                System.out.println(DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " 1天，" + DateUtil.format(result, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));

                result = DateUtil.plus(source, 1, TimeUnit.HOURS);
                System.out.println(DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " 1小时，" + DateUtil.format(result, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));

                result = DateUtil.plus(source, 1, TimeUnit.MINUTES);
                System.out.println(DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " 1分钟，" + DateUtil.format(result, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));

                result = DateUtil.plus(source, 1, TimeUnit.SECONDS);
                System.out.println(DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " 1秒，" + DateUtil.format(result, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));

                result = DateUtil.plus(source, 1, TimeUnit.MILLISECONDS);
                System.out.println(DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " 1毫秒，" + DateUtil.format(result, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));

                result = DateUtil.plus(source, 1, TimeUnit.MICROSECONDS);
                System.out.println(DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " 1微秒，" + DateUtil.format(result, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 在基础时间上增加
     */
    @Test
    public void plusLocalDateTime() {
        for (int i = 1; i < LOCAL_DATE_TIME_OFFSET_LIST.size(); i++) {
            EXECUTOR.submit(new PlusLocalDateTimeRunnable(LOCAL_DATE_TIME_OFFSET_LIST.get(i)));
        }
    }

    public class PlusLocalDateRunnable implements Runnable {

        private LocalDate source;

        public PlusLocalDateRunnable(LocalDate source) {
            this.source = source;
        }

        @Override
        public void run() {
            try {
                LocalDate result;
                result = DateUtil.plus(source, 1, TimeUnit.DAYS);
                System.out.println(DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " 1天，" + DateUtil.format(result, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                LocalDate result;
                result = DateUtil.plus(source, 1, TimeUnit.HOURS);
                System.out.println(DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " 1小时，" + DateUtil.format(result, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                LocalDate result;
                result = DateUtil.plus(source, 1, TimeUnit.MINUTES);
                System.out.println(DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " 1分钟，" + DateUtil.format(result, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                LocalDate result;
                result = DateUtil.plus(source, 1, TimeUnit.SECONDS);
                System.out.println(DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " 1秒，" + DateUtil.format(result, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                LocalDate result;
                result = DateUtil.plus(source, 1, TimeUnit.MILLISECONDS);
                System.out.println(DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " 1毫秒，" + DateUtil.format(result, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                LocalDate result;
                result = DateUtil.plus(source, 1, TimeUnit.MICROSECONDS);
                System.out.println(DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " 1微秒，" + DateUtil.format(result, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 在基础时间上增加
     */
    @Test
    public void plusLocalDate() {
        for (int i = 1; i < LOCAL_DATE_OFFSET_LIST.size(); i++) {
            EXECUTOR.submit(new PlusLocalDateRunnable(LOCAL_DATE_OFFSET_LIST.get(i)));
        }
    }

    public class PlusLocalTimeRunnable implements Runnable {

        private LocalTime source;

        public PlusLocalTimeRunnable(LocalTime source) {
            this.source = source;
        }

        @Override
        public void run() {
            LocalTime result;
            try {
                result = DateUtil.plus(source, 1, TimeUnit.DAYS);
                System.out.println(DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " 1天，" + DateUtil.format(result, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                result = DateUtil.plus(source, 1, TimeUnit.HOURS);
                System.out.println(DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " 1小时，" + DateUtil.format(result, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                result = DateUtil.plus(source, 1, TimeUnit.MINUTES);
                System.out.println(DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " 1分钟，" + DateUtil.format(result, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                result = DateUtil.plus(source, 1, TimeUnit.SECONDS);
                System.out.println(DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " 1秒，" + DateUtil.format(result, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                result = DateUtil.plus(source, 1, TimeUnit.MILLISECONDS);
                System.out.println(DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " 1毫秒，" + DateUtil.format(result, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                result = DateUtil.plus(source, 1, TimeUnit.MICROSECONDS);
                System.out.println(DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " 1微秒，" + DateUtil.format(result, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 在基础时间上增加
     */
    @Test
    public void plusLocalTime() {
        for (int i = 1; i < LOCAL_TIME_OFFSET_LIST.size(); i++) {
            EXECUTOR.submit(new PlusLocalTimeRunnable(LOCAL_TIME_OFFSET_LIST.get(i)));
        }
    }

    public class plusNowRunnable implements Runnable {
        TimeUnit[] timeUnits = new TimeUnit[]{
                TimeUnit.DAYS, TimeUnit.SECONDS,
                TimeUnit.MINUTES, TimeUnit.HOURS,
                TimeUnit.MICROSECONDS, TimeUnit.NANOSECONDS,
                TimeUnit.MILLISECONDS};
        Class[] classes = new Class[]{Date.class, Instant.class, LocalDateTime.class, LocalDate.class, LocalTime.class};

        @Override
        public void run() {
            for (int i = 0; i < timeUnits.length; i++) {
                for (int j = 0; j < classes.length; j++) {
                    try {
                        System.out.println(classes[j].getName() + "增加1" + timeUnits[i].name() + " ~~ " + DateUtil.plusNow(1, timeUnits[i], classes[j]).toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 在当前时间上增加
     */
    @Test
    public void plusNow() throws InterruptedException {
        EXECUTOR.submit(new plusNowRunnable());
    }

    public class MinusDateRunnable implements Runnable {

        private Date source;

        public MinusDateRunnable(Date source) {
            this.source = source;
        }

        @Override
        public void run() {
            Date result;
            try {
                result = DateUtil.minus(source, 1, TimeUnit.DAYS);
                System.out.println(DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " 1天，" + DateUtil.format(result, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                result = DateUtil.minus(source, 1, TimeUnit.HOURS);
                System.out.println(DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " 1小时，" + DateUtil.format(result, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                result = DateUtil.minus(source, 1, TimeUnit.MINUTES);
                System.out.println(DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " 1分钟，" + DateUtil.format(result, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                result = DateUtil.minus(source, 1, TimeUnit.SECONDS);
                System.out.println(DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " 1秒，" + DateUtil.format(result, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                result = DateUtil.minus(source, 1, TimeUnit.MILLISECONDS);
                System.out.println(DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " 1毫秒，" + DateUtil.format(result, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                result = DateUtil.minus(source, 1, TimeUnit.MICROSECONDS);
                System.out.println(DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " 1微秒，" + DateUtil.format(result, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 在基础时间上减少
     */
    @Test
    public void minusDate() {
        for (int i = 1; i < DATE_OFFSET_LIST.size(); i++) {
            EXECUTOR.submit(new MinusDateRunnable(DATE_OFFSET_LIST.get(i)));
        }
    }

    public class MinusInstantRunnable implements Runnable {

        private Instant source;

        public MinusInstantRunnable(Instant source) {
            this.source = source;
        }

        @Override
        public void run() {
            Instant result;
            try {
                result = DateUtil.minus(source, 1, TimeUnit.DAYS);
                System.out.println(DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " 1天，" + DateUtil.format(result, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                result = DateUtil.minus(source, 1, TimeUnit.HOURS);
                System.out.println(DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " 1小时，" + DateUtil.format(result, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                result = DateUtil.minus(source, 1, TimeUnit.MINUTES);
                System.out.println(DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " 1分钟，" + DateUtil.format(result, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                result = DateUtil.minus(source, 1, TimeUnit.SECONDS);
                System.out.println(DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " 1秒，" + DateUtil.format(result, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                result = DateUtil.minus(source, 1, TimeUnit.MILLISECONDS);
                System.out.println(DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " 1毫秒，" + DateUtil.format(result, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                result = DateUtil.minus(source, 1, TimeUnit.MICROSECONDS);
                System.out.println(DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " 1微秒，" + DateUtil.format(result, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 在基础时间上减少
     */
    @Test
    public void minusInstant() {
        for (int i = 1; i < INSTANT_OFFSET_LIST.size(); i++) {
            EXECUTOR.submit(new MinusInstantRunnable(INSTANT_OFFSET_LIST.get(i)));
        }
    }

    public class MinusLocalDateTimeRunnable implements Runnable {

        private LocalDateTime source;

        public MinusLocalDateTimeRunnable(LocalDateTime source) {
            this.source = source;
        }

        @Override
        public void run() {
            LocalDateTime result;
            try {
                result = DateUtil.minus(source, 1, TimeUnit.DAYS);
                System.out.println(DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " 1天，" + DateUtil.format(result, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                result = DateUtil.minus(source, 1, TimeUnit.HOURS);
                System.out.println(DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " 1小时，" + DateUtil.format(result, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                result = DateUtil.minus(source, 1, TimeUnit.MINUTES);
                System.out.println(DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " 1分钟，" + DateUtil.format(result, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                result = DateUtil.minus(source, 1, TimeUnit.SECONDS);
                System.out.println(DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " 1秒，" + DateUtil.format(result, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                result = DateUtil.minus(source, 1, TimeUnit.MILLISECONDS);
                System.out.println(DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " 1毫秒，" + DateUtil.format(result, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                result = DateUtil.minus(source, 1, TimeUnit.MICROSECONDS);
                System.out.println(DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " 1微秒，" + DateUtil.format(result, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 在基础时间上减少
     */
    @Test
    public void minusLocalDateTime() {
        for (int i = 1; i < LOCAL_DATE_TIME_OFFSET_LIST.size(); i++) {
            EXECUTOR.submit(new MinusLocalDateTimeRunnable(LOCAL_DATE_TIME_OFFSET_LIST.get(i)));
        }
    }

    public class MinusLocalDateRunnable implements Runnable {

        private LocalDate source;

        public MinusLocalDateRunnable(LocalDate source) {
            this.source = source;
        }

        @Override
        public void run() {
            LocalDate result;
            try {
                result = DateUtil.minus(source, 1, TimeUnit.DAYS);
                System.out.println(DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " 1天，" + DateUtil.format(result, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                result = DateUtil.minus(source, 1, TimeUnit.HOURS);
                System.out.println(DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " 1小时，" + DateUtil.format(result, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                result = DateUtil.minus(source, 1, TimeUnit.MINUTES);
                System.out.println(DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " 1分钟，" + DateUtil.format(result, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                result = DateUtil.minus(source, 1, TimeUnit.SECONDS);
                System.out.println(DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " 1秒，" + DateUtil.format(result, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                result = DateUtil.minus(source, 1, TimeUnit.MILLISECONDS);
                System.out.println(DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " 1毫秒，" + DateUtil.format(result, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                result = DateUtil.minus(source, 1, TimeUnit.MICROSECONDS);
                System.out.println(DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " 1微秒，" + DateUtil.format(result, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 在基础时间上减少
     */
    @Test
    public void minusLocalDate() {
        for (int i = 1; i < LOCAL_DATE_OFFSET_LIST.size(); i++) {
            EXECUTOR.submit(new MinusLocalDateRunnable(LOCAL_DATE_OFFSET_LIST.get(i)));
        }
    }

    public class MinusLocalTimeRunnable implements Runnable {

        private LocalTime source;

        public MinusLocalTimeRunnable(LocalTime source) {
            this.source = source;
        }

        @Override
        public void run() {
            LocalTime result;
            try {
                result = DateUtil.minus(source, 1, TimeUnit.DAYS);
                System.out.println(DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " 1天，" + DateUtil.format(result, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                result = DateUtil.minus(source, 1, TimeUnit.HOURS);
                System.out.println(DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " 1小时，" + DateUtil.format(result, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                result = DateUtil.minus(source, 1, TimeUnit.MINUTES);
                System.out.println(DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " 1分钟，" + DateUtil.format(result, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                result = DateUtil.minus(source, 1, TimeUnit.SECONDS);
                System.out.println(DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " 1秒，" + DateUtil.format(result, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                result = DateUtil.minus(source, 1, TimeUnit.MILLISECONDS);
                System.out.println(DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " 1毫秒，" + DateUtil.format(result, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                result = DateUtil.minus(source, 1, TimeUnit.MICROSECONDS);
                System.out.println(DateUtil.format(source, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS) + " 1微秒，" + DateUtil.format(result, DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 在基础时间上减少
     */
    @Test
    public void minusLocalTime() {
        for (int i = 1; i < LOCAL_TIME_OFFSET_LIST.size(); i++) {
            EXECUTOR.submit(new MinusLocalTimeRunnable(LOCAL_TIME_OFFSET_LIST.get(i)));
        }
    }

    public class minusNowRunnable implements Runnable {
        TimeUnit[] timeUnits = new TimeUnit[]{
                TimeUnit.DAYS, TimeUnit.SECONDS,
                TimeUnit.MINUTES, TimeUnit.HOURS,
                TimeUnit.MICROSECONDS, TimeUnit.NANOSECONDS,
                TimeUnit.MILLISECONDS};
        Class[] classes = new Class[]{Date.class, Instant.class, LocalDateTime.class, LocalDate.class, LocalTime.class};

        @Override
        public void run() {
            for (int i = 0; i < timeUnits.length; i++) {
                for (int j = 0; j < classes.length; j++) {
                    try {
                        System.out.println(classes[j].getName() + "减少1" + timeUnits[i].name() + " ~~ " + DateUtil.minusNow(1, timeUnits[i], classes[j]).toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 在当前时间上减少
     */
    @Test
    public void minusNow() {
        EXECUTOR.submit(new minusNowRunnable());
    }
}