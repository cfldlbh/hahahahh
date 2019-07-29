package com.lbh.cfld.utils;

import com.lbh.cfld.baseModel.BaseModel;

public class ResponseEntityGenarate {
    public static ResponseEntity genOK() throws IllegalAccessException, InstantiationException {
        ResponseEntity responseEntity = ResponseEntity.class.newInstance();
        responseEntity.setCode(200);
        responseEntity.setMsg("成功");
        return responseEntity;
    }

    public static ResponseEntity genOK(String msg, BaseModel model) throws IllegalAccessException, InstantiationException {
        String className = model.getClass().getName();
        ResponseEntity responseEntity = ResponseEntity.class.newInstance();
        responseEntity.setCode(200);
        responseEntity.setData(model);
        responseEntity.setMsg("成功");
        return responseEntity;
    }

    public static ResponseEntity genFail() throws IllegalAccessException, InstantiationException {
        ResponseEntity responseEntity = ResponseEntity.class.newInstance();
        responseEntity.setCode(4002);
        responseEntity.setMsg("失败");
        return responseEntity;
    }
    public static ResponseEntity genFail(Integer i ,String msg) throws IllegalAccessException, InstantiationException {
        ResponseEntity responseEntity = ResponseEntity.class.newInstance();
        responseEntity.setCode(i);
        responseEntity.setMsg(msg);
        return responseEntity;
    }
}
