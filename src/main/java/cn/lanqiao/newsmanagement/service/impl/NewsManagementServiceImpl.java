package cn.lanqiao.newsmanagement.service.impl;

import cn.lanqiao.newsmanagement.mapper.NewsManagementMapper;
import cn.lanqiao.newsmanagement.model.pojo.TNews;
import cn.lanqiao.newsmanagement.service.NewsManagementService;
import cn.lanqiao.newsmanagement.utils.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsManagementServiceImpl implements NewsManagementService {
    @Autowired
    private NewsManagementMapper newsManagementMapper;

    @Override
    public PageHelper<TNews> pageList(String title, String username, String date, int pageNum, int pageSize) {
        //一共有多少条数据
        int totalNum = newsManagementMapper.totalNewsNum(new TNews());
        //数据库中初始索引
        int offset=(pageNum -1)*pageSize;
        List<TNews> tNews = newsManagementMapper.pageList(title, username, date, pageNum, pageSize, offset);
        //创建分页对象
        PageHelper<TNews> pageHelper = new PageHelper<>();
        //当前页码
        pageHelper.setPageNum(pageNum);
        //每页的数据条数
        pageHelper.setPageSize(pageSize);
        //总页数
        pageHelper.setPages((int)Math.ceil((double) totalNum /pageSize));
        pageHelper.setList(tNews);
        return pageHelper;
    }

    @Override
    public PageHelper<TNews> fuzzyQueryNews(String title, String username, String date, int pageNum, int pageSize) {
        //一共有多少条数据
        int fuzzyTotalNewsNum = newsManagementMapper.fuzzyTotalNewsNum(new TNews());
        //数据库中初始索引
        int offset=(pageNum -1)*pageSize;
        List<TNews> tNews = newsManagementMapper.fuzzyQueryNews(title, username, date, pageNum, pageSize, offset);
        //创建分页对象
        PageHelper<TNews> pageHelper = new PageHelper<>();
        //当前页码
        pageHelper.setPageNum(pageNum);
        //每页的数据条数
        pageHelper.setPageSize(pageSize);
        //总页数
        pageHelper.setPages((int)Math.ceil((double) fuzzyTotalNewsNum /pageSize));
        pageHelper.setList(tNews);
        return pageHelper;
    }
}
