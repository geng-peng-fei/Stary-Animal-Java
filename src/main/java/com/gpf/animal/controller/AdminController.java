package com.gpf.animal.controller;


import com.gpf.animal.common.PageVO;
import com.gpf.animal.common.Result;
import com.gpf.animal.entity.Admin;
import com.gpf.animal.service.AdminService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


/**
 * (Admin)表控制层
 *
 * @author makejava
 * @since 2022-11-10 09:14:03
 */
@RestController
@RequestMapping("admin")
public class AdminController {
    /**
     * 服务对象
     */
    @Resource
    private AdminService adminService;

    /**
     * 管理员登录
     *
     * @param request
     * @param admin
     * @return
     */
    @PostMapping("/login")
    public Result adminLogin(HttpServletRequest request, @RequestBody Admin admin) {
        return adminService.adminLogin(request, admin);
    }

    /**
     * 员工退出
     *
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public Result adminLogout(HttpServletRequest request) {
        return adminService.adminLogout(request);
    }

    /**
     * 新增员工
     *
     * @param httpServletRequest
     * @param admin
     * @return
     */
    @PostMapping
    public Result insertAdmin(HttpServletRequest httpServletRequest, @RequestBody Admin admin) {
        return adminService.insertAdmin(httpServletRequest, admin);
    }

    /**
     * 分页查询
     *
     * @return
     */
    @PostMapping("/page")
    public Result adminPage(@RequestBody  PageVO pageVO) {
        Integer page = pageVO.getPage();
        Integer pageSize = pageVO.getPageSize();
        String name = pageVO.getName();
        return adminService.adminPage(page, pageSize, name);
    }

    /**
     * 更新员工信息
     *
     * @param admin
     * @return
     */
    @PutMapping
    public Result updateAdmin(@RequestBody Admin admin) {
        return adminService.updateAdmin(admin);
    }

    /**
     * 编辑信息  回显数据
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result selectById(@PathVariable Long id) {
        return adminService.selectById(id);
    }

}

