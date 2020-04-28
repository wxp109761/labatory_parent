package com.lab.base.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * item实体类
 * @author Administrator
 *
 */
@Entity
@Table(name="xjitem")
@JsonIgnoreProperties(value = {"laboratoryList"})
public class Item implements Serializable{

	@Id
	@Column(name = "itemid")
	private String itemid;//itemid


	@Column(name = "itemname")
	private String itemname;//description
	@Column(name = "belong")
	private String belong;//0--公共检查项目，
	@Column(name = "gmt_create")
	private java.util.Date gmtCreate;//gmt_create
	@Column(name = "gmt_update")
	private java.util.Date gmtUpdate;//gmt_update

	public String getItemid() {
		return itemid;
	}

	public void setItemid(String itemid) {
		this.itemid = itemid;
	}

	public String getItemname() {
		return itemname;
	}

	public void setItemname(String itemname) {
		this.itemname = itemname;
	}

	public String getBelong() {
		return belong;
	}
	public void setBelong(String belong) {
		this.belong = belong;
	}

	public java.util.Date getGmtCreate() {
		return gmtCreate;
	}
	public void setGmtCreate(java.util.Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public java.util.Date getGmtUpdate() {
		return gmtUpdate;
	}
	public void setGmtUpdate(java.util.Date gmtUpdate) {
		this.gmtUpdate = gmtUpdate;
	}

//	@JsonIgnore
//	@ManyToMany(mappedBy = "itemList")  //配置多表关系
//	private List<Xjrecord> XjRecordList;



	@ManyToMany(mappedBy = "items",fetch = FetchType.EAGER)
	private List<Laboratory> laboratoryList;

	public List<Laboratory> getLaboratoryList() {
		return laboratoryList;
	}

	public void setLaboratoryList(List<Laboratory> laboratoryList) {
		this.laboratoryList = laboratoryList;
	}

	@Override
    public String toString() {
        return "Item{" +
                "itemid=" + itemid +
                ", itemname='" + itemname + '\'' +
                ", belong='" + belong + '\'' +
                ", gmtCreate=" + gmtCreate +
                ", gmtUpdate=" + gmtUpdate +
//                ", XjRecordList=" + XjRecordList +
                '}';
    }

//    public List<Xjrecord> getXjRecordList() {
//        return XjRecordList;
//    }
//
//    public void setXjRecordList(List<Xjrecord> xjRecordList) {
//        XjRecordList = xjRecordList;
//    }
}
