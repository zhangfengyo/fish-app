package com.entity.vo;

import com.entity.WeiyutixingEntity;

import com.baomidou.mybatisplus.annotations.TableName;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
 

/**
 * 喂鱼提醒
 * @author 
 * @email 
 * @date 2026-04-23 14:52:49
 */
public class WeiyutixingVO  implements Serializable {
	private static final long serialVersionUID = 1L;

	 			
	/**
	 * 用户姓名
	 */
	
	private String yonghuxingming;
		
	/**
	 * 提醒标题
	 */
	
	private String tixingbiaoti;
		
	/**
	 * 提醒日期
	 */
		
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat 
	private Date tixingriqi;
		
	/**
	 * 提醒内容
	 */
	
	private String tixingneirong;
				
	
	/**
	 * 设置：用户姓名
	 */
	 
	public void setYonghuxingming(String yonghuxingming) {
		this.yonghuxingming = yonghuxingming;
	}
	
	/**
	 * 获取：用户姓名
	 */
	public String getYonghuxingming() {
		return yonghuxingming;
	}
				
	
	/**
	 * 设置：提醒标题
	 */
	 
	public void setTixingbiaoti(String tixingbiaoti) {
		this.tixingbiaoti = tixingbiaoti;
	}
	
	/**
	 * 获取：提醒标题
	 */
	public String getTixingbiaoti() {
		return tixingbiaoti;
	}
				
	
	/**
	 * 设置：提醒日期
	 */
	 
	public void setTixingriqi(Date tixingriqi) {
		this.tixingriqi = tixingriqi;
	}
	
	/**
	 * 获取：提醒日期
	 */
	public Date getTixingriqi() {
		return tixingriqi;
	}
				
	
	/**
	 * 设置：提醒内容
	 */
	 
	public void setTixingneirong(String tixingneirong) {
		this.tixingneirong = tixingneirong;
	}
	
	/**
	 * 获取：提醒内容
	 */
	public String getTixingneirong() {
		return tixingneirong;
	}
			
}
