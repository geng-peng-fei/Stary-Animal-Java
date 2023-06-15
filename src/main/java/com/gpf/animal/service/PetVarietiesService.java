package com.gpf.animal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gpf.animal.common.Result;
import com.gpf.animal.entity.PetVarieties;

/**
 * (PetVarieties)表服务接口
 *
 * @author makejava
 * @since 2022-11-10 09:14:03
 */
public interface PetVarietiesService extends IService<PetVarieties> {

    /**
     * 种类列表 分页
     */
    Result PetVarietiesPage(int page, int pageSize, String name, String petVarieties);

    /**
     * 删除宠物种类 宠物种类有宠物的不能删除
     *
     * @param id
     */
    Result deletePetVarieties(Long id);

    /**
     * 修改宠物种类 宠物种类有宠物的不能修改
     */
    Result editPetVarieties(PetVarieties petVarieties);

    /**
     * 新增宠物品种
     */
    Result savePetVarieties(PetVarieties petVarieties);

    /**
     * 获得品种列表
     *
     * @param varietiesId
     */
    Result getPetVarietiesList(Long varietiesId);

    /**
     * 根据类型名称获取品种
     * @param varieties
     * @return
     */
    Result getPetVarietiesEchartData(String varieties);

//    /**
//     * 获取varieties下的petVarieties
//     * @param requestParams
//     * @return
//     */
//    Result getPetVarietiesFilters(RequestParams requestParams);
}

