package com.example.demo.scheduleTask;

import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class RedisFlushTask {
    private final static Logger logger = LoggerFactory.getLogger(RedisFlushTask.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    /**
     * 每天0点触发（0秒 0分 0时，每天）
     * cron 表达式说明：
     *    1. 秒：0
     *    2. 分：0
     *    3. 时：0
     *    4. 日：每一天 *
     *    5. 月：每个月 *
     *    6. 周：不指定（用 ? 表示忽略）
     */
    @Scheduled(cron = "1 * * * * ?")
    public void flushUsersToRedis() {
        // 从 MySQL 获取所有用户数据
        List<User> userList = userMapper.findAll();

        // 示例1：将所有用户数据以单个 key 存储（推荐以 JSON 序列化）
        redisTemplate.opsForValue().set("users", userList);

        // 示例2：将每个用户数据单独存储，以 user:id 作为 key
        for (User user : userList) {
            String key = "user:" + user.getId();
            redisTemplate.opsForValue().set(key, user);
        }
        logger.info("成功将 MySQL 中的用户数据存入 Redis");
    }
}
