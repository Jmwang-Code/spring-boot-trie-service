package com.cn.jmw;


import com.cn.jmw.adapter.Adapter;
import com.cn.jmw.adapter.AdapterFactory;
import com.cn.jmw.color.ThreadColor;
import com.cn.jmw.config.ThreadPoolConfig;
import com.cn.jmw.entity.ProviderEntity;
import com.cn.jmw.provider.AbstractFactoryProvider;
import com.cn.jmw.trie.Trie;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author jmw
 * @Description 提供一个可超时自动关闭的计时器，超时时间通过 timeoutMillis() 方法设置。 通过refresh()方法可刷新超时时间 超时后Timer自动调用 close方法
 * @date 2023年04月06日 9:44
 * @Version 1.0
 */
@Slf4j
public class JdbcProvider extends AbstractFactoryProvider {

    private final Trie<Object,Object> forest;

    public JdbcProvider(Trie<Object,Object> forest) {
        this.forest = (Trie<Object, Object>) forest;
         log.info(ThreadColor.getColor256(Thread.currentThread().getName()).getColoredString(Thread.currentThread().getName() + "——创建JDBC数据源提供者"));

    }

    /**
     * 构造函数，创建JDBC数据源提供者。
     * @param forest Forest对象
     */
    public final String CONFIG = "JDBC";

    /**
     * 执行 流式读取数据。
     * @param providerEntity 数据源提供者实体
     * @return 是否成功读取数据
     * @throws Exception 异常
     */
    @Override
    public boolean execute(ProviderEntity providerEntity) throws Exception {
try (ThreadPoolConfig threadPoolConfig = new ThreadPoolConfig(5)) {
            ExecutorService configurationCheckThreadPool = threadPoolConfig.getConfigurationCheckThreadPool();
            List<Future<Boolean>> futures = new ArrayList<>();
            for (int i = 0; i < providerEntity.getDataSources().size(); i++) {
                int finalI = i;
                Future<Boolean> future = configurationCheckThreadPool.submit(() -> {
                    Boolean aBoolean = false;
                    try (Adapter<Boolean> dataAdapter = AdapterFactory.createDataAdapter(providerEntity.getDataSources().get(finalI), forest)) {
                        aBoolean = dataAdapter.streamingRead();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return aBoolean;
                });
                futures.add(future);
            }
            for (Future<Boolean> future : futures) {
                if (!future.get()) {
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    //测试连接
    @Override
    public boolean test(ProviderEntity providerEntity) throws Exception {
        try (ThreadPoolConfig threadPoolConfig = new ThreadPoolConfig(5)) {
            ExecutorService configurationCheckThreadPool = threadPoolConfig.getConfigurationCheckThreadPool();
            List<Future<Boolean>> futures = new ArrayList<>();
            for (int i = 0; i < providerEntity.getDataSources().size(); i++) {
                int finalI = i;
                Future<Boolean> future= configurationCheckThreadPool.submit(()->{
                    Boolean aBoolean = false;
                    try (Adapter<Boolean> dataAdapter = AdapterFactory.createDataAdapter(providerEntity.getDataSources().get(finalI),forest);) {
                        aBoolean = dataAdapter.test();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return aBoolean;
                });
                futures.add(future);
            }
            for (Future<Boolean> future : futures) {
                if (!future.get()) {
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public String getConfigJsonFileName() {
        return CONFIG;
    }

    @Override
    public void close() throws IOException {
        System.out.println("close:" + getConfigJsonFileName());
    }

}
