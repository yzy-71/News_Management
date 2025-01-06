package cn.lanqiao.newsmanagement.service;

import cn.lanqiao.newsmanagement.model.pojo.TNews;
import cn.lanqiao.newsmanagement.utils.PageHelper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface NewsManagementService {
    // 获取新闻列表, 分页查询
    PageHelper<TNews> pageList(String title, String username, String date, int pageNum, int pageSize);
    //模糊查询新闻
    PageHelper<TNews> fuzzyQueryNews(String title,String username,String date,int pageNum, int pageSize);
}
