package cn.lanqiao.newsmanagement.mapper;

import cn.lanqiao.newsmanagement.model.pojo.TNews;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TNewsMapper {

//    //ct
//    @Select("SELECT COUNT(*) FROM t_news;")
//    Integer selectNewsTotal();
//
//    @Select("SELECT * FROM t_news WHERE is_delete = 0 ORDER BY date DESC LIMIT #{pageNumber}, #{pageSize}")
//    List<TNews> selectAllNews(Integer pageNumber,Integer pageSize);
//
//    @Select("SELECT * FROM t_news WHERE is_delete = 0 AND title LIKE CONCAT('%', #{title}, '%') ORDER BY date DESC")
//    List<TNews> selectNewsByTitle(String title);
//    //ct
//
// @Select("SELECT n.*, s.sort_name as sort " +
//         "FROM t_news n " +
//         "LEFT JOIN t_sort s ON n.sort = s.sort_name " +
//         "WHERE s.sort_name = #{sort} " +
//         "AND n.is_delete = 0 " +
//         "AND s.is_delete = 0 " +
//         "ORDER BY n.date DESC " +
//         "LIMIT 3")
//    List<TNews> selectNewsBySort(String sort);
//
////    //ct
////    @Select("SELECT username,sort,content,date,title from t_news " +
////            "         WHERE sort = #{sort} " +
////            "         AND is_delete = 0  " +
////            "         ORDER BY `date` DESC " +
////            "         LIMIT 3")
////    List<TNews> selectNewsBySort(String sort);

    //查询总数量（只统计审核通过的新闻）
    @Select("SELECT COUNT(*) FROM t_news WHERE is_delete = 0 AND audit = 1")
    Integer selectNewsTotal();

    //查询所有新闻（只查询审核通过的）
    @Select("SELECT * FROM t_news WHERE is_delete = 0 AND audit = 1 ORDER BY date DESC LIMIT #{pageNumber}, #{pageSize}")
    List<TNews> selectAllNews(Integer pageNumber, Integer pageSize);

    //按标题查询新闻（只查询审核通过的）
    @Select("SELECT * FROM t_news WHERE is_delete = 0 AND audit = 1 AND title LIKE CONCAT('%', #{title}, '%') ORDER BY date DESC")
    List<TNews> selectNewsByTitle(String title);

    //按分类查询新闻（只查询审核通过的）
    @Select("SELECT n.*, s.sort_name as sort " +
            "FROM t_news n " +
            "LEFT JOIN t_sort s ON n.sort = s.sort_name " +
            "WHERE s.sort_name = #{sort} " +
            "AND n.is_delete = 0 " +
            "AND n.audit = 1 " +
            "AND s.is_delete = 0 " +
            "ORDER BY n.date DESC " +
            "LIMIT 3")
    List<TNews> selectNewsBySort(String sort);
}
