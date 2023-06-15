package com.gpf.animal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gpf.animal.common.Result;
import com.gpf.animal.dto.RequestParams;
import com.gpf.animal.entity.Active;

import java.util.List;

/**
 * (Active)表服务接口
 *
 * @author makejava
 * @since 2022-11-10 09:14:02
 */
public interface ActiveService extends IService<Active> {
    /**
     * 新增活动
     */
    Result insertActive(Active active);

    /**
     * 修改状态
     *
     * @param status
     * @param id
     * @return
     */
    Result updateStatus(int status, Long id);

    /**
     * 删除活动（单个或批量）
     */
    Result deleteActives(Long id);

    /**
     * 修改活动
     *
     * @param active
     */
    Result updateActive(Active active);

    /**
     * 查询活动
     *
     * @param id
     */
    Result getActive(Long id);

    /**
     * 查询活动列表
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    Result getActivePage(int page, int pageSize, String name);

    /**
     * 前端获得活动列表
     *
     * @param requestParams
     * @return
     */
    Result getActiveList(RequestParams requestParams);

    /**
     * 前端主页获得活动列表
     *
     * @return
     */
    Result getActiveList();

    /**
     * 用户加入活动
     *
     * @param activeId
     * @param userId
     * @return
     */
    Result joinActive(int activeId, int userId);

    /**
     * echart数据
     * @return
     */
    Result getActiveEchartData();
}

