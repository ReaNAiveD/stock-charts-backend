package com.naived.stockchartsbackend.controller;

import com.naived.stockchartsbackend.VO.ResultVO;
import com.naived.stockchartsbackend.service.IndustryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/6/30
 * @description: 包括板块每日指标
 */
@Controller
@ResponseBody
@RequestMapping("/industry")
@CrossOrigin(origins = "*")
public class IndustryController {

    public static final List<String> industryList = Arrays.asList("房地产", "化工行业", "银行", "贵金属");

    @Autowired
    IndustryService industryService;

    @RequestMapping("/daily")
    public ResultVO getIndustryDaily(@RequestParam("industry") String industry){
        if(industryList.contains(industry)){
            return ResultVO.SUCCESS(industryService.getIndustryDaily(industry));
        }
        else {
            return ResultVO.FAILED(1, "没有这个行业");
        }
    }

    
}
