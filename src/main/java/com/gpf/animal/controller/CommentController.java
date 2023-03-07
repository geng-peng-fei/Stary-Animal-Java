package com.gpf.animal.controller;

import com.gpf.animal.common.Result;
import com.gpf.animal.entity.Comment;
import com.gpf.animal.service.CommentService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


/**
 * (Comment)表控制层
 *
 * @author makejava
 * @since 2022-11-10 09:14:03
 */
@RestController
@RequestMapping("/comment")
public class CommentController {
    /**
     * 服务对象
     */
    @Resource
    private CommentService commentService;

    /**
     * 新增评论
     *
     * @return result
     * @parme comment
     */
    @PostMapping
    public Result insertComment(@RequestBody Comment comment) {
        return commentService.insertComment(comment);
    }

    /**
     * 分页查询评论列表
     *
     * @return result
     * @parme comment
     */
    @GetMapping("/list/{id}")
    public Result commentList(@PathVariable Long id, int page, int pageSize, int sort) {
        return commentService.commentList(id, page, pageSize, sort);
    }

    /**
     * 删除评论
     *
     * @return result
     * @parme comment
     */
    @DeleteMapping("/{ids}")
    public Result deleteComment(@PathVariable List<Long> ids) {
        return commentService.deleteComment(ids);
    }
}

