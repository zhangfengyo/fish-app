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
 * 线上商城
 * 数据库通用操作实体类（普通增删改查）
 * @author 
 * @email 
 * @date 2026-04-23 14:52:49
 */
@TableName("xianshangshangcheng")
@Data
public class XianshangshangchengEntity<T> implements Serializable {
	private static final long serialVersionUID = 1L;


	public XianshangshangchengEntity() {
		
	}
	
	public XianshangshangchengEntity(T t) {
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
	 * 商品名称
	 */
	private String shangpinmingcheng;
	
	/**
	 * 商品分类
	 */
	private String shangpinfenlei;
	
	/**
	 * 商品图片
	 */
	private String shangpintupian;
	
	/**
	 * 商品品牌
	 */
	private String shangpinpinpai;
	
	/**
	 * 商品规格
	 */
	private String shangpinguige;
	
	/**
	 * 生产厂家
	 */
	private String shengchanchangjia;
	
	/**
	 * 商品详情
	 */
	private String shangpinxiangqing;
	
	/**
	 * 单限
	 */
	private Integer onelimittimes;
	
	/**
	 * 库存
	 */
	private Integer alllimittimes;
	
	/**
	 * 价格
	 */
	private Double price;
	
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
	 * 评论数
	 */
	private Integer discussnum;
	
	/**
	 * 是否上架(1:上架，0:下架)
	 */
	private Integer onshelves;
	
	/**
	 * 收藏数
	 */
	private Integer storeupnum;
	
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
	private Date addtime;

}
