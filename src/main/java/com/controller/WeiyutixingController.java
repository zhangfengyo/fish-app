package com.controller;

import com.annotation.IgnoreAuth;
import com.annotation.SysLog;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.entity.TokenEntity;
import com.entity.WeiyutixingEntity;
import com.entity.YonghuEntity;
import com.entity.view.WeiyutixingView;
import com.service.TokenService;
import com.service.WeiyutixingService;
import com.service.YonghuService;
import com.utils.CommonUtil;
import com.utils.MPUtil;
import com.utils.PageUtils;
import com.utils.R;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/weiyutixing")
public class WeiyutixingController {

    @Autowired
    private WeiyutixingService weiyutixingService;

    @Autowired
    private YonghuService yonghuService;

    @Autowired
    private TokenService tokenService;

    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params, WeiyutixingEntity weiyutixing,
                  @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date tixingriqistart,
                  @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date tixingriqiend,
                  HttpServletRequest request) {
        applyUserScope(weiyutixing, request);
        EntityWrapper<WeiyutixingEntity> ew = buildDateWrapper(tixingriqistart, tixingriqiend);
        PageUtils page = weiyutixingService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, weiyutixing), params), params));
        return R.ok().put("data", page);
    }

    @IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params, WeiyutixingEntity weiyutixing,
                  @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date tixingriqistart,
                  @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date tixingriqiend,
                  HttpServletRequest request) {
        applyUserScope(weiyutixing, request);
        EntityWrapper<WeiyutixingEntity> ew = buildDateWrapper(tixingriqistart, tixingriqiend);
        PageUtils page = weiyutixingService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, weiyutixing), params), params));
        return R.ok().put("data", page);
    }

    @RequestMapping("/lists")
    public R lists(WeiyutixingEntity weiyutixing) {
        EntityWrapper<WeiyutixingEntity> ew = new EntityWrapper<>();
        ew.allEq(MPUtil.allEQMapPre(weiyutixing, "weiyutixing"));
        return R.ok().put("data", weiyutixingService.selectListView(ew));
    }

    @RequestMapping("/query")
    public R query(WeiyutixingEntity weiyutixing) {
        EntityWrapper<WeiyutixingEntity> ew = new EntityWrapper<>();
        ew.allEq(MPUtil.allEQMapPre(weiyutixing, "weiyutixing"));
        WeiyutixingView view = weiyutixingService.selectView(ew);
        return R.ok("Reminder query success").put("data", view);
    }

    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        return R.ok().put("data", weiyutixingService.selectById(id));
    }

    @IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id) {
        return R.ok().put("data", weiyutixingService.selectById(id));
    }

    @RequestMapping("/save")
    @SysLog("新增喂鱼提醒")
    public R save(@RequestBody WeiyutixingEntity weiyutixing, HttpServletRequest request) {
        applyUserScope(weiyutixing, request);
        weiyutixingService.insert(weiyutixing);
        return R.ok().put("data", weiyutixing.getId());
    }

    @RequestMapping("/add")
    @SysLog("新增喂鱼提醒")
    public R add(@RequestBody WeiyutixingEntity weiyutixing, HttpServletRequest request) {
        applyUserScope(weiyutixing, request);
        weiyutixingService.insert(weiyutixing);
        return R.ok().put("data", weiyutixing.getId());
    }

    @RequestMapping("/update")
    @Transactional
    @SysLog("修改喂鱼提醒")
    public R update(@RequestBody WeiyutixingEntity weiyutixing, HttpServletRequest request) {
        if (!canOperateReminder(weiyutixing.getId(), request)) {
            return R.error(403, "No permission to modify this reminder");
        }
        applyUserScope(weiyutixing, request);
        weiyutixingService.updateById(weiyutixing);
        return R.ok();
    }

    @RequestMapping("/delete")
    @SysLog("删除喂鱼提醒")
    public R delete(@RequestBody Long[] ids, HttpServletRequest request) {
        if (!canDeleteReminders(ids, request)) {
            return R.error(403, "No permission to delete selected reminders");
        }
        weiyutixingService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }

    @RequestMapping("/remind/{columnName}/{type}")
    public R remindCount(@PathVariable("columnName") String columnName,
                         @PathVariable("type") String type,
                         HttpServletRequest request) {
        Wrapper<WeiyutixingEntity> wrapper = new EntityWrapper<>();
        Map<String, Object> params = CommonUtil.parseRequest(request);
        Object remindStart = params.get("remindstart") != null ? params.get("remindstart").toString() : null;
        Object remindEnd = params.get("remindend") != null ? params.get("remindend").toString() : null;
        params.remove("remindstart");
        params.remove("remindend");

        params.keySet().forEach(key -> {
            Object value = params.get(key);
            if (value != null && !"".equals(value)) {
                wrapper.eq(key, value);
            }
        });

        if ("1".equals(type)) {
            if (remindStart != null && remindEnd != null) {
                if (Double.parseDouble(remindStart.toString()) < Double.parseDouble(remindEnd.toString())) {
                    wrapper.ge(columnName, remindStart).le(columnName, remindEnd);
                } else {
                    wrapper.ge(columnName, remindStart).or().le(columnName, remindEnd);
                }
            } else if (remindStart == null && remindEnd != null) {
                wrapper.le(columnName, remindEnd);
            } else if (remindStart != null) {
                wrapper.ge(columnName, remindStart);
            }
        } else if ("2".equals(type)) {
            Date startDate = null;
            Date endDate = null;
            if (remindStart != null) {
                Calendar c = Calendar.getInstance();
                c.setTime(new Date());
                c.add(Calendar.DAY_OF_MONTH, Integer.parseInt(remindStart.toString()));
                startDate = c.getTime();
            }
            if (remindEnd != null) {
                Calendar c = Calendar.getInstance();
                c.setTime(new Date());
                c.add(Calendar.DAY_OF_MONTH, Integer.parseInt(remindEnd.toString()));
                endDate = c.getTime();
            }
            if (startDate != null && endDate != null) {
                if (startDate.before(endDate)) {
                    wrapper.ge(columnName, startDate).le(columnName, endDate);
                } else {
                    wrapper.ge(columnName, startDate).or().le(columnName, endDate);
                }
            } else if (startDate == null && endDate != null) {
                wrapper.le(columnName, endDate);
            } else if (startDate != null) {
                wrapper.ge(columnName, startDate);
            }
        } else if ("3".equals(type) && remindStart != null) {
            wrapper.in(columnName, Arrays.asList(String.valueOf(remindStart).replace("锛?", ",").split(",")));
        }

        TokenEntity tokenEntity = getCurrentTokenEntity(request);
        if (tokenEntity != null && "yonghu".equals(tokenEntity.getTablename())) {
            wrapper.andNew().eq("yonghuzhanghao", tokenEntity.getUsername());
        }

        List<WeiyutixingEntity> list = weiyutixingService.selectList(wrapper);
        Map<String, Object> res = new HashMap<>();
        res.put("count", list.size());
        res.put("data", list.stream().map(WeiyutixingEntity::getTixingbiaoti).collect(Collectors.toList()));
        return R.ok(res);
    }

    private EntityWrapper<WeiyutixingEntity> buildDateWrapper(Date start, Date end) {
        EntityWrapper<WeiyutixingEntity> ew = new EntityWrapper<>();
        if (start != null) {
            ew.ge("tixingriqi", start);
        }
        if (end != null) {
            ew.le("tixingriqi", end);
        }
        return ew;
    }

    private void applyUserScope(WeiyutixingEntity entity, HttpServletRequest request) {
        TokenEntity tokenEntity = getCurrentTokenEntity(request);
        if (tokenEntity != null && "yonghu".equals(tokenEntity.getTablename())) {
            entity.setYonghuzhanghao(tokenEntity.getUsername());
            if (tokenEntity.getUserid() != null) {
                YonghuEntity yonghu = yonghuService.selectById(tokenEntity.getUserid());
                if (yonghu != null) {
                    entity.setYonghuxingming(yonghu.getYonghuxingming());
                }
            }
        }
    }

    private boolean canOperateReminder(Long id, HttpServletRequest request) {
        if (id == null) {
            return true;
        }
        TokenEntity tokenEntity = getCurrentTokenEntity(request);
        if (tokenEntity != null && "yonghu".equals(tokenEntity.getTablename())) {
            WeiyutixingEntity existing = weiyutixingService.selectById(id);
            return existing != null && StringUtils.equals(tokenEntity.getUsername(), existing.getYonghuzhanghao());
        }
        return true;
    }

    private boolean canDeleteReminders(Long[] ids, HttpServletRequest request) {
        TokenEntity tokenEntity = getCurrentTokenEntity(request);
        if (tokenEntity != null && "yonghu".equals(tokenEntity.getTablename())) {
            List<WeiyutixingEntity> owned = weiyutixingService.selectList(
                    new EntityWrapper<WeiyutixingEntity>()
                            .in("id", Arrays.asList(ids))
                            .eq("yonghuzhanghao", tokenEntity.getUsername())
            );
            return owned.size() == ids.length;
        }
        return true;
    }

    private TokenEntity getCurrentTokenEntity(HttpServletRequest request) {
        String token = request.getHeader("Token");
        if (StringUtils.isBlank(token)) {
            return null;
        }
        try {
            return tokenService.getTokenEntity(token);
        } catch (Exception e) {
            return null;
        }
    }
}
