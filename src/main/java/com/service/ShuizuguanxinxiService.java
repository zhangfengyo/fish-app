package com.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.utils.PageUtils;
import com.entity.ShuizuguanxinxiEntity;
import java.util.List;
import java.util.Map;
import com.entity.vo.ShuizuguanxinxiVO;
import org.apache.ibatis.annotations.Param;
import com.entity.view.ShuizuguanxinxiView;


/**
 * 水族馆信息
 *
 * @author 
 * @email 
 * @date 2026-04-23 14:52:48
 */
public interface ShuizuguanxinxiService extends IService<ShuizuguanxinxiEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	List<ShuizuguanxinxiVO> selectListVO(Wrapper<ShuizuguanxinxiEntity> wrapper);
   	
   	ShuizuguanxinxiVO selectVO(@Param("ew") Wrapper<ShuizuguanxinxiEntity> wrapper);
   	
   	List<ShuizuguanxinxiView> selectListView(Wrapper<ShuizuguanxinxiEntity> wrapper);
   	
   	ShuizuguanxinxiView selectView(@Param("ew") Wrapper<ShuizuguanxinxiEntity> wrapper);
   	
   	PageUtils queryPage(Map<String, Object> params,Wrapper<ShuizuguanxinxiEntity> wrapper);

   	

}

