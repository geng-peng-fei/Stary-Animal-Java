package com.gpf.animal.controller;

import com.gpf.animal.common.Result;
import com.gpf.animal.dto.RequestParams;
import com.gpf.animal.entity.PetVarieties;
import com.gpf.animal.entity.Varieties;
import com.gpf.animal.service.VarietiesService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * (Varieties)表控制层
 *
 * @author gpf
 * @since 2022-11-10 09:14:03
 */
@RestController
@RequestMapping("varieties")
public class VarietiesController {

    /**
     * 服务对象
     */
    @Resource
    private VarietiesService varietiesService;

    /**
     * 查询宠物类型分类列表
     *
     * @return
     */
    @GetMapping("/list")
    public Result varietiesList() {
        return varietiesService.varietiesList();
    }

    /**
     * 获得品种列表
     */
    @GetMapping("/menu")
    public Result getVarietiesMenu(Varieties varieties) {
        return varietiesService.getVarietiesMenu(varieties);
    }

    /**
     * 删除分类
     *
     * @param id
     * @return
     */
    @DeleteMapping
    public Result deleteVarieties(Long id) {
        return varietiesService.deleteVarieties(id);
    }

    /**
     * 编辑分类
     *
     * @param varieties
     * @return
     */
    @PutMapping
    public Result edit(@RequestBody Varieties varieties) {
        return varietiesService.edit(varieties);
    }

    /**
     * 添加分类
     *
     * @param varieties
     * @return
     */
    @PostMapping
    public Result insertVarieties(@RequestBody Varieties varieties) {
        return varietiesService.insertVarieties(varieties);
    }

    /**
     * 查询宠物类型分类列表
     *
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public Result varietiesPage(int page, int pageSize) {
        return varietiesService.varietiesPage(page, pageSize);
    }

    /**
     * 前端获取过滤项
     * @param requestParams
     * @return
     */
    @PostMapping("/filters")
    public Result varietiesFilter(@RequestBody RequestParams requestParams){return varietiesService.getVarietiesFilter(requestParams);}
}

