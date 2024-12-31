package cn.lanqiao.newsmanagement.controller;

import cn.lanqiao.newsmanagement.model.dto.TAdminQuery;
import cn.lanqiao.newsmanagement.model.pojo.TAdmin;
import cn.lanqiao.newsmanagement.service.TAdminService;
import cn.lanqiao.newsmanagement.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static cn.lanqiao.newsmanagement.model.common.CommonUse.CAPTCHA_PREFIX;

@RestController
@RequestMapping("/tAdmin")
public class TAdminController {
    @Autowired
    private TAdminService tAdminService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    //手机验证码发送
    @RequestMapping("/sendCode")
    public ResponseUtils sendCode(@RequestBody TAdminQuery tAdminQuery){
        TAdmin tAdmin=tAdminService.queryByPhone(tAdminQuery.getPhone());
        try {
            if (tAdmin==null){
                return new ResponseUtils<>(500,"手机号不存在");
            }else {
                return new ResponseUtils<>(200,"验证码发送成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    //查询登录用户的身份
    @RequestMapping("/sendCodeLogin")
    public ResponseUtils sendCodeLogin(@RequestBody TAdminQuery tAdminQuery){
        int identity = tAdminService.queryIdentity(tAdminQuery);
        //先判断验证码是否一致
        //获取用户输入的验证码
        String verifyCode = tAdminQuery.getVerifyCode();
        //获取后台服务器生成的验证码
        String phoneCode = stringRedisTemplate.opsForValue().get(CAPTCHA_PREFIX + tAdminQuery.getPhone());
        //判断用户身份
        if (verifyCode.equals(phoneCode)) {
            if (identity==0){
                return new ResponseUtils<>(200,"手机验证码发送成功，是管理员");
            }else {
                return new ResponseUtils<>(201,"手机验证码发送成功，是用户");
            }
        }else {
            return new ResponseUtils(505,"验证码输入错误");
        }
    }
}
