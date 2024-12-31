package cn.lanqiao.newsmanagement.service.impl;
import cn.hutool.core.util.RandomUtil;
import cn.lanqiao.newsmanagement.mapper.ResetPasswordMapper;
import cn.lanqiao.newsmanagement.model.dto.tadmin.ResetPasswordQuery;
import cn.lanqiao.newsmanagement.model.pojo.TAdmin;
import cn.lanqiao.newsmanagement.service.ResetPasswordService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;
import static cn.lanqiao.newsmanagement.model.common.CommonUse.CAPTCHA_PREFIX;
@Service
@Slf4j
public class ResetPasswordServiceImpl implements ResetPasswordService {
    @Autowired
    private ResetPasswordMapper resetPasswordMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    //验证码
    @Override
    public TAdmin queryByPhone(String phone, HttpServletRequest request) {
        //如果没有手机号，就向手机发送验证码
        TAdmin tAdmin = resetPasswordMapper.queryByPhone(phone);
        if (tAdmin !=null){
            //如果有手机号，就向该手机发送验证码（向手机发送验证码需要使用第三方接口）随机6位数（Random）
            String Code = RandomUtil.randomNumbers(6);
            //模拟发送验证码给手机（验证码存在日志中）
            log.info("生成验证码：手机号 = {}, 验证码 = {}", phone, Code);
            //这里应该使用redis来存储(手机验证码应该设置60s自动销毁 )
            //如果是写死的字符串或者写死的一些数据，一般不会直接应用到业务中，而且需要更优雅的写法
            stringRedisTemplate.opsForValue().set(CAPTCHA_PREFIX + phone, Code, 60, TimeUnit.SECONDS);
            return tAdmin;
        }else {
            //如果没有提示用户，手机号不存在
            return null;
        }
    }

    @Override
    public int updatePassword(ResetPasswordQuery resetPasswordQuery) {
        int result = resetPasswordMapper.updatePassword(resetPasswordQuery);
        if (result>0){
            return 1;
        }else {
            return 0;
        }
    }
}
