package com.example.demo.mapper;

import com.example.demo.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest// 可以在测试代码时候加载容器，必须加
class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    void findAll() {
        for (User user : userMapper.findAll()) {
            System.out.println(user);
        }
    }
}