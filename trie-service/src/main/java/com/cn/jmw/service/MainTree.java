package com.cn.jmw.service;

import com.cn.jmw.entity.ProviderEntity;
import com.cn.jmw.loading.lo.LoadTireService;
import com.cn.jmw.loading.lo.LoadTireServiceImpl;
import com.cn.jmw.trie.Trie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author jmw
 * @Description 加载模块，主树注入数据
 * @date 2023年04月28日 17:08
 * @Version 1.0 TODO
 */
@Configuration
@EnableScheduling
@Slf4j
public class MainTree {

    private final ProviderEntity providerEntity;
    private final Trie trie;
    private final boolean scheduleEnabled;


    public MainTree(ProviderEntity providerEntity, Trie trie, @Value("${spring.trie.schedule.enable:false}") boolean scheduleEnabled) {
        this.providerEntity = providerEntity;
        this.trie = trie;
        this.scheduleEnabled = scheduleEnabled;
    }

    /**
     * 配置MainTree，用于加载Trie数据
     */
    @Bean
    @DependsOn({"providerEntity","tire"})
    public void MainTree(){
        LoadTireService loadTireService = new LoadTireServiceImpl(providerEntity, trie);
        loadTireService.loadConsumer(abstractFactoryProvider -> {
            log.info("加载判断逻辑");
            //判断缓存层面是否存在数据
            //判断数据库层面是否存在数据
            //应该采取何种方式加载数据
            boolean execute = false;
            try {
                execute = abstractFactoryProvider.execute(providerEntity);
            }catch (Exception e){
                e.printStackTrace();
            }
        });
    }

/**
     * 定期加载Trie数据
     */
    @Scheduled(fixedDelayString = "${spring.trie.schedule.fixedDelay:3600000}") // Run every hour
    @ConditionalOnProperty(name = "spring.trie.schedule.enable", havingValue = "true", matchIfMissing = false)
    public void loadTireData() {
        if (!scheduleEnabled) {
            return;
        }
        LoadTireService loadTireService = new LoadTireServiceImpl(providerEntity, trie);
        loadTireService.loadConsumer(abstractFactoryProvider -> {
            log.info("Loading Trie data...");
            // TODO: Add logic to load Trie data from cache or database
            boolean execute = false;
            try {
                execute = abstractFactoryProvider.execute(providerEntity);
            } catch (Exception e) {
                log.error("Failed to load Trie data: {}", e.getMessage());
            }
        });
    }

}
