package com.yinjun.loginback.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yinjun.loginback.entity.SysUserEntity;
import com.yinjun.loginback.mapper.SysUserMapper;
import com.yinjun.loginback.repo.UserRepository;
import com.yinjun.loginback.req.SysUserLoginReq;
import com.yinjun.loginback.req.SysUserSaveReq;
import com.yinjun.loginback.service.SysUserService;
import com.yinjun.loginback.utils.CopyUtil;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUserEntity> implements SysUserService {

    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private UserRepository userRepository;

    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private ObjectMapper objectMapper;

    @Override
    public boolean register(SysUserSaveReq req) {
        SysUserEntity user = CopyUtil.copy(req, SysUserEntity.class);
            SysUserEntity userDb = selectByLoginName(req.getLoginName());
            if(ObjectUtils.isEmpty(userDb)){
                userRepository.save(user);
                return true;
            }else {
                return false;
            }
    }

    @Override
    public boolean login(SysUserLoginReq req) {
        String loginName=req.getLoginName();
//        QueryWrapper<SysUserEntity> wrapper = new QueryWrapper<>();
//        wrapper.lambda().eq(SysUserEntity::getLoginName,loginName);
//        List<SysUserEntity> userEntityList = sysUserMapper.selectList(wrapper);
        List<SysUserEntity> userEntityList;
        String toString=stringRedisTemplate.opsForValue().get(loginName);
        if(toString!=null){
            try {
                userEntityList=objectMapper.readValue(toString, new TypeReference<List<SysUserEntity>>() {});
                System.out.println("我是从缓存里取出来的！！！！！！！！！！！！！！登陆");
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return true;

        }


         userEntityList=userRepository.findByLoginName(req.getLoginName());
        if(userEntityList.size()!=0){
            try {
                stringRedisTemplate.opsForValue().set(loginName,objectMapper.writeValueAsString(userEntityList));
                System.out.println("我第一次被塞进缓存了！！！！！！！！！！！！！！登陆");
                return true;
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    //查询loginName是否被注册
    public SysUserEntity selectByLoginName(String loginName){
//        QueryWrapper<SysUserEntity> wrapper = new QueryWrapper<>();
//        wrapper.lambda().eq(SysUserEntity::getLoginName,loginName);
//        List<SysUserEntity> userEntityList = sysUserMapper.selectList(wrapper);
        List<SysUserEntity> userEntityList=userRepository.findByLoginName(loginName);
        if(CollectionUtils.isEmpty(userEntityList)){
            return null;
        }else {
            return userEntityList.get(0);
        }



    }

}
