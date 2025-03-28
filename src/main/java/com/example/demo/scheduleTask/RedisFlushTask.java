//package com.example.demo.scheduleTask;
//
//import com.example.demo.entity.User;
//import com.example.demo.mapper.UserMapper;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//import java.util.List;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//import com.fasterxml.jackson.annotation.JsonInclude;
//
//@Component
//public class RedisFlushTask {
//    private final static Logger logger = LoggerFactory.getLogger(RedisFlushTask.class);
//
//    @Autowired
//    private UserMapper userMapper;
//
//    @Autowired
//    private RedisTemplate<String, Object> redisTemplate;
//
//    private ObjectMapper objectMapper;
//
//    public RedisFlushTask() {
//        objectMapper = new ObjectMapper();
//        // Register JavaTimeModule to handle Java 8 date/time types like LocalDateTime
//        objectMapper.registerModule(new JavaTimeModule());
//        // Optional: configure how null values should be handled
//        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
//    }
//
/////**
////* 每10秒触发一次
////* cron 表达式说明：
////* 1. 秒：*/10（每10秒）
////* 2. 分：*（每分钟）
////* 3. 时：*（每小时）
////* 4. 日：*（每天）
////* 5. 月：*（每月）
////* 6. 周：?（忽略）
////*/
//    @Scheduled(cron = "*/10 * * * * ?")
//    public void flushUsersToRedis() {
//        // 从 MySQL 获取所有用户数据
//        List<User> userList = userMapper.findAll();
//
//        // 示例1：将所有用户数据以单个 key 存储（建议以 JSON 序列化）
//        String allUsersKey = "users";
//        redisTemplate.opsForValue().set(allUsersKey, userList);
//        logger.info("已存储所有用户数据到 Redis key: {}", allUsersKey);
//
//        // 示例2：将每个用户数据单独存储，以 user:id 作为 key
//        for (User user : userList) {
//            String singleUserKey = "user:" + user.getId();
//            redisTemplate.opsForValue().set(singleUserKey, user);
//            logger.info("已存储用户数据到 Redis key: {}，用户ID: {}", singleUserKey, user.getId());
//        }
//
//        // 新增存储方式：以 hash 的方式存储用户数据，按用户等级分组，
//        // 并通过用户 id 的 hashCode 取余以实现各个分区数据均衡存放
//        final int PARTITION_COUNT = 3;  // 测试用，固定为3个分区
//
//        try {
//            for (User user : userList) {
//                // 根据用户等级进行分组
//                String level = user.getUserLevel();
//
//                // 使用用户 id 的 hashCode 取模计算分区号（注意取绝对值防止负数）
//                int partition = Math.abs(user.getId().hashCode() % PARTITION_COUNT);
//
//                // 构造 Redis hash 的 key，例如 "level:normal:3" 或 "level:vip_plus:7"
//                String hashKey = "level:" + level + ":" + partition;
//
//                // 将用户对象序列化为JSON字符串，包含除ID外的所有字段
//                String userJson = objectMapper.writeValueAsString(user);
//
//                // 以用户 id 为 field，将用户数据存入对应的 Redis hash 中
//                redisTemplate.opsForHash().put(hashKey, user.getId().toString(), userJson);
//                logger.info("已存储用户数据到 Redis hash key: {}， field: {}， 用户ID: {}",
//                        hashKey, user.getId().toString(), user.getId());
//            }
//        } catch (Exception e) {
//            logger.error("将用户数据存入Redis时发生错误", e);
//        }
//
//        logger.info("成功将 MySQL 中的用户数据存入 Redis");
//    }
//}