package com.lbh.cfld.service;


import com.lbh.cfld.shiro.shiroRealm.RealmAdmin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private RealmAdmin realmAdmin;

}
