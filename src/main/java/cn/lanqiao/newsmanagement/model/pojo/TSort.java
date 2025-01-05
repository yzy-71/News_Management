package cn.lanqiao.newsmanagement.model.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TSort {
    private Integer id;
    private String sortName;
    private Integer isDelete;
}
