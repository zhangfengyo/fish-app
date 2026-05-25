package com.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import lombok.*;
import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;


/**
 * 观赏鱼百科
 * 数据库通用操作实体类（普通增删改查）
 * @author 
 * @email 
 * @date 2026-04-23 14:52:48
 */
@TableName("guanshangyubaike")
@Data
public class GuanshangyubaikeEntity<T> implements Serializable {
	private static final long serialVersionUID = 1L;


	public GuanshangyubaikeEntity() {
		
	}
	
	public GuanshangyubaikeEntity(T t) {
		try {
			BeanUtils.copyProperties(t, this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * 主键id
	 */
    @TableId(type = IdType.AUTO)
	private Long id;
	/**
	 * 中文名
	 */
	private String zhongwenming;
	
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
	
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
	private Date addtime;

}
