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



### 22. d7c_springboot_flowable

该模块提供了访问 flowable 技术进行工作流操作的公共代码。

## 使用说明

1.  xxxx
2.  xxxx
3.  xxxx

## 运行展示

```

```

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
