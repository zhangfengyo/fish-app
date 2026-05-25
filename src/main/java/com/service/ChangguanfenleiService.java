package com.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.utils.PageUtils;
import com.entity.ChangguanfenleiEntity;
import java.util.List;
import java.util.Map;
import com.entity.vo.ChangguanfenleiVO;
import org.apache.ibatis.annotations.Param;
import com.entity.view.ChangguanfenleiView;


/**
 * 场馆分类
 *
 * @author 
 * @email 
 * @date 2026-04-23 14:52:48
 */
public interface ChangguanfenleiService extends IService<ChangguanfenleiEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	List<ChangguanfenleiVO> selectListVO(Wrapper<ChangguanfenleiEntity> wrapper);
   	
   	ChangguanfenleiVO selectVO(@Param("ew") Wrapper<ChangguanfenleiEntity> wrapper);
   	
   	List<ChangguanfenleiView> selectListView(Wrapper<ChangguanfenleiEntity> wrapper);
   	
   	ChangguanfenleiView selectView(@Param("ew") Wrapper<ChangguanfenleiEntity> wrapper);
   	
   	PageUtils queryPage(Map<String, Object> params,Wrapper<ChangguanfenleiEntity> wrapper);

   	

}

