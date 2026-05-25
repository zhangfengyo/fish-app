package com.dao;

import com.entity.SiyangjiaoxueEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.vo.SiyangjiaoxueVO;
import com.entity.view.SiyangjiaoxueView;


/**
 * 饲养教学
 * 
 * @author 
 * @email 
 * @date 2026-04-23 14:52:48
 */
public interface SiyangjiaoxueDao extends BaseMapper<SiyangjiaoxueEntity> {
	
	List<SiyangjiaoxueVO> selectListVO(@Param("ew") Wrapper<SiyangjiaoxueEntity> wrapper);
	
	SiyangjiaoxueVO selectVO(@Param("ew") Wrapper<SiyangjiaoxueEntity> wrapper);
	
	List<SiyangjiaoxueView> selectListView(@Param("ew") Wrapper<SiyangjiaoxueEntity> wrapper);

	List<SiyangjiaoxueView> selectListView(Pagination page,@Param("ew") Wrapper<SiyangjiaoxueEntity> wrapper);

	
	SiyangjiaoxueView selectView(@Param("ew") Wrapper<SiyangjiaoxueEntity> wrapper);
	

}
