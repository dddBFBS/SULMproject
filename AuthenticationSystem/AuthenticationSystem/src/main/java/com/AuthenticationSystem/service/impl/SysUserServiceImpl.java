package com.AuthenticationSystem.service.impl;


import com.AuthenticationSystem.entity.SysUser;
import com.AuthenticationSystem.mapper.SysUserMapper;
import com.AuthenticationSystem.service.SysUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
* @author ddd
* @description 针对表【sys_user】的数据库操作Service实现
* @createDate 2023-11-06 19:57:10
*/
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser>
    implements SysUserService {
    @Override
    public SysUser getByUsername(String username) {
        return getOne(new QueryWrapper<SysUser> ().eq("username",username));
    }

}




