package com.yinjun.loginback.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yinjun.loginback.entity.SysUserEntity;
import com.yinjun.loginback.repo.UserRepository;
import com.yinjun.loginback.req.SysUserLoginReq;
import com.yinjun.loginback.req.SysUserSaveReq;
import com.yinjun.loginback.resp.CommonResp;
import com.yinjun.loginback.service.SysUserService;
import org.springframework.data.redis.core.StringRedisTemplate;
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
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    ObjectMapper objectMapper;


    @PostMapping("/sys-user/register")
    public CommonResp<Object> register(@RequestBody SysUserSaveReq req) {
        // header("Access-Control-Allow-Origin:*");
        req.setPassword(DigestUtils.md5DigestAsHex(req.getPassword().getBytes()));
        CommonResp<Object> resp = new CommonResp<>();
        boolean res = sysUserService.register(req);
        resp.setSuccess(res);
        return resp;
    }

    @PostMapping("/sys-user/login")
    public CommonResp<Object> login(@RequestBody SysUserLoginReq req) {
        CommonResp<Object> resp = new CommonResp<>();
        boolean res = sysUserService.login(req);
        resp.setSuccess(res);
        return resp;
    }

    /**
     * 用了 redis
     * @return
     */
    @PostMapping("/sys-user/ppp")
    public CommonResp<Object> ppp() {
        CommonResp<Object> resp = new CommonResp<>();
        String toString = stringRedisTemplate.opsForValue().get("userall");
        List<SysUserEntity> userEntityList;
        if (toString != null) {
            try {
                userEntityList = objectMapper.readValue(toString, new TypeReference<List<SysUserEntity>>() {});
                resp.setContent(userEntityList);
                System.out.println("我是从缓存里取出来的！！！！！！！！！！！！！！！！！！");
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return resp;
        }

        userEntityList = userRepository.findAll();
        if (userEntityList.size() != 0) {
            try {
                stringRedisTemplate.opsForValue().set("userall", objectMapper.writeValueAsString(userEntityList));
                System.out.println("我第一次被塞进缓存了啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊");
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            resp.setContent(userEntityList);
            return resp;

        }

        resp.setContent(null);
        return resp;
    }
}
