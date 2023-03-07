package com.gpf.animal.dto;

import com.gpf.animal.entity.Comment;
import com.gpf.animal.entity.SubComment;
import lombok.Data;

import java.time.LocalDateTime;
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
    private Long id;
    /**
     * 用户信息
     */
    private Long userId;
    private String userNickName;
    private String userPicture;
    /**
     * 评论信息
     */
    private Long commentId;
    private Long liked;
    private String content;
    private LocalDateTime createTime;
    /**
     * 子评论列表
     */
    private List<SubCommentDTO> subCommentList;
}
