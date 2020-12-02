package cn.edu.xmu.groupon.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author Feiyan Liu
 * @date Created at 2020/11/28 23:30
 */
@SpringBootApplication(scanBasePackages = {"cn.edu.xmu.ooad","cn.edu.xmu.groupon"})
@MapperScan("cn/edu/xmu/grouopon/mapper")
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})

public class GrouponServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(GrouponServiceApplication.class, args);
    }
}
