package com.naived.stockchartsbackend.service.impl;

import com.naived.stockchartsbackend.domain.IndustryDaily;
import com.naived.stockchartsbackend.service.IndustryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/6/30
 * @description:
 */
@Service
public class IndustryServiceImpl implements IndustryService {

    public static final String[] industryArray = {"房地产", "化工行业", "银行", "贵金属"};

    @Autowired
    MongoTemplate mongoTemplate;

    /**
     * 获得从2000年开始的板块的每日指标
     * 注意：这个方法中2020年7.1以前的每日指标是从数据库读取的，7.1以后的指标是动态计算得到的。
     *
     * @param industry
     * @return
     */
    @Override
    public List<IndustryDaily> getIndustryDaily(String industry) {
        //获得数据库里的记录
        Sort sort = Sort.by(Sort.Order.asc("trade_date"));
        Query query = new Query(Criteria.where("industry_type").is(industry));
        query.with(sort);
        List<IndustryDaily> industryDailyList = mongoTemplate.find(query, IndustryDaily.class);
        //获得交易日列表
        List<String> tradeDateList = getTradeDate();
        //计算没有保存在数据库里的交易日的数据
        int pivot = tradeDateList.indexOf(industryDailyList.get(industryDailyList.size()-1).getTrade_date());
        tradeDateList = tradeDateList.subList(pivot + 1, tradeDateList.size());
        for (String tradeDate : tradeDateList) {
            industryDailyList.add(computeIndustryDaily(tradeDate, industry));
        }
        return industryDailyList;
    }

    /**
     * 返回某日的板块指标
     *
     * @param date     日期
     * @param industry 板块名，有房地产等四种
     * @return
     */
    public IndustryDaily computeIndustryDaily(String date, String industry) {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("trade_date").is(date).and("industry_type").is(industry)
                        .and("pe").ne(Double.NaN)
                        .and("pe_ttm").ne(Double.NaN)
                        .and("pb").ne(Double.NaN)
                        .and("ps").ne(Double.NaN)
                        .and("ps_ttm").ne(Double.NaN)),
                Aggregation.group("industry_type")
                        .first("industry_type").as("industry_type")
                        .first("trade_date").as("trade_date")
                        .avg("pe").as("pe")
                        .avg("pe_ttm").as("pe_ttm")
                        .avg("pb").as("pb")
                        .avg("ps").as("ps")
                        .avg("ps_ttm").as("ps_ttm")
                        .avg("dv_ratio").as("dv_ratio")
                        .avg("dv_ttm").as("dv_ttm")
        );
        IndustryDaily industryDaily = mongoTemplate.aggregate(aggregation, "daily_basic", IndustryDaily.class).
                getMappedResults().get(0);
        industryDaily.setId(null);
        return industryDaily;

    }

    /**
     * 获得daily_basic表中所有的交易日
     *
     * @return
     */
    public List<String> getTradeDate() {
        Sort sort = Sort.by(Sort.Order.asc("trade_date"));
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.sort(sort),
                Aggregation.group("trade_date").first("trade_date").as("trade_date")
        );
        List<HashMap> map = mongoTemplate.aggregate(aggregation, "daily_basic", HashMap.class).getMappedResults();
        List<String> tradeDateList = new ArrayList<>();
        map.forEach(item -> {
            tradeDateList.add(item.get("trade_date").toString());
        });
        return tradeDateList;
    }

}
