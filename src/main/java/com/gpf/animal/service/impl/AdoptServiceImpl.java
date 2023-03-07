package com.gpf.animal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gpf.animal.common.Result;
import com.gpf.animal.dao.AdoptDao;
import com.gpf.animal.entity.Adopt;
import com.gpf.animal.entity.Pet;
import com.gpf.animal.entity.User;
import com.gpf.animal.service.AdoptService;
import com.gpf.animal.service.PetService;
import com.gpf.animal.service.UserService;
import javafx.scene.input.DataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * (Adopt)表服务实现类
 *
 * @author makejava
 * @since 2022-11-10 09:14:03
 */
@Service("adoptService")
public class AdoptServiceImpl extends ServiceImpl<AdoptDao, Adopt> implements AdoptService {

    @Resource
    private UserService userService;
    @Resource
    private PetService petService;

    @Override
    public Result insertAdopt(Adopt adopt) {
        Long petId = adopt.getPetId();
        Long userId = adopt.getUserId();
        if (petId != null && userId != null) {
            LambdaQueryWrapper<Adopt> adoptLambdaQueryWrapper = new LambdaQueryWrapper<>();
            adoptLambdaQueryWrapper.eq(Adopt::getPetId, petId);
            adoptLambdaQueryWrapper.eq(Adopt::getUserId, userId);
            adoptLambdaQueryWrapper.last("limit 1");
            Adopt newAdopt = getOne(adoptLambdaQueryWrapper);

            if (newAdopt == null) {
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String time = formatter.format(now);
                adopt.setCreateTime(time);
                save(adopt);
                return Result.ok("提交成功！请耐心等待审批");
            }
        }
        return Result.fail("请勿重复申请领养");
    }

    @Override
    public Result updateAdopt(Adopt adopt) {
        updateById(adopt);
        return Result.ok("修改成功");
    }

    @Override
    public Result deleteAdopt(Long id) {
        removeById(id);
        return Result.ok("删除成功");
    }

    @Override
    public Result getAdopt(Long id) {
        LambdaQueryWrapper<Adopt> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Adopt::getUserId, id);
        List<Adopt> adoptList = list(wrapper);
        for (Adopt adopt : adoptList) {
            Pet pet = petService.getById(adopt.getUserId());
            adopt.setPetName(pet.getNickName());
            User user = userService.getById(adopt.getUserId());
            adopt.setUserAddress(user.getAddress());
            adopt.setUserPhone(user.getTelephone());
            adopt.setNickName(user.getNickName());
        }
        return Result.ok(adoptList);
    }

    @Override
    public Result updateStatus(int status, Long id) {
        Adopt adopt = getById(id);
        adopt.setStatus(status);
        updateById(adopt);

        return Result.ok("修改成功");
    }

    @Override
    public Result getAdoptPage(int page, int pageSize) {
        Page<Adopt> adoptPage = new Page<>(page, pageSize);
        LambdaQueryWrapper<Adopt> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(Adopt::getId);
        List<Adopt> adoptList = list(wrapper);
        for (Adopt adopt : adoptList) {
            //
            Long petId = adopt.getPetId();
            Pet pet = petService.getById(petId);
            String petName = pet.getNickName();
            adopt.setPetName(petName);
            //
            Long userId = adopt.getUserId();
            User user = userService.getById(userId);
            String nickName = user.getNickName();
            String telephone = user.getTelephone();
            String address = user.getAddress();
            adopt.setNickName(nickName);
            adopt.setUserPhone(telephone);
            adopt.setUserAddress(address);
        }
        List<Adopt> newList;
        if (page * pageSize < adoptList.size()) {
            newList = adoptList.subList((page - 1) * pageSize, page * pageSize);
        } else {
            newList = adoptList.subList((page - 1) * pageSize, adoptList.size());
        }
        adoptPage.setRecords(newList);
        adoptPage.setTotal(adoptList.size());
        return Result.ok(adoptPage);
    }
}

