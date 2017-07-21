package com.lemon.chen.dao.bean.point;

import java.util.Date;
import javax.persistence.*;

@Table(name = "point_info")
public class PointInfoPO {
    /**
     * 自增字段
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 插入时间
     */
    @Column(name = "insert_time")
    private Date insertTime;

    /**
     * 修改时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 用户的积分
     */
    @Column(name = "user_point")
    private Long userPoint;

    /**
     * 获取自增字段
     *
     * @return id - 自增字段
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置自增字段
     *
     * @param id 自增字段
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取用户id
     *
     * @return user_id - 用户id
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置用户id
     *
     * @param userId 用户id
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取插入时间
     *
     * @return insert_time - 插入时间
     */
    public Date getInsertTime() {
        return insertTime;
    }

    /**
     * 设置插入时间
     *
     * @param insertTime 插入时间
     */
    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    /**
     * 获取修改时间
     *
     * @return update_time - 修改时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置修改时间
     *
     * @param updateTime 修改时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取用户的积分
     *
     * @return user_point - 用户的积分
     */
    public Long getUserPoint() {
        return userPoint;
    }

    /**
     * 设置用户的积分
     *
     * @param userPoint 用户的积分
     */
    public void setUserPoint(Long userPoint) {
        this.userPoint = userPoint;
    }
}