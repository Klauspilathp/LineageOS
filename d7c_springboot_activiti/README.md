# d7c_springboot_activiti

#### 介绍
d7c 项目 Spring Boot 版 activiti 模块。

#### 软件架构
该模块采用  activiti7 进行工作流  API 接口管理，为 d7c_springboot_web 模块的工作流部分提供接口，在 d7c_springboot_oauth2 模块进行认证。

#### 安装教程

1.  先启动 d7c_springboot_eureka_server 模块监听服务注册；
2.  再依次启动 d7c_springboot_oauth2、d7c_springboot_activiti、d7c_springboot_web、d7c_springboot_gateway；
3.  访问 127.0.0.1:8081/web。

#### 使用说明

1.  启动该模块，该模块就会为 d7c_springboot_web 模块提供操作 activiti 数据的 API 接口；
2.  访问该模块的路径为 http(s)://IP:PORT/activiti/**。

#### 测试流程

1.  在数据库中分别为

1.  分别启动 d7c_springboot_gateway、d7c_springboot_oauth2、d7c_springboot_activiti 模块；
2.  

#### 参与贡献

1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request
