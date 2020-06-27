package com.naived.stockchartsbackend.controller;

import com.naived.stockchartsbackend.VO.ResultVO;
import com.naived.stockchartsbackend.service.DailyStatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/daily")
@CrossOrigin(origins = "*")
public class DailyStatisticController {

    @Autowired
    private DailyStatisticService dailyStatisticService;

    @GetMapping("/statistic")
    public ResultVO getStatistic(@RequestParam("tscode")String tsCode, @RequestParam("pagesize")int pageSize,
                                 @RequestParam("offset")int offset){
        System.out.println(tsCode);
        return ResultVO.SUCCESS(dailyStatisticService.getDaily(tsCode, pageSize, offset * pageSize));
    }

    @GetMapping("/count")
    public ResultVO getCount(@RequestParam("tscode")String tsCode){
        System.out.println(tsCode);
        return ResultVO.SUCCESS(dailyStatisticService.getDailyCount(tsCode));
    }

}
