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

import com.entity.DiscussxianshangshangchengEntity;
import com.entity.view.DiscussxianshangshangchengView;

import com.service.DiscussxianshangshangchengService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MPUtil;
import com.utils.MapUtils;
import com.utils.CommonUtil;
import java.io.IOException;

/**
 * 线上商城评论
 * 后端接口
 * @author 
 * @email 
 * @date 2026-04-23 14:52:50
 */
@RestController
@RequestMapping("/discussxianshangshangcheng")
public class DiscussxianshangshangchengController {
    @Autowired
    private DiscussxianshangshangchengService discussxianshangshangchengService;










    /**
     * 后台列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,DiscussxianshangshangchengEntity discussxianshangshangcheng,
		HttpServletRequest request){
        //设置查询条件
        EntityWrapper<DiscussxianshangshangchengEntity> ew = new EntityWrapper<DiscussxianshangshangchengEntity>();


        //查询结果
		PageUtils page = discussxianshangshangchengService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, discussxianshangshangcheng), params), params));
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
    public R list(@RequestParam Map<String, Object> params,DiscussxianshangshangchengEntity discussxianshangshangcheng,
                @RequestParam(required = false) Double thumbsupnumstart,
                @RequestParam(required = false) Double thumbsupnumend,
                @RequestParam(required = false) Double crazilynumstart,
                @RequestParam(required = false) Double crazilynumend,
                @RequestParam(required = false) Double istopstart,
                @RequestParam(required = false) Double istopend,
		HttpServletRequest request){
        //设置查询条件
        EntityWrapper<DiscussxianshangshangchengEntity> ew = new EntityWrapper<DiscussxianshangshangchengEntity>();
        if(thumbsupnumstart!=null) ew.ge("thumbsupnum", thumbsupnumstart);
        if(thumbsupnumend!=null) ew.le("thumbsupnum", thumbsupnumend);
        if(crazilynumstart!=null) ew.ge("crazilynum", crazilynumstart);
        if(crazilynumend!=null) ew.le("crazilynum", crazilynumend);
        if(istopstart!=null) ew.ge("istop", istopstart);
        if(istopend!=null) ew.le("istop", istopend);

        //查询结果
		PageUtils page = discussxianshangshangchengService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, discussxianshangshangcheng), params), params));
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(page,deSens);
        return R.ok().put("data", page);
    }




	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( DiscussxianshangshangchengEntity discussxianshangshangcheng){
       	EntityWrapper<DiscussxianshangshangchengEntity> ew = new EntityWrapper<DiscussxianshangshangchengEntity>();
      	ew.allEq(MPUtil.allEQMapPre( discussxianshangshangcheng, "discussxianshangshangcheng"));
        return R.ok().put("data", discussxianshangshangchengService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(DiscussxianshangshangchengEntity discussxianshangshangcheng){
        EntityWrapper< DiscussxianshangshangchengEntity> ew = new EntityWrapper< DiscussxianshangshangchengEntity>();
 		ew.allEq(MPUtil.allEQMapPre( discussxianshangshangcheng, "discussxianshangshangcheng"));
		DiscussxianshangshangchengView discussxianshangshangchengView =  discussxianshangshangchengService.selectView(ew);
		return R.ok("查询线上商城评论成功").put("data", discussxianshangshangchengView);
    }

    /**
     * 后台详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        DiscussxianshangshangchengEntity discussxianshangshangcheng = discussxianshangshangchengService.selectById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(discussxianshangshangcheng,deSens);
        return R.ok().put("data", discussxianshangshangcheng);
    }

    /**
     * 前台详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        DiscussxianshangshangchengEntity discussxianshangshangcheng = discussxianshangshangchengService.selectById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(discussxianshangshangcheng,deSens);
        return R.ok().put("data", discussxianshangshangcheng);
    }




    /**
     * 后台保存
     */
    @RequestMapping("/save")
    @SysLog("新增线上商城评论")
    public R save(@RequestBody DiscussxianshangshangchengEntity discussxianshangshangcheng, HttpServletRequest request){
        //ValidatorUtils.validateEntity(discussxianshangshangcheng);
        discussxianshangshangchengService.insert(discussxianshangshangcheng);
        return R.ok().put("data",discussxianshangshangcheng.getId());
    }

    /**
     * 前台保存
     */
    @SysLog("新增线上商城评论")
    @RequestMapping("/add")
    public R add(@RequestBody DiscussxianshangshangchengEntity discussxianshangshangcheng, HttpServletRequest request){
        //ValidatorUtils.validateEntity(discussxianshangshangcheng);
        discussxianshangshangchengService.insert(discussxianshangshangcheng);
        return R.ok().put("data",discussxianshangshangcheng.getId());
    }



     /**
     * 获取用户密保
     */
    @RequestMapping("/security")
    @IgnoreAuth
    public R security(@RequestParam String username){
        DiscussxianshangshangchengEntity discussxianshangshangcheng = discussxianshangshangchengService.selectOne(new EntityWrapper<DiscussxianshangshangchengEntity>().eq("", username));
        return R.ok().put("data", discussxianshangshangcheng);
    }


    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    @IgnoreAuth
    public R update(@RequestBody DiscussxianshangshangchengEntity discussxianshangshangcheng, HttpServletRequest request){
        //ValidatorUtils.validateEntity(discussxianshangshangcheng);
        //全部更新
        discussxianshangshangchengService.updateById(discussxianshangshangcheng);
        return R.ok();
    }





    /**
     * 删除
     */
    @RequestMapping("/delete")
    @SysLog("删除线上商城评论")
    public R delete(@RequestBody Long[] ids){
        discussxianshangshangchengService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }

	/**
     * 前台智能排序
     */
	@IgnoreAuth
    @RequestMapping("/autoSort")
    public R autoSort(@RequestParam Map<String, Object> params,DiscussxianshangshangchengEntity discussxianshangshangcheng, HttpServletRequest request,String pre){
        EntityWrapper<DiscussxianshangshangchengEntity> ew = new EntityWrapper<DiscussxianshangshangchengEntity>();
        Map<String, Object> newMap = new HashMap<String, Object>();
        Map<String, Object> param = new HashMap<String, Object>();
        // 组装参数
		Iterator<Map.Entry<String, Object>> it = param.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Object> entry = it.next();
			String key = entry.getKey();
			String newKey = entry.getKey();
			if (pre.endsWith(".")) {
				newMap.put(pre + newKey, entry.getValue());
			} else if (StringUtils.isEmpty(pre)) {
				newMap.put(newKey, entry.getValue());
			} else {
				newMap.put(pre + "." + newKey, entry.getValue());
			}
		}
		params.put("sort", "clicktime");
        params.put("order", "desc");

		PageUtils page = discussxianshangshangchengService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, discussxianshangshangcheng), params), params));
        return R.ok().put("data", page);
    }









}
