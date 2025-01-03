package cn.lanqiao.newsmanagement.service;

import cn.lanqiao.newsmanagement.model.pojo.TNews;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TNewsService {
    //新闻列表
    List<TNews> selectAllNews();
    //按分类查询新闻
    List<TNews> selectNewsBySort(String sort);
}
