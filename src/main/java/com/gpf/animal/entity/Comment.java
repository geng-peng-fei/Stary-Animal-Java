package com.gpf.animal.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * (Comment)表实体类
 *
 * @author makejava
 * @since 2022-11-10 09:14:03
 */
@Data
public class Comment implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @TableField(value = "comment_id")
    private Long commentId;
    @TableField(value = "blog_id")
    private Long blogId;
    @TableField(value = "comment_user_id")
    private Long commentUserId;
    @TableField(value = "commented_user_id")
    private Long commentedUserId;
    @TableField(value = "parent_id")
    private Long parentId;
    private String content;
    private Long liked;
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

}

