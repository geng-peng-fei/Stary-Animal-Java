package com.gpf.animal.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * (Comment)实体类
 *
 * @author makejava
 * @since 2023-04-05 19:15:46
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment implements Serializable {
    /**
     * 评论id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 文章id
     */
    @TableField(value = "blog_id")
    private Integer blogId;
    /**
     * 内容
     */
    private String content;
    /**
     * 用户名称
     */
    @TableField(value = "user_name")
    private String userName;
    /**
     * 用户id
     */
    @TableField(value = "user_id")
    private Integer userId;
    /**
     * 父级评论id
     */
    private Integer pid;
    /**
     * 回复对象
     */
    private String target;
    /**
     * 回复对象Id
     */
    @TableField(value = "target_id")
    private Integer targetId;
    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "create_time")
    private String createTime;

}

