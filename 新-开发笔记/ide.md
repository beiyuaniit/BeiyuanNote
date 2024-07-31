# ==引言==

- 快捷键不生效，可能是被其他程序占用了

# ==Dos命令行==

## ipconfig

- 查看ip

## del

- 删除

## cls

- win10命令行清屏

# ==Win10==

## Alt+Tab

- 切换程序窗口

## Win+左边数字

- 打开任务栏应用或者最小化

## Ctrl+Win

- 关闭当前窗口

## Win+上下键

- 最大/中等/小化当前窗口

## cls

- 清屏

## 拖动文件

- 直接拖动
  - 文件和目标目录在一个磁盘分区，是移动；不在同一磁盘分区，是复制并粘贴
- 配合按键
  - 拖动时按住shift键——移动
  - 按住ctrl键——复制
  - 按住alt键 或 同时按住ctrl和shift键——创建快捷方式

# ==Pycharm==

## Alt+F1+1

- pycharm定位当前编辑的文件在Project目录中的位置
- 或者直接点下Project窗口右上方的瞄准图标也可以，这个更快

# ==Idea==

## 插件

- 通义灵码
- jclasslib-查看字节码

## Alt+左键

- 行编辑

## Ctrl+D

- 复制粘贴当前行到下一行

## Ctrl+Y

- 删除当前行
- 谨慎使用

## Ctrl +/

- 注释与反注释某行
- 可注释选中代码块

## Ctrl+i

- 实现方法。实现接口时

## Ctrl+o

- 重写方法。继承时

## Ctrl + 减号

- 折叠当前方法

- Ctrl+Shift+减号：折叠类中所有方法

## Ctrl+等于

- 展开当前方法
- Ctrl+Shift+等于：展开所有方法

## Ctrl+y

- 删除当前行

## Ctrl+Tab			

- 唤出Switcher窗口。可以切换java程序或者其他窗口

## Ctrl+F4

- 关闭当前的那个代码文件

## Ctrl+Shift+F12

- 关闭或打开Project窗口

## Ctrl+Shift+r

- 可以在整个项目查找并替换，不能在源码找。可用于全局查找（找方法变量啥的

## Ctrl+H

- 查看接口的父接口、子接口、实现类，类的子类、父类。（继承关系
- 在某个接口或类中使用

## Ctrl+鼠标点击

- 在xml文件中也可以查看某个类。如某些servlet-class

##  Ctrl + shift + alt + U

- 以图的方式显示项目中依赖之间的关系。

## Ctrl+w+Backspace

- 自创
- 删除一个单词

## Ctrl+G

- 定位到某行

## Ctrl+左{右}

- 跳到大括号开头、结尾

## Ctrl+F

- 文件内查找

## Crtl+R

- 文件内替换

## Ctrl+Alt+左方向键

- 光标回到上一次编辑的位置

## Ctrl+Alt+V

- 抽取成变量 value
- 如 new Calculator（）；

## Ctrl+Alt+M

- 将选中的代码抽成一个方法 method
- 好像还挺实用的

## Ctrl+Alt+F

- 抽取成成员变量 filed

## Ctrl+Alt+N

- 提取变量，更加简洁

```java
public int func(){
    int a=9;
    return a;
}

// 在int a = 9;这行按下。变成
public int func(){
    return 9;
}
```

## Ctrl+Alt+L

- 格式化代码（不格式空行）

## Ctrl+Alt+U

- 以UML的类图展现类有哪些继承类，派生类以及实现哪些接口。空格增加类
- 快捷键是开个小窗口，可能不太方便查看。建议直接定位到该文件 -> 右键 ->Diagrams-> ...。之后可以右键添加类啥的

## Ctrl+Alt+T

- 把选中的代码用代码段包起来。Surround With   如：if、if/else、for、while、try/catch、{}、synchronized等
- 这个也很好用

## Ctrl + Alt + Shift + T

- 选中后
- 功能很多，比如引用为变量、函数接口等

## Ctrl+F12

- 小窗口查看类的属性和方法

## Ctrl+N

- 全局查找类、接口等
- 2个Shit也可以

## Ctrl+W

- 选中一个单词
- 连续按w会依次会选中单词-整个字符串-整个字符串包含引号-整个字符串赋值语句-整行-整个方法体

## Alt+Shitf+鼠标

- 列编辑，直接操作一列

## Alt+Insert

- 自动生成set()、get()等方法

## Alt+回车

- 代码纠错

- Alt+回车+Introduce variable
  - 引入变量

## Alt +左右方向键	

- 切换java程序

## Alt+上下方向键		

- 以函数为单位上下翻

## Alt+数字			

- 切换该标号的窗口

## Alt+7

- Sructure窗口

- 简易查看类、接口等的结构（属性，方法。有向上的箭头是重写或实现了类或接口的方法

## Shift+Shift

- 全局搜索.可用于在整个工程搜索类

## Shift+home/end	

- 选中当前行光标前面/后面部分（行开头或末尾则是一行）

## Shift+方向键

- 选中

## F12

- 从编辑窗口跳到Project窗口

## F4

- 定位变量或进入方法内部
- 相当于按住Ctrl、鼠标左键点击某个变量、方法

## Esc

- 退出任何开的窗口
- 从Project回到编辑窗口

## "str".sout

- 等价于Sysetm.out("str")

## sout	

- 方法体中使用生成System.out.println();

## obj.notnull

- 等价于if(obj!=null)

## 双击

- 选中单词

## Debug

- 一竖一个箭头
  - 走到下一个断点
- 方法栈

  - Debugger窗口的方法下面的是断点处的方法栈中的方法，也就是已经执行到、还未执行完的方法。每执行完一个方法就出栈一个，调用过一个方法就入栈一个	
- 用于追踪代码的执行，十分的有用
- 在断点处计算一些结果
  - 选中代码->右键->Evaluate Expression(Alt +F8)->得到表达式结果
  - 那执行过的代码呢？
- 2个点重叠
  - 查看整个项目打过的断点，可以取消
- 点加一个斜线
  - 好像是禁用所有断点


## Project

- Alt+方向左右
  - 隐藏或者展开项目

## “c”变'c'

- 选中"c"，点击单引号键即可''

## 小图标

- 右上角从右往左数第三个：project structure
  - 可修改目录属性。如资源目录，源文件目录

## 全类名

- 类文件中，右键->copy reference

## properties

- 用#注释

## project窗口右上角的小齿轮

- Compact Middle Package
  - compact：紧凑的。勾上的话会出现abc.xyz之类的目录结构。。。
  - 取消
- Show  Members
  - 在project可以显示类中的方法，建议勾上

## 添加模板

- File->Settings->Editor->File and Code Templates->File
  - 之前一直在Includes所以不成功，是Files

## 分号;

- 即使光标在代码中间，直接输入分号;也是给末尾添加

## 左括号(

- 即使光标在代码中间，直接输入左括号(也是给末尾添加.如while (node!=null { )   for好像不太行 目前好像只能while

## 启动同一.java

- 运行的三角图标旁边->Edit Configurations->Allow parallel run->Apply就行了。
- 代码不一定要相同，可以修改后再运行（提高区分度

## controller测试

- 启动项目后，点击请求映射方法左边的图标（open in http client），能够发送请求进行测试

# ==Browser==

## Ctrl+shift+delete

- 唤出清除浏览记录窗口。如cookie等信息

## 鼠标停留在超链接上

- 左下角会提示超链接的具体信息

## 搜索

- 在页面用Ctrl+F可搜索关键字

## PDF转word

- 文字PDF用QQ浏览器打开，标签栏有格式转换（限免。。
- 不是特别理想，凑合着用吧

# ==Mysql==

##  describe table;

- 查看字段数据类型

```sql
mysql> describe student;
+-------+--------------+------+-----+---------+----------------+
| Field | Type         | Null | Key | Default | Extra          |
+-------+--------------+------+-----+---------+----------------+
| id    | int(11)      | NO   | PRI | NULL    | auto_increment |
| name  | varchar(255) | YES  |     | NULL    |                |
| sno   | varchar(255) | YES  |     | NULL    |                |
| age   | int(11)      | YES  |     | NULL    |                |
+-------+--------------+------+-----+---------+----------------+
```

# ==VSCode==

## Shift+Alt+F

- 格式化代码

## 选中补全

- TAB、空格、回车都可以了。空格会追加一个空格

## Shift+Alt

- 列编辑

# ==Typora==

- 不知道的话，直接看导航栏。都有

==标题==

# 一级标题

## 二级标题

==字体==

**加粗**

*斜体*

***斜体加粗***

~~删除线~~

我是^上标^

我是~下标~

==列表==

+ 一二三四五
+ 上山打老虎

- 加号减号都行，减号更加方便 

==表格==

| name | age  |      |      |
| ---- | :--- | ---- | ---- |
| 423  | 313  |      |      |

==引用==

> 一二三四五

> > 上山打老虎

==代码==

`单行代码`

```语言
可以选择语言，按下方向键可以走出
```

# ==Word==

## Ctrl+B

- 字体加粗

## 大纲

- 视图->导航窗格显式

# ==XShell==

## 多个连接

- 同一个连接同一账户可以创建多个shell客户端，文件->打开->连接就好了

# ==Clash==

## 安装位置

- clash要放在一个不需要管理员权限的盘