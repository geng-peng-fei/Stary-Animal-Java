package com.gpf.animal.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gpf.animal.common.Result;
import com.gpf.animal.dao.CommentDao;
import com.gpf.animal.dto.CommentDTO;
import com.gpf.animal.dto.SubCommentDTO;
import com.gpf.animal.dto.UserDTO;
import com.gpf.animal.entity.Comment;
import com.gpf.animal.entity.SubComment;
import com.gpf.animal.service.CommentService;
import com.gpf.animal.service.SubCommentService;
import com.gpf.animal.util.UserHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * (Comment)表服务实现类
 *
 * @author makejava
 * @since 2022-11-10 09:14:03
 */
@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentDao, Comment> implements CommentService {
    @Resource
    private SubCommentService subCommentService;

    /**
     * 新增评论
     *
     * @return Result
     */
    @Override
    public Result insertComment(Comment comment) {
        // TODO 判断用户是否登陆 未登陆不允许评论
        UserDTO user = UserHolder.getUser();
        if (user == null) {
            return Result.fail("用户未登录");
        }
        // TODO 判断评论内容是否为空 为空不允许评论
        if (comment.getContent() == null) {
            return Result.fail("评论内容为空");
        }
        Long id = user.getId();
        comment.setCommentUserId(id);
        save(comment);
        return Result.ok("添加成功");
    }

    /**
     * 分页查询评论列表
     *
     * @return result
     */
    @Override
    public Result commentList(Long id, int page, int pageSize, int sort) {
        //TODO 查询当前博客的所有一级评论
        LambdaQueryWrapper<Comment> commentWrapper = new LambdaQueryWrapper<>();
        commentWrapper.eq(Comment::getBlogId, id);
        if (sort == 0) {
            commentWrapper.orderByAsc(Comment::getCreateTime);
        } else if (sort == 1) {
            commentWrapper.orderByDesc(Comment::getLiked);
        }
        // TODO 查询当前博客下的所有一级评论到所有子评论 添加到当前一级评论中
        List<Comment> commentList = list(commentWrapper);
        List<CommentDTO> commentDTOList = commentList.stream().map(comment -> BeanUtil.copyProperties(comment, CommentDTO.class)).collect(Collectors.toList());
        List<SubCommentDTO> subCommentDTOList = subCommentService.listSubComments(id);
        for (CommentDTO commentDTO : commentDTOList) {
            Long commentId = commentDTO.getCommentId();
            for (SubCommentDTO subCommentDTO : subCommentDTOList) {
                Long dtoCommentId = subCommentDTO.getCommentId();
                if (dtoCommentId.equals(commentId)) {
                    List<SubCommentDTO> subCommentDTOs = commentDTO.getSubCommentList();
                    if (subCommentDTOs == null) {
                        subCommentDTOs = new ArrayList<>();
                    }
                    subCommentDTOs.add(subCommentDTO);
                }
            }
        }
        return Result.ok(commentList);
    }

    /**
     * 删除评论
     *
     * @return result
     */
    @Override
    public Result deleteComment(List<Long> ids) {
        // TODO 判断用户是否登陆 未登陆不允许删除评论
        UserDTO user = UserHolder.getUser();
        if (user == null) {
            return Result.fail("用户未登录");
        }
        for (Long id : ids) {
            // TODO 判断当前是否有评论
            Comment comment = getById(id);
            if (comment == null) {
                return Result.fail("当前无评论");
            }
            Long commentUserId = comment.getCommentUserId();
            //TODO 判断当前评论的用户id和当前登陆的用户id是否一致
            if (commentUserId.equals(user.getId())) {
                return Result.fail("不能删除别人的评论");
            }
            //TODO 删除子评论
            LambdaQueryWrapper<SubComment> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(SubComment::getCommentId, id);
            subCommentService.remove(wrapper);
        }
        // TODO 删除评论
        removeByIds(ids);
        return Result.ok("删除成功");
    }
}

