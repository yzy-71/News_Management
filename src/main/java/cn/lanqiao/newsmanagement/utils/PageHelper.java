package cn.lanqiao.newsmanagement.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageHelper<T> {
    private int pageSize;//每页数量
    private int pages;//总页数
    private int pageNum;//当前页
    private List<T> list;//数据
}
