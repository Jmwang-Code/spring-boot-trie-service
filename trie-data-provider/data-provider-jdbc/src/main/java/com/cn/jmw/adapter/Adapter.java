package com.cn.jmw.adapter;


import java.io.Closeable;

/**
 * @author jmw
 * @Description 适配器接口，用于从源中读取数据。
 * @param <Result> 要读取的数据类型。
 * @date 2023年04月11日 16:20
 * @Version 1.0
 */
public interface Adapter<Result> extends Closeable {

    /**
     * 测试适配器是否工作。
     * @return 如果适配器工作，则为true，否则为false。
     */
    public boolean test();
    
    /**
     * 从源中读取数据。
     * @return 读取的数据。
     */
    public Result streamingRead();
}
