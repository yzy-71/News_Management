package cn.lanqiao.newsmanagement.mapper;

import cn.lanqiao.newsmanagement.model.dto.TAdminQuery;
//import cn.lanqiao.newsmanagement.model.dto.tadmin.TAdminAdd;
import cn.lanqiao.newsmanagement.model.pojo.TAdmin;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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
    @Update("UPDATE t_admin SET user_id = id WHERE username = #{username} AND password = #{password} AND is_delete = 0")
    void updateUserId(TAdminQuery tAdminQuery);

    @Select("select * from t_admin where username=#{username} and password = #{password} and is_delete=0")
    TAdmin login(TAdminQuery tAdminQuery);

    /**
     * 注册界面
     */
    /**
     * 注册新用户
     */
    @Insert("INSERT INTO t_admin ( username, phone, password, identity, date, is_delete,user_id) " +
            "VALUES ( #{username}, #{phone}, #{password}, #{identity}, #{date}, 0, 1)")
    int register(TAdmin tAdmin);

    @Select("select * from t_admin where phone =#{phone} and is_delete=0")
    TAdmin queryByPhoneTwo(String phone);

    @Select("select * from t_admin where username = #{username} and is_delete = 0")
    TAdmin queryByUsername(String username);

    @Select("select count(*) from t_admin where username = #{username} and is_delete = 0")
    int countByUsername(String username);

}
