package com.example.demo.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    /**
     * 配置 RedisConnectionFactory，这里使用 Lettuce 作为客户端。
     */
    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        // 指定 Redis 服务器地址、端口和密码
        RedisStandaloneConfiguration configuration =
                new RedisStandaloneConfiguration("110.41.60.133", 6379);
        configuration.setPassword("root");
        return new LettuceConnectionFactory(configuration);
    }

    /**
     * 定义 RedisTemplate Bean，指定 key 使用 String 序列化器，
     * value 和 hashValue 使用 Jackson2JsonRedisSerializer 序列化器，
     * 并通过注册 JavaTimeModule 支持 Java 8 日期/时间类型。
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);

        // 配置 key 以及 hash key 使用 String 序列化器
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        template.setKeySerializer(stringRedisSerializer);
        template.setHashKeySerializer(stringRedisSerializer);

        // 配置 value 以及 hash value 使用 Jackson2JsonRedisSerializer
        Jackson2JsonRedisSerializer<Object> jacksonSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        // 注册 JavaTimeModule 用于支持 Java 8 日期/时间类型
        objectMapper.registerModule(new JavaTimeModule());
        // 配置反序列化时忽略未知属性
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 禁用将日期序列化为时间戳的方式，保证格式为 ISO8601
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        jacksonSerializer.setObjectMapper(objectMapper);

        template.setValueSerializer(jacksonSerializer);
        template.setHashValueSerializer(jacksonSerializer);

        template.afterPropertiesSet();
        return template;
    }
}