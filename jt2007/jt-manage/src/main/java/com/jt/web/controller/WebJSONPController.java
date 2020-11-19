package com.jt.web.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.pojo.ItemDesc;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebJSONPController {
    /**
     * 完成JSONP跨域访问
     * url地址: http://manage.jt.com/web/testJSONP?callback=hello&_=1605584709377
     * 参数:    callback 回调函数的名称
     * 返回值:  callback(json)
     */
    @GetMapping("/web/testJSONP")
    public JSONPObject testJSONP(String callback){
        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setItemId(1000L).setItemDesc("JSONP远程调用！！！");
        JSONPObject jsonpObject = new JSONPObject(callback,itemDesc);
        return jsonpObject;
    }
}
