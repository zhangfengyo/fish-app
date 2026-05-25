package com.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.utils.PageUtils;
import com.entity.WeiyutixingEntity;
import java.util.List;
import java.util.Map;
import com.entity.vo.WeiyutixingVO;
import org.apache.ibatis.annotations.Param;
import com.entity.view.WeiyutixingView;


/**
 * 喂鱼提醒
 *
 * @author 
 * @email 
 * @date 2026-04-23 14:52:49
 */
public interface WeiyutixingService extends IService<WeiyutixingEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	List<WeiyutixingVO> selectListVO(Wrapper<WeiyutixingEntity> wrapper);
   	
   	WeiyutixingVO selectVO(@Param("ew") Wrapper<WeiyutixingEntity> wrapper);
   	
   	List<WeiyutixingView> selectListView(Wrapper<WeiyutixingEntity> wrapper);
   	
   	WeiyutixingView selectView(@Param("ew") Wrapper<WeiyutixingEntity> wrapper);
   	
   	PageUtils queryPage(Map<String, Object> params,Wrapper<WeiyutixingEntity> wrapper);

   	

}

