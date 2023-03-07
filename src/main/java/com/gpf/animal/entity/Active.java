package com.gpf.animal.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import java.io.Serializable;


/**
 * (Active)表实体类
 *
 * @author makejava
 * @since 2022-11-10 09:14:02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Active implements Serializable {
    /**
     * (Active)表实体类
     *
     * @author makejava
     * @since 2023-01-14 17:17:10
     */
    /**
     * 活动id
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 活动名称
     */
    private String name;
    /**
     * 活动成员（志愿者id）
     */
    private String member;
    /**
     * 活动图片
     */
    private String image;
    /**
     * 地址
     */
    private String address;
    /**
     * 开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String startTime;
    /**
     * 结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String endTime;
    /**
     * 描述
     */
    private String description;
    /**
     * 0 未开始 1 进行中 2 已结束
     */
    private Integer status;
}

