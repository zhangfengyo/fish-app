package com.dao;

import com.entity.ChangguanfenleiEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.vo.ChangguanfenleiVO;
import com.entity.view.ChangguanfenleiView;


/**
 * 场馆分类
 * 
 * @author 
 * @email 
 * @date 2026-04-23 14:52:48
 */
public interface ChangguanfenleiDao extends BaseMapper<ChangguanfenleiEntity> {
	
	List<ChangguanfenleiVO> selectListVO(@Param("ew") Wrapper<ChangguanfenleiEntity> wrapper);
	
	ChangguanfenleiVO selectVO(@Param("ew") Wrapper<ChangguanfenleiEntity> wrapper);
	
	List<ChangguanfenleiView> selectListView(@Param("ew") Wrapper<ChangguanfenleiEntity> wrapper);

	List<ChangguanfenleiView> selectListView(Pagination page,@Param("ew") Wrapper<ChangguanfenleiEntity> wrapper);

	
	ChangguanfenleiView selectView(@Param("ew") Wrapper<ChangguanfenleiEntity> wrapper);
	

}
