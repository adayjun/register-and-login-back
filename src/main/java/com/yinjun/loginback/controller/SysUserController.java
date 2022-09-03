package com.yinjun.loginback.controller;

import com.yinjun.loginback.req.SysUserLoginReq;
import com.yinjun.loginback.req.SysUserSaveReq;
import com.yinjun.loginback.resp.CommonResp;
import com.yinjun.loginback.service.SysUserService;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@CrossOrigin(origins = "*")
public class SysUserController {


    @Resource
    private SysUserService sysUserService;

    @PostMapping("/sys-user/register")
    public CommonResp<Object> register( SysUserSaveReq req){
       // header("Access-Control-Allow-Origin:*");
        req.setPassword(DigestUtils.md5DigestAsHex(req.getPassword().getBytes()));
        CommonResp<Object> resp=new CommonResp<>();
        sysUserService.register(req);
        return resp;
    }

    @PostMapping("/sys-user/login")
    public CommonResp<Object> login(@RequestBody SysUserLoginReq req){
        CommonResp<Object> resp=new CommonResp<>();
        boolean res=sysUserService.login(req);
        resp.setSuccess(res);
        return resp;
    }


}
