# spring-security 权限控制

## 1 Spring Security介绍

- 认证
- 授权

### 认证

认证即系统判断用户的身份是否合法，合法可继续访问，不合法则拒绝访问。常见的用户身份认证方式有：用户名密码登录、二维码登录、手机短信登录、脸部识别认证、指纹认证等方式。

认证是为了保护系统的隐私数据与资源，用户的身份合法才能访问该系统的资源。

### 授权

授权即**认证通过后**，根据用户的权限来控制用户访问资源的过程，拥有资源的访问权限则正常访问，没有权限则拒绝访问。 比如在一些视频网站中，普通用户登录后只有观看免费视频的权限，而VIP用户登录后，网站会给该用户提供观看VIP视频的权限。

认证是为了保证用户身份的合法性，授权则是为了更细粒度的对隐私数据进行划分，控制不同的用户能够访问不同的资源。



**认证**（Authentication）：确认用户的身份，如用户名+密码、指纹、人脸识别等。

**授权**（Authorization）：基于身份确认后，决定用户能访问哪些资源。

它们通常是 **先认证，再授权**：

​	1.	认证通过后，系统才知道用户是谁。

​	2.	再根据用户的权限信息（角色、权限列表）进行授权，决定用户是否可以访问某些资源。



## 2 项目搭建和概览

![image-20250328171528440](https://markdown-pictures-jhx.oss-cn-beijing.aliyuncs.com/picgo/image-20250328171528440.png)



引入依赖：

```
<!--Spring Security-->
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

然后配置好数据源就行了；

这里注意⚠️ ： springboot3 以后 需要更新plus ，否则会有启动报错（已经在issue 5874中解决）

```
            <dependency>
                <groupId>com.baomidou</groupId>
                <artifactId>mybatis-plus-spring-boot3-starter</artifactId>
                <version>3.5.5</version>
            </dependency>
```

然后再template中随便编写一个页面，(在`template`文件夹编写项目主页面`main.html`)

添加页面控制器(会对所有页面进行控制)：

```java
@Controller
public class PageController {
  @RequestMapping("/{page}")
  public String showPage(@PathVariable String page){
    return page;
   }
}
```

Spring Security已经开启了认证功能，不登录无法访问所有资源，该页面就是Spring Security自带的登录页面。



## 3 Spring Security认证_内存认证

这里将@Bean 加到方法中，表示方法的返回值交给spring管理

```java
// Security配置类
@Configuration
public class SecurityConfig {
  // 定义认证逻辑
  @Bean
  public UserDetailsService userDetailsService(){
    // 1.使用内存数据进行认证
    InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();


    // 2.创建两个用户
    UserDetails user1 = User.withUsername("baizhan").password("123").authorities("admin").build();
    UserDetails user2 = User.withUsername("sxt").password("456").authorities("admin").build();


    // 3.将这两个用户添加到内存中
    manager.createUser(user1);
    manager.createUser(user2);


    return manager;
   }


  //密码编码器，不解析密码
  @Bean
  public PasswordEncoder passwordEncoder(){
    return NoOpPasswordEncoder.getInstance();
   }
}

```

此时进行认证测试，我们可以将登录页传来的用户名密码和内存中用户名密码做匹配认证。

PasswordEncoder 是 Spring Security 提供的密码加密接口，主要用于：

​	•	**存储用户密码时进行加密**（防止明文存储，提高安全性）。

​	•	**用户登录时校验密码**（输入密码经过相同的编码过程，与数据库中存储的密文进行比对）。

**密码编码器（PasswordEncoder）的含义**

在 SecurityConfig 配置类中，定义了一个 PasswordEncoder Bean：

```
@Bean
public PasswordEncoder passwordEncoder(){
    return NoOpPasswordEncoder.getInstance();
}
```

**1. 什么是密码编码器？**

PasswordEncoder 是 Spring Security 提供的密码加密接口，主要用于：

​	•	**存储用户密码时进行加密**（防止明文存储，提高安全性）。

​	•	**用户登录时校验密码**（输入密码经过相同的编码过程，与数据库中存储的密文进行比对）。

Spring Security 提供了多个常见的密码编码器，例如：

| **编码器**            | **说明**                                             |
| --------------------- | ---------------------------------------------------- |
| NoOpPasswordEncoder   | **不进行加密**，明文存储密码（不推荐用于生产环境）。 |
| BCryptPasswordEncoder | 使用 **bcrypt** 进行加密，安全性较高（推荐）。       |
| PBKDF2PasswordEncoder | 使用 PBKDF2 进行加密，适合高安全要求的应用。         |
| SCryptPasswordEncoder | 基于 scrypt 算法，加密强度高。                       |
| Argon2PasswordEncoder | 使用 Argon2 算法，是现代推荐的加密方式之一。         |

**2. NoOpPasswordEncoder 的作用**

​	•	**明文存储密码**，不会进行任何加密或哈希处理。

​	•	适用于 **开发调试环境**，方便快速测试用户认证功能。

​	•	**不推荐用于生产环境**，因为明文存储密码有很大安全隐患。

示例：

```
UserDetails user1 = User.withUsername("bootscoder")
    .password("root")  // 这里的密码是明文
    .authorities("admin")
    .build();
```

由于使用了 NoOpPasswordEncoder，在用户登录时，Spring Security 直接对比用户输入的密码与 userDetailsService 中存储的明文密码（“root”），如果匹配则认证成功。

**3. 生产环境应该如何修改？**

在生产环境下，不应该使用 NoOpPasswordEncoder，而应该使用更安全的加密方式，比如 BCryptPasswordEncoder：

```
@Bean
public PasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder();
}
```

并且，创建用户时需要对密码进行加密存储：

```
UserDetails user1 = User.withUsername("bootscoder")
    .password(passwordEncoder().encode("root"))  // 加密存储密码
    .authorities("admin")
    .build();
```

**总结**

​	1.	PasswordEncoder 负责对密码进行加密和验证。

​	2.	NoOpPasswordEncoder **不会加密密码**，适用于测试环境，但**不适用于生产环境**。

​	3.	**生产环境应使用** BCryptPasswordEncoder 或其他安全的密码加密方式，以增强密码安全性。

## 4 Spring Security认证_UserDetailsService接口

在实际项目中，认证逻辑是需要自定义控制的。将`UserDetailsService`接口的实现类放入Spring容器即可自定义认证逻辑。`InMemoryUserDetailsManager`就是`UserDetailsService`接口的一个实现类，它将登录页传来的用户名密码和内存中用户名密码做匹配认证。当然我们也可以自定义`UserDetailsService`接口的实现类。

```java
1public interface UserDetailsService {
2  UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
3}
```

`UserDetailsService`的实现类必须重写`loadUserByUsername`方法，该方法定义了具体的认证逻辑，参数`username`是前端传来的用户名，我们需要根据传来的用户名查询到该用户（一般是从数据库查询），并将查询到的用户封装成一个UserDetails对象，该对象是Spring Security提供的用户对象，包含用户名、密码、权限。Spring Security会根据UserDetails对象中的密码和客户端提供密码进行比较。相同则认证通过，不相同则认证失败。

![image-20220324105909277](https://www.itbaizhan.com/wiki/imgs/image-20220324105909277.png)





## 5 数据库进行认证

1 建立sql

```mysql
CREATE TABLE `admin` (
 `id` int(11) NOT NULL AUTO_INCREMENT,
 `username` varchar(255),
 `password` varchar(255) ,
 `phone` varchar(255) ,
 PRIMARY KEY (`id`)
);


INSERT INTO `users` VALUES (1, 'bootscoder', 'root', '178xxx');
INSERT INTO `users` VALUES (2, 'jiahaoxuan', 'root', '178xxx');

```

2 引入plus 依赖

3 编写dao + 实体类

4 添加MapperScan 或者在Mapper中添加@Mapper注释

5 UserDetailsService 实现类，主要重写 loadUserByUsername 方法；通过queryMapper 来进行条件构造



## 6 Spring Security认证_PasswordEncoder

对密码使用一定方式进行加密

Spring Security要求容器中必须有`PasswordEncoder`实例，之前使用的`NoOpPasswordEncoder`是`PasswordEncoder`的实现类，意思是不解析密码，使用明文密码。

Spring Security官方推荐的密码解析器是`BCryptPasswordEncoder`。接下来我们学习`BCryptPasswordEncoder`的使用。

```
1@SpringBootTest
2public class PasswordEncoderTest {
3  @Test
4  public void testBCryptPasswordEncoder(){
5    //创建加密器
6    PasswordEncoder encoder = new BCryptPasswordEncoder();
7

8    //密码加密
9    String password = encoder.encode("root");
10    System.out.println("加密后:"+password);
11

12    //密码校验
13    /**
14     * 参数1:明文密码
15     * 参数2:加密密码
16     * 返回值：是否校验成功
17     */
18    boolean result = encoder.matches("root","$2a$10$/MImcrpDO21HAP2amayhme8j2SM0YM50/WO8YBH.NC1hEGGSU9ByO");
19    System.out.println(result);
20   }
21}
```

在开发中，我们将`BCryptPasswordEncoder`的实例放入Spring容器即可，并且在用户注册完成后，将密码加密再保存到数据库。

修改我们的配置文件：

```
1//密码编码器
2@Bean
3public PasswordEncoder passwordEncoder() {
4  return new BCryptPasswordEncoder();
5}
```



## 7 Spring Security认证_自定义登录页面

在springSecurity 中指定登陆和失败的页面；

这里需要注意一点， 重写后，必须有自己的controller 来进行接受第一次的login 请求； 否则会报重定向异常（和security 内部的跳转逻辑有关）; 但是如果不进行重写， 其内部会有自己的控制器来进行拦截。

这里的路径要和下面配置的config的 访问和允许资源一致

```java

@Controller
public class LoginController {

    @GetMapping("/login.html")
    public String login() {
        return "login"; // 对应 templates/login.html (如果使用模板引擎)
        // 如果使用静态页面，则不需要此方法
    }

    @GetMapping("/main.html")
    public String main() {
        return "main"; // 对应 templates/main.html
    }

    @GetMapping("/fail.html")
    public String fail() {
        return "fail"; // 对应 templates/fail.html
    }
}
```







```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {
  // Security详细配置
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    // 自定义表单登录
    http.formLogin(form -> {
      form.loginPage("/login.html") // 自定义登录页面
           .usernameParameter("username") // 表单中的用户名项
           .passwordParameter("password") // 表单中的密码项
           .loginProcessingUrl("/login") //登录路径，表单向该路径提交，提交后自动执行UserDetailsService的方法
           .successForwardUrl("/main.html") //登录成功后跳转的路径
           .failureForwardUrl("/fail.html"); //登录失败后跳转的路径
     });


    // 需要认证的资源
    http.authorizeHttpRequests(resp -> {
      resp.requestMatchers("/login.html","/fail.html").permitAll(); // 不需要认证的资源
      resp.requestMatchers("/css/*.css","/js/*.js","/img/**").permitAll(); // 静态资源不需要认证
      resp.anyRequest().authenticated();//其余所有请求都需要认证
     });


    // 关闭csrf防护
    http.csrf(csrf ->{
      csrf.disable();
     });
    return http.build();
   }
}

```





## 8 Spring Security认证_CSRF防护





CSRF防护：

CSRF：跨站请求伪造，通过伪造用户请求访问受信任的站点从而进行非法请求访问，是一种攻击手段。 Spring Security为了防止CSRF攻击，默认开启了CSRF防护，这限制了除了GET请求以外的大多数方法。我们要想正常使用Spring Security需要突破CSRF防护。

解决方法一：关闭CSRF防护：

```
1// 关闭csrf防护
2http.csrf(csrf ->{
3 csrf.disable();
4});
```

解决方法二：突破CSRF防护：

- 这里提醒了我， 好像之前在公司的时候就是这个hh

CSRF为了保证不是其他第三方网站访问，要求访问时携带参数名为_csrf值为令牌，令牌在服务端产生，如果携带的令牌和服务端的令牌匹配成功，则正常访问。

- 这里把value字段改成text 可以看到👀token 的具体值是什么

```
1<form class="form" action="/login" method="post">
2<!-- 在表单中添加令牌隐藏域 -->
3<input type="hidden" th:value="${_csrf.token}" name="_csrf" th:if="${_csrf}"/>
4<input type="text" placeholder="用户名" name="username">
5<input type="password" placeholder="密码" name="password">
6<button type="submit">登录</button>
7</form>
```



## 9 Spring Security认证_认证成功后的处理方式

登录成功后，如果除了跳转页面还需要执行一些自定义代码时，如：统计访问量，推送消息等操作时，可以自定义登录成功处理器。

1. 自定义登录成功处理器

   ```
   1public class MyLoginSuccessHandler implements AuthenticationSuccessHandler {
   2  @Override
   3  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
   4    // 拿到登录用户的信息
   5    UserDetails userDetails = (UserDetails)authentication.getPrincipal();
   6    System.out.println("用户名:"+userDetails.getUsername());
   7    System.out.println("一些操作...");
   8

   9    // 重定向到主页
   10    response.sendRedirect("/main");
   11   }
   12}
   ```

2. 配置登录成功处理器

   ```
   1// 自定义表单登录
   2http.formLogin(form -> {
   3  form.loginPage("/login.html") // 自定义登录页面
   4     .usernameParameter("username") // 表单中的用户名项
   5     .passwordParameter("password") // 表单中的密码项
   6     .loginProcessingUrl("/login") //登录路径，表单向该路径提交，提交后自动执行UserDetailsService的方法
   7    //           .successForwardUrl("/main.html")  //登录成功后跳转的路径
   8     .successHandler(new MyLoginSuccessHandler()) // 登录成功处理器
   9     .failureForwardUrl("/fail.html"); //登录失败后跳转的路径
   10});
   ```





## 10 Spring Security认证_认证失败后的处理方式

登录失败后，如果除了跳转页面还需要执行一些自定义代码时，如：==统计失败次数==等等，可以自定义登录失败处理器。

1. 自定义登录失败处理器

   ```
   1public class MyLoginFailureHandler implements AuthenticationFailureHandler {
   2  @Override
   3  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
   4    System.out.println("记录失败日志...");
   5    response.sendRedirect("/fail.html");
   6   }
   7}
   ```

2. 配置登录失败处理器

   ```
   1// 自定义表单登录
   2http.formLogin(form -> {
   3  form.loginPage("/login.html") // 自定义登录页面
   4     .usernameParameter("username") // 表单中的用户名项
   5     .passwordParameter("password") // 表单中的密码项
   6     .loginProcessingUrl("/login") //登录路径，表单向该路径提交，提交后自动执行UserDetailsService的方法
   7    //           .successForwardUrl("/main.html")  //登录成功后跳转的路径
   8     .successHandler(new MyLoginSuccessHandler()) // 登录成功处理器
   9    //           .failureForwardUrl("/fail.html"); //登录失败后跳转的路径
   10     .failureHandler(new MyLoginFailureHandler()); // 登录失败处理器
   11});
   ```



## 11 退出登陆

在系统中一般都有退出登录的操作。退出登录后，Spring Security可以进行以下操作：

- 清除认证状态
- 销毁HttpSession对象
- 跳转到登录页面

在Spring Security中，退出登录的写法如下：

1. 配置退出登录的路径和退出后跳转的路径

   ```
   1http.logout(logout -> {
   2  logout.logoutUrl("/logout") // 退出登录路径
   3     .logoutSuccessUrl("/login.html") // 退出登录后跳转的路径
   4     .clearAuthentication(true) //清除认证状态，默认为true
   5     .invalidateHttpSession(true); // 销毁HttpSession对象，默认为true
   6});
   ```

2. 在网页中添加退出登录超链接

   ```
   1<!DOCTYPE html>
   2<html>
   3<head>
   4  <meta charset="UTF-8">
   5  <title>主页面</title>
   6</head>
   7<body>
   8<h1>主页面</h1>
   9<a href="/logout">退出登录</a>
   10</body>
   11</html>
   ```



### Spring Security认证_退出成功处理器

我们也可以自定义退出成功处理器，在退出后清理一些数据，写法如下：

1. 自定义退出成功处理器

   ```
   1public class MyLogoutSuccessHandler implements LogoutSuccessHandler {
   2  @Override
   3  public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
   4    System.out.println("清除一些数据...");
   5    response.sendRedirect("/login.html");
   6   }
   7}
   ```

2. 配置退出成功处理器

   ```
   1// 退出登录
   2http.logout(logout ->{
   3  logout.logoutUrl("/logout") // 退出登录路径
   4    //           .logoutSuccessUrl("/login.html") // 退出登录后跳转的路径
   5     .logoutSuccessHandler(new MyLogoutSuccessHandler()) // 自定义退出成功处理器
   6     .clearAuthentication(true) // 清除认证状态，默认为true
   7     .invalidateHttpSession(true); // 销毁HttpSession对象，默认为true
   8});
   ```



## 12 Spring Security认证_RememberMe

Spring Security中Remember Me为“记住我”功能，即下次访问系统时无需重新登录。当使用“记住我”功能登录后，Spring Security会生成一个令牌，令牌一方面保存到数据库中，另一方面生成一个叫`remember-me`的Cookie保存到客户端。之后客户端访问项目时自动携带令牌，不登录即可完成认证。

![image-20220328105039280](https://www.itbaizhan.com/wiki/imgs/image-20220328105039280.png)

1. 编写“记住我”配置类

   ```
   1@Configuration
   2public class RememberMeConfig {
   3  @Autowired
   4  private DataSource dataSource;
   5

   6  // 令牌Repository
   7  @Bean
   8  public PersistentTokenRepository getPersistentTokenRepository() {
   9    // 为Spring Security自带的令牌控制器设置数据源
   10    JdbcTokenRepositoryImpl jdbcTokenRepositoryImpl = new JdbcTokenRepositoryImpl();
   11    jdbcTokenRepositoryImpl.setDataSource(dataSource);
   12    //自动建表，第一次启动时需要，第二次启动时注释掉
   13//     jdbcTokenRepositoryImpl.setCreateTableOnStartup(true);
   14    return jdbcTokenRepositoryImpl;
   15   }
   16}
   ```

2. 修改Security配置类

   ```
   1// 记住我配置
   2http.rememberMe(remember -> {
   3  remember.userDetailsService(userDetailsService) //认证逻辑对象
   4     .tokenRepository(repository) //持久层对象
   5     .tokenValiditySeconds(30); //保存时间，单位：秒
   6});
   ```

3. 在登录页面添加“记住我”复选框

   ```
   1<form class="form" action="/login" method="post">
   2  <input type="text" placeholder="用户名" name="username">
   3  <input type="password" placeholder="密码" name="password">
   4  <input type="checkbox" name="remember-me" value="true"/>记住我</br>
   5  <button type="submit">登录</button>
   6</form>
   ```



在尝试从 session 中获取用户名来显示登录状态。在 Spring Security 中，默认情况下用户信息是存储在 Authentication 对象中，而不是直接放在 session 属性中，所以 `${session.username}` 可能无法正确获取到登录用户的用户名。

这里有两种方法可以解决这个问题：

### 方法 1：使用 Spring Security 的 Thymeleaf 集成（强烈推荐，可以避免很多奇怪的路径问题）

首先添加 Thymeleaf Spring Security 扩展依赖：

```xml
<dependency>
    <groupId>org.thymeleaf.extras</groupId>
    <artifactId>thymeleaf-extras-springsecurity6</artifactId>
    <!-- 如果使用 Spring Security 5.x，则使用 thymeleaf-extras-springsecurity5 -->
</dependency>
```

然后在 HTML 头部添加 Spring Security 命名空间：

```html
<html xmlns:th="http://www.thymeleaf.org"
      xmlns:sec="http://www.thymeleaf.org/extras/spring-security">
```

最后，修改您的代码来使用 Spring Security 的标签：

```html
<div class="container">
    <div class="contact-info">
        <a href="tel:4008001234">📞 400-800-1234</a>
        <a href="mailto:service@example.com">📧 service@example.com</a>
    </div>
    <div class="user-links">
        <!-- 使用 Spring Security 的 isAuthenticated 判断 -->
        <div sec:authorize="isAuthenticated()">
            <!-- 已登录状态 -->
            <span>欢迎, <a th:href="@{/user/profile}" sec:authentication="name">用户名</a></span>
            <form th:action="@{/logout}" method="post" style="display:inline;">
                <button type="submit" style="background:none; border:none; color:blue; text-decoration:underline; cursor:pointer; padding:0;">退出登录</button>
            </form>
        </div>
        <div sec:authorize="!isAuthenticated()">
            <!-- 未登录状态 -->
            <a th:href="@{/login.html}">登录</a>
            <a th:href="@{/register.html}">注册</a>
        </div>
    </div>
</div>
```

### 方法 2：将用户名存入 session

如果您想保持使用 `${session.username}`，您需要在用户登录成功后手动将用户名存入 session。修改您的登录成功处理器：

```java
public class MyLoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                       Authentication authentication) throws IOException {
        // 将用户名存入 session
        request.getSession().setAttribute("username", authentication.getName());

        // 重定向到主页或其他页面
        response.sendRedirect(request.getContextPath() + "/main.html");
    }
}
```

然后您的原始 Thymeleaf 代码就可以正常工作了：

```html
<div class="container">
    <div class="contact-info">
        <a href="tel:4008001234">📞 400-800-1234</a>
        <a href="mailto:service@example.com">📧 service@example.com</a>
    </div>
    <div class="user-links">
        <!-- 使用 session 属性判断 -->
        <div th:if="${session.username != null}">
            <!-- 已登录状态 -->
            <span>欢迎, <a th:href="@{/user/profile}" th:text="${session.username}">用户名</a></span>
            <form th:action="@{/logout}" method="post" style="display:inline;">
                <button type="submit" style="background:none; border:none; color:blue; text-decoration:underline; cursor:pointer; padding:0;">退出登录</button>
            </form>
        </div>
        <div th:unless="${session.username != null}">
            <!-- 未登录状态 -->
            <a th:href="@{/login.html}">登录</a>
            <a th:href="@{/register.html}">注册</a>
        </div>
    </div>
</div>
```

### 关于退出登录

请注意，我已将退出登录链接更改为表单提交。这是因为 Spring Security 默认需要使用 POST 请求处理登出操作，以防止 CSRF 攻击。

如果您确实希望使用链接（GET 请求）进行登出，您需要在 Security 配置中明确允许：

```java
http.logout(logout -> {
    logout.logoutUrl("/logout")
          .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET")) // 允许 GET 请求退出
          .logoutSuccessUrl("/login.html")
          .clearAuthentication(true)
          .invalidateHttpSession(true);
});
```

并且确保链接正确：

```html
<a th:href="@{/logout}">退出登录</a>
```

### 最后的建议

1. 确保所有路径都使用 Thymeleaf 的 `@{...}` 语法，这样会自动考虑上下文路径
2. 使用方法 1（Spring Security Thymeleaf 集成）更加标准和安全
3. 如果使用退出登录按钮，最好使用表单提交而不是链接，以符合 Spring Security 的安全最佳实践



总结一下上面的 ：

1. 如果改写登陆页面等， 必须要自己写controller 进行页面控制
2. 登陆处理请求可以通过security 内部进行处理
3. 前端使用themleaf 的话建议使用， security 和themleaf 的集成依赖； 有效避免路径渲染和编码错误； 并且在获取用户登陆信息等场景🎬 更方便。

优势：

- **CSRF 保护**：自动在表单中添加 CSRF 令牌，无需手动添加
- **获取认证详情**：轻松访问认证对象的各种属性（用户名、角色、权限等）
- **集中式安全管理**：安全规则集中在 Security 配置中，视图层仅负责显示

## 扩展： Spring Security Thymeleaf 集成的三大核心优势详解

### 1. CSRF 保护：自动在表单中添加 CSRF 令牌

#### 使用集成依赖方式

```html
<!-- 使用集成依赖，表单自动添加CSRF令牌 -->
<form th:action="@{/logout}" method="post">
    <button type="submit">退出登录</button>
</form>
```

当使用 Thymeleaf Security 集成时，只要使用 `th:action` 属性，它会自动在表单中添加 CSRF 令牌字段，生成的HTML如下：

```html
<form action="/logout" method="post">
    <input type="hidden" name="_csrf" value="f7d1e3d5-1f71-46be-8f30-e54e9348741c"/>
    <button type="submit">退出登录</button>
</form>
```

#### 不使用集成依赖方式

```html
<!-- 不使用集成依赖，需要手动添加CSRF令牌 -->
<form action="/logout" method="post">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
    <!-- 或者使用标准Thymeleaf方式 -->
    <input type="hidden" th:name="${_csrf.parameterName}" th:value="${_csrf.token}" />
    <button type="submit">退出登录</button>
</form>
```

区别：

- **集成方式更简洁**：无需显式添加CSRF字段
- **降低忘记添加的风险**：减少安全漏洞
- **适应性更强**：如果Spring Security配置中CSRF令牌策略变更，视图层无需修改

### 2. 获取认证详情：轻松访问认证对象属性

#### 使用集成依赖方式

```html
<!-- 直接访问认证对象的各种属性 -->
<p>用户名: <span sec:authentication="name">用户名</span></p>
<p>角色列表: <span sec:authentication="principal.authorities">角色</span></p>
<p>是否记住我登录: <span sec:authentication="authenticated">状态</span></p>

<!-- 还可以访问自定义UserDetails中的属性 -->
<p>电子邮件: <span sec:authentication="principal.email">email@example.com</span></p>
<p>账户创建时间: <span sec:authentication="principal.createdDate">2023-01-01</span></p>
```

#### 不使用集成依赖方式

Controller中需要添加代码

```java
@GetMapping("/profile")
public String profile(Model model) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    model.addAttribute("username", auth.getName());

    if (auth.getPrincipal() instanceof UserDetails) {
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        model.addAttribute("authorities", userDetails.getAuthorities());

        // 如果有自定义UserDetails，还需要单独处理
        if (userDetails instanceof CustomUserDetails) {
            CustomUserDetails customDetails = (CustomUserDetails) userDetails;
            model.addAttribute("email", customDetails.getEmail());
            model.addAttribute("createdDate", customDetails.getCreatedDate());
        }
    }

    return "profile";
}
```

模板中使用

```html
<p>用户名: <span th:text="${username}">用户名</span></p>
<p>角色列表: <span th:text="${authorities}">角色</span></p>
<!-- 其他自定义字段 -->
<p>电子邮件: <span th:text="${email}">email@example.com</span></p>
<p>账户创建时间: <span th:text="${createdDate}">2023-01-01</span></p>
```

区别：

- **减少Controller代码量**：无需在每个Controller中重复添加认证信息
- **动态性更强**：自动获取最新的认证状态，不依赖Controller传递
- **更少的错误可能**：避免传递不完整信息或忘记传递某些属性
- **类型安全**：不需要类型转换，直接访问对象属性

### 3. 集中式安全管理：安全规则集中在配置中

#### 使用集成依赖方式

首先在Security配置中定义权限规则：

```java
@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(authz -> {
        authz.requestMatchers("/admin/**").hasRole("ADMIN");
        authz.requestMatchers("/user/**").hasAnyRole("USER", "ADMIN");
        authz.requestMatchers("/api/**").hasAuthority("API_ACCESS");
        // 其他规则...
    });
    // 其他配置...
    return http.build();
}
```

然后在视图中使用这些规则：

```html
<!-- 自动与Security配置同步，使用相同的规则 -->
<div sec:authorize="hasRole('ADMIN')">
    <h2>管理员面板</h2>
    <!-- 管理员特有内容 -->
</div>

<div sec:authorize="hasAnyRole('USER', 'ADMIN')">
    <h2>用户内容</h2>
    <!-- 用户可见内容 -->
</div>

<div sec:authorize="hasAuthority('API_ACCESS')">
    <h2>API访问权限</h2>
    <!-- API访问相关内容 -->
</div>
```

#### 不使s用集成依赖方式

需要在Controller中复制安全逻辑：

```java
@GetMapping("/dashboard")
public String dashboard(Model model) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();

    boolean isAdmin = authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    boolean isUser = authorities.stream().anyMatch(a ->
        a.getAuthority().equals("ROLE_USER") || a.getAuthority().equals("ROLE_ADMIN"));
    boolean hasApiAccess = authorities.stream().anyMatch(a -> a.getAuthority().equals("API_ACCESS"));

    model.addAttribute("isAdmin", isAdmin);
    model.addAttribute("isUser", isUser);
    model.addAttribute("hasApiAccess", hasApiAccess);

    return "dashboard";
}
```

模板中使用：

```html
<div th:if="${isAdmin}">
    <h2>管理员面板</h2>
    <!-- 管理员特有内容 -->
</div>

<div th:if="${isUser}">
    <h2>用户内容</h2>
    <!-- 用户可见内容 -->
</div>

<div th:if="${hasApiAccess}">
    <h2>API访问权限</h2>
    <!-- API访问相关内容 -->
</div>
```

区别：

- **减少重复代码**：避免在Controller中复制Security配置中的规则
- **保持一致性**：确保视图层和Security配置使用相同的规则
- **易于维护**：当权限规则变更时，只需在Security配置中修改，视图层自动适应
- **降低出错风险**：避免因手动复制规则导致的不一致
- **更精细的控制**：可以在视图中使用与配置相同复杂度的SpEL表达式



## 13 Spring Security认证_会话管理

用户认证通过后，有时我们需要获取用户信息，比如在网站顶部显示：欢迎您，XXX。Spring Security将用户信息保存在会话中，并提供会话管理，我们可以从`SecurityContext`对象中获取用户信息，`SecurityContext`对象与当前线程进行绑定。

获取用户信息的写法如下：

```
1@RestController
2public class MyController {
3  // 获取当前登录用户名
4  @RequestMapping("/users/username")
5  public String getUsername(){
6    // 1.获取会话对象
7    SecurityContext context = SecurityContextHolder.getContext();
8    // 2.获取认证对象
9    Authentication authentication = context.getAuthentication();
10    // 3.获取登录用户信息
11    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
12

13    return userDetails.getUsername();
14   }
15}
```

> 这里主要是为了教学目的演示，其实在我们的项目中已经使用了



## 14 Spring Security认证_会话失效处理

会话过期是指当用户登录网站后，较长一段时间没有与服务器进行交互，将会导致服务器上的用户会话数据(即session)被销毁。此时，当用户再次操作网页时，服务器会进行session校验，浏览器提醒用户session超时。此时相当于用户被动的退出登录。

当会话失效后，我们可以跳转到某个页面，也可以自定义会话失效策略。

1. 配置会话失效时间

   ```
   1server:
   2  servlet:
   3   session:
   4   # 会话过期时间默认是30m过期
   5    timeout: 30s
   ```

2. 配置会话失效后跳转的页面

   ```
   1// 会话配置
   2http.sessionManagement(session ->{
   3  // 会话过期跳转的页面
   4  session.invalidSessionUrl("/login");
   5});
   ```

3. 自定义会话失效策略

   ```
   1public class MyInvalidSessionStrategy implements InvalidSessionStrategy {
   2  @Override
   3  public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
   4    System.out.println("会话过期");
   5    // 会话失效，需要创建新session，否则会由于一直没有session不断的重定向
   6    request.getSession();
   7    response.sendRedirect("/login");
   8   }
   9}
   ```

4. 配置自定义过期策略

   ```
   1// 会话配置
   2http.sessionManagement(session ->{
   3  // 会话失效跳转的页面
   4  //       session.invalidSessionUrl("/login");
   5  // 会话失效处理器
   6  session.invalidSessionStrategy(new MyInvalidSessionStrategy());
   7});
   ```



## 15 Spring Security认证_会话并发控制 -- 有点像单点登录的意思

有时候出于安全的目的，我们会规定在同一个系统中，只允许一个用户在一个终端上登录，这就是对会话的并发控制。会话的并发控制有两种策略：

### 踢掉原有登录用户

我们可以在SecurityConfig配置类中，通过maximumSessions()方法来置单个用户允许同时在线的最大并发会话数，如果没有额外配置，重新登录的会话会踢掉旧的会话。

```
1// 会话配置
2http.sessionManagement(session ->{
3  // 会话失效跳转的页面
4  //       session.invalidSessionUrl("/login");
5  // 会话失效处理器
6  session.invalidSessionStrategy(new MyInvalidSessionStrategy());
7   //最大并发会话数,设置单个用户允许同时在线的最大会话数,重新登录的会话会踢掉旧的会话.
8  session.maximumSessions(1);
9});
```

### 阻止新用户登录

如果我们已经有一个用户登录了，这时候这个相同的账号信息还想再次登录，我们可以阻止新用户登录，配置方式如下：

```
1// 会话配置
2http.sessionManagement(session ->{
3  // 会话失效跳转的页面
4  //       session.invalidSessionUrl("/login");
5  // 会话失效处理器
6  session.invalidSessionStrategy(new MyInvalidSessionStrategy());
7  //当会话达到最大值时，是否保留已经登录的用户，默认为false
8  session.maximumSessions(1).maxSessionsPreventsLogin(true);
9});
```

## 16 Spring Security认证_主动踢人下线

在后台管理中，我们常常需要查看在线的用户，然后管理用户，有时候还需要踢出恶意操作的用户。在Spring Security中提供了Session注册器SessionRegistry，利用Session注册器可以强制用户下线。

1. 在容器中注入SessionRegistry

   ```
   1// 注入SessionRegistry
   2@Bean
   3public SessionRegistry sessionRegistry(){
   4  return new SessionRegistryImpl();
   5}
   ```

2. 编写踢出用户的方法

   ```
   1@Autowired
   2private SessionRegistry sessionRegistry;
   3

   4// 踢出指定用户
   5@GetMapping("/kickOut")
   6public void kickOutUser(String username) {
   7  // 1.获取全部登录用户
   8  List<Object> allPrincipals = sessionRegistry.getAllPrincipals();
   9  // 2.遍历全部登录用户，找到要强制登出的用户
   10  for (Object principal : allPrincipals) {
   11    UserDetails userDetail = (UserDetails) principal;
   12    if (username.equals(userDetail.getUsername())) {
   13      // 3.找到认证用户所有的会话，不包含过期会话
   14      List<SessionInformation> sessions = sessionRegistry.getAllSessions(userDetail, false);
   15      if (null != sessions && !sessions.isEmpty()) {
   16        // 4.遍历该用户的会话,使其立即失效
   17        for (SessionInformation session : sessions) {
   18          session.expireNow();
   19         }
   20       }
   21     }
   22   }
   23}
   ```



## 17 Spring Security授权_RBAC

<img src="https://markdown-pictures-jhx.oss-cn-beijing.aliyuncs.com/picgo/image-20250408181659458.png" alt="image-20250408181659458" style="zoom:33%;" />



授权即认证通过后，系统给用户赋予一定的权限，用户只能根据权限访问系统中的某些资源。RBAC是业界普遍采用的授权方式，它有两种解释：

### Role-Based Access Control

基于角色的访问控制，即按角色进行授权。比如在企业管理系统中，主体角色为总经理可以查询企业运营报表。逻辑为：

```
1if(主体.hasRole("总经理角色")){
2    查询运营报表
3}
```

如果查询企业运营报表的角色变化为总经理和股东，此时就需要修改判断逻辑代码：

```
1if(主体.hasRole("总经理角色") || 主体.hasRole("股东角色")){
2    查询运营报表
3}
```

此时我们可以发现，当需要修改角色的权限时就需要修改授权的相关代码，系统可扩展性差。

### Resource-Based Access Control

基于资源的访问控制，即按资源（或权限）进行授权。比如在企业管理系统中，用户必须 具有查询报表权限才可以查询企业运营报表。逻辑为：

```
1if(主体.hasPermission("查询报表权限")){
2    查询运营报表
3}
```

这样在系统设计时就已经定义好查询报表的权限标识，即使查询报表所需要的角色变化为总经理和股东也不需要修改授权代码，系统可扩展性强。该授权方式更加常用。



### Spring Security授权_权限表设计

用户和权限的关系为多对多，即用户拥有多个权限，权限也属于多个用户，所以建表方式如下：

![image-20220329105021419](https://www.itbaizhan.com/wiki/imgs/image-20220329105021419.png)

这种方式需要指定用户有哪些权限，如：张三有查询工资的权限，即在用户权限中间表中添加一条数据，分别记录张三和查询工资权限ID。但在系统中权限数量可能非常庞大，如果一条一条添加维护数据较为繁琐。所以我们通常的做法是再加一张角色表：

![image-20220329105509797](https://www.itbaizhan.com/wiki/imgs/image-20220329105509797.png)

用户角色，角色权限都是多对多关系，即一个用户拥有多个角色，一个角色属于多个用户；一个角色拥有多个权限，一个权限属于多个角色。这种方式需要指定用户有哪些角色，而角色又有哪些权限。

如：张三拥有总经理的角色，而总经理拥有查询工资、查询报表的权限，这样张三就拥有了查询工资、查询报表的权限。这样管理用户时只需管理少量角色，而管理角色时也只需要管理少量权限即可。接下来我们创建五张表：

```
1CREATE TABLE `users` (
2 `uid` int(11) NOT NULL AUTO_INCREMENT,
3 `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
4 `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
5 `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
6 PRIMARY KEY (`uid`) USING BTREE
7) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;
8INSERT INTO `users` VALUES (1, 'baizhan', '$2a$10$Eqv9PRMl6bPt5BiwgPr2eucgyl.E.xLENt4bvfDvv7DyS5AVPT.U6', '13812345678');
9

10CREATE TABLE `role` (
11 `rid` int(11) NOT NULL AUTO_INCREMENT,
12 `roleName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
13 `roleDesc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
14 PRIMARY KEY (`rid`) USING BTREE
15) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;
16INSERT INTO `role` VALUES (1, '总经理', '管理整个公司');
17INSERT INTO `role` VALUES (2, '股东', '参与公司决策');
18INSERT INTO `role` VALUES (3, '财务', '管理公司资产');
19

20CREATE TABLE `permission` (
21 `pid` int(11) NOT NULL AUTO_INCREMENT,
22 `permissionName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
23 `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
24 PRIMARY KEY (`pid`) USING BTREE
25) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;
26INSERT INTO `permission` VALUES (1, '查询报表', '/reportform/find');
27INSERT INTO `permission` VALUES (2, '查询工资', '/salary/find');
28INSERT INTO `permission` VALUES (3, '查询税务', '/tax/find');
29

30CREATE TABLE `users_role` (
31 `uid` int(255) NOT NULL,
32 `rid` int(11) NOT NULL,
33 PRIMARY KEY (`uid`, `rid`) USING BTREE,
34 INDEX `rid`(`rid`) USING BTREE,
35 CONSTRAINT `users_role_ibfk_1` FOREIGN KEY (`uid`) REFERENCES `users` (`uid`) ON DELETE RESTRICT ON UPDATE RESTRICT,
36 CONSTRAINT `users_role_ibfk_2` FOREIGN KEY (`rid`) REFERENCES `role` (`rid`) ON DELETE RESTRICT ON UPDATE RESTRICT
37) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;
38INSERT INTO `users_role` VALUES (1, 2);
39INSERT INTO `users_role` VALUES (1, 3);
40

41CREATE TABLE `role_permission` (
42 `rid` int(11) NOT NULL,
43 `pid` int(11) NOT NULL,
44 PRIMARY KEY (`rid`, `pid`) USING BTREE,
45 INDEX `pid`(`pid`) USING BTREE,
46 CONSTRAINT `role_permission_ibfk_1` FOREIGN KEY (`rid`) REFERENCES `role` (`rid`) ON DELETE RESTRICT ON UPDATE RESTRICT,
47 CONSTRAINT `role_permission_ibfk_2` FOREIGN KEY (`pid`) REFERENCES `permission` (`pid`) ON DELETE RESTRICT ON UPDATE RESTRICT
48) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;
49INSERT INTO `role_permission` VALUES (1, 1);
50INSERT INTO `role_permission` VALUES (2, 1);
51INSERT INTO `role_permission` VALUES (1, 2);
52INSERT INTO `role_permission` VALUES (3, 2);
53INSERT INTO `role_permission` VALUES (1, 3);
54INSERT INTO `role_permission` VALUES (2, 3);
```







### Spring Security授权_编写查询权限方法

![image-20220330105557967](https://www.itbaizhan.com/wiki/imgs/image-20220330105557967.png)

在认证通过后进行授权，需要根据用户查询用户的权限，写法如下：

1. 编写用户、角色、权限实体类

   ```
   1// 不要命名为User，避免和Spring Security提供的User混淆
   2@Data
   3public class Users {
   4  private Integer uid;
   5  private String username;
   6  private String password;
   7  private String phone;
   8}
   9

   10// 角色
   11@Data
   12public class Role {
   13  private String rid;
   14  private String roleName;
   15  private String roleDesc;
   16}
   17

   18// 权限
   19@Data
   20public class Permission {
   21  private String pid;
   22  private String permissionName;
   23  private String url;
   24}
   ```

2. 编写UserMapper接口

   ```
   1// 根据用户名查询权限
   2List<Permission> findPermissionByUsername(String username);
   ```

3. 在resources目录中编写UsersMapper的映射文件

   ```
   1<?xml version="1.0" encoding="UTF-8"?>
   2<!DOCTYPE mapper
   3    PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
   4    "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
   5<mapper namespace="com.itbaizhan.myspringsecurity.mapper.UsersMapper">
   6  <select id="findPermissionByUsername" parameterType="string" resultType="com.itbaizhan.myspringsecurity.domain.Permission">
   7     SELECT DISTINCT permission.pid,permission.permissionName,permission.url FROM
   8     users
   9     LEFT JOIN users_role on users.uid = users_role.uid
   10     LEFT JOIN role on users_role.rid = role.rid
   11     LEFT JOIN role_permission on role.rid = role_permission.rid
   12     LEFT JOIN permission on role_permission.pid = permission.pid
   13     where username = #{username}
   14  </select>
   15</mapper>
   ```

4. 测试方法

   ```
   1@SpringBootTest
   2public class UsersMapperTest {
   3  @Autowired
   4  private UsersMapper usersMapper;
   5

   6  @Test
   7  public void testFindPermissionByUsername(){
   8    List<Permission> baizhan = usersMapper.findPermissionByUsername("baizhan");
   9    baizhan.forEach(System.out::println);
   10   }
   11}
   ```

5. 修改认证逻辑，认证成功后给用户授权

   ```
   1// 自定义认证逻辑
   2@Override
   3public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
   4  // 1.构造查询条件
   5  QueryWrapper<Users> wrapper = new QueryWrapper<Users>().eq("username", username);
   6  // 2.查询用户
   7  Users users = userMapper.selectOne(wrapper);
   8  if (users == null){
   9    return null;
   10   }
   11  // 3.查询用户权限
   12  List<Permission> permissions = userMapper.findPermissionByUsername(username);
   13  // 4.将自定义权限集合转为Security的权限类型集合
   14  List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
   15  for (Permission permission : permissions) {
   16    grantedAuthorities.add(new SimpleGrantedAuthority(permission.getUrl()));
   17   }
   18  // 5.封装为UserDetails对象
   19  UserDetails userDetails = User.withUsername(users.getUsername())
   20     .password(users.getPassword())
   21     .authorities(grantedAuthorities)
   22     .build();
   23  // 6.返回封装好的UserDetails对象
   24  return userDetails;
   25}
   ```





### Spring Security授权_配置类设置访问控制

在给用户授权后，我们就可以给系统中的资源设置访问控制，即拥有什么权限才能访问什么资源。

1. 编写控制器类，添加控制器方法资源

   ```
   1@RestController
   2public class MyController {
   3  @GetMapping("/reportform/find")
   4  public String findReportForm() {
   5    return "查询报表";
   6   }
   7

   8  @GetMapping("/salary/find")
   9  public String findSalary() {
   10    return "查询工资";
   11   }
   12

   13  @GetMapping("/staff/find")
   14  public String findStaff() {
   15    return "查询员工";
   16   }
   17}
   ```

2. 修改Security配置类

   ```
   1// 权限拦截配置
   2http.authorizeHttpRequests(resp -> {
   3  resp.requestMatchers("/login.html", "/fail.html").permitAll(); // 不需要认证的资源
   4  resp.requestMatchers("/css/*.css", "/js/*.js", "/img/**").permitAll(); // 静态资源不需要认证
   5  resp.requestMatchers("/reportform/find").hasAnyAuthority("/reportform/find");
   6  resp.requestMatchers("/salary/find").hasAnyAuthority("/salary/find");
   7  resp.requestMatchers("/staff/find").hasAnyAuthority("/staff/find");
   8  resp.anyRequest().authenticated();//其余所有请求都需要认证
   9});
   ```

3. 测试访问资源，由于没有权限被拦截访问时会抛出403异常

### Spring Security授权_自定义访问控制逻辑

如果资源数量很多，一条条配置资源需要的权限效率较低。我们可以自定义访问控制逻辑，即访问资源时判断用户是否具有名为该资源URL的权限。

在配置文件中自定义访问控制逻辑

```
1// 权限拦截配置
2http.authorizeHttpRequests(resp -> {
3  resp.requestMatchers("/login.html", "/fail.html").permitAll(); // 不需要认证的资源
4  resp.requestMatchers("/css/*.css", "/js/*.js", "/img/**").permitAll(); // 静态资源不需要认证
5  //       resp.requestMatchers("/reportform/find").hasAnyAuthority("/reportform/find");
6  //       resp.requestMatchers("/salary/find").hasAnyAuthority("/salary/find");
7  //       resp.requestMatchers("/staff/find").hasAnyAuthority("/staff/find");
8  //       resp.anyRequest().authenticated();//其余所有请求都需要认证
9  /**
10       * access的参数是一个函数式接口
11       * 方法的第一个参数代表认证对象，可以获取认证用户的权限集合
12       * 方法的第二个参数代表网络环境，可以获取当前请求的路径
13       */
14  resp.anyRequest().access((authentication, requestContext) -> {
15    // 获取认证的用户权限
16    Collection<? extends GrantedAuthority> authorities = authentication.get().getAuthorities();
17    // 获取请求的URL路径
18    String uri = requestContext.getRequest().getRequestURI();
19    // 将URL路径封装为权限对象
20    SimpleGrantedAuthority authority = new SimpleGrantedAuthority(uri);
21    // 判断用户的权限集合是否包含请求的URL权限对象
22    boolean result = authorities.contains(authority);
23    return new AuthorizationDecision(result);
24   });
25});
```

### Spring Security授权_注解设置访问控制

除了配置类，在SpringSecurity中提供了设置访问控制的注解。注解默认是不可用的，需要开启后使用。

@**PreAuthorize**

该注解可以在方法执行前判断用户是否具有权限

1. 在启动类开启注解使用

   ```
   1@SpringBootApplication
   2@MapperScan("com.itbaizhan.mysecurity.mapper")
   3@EnableMethodSecurity
   4public class MysecurityApplication {
   5  public static void main(String[] args) {
   6    SpringApplication.run(MysecurityApplication.class, args);
   7   }
   8}
   ```

2. 在控制器方法上添加注解

   ```
   1@PreAuthorize("hasAnyAuthority('/reportform/find')")
   2@GetMapping("/reportform/find")
   3public String findReportForm() {
   4  return "查询报表";
   5}
   ```

### Spring Security授权_在前端进行访问控制

SpringSecurity可以在一些视图技术中进行控制显示效果。例如Thymeleaf中，只有用户拥有某些权限才会展示一些菜单。

1. 在pom中引入Spring Security和Thymeleaf的整合依赖

   ```
   1<!--Spring Security整合Thymeleaf-->
   2<dependency>
   3  <groupId>org.thymeleaf.extras</groupId>
   4  <artifactId>thymeleaf-extras-springsecurity6</artifactId>
   5</dependency>
   ```

2. 在Thymeleaf中使用Security标签，控制前端的显示内容

   ```
   1<!DOCTYPE html>
   2<html xmlns:th="http://www.thymeleaf.org"
   3   xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity5">
   4<head>
   5  <meta charset="UTF-8">
   6  <title>主页面</title>
   7</head>
   8<body>
   9<h1>主页面</h1>
   10<ul>
   11  <li sec:authorize="hasAnyAuthority('/reportform/find')"><a href="/reportform/find">查询报表</a></li>
   12  <li sec:authorize="hasAnyAuthority('/salary/find')"><a href="/salary/find">查询工资</a></li>
   13  <li sec:authorize="hasAnyAuthority('/staff/find')"><a href="/staff/find">查询员工</a></li>
   14</ul>
   15<a href="/logout">退出登录</a>
   16</body>
   17</html>
   ```

3. 这样面对不同权限的用户，前端可以显示不同的菜单

## Spring Security授权_403处理方案

使用Spring Security时经常会看见403（无权限），这样的页面很不友好，我们可以自定义403异常处理方案：

1. 编写权限不足页面`noPermission.html`

   ```
   1<!DOCTYPE html>
   2<html lang="en">
   3<head>
   4  <meta charset="UTF-8">
   5  <title>权限不足</title>
   6</head>
   7<body>
   8<h1>您的权限不足，请联系管理员！</h1>
   9</body>
   10</html>
   ```

2. 编写权限不足处理类

   ```
   1public class MyAccessDeniedHandler implements AccessDeniedHandler {
   2  @Override
   3  public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
   4    response.sendRedirect("/noPermission.html");
   5   }
   6}
   ```

3. 在Spring Security配置文件中配置异常处理

   ```
   1//异常处理
   2http.exceptionHandling().
   3        accessDeniedHandler(new MyAccessDeniedHandler());
   ```

