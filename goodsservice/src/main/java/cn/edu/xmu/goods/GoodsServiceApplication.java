package cn.edu.xmu.goods;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author Feiyan Liu
 * @date Created at 2020/11/28 23:30
 */
@SpringBootApplication(scanBasePackages = {"cn.edu.xmu.ooad","cn.edu.xmu.goods","cn.edu.xmu.shop"})
@MapperScan("cn.edu.xmu.goods.mapper")
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
public class GoodsServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(GoodsServiceApplication.class, args);
    }
}
