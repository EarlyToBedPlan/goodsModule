package cn.edu.xmu.flashsale.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.Future;
import java.time.LocalDateTime;

/**
 * @author LJP_3424
 * @create 2020-12-03 18:00
 */
public class NewFlashSaleVo {
    @Future
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime flashDate;

    public LocalDateTime getFlashDate() {
        return flashDate;
    }

    public void setFlashDate(LocalDateTime flashDate) {
        this.flashDate = flashDate;
    }
}
