package com.lemon.springboot.study125.dao.bean.user;

import java.util.Date;
import javax.persistence.*;

@Table(name = "study_springboot_user")
public class SpringBootUserPO {
    /**
     * 主键自增ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 插入时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 名字
     */
    private String username;

    /**
     * 获取主键自增ID
     *
     * @return id - 主键自增ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置主键自增ID
     *
     * @param id 主键自增ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取插入时间
     *
     * @return create_time - 插入时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置插入时间
     *
     * @param createTime 插入时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取更新时间
     *
     * @return update_time - 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新时间
     *
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取名字
     *
     * @return username - 名字
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置名字
     *
     * @param username 名字
     */
    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }


    @Override
    public String toString() {
        return "SpringBootUserPO{" +
                "id=" + id +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", username='" + username + '\'' +
                '}';
    }
}