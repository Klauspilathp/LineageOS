# d7c_springboot

## 介绍

d7c_springboot 项目是基于 d7c_springboot_plugins 开发的强权限校验、高可扩展性的分布式与微服务骨架项目。

## 软件架构

d7c_springboot 采用 Maven 进行项目构建系统，支持 JDK1.8+、MySQL5.7/8+。该项目需要依赖 d7c_springboot_plugins 插件，如需源码请前往 [d7c_springboot_plugins](https://item.taobao.com/item.htm?ft=t&id=637995804294)，如需运维文档请前往 [d7c_docs](https://item.taobao.com/item.htm?ft=t&id=637839200595)。

## 安装教程

1.  克隆项目到本地
2.  导入开发工具，获取 [开发工具](https://pan.baidu.com/s/1bNUzfSV7d-kQdXC5tkl-FA)，提取码：7777

## 模块介绍
### 1. d7c_springboot_common

该模块用于封装一些公共常量、初始化一些公共端点、服务等。

### 2. d7c_springboot_common_services

该模块用于存放枚举类、实体类、Dao 接口、Mapper.xml 定义等不常被修改的通用代码。

### 3. d7c_springboot_config

该模块为分布式与微服务系统配置中心服务端，为所有微服务提供属性配置。

### 4. d7c_springboot_eureka_server

该模块是 eureka 服务发现模块，需要先启动，因为其他服务要注册到 eureka 上，该服务也提供了其他服务上下线的通知功能。

### 5. d7c_springboot_zuul

该模块是 zuul 网关服务模块，支持动态过滤器功能。

### 6. d7c_springboot_gateway

该模块是 gateway 网关服务模块，支持过滤器和动态添加路由规则功能。

### 7. d7c_springboot_admin

该模块用于管理和监控其他 springboot 应用程序，它针对 spring boot 的 actuator 接口进行 UI 美化封装。

### 8. d7c_springboot_dashboard

该模块是 dashboard 监控模块。

### 9. d7c_springboot_auth_feign

该模块主要为其他微服务认证提供 Feign 客户端接口。

### 10. d7c_springboot_auth

该模块为基于 JWT 加解密方式实现的无状态 token 多用户类型认证服务。

### 11. d7c_springboot_client_oauth2

该模块为 oauth2 资源授权服务器模块。

### 12. d7c_springboot_client_web

该模块为 WEB 客户端管理模块。权限校验采用 security 技术，页面解析采用 thymeleaf 技术，页面展示采用 layui 框架。

### 13. d7c_springboot_client_api

该模块可以对外提供 HTTP Rest 服务。

### 14. d7c_springboot_client_websocket

该模块可以对外提供 Websocket 服务。

### 15. d7c_springboot_eureka_client1

该模块提供了一些服务测试代码。

### 16. d7c_springboot_eureka_client2

该模块提供了一些服务测试代码。

### 17. d7c_springboot_quartz

该模块为集群模式定时任务模块，不对外提供服务，只用于运行其他服务插入数据库（或消息队列）中的定时任务或本类定义的定时任务。

### 18. d7c_springboot_sharding

该模块提供了一些使用 sharding 技术进行分库分表的使用案例。

### 19. d7c_springboot_webflux

该模块提供了一些 webflux 技术实现的异步处理测试代码。

### 20. d7c_springboot_activiti

该模块提供了访问 activiti 技术进行工作流操作的公共代码。

### 21. d7c_springboot_flowable_modeler

该模块提供了使用 flowable modeler 设计器进行流程定义的服务。

浏览器访问 http://127.0.0.1:8099/flowable-modeler
![flowable modeler 首页](https://images.gitee.com/uploads/images/2021/0610/180336_c22eeb21_1070311.png "QQ图片20210610180211.png")

### 22. d7c_springboot_flowable

该模块提供了访问 flowable 技术进行工作流操作的公共代码。

## 使用说明

1.  安装 MySQL、Redis、Rabbitmq 等服务，数据库脚本位于项目根目录下的 d7c.sql；
2.  修改应用下的 d7c_springboot_**/src/main/resources/application.properties 属性文件，并根据所选环境查看 d7c_springboot_**/src/main/resources/config/application-*.properties 文件中引用的配置，去 d7c_springboot_common/src/main/resources/config 目录下找到对应的属性文件并修改属性配置；
3.  服务启动类是应用包下的 *Application.java 文件，此处仅展示 d7c_springboot_client_web 应用的运行流程。

## 运行展示

1.  启动 d7c_springboot_eureka_server 应用发现其他服务

java -jar d7c_springboot_eureka_server.jar

```
*
*
*
[d7c] INFO  [2021-06-11 09:35:33] com.netflix.eureka.registry.PeerAwareInstanceRegistryImpl.openForTraffic(PeerAwareInstanceRegistryImpl.java:242) - Renew threshold is: 1
[d7c] INFO  [2021-06-11 09:35:33] com.netflix.eureka.registry.PeerAwareInstanceRegistryImpl.openForTraffic(PeerAwareInstanceRegistryImpl.java:253) - Changing status to UP
[d7c] INFO  [2021-06-11 09:35:33] org.springframework.cloud.netflix.eureka.server.EurekaServerInitializerConfiguration.lambda$start$0(EurekaServerInitializerConfiguration.java:72) - Started Eureka Server
[d7c] DEBUG [2021-06-11 09:35:33] com.d7c.springboot.server.listener.EurekaInstanceListener.onApplicationEvent(EurekaInstanceListener.java:54) - 当前服务正常！
[d7c] INFO  [2021-06-11 09:35:33] org.springframework.integration.monitor.IntegrationMBeanExporter.registerChannel(IntegrationMBeanExporter.java:767) - Registering MessageChannel springCloudBus.anonymous.9OvjuqxvQYuOKO3bx4RGGA.errors
[d7c] INFO  [2021-06-11 09:35:33] org.springframework.cloud.stream.binder.BinderErrorChannel.adjustCounterIfNecessary(AbstractSubscribableChannel.java:64) - Channel 'application-1.springCloudBus.anonymous.9OvjuqxvQYuOKO3bx4RGGA.errors' has 1 subscriber(s).
[d7c] INFO  [2021-06-11 09:35:33] org.springframework.cloud.stream.binder.BinderErrorChannel.adjustCounterIfNecessary(AbstractSubscribableChannel.java:64) - Channel 'application-1.springCloudBus.anonymous.9OvjuqxvQYuOKO3bx4RGGA.errors' has 2 subscriber(s).
[d7c] INFO  [2021-06-11 09:35:33] org.springframework.integration.amqp.inbound.AmqpInboundChannelAdapter.start(AbstractEndpoint.java:159) - started inbound.springCloudBus.anonymous.9OvjuqxvQYuOKO3bx4RGGA
[d7c] INFO  [2021-06-11 09:35:33] org.apache.coyote.http11.Http11NioProtocol.log(DirectJDKLog.java:173) - Starting ProtocolHandler ["http-nio-9000"]
[d7c] INFO  [2021-06-11 09:35:33] org.springframework.boot.web.embedded.tomcat.TomcatWebServer.start(TomcatWebServer.java:202) - Tomcat started on port(s): 9000 (http) with context path ''
[d7c] INFO  [2021-06-11 09:35:33] org.springframework.cloud.netflix.eureka.serviceregistry.EurekaAutoServiceRegistration.onApplicationEvent(EurekaAutoServiceRegistration.java:145) - Updating port to 9000
[d7c] INFO  [2021-06-11 09:35:33] com.d7c.springboot.server.EurekaServerApplication.logStarted(StartupInfoLogger.java:59) - Started EurekaServerApplication in 36.026 seconds (JVM running for 37.735)
[d7c] INFO  [2021-06-11 09:35:43] com.netflix.eureka.registry.AbstractInstanceRegistry.run(AbstractInstanceRegistry.java:1250) - Running the evict task with compensationTime 0ms
```

2.  启动 d7c-springboot-client-web 应用 com.d7c.springboot.client.WebApplication.main()

```
*
*
*
[d7c] INFO  [2021-06-11 09:39:54] org.springframework.cloud.stream.binder.BinderErrorChannel.adjustCounterIfNecessary(AbstractSubscribableChannel.java:64) - Channel 'application-1.springCloudBus.anonymous.vKLjr4ZbSpqtr-7EiIDebg.errors' has 2 subscriber(s).
[d7c] INFO  [2021-06-11 09:39:54] org.springframework.integration.amqp.inbound.AmqpInboundChannelAdapter.start(AbstractEndpoint.java:159) - started inbound.springCloudBus.anonymous.vKLjr4ZbSpqtr-7EiIDebg
[d7c] INFO  [2021-06-11 09:39:54] org.apache.coyote.http11.Http11NioProtocol.log(DirectJDKLog.java:173) - Starting ProtocolHandler ["http-nio-8090"]
[d7c] INFO  [2021-06-11 09:39:54] org.springframework.boot.web.embedded.tomcat.TomcatWebServer.start(TomcatWebServer.java:202) - Tomcat started on port(s): 8090 (http) with context path ''
[d7c] INFO  [2021-06-11 09:39:54] org.springframework.cloud.netflix.eureka.serviceregistry.EurekaAutoServiceRegistration.onApplicationEvent(EurekaAutoServiceRegistration.java:145) - Updating port to 8090
[d7c] INFO  [2021-06-11 09:39:54] com.d7c.springboot.client.WebApplication.logStarted(StartupInfoLogger.java:59) - Started WebApplication in 25.781 seconds (JVM running for 26.844)
[d7c] INFO  [2021-06-11 09:40:04] com.netflix.discovery.DiscoveryClient.fetchRegistry(DiscoveryClient.java:976) - Disable delta property : false
[d7c] INFO  [2021-06-11 09:40:04] com.netflix.discovery.DiscoveryClient.fetchRegistry(DiscoveryClient.java:977) - Single vip registry refresh property : null
[d7c] INFO  [2021-06-11 09:40:04] com.netflix.discovery.DiscoveryClient.fetchRegistry(DiscoveryClient.java:978) - Force full registry fetch : false
[d7c] INFO  [2021-06-11 09:40:04] com.netflix.discovery.DiscoveryClient.fetchRegistry(DiscoveryClient.java:979) - Application is null : false
[d7c] INFO  [2021-06-11 09:40:04] com.netflix.discovery.DiscoveryClient.fetchRegistry(DiscoveryClient.java:980) - Registered Applications size is zero : true
[d7c] INFO  [2021-06-11 09:40:04] com.netflix.discovery.DiscoveryClient.fetchRegistry(DiscoveryClient.java:982) - Application version is -1: false
[d7c] INFO  [2021-06-11 09:40:04] com.netflix.discovery.DiscoveryClient.getAndStoreFullRegistry(DiscoveryClient.java:1065) - Getting all instance registry info from the eureka server
[d7c] INFO  [2021-06-11 09:40:04] com.netflix.discovery.DiscoveryClient.getAndStoreFullRegistry(DiscoveryClient.java:1074) - The response status is 200
```

3.  浏览器访问 http://127.0.0.1:8090/index

账号：wujialong
密码都是：000000

![首页](https://images.gitee.com/uploads/images/2021/0611/094801_f98caed2_1070311.png "QQ图片20210611094207.png")

## 捐助打赏

如果您觉得我们的开源软件对你有所帮助，请扫下方二维码打赏我们一杯咖啡。
![微信收款码](https://images.gitee.com/uploads/images/2021/0222/174352_b22739f5_1070311.jpeg "微信收款码.jpg")
![微信赞赏码](https://images.gitee.com/uploads/images/2021/0222/174521_67e18b39_1070311.jpeg "微信赞赏码.jpg")
![支付宝收款码](https://images.gitee.com/uploads/images/2021/0222/174540_94a9ac41_1070311.jpeg "支付宝收款码.jpg")

## 参与贡献

1.  Fork 本仓库
2.  新建 d7c_springboot_xxx 分支
3.  提交代码
4.  新建 Pull Request

## 码云特技

1.  使用 Readme\_XXX.md 来支持不同的语言，例如 Readme\_en.md, Readme\_zh.md
2.  码云官方博客 [blog.gitee.com](https://blog.gitee.com)
3.  你可以 [https://gitee.com/explore](https://gitee.com/explore) 这个地址来了解码云上的优秀开源项目
4.  [GVP](https://gitee.com/gvp) 全称是码云最有价值开源项目，是码云综合评定出的优秀开源项目
5.  码云官方提供的使用手册 [https://gitee.com/help](https://gitee.com/help)
6.  码云封面人物是一档用来展示码云会员风采的栏目 [https://gitee.com/gitee-stars/](https://gitee.com/gitee-stars/)

<style>p{text-indent:2em}</style>
