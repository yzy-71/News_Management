package cn.lanqiao.newsmanagement.service.impl;

import cn.hutool.core.util.PageUtil;
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

    //ct
    @Override
    public Integer selectNewsTotal() {
        return tNewsMapper.selectNewsTotal();
    }
  @Override
  public Integer updateAudit(String id) {
    return tNewsMapper.updateAudit(id);
  }

  @Override
  public Integer updateAuditno(String id) {
    return tNewsMapper.updateAuditno(id);
  }

  @Override
  public Object updateRemarks(String id, String remarks) {
    return tNewsMapper.updateRemarks(id,remarks);
  }

  //ct
    //查询新闻类别功能
    @Override
    public List<TNews> selectAllNews(Integer pageNum,Integer pageSize) { //ct
        int[] startEnd = PageUtil.transToStartEnd(pageNum, pageSize); //ct
        List<TNews> tNews = tNewsMapper.selectAllNews(startEnd[0],startEnd[1]); //ct
        if (tNews != null) {
            return tNews;
        } else {
            return null;
        }
    }

    //ct
    //查询新闻类别功能
    @Override
    public List<TNews> selectAllNewsList(Integer pageNum, Integer pageSize) {
        Integer offset = (pageNum - 1) * pageSize;
        List<TNews> tNews = tNewsMapper.selectAllNewsList(offset, pageSize);
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


    //ct
    @Override
    public List<TNews> selectNewsByTitle(String title) {
        List<TNews> tNews = tNewsMapper.selectNewsByTitle(title);
        if (tNews != null) {
            return tNews;
        } else {
            return null;
        }
    }
}
