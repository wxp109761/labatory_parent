package com.lab.base.pojo;


import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "user")
public class User implements Serializable {
    @Id
    @Column(name = "uid")
    private String uid;

    @NotEmpty(message = "工号不能为空")
    @Column(name = "job_number")
    private String jobNumber;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "depart_id")
    private String departId;
    @Column(name = "tel")
    private String tel;
    @Column(name = "permission")
    private String permission;


    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    @Column(name = "avatar_url")
    private String avatarUrl;


    @Column(name = "gmt_create")
    private java.util.Date gmtCreate;//gmt_create
    @Column(name = "gmt_update")
    private java.util.Date gmtUpdate;//gmt_update

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDepartId() {
        return departId;
    }

    public void setDepartId(String departId) {
        this.departId = departId;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }
    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtUpdate() {
        return gmtUpdate;
    }

    public void setGmtUpdate(Date gmtUpdate) {
        this.gmtUpdate = gmtUpdate;
    }


    @Override
    public String toString() {
        return "User{" +
                "uid='" + uid + '\'' +
                ", jobNumber='" + jobNumber + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", departId='" + departId + '\'' +
                ", tel='" + tel + '\'' +
                ", permission='" + permission + '\'' +
                ", gmtCreate=" + gmtCreate +
                ", gmtUpdate=" + gmtUpdate +
                '}';
    }


    //    //一对多关系
//    @OneToMany(targetEntity = Xjrecord.class)
//    @JoinColumn(name = "xjr_uid",referencedColumnName = "uid")
//    private List<Xjrecord> xjrecordList;
//
//    @OneToMany(targetEntity = Laboratory.class)
//    @JoinColumn(name = "lab_uid",referencedColumnName = "uid")
//    private List<Laboratory> laboratoryList;
//
//
//
//    public List<Xjrecord> getXjrecordList() {
//        return xjrecordList;
//    }
//
//    public void setXjrecordList(List<Xjrecord> xjrecordList) {
//        this.xjrecordList = xjrecordList;
//    }
//
//    public List<Laboratory> getLaboratoryList() {
//        return laboratoryList;
//    }
//
//    public void setLaboratoryList(List<Laboratory> laboratoryList) {
//        this.laboratoryList = laboratoryList;
//    }
}
