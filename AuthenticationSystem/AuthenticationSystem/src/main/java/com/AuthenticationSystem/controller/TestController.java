package com.AuthenticationSystem.controller;

import com.AuthenticationSystem.entity.R;
import com.AuthenticationSystem.entity.SysUser;
import com.AuthenticationSystem.service.SysUserService;
import com.AuthenticationSystem.util.JwtUtils;
import com.AuthenticationSystem.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*测试*/
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired//一个注解，它可以对类成员变量、方法及构造函数进行标注，让 spring 完成 bean 自动装配的工作
    private SysUserService sysUserService;

    @RequestMapping("/user/list")
    public R userList(@RequestHeader(required=false)String token){
        //判断
        if(StringUtil.isNotEmpty ( token )){
            Map<String,Object> resutlMap=new HashMap<> ();//加入hashmap
            List<SysUser> userList = sysUserService.list ();//List方法获取信息
            resutlMap.put ( "userList",userList );//显示用户信息
            return R.ok (resutlMap);
        }else{
            return R.error(401,"没有权限访问");
        }

    }

    @RequestMapping("/login")
    public R login(){
        String token= JwtUtils.genJwtToken ( "Java1234" );
        return R.ok ().put ( "token",token );
    }
}