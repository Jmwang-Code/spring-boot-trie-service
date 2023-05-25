package com.cn.jmw.loading.lo;


import com.cn.jmw.provider.AbstractFactoryProvider;

@FunctionalInterface
/**
 * @author jmw
 * @Description TODO
 * @date 2023年04月23日 17:06
 * @Version 1.0
 */
public interface ConsumerHandler<T extends AbstractFactoryProvider> extends Handler {

    void handle(T t);
}



