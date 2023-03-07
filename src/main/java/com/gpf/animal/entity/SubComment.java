package com.gpf.animal.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * (SubComment)表实体类
 *
 * @author makejava
 * @since 2022-11-10 09:14:03
 */
@Data
public class SubComment implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @TableField(value = "comment_id")
    private Long commentId;
    @TableField(value = "subComment_id")
    private Long subCommentId;
    private Long commentUserId;
    private Long commentedUserId;
    private String content;
    private Long liked;
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

}

