package com.naived.stockchartsbackend.domain;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("daily_basic")
public class DailyBasic {
    private String id;
    private String symbol;
    private String name;
    private String industry_type;
    private String ts_code;
    private String trade_date;
    private Double close;
    private Double turnover_rate;
    private Double turnover_rate_f;
    private Double volume_ratio;
    private Double pe;
    private Double pe_ttm;
    private Double pb;
    private Double ps;
    private Double ps_ttm;
    private Double dv_ratio;
    private Double dv_ttm;
    private Double total_share;
    private Double float_share;
    private Double free_share;
    private Double total_mv;
    private Double circ_mv;
}
