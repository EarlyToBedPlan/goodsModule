package cn.edu.xmu.coupon.model.po;

import java.time.LocalDateTime;

public class CouponSkuPo {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column coupon_sku.id
     *
     * @mbg.generated
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column coupon_sku.activity_id
     *
     * @mbg.generated
     */
    private Long activityId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column coupon_sku.sku_id
     *
     * @mbg.generated
     */
    private Long skuId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column coupon_sku.gmt_create
     *
     * @mbg.generated
     */
    private LocalDateTime gmtCreate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column coupon_sku.gmt_modified
     *
     * @mbg.generated
     */
    private LocalDateTime gmtModified;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column coupon_sku.id
     *
     * @return the value of coupon_sku.id
     *
     * @mbg.generated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column coupon_sku.id
     *
     * @param id the value for coupon_sku.id
     *
     * @mbg.generated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column coupon_sku.activity_id
     *
     * @return the value of coupon_sku.activity_id
     *
     * @mbg.generated
     */
    public Long getActivityId() {
        return activityId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column coupon_sku.activity_id
     *
     * @param activityId the value for coupon_sku.activity_id
     *
     * @mbg.generated
     */
    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column coupon_sku.sku_id
     *
     * @return the value of coupon_sku.sku_id
     *
     * @mbg.generated
     */
    public Long getSkuId() {
        return skuId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column coupon_sku.sku_id
     *
     * @param skuId the value for coupon_sku.sku_id
     *
     * @mbg.generated
     */
    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column coupon_sku.gmt_create
     *
     * @return the value of coupon_sku.gmt_create
     *
     * @mbg.generated
     */
    public LocalDateTime getGmtCreate() {
        return gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column coupon_sku.gmt_create
     *
     * @param gmtCreate the value for coupon_sku.gmt_create
     *
     * @mbg.generated
     */
    public void setGmtCreate(LocalDateTime gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column coupon_sku.gmt_modified
     *
     * @return the value of coupon_sku.gmt_modified
     *
     * @mbg.generated
     */
    public LocalDateTime getGmtModified() {
        return gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column coupon_sku.gmt_modified
     *
     * @param gmtModified the value for coupon_sku.gmt_modified
     *
     * @mbg.generated
     */
    public void setGmtModified(LocalDateTime gmtModified) {
        this.gmtModified = gmtModified;
    }
}