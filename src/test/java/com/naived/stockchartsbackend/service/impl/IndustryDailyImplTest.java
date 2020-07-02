package com.naived.stockchartsbackend.service.impl;

import com.naived.stockchartsbackend.domain.IndustryDaily;
import com.naived.stockchartsbackend.service.IndustryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/7/1
 * @description:
 */
@SpringBootTest
public class IndustryDailyImplTest {

    @Autowired
    IndustryService industryService;

    @Autowired
    IndustryServiceImpl industryServiceImpl;

    /**
     * 测试getTradeDate方法
     */
    @Test
    public void test1(){
        List<String> tradeDate = industryServiceImpl.getTradeDate();
        System.out.println(tradeDate);
        System.out.println(tradeDate.get(0));
        System.out.println(tradeDate.get(tradeDate.size()-1));
    }

    /**
     * 测试computeIndustryDaily方法
     */
    @Test
    public void test2(){
        IndustryDaily industryDaily = industryServiceImpl.computeIndustryDaily("20200630", "银行");
        System.out.println(industryDaily);
    }

    /**
     * 测试getIndustryDaily方法
     */
    @Test
    public void test3(){
        List<IndustryDaily> industryDailyList = industryService.getIndustryDaily("银行");
        System.out.println(industryDailyList.get(0));
        for(int i=0;i<10;i++){
            System.out.println(industryDailyList.get(industryDailyList.size()-1-i));
        }
    }
}
