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


import com.dao.GuanshangyubaikeDao;
import com.entity.GuanshangyubaikeEntity;
import com.service.GuanshangyubaikeService;
import com.entity.vo.GuanshangyubaikeVO;
import com.entity.view.GuanshangyubaikeView;

@Service("guanshangyubaikeService")
public class GuanshangyubaikeServiceImpl extends ServiceImpl<GuanshangyubaikeDao, GuanshangyubaikeEntity> implements GuanshangyubaikeService {
	
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<GuanshangyubaikeEntity> page = this.selectPage(
                new Query<GuanshangyubaikeEntity>(params).getPage(),
                new EntityWrapper<GuanshangyubaikeEntity>()
        );
        return new PageUtils(page);
    }
    
    @Override
	public PageUtils queryPage(Map<String, Object> params, Wrapper<GuanshangyubaikeEntity> wrapper) {
		  Page<GuanshangyubaikeView> page =new Query<GuanshangyubaikeView>(params).getPage();
	        page.setRecords(baseMapper.selectListView(page,wrapper));
	    	PageUtils pageUtil = new PageUtils(page);
	    	return pageUtil;
 	}

    
    @Override
	public List<GuanshangyubaikeVO> selectListVO(Wrapper<GuanshangyubaikeEntity> wrapper) {
 		return baseMapper.selectListVO(wrapper);
	}
	
	@Override
	public GuanshangyubaikeVO selectVO(Wrapper<GuanshangyubaikeEntity> wrapper) {
 		return baseMapper.selectVO(wrapper);
	}
	
	@Override
	public List<GuanshangyubaikeView> selectListView(Wrapper<GuanshangyubaikeEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public GuanshangyubaikeView selectView(Wrapper<GuanshangyubaikeEntity> wrapper) {
		return baseMapper.selectView(wrapper);
	}


}
