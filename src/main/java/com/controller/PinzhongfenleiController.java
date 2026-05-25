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

import com.entity.PinzhongfenleiEntity;
import com.entity.view.PinzhongfenleiView;

import com.service.PinzhongfenleiService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MPUtil;
import com.utils.MapUtils;
import com.utils.CommonUtil;
import java.io.IOException;

/**
 * 品种分类
 * 后端接口
 * @author 
 * @email 
 * @date 2026-04-23 14:52:48
 */
@RestController
@RequestMapping("/pinzhongfenlei")
public class PinzhongfenleiController {
    @Autowired
    private PinzhongfenleiService pinzhongfenleiService;










    /**
     * 后台列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,PinzhongfenleiEntity pinzhongfenlei,
		HttpServletRequest request){
        //设置查询条件
        EntityWrapper<PinzhongfenleiEntity> ew = new EntityWrapper<PinzhongfenleiEntity>();


        //查询结果
		PageUtils page = pinzhongfenleiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, pinzhongfenlei), params), params));
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
    public R list(@RequestParam Map<String, Object> params,PinzhongfenleiEntity pinzhongfenlei,
		HttpServletRequest request){
        //设置查询条件
        EntityWrapper<PinzhongfenleiEntity> ew = new EntityWrapper<PinzhongfenleiEntity>();

        //查询结果
		PageUtils page = pinzhongfenleiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, pinzhongfenlei), params), params));
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(page,deSens);
        return R.ok().put("data", page);
    }




	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( PinzhongfenleiEntity pinzhongfenlei){
       	EntityWrapper<PinzhongfenleiEntity> ew = new EntityWrapper<PinzhongfenleiEntity>();
      	ew.allEq(MPUtil.allEQMapPre( pinzhongfenlei, "pinzhongfenlei"));
        return R.ok().put("data", pinzhongfenleiService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(PinzhongfenleiEntity pinzhongfenlei){
        EntityWrapper< PinzhongfenleiEntity> ew = new EntityWrapper< PinzhongfenleiEntity>();
 		ew.allEq(MPUtil.allEQMapPre( pinzhongfenlei, "pinzhongfenlei"));
		PinzhongfenleiView pinzhongfenleiView =  pinzhongfenleiService.selectView(ew);
		return R.ok("查询品种分类成功").put("data", pinzhongfenleiView);
    }

    /**
     * 后台详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        PinzhongfenleiEntity pinzhongfenlei = pinzhongfenleiService.selectById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(pinzhongfenlei,deSens);
        return R.ok().put("data", pinzhongfenlei);
    }

    /**
     * 前台详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        PinzhongfenleiEntity pinzhongfenlei = pinzhongfenleiService.selectById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(pinzhongfenlei,deSens);
        return R.ok().put("data", pinzhongfenlei);
    }




    /**
     * 后台保存
     */
    @RequestMapping("/save")
    @SysLog("新增品种分类")
    public R save(@RequestBody PinzhongfenleiEntity pinzhongfenlei, HttpServletRequest request){
        //验证字段唯一性，否则返回错误信息
        if(pinzhongfenleiService.selectCount(new EntityWrapper<PinzhongfenleiEntity>().eq("pinzhongfenlei", pinzhongfenlei.getPinzhongfenlei()))>0) {
            return R.error("品种分类已存在");
        }
        //ValidatorUtils.validateEntity(pinzhongfenlei);
        pinzhongfenleiService.insert(pinzhongfenlei);
        return R.ok().put("data",pinzhongfenlei.getId());
    }

    /**
     * 前台保存
     */
    @SysLog("新增品种分类")
    @RequestMapping("/add")
    public R add(@RequestBody PinzhongfenleiEntity pinzhongfenlei, HttpServletRequest request){
        //验证字段唯一性，否则返回错误信息
        if(pinzhongfenleiService.selectCount(new EntityWrapper<PinzhongfenleiEntity>().eq("pinzhongfenlei", pinzhongfenlei.getPinzhongfenlei()))>0) {
            return R.error("品种分类已存在");
        }
        //ValidatorUtils.validateEntity(pinzhongfenlei);
        pinzhongfenleiService.insert(pinzhongfenlei);
        return R.ok().put("data",pinzhongfenlei.getId());
    }





    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    @SysLog("修改品种分类")
    public R update(@RequestBody PinzhongfenleiEntity pinzhongfenlei, HttpServletRequest request){
        //ValidatorUtils.validateEntity(pinzhongfenlei);
        //验证字段唯一性，否则返回错误信息
        if(pinzhongfenleiService.selectCount(new EntityWrapper<PinzhongfenleiEntity>().ne("id", pinzhongfenlei.getId()).eq("pinzhongfenlei", pinzhongfenlei.getPinzhongfenlei()))>0) {
            return R.error("品种分类已存在");
        }
        //全部更新
        pinzhongfenleiService.updateById(pinzhongfenlei);
        return R.ok();
    }





    /**
     * 删除
     */
    @RequestMapping("/delete")
    @SysLog("删除品种分类")
    public R delete(@RequestBody Long[] ids){
        pinzhongfenleiService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }










}
