package com.gpf.animal.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;

import java.io.Serializable;

/**
 * (Admin)表实体类
 *
 * @author makejava
 * @since 2022-11-10 09:14:03
 */

@Data
@TableName("admin")
public class Admin implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String adminName;
    private String adminPassword;
    private String name;
    private Integer sex;
    private String telephone;
    private String email;
    private String idNumber;
    private Integer status;

}

