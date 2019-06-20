package com.caetp.digiex.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by gaoyx on 2019/6/14.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Mt5AccountNumber implements IEntity{
    private List<Long> login;
}
