package com.gpf.animal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gpf.animal.common.Result;
import com.gpf.animal.dao.BlogDao;
import com.gpf.animal.entity.Blog;
import com.gpf.animal.entity.User;
import com.gpf.animal.service.BlogService;
import com.gpf.animal.service.UserService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import java.util.Date;
import java.util.List;


/**
 * (Blog)表服务实现类
 *
 * @author makejava
 * @since 2022-11-10 09:14:03
 */
@Service("blogService")
public class BlogServiceImpl extends ServiceImpl<BlogDao, Blog> implements BlogService {

    @Resource
    private UserService userService;

    /**
     * 发布文章
     *
     * @param blog
     * @return
     */
    @Override
    public Result saveBlog(Blog blog) {
        blog.setDataTime(new Date());
        boolean save = save(blog);
        if (save) {
            return Result.ok("发布成功");
        }
        return Result.fail("发布失败");
    }

    /**
     * @description: 根据id查询文章
     *
     * @param id
     * @return
     */
    @Override
    public Result queryBlogById(Long id) {
        //1.    查询blog
        Blog blog = getById(id);
        //2.    查询blog的用户
        Integer userId = blog.getUserId();
        User user = userService.getById(userId);
        blog.setNickName(user.getNickName());
        blog.setUserImg(user.getPicture());
        //3.    返回带用户信息的blog
        return Result.ok(blog);
    }

    /**
     * @description: 根据id删除文章
     *
     * @param id
     * @return
     */
    @Override
    public Result deleteBlogById(Long id) {
        boolean success = removeById(id);
        if (!success){
            return Result.fail("删除失败");
        }
        return Result.ok("删除成功");
    }


    /**
     * @description: 查询用户的所有文章
     * @author gengpengfei
     * @date 2022/10/23 08:09
     */
    @Override
    public Result queryBlogByUserId(Long id, Integer current) {
        //  查询用户的所有博客 并 分页
        LambdaQueryWrapper<Blog> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Blog::getUserId, id);
        Page<Blog> page = page(new Page<Blog>(), wrapper);
        //  获取当前页数据
        List<Blog> records = page.getRecords();
        return Result.ok(records);
    }

    /**
     * 查询所有文章
     *
     * @return
     */
    @Override
    public Result queryBlog(int page, int pageSize) {
        Page<Blog> blogPage = new Page<>(page, pageSize);
        LambdaQueryWrapper<Blog> wrapper = new LambdaQueryWrapper<>();
        List<Blog> blogList = list(wrapper);
        for (Blog blog : blogList) {
            User user = userService.getById(blog.getUserId());
            blog.setNickName(user.getNickName());
            blog.setUserImg(user.getPicture());
        }
        blogPage.setRecords(blogList);
        blogPage.setTotal(blogList.size());
        return Result.ok(blogPage);
    }

    /**
     * 根据博客id添加浏览量
     * @param blogId
     * @param userId
     * @return
     */
    @Override
    public Result addLookNums(Long blogId, Long userId) {
        Blog blog = getById(blogId);
        if(blog == null){
            return Result.fail("没有博客！");
        }
        if (blog.getUserId().equals(userId)){
            return Result.ok(blog);
        }
        blog.setLook(blog.getLook() + 1);
        updateById(blog);
        return Result.ok(blog);
    }


}

