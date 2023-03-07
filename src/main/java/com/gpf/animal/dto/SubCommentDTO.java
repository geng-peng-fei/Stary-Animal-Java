package com.gpf.animal.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author gengpengfei
 * @version V1.0
 * @ClassNane: SubCommentDTO
 * @description:
 * @date 2022/11/12 17:51
 */
@Data
public class SubCommentDTO {
    private Long id;
    /**
     * 自己的用户信息
     */
    private Long commentUserId;
    private String commentUserPicture;
    private String commentUserNickName;
    /**
     * 被回复用户的信息
     */
    private Long commentedUserId;
    private String commentedUserPicture;
    private String commentedUserNickName;
    /**
     * 评论内容
     */
    private Long commentId;
    private Long liked;
    private String content;
    private LocalDateTime createTime;

}