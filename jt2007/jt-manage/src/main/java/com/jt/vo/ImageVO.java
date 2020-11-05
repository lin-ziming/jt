package com.jt.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 *  {"error":0,"url":"图片的保存路径","width":图片的宽度,"height":图片的高度}
 * 参数说明： 0代表是一张图片，如果是0，前台才可以解析并显示。1代表不是图片，
 * 不显示如果不设置宽度和高度，则默认用图片原来的大小，所以不用设置
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ImageVO {
    private Integer error;  //错误信息  0正常  1失败
    private String url;     //图片网址
    private Integer width;  //宽度
    private Integer height; //高度

    //准备API  简化用户操作
    public static ImageVO fail(){
        return new ImageVO(1,null,null,null);
    }
    public static ImageVO success(String url,Integer width,Integer height){
        return new ImageVO(0,url,width,height);
    }
}
