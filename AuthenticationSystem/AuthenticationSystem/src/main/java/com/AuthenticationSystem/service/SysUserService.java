package com.AuthenticationSystem.service;

import com.AuthenticationSystem.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author ddd
* @description 针对表【sys_user】的数据库操作Service
* @createDate 2023-11-06 19:57:10
*/
public interface SysUserService extends IService<SysUser> {
    SysUser getByUsername(String username);
}

