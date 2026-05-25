package com.entity.view;

import com.entity.GuanshangyubaikeEntity;

import com.baomidou.mybatisplus.annotations.TableName;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import java.math.BigDecimal;

import java.io.Serializable;
import com.utils.EncryptUtil;
 

/**
 * 观赏鱼百科
 * 后端返回视图实体辅助类   
 * （通常后端关联的表或者自定义的字段需要返回使用）
 * @author 
 * @email 
 * @date 2026-04-23 14:52:48
 */
@TableName("guanshangyubaike")
public class GuanshangyubaikeView  extends GuanshangyubaikeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public GuanshangyubaikeView(){
	}
 
 	public GuanshangyubaikeView(GuanshangyubaikeEntity guanshangyubaikeEntity){
 	try {
			BeanUtils.copyProperties(this, guanshangyubaikeEntity);
		} catch (BeansException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		
	}


}
