package com.example.demo.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class User {
    private static final long serialVersionUID = 1L;

    private Long id;                  // 主键
    private String username;          // 用户名，唯一
    private String email;             // 邮箱，唯一
    private String password;          // 密码（建议存储哈希值）
    private String phoneNumber;       // 手机号

    private String userLevel;         // 用户等级 (normal, vip, vip_plus)
    private BigDecimal totalSpent;    // 总消费金额
    private Integer purchaseCount;    // 购买次数

    private LocalDateTime createdAt;  // 用户创建时间
    private LocalDateTime updatedAt;  // 用户信息最后更新时间
    private LocalDateTime lastLogin;  // 最后登录时间
}