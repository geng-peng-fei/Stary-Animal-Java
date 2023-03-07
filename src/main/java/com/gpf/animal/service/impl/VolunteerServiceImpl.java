package com.gpf.animal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gpf.animal.common.Result;
import com.gpf.animal.dao.VolunteerDao;
import com.gpf.animal.entity.Adopt;
import com.gpf.animal.entity.Pet;
import com.gpf.animal.entity.User;
import com.gpf.animal.entity.Volunteer;
import com.gpf.animal.service.UserService;
import com.gpf.animal.service.VolunteerService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Volunteer)表服务实现类
 *
 * @author makejava
 * @since 2022-11-10 09:14:04
 */
@Service("volunteerService")
public class VolunteerServiceImpl extends ServiceImpl<VolunteerDao, Volunteer> implements VolunteerService {

    @Resource
    private UserService userService;

    @Override
    public Result insertVolunteer(Volunteer volunteer) {
        save(volunteer);
        return Result.ok("添加成功");
    }

    @Override
    public Result updateVolunteer(Volunteer volunteer) {
        updateById(volunteer);
        return Result.ok("修改成功");
    }

    @Override
    public Result getVolunteer(Long id) {
        Volunteer volunteer = getById(id);
        return Result.ok(volunteer);
    }

    @Override
    public Result deleteVolunteer(List<Long> ids) {
        removeByIds(ids);
        return Result.ok("删除成功");
    }

    @Override
    public Result updateStatus(int status, List<Long> ids) {
        for (Long id : ids) {
            Volunteer volunteer = getById(id);
            volunteer.setStatus(status);
            updateById(volunteer);
        }
        return Result.ok("修改成功");
    }

    @Override
    public Result getVolunteerPage(int page, int pageSize, String id) {
        Page<Volunteer> volunteerPage = new Page<>(page, pageSize);
        LambdaQueryWrapper<Volunteer> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.isNotEmpty(id), Volunteer::getId, id);
        wrapper.orderByAsc(Volunteer::getCreateTime);
        List<Volunteer> volunteerList = list(wrapper);
        for (Volunteer volunteer : volunteerList) {
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
        List<Volunteer> newList;
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

