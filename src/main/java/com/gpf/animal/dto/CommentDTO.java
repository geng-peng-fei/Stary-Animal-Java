package com.gpf.animal.dto;

import com.gpf.animal.entity.Comment;
import lombok.Data;

import java.util.List;

/**
 * @author gengpengfei
 * @version V1.0
 * @ClassNane: CommentDTO
 * @description:
 * @date 2022/11/12 15:37
 */
@Data
public class CommentDTO extends Comment {

    /**
     * 用户信息
     */
    private Integer userId;
    private String userNickName;
    private String userPicture;
    /**
     * 评论信息
     */
    private Integer commentId;
    private String content;
    private String createTime;
    /**
     * 子评论列表
     */
    private List<CommentDTO> subCommentList;
}
