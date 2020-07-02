package com.naived.stockchartsbackend.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/6/30
 * @description: 板块每日指标
 */

@Data
@Document("industry_daily")
public class IndustryDaily {

    @Id
    private String id;
    //板块名称，有房地产、化工行业、银行、贵金属四种
    private String industry_type;
    //交易日
    private String trade_date;
    private Double pe;
    private Double pe_ttm;
    private Double pb;
    private Double ps;
    private Double ps_ttm;
    private Double dv_ratio;
    private Double dv_ttm;

}
