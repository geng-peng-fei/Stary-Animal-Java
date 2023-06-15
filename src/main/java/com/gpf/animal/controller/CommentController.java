package com.gpf.animal.controller;

import com.gpf.animal.common.Result;
import com.gpf.animal.entity.Comment;
import com.gpf.animal.service.BlogService;
import com.gpf.animal.service.CommentService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


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
     * @param blogId
     * @return
     */
    @GetMapping("/list/{blogId}")
    public Result commentList(@PathVariable int blogId) {
        return commentService.commentList(blogId);
    }

    /**
     * 删除评论
     *
     * @parme commentId
     * @parme userId
     * @return result
     */
    @DeleteMapping("/{commentId}/{userId}")
    public Result deleteComment(@PathVariable Long commentId, @PathVariable Long userId) {
        return commentService.deleteComment(commentId, userId);
    }
}

