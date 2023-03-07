package com.gpf.animal.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gpf.animal.common.Result;
import com.gpf.animal.dto.LoginFormDTO;
import com.gpf.animal.entity.User;
import com.gpf.animal.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.websocket.server.PathParam;
import java.io.Serializable;
import java.util.List;


/**
 * (User)表控制层
 *
 * @author makejava
 * @since 2022-11-10 09:14:03
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    /**
     * 发送验证码
     */
    @PostMapping("/code")
    public Result sendCode(@RequestParam("phone") String phone) {
        return userService.sendCode(phone);
    }

    /**
     * 登录功能
     *
     * @param loginForm 登录参数，包含手机号、验证码；或者账号、密码
     */
    @PostMapping("/login")
    public Result login(@RequestBody LoginFormDTO loginForm) {
        // TODO 实现登录功能
        return userService.login(loginForm);
    }

    /**
     * 用户登出
     *
     * @param token 用户token
     */
    @PostMapping("/{token}")
    public Result loginOut(@PathVariable String token) {
        return userService.loginOut(token);
    }

    /**
     * 修改用户账号状态
     * @param user
     * @return
     */
    @PutMapping
    public Result updateUserStatus(@RequestBody User user){
        return userService.updateUserStatus(user);
    }

    /**
     * 查询用户信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result getUserById(@PathVariable Long id){
        return userService.getUserById(id);
    }
    /**
     * 用户中心
     *
     * @param id 用户id
     */
    @PostMapping("/userCenter/{id}")
    public Result userCenter(@PathVariable Long id) {
        return userService.userCenter(id);
    }

    /**
     * 用户中心修改信息
     *
     * @param user 用户
     */
    @PostMapping("/update")
    public Result updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    /**
     * 分页查询
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public Result adminPage(int page, int pageSize, String name) {
        return userService.userPage(page, pageSize, name);
    }
}

