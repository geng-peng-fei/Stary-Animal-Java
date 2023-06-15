package com.gpf.animal.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gpf.animal.common.PageVO;
import com.gpf.animal.common.Result;
import com.gpf.animal.dto.RequestParams;
import com.gpf.animal.entity.PetVarieties;
import com.gpf.animal.entity.Varieties;
import com.gpf.animal.service.PetVarietiesService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


/**
 * (PetVarieties)表控制层
 *
 * @author makejava
 * @since 2022-11-10 09:14:03
 */
@RestController
@RequestMapping("pet_Varieties")
public class PetVarietiesController {
    /**
     * 服务对象
     */
    @Resource
    private PetVarietiesService petVarietiesService;

    /**
     * 种类列表 分页
     */
    @PostMapping("/page")
    public Result petVarietiesPage(@RequestBody PageVO pageVO) {
        Integer page = pageVO.getPage();
        Integer pageSize = pageVO.getPageSize();
        String name = pageVO.getName();
        String petVarieties = pageVO.getPetVarieties();
        return petVarietiesService.PetVarietiesPage(page, pageSize, name, petVarieties);
    }

    /**
     * 根据id获得品种信息
     */
    @GetMapping("/{id}")
    public Result getPetVarietiesById(@PathVariable Long id) {
        return Result.ok(petVarietiesService.getById(id));
    }

    /**
     * 删除宠物种类 宠物种类有宠物的不能删除
     */
    @DeleteMapping
    public Result deletePetVarieties(@RequestParam Long id) {
        return petVarietiesService.deletePetVarieties(id);
    }

    /**
     * 修改宠物种类 宠物种类有宠物的不能修改
     */
    @PutMapping
    public Result editPetVarieties(@RequestBody PetVarieties petVarieties) {
        return petVarietiesService.editPetVarieties(petVarieties);
    }

    /**
     * 新增种类信息
     */
    @PostMapping
    public Result insertPetVarieties(@RequestBody PetVarieties petVarieties) {
        return petVarietiesService.savePetVarieties(petVarieties);
    }

    /**
     * 获得品种列表
     *
     * @param varietiesId
     */
    @GetMapping("/list/{varietiesId}")
    public Result getPetVarietiesList(@PathVariable Long varietiesId) {
        return petVarietiesService.getPetVarietiesList(varietiesId);
    }

    /**
     * 根据类型名称获取品种
     * @param varieties
     * @return
     */
    @GetMapping("/echart")
    public Result getPetVarietiesEchartData(@RequestParam(value = "varieties") String varieties){
        return petVarietiesService.getPetVarietiesEchartData(varieties);
    }
//    /**
//     * 获取varieties下的petVarieties
//     * @param requestParams
//     * @return
//     */
//    @PostMapping("/filters")
//    public Result getPetVarietiesFilters(@RequestBody RequestParams requestParams){
//        return petVarietiesService.getPetVarietiesFilters(requestParams);
//    }
}

