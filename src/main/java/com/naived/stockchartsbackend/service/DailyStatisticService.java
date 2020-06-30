package com.naived.stockchartsbackend.service;

import com.naived.stockchartsbackend.domain.DailyBasic;

import java.util.List;
import java.util.Map;

public interface DailyStatisticService {

    public List<DailyBasic> getDaily(String tsCode, int pageSize, int offset);

    public int getDailyCount(String tsCode);

    public DailyBasic getLatest(String tsCode);

    public Map<Integer, DailyBasic> getAverage(String tsCode);

}
