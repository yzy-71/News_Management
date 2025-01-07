package cn.lanqiao.newsmanagement.mapper;

import cn.lanqiao.newsmanagement.model.pojo.TNews;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface NewsManagementMapper {
    //查询一共有多少条数据
    @Select("select COUNT(*) from t_news where is_delete=0 and audit=1")
    int totalNewsNum(TNews tNews);

    // 获取新闻列表, 分页查询
    @Select("select title,username,date,id from t_news where is_delete=0 and audit=1")
    List<TNews> pageList(String title,String username,String date,int pageNum, int pageSize, int id,int offset);//offset起始索引

    //模糊查询一共有多少条数据
    @Select("select COUNT(*) from t_news where title like CONCAT('%', #{title}, '%') and is_delete = 0 and audit = 1")
    int fuzzyTotalNewsNum(TNews tNews);

    //模糊查询新闻, 分页查询
    @Select("select title,username,date,id from t_news where title like CONCAT('%', #{title}, '%') and is_delete = 0 and audit = 1")
    List<TNews> fuzzyQueryNews(String title,String username,String date,int pageNum, int pageSize, int id,int offset);

    //删除新闻
    @Update("update t_news set is_delete =1 where id=#{id} and audit=1")
    int deleteNews(int id);

    //查看新闻详情
    @Select("select id,title,username,date,content from t_news where id=#{id} and is_delete=0 and audit=1")
    List<TNews> selectNews(int id);
}
