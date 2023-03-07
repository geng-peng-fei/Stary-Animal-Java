package com.gpf.animal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gpf.animal.common.Result;
import com.gpf.animal.dao.PetDao;
import com.gpf.animal.dto.RequestParams;
import com.gpf.animal.entity.Active;
import com.gpf.animal.entity.Pet;
import com.gpf.animal.entity.PetVarieties;
import com.gpf.animal.entity.Varieties;
import com.gpf.animal.service.PetService;
import com.gpf.animal.service.PetVarietiesService;
import com.gpf.animal.service.VarietiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static net.sf.jsqlparser.parser.feature.Feature.orderBy;

/**
 * (Pet)表服务实现类
 *
 * @author makejava
 * @since 2022-11-10 09:14:03
 */
@Service("petService")
public class PetServiceImpl extends ServiceImpl<PetDao, Pet> implements PetService {

    @Resource
    private PetVarietiesService petVarietiesService;
    @Resource
    private VarietiesService varietiesService;


    @Override
    public Result getPetList(RequestParams requestParams) {
        Integer page = requestParams.getPage();
        Integer pageSize = requestParams.getPageSize();
        String sortBy = requestParams.getSortBy();
        String key = requestParams.getKey();
        String petVarieties = requestParams.getPetVarietiesName();
        String varieties = requestParams.getVarietiesName();
        Page<Pet> petPage = new Page<>(page, pageSize);
        LambdaQueryWrapper<Pet> wrapper = new LambdaQueryWrapper<>();
        //宠物品种的查询
        wrapper.eq(varieties != null, Pet::getVarietiesName, varieties);
        wrapper.eq(petVarieties != null, Pet::getPetVarietiesName, petVarieties);
        wrapper.like(!"".equals(key), Pet::getPetVarietiesName, key);
        //排序条件
        if (sortBy == null || sortBy.equals("default")) {
            wrapper.orderByAsc(Pet::getId);
        } else if (sortBy.equals("timeDesc")) {
            wrapper.orderByDesc(Pet::getId);
        }
        List<Pet> petList = list(wrapper);
        List<Pet> newList;
        if (page * pageSize < petList.size()) {
            newList = petList.subList((page - 1) * pageSize, page * pageSize);
        } else {
            newList = petList.subList((page - 1) * pageSize, petList.size());
        }
        petPage.setRecords(newList);
        petPage.setTotal(petList.size());
        return Result.ok(petPage);
    }


    /**
     * 查询宠物列表 分页
     */
    @Override
    public Result getPetPage(Integer page, Integer pageSize, String nickName) {
        Page<Pet> petPage = new Page<>(page, pageSize);
        LambdaQueryWrapper<Pet> wrapper = new LambdaQueryWrapper<>();
        //宠物昵称的模糊查询
        wrapper.like(nickName != null, Pet::getNickName, nickName);
        wrapper.orderByAsc(Pet::getId);
        List<Pet> petList = list(wrapper);
        List<Pet> newList;
        if (page * pageSize < petList.size()) {
            newList = petList.subList((page - 1) * pageSize, page * pageSize);
        } else {
            newList = petList.subList((page - 1) * pageSize, petList.size());
        }
        petPage.setRecords(newList);
        petPage.setTotal(petList.size());
        return Result.ok(petPage);
    }

    /**
     * 修改状态
     *
     * @param status
     * @param id
     * @return
     */
    @Override
    public Result updateStatus(int status, Long id) {
        Pet pet = getById(id);
        pet.setStatus(status);
        updateById(pet);
        return Result.ok("修改成功");
    }

    @Override
    public Result deletePet(Long id) {
        removeById(id);
        return Result.ok("删除成功");
    }

    @Override
    public Result updatePet(Pet pet) {
        updateById(pet);
        return Result.ok("修改成功");
    }

    @Override
    public Result getPet(Long id) {
        Pet pet = getById(id);
        return Result.ok(pet);
    }

    /**
     * 新增宠物
     * @param pet
     * @return
     */
    @Override
    public Result savePet(Pet pet) {
        pet.setVarietiesName(varietiesService.getById(pet.getVarietiesId()).getName());
        pet.setPetVarietiesName(petVarietiesService.getById(pet.getPetVarietiesId()).getName());
        save(pet);
        return Result.ok("添加成功");
    }


    /**
     * 前端主页获得宠物列表
     *
     * @return Result
     */
    @Override
    public Result getPetList() {
        LambdaQueryWrapper<Pet> wrapper = new LambdaQueryWrapper<>();
        //排序条件
        wrapper.orderByDesc(Pet::getId);
        List<Pet> petList = list(wrapper);
        if (petList.size() <= 4){
            return Result.ok(petList);
        }
        List<Pet> pets = petList.subList(0, petList.size() - 1);
        return Result.ok(pets);
    }


}

