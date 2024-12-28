package cn.lanqiao.newsmanagement.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TAdminQuery {
    private String id;
    private String username;
    private String phone;
    private String password;
    private long identity;
    private String email;
    private String date;
    private long isDelete;
    private String verifyCode;
}