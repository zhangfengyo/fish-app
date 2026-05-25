package com.entity.model;

import com.entity.ShuizuguanxinxiEntity;

import com.baomidou.mybatisplus.annotations.TableName;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
 

/**
 * 水族馆信息
 * 接收传参的实体类  
 *（实际开发中配合移动端接口开发手动去掉些没用的字段， 后端一般用entity就够用了） 
 * 取自ModelAndView 的model名称
 * @author 
 * @email 
 * @date 2026-04-23 14:52:48
 */
public class ShuizuguanxinxiModel  implements Serializable {
	private static final long serialVersionUID = 1L;

	 			
	/**
	 * 场馆名称
	 */
	
	private String changguanmingcheng;
		
	/**
	 * 场馆分类
	 */
	
	private String changguanfenlei;
		
	/**
	 * 场馆图片
	 */
	
	private String changguantupian;
		
	/**
	 * 场馆位置
	 */
	
	private String changguanweizhi;
		
	/**
	 * 联系人
	 */
	
	private String lianxiren;
		
	/**
	 * 联系电话
	 */
	
	private String lianxidianhua;
		
	/**
	 * 开放时间
	 */
	
	private String kaifangshijian;
		
	/**
	 * 场馆介绍
	 */
	
	private String changguanjieshao;
		
	/**
	 * 收藏数
	 */
	
	private Integer storeupnum;
				
	
	/**
	 * 设置：场馆名称
	 */
	 
	public void setChangguanmingcheng(String changguanmingcheng) {
		this.changguanmingcheng = changguanmingcheng;
	}
	
	/**
	 * 获取：场馆名称
	 */
	public String getChangguanmingcheng() {
		return changguanmingcheng;
	}
				
	
	/**
	 * 设置：场馆分类
	 */
	 
	public void setChangguanfenlei(String changguanfenlei) {
		this.changguanfenlei = changguanfenlei;
	}
	
	/**
	 * 获取：场馆分类
	 */
	public String getChangguanfenlei() {
		return changguanfenlei;
	}
				
	
	/**
	 * 设置：场馆图片
	 */
	 
	public void setChangguantupian(String changguantupian) {
		this.changguantupian = changguantupian;
	}
	
	/**
	 * 获取：场馆图片
	 */
	public String getChangguantupian() {
		return changguantupian;
	}
				
	
	/**
	 * 设置：场馆位置
	 */
	 
	public void setChangguanweizhi(String changguanweizhi) {
		this.changguanweizhi = changguanweizhi;
	}
	
	/**
	 * 获取：场馆位置
	 */
	public String getChangguanweizhi() {
		return changguanweizhi;
	}
				
	
	/**
	 * 设置：联系人
	 */
	 
	public void setLianxiren(String lianxiren) {
		this.lianxiren = lianxiren;
	}
	
	/**
	 * 获取：联系人
	 */
	public String getLianxiren() {
		return lianxiren;
	}
				
	
	/**
	 * 设置：联系电话
	 */
	 
	public void setLianxidianhua(String lianxidianhua) {
		this.lianxidianhua = lianxidianhua;
	}
	
	/**
	 * 获取：联系电话
	 */
	public String getLianxidianhua() {
		return lianxidianhua;
	}
				
	
	/**
	 * 设置：开放时间
	 */
	 
	public void setKaifangshijian(String kaifangshijian) {
		this.kaifangshijian = kaifangshijian;
	}
	
	/**
	 * 获取：开放时间
	 */
	public String getKaifangshijian() {
		return kaifangshijian;
	}
				
	
	/**
	 * 设置：场馆介绍
	 */
	 
	public void setChangguanjieshao(String changguanjieshao) {
		this.changguanjieshao = changguanjieshao;
	}
	
	/**
	 * 获取：场馆介绍
	 */
	public String getChangguanjieshao() {
		return changguanjieshao;
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
