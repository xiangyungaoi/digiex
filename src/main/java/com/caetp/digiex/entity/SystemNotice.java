package com.caetp.digiex.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SystemNotice implements IEntity{
    private Long id;

    private String title;

    private Boolean delStatus;

    private LocalDateTime createdTime;

    private LocalDateTime updatedTime;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String content;

    private Boolean isPopup;

}