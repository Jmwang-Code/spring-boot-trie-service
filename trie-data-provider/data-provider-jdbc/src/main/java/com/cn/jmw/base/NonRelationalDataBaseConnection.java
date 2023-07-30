package com.cn.jmw.base;

import com.cn.jmw.service.AutoCloseBean;

import java.io.IOException;

public class NonRelationalDataBaseConnection extends AutoCloseBean {
    @Override
    public int timeoutMillis() {
        return 0;
    }

    @Override
    public void close() throws IOException {

    }
}
