package com.lab.base.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * xjrecord实体类
 * @author Administrator
 *
 */
@Entity
@Table(name="xjrecord")
public class Xjrecord implements Serializable{

	@Id
	@Column(name = "xjid")
	private String xjid;//xjid


	@Column(name = "xjr_uid")
	private String xjrUid;//uid
	@Column(name = "labid")
	private String labid;//labid
	@Column(name = "state")
	private String state;//状态：0，1，2，3
	@Column(name = "gmt_create")
	private java.util.Date gmtCreate;//gmt_create
	@Column(name = "gmt_update")
	private java.util.Date gmtUpdate;//gmt_update

	
	public String getXjid() {
		return xjid;
	}
	public void setXjid(String xjid) {
		this.xjid = xjid;
	}

	public String getXjrUid() {
		return xjrUid;
	}

	public void setXjrUid(String xjrUid) {
		this.xjrUid = xjrUid;
	}

	public String getLabid() {
		return labid;
	}
	public void setLabid(String labid) {
		this.labid = labid;
	}

	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
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


    /**
     *
     */
	@ManyToMany(targetEntity = Item.class,cascade = CascadeType.ALL,fetch=FetchType.EAGER)
	@JsonIgnore
    @JoinTable(name = "sys_record_item",
			//joinColumns,当前对象在中间表中的外键
			joinColumns = {@JoinColumn(name = "sys_xjid",referencedColumnName = "xjid")},
			//inverseJoinColumns，对方对象在中间表的外键
			inverseJoinColumns = {@JoinColumn(name = "sys_item_id",referencedColumnName = "itemid"),
            }
	)
    private List<Item> itemList;

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    @Override
    public String toString() {
        return "Xjrecord{" +
                "xjid='" + xjid + '\'' +
                ", xjrUid='" + xjrUid + '\'' +
                ", labid='" + labid + '\'' +
                ", state='" + state + '\'' +
                ", gmtCreate=" + gmtCreate +
                ", gmtUpdate=" + gmtUpdate +
                ", itemList=" + itemList +
                '}';
    }
}
