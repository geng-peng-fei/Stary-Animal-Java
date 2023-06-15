package com.gpf.animal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gpf.animal.common.Result;
import com.gpf.animal.dao.VolunteerAdoptDao;
import com.gpf.animal.entity.User;
import com.gpf.animal.entity.VolunteerAdopt;
import com.gpf.animal.service.UserService;
import com.gpf.animal.service.VolunteerAdoptService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * (Volunteer)表服务实现类
 *
 * @author makejava
 * @since 2022-11-10 09:14:04
 */
@Service("volunteerService")
public class VolunteerAdoptServiceImpl extends ServiceImpl<VolunteerAdoptDao, VolunteerAdopt> implements VolunteerAdoptService {

    @Resource
    private UserService userService;

    @Override
    public Result insertVolunteer(VolunteerAdopt volunteer) {
        volunteer.setCreateTime(new Date());
        save(volunteer);
        return Result.ok("添加成功");
    }

    @Override
    public Result updateVolunteer(VolunteerAdopt volunteer) {
        updateById(volunteer);
        return Result.ok("修改成功");
    }

    @Override
    public Result getVolunteer(Long id) {
        VolunteerAdopt volunteer = getById(id);
        return Result.ok(volunteer);
    }

    @Override
    public Result deleteVolunteer(Long id) {
        removeById(id);
        return Result.ok("删除成功");
    }

    @Override
    public Result updateStatus(int status, int id) {
        VolunteerAdopt volunteer = getById(id);
        volunteer.setStatus(status);
        updateById(volunteer);
        return Result.ok("修改成功");
    }

    @Override
    public Result getVolunteerPage(int page, int pageSize, String name) {
        Page<VolunteerAdopt> volunteerPage = new Page<>(page, pageSize);
        LambdaQueryWrapper<VolunteerAdopt> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.isNotEmpty(name), VolunteerAdopt::getId, name);
        wrapper.orderByAsc(VolunteerAdopt::getCreateTime);
        List<VolunteerAdopt> volunteerList = list(wrapper);
        for (VolunteerAdopt volunteer : volunteerList) {
            //
            Long userId = volunteer.getUserId();
            User user = userService.getById(userId);
            String nickName = user.getNickName();
            String telephone = user.getTelephone();
            String address = user.getAddress();
            volunteer.setUserName(nickName);
            volunteer.setUserPhone(telephone);
            volunteer.setUserAddress(address);
        }
        List<VolunteerAdopt> newList;
        if (page * pageSize < volunteerList.size()) {
            newList = volunteerList.subList((page - 1) * pageSize, page * pageSize);
        } else {
            newList = volunteerList.subList((page - 1) * pageSize, volunteerList.size());
        }
        volunteerPage.setRecords(newList);
        volunteerPage.setTotal(volunteerList.size());
        return Result.ok(volunteerPage);
    }
}

