package com.lbh.cfld.service;

import com.alibaba.fastjson.JSONObject;
import com.lbh.cfld.dao.UserDao;
import com.lbh.cfld.model.UmsPermission;
import com.lbh.cfld.model.UserAdmin;
import com.lbh.cfld.shiro.shiroRealm.RealmAdmin;
import com.lbh.cfld.utils.ResponseEntity;
import com.lbh.cfld.utils.ResponseEntityGenarate;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private RealmAdmin realmAdmin;

    public UserAdmin getOne(Integer id){
        return userDao.getOne(id);
    }

    public UserAdmin getOneByName(String name){
        return userDao.getOneByName(name);
    }

    public ResponseEntity login(String userName, String passWord) throws InstantiationException, IllegalAccessException {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken to = new UsernamePasswordToken(userName, passWord);
        try {
            subject.login(to);
        }catch (AuthenticationException e){
            ResponseEntity responseEntity = ResponseEntityGenarate.genFail(4002,"登录失败");
            return responseEntity;
        }
        if(subject.isAuthenticated()){
            ResponseEntity responseEntity = ResponseEntityGenarate.genOK();
            responseEntity.setToken(subject.getSession().getId());
            return responseEntity;
        }else{
            ResponseEntity responseEntity = ResponseEntityGenarate.genFail(4002,"未登录");
            return responseEntity;
        }
    }

    public List<UmsPermission> getPermissionByUserId(Integer id){
        List<UmsPermission> list = userDao.getPermissionByUserId(id);
        return list;
    }
}
