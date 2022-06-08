package com.yuangege.springboot.controller;

import com.yuangege.springboot.entity.Warn;
import com.yuangege.springboot.mapper.WarnMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/warn")
public class WarnController {

    @Resource
    private WarnMapper warnMapper;

    @GetMapping
    List<Warn> findall(){
        return warnMapper.findAll();
    }

    @GetMapping("/page")
    public Map<String, Object> findPage(@RequestParam Integer pageNum,
                                        @RequestParam Integer pageSize,
                                        @RequestParam String warnName
                                        ){
        pageNum = (pageNum - 1) * pageSize;//页码规则，select语句limit 0,2第1个数据开始前两（2）个数据
//        List<Warn> list = warnMapper.findAll();
        warnName = "%" + warnName + "%";
        List<Warn> data = warnMapper.seletPage(pageNum, pageSize, warnName);
        Integer total = warnMapper.selectTotal(warnName);
        Map<String, Object> res = new HashMap<>();
//        res.put("list", list);
        res.put("data", data);
        res.put("total", total);
        return res;
    }

}
