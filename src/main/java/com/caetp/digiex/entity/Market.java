package com.caetp.digiex.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Market implements IEntity {
    private Long id;

    private String coin;

    private String coinName;

    private String coinIcon;

    private String coinType;

    private Boolean isDelete;
}