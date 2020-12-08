package cn.edu.xmu.timer.model.po;

import java.time.LocalDateTime;

public class TaskPo {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column timer_task.id
     *
     * @mbg.generated
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column timer_task.begin_time
     *
     * @mbg.generated
     */
    private LocalDateTime beginTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column timer_task.topic
     *
     * @mbg.generated
     */
    private String topic;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column timer_task.tag
     *
     * @mbg.generated
     */
    private String tag;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column timer_task.bean_name
     *
     * @mbg.generated
     */
    private String beanName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column timer_task.method_name
     *
     * @mbg.generated
     */
    private String methodName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column timer_task.return_type_name
     *
     * @mbg.generated
     */
    private String returnTypeName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column timer_task.period
     *
     * @mbg.generated
     */
    private Byte period;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column timer_task.gmt_create
     *
     * @mbg.generated
     */
    private LocalDateTime gmtCreate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column timer_task.gmt_modified
     *
     * @mbg.generated
     */
    private LocalDateTime gmtModified;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column timer_task.sender_name
     *
     * @mbg.generated
     */
    private String senderName;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column timer_task.id
     *
     * @return the value of timer_task.id
     *
     * @mbg.generated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column timer_task.id
     *
     * @param id the value for timer_task.id
     *
     * @mbg.generated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column timer_task.begin_time
     *
     * @return the value of timer_task.begin_time
     *
     * @mbg.generated
     */
    public LocalDateTime getBeginTime() {
        return beginTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column timer_task.begin_time
     *
     * @param beginTime the value for timer_task.begin_time
     *
     * @mbg.generated
     */
    public void setBeginTime(LocalDateTime beginTime) {
        this.beginTime = beginTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column timer_task.topic
     *
     * @return the value of timer_task.topic
     *
     * @mbg.generated
     */
    public String getTopic() {
        return topic;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column timer_task.topic
     *
     * @param topic the value for timer_task.topic
     *
     * @mbg.generated
     */
    public void setTopic(String topic) {
        this.topic = topic == null ? null : topic.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column timer_task.tag
     *
     * @return the value of timer_task.tag
     *
     * @mbg.generated
     */
    public String getTag() {
        return tag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column timer_task.tag
     *
     * @param tag the value for timer_task.tag
     *
     * @mbg.generated
     */
    public void setTag(String tag) {
        this.tag = tag == null ? null : tag.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column timer_task.bean_name
     *
     * @return the value of timer_task.bean_name
     *
     * @mbg.generated
     */
    public String getBeanName() {
        return beanName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column timer_task.bean_name
     *
     * @param beanName the value for timer_task.bean_name
     *
     * @mbg.generated
     */
    public void setBeanName(String beanName) {
        this.beanName = beanName == null ? null : beanName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column timer_task.method_name
     *
     * @return the value of timer_task.method_name
     *
     * @mbg.generated
     */
    public String getMethodName() {
        return methodName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column timer_task.method_name
     *
     * @param methodName the value for timer_task.method_name
     *
     * @mbg.generated
     */
    public void setMethodName(String methodName) {
        this.methodName = methodName == null ? null : methodName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column timer_task.return_type_name
     *
     * @return the value of timer_task.return_type_name
     *
     * @mbg.generated
     */
    public String getReturnTypeName() {
        return returnTypeName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column timer_task.return_type_name
     *
     * @param returnTypeName the value for timer_task.return_type_name
     *
     * @mbg.generated
     */
    public void setReturnTypeName(String returnTypeName) {
        this.returnTypeName = returnTypeName == null ? null : returnTypeName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column timer_task.period
     *
     * @return the value of timer_task.period
     *
     * @mbg.generated
     */
    public Byte getPeriod() {
        return period;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column timer_task.period
     *
     * @param period the value for timer_task.period
     *
     * @mbg.generated
     */
    public void setPeriod(Byte period) {
        this.period = period;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column timer_task.gmt_create
     *
     * @return the value of timer_task.gmt_create
     *
     * @mbg.generated
     */
    public LocalDateTime getGmtCreate() {
        return gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column timer_task.gmt_create
     *
     * @param gmtCreate the value for timer_task.gmt_create
     *
     * @mbg.generated
     */
    public void setGmtCreate(LocalDateTime gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column timer_task.gmt_modified
     *
     * @return the value of timer_task.gmt_modified
     *
     * @mbg.generated
     */
    public LocalDateTime getGmtModified() {
        return gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column timer_task.gmt_modified
     *
     * @param gmtModified the value for timer_task.gmt_modified
     *
     * @mbg.generated
     */
    public void setGmtModified(LocalDateTime gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column timer_task.sender_name
     *
     * @return the value of timer_task.sender_name
     *
     * @mbg.generated
     */
    public String getSenderName() {
        return senderName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column timer_task.sender_name
     *
     * @param senderName the value for timer_task.sender_name
     *
     * @mbg.generated
     */
    public void setSenderName(String senderName) {
        this.senderName = senderName == null ? null : senderName.trim();
    }
}