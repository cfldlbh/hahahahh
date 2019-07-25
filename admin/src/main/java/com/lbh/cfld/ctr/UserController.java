package com.lbh.cfld.ctr;

import com.alibaba.fastjson.JSONObject;
import com.lbh.cfld.model.UserAdmin;
import com.lbh.cfld.service.UserService;
import com.lbh.cfld.utils.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @RequestMapping("/login")
    @ResponseBody
    public ResponseEntity login(@RequestBody String user) throws IllegalAccessException, InstantiationException {
        JSONObject parse = JSONObject.parseObject(user);
        return userService.login(parse.getString("username"),parse.getString("password"));
    }

    @RequestMapping("/info")
    public Map userInfo(){
        UserAdmin umsAdmin = userService.getOne(1);
        Map<String, Object> data = new HashMap();
        data.put("username", umsAdmin.getUsername());
        data.put("roles", new String[]{"ADMIN"});
        data.put("icon", umsAdmin.getIcon());
        data.put("code",200);
        return data;
    }
}
