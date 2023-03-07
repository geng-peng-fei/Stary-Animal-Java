package com.gpf.animal.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
//import org.springframework.data.annotation.Transient;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * (PetVarieties)表实体类
 *
 * @author makejava
 * @since 2022-11-10 09:14:03
 */
@Data
public class PetVarieties implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long varietiesId;

    private String varietiesName;

    private String name;

    private String description;


}

