package com.yinjun.loginback.req;

import com.baomidou.mybatisplus.annotation.TableName;

public class SysUserSaveReq {
private String loginName;
private String name;
private String password;
private  String checkPass;
/*
*
*  loginName:'',
          name:'',
          password: '',
          checkPass: ''
* */


    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
       this.loginName = loginName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCheckPass() {
        return checkPass;
    }

    public void setCheckPass(String checkPass) {
        this.checkPass = checkPass;
    }

    @Override
    public String toString() {
        return "SysUserSaveReq{" +
                "LoginName='" + loginName + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", checkPass='" + checkPass + '\'' +
                '}';
    }
}
