package com.gpf.animal.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * (Blog)表实体类
 *
 * @author makejava
 * @since 2022-11-10 09:14:03
 */
@Data
public class Blog implements Serializable {

    private Long id;

    private Long userId;

    private String title;

    private String picture;

    private String content;
    /**
     * 用户图标
     */
    @TableField(exist = false)
    private String icon;
    /**
     * 用户昵称
     */
    @TableField(exist = false)
    private String nickName;
    /**
     * 是否点赞过了
     */
    @TableField(exist = false)
    private Boolean isLike;

    private Integer liked;

    private Integer comment;

    @TableField(value = "create_time")
    private LocalDateTime createTime;

    @TableField(value = "update_time")
    private LocalDateTime updateTime;

}

