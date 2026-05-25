package com.entity.view;

import com.entity.WeiyutixingEntity;

import com.baomidou.mybatisplus.annotations.TableName;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import java.math.BigDecimal;

import java.io.Serializable;
import com.utils.EncryptUtil;
 

/**
 * 喂鱼提醒
 * 后端返回视图实体辅助类   
 * （通常后端关联的表或者自定义的字段需要返回使用）
 * @author 
 * @email 
 * @date 2026-04-23 14:52:49
 */
@TableName("weiyutixing")
public class WeiyutixingView  extends WeiyutixingEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public WeiyutixingView(){
	}
 
 	public WeiyutixingView(WeiyutixingEntity weiyutixingEntity){
 	try {
			BeanUtils.copyProperties(this, weiyutixingEntity);
		} catch (BeansException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		
	}


}
