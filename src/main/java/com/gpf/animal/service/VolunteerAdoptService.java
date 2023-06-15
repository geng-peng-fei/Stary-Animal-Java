package com.gpf.animal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gpf.animal.common.Result;
import com.gpf.animal.entity.VolunteerAdopt;

import java.util.List;

/**
 * (Volunteer)表服务接口
 *
 * @author makejava
 * @since 2022-11-10 09:14:04
 */
public interface VolunteerAdoptService extends IService<VolunteerAdopt> {
    /**
     * 新增申请
     */
    Result insertVolunteer(VolunteerAdopt volunteer);

    /**
     * 修改申请
     *
     * @param volunteer
     */
    Result updateVolunteer(VolunteerAdopt volunteer);

    /**
     * 查询申请
     *
     * @param id
     */
    Result getVolunteer(Long id);

    /**
     * 删除申请
     */
    Result deleteVolunteer(Long id);

    /**
     * 修改状态
     *
     * @param status
     * @param id
     * @return
     */
    Result updateStatus(int status, int id);

    /**
     * 查询申请列表
     */
    Result getVolunteerPage(int page, int pageSize, String name);
}

