package com.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.utils.PageUtils;
import com.entity.PinzhongfenleiEntity;
import java.util.List;
import java.util.Map;
import com.entity.vo.PinzhongfenleiVO;
import org.apache.ibatis.annotations.Param;
import com.entity.view.PinzhongfenleiView;


/**
 * 品种分类
 *
 * @author 
 * @email 
 * @date 2026-04-23 14:52:48
 */
public interface PinzhongfenleiService extends IService<PinzhongfenleiEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	List<PinzhongfenleiVO> selectListVO(Wrapper<PinzhongfenleiEntity> wrapper);
   	
   	PinzhongfenleiVO selectVO(@Param("ew") Wrapper<PinzhongfenleiEntity> wrapper);
   	
   	List<PinzhongfenleiView> selectListView(Wrapper<PinzhongfenleiEntity> wrapper);
   	
   	PinzhongfenleiView selectView(@Param("ew") Wrapper<PinzhongfenleiEntity> wrapper);
   	
   	PageUtils queryPage(Map<String, Object> params,Wrapper<PinzhongfenleiEntity> wrapper);

   	

}

