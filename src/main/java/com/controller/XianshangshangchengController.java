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

import com.entity.XianshangshangchengEntity;
import com.entity.view.XianshangshangchengView;

import com.service.XianshangshangchengService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MPUtil;
import com.utils.MapUtils;
import com.utils.CommonUtil;
import java.io.IOException;
import com.service.StoreupService;
import com.entity.StoreupEntity;

/**
 * 线上商城
 * 后端接口
 * @author 
 * @email 
 * @date 2026-04-23 14:52:49
 */
@RestController
@RequestMapping("/xianshangshangcheng")
public class XianshangshangchengController {
    @Autowired
    private XianshangshangchengService xianshangshangchengService;

    @Autowired
    private StoreupService storeupService;









    /**
     * 后台列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,XianshangshangchengEntity xianshangshangcheng,
                @RequestParam(required = false) Double pricestart,
                @RequestParam(required = false) Double priceend,
		HttpServletRequest request){
        //设置查询条件
        EntityWrapper<XianshangshangchengEntity> ew = new EntityWrapper<XianshangshangchengEntity>();
        if(pricestart!=null) ew.ge("price", pricestart);
        if(priceend!=null) ew.le("price", priceend);


        //查询结果
		PageUtils page = xianshangshangchengService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, xianshangshangcheng), params), params));
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
    public R list(@RequestParam Map<String, Object> params,XianshangshangchengEntity xianshangshangcheng,
                @RequestParam(required = false) Double onelimittimesstart,
                @RequestParam(required = false) Double onelimittimesend,
                @RequestParam(required = false) Double alllimittimesstart,
                @RequestParam(required = false) Double alllimittimesend,
                @RequestParam(required = false) Double pricestart,
                @RequestParam(required = false) Double priceend,
                @RequestParam(required = false) Double thumbsupnumstart,
                @RequestParam(required = false) Double thumbsupnumend,
                @RequestParam(required = false) Double crazilynumstart,
                @RequestParam(required = false) Double crazilynumend,
                @RequestParam(required = false) Double clicknumstart,
                @RequestParam(required = false) Double clicknumend,
                @RequestParam(required = false) Double discussnumstart,
                @RequestParam(required = false) Double discussnumend,
                @RequestParam(required = false) Double onshelvesstart,
                @RequestParam(required = false) Double onshelvesend,
                @RequestParam(required = false) Double storeupnumstart,
                @RequestParam(required = false) Double storeupnumend,
		HttpServletRequest request){
        //设置查询条件
        EntityWrapper<XianshangshangchengEntity> ew = new EntityWrapper<XianshangshangchengEntity>();
        if(onelimittimesstart!=null) ew.ge("onelimittimes", onelimittimesstart);
        if(onelimittimesend!=null) ew.le("onelimittimes", onelimittimesend);
        if(alllimittimesstart!=null) ew.ge("alllimittimes", alllimittimesstart);
        if(alllimittimesend!=null) ew.le("alllimittimes", alllimittimesend);
        if(pricestart!=null) ew.ge("price", pricestart);
        if(priceend!=null) ew.le("price", priceend);
        if(thumbsupnumstart!=null) ew.ge("thumbsupnum", thumbsupnumstart);
        if(thumbsupnumend!=null) ew.le("thumbsupnum", thumbsupnumend);
        if(crazilynumstart!=null) ew.ge("crazilynum", crazilynumstart);
        if(crazilynumend!=null) ew.le("crazilynum", crazilynumend);
        if(clicknumstart!=null) ew.ge("clicknum", clicknumstart);
        if(clicknumend!=null) ew.le("clicknum", clicknumend);
        if(discussnumstart!=null) ew.ge("discussnum", discussnumstart);
        if(discussnumend!=null) ew.le("discussnum", discussnumend);
        if(onshelvesstart!=null) ew.ge("onshelves", onshelvesstart);
        if(onshelvesend!=null) ew.le("onshelves", onshelvesend);
        if(storeupnumstart!=null) ew.ge("storeupnum", storeupnumstart);
        if(storeupnumend!=null) ew.le("storeupnum", storeupnumend);

        //查询结果
		PageUtils page = xianshangshangchengService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, xianshangshangcheng), params), params));
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(page,deSens);
        return R.ok().put("data", page);
    }




	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( XianshangshangchengEntity xianshangshangcheng){
       	EntityWrapper<XianshangshangchengEntity> ew = new EntityWrapper<XianshangshangchengEntity>();
      	ew.allEq(MPUtil.allEQMapPre( xianshangshangcheng, "xianshangshangcheng"));
        return R.ok().put("data", xianshangshangchengService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(XianshangshangchengEntity xianshangshangcheng){
        EntityWrapper< XianshangshangchengEntity> ew = new EntityWrapper< XianshangshangchengEntity>();
 		ew.allEq(MPUtil.allEQMapPre( xianshangshangcheng, "xianshangshangcheng"));
		XianshangshangchengView xianshangshangchengView =  xianshangshangchengService.selectView(ew);
		return R.ok("查询线上商城成功").put("data", xianshangshangchengView);
    }

    /**
     * 后台详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        XianshangshangchengEntity xianshangshangcheng = xianshangshangchengService.selectById(id);
        if(null==xianshangshangcheng.getClicknum()){
            xianshangshangcheng.setClicknum(0);
        }
		xianshangshangcheng.setClicknum(xianshangshangcheng.getClicknum()+1);
		xianshangshangchengService.updateById(xianshangshangcheng);
        xianshangshangcheng = xianshangshangchengService.selectView(new EntityWrapper<XianshangshangchengEntity>().eq("id", id));
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(xianshangshangcheng,deSens);
        return R.ok().put("data", xianshangshangcheng);
    }

    /**
     * 前台详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        XianshangshangchengEntity xianshangshangcheng = xianshangshangchengService.selectById(id);
        if(null==xianshangshangcheng.getClicknum()){
            xianshangshangcheng.setClicknum(0);
        }
		xianshangshangcheng.setClicknum(xianshangshangcheng.getClicknum()+1);
		xianshangshangchengService.updateById(xianshangshangcheng);
        xianshangshangcheng = xianshangshangchengService.selectView(new EntityWrapper<XianshangshangchengEntity>().eq("id", id));
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(xianshangshangcheng,deSens);
        return R.ok().put("data", xianshangshangcheng);
    }



    /**
     * 赞或踩
     */
    @RequestMapping("/thumbsup/{id}")
    public R vote(@PathVariable("id") String id,String type){
        XianshangshangchengEntity xianshangshangcheng = xianshangshangchengService.selectById(id);
        if(type.equals("1")) {
        	xianshangshangcheng.setThumbsupnum(xianshangshangcheng.getThumbsupnum()+1);
        } else {
        	xianshangshangcheng.setCrazilynum(xianshangshangcheng.getCrazilynum()+1);
        }
        xianshangshangchengService.updateById(xianshangshangcheng);
        return R.ok("投票成功");
    }

    /**
     * 后台保存
     */
    @RequestMapping("/save")
    @SysLog("新增线上商城")
    public R save(@RequestBody XianshangshangchengEntity xianshangshangcheng, HttpServletRequest request){
        //ValidatorUtils.validateEntity(xianshangshangcheng);
        xianshangshangchengService.insert(xianshangshangcheng);
        return R.ok().put("data",xianshangshangcheng.getId());
    }

    /**
     * 前台保存
     */
    @SysLog("新增线上商城")
    @RequestMapping("/add")
    public R add(@RequestBody XianshangshangchengEntity xianshangshangcheng, HttpServletRequest request){
        //ValidatorUtils.validateEntity(xianshangshangcheng);
        xianshangshangchengService.insert(xianshangshangcheng);
        return R.ok().put("data",xianshangshangcheng.getId());
    }





    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    @SysLog("修改线上商城")
    public R update(@RequestBody XianshangshangchengEntity xianshangshangcheng, HttpServletRequest request){
        //ValidatorUtils.validateEntity(xianshangshangcheng);
        //全部更新
        xianshangshangchengService.updateById(xianshangshangcheng);
        return R.ok();
    }





    /**
     * 删除
     */
    @RequestMapping("/delete")
    @SysLog("删除线上商城")
    public R delete(@RequestBody Long[] ids){
        xianshangshangchengService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }




    /**
     * 协同算法（按收藏推荐）
     */
    @RequestMapping("/autoSort2")
    public R autoSort2(@RequestParam Map<String, Object> params,XianshangshangchengEntity xianshangshangcheng, HttpServletRequest request){
        String userId = request.getSession().getAttribute("userId").toString();
        String inteltypeColumn = "shangpinfenlei";
        // 查询收藏集合
        List<StoreupEntity> storeups = storeupService.selectList(new EntityWrapper<StoreupEntity>().eq("type", 1).eq("userid", userId).eq("tablename", "xianshangshangcheng").orderBy("addtime", false));
        List<String> inteltypes = new ArrayList<String>();
        Integer limit = params.get("limit")==null?10:Integer.parseInt(params.get("limit").toString());
        List<XianshangshangchengEntity> xianshangshangchengList = new ArrayList<XianshangshangchengEntity>();
        //去重
        if(storeups!=null && storeups.size()>0) {
            List<String> typeList = new ArrayList<String>();
            for(StoreupEntity s : storeups) {
                if(typeList.contains(s.getInteltype())) continue;
                typeList.add(s.getInteltype());
                xianshangshangchengList.addAll(xianshangshangchengService.selectList(new EntityWrapper<XianshangshangchengEntity>().eq(inteltypeColumn, s.getInteltype())));
            }
        }
        EntityWrapper<XianshangshangchengEntity> ew = new EntityWrapper<XianshangshangchengEntity>();
        params.put("sort", "id");
        params.put("order", "desc");
        // 根据协同结果查询结果并返回
        PageUtils page = xianshangshangchengService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, xianshangshangcheng), params), params));
        List<XianshangshangchengEntity> pageList = (List<XianshangshangchengEntity>)page.getList();
        if(xianshangshangchengList.size()<limit) {
            int toAddNum = (limit-xianshangshangchengList.size())<=pageList.size()?(limit-xianshangshangchengList.size()):pageList.size();
            for(XianshangshangchengEntity o1 : pageList) {
                boolean addFlag = true;
                for(XianshangshangchengEntity o2 : xianshangshangchengList) {
                    if(o1.getId().intValue()==o2.getId().intValue()) {
                        addFlag = false;
                        break;
                    }
                }
                if(addFlag) {
                    xianshangshangchengList.add(o1);
                    if(--toAddNum==0) break;
                }
            }
        } else if(xianshangshangchengList.size()>limit) {
            xianshangshangchengList = xianshangshangchengList.subList(0, limit);
        }
        page.setList(xianshangshangchengList);
        return R.ok().put("data", page);
    }






}
