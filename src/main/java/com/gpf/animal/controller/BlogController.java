package com.gpf.animal.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gpf.animal.common.PageVO;
import com.gpf.animal.common.Result;
import com.gpf.animal.dto.UserDTO;
import com.gpf.animal.entity.Blog;
import com.gpf.animal.service.BlogService;
import com.gpf.animal.util.UserHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * (Blog)表控制层
 *
 * @author makejava
 * @since 2022-11-10 09:14:03
 */
@RestController
@RequestMapping("blog")
public class BlogController {

    /**
     * 服务对象
     */
    @Resource
    private BlogService blogService;

    /**
     * 发布文章
     *
     * @param blog
     * @return
     */
    @PostMapping
    public Result saveBlog(@RequestBody Blog blog) {
        return blogService.saveBlog(blog);
    }

    /**
     * 根据id查询文章
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result queryBlogById(@PathVariable("id") Long id) {
        return blogService.queryBlogById(id);
    }

    /**
     * 查询所有文章
     *
     * @return
     */
    @PostMapping("/page")
    public Result queryBlog(@RequestBody PageVO pageVO) {
        return blogService.queryBlog(pageVO.getPage(), pageVO.getPageSize());
    }

    /**
     * 根据id查询文章
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result deleteBlogById(@PathVariable("id") Long id) {
        return blogService.deleteBlogById(id);
    }


    /**
     * @description: 查询用户的所有博客
     * @author gengpengfei
     * @date 2022/10/23 08:08
     */
    @GetMapping("/of/user")
    public Result queryBlogByUserId(@RequestParam(value = "current", defaultValue = "1") Integer current
            , @RequestParam("id") Long id) {
        return blogService.queryBlogByUserId(id, current);
    }

    /**
     * 根据博客id添加浏览量
     * @param blogId
     * @param userId
     * @return
     */
    @PostMapping("/{blogId}/{userId}")
    public Result addLookNums(@PathVariable Long blogId, @PathVariable Long userId){
        return blogService.addLookNums(blogId, userId);
    }

}

