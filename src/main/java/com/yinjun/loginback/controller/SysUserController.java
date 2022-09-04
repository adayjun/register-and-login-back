package com.yinjun.loginback.controller;

import com.yinjun.loginback.entity.SysUserEntity;
import com.yinjun.loginback.repo.UserRepository;
import com.yinjun.loginback.req.SysUserLoginReq;
import com.yinjun.loginback.req.SysUserSaveReq;
import com.yinjun.loginback.resp.CommonResp;
import com.yinjun.loginback.service.SysUserService;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class SysUserController {


    @Resource
    private SysUserService sysUserService;
    @Resource
    private UserRepository userRepository;

    @PostMapping("/sys-user/register")
    public CommonResp<Object> register( @RequestBody SysUserSaveReq req){
       // header("Access-Control-Allow-Origin:*");
        req.setPassword(DigestUtils.md5DigestAsHex(req.getPassword().getBytes()));
        CommonResp<Object> resp=new CommonResp<>();
        boolean res=sysUserService.register(req);
        resp.setSuccess(res);
        return resp;
    }

    @PostMapping("/sys-user/login")
    public CommonResp<Object> login(@RequestBody SysUserLoginReq req){
        CommonResp<Object> resp=new CommonResp<>();
        boolean  res=sysUserService.login(req);
        resp.setSuccess(res);
        return resp;
    }

    @PostMapping("/sys-user/ppp")
    public CommonResp<Object> ppp(){
        CommonResp<Object> resp=new CommonResp<>();
        List<SysUserEntity> list=userRepository.findAll();
        resp.setContent(list);
        return resp;
    }
}
