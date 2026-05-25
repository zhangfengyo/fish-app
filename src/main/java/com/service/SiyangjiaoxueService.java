package com.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.utils.PageUtils;
import com.entity.SiyangjiaoxueEntity;
import java.util.List;
import java.util.Map;
import com.entity.vo.SiyangjiaoxueVO;
import org.apache.ibatis.annotations.Param;
import com.entity.view.SiyangjiaoxueView;


/**
 * 饲养教学
 *
 * @author 
 * @email 
 * @date 2026-04-23 14:52:48
 */
public interface SiyangjiaoxueService extends IService<SiyangjiaoxueEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	List<SiyangjiaoxueVO> selectListVO(Wrapper<SiyangjiaoxueEntity> wrapper);
   	
   	SiyangjiaoxueVO selectVO(@Param("ew") Wrapper<SiyangjiaoxueEntity> wrapper);
   	
   	List<SiyangjiaoxueView> selectListView(Wrapper<SiyangjiaoxueEntity> wrapper);
   	
   	SiyangjiaoxueView selectView(@Param("ew") Wrapper<SiyangjiaoxueEntity> wrapper);
   	
   	PageUtils queryPage(Map<String, Object> params,Wrapper<SiyangjiaoxueEntity> wrapper);

   	

}

