package com.dao;

import com.entity.XianshangshangchengEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.vo.XianshangshangchengVO;
import com.entity.view.XianshangshangchengView;


/**
 * 线上商城
 * 
 * @author 
 * @email 
 * @date 2026-04-23 14:52:49
 */
public interface XianshangshangchengDao extends BaseMapper<XianshangshangchengEntity> {
	
	List<XianshangshangchengVO> selectListVO(@Param("ew") Wrapper<XianshangshangchengEntity> wrapper);
	
	XianshangshangchengVO selectVO(@Param("ew") Wrapper<XianshangshangchengEntity> wrapper);
	
	List<XianshangshangchengView> selectListView(@Param("ew") Wrapper<XianshangshangchengEntity> wrapper);

	List<XianshangshangchengView> selectListView(Pagination page,@Param("ew") Wrapper<XianshangshangchengEntity> wrapper);

	
	XianshangshangchengView selectView(@Param("ew") Wrapper<XianshangshangchengEntity> wrapper);
	

}
