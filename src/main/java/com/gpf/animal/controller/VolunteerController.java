package com.gpf.animal.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gpf.animal.common.Result;
import com.gpf.animal.entity.Adopt;
import com.gpf.animal.entity.Volunteer;
import com.gpf.animal.service.VolunteerService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;


/**
 * (Volunteer)表控制层
 *
 * @author makejava
 * @since 2022-11-10 09:14:04
 */
@RestController
@RequestMapping("volunteer")
public class VolunteerController {
    /**
     * 服务对象
     */
    @Resource
    private VolunteerService volunteerService;

    /**
     * 新增申请
     */
    @PostMapping
    public Result insertVolunteer(@RequestBody Volunteer volunteer) {
        return volunteerService.insertVolunteer(volunteer);
    }

    /**
     * 删除申请（单个或批量）
     */
    @DeleteMapping
    public Result deleteVolunteer(@RequestParam List<Long> ids) {
        return volunteerService.deleteVolunteer(ids);
    }

    /**
     * 修改申请
     *
     * @param volunteer
     */
    @PutMapping
    public Result updateVolunteer(@RequestBody Volunteer volunteer) {
        return volunteerService.updateVolunteer(volunteer);
    }

    /**
     * 查询申请
     *
     * @param id
     */
    @GetMapping("/{id}")
    public Result getVolunteer(@PathVariable Long id) {
        return volunteerService.getVolunteer(id);
    }


    /**
     * 批量（单个）修改状态
     *
     * @param status
     * @param ids
     * @return
     */
    @PostMapping("/status/{status}")
    public Result updateStatus(@PathVariable int status, @RequestParam List<Long> ids) {
        return volunteerService.updateStatus(status, ids);
    }

    /**
     * 查询申请列表
     */
    @GetMapping("/page")
    public Result getVolunteerPage(int page, int pageSize, String id) {
        return volunteerService.getVolunteerPage(page, pageSize, id);
    }

}

