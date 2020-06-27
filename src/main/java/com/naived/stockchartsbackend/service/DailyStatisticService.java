package com.naived.stockchartsbackend.service;

import com.naived.stockchartsbackend.domain.DailyBasic;

import java.util.List;

public interface DailyStatisticService {

    public List<DailyBasic> getDaily(String tsCode, int pageSize, int offset);

    public int getDailyCount(String tsCode);

}
