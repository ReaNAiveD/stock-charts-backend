package com.naived.stockchartsbackend.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DailyStatisticControllerTest {

    @Autowired
    DailyStatisticController dailyStatisticController;

    @Test
    void getStatistic() {
        System.out.println(dailyStatisticController.getStatistic("600007.SH", 40, 0).getData());
    }
}