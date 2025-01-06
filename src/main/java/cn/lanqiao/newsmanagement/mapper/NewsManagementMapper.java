package cn.lanqiao.newsmanagement.mapper;

import cn.lanqiao.newsmanagement.model.pojo.TNews;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface NewsManagementMapper {
    //查询一共有多少条数据
    @Select("select COUNT(*) from t_news where is_delete=0 and audit=2")
    int totalNewsNum(TNews tNews);

    // 获取新闻列表, 分页查询
    @Select("select title,username,date from t_news where is_delete=0 and audit=2")
    List<TNews> pageList(String title,String username,String date,int pageNum, int pageSize, int offset);//offset起始索引

    //模糊查询一共有多少条数据
    @Select("select COUNT(*) from t_news where title like CONCAT('%', '新闻', '%') and is_delete = 0 and audit = 2")
    int fuzzyTotalNewsNum(TNews tNews);

    //模糊查询新闻, 分页查询
    @Select("select  title,username,date  from t_news where title like CONCAT('%', '新闻', '%') and is_delete = 0 and audit = 2")
    List<TNews> fuzzyQueryNews(String title,String username,String date,int pageNum, int pageSize, int offset);
}
