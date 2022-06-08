package com.yuangege.springboot.controller;

import com.yuangege.springboot.entity.PriceIndex;
import com.yuangege.springboot.mapper.PriceIndexMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/priceindex")
public class PriceIndexController {

    @Resource
    public PriceIndexMapper priceIndexMapper;

    @GetMapping
    public List<PriceIndex> findAll(){
        return priceIndexMapper.findAll();
    }

    @GetMapping("/page")
    public Map<String, Object> findPage(@RequestParam Integer pageNum,
                                        @RequestParam Integer pageSize,
                                        @RequestParam String datename,
                                        @RequestParam String value
    ){
        pageNum = (pageNum - 1) * pageSize;//页码规则，select语句limit 0,2第1个数据开始前两（2）个数据
        datename = "%" + datename + "%";
        List<PriceIndex> data = priceIndexMapper.seletPage(pageNum, pageSize, datename);
        Integer total = priceIndexMapper.selectTotal(datename);
        Map<String, Object> res = new HashMap<>();
        res.put("data", data);
        res.put("total", total);
        return res;
    }


}
