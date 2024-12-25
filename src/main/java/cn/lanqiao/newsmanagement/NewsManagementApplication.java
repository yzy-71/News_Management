package cn.lanqiao.newsmanagement;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.lanqiao.newsmanagement.mapper")
public class NewsManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(NewsManagementApplication.class, args);
    }

}
