package com.cn.jmw.service;

import com.cn.jmw.trie.Trie;
import com.cn.jmw.trie.entity.MultiCodeMode;
import com.cn.jmw.trie.entity.TriePrefixQueryResult;
import com.cn.jmw.trie.entity.TrieQueryResult;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author jmw
 * @Description TireService的实现类
 * @date 2023年03月20日 15:58
 * @Version 1.0
 */
@Service
public class TireServiceImpl implements TireService{
    private final Trie<Object,Object> trie;

    public TireServiceImpl(Trie<Object,Object> trie) {
        this.trie = trie;
    }

    @Override
    public boolean add(int[] word, MultiCodeMode mode, int code, int type) {
        return trie.add(word, mode, code, type);
    }

    @Override
    public boolean add(int[] word, MultiCodeMode mode, int code) {
        return trie.add(word, mode, code);
    }

    @Override
    public boolean add(int[] word, MultiCodeMode mode) {
        return trie.add(word, mode);
    }

    @Override
    public boolean add(String word, MultiCodeMode mode, int code, int type) {
        return trie.add(word, mode, code, type);
    }

    @Override
    public boolean add(String word, MultiCodeMode mode, int code) {
        return trie.add(word, mode, code);
    }

    @Override
    public boolean add(String word, MultiCodeMode mode) {
        return trie.add(word, mode);
    }

    @Override
    public TrieQueryResult get(String word) {
        return trie.get(word);
    }

    @Override
    public List<TrieQueryResult> getAll(String word) {
        return trie.getAll(word);
    }

    @Override
    public int getSize() {
        return trie.getSize();
    }

    @Override
    public TriePrefixQueryResult getPrefix(String word) {
        return trie.getPrefix(word);
    }

    @Override
    public boolean remove(String word, int code, int type) {
        return trie.remove(word,code,type);
    }

    @Override
    public boolean remove(int[] word, int code, int type) {
        return trie.remove(word,code,type);
    }

    @Override
    public int getDeep(String word) {
        return trie.getDeep(word);
    }

    @Override
    public void clear() {
        trie.clear();
    }
}
