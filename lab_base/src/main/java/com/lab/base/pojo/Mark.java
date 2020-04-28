package com.lab.base.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
/**
 * mark实体类
 * @author Administrator
 *
 */
@Entity
@Table(name="notice_mark")
public class Mark implements Serializable{

	@Id
	private Integer noticeId;//notice_id


	
	private Integer userId;//user_id
	private String isRead;//is_read
	private java.util.Date readTime;//read_time
	private String userCate;//user_cate

	
	public Integer getNoticeId() {
		return noticeId;
	}
	public void setNoticeId(Integer noticeId) {
		this.noticeId = noticeId;
	}

	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getIsRead() {
		return isRead;
	}
	public void setIsRead(String isRead) {
		this.isRead = isRead;
	}

	public java.util.Date getReadTime() {
		return readTime;
	}
	public void setReadTime(java.util.Date readTime) {
		this.readTime = readTime;
	}

	public String getUserCate() {
		return userCate;
	}
	public void setUserCate(String userCate) {
		this.userCate = userCate;
	}


	
}
