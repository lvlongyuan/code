package com.yuangege.springboot.controller;

import com.yuangege.springboot.entity.Market;
import com.yuangege.springboot.mapper.MarketMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/market")
public class MarketController {

    @Resource
    public MarketMapper marketMapper;

    @GetMapping
    public List<Market> findAll(){
        return marketMapper.findAll();
    }


    @GetMapping("/page")
    public Map<String, Object> findPage(@RequestParam Integer pageNum,
                                        @RequestParam Integer pageSize,
                                        @RequestParam String name
    ){
        pageNum = (pageNum - 1) * pageSize;//页码规则，select语句limit 0,2第1个数据开始前两（2）个数据
        name = "%" + name + "%";
        List<Market> data = marketMapper.seletPage(pageNum, pageSize, name);
        Integer total = marketMapper.selectTotal(name);
        Map<String, Object> res = new HashMap<>();
        res.put("data", data);
        res.put("total", total);
        return res;
    }
}
