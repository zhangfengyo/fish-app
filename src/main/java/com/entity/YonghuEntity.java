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
 * 用户
 * 数据库通用操作实体类（普通增删改查）
 * @author 
 * @email 
 * @date 2026-04-23 14:52:48
 */
@TableName("yonghu")
@Data
public class YonghuEntity<T> implements Serializable {
	private static final long serialVersionUID = 1L;


	public YonghuEntity() {
		
	}
	
	public YonghuEntity(T t) {
		try {
			BeanUtils.copyProperties(t, this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * 主键id
	 */
    @TableId
	private Long id;
	/**
	 * 用户账号
	 */
	private String yonghuzhanghao;
	
	/**
	 * 密码
	 */
	private String mima;
	
	/**
	 * 用户姓名
	 */
	private String yonghuxingming;
	
	/**
	 * 性别
	 */
	private String xingbie;
	
	/**
	 * 身份证
	 */
	private String idcard;
	
	/**
	 * 年龄
	 */
	private Integer age;
	
	/**
	 * 手机号
	 */
	private String shoujihao;
	
	/**
	 * 头像
	 */
	private String touxiang;
	
	/**
	 * 邮箱
	 */
	private String email;
	
	/**
	 * 余额
	 */
	private Double money;
	
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
	private Date addtime;

}
