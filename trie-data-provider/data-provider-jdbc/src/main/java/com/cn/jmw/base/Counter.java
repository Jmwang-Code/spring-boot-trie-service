package com.cn.jmw.base;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author jmw
 * @Description 计数器类，用于计数。
 * @date 2023年04月28日 15:36
 * @Version 1.0
 */
public final class Counter {

    private final AtomicLong atomicInteger = new AtomicLong(0);

    /**
     * 将计数器加1并返回当前值。
     * @return 当前计数器的值。
     */
    public long inc() {
        return atomicInteger.incrementAndGet();
    }

    /**
     * 将计数器减1并返回当前值。
     * @return 当前计数器的值。
     */
    public long dec() {
        return atomicInteger.decrementAndGet();
    }

    /**
     * 返回当前计数器的值。
     * @return 当前计数器的值。
     */
    public long value() {
        return atomicInteger.get();
    }

}