package com.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.*;
import java.lang.*;
import java.math.*;
import com.utils.*;
import com.service.*;
import com.entity.*;
import com.entity.view.*;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.annotation.IgnoreAuth;
import com.annotation.SysLog;

import com.entity.ShuizuguanxinxiEntity;
import com.entity.view.ShuizuguanxinxiView;

import com.service.ShuizuguanxinxiService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MPUtil;
import com.utils.MapUtils;
import com.utils.CommonUtil;
import java.io.IOException;
import com.service.StoreupService;
import com.entity.StoreupEntity;

/**
 * 水族馆信息
 * 后端接口
 * @author 
 * @email 
 * @date 2026-04-23 14:52:48
 */
@RestController
@RequestMapping("/shuizuguanxinxi")
public class ShuizuguanxinxiController {
    @Autowired
    private ShuizuguanxinxiService shuizuguanxinxiService;

    @Autowired
    private StoreupService storeupService;









    /**
     * 后台列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,ShuizuguanxinxiEntity shuizuguanxinxi,
		HttpServletRequest request){
        //设置查询条件
        EntityWrapper<ShuizuguanxinxiEntity> ew = new EntityWrapper<ShuizuguanxinxiEntity>();


        //查询结果
		PageUtils page = shuizuguanxinxiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, shuizuguanxinxi), params), params));
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(page,deSens);
        return R.ok().put("data", page);
    }


    /**
     * 前台列表
     */
	@IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,ShuizuguanxinxiEntity shuizuguanxinxi,
                @RequestParam(required = false) Double storeupnumstart,
                @RequestParam(required = false) Double storeupnumend,
		HttpServletRequest request){
        //设置查询条件
        EntityWrapper<ShuizuguanxinxiEntity> ew = new EntityWrapper<ShuizuguanxinxiEntity>();
        if(storeupnumstart!=null) ew.ge("storeupnum", storeupnumstart);
        if(storeupnumend!=null) ew.le("storeupnum", storeupnumend);

        //查询结果
		PageUtils page = shuizuguanxinxiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, shuizuguanxinxi), params), params));
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(page,deSens);
        return R.ok().put("data", page);
    }




	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( ShuizuguanxinxiEntity shuizuguanxinxi){
       	EntityWrapper<ShuizuguanxinxiEntity> ew = new EntityWrapper<ShuizuguanxinxiEntity>();
      	ew.allEq(MPUtil.allEQMapPre( shuizuguanxinxi, "shuizuguanxinxi"));
        return R.ok().put("data", shuizuguanxinxiService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(ShuizuguanxinxiEntity shuizuguanxinxi){
        EntityWrapper< ShuizuguanxinxiEntity> ew = new EntityWrapper< ShuizuguanxinxiEntity>();
 		ew.allEq(MPUtil.allEQMapPre( shuizuguanxinxi, "shuizuguanxinxi"));
		ShuizuguanxinxiView shuizuguanxinxiView =  shuizuguanxinxiService.selectView(ew);
		return R.ok("查询水族馆信息成功").put("data", shuizuguanxinxiView);
    }

    /**
     * 后台详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        ShuizuguanxinxiEntity shuizuguanxinxi = shuizuguanxinxiService.selectById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(shuizuguanxinxi,deSens);
        return R.ok().put("data", shuizuguanxinxi);
    }

    /**
     * 前台详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        ShuizuguanxinxiEntity shuizuguanxinxi = shuizuguanxinxiService.selectById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(shuizuguanxinxi,deSens);
        return R.ok().put("data", shuizuguanxinxi);
    }




    /**
     * 后台保存
     */
    @RequestMapping("/save")
    @SysLog("新增水族馆信息")
    public R save(@RequestBody ShuizuguanxinxiEntity shuizuguanxinxi, HttpServletRequest request){
        //ValidatorUtils.validateEntity(shuizuguanxinxi);
        shuizuguanxinxiService.insert(shuizuguanxinxi);
        return R.ok().put("data",shuizuguanxinxi.getId());
    }

    /**
     * 前台保存
     */
    @SysLog("新增水族馆信息")
    @RequestMapping("/add")
    public R add(@RequestBody ShuizuguanxinxiEntity shuizuguanxinxi, HttpServletRequest request){
        //ValidatorUtils.validateEntity(shuizuguanxinxi);
        shuizuguanxinxiService.insert(shuizuguanxinxi);
        return R.ok().put("data",shuizuguanxinxi.getId());
    }





    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    @SysLog("修改水族馆信息")
    public R update(@RequestBody ShuizuguanxinxiEntity shuizuguanxinxi, HttpServletRequest request){
        //ValidatorUtils.validateEntity(shuizuguanxinxi);
        //全部更新
        shuizuguanxinxiService.updateById(shuizuguanxinxi);
        return R.ok();
    }





    /**
     * 删除
     */
    @RequestMapping("/delete")
    @SysLog("删除水族馆信息")
    public R delete(@RequestBody Long[] ids){
        shuizuguanxinxiService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }










}
