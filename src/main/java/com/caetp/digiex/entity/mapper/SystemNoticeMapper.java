package com.caetp.digiex.entity.mapper;


import com.caetp.digiex.dto.api.SystemNoticeDetailDTO;
import com.caetp.digiex.entity.SystemNotice;

public interface SystemNoticeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SystemNotice record);

    int insertSelective(SystemNotice record);

    SystemNotice selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SystemNotice record);

    int updateByPrimaryKeyWithBLOBs(SystemNotice record);

    int updateByPrimaryKey(SystemNotice record);
    
    SystemNoticeDetailDTO isPopup();

}