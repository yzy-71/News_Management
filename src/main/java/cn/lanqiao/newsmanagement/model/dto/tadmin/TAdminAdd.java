package cn.lanqiao.newsmanagement.model.dto.tadmin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor//全参构造
@AllArgsConstructor//无参构造
/**
 * dto:专门用来接受前端发送到后端的参数
 * vo:专门用来响应后端查询出来的数据给前端
 */
public class TAdminAdd {
    private String username;
    private String phone;
    private String phoneCode;
    private String role;
    private String password;
    private String confirmPassword;
    private long identity;
}
