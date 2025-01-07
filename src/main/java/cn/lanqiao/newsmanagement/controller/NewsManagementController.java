package cn.lanqiao.newsmanagement.controller;

import cn.lanqiao.newsmanagement.model.dto.PageHelperQuery;
import cn.lanqiao.newsmanagement.model.dto.TNewsDelete;
import cn.lanqiao.newsmanagement.model.pojo.TNews;
import cn.lanqiao.newsmanagement.service.NewsManagementService;
import cn.lanqiao.newsmanagement.utils.PageHelper;
import cn.lanqiao.newsmanagement.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/news")
public class NewsManagementController {
    @Autowired
    private NewsManagementService newsManagementService;
    // 获取新闻列表, 分页查询
    @RequestMapping("/list")
    public ResponseUtils list(@RequestBody PageHelperQuery pageHelperQuery) {
        try {
        PageHelper<TNews> pageHelper = newsManagementService.pageList(pageHelperQuery.getTitle(), pageHelperQuery.getUsername(), pageHelperQuery.getDate(), pageHelperQuery.getPageNum(), pageHelperQuery.getPageSize(), pageHelperQuery.getId());
        return new ResponseUtils<>(200,"分页查询成功",pageHelper);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    //模糊查询新闻, 分页查询
    @RequestMapping("/search")
    public ResponseUtils fuzzyQueryNews(@RequestBody PageHelperQuery pageHelperQuery) {
        try {
        PageHelper<TNews> pageHelper = newsManagementService.fuzzyQueryNews(pageHelperQuery.getTitle(), pageHelperQuery.getUsername(), pageHelperQuery.getDate(), pageHelperQuery.getPageNum(), pageHelperQuery.getPageSize(), pageHelperQuery.getId());
        return new ResponseUtils<>(200,"分页查询成功",pageHelper);
    } catch (Exception e) {
        e.printStackTrace();
        throw new RuntimeException(e);
    }
    }
    //删除功能
    @RequestMapping("/delete")
    public ResponseUtils delete(@RequestBody TNewsDelete tNewsDelete){
        try {
            int result = newsManagementService.deleteNews(tNewsDelete.getId());
            if (result==1){
                return new ResponseUtils(200,"删除成功");
            }else {
                return new ResponseUtils(300,"删除失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    //查看新闻详情
    @RequestMapping("select")
    public ResponseUtils select(@RequestBody TNews tNews){
        try {
            System.out.println("接收到的新闻ID: " + tNews.getId());
            List<TNews> news = newsManagementService.selectNews(tNews.getId());

            if (news != null && !news.isEmpty()){
                TNews newsDetail = news.get(0);
                System.out.println("查询到的新闻详情: " + newsDetail); // 添加日志
                return new ResponseUtils<>(200, "查看新闻详情成功", newsDetail);
            } else {
                System.out.println("未找到ID为 " + tNews.getId() + " 的新闻"); // 添加日志
                return new ResponseUtils<>(404, "未找到新闻", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseUtils<>(500, "系统错误: " + e.getMessage());
        }
    }
}
