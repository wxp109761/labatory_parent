package com.lab.base.pojo;

import javax.persistence.*;
import java.io.Serializable;
/**
 * remind实体类
 * @author Administrator
 *
 */
@Entity
@Table(name="task_remind")
public class Remind implements Serializable{

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)//自增
	private Integer id;//id


	
	private String title;//title
	private String content;//content
	private String isDone;//is_done
	private java.util.Date createTime;//create_time
	private java.util.Date remindTime;//remind_time
	private String uid;//uid
	private Integer imgId;//uid

	public Integer getImgId() {
		return imgId;
	}

	public void setImgId(Integer imgId) {
		this.imgId = imgId;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
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

	public String getIsDone() {
		return isDone;
	}
	public void setIsDone(String isDone) {
		this.isDone = isDone;
	}

	public java.util.Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public java.util.Date getRemindTime() {
		return remindTime;
	}
	public void setRemindTime(java.util.Date remindTime) {
		this.remindTime = remindTime;
	}

	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}


	
}
