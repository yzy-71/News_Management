package cn.lanqiao.newsmanagement.controller;

import cn.lanqiao.newsmanagement.model.dto.PageHelperQuery;
import cn.lanqiao.newsmanagement.model.pojo.TNews;
import cn.lanqiao.newsmanagement.service.UserNewsService;
import cn.lanqiao.newsmanagement.utils.PageHelper;
import cn.lanqiao.newsmanagement.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tNews")
public class UserNewsController {
    @Autowired
    private UserNewsService userNewsService;
    //分页查询
    @RequestMapping("/PagingQuery")
    public ResponseUtils PagingQuery(@RequestBody PageHelperQuery pageHelperQuery){
        PageHelper<TNews> pageHelper=userNewsService.PageHelperQuery(pageHelperQuery);
        return new ResponseUtils<>(200,"分页查询成功",pageHelper);
    }
}
