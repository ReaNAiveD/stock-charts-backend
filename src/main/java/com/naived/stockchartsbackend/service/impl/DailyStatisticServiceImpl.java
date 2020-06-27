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

import java.util.List;


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
}
