package com.controller;

import com.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @RequestMapping("/overview")
    public R overview() {
        Map<String, Object> data = new LinkedHashMap<>();
        Map<String, Object> overview = new LinkedHashMap<>();
        Map<String, Object> charts = new LinkedHashMap<>();

        overview.put("userCount", queryCount("select count(*) from yonghu"));
        overview.put("newsCount", queryCount("select count(*) from news"));
        overview.put("teachingCount", queryCount("select count(*) from siyangjiaoxue"));
        overview.put("todayReminderCount", queryCount("select count(*) from weiyutixing where date(tixingriqi) = curdate()"));
        overview.put("shopProductCount", queryCount("select count(*) from xianshangshangcheng"));

        charts.put("newsCategories", queryLabelValues(
                "select coalesce(nullif(pinzhongfenlei, ''), 'general') as label, count(*) as total " +
                        "from news group by coalesce(nullif(pinzhongfenlei, ''), 'general') order by total desc"
        ));
        charts.put("favoritePreferences", queryLabelValues(
                "select label, count(*) as total from (" +
                        "select coalesce(nullif(s.pinzhongfenlei, ''), 'general') as label " +
                        "from storeup st join siyangjiaoxue s on st.refid = s.id " +
                        "where st.type = '1' and st.tablename = 'siyangjiaoxue' " +
                        "union all " +
                        "select coalesce(nullif(n.pinzhongfenlei, ''), 'general') as label " +
                        "from storeup st join news n on st.refid = n.id " +
                        "where st.type = '1' and st.tablename = 'news' " +
                        "union all " +
                        "select coalesce(nullif(g.pinzhongfenlei, ''), 'general') as label " +
                        "from storeup st join guanshangyubaike g on st.refid = g.id " +
                        "where st.type = '1' and st.tablename = 'guanshangyubaike' " +
                        ") preference group by label order by total desc"
        ));
        charts.put("teachingVideos", queryLabelValues(
                "select coalesce(nullif(pinzhongfenlei, ''), 'general') as label, count(*) as total " +
                        "from siyangjiaoxue " +
                        "group by coalesce(nullif(pinzhongfenlei, ''), 'general') order by total desc"
        ));

        data.put("overview", overview);
        data.put("charts", charts);
        data.put("updatedAt", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        return R.ok().put("data", data);
    }

    private long queryCount(String sql) {
        Number number = jdbcTemplate.queryForObject(sql, Number.class);
        return number == null ? 0L : number.longValue();
    }

    private List<Map<String, Object>> queryLabelValues(String sql) {
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        List<Map<String, Object>> list = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            Map<String, Object> item = new LinkedHashMap<>();
            item.put("name", row.get("label") == null ? "general" : String.valueOf(row.get("label")));
            Number total = (Number) row.get("total");
            item.put("value", total == null ? 0L : total.longValue());
            list.add(item);
        }
        return list;
    }
}
