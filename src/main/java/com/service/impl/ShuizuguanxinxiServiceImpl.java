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


import com.dao.ShuizuguanxinxiDao;
import com.entity.ShuizuguanxinxiEntity;
import com.service.ShuizuguanxinxiService;
import com.entity.vo.ShuizuguanxinxiVO;
import com.entity.view.ShuizuguanxinxiView;

@Service("shuizuguanxinxiService")
public class ShuizuguanxinxiServiceImpl extends ServiceImpl<ShuizuguanxinxiDao, ShuizuguanxinxiEntity> implements ShuizuguanxinxiService {
	
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<ShuizuguanxinxiEntity> page = this.selectPage(
                new Query<ShuizuguanxinxiEntity>(params).getPage(),
                new EntityWrapper<ShuizuguanxinxiEntity>()
        );
        return new PageUtils(page);
    }
    
    @Override
	public PageUtils queryPage(Map<String, Object> params, Wrapper<ShuizuguanxinxiEntity> wrapper) {
		  Page<ShuizuguanxinxiView> page =new Query<ShuizuguanxinxiView>(params).getPage();
	        page.setRecords(baseMapper.selectListView(page,wrapper));
	    	PageUtils pageUtil = new PageUtils(page);
	    	return pageUtil;
 	}

    
    @Override
	public List<ShuizuguanxinxiVO> selectListVO(Wrapper<ShuizuguanxinxiEntity> wrapper) {
 		return baseMapper.selectListVO(wrapper);
	}
	
	@Override
	public ShuizuguanxinxiVO selectVO(Wrapper<ShuizuguanxinxiEntity> wrapper) {
 		return baseMapper.selectVO(wrapper);
	}
	
	@Override
	public List<ShuizuguanxinxiView> selectListView(Wrapper<ShuizuguanxinxiEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public ShuizuguanxinxiView selectView(Wrapper<ShuizuguanxinxiEntity> wrapper) {
		return baseMapper.selectView(wrapper);
	}


}
