## 自我介绍

- 面试官好，我叫聂炜强，来自中南大学计算机学院数据科学与大数据专业。
- 在学习方面：我对后端开发有着浓厚的兴趣，现在完成java开发的常用技术栈的学习。平时比较喜欢研究底层原理、顶层架构、算法实现等方面的知识
- 在项目经历方面：除了简历上的，近期还学习并开发了两个项目，一个是高并发的抢购系统，一个是网关中间件项目

## 项目经历、基本情况。收获是什么？

- 在抢购系统中，性能方面：用redis作为缓存中间件，做了库存预热，预减库存（用分布式锁防止重复预减），并针对页面进行了缓存。安全方面：采用MD5算法进行加密，并隐藏抢购时的接口地址，同时设置验证码防止恶意代刷。 并发方面：进行了接口限流和借助消息中间件来异步处理下单，最后进行了压力测试，QPS从初始的没优化1500提升到了3500

- 网关项目目前还在开发中，有几个模块。

  核心通信模块采用的技术栈是neety，cglib，dubbo（只是作为数据源）。已经完成了通过CGlib动态代理来完成的服务提供源的泛化处理，能够适用多种通信方式来 获取服务如Dubbo、Http。进行了客户端请求的参数解析、会话管理。最后顺利通过网关完成了有参数、无参数方法的调用并返回结果给客户端的测试。

  服务注册模块（SpringBoot+Mybatis）：采用 DDD 模式管理业务领域，实现了网关、服务、接口的注册。  核心部分已经完成开发，后续一个月实现服务发现、路由分发、负载均衡等功能。

## 在项目中担任的角色

- 之前参加比赛开发一个微信小程序时，项目负责人。负责技术选型、ui设计，项目协调等工作

## 坚持最长的一件事

- 每日学习算法
- 跑步

## 意向城市

- 深圳

- 领略大城市的繁华。
- 交通方便
- 看看外面的世界

## 怎么学习后端技术

- 学校老师
- 视频
- 博客
- 社区

## 反问

- 后续还有什么流程吗？
- 工作地点，到岗时间
- 部门对实习生的培养计划是怎么样的

