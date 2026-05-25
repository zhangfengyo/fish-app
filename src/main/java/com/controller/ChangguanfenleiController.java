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

import com.entity.ChangguanfenleiEntity;
import com.entity.view.ChangguanfenleiView;

import com.service.ChangguanfenleiService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MPUtil;
import com.utils.MapUtils;
import com.utils.CommonUtil;
import java.io.IOException;

/**
 * 场馆分类
 * 后端接口
 * @author 
 * @email 
 * @date 2026-04-23 14:52:48
 */
@RestController
@RequestMapping("/changguanfenlei")
public class ChangguanfenleiController {
    @Autowired
    private ChangguanfenleiService changguanfenleiService;










    /**
     * 后台列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,ChangguanfenleiEntity changguanfenlei,
		HttpServletRequest request){
        //设置查询条件
        EntityWrapper<ChangguanfenleiEntity> ew = new EntityWrapper<ChangguanfenleiEntity>();


        //查询结果
		PageUtils page = changguanfenleiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, changguanfenlei), params), params));
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
    public R list(@RequestParam Map<String, Object> params,ChangguanfenleiEntity changguanfenlei,
		HttpServletRequest request){
        //设置查询条件
        EntityWrapper<ChangguanfenleiEntity> ew = new EntityWrapper<ChangguanfenleiEntity>();

        //查询结果
		PageUtils page = changguanfenleiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, changguanfenlei), params), params));
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(page,deSens);
        return R.ok().put("data", page);
    }




	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( ChangguanfenleiEntity changguanfenlei){
       	EntityWrapper<ChangguanfenleiEntity> ew = new EntityWrapper<ChangguanfenleiEntity>();
      	ew.allEq(MPUtil.allEQMapPre( changguanfenlei, "changguanfenlei"));
        return R.ok().put("data", changguanfenleiService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(ChangguanfenleiEntity changguanfenlei){
        EntityWrapper< ChangguanfenleiEntity> ew = new EntityWrapper< ChangguanfenleiEntity>();
 		ew.allEq(MPUtil.allEQMapPre( changguanfenlei, "changguanfenlei"));
		ChangguanfenleiView changguanfenleiView =  changguanfenleiService.selectView(ew);
		return R.ok("查询场馆分类成功").put("data", changguanfenleiView);
    }

    /**
     * 后台详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        ChangguanfenleiEntity changguanfenlei = changguanfenleiService.selectById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(changguanfenlei,deSens);
        return R.ok().put("data", changguanfenlei);
    }

    /**
     * 前台详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        ChangguanfenleiEntity changguanfenlei = changguanfenleiService.selectById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(changguanfenlei,deSens);
        return R.ok().put("data", changguanfenlei);
    }




    /**
     * 后台保存
     */
    @RequestMapping("/save")
    @SysLog("新增场馆分类")
    public R save(@RequestBody ChangguanfenleiEntity changguanfenlei, HttpServletRequest request){
        //验证字段唯一性，否则返回错误信息
        if(changguanfenleiService.selectCount(new EntityWrapper<ChangguanfenleiEntity>().eq("changguanfenlei", changguanfenlei.getChangguanfenlei()))>0) {
            return R.error("场馆分类已存在");
        }
        //ValidatorUtils.validateEntity(changguanfenlei);
        changguanfenleiService.insert(changguanfenlei);
        return R.ok().put("data",changguanfenlei.getId());
    }

    /**
     * 前台保存
     */
    @SysLog("新增场馆分类")
    @RequestMapping("/add")
    public R add(@RequestBody ChangguanfenleiEntity changguanfenlei, HttpServletRequest request){
        //验证字段唯一性，否则返回错误信息
        if(changguanfenleiService.selectCount(new EntityWrapper<ChangguanfenleiEntity>().eq("changguanfenlei", changguanfenlei.getChangguanfenlei()))>0) {
            return R.error("场馆分类已存在");
        }
        //ValidatorUtils.validateEntity(changguanfenlei);
        changguanfenleiService.insert(changguanfenlei);
        return R.ok().put("data",changguanfenlei.getId());
    }





    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    @SysLog("修改场馆分类")
    public R update(@RequestBody ChangguanfenleiEntity changguanfenlei, HttpServletRequest request){
        //ValidatorUtils.validateEntity(changguanfenlei);
        //验证字段唯一性，否则返回错误信息
        if(changguanfenleiService.selectCount(new EntityWrapper<ChangguanfenleiEntity>().ne("id", changguanfenlei.getId()).eq("changguanfenlei", changguanfenlei.getChangguanfenlei()))>0) {
            return R.error("场馆分类已存在");
        }
        //全部更新
        changguanfenleiService.updateById(changguanfenlei);
        return R.ok();
    }





    /**
     * 删除
     */
    @RequestMapping("/delete")
    @SysLog("删除场馆分类")
    public R delete(@RequestBody Long[] ids){
        changguanfenleiService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }










}
