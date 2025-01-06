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
    //ct
    @Select("SELECT COUNT(*) FROM t_news;")
    Integer selectNewsTotal();

    @Select("SELECT * FROM t_news WHERE is_delete = 0 ORDER BY date DESC LIMIT #{pageNumber}, #{pageSize}")
    List<TNews> selectAllNews(Integer pageNumber,Integer pageSize);

    @Select("SELECT * FROM t_news WHERE is_delete = 0 AND title LIKE CONCAT('%', #{title}, '%') ORDER BY date DESC")
    List<TNews> selectNewsByTitle(String title);
    //ct

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
 /*@Select("SELECT n.*, s.sort_name as sort " +
         "FROM t_news n " +
         "LEFT JOIN t_sort s ON n.sort = s.sort_name " +
         "WHERE s.sort_name = #{sort} " +
         "AND n.is_delete = 0 " +
         "AND s.is_delete = 0 " +
         "ORDER BY n.date DESC " +
         "LIMIT 3")
    List<TNews> selectNewsBySort(String sort);*/

    //ct
    @Select("SELECT username,sort,content,date,title from t_news " +
            "         WHERE sort = #{sort} " +
            "         AND is_delete = 0  " +
            "         ORDER BY `date` DESC " +
            "         LIMIT 3")
    List<TNews> selectNewsBySort(String sort);
    //ct
}
