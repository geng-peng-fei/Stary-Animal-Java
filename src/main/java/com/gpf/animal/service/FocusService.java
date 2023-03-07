package com.gpf.animal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gpf.animal.common.Result;
import com.gpf.animal.entity.Focus;

/**
 * (Focus)表服务接口
 *
 * @author makejava
 * @since 2022-11-10 09:14:03
 */
public interface FocusService extends IService<Focus> {
    /**
     * @description: 关注/取关功能
     * @param: followUserId  isFollow
     * @return: Result
     * @author gengpengfei
     * @date: 2022/11/22 17:10
     */
    Result follow(Long focusedUserId, Boolean isFocus);

    /**
     * @description: 是否关注
     * @param: followUserId
     * @return: Result
     * @author gengpengfei
     * @date: 2022/11/22 17:08
     */
    Result isFollow(Long focusedUserId);

    /**
     * @description: 共同关注
     * @author gengpengfei
     * @date 2022/11/23 08:26
     */
    Result followCommons(Long id);

}

