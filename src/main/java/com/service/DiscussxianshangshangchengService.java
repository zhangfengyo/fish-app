package com.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.utils.PageUtils;
import com.entity.DiscussxianshangshangchengEntity;
import java.util.List;
import java.util.Map;
import com.entity.vo.DiscussxianshangshangchengVO;
import org.apache.ibatis.annotations.Param;
import com.entity.view.DiscussxianshangshangchengView;


/**
 * 线上商城评论
 *
 * @author 
 * @email 
 * @date 2026-04-23 14:52:50
 */
public interface DiscussxianshangshangchengService extends IService<DiscussxianshangshangchengEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	List<DiscussxianshangshangchengVO> selectListVO(Wrapper<DiscussxianshangshangchengEntity> wrapper);
   	
   	DiscussxianshangshangchengVO selectVO(@Param("ew") Wrapper<DiscussxianshangshangchengEntity> wrapper);
   	
   	List<DiscussxianshangshangchengView> selectListView(Wrapper<DiscussxianshangshangchengEntity> wrapper);
   	
   	DiscussxianshangshangchengView selectView(@Param("ew") Wrapper<DiscussxianshangshangchengEntity> wrapper);
   	
   	PageUtils queryPage(Map<String, Object> params,Wrapper<DiscussxianshangshangchengEntity> wrapper);

   	

}

