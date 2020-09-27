package com.offcn;

import org.antlr.stringtemplate.StringTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {UserStartApplication.class})
public class UserApplicationTest {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void demoRedis(){
        stringRedisTemplate.opsForValue().set("test","这是一个小测试.");
    }
}
