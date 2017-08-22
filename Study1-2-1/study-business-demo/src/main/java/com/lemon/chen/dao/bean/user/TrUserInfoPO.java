package com.lemon.chen.dao.bean.user;

import java.util.Date;
import javax.persistence.*;

@Table(name = "tr_user")
public class TrUserInfoPO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 用户Id
     */
    private String userid;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户姓名
     */
    private String username;

    /**
     * 用户手机号
     */
    private String mobilephone;

    /**
     * 用户的加密后的密码
     */
    private String password;

    /**
     * 加入时间
     */
    private Date createtime;

    /**
     * 用户修改时间
     */
    private Date updatetime;

    /**
     * 用户手机唯一标示
     */
    private String imel;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取用户Id
     *
     * @return userid - 用户Id
     */
    public String getUserid() {
        return userid;
    }

    /**
     * 设置用户Id
     *
     * @param userid 用户Id
     */
    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    /**
     * 获取用户昵称
     *
     * @return nickname - 用户昵称
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * 设置用户昵称
     *
     * @param nickname 用户昵称
     */
    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    /**
     * 获取用户姓名
     *
     * @return username - 用户姓名
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置用户姓名
     *
     * @param username 用户姓名
     */
    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    /**
     * 获取用户手机号
     *
     * @return mobilephone - 用户手机号
     */
    public String getMobilephone() {
        return mobilephone;
    }

    /**
     * 设置用户手机号
     *
     * @param mobilephone 用户手机号
     */
    public void setMobilephone(String mobilephone) {
        this.mobilephone = mobilephone == null ? null : mobilephone.trim();
    }

    /**
     * 获取用户的加密后的密码
     *
     * @return password - 用户的加密后的密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置用户的加密后的密码
     *
     * @param password 用户的加密后的密码
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * 获取加入时间
     *
     * @return createtime - 加入时间
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * 设置加入时间
     *
     * @param createtime 加入时间
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    /**
     * 获取用户修改时间
     *
     * @return updatetime - 用户修改时间
     */
    public Date getUpdatetime() {
        return updatetime;
    }

    /**
     * 设置用户修改时间
     *
     * @param updatetime 用户修改时间
     */
    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    /**
     * 获取用户手机唯一标示
     *
     * @return imel - 用户手机唯一标示
     */
    public String getImel() {
        return imel;
    }

    /**
     * 设置用户手机唯一标示
     *
     * @param imel 用户手机唯一标示
     */
    public void setImel(String imel) {
        this.imel = imel == null ? null : imel.trim();
    }
}