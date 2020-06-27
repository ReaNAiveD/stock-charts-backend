package com.naived.stockchartsbackend.service.impl;

import com.naived.stockchartsbackend.service.DailyStatisticService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DailyStatisticServiceImplTest {

    @Autowired
    DailyStatisticService dailyStatisticService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void getDaily() {
        System.out.println(dailyStatisticService.getDaily("000002.SZ", 40, 0));
    }
}