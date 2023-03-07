package com.gpf.animal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gpf.animal.common.Result;
import com.gpf.animal.dao.VarietiesDao;
import com.gpf.animal.dto.RequestParams;
import com.gpf.animal.entity.PetVarieties;
import com.gpf.animal.entity.Varieties;
import com.gpf.animal.service.PetVarietiesService;
import com.gpf.animal.service.VarietiesService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (Varieties)表服务实现类
 *
 * @author gpf
 * @since 2022-11-10 09:14:03
 */
@Service("varietiesService")
public class VarietiesServiceImpl extends ServiceImpl<VarietiesDao, Varieties> implements VarietiesService {

    @Resource
    private PetVarietiesService petVarietiesService;

    /**
     * 查询宠物类型分类列表
     *
     * @return
     */
    @Override
    public Result varietiesList() {
        //构造条件构造器
        LambdaQueryWrapper<Varieties> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        //                     升序 排序
        lambdaQueryWrapper.orderByAsc(Varieties::getId);
        //  List集合接收 查询结果
        List<Varieties> varietiesList = list(lambdaQueryWrapper);
        //返回  数据
        return Result.ok(varietiesList);
    }

    @Override
    public Result getVarietiesMenu(Varieties varieties) {
        //构造条件构造器
        LambdaQueryWrapper<Varieties> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        //             等值查询
        lambdaQueryWrapper.eq(varieties.getId() != null, Varieties::getId, varieties.getId());
        //                     升序 排序
        lambdaQueryWrapper.orderByAsc(Varieties::getId);
        //  List集合接收 查询结果
        List<Varieties> varietiesList = list(lambdaQueryWrapper);
        for (Varieties varieties1 : varietiesList) {
            LambdaQueryWrapper<PetVarieties> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(PetVarieties::getVarietiesId, varieties1.getId());
            List<PetVarieties> arrayList = petVarietiesService.list(wrapper);
            varieties1.setChlidren(arrayList);
        }
        return Result.ok(varietiesList);
    }

    /**
     * 查询宠物类型分类列表
     *
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public Result varietiesPage(int page, int pageSize) {
        Page<Varieties> varietiesPage = new Page<>(page, pageSize);
        LambdaQueryWrapper<Varieties> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.orderByAsc(Varieties::getId);
        page(varietiesPage, lambdaQueryWrapper);
        return Result.ok(varietiesPage);
    }

    /**
     * 编辑宠物类型分类
     *
     * @param varieties
     * @return
     */
    @Override
    public Result edit(Varieties varieties) {
        updateById(varieties);
        return Result.ok("修改成功");
    }

    /**
     * 删除分类
     *
     * @param id
     * @return
     */
    @Override
    public Result deleteVarieties(Long id) {
        //定义条件构造器
        LambdaQueryWrapper<PetVarieties> petVarietiesWrapper = new LambdaQueryWrapper<>();
        //通过条件构造器去查分类与品种是否有绑定
        petVarietiesWrapper.eq(PetVarieties::getVarietiesId, id);
        Long petVarietiesCount = petVarietiesService.count(petVarietiesWrapper);
        if (petVarietiesCount > 0) {
            //说明有绑定 返回错误信息
            return Result.fail("绑定了宠物品种，请先取消绑定");
        }
        removeById(id);
        return Result.ok("删除成功");
    }

    /**
     * 添加分类
     *
     * @param varieties
     * @return
     */
    @Override
    public Result insertVarieties(Varieties varieties) {
        save(varieties);
        return Result.ok("添加成功");
    }

    /**
     * 前端获取过滤项
     *
     * @param requestParams
     * @return
     */
    @Override
    public Result getVarietiesFilter(RequestParams requestParams) {
        //构造条件构造器
        LambdaQueryWrapper<Varieties> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        //                     升序 排序
        lambdaQueryWrapper.orderByAsc(Varieties::getId);
        //  List集合接收 查询结果
        List<Varieties> varietiesList = list(lambdaQueryWrapper);
        List<String> varietiesFilterList = new ArrayList<>(varietiesList.size());
        for (Varieties varieties : varietiesList) {
            varietiesFilterList.add(varieties.getName());
        }
        HashMap<String, List<String>> listHashMap = new HashMap<>();
        listHashMap.put("varietiesName", varietiesFilterList);
        String varietiesName = requestParams.getVarietiesName();
        // varieties不为空
        if (varietiesName != null) {
            LambdaQueryWrapper<PetVarieties> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(PetVarieties::getVarietiesName,varietiesName);
            List<PetVarieties> petVarietiesList = petVarietiesService.list(wrapper);
            List<String> list = new ArrayList<>(petVarietiesList.size());
            for (PetVarieties petVarieties : petVarietiesList) {
                list.add(petVarieties.getName());
            }
            listHashMap.put("petVarietiesName", list);
        }
        //返回  数据
        return Result.ok(listHashMap);
    }
}

