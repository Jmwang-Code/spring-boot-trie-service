package com.cn.jmw.loading;

import com.cn.jmw.JdbcProvider;
import com.cn.jmw.adapter.AdapterFactory;
import com.cn.jmw.entity.ProviderEntity;
import com.cn.jmw.provider.AbstractFactoryProvider;
import com.cn.jmw.trie.Trie;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author jmw
 * @Description TODO
 * @Version 1.0
 */
@SpringBootTest
@Slf4j
public class JdbcAdapterTest {
    @Autowired
    private ProviderEntity providerEntity;

    @Test
    public void jdbcAdapter() {
        System.out.println(providerEntity.getRunnableThreadNum());
        boolean test = AdapterFactory.createDataAdapter(providerEntity.getDataSources().get(0),new Trie()).test();
        log.info("test:" + test);
    }

    @Test
    public void test() {
        AbstractFactoryProvider jdbcProvider = new JdbcProvider(new Trie());
        try {
            boolean test = jdbcProvider.test(providerEntity);
            System.out.println(test);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void execute() {
        AbstractFactoryProvider jdbcProvider = new JdbcProvider(new Trie());
        try {
            boolean execute = jdbcProvider.execute(providerEntity);
            System.out.println(execute);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
