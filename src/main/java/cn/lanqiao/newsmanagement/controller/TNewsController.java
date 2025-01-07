package cn.lanqiao.newsmanagement.controller;
import cn.hutool.db.PageResult;
import cn.lanqiao.newsmanagement.model.pojo.TNews;
import cn.lanqiao.newsmanagement.service.TNewsService;
import cn.lanqiao.newsmanagement.utils.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/news")
public class TNewsController {
    @Autowired
    private TNewsService tNewsService;
    /**
     * 03-加载新闻审核
     */
    @RequestMapping("/selectAllNews")
    public ResponseUtils selectAllNews(Integer pageNum,Integer pageSize) {
        try {
            List<TNews> tNewsList = tNewsService.selectAllNews(pageNum,pageSize); //ct
            Integer total =tNewsService.selectNewsTotal();
            total=(total + pageSize - 1) / pageSize;
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
                return new ResponseUtils(200, "查询成功", newsVoList,total);
            } else {
                return new ResponseUtils(400, "查询失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    /**
     * 03-加载新闻列表
     */
    @RequestMapping("/selectAllNewsList")
    public ResponseUtils selectAllNewsList(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "3") Integer pageSize) {
        try {
            // 确保页码不小于1
            pageNum = Math.max(1, pageNum);
            // 计算偏移量，确保不会出现负数
            Integer offset = Math.max(0, (pageNum - 1) * pageSize);

            List<TNews> tNewsList = tNewsService.selectAllNewsList(pageNum, pageSize);
            Integer total = tNewsService.selectNewsTotal();

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

            // 计算总页数
            Integer totalPages = (total + pageSize - 1) / pageSize;
            return new ResponseUtils(200, "查询成功", newsVoList, totalPages);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
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
//    @RequestMapping("/selectNewsBySort")
//    public ResponseUtils selectNewsBySort (String sort){
//        try {
//            List<TNews> tNewsList = tNewsService.selectNewsBySort(sort);
//            if (tNewsList != null) {
//                List<TNews> newsVoList = new ArrayList<>();
//                for (TNews news : tNewsList) {
//                    TNews newsVo = new TNews();
//                    newsVo.setId(news.getId());
//                    newsVo.setTitle(news.getTitle());
//                    newsVo.setUsername(news.getUsername());
//                    newsVo.setSort(news.getSort());
//                    newsVo.setContent(news.getContent());
//                    newsVo.setDate(news.getDate());
//                    newsVo.setRead(news.getRead());
//                    newsVoList.add(newsVo);
//                }
//                return new ResponseUtils(200, "查询成功", newsVoList);
//            } else {
//                return new ResponseUtils(200, "该分类暂无数据", null);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ResponseUtils(500, "查询失败：" + e.getMessage());
//        }
//    }
    /**
     * 04-按分类查询新闻
     */
    @RequestMapping("/selectNewsBySort")
    public ResponseUtils selectNewsBySort(
            @RequestParam String sort,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "3") Integer pageSize) {
        try {
            // 获取分类新闻列表
            List<TNews> tNewsList = tNewsService.selectNewsBySort(sort, pageNum, pageSize);
            // 获取该分类的总新闻数
            Integer total = tNewsService.selectNewsBySortTotal(sort);
            // 计算总页数
            Integer totalPages = (total + pageSize - 1) / pageSize;

            if (tNewsList != null && !tNewsList.isEmpty()) {
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
                return new ResponseUtils<>(200, "查询成功", newsVoList, totalPages);
            } else {
                return new ResponseUtils<>(200, "该分类暂无数据", null);
            }
        } catch (Exception e) {
            return new ResponseUtils<>(500, "查询失败：" + e.getMessage());
        }
    }

    @RequestMapping("/updateAudit")
    public Integer updateA(String id){
        return tNewsService.updateAudit(id);
    }
    @RequestMapping("/updateAuditno")
    public Integer updateB(String id){
        return tNewsService.updateAuditno(id);
    }
    @PostMapping("/remarks")
    public ResponseUtils updateNewsRemakrs(String id, @RequestParam String remarks){
        return new ResponseUtils(200,"更新成功",tNewsService.updateRemarks(id,remarks));
    }
}