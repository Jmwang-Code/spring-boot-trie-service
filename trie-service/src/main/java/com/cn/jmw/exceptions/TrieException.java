package com.cn.jmw.exceptions;

/**
 * @author jmw
 * @Description TODO
 * @date 2023年05月25日 11:05
 * @Version 1.0
 */
public class TrieException extends RuntimeException {

    /**
     * 构造函数
     * @param message 错误消息
     */
    public TrieException(String message) {
        super(message);
    }

    /**
     * 构造函数
     * @param message 错误消息
     * @param cause 异常原因
     */
    public TrieException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Trie树节点不存在异常
     */
    public static class NodeNotFoundException extends TrieException {

        /**
         * 构造函数
         * @param message 错误消息
         */
        public NodeNotFoundException(String message) {
            super(message);
        }

    }

    /**
     * Trie树节点已存在异常
     */
    public static class NodeAlreadyExistsException extends TrieException {

        /**
         * 构造函数
         * @param message 错误消息
         */
        public NodeAlreadyExistsException(String message) {
            super(message);
        }

    }

}