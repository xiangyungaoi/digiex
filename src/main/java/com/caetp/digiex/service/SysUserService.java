package com.caetp.digiex.service;


import com.caetp.digiex.dto.cms.SysUserDTO;
import com.caetp.digiex.entity.mapper.SysUserMapper;
import com.caetp.digiex.exception.UserException;
import com.caetp.digiex.utli.encryption.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;


/**
 * @author shijy
 * @date 2019/2/25 18:06
 */
@Service
public class SysUserService {

    @Autowired
    SysUserMapper sysUserMapper;
    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    public SysUserDTO toLogin(HttpServletRequest request, String username, String password) {

        SysUserDTO userInfo = sysUserMapper.selectByUsername(username);
        if(userInfo == null) {
            throw UserException.USER_NOT_EXIST;
        }
        if(0==userInfo.getUserStatus()){
            throw UserException.USER_DISABLE;
        }else if(2==userInfo.getUserStatus()){
            throw UserException.USER_DELETE;
        }
        //登录
        SysUserDTO sysUser = sysUserMapper.selectUserByUsername(username);
        if(!MD5.md5(password).equals(sysUser.getPassword())){
            throw UserException.PASSWORD_WRONG;
        }
        request.getSession().setAttribute("sysUser",sysUser);
        request.getSession().setMaxInactiveInterval(30*60);
        return sysUser;
    }

    /**
     * 退出登陆
     * @param request
     */
    public void logout(HttpServletRequest request){
        //SysUserDTO sysUserDTO = (SysUserDTO) request.getSession().getAttribute("sysUser");
        Enumeration em = request.getSession().getAttributeNames();
        while(em.hasMoreElements()){
            request.getSession().removeAttribute(em.nextElement().toString());
        }
    }

    /**
     * 用户信息
     * @param request
     * @return
     */
    public SysUserDTO userDetail(HttpServletRequest request){
        SysUserDTO sysUserDTO = (SysUserDTO) request.getSession().getAttribute("sysUser");
        String username = sysUserDTO.getUsername();
        SysUserDTO userInfo = sysUserMapper.selectByUsername(username);
        if(userInfo == null) {
            throw UserException.USER_NOT_EXIST;
        }
        return userInfo;
    }
}
