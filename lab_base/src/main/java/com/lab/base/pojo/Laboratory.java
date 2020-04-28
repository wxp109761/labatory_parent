package com.lab.base.pojo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * laboratory实体类
 * @author Administrator
 *
 */
@Entity
@Table(name="laboratory")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Laboratory implements Serializable{

	@Id
	@Column(name = "labid")
	private String labid;//labid


	@Column(name = "label")
	private String label;//门牌号
	@Column(name = "state")
	private String state;//状态，0，1，2建设中，运行，废弃


	@Column(name = "function")
	private String function;//功能：描述
	@Column(name = "category")
	private String category;//类别
	@Column(name = "safe_status")
	private String safeStatus;//安全状态
	@Column(name = "lab_uid")
	private String labUid;//安全负责人对应id

	@Column(name = "gmt_create")
	private java.util.Date gmtCreate;//gmt_create
	@Column(name = "gmt_update")
	private java.util.Date gmtUpdate;//gmt_update

	public String getLabid() {
		return labid;
	}
	public void setLabid(String labid) {
		this.labid = labid;
	}

	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}

	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}



//	@Column(name = "depart_id")
//	private String departId;//安全负责人对应id
//	public String getDepartId() {
//		return departId;
//	}
//
//	public void setDepartId(String departId) {
//		this.departId = departId;
//	}

	public String getFunction() {
		return function;
	}
	public void setFunction(String function) {
		this.function = function;
	}

	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}

	public String getSafeStatus() {
		return safeStatus;
	}
	public void setSafeStatus(String safeStatus) {
		this.safeStatus = safeStatus;
	}

	public String getLabUid() {
		return labUid;
	}

	public void setLabUid(String labUid) {
		this.labUid = labUid;
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

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	/**
	 * 配置实验室到检查条目多对多关系
	 */

	@ManyToMany(targetEntity = Item.class,cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinTable(name = "sys_lab_item",
			joinColumns = {@JoinColumn(name = "sys_lab_id",referencedColumnName = "labid")},
			inverseJoinColumns ={@JoinColumn(name = "sys_item_id",referencedColumnName = "itemid")})
	private List<Item> items;


//	//多对一关系
//	@ManyToOne(targetEntity = User.class)
//	private  User user;
//
//	public User getUser() {
//		return user;
//	}
//
//	public void setUser(User user) {
//		this.user = user;
//	}

	@ManyToOne(targetEntity =Depart.class)
	@JsonBackReference
	@JoinColumn(name ="depart_id")
	private Depart depart;

	public Depart getDepart() {
		return depart;
	}

	public void setDepart(Depart depart) {
		this.depart = depart;
	}

	@Override
	public String toString() {
		return "Laboratory{" +
				"labid='" + labid + '\'' +
				", label='" + label + '\'' +
				", state='" + state + '\'' +
				", function='" + function + '\'' +
				", category='" + category + '\'' +
				", safeStatus='" + safeStatus + '\'' +
				", labUid='" + labUid + '\'' +
				", gmtCreate=" + gmtCreate +
				", gmtUpdate=" + gmtUpdate +
				", items=" + items +
				", depart=" + depart +
				'}';
	}
}
