package cn.edu.xmu.flashsale.model.po;

import java.time.LocalDateTime;

public class FlashSaleItemPo {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column flash_sale_item.id
     *
     * @mbg.generated
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column flash_sale_item.sale_id
     *
     * @mbg.generated
     */
    private Long saleId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column flash_sale_item.goods_sku_id
     *
     * @mbg.generated
     */
    private Long goodsSkuId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column flash_sale_item.price
     *
     * @mbg.generated
     */
    private Long price;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column flash_sale_item.quantity
     *
     * @mbg.generated
     */
    private Integer quantity;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column flash_sale_item.gmt_create
     *
     * @mbg.generated
     */
    private LocalDateTime gmtCreate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column flash_sale_item.gmt_modified
     *
     * @mbg.generated
     */
    private LocalDateTime gmtModified;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column flash_sale_item.id
     *
     * @return the value of flash_sale_item.id
     *
     * @mbg.generated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column flash_sale_item.id
     *
     * @param id the value for flash_sale_item.id
     *
     * @mbg.generated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column flash_sale_item.sale_id
     *
     * @return the value of flash_sale_item.sale_id
     *
     * @mbg.generated
     */
    public Long getSaleId() {
        return saleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column flash_sale_item.sale_id
     *
     * @param saleId the value for flash_sale_item.sale_id
     *
     * @mbg.generated
     */
    public void setSaleId(Long saleId) {
        this.saleId = saleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column flash_sale_item.goods_sku_id
     *
     * @return the value of flash_sale_item.goods_sku_id
     *
     * @mbg.generated
     */
    public Long getGoodsSkuId() {
        return goodsSkuId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column flash_sale_item.goods_sku_id
     *
     * @param goodsSkuId the value for flash_sale_item.goods_sku_id
     *
     * @mbg.generated
     */
    public void setGoodsSkuId(Long goodsSkuId) {
        this.goodsSkuId = goodsSkuId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column flash_sale_item.price
     *
     * @return the value of flash_sale_item.price
     *
     * @mbg.generated
     */
    public Long getPrice() {
        return price;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column flash_sale_item.price
     *
     * @param price the value for flash_sale_item.price
     *
     * @mbg.generated
     */
    public void setPrice(Long price) {
        this.price = price;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column flash_sale_item.quantity
     *
     * @return the value of flash_sale_item.quantity
     *
     * @mbg.generated
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column flash_sale_item.quantity
     *
     * @param quantity the value for flash_sale_item.quantity
     *
     * @mbg.generated
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column flash_sale_item.gmt_create
     *
     * @return the value of flash_sale_item.gmt_create
     *
     * @mbg.generated
     */
    public LocalDateTime getGmtCreate() {
        return gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column flash_sale_item.gmt_create
     *
     * @param gmtCreate the value for flash_sale_item.gmt_create
     *
     * @mbg.generated
     */
    public void setGmtCreate(LocalDateTime gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column flash_sale_item.gmt_modified
     *
     * @return the value of flash_sale_item.gmt_modified
     *
     * @mbg.generated
     */
    public LocalDateTime getGmtModified() {
        return gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column flash_sale_item.gmt_modified
     *
     * @param gmtModified the value for flash_sale_item.gmt_modified
     *
     * @mbg.generated
     */
    public void setGmtModified(LocalDateTime gmtModified) {
        this.gmtModified = gmtModified;
    }
}