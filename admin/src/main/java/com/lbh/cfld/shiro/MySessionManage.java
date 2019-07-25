package com.lbh.cfld.shiro;


import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
@Component("sessionManager")
public class MySessionManage extends DefaultWebSessionManager {
    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response){
        String value = WebUtils.toHttp(request).getHeader("Authentication");
        HttpServletRequest a=(HttpServletRequest)request;
        System.out.println(a.getParameterValues("username"));
        if(StringUtils.isEmpty(value)){
            return super.getSessionId(request,response);
        }
        //请求头中获取到ID，抄袭原方法的操作对ID处理。然后返回ID
        request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, value);
        request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
        return value;
    }
}
