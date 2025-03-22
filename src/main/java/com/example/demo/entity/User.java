package com.example.demo.entity;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.Email;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class User {

    private static final long serialVersionUID = 1L;

    private Long id;  // 主键，一般由数据库自动生成，不一定需要校验

    @NotBlank(message = "用户名不能为空")
    @Size(min = 3, max = 20, message = "用户名长度应在3到20个字符之间")
    private String username;  // 用户名，要求唯一

    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private String email;  // 邮箱，要求唯一

    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 20, message = "密码长度应在6到20个字符之间")
    private String password;  // 密码（建议存储哈希值）

    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^(\\+\\d{1,3}[- ]?)?\\d{10}$", message = "手机号格式不正确")
    private String phoneNumber;  // 手机号

    @NotBlank(message = "用户等级不能为空")
    @Pattern(regexp = "^(normal|vip|vip_plus)$",
            message = "用户等级必须是 normal、vip 或 vip_plus")
    private String userLevel;  // 用户等级 (normal, vip, vip_plus)

    @NotNull(message = "总消费金额不能为空")
    @DecimalMin(value = "0.0", inclusive = true, message = "总消费金额不能为负")
    private BigDecimal totalSpent;  // 总消费金额

    @NotNull(message = "购买次数不能为空")
    @Min(value = 0, message = "购买次数不能为负")
    private Integer purchaseCount;  // 购买次数

    @PastOrPresent(message = "创建时间不能是未来时间")
    private LocalDateTime createdAt;  // 用户创建时间

    @PastOrPresent(message = "最后更新时间不能是未来时间")
    private LocalDateTime updatedAt;  // 用户信息最后更新时间

    @PastOrPresent(message = "最后登录时间不能是未来时间")
    private LocalDateTime lastLogin;  // 最后登录时间
}