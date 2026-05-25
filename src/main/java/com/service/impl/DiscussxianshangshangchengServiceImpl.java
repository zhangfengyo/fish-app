package com.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.List;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.utils.PageUtils;
import com.utils.Query;


import com.dao.DiscussxianshangshangchengDao;
import com.entity.DiscussxianshangshangchengEntity;
import com.service.DiscussxianshangshangchengService;
import com.entity.vo.DiscussxianshangshangchengVO;
import com.entity.view.DiscussxianshangshangchengView;

@Service("discussxianshangshangchengService")
public class DiscussxianshangshangchengServiceImpl extends ServiceImpl<DiscussxianshangshangchengDao, DiscussxianshangshangchengEntity> implements DiscussxianshangshangchengService {
	
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<DiscussxianshangshangchengEntity> page = this.selectPage(
                new Query<DiscussxianshangshangchengEntity>(params).getPage(),
                new EntityWrapper<DiscussxianshangshangchengEntity>()
        );
        return new PageUtils(page);
    }
    
    @Override
	public PageUtils queryPage(Map<String, Object> params, Wrapper<DiscussxianshangshangchengEntity> wrapper) {
		  Page<DiscussxianshangshangchengView> page =new Query<DiscussxianshangshangchengView>(params).getPage();
	        page.setRecords(baseMapper.selectListView(page,wrapper));
	    	PageUtils pageUtil = new PageUtils(page);
	    	return pageUtil;
 	}

    
    @Override
	public List<DiscussxianshangshangchengVO> selectListVO(Wrapper<DiscussxianshangshangchengEntity> wrapper) {
 		return baseMapper.selectListVO(wrapper);
	}
	
	@Override
	public DiscussxianshangshangchengVO selectVO(Wrapper<DiscussxianshangshangchengEntity> wrapper) {
 		return baseMapper.selectVO(wrapper);
	}
	
	@Override
	public List<DiscussxianshangshangchengView> selectListView(Wrapper<DiscussxianshangshangchengEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public DiscussxianshangshangchengView selectView(Wrapper<DiscussxianshangshangchengEntity> wrapper) {
		return baseMapper.selectView(wrapper);
	}


}
