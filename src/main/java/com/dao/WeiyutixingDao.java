package com.dao;

import com.entity.WeiyutixingEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.vo.WeiyutixingVO;
import com.entity.view.WeiyutixingView;


/**
 * 喂鱼提醒
 * 
 * @author 
 * @email 
 * @date 2026-04-23 14:52:49
 */
public interface WeiyutixingDao extends BaseMapper<WeiyutixingEntity> {
	
	List<WeiyutixingVO> selectListVO(@Param("ew") Wrapper<WeiyutixingEntity> wrapper);
	
	WeiyutixingVO selectVO(@Param("ew") Wrapper<WeiyutixingEntity> wrapper);
	
	List<WeiyutixingView> selectListView(@Param("ew") Wrapper<WeiyutixingEntity> wrapper);

	List<WeiyutixingView> selectListView(Pagination page,@Param("ew") Wrapper<WeiyutixingEntity> wrapper);

	
	WeiyutixingView selectView(@Param("ew") Wrapper<WeiyutixingEntity> wrapper);
	

}
