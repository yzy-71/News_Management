package cn.lanqiao.newsmanagement.mapper;

import cn.lanqiao.newsmanagement.model.pojo.TNews;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TNewsMapper {
    @Select("SELECT * FROM t_news WHERE is_delete = 0 ORDER BY date DESC")
    List<TNews> selectAllNews();

 //按分类查询新闻
    @Select("SELECT * FROM t_news WHERE sort = #{sort} AND is_delete = 0 ORDER BY date DESC")
    List<TNews> selectNewsBySort(String sort);
}
