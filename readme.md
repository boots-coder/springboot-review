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





## 11 SpringBoot整合Web开发_Servlet



![image-20250322004940715](https://markdown-pictures-jhx.oss-cn-beijing.aliyuncs.com/picgo/image-20250322004940715.png)

可能需要引入第三方的验证码组件；之前使用web.xml文件进行注解；

由于SpringBoot项目没有web.xml文件，所以无法在web.xml中注册web组件，SpringBoot有自己的方式注册web组件。

### 注册方式一

核心注解：@WebServlet

1. 编写servlet

   ```
   1@WebServlet("/first")
   2public class FirstServlet extends HttpServlet {
   3  public void doGet(HttpServletRequest request, HttpServletResponse response){
   4    System.out.println("First Servlet........");
   5   }
   6}
   ```

2. 启动类扫描web组件

   ```
   1@SpringBootApplication
   2//SpringBoot启动时扫描注册注解标注的Web组件
   3@ServletComponentScan
   4public class Springbootdemo2Application {
   5  public static void main(String[] args) {
   6    SpringApplication.run(Springbootdemo2Application.class, args);
   7   }
   8}
   ```

### 注册方式二

核心注解：@Configuration

核心组件： ServletRegistrationBean



1. 编写servlet

   ```
   1public class SecondServlet extends HttpServlet {
   2  public void doGet(HttpServletRequest request, HttpServletResponse response){
   3    System.out.println("Second Servlet........");
   4   }
   5}
   ```

2. 使用配置类注册servlet

   ```
   1@Configuration
   2public class ServletConfig {
   3  //ServletRegistrationBean可以注册Servlet组件，将其放入Spring容器中即可注册Servlet
   4  @Bean
   5  public ServletRegistrationBean getServletRegistrationBean(){
   6    // 注册Servlet组件
   7    ServletRegistrationBean bean = new ServletRegistrationBean(new SecondServlet());
   8    // 添加Servlet组件访问路径
   9    bean.addUrlMappings("/second");
   10    return bean;
   11   }
   12}
   ```



## 12 SpringBoot整合Web开发_Filter

和上面servlet类似 -- 也有两种方法；



## 13 SpringBoot整合Web开发_Listener

**监听器的作用**

​	•	**全局生命周期管理**

​	•	通过实现 ServletContextListener 接口，监听器能够在 Web 应用启动时（contextInitialized 方法）执行初始化工作，例如加载配置、初始化资源、建立数据库连接池等；

​	•	在应用关闭时（contextDestroyed 方法），可以释放资源、保存状态或进行日志记录等清理工作。

​	•	**事件驱动机制**

​	•	监听器可以捕获和响应 ServletContext、Session、Request 等不同层次的事件，如应用启动、Session 创建销毁、请求属性变更等，从而实现对系统各个环节的统一管理和监控。

​	•	**解耦和模块化**

​	•	将一些全局、跨模块的初始化和清理任务从业务代码中剥离出来，集中在监听器中处理，使得业务代码更加专注于自身逻辑，增强系统模块的解耦性和可维护性。

```java
@WebListener
public class FirstListener implements ServletContextListener {
  @Override
  public void contextInitialized(ServletContextEvent sce) {
    System.out.println("First Listener Init......");
   }

  @Override
  public void contextDestroyed(ServletContextEvent sce) {

   }
}
@SpringBootApplication
//SpringBoot启动时扫描注册注解标注的Web组件
@ServletComponentScan
public class Springbootdemo2Application {
  public static void main(String[] args) {             
    SpringApplication.run(Springbootdemo2Application.class, args);
   }
}

```



## 14 SpringBoot整合Web开发_静态资源

SpringBoot项目中没有WebApp目录，只有src目录。在`src/main/resources`下面有`static`和`templates`两个文件夹。SpringBoot默认在`static`目录中存放静态资源，而在`templates`中放动态页面。

> SpringBoot不推荐JSP作为动态页面，推荐使用Thymeleaf技术编写动态页面。templates目录是存放Thymeleaf页面的目录，稍后我们讲解Thymeleaf技术。



注意一点： 如果项目本身加了前缀 -- 在yml中，html中资源寻找也要加前缀







## 15 SpringBoot整合Web开发_静态资源其他存放位置

除了`/resources/static`目录，SpringBoot还会扫描以下位置的静态资源：

- `/resources/META‐INF/resources/`
- `/resources/resources/`
- `/resources/public/`



## 16 SpringBoot整合Web开发_JSP

在SpringBoot中不推荐使用JSP作为动态页面，如果我们要想使用JSP编写动态页面，需要手动添加webapp目录。

1. 由于SpringBoot自带tomcat无法解析JSP，需要在pom文件添加JSP引擎

   ```
   1<!--添加jsp引擎，SpringBoot内置的Tomcat不能解析JSP-->
   2<dependency>
   3  <groupId>org.apache.tomcat.embed</groupId>
   4  <artifactId>tomcat-embed-jasper</artifactId>
   5</dependency>
   ```

2. 将webapp标记为web目录

   ![image-20230816174101492](https://www.itbaizhan.com/wiki/imgs/image-20230816174101492.png)

3. 创建webapp目录，编写JSP文件

   ![image-20230816174122877](https://www.itbaizhan.com/wiki/imgs/image-20230816174122877.png)

   ```
   1<%@ page contentType="text/html;charset=UTF-8" language="java" %>
   2<html>
   3<head>
   4  <title>MYJSP</title>
   5</head>
   6<body>
   7MYJSP
   8</body>
   9</html>
   ```

4. 启动项目，访问http://localhost:8080/myJsp.jsp





**JSP（Java Server Pages）** 是一种用于开发动态 Web 页面技术，它允许在 HTML 页面中嵌入 Java 代码，帮助开发者在页面上动态生成内容。JSP 页面在运行时会被 Servlet 容器（如 Tomcat）编译成 Servlet，然后由容器执行。

**关系和区别**

​	•	**JSP 是基于 Servlet 的**

​	•	每个 JSP 页面都会在首次请求时或重新编译时转换为一个 Servlet 类，这个 Servlet 类继承自 HttpServlet。

​	•	编译后的 Servlet 负责处理请求、生成响应，就像手写的 Servlet 一样。

​	•	**简化动态页面开发**

​	•	相比直接编写 Servlet，JSP 提供了一种更为直观和便捷的方式来创建动态网页，开发者可以直接在 HTML 中嵌入 Java 表达式、脚本片段和自定义标签。

​	•	这样既能利用 HTML 的标记能力，也能通过 Java 代码生成动态内容。

​	•	**职责分离**

​	•	在理想的开发模式下，JSP 主要负责视图展示，而业务逻辑则由 Servlet 或其他后端组件处理，这有助于实现 MVC 架构的分层设计。

**小结**

​	•	**JSP 是一种动态页面技术**，能够让开发者在 HTML 页面中嵌入 Java 代码生成动态内容。

​	•	**JSP 与 Servlet 关系密切**：每个 JSP 在运行时会被转换为 Servlet，JSP 主要作为视图层技术使用，而 Servlet 通常处理业务逻辑和请求分发。

## 17 Thymeleaf_Thymeleaf入门



![image-20250322020533685](https://markdown-pictures-jhx.oss-cn-beijing.aliyuncs.com/picgo/image-20250322020533685.png)



Thymeleaf是一款用于渲染XML/HTML5内容的模板引擎，类似JSP。它可以轻易的与SpringMVC等Web框架进行集成作为Web应用的模板引擎。SpringBoot推荐使用Thymeleaf编写动态页面。

Thymeleaf最大的特点是能够直接在浏览器中打开并正确显示模板页面，而不需要启动整个Web应用。

Thymeleaf在有服务和无服务的环境下皆可运行，它即可以让美工在浏览器查看页面的静态效果，也可以让程序员在服务器查看带数据的动态页面效果

> 没有服务时，Thymeleaf的模板可以展示静态数据；当有数据返回到页面时，Thymeleaf会动态地替换掉静态内容，使页面动态显示。

1. 创建SpringBoot项目
2. 引入SpringMVC和Thymeleaf起步依赖





## 18 Thymeleaf_变量输出

| 语法     | 作用                                        |
| -------- | ------------------------------------------- |
| th:text  | 将model中的值作为内容放入标签中             |
| th:value | 将model中的值放入`input`标签的`value`属性中 |

input 一般就是表单；



1. ～准备模型数据

   ```
   1@GetMapping("/show")
   2public String showPage(Model model){
   3  model.addAttribute("msg","Hello Thymeleaf");
   4  return "index";
   5}
   ```

2. 在视图展示model中的值

   ```
   1<span th:text="${msg}"></span>
   2<hr/>
   3<input th:value="${msg}">
   ```



## 18 **操作字符串&时间**

### 操作字符串

操作字符串的内置对象为strings。

| 方法                            | 说明                                                         |
| ------------------------------- | ------------------------------------------------------------ |
| ${#strings.isEmpty(key)}        | 判断字符串是否为空，如果为空返回true，否则返回false          |
| ${#strings.contains(msg,'T')}   | 判断字符串是否包含指定的子串，如果包含返回true，否则返回false |
| ${#strings.startsWith(msg,'a')} | 判断当前字符串是否以子串开头，如果是返回true，否则返回false  |
| ${#strings.endsWith(msg,'a')}   | 判断当前字符串是否以子串结尾，如果是返回true，否则返回false  |
| ${#strings.length(msg)}         | 返回字符串的长度                                             |
| ${#strings.indexOf(msg,'h')}    | 查找子串的位置，并返回该子串的下标，如果没找到则返回-1       |
| ${#strings.substring(msg,2,5)}  | 截取子串，用法与JDK的`subString`方法相同                     |
| ${#strings.toUpperCase(msg)}    | 字符串转大写                                                 |
| ${#strings.toLowerCase(msg)}    | 字符串转小写                                                 |

使用方式：

```
1<span th:text="${#strings.isEmpty(msg)}"></span>
2<hr/>
3<span th:text="${#strings.contains(msg,'s')}"></span>
4<hr/>
5<span th:text="${#strings.length(msg)}"></span>
```

### 操作时间

操作时间的内置对象为dates

| 方法                               | 说明                                               |
| ---------------------------------- | -------------------------------------------------- |
| ${#dates.format(key)}              | 格式化日期，默认的以浏览器默认语言为标准进行格式化 |
| ${#dates.format(key,'yyyy/MM/dd')} | 按照自定义的格式做日期转换                         |
| ${#dates.year(key)}                | 取年                                               |
| ${#dates.month(key)}               | 取月                                               |
| ${#dates.day(key)}                 | 取日                                               |

1. 准备数据

   ```
   1model.addAttribute("date",new Date(130,0,1)); // 从1970年开始算的； 往后退 年月日
   ```

2. 使用内置对象操作时间

   ```
   1<span th:text="${#dates.format(date)}"></span>
   2<hr/>
   3<span th:text="${#dates.format(date,'yyyy/MM/dd')}"></span>
   4<hr/>
   5<span th:text="${#dates.year(date)}"></span>
   6<span th:text="${#dates.month(date)}"></span>
   7<span th:text="${#dates.day(date)}"></span>
   ```







## 19 Thymeleaf_条件判断

![image-20211021115534302](https://www.itbaizhan.com/wiki/imgs/image-20211021115534302.png)

| 语法  | 作用     |
| ----- | -------- |
| th:if | 条件判断 |

1. 准备数据

   ```
   1model.addAttribute("sex","女");
   ```

2. 进行条件判断

   ```
   1<div>
   2  <span th:if="${sex} == '男'">
   3     性别：男
   4  </span>
   5  <span th:if="${sex} == '女'">
   6     性别：女
   7  </span>
   8</div>
   ```

| 语法              | 作用                                                         |
| ----------------- | ------------------------------------------------------------ |
| th:switch/th:case | th:switch/th:case与Java中的switch语句等效。`th:case="*"`表示Java中switch的default，即没有case的值为true时显示`th:case="*"`的内容。 |

1. 准备数据

   ```
   1model.addAttribute("id","12");
   ```

2. 进行条件判断

   ```
   1<div th:switch="${id}">
   2  <span th:case="1">ID为1</span>
   3  <span th:case="2">ID为2</span>
   4  <span th:case="3">ID为3</span>
   5  <span th:case="*">ID为*</span>
   6</div>
   ```

## 20 Thymeleaf_迭代遍历



| 语法    | 作用                     |
| ------- | ------------------------ |
| th:each | 迭代器，用于循环迭代集合 |

### 遍历集合

1. 编写实体类

   ```
   1public class User {
   2  private String id;
   3  private String name;
   4  private int age;
   5  // 省略getter/setter/构造方法
   6}
   ```

2. 准备数据

   ```
   1List<User> users = new ArrayList();
   2users.add(new User("1","sxt",23));
   3users.add(new User("2","baizhan",22));
   4users.add(new User("3","admin",25));
   5model.addAttribute("users",users);
   ```

3. 在页面中展示数据

   ```
   1<table border="1" width="50%">
   2  <tr>
   3    <th>ID</th>
   4    <th>Name</th>
   5    <th>Age</th>
   6  </tr>
   7  <!-- 遍历集合的每一项起名为user -->
   8  <tr th:each="user : ${users}">
   9    <td th:text="${user.id}"></td>
   10    <td th:text="${user.name}"></td>
   11    <td th:text="${user.age}"></td>
   12  </tr>
   13</table>
   ```

### 遍历Map

1. 准备数据

   ```
   1Map<String,User> map = new HashMap();
   2map.put("user1",new User("1","sxt",23));
   3map.put("user2",new User("2","baizhan",22));
   4map.put("user3",new User("3","admin",25));
   5model.addAttribute("map",map);
   ```

2. 遍历map

   ```
   1<table border="1" width="50%">
   2  <tr>
   3    <th>ID</th>
   4    <th>Name</th>
   5    <th>Age</th>
   6    <th>Key</th>
   7  </tr>
   8  <!-- 遍历出的是一个键值对对象，key获取键，value获取值 -->
   9  <tr th:each="m : ${map}">
   10    <td th:text="${m.value.id}"></td>
   11    <td th:text="${m.value.name}"></td>
   12    <td th:text="${m.value.age}"></td>
   13    <td th:text="${m.key}"></td>
   14  </tr>
   15</table>
   ```







## 21 Thymeleaf_使用状态变量

thymeleaf将遍历的状态变量封装到一个对象中，通过该对象的属性可以获取状态变量：

| 状态变量 | 含义                                                         |
| -------- | ------------------------------------------------------------ |
| index    | 当前迭代器的索引，从0开始                                    |
| count    | 当前迭代对象的计数，从1开始                                  |
| size     | 被迭代对象的长度                                             |
| odd/even | 布尔值，当前循环是否是偶数/奇数，从0开始                     |
| first    | 布尔值，当前循环的是否是第一条，如果是返回true，否则返回false |
| last     | 布尔值，当前循环的是否是最后一条，如果是则返回true，否则返回false |

使用状态变量

```
1<!--冒号前的第一个对象是遍历出的对象，第二个对象是封装状态变量的对象-->
2<tr th:each="user,status : ${users}">
3  <td th:text="${user.id}"></td>
4  <td th:text="${user.name}"></td>
5  <td th:text="${user.age}"></td>
6  <td th:text="${status.index}"></td>
7  <td th:text="${status.count}"></td>
8  <td th:text="${status.size}"></td>
9  <td th:text="${status.odd}"></td>
10  <td th:text="${status.even}"></td>
11  <td th:text="${status.first}"></td>
12  <td th:text="${status.last}"></td>
13</tr>
```



## 22 Thymeleaf_获取域中的数据

thymeleaf也可以获取request，session，application域中的数据，方法如下：

1. 准备数据

   ```
   1request.setAttribute("req","HttpServletRequest");
   2session.setAttribute("ses","HttpSession");
   3session.getServletContext().setAttribute("app","application");
   ```

2. 获取域数据

   ```
   1request:<span th:text="${req}"></span><hr/>
   2session: <span th:text="${session.ses}"/><hr/>
   3application: <span th:text="${application.app}"/>
   ```



**场景：用户登录管理系统**

假设我们要开发一个简单的用户登录管理系统，用户登录后，系统会存储用户信息，并在不同的页面展示用户的登录状态。

**使用不同的作用域存储数据**



**1. Request 域：处理登录请求**



当用户提交用户名和密码，服务器验证后，将结果存储在 Request 域中，并将请求转发到登录结果页面。

```
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if ("admin".equals(username) && "1234".equals(password)) {
            request.setAttribute("message", "登录成功！");
            request.getRequestDispatcher("welcome.jsp").forward(request, response);
        } else {
            request.setAttribute("message", "用户名或密码错误！");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}
```

**2. Session 域：保持用户登录状态**



如果用户登录成功，我们需要在多个页面保持用户登录状态，因此将用户信息存入 Session 域。

```
@WebServlet("/sessionLogin")
public class SessionLoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if ("admin".equals(username) && "1234".equals(password)) {
            HttpSession session = request.getSession();
            session.setAttribute("currentUser", username);
            response.sendRedirect("dashboard.jsp");
        } else {
            request.setAttribute("message", "用户名或密码错误！");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}
```

在 **dashboard.jsp** 页面中：

```
<% String user = (String) session.getAttribute("currentUser"); %>
<% if (user != null) { %>
    <h2>欢迎, <%= user %>!</h2>
    <a href="logout">退出登录</a>
<% } else { %>
    <h2>请先登录</h2>
    <a href="login.jsp">去登录</a>
<% } %>
```

**3. Application 域：统计在线用户数**



假设我们想统计当前在线用户数，可以在 Application 域（ServletContext）存储这个信息，每当有用户登录时，增加计数，用户退出时减少计数。

```
@WebListener
public class OnlineUserListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        ServletContext context = se.getSession().getServletContext();
        Integer onlineUsers = (Integer) context.getAttribute("onlineUsers");
        if (onlineUsers == null) {
            onlineUsers = 0;
        }
        context.setAttribute("onlineUsers", onlineUsers + 1);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        ServletContext context = se.getSession().getServletContext();
        Integer onlineUsers = (Integer) context.getAttribute("onlineUsers");
        if (onlineUsers != null && onlineUsers > 0) {
            context.setAttribute("onlineUsers", onlineUsers - 1);
        }
    }
}
```

在 **dashboard.jsp** 页面中：

```
<% Integer onlineUsers = (Integer) application.getAttribute("onlineUsers"); %>
<h3>当前在线用户数: <%= onlineUsers != null ? onlineUsers : 0 %></h3>
```

**总结**

| **作用域**         | **生命周期**     | **适用场景**         | **示例**                   |
| ------------------ | ---------------- | -------------------- | -------------------------- |
| **Request 域**     | 仅在当前请求     | 适用于一次性数据传递 | 处理登录结果，显示提示信息 |
| **Session 域**     | 直到用户会话结束 | 适用于保持用户状态   | 记录当前登录用户信息       |
| **Application 域** | 直到服务器关闭   | 适用于全局共享数据   | 统计在线用户数             |

这种方式可以让不同的数据在适合的作用域内存储，提高系统的性能和数据管理能力。



## 23 url的写法

在Thymeleaf中路径的写法为`@{路径}`

```
1<a th:href="@{http://www.baidu.com}">百度</a>
2<a th:href="@{http://www.baidu.com?id=1&name=sxt}">静态参数一</a>
3<a th:href="@{http://www.baidu.com(id=2,name=bz)}">静态参数二</a> //thymleaf 特有的写法
```

### 添加动态参数

1. 准备数据

   ```
   1model.addAttribute("id","100");
   2model.addAttribute("name","bzcxy");
   ```

2. 在URL中添加参数

   ```
   1<a th:href="@{'http://www.baidu.com?id='+${id}+'&name='+${name}}">动态参数一</a>
   2<a th:href="@{http://www.baidu.com(id=${id},name=${name})}">动态参数二</a>
   ```

### 添加RESTful风格的参数

```
1<a th:href="@{http://www.baidu.com/{id}/{name}(id=${id},name=${name})}">restful格式传递参数方式</a>
```

restFUl 风格和query 风格的区别

✅ **答案：不完全等效**

​	•	**语法不同**：RESTful 方式使用路径参数，而 Query 方式使用 URL 查询参数。

​	•	**适用场景不同**：

​	•	RESTful 适用于资源定位，比如 GET /users/123 表示获取 ID 为 123 的用户。

​	•	Query 参数适用于筛选或查询，比如 GET /users?name=Tom 表示查询名字为 Tom 的用户。

​	•	**影响后端处理**：

​	•	如果后端是 Spring Boot 处理 RESTful URL，它会使用 @PathVariable 获取路径参数：

```
@GetMapping("/users/{id}/{name}")
public String getUser(@PathVariable("id") int id, @PathVariable("name") String name) { ... }
```

如果是 Query 参数，则需要 @RequestParam：

```
@GetMapping("/users")
public String getUser(@RequestParam("id") int id, @RequestParam("name") String name) { ... }
```

**总结**

​	•	**Thymeleaf 支持 RESTful URL 绑定路径参数**，可以让 URL 更符合 REST 设计。

​	•	**RESTful 方式和 Query 方式不完全等效**，但可以在不同场景下互相替代。

​	•	**实际开发中选择哪种方式，取决于后端 API 设计**：

​	•	**资源访问**（如用户详情）：推荐 RESTful /users/123/Tom

​	•	**查询数据**（如搜索）：推荐 Query 参数 /users?name=Tom

**如果你的后端 API 是基于 RESTful 设计的，建议使用 {id}/{name} 方式，否则使用 Query 参数更通用。**



## 24 Thymeleaf_相关配置

在SpringBoot配置文件中可以进行Thymeleaf相关配置

| 配置项                                | 含义                                            |
| ------------------------------------- | ----------------------------------------------- |
| spring.thymeleaf.prefix               | 视图前缀                                        |
| spring.thymeleaf.suffix               | 视图后缀                                        |
| spring.thymeleaf.encoding             | 编码格式                                        |
| spring.thymeleaf.servlet.content-type | 响应类型                                        |
| spring.thymeleaf.cache=false          | 页面缓存，配置为false则不启用页面缓存，方便测试 |

```
1spring:
2  thymeleaf:
3   prefix: classpath:/templates/
4   suffix: .html
5   encoding: UTF-8
6   cache: false
7   servlet:
8    content-type: text/html
```



**Spring Boot Thymeleaf 配置中 content-type 的作用**

在 application.yml 配置中：

```
spring:
  thymeleaf:
    prefix: classpath:/templates/
    suffix: .html
    encoding: UTF-8
    cache: false
    servlet:
      content-type: text/html
```

**1. content-type 作用**

​	•	content-type: text/html **表示服务器返回给客户端的响应内容类型**（MIME 类型）。

​	•	这里指定 text/html，意味着 **浏览器或客户端会将响应解析为 HTML 页面**，而不是纯文本、JSON 或其他格式。

**2. Content-Type 的常见值及作用**

| **Content-Type**    | **作用**       | **适用场景**                   |
| ------------------- | -------------- | ------------------------------ |
| text/html           | 返回 HTML 页面 | 浏览器渲染网页                 |
| text/plain          | 纯文本格式     | 仅返回文本内容                 |
| application/json    | 返回 JSON 数据 | 前后端分离接口，前端 AJAX 请求 |
| application/xml     | 返回 XML 数据  | Web 服务（如 SOAP）            |
| image/png           | 返回 PNG 图片  | 直接展示图片                   |
| multipart/form-data | 处理文件上传   | 表单上传文件                   |

**3. 示例：不同 Content-Type 的影响**

**返回 HTML 页面（默认 Thymeleaf 配置）**

```
@Controller
public class TestController {
    @GetMapping("/html")
    public String htmlPage() {
        return "index"; // Thymeleaf 会渲染 index.html
    }
}
```

📌 **浏览器访问 /html，服务器返回 HTML 页面**，Content-Type 为 text/html。

**返回 JSON 数据**

如果改成：

```
@RestController
public class TestController {
    @GetMapping("/json")
    public Map<String, String> jsonResponse() {
        return Map.of("message", "Hello, JSON");
    }
}
```

📌 **浏览器访问 /json，返回 JSON 格式**，Content-Type 为 application/json：

```
{
  "message": "Hello, JSON"
}
```

**4. 为什么 text/html 适用于 Thymeleaf？**

​	•	Thymeleaf 是基于 **HTML 模板** 渲染动态内容的，因此 **默认返回的内容是 HTML**。

​	•	服务器发送 Content-Type: text/html，告诉浏览器 **按照 HTML 方式解析页面**。

**总结**

​	1.	spring.thymeleaf.servlet.content-type: text/html **确保 Thymeleaf 返回的是 HTML 页面**。

​	2.	Content-Type 告诉浏览器 **如何解析服务器返回的数据**。

​	3.	如果你的 API 需要返回 JSON，应该使用 @RestController 并返回 application/json 而不是 text/html。

🚀 **简而言之：Thymeleaf 用 text/html，API 用 application/json！**



## 25 SpringBoot整合MyBatis

准备数据

```mysql
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
    username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名，唯一',
    email VARCHAR(100) NOT NULL UNIQUE COMMENT '用户邮箱，唯一',
    password VARCHAR(255) NOT NULL COMMENT '用户密码，建议存储哈希值',
    phone_number VARCHAR(20) DEFAULT NULL COMMENT '用户手机号',
    
    -- 用户重要性等级，用于营销推送优先级
    -- "normal" 表示普通用户, "vip" 表示VIP用户, "vip_plus" 表示VIP+用户
    -- 这里的vip、vip_plus不是严格的付费会员，只是表示用户重要性 
    user_level ENUM('normal', 'vip', 'vip_plus') NOT NULL DEFAULT 'normal' COMMENT '用户等级',
    
    -- 以下字段可作为辅助信息来判断或记录用户行为、价值等
    total_spent DECIMAL(10,2) NOT NULL DEFAULT 0.00 COMMENT '总消费金额，用于判断用户价值',
    purchase_count INT NOT NULL DEFAULT 0 COMMENT '购买次数，用于判断用户活跃度',
    
    -- 记录用户注册时间、最后一次登录时间、更新时间等
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '用户创建时间',
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '用户信息最后更新时间',
    last_login DATETIME DEFAULT NULL COMMENT '用户最后登录时间'
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COMMENT='用户表，包含基本信息及营销等级';
```

添加数据库持久化依赖：

```
    <!-- MySQL 驱动，用于连接 MySQL 数据库 -->
        <dependency>
            <groupId>com.mysql</groupId>
            <artifactId>mysql-connector-j</artifactId>
            <scope>runtime</scope>
        </dependency>
```

```
<!-- MyBatis 依赖，用于数据持久化操作 -->
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>3.0.4</version>
        </dependency>

        <!-- mybatis测试起步依赖 -->
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter-test</artifactId>
            <version>3.0.4</version>
            <scope>test</scope>
        </dependency>
```

编写实体类

编写mapper接口

编写映射文件

编写配置文件





## 提出一个问题， 如何选择合适的依赖版本？ 并且管理依赖

![image-20250322164537849](https://markdown-pictures-jhx.oss-cn-beijing.aliyuncs.com/picgo/image-20250322164537849.png)



查阅官网后发现最新版本已经被更新了



**使用 Spring Boot 的依赖管理能力**
利用父 POM 或 BOM 统一管理依赖版本，降低手动维护依赖版本带来的复杂性。

**查阅官方文档和社区资源**
随时关注 Spring Boot 版本发布说明、依赖的官方更新日志以及社区的最佳实践经验，确保选用的依赖版本稳定且兼容。

**依赖分析和冲突检测**
利用工具（如 Maven 的 `dependency:tree`）检查传递依赖，并在 `<dependencyManagement>` 中集中配置依赖版本，避免多版本共存产生冲突。

**根据项目需求指定依赖**
在前后端分离项目中只保留必要的后端依赖，确保 API 服务的精简和高效，同时使用正确的版本能够更好地保证项目的稳定性与可维护性。

在 Spring Boot 开发中（尤其是前后端分离的架构中），如何选择合适的依赖版本以及如何管理依赖避免冲突是一个常见的问题。下面提供一些建议和最佳实践：

------

### 1. 使用 Spring Boot 提供的依赖管理

- **继承 `spring-boot-starter-parent` 或使用 Spring Boot 的 BOM**
  Spring Boot 的父 POM 或 BOM（Bill of Materials）会统一管理常见依赖的版本，如 Spring 框架、Hibernate、数据库驱动（比如 MySQL Connector）等。这意味着：

    - 你在 POM 中添加依赖时通常可以省略版本号，因为已由 Spring Boot 统一管理。
    - 当你升级 Spring Boot 版本时，相关依赖的版本也随之更新，降低了冲突的风险。

- **示例 Maven 配置**

  ```xml
  <parent>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-parent</artifactId>
      <version>3.0.0</version> <!-- 例如使用 Spring Boot 3.0.0 -->
  </parent>
  
  <dependencies>
      <!-- 使用 Spring Boot Starter Data JPA，它会自动引入常用数据库驱动 -->
      <dependency>
          <groupId>org.springframework.boot</groupId>
          <artifactId>spring-boot-starter-data-jpa</artifactId>
      </dependency>
  
      <!-- 如果需要使用 MySQL 驱动，由于 Spring Boot 管理了版本，可以不指定版本 -->
      <dependency>
          <groupId>mysql</groupId>
          <artifactId>mysql-connector-java</artifactId>
      </dependency>
  </dependencies>
  ```

------

### 2. 如何选择合适的依赖版本

- **参考 Spring Boot 官方文档和发布说明**
  每个 Spring Boot 版本的文档中通常会说明所推荐的依赖版本，确保和当前 Spring Boot 版本的兼容性。
- **利用 Maven Repository 或 Gradle Plugin**
  在 Maven Central 或使用 [Maven Repository](https://mvnrepository.com/) 搜索所需依赖（例如 MySQL Connector），查看最新版本及其兼容性说明。
  同时，阅读对应依赖的官方文档和 Release Notes 来确认该版本是否适用于你的项目环境和 Java 版本。
- **检查传递性依赖关系**
  使用 Maven 的 `mvn dependency:tree` 命令或 Gradle 的 `gradle dependencies` 任务查看所有依赖及其版本，这样可以判断：
    - 某个依赖是否由 Spring Boot Starter 自动引入；
    - 是否存在多个版本冲突的情况；
    - 是否需要在 `<dependencyManagement>` 中显式覆盖默认版本。

------

### 3. 避免依赖冲突的策略

- **依赖管理区域（Dependency Management）**
  如果需要在项目中指定或覆盖某个依赖的版本，建议通过 `<dependencyManagement>` 块来集中管理版本。这样所有子模块引用该依赖时，都将使用统一的版本。

  ```xml
  <dependencyManagement>
      <dependencies>
          <dependency>
              <groupId>mysql</groupId>
              <artifactId>mysql-connector-java</artifactId>
              <version>8.0.32</version> <!-- 根据需要指定版本 -->
          </dependency>
      </dependencies>
  </dependencyManagement>
  ```

- **使用依赖分析工具**
  利用 Maven Enforcer Plugin 或 Gradle 的依赖冲突检测功能，可以在构建阶段提前发现和解决依赖版本冲突。

- **合理拆分模块**
  如果项目规模较大，可以考虑把不同功能模块拆分为多个模块，并通过统一的 BOM 或父 POM 来管理各个模块的依赖版本。

- **明确区分前后端依赖**
  对于前后端分离项目，后端侧通常只提供 API 和业务逻辑，不需要引入与页面渲染相关的依赖（如 Thymeleaf 等）。这样可以减少不必要的依赖和潜在冲突。





## 26 SpringBoot单元测试

Command + shift +T --- 创建单元测试快捷键

核心注解： @SpringBootTest

```
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
```



## 27 SpringBoot热部署

热部署，就是在应用正在运行的时候升级软件，却不需要重新启动应用。即修改完代码后不需要重启项目即可生效。在SpringBoot中，可以使用DevTools工具实现热部署

1. 添加DevTools依赖

   ```
   1<!-- 热部署工具 -->
   2<dependency>
   3  <groupId>org.springframework.boot</groupId>
   4  <artifactId>spring-boot-devtools</artifactId>
   5  <optional>true</optional>
   6</dependency>
   ```

2. 在idea中设置自动编译

   点击`File-->Settings-->Compiler`

   ![image-20230807154159257](https://www.itbaizhan.com/wiki/imgs/image-20230807154159257.png)

3. 开启允许在运行中修改文件

   点击`File-->Settings-->Advanced Settings`，勾选`Allow auto-make to start even if developed application is currently running`

   ![image-20230807155349721](https://www.itbaizhan.com/wiki/imgs/image-20230807155349721.png)

此时热部署即可生效



## 28 SpringBoot定时任务

定时任务即系统在特定时间执行一段代码。Spring Boot默认已经整合了Spring Task定时任务，只需要添加相应的注解即可完成。

1. 在启动类中加入`@EnableScheduling`注解即可开启定时任务

   ```
   1@SpringBootApplication
   2@EnableScheduling
   3public class Demo1Application {
   4  public static void main(String[] args) {
   5    SpringApplication.run(Demo1Application.class, args);
   6   }
   7}
   ```

2. 编写定时任务

   ```
   1@Component
   2public class MyTask {
   3  // 定时任务方法，每秒执行一次
   4  @Scheduled(cron="* * * * * *")
   5  public void task1() {
   6    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
   7    System.out.println(sdf.format(new Date()));
   8   }
   9}
   ```

3. 启动项目，定时任务方法按照配置定时执行。











## 29 redis 定时刷新（springboot整合redis -- 重点）

下面是一步步的计算过程：

1. **已知条件**

    - 23 个用户数据占用约 6 KB 内存
    - 1 个用户数据占用的内存 ≈ 6 KB / 23 ≈ 0.26087 KB

2. **计算 500 万（5,000,000）个用户数据所需内存**

   $\text{总内存} ≈ 5\,000\,000 \times 0.26087\text{KB} ≈ 1\,304\,347.8\text{KB}$

3. **单位转换**

    - 将 KB 转换为 MB（假设 1MB = 1024 KB）：

      $\text{内存 (MB)} ≈ \frac{1\,304\,347.8\text{KB}}{1024} ≈ 1\,274 \text{MB}$

    - 或者大约 1.27 GB（1GB ≈ 1024 MB）

因此，大约需要 **1.3GB 内存** 来存储 500 万用户数据。
注意：实际内存占用会受到 Redis 内部管理数据结构、对象头信息和其他额外数据的影响，上述计算只是一种粗略估算。



## 30 SpringBoot内容协商机制（了解）



### 基于请求头修改返回数据格式

![image-20230807160753513](https://www.itbaizhan.com/wiki/imgs/image-20230807160753513.png)

如果我们的Java服务为浏览器和安卓手机同时提供服务，浏览器期望接受的请求是JSON格式，安卓客户端期望接收的请求是XML格式，这个时候是否需要写两个方法？

不需要！SpringBoot的内容协商机制可以解决这个问题。

- 内容协商机制：根据客户端接收能力不同，SpringBoot 返回不同媒体类型的数据。

Spring默认支持内容协商机制，但SpringBoot默认只支持返回Json数据，所以需要导入`jackson-dataformat-xml`让SpringBoot支持返回xml数据

1. 引入依赖

   ```
   1<!-- 引入支持返回 xml 数据格式 -->
   2<dependency>
   3  <groupId>com.fasterxml.jackson.dataformat</groupId>
   4  <artifactId>jackson-dataformat-xml</artifactId>
   5</dependency>
   ```

2. 编写控制器

   ```
   1@Controller
   2public class ConsultController {
   3  @Autowired
   4  private StudentMapper studentMapper;
   5
   
   6  @RequestMapping("/student/findById")
   7  @ResponseBody
   8  public Student findById(Integer id){
   9    Student student = studentMapper.findById(id);
   10    return student;
   11   }
   12}
   ```

3. 进行测试，SpringBoot的内容协商机制是根据请求头不同，返回不同格式的数据，所以需要我们能够修改请求头，我们使用postman进行测试：

   ![image-20230807162522810](https://www.itbaizhan.com/wiki/imgs/image-20230807162522810.png)

   ![image-20230807162620597](https://www.itbaizhan.com/wiki/imgs/image-20230807162620597.png)



### 基于请求参数的内容协商机制

SpringBoot默认根据请求头不同，返回不同的数据格式。我们还可以配置基于请求参数的内容协商，也就是请求参数值不同，返回不同的数据：

1. 配置SpringBoot基于请求参数的内容协商

   ```
   1#开启请求参数内容协商模式
   2spring.mvc.contentnegotiation.favor-parameter=true
   3#请求参数内容协商模式的参数名
   4spring.mvc.contentnegotiation.parameter-name=format
   ```

2. 在postman进行测试：

   ![image-20230807163615357](https://www.itbaizhan.com/wiki/imgs/image-20230807163615357.png)

   ![image-20230807163644442](https://www.itbaizhan.com/wiki/imgs/image-20230807163644442.png)







## 31 SpringBoot国际化（了解）

国际化：（Internationalization 简称 I18n，其中“I”和“n”分别为首末字符，18 则为中间的字符数）。是指软件能同时应对不同国家和地区的用户访问，并根据用户地区和语言习惯，提供相应的、符合用具阅读习惯的页面和数据，例如，为中国用户提供汉语界面显示，为美国用户提供英语界面显示。接下来我们来说一下在SpringBoot项目中，如何进行国际化配置：

### 1、编写国际化资源文件

SpringBoot国际化资源文件的文件名规范为：基本名_语言代码_国家或地区代码。例如：

- 美国英语：messages_en_US.properties：
- 中国汉语：messages_zh_CN.properties。

我们在 src/main/resources中，按照国际化资源文件命名格式分别创建以下三个文件：

- messages.properties：无语言设置时生效
- messages_en_US.properties：美国英语时生效
- messages_zh_CN.properties：中文时生效

![image-20230807173716552](https://www.itbaizhan.com/wiki/imgs/image-20230807173716552.png)

编写三个文件：

```
1# messages.properties
2welcome=欢迎使用{0}(默认)
3

4# messages_en_US.properties 
5welcome=welcome to {0}
6

7# messages_zh_CN.properties
8welcome=欢迎使用{0}(中文)
```

> 注意，这里要将项目配置文件的编码方式改成UTF-8，否则会出现乱码
>
> ![image-20230807173935519](https://www.itbaizhan.com/wiki/imgs/image-20230807173935519.png)

### 2、在配置文件指定国际资源文件的基本名

```
1spring:
2  messages:
3   basename: messages
```

### 3、编写控制器

```
1@Controller
2public class I18nController {
3  @Autowired
4  private MessageSource messageSource;
5

6  @RequestMapping("/welcome")
7  @ResponseBody
8  public String welcome(HttpServletRequest request) {
9    // 获取请求来源的地区
10    Locale locale = request.getLocale();
11    /**
12     * 使用国际化
13     * 第一个参数是国际化文件的key，
14     * 第二个参数value中的占位符数据
15     * 第三个是区域
16     */
17    String welcome = messageSource.getMessage("welcome", new Object[]{"springboot"}, locale);
18    return welcome;
19   }
20}
```

### 4、在浏览器测试国际化

先在默认情况下访问`/welcome`，之后切换浏览器环境，再次访问`/welcome`

![image-20230807174122875](https://www.itbaizhan.com/wiki/imgs/image-20230807174122875.png)

在 Spring Boot 中进行国际化时，默认约定的资源文件名是基于 Java 标准的 ResourceBundle 的命名规则。例如：

- **messages.properties**
  为默认（缺省）语言配置文件。
- **messages_en_US.properties**
  为美国英语配置文件。
- **messages_zh_CN.properties**
  为简体中文配置文件。

### 5 是否可以自定义资源文件名？

**可以自定义**。Spring Boot 默认使用 `spring.messages.basename` 属性，它的默认值就是 `messages`。你可以通过修改这个属性来自定义国际化资源文件的基本名称。比如，你可以在 `application.yml` 或 `application.properties` 中修改为：

```yaml
spring:
  messages:
    basename: my-messages
```

这样，Spring Boot 会查找以下文件（如果存在）：

- `my-messages.properties`
- `my-messages_en_US.properties`
- `my-messages_zh_CN.properties`

等，前提是按照 ResourceBundle 标准的命名规则封装 locale 信息。

### 总结

- **约定优于配置：** 默认情况下，Spring Boot 会自动寻找 `messages` 开头的文件，符合 ResourceBundle 的命名规则。
- **自定义配置：** 你可以通过 `spring.messages.basename` 属性来自定义基本名称，只要后续的文件命名仍然符合 locale 后缀规范即可。

因此，资源文件的名称既可以遵循默认的约定，也可以根据需求进行自定义，只要在配置中正确指定。





###  6 SpringBoot国际化_在Thymeleaf中进行国际化



```
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
  <meta charset="UTF-8"/>
  <title>Title</title>
</head>
<body>
<h1 th:text="#{welcome('springboot')}">欢迎</h1>
<h1>[[#{welcome('springboot')}]]</h1>
</body>
</html>

```





## 32 SpringBoot参数校验_简单数据类型

![image-20250322191026281](https://markdown-pictures-jhx.oss-cn-beijing.aliyuncs.com/picgo/image-20250322191026281.png)





