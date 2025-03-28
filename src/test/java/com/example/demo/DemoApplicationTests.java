package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class DemoApplicationTests {

    @Test
    void contextLoads() {
    }

    /**
     * 进行加密和解密
     * 进行对比
     */
    @Test
    void EncoderDecoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String root = bCryptPasswordEncoder.encode("root");
        System.out.println(root);
        boolean result = bCryptPasswordEncoder.matches("root", "$2a$10$ix4Bbumtf56ssKnMkB/isuWV4V5RgldyVfnBC4aNImNGvoE1E9ReK");
        System.out.println(result);
    }

}
