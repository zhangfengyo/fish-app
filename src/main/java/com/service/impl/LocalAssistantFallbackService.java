package com.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.entity.GuanshangyubaikeEntity;
import com.entity.NewsEntity;
import com.entity.SiyangjiaoxueEntity;
import com.entity.WeiyutixingEntity;
import com.llm.MultiModelAIClient;
import com.service.GuanshangyubaikeService;
import com.service.NewsService;
import com.service.SiyangjiaoxueService;
import com.service.WeiyutixingService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

@Service
public class LocalAssistantFallbackService {

    private static final int MAX_REFERENCE_COUNT = 3;
    private static final int MAX_SOURCE_ROWS = 80;

    @Autowired
    private GuanshangyubaikeService guanshangyubaikeService;

    @Autowired
    private NewsService newsService;

    @Autowired
    private SiyangjiaoxueService siyangjiaoxueService;

    @Autowired
    private WeiyutixingService weiyutixingService;

    public String generateReply(MultiModelAIClient.ChatRequest request, Long userId, String username) {
        String ask = request == null ? null : request.getMessage();
        if (StringUtils.isBlank(ask)) {
            return "请先输入问题，我再继续回答。";
        }

        String normalizedAsk = ask.trim();
        if (isReminderQuestion(normalizedAsk) && StringUtils.isNotBlank(username)) {
            return buildReminderReply(username);
        }
        if (isSystemUsageQuestion(normalizedAsk)) {
            return buildSystemUsageReply(normalizedAsk);
        }

        List<ScoredSnippet> snippets = collectKnowledgeSnippets(normalizedAsk);
        if (!snippets.isEmpty()) {
            return buildKnowledgeReply(normalizedAsk, snippets);
        }

        return buildGenericReply(normalizedAsk);
    }

    private String buildReminderReply(String username) {
        List<WeiyutixingEntity> allReminders = weiyutixingService.selectList(
                new EntityWrapper<WeiyutixingEntity>().eq("yonghuzhanghao", username).orderBy("tixingriqi", true)
        );
        if (allReminders == null || allReminders.isEmpty()) {
            return "结论：你当前还没有喂鱼提醒。\n\n你可以进入“提醒”页面新增提醒，填写标题、日期和提醒内容后，系统就会按账号保存。";
        }

        Date today = startOfDay(new Date());
        List<WeiyutixingEntity> upcoming = new ArrayList<WeiyutixingEntity>();
        for (WeiyutixingEntity item : allReminders) {
            if (item.getTixingriqi() == null || !item.getTixingriqi().before(today)) {
                upcoming.add(item);
            }
        }
        List<WeiyutixingEntity> displayList = upcoming.isEmpty() ? allReminders : upcoming;

        StringBuilder builder = new StringBuilder();
        builder.append("结论：账号 ").append(username).append(" 当前有 ")
                .append(allReminders.size()).append(" 条喂鱼提醒，下面是最需要先看的几条。");

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        int limit = Math.min(5, displayList.size());
        for (int i = 0; i < limit; i++) {
            WeiyutixingEntity item = displayList.get(i);
            builder.append("\n")
                    .append(i + 1)
                    .append(". ")
                    .append(item.getTixingriqi() == null ? "未设置日期" : formatter.format(item.getTixingriqi()))
                    .append(" - ")
                    .append(firstNonBlank(item.getTixingbiaoti(), "未命名提醒"));
            if (StringUtils.isNotBlank(item.getTixingneirong())) {
                builder.append("：").append(trimSnippet(item.getTixingneirong(), 54));
            }
        }
        builder.append("\n\n如果你要我继续整理“今天要做什么”或“哪条最紧急”，可以直接继续问。");
        return builder.toString();
    }

    private String buildSystemUsageReply(String ask) {
        StringBuilder builder = new StringBuilder();
        builder.append("结论：这个系统现在可以直接用当前项目里的本地数据回答你，常用入口如下。");
        if (containsAny(ask, "提醒", "喂鱼")) {
            builder.append("\n1. “提醒”页面用于新增、查看和修改喂鱼提醒。")
                    .append("\n2. 登录后系统会按当前账号读取提醒，不需要切回旧目录。")
                    .append("\n3. 如果你想看当天待办，可以直接问“今天有哪些提醒”。");
            return builder.toString();
        }
        if (containsAny(ask, "商城", "购物车", "订单", "购买")) {
            builder.append("\n1. “商城”页浏览商品。")
                    .append("\n2. “购物车”页统一调整数量并结算。")
                    .append("\n3. “订单”页查看下单结果和状态。");
            return builder.toString();
        }
        if (containsAny(ask, "百科", "鱼种")) {
            builder.append("\n1. “百科”页查鱼种资料、习性和饲养简介。")
                    .append("\n2. “教学”页看实际操作建议。")
                    .append("\n3. 如果你告诉我具体鱼名，我可以直接从当前资料里帮你归纳。");
            return builder.toString();
        }
        if (containsAny(ask, "后台", "管理", "管理员")) {
            builder.append("\n1. 后台可以维护资讯、教学、百科、商城和助手配置。")
                    .append("\n2. 助手设置页只负责外部模型配置和对话记录查看。")
                    .append("\n3. 即使外部模型失效，前台普通聊天现在也会自动切到本地资料模式。");
            return builder.toString();
        }
        builder.append("\n1. 首页看推荐内容和快捷入口。")
                .append("\n2. “资讯”看文章，“教学”看饲养方法，“百科”查鱼种资料。")
                .append("\n3. “提醒”管理喂鱼日程，“论坛”发帖交流，“个人中心”改资料。")
                .append("\n4. 如果你告诉我想完成哪一步，我可以按页面顺序继续说明。");
        return builder.toString();
    }

    private String buildKnowledgeReply(String ask, List<ScoredSnippet> snippets) {
        StringBuilder builder = new StringBuilder();
        builder.append("结论：我已经从当前系统内置资料里找到和“")
                .append(ask)
                .append("”最接近的内容，优先给你可直接参考的结论。");

        ScoredSnippet top = snippets.get(0);
        if (StringUtils.isNotBlank(top.summary)) {
            builder.append("\n\n核心参考：")
                    .append(top.source)
                    .append("《")
                    .append(top.title)
                    .append("》")
                    .append("：")
                    .append(trimSnippet(top.summary, 92));
        }

        builder.append("\n\n相关内容：");
        int limit = Math.min(MAX_REFERENCE_COUNT, snippets.size());
        for (int i = 0; i < limit; i++) {
            ScoredSnippet snippet = snippets.get(i);
            builder.append("\n")
                    .append(i + 1)
                    .append(". ")
                    .append(snippet.source)
                    .append("：")
                    .append(snippet.title);
            if (StringUtils.isNotBlank(snippet.summary)) {
                builder.append("\n   ").append(trimSnippet(snippet.summary, 84));
            }
        }

        builder.append("\n\n建议：");
        if (containsAny(ask, "怎么养", "饲养", "新手", "准备", "喂")) {
            builder.append("\n1. 先用“百科”确认品种和基础习性。")
                    .append("\n2. 再看对应“教学”条目，按步骤执行。")
                    .append("\n3. 如果你要精确参数，比如具体水温、换水频率或混养数量，请继续追问具体鱼种。");
        } else if (containsAny(ask, "区别", "差别", "哪个好")) {
            builder.append("\n1. 先对比鱼种分类、性情和饲养难度。")
                    .append("\n2. 再结合你的缸体大小和混养目标做决定。")
                    .append("\n3. 如果你告诉我两种具体鱼名，我可以继续按当前资料帮你逐项对比。");
        } else {
            builder.append("\n1. 当前回答基于项目里已经迁移到本地的数据。")
                    .append("\n2. 如果你想让我只讲百科、只讲教学，或者只看提醒，也可以直接指定。")
                    .append("\n3. 没有明确数字的地方我不会乱编，你可以继续追问更具体的问题。");
        }
        return builder.toString();
    }

    private String buildGenericReply(String ask) {
        return "结论：我暂时没有在当前项目的本地资料里找到和“" + ask + "”完全对应的现成条目。"
                + "\n\n你可以换一种更具体的问法，例如："
                + "\n1. 某个鱼种名 + 怎么养"
                + "\n2. 某个页面名 + 怎么用"
                + "\n3. 今天提醒 / 商城下单 / 论坛发帖"
                + "\n\n当前可直接检索的本地资料主要来自：观赏鱼百科、资讯、教学和喂鱼提醒。";
    }

    private List<ScoredSnippet> collectKnowledgeSnippets(String ask) {
        List<String> keywords = extractKeywords(ask);
        List<ScoredSnippet> matches = new ArrayList<ScoredSnippet>();

        for (GuanshangyubaikeEntity item : limitList(guanshangyubaikeService.selectList(new EntityWrapper<GuanshangyubaikeEntity>().orderBy("id", false)))) {
            String title = firstNonBlank(item.getZhongwenming(), item.getYingwenming(), "未命名百科条目");
            String summary = firstNonBlank(item.getSiyangfangfa(), item.getYaoqiu(), item.getShengwujianjie(), item.getShiwuyaoqiu());
            int score = scoreMatch(title + " " + firstNonBlank(item.getPinzhongfenlei(), ""), summary, keywords);
            if (score > 0) {
                matches.add(new ScoredSnippet("观赏鱼百科", title, summary, score));
            }
        }

        for (SiyangjiaoxueEntity item : limitList(siyangjiaoxueService.selectList(new EntityWrapper<SiyangjiaoxueEntity>().orderBy("id", false)))) {
            String title = firstNonBlank(item.getJiaoxuebiaoti(), "未命名教学条目");
            String summary = firstNonBlank(item.getSiyangfangfa(), item.getJiaoxuexiangqing(), item.getYubingzhiliao());
            int score = scoreMatch(title + " " + firstNonBlank(item.getPinzhongfenlei(), ""), summary, keywords);
            if (score > 0) {
                matches.add(new ScoredSnippet("饲养教学", title, summary, score));
            }
        }

        for (NewsEntity item : limitList(newsService.selectList(new EntityWrapper<NewsEntity>().orderBy("id", false)))) {
            String title = firstNonBlank(item.getTitle(), "未命名资讯");
            String summary = firstNonBlank(item.getIntroduction(), stripHtml(item.getContent()));
            int score = scoreMatch(title + " " + firstNonBlank(item.getPinzhongfenlei(), ""), summary, keywords);
            if (score > 0) {
                matches.add(new ScoredSnippet("资讯", title, summary, score));
            }
        }

        matches.sort(Comparator.comparingInt(ScoredSnippet::getScore).reversed().thenComparing(ScoredSnippet::getTitle));
        if (matches.size() <= MAX_REFERENCE_COUNT) {
            return matches;
        }
        return new ArrayList<ScoredSnippet>(matches.subList(0, MAX_REFERENCE_COUNT));
    }

    private List<String> extractKeywords(String ask) {
        Set<String> keywords = new LinkedHashSet<String>();
        String compact = normalizeForMatch(ask);
        if (StringUtils.isNotBlank(compact)) {
            keywords.add(compact);
            int maxGram = Math.min(4, compact.length());
            for (int size = maxGram; size >= 2; size--) {
                for (int i = 0; i + size <= compact.length(); i++) {
                    String token = compact.substring(i, i + size);
                    if (isMeaningfulKeyword(token)) {
                        keywords.add(token);
                    }
                    if (keywords.size() >= 12) {
                        return new ArrayList<String>(keywords);
                    }
                }
            }
        }

        String[] splitTokens = ask.split("[\\s,，。！？?、；;：:/()（）【】《》]+");
        for (String token : splitTokens) {
            String normalized = normalizeForMatch(token);
            if (isMeaningfulKeyword(normalized)) {
                keywords.add(normalized);
            }
            if (keywords.size() >= 12) {
                break;
            }
        }
        if (keywords.isEmpty()) {
            keywords.add(normalizeForMatch(ask));
        }
        return new ArrayList<String>(keywords);
    }

    private int scoreMatch(String title, String body, List<String> keywords) {
        String normalizedTitle = normalizeForMatch(title);
        String normalizedBody = normalizeForMatch(body);
        int score = 0;
        for (String keyword : keywords) {
            String normalizedKeyword = normalizeForMatch(keyword);
            if (StringUtils.isBlank(normalizedKeyword)) {
                continue;
            }
            if (normalizedTitle.contains(normalizedKeyword)) {
                score += 12 + Math.min(4, normalizedKeyword.length());
            }
            if (normalizedBody.contains(normalizedKeyword)) {
                score += 4 + Math.min(3, normalizedKeyword.length());
            }
        }
        return score;
    }

    private boolean isReminderQuestion(String ask) {
        return containsAny(ask, "提醒", "喂鱼", "待办", "今天", "日程");
    }

    private boolean isSystemUsageQuestion(String ask) {
        return containsAny(ask, "系统", "页面", "功能", "怎么用", "如何用", "哪里看", "在哪", "后台", "登录", "注册", "商城", "购物车", "订单", "论坛");
    }

    private boolean containsAny(String text, String... keywords) {
        if (StringUtils.isBlank(text) || keywords == null) {
            return false;
        }
        String lower = text.toLowerCase(Locale.ROOT);
        for (String keyword : keywords) {
            if (StringUtils.isNotBlank(keyword) && lower.contains(keyword.toLowerCase(Locale.ROOT))) {
                return true;
            }
        }
        return false;
    }

    private boolean isMeaningfulKeyword(String token) {
        return StringUtils.isNotBlank(token) && token.length() >= 2;
    }

    private String normalizeForMatch(String text) {
        if (text == null) {
            return "";
        }
        return stripHtml(text)
                .toLowerCase(Locale.ROOT)
                .replaceAll("[\\s\\p{Punct}！？。，“”‘’、：；（）()【】《》]", "");
    }

    private String stripHtml(String text) {
        if (text == null) {
            return "";
        }
        return text.replaceAll("<[^>]+>", " ").replace("&nbsp;", " ").replaceAll("\\s+", " ").trim();
    }

    private String trimSnippet(String text, int maxLength) {
        String normalized = stripHtml(text);
        if (normalized.length() <= maxLength) {
            return normalized;
        }
        return normalized.substring(0, maxLength) + "...";
    }

    private <T> List<T> limitList(List<T> source) {
        if (source == null || source.isEmpty()) {
            return Collections.emptyList();
        }
        if (source.size() <= MAX_SOURCE_ROWS) {
            return source;
        }
        return source.subList(0, MAX_SOURCE_ROWS);
    }

    private String firstNonBlank(String... values) {
        if (values == null) {
            return null;
        }
        for (String value : values) {
            if (StringUtils.isNotBlank(value)) {
                return value.trim();
            }
        }
        return null;
    }

    private Date startOfDay(Date value) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(value);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    private static final class ScoredSnippet {
        private final String source;
        private final String title;
        private final String summary;
        private final int score;

        private ScoredSnippet(String source, String title, String summary, int score) {
            this.source = source;
            this.title = title;
            this.summary = summary;
            this.score = score;
        }

        private String getTitle() {
            return title;
        }

        private int getScore() {
            return score;
        }
    }
}
