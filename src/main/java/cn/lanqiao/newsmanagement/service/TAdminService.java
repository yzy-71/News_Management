package cn.lanqiao.newsmanagement.service;

import cn.lanqiao.newsmanagement.model.dto.TAdminQuery;
import cn.lanqiao.newsmanagement.model.pojo.TAdmin;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.ibatis.annotations.Select;

public interface TAdminService {
    //查询手机号是否存在
    TAdmin queryByPhone(String phone);
    //查询登录用户的身份
    int queryIdentity(TAdminQuery tAdminQuery);
}
