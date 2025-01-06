package cn.lanqiao.newsmanagement.controller;

import cn.lanqiao.newsmanagement.model.dto.PageHelperQuery;
import cn.lanqiao.newsmanagement.model.pojo.TNews;
import cn.lanqiao.newsmanagement.service.NewsManagementService;
import cn.lanqiao.newsmanagement.utils.PageHelper;
import cn.lanqiao.newsmanagement.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/news")
public class NewsManagementController {
    @Autowired
    private NewsManagementService newsManagementService;
    // 获取新闻列表, 分页查询
    @RequestMapping("/list")
    public ResponseUtils list(@RequestBody PageHelperQuery pageHelperQuery) {
        PageHelper<TNews> pageHelper = newsManagementService.pageList(pageHelperQuery.getTitle(), pageHelperQuery.getUsername(), pageHelperQuery.getDate(), pageHelperQuery.getPageNum(), pageHelperQuery.getPageSize());
        return new ResponseUtils<>(200,"分页查询成功",pageHelper);
    }
    //模糊查询新闻
    @RequestMapping("/fuzzyList")
    public ResponseUtils fuzzyQueryNews(@RequestBody PageHelperQuery pageHelperQuery) {
        PageHelper<TNews> pageHelper = newsManagementService.fuzzyQueryNews(pageHelperQuery.getTitle(), pageHelperQuery.getUsername(), pageHelperQuery.getDate(), pageHelperQuery.getPageNum(), pageHelperQuery.getPageSize());
        return new ResponseUtils<>(200,"分页查询成功",pageHelper);
    }
}
