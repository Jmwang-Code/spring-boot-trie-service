package com.cn.jmw.service;

import com.cn.jmw.trie.entity.MultiCodeMode;
import com.cn.jmw.trie.entity.TriePrefixQueryResult;
import com.cn.jmw.trie.entity.TrieQueryResult;

import java.util.List;

/**
 * @author jmw
 * @Description TODO
 * @date 2023年03月20日 15:56
 * @Version 1.0
 */
public interface TireService {

    /**
     * 添加一个单词到Trie中
     *
     * @param word 单词的整数数组表示
     * @param mode 多音字模式
     * @param code 单词的编码
     * @param type 单词的类型
     * @return 添加是否成功
     */
    boolean add(int[] word, MultiCodeMode mode, int code, int type);

    /**
     * 添加一个单词到Trie中
     *
     * @param word 单词的整数数组表示
     * @param mode 多音字模式
     * @param code 单词的编码
     * @return 添加是否成功
     */
    boolean add(int[] word, MultiCodeMode mode, int code);

    /**
     * 添加一个单词到Trie中
     *
     * @param word 单词的整数数组表示
     * @param mode 多音字模式
     * @return 添加是否成功
     */
    boolean add(int[] word, MultiCodeMode mode);

    /**
     * 添加一个单词到Trie中
     *
     * @param word 单词的字符串表示
     * @param mode 多音字模式
     * @param code 单词的编码
     * @param type 单词的类型
     * @return 添加是否成功
     */
    boolean add(String word, MultiCodeMode mode, int code, int type);

    /**
     * 添加一个单词到Trie中
     *
     * @param word 单词的字符串表示
     * @param mode 多音字模式
     * @param code 单词的编码
     * @return 添加是否成功
     */
    boolean add(String word, MultiCodeMode mode, int code);

    /**
     * 添加一个单词到Trie中
     *
     * @param word 单词的字符串表示
     * @param mode 多音字模式
     * @return 添加是否成功
     */
    boolean add(String word, MultiCodeMode mode);

    /**
     * 获取一个单词在Trie中的查询结果
     *
     * @param word 单词的字符串表示
     * @return 查询结果
     */
    TrieQueryResult get(String word);

    /**
     * 获取一个单词在Trie中的所有查询结果
     *
     * @param word 单词的字符串表示
     * @return 查询结果列表
     */
    public List<TrieQueryResult> getAll(String word);

    /**
     * 获取Trie中的单词数量
     *
     * @return 单词数量
     */
    int getSize();

    /**
     * 获取一个单词在Trie中的前缀查询结果
     *
     * @param word 单词的字符串表示
     * @return 前缀查询结果
     */
    TriePrefixQueryResult getPrefix(String word);

    /**
     * 从Trie中删除一个单词
     *
     * @param word 单词的字符串表示
     * @param code 单词的编码
     * @param type 单词的类型
     * @return 删除是否成功
     */
    boolean remove(String word, int code, int type);

    /**
     * 从Trie中删除一个单词
     *
     * @param word 单词的整数数组表示
     * @param code 单词的编码
     * @param type 单词的类型
     * @return 删除是否成功
     */
    boolean remove(int[] word, int code, int type);

    /**
     * 获取一个单词在Trie中的深度
     *
     * @param word 单词的字符串表示
     * @return 单词在Trie中的深度
     */
    int getDeep(String word);

    /**
     * @deprecated 禁用,会删除缓存树，如果连接缓存源会删除所有缓存
     */
    void clear();

}
