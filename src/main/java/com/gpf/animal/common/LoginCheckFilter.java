//package com.gpf.animal.common;
//
//import com.alibaba.fastjson.JSON;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.util.AntPathMatcher;
//
//import javax.servlet.*;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//
//@Slf4j
///**          过滤器名称                         拦截路径 */
//@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
//public class LoginCheckFilter implements Filter {
//    /**
//     * 路径匹配器，支持通配符
//     */
//    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
//        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
//
//        //获取本次请求的URL
//        String requestURI = httpServletRequest.getRequestURI();
//        log.info("请求路径{}", requestURI);
//        //定义不需要拦截的路径
//        String[] urls = new String[]{
//                "/backend/page/login/login.html",
//                "/admin/login",
//                "/admin/logout",
//                "/backend/**",
//                "/frontend/**",
//                "/varieties/filters",
//                "/pet/list",
//                "/user/code",
//                "/user/login",
//                "/frontend/page/**",
//                "/frontend/api/**",
//                "/frontend/js/**"
//        };
//        //判断本次请求是否需要拦截（AnPathMatcher）
//        boolean validate = validate(urls, requestURI);
//        if (validate) {
//            // 不需要  放行
//            log.info("请求路径{}不需要处理", requestURI);
//            filterChain.doFilter(httpServletRequest, httpServletResponse);
//        } else {
//            //需要拦截 判断是否登录
//            log.info("请求路径{}需要处理", requestURI);
//            //   从浏览器中取值    能取出来说明已经登录            通过admin取值
//            if (httpServletRequest.getSession().getAttribute("admin") != null) {
//                //已登录 放行
//                log.info("用户已登录", httpServletRequest.getSession().getAttribute("admin"));
//                //存储商家用户id配合后续的公共字段填充操作
//                Long employeeId = (Long) httpServletRequest.getSession().getAttribute("admin");
//                BaseContext.setCurrentId(employeeId);
//                //放行
//                filterChain.doFilter(httpServletRequest, httpServletResponse);
//                log.info("请求路径{}放行", requestURI);
//            } else if (httpServletRequest.getSession().getAttribute("user") != null) {
//                //存储用户id配合后续的公共字段填充操作
//                Long userId = (Long) httpServletRequest.getSession().getAttribute("user");
//                BaseContext.setCurrentId(userId);
//                //放行
//                filterChain.doFilter(httpServletRequest, httpServletResponse);
//                log.info("请求路径{}放行", requestURI);
//            } else {//未登录返回数据 配合前端的响应拦截器
//                //   字节输出流将返回的数据响应给页面            将返回的数据转成JSON字符串
//                httpServletResponse.getWriter().write(JSON.toJSONString(Result.fail("NOT LOGIN")));
//                log.info("用户未登录");
//            }
//        }
//    }
//
//    /**
//     * 校验路径是否相等
//     * @param urls
//     * @param requestURI
//     * @return
//     */
//    /**
//     * 匹配的校验路径     需要检验的路径
//     */
//    public boolean validate(String[] urls, String requestURI) {
//        for (String url : urls) {
//            //  判断路径是否匹配
//            boolean match = PATH_MATCHER.match(url, requestURI);
//            if (match) {
//                return true;
//            }
//        }
//        return false;
//    }
//}
//
