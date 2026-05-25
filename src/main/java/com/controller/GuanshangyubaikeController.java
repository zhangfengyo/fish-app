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

import com.entity.GuanshangyubaikeEntity;
import com.entity.view.GuanshangyubaikeView;

import com.service.GuanshangyubaikeService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MPUtil;
import com.utils.MapUtils;
import com.utils.CommonUtil;
import java.io.IOException;
import com.service.StoreupService;
import com.entity.StoreupEntity;

/**
 * 观赏鱼百科
 * 后端接口
 * @author 
 * @email 
 * @date 2026-04-23 14:52:48
 */
@RestController
@RequestMapping("/guanshangyubaike")
public class GuanshangyubaikeController {
    @Autowired
    private GuanshangyubaikeService guanshangyubaikeService;

    @Autowired
    private StoreupService storeupService;









    /**
     * 后台列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,GuanshangyubaikeEntity guanshangyubaike,
		HttpServletRequest request){
        //设置查询条件
        EntityWrapper<GuanshangyubaikeEntity> ew = new EntityWrapper<GuanshangyubaikeEntity>();


        //查询结果
		PageUtils page = guanshangyubaikeService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, guanshangyubaike), params), params));
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
    public R list(@RequestParam Map<String, Object> params,GuanshangyubaikeEntity guanshangyubaike,
                @RequestParam(required = false) Double thumbsupnumstart,
                @RequestParam(required = false) Double thumbsupnumend,
                @RequestParam(required = false) Double crazilynumstart,
                @RequestParam(required = false) Double crazilynumend,
                @RequestParam(required = false) Double clicknumstart,
                @RequestParam(required = false) Double clicknumend,
                @RequestParam(required = false) Double storeupnumstart,
                @RequestParam(required = false) Double storeupnumend,
		HttpServletRequest request){
        //设置查询条件
        EntityWrapper<GuanshangyubaikeEntity> ew = new EntityWrapper<GuanshangyubaikeEntity>();
        if(thumbsupnumstart!=null) ew.ge("thumbsupnum", thumbsupnumstart);
        if(thumbsupnumend!=null) ew.le("thumbsupnum", thumbsupnumend);
        if(crazilynumstart!=null) ew.ge("crazilynum", crazilynumstart);
        if(crazilynumend!=null) ew.le("crazilynum", crazilynumend);
        if(clicknumstart!=null) ew.ge("clicknum", clicknumstart);
        if(clicknumend!=null) ew.le("clicknum", clicknumend);
        if(storeupnumstart!=null) ew.ge("storeupnum", storeupnumstart);
        if(storeupnumend!=null) ew.le("storeupnum", storeupnumend);

        //查询结果
		PageUtils page = guanshangyubaikeService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, guanshangyubaike), params), params));
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(page,deSens);
        return R.ok().put("data", page);
    }




	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( GuanshangyubaikeEntity guanshangyubaike){
       	EntityWrapper<GuanshangyubaikeEntity> ew = new EntityWrapper<GuanshangyubaikeEntity>();
      	ew.allEq(MPUtil.allEQMapPre( guanshangyubaike, "guanshangyubaike"));
        return R.ok().put("data", guanshangyubaikeService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(GuanshangyubaikeEntity guanshangyubaike){
        EntityWrapper< GuanshangyubaikeEntity> ew = new EntityWrapper< GuanshangyubaikeEntity>();
 		ew.allEq(MPUtil.allEQMapPre( guanshangyubaike, "guanshangyubaike"));
		GuanshangyubaikeView guanshangyubaikeView =  guanshangyubaikeService.selectView(ew);
		return R.ok("查询观赏鱼百科成功").put("data", guanshangyubaikeView);
    }

    /**
     * 后台详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        GuanshangyubaikeEntity guanshangyubaike = guanshangyubaikeService.selectById(id);
        if(null==guanshangyubaike.getClicknum()){
            guanshangyubaike.setClicknum(0);
        }
		guanshangyubaike.setClicknum(guanshangyubaike.getClicknum()+1);
		guanshangyubaikeService.updateById(guanshangyubaike);
        guanshangyubaike = guanshangyubaikeService.selectView(new EntityWrapper<GuanshangyubaikeEntity>().eq("id", id));
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(guanshangyubaike,deSens);
        return R.ok().put("data", guanshangyubaike);
    }

    /**
     * 前台详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        GuanshangyubaikeEntity guanshangyubaike = guanshangyubaikeService.selectById(id);
        if(null==guanshangyubaike.getClicknum()){
            guanshangyubaike.setClicknum(0);
        }
		guanshangyubaike.setClicknum(guanshangyubaike.getClicknum()+1);
		guanshangyubaikeService.updateById(guanshangyubaike);
        guanshangyubaike = guanshangyubaikeService.selectView(new EntityWrapper<GuanshangyubaikeEntity>().eq("id", id));
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(guanshangyubaike,deSens);
        return R.ok().put("data", guanshangyubaike);
    }



    /**
     * 赞或踩
     */
    @RequestMapping("/thumbsup/{id}")
    public R vote(@PathVariable("id") String id,String type){
        GuanshangyubaikeEntity guanshangyubaike = guanshangyubaikeService.selectById(id);
        if(type.equals("1")) {
        	guanshangyubaike.setThumbsupnum(guanshangyubaike.getThumbsupnum()+1);
        } else {
        	guanshangyubaike.setCrazilynum(guanshangyubaike.getCrazilynum()+1);
        }
        guanshangyubaikeService.updateById(guanshangyubaike);
        return R.ok("投票成功");
    }

    /**
     * 后台保存
     */
    @RequestMapping("/save")
    @SysLog("新增观赏鱼百科")
    public R save(@RequestBody GuanshangyubaikeEntity guanshangyubaike, HttpServletRequest request){
        //ValidatorUtils.validateEntity(guanshangyubaike);
        guanshangyubaikeService.insert(guanshangyubaike);
        return R.ok().put("data",guanshangyubaike.getId());
    }

    /**
     * 前台保存
     */
    @SysLog("新增观赏鱼百科")
    @RequestMapping("/add")
    public R add(@RequestBody GuanshangyubaikeEntity guanshangyubaike, HttpServletRequest request){
        //ValidatorUtils.validateEntity(guanshangyubaike);
        guanshangyubaikeService.insert(guanshangyubaike);
        return R.ok().put("data",guanshangyubaike.getId());
    }





    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    @SysLog("修改观赏鱼百科")
    public R update(@RequestBody GuanshangyubaikeEntity guanshangyubaike, HttpServletRequest request){
        //ValidatorUtils.validateEntity(guanshangyubaike);
        //全部更新
        guanshangyubaikeService.updateById(guanshangyubaike);
        return R.ok();
    }





    /**
     * 删除
     */
    @RequestMapping("/delete")
    @SysLog("删除观赏鱼百科")
    public R delete(@RequestBody Long[] ids){
        guanshangyubaikeService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }










}
