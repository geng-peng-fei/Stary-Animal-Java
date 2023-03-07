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
 * (Volunteer)表实体类
 *
 * @author makejava
 * @since 2022-11-10 09:14:04
 */
@Data
public class Volunteer implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Long userId;
    private String description;
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private String createTime;
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private String updateTime;
    @TableField(value = "assessor", fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;
    @TableField(value = "audit_time", fill = FieldFill.INSERT_UPDATE)
    private String auditTime;
    private Integer status;

    @TableField(exist = false)
    private String userName;
    @TableField(exist = false)
    private String userPhone;
    @TableField(exist = false)
    private String userAddress;

}

