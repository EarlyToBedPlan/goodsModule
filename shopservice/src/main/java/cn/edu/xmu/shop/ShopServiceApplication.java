package cn.edu.xmu.shop;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author Ming Qiu
 **/
@SpringBootApplication(scanBasePackages = {"cn.edu.xmu.ooad","cn.edu.xmu.shop"})
@MapperScan("cn/edu/xmu/shop/mapper")
/**
 * @author Feiyan Liu
 * @date Created at 2020/11/28 23:30
 */
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})

public class ShopServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShopServiceApplication.class, args);
    }
}
