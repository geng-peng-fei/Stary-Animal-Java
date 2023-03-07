package com.gpf.animal.controller;

import com.gpf.animal.common.Result;
import com.gpf.animal.entity.Adopt;
import com.gpf.animal.service.AdoptService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


/**
 * (Adopt)表控制层
 *
 * @author makejava
 * @since 2022-11-10 09:14:03
 */
@RestController
@RequestMapping("adopt")
public class AdoptController {
    /**
     * 服务对象
     */
    @Resource
    private AdoptService adoptService;

    /**
     * 新增领养申请
     */
    @PostMapping
    public Result insertAdopt(@RequestBody Adopt adopt) {
        return adoptService.insertAdopt(adopt);
    }

    /**
     * 删除领养申请
     */
    @DeleteMapping
    public Result deleteAdopt(@RequestParam Long id) {
        return adoptService.deleteAdopt(id);
    }

    /**
     * 修改领养申请
     *
     * @param adopt
     */
    @PutMapping
    public Result updateAdopt(@RequestBody Adopt adopt) {
        return adoptService.updateAdopt(adopt);
    }

    /**
     * 查询领养申请
     *
     * @param id
     */
    @GetMapping("/{id}")
    public Result getAdopt(@PathVariable Long id) {
        return adoptService.getAdopt(id);
    }


    /**
     * 修改状态
     *
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/status/{status}")
    public Result updateStatus(@PathVariable int status, @RequestParam Long id) {
        return adoptService.updateStatus(status, id);
    }

    /**
     * 查询领养申请列表
     */
    @GetMapping("/page")
    public Result getAdoptPage(int page, int pageSize) {
        return adoptService.getAdoptPage(page, pageSize);
    }
}

