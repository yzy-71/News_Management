package cn.lanqiao.newsmanagement.service.impl;

import cn.lanqiao.newsmanagement.mapper.TNewsMapper;
import cn.lanqiao.newsmanagement.model.pojo.TNews;
import cn.lanqiao.newsmanagement.service.TNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TNewsServiceImpl implements TNewsService {
    @Autowired
    private TNewsMapper tNewsMapper;
    //查询新闻类别功能
    @Override
    public List<TNews> selectAllNews() {
        List<TNews> tNews = tNewsMapper.selectAllNews();
        if (tNews != null) {
            return tNews;
        } else {
            return null;
        }
    }
    //按分类查询新闻
    @Override
    public List<TNews> selectNewsBySort(String sort) {
        List<TNews> tNews = tNewsMapper.selectNewsBySort(sort);
        if (tNews != null) {
            return tNews;
        } else {
            return null;
        }
    }
}