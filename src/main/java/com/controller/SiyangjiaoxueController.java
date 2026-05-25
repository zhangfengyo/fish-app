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

import com.entity.SiyangjiaoxueEntity;
import com.entity.view.SiyangjiaoxueView;

import com.service.SiyangjiaoxueService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MPUtil;
import com.utils.MapUtils;
import com.utils.CommonUtil;
import java.io.IOException;
import com.service.StoreupService;
import com.entity.StoreupEntity;

/**
 * 饲养教学
 * 后端接口
 * @author 
 * @email 
 * @date 2026-04-23 14:52:48
 */
@RestController
@RequestMapping("/siyangjiaoxue")
public class SiyangjiaoxueController {
    @Autowired
    private SiyangjiaoxueService siyangjiaoxueService;

    @Autowired
    private StoreupService storeupService;









    /**
     * 后台列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,SiyangjiaoxueEntity siyangjiaoxue,
		HttpServletRequest request){
        //设置查询条件
        EntityWrapper<SiyangjiaoxueEntity> ew = new EntityWrapper<SiyangjiaoxueEntity>();


        //查询结果
		PageUtils page = siyangjiaoxueService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, siyangjiaoxue), params), params));
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
    public R list(@RequestParam Map<String, Object> params,SiyangjiaoxueEntity siyangjiaoxue,
                @RequestParam(required = false) Double storeupnumstart,
                @RequestParam(required = false) Double storeupnumend,
		HttpServletRequest request){
        //设置查询条件
        EntityWrapper<SiyangjiaoxueEntity> ew = new EntityWrapper<SiyangjiaoxueEntity>();
        if(storeupnumstart!=null) ew.ge("storeupnum", storeupnumstart);
        if(storeupnumend!=null) ew.le("storeupnum", storeupnumend);

        //查询结果
		PageUtils page = siyangjiaoxueService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, siyangjiaoxue), params), params));
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(page,deSens);
        return R.ok().put("data", page);
    }




	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( SiyangjiaoxueEntity siyangjiaoxue){
       	EntityWrapper<SiyangjiaoxueEntity> ew = new EntityWrapper<SiyangjiaoxueEntity>();
      	ew.allEq(MPUtil.allEQMapPre( siyangjiaoxue, "siyangjiaoxue"));
        return R.ok().put("data", siyangjiaoxueService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(SiyangjiaoxueEntity siyangjiaoxue){
        EntityWrapper< SiyangjiaoxueEntity> ew = new EntityWrapper< SiyangjiaoxueEntity>();
 		ew.allEq(MPUtil.allEQMapPre( siyangjiaoxue, "siyangjiaoxue"));
		SiyangjiaoxueView siyangjiaoxueView =  siyangjiaoxueService.selectView(ew);
		return R.ok("查询饲养教学成功").put("data", siyangjiaoxueView);
    }

    /**
     * 后台详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        SiyangjiaoxueEntity siyangjiaoxue = siyangjiaoxueService.selectById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(siyangjiaoxue,deSens);
        return R.ok().put("data", siyangjiaoxue);
    }

    /**
     * 前台详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        SiyangjiaoxueEntity siyangjiaoxue = siyangjiaoxueService.selectById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(siyangjiaoxue,deSens);
        return R.ok().put("data", siyangjiaoxue);
    }




    /**
     * 后台保存
     */
    @RequestMapping("/save")
    @SysLog("新增饲养教学")
    public R save(@RequestBody SiyangjiaoxueEntity siyangjiaoxue, HttpServletRequest request){
        //ValidatorUtils.validateEntity(siyangjiaoxue);
        siyangjiaoxueService.insert(siyangjiaoxue);
        return R.ok().put("data",siyangjiaoxue.getId());
    }

    /**
     * 前台保存
     */
    @SysLog("新增饲养教学")
    @RequestMapping("/add")
    public R add(@RequestBody SiyangjiaoxueEntity siyangjiaoxue, HttpServletRequest request){
        //ValidatorUtils.validateEntity(siyangjiaoxue);
        siyangjiaoxueService.insert(siyangjiaoxue);
        return R.ok().put("data",siyangjiaoxue.getId());
    }





    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    @SysLog("修改饲养教学")
    public R update(@RequestBody SiyangjiaoxueEntity siyangjiaoxue, HttpServletRequest request){
        //ValidatorUtils.validateEntity(siyangjiaoxue);
        //全部更新
        siyangjiaoxueService.updateById(siyangjiaoxue);
        return R.ok();
    }





    /**
     * 删除
     */
    @RequestMapping("/delete")
    @SysLog("删除饲养教学")
    public R delete(@RequestBody Long[] ids){
        siyangjiaoxueService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }










}
