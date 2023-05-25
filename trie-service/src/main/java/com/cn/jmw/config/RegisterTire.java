package com.cn.jmw.config;

import com.cn.jmw.trie.Trie;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author jmw
 * @Description RegisterTire 注册前缀树，空节点，无数据
 * @date 2023年04月21日 14:35
 * @Version 1.0
 */
@Configuration
public class RegisterTire {

    /**
     * 注册前缀树，空节点，无数据
     * @return Trie<Object,Object>
     */
    @Bean
    @ConditionalOnProperty(name = "spring.trie.create-trie", havingValue = "true",matchIfMissing = true)
    public Trie<Object,Object> tire() {
        return new Trie<Object,Object>();
    }

}
