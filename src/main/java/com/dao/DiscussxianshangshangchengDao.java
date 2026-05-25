package com.dao;

import com.entity.DiscussxianshangshangchengEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.vo.DiscussxianshangshangchengVO;
import com.entity.view.DiscussxianshangshangchengView;


/**
 * 线上商城评论
 * 
 * @author 
 * @email 
 * @date 2026-04-23 14:52:50
 */
public interface DiscussxianshangshangchengDao extends BaseMapper<DiscussxianshangshangchengEntity> {
	
	List<DiscussxianshangshangchengVO> selectListVO(@Param("ew") Wrapper<DiscussxianshangshangchengEntity> wrapper);
	
	DiscussxianshangshangchengVO selectVO(@Param("ew") Wrapper<DiscussxianshangshangchengEntity> wrapper);
	
	List<DiscussxianshangshangchengView> selectListView(@Param("ew") Wrapper<DiscussxianshangshangchengEntity> wrapper);

	List<DiscussxianshangshangchengView> selectListView(Pagination page,@Param("ew") Wrapper<DiscussxianshangshangchengEntity> wrapper);

	
	DiscussxianshangshangchengView selectView(@Param("ew") Wrapper<DiscussxianshangshangchengEntity> wrapper);
	

}
