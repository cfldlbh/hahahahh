package com.lbh.cfld.shiro.shiroRealm;

import com.lbh.cfld.model.UmsPermission;
import com.lbh.cfld.model.UserAdmin;
import com.lbh.cfld.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;

@Component
public class RealmAdmin extends AuthorizingRealm {
    @Autowired
    private UserService userService;
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        UserAdmin loginUser =(UserAdmin) principalCollection.getPrimaryPrincipal();
        List<UmsPermission> permissions = null;
        if(loginUser!=null){
           permissions =  userService.getPermissionByUserId(loginUser.getId());
            if(permissions !=null && permissions.size()>0){
                for(UmsPermission s : permissions){
                    simpleAuthorizationInfo.addStringPermission(s.getValue());
                }
            }
            return simpleAuthorizationInfo;
        }else {
            return null;
        }
    }

    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken user = (UsernamePasswordToken)authenticationToken;
        String userName = user.getUsername();
        UserAdmin currentUser = userService.getOneByName(userName);
        if (currentUser==null){
            return null;
        }
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(user,user.getPassword(),this.getClass().getName());
        return simpleAuthenticationInfo;
    }
}
