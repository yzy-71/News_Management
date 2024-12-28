package cn.lanqiao.newsmanagement.mapper;

import cn.lanqiao.newsmanagement.model.dto.TAdminQuery;
import cn.lanqiao.newsmanagement.model.pojo.TAdmin;
import org.apache.ibatis.annotations.Select;

public interface TAdminMapper {
    //查询手机号是否存在
    @Select("select * from t_admin where phone =#{phone} and is_delete=0")
    TAdmin queryByPhone(String phone);
    //查询登录用户的身份
    @Select("select identity from t_admin where phone =#{phone} and is_delete=0")
    int queryIdentity(TAdminQuery tAdminQuery);
}
