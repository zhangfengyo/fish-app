package com.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.utils.PageUtils;
import com.entity.XianshangshangchengEntity;
import java.util.List;
import java.util.Map;
import com.entity.vo.XianshangshangchengVO;
import org.apache.ibatis.annotations.Param;
import com.entity.view.XianshangshangchengView;


/**
 * 线上商城
 *
 * @author 
 * @email 
 * @date 2026-04-23 14:52:49
 */
public interface XianshangshangchengService extends IService<XianshangshangchengEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	List<XianshangshangchengVO> selectListVO(Wrapper<XianshangshangchengEntity> wrapper);
   	
   	XianshangshangchengVO selectVO(@Param("ew") Wrapper<XianshangshangchengEntity> wrapper);
   	
   	List<XianshangshangchengView> selectListView(Wrapper<XianshangshangchengEntity> wrapper);
   	
   	XianshangshangchengView selectView(@Param("ew") Wrapper<XianshangshangchengEntity> wrapper);
   	
   	PageUtils queryPage(Map<String, Object> params,Wrapper<XianshangshangchengEntity> wrapper);

   	

}

