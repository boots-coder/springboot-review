//package com.example.demo.controller.restful;
//
//import com.example.demo.entity.User;
//import jakarta.validation.Valid;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//
//import java.time.LocalDateTime;
//import java.util.Collection;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.atomic.AtomicLong;
//import java.util.stream.Collectors;
//
//
//@RestController
//@RequestMapping("/usersTest")
//public class UserControllerTest {
//
//    // 模拟数据库存储用户信息
//    private Map<Long, User> userMap = new ConcurrentHashMap<>();
//    // 用于生成自增的 ID
//    private AtomicLong idGenerator = new AtomicLong(1);
//
//    /**
//     * 创建用户 (Create)
//     * URL: POST /users
//     * 请求体: JSON 格式的 User 对象，同时进行校验。
//     * 注意：忽略客户端传入的 id 与时间字段，统一由服务器生成。
//     */
//    @PostMapping
//    public ResponseEntity<?> createUser(@Valid @RequestBody User user, BindingResult result) {
//        // 如果校验失败，则返回详细错误信息（包括字段名）
//        if (result.hasErrors()) {
//            List<String> errors = result.getFieldErrors()
//                    .stream()
//                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
//                    .collect(Collectors.toList());
//            return ResponseEntity.badRequest().body(errors);
//        }
//        // 忽略客户端可能传入的 id、createdAt、updatedAt、lastLogin
//        long id = idGenerator.getAndIncrement();
//        user.setId(id);
//        user.setCreatedAt(LocalDateTime.now());
//        user.setUpdatedAt(null);
//        user.setLastLogin(null);
//
//        userMap.put(id, user);
//        return ResponseEntity.ok(user);
//    }
//
//    /**
//     * 获取所有用户 (Read all)
//     * URL: GET /users
//     */
//    @GetMapping
//    public Collection<User> getAllUsers() {
//        return userMap.values();
//    }
//
//    /**
//     * 根据 ID 获取用户 (Read one)
//     * URL: GET /users/{id}
//     */
//    @GetMapping("/{id}")
//    public ResponseEntity<?> getUser(@PathVariable Long id) {
//        User user = userMap.get(id);
//        if (user == null) {
//            return ResponseEntity
//                    .status(HttpStatus.NOT_FOUND)
//                    .body("User not found with id: " + id);
//        }
//        return ResponseEntity.ok(user);
//    }
//
//    /**
//     * 更新用户 (Update)
//     * URL: PUT /users/{id}
//     * 请求体: JSON 格式的 User 对象，同时进行校验。
//     * 注意：保持原有创建时间不变，更新时间由系统设置。
//     */
//    @PutMapping("/{id}")
//    public ResponseEntity<?> updateUser(@PathVariable Long id, @Valid @RequestBody User user, BindingResult result) {
//        // 如果校验失败，则返回详细错误信息
//        if (result.hasErrors()) {
//            List<String> errors = result.getFieldErrors()
//                    .stream()
//                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
//                    .collect(Collectors.toList());
//            return ResponseEntity.badRequest().body(errors);
//        }
//
//        User existingUser = userMap.get(id);
//        if (existingUser == null) {
//            return ResponseEntity
//                    .status(HttpStatus.NOT_FOUND)
//                    .body("User not found with id: " + id);
//        }
//        // 保留原有创建时间，并更新其他信息，由系统设置更新时间
//        user.setId(id);
//        user.setCreatedAt(existingUser.getCreatedAt());
//        user.setUpdatedAt(LocalDateTime.now());
//        // 若需要限制对 lastLogin 的直接修改，也可以在这里忽略或者由其他逻辑处理
//        userMap.put(id, user);
//        return ResponseEntity.ok(user);
//    }
//
//    /**
//     * 删除用户 (Delete)
//     * URL: DELETE /users/{id}
//     */
//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
//        User removedUser = userMap.remove(id);
//        if (removedUser == null) {
//            return ResponseEntity
//                    .status(HttpStatus.NOT_FOUND)
//                    .body("User not found with id: " + id);
//        }
//        return ResponseEntity.ok("User deleted successfully with id: " + id);
//    }
//}