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


import com.dao.WeiyutixingDao;
import com.entity.WeiyutixingEntity;
import com.service.WeiyutixingService;
import com.entity.vo.WeiyutixingVO;
import com.entity.view.WeiyutixingView;

@Service("weiyutixingService")
public class WeiyutixingServiceImpl extends ServiceImpl<WeiyutixingDao, WeiyutixingEntity> implements WeiyutixingService {
	
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<WeiyutixingEntity> page = this.selectPage(
                new Query<WeiyutixingEntity>(params).getPage(),
                new EntityWrapper<WeiyutixingEntity>()
        );
        return new PageUtils(page);
    }
    
    @Override
	public PageUtils queryPage(Map<String, Object> params, Wrapper<WeiyutixingEntity> wrapper) {
		  Page<WeiyutixingView> page =new Query<WeiyutixingView>(params).getPage();
	        page.setRecords(baseMapper.selectListView(page,wrapper));
	    	PageUtils pageUtil = new PageUtils(page);
	    	return pageUtil;
 	}

    
    @Override
	public List<WeiyutixingVO> selectListVO(Wrapper<WeiyutixingEntity> wrapper) {
 		return baseMapper.selectListVO(wrapper);
	}
	
	@Override
	public WeiyutixingVO selectVO(Wrapper<WeiyutixingEntity> wrapper) {
 		return baseMapper.selectVO(wrapper);
	}
	
	@Override
	public List<WeiyutixingView> selectListView(Wrapper<WeiyutixingEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public WeiyutixingView selectView(Wrapper<WeiyutixingEntity> wrapper) {
		return baseMapper.selectView(wrapper);
	}


}
