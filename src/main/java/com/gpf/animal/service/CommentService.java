package com.gpf.animal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gpf.animal.common.Result;
import com.gpf.animal.entity.Comment;

import java.util.List;

/**
 * (Comment)表服务接口
 *
 * @author makejava
 * @since 2022-11-10 09:14:03
 */
public interface CommentService extends IService<Comment> {
    /**
     * 新增评论
     *
     * @return
     * @parme comment
     */
    Result insertComment(Comment comment);

    /**
     * 分页查询评论列表
     *
     * @return result
     * @parme comment
     */
    Result commentList(Long id, int page, int pageSize, int sort);

    /**
     * 删除评论
     *
     * @return result
     * @parme comment
     */
    Result deleteComment(List<Long> ids);
}

