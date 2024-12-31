package cn.lanqiao.newsmanagement.service;

import cn.lanqiao.newsmanagement.model.dto.tadmin.ResetPasswordQuery;
import cn.lanqiao.newsmanagement.model.pojo.TAdmin;
import jakarta.servlet.http.HttpServletRequest;

public interface ResetPasswordService {
    /**
     * 根据手机号查询用户
     */
    TAdmin queryByPhone(String phone, HttpServletRequest request);
    /**
     * 更新用户密码
     *
     */
    int updatePassword(ResetPasswordQuery resetPasswordQuery);
}
