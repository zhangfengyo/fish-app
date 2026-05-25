package com.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.utils.PageUtils;
import com.entity.GuanshangyubaikeEntity;
import java.util.List;
import java.util.Map;
import com.entity.vo.GuanshangyubaikeVO;
import org.apache.ibatis.annotations.Param;
import com.entity.view.GuanshangyubaikeView;


/**
 * 观赏鱼百科
 *
 * @author 
 * @email 
 * @date 2026-04-23 14:52:48
 */
public interface GuanshangyubaikeService extends IService<GuanshangyubaikeEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	List<GuanshangyubaikeVO> selectListVO(Wrapper<GuanshangyubaikeEntity> wrapper);
   	
   	GuanshangyubaikeVO selectVO(@Param("ew") Wrapper<GuanshangyubaikeEntity> wrapper);
   	
   	List<GuanshangyubaikeView> selectListView(Wrapper<GuanshangyubaikeEntity> wrapper);
   	
   	GuanshangyubaikeView selectView(@Param("ew") Wrapper<GuanshangyubaikeEntity> wrapper);
   	
   	PageUtils queryPage(Map<String, Object> params,Wrapper<GuanshangyubaikeEntity> wrapper);

   	

}

