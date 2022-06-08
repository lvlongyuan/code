package com.yuangege.springboot.entity;

import lombok.Data;

@Data
public class Market {
    private String name;
    private String lowPrice;
    private String highPrice;
    private String avgPrice;
    private String unitInfo;
    private String datetime;
}
