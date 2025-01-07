package cn.lanqiao.newsmanagement.service.impl;

import cn.lanqiao.newsmanagement.mapper.AdminManagementMapper;
import cn.lanqiao.newsmanagement.model.pojo.TAdmin;
import cn.lanqiao.newsmanagement.model.pojo.TNews;
import cn.lanqiao.newsmanagement.service.AdminManagementService;
import cn.lanqiao.newsmanagement.utils.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminManagementServiceImpl implements AdminManagementService {
    @Autowired
    private AdminManagementMapper adminManagementMapper;

    @Override
    public PageHelper<TAdmin> pageList(String username, int id, String password, String email, String phone, String date, int pageNum, int pageSize) {
        //一共有多少条数据
        int totalNum = adminManagementMapper.totalAdminNum(new TAdmin());
        //数据库中初始索引
        int offset=(pageNum -1)*pageSize;
        List<TAdmin> tAdmins = adminManagementMapper.pageList(username, id, password, email, phone,date,pageNum,pageSize,offset);
        //创建分页对象
        PageHelper<TAdmin> pageHelper = new PageHelper<>();
        //当前页码
        pageHelper.setPageNum(pageNum);
        //每页的数据条数
        pageHelper.setPageSize(pageSize);
        //总页数
        pageHelper.setPages((int)Math.ceil((double) totalNum /pageSize));
        pageHelper.setList(tAdmins);
        return pageHelper;
    }

    @Override
    public PageHelper<TAdmin> fuzzyQueryAdmin(String username, int id, String password, String email, String phone, String date, int pageNum, int pageSize) {
        //一共有多少条数据
        int fuzzyTotalTAminNum = adminManagementMapper.fuzzyTotalAdminNum(new TAdmin());
        //数据库中初始索引
        int offset=(pageNum -1)*pageSize;
        List<TAdmin> tAdmins = adminManagementMapper.fuzzyQueryAdmin(username, id, password, email, phone,date,pageNum,pageSize,offset);
        //创建分页对象
        PageHelper<TAdmin> pageHelper = new PageHelper<>();
        //当前页码
        pageHelper.setPageNum(pageNum);
        //每页的数据条数
        pageHelper.setPageSize(pageSize);
        //总页数
        pageHelper.setPages((int)Math.ceil((double) fuzzyTotalTAminNum /pageSize));
        pageHelper.setList(tAdmins);
        return pageHelper;
    }

    @Override
    public int deleteAdmin(int id) {
        int result = adminManagementMapper.deleteAdmin(id);
        if (result>0){
            return 1;
        }else {
            return 0;
        }
    }

    @Override
    public int updateAdmin(TAdmin tAdmin) {
        int result = adminManagementMapper.updateAdmin(tAdmin);
        if (result > 0) {
            return 1;
        } else {
            return 0;
        }
    }

}
