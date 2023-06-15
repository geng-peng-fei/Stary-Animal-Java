package com.gpf.animal.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gpf.animal.common.Result;
import com.gpf.animal.dao.BlogDao;
import com.gpf.animal.dao.CommentDao;
import com.gpf.animal.dao.UserDao;
import com.gpf.animal.dto.CommentDTO;
import com.gpf.animal.entity.Blog;
import com.gpf.animal.entity.Comment;
import com.gpf.animal.entity.SubComment;
import com.gpf.animal.entity.User;
import com.gpf.animal.service.BlogService;
import com.gpf.animal.service.CommentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static net.sf.jsqlparser.util.validation.metadata.NamedObject.user;

/**
 * (Comment)表服务实现类
 *
 * @author makejava
 * @since 2022-11-10 09:14:03
 */
@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentDao, Comment> implements CommentService {

    @Resource
    private CommentDao commentDao;

    @Resource
    private UserDao userDao;

    @Resource
    private BlogDao blogDao;

    /**
     * 新增评论
     *
     * @return Result
     */
    @Override
    public Result insertComment(Comment comment) {
        // TODO 判断评论内容是否为空 为空不允许评论
        if (comment.getContent() == null) {
            return Result.fail("评论内容为空");
        }
        if (commentDao.insert(comment) > 0) {
            Integer blogId = comment.getBlogId();
            Blog blog = blogDao.selectById(blogId);
            if (blog == null) {
                return Result.fail("");
            }
            Integer commentNum = blog.getCommentNum();
            commentNum += 1;
            if(blogDao.updateById(blog) > 0) {
                return Result.ok("添加成功");
            }
        }
        return Result.fail("添加失败，稍后再试！");
    }

    /**
     * 分页查询评论列表
     *
     * @return result
     */
    @Override
    public Result commentList(int blogId) {
        //查询当前文章的所有一级评论 pid = 0
        LambdaQueryWrapper<Comment> commentWrapper = new LambdaQueryWrapper<>();
        commentWrapper.eq(Comment::getBlogId, blogId);
        commentWrapper.orderByAsc(Comment::getCreateTime);

        //查询当前文章下的所有一级评论到所有子评论 添加到当前一级评论中
        List<Comment> commentList = commentDao.selectList(commentWrapper);
        List<CommentDTO> commentDTOList = new ArrayList<>();
        for (Comment comment : commentList) {
            User user = userDao.selectById(comment.getUserId());
            CommentDTO commentDTO = BeanUtil.copyProperties(comment, CommentDTO.class);
            commentDTO.setUserId(user.getId().intValue());
            commentDTO.setUserNickName(user.getNickName());
            commentDTO.setUserPicture(user.getPicture());
            commentDTO.setCommentId(comment.getId());
            commentDTOList.add(commentDTO);
        }
        List<CommentDTO> firstLevelCommentDTOList = commentDTOList.stream().filter(commentDTO -> commentDTO.getPid() == null).collect(Collectors.toList());
        List<CommentDTO> subCommentDTOList = commentDTOList.stream().filter(commentDTO -> commentDTO.getPid() != null).collect(Collectors.toList());

        for (CommentDTO firstLevelCommentDTO : firstLevelCommentDTOList) {
            firstLevelCommentDTO.setSubCommentList(subCommentDTOList.stream().filter(subCommentDTO -> firstLevelCommentDTO.getCommentId().equals(subCommentDTO.getPid())).collect(Collectors.toList()));
        }

        System.out.println(firstLevelCommentDTOList);
        return Result.ok(firstLevelCommentDTOList);
    }

    /**
     * 删除评论
     *
     * @return result
     */
    @Override
    public Result deleteComment(Long blogId, Long userId) {
        // TODO 判断当前是否有评论
        Comment comment = commentDao.selectById(blogId);
        if (comment == null) {
            return Result.fail("当前无评论");
        }
        Integer commentUserId = comment.getUserId();
        //TODO 判断当前评论的用户id和当前登陆的用户id是否一致
//        if (!commentUserId.equals(userId)) {
//            return Result.fail("不能删除别人的评论");
//        }
        //TODO 删除子评论
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Comment::getPid, comment.getPid());
        commentDao.delete(wrapper);

        // TODO 删除评论
        removeById(blogId);
        return Result.ok("删除成功");
    }
}

