package cn.lanqiao.newsmanagement.model.dto;

import lombok.Data;

@Data
public class PageHelperQuery {
    //当前页码，表示用户请求的页数
    private Integer pageNum;
    //每页显示的记录数
    private Integer pageSize;
    private String username;
    private String sort;
    private String content;
    private String date;
    private String title;
    private int id;
    private String keyword;
    private String phone;
    private String password;
    private long identity;
    private String email;
    private long isDelete;
}
