package com.cn.jmw;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by xujingfeng on 2017/8/3.
 */
@RestController
public class ConsumerController {

    @Autowired
    CalculateApi calculateApi;

    @RequestMapping("/test")
    public String test() {
        Integer result = calculateApi.add(1, 2);
        System.out.println("the result is " + result);
        return "success";
    }

}