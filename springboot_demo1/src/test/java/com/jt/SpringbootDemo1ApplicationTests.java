package com.jt;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest ////将整个测试类交给spring容器管理
class SpringbootDemo1ApplicationTests {

    //在测试类中可以直接注入spring容器中的任意对象进行测试

    @Test
    void contextLoads() {
    }

}
