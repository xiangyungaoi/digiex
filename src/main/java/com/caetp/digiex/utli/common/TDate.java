package com.caetp.digiex.utli.common;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * TDate.java
 * 时间工具包
 */
public final class TDate {


	/**
	 * 项目默认时间格式
	 */
	public static final DateTimeFormatter DEFAULT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

	/**
	 * BASIC_ISO_DATE yyyyMMdd
	 */
	public static final DateTimeFormatter BASIC_ISO_DATE = DateTimeFormatter.BASIC_ISO_DATE;

	/**
	 * ISO_LOCAL_DATE yyyy-MM-dd
	 */
	public static final DateTimeFormatter ISO_LOCAL_DATE = DateTimeFormatter.ISO_LOCAL_DATE;

	/**
	 * yyyy-MM-ddTHH:mm:ss.SSSZ
	 */
	public static final DateTimeFormatter ISO_INSTANT_ZONE = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

	/**
	 * yyyyMMddHHmmssSSSZ, 如 20170929161621000+0800
	 * 不能直接ofPattern()调用, Java 9 已修复此BUG, 见JDK-8031085
	 */
	public static final DateTimeFormatter yyyyMMddHHmmssSSSZ = new DateTimeFormatterBuilder().appendPattern("yyyyMMddHHmmss")
			.appendValue(ChronoField.MILLI_OF_SECOND, 3).appendPattern("Z").toFormatter();

	/**
	 * yyyyMMddHHmmssZ, 如 20121222222222+0800
	 */
	public static final DateTimeFormatter yyyyMMddHHmmssZ = DateTimeFormatter.ofPattern("yyyyMMddHHmmssZ");

    /**
     * ➜ 对比2个时间相隔秒数
     * @param start 起始时间
     * @param end 结束时间
     * @return
     */
    public static long compare(LocalDateTime start, LocalDateTime end) {
        if(start == null || end == null)
            return 0L;
        return Duration.between(start, end).getSeconds();
    }

    /**
     * Date 转 LocalDateTime
     */
    public static LocalDateTime dateToLocalDateTime(Date date) {
    	if(date == null)
    		return null;
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    /**
     * LocalDateTime 转 Date
     */
    public static Date localDateTimeToDate(LocalDateTime ldt) {
    	if(ldt == null)
    		return null;
        return Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * Date 转 LocalDate
     * @param date
     * @return
     */
    public static LocalDate dateToLocalDate(Date date) {
    	if(date == null)
    		return null;
    	return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * LocalDate 转 Date
     * @param ld
     * @return
     */
    public static Date localDateToDate(LocalDate ld) {
    	if(ld == null)
    		return null;
    	return Date.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    /**
     * localDate 转 localDateTime
     * @param date 待转换的localDate
     * @return
     */
    public static LocalDateTime localDateToLocalDateTime(LocalDate date) {
    	if(date == null)
    		return null;
    	return LocalDateTime.of(date, LocalTime.MIDNIGHT);
    }

    /**
     * XMLGregorianCalendar 转 LocalDateTime
     * @param calendar
     * @return
     */
    public static LocalDateTime XMLGregorianCalendarToLocalDateTime(XMLGregorianCalendar calendar) {
    	if(calendar == null)
    		return null;
    	return calendar.toGregorianCalendar().toZonedDateTime().toLocalDateTime();
    }

    /**
     * LocalDateTime 转 XMLGregorianCalendar
     * @param dateTime
     * @return
     */
    public static XMLGregorianCalendar localDateTimeToXMLGregorianCalendar(LocalDateTime dateTime) {
    	if(dateTime == null)
    		return null;
    	GregorianCalendar gCal = GregorianCalendar.from(dateTime.atZone(ZoneId.systemDefault()));
    	try {
			return DatatypeFactory.newInstance().newXMLGregorianCalendar(gCal);
		} catch (DatatypeConfigurationException e) {
			throw Utility.wrap(e);
		}
    }

}
