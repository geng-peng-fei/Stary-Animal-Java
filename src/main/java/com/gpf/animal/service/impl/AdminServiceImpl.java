package com.gpf.animal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gpf.animal.common.Result;
import com.gpf.animal.dao.AdminDao;
import com.gpf.animal.entity.Admin;
import com.gpf.animal.service.AdminService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * (Admin)表服务实现类
 *
 * @author makejava
 * @since 2022-11-10 09:14:03
 */
@Service("adminService")
public class AdminServiceImpl extends ServiceImpl<AdminDao, Admin> implements AdminService {

    @Resource
    private AdminService adminService;
    /**
     * 管理员登录
     *
     * @param request
     * @param admin
     * @return
     */
    @Override
    public Result adminLogin(HttpServletRequest request, Admin admin) {
        //根据页面提交的用户名username查询数据库
        LambdaQueryWrapper<Admin> queryWrapper = new LambdaQueryWrapper<>();
        //查询条件   相等       adminName          值
        queryWrapper.eq(Admin::getAdminName, admin.getAdminName());
        Admin ad = getOne(queryWrapper);
        //如果没有查询到则返回登录失败结果
        if (ad == null) {
            return Result.fail("用户名错误");
        }
        //密码比对，如果不一致则返回登录失败结果
        if (!ad.getAdminPassword().equals(admin.getAdminPassword())) {
            return Result.fail("密码错误");
        }
        //查看管理员状态，如果为已禁用状态，则返回员工已禁用结果
        String status = "0";
        if (status.equals(ad.getStatus())) {
            return Result.fail("账号已禁用");
        }
        //登录成功，将员工id存入Session并返回登录成功结果
        request.getSession().setAttribute("admin", ad.getId());
        return Result.ok(ad);
    }

    /**
     * 新增管理员
     *
     * @param httpServletRequest
     * @param admin
     * @return
     */
    @Override
    public Result insertAdmin(HttpServletRequest httpServletRequest, Admin admin) {
        boolean success = save(admin);
        if (success) {
            return Result.ok("添加成功");
        }
        return Result.fail("添加失败");
    }

    /**
     * 分页查询
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @Override
    public Result adminPage(int page, int pageSize, String name) {
        //分页构造器
        Page adminPage = new Page(page, pageSize);
        //条件构造器
        LambdaQueryWrapper<Admin> wrapper = new LambdaQueryWrapper<>();
        //                模糊查询     name不为空          方法引用 对象 ：：实例方法  值
        wrapper.like(StringUtils.isNotEmpty(name), Admin::getName, name);
        //                   降序排列    方法引用 对象 ：：实例方法
        wrapper.orderByAsc(Admin::getId);
        //调page方法
        page(adminPage, wrapper);
        //返回数据
        return Result.ok(adminPage);
    }

    /**
     * 编辑信息  回显数据
     *
     * @param id
     * @return
     */
    @Override
    public Result selectById(Long id) {
        Admin admin = getById(id);
        return Result.ok(admin);
    }

    /**
     * 员工退出
     *
     * @param request
     * @return
     */
    @Override
    public Result adminLogout(HttpServletRequest request) {
        //清理Session中保存的当前登录员工的id
        request.getSession().removeAttribute("admin");
        return Result.ok("退出成功");
    }

    /**
     * 更新员工信息
     *
     * @param admin
     * @return
     */
    @Override
    public Result updateAdmin(Admin admin) {
        boolean success = updateById(admin);
        System.out.println(success);
        if (success == true) {
            return Result.ok("更新成功");
        } else {
            return Result.fail("更新失败");
        }
    }

}

