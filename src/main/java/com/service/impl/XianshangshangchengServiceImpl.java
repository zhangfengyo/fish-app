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


import com.dao.XianshangshangchengDao;
import com.entity.XianshangshangchengEntity;
import com.service.XianshangshangchengService;
import com.entity.vo.XianshangshangchengVO;
import com.entity.view.XianshangshangchengView;

@Service("xianshangshangchengService")
public class XianshangshangchengServiceImpl extends ServiceImpl<XianshangshangchengDao, XianshangshangchengEntity> implements XianshangshangchengService {
	
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<XianshangshangchengEntity> page = this.selectPage(
                new Query<XianshangshangchengEntity>(params).getPage(),
                new EntityWrapper<XianshangshangchengEntity>()
        );
        return new PageUtils(page);
    }
    
    @Override
	public PageUtils queryPage(Map<String, Object> params, Wrapper<XianshangshangchengEntity> wrapper) {
		  Page<XianshangshangchengView> page =new Query<XianshangshangchengView>(params).getPage();
	        page.setRecords(baseMapper.selectListView(page,wrapper));
	    	PageUtils pageUtil = new PageUtils(page);
	    	return pageUtil;
 	}

    
    @Override
	public List<XianshangshangchengVO> selectListVO(Wrapper<XianshangshangchengEntity> wrapper) {
 		return baseMapper.selectListVO(wrapper);
	}
	
	@Override
	public XianshangshangchengVO selectVO(Wrapper<XianshangshangchengEntity> wrapper) {
 		return baseMapper.selectVO(wrapper);
	}
	
	@Override
	public List<XianshangshangchengView> selectListView(Wrapper<XianshangshangchengEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public XianshangshangchengView selectView(Wrapper<XianshangshangchengEntity> wrapper) {
		return baseMapper.selectView(wrapper);
	}


}
