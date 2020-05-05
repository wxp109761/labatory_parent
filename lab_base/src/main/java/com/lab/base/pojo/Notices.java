package com.lab.base.pojo;

import javax.persistence.*;
import java.io.Serializable;

/**
 * notices实体类
 * @author Administrator
 *
 */
@Entity
@Table(name="notices")
public class Notices implements Serializable{

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)//自增
	private Integer id;//id


	
	private String title;//title
	private String notice;//notice
	private String sendId;//send_id
	private String isDel;//is_delD
	private String sendDepart;//send_depart
	private java.util.Date createTime;//create_time
	private java.util.Date updateTime;//update_time

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSendDepart() {
		return sendDepart;
	}

	public void setSendDepart(String sendDepart) {
		this.sendDepart = sendDepart;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public String getNotice() {
		return notice;
	}
	public void setNotice(String notice) {
		this.notice = notice;
	}


	public String getIsDel() {
		return isDel;
	}
	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}

	public java.util.Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public java.util.Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}


	public String getSendId() {
		return sendId;
	}

	public void setSendId(String sendId) {
		this.sendId = sendId;
	}
}
