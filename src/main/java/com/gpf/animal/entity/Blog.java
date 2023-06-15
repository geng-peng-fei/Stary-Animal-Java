package com.gpf.animal.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * (Blog)实体类
 *
 * @author makejava
 * @since 2023-03-16 16:48:04
 */
@Data
public class Blog implements Serializable {
    private static final long serialVersionUID = 681838979880245010L;
    /**
     * 文章id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 发布文章用户id
     */
    private Integer userId;
    /**
     * 文章标题
     */
    private String title;
    /**
     * 文章内容
     */
    private String content;
    /**
     * 文章浏览量
     */
    private Integer look;
    /**
     * 文章评论量
     */
    private Integer commentNum;
    /**
     * 文章发布时间
     */
    @JsonFormat(locale = "zh", timezone = "GMT+8")
    private Date dataTime;

    @TableField(exist = false)
    private String nickName;

    @TableField(exist = false)
    private String userImg;


    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", userId=" + userId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", look=" + look +
                ", commentNum=" + commentNum +
                ", dataTime=" + dataTime +
                '}';
    }
}

