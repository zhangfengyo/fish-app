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
 * 水族馆信息
 * 数据库通用操作实体类（普通增删改查）
 * @author 
 * @email 
 * @date 2026-04-23 14:52:48
 */
@TableName("shuizuguanxinxi")
@Data
public class ShuizuguanxinxiEntity<T> implements Serializable {
	private static final long serialVersionUID = 1L;


	public ShuizuguanxinxiEntity() {
		
	}
	
	public ShuizuguanxinxiEntity(T t) {
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
	 * 场馆编号
	 */
	private String changguanbianhao;
	
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
	
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
	private Date addtime;

}
