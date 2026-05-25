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

import com.entity.ShangpinfenleiEntity;
import com.entity.view.ShangpinfenleiView;

import com.service.ShangpinfenleiService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MPUtil;
import com.utils.MapUtils;
import com.utils.CommonUtil;
import java.io.IOException;

/**
 * 商品分类
 * 后端接口
 * @author 
 * @email 
 * @date 2026-04-23 14:52:48
 */
@RestController
@RequestMapping("/shangpinfenlei")
public class ShangpinfenleiController {
    @Autowired
    private ShangpinfenleiService shangpinfenleiService;










    /**
     * 后台列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,ShangpinfenleiEntity shangpinfenlei,
		HttpServletRequest request){
        //设置查询条件
        EntityWrapper<ShangpinfenleiEntity> ew = new EntityWrapper<ShangpinfenleiEntity>();


        //查询结果
		PageUtils page = shangpinfenleiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, shangpinfenlei), params), params));
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
    public R list(@RequestParam Map<String, Object> params,ShangpinfenleiEntity shangpinfenlei,
		HttpServletRequest request){
        //设置查询条件
        EntityWrapper<ShangpinfenleiEntity> ew = new EntityWrapper<ShangpinfenleiEntity>();

        //查询结果
		PageUtils page = shangpinfenleiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, shangpinfenlei), params), params));
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(page,deSens);
        return R.ok().put("data", page);
    }




	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( ShangpinfenleiEntity shangpinfenlei){
       	EntityWrapper<ShangpinfenleiEntity> ew = new EntityWrapper<ShangpinfenleiEntity>();
      	ew.allEq(MPUtil.allEQMapPre( shangpinfenlei, "shangpinfenlei"));
        return R.ok().put("data", shangpinfenleiService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(ShangpinfenleiEntity shangpinfenlei){
        EntityWrapper< ShangpinfenleiEntity> ew = new EntityWrapper< ShangpinfenleiEntity>();
 		ew.allEq(MPUtil.allEQMapPre( shangpinfenlei, "shangpinfenlei"));
		ShangpinfenleiView shangpinfenleiView =  shangpinfenleiService.selectView(ew);
		return R.ok("查询商品分类成功").put("data", shangpinfenleiView);
    }

    /**
     * 后台详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        ShangpinfenleiEntity shangpinfenlei = shangpinfenleiService.selectById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(shangpinfenlei,deSens);
        return R.ok().put("data", shangpinfenlei);
    }

    /**
     * 前台详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        ShangpinfenleiEntity shangpinfenlei = shangpinfenleiService.selectById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(shangpinfenlei,deSens);
        return R.ok().put("data", shangpinfenlei);
    }




    /**
     * 后台保存
     */
    @RequestMapping("/save")
    @SysLog("新增商品分类")
    public R save(@RequestBody ShangpinfenleiEntity shangpinfenlei, HttpServletRequest request){
        //验证字段唯一性，否则返回错误信息
        if(shangpinfenleiService.selectCount(new EntityWrapper<ShangpinfenleiEntity>().eq("shangpinfenlei", shangpinfenlei.getShangpinfenlei()))>0) {
            return R.error("商品分类已存在");
        }
        //ValidatorUtils.validateEntity(shangpinfenlei);
        shangpinfenleiService.insert(shangpinfenlei);
        return R.ok().put("data",shangpinfenlei.getId());
    }

    /**
     * 前台保存
     */
    @SysLog("新增商品分类")
    @RequestMapping("/add")
    public R add(@RequestBody ShangpinfenleiEntity shangpinfenlei, HttpServletRequest request){
        //验证字段唯一性，否则返回错误信息
        if(shangpinfenleiService.selectCount(new EntityWrapper<ShangpinfenleiEntity>().eq("shangpinfenlei", shangpinfenlei.getShangpinfenlei()))>0) {
            return R.error("商品分类已存在");
        }
        //ValidatorUtils.validateEntity(shangpinfenlei);
        shangpinfenleiService.insert(shangpinfenlei);
        return R.ok().put("data",shangpinfenlei.getId());
    }





    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    @SysLog("修改商品分类")
    public R update(@RequestBody ShangpinfenleiEntity shangpinfenlei, HttpServletRequest request){
        //ValidatorUtils.validateEntity(shangpinfenlei);
        //验证字段唯一性，否则返回错误信息
        if(shangpinfenleiService.selectCount(new EntityWrapper<ShangpinfenleiEntity>().ne("id", shangpinfenlei.getId()).eq("shangpinfenlei", shangpinfenlei.getShangpinfenlei()))>0) {
            return R.error("商品分类已存在");
        }
        //全部更新
        shangpinfenleiService.updateById(shangpinfenlei);
        return R.ok();
    }





    /**
     * 删除
     */
    @RequestMapping("/delete")
    @SysLog("删除商品分类")
    public R delete(@RequestBody Long[] ids){
        shangpinfenleiService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }










}
