package com.jt;

import com.jt.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringbootDemo2ApplicationTests {

    @Test
    void contextLoads() {
        User user = new User();
        user.setId(100)
                .setName("ssss");
    }

}
