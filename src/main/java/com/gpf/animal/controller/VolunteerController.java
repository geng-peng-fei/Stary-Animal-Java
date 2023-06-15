package com.gpf.animal.controller;


import com.gpf.animal.common.PageVO;
import com.gpf.animal.common.Result;
import com.gpf.animal.common.StatusVo;
import com.gpf.animal.entity.VolunteerAdopt;
import com.gpf.animal.service.VolunteerAdoptService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


/**
 * (Volunteer)表控制层
 *
 * @author makejava
 * @since 2022-11-10 09:14:04
 */
@RestController
@RequestMapping("volunteerAdopt")
public class VolunteerController {

    /**
     * 服务对象
     */
    @Resource
    private VolunteerAdoptService volunteerService;

    /**
     * 新增申请
     */
    @PostMapping
    public Result insertVolunteer(@RequestBody VolunteerAdopt volunteer) {
        return volunteerService.insertVolunteer(volunteer);
    }

    /**
     * 删除申请
     */
    @DeleteMapping
    public Result deleteVolunteer(@RequestParam Long id) {
        return volunteerService.deleteVolunteer(id);
    }

    /**
     * 修改申请
     *
     * @param volunteer
     */
    @PutMapping
    public Result updateVolunteer(@RequestBody VolunteerAdopt volunteer) {
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
     * 修改状态
     *
     * @return
     */
    @PostMapping("/status")
    public Result updateStatus(@RequestBody StatusVo statusVo) {
        int status = statusVo.getStatus();
        int id = statusVo.getId();
        return volunteerService.updateStatus(status, id);
    }

    /**
     * 查询申请列表
     */
    @PostMapping("/page")
    public Result getVolunteerPage(@RequestBody PageVO pageVO) {
        Integer page = pageVO.getPage();
        Integer pageSize = pageVO.getPageSize();
        String name = pageVO.getName();
        return volunteerService.getVolunteerPage(page, pageSize, name);
    }

}

