package com.lab.base.pojo;


import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "depart")
public class Depart implements Serializable {
    @Id
    @Column(name = "depart_id")
    private String depart_id;

    @Column(name = "depart_name")
    private String depart_name;

    @Column(name = "gmt_create")
    private Date gmtCreate;

    @Column(name = "gmt_update")
    private Date gmtUpdate;

    public String getDepart_id() {
        return depart_id;
    }

    public void setDepart_id(String depart_id) {
        this.depart_id = depart_id;
    }

    public String getDepart_name() {
        return depart_name;
    }

    public void setDepart_name(String depart_name) {
        this.depart_name = depart_name;
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

//    @OneToMany(mappedBy = "depart",cascade=CascadeType.REFRESH,fetch=FetchType.EAGER)
//    //级联保存、更新、删除、刷新;延迟加载。当删除用户，会级联删除该用户的所有文章
//    //拥有mappedBy注解的实体类为关系被维护端
//    //mappedBy="author"中的author是Article中的author属性
//    private List<Laboratory> laboratoryList;//文章列表
//
//    public List<Laboratory> getLaboratoryList() {
//        return laboratoryList;
//    }
//
//    public void setLaboratoryList(List<Laboratory> laboratoryList) {
//        this.laboratoryList = laboratoryList;
//    }
//


    @Override
    public String toString() {
        return "Depart{" +
                "depart_id='" + depart_id + '\'' +
                ", depart_name='" + depart_name + '\'' +
                ", gmtCreate=" + gmtCreate +
                ", gmtUpdate=" + gmtUpdate +
//                ", laboratoryList=" + laboratoryList +
                '}';
    }
}
