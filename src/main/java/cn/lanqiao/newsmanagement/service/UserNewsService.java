package cn.lanqiao.newsmanagement.service;

import cn.lanqiao.newsmanagement.model.dto.PageHelperQuery;
import cn.lanqiao.newsmanagement.model.pojo.TNews;
import cn.lanqiao.newsmanagement.utils.PageHelper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserNewsService {
    //查询一共有多少条数据
    int totalPage(TNews tNews);
    //分页查询
    PageHelper<TNews> PageHelperQuery(PageHelperQuery pageHelperQuery);
}
