package com.yinjun.loginback.service;

import com.yinjun.loginback.req.SysUserLoginReq;
import com.yinjun.loginback.req.SysUserSaveReq;

public interface SysUserService {
    boolean register(SysUserSaveReq req);
    boolean login(SysUserLoginReq req);
}
