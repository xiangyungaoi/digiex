package com.caetp.digiex.service;

import com.caetp.digiex.dto.api.SystemNoticeDetailDTO;
import com.caetp.digiex.entity.mapper.*;
import com.caetp.digiex.exception.UserException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by hy on 2018/12/29.
 */
@Service
public class SystemNoticeService extends BaseService {

	@Autowired
	private SystemNoticeMapper systemNoticeMapper;

    
    /**
     * 是否有弹框推送
     * @return
     */
    public SystemNoticeDetailDTO isPopup() {
    	SystemNoticeDetailDTO popup = systemNoticeMapper.isPopup();
    	if(popup != null){
    		String content = popup.getContent();
    		String regEx_html = "<[^>]+>";	 // 定义HTML标签的正则表达式
    		String regEx_space = "\\s*|\t|\r|\n";//定义空格回车换行符
    		String cont = content.replaceAll(regEx_html, "");
    		String con = cont.replaceAll(regEx_space, "");
    		popup.setContent(con.replaceAll("&nbsp;", ""));
    	}else{
    		throw UserException.NOT_FOUND_RESULT;
    	}
    	return popup;
    }

}
