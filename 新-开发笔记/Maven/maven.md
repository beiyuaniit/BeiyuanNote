## Maven

- 作用

  - 管理依赖，自动下载jar和其源码

  - 一个自动化构建工具。帮助完成项目的清理、编译、测试、打包、安装、部署等工作


- 安装
  - 从apache官网下载bin.zip，解压
  - C:\Program Files\Java\apache-maven-3.8.5\bin。添加到path
  - mvn -v测试是否成功

## 约定的目录结构

```java
Hello					//项目名
    \src		
    	\main			//主程序
    		\java
    		\resources	//配置文件
    	\test			//测试
    		\java
    		\resources
    \pom.xml			//maven的配置文件
```

## 命令行使用Maven

```java
//最少创建src、main、pom.xml即可,在main文件夹编辑java代码

//在pom.mxl所在目录使用mvn complile进行编译，生成的.class会放在新建的target目录
```

## POM

- 项目对象模型，通过pom.xml实现项目的构建和依赖管理

```xml
<project> <!-- 根标签，.xsd是约束文件，限定了pom.xml中标签的定义规则-->
    
<!-- POM版本目前只有这个值 -->
<modelVersion>4.0.0</modelVersion>
    
<properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>//项目构建时编码
    <maven.compiler.source>1.7</maven.compiler.source>//编译时jdk版本
    <maven.compiler.target>1.7</maven.compiler.target>//运行时
    
    <!--可以定义全局变量(如用来统一管理版本-->
    <spring-version>5.2.5.RELEASE</spring-version>
</properties>
<!--使用全局变量-->
 <dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-core</artifactId>
    <version>${spring-version}</version>//就是EL表达式吧
</dependency>

```

## 坐标

- 唯一标识资源(jar)

```xml
<!--坐标gav -->
<groupId>com.beiyuaniit</groupId>
<artifactId>myMavenProject</artifactId>
<version>1.0.0</version>
<packaging>jar</packaging> 打包类型jar、war、rar、ear、pom.默认jar


groupId
	组织名称.通常域名倒写
artifactId
	项目名称，应是唯一值
version
	三位数字如2.3.1。主版本.次版本.小版本
	如果有SNAPSHOT，表示快照，不是稳定版本

使用
	项目自己要有gav
	管理依赖要用到gav(<dependency>gav</dependency>
```



## 依赖

- 项目中要使用的资源(jar)
- maven根据dependency从互联网下载相关依赖

```xml
<dependencies>
<dependency>
    
<!-- https://mvnrepository.com/artifact/mysql/mysql-connector-java -->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.29</version>
</dependency>

</dependency>
</dependencies>
```

## 仓库

- 存放
  - maven自己的jar包
  - 第三方jar，如mysql驱动
  - 自己写的jar
- 分类
  - 本地仓库
    - 默认路径C:\Users\beilinanju\.m2   (IDEA的也是
    - 修改路径
      - 仓库还是不放C盘。权限问题
      - 找到C:\Program Files\Java\apache-maven-3.8.5\conf\setting.xml
      - 找到53行，解开注释，添加<localRepository>B:/maven-LocalRepository</localRepository>
  - 远程仓库
    - 中央仓库
      - https://mvnrepository.com    
      - 最权威，访问不了的话先开下VPN(也不太行。点击就是复制了。
        - 好像可以，挂vpn去找依赖，下载时也不用关vpn
      - QQ和Chorm都废了，官网总是要验证。直接Edge
      - 问就是校园网的问题，换成手机热点，直接打开
      - 如果Cannot resolve某个jar，建议手动删除后重新下载
    - 镜像仓库
      - 中央仓库的备份，减轻中央仓库服务器的压力。
    - 私服仓库
      - 在公司内部，局域网使用，不对外公开
- 添加阿里云镜像

```xml
在setting.xml中添加
<mirror>
  <id>alimaven</id>
  <name>aliyun maven</name>
  <url>http://maven.aliyun.com/nexus/content/groups/public/</url>
  <mirrorOf>central</mirrorOf>
</mirror>
```

## 生命周期

- 项目构建的各个阶段，包括清理、编译、测试、报告、打包、安装、部署

## 命令和插件

- 完成maven生命周期的各个阶段，要执行命令。而命令是通过插件来完成的

```java
mvn clean   //清理。删除已编译的.class，即target目录
    对应插件maven-clean-plugin
mvn compile	//编译主程序。创建target目录，生成的字节码文件放到target/classes(类路径)
    maven-compiler-plugin	编译
    maven-resources-plugin	拷贝资源到类路径
mvn test-compile //编译测试程序。同上。类路径target/test-classes
	插件同上
mvn test		//执行测试程序(target/test-classes中的.class。生成surefire-reports目录用.txt保存测试结果
    maven-surefire-plugin
mvn package		//打包。打包的是.class和配置文件。生成的包放在target/。默认jar，其他类型如war也可以。包名和项目gav有关。
    maven-jar-plugin
mvn install    //把生成的jar包安装(放)到本地仓库。路径和gav有关
    maven-install-plugin
mvn deploy		//部署。打包工程，保存到本地仓库和私服仓库，最后把项目部署到web服务器
```

- 部署
  - 打包成war包，把war扔到tomcat的webapps目录，启动服务器即可
  - 服务器会解压war(不删除原war),然后运行解压后文件

- 插件配置
  - 自定义一些插件内容

```xml
<!--pom.xml中设置-->
<build>
<plugins>
    <plugin>
    <!--一个plugin标签代表一个插件-->
    <!-- 定义jdk版本。IDEA还要不要配置?应该不用了吧。这好像是命令行的。。-->
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-compiler-plugin</artifactId>
    <version>3.8.1</version><!--现在不知道-->
    <configuration>
        <!--以前不配置的话默认1.7，现在不知道-->
    	<source>1.8</source>   <!--编译时jdk版本--> 
        <target>1.8</target>   <!--运行-->
    </configuration>
    </plugin>
    
</plugins>
</build>
```

## 单元测试(Junit)

- Junit是一个单元测试工具。一个方法就是一个测试单元
- maven的test目录结构还是和main一致，不然容易找不到类

```java
//步骤

加入junit依赖。(可去仓库官网搜'junit')

定义测试方法
    注解@Test
    权限public 
    返回值void
    没有参数
@Test
public void testAddNum(){
    Solution Sol=new Solution();
    int res=Sol.addNum(2,3);
    Assert.assertEquals(5,res);//测试并控制台输出测试信息。idea的maven测试正确好像不打印。绿色就表示通过
} 
    
测试方法可以单独执行
```

## IDEA使用Maven

- idea自带有一个maven，但是改配置文件麻烦，所以用自己的
- idea2020得用3.63及以前得版本
- 没有的resources可以自己补上，并Mark Driectory as->Resources Root

```java
//找到配置的地方
File->Setting->Build,Execution,Deployment->Build->Maven

//设置如下
Maven home directory:C:/Program Files/Java/apache-maven-3.6.2   //安装目录
User setting file:C:\Program Files\Java\apache-maven-3.6.2\conf\settings.xml  //配置文件，选择Override
Local repository:B:\maven-LocalRepository	//本地仓库，也选择Override
    
//点开Maven->Runner
JRE:设置下JDK，20的最高选14吧
VM Options:-DarchetypeCatalog=internal//告诉maven，创建项目时，不去下载archetype-catalog.xml到仓库(下载可能很慢)，加快创建速度。现在不设置看下
    
//上面的让idea关联了自己maven。为了对新建和导入项目时也生效，还要在下面的地方做相同的配置
File->New Projects Setting->Settings for New Projects
```

- maven的可视化窗口

```java
//和Database一样。在右侧有个工具栏
//Lifecycle   生命周期命令
双击即可执行
 
//Plugins  插件。有更加详细的功能
双击可执行
    
//Dependencies		把pom.xml中的依赖关系一层层显示
上方2个箭头向上的按键设置可以用图来展示依赖关系
```

##  maven项目

- 创建
  - 可以用maven创建项目或模块。跟之前创建JavaWeb项目或模块一样
  - 算了。还是直接建maven项目。maven模块就另说吧（先建空工程再添加maven模块会出点问题，现在没问题了，可以使用。但是创建的模块和项目同一级目录。。


```java
//创建 。根据原型（模板）创建    archetype--原型
Maven->选择jdk->Create form archetype->选择原型
    
//原型
//org.apche.maven.archetypes下
maven-archetype-quickstart     //普通javaee项目

maven-archetype-webapp	//web项目，记得设置下打包类型为war。加依赖好像就行.不是空工程下的模块好像设置不了，也就不能单独打包?	
```

- 导入模块
  - 此处导入的是maven模块，其他类型的同理

```java
//右上角有个图标，图标右下有个三角形。这个工程结构。
Project Structure->Modules->加号->import Module->Import module from external model->Maven->...
```

## 依赖作用域

| scope    | compile | test | provided |
| -------- | ------- | ---- | -------- |
| 主程序   | yes     | no   | yes      |
| 测试程序 | yes     | yes  | yes      |
| 参与打包 | yes     | no   | no       |
| 参与部署 | yes     | no   | no       |

- 默认是compile，可以通过scpoe标签改变
  - compile：参与各阶段
  - test只是测试时起作用 
  - provided： 提供者提供。如jsp,servlet依赖由服务器提供。该权限的依赖版本最好和提供者的一致

```xml
    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>4.11</version>
      <scope>test</scope>
    </dependency>
```

## 处理配置文件

- 默认
  - 把resources目录下文件的拷贝到target/classes
  - 不处理(拷贝)main目录下除了.java的其他文件
- 修改
  - 有时候如写StudentDao.java时把StudentDao.xml放在了main下的同一目录。这时就无法拿到.xml了

```xml
<build>
    <!--资源插件-->
	<resources>
    	<directory>src/main/java</directory><!--要处理的目录-->
    	<includes>
        	<include>**/*.properties</include><!--同理-->
        	<include>**/*.xml</include><!--处理.xml结尾的文件。拷贝-->
     	</includes>
        <filtering>false</filtering><!--不启用过滤器-->
    </resources>

</build>
```

