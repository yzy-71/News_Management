package cn.lanqiao.newsmanagement.service;

import cn.lanqiao.newsmanagement.model.pojo.TNews;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TNewsService {
    //新闻列表
//    List<TNews> selectAllNews();
    List<TNews> selectAllNews(Integer pageNum,Integer pageSize); //ct
    //按分类查询新闻
    List<TNews> selectNewsBySort(String sort);

    //ct
    //通过标题查询新闻
    List<TNews> selectNewsByTitle(String title);

    Integer selectNewsTotal();
    //ct
}
