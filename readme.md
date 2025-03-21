# springboot 和springboot3

![image-20250321145204538](https://markdown-pictures-jhx.oss-cn-beijing.aliyuncs.com/picgo/image-20250321145204538.png)



## **1 Spring框架的缺点**

- 特点（优点）：组件代码，轻量级代码开发 -- 思想内核 -- 面向切面和控制反转（用哪一块组件，就引入哪一个组件就可以）
- 重量级配置代码 -- 配置文件多， 以mybatis 为例， 数据源对象-- 分装sqlSessoionFactory -- spring封装后的sqlSession -- 对象自动扫描持久岑接口，为接口创建代理对象
- 依赖管理-- 依赖版本 -- 导致不兼容-- 当spring升级以后依赖坐标也需要升级（例如spring5 以上， 需要使用junit 4.12 以上的版本）

spring -- 事务 和MVC 的相关配置 -- 默认配置



## 2 **什么是SpringBoot**

改善和优化spring -- 底层还是spring

- 配置简单
- 引入以来简单
- 提供大型项目的肺功能特性-- 嵌入服务器（tomcat）

！！！ ==约定大于配置==

两大核心功能：

- 自动配置-- 可以修改默认值-- 满足业务需求-
- 起步依赖  -- mybatis -- mybatis-spring 整合包-- 还需要调配版本； spring 的依赖基于功能， 使用mybatis -- 只需要引入一个起步依赖即可；



3 **springboot 3 ** -- 第一次重大修订

版本支持要求

![image-20250321150944164](https://markdown-pictures-jhx.oss-cn-beijing.aliyuncs.com/picgo/image-20250321150944164.png)



## 3 SpringBoot入门-- 搭建一个springboot项目

- **通过官网搭建项目**
- 使用idea
- 使用maven

使用官网搭建

https://start.spring.io/

这里注意一下： 国外大多数使用Gradile -- 进行项目管理

![image-20250321152141082](https://markdown-pictures-jhx.oss-cn-beijing.aliyuncs.com/picgo/image-20250321152141082.png)



使用idea 构建项目 -- 相似

项目结构介绍：

SpringBoot默认在`static`目录中存放静态资源，如css、js、图片等等。而`templates`中存放模板引擎，如jsp、thymeleaf等。

但是springboot 不推荐使用jsp -- 希望使用内部渲染引擎-- thymeleaf

点击启动类 -- 即可使用内部tomcat

修改端口号：

server.port = 8888



pom**文件介绍** ：

所有springboot 都有一个父项目：

```pom
<parent>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-parent</artifactId>
  <version>3.1.2</version>
  <relativePath/> <!-- lookup parent from repository -->
</parent>
```



下面是常用的起步依赖：

其中 starter - web 整合了所有的web相关的依赖， 下面test 相当于junit 的起步依赖

```pom
<dependencies>
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
  </dependency>
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
  </dependency>
</dependencies>

```

打包的插件-- springboot 自带一个maven的插件； 用于直接将项目打包为jar包 -- 无需依赖tomcat ，直接使用jdk环境即可

```
    <build>
  <plugins>
    <plugin>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-maven-plugin</artifactId>
    </plugin>
  </plugins>
</build>
```



SpringBoot入门_通过Maven搭建项目

> 最麻烦的一种方式

不管是通过官网，还是通过脚手架搭建项目，都需要连接SpringBoot官网，但国内与SpringBoot官网连接并不稳定，此时我们也可以使用Maven手动搭建SpringBoot项目：

1. 创建新项目

   ![image-20230803113459684](https://www.itbaizhan.com/wiki/imgs/image-20230803113459684.png)

2. 在pom中添加项目的父工程、起步依赖、插件、依赖和插件的下载地址

   ```
   1<!-- 父工程 -->
   2<parent>
   3  <groupId>org.springframework.boot</groupId>
   4  <artifactId>spring-boot-starter-parent</artifactId>
   5  <version>3.1.2</version>
   6</parent>
   7<!-- 起步依赖 -->
   8<dependencies>
   9  <dependency>
   10    <groupId>org.springframework.boot</groupId>
   11    <artifactId>spring-boot-starter-web</artifactId>
   12  </dependency>
   13
   
   14  <dependency>
   15    <groupId>org.springframework.boot</groupId>
   16    <artifactId>spring-boot-starter-test</artifactId>
   17    <scope>test</scope>
   18  </dependency>
   19</dependencies>
   20
   
   21<!-- 插件 -->
   22<build>
   23  <plugins>
   24    <plugin>
   25      <groupId>org.springframework.boot</groupId>
   26      <artifactId>spring-boot-maven-plugin</artifactId>
   27    </plugin>
   28  </plugins>
   29</build>
   ```

3. 编写启动类

   ```
   1@SpringBootApplication
   2public class SpringBootApp {
   3  public static void main(String[] args) {
   4    SpringApplication.run(SpringBootApp.class, args);
   5   }
   6}
   ```

4. 编写配置文件application.properties

   ```
   1#日志格式
   2logging.pattern.console=%d{MM/dd HH:mm:ss.SSS} %clr(%-5level) ---  [%-15thread] %cyan(%-50logger{50}):%msg%n
   3#端口号
   4server.port=8889
   ```

5. 运行启动类主方法，启动项目







## 4 编写java代码

之前搭建的SpringBoot项目已经都整合了SpringMVC，我们编写一个控制器进行测试：

```java
@Controller
public class MyController {
  @RequestMapping("/hello")
  @ResponseBody
  public String hello(){
    System.out.println("hello springboot!");
    return "hello springboot!";
   }
}

```

之前spring 中必须配置扫描 controller 注解； 现在不需要，默认约定了， 在启动类中，默认的扫描位置： 同包或者同级别包下的注解

启动类在启动时会做注解扫描(@Controller、@Service、@Repository......)，扫描位置为同包或者同级包下的注解，所以我们要在启动类同级或同级包下编写代码

**✅ 你写的代码**

```
@Controller
public class MyController {

  @RequestMapping("/hello")
  @ResponseBody
  public String hello(){
    System.out.println("hello springboot!");
    return "hello springboot!";
  }
}
```

**✅ 注解详解 + 补充**

**🔹 @Controller**

**作用：**

标记当前类是一个 Spring MVC 的控制器类。**用于处理 Web 请求**，并由 DispatcherServlet 统一分发请求给该类。

**特点：**

​	•	默认返回的是 **视图名（页面名）**，而不是数据。

​	•	通常与 @RequestMapping、@ResponseBody 等组合使用。

**相关补充：**

​	•	@Controller 是传统 Spring MVC 的风格。

​	•	如果你想让控制器返回 **纯 JSON 或文本数据**，更推荐使用：

```
@RestController
```

它相当于 @Controller + @ResponseBody 的组合注解，适用于构建 API。

**✅ 推荐写法（现代 Spring Boot 风格）**

```
@RestController
public class MyController {

  @GetMapping("/hello")
  public String hello() {
    System.out.println("hello springboot!");
    return "hello springboot!";
  }
}
```

**✅ 额外补充知识**

| **注解**        | **描述**                                         |
| --------------- | ------------------------------------------------ |
| @RestController | 等价于 @Controller + @ResponseBody，用于开发 API |
| @RequestMapping | 支持所有请求方法，可细粒度配置路径、请求方法等   |
| @GetMapping     | 简化 @RequestMapping(method = RequestMethod.GET) |
| @PostMapping    | 简化 POST 请求的映射                             |
| @RequestParam   | 获取请求参数，如 /hello?name=Tom                 |
| @PathVariable   | 获取路径变量，如 /user/{id}                      |
| @RequestBody    | 接收 JSON 请求体并自动转换为 Java 对象           |
| @ResponseStatus | 设置响应状态码，例如 404、201 等                 |





## 5 yaml 配置详解

YAML文件的基本要求如下：

1. 大小写敏感
2. 使用缩进代表层级关系
3. 同级配置必须对齐，上下级配置必须缩进，但缩进的空格数不限。
4. 相同的部分只出现一次
5. 冒号和值之间必须要有空格



SpringBoot默认会从resources目录下加载`application.properties`或`application.yml`文件。

官网查询：

https://docs.spring.io/spring-boot/docs/3.1.2/reference/htmlsingle/#appendix.application-properties

以server port 为例

![image-20250321191358747](https://markdown-pictures-jhx.oss-cn-beijing.aliyuncs.com/picgo/image-20250321191358747.png)







### Self- 配置简单数据

- 语法:

  ```
  数据名: 值
  ```

- 示例代码：

  ```
  email: 3552618816@qq.com
  ```

### Self - 配置对象数据

- 语法：

  ```
  1对象:
  2    属性名1: 属性值
  3    属性名2: 属性值
  4    
  5# 或者
  6对象: {属性名1: 属性值,属性名2: 属性值}
  ```

- 示例代码：

  ```
  1# 邮箱1
  2my1:
  3  email: itbaizhan@sxt.com
  4  password: itbaizhan
  5
  
  6# 邮箱2
  7my2: {email: itbaizhan1@sxt.com,password: itbaizhan}
  ```

### Self - 配置集合数据

- 语法

  ```
  1集合:
  2    - 值1
  3    - 值2
  4    
  5# 或者
  6集合: [值1,值2]
  ```

- 示例代码

  ```
  1# 城市
  2city1:
  3  - beijing
  4  - shanghai
  5  - tianjin
  6  - chongqing
  7
  
  8city2: [beijing,tianjin,shanghai,chongqing]
  9
  
  10# 集合中的元素是对象
  11sxt:
  12  - address: beijing
  13   mobile: 13888888888
  14   email: bjsxt@sxt.com
  15  - address: shanghai
  16   mobile: 13777777777
  17   email: shsxt@sxt.com
  18  - address: guangzhou
  19   mobile: 13666666666
  20   email: gzsxt@sxt.com
  ```

> 注意：值与之前的 - 之间存在一个空格

## 6 YAML文件_@Value读取配置文件和改进方案

> **松散绑定（Relaxed Binding）**
>
> Spring Boot 支持不同格式的键名（如 myConfig、my-config、MY_CONFIG 等），上面的 YAML 与 Java 类的属性名称会自动匹配。
>
> **内部静态类的好处**
>
> 使用内部静态类可以使配置类更加结构化，不必为每一层单独创建一个文件，便于管理复杂的嵌套配置。
>
> **配置绑定启用**
>
> 通过 @Component 或在启动类上使用 @EnableConfigurationProperties(MyConfigProperties.class) 即可启用自动绑定。

**构造器注入**

**实现方式**：依赖由 Spring 容器在启动时自动创建并通过构造函数注入到需要的类中。例如：

```java
@Component
public class MyController {
    private final MyService myService;

    // 构造器注入
    public MyController(MyService myService) {
        this.myService = myService;
    }
}
```

**解耦**：不需要在代码中直接使用 new 创建依赖，依赖关系由 Spring 容器管理，降低了耦合度。

**关于 @Component 注解**

**作用**：

@Component 用于告诉 Spring 这是一个可被扫描、实例化并纳入容器管理的 Bean。只有被 Spring 容器管理的 Bean 才能实现自动注入（例如通过构造器注入、字段注入或 Setter 注入）。

**对象上加了 @Component（或其他派生注解，如 @Service、@Repository）**，使得它被 Spring 自动扫描并实例化。

图画解释：

![image-20250321234420466](https://markdown-pictures-jhx.oss-cn-beijing.aliyuncs.com/picgo/image-20250321234420466.png)

![image-20250321234613489](https://markdown-pictures-jhx.oss-cn-beijing.aliyuncs.com/picgo/image-20250321234613489.png)



## 7  yaml - 占位符

YAML文件中可以使用`${}`占位符，它有两个作用：

### 使用配置文件中的值

1. 编写配置文件

   ```
   1server:
   2  port: 8888
   3
   
   4myconfig:
   5  myport: ${server.port}
   ```

2. 读取配置文件

   ```
   1@Controller
   2public class YmlController3 {
   3  @Value("${myconfig.myport}")
   4  private int port;
   5
   
   6  @RequestMapping("/yml3")
   7  @ResponseBody
   8  public String yml3(){
   9    System.out.println(port);
   10    return "hello springboot!";
   11   }
   12}
   ```

### 使用框架提供的方法

SpringBoot框架提供了一些生成随机数的方法可以在yml文件中使用：

- ${random.value} ：生成类似uuid的随机数，没有"-"连接
- ${random.uuid} ：生成一个uuid，有短杠连接
- ${random.int} ：随机取整型范围内的一个值
- ${random.int(10)}：随机生成一个10以内的数
- ${random.int(100,200)}：随机生成一个100-200 范围以内的数
- ${random.long}：随机取长整型范围内的一个值
- ${random.long(100,200)}：随机生成长整型100-200范围内的一个值

用法如下：

```
1# 随机生成tomcat端口
2server:
3  port: ${random.int(1024,9999)}
```





## 8 连系占位符 - 数据库配置加密 - 以及方案调研

确实可以，YAML 文件中可以使用占位符来读取加密后的敏感配置（例如数据库密码），但前提是你需要一个解密机制在应用启动时将这些加密字符串转换为明文。下面是几种常见方案：

**1. 使用 Jasypt 加密**

​	•	**原理**：

将敏感信息（如数据库密码）使用 Jasypt 工具加密，存储在 YAML 中的格式通常以 ENC(...) 表示；应用启动时，Jasypt 自动检测并解密该值。

​	•	**示例**：

在 application.yml 中：

```
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/mydb?useSSL=false&serverTimezone=UTC
    username: myuser
    password: ENC(9n4G5e1RZ...)  # 加密后的密码
```

配置解密器，例如在 application.properties 或环境变量中指定解密密钥：

```
jasypt.encryptor.password=yourSecretKey
```

同时引入 Jasypt starter 依赖：

```
<dependency>
    <groupId>com.github.ulisesbocchio</groupId>
    <artifactId>jasypt-spring-boot-starter</artifactId>
    <version>3.0.4</version>
</dependency>
```

启动时，Jasypt 会自动解密 ENC(...) 格式的配置值。

**2. 使用 Spring Cloud Config 的加密/解密功能**

​	•	**原理**：

如果你使用 Spring Cloud Config Server，Config Server 可以对敏感信息进行加密存储，客户端在拉取配置时会自动解密。

​	•	**示例**：

在 Config Server 上，你可以将敏感信息用对称或非对称加密，然后客户端只需提供密钥（或通过公钥验证）即可解密。

配置示例：

```
spring:
  datasource:
    password: '{cipher}AQBlAHgA...'  # 加密后的密文
```

客户端配置：

```
encrypt.key=yourSecretKey
```

详细配置需要参考 Spring Cloud Config 官方文档。

**3. 使用外部密钥管理工具（如 HashiCorp Vault）**

​	•	**原理**：

使用 Vault 等外部密钥管理系统，将敏感数据存储在 Vault 中，Spring Boot 应用通过 Spring Cloud Vault 集成在启动时从 Vault 中动态拉取配置。

​	•	**示例**：

在 application.yml 中配置 Vault 相关信息：

```
spring:
  cloud:
    vault:
      uri: http://localhost:8200
      authentication: TOKEN
      token: s.xxxxxxx
      generic:
        enabled: true
        backend: secret
        default-context: application
```

Vault 中存储的内容可以是未加密的，但只有经过认证的应用才能访问，从而实现敏感信息的保护。

**对比表格**

| **方案**                  | **优点**                                   | **缺点**                                 |
| ------------------------- | ------------------------------------------ | ---------------------------------------- |
| **Jasypt 加密**           | 实现简单，直接在配置文件中使用加密字符串   | 密钥管理需谨慎；加密方式对称密钥泄露风险 |
| **Spring Cloud Config**   | 集中管理配置，支持自动解密，适合微服务架构 | 需要搭建 Config Server；配置流程较复杂   |
| **外部密钥管理（Vault）** | 提供更高安全性，敏感数据不存放在配置文件中 | 集成和部署成本较高；依赖外部服务         |

**小结**

​	•	**使用 YAML 占位符读取加密后的配置是可行的**，关键在于引入合适的解密组件（例如 Jasypt）。

​	•	**选择哪种方案**取决于你的应用规模、安全需求和架构：

​	•	对于中小型项目或简单场景，Jasypt 是个常见选择；

​	•	对于微服务架构，可以考虑 Spring Cloud Config 或 Vault 这种集中管理的方式。



这样配置之后，应用启动时会自动将加密的数据库密码解密，确保在配置文件中敏感数据得到了保护，同时应用内部能够使用解密后的明文数据进行连接。



## 9 YAML文件_配置文件存放位置及优先级

- 项目根目录下
- 项目根目录下的/config子目录中
- 项目的resources目录中
- 项目的resources下的/config子目录中

这些目录下都可以存放两类配置文件，分别是`application.yml`和`application.properties`，这些配置文件的优先级从高到低依次为：

> 项目根目录下的/config子目录中
>
> - config/application.properties
> - config/application.yml
>
> 项目根目录下
>
> - application.properties
> - application.yml
>
> 项目的resources下的/config子目录中
>
> - resources/config/application.properties
> - resources/config/application.yml
>
> 项目的resources目录中
>
> - resources/application.properties
> - resources/application.yml
>
> 优先级高的文件会覆盖优先级低的文件中的配置



## 10 YAML文件_bootstrap配置文件

SpringBoot中有两种容器对象，分别是bootstrap和application，bootstrap是应用程序的父容器，bootstrap加载优先于applicaton。bootstrap配置文件主要对bootstrap容器进行配置，application配置文件是对applicaton容器进行配置。

bootstrap配置文件也同样支持properties和yml两种格式，主要用于从外部引入Spring应用程序的配置。

bootstrap配置文件特征

> - boostrap由父ApplicationContext加载，比applicaton优先加载。
> - boostrap里面的属性不能被覆盖。

bootstrap与application的应用场景

> - application配置文件主要用于SpringBoot项目的自动化配置。
> - bootstrap配置文件有以下几个应用场景。
    >   1. 使用Spring Cloud Config配置中心时，需要在bootstrap配置文件中添加连接到配置中心的配置属性来加载外部配置中心的配置信息。
>   2. 一些固定的不能被覆盖的属性。
>   3. 一些加密/解密的场景。











