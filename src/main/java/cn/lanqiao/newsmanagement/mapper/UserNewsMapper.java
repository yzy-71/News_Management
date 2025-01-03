package cn.lanqiao.newsmanagement.mapper;

import cn.lanqiao.newsmanagement.model.dto.PageHelperQuery;
import cn.lanqiao.newsmanagement.model.pojo.TNews;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserNewsMapper {
    //查询一共有多少条数据
    @Select("select count(*) from t_news")
    int totalPage(TNews tNews);

    //分页查询
    @Select("select * from t_news")
    List<TNews> PagingQuery(PageHelperQuery pageHelperQuery,int offset);
}
