package com.gpf.animal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gpf.animal.common.Result;
import com.gpf.animal.entity.Volunteer;

import java.util.List;

/**
 * (Volunteer)表服务接口
 *
 * @author makejava
 * @since 2022-11-10 09:14:04
 */
public interface VolunteerService extends IService<Volunteer> {
    /**
     * 新增申请
     */
    Result insertVolunteer(Volunteer volunteer);

    /**
     * 修改申请
     *
     * @param volunteer
     */
    Result updateVolunteer(Volunteer volunteer);

    /**
     * 查询申请
     *
     * @param id
     */
    Result getVolunteer(Long id);

    /**
     * 删除申请（单个或批量）
     */
    Result deleteVolunteer(List<Long> ids);

    /**
     * 批量（单个）修改状态
     *
     * @param status
     * @param ids
     * @return
     */
    Result updateStatus(int status, List<Long> ids);

    /**
     * 查询申请列表
     */
    Result getVolunteerPage(int page, int pageSize, String id);
}

