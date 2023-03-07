package com.gpf.animal.entity;

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
 * (User)表实体类
 *
 * @author makejava
 * @since 2022-11-10 09:14:03
 */
@Data
public class User implements Serializable {

    /**
     * @TableField 实体类属性与数据库字段的映射
     */

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String nickName;
    @TableField(value = "name")
    private String name;
    private String telephone;
    private String idCard;
    private Integer age;
    private String picture;
    private Integer sex;
    private String email;
    private String address;
    private Integer status;


}

