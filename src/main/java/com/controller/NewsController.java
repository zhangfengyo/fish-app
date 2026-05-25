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

import com.entity.NewsEntity;
import com.entity.view.NewsView;

import com.service.NewsService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MPUtil;
import com.utils.MapUtils;
import com.utils.CommonUtil;
import java.io.IOException;

/**
 * 观赏鱼资讯
 * 后端接口
 * @author 
 * @email 
 * @date 2026-04-23 14:52:49
 */
@RestController
@RequestMapping("/news")
public class NewsController {
    @Autowired
    private NewsService newsService;
    @Autowired
    private StoreupService storeupService;
    @Autowired
    private SiyangjiaoxueService siyangjiaoxueService;
    @Autowired
    private GuanshangyubaikeService guanshangyubaikeService;










    /**
     * 后台列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,NewsEntity news,
		HttpServletRequest request){
        //设置查询条件
        EntityWrapper<NewsEntity> ew = new EntityWrapper<NewsEntity>();


        //查询结果
		PageUtils page = newsService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, news), params), params));
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
    public R list(@RequestParam Map<String, Object> params,NewsEntity news,
		HttpServletRequest request){
        //设置查询条件
        EntityWrapper<NewsEntity> ew = new EntityWrapper<NewsEntity>();

        //查询结果
		PageUtils page = newsService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, news), params), params));
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(page,deSens);
        return R.ok().put("data", page);
    }

    /**
     * 涓€у寲鎺ㄨ崘
     */
    @IgnoreAuth
    @RequestMapping("/recommend")
    public R recommend(@RequestParam Map<String, Object> params, HttpServletRequest request){
        int limit = 6;
        if(params.get("limit") != null) {
            try {
                limit = Integer.parseInt(String.valueOf(params.get("limit")));
            } catch (Exception ignored) {
            }
        }
        if(limit <= 0) {
            limit = 6;
        }

        List<NewsEntity> result = new ArrayList<>();
        boolean personalized = false;
        String preference = null;
        Map<String, Integer> preferenceCount = new LinkedHashMap<>();
        Set<String> keywords = new LinkedHashSet<>();

        Object tableNameObj = request.getSession().getAttribute("tableName");
        Object userIdObj = request.getSession().getAttribute("userId");
        if(tableNameObj != null && userIdObj != null && "yonghu".equals(String.valueOf(tableNameObj))) {
            Long userId = Long.valueOf(String.valueOf(userIdObj));
            List<StoreupEntity> storeupList = storeupService.selectList(
                    new EntityWrapper<StoreupEntity>()
                            .eq("userid", userId)
                            .eq("type", "1")
                            .in("tablename", Arrays.asList("siyangjiaoxue", "guanshangyubaike"))
                            .orderBy("addtime", false)
            );

            for (StoreupEntity storeup : storeupList) {
                if("siyangjiaoxue".equals(storeup.getTablename())) {
                    SiyangjiaoxueEntity siyangjiaoxue = siyangjiaoxueService.selectById(storeup.getRefid());
                    if(siyangjiaoxue != null) {
                        collectPreference(
                                preferenceCount,
                                keywords,
                                siyangjiaoxue.getPinzhongfenlei(),
                                siyangjiaoxue.getJiaoxuebiaoti(),
                                siyangjiaoxue.getSiyangfangfa(),
                                siyangjiaoxue.getYubingzhiliao(),
                                siyangjiaoxue.getJiaoxuexiangqing()
                        );
                    } else {
                        collectPreference(preferenceCount, keywords, storeup.getInteltype(), storeup.getName(), storeup.getRemark());
                    }
                }
                if("guanshangyubaike".equals(storeup.getTablename())) {
                    GuanshangyubaikeEntity encyclopedia = guanshangyubaikeService.selectById(storeup.getRefid());
                    if(encyclopedia != null) {
                        collectPreference(
                                preferenceCount,
                                keywords,
                                encyclopedia.getPinzhongfenlei(),
                                encyclopedia.getZhongwenming(),
                                encyclopedia.getYingwenming(),
                                encyclopedia.getXingqing(),
                                encyclopedia.getSiyangfangfa(),
                                encyclopedia.getShengwujianjie()
                        );
                    } else {
                        collectPreference(preferenceCount, keywords, storeup.getInteltype(), storeup.getName(), storeup.getRemark());
                    }
                }
            }

            int maxCount = 0;
            for (Map.Entry<String, Integer> entry : preferenceCount.entrySet()) {
                if(entry.getValue() > maxCount) {
                    maxCount = entry.getValue();
                    preference = entry.getKey();
                }
            }

            personalized = StringUtils.isNotBlank(preference) || !keywords.isEmpty();

            if(personalized) {
                int candidateLimit = Math.max(limit * 10, 60);
                List<NewsEntity> candidates = newsService.selectList(
                        new EntityWrapper<NewsEntity>()
                                .orderBy("addtime", false)
                                .last("limit " + candidateLimit)
                );
                Map<Long, Integer> scoreMap = new HashMap<>();
                for (int i = 0; i < candidates.size(); i++) {
                    NewsEntity item = candidates.get(i);
                    int score = scoreNews(item, preferenceCount, keywords);
                    if(score > 0) {
                        score += Math.max(0, candidateLimit - i);
                    }
                    scoreMap.put(item.getId(), score);
                }
                candidates.sort((a, b) -> {
                    int compare = Integer.compare(scoreMap.getOrDefault(b.getId(), 0), scoreMap.getOrDefault(a.getId(), 0));
                    if(compare != 0) {
                        return compare;
                    }
                    Date timeA = a.getAddtime();
                    Date timeB = b.getAddtime();
                    if(timeA != null && timeB != null) {
                        return timeB.compareTo(timeA);
                    }
                    if(timeB != null) {
                        return 1;
                    }
                    if(timeA != null) {
                        return -1;
                    }
                    Long idA = a.getId() == null ? 0L : a.getId();
                    Long idB = b.getId() == null ? 0L : b.getId();
                    return idB.compareTo(idA);
                });
                for (NewsEntity item : candidates) {
                    if(scoreMap.getOrDefault(item.getId(), 0) <= 0) {
                        continue;
                    }
                    result.add(item);
                    if(result.size() >= limit) {
                        break;
                    }
                }
            }
        }

        if(result.size() < limit) {
            List<NewsEntity> fallbackList = newsService.selectList(
                    new EntityWrapper<NewsEntity>()
                            .orderBy("addtime", false)
                            .last("limit " + (limit * 2))
            );
            Set<Long> existingIds = result.stream().map(NewsEntity::getId).collect(Collectors.toSet());
            for (NewsEntity item : fallbackList) {
                if(existingIds.contains(item.getId())) {
                    continue;
                }
                result.add(item);
                if(result.size() >= limit) {
                    break;
                }
            }
        }

        Map<String, Object> data = new HashMap<>();
        data.put("list", result);
        data.put("personalized", personalized);
        data.put("preference", preference);
        return R.ok().put("data", data);
    }

    private void collectPreference(Map<String, Integer> preferenceCount, Set<String> keywords, String category, String... texts) {
        if(StringUtils.isNotBlank(category)) {
            preferenceCount.put(category, preferenceCount.getOrDefault(category, 0) + 1);
            addKeywords(keywords, category.replace('_', ' '));
        }
        if(texts == null) {
            return;
        }
        for (String text : texts) {
            addKeywords(keywords, text);
        }
    }

    private void addKeywords(Set<String> keywords, String text) {
        if(StringUtils.isBlank(text)) {
            return;
        }
        String normalized = text
                .replaceAll("<[^>]+>", " ")
                .replaceAll("[\\r\\n\\t]+", " ")
                .replaceAll("[^A-Za-z0-9\\u4e00-\\u9fa5]+", " ")
                .toLowerCase(Locale.ROOT)
                .trim();
        if(StringUtils.isBlank(normalized)) {
            return;
        }
        for (String token : normalized.split("\\s+")) {
            if(StringUtils.isBlank(token) || token.length() < 2) {
                continue;
            }
            keywords.add(token);
        }
        String compact = normalized.replace(" ", "");
        if(compact.length() >= 2 && compact.length() <= 12) {
            keywords.add(compact);
        }
    }

    private int scoreNews(NewsEntity news, Map<String, Integer> preferenceCount, Set<String> keywords) {
        if(news == null) {
            return 0;
        }
        int score = 0;
        if(StringUtils.isNotBlank(news.getPinzhongfenlei())) {
            score += preferenceCount.getOrDefault(news.getPinzhongfenlei(), 0) * 100;
        }
        String searchText = buildSearchText(
                news.getTitle(),
                news.getIntroduction(),
                news.getContent(),
                news.getPinzhongfenlei(),
                news.getName()
        );
        for (String keyword : keywords) {
            if(StringUtils.isBlank(keyword) || keyword.length() < 2) {
                continue;
            }
            if(searchText.contains(keyword)) {
                score += keyword.length() >= 4 ? 12 : 6;
            }
        }
        return score;
    }

    private String buildSearchText(String... parts) {
        StringBuilder builder = new StringBuilder();
        if(parts == null) {
            return "";
        }
        for (String part : parts) {
            if(StringUtils.isBlank(part)) {
                continue;
            }
            builder.append(' ')
                    .append(part.replaceAll("<[^>]+>", " ").toLowerCase(Locale.ROOT));
        }
        return builder.toString();
    }




	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( NewsEntity news){
       	EntityWrapper<NewsEntity> ew = new EntityWrapper<NewsEntity>();
      	ew.allEq(MPUtil.allEQMapPre( news, "news"));
        return R.ok().put("data", newsService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(NewsEntity news){
        EntityWrapper< NewsEntity> ew = new EntityWrapper< NewsEntity>();
 		ew.allEq(MPUtil.allEQMapPre( news, "news"));
		NewsView newsView =  newsService.selectView(ew);
		return R.ok("查询观赏鱼资讯成功").put("data", newsView);
    }

    /**
     * 后台详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        NewsEntity news = newsService.selectById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(news,deSens);
        return R.ok().put("data", news);
    }

    /**
     * 前台详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        NewsEntity news = newsService.selectById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(news,deSens);
        return R.ok().put("data", news);
    }




    /**
     * 后台保存
     */
    @RequestMapping("/save")
    @SysLog("新增观赏鱼资讯")
    public R save(@RequestBody NewsEntity news, HttpServletRequest request){
        //ValidatorUtils.validateEntity(news);
        newsService.insert(news);
        return R.ok().put("data",news.getId());
    }

    /**
     * 前台保存
     */
    @SysLog("新增观赏鱼资讯")
    @RequestMapping("/add")
    public R add(@RequestBody NewsEntity news, HttpServletRequest request){
        //ValidatorUtils.validateEntity(news);
        newsService.insert(news);
        return R.ok().put("data",news.getId());
    }





    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    @SysLog("修改观赏鱼资讯")
    public R update(@RequestBody NewsEntity news, HttpServletRequest request){
        //ValidatorUtils.validateEntity(news);
        //全部更新
        newsService.updateById(news);
        return R.ok();
    }





    /**
     * 删除
     */
    @RequestMapping("/delete")
    @SysLog("删除观赏鱼资讯")
    public R delete(@RequestBody Long[] ids){
        newsService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }










}
