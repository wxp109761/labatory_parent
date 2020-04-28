package com.lab.base.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
/**
 * notice实体类
 * @author Administrator
 *
 */
@Entity
@Table(name="notice")
public class Notice implements Serializable{

	@Id
	@Column(name = "id")
	private String id;//id


	@Column(name = "title")
	private String title;//title
	@Column(name = "content")
	private String content;//content
	@Column(name = "create_time")
	private java.util.Date createTime;//create_time
	@Column(name = "is_cancel")
	private String isCancel;//is_cancel
	@Column(name = "cancel_time")
	private java.util.Date cancelTime;//cancel_time
	@Column(name = "is_delete")
	private String isDelete;//is_delete
	@Column(name = "delete_time")
	private java.util.Date deleteTime;//delete_time
	@Column(name = "prioryty")
	private String prioryty;//prioryty
	@Column(name = "user_cate")
	private String userCate;//user_cate

	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	public java.util.Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public String getIsCancel() {
		return isCancel;
	}
	public void setIsCancel(String isCancel) {
		this.isCancel = isCancel;
	}

	public java.util.Date getCancelTime() {
		return cancelTime;
	}
	public void setCancelTime(java.util.Date cancelTime) {
		this.cancelTime = cancelTime;
	}

	public String getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}

	public java.util.Date getDeleteTime() {
		return deleteTime;
	}
	public void setDeleteTime(java.util.Date deleteTime) {
		this.deleteTime = deleteTime;
	}

	public String getPrioryty() {
		return prioryty;
	}
	public void setPrioryty(String prioryty) {
		this.prioryty = prioryty;
	}

	public String getUserCate() {
		return userCate;
	}
	public void setUserCate(String userCate) {
		this.userCate = userCate;
	}


	
}
