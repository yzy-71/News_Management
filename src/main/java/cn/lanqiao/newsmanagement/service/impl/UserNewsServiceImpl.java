package cn.lanqiao.newsmanagement.service.impl;

import cn.lanqiao.newsmanagement.mapper.UserNewsMapper;
import cn.lanqiao.newsmanagement.model.dto.PageHelperQuery;
import cn.lanqiao.newsmanagement.model.pojo.TNews;
import cn.lanqiao.newsmanagement.service.UserNewsService;
import cn.lanqiao.newsmanagement.utils.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserNewsServiceImpl implements UserNewsService {
    @Autowired
    private UserNewsMapper userNewsMapper;
    @Override
    public int totalPage(TNews tNews) {
        return 0;
    }

    @Override
    public PageHelper<TNews> PageHelperQuery(PageHelperQuery pageHelperQuery) {
        Integer pageNum = pageHelperQuery.getPageNum();
        Integer pageSize = pageHelperQuery.getPageSize();
        //一共有多少条数据
        int totalPage = userNewsMapper.totalPage(new TNews());
        //计算当前页的数据在数据库中应该从哪一条记录开始检索
        int offset=(pageNum -1)*pageSize;
        List<TNews> tNews = userNewsMapper.PagingQuery(pageHelperQuery,offset);
        //创建分页对象
        PageHelper<TNews> pageHelper=new PageHelper<>();
        pageHelper.setPageNum(pageNum);
        pageHelper.setPageSize(pageSize);
        //计算并设置分页中的总页数
        pageHelper.setPages((int)Math.ceil((double) totalPage /pageSize));
        pageHelper.setList(tNews);
        return pageHelper;
    }
}
