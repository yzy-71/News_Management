package cn.lanqiao.newsmanagement.controller;

import cn.lanqiao.newsmanagement.model.dto.tadmin.ResetPasswordQuery;
import cn.lanqiao.newsmanagement.model.pojo.TAdmin;
import cn.lanqiao.newsmanagement.service.ResetPasswordService;
import cn.lanqiao.newsmanagement.utils.ResponseUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static cn.lanqiao.newsmanagement.model.common.CommonUse.CAPTCHA_PREFIX;

@Slf4j
@RestController
@RequestMapping("/Reset")
public class ResetPasswordController {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private ResetPasswordService resetPasswordService;
    /**
     * 根据手机号查询用户信息
     */
    @RequestMapping("/sendCode")
    public ResponseUtils sendCode(@RequestBody ResetPasswordQuery resetPasswordQuery, HttpServletRequest request){
        log.info("收到发送验证码请求，参数：{}", resetPasswordQuery);
        String phone = resetPasswordQuery.getPhone();
        if (phone == null || phone.trim().isEmpty()) {
            return new ResponseUtils<>(500, "手机号不能为空");
        }
        try {
            TAdmin tAdmin = resetPasswordService.queryByPhone(phone, request);
            if (tAdmin == null) {
                return new ResponseUtils<>(500, "手机号不存在");
            }
            return new ResponseUtils<>(200, "验证码发送成功");
        } catch (Exception e) {
            log.error("发送验证码失败", e);
            return new ResponseUtils<>(500, "发送验证码失败：" + e.getMessage());
        }
    }
    /**
     * 更新用户密码
     */
    @RequestMapping("/RestPassword")
    public ResponseUtils RestPassword(@RequestBody ResetPasswordQuery resetPasswordQuery){
        try {
            // 1. 验证验证码是否正确
            String phoneCode = stringRedisTemplate.opsForValue().get(CAPTCHA_PREFIX + resetPasswordQuery.getPhone());
            if (phoneCode == null) {
                return new ResponseUtils<>(505, "验证码已过期，请重新获取");
            }
            if (!phoneCode.equals(resetPasswordQuery.getPhoneCode())) {
                return new ResponseUtils<>(505, "验证码错误");
            }
            // 2. 更新密码
            int result = resetPasswordService.updatePassword(resetPasswordQuery);
            if (result == 1) {
                // 3. 删除Redis中的验证码
                stringRedisTemplate.delete(CAPTCHA_PREFIX + resetPasswordQuery.getPhone());
                return new ResponseUtils<>(200, "密码修改成功");
            } else {
                return new ResponseUtils<>(500, "密码修改失败");
            }
        } catch (Exception e) {
            log.error("重置密码失败", e);
            return new ResponseUtils<>(500, "重置密码失败：" + e.getMessage());
        }
    }
}
