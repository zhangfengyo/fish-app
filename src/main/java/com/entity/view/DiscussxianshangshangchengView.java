package com.entity.view;

import com.entity.DiscussxianshangshangchengEntity;

import com.baomidou.mybatisplus.annotations.TableName;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import java.math.BigDecimal;

import java.io.Serializable;
import com.utils.EncryptUtil;
 

/**
 * 线上商城评论
 * 后端返回视图实体辅助类   
 * （通常后端关联的表或者自定义的字段需要返回使用）
 * @author 
 * @email 
 * @date 2026-04-23 14:52:50
 */
@TableName("discussxianshangshangcheng")
public class DiscussxianshangshangchengView  extends DiscussxianshangshangchengEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public DiscussxianshangshangchengView(){
	}
 
 	public DiscussxianshangshangchengView(DiscussxianshangshangchengEntity discussxianshangshangchengEntity){
 	try {
			BeanUtils.copyProperties(this, discussxianshangshangchengEntity);
		} catch (BeansException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		
	}


}
