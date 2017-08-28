package com.tr.file.dao.bean.file;

import javax.persistence.*;

@Table(name = "tr_file")
public class TrFilePO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String filevid;

    private String username;

    private String argeementid;

    private String accessurl;

    private String sourceurl;

    private String sourcepath;

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
     * @return filevid
     */
    public String getFilevid() {
        return filevid;
    }

    /**
     * @param filevid
     */
    public void setFilevid(String filevid) {
        this.filevid = filevid == null ? null : filevid.trim();
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

    /**
     * @return accessurl
     */
    public String getAccessurl() {
        return accessurl;
    }

    /**
     * @param accessurl
     */
    public void setAccessurl(String accessurl) {
        this.accessurl = accessurl == null ? null : accessurl.trim();
    }

    /**
     * @return sourceurl
     */
    public String getSourceurl() {
        return sourceurl;
    }

    /**
     * @param sourceurl
     */
    public void setSourceurl(String sourceurl) {
        this.sourceurl = sourceurl == null ? null : sourceurl.trim();
    }

    /**
     * @return sourcepath
     */
    public String getSourcepath() {
        return sourcepath;
    }

    /**
     * @param sourcepath
     */
    public void setSourcepath(String sourcepath) {
        this.sourcepath = sourcepath == null ? null : sourcepath.trim();
    }
}