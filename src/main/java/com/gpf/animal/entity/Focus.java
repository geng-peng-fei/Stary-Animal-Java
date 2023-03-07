package com.gpf.animal.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * (Focus)表实体类
 *
 * @author makejava
 * @since 2022-11-10 09:14:03
 */
@Data
public class Focus implements Serializable {

    private Long id;
    private Long focusUserId;
    private Long focusedUserId;
    @TableField(value = "create_time")
    private LocalDateTime createTime;

}

