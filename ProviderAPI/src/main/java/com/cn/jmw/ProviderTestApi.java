package com.cn.jmw;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "Trie")
public interface ProviderTestApi {
    //test
    @RequestMapping(value = "/helloNacos", method = RequestMethod.GET)
    public String dataListLabel();
}
