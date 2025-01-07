package cn.lanqiao.newsmanagement.mapper;

import cn.lanqiao.newsmanagement.model.pojo.TNews;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
public interface TNewsMapper {
  @Update("UPDATE t_news SET remarks = #{remarks} WHERE id = #{id}")
  int updateRemarks(@Param("id") String id, @Param("remarks") String remarks);

  @Update("UPDATE t_news SET audit=3 WHERE id=#{id} ")
  Integer updateAuditno(String id);

  @Update("UPDATE t_news SET audit=1 WHERE id=#{id} ")
  Integer updateAudit(String id);

  @Select("SELECT COUNT(*) FROM t_news;")
  Integer selectNewsTotal();

  //查询所有新闻（只查询审核通过的）
  @Select("SELECT * FROM t_news WHERE is_delete = 0 ORDER BY date DESC LIMIT #{pageNumber}, #{pageSize}")
  List<TNews> selectAllNews(Integer pageNumber, Integer pageSize);

  @Select("SELECT * FROM t_news WHERE is_delete = 0 AND audit = 1 ORDER BY date DESC LIMIT #{pageNumber}, #{pageSize}")
  List<TNews> selectAllNewsList(@Param("pageNumber") Integer pageNumber, @Param("pageSize") Integer pageSize);

  //按标题查询新闻（只查询审核通过的）
  @Select("SELECT * FROM t_news WHERE is_delete = 0 AND audit = 1 AND title LIKE CONCAT('%', #{title}, '%') ORDER BY date DESC")
  List<TNews> selectNewsByTitle(String title);

  //按分类查询新闻
//  @Select("SELECT n.*, s.sort_name as sort " +
//          "FROM t_news n " +
//          "LEFT JOIN t_sort s ON n.sort = s.sort_name " +
//          "WHERE s.sort_name = #{sort} " +
//          "AND n.is_delete = 0 " +
//          "AND n.audit = 1 " +
//          "AND s.is_delete = 0 " +
//          "ORDER BY n.date DESC " +
//          "LIMIT 3")
//  List<TNews> selectNewsBySort(String sort);
  @Select("SELECT COUNT(*) FROM t_news n " +
          "LEFT JOIN t_sort s ON n.sort = s.sort_name " +
          "WHERE s.sort_name = #{sort} " +
          "AND n.is_delete = 0 " +
          "AND n.audit = 1 " +
          "AND s.is_delete = 0")
  Integer selectNewsBySortTotal(String sort);

  @Select("SELECT n.*, s.sort_name as sort " +
          "FROM t_news n " +
          "LEFT JOIN t_sort s ON n.sort = s.sort_name " +
          "WHERE s.sort_name = #{sort} " +
          "AND n.is_delete = 0 " +
          "AND n.audit = 1 " +
          "AND s.is_delete = 0 " +
          "ORDER BY n.date DESC " +
          "LIMIT #{offset}, #{pageSize}")
  List<TNews> selectNewsBySort(@Param("sort") String sort,
                               @Param("offset") Integer offset,
                               @Param("pageSize") Integer pageSize);
}
