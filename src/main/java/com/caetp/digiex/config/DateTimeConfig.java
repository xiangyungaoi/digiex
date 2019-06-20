package com.caetp.digiex.config;

import com.caetp.digiex.consts.ProjectConsts;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Configuration
public class DateTimeConfig {

    public static final DateTimeFormatter DATE = DateTimeFormatter.ofPattern(ProjectConsts.DEFAULT_DATE_PATTERN);
    public static final DateTimeFormatter DATETIME = DateTimeFormatter.ofPattern(ProjectConsts.DEFAULT_DATE_TIME_PATTERN);

    @Bean
    @Primary
    public ObjectMapper serializingObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();

        // 不序列化 null 字段
        // mapper.setSerializationInclusion(Include.NON_NULL);

        // 解序列化时不处理未知字段
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // 仅处理成员变量
        mapper.setVisibility(mapper.getSerializationConfig().getDefaultVisibilityChecker()
                .withFieldVisibility(Visibility.ANY).withGetterVisibility(Visibility.NONE)
                .withSetterVisibility(Visibility.NONE).withCreatorVisibility(Visibility.NONE)
                .withIsGetterVisibility(Visibility.NONE));

        // 自动转换 Java 8 本地时间
        JavaTimeModule module = new JavaTimeModule();
        module.addSerializer(LocalDate.class, new LocalDateSerializer(DATE));
        module.addDeserializer(LocalDate.class, new LocalDateDeserializer(DATE));
        module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DATETIME));
        module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DATETIME));
        mapper.registerModule(module);
        return mapper;
    }

    @Bean
    public Formatter<LocalDate> localDateFormatter() {
        return new Formatter<LocalDate>() {
            @Override
            public LocalDate parse(String text, Locale locale) throws ParseException {
                return LocalDate.parse(text, DATE);
            }
            @Override
            public String print(LocalDate object, Locale locale) {
                return DATE.format(object);
            }
        };
    }

    @Bean
    public Formatter<LocalDateTime> localDateTimeFormatter() {
        return new Formatter<LocalDateTime>() {
            @Override
            public LocalDateTime parse(String text, Locale locale) throws ParseException {
                return LocalDateTime.parse(text, DATETIME);
            }
            @Override
            public String print(LocalDateTime object, Locale locale) {
                return DATETIME.format(object);
            }
        };
    }


}