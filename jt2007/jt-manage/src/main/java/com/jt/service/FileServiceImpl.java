package com.jt.service;

import com.jt.vo.ImageVO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    //定义文件存储的根目录
    private String fileLocalDir = "d:/JT-SOFT/images";
    private static Set<String> typeSet = new HashSet<>();
    static {
        typeSet.add(".jpg");
        typeSet.add(".png");
        typeSet.add(".gif");
    }
    /**
     * 注意事项：
     * 1.校验是否为图片类型  xxx.jpg/png/jpeg/git/...
     * 2.校验是否为恶意程序  宽度、高度
     * 3.采用分目录的方式进行数据的存储  1.hash方式 2.时间单位 yyyy/MM/dd
     * 4.防止文件重名      UUID.jpg
     * @param uploadFile
     * @return
     */
    @Override
    public ImageVO upload(MultipartFile uploadFile) {
        //1.获取图片文件名
        String fileName = uploadFile.getOriginalFilename(); //123.jpg
        //2.获取图片的类型
        int index = fileName.lastIndexOf(".");
        String fileType = fileName.substring(index); //.jpg
        fileType = fileType.toLowerCase();//全部改成小写
        if(!typeSet.contains(fileType)){
            return ImageVO.fail();  //结束任务
        }
        //问题2：防止恶意程序攻击，图片有宽度和高度
        try {
            BufferedImage bufferedImage = ImageIO.read(uploadFile.getInputStream());
            int width = bufferedImage.getWidth();
            int height = bufferedImage.getHeight();
            if (width==0 || height==0 ){
                return ImageVO.fail();
            }
            /**
             * 3.分目录存储，以时间为维度截串 /yyyy/MM/dd/
             */
            String dateDir = new SimpleDateFormat("/yyyy/MM/dd/")
                    .format(new Date());
            String fileDir = fileLocalDir + dateDir;
            File imageFileDir = new File(fileDir);
            if (!imageFileDir.exists()){
                //动态生成文件目录
                imageFileDir.mkdirs();
            }
            /**
             * 4.防止文件重名，动态生成文件名称 uuid.jpg
             * uuid  32位16进制数
             */
            String uuid = UUID.randomUUID().toString().replace("-", "");
            String realFileName = uuid + fileType;  //uuid.jpg
            //  fileDir/uuid.jpg
            File realFile = new File(fileDir+realFileName);
            uploadFile.transferTo(realFile);

            //如果程序一切正常
            String url = "//img14.360buyimg.com/n1/jfs/t1/139289/19/2161/71101/5efff028E5f8e52f1/bf82189580832a4f.jpg";
            return ImageVO.success(url,width,height);
        } catch (IOException e) {
            //将检查异常，转化为运行时异常
            e.printStackTrace();
//            throw new RuntimeException(e);
            return ImageVO.fail();
        }
    }
}
