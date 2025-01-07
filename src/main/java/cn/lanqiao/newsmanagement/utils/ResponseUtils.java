package cn.lanqiao.newsmanagement.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseUtils<T> {
    private int code;//响应状态码 200 成功 304 失败
    private String msg;//响应信息 登录成功 登录失败....
    private T data;//响应数据 比如查询的数据，存放到data中
    private int total; //ct
    public ResponseUtils(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    //ct
    public ResponseUtils(int code, String msg,T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
