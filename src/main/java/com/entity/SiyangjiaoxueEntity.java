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
 * 饲养教学
 * 数据库通用操作实体类（普通增删改查）
 * @author 
 * @email 
 * @date 2026-04-23 14:52:48
 */
@TableName("siyangjiaoxue")
@Data
public class SiyangjiaoxueEntity<T> implements Serializable {
	private static final long serialVersionUID = 1L;


	public SiyangjiaoxueEntity() {
		
	}
	
	public SiyangjiaoxueEntity(T t) {
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
	 * 教学标题
	 */
	private String jiaoxuebiaoti;
	
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
	
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
	private Date addtime;

}
