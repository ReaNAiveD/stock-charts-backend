package com.naived.stockchartsbackend.service;

import com.naived.stockchartsbackend.domain.IndustryDaily;

import java.util.List;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/6/30
 * @description:
 */
public interface IndustryService {


    public List<IndustryDaily> getIndustryDaily(String industry);
}
