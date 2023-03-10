package com.gpf.animal.controller;

import com.gpf.animal.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * 文件上传和下载
 *
 * @author gengpengfei
 */
@RestController
@RequestMapping("/common")
@Slf4j
public class CommonController {

    private static final String userPath = "/Users/gengpengfei/IdeaProjects/animal/src/main/resources/backend/images/user/";
    private static final String petPath = "/Users/gengpengfei/IdeaProjects/animal/src/main/resources/backend/images/pet/";
    private static final String activePath = "/Users/gengpengfei/IdeaProjects/animal/src/main/resources/backend/images/active/";
    String basePath = "";

    /**
     * 文件上传
     *
     * @param file
     * @return
     */
    @PostMapping("/upload/{type}")
    public Result upload(MultipartFile file, @PathVariable String type) {
        //file是一个临时文件，需要转存到指定位置，否则本次请求完成后临时文件会删除
        log.info(file.toString());
        //                                  获取原始文件名
        String originalFilename = file.getOriginalFilename();
        //                                  截取                       从最后一个.开始截
        assert originalFilename != null;
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        //防止文件名称重复造成文件覆盖  使用UUID重新生成文件名
        String fileName = UUID.randomUUID().toString() + suffix;
        //   创建目录对象
        imagePath(type);
        File dir = new File(basePath);
        //判断当前目录是否存在
        if (!dir.exists()) {
            //目录不存在，需要创建
            dir.mkdirs();
        }
        try {
            //将临时文件转存到                   指定位置
            file.transferTo(new File(basePath + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Result.ok(fileName);
    }

    /**
     * 文件下载
     *
     * @param name
     * @param response
     * @param type
     */
    @GetMapping("/download/{type}")
    public void download(@RequestParam String name, HttpServletResponse response, @PathVariable String type) {
        imagePath(type);
        try {
            //输入流，通过输入流读取文件内容
            FileInputStream fileInputStream = new FileInputStream(basePath + name);
            //输出流，通过输出流将文件写回浏览器     图片回显
            ServletOutputStream outputStream = response.getOutputStream();
            //  响应       类型    ：    图片文件
            response.setContentType("image/jpeg");
            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = fileInputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
                outputStream.flush();
            }
            //关闭资源
            outputStream.close();
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 文件下载
     *
     * @param name
     * @param response
     */
    @GetMapping("/download")
    public void download(@RequestParam String name, HttpServletResponse response) {
        try {
            //输入流，通过输入流读取文件内容
            FileInputStream fileInputStream = new FileInputStream(basePath + name);
            //输出流，通过输出流将文件写回浏览器     图片回显
            ServletOutputStream outputStream = response.getOutputStream();
            //  响应       类型    ：    图片文件
            response.setContentType("image/jpeg");
            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = fileInputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
                outputStream.flush();
            }
            //关闭资源
            outputStream.close();
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void imagePath(String type) {
        switch (type) {
            case "user":
                basePath = userPath;
                break;
            case "pet":
                basePath = petPath;
                break;
            case "active":
                basePath = activePath;
                break;
            default:
                throw new RuntimeException("Unknown type");
        }
    }
}
