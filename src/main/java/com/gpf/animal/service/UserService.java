package com.gpf.animal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gpf.animal.common.Result;
import com.gpf.animal.dto.LoginFormDTO;
import com.gpf.animal.entity.User;

/**
 * (User)表服务接口
 *
 * @author makejava
 * @since 2022-11-10 09:14:04
 */
public interface UserService extends IService<User> {

    /**
     * 发送验证码
     *
     * @param phone 电话
     * @return Result 发送结果
     */
    Result sendCode(String phone);

    /**
     * 用户登陆
     *
     * @param loginForm 用户表单
     * @return Result 发送结果
     */
    Result login(LoginFormDTO loginForm);

    /**
     * 用户登出
     *
     * @param token 用户token
     * @return Result 发送结果
     */
    Result loginOut(String token);

    /**
     * 用户中心
     *
     * @param id 用户id
     * @return userDTO
     */
    Result userCenter(Long id);

    /**
     * 用户中心修改用户信息
     *
     * @param user 用户
     * @return Result 发送结果
     */
    Result updateUser(User user);

    /**
     * 分页查询
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    Result userPage(int page, int pageSize, String name);

    /**
     * 修改用户账号状态
     *
     * @param user
     * @return
     */
    Result updateUserStatus(User user);

    /**
     * 查询用户信息
     *
     * @param id
     * @return
     */
    Result getUserById(Long id);

    /**
     * 用户echart数据
     * @return
     */
    Result getUserEchartData();
}

