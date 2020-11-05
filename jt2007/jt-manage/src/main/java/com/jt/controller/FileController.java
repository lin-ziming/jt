package com.jt.controller;

import com.jt.service.FileService;
import com.jt.vo.ImageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
public class FileController {
    /**
     * url地址:  http://localhost:8091/file
     * 步骤:
     *      1.获取图片的名称
     *      2.准备文件目录
     *      3.拼接文件上传的路径
     *      4.实现文件上传.
     *
     * @param fileImage
     * @return
     */
    @RequestMapping("/file")
    public String file(MultipartFile fileImage) throws IOException {
        //1.获取图片名称
        String name = fileImage.getOriginalFilename();
        //2.准备文件上传目录
        String dir = "D:\\IdeaProject\\pictrue";
        //3.利用对象封装路径
        File dirFile = new File(dir);
        if(!dirFile.exists()) {
            dirFile.mkdirs();
        }
        //4.实现文件上传
        File file = new File(dir+"\\"+name);
        fileImage.transferTo(file);
        return "操作成功！";
    }

    @Autowired
    private FileService fileService;
    /**
     *
     */
    @RequestMapping("/pic/upload")
    public ImageVO upload(MultipartFile uploadFile){
        return fileService.upload(uploadFile);
    }
}
