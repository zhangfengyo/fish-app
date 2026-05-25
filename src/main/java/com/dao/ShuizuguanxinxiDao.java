package com.dao;

import com.entity.ShuizuguanxinxiEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.vo.ShuizuguanxinxiVO;
import com.entity.view.ShuizuguanxinxiView;


/**
 * 水族馆信息
 * 
 * @author 
 * @email 
 * @date 2026-04-23 14:52:48
 */
public interface ShuizuguanxinxiDao extends BaseMapper<ShuizuguanxinxiEntity> {
	
	List<ShuizuguanxinxiVO> selectListVO(@Param("ew") Wrapper<ShuizuguanxinxiEntity> wrapper);
	
	ShuizuguanxinxiVO selectVO(@Param("ew") Wrapper<ShuizuguanxinxiEntity> wrapper);
	
	List<ShuizuguanxinxiView> selectListView(@Param("ew") Wrapper<ShuizuguanxinxiEntity> wrapper);

	List<ShuizuguanxinxiView> selectListView(Pagination page,@Param("ew") Wrapper<ShuizuguanxinxiEntity> wrapper);

	
	ShuizuguanxinxiView selectView(@Param("ew") Wrapper<ShuizuguanxinxiEntity> wrapper);
	

}
