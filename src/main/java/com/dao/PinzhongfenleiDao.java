package com.dao;

import com.entity.PinzhongfenleiEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.vo.PinzhongfenleiVO;
import com.entity.view.PinzhongfenleiView;


/**
 * 品种分类
 * 
 * @author 
 * @email 
 * @date 2026-04-23 14:52:48
 */
public interface PinzhongfenleiDao extends BaseMapper<PinzhongfenleiEntity> {
	
	List<PinzhongfenleiVO> selectListVO(@Param("ew") Wrapper<PinzhongfenleiEntity> wrapper);
	
	PinzhongfenleiVO selectVO(@Param("ew") Wrapper<PinzhongfenleiEntity> wrapper);
	
	List<PinzhongfenleiView> selectListView(@Param("ew") Wrapper<PinzhongfenleiEntity> wrapper);

	List<PinzhongfenleiView> selectListView(Pagination page,@Param("ew") Wrapper<PinzhongfenleiEntity> wrapper);

	
	PinzhongfenleiView selectView(@Param("ew") Wrapper<PinzhongfenleiEntity> wrapper);
	

}
