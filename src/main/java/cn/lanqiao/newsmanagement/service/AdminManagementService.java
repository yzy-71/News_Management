package cn.lanqiao.newsmanagement.service;

import cn.lanqiao.newsmanagement.model.pojo.TAdmin;
import cn.lanqiao.newsmanagement.model.pojo.TNews;
import cn.lanqiao.newsmanagement.utils.PageHelper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface AdminManagementService {
    // 获取用户列表, 分页查询
    PageHelper<TAdmin> pageList(String username, int id, String password, String email, String phone, String date, int pageNum, int pageSize);
    //模糊查询用户, 分页查询
    PageHelper<TAdmin> fuzzyQueryAdmin(String username, int id, String password, String email, String phone, String date, int pageNum, int pageSize);
    //删除用户
    int deleteAdmin(int id);
    //编辑用户
    int updateAdmin(TAdmin tAdmin);
}
