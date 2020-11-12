package com.jt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.pojo.ItemDesc;
import com.jt.util.ObjectMapperUtil;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class TestObjectMapper {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 1.对象如何转化为JSON串的？
     * 答：1.获取对象的所有getXXX()方法
     *    2.获取的getXXX方法的前缀get去掉，形成json的key=XXX
     *    3.通过getXXX方法的调用获取属性值，形成json的value值
     *    4.将获取到的数据  利用json格式进行拼接{key1:value1,key2:value2,...}
     * 2.JSON如何转化为对象？
     * 答：1.根据class参数类型，利用java的反射机制，实例化对象。
     *    2.解析json格式，区分 key:value
     *    3.进行方法的拼接 setXXX()名称
     *    4.调用对象的setXXX(value) 将数据进行传递
     *    5.最终将所有的json串中的key转化为对象的属性.
     * @throws JsonProcessingException
     */
    @Test
    public void testToJSON() throws JsonProcessingException {
        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setItemId(100L).setItemDesc("测试数据转化")
                .setCreated(new Date()).setUpdated(new Date());
        //1.将对象转化为JSON
        String json = MAPPER.writeValueAsString(itemDesc);
        System.out.println(json);
        //2.将JSON转化为对象 src:需要转化的JSON串，valueType:需要转化为什么对象
        ItemDesc itemDesc2 = MAPPER.readValue(json, ItemDesc.class);
        System.out.println(itemDesc2);

        Object itemDesc3 = ObjectMapperUtil.toObject(json, ItemDesc.class);
        System.out.println(itemDesc3);
    }
}
