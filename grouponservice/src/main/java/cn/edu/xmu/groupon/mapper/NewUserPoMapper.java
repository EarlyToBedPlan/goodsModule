package cn.edu.xmu.groupon.mapper;

import cn.edu.xmu.groupon.model.po.NewUserPo;

public interface NewUserPoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auth_new_user
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auth_new_user
     *
     * @mbg.generated
     */
    int insert(NewUserPo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auth_new_user
     *
     * @mbg.generated
     */
    int insertSelective(NewUserPo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auth_new_user
     *
     * @mbg.generated
     */
    NewUserPo selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auth_new_user
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(NewUserPo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auth_new_user
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(NewUserPo record);
}