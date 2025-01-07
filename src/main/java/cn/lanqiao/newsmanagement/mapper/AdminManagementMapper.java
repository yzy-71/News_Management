package cn.lanqiao.newsmanagement.mapper;

import cn.lanqiao.newsmanagement.model.pojo.TAdmin;
import cn.lanqiao.newsmanagement.model.pojo.TNews;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface AdminManagementMapper {
    //查询一共有多少条数据
    @Select("select COUNT(*) from t_admin where is_delete=0 and identity=1")
    int totalAdminNum(TAdmin tAdmin);

    // 获取用户列表, 分页查询
    @Select("select username,id,password,email,phone,date from t_admin where is_delete=0 and identity=1")
    List<TAdmin> pageList(String username, int id, String password, String email, String phone, String date, int pageNum, int pageSize, int offset);//offset起始索引

    //模糊查询一共有多少条数据
    @Select("select COUNT(*) from t_admin where username like CONCAT('%', #{username}, '%') and is_delete = 0 and identity = 1")
    int fuzzyTotalAdminNum(TAdmin tAdmin);

    //模糊查询用户, 分页查询
    @Select("select username,id,password,email,phone,date from t_admin where username like CONCAT('%', #{username}, '%') and is_delete=0 and identity=1")
    List<TAdmin> fuzzyQueryAdmin(String username, int id, String password, String email, String phone, String date, int pageNum, int pageSize, int offset);//offset起始索引

    //删除用户
    @Update("update t_admin set is_delete =1 where id=#{id} and identity=1")
    int deleteAdmin(int id);

    //编辑用户
    @Update("update t_admin set username=#{username}, password=#{password}, email=#{email}, phone=#{phone} where id=#{id} and identity=1")
    int updateAdmin(TAdmin tAdmin);
}
