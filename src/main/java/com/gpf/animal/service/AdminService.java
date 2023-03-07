package com.gpf.animal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gpf.animal.common.Result;
import com.gpf.animal.entity.Admin;

import javax.servlet.http.HttpServletRequest;

/**
 * (Admin)表服务接口
 *
 * @author makejava
 * @since 2022-11-10 09:14:03
 */
public interface AdminService extends IService<Admin> {
    /**
     * 管理员登录
     *
     * @param request
     * @param admin
     * @return
     */
    Result adminLogin(HttpServletRequest request, Admin admin);

    /**
     * 新增管理员
     *
     * @param httpServletRequest
     * @param admin
     * @return
     */
    Result insertAdmin(HttpServletRequest httpServletRequest, Admin admin);

    /**
     * 分页查询
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    Result adminPage(int page, int pageSize, String name);

    /**
     * 编辑信息  回显数据
     *
     * @param id
     * @return
     */
    Result selectById(Long id);

    /**
     * 员工退出
     *
     * @param request
     * @return
     */
    Result adminLogout(HttpServletRequest request);

    /**
     * 更新员工信息
     *
     * @param admin
     * @return
     */
    Result updateAdmin(Admin admin);
}

