package com.gpf.animal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gpf.animal.common.Result;
import com.gpf.animal.dao.SubCommentDao;
import com.gpf.animal.dto.SubCommentDTO;
import com.gpf.animal.dto.UserDTO;
import com.gpf.animal.entity.Comment;
import com.gpf.animal.entity.SubComment;
import com.gpf.animal.entity.User;
import com.gpf.animal.service.CommentService;
import com.gpf.animal.service.SubCommentService;
import com.gpf.animal.service.UserService;
import com.gpf.animal.util.UserHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author gengpengfei
 * @version V1.0
 * @ClassNane: SubCommentServiceImpl
 * @description:
 * @date 2022/11/12 12:29
 */
@Service("subCommentService")
public class SubCommentServiceImpl extends ServiceImpl<SubCommentDao, SubComment> implements SubCommentService {
    @Resource
    private CommentService commentService;
    @Resource
    private UserService userService;

    /**
     * 新增子评论
     *
     * @return
     * @parme comment
     */
    @Override
    public Result insertSubComment(SubComment subComment) {
        // TODO 判断用户是否登陆 未登陆不允许评论
        UserDTO user = UserHolder.getUser();
        if (user == null) {
            return Result.fail("用户未登录");
        }
        // TODO 判断评论内容是否为空 为空不允许评论
        if (subComment.getContent() == null) {
            return Result.fail("评论内容为空");
        }
        // TODO 添加子评论  并更改父评论的一级评论状态
        Long id = user.getId();
        subComment.setCommentUserId(id);
        save(subComment);
        Comment comment = commentService.getById(subComment.getCommentId());
        comment.setParentId(1L);
        commentService.updateById(comment);
        return Result.ok("添加成功");
    }

    /**
     * 删除子评论
     *
     * @param ids
     * @return
     * @parme subComment
     */
    @Override
    public Result deleteSubComment(List<Long> ids) {
        // TODO 判断用户是否登陆 未登陆不允许删除评论
        UserDTO user = UserHolder.getUser();
        if (user == null) {
            return Result.fail("用户未登录");
        }
        for (Long id : ids) {
            // TODO 判断当前是否有评论
            SubComment subComment = getById(id);
            if (subComment == null) {
                return Result.fail("当前无评论");
            }
            Long commentUserId = subComment.getCommentUserId();
            //TODO 判断当前评论的用户id和当前登陆的用户id是否一致
            if (commentUserId.equals(user.getId())) {
                return Result.fail("不能删除别人的评论");
            }
        }
        // TODO 删除评论
        removeByIds(ids);
        // TODO 修改父评论的一级评论状态为0
        for (Long id : ids) {
            SubComment subComment = getById(id);
            Comment comment = commentService.getById(subComment.getCommentId());
            comment.setParentId(0L);
            commentService.updateById(comment);
        }
        return Result.ok("删除成功");
    }

    /**
     * 查询所有子评论信息
     */
    @Override
    public List<SubCommentDTO> listSubComments(Long id) {
        // TODO 根据id查询所有子评论
        LambdaQueryWrapper<SubComment> wrapper = new LambdaQueryWrapper<>();
        List<SubComment> subCommentList = list(wrapper);
        List<SubCommentDTO> subCommentDTOList = new ArrayList<>();
        for (SubComment subComment : subCommentList) {
            // TODO  添加评论用户信息
            User commentUser = userService.getById(subComment.getCommentUserId());
            SubCommentDTO subCommentDTO = new SubCommentDTO();
            subCommentDTO.setCommentUserId(commentUser.getId());
            subCommentDTO.setCommentUserPicture(commentUser.getPicture());
            subCommentDTO.setCommentUserNickName(commentUser.getNickName());
            // TODO 添加被评论用户信息
            User commentedUser = userService.getById(subComment.getCommentedUserId());
            subCommentDTO.setCommentedUserId(commentedUser.getId());
            subCommentDTO.setCommentUserPicture(commentedUser.getPicture());
            subCommentDTO.setCommentedUserNickName(commentedUser.getNickName());
            // TODO 添加评论信息
            subCommentDTO.setCommentId(subComment.getCommentId());
            subCommentDTO.setLiked(subComment.getLiked());
            subCommentDTO.setContent(subComment.getContent());
            subCommentDTO.setCreateTime(subComment.getCreateTime());
            // TODO 加入到List集合中
            subCommentDTOList.add(subCommentDTO);
        }
        return subCommentDTOList;
    }
}
