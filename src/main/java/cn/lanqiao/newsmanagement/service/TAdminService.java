package cn.lanqiao.newsmanagement.service;

import cn.lanqiao.newsmanagement.model.dto.TAdminQuery;
//import cn.lanqiao.newsmanagement.model.dto.tadmin.TAdminAdd;
import cn.lanqiao.newsmanagement.model.dto.tadmin.TAdminAdd;
import cn.lanqiao.newsmanagement.model.pojo.TAdmin;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.ibatis.annotations.Select;

import javax.xml.transform.Result;

public interface TAdminService {
    //查询手机号是否存在
    TAdmin queryByPhone(String phone);
    //查询登录用户的身份
    int queryIdentity(TAdminQuery tAdminQuery);

    /**
     * 登录功能
     */
    TAdmin login(TAdminQuery tAdminQuery);
    /**
     * 手机号检验
     */
    TAdmin queryByPhoneTwo(String phone);

    /**
     * 注册界面
     */
    int register(TAdminAdd tAdminAdd);

    // 查询用户名是否存在
    boolean isUsernameExists(String username);
}

