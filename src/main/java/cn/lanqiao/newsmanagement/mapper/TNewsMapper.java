package cn.lanqiao.newsmanagement.mapper;

import cn.lanqiao.newsmanagement.model.pojo.TNews;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TNewsMapper {
    @Select("SELECT * FROM t_news WHERE is_delete = 0 ORDER BY date DESC")
    List<TNews> selectAllNews();

 //按分类查询新闻
//    @Select("SELECT * FROM t_news WHERE sort = #{sort} AND is_delete = 0 ORDER BY date DESC")
    //多表查询
 // 修改为多表查询
// @Select("SELECT n.*, s.sort_name as sort " +
//         "FROM t_news n " +
//         "LEFT JOIN t_sort s ON n.sort = s.sort_name " +
//         "WHERE s.sort_name = #{sort} " +
//         "AND n.is_delete = 0 " +
//         "AND s.is_delete = 0 " +
//         "ORDER BY n.date DESC")
 // 修改查询语句，添加 LIMIT 3
 @Select("SELECT n.*, s.sort_name as sort " +
         "FROM t_news n " +
         "LEFT JOIN t_sort s ON n.sort = s.sort_name " +
         "WHERE s.sort_name = #{sort} " +
         "AND n.is_delete = 0 " +
         "AND s.is_delete = 0 " +
         "ORDER BY n.date DESC " +
         "LIMIT 3")
    List<TNews> selectNewsBySort(String sort);
}
