package cn.lanqiao.newsmanagement.model.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TNews {
    private int id;
    private String username;
    private String sort;
    private String content;
    private String title;
    private long audit;
    private String remarks;
    private String date;
    private long isDelete;
    private long read;
    private int userId;
}
