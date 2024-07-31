## Maven

### java: 程序包lombok不存在。

- 描述
  - 依赖引了，插件也装了。pom不报错
  - 但是扩展库里找不到，@Data等直接爆红
- 原因
  - pom不报错是依赖已经下载了
  - 找不到是因为下载在了默认路径，而不是b盘的那个。B盘不全
- 解决
  - 设置idea的maven仓库为C:\Users\beilinanju\.m2\repository
  - 设置setting的仓库地址<localRepository>C:/Users/beilinanju/.m2/repository</localRepository>
  - 重启

- 结果
  - 事实证明不是这个的问题。新建一个maven项目来测试，用原来B盘的仓库也能正常使用lombok，没有也会自动下载
  - 纯粹扩展库加载不到依赖
- 最后
  - 重新创建工程，第一个就加lombok这个依赖
  - 总结：慢慢解决，虽然还有一些其他的依赖加载不到

### This failure was cached in the local repository

- 导致下载失败

  - .lastUpdate文件出现
  - 报错was cached in the local repository
  - 一旦这个文件存在，那么在直到下一次nexus更新以前都不会更新这个依赖库

- 原因

  - 原有jar包不存在
  - 网络原因
  - 镜像地址失效

- 解决

  - 删除后重新下载（清除了所有后springcloud的那个项目就有点报错。
  - 还在的话一定要手动去删除，自己对着路径去本地 仓库找
  - 去镜像地址直接找依赖
  - 修改setting.xml来强制更新

  ```pom
  <repositories>
          <repository>
                  <id>central</id>
                  <url>http://central</url>
                  <releases>
                          <enabled>true</enabled>
                          <updatePolicy>always</updatePolicy>
                  </releases>
                  <snapshots>
                          <enabled>true</enabled>
                          <updatePolicy>always</updatePolicy>
                  </snapshots>
          </repository>
  </repositories>
  ```

  - 最后可以试下换成阿里云镜像

### 关于依赖下载不到

- 有些依赖中央仓库能找到，阿里云镜像找不到。自己看着办 吧
- 关了阿里云镜像，用一个能上maven仓库的vpn下载
- 删lastUpdated，换镜像

### 有同名不同版本的依赖

- The class hierarchy was loaded from the following locations:
  - com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator: file:/B:/maven-LocalRepository/com/baomidou/mybatis-plus-core/3.4.1/mybatis-plus-core-3.4.1.jar

- 去项目结构libraries里删除重复的即可
- （好像不太行

### 项目结构中删除依赖时

- The root already belongs to module 'model'
- 删除对应模块就行

### 最终方案

- 直接使用spring initializr初始化一个项目。用start.aliyun.com
- 随便选个spring cloud和spring cloud alibaba的组件，然后在pom中就能找到对应的依赖版本管理
  - 只要是在这里管理的依赖，用的时候直接加，不用加版本号
  - 没有找到的，则自行选择版本
- idea的maven设置用的是b盘仓库。setting.xml并没有配阿里云镜像。挂了vpn
- 在原工程下在创建子模块就行了
  - 父工程留公共的依赖
  - 子工程要什么就自己加
- 不要选什么nacos，除非就做nacos，不然真的又多了一个服务，还不知道怎么删除
- 整得原服务都找不到了404（明天重建service，不要nacos

### 下载好了依赖却迟迟不导入扩展库

- 刷新并在等等

### 一些依赖问题

- Swagger2
  - <version>2.10.5</version>
  - 这个版本找不到注解@EnableSwagger2
  - 用@EnableSwagger2WebMvc试下
    - 不太行，会出现Unable to infer base url. This is common when using dynamic servlet registration or when the API is behind an API Gateway. 
  - 用2.9.2。加上@EnableSwagger2，完美解决



## SpringBoot

### 启动类

- 不能在根路径java下，至少要放在一个包中
- 父子工程都有启动类，启动父工程好像会在子工程生成target，目前就不要父工程放启动类了

### MVC分层

- 正确
  - 之一
    - model只放entity的定义（模型层
    - service放mapper，xxxMapper.xml，service  因为要处理逻辑，所以在这里访问数据库没啥毛病
      - service层也放controller，因为好像不同模块的无法@Autowrid（下个已解决

### 不同模块间的@Autowired

- https://blog.csdn.net/weixin_42326851/article/details/123894504

  - 修改SpringBoot的组件扫描范围 

- 关于ComponentScan

  - 默认扫描启动类所在目录及其之下的所有组件
  - 修改@ComponentScans(@ComponentScan("com.beiyuan"))

  - 导入其他模块后，其他模块（及其子模块）的代码相当于直接复制到当前模块，所以建议有相同的包名结构，方便扫描

### 出现Process finished with exit code 0

- 没有加spring-boot-starter-web的依赖

## IDEA

### 无法创建项目

- 点击创建项目idea就死机
- 解决
  - 剪切掉当前项目，然后从idea的开始界面创建
  - （记得把项目放回原处

## Note

### 创建Service层接口及其实现类是MybatisPlus的内容

