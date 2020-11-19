package com.jt.service;

import com.jt.pojo.User;
import com.jt.util.ObjectMapperUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public List<User> testHttpClient() {
        List<User> userList = new ArrayList<>();
        //由jt-web服务器去链接jt-sso的服务器
        String url = "http://sso.jt.com/user/testHttpClient";
        HttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        try {
            HttpResponse httpResponse = httpClient.execute(httpGet);
            if(httpResponse.getStatusLine().getStatusCode()==200){
                HttpEntity httpEntity = httpResponse.getEntity();
                String result = EntityUtils.toString(httpEntity,"UTF-8");
                userList = ObjectMapperUtil.toObject(result,userList.getClass());
               /* for (LinkedHashMap<String,Object> map : userList){
                    User userTemp = new User();
                    userTemp.setId( Long.parseLong(map.get("id")+""));
                    userTemp.setUsername((String)map.get("username"));
                    userTemp.setPassword((String)map.get("password"));
                    userTemp.setPhone((String)map.get("phone"));
                    userTemp.setEmail((String)map.get("phone"));
                    userList2.add(userTemp);
                }*/
                userList = ObjectMapperUtil.linkedMapTypeListToObjectList(userList,User.class);
                userList.forEach((user)->{
                    user.setEmail(user.getPhone());
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return userList;
    }
}
