package com.cn.jmw.controller;

import com.cn.jmw.dto.ResponseData;
import com.cn.jmw.service.TireService;
import com.cn.jmw.trie.entity.MultiCodeMode;
import com.cn.jmw.trie.entity.TriePrefixQueryResult;
import com.cn.jmw.trie.entity.TrieQueryResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.print.Book;
import java.util.List;

/**
 * @author jmw
 * @Description TODO
 * @date 2023年03月20日 16:31
 * @Version 1.0
 */
@RestController
@RequestMapping("Tire")
@Tag(name = "前缀树操作")
@RefreshScope
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = Book.class))),
        @ApiResponse(responseCode = "404", description = "Book not found")
})
public class TireController {

    private final TireService tireService;

    public TireController(TireService tireService) {
        this.tireService = tireService;
    }

    public boolean add(int[] word, MultiCodeMode mode, int code, int type) {
        return tireService.add(word, mode, code, type);
    }

    public boolean add(int[] word, MultiCodeMode mode, int code) {
        return tireService.add(word, mode, code);
    }

    public boolean add(int[] word, MultiCodeMode mode) {
        return tireService.add(word, mode);
    }

    public boolean add(String word, MultiCodeMode mode, int code, int type) {
        return tireService.add(word, mode, code, type);
    }

    public boolean add(String word, MultiCodeMode mode, int code) {
        return tireService.add(word, mode, code);
    }

    /**
     * 添加单词到前缀树中
     * @param word 单词
     * @param mode 多编码模式
     * @return 添加结果
     */
    @Operation(summary = "字符串添加")
    @PostMapping("/add")
    public ResponseData<Boolean> add(String word, MultiCodeMode mode) {
        return ResponseData.success(tireService.add(word, mode));
    }

    /**
     * 单词第一个匹配信息查询
     * @param word 单词
     * @return 匹配结果
     */
    @Operation(summary = "单词第一个匹配信息查询")
    @PostMapping("/query")
    public ResponseData<TrieQueryResult> get(String word) {
        return ResponseData.success(tireService.get(word));
    }

    /**
     * 单词全部匹配信息查询
     * @param word 单词
     * @return 匹配结果列表
     */
    @Operation(summary = "单词全部匹配信息查询")
    @PostMapping("/queryAll")
    public ResponseData<List<TrieQueryResult>> getAll(String word) {
        return ResponseData.success(tireService.getAll(word));
    }

    /**
     * 单词数量查询
     * @return 单词数量
     */
    @Operation(summary = "单词数量查询")
    @PostMapping("/size")
    public ResponseData<Integer> getSize() {
        return ResponseData.success(tireService.getSize());
    }

    /**
     * 单词前缀查询,查询子树
     * @param word 单词
     * @return 前缀查询结果
     */
    @Operation(summary = "单词前缀查询,查询子树")
    @PostMapping("/prefix/query")
    public ResponseData<TriePrefixQueryResult> getPrefix(String word) {
        return ResponseData.success(tireService.getPrefix(word));
    }

    /**
     * 移除单词
     * @param word 单词
     * @param code 编码
     * @param type 类型
     * @return 移除结果
     */
    @Operation(summary = "移除单词")
    @PostMapping("/remove")
    public ResponseData<Boolean> remove(String word, int code, int type) {
        return ResponseData.success(tireService.remove(word, code, type));
    }

    public boolean remove(int[] word, int code, int type) {
        return tireService.remove(word,code,type);
    }

    /**
     * 获取子树（辅助树）深度
     * @param word 单词
     * @return 子树深度
     */
    @Operation(summary = "获取子树（辅助树）深度")
    @PostMapping("/deep")
    public ResponseData<Integer> getDeep(String word) {
        return ResponseData.success(tireService.getDeep(word));
    }

    /**
     * 清空前缀树
     */
    public void clear() {
        tireService.clear();
    }

}
