package cn.lanqiao.newsmanagement.controller;

import cn.hutool.core.util.RandomUtil;
import cn.lanqiao.newsmanagement.model.dto.TAdminQuery;
import cn.lanqiao.newsmanagement.model.dto.tadmin.TAdminAdd;
import cn.lanqiao.newsmanagement.model.pojo.TAdmin;
import cn.lanqiao.newsmanagement.service.TAdminService;
import cn.lanqiao.newsmanagement.utils.ResponseUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

import static cn.lanqiao.newsmanagement.model.common.CommonUse.CAPTCHA_PREFIX;
import static org.apache.el.lang.ELArithmetic.add;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@RestController
@RequestMapping("/tAdmin")
public class TAdminController {
    private static final Logger log = LoggerFactory.getLogger(TAdminController.class);
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



    /**
     * 登录功能
     */

    @RequestMapping("/login")
    public ResponseUtils login(@RequestBody TAdminQuery tAdminQuery) {
        try {
            TAdmin userLogin = tAdminService.login(tAdminQuery);
            if (userLogin != null) {
                // 登录成功，返回用户身份
                return new ResponseUtils(200, "登录成功", userLogin.getIdentity());
            } else {
                // 登录失败
                return new ResponseUtils(500, "登录失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseUtils(500, "登录失败：" + e.getMessage());
        }
    }


    /**
     * 验证码功能
     */
    @RequestMapping("/sendCodeTwo")
    public ResponseUtils sendCodeTwo(@RequestBody TAdminQuery tAdminQuery) {
        try {
            // 1. 验证手机号格式
            if (tAdminQuery.getPhone() == null || tAdminQuery.getPhone().trim().isEmpty()) {
                return new ResponseUtils<>(500, "手机号不能为空");
            }

            // 2. 查询手机号是否已注册
            TAdmin tAdmin = tAdminService.queryByPhoneTwo(tAdminQuery.getPhone());
            if (tAdmin != null) {
                return new ResponseUtils<>(500, "该手机号已被注册");
            }

            // 3. 生成6位随机验证码
            String phoneCode = RandomUtil.randomNumbers(6);
            log.info("生成验证码：{}，手机号：{}", phoneCode, tAdminQuery.getPhone());

            try {
                // 4. 将验证码存入Redis
                stringRedisTemplate.opsForValue().set(
                        CAPTCHA_PREFIX + tAdminQuery.getPhone(),
                        phoneCode,
                        60,
                        TimeUnit.SECONDS
                );

                return new ResponseUtils<>(200, "验证码发送成功");
            } catch (Exception e) {
                log.error("Redis存储验证码失败", e);
                return new ResponseUtils<>(500, "验证码发送失败");
            }
        } catch (Exception e) {
            log.error("发送验证码失败", e);
            return new ResponseUtils<>(500, "发送验证码失败");
        }
    }


    /**
     * 注册功能
     */
    @RequestMapping("/register")
    public ResponseUtils<TAdmin> register(@RequestBody TAdminAdd tAdminAdd) {
        try {
            // 1. 基本参数验证
            if (tAdminAdd.getUsername() == null || tAdminAdd.getPhone() == null ||
                    tAdminAdd.getPassword() == null || tAdminAdd.getPhoneCode() == null) {
                return new ResponseUtils<>(500, "请填写完整信息");
            }

            // 2. 验证验证码
            String phoneCode = stringRedisTemplate.opsForValue().get(CAPTCHA_PREFIX + tAdminAdd.getPhone());
            if (phoneCode == null || !phoneCode.equals(tAdminAdd.getPhoneCode())) {
                return new ResponseUtils<>(500, "验证码错误或已过期");
            }

            // 3. 设置用户身份
            tAdminAdd.setIdentity("admin".equals(tAdminAdd.getRole()) ? 0L : 1L);

            // 4. 调用service层注册方法
            int result = tAdminService.register(tAdminAdd);

            // 5. 处理注册结果
            if (result > 0) {
                // 注册成功后删除验证码
                stringRedisTemplate.delete(CAPTCHA_PREFIX + tAdminAdd.getPhone());
                return new ResponseUtils<>(200, "注册成功");
            } else {
                return new ResponseUtils<>(500, "注册失败");
            }
        } catch (Exception e) {
            return new ResponseUtils<>(500, "注册失败：" + e.getMessage());
        }
    }


    @RequestMapping("/checkUsername")
    public ResponseUtils checkUsername(@RequestBody TAdminQuery tAdminQuery) {
        try {
            // 验证用户名格式
            if (tAdminQuery.getUsername() == null || tAdminQuery.getUsername().trim().isEmpty()) {
                return new ResponseUtils<>(500, "用户名不能为空");
            }

            // 查询用户名是否存在
            boolean exists = tAdminService.isUsernameExists(tAdminQuery.getUsername());
            if (exists) {
                return new ResponseUtils<>(500, "该用户名已被使用");
            }

            return new ResponseUtils<>(200, "用户名可用");
        } catch (Exception e) {
            log.error("检查用户名失败", e);
            return new ResponseUtils<>(500, "检查用户名失败");
        }
    }
}



