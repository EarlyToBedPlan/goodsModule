package test.cn.edu.xmu.presale.dao;

import cn.edu.xmu.presale.PreSaleServiceApplication;
import cn.edu.xmu.presale.dao.PreSaleDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.StyledEditorKit;
import java.time.LocalDateTime;

/**
 * @author LJP_3424
 * @create 2020-12-05 18:27
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PreSaleServiceApplication.class)   //标识本类是一个SpringBootTest
@Transactional
public class PreSaleDaoTest {
    @Autowired
    private PreSaleDao preSaleDao;

    @Test
    public void preSaleDaoTest() {

        LocalDateTime localDateTime = LocalDateTime.of(2020, 11, 1, 00, 00, 00);
        LocalDateTime localDateTime1 = LocalDateTime.of(2020, 11, 9, 00, 00, 00);
        Boolean result = preSaleDao.getPreSaleInActivities(10002L, localDateTime, localDateTime1);
        System.out.println(result);
    }
}
