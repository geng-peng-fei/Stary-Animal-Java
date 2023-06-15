package com.gpf.animal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gpf.animal.common.Result;
import com.gpf.animal.entity.Blog;

/**
 * (Blog)表服务接口
 *
 * @author makejava
 * @since 2022-11-10 09:14:03
 */
public interface BlogService extends IService<Blog> {

    /**
     * 发布文章
     *
     * @param blog
     * @return
     */
    Result saveBlog(Blog blog);

    /**
     * 根据id查询文章
     *
     * @param id
     * @return
     */
    Result deleteBlogById(Long id);

    /**
     * 根据id删除文章
     *
     * @param id
     * @return
     */
    Result queryBlogById(Long id);

    /**
     * 查询用户的所有文章
     *
     * @param id
     * @param current
     * @return
     */
    Result queryBlogByUserId(Long id, Integer current);


    /**
     * 查询所有文章
     *
     * @return
     */
    Result queryBlog(int page, int pageSize);

    /**
     * 根据博客id添加浏览量
     * @param blogId
     * @param userId
     * @return
     */
    Result addLookNums(Long blogId, Long userId);
}

