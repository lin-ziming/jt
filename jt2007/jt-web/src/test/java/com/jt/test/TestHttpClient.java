package com.jt.test;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;

//@SpringBootTest   //从spring容器中获取bean对象,之后完成测试业务.
public class TestHttpClient {
    /**
     * 1.实例化HttpClient对象
     * 2.定义远程访问的url地址
     * 3.定义请求类型的对象
     * 4.发起http请求,获取响应的结果
     * 5.对返回值结果进行校验.获取真实的数据信息.
     * */
    @Test
    public void testGet() throws IOException {
        HttpClient httpClient = HttpClients.createDefault();
        String url = "http://www.baidu.com";
        HttpGet httpGet = new HttpGet(url);
        HttpResponse httpResponse = httpClient.execute(httpGet);
        //常见结果状态 200 404 406参数异常  500后端服务器异常 504超时  502访问的网址不存在
        //获取状态码
        int status = httpResponse.getStatusLine().getStatusCode();
        if(status==200){
            //获取响应结果
            HttpEntity entity = httpResponse.getEntity();
            String result = EntityUtils.toString(entity,"UTF-8");
            System.out.println(result);
        }
    }

}
