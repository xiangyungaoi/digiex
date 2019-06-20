package com.caetp.digiex.config;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class DatetimeConvert {
	
	public static LocalDateTime DateToLocalDateTime(Date date) {
		if(date == null) {
			return null;
		}
		Instant instant = date.toInstant();
		ZoneId zone = ZoneId.systemDefault();
		return LocalDateTime.ofInstant(instant, zone);
	}
	
	public static Date LocalDateTimeToDate(LocalDateTime localDateTime) {
		if(localDateTime == null) {
			return null;
		}
		ZoneId zone = ZoneId.systemDefault();
		Instant instant = localDateTime.atZone(zone).toInstant();
		return Date.from(instant);
	}
}
