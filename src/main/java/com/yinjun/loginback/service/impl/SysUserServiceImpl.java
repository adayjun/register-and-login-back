package com.yinjun.loginback.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yinjun.loginback.entity.SysUserEntity;
import com.yinjun.loginback.mapper.SysUserMapper;
import com.yinjun.loginback.repo.UserRepository;
import com.yinjun.loginback.req.SysUserLoginReq;
import com.yinjun.loginback.req.SysUserSaveReq;
import com.yinjun.loginback.service.SysUserService;
import com.yinjun.loginback.utils.CopyUtil;
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
          List<SysUserEntity> userEntityList=userRepository.findByLoginName(req.getLoginName());
        if(CollectionUtils.isEmpty(userEntityList)){
            return false;
        }else {
            return true;
        }
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
