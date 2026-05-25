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
import com.service.TokenService;
import com.entity.TokenEntity;
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

import com.entity.YonghuEntity;
import com.entity.view.YonghuView;

import com.service.YonghuService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MPUtil;
import com.utils.MapUtils;
import com.utils.CommonUtil;
import java.io.IOException;
import com.service.EmailregistercodeService;
import com.entity.EmailregistercodeEntity;

/**
 * 用户
 * 后端接口
 * @author 
 * @email 
 * @date 2026-04-23 14:52:48
 */
@RestController
@RequestMapping("/yonghu")
public class YonghuController {
    @Autowired
    private YonghuService yonghuService;
    @Autowired
    private EmailregistercodeService emailregistercodeService;







	@Autowired
	private TokenService tokenService;

	/**
	 * 登录
	 */
	@IgnoreAuth
	@RequestMapping(value = "/login")
	public R login(String username, String password, String captcha, HttpServletRequest request) {
		// 根据登录查询用户信息
        YonghuEntity u = yonghuService.selectOne(new EntityWrapper<YonghuEntity>().eq("yonghuzhanghao", username));
        if(u==null || !u.getMima().equals(password)) {
            //账号或密码不正确提示
			return R.error("账号或密码不正确");
		}
        // 获取登录token
        String token = tokenService.generateToken(u.getId(), username,"yonghu",  "用户" );
        //返回token
		return R.ok().put("token", token);
	}



	/**
     * 注册
     * 简化版：只需要账号 + 密码即可注册；邮箱、验证码均为可选
     * - 邮箱为空时不做邮箱重复校验
     * - emailcode 为空时跳过验证码校验
     */
	@IgnoreAuth
    @RequestMapping("/register")
    public R register(@RequestBody YonghuEntity yonghu, @RequestParam(required = false) String emailcode){
    	//ValidatorUtils.validateEntity(yonghu);
        //根据登录账号获取用户信息判断是否存在该用户，否则返回错误信息
    	YonghuEntity u = yonghuService.selectOne(new EntityWrapper<YonghuEntity>().eq("yonghuzhanghao", yonghu.getYonghuzhanghao()));
		if(u!=null) {
			return R.error("注册用户已存在");
		}
        //判断是否存在相同用户账号，否则返回错误信息
        if(yonghuService.selectCount(new EntityWrapper<YonghuEntity>().eq("yonghuzhanghao", yonghu.getYonghuzhanghao()))>0) {
            return R.error("用户账号已存在");
        }
        //邮箱非空时才做邮箱重复校验
        if(yonghu.getEmail()!=null && !yonghu.getEmail().isEmpty()) {
            u = yonghuService.selectOne(new EntityWrapper<YonghuEntity>().eq("email", yonghu.getEmail()));
            if(u!=null) {
                return R.error("邮箱已被注册");
            }
            //emailcode 传了才校验，否则跳过
            if(emailcode!=null && !emailcode.isEmpty()) {
                List<EmailregistercodeEntity> emailregistercodeList = emailregistercodeService.selectList(new EntityWrapper<EmailregistercodeEntity>().eq("role", "用户").eq("email", yonghu.getEmail()).orderBy("addtime", false));
                boolean emailValidate = false;
                if(emailregistercodeList!=null && emailregistercodeList.size()>0) {
                    if(emailregistercodeList.get(0).getCode().equals(emailcode)) {
                        emailValidate = true;
                    }
                }
                if(!emailValidate) return R.error("邮箱验证码不正确");
            }
        }
		Long uId = new Date().getTime();
		yonghu.setId(uId);
        // yonghuxingming 在数据库中为 NOT NULL，简化注册若未提供则用账号兜底
        if(yonghu.getYonghuxingming()==null || yonghu.getYonghuxingming().isEmpty()) {
            yonghu.setYonghuxingming(yonghu.getYonghuzhanghao());
        }
        //保存用户
        yonghuService.insert(yonghu);
        return R.ok();
    }

    /**
     * 发送邮件
     */
    @IgnoreAuth
    @RequestMapping("/sendemail")
    public R sendemail(@RequestParam String email){
        String code = CommonUtil.getRandomNumber(4);
        EmailregistercodeEntity emailregistercode = new EmailregistercodeEntity();
        emailregistercode.setCode(code);
        emailregistercode.setRole("用户");
        emailregistercode.setEmail(email);
        emailregistercodeService.insert(emailregistercode);
        CommonUtil.sendEmail(email, "用户注册","您的注册验证码是【"+code+"】，请不要把验证码泄漏给其他人，如非本人请勿操作。");
        return R.ok().put("data", code);
    }

    /**
     * 发送邮件
     */
    @RequestMapping("/sendemail/content")
    public R sendEmailMessage(String email, String title, String content) {
        org.springframework.util.Assert.hasLength(email, "邮箱不能为空");
        org.springframework.util.Assert.hasLength(title, "标题不能为空");
        org.springframework.util.Assert.hasLength(content, "内容不能为空");
        CommonUtil.sendEmail(email, title, content);
        return R.ok();
    }

    /**
     * 发送登录邮件
     */
    @IgnoreAuth
    @RequestMapping("/sendemail/login")
    public R sendemailForLogin(@RequestParam String email){
        YonghuEntity u = yonghuService.selectOne(new EntityWrapper<YonghuEntity>().eq("email", email));
        if(u==null) return R.error("用户不存在");
        String code = CommonUtil.getRandomNumber(4);
        EmailregistercodeEntity emailregistercode = new EmailregistercodeEntity();
        emailregistercode.setCode(code);
        emailregistercode.setRole("用户");
        emailregistercode.setEmail(email);
        emailregistercodeService.insert(emailregistercode);
        CommonUtil.sendEmail(email, "用户登录","您的登录验证码是【"+code+"】，请不要把验证码泄漏给其他人，如非本人请勿操作。");
        return R.ok().put("data", code);
    }

    /**
     * 邮箱登录
     */
    @IgnoreAuth
    @RequestMapping("/email/login")
    public R emailLogin(@RequestParam String email,@RequestParam(required = false) String emailcode){
        YonghuEntity u = yonghuService.selectOne(new EntityWrapper<YonghuEntity>().eq("email", email));
        if(u==null) return R.error("用户不存在");
        //判断验证码是否正确，否则返回错误信息
        List<EmailregistercodeEntity> emailregistercodeList = emailregistercodeService.selectList(new EntityWrapper<EmailregistercodeEntity>().eq("role", "用户").eq("email", email).orderBy("addtime", false));
        boolean emailValidate = false;
        if(emailregistercodeList!=null && emailregistercodeList.size()>0) {
            if(emailregistercodeList.get(0).getCode().equals(emailcode)) {
                emailValidate = true;
            }
        }
        if(!emailValidate) return R.error("邮箱验证码不正确");
        // 获取登录token
        String token = tokenService.generateToken(u.getId(), u.getYonghuzhanghao(),"yonghu",  "用户" );
        //返回token
        return R.ok().put("token", token).put("username", u.getYonghuzhanghao());
    }


	/**
	 * 退出
	 */
	@RequestMapping("/logout")
	public R logout(HttpServletRequest request) {
		request.getSession().invalidate();
		return R.ok("退出成功");
	}

	/**
     * 获取用户的session用户信息
     */
    @RequestMapping("/session")
    public R getCurrUser(HttpServletRequest request){
    	Long id = (Long)request.getSession().getAttribute("userId");
        YonghuEntity u = yonghuService.selectById(id);
        return R.ok().put("data", u);
    }

    /**
     * 密码重置
     */
    @IgnoreAuth
	@RequestMapping(value = "/resetPass")
    public R resetPass(String username, HttpServletRequest request){
    	//根据登录账号判断是否存在用户信息，否则返回错误信息
        YonghuEntity u = yonghuService.selectOne(new EntityWrapper<YonghuEntity>().eq("yonghuzhanghao", username));
    	if(u==null) {
    		return R.error("账号不存在");
    	}
        //重置密码为123456
        u.setMima("123456");
        yonghuService.updateById(u);
        return R.ok("密码已重置为：123456");
    }

    /**
     * 获取账号列表
     */
    @RequestMapping("/accountList")
    public R getAccountList(@RequestParam Map<String, Object> params,YonghuEntity yonghu){
        EntityWrapper<YonghuEntity> ew = new EntityWrapper<YonghuEntity>();
        Wrapper<YonghuEntity> wrapper =MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, yonghu), params), params);
        List<Map> list = yonghuService.selectList(wrapper).stream().map(v -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", v.getId());
            map.put("account", v.getYonghuzhanghao());
            map.put("email", v.getEmail());
            return map;
        }).collect(Collectors.toList());
        return R.ok().put("data", list);
    }






    /**
     * 后台列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,YonghuEntity yonghu,
		HttpServletRequest request){
        //设置查询条件
        EntityWrapper<YonghuEntity> ew = new EntityWrapper<YonghuEntity>();


        //查询结果
		PageUtils page = yonghuService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, yonghu), params), params));
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
    public R list(@RequestParam Map<String, Object> params,YonghuEntity yonghu,
                @RequestParam(required = false) Double agestart,
                @RequestParam(required = false) Double ageend,
                @RequestParam(required = false) Double moneystart,
                @RequestParam(required = false) Double moneyend,
		HttpServletRequest request){
        //设置查询条件
        EntityWrapper<YonghuEntity> ew = new EntityWrapper<YonghuEntity>();
        if(agestart!=null) ew.ge("age", agestart);
        if(ageend!=null) ew.le("age", ageend);
        if(moneystart!=null) ew.ge("money", moneystart);
        if(moneyend!=null) ew.le("money", moneyend);

        //查询结果
		PageUtils page = yonghuService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, yonghu), params), params));
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(page,deSens);
        return R.ok().put("data", page);
    }




	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( YonghuEntity yonghu){
       	EntityWrapper<YonghuEntity> ew = new EntityWrapper<YonghuEntity>();
      	ew.allEq(MPUtil.allEQMapPre( yonghu, "yonghu"));
        return R.ok().put("data", yonghuService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(YonghuEntity yonghu){
        EntityWrapper< YonghuEntity> ew = new EntityWrapper< YonghuEntity>();
 		ew.allEq(MPUtil.allEQMapPre( yonghu, "yonghu"));
		YonghuView yonghuView =  yonghuService.selectView(ew);
		return R.ok("查询用户成功").put("data", yonghuView);
    }

    /**
     * 后台详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        YonghuEntity yonghu = yonghuService.selectById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(yonghu,deSens);
        return R.ok().put("data", yonghu);
    }

    /**
     * 前台详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        YonghuEntity yonghu = yonghuService.selectById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(yonghu,deSens);
        return R.ok().put("data", yonghu);
    }




    /**
     * 后台保存
     */
    @RequestMapping("/save")
    @SysLog("新增用户")
    public R save(@RequestBody YonghuEntity yonghu, HttpServletRequest request){
        //验证字段唯一性，否则返回错误信息
        if(yonghuService.selectCount(new EntityWrapper<YonghuEntity>().eq("yonghuzhanghao", yonghu.getYonghuzhanghao()))>0) {
            return R.error("用户账号已存在");
        }
        //ValidatorUtils.validateEntity(yonghu);
        //验证账号唯一性，否则返回错误信息
        YonghuEntity u = yonghuService.selectOne(new EntityWrapper<YonghuEntity>().eq("yonghuzhanghao", yonghu.getYonghuzhanghao()));
        if(u!=null) {
            return R.error("用户已存在");
        }
    	yonghu.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
		yonghu.setId(new Date().getTime());
        yonghuService.insert(yonghu);
        return R.ok().put("data",yonghu.getId());
    }

    /**
     * 前台保存
     */
    @SysLog("新增用户")
    @RequestMapping("/add")
    public R add(@RequestBody YonghuEntity yonghu, HttpServletRequest request){
        //验证字段唯一性，否则返回错误信息
        if(yonghuService.selectCount(new EntityWrapper<YonghuEntity>().eq("yonghuzhanghao", yonghu.getYonghuzhanghao()))>0) {
            return R.error("用户账号已存在");
        }
        //ValidatorUtils.validateEntity(yonghu);
        //验证账号唯一性，否则返回错误信息
        YonghuEntity u = yonghuService.selectOne(new EntityWrapper<YonghuEntity>().eq("yonghuzhanghao", yonghu.getYonghuzhanghao()));
        if(u!=null) {
            return R.error("用户已存在");
        }
    	yonghu.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
		yonghu.setId(new Date().getTime());
        yonghuService.insert(yonghu);
        return R.ok().put("data",yonghu.getId());
    }





    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    @SysLog("修改用户")
    public R update(@RequestBody YonghuEntity yonghu, HttpServletRequest request){
        //ValidatorUtils.validateEntity(yonghu);
        //验证字段唯一性，否则返回错误信息
        if(yonghuService.selectCount(new EntityWrapper<YonghuEntity>().ne("id", yonghu.getId()).eq("yonghuzhanghao", yonghu.getYonghuzhanghao()))>0) {
            return R.error("用户账号已存在");
        }
        //全部更新
        yonghuService.updateById(yonghu);
        if(null!=yonghu.getYonghuzhanghao())
        {
            // 修改token
            TokenEntity tokenEntity = new TokenEntity();
            tokenEntity.setUsername(yonghu.getYonghuzhanghao());
            tokenService.update(tokenEntity, new EntityWrapper<TokenEntity>().eq("userid", yonghu.getId()));
        }
        return R.ok();
    }





    /**
     * 删除
     */
    @RequestMapping("/delete")
    @SysLog("删除用户")
    public R delete(@RequestBody Long[] ids){
        yonghuService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }










}
