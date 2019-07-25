package com.lbh.cfld.dao;

import com.lbh.cfld.model.UmsPermission;
import com.lbh.cfld.model.UserAdmin;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserDao {

    @Select("select * from ums_admin where id = #{id}")
    UserAdmin getOne(Integer id);

    @Select("select * from ums_admin where username = #{name}")
    UserAdmin getOneByName(String name);

    @Select("select * from ums_permission WHERE id in" +
            " (select permission_id from ums_role_permission_relation " +
            "WHERE role_id in (select role_id from ums_admin_role_relation WHERE admin_id = #{id}))")
    List<UmsPermission> getPermissionByUserId(Integer id);
}
