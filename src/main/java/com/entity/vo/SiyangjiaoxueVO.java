package com.entity.vo;

import com.entity.SiyangjiaoxueEntity;

import com.baomidou.mybatisplus.annotations.TableName;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
 

/**
 * 饲养教学
 * @author 
 * @email 
 * @date 2026-04-23 14:52:48
 */
public class SiyangjiaoxueVO  implements Serializable {
	private static final long serialVersionUID = 1L;

	 			
	/**
	 * 品种分类
	 */
	
	private String pinzhongfenlei;
		
	/**
	 * 封面
	 */
	
	private String fengmian;
		
	/**
	 * 教学视频
	 */
	
	private String jiaoxueshipin;
		
	/**
	 * 饲养方法
	 */
	
	private String siyangfangfa;
		
	/**
	 * 鱼病治疗
	 */
	
	private String yubingzhiliao;
		
	/**
	 * 教学详情
	 */
	
	private String jiaoxuexiangqing;
		
	/**
	 * 收藏数
	 */
	
	private Integer storeupnum;
				
	
	/**
	 * 设置：品种分类
	 */
	 
	public void setPinzhongfenlei(String pinzhongfenlei) {
		this.pinzhongfenlei = pinzhongfenlei;
	}
	
	/**
	 * 获取：品种分类
	 */
	public String getPinzhongfenlei() {
		return pinzhongfenlei;
	}
				
	
	/**
	 * 设置：封面
	 */
	 
	public void setFengmian(String fengmian) {
		this.fengmian = fengmian;
	}
	
	/**
	 * 获取：封面
	 */
	public String getFengmian() {
		return fengmian;
	}
				
	
	/**
	 * 设置：教学视频
	 */
	 
	public void setJiaoxueshipin(String jiaoxueshipin) {
		this.jiaoxueshipin = jiaoxueshipin;
	}
	
	/**
	 * 获取：教学视频
	 */
	public String getJiaoxueshipin() {
		return jiaoxueshipin;
	}
				
	
	/**
	 * 设置：饲养方法
	 */
	 
	public void setSiyangfangfa(String siyangfangfa) {
		this.siyangfangfa = siyangfangfa;
	}
	
	/**
	 * 获取：饲养方法
	 */
	public String getSiyangfangfa() {
		return siyangfangfa;
	}
				
	
	/**
	 * 设置：鱼病治疗
	 */
	 
	public void setYubingzhiliao(String yubingzhiliao) {
		this.yubingzhiliao = yubingzhiliao;
	}
	
	/**
	 * 获取：鱼病治疗
	 */
	public String getYubingzhiliao() {
		return yubingzhiliao;
	}
				
	
	/**
	 * 设置：教学详情
	 */
	 
	public void setJiaoxuexiangqing(String jiaoxuexiangqing) {
		this.jiaoxuexiangqing = jiaoxuexiangqing;
	}
	
	/**
	 * 获取：教学详情
	 */
	public String getJiaoxuexiangqing() {
		return jiaoxuexiangqing;
	}
				
	
	/**
	 * 设置：收藏数
	 */
	 
	public void setStoreupnum(Integer storeupnum) {
		this.storeupnum = storeupnum;
	}
	
	/**
	 * 获取：收藏数
	 */
	public Integer getStoreupnum() {
		return storeupnum;
	}
			
}
