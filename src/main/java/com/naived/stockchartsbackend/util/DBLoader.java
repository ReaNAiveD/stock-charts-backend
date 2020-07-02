package com.naived.stockchartsbackend.util;

import com.naived.stockchartsbackend.domain.IndustryDaily;
import com.naived.stockchartsbackend.service.impl.IndustryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/6/30
 * @description:
 */
@Component
public class DBLoader implements CommandLineRunner {

    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    IndustryServiceImpl industryService;

    @Override
    public void run(String... args) throws Exception {
//        new Thread(this::computeIndustryDaily).start();
    }

    /**
     * 计算板块每日指标
     */
    private void computeIndustryDaily(){
        System.out.println("开始计算板块每日指标...");
        //删表
        mongoTemplate.dropCollection(IndustryDaily.class);
        //加载数据
        List<String> tradeDateList = industryService.getTradeDate();
        for(String industry: IndustryServiceImpl.industryArray){
            for(String tradeDate: tradeDateList){
                IndustryDaily industryDaily = industryService.computeIndustryDaily(tradeDate, industry);
                if(industryDaily!=null){
                    mongoTemplate.save(industryDaily);
                    System.out.println(industryDaily);
                }
            }
        }
        System.out.println("板块每日指标计算完成...");
    }
}
