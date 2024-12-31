package cn.lanqiao.newsmanagement.mapper;

import cn.lanqiao.newsmanagement.model.dto.tadmin.ResetPasswordQuery;
import cn.lanqiao.newsmanagement.model.pojo.TAdmin;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface ResetPasswordMapper {
    /**
     * 根据手机号查询用户信息
     */
    @Select("SELECT * FROM t_admin WHERE phone = #{phone} AND is_delete = 0")
    TAdmin queryByPhone(String phone);
    /**
     * 更新用户密码
     */
    @Update("UPDATE t_admin SET password = #{newPassword} WHERE phone = #{phone} AND is_delete = 0")
    int updatePassword(ResetPasswordQuery resetPasswordQuery);
}
