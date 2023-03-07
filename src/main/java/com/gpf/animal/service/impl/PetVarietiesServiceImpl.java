package com.gpf.animal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gpf.animal.common.Result;
import com.gpf.animal.dao.PetVarietiesDao;
import com.gpf.animal.entity.Pet;
import com.gpf.animal.entity.PetVarieties;
import com.gpf.animal.entity.Varieties;
import com.gpf.animal.service.PetService;
import com.gpf.animal.service.PetVarietiesService;
import com.gpf.animal.service.VarietiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (PetVarieties)表服务实现类
 *
 * @author makejava
 * @since 2022-11-10 09:14:03
 */
@Service("petVarietiesService")
public class PetVarietiesServiceImpl extends ServiceImpl<PetVarietiesDao, PetVarieties> implements PetVarietiesService {

    @Resource
    private PetService petService;

    @Autowired
    private VarietiesService varietiesService;

    /**
     * 种类列表 分页
     */
    @Override
    public Result PetVarietiesPage(int page, int pageSize, String name, String petVarieties) {
        Page<PetVarieties> petVarietiesPage = new Page<>();
        LambdaQueryWrapper<PetVarieties> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(name != null, PetVarieties::getName, name);
        wrapper.like(petVarieties != null, PetVarieties::getVarietiesName, petVarieties);
        wrapper.orderByAsc(PetVarieties::getId);
        page(petVarietiesPage, wrapper);
        return Result.ok(petVarietiesPage);
    }

    /**
     * 删除宠物品种 宠物品种有宠物的不能删除
     *
     * @param id
     */
    @Override
    public Result deletePetVarieties(Long id) {
        LambdaQueryWrapper<Pet> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Pet::getPetVarietiesId, id);
        long count = petService.count(wrapper);
        if (count > 0) {
            return Result.fail("该分类下有宠物，不能删除！");
        }
        removeById(id);
        return Result.ok("删除成功");
    }

    /**
     * 修改宠物种类 宠物种类有宠物的不能修改
     */
    @Override
    public Result editPetVarieties(PetVarieties petVarieties) {
        Long varieties = petVarieties.getVarietiesId();
        LambdaQueryWrapper<Pet> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Pet::getPetVarietiesId, varieties);
        long count = petService.count(wrapper);
        if (count > 0) {
            return Result.fail("该分类下有宠物,不能修改");
        }
        updateById(petVarieties);
        return Result.ok("修改成功");
    }

    /**
     * 新增宠物品种
     */
    @Override
    public Result savePetVarieties(PetVarieties petVarieties) {
        Long varietiesId = petVarieties.getVarietiesId();
        Varieties varieties = varietiesService.getById(varietiesId);
        petVarieties.setVarietiesName(varieties.getName());
        save(petVarieties);
        return Result.ok("新增成功");
    }

    /**
     * 获得品种列表
     * @param varietiesId
     */
    @Override
    public Result getPetVarietiesList(Long varietiesId) {
        if (varietiesId != null) {
            LambdaQueryWrapper<PetVarieties> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            //构造条件构造器
            lambdaQueryWrapper.eq(PetVarieties::getVarietiesId, varietiesId);
            //                     升序 排序
            lambdaQueryWrapper.orderByAsc(PetVarieties::getId);
            //  List集合接收 查询结果
            List<PetVarieties> petVarietiesList = list(lambdaQueryWrapper);
            //返回  数据
            return Result.ok(petVarietiesList);
        }
        return Result.fail("varietiesId为空");
    }

//    /**
//     * 获取varieties下的petVarieties
//     * @param requestParams
//     * @return
//     */
//    @Override
//    public Result getPetVarietiesFilters(RequestParams requestParams) {
//        String varietiesName = requestParams.getVarietiesName();
//        LambdaQueryWrapper<PetVarieties> wrapper = new LambdaQueryWrapper<>();
//        wrapper.eq(PetVarieties::getVarietiesName,varietiesName);
//        List<PetVarieties> petVarietiesList = list(wrapper);
//        List<String> petVarietiesFilterList = new ArrayList<>();
//        for (PetVarieties petVarieties : petVarietiesList) {
//            petVarietiesFilterList.add(petVarieties.getName());
//        }
//        return Result.ok(petVarietiesFilterList);
//    }


}

