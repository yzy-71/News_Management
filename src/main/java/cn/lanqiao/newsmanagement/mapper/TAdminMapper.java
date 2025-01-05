package cn.lanqiao.newsmanagement.mapper;

import cn.lanqiao.newsmanagement.model.dto.TAdminQuery;
//import cn.lanqiao.newsmanagement.model.dto.tadmin.TAdminAdd;
import cn.lanqiao.newsmanagement.model.pojo.TAdmin;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

public interface TAdminMapper {
    //查询手机号是否存在
    @Select("select * from t_admin where phone =#{phone} and is_delete=0")
    TAdmin queryByPhone(String phone);
    //查询登录用户的身份
    @Select("select identity from t_admin where phone =#{phone} and is_delete=0")
    int queryIdentity(TAdminQuery tAdminQuery);

    /**
     * 登录功能
     */
    @Select("select * from t_admin where username=#{username}and password = #{password} and is_delete=0")
    TAdmin login(TAdminQuery tAdminQuery);

    /**
     * 注册界面
     */
    /**
     * 注册新用户
     */
    @Insert("INSERT INTO t_admin (id, username, phone, password, identity, date, is_delete) " +
            "VALUES (#{id}, #{username}, #{phone}, #{password}, #{identity}, #{date}, 0)")
    int register(TAdmin tAdmin);

    @Select("select * from t_admin where phone =#{phone} and is_delete=0")
    TAdmin queryByPhoneTwo(String phone);


}
