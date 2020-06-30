package com.naived.stockchartsbackend.service.impl;

import com.naived.stockchartsbackend.domain.DailyBasic;
import com.naived.stockchartsbackend.service.DailyStatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class DailyStatisticServiceImpl implements DailyStatisticService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<DailyBasic> getDaily(String tsCode, int pageSize, int offset) {
        Sort sort = Sort.by(Sort.Order.desc("trade_date"));
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("ts_code").is(tsCode)),
                Aggregation.sort(sort),
                Aggregation.skip((long)offset),
                Aggregation.limit(pageSize)
        );
//        Query query = new Query();
//        query.addCriteria(Criteria.where("ts_code").is(tsCode));
//        return mongoTemplate.find(query.with(Sort.by(Sort.Order.desc("trade_date"))).skip(offset).limit(pageSize), DailyBasic.class);
        return mongoTemplate.aggregate(aggregation, "daily_basic", DailyBasic.class).getMappedResults();
    }

    @Override
    public int getDailyCount(String tsCode) {
        Query query = new Query();
        query.addCriteria(Criteria.where("ts_code").is(tsCode));
        return (int)mongoTemplate.count(query, DailyBasic.class);
    }

    @Override
    public Map<Integer, DailyBasic> getAverage(String tsCode) {
        Map<Integer, DailyBasic> result = new HashMap<>();
        Sort sort = Sort.by(Sort.Order.desc("trade_date"));
        int[] dayCount = {30, 150, 300, 900, 3000};
        for (int limit : dayCount) {
            Aggregation aggregation = Aggregation.newAggregation(
                    Aggregation.match(Criteria.where("ts_code").is(tsCode)),
                    Aggregation.sort(sort),
                    Aggregation.limit(limit),
                    Aggregation.group("ts_code").first("symbol").as("symbol").first("name").as("name")
                            .first("industry_type").as("industry_type").first("ts_code").as("ts_code")
                            .avg("close").as("close")
                            .avg("turnover_rate").as("turnover_rate").avg("turnover_rate_f").as("turnover_rate_f")
                            .avg("volume_ratio").as("volume_ratio").avg("pe").as("pe")
                            .avg("pe_ttm").as("pe_ttm").avg("pb").as("pb").avg("ps").as("ps")
                            .avg("ps_ttm").as("ps_ttm").avg("dv_ratio").as("dv_ratio")
                            .avg("dv_ttm").as("dv_ttm").avg("total_share").as("total_share")
                            .avg("float_share").as("float_share").avg("free_share").as("free_share")
                            .avg("total_mv").as("total_mv").avg("circ_mv").as("circ_mv")
            );
            result.put(limit, mongoTemplate.aggregate(aggregation, "daily_basic", DailyBasic.class).getMappedResults().get(0));
        }
        return result;
    }

    @Override
    public DailyBasic getLatest(String tsCode) {
        Sort sort = Sort.by(Sort.Order.desc("trade_date"));
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("ts_code").is(tsCode)),
                Aggregation.sort(sort),
                Aggregation.limit(1)
        );
        return mongoTemplate.aggregate(aggregation, "daily_basic", DailyBasic.class).getMappedResults().get(0);
    }
}
