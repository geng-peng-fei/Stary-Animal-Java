package com.gpf.animal.common;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;


/**
 * 文件上传
 *
 * @author gengpengfei
 */
@Component
public class FileLoad {
    /**
     * 用户图片上传到用户文件夹下
     *
     * @param file
     * @return
     */
    public static String uploadUserPicture(MultipartFile file) {
        String picName = UUID.randomUUID().toString();
        //获取上传文件得元素得名称
        String fileName = file.getOriginalFilename();
        String substring = fileName.substring(fileName.lastIndexOf("."));
        //上传文件
        try {
            file.transferTo(new File("/Users/gengpengfei/IdeaProjects/animal/src/main/resources/backend/images/user/" + picName + substring));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return picName + substring;
    }

    /**
     * 宠物图片上传到宠物文件夹下
     *
     * @param file
     * @return
     */
    public static String uploadPetPicture(MultipartFile file) {
        String picName = UUID.randomUUID().toString();
        //获取上传文件得元素得名称
        String fileName = file.getOriginalFilename();
        String substring = fileName.substring(fileName.lastIndexOf("."));
        //上传文件
        try {
            file.transferTo(new File("/Users/gengpengfei/IdeaProjects/animal/src/main/resources/backend/images/pet/" + picName + substring));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return picName + substring;
    }

    /**
     * 活动图片上传到活动文件夹下
     *
     * @param file
     * @return
     */
    public static String uploadActivePicture(MultipartFile file) {
        String picName = UUID.randomUUID().toString();
        //获取上传文件得元素得名称
        String fileName = file.getOriginalFilename();
        String substring = fileName.substring(fileName.lastIndexOf("."));
        //上传文件
        try {
            file.transferTo(new File("/Users/gengpengfei/IdeaProjects/animal/src/main/resources/backend/images/active/" + picName + substring));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return picName + substring;
    }

}
