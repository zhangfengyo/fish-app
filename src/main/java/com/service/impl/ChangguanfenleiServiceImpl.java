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


import com.dao.ChangguanfenleiDao;
import com.entity.ChangguanfenleiEntity;
import com.service.ChangguanfenleiService;
import com.entity.vo.ChangguanfenleiVO;
import com.entity.view.ChangguanfenleiView;

@Service("changguanfenleiService")
public class ChangguanfenleiServiceImpl extends ServiceImpl<ChangguanfenleiDao, ChangguanfenleiEntity> implements ChangguanfenleiService {
	
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<ChangguanfenleiEntity> page = this.selectPage(
                new Query<ChangguanfenleiEntity>(params).getPage(),
                new EntityWrapper<ChangguanfenleiEntity>()
        );
        return new PageUtils(page);
    }
    
    @Override
	public PageUtils queryPage(Map<String, Object> params, Wrapper<ChangguanfenleiEntity> wrapper) {
		  Page<ChangguanfenleiView> page =new Query<ChangguanfenleiView>(params).getPage();
	        page.setRecords(baseMapper.selectListView(page,wrapper));
	    	PageUtils pageUtil = new PageUtils(page);
	    	return pageUtil;
 	}

    
    @Override
	public List<ChangguanfenleiVO> selectListVO(Wrapper<ChangguanfenleiEntity> wrapper) {
 		return baseMapper.selectListVO(wrapper);
	}
	
	@Override
	public ChangguanfenleiVO selectVO(Wrapper<ChangguanfenleiEntity> wrapper) {
 		return baseMapper.selectVO(wrapper);
	}
	
	@Override
	public List<ChangguanfenleiView> selectListView(Wrapper<ChangguanfenleiEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public ChangguanfenleiView selectView(Wrapper<ChangguanfenleiEntity> wrapper) {
		return baseMapper.selectView(wrapper);
	}


}
