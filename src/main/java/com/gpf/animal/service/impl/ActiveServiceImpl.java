package com.gpf.animal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gpf.animal.common.Result;
import com.gpf.animal.dao.ActiveDao;
import com.gpf.animal.dto.RequestParams;
import com.gpf.animal.entity.Active;
import com.gpf.animal.entity.Pet;
import com.gpf.animal.service.ActiveService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * (Active)表服务实现类
 *
 * @author makejava
 * @since 2022-11-10 09:14:02
 */
@Service("activeService")
public class ActiveServiceImpl extends ServiceImpl<ActiveDao, Active> implements ActiveService {
    /**
     * 新增活动
     */
    @Override
    public Result insertActive(Active active) {
        save(active);
        return Result.ok("添加成功");
    }

    /**
     * 批量（单个）修改状态
     *
     * @param status
     * @param id
     * @return
     */
    @Override
    public Result updateStatus(int status, Long id) {
        Active active = getById(id);
        active.setStatus(status);
        updateById(active);
        return Result.ok("修改成功");
    }

    /**
     * 删除活动
     */
    @Override
    public Result deleteActives(Long id) {
        removeById(id);
        return Result.ok("删除成功");
    }

    /**
     * 修改活动
     *
     * @param active
     */
    @Override
    public Result updateActive(Active active) {
        updateById(active);
        return Result.ok("修改成功");
    }

    /**
     * 查询活动
     *
     * @param id
     */
    @Override
    public Result getActive(Long id) {
        Active active = getById(id);
        return Result.ok(active);
    }

    /**
     * 查询活动列表
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @Override
    public Result getActivePage(int page, int pageSize, String name) {
        Page<Active> activePage = new Page(page, pageSize);
        LambdaQueryWrapper<Active> wrapper = new LambdaQueryWrapper();
        wrapper.like(name != null, Active::getName, name);
        wrapper.orderByAsc(Active::getId);
        page(activePage, wrapper);
        return Result.ok(activePage);
    }

    /**
     * 前端获得活动列表
     *
     * @param requestParams
     * @return Result
     */
    @Override
    public Result getActiveList(RequestParams requestParams) {
        Integer pageSize = requestParams.getPageSize();
        Integer page = requestParams.getPage();
        String key = requestParams.getKey();
        String sortBy = requestParams.getSortBy();
        Page<Active> activePage = new Page(page, pageSize);
        LambdaQueryWrapper<Active> wrapper = new LambdaQueryWrapper();
        wrapper.like(key != null, Active::getName, key);
        //排序条件
        if (sortBy == null || sortBy.equals("default")) {
            wrapper.orderByAsc(Active::getId);
        } else if (sortBy.equals("timeDesc")) {
            wrapper.orderByDesc(Active::getId);
        }
        List<Active> activeList = list(wrapper);
        List<Active> newList;
        if (page * pageSize < activeList.size()) {
            newList = activeList.subList((page - 1) * pageSize, page * pageSize);
        } else {
            newList = activeList.subList((page - 1) * pageSize, activeList.size());
        }
        activePage.setRecords(newList);
        activePage.setTotal(activeList.size());
        return Result.ok(activePage);
    }

    /**
     * 前端主页获得活动列表
     *
     * @return Result
     */
    @Override
    public Result getActiveList() {
        LambdaQueryWrapper<Active> wrapper = new LambdaQueryWrapper<>();
        //排序条件
        wrapper.orderByDesc(Active::getId);
        List<Active> activeList = list(wrapper);
        List<Active> actives;
        if (activeList.size() <= 4) {
            return Result.ok(activeList);
        }else {
            actives = activeList.subList(0, activeList.size() - 1);
        }
        return Result.ok(actives);
    }

    /**
     * 用户加入活动
     *
     * @param activeId
     * @param userId
     * @return
     */
    @Override
    public Result joinActive(int activeId, int userId) {
        Active active = getById(activeId);
        String member = active.getMember();
        if (member != null) {
            String[] split = member.split(",");
            for (String s : split) {
                if (s.equals(String.valueOf(userId))) {
                    return Result.fail("您已参加此活动");
                }
            }
            member += ("," + userId);
        }
        member += userId;
        active.setMember(member);
        boolean isSuccess = updateById(active);
        return Result.ok(isSuccess);
    }
}

