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


import com.dao.PinzhongfenleiDao;
import com.entity.PinzhongfenleiEntity;
import com.service.PinzhongfenleiService;
import com.entity.vo.PinzhongfenleiVO;
import com.entity.view.PinzhongfenleiView;

@Service("pinzhongfenleiService")
public class PinzhongfenleiServiceImpl extends ServiceImpl<PinzhongfenleiDao, PinzhongfenleiEntity> implements PinzhongfenleiService {
	
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<PinzhongfenleiEntity> page = this.selectPage(
                new Query<PinzhongfenleiEntity>(params).getPage(),
                new EntityWrapper<PinzhongfenleiEntity>()
        );
        return new PageUtils(page);
    }
    
    @Override
	public PageUtils queryPage(Map<String, Object> params, Wrapper<PinzhongfenleiEntity> wrapper) {
		  Page<PinzhongfenleiView> page =new Query<PinzhongfenleiView>(params).getPage();
	        page.setRecords(baseMapper.selectListView(page,wrapper));
	    	PageUtils pageUtil = new PageUtils(page);
	    	return pageUtil;
 	}

    
    @Override
	public List<PinzhongfenleiVO> selectListVO(Wrapper<PinzhongfenleiEntity> wrapper) {
 		return baseMapper.selectListVO(wrapper);
	}
	
	@Override
	public PinzhongfenleiVO selectVO(Wrapper<PinzhongfenleiEntity> wrapper) {
 		return baseMapper.selectVO(wrapper);
	}
	
	@Override
	public List<PinzhongfenleiView> selectListView(Wrapper<PinzhongfenleiEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public PinzhongfenleiView selectView(Wrapper<PinzhongfenleiEntity> wrapper) {
		return baseMapper.selectView(wrapper);
	}


}
