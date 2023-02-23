<img src="logo.png" style="zoom:80%;"/>

<div align="center">
<h1>SimpleFramework</h1>
</div>

<div align="center">
<b>造轮子项目：从头实现Spring框架</b>
</div>

<div align="center">
<img src="https://img.shields.io/badge/Java-1.8-orange"/>
<img src="https://img.shields.io/badge/CGLIB-3.3.0-green"/>
<img src="https://img.shields.io/badge/AspectJWeaver-1.9.19-yellowgreen"/>
<img src="https://img.shields.io/badge/javax.servlet.jsp--api-2.3.3-blue"/>
<img src="https://img.shields.io/badge/javax.servlet--api-4.0.1-lightgrey"/>
<img src="https://img.shields.io/badge/Gson-2.8.9-yellow"/>
<img src="https://img.shields.io/badge/Slf4j--log4j12-1.7.36-yellow"/>
<img src="https://img.shields.io/badge/Lombok-1.18.24-blue"/>
</div>

## 项目简介

> Spring框架家族在Java开发中居于霸主地位，不仅在于其简单易懂，功能强大，更在于其使用了大量的设计模式，规范的项目架构，再加上开源免费，让众多的开发者可以从中学习，提升自己，为Java开发带来了“春天”

在我看来，用少量的基础技术工具类实现一个Spring框架应该是每个Java开发者的梦想，所以才有了这个项目

**SimpleFramework是一个开源的项目，任何企业和个人可以免费学习使用**

- 本项目实现了一个**简易版本的Spring框架**，实现了其三大核心功能：**IOC，AOP，MVC**
- `core`包实现了框架的核心功能：`bean`的扫描加载，容器的维护，单例模式实现
- `Inject`包负责依赖注入：`autowired`注解以及单例模式注入以及接口注入实现类
- `aop`包遵循面向切面编程思想：`Aspect`和`Order`注解负责切面类标识排序，通过cglib动态代理以及aspectjweaver织入横切逻辑，实现动态修改方法逻辑
- `mvc`包对应于请求分发相关功能：重构`DispatcherServlet`,实现`RequestProcessorChain`责任链`RequestProcessor`矩阵以及`ResultRender`矩阵，完成多种请求的处理与响应

## 项目架构图



## 项目用到的技术



## 如何使用本项目

- **方法一：**

下载源码，导入Idea，可以在demo目录下进行相关的测试（与使用Spring Boot开发项目基本相同）

- **方法二：**

将target目录下的jar包导入自己的项目即可，注意依赖冲突问题
