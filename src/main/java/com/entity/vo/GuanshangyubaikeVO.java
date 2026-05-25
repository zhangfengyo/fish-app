package com.entity.vo;

import com.entity.GuanshangyubaikeEntity;

import com.baomidou.mybatisplus.annotations.TableName;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
 

/**
 * 观赏鱼百科
 * @author 
 * @email 
 * @date 2026-04-23 14:52:48
 */
public class GuanshangyubaikeVO  implements Serializable {
	private static final long serialVersionUID = 1L;

	 			
	/**
	 * 英文名
	 */
	
	private String yingwenming;
		
	/**
	 * 品种分类
	 */
	
	private String pinzhongfenlei;
		
	/**
	 * 种属
	 */
	
	private String zhongshu;
		
	/**
	 * 观赏鱼图片
	 */
	
	private String guanshangyutupian;
		
	/**
	 * 最小水体
	 */
	
	private String zuixiaoshuiti;
		
	/**
	 * 饲养难度
	 */
	
	private String siyangnandu;
		
	/**
	 * 成体尺寸
	 */
	
	private String chengtichicun;
		
	/**
	 * 珊瑚缸兼容
	 */
	
	private String shanhugangjianrong;
		
	/**
	 * 主要产地
	 */
	
	private String zhuyaochandi;
		
	/**
	 * 食物要求
	 */
	
	private String shiwuyaoqiu;
		
	/**
	 * 性情
	 */
	
	private String xingqing;
		
	/**
	 * 要求
	 */
	
	private String yaoqiu;
		
	/**
	 * 饲养方法
	 */
	
	private String siyangfangfa;
		
	/**
	 * 生物简介
	 */
	
	private String shengwujianjie;
		
	/**
	 * 赞
	 */
	
	private Integer thumbsupnum;
		
	/**
	 * 踩
	 */
	
	private Integer crazilynum;
		
	/**
	 * 点击次数
	 */
	
	private Integer clicknum;
		
	/**
	 * 收藏数
	 */
	
	private Integer storeupnum;
				
	
	/**
	 * 设置：英文名
	 */
	 
	public void setYingwenming(String yingwenming) {
		this.yingwenming = yingwenming;
	}
	
	/**
	 * 获取：英文名
	 */
	public String getYingwenming() {
		return yingwenming;
	}
				
	
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
	 * 设置：种属
	 */
	 
	public void setZhongshu(String zhongshu) {
		this.zhongshu = zhongshu;
	}
	
	/**
	 * 获取：种属
	 */
	public String getZhongshu() {
		return zhongshu;
	}
				
	
	/**
	 * 设置：观赏鱼图片
	 */
	 
	public void setGuanshangyutupian(String guanshangyutupian) {
		this.guanshangyutupian = guanshangyutupian;
	}
	
	/**
	 * 获取：观赏鱼图片
	 */
	public String getGuanshangyutupian() {
		return guanshangyutupian;
	}
				
	
	/**
	 * 设置：最小水体
	 */
	 
	public void setZuixiaoshuiti(String zuixiaoshuiti) {
		this.zuixiaoshuiti = zuixiaoshuiti;
	}
	
	/**
	 * 获取：最小水体
	 */
	public String getZuixiaoshuiti() {
		return zuixiaoshuiti;
	}
				
	
	/**
	 * 设置：饲养难度
	 */
	 
	public void setSiyangnandu(String siyangnandu) {
		this.siyangnandu = siyangnandu;
	}
	
	/**
	 * 获取：饲养难度
	 */
	public String getSiyangnandu() {
		return siyangnandu;
	}
				
	
	/**
	 * 设置：成体尺寸
	 */
	 
	public void setChengtichicun(String chengtichicun) {
		this.chengtichicun = chengtichicun;
	}
	
	/**
	 * 获取：成体尺寸
	 */
	public String getChengtichicun() {
		return chengtichicun;
	}
				
	
	/**
	 * 设置：珊瑚缸兼容
	 */
	 
	public void setShanhugangjianrong(String shanhugangjianrong) {
		this.shanhugangjianrong = shanhugangjianrong;
	}
	
	/**
	 * 获取：珊瑚缸兼容
	 */
	public String getShanhugangjianrong() {
		return shanhugangjianrong;
	}
				
	
	/**
	 * 设置：主要产地
	 */
	 
	public void setZhuyaochandi(String zhuyaochandi) {
		this.zhuyaochandi = zhuyaochandi;
	}
	
	/**
	 * 获取：主要产地
	 */
	public String getZhuyaochandi() {
		return zhuyaochandi;
	}
				
	
	/**
	 * 设置：食物要求
	 */
	 
	public void setShiwuyaoqiu(String shiwuyaoqiu) {
		this.shiwuyaoqiu = shiwuyaoqiu;
	}
	
	/**
	 * 获取：食物要求
	 */
	public String getShiwuyaoqiu() {
		return shiwuyaoqiu;
	}
				
	
	/**
	 * 设置：性情
	 */
	 
	public void setXingqing(String xingqing) {
		this.xingqing = xingqing;
	}
	
	/**
	 * 获取：性情
	 */
	public String getXingqing() {
		return xingqing;
	}
				
	
	/**
	 * 设置：要求
	 */
	 
	public void setYaoqiu(String yaoqiu) {
		this.yaoqiu = yaoqiu;
	}
	
	/**
	 * 获取：要求
	 */
	public String getYaoqiu() {
		return yaoqiu;
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
	 * 设置：生物简介
	 */
	 
	public void setShengwujianjie(String shengwujianjie) {
		this.shengwujianjie = shengwujianjie;
	}
	
	/**
	 * 获取：生物简介
	 */
	public String getShengwujianjie() {
		return shengwujianjie;
	}
				
	
	/**
	 * 设置：赞
	 */
	 
	public void setThumbsupnum(Integer thumbsupnum) {
		this.thumbsupnum = thumbsupnum;
	}
	
	/**
	 * 获取：赞
	 */
	public Integer getThumbsupnum() {
		return thumbsupnum;
	}
				
	
	/**
	 * 设置：踩
	 */
	 
	public void setCrazilynum(Integer crazilynum) {
		this.crazilynum = crazilynum;
	}
	
	/**
	 * 获取：踩
	 */
	public Integer getCrazilynum() {
		return crazilynum;
	}
				
	
	/**
	 * 设置：点击次数
	 */
	 
	public void setClicknum(Integer clicknum) {
		this.clicknum = clicknum;
	}
	
	/**
	 * 获取：点击次数
	 */
	public Integer getClicknum() {
		return clicknum;
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
