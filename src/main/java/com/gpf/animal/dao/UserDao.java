package com.gpf.animal.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gpf.animal.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * (User)表数据库访问层
 *
 * @author makejava
 * @since 2022-11-10 09:14:03
 */

public interface UserDao extends BaseMapper<User> {

}

