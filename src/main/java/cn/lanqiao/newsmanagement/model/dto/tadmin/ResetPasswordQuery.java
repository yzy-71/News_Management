package cn.lanqiao.newsmanagement.model.dto.tadmin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor//全参构造
@AllArgsConstructor//无参构造
public class ResetPasswordQuery {
    private String phone;
    private String phoneCode;
    private String newPassword;
}
