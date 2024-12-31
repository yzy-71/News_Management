package cn.lanqiao.newsmanagement.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.lanqiao.newsmanagement.mapper.TAdminMapper;
import cn.lanqiao.newsmanagement.model.dto.TAdminQuery;
import cn.lanqiao.newsmanagement.model.pojo.TAdmin;
import cn.lanqiao.newsmanagement.service.TAdminService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

import static cn.lanqiao.newsmanagement.model.common.CommonUse.CAPTCHA_PREFIX;


@Service
@Slf4j
public class TAdminServiceImpl implements TAdminService {
    @Autowired
    private TAdminMapper tAdminMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public TAdmin queryByPhone(String phone) {
        //如果有手机，就向该手机发送验证码
        TAdmin tAdmin = tAdminMapper.queryByPhone(phone);
        System.out.println(tAdmin);
        if (tAdmin!=null){
            String phoneCode = RandomUtil.randomNumbers(6);
            log.info("手机验证码发送:"+phoneCode);
            //使用redis来存储
            stringRedisTemplate.opsForValue().set(CAPTCHA_PREFIX+tAdmin.getPhone(),phoneCode,60L, TimeUnit.SECONDS);
            return tAdmin;
        }else {
            //如果没有，提示用户，手机号不存在
            return null;
        }
    }

    @Override
    public int queryIdentity(TAdminQuery tAdminQuery) {
        int result = tAdminMapper.queryIdentity(tAdminQuery);
        //0为管理员，1为用户
        if (result==0){
            return 0;
        }else {
            return 1;
        }
    }
}
