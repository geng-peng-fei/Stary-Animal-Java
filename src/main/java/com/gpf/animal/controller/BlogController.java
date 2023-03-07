//package com.gpf.animal.controller;
//
//
//import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import com.gpf.animal.common.Result;
//import com.gpf.animal.dto.UserDTO;
//import com.gpf.animal.entity.Blog;
//import com.gpf.animal.service.BlogService;
//import com.gpf.animal.util.UserHolder;
//import org.springframework.web.bind.annotation.*;
//
//import javax.annotation.Resource;
//import java.util.List;
//
//
///**
// * (Blog)表控制层
// *
// * @author makejava
// * @since 2022-11-10 09:14:03
// */
//@RestController
//@RequestMapping("blog")
//public class BlogController {
//
//    /**
//     * 服务对象
//     */
//    @Resource
//    private BlogService blogService;
//
//    @PostMapping
//    public Result saveBlog(@RequestBody Blog blog) {
//        return blogService.saveblog(blog);
//    }
//
//    @GetMapping("/{id}")
//    public Result queryBlogById(@PathVariable("id") Long id) {
//        return blogService.queryBlogById(id);
//    }
//
//    @PutMapping("/like/{id}")
//    public Result likeBlog(@PathVariable Long id) {
//        return blogService.likeBlog(id);
//    }
//
//    @GetMapping("/likes/{id}")
//    public Result queryBlogLikes(@PathVariable Long id) {
//        return blogService.queryBlogLikes(id);
//    }
//
//    @GetMapping("/of/me")
//    public Result queryMyBlog(@RequestParam(value = "current", defaultValue = "1") Integer current) {
//        // 获取登录用户
//        UserDTO user = UserHolder.getUser();
//        // 根据用户查询
//        Page<Blog> page = blogService.query()
//                .eq("user_id", user.getId()).page(new Page<>(current, 10));
//        // 获取当前页数据
//        List<Blog> records = page.getRecords();
//        return Result.ok(records);
//    }
//
//    @GetMapping("/hot")
//    public Result queryHotBlog(@RequestParam(value = "current", defaultValue = "1") Integer current) {
//        return blogService.queryHotBlog(current);
//    }
//
//    /**
//     * @description: 查询用户的所有博客
//     * @author gengpengfei
//     * @date 2022/10/23 08:08
//     */
//    @GetMapping("/of/user")
//    public Result queryBlogByUserId(@RequestParam(value = "current", defaultValue = "1") Integer current
//            , @RequestParam("id") Long id) {
//        return blogService.queryBlogByUserId(id, current);
//    }
//
//    @GetMapping("/of/blog")
//    public Result queryBlogOfFollow(
//            @RequestParam("lastId") Long max,
//            @RequestParam(value = "offset", defaultValue = "0") Integer offset) {
//        return blogService.queryBlogOfFollow(max, offset);
//    }
//
//}
//
