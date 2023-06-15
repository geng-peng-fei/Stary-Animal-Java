package com.gpf.animal.controller;


import com.gpf.animal.common.PageVO;
import com.gpf.animal.common.Result;
import com.gpf.animal.common.StatusVo;
import com.gpf.animal.dto.RequestParams;
import com.gpf.animal.entity.Pet;
import com.gpf.animal.service.PetService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.websocket.server.PathParam;
import java.io.Serializable;
import java.util.List;


/**
 * (Pet)表控制层
 *
 * @author makejava
 * @since 2022-11-10 09:14:03
 */
@RestController
@RequestMapping("pet")
public class PetController {
    /**
     * 服务对象
     */
    @Resource
    private PetService petService;

    /**
     * 查询宠物列表
     */
    @PostMapping("/list")
    public Result getPetList(@RequestBody RequestParams requestParams) {
        return petService.getPetList(requestParams);
    }

    /**
     * 查询宠物分页
     */
    @PostMapping("/page")
    public Result getPetPage(@RequestBody PageVO pageVO) {
        Integer page = pageVO.getPage();
        Integer pageSize = pageVO.getPageSize();
        String name = pageVO.getName();
        return petService.getPetPage(page, pageSize, name);
    }

    /**
     * 删除宠物
     */
    @DeleteMapping
    public Result deletePet(@RequestParam Long id) {
        return petService.deletePet(id);
    }

    /**
     * 修改宠物
     *
     * @param pet
     */
    @PutMapping
    public Result updatePet(@RequestBody Pet pet) {
        return petService.updatePet(pet);
    }

    /**
     * 查询宠物
     *
     * @param id
     */
    @GetMapping("/{id}")
    public Result getPet(@PathVariable Long id) {
        return petService.getPet(id);
    }


    /**
     * 添加宠物
     *
     * @param pet
     */
    @PostMapping
    public Result savePet(@RequestBody Pet pet) {
        return petService.savePet(pet);
    }

    /**
     * 修改状态
     *
     * @return
     */
    @PostMapping("/status")
    public Result updateStatus(@RequestBody StatusVo statusVo) {
        Integer status = statusVo.getStatus();
        Long id = Long.valueOf(statusVo.getId());
        return petService.updateStatus(status, id);
    }

    /**
     * 前端主页获得宠物列表
     *
     * @return
     */
    @PostMapping("/listWu")
    public Result getPetList() {
        return petService.getPetList();
    }
}

