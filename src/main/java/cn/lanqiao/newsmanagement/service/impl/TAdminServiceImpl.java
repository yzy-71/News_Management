package cn.lanqiao.newsmanagement.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.lanqiao.newsmanagement.mapper.TAdminMapper;
import cn.lanqiao.newsmanagement.model.dto.TAdminQuery;
//import cn.lanqiao.newsmanagement.model.dto.tadmin.TAdminAdd;
import cn.lanqiao.newsmanagement.model.dto.tadmin.TAdminAdd;
import cn.lanqiao.newsmanagement.model.pojo.TAdmin;
import cn.lanqiao.newsmanagement.service.TAdminService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.xml.transform.Result;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
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

    /**
     * 登录功能
     */
    @Override
    public TAdmin login(TAdminQuery tAdminQuery) {
        try {
            // 1. 先执行登录查询
            TAdmin result = tAdminMapper.login(tAdminQuery);

            if (result != null) {
                // 2. 如果登录成功，更新user_id
                tAdminMapper.updateUserId(tAdminQuery);
                return result;
            }

            return null;
        } catch (Exception e) {
            log.error("登录失败", e);
            return null;
        }
    }

    /**
     * 注册功能
     */
    @Override
    public int register(TAdminAdd tAdminAdd) {
        try {
            // 1. 创建新用户对象
            TAdmin tAdmin = new TAdmin();
            tAdmin.setId(UUID.randomUUID().toString());
            tAdmin.setUsername(tAdminAdd.getUsername());
            tAdmin.setPhone(tAdminAdd.getPhone());
            tAdmin.setPassword(tAdminAdd.getPassword());
            tAdmin.setIdentity(tAdminAdd.getIdentity());
            tAdmin.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            tAdmin.setIsDelete(0L);

            // 2. 执行注册
            return tAdminMapper.register(tAdmin);
        } catch (Exception e) {
            log.error("注册失败", e);
            return 0;
        }
    }


    @Override
    public TAdmin queryByPhoneTwo(String phone) {
        try {
            // 只查询手机号是否存在，不生成验证码
            return tAdminMapper.queryByPhone(phone);
        } catch (Exception e) {
            log.error("查询手机号失败", e);
            return null;
        }
    }

//    @Override
//    public TAdmin queryByUsername(String username) {
//        try {
//            return tAdminMapper.queryByUsername(username);
//        } catch (Exception e) {
//            log.error("查询用户名失败", e);
//            return null;
//        }
//    }

    @Override
    public boolean isUsernameExists(String username) {
        try {
            return tAdminMapper.countByUsername(username) > 0;
        } catch (Exception e) {
            log.error("查询用户名失败", e);
            return false;
        }
    }

}




