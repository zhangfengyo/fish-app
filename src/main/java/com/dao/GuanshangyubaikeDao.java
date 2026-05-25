package com.dao;

import com.entity.GuanshangyubaikeEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.vo.GuanshangyubaikeVO;
import com.entity.view.GuanshangyubaikeView;


/**
 * 观赏鱼百科
 * 
 * @author 
 * @email 
 * @date 2026-04-23 14:52:48
 */
public interface GuanshangyubaikeDao extends BaseMapper<GuanshangyubaikeEntity> {
	
	List<GuanshangyubaikeVO> selectListVO(@Param("ew") Wrapper<GuanshangyubaikeEntity> wrapper);
	
	GuanshangyubaikeVO selectVO(@Param("ew") Wrapper<GuanshangyubaikeEntity> wrapper);
	
	List<GuanshangyubaikeView> selectListView(@Param("ew") Wrapper<GuanshangyubaikeEntity> wrapper);

	List<GuanshangyubaikeView> selectListView(Pagination page,@Param("ew") Wrapper<GuanshangyubaikeEntity> wrapper);

	
	GuanshangyubaikeView selectView(@Param("ew") Wrapper<GuanshangyubaikeEntity> wrapper);
	

}
