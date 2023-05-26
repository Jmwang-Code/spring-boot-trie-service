package com.cn.jmw.entity;

/**
 * 数据加载类型枚举。
 */
public enum LoadOn {
    //仅本地，远程仓库，混合
    LOCAL, // 仅本地
    REMOTE_REPOSITORY, // 远程仓库
    MIX; // 混合
}