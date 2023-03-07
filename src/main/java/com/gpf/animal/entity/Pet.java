package com.gpf.animal.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * (Pet)表实体类
 *
 * @author makejava
 * @since 2022-11-10 09:14:03
 */
@Data
public class Pet implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;


    private String nickName;


    private Integer age;

    @TableField("picture")
    private String image;


    @TableField(value = "petVarieties_id")
    private Integer petVarietiesId;


    @TableField(value = "petVarieties_name")
    private String petVarietiesName;


    private String varietiesId;

    private String varietiesName;


    private Integer sex;

    private String description;

    private Integer status;


}
