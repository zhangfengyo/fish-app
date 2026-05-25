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


import com.dao.SiyangjiaoxueDao;
import com.entity.SiyangjiaoxueEntity;
import com.service.SiyangjiaoxueService;
import com.entity.vo.SiyangjiaoxueVO;
import com.entity.view.SiyangjiaoxueView;

@Service("siyangjiaoxueService")
public class SiyangjiaoxueServiceImpl extends ServiceImpl<SiyangjiaoxueDao, SiyangjiaoxueEntity> implements SiyangjiaoxueService {
	
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<SiyangjiaoxueEntity> page = this.selectPage(
                new Query<SiyangjiaoxueEntity>(params).getPage(),
                new EntityWrapper<SiyangjiaoxueEntity>()
        );
        return new PageUtils(page);
    }
    
    @Override
	public PageUtils queryPage(Map<String, Object> params, Wrapper<SiyangjiaoxueEntity> wrapper) {
		  Page<SiyangjiaoxueView> page =new Query<SiyangjiaoxueView>(params).getPage();
	        page.setRecords(baseMapper.selectListView(page,wrapper));
	    	PageUtils pageUtil = new PageUtils(page);
	    	return pageUtil;
 	}

    
    @Override
	public List<SiyangjiaoxueVO> selectListVO(Wrapper<SiyangjiaoxueEntity> wrapper) {
 		return baseMapper.selectListVO(wrapper);
	}
	
	@Override
	public SiyangjiaoxueVO selectVO(Wrapper<SiyangjiaoxueEntity> wrapper) {
 		return baseMapper.selectVO(wrapper);
	}
	
	@Override
	public List<SiyangjiaoxueView> selectListView(Wrapper<SiyangjiaoxueEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public SiyangjiaoxueView selectView(Wrapper<SiyangjiaoxueEntity> wrapper) {
		return baseMapper.selectView(wrapper);
	}


}
