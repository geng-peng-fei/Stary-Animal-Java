//package com.gpf.animal.controller;
//
//
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import com.gpf.animal.common.Result;
//import com.gpf.animal.entity.Focus;
//import com.gpf.animal.service.FocusService;
//import org.springframework.web.bind.annotation.*;
//
//import javax.annotation.Resource;
//import java.io.Serializable;
//import java.util.List;
//
//
///**
// * (Focus)表控制层
// *
// * @author makejava
// * @since 2022-11-10 09:14:03
// */
//@RestController
//@RequestMapping("focus")
//public class FocusController {
//    /**
//     * 服务对象
//     */
//    @Resource
//    private FocusService focusService;
//
//    /**
//     * @description: 关注/取关功能
//     * @param: followUserId  isFollow
//     * @return: Result
//     * @author gengpengfei
//     * @date: 2022/11/22 17:10
//     */
//    @PutMapping("/{id}/{isFollow}")
//    public Result follow(@PathVariable("id") Long focusedUserId, @PathVariable("isFollow") Boolean isFocus) {
//        return focusService.follow(focusedUserId, isFocus);
//    }
//
//    /**
//     * @description: 是否关注
//     * @param: followUserId
//     * @return: Result
//     * @author gengpengfei
//     * @date: 2022/11/22 17:08
//     */
//    @GetMapping("/or/not/{id}")
//    public Result isFollow(@PathVariable("id") Long focusedUserId) {
//        return focusService.isFollow(focusedUserId);
//    }
//
//    /**
//     * @description: 共同关注
//     * @author gengpengfei
//     * @date 2022/11/23 08:26
//     */
//    @GetMapping("/common/{id}")
//    public Result followCommons(@PathVariable("id") Long id) {
//        return focusService.followCommons(id);
//    }
//}
//
