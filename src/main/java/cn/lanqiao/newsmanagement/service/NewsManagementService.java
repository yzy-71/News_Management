package cn.lanqiao.newsmanagement.service;

import cn.lanqiao.newsmanagement.model.pojo.TNews;
import cn.lanqiao.newsmanagement.utils.PageHelper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface NewsManagementService {
    // 获取新闻列表, 分页查询
    PageHelper<TNews> pageList(String title, String username, String date, int pageNum, int pageSize,int id);
    //模糊查询新闻, 分页查询
    PageHelper<TNews> fuzzyQueryNews(String title,String username,String date,int pageNum, int pageSize,int id);
    //删除新闻
    int deleteNews(int id);
    //查看新闻详情
    List<TNews> selectNews(int id);
}
