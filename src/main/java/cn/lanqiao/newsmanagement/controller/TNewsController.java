package cn.lanqiao.newsmanagement.controller;

import cn.lanqiao.newsmanagement.model.pojo.TNews;
import cn.lanqiao.newsmanagement.service.TNewsService;
import cn.lanqiao.newsmanagement.utils.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/news")
public class TNewsController {
    @Autowired
    private TNewsService tNewsService;
    /**
     * 03-加载新闻数据
     */
    @RequestMapping("/selectAllNews")
    public ResponseUtils selectAllNews(Integer pageNum, Integer pageSize) {
        try {
            List<TNews> tNewsList = tNewsService.selectAllNews(pageNum, pageSize);
            Integer total = tNewsService.selectNewsTotal();
            if (tNewsList != null) {
                List<TNews> newsVoList = new ArrayList<>();
                for (TNews news : tNewsList) {
                    TNews newsVo = new TNews();
                    newsVo.setId(news.getId());
                    newsVo.setTitle(news.getTitle());
                    newsVo.setUsername(news.getUsername());
                    newsVo.setSort(news.getSort());
                    newsVo.setContent(news.getContent());
                    newsVo.setDate(news.getDate());
                    newsVo.setRead(news.getRead());
                    newsVoList.add(newsVo);
                }
                return new ResponseUtils(200, "查询成功", newsVoList, total);
            } else {
                return new ResponseUtils(200, "暂无审核通过的新闻", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseUtils(500, "查询失败：" + e.getMessage());
        }
    }



    //ct
    @RequestMapping("/selectNews")
    public ResponseUtils selectNewsByTitle(String title,Integer pageNum,Integer pageSize) {
        System.out.println("title = " + title);
        List<TNews> tNewsList = null;
        try {
            if (!title.equals("")){
                tNewsList = tNewsService.selectNewsByTitle(title);
            }else {
                tNewsList = tNewsService.selectAllNews(pageNum, pageSize);
            }
            if (tNewsList != null) {
                List<TNews> newsVoList = new ArrayList<>();
                for (TNews news : tNewsList) {
                    TNews newsVo = new TNews();
                    newsVo.setId(news.getId());
                    newsVo.setTitle(news.getTitle());
                    newsVo.setUsername(news.getUsername());
                    newsVo.setSort(news.getSort());
                    newsVo.setContent(news.getContent());
                    newsVo.setDate(news.getDate());
                    newsVo.setRead(news.getRead());
                    newsVoList.add(newsVo);
                }
                return new ResponseUtils(200, "查询成功", newsVoList);
            } else {
                return new ResponseUtils(400, "查询失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    //ct

    /**
     * 04-按分类查询新闻
     */
    @RequestMapping("/selectNewsBySort")
    public ResponseUtils selectNewsBySort (String sort){
        try {
            List<TNews> tNewsList = tNewsService.selectNewsBySort(sort);
            if (tNewsList != null) {
                List<TNews> newsVoList = new ArrayList<>();
                for (TNews news : tNewsList) {
                    TNews newsVo = new TNews();
                    newsVo.setId(news.getId());
                    newsVo.setTitle(news.getTitle());
                    newsVo.setUsername(news.getUsername());
                    newsVo.setSort(news.getSort());
                    newsVo.setContent(news.getContent());
                    newsVo.setDate(news.getDate());
                    newsVo.setRead(news.getRead());
                    newsVoList.add(newsVo);
                }
                return new ResponseUtils(200, "查询成功", newsVoList);
            } else {
                return new ResponseUtils(200, "该分类暂无数据", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseUtils(500, "查询失败：" + e.getMessage());
        }
    }
}
