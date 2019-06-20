package com.caetp.digiex.entity.mapper;

import com.caetp.digiex.dto.cms.SysUserDTO;
import com.caetp.digiex.entity.SysUser;

public interface SysUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    /**
     * 查询用户
     * @param username
     * @return
     */
    SysUserDTO selectByUsername(String username);

    /**
     * 查找用户密码
     * @param username
     * @return
     */
    SysUserDTO selectUserByUsername(String username);
}