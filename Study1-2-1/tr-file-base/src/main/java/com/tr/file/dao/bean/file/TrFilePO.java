package com.tr.file.dao.bean.file;

import javax.persistence.*;

@Table(name = "tr_file")
public class TrFilePO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String fileid;

    private String username;

    private String argeementid;

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
     * @return fileid
     */
    public String getFileid() {
        return fileid;
    }

    /**
     * @param fileid
     */
    public void setFileid(String fileid) {
        this.fileid = fileid == null ? null : fileid.trim();
    }

    /**
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username
     */
    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    /**
     * @return argeementid
     */
    public String getArgeementid() {
        return argeementid;
    }

    /**
     * @param argeementid
     */
    public void setArgeementid(String argeementid) {
        this.argeementid = argeementid == null ? null : argeementid.trim();
    }
}