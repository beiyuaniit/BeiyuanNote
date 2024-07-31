# ==基础==

## 随笔

- 类体中不能直接写语句
- SE类库字节码：jdk\jre\lib\rt.jar;
- SE类库源码:jdk\src.zip;

## 命令行

- 网址：https://docs.oracle.com/javase/8/docs/technotes/tools/

  ```shell
  # 编译
  javac JwtUtil.java
  # 与通配符使用，编译以welcome开头的
  javac Jwt*.java 
  # 即使中文出现在注释中也可能出错.因为javac是GBK编码
  javac -encoding UTF-8 HelloServlet.java//解决
      
      
  # 运行 
  java HelloServlet # 包名要和当前文件路径一致。
  # main函数参数。IDEA时用Run->Edit Config…->Program arguments
  java HelloServlet  abc xyz # abc 、xyz可以在String []args拿到
  
  # 反编译 
  javap -p -v HelloServelt.class # 查看字节码
  
  
  # shell
  jshell # 和python那个shell差不多
  
  
  # java监控和管理控制台，可以查看JVM的运行信息
  jconsole
  
  
  # 生成整个项目文档，放在docs/api目录，-overview指定了概要文件
  javadoc -d docs/api -overview overview.html com.example.*
  
  
  # 查看进程号、进程名
  jps
  
  
  # 进程的栈信息
  jstack <pid>
  jstack 18018
  
  
  # 生成堆内存快照dump
  # 可以直接在idea中双击点开(Profiler窗口)，也可以用其他工具
  jmap -dump:format=b,file=ServerExample.hprof  18028
  # idea打开时的一些图例
  - Class:
  - count: 类的实例数量
  - shallow:
  - retained:
  - Biggest Objects:
  - GC Roots:
  - Merged Paths:
  - Summary:
  - Packages:
  ```

## 变量

- 命名规则
  - 不能以数字开头。
  - 驼峰命名法
  - 局部变量会覆盖同名成员变量
- 默认值
  - 成员变量在创建时不赋值的话会有默认值（0或者null）
  - 局部变量不会赋默认值。
  - 成员变量系统有默认值（一般是0类），局部变量没有默认值

- var关键字
  - java10开始，如果初始值能推出类型，可以使用var声明局部变量，而无须指定类型。

  ```java
  var n = 2;
  var harry=new Employee();
  //注意，应该在方法内声明。一般也不对基本类型用var
  ```

## 基本类型

- java强类型语言，通过类型限制变量的持有值、操作。

- 基本数据类型范围

  - 类型占用空间和平台无关，java已经屏蔽低层细节
  - 可去包装类型的MAX_VALUE，MIN_VALUE查看
  
  ![2024-06-08_172201](img\2024-06-08_172201.png)
  
- 类型转换

  - 自动类型转换
  
    - 红色箭头末尾还是会有精度损失
    
    ![2024-06-08_193621](img\2024-06-08_193621.png)
    
  - 强制类型转换
  
    - 大转小
  
    - long a=100L; int b=(int)a;
  
      ```java
      // 前面加(类型)
      Cat cat=(Cat) animal;
      ```
  
  - 类型提升
  
    - 算数表达式中包含多个 类型时
  
    - 自动提升为表达式中最高类型后再计算
  
    - byte、char、short自动提升为int
  
      ```java
      shortValue = charValue + shortValue; // 报错，应该用int接收
      shortValue = shortValue + shortValue; // 也报错，即使都是short，但也会先转成int再计算
      ```

- 注意事项

  - byte、char、short运算时，结果取int。防止溢出
  
- float和double存储格式

  ![float和double存储格式](img\float和double存储格式.png)

- example

  ```java
  - 基本数据类型
    - 数值型
      - 整数类型
        - byte
        - short
        - int
        - long
          - 以l或者L结尾
          - long x=32L;
      - 浮点类型
        - float
          - 以f或者F结尾，不加则是默认的double
          - float f=3.2F
          - 23位二进制尾数，2的负23次方，约等于1.1920929e−7。大约能够精确到6-7位十进制的小数
        - double
          - 小数默认类型
          - 52位二进制尾数，约能精确到15-16位小数
    - 字符型
      - char
        - 2个字节，可表示为中文。
        - 用单引号括起来''
    - 布尔型
      - boolean
          - 与0和1不等效
  - 引用类型 (默认为null)
    - 类
      - class
    - 接口
      - interface
    - 数组
    - 枚举
    - 注解
  
  
  //向下转型
  
  //整数默认是int
  byte b=2;//声明时已经byte b=(int)2;??
  //b=b+3;//无法通过编译。3是int。大转小要强制类型转换
  b=(byte)(b+3);
  
  //小数2.3默认是double
  float f=2;
  f= (float) (f+2.3);
  
  
  /*八种数据类型中除了boolean外七种可以转换
      小容量转换为大容量（自动类型转换）
  		byte<short char<int<long<float<double
  	大转小：强制类型转换，但是可能精度损失
  	byte 0~255
  */
  //以下优点特殊
  byte b=8;
  char c= (char) b;
  byte bb= (byte) c;
  short s= (short) c;
  short ss=b;
  
  //char是2个字节。可以存储中文
  char c='看';
  
  /*长整型可以只写long x=231;但是不能long y=2147483648;及更大的数;因为后面 数字是默认整型int
  可以改为long y=2147483648L;*/
  
  
  // int相加溢出问题
  @Test
  public void testSol(){
      int a =Integer.MAX_VALUE;
      int b =Integer.MAX_VALUE-1;
      long c = a+b; // 溢出
      long d = (long)(a+b); // 溢出
      long e = (long)a+b;
      long f = (long)a+ (long)b; // 推荐
      
      System.out.println(a); // 2147483647
      System.out.println(b); // 2147483646
      System.out.println(c); // -3
      System.out.println(d); // -3
      System.out.println(e); // 4294967293
      System.out.println(f); // 4294967293
  }
  ```

## 枚举

- enum

  - java的一个关键字。用来定义枚举类型
  - 单实例

- Enum

  - 一个抽象类，本身不能来new对象。用enum关键字定义的类默认继承该类。

  ```java
  package java.lang;
  
  public abstract class Enum<E extends Enum<E>>
          implements Constable, Comparable<E>, Serializable {
  	//在Enum中有以下属性
  	private final String name;
  	private final int ordinal;
  	
  	//获取方法
  	public final int ordinal() {return ordinal;}
  	public final int name() {return name;}
      
      protected Enum(String name, int ordinal) {
      	this.name = name;
      	this.ordinal = ordinal;
      }
      // and so on...
  }
  ```

- 原理示例

  ```java
  // 定义一个枚举。创建时甚至不需要;结尾 
  public enum Num{
       ONE,
       TWO
  }
  
  
  //  反编译（部分代码） 类中包含了多个同类对象
  final class Num extends Enum{//final修饰。不能被继承
      //私有构造函数。
      private Num(String s,int i){
          super(s,i);
      }
      
      /*
      static保证不用创建Num类就能访问已经创建好的对象
      就是用类来访问对象。
      */
      public static final Num ONE;
  	public static final Nnm TWO;
      
      // static只加载一次。确保了不会嵌套创建对象
      static{
          ONE=new Num("ONE",0);
          TWO=new Num("TWO",1);
          
          $VALUES=(new Num[]{
              ONE,TWO
          });
      }
  }
  
  // test
  public static void main(String[] args) {
      //int num=new int[Num.ONE]; // 不能直接当int使用。。因为这是一个类
      System.out.println(Num.ONE.ordinal()); // 0
      System.out.println(Num.TWO.ordinal()); // 1
      System.out.println(Arrays.toString(Num.values())); //[ONE, TWO]
  }
  
  public enum Num {ONE,ONE};//当然它们是不同的对象
  ```

- 正常使用示例

  ```java
  public enum Season {
      // 定义四个枚举常量，代表四季
      SPRING("春天", "温暖"),
      SUMMER("夏天", "炎热"),
      AUTUMN("秋天", "凉爽"),
      WINTER("冬天", "寒冷");
  
      // 成员变量：每个季节的名称和特点
      private String name;
      private String description;
  
      // 构造方法，枚举类型的构造方法必须是私有的
      // 已经默认为private
      Season(String name, String description) {
          this.name = name;
          this.description = description;
      }
  
      // Getter方法
      public String getName() {
          return name;
      }
  
      public String getDescription() {
          return description;
      }
  
      // 可以添加其他方法，如根据名字获取Season实例等
      public static Season fromName(String name) {
          for (Season season : Season.values()) {
              if (season.getName().equals(name)) {
                  return season;
              }
          }
          throw new IllegalArgumentException("无效的季节名称: " + name);
      }
  }
  
  
  @Test
  public void testSol(){
      Solution sol=new Solution();
      // 直接引用枚举常量
      Season currentSeason = Season.SPRING;
      System.out.println("当前季节是: " + currentSeason.getName() + "，特点是: " + currentSeason.getDescription());
      // 遍历所有枚举值
      for (Season season : Season.values()) {
          System.out.println(season);
      }
    
      // 根据名称获取枚举
      try {
          Season foundSeason = Season.fromName("夏天");
          System.out.println("找到的季节是: " + foundSeason);
      } catch (IllegalArgumentException e) {
          System.err.println(e.getMessage());
      }
    
      Season season = Season.SUMMER;
      switch (season) {
          case SPRING -> System.out.println("春天来了，万物复苏！");
          case SUMMER -> System.out.println("夏日炎炎，注意防晒！");
          case AUTUMN -> System.out.println("秋风送爽，收获的季节！");
          case WINTER -> System.out.println("冬天已至，保暖最重要！");
          default ->System.out.println("未知的季节...");
      }
  }
  ```



## 数组

- 概述

  - 也是一个对象

    - 用Arrays类的方法 可对数组进行操作

    - 作为实参时是引用传递


  - 创建和访问

      - 作为字段或局部变量都有默认值，可以不用初始化。

- 代码

     ```java
     //只是声明了一个int[]类型的变量
     int[]a;
     
     //创建并静态初始化
     int []a={1,2,3};
     int []a=new int[]{1,2,3};
     
     //创建，并在之后动态初始化
     int[]a=new int [100];//这才是创建一个数组
     int[]a=new int[n];//n不能是未初始化的n
     
     //匿名数组
     new int []{1.2.3};//不能在任意位置创建。目前来看可以用来作为方法实参
     
     //对象数组。不用()
     Set []sets=new HashSet[3];//声明一个变量
     for(int i=0;i<3;i++){//还得初始化才能用
        sets[i]=new HashSet();
     }
     
     //访问。可以用下标，不像字符串
     a[1];
     a[0]=3;
     
     // 多维数组
     // java实际上没有多维数组，底层是一个数组的数组。所以可以出现不规则数组
     int arr[][]= new int [3][];//行必须有值，列可暂时没有
     double [][]nums=new double[m][n];
     double [][]nums={{1},{1,2}};//创建了2个一维数组对象nums[0],nums[1]
     
     
     // Arrays工具类
     
     // 打印
     // 简单的话直接同String
     // 只是a.toString()的话会打印在内存中的地址
     System.out.println(Arrays.toString(a));
     
     // 拷贝
     /*
     数值型，额外元素赋值为0，布尔型，额外元素赋值为false（好像并不会）
     位置不够时，只拷贝前面的元素
     */
     int []b=a;//2个引用指向同一对象
     int []b=Arrays.copyOf(a,n);//创建一个新的数组。n是长度，或者说希望拷贝的个数
     Arrays.copyOfRange(a,1,4);
     
     // 查找
     Arrays.binarySearch(a,3);
     
     // 判断相等
     Arrays.equals(a,b);
     
     // 排序
     /*
     比较其他类型则要实现比较器接口
     只有升序。没有其他方法实现降序。如果想降序访问，通过下标就能很好实现，所以就没有降序？？
     */
     Arrays.sort(a)
     ```

- 数组元素默认值
  - 引用类型有很多。数组也是对象
    - 在方法中定义也有默认值？
  
  ```java
  一维 []arr
     int 0
     float 0.0
     char  0或'\u0000'   不是48那个'0'
     boolean  false 
     引用null
  二维
  int [][]arr = new int[3][2];
     arr[i]      外层地址
     arr[i][j]   内层和一维一致
  int [][]arr = new int[3][];
     外层null
     内层，不能访问，否则报错
  ```

## 记录执行时间

- 睡眠啥的可以查Thread类

  ```java
  public static void main(String[] args) throws InterruptedException {
      long start=System.currentTimeMillis();
      Thread.sleep(1000);
      long end=System.currentTimeMillis();
  }
  ```

## 运算

### 进制

- 3

  ```java
  /*
  开头
  二进制：0b或0B
  八进制：数字0
  十进制
  十六进制：0x或0X
  */
  int a=0b101;
  int b=0167;
  int c=0x4AB3;
  ```

### 三元运算符

- 太好用了，简化if写法

  ```java
  // 成立选择第一个，不成立第二个的值
  return  x <y ?x:y; // true取x，false取y
  
  // x和y须是变量或者有返回值的函数等
  ```

### instanceof运算符

- 判断对象是否属于某一类型（类、接口）

  ```java
  Integer a = 200;
  System.out.println(a instanceof Integer); // true
  ```

### 位运算

- 简介

  - 按照每位进行运算（一般用于整数
  - 乘/除2或其倍数，一律用左右移
  - 左右移的优先级小于加减乘除

- 7

  ```java
  &  与
  |  或
  ~  非
  ^  异或
  << 左移(一位则乘2)
  >> 有符号右移(符号位补充高位，正数用0，负数用1)
  >>>无符号右移(0补充高位)
  没有<<<
  
  x>>y;  //x右移y位
  
  
  int num1 = -16; // 十进制-16的二进制表示为1111111111111111111111111110000
  int result1 = num1 >> 2; // 使用带符号右移，结果为-4，二进制为1111111111111111111111111111100
  int result2 = num1 >>> 2; // 使用无符号右移，结果为1073741820，二进制为0011111111111111111111111111100
  
  int num2 = 16; // 十进制16的二进制表示为00000000000000000000000000010000
  int result3 = num2 >> 2; // 使用带符号右移，结果为4，二进制为000000000000000000000000000010
  int result4 = num2 >>> 2; // 使用无符号右移，结果也为4，二进制为000000000000000000000000000010
  ```

### &和&&

- &&是短路与，若第一个表达式成立则不比较第二个。&是比较完左右表达式。

- |和||亦是如此

  ```java
  int a=0;
  System.out.println(a==1|a++==1);
  System.out.println(a);//1
  ```

### strictfp关键字

 - 简介

    - 标记的方法使用严格的浮点计算生成可再生的结果
    - 标记的类，类中的所有方法都严格计算
    - 如有些寄存器80位，结果double用64位存储。
      - 不加strictfp的话则中间结果截断为64位。
      - 使用后，则中间结果用80位，直到最终结果才截断

   ```java
   public static strictfp void getResult (){}
   ```

### BigInteger

- 7

  ```java
  /*
  在Java.math中
  - BigInteger	任意精度整数运算
  - BigDecimal   任意精度浮点数
  */
  
  //定义
  BigInteger a=BigInteger.valueOf(200);//一般数
  BigInerger b=new BigInteger("47197419479237491247392");//任意精度
  BigInteger.ZERO,BigInteger.ONE...//一些常量
  
  //计算。不能用+-*/，即使是正常的数。可以用成员方法
  BigInteger c=a.add(b);
  BigInteger d=a.multiply(b);
  a.compareto(b);//比较
  ```

## 标准输入输出

- System.in

```java
Scanner in=new Scanner(System.in);
String name=in.nextLine();//一行输入，可以包含空格
String firstName=in.next();//空白符作为分隔符
int age=in.nextInt();
```

- System.console()
- 输入不可见，用来读取密码。每次一行输入，没有读取单个单词，数值的方法。不如System.in方便

```java
Console cons=System.console();//此处居然不用new创建对象
String username=cons.readLine("User name:");
//用char数组存放而不用String，为了处理完密码后可以很好覆盖
char [] passwd = cons.readPassword("Password:");//效果是啥也没有，连*遮挡都没有

/*
  cons为null
  原因
  如果Java程序要与windows下的cmd或者Linux下的Terminal交互，就可以使用这个Java Console类代劳。
  Java要与Console进行交互，不总是能得到可用的Java Console类的。一个JVM是否有可用的Console，依
  赖于底层平台和JVM如何调用。如果JVM是在交互式命令行（比如Windows的cmd）中启动的，并且输入输出
  没有重定向到另外的地方，那么就可以得到一个可用的Console实例。
  在命令行下运行就没有什么问题
*/
```

- 格式化输出

```java
System.out.printf("%3.2f",x);//3个字符，小数点后2位。前面用空格补全
System.out.printf("%s",str);  
```

## 流程控制

- 块block:若干条语句组成，并用{}括起来。使原本只能放一条语句的地方可以放多条

```java
/*
块确定了变量的作用域，但是嵌套块中不能定义同名变量。如以下是错的
但是C++可以嵌套定义
*/
public void getA(){
    int a = 1;
    {
        int a = 2;
        int b = 9;
    }
    // System.out.println(b); // 报错，外部访问不到
}
```

- 条件

```java
if{}
else if{}
else{}
//else与最近的if构成一组
```

- 循环

  ```java
   while(){}
   do{}while(); // 先执行一次do语句，再走while判断
  //用浮点数可能死循环.如
  for(double x=0;x!=10;x+=0.1)//0.1无法用二进制精确表示，存在舍入的误差.IDEA中还真的没停下来
      
  // for each。更简洁，但拿不到下标
  //语法。collection必须是数组或实现了Iterable接口的对象
  for(variable:collection)statement;
  for(double num:nums){}
  
  
  // 关于while写法
  public class Main{
      public static void main(String[] args) {
          Scanner in = new Scanner(System.in);
          
          // 这个写法会报错
  //        while ((String line = in.nextLine())!=null {
  //            // ...
  //        }
          
          // 这样写就不会报错
          String line;
          // nextLine先去找缓存result，没有就自己去拿，result为null则抛异常
  //        while ((line = in.nextLine()) != null) {
  //            System.out.println(line);
  //        }
          
          
          // in.nextLine()会去拿result，有的话会缓存起来，result为null不抛异常，返回null
          // 因此这个写法更加保险
          while (in.hasNextLine()){
              System.out.println(in.nextLine());
          }
      }
  }
  ```

-  多重选择
   -  switch
      -  只能是byte,short,char,int,枚举,String 
      -  case后只能是常量，不能是范围
   
   ```java
   /*
   和C/C++完全一样
   标签类型
   char,byte,short,int的常量表达式
   枚举类型
   java7开始，也可以是字符串字面量.
   */
   switch(choice){
       case 1:
           ...
           break;
       case 2:
            ...
            break;
        default:
            ...
            break;
   }
   
   
   // switch新写法，jdk12预览，jdk14成为标准
   public class Main{
       public static void main(String[] args) {
           Scanner in = new Scanner(System.in);
           String day = in.next(); 
           String result = switch (day) {
               case "Monday" -> "今天是周一";
               case "Tuesday" -> "今天是周二";
               case "Wednesday", "Thursday", "Friday" -> "工作日加油！";
               case "Saturday", "Sunday" -> "周末愉快！";
               default -> "未知的星期";
           };
           System.out.println(result);
       }
   }
   ```

- goto
  
- 尽管Java设计者将goto作为关键字，但实际并没有打算使用
  
- 带标签的break

  - 可用于跳出多重循环

  ```java
  //标签放在外层循环之前。遇到则直接跳出
  toContinue:
  while(){
      for(){
          break toContinue;
      }
  }
  ```

# ==面向对象==

## 包

- 简介

  - 本质是一个文件夹
  - 类的容器，用于存放相关的类
  - 命名：公司域名倒序.项目名.模块.功能名。全部小写
  - 一个类可以使用同包下的所有类，以及其他包中的公共类
  - 导入静态方法和静态字段（没啥用
  - 类的包名要与存储路径一致

  ```java
  import static java.lang.System.*;
  out.println("Well");//不必加类名前缀
  ```

- 导入时机
  - 不在java.lang包
  - 不在同一个包下

## 类

- 简介
  - 引用是局部变量时，放在栈中。所引用的对象放在堆中
  - 类：制作月饼的模具。对象：月饼
  - 并不是所有的类都表现面向对象的特征。如Math类只封装了功能，并不隐藏数据。

- 类之间的关系
  - 依赖：use a。一个类的方法访问其他对象。应尽量避免，来降低耦合度
  - 聚合：has a。一个对象包含另一个对象。或者叫关联
  - 继承：is a。

- 访问修饰符
  - private			仅对本类可见
  - 默认                 对本包可见
  - private            对本包和子类可见
  - public              完全可见

- 转型

  - 向上转型（自动类型转换）
    - 子类型转父类型，或者基本类型的小精度类型转大精度类型
  - 向下转型
    - 要强制类型转换，并用instanceof校验向下类型转换的合法性

- 构成

  ```java
  - static方法
    - 只能访问类中的静态数据.无法访问类中的非静态成员变量和方法 
    - 不能使用this，因为没有对象存在
    - 可以被重载，不支持重写
    - 不是对象特有的一般定义为静态，如工具类
  - static-成员变量
  	- 声明时就要赋值。和final一起就变成了常量
  	- public final static或者public static final都可以。甚至 final static public。不过还是规范点吧
  - 代码块
    - 普通代码块
      - 类中纯粹用{}括起来的代码
      - 在构造函数执行开始前执行，每创建一个对象执行一次
    - 静态代码块
      - static {}
      - 类加载时执行，并且只执行一次
      - 用处：监视该执行期的行为
  ```
```
  
- 构造方法

  - 又称为构造器、构造函数、Constructor
  - 支持重载
  - 提供了构造方法后系统不再提供默认无参构造

  ```java
  
  结构：
  	修饰符 方法名 （参数）{}
  	不写返回值
  	方法名和类名一致	
  调用
  	new 方法名（参数）
  返回值：
  	返回值类型是类本身类型，所以不用写返回值类型，也不用写return
  
  
```

- 继承设计技巧
  - 将公共方法、字段放在超类
  - 少使用project，永远不知道有多少子类
  - 覆盖方法时不要改变预期行为
  - 子类里的访问权限不能比父类低

## final

- 不可变的

- 修饰
  - 类：无法被继承
  - 方法：无法被重写
  - 局部变量：赋值后无法改变值（只能赋值一次）
  - 成员变量：必须手动赋值（不然报错），赋值后无法改变值
    - 可以声明时赋值，也可以声明后在构造方法中赋值
  - 引用：不能指向其他对象了，无法被GC回收（引用的值是地址，地址不能再改变）
    - 但是可以修改对象的数据（通过地址访问）

- static联合使用，被修饰的称为“常量”
  - 节省内存，值不改变
  - 常量值不能改变，属于类本身
  - 般所有字母大写，单词用下划线连接

## this

- 简介
  - 本质是本对象的地址，指向了对象本身
  - 调用本对象中的属性或者方法，解决实例变量和参数同名问题
- 使用
  - this不能写在static修饰的方法中，因为static方法属于类
  - 创建对象后，同一个类N个对象就有N个this引用
  - this(参数); 
    - 只能出现在构造方法的第一行，用来调用本类其他构造方法
    - 如：在无参构造里面添加有参构造来设置默认值

## super

- 简介

  - 只代表对象的父类型特征，不是引用，不指向任何对象（所以不能return super;）this就可以解决父类子类同名问题

- 使用

  - super.属性名;	访问父类中属性
  - super.方法名（）;	调用父类中方法

- super.（实参）;		

  - 调用父类中构造方法，在子类对象创建前初始化父类，以便能够继承父类的特征。（不创建父类对象
  - 不提供则调用默认的super();
  - 不和this();同时出现在子类的构造方法中。因为2个都要求在构造方法第一行出现
    - this();super();		//this()执行时没有父类的初始化
    - super();this();        //第一个执行super();没有问题，执行this();后又重新进入了super();。。造成很多意想不到的结果

  ```java
  // 虽然程序员并没有在程序中主动调用父类(Person)的构造方法，但遵循“先父后子”的创建顺序，编译器会在编译源代时会自动把调用父类构造方法的语句添加到程序中，并且会把父类的构造方法添加到子类构造方法的第一行
  Student(){
      super();
      System.out.println("子类构造方法被执行");
  }
  ```

- this和super的变量指向同一个，而this和super的方法却不是同一个。

  ```java
  //本md笔记演示都是再jdk14环境
  public class Se {
      public static void main(String[] args) {
      D d=new D();
      System.out.println(d.a);//2
  
      }
  }
  class  C{
      int a;
      public C(){
          a=1;
      }
      
      public void test(){
          System.out.println("super method");
      }
  }
  class D extends C{
      public D(){
          this(2);//1.		this(2);只能在第一行，直接进入D(2)。来创建对象。
          
          //从D(2)返回时已经创建好对象。不会再创建。保证对象只创建一次
          System.out.ptintln(a);
      }
      public D(int a){
          //super();//2.默认加了然后执行。a=4
          System.out.println(this.a);//1
          this.a=a;//a=2			
          /*
          此时super.a和this.a是同一个变量。改变其一就会影响另一个。如super.a=100;后this.a==100;亦是如此
          引用类型的变量也是一样
          */
          System.out.println(super.a);//2	    
          //但是对于方法。父类的副本一直在，可以调用
          super.test();//父类的
        	this.test();//子类的
        	test();//子类的
      }   
      public void test(){
          System.out.println("this method");
      }  
      public void fun(){
          super.test();//
      }
      /*
      	通过对象方法也可以
      	D d=new D();
      	d.fun();//父类的。
      */
  }
  
  public class Se {
      public static void main(String[] args) {
      	C c=new D();
      	System.out.println(c.a);//3。即使通过父类引用，拿到的依旧是子类的。
      	//方法的话就是多态了，也是拿到子类的
      }
  }
  class C{
      int a=1;
      public C(){
          a=2;
      }
  }
  class D extends C{
      public D(){
          System.out.println(super.a);//2
          System.out.println(this.a);//2
          a=3;
      }
  }
  ```

  

## 多态

- 简介
  - 成员变量没有多态。内部类也算是成员变量？反正覆盖不了
  - 当通过一个父类引用访问一个子类对象的成员变量时，访问到的是父类定义的变量，而不是子类中同名的变量。
  - 动态方法
- 父类引用指向子类对象（自动转换）
  
  - 如：Animal  a=new Cat（）；
    - 使用父类对象的地方都可以使用子类对象代替(子类重写了父类的方法且存在对象转换
  - （编译时是静态绑定a当做Animal类来检查语法，运行时是动态绑定，也就是实际创建的是Cat对象）
  
  - 调用a其实就是调用Cat类的一个对象
    - 同一个指令，不同的子类有不同的行为
- 要求
  - 父类中也有子类的同名方法。（一般子类重写了多态才有意义
  - 只能调用父类和子类中都有的方法，如果子类没有重写则调用父类的
  - 不能直接调用子类中特有的方法
- 真的要访问子类中特有的话

  - 向下类型转换。Cat c=（Cat）a；通过新建的c引用访问
- 作用
  - 降低程序耦合度，提高扩展力（不用在父类中重写所有功能相似的子类方法）
  - 调用的类只需要对父类进行操作即可
  - 大型程序开发中作用就明显了  				

- instanceof和多态

  - 判断左边对象是否是右边类的实例。子类也行

  ```java
  //y可以是父类或接口。也就是要求x具有y的特征
  x instanceof y;//多态中父类向向子类转换
  ```

  

## 方法

- 参数

  - 值调用(call by value)：不会改变原基本数据类型的值
  - 引用调用(call by reference):传递的也是引用的副本，即不会改变原引用的值。但是可通过引用访问对象

- 可变参数

  - 传递任意数量指定类型的参数
  - type...name				可以是任何类型，包括引用和基本

  ```java
  //nums用一个数组来接收
  public test(int i,double ...nums){}
  ```

- 重载&重写
  - 重载
    - 类内。同名、不同参数的方法
    - 对返回值没有要求

  - 重写（方法覆盖）
    - 针对方法而言，不是属性
    - 名称、参数、返回值和父类中的一致
    - 重写的方法访问权限不能比父类的小
    - 非静态成员方法才存在重写
    - 父类静态、构造、私有，final修饰的不能重写

## 接口

- 概念

  - 对希望符合这个接口的类的一组要求
  - 是引用数据类型，编译后生成.class文件

- 接口语法

  ```java
  // 接口中方法自动设置为public abstract java 8后支持default方法和static方法
  // 字段自动设置为public static final  常量
  
  //可以继承多接口
  public interface Powered extends Moveable{
      //可以包含静态方法。常放在伴随类中，如工具类
      double static move();
      
  	//不能有实例字段，却可以有常量
  	double MPVE_SPEED=3;
      
      //Java9中甚至可以有私有方法和实例方法，不过只能在接口中使用，用法有限，常常作为辅助方法
      
      /*
      默认实现
      	希望是这样实现
      	也是为了兼容，如新版本接口中增加了新方法，提供默认实现来兼容旧版本的代码
      */
      default void remove(){throw new UnsupportedOperationException("remove");}
  }
  ```

- example

  ```java
  // 如Arrays的sort()方法,要求数组中的对象实现Comparable接口
  public interface Comparable{
      int compareTo(Object other);//只有一个方法
      //对象小于other返回负数，相等0
  }
  
  // 接口不是类，不能用new创建对象
  Comparable x=new Comparable();//错误
  
  // 可以声明接口变量、实现多态
  Comparable x=new Employee();//必须引用实现该接口的类
  
  //可以用instanceof判断类型
  if(x instanceof Comparable){}
  ```

- 接口方法冲突
  
  - 待补充

## 抽象类

- 简介

  - 抽象是对类的抽想，是类的类。进一步降低了程序耦合度，提高扩展力
  - 抽象类可以没有抽象方法，抽象方法必须在抽象类
  - 抽象类中可以有普通方法和变量
  - 不是没有方法体的都是抽象方法，如native修饰的方法就没有方法体

  ```java
  public abstract class Person{
      ...
      public abstract void fun();//没有{}实现
  }
  ```

- 特点
  - 无法实例化，无法创建对象，只能子类（也可以是抽象类）继承
    - 抽象子类：可以不重写父类中的抽象方法
    - 非抽象子类：要重写父类中的抽象方法
  - 抽象类有构造方法，是给子类使用的
  - 不能和final连用，因为final修饰后无法继承

## 泛型

- <>声明时不允许是基本数据类型。填入取出数据时会自动装箱拆箱

```java
var list=new ArrayList<Integer>();
list.add(3);//自动装箱。Integer.valueOf(3);
list.get(i);//自动拆箱。x.intValue();
```

## 自定义排序

- Arrays.sort()			//默认使用调用类的比较器

```java
//第二个参数是一个比较器
static <T> void sort(T[] a, Comparator<? super T> c)

/*
Comparable
	类内比较。类A实现了，之后就能调用Arrays.sort(a);按照规则排序le
	其中唯一的方法是public int comparaTo(Object o)
	//都在类内了。因此只能给引用类型排序，不能给基本数据类型
Comparator
	没有实现Comparable接口的类编写排序规则。如类B没有实现Comparable，也可以Arrays.sort(b,cmp);
	排序方法叫做public int compare(Object o1, Object o2)
	//参数只能是Object。因此只能给引用类型排序，不能给基本数据类型
	前-后			下标0~n  升序
*/
```

- 法一：传递一个新的实现了Comparator接口的对象

```java
//字符串按照长度排序
class LenthComparator implements Comparator{//此处绝对不是Comparable
    public int compare(String str1,String str2){
        return str1.lenth()-str2.lenth();
    }
}

Arrays.sort(str,new Lenthcomparator());
```

- 法二：下文中lambda表达式

## 拷贝对象

- 最浅的：引用直接赋值

```java
/*
引用的值相同，所引用的对象是同一个。所有基本数据类型和引用类型字段完全相同
*/
Employee copy=original;
```

- 浅拷贝

```java
/*
Object.clone()
2个不同的对象。基本类型是不同的副本，但是引用类型字段的值一样，也就是引用同一个对象
若是引用的对象是不可变，那么这是安全的。如String name;
*/
Employee copy=original.clone()
    
/*
Object中是projected。只能在子类调用父类的，或者父类的同包类调用
protected Object clone()throws CloneNotSupportedException

实现Cloneable接口（可选）
重写clone方法
将其设置为public			为了其他包的类能用该实现类的对象调用
*/
public class Employee implements Cloneable{
    ...
    public Employee clone(){
        //调用clone()返回一个新的对象
    	Employee cloned=(Employee)super.clone;//先进行浅拷贝
        cloned.hireDay=(Date)hire.clone();//Date本身是深拷贝
        
        return cloned;
	}
}
Employee copy=original.clone();//就是深拷贝了
```

- Cloneable

```java
public interface Cloneable {
}
//标记接口。if(x instanceof Comparable){}
```

## lambda表达式

- 是一个含有变量规范可传递的代码块，可以在以后执行一次或多次

- 格式

```java
//无须指定返回值。会根据上下文自动推出
(type args)->{}
(int a,int b)->{return a-b;}

//参数类型可推导出，则可无须指定
Comparator <String> comp=(str1,str2)->{return str1.length()-str2.length();};//命名后可重复使用

//只有一个参数且类型可推断出，甚至可以不用小括号()
ActionListener listener=event->{System.out.println("the time is"+Instant.ofEpochMilli(event.getWhen()));};

//一点原理
Arrays.sort(strs,
            (str1,str2)->{return str1.length()-str2.length();});
//调用strs对象上的compare方法时会执行lambda表达式中的代码
```

- 只能是函数式接口
  - 只有一个抽象方法的接口，使用时可以选择lambda的方式
  - lambda前面的接口只能是函数式接口
  - 用lambda的话可以不用直接创建该接口的实现类，就能完成对应功能
- lambda表达式是对接口抽象一个方法的实现。不知道对不对？
- lambda表达式的组成
  - 代码块{}中
  - 参数()
  - 自由变量的值。(){}之外定义的
- 变量使用规范
  - 不能改变自由变量的值。（栈帧中的局部变量是线程私有的，如果lambda表达式被其他线程调用，就很危险了）
    - lambda捕获的是final或effectively final的变量，不会再赋新值

  - 内部定义的变量名字不能与自由变量和参数一致

- 再lambda中使用this

```java
//this 是指创建lambda的方法的this
public class LambdaTest{
    public void init(){
        ActionListener listener=event->{
            System.out.println(this.toString());//此处this代表LambdaTest
        }
    }
}
```

- lambda表达式的延迟执行，即到真正调用的时候才执行
- 处理lambda表达式
  - 待补充。一些重要的函数式接口

## 方法引用

- 比lambda更加简洁
- 它指示编译器生成一个函数式接口的实例，覆盖该接口的抽象方法，来调用给定的方法
- 类似于lambda表达式，方法引用也不是一个对象。不过一个类型为函数式接口的变量赋值时会生成一个对象

```java
//形式
object::instanceMethod		对象
Class::instanceMethod		类
Class::staticMethod			类

//生成一个ActionListener对象，它actionPerformed(ActionEvent e)要调用System.out.println(e);
var timer=new Timer(1000,System.out::pritln);

//对字符串排序，而不考录大小写
Arrays.sort(strs,String::compareToIgnoreCase);
```

- 构造方法引用
  - 待补充。跟流库有关，在卷二中学习。

```java
String::new
String[]::new
```

## ==常用类==

### Object

- 简介

  - 所有类默认直接或者间接继承Object类
  - Java只有基本类型(primitive type)不是对象。数组是对象

- 一些方法
  - equals()
    - ==比较的是引用的值
  - getClass()        
    - 返回一个对象的所属类
  - hashCode()
    - 返回通过对象的地址计算得的哈希值
  - toString()
    - 默认输出：类名@十六进制的对象地址
  - fineline()
    - java对象即将被垃圾回收器回收时调用
    - protected修饰

- 7

  ```java
  package java.lang;
  
  // 该注解提示编译器内联优化
  import jdk.internal.vm.annotation.IntrinsicCandidate;
  
  
  public class Object {
  
      @IntrinsicCandidate
      public Object() {}
  
      @IntrinsicCandidate
      public final native Class<?> getClass();
  
      @IntrinsicCandidate
      public native int hashCode();
  
      public boolean equals(Object obj) {
          return (this == obj);
      }
  
      @IntrinsicCandidate
      protected native Object clone() throws CloneNotSupportedException;
  
      public String toString() {
          return getClass().getName() + "@" + Integer.toHexString(hashCode());
      }
  
      @IntrinsicCandidate
      public final native void notify();
      
      @IntrinsicCandidate
      public final native void notifyAll();
      
      public final void wait() throws InterruptedException {
          wait(0L);
      }
      
      // nanos是纳秒
      public final void wait(long timeoutMillis, int nanos) throws InterruptedException {
          if (timeoutMillis < 0) {
              throw new IllegalArgumentException("timeoutMillis value is negative");
          }
  
          if (nanos < 0 || nanos > 999999) {
              throw new IllegalArgumentException(
                                  "nanosecond timeout value out of range");
          }
  
          if (nanos > 0 && timeoutMillis < Long.MAX_VALUE) {
              timeoutMillis++;
          }
  
          wait(timeoutMillis);
      }
      
      @Deprecated(since="9")
      protected void finalize() throws Throwable { }
  }
  
  ```

  

### String

- 简介

  - String没有提供修改其中某个字符的方法
  - ""空串也是个对象，与null不同
  - 判断相等equal()

- 方法

  ```java
  // 创建
  String s="rain";
  String s=new String("abc");
  byte[]b={97,98,99};//Ascill
  String s=new String(b);//等价于s="abc"
  String s1=new String(b,1,2);//(字节数组，下标，长度） "bc"
  
  //把多个字符串连接起来，并用分隔符分开
  String all=String.join("/","S","M","A");
  //all="S/M/A"
  
  String s="Welcome to java";
  
  //匹配
  System.out.println(s.matches(".*come.*"));//true
  
  // 码点与代码单元
  str.charAt(int index)//返回给定位置的代码单元
      
  // 移除两端空白字符.(空格、制表、换行、回车)
  String.trim();
  
  
  
  compareTo();
  	前面减后面
  	0前后一致
  	-1前小后大
  	1前大后小
  contains()
  	是否包含子字符串
  endWith("i")
  	是否以i结尾，可以用来判断文件后缀
  equals()
  	判断2个字符串是否相等
  equalsIgnoreCase()
  	判断2个字符串是否相等，并且忽略大小写
  getBytes()
  	将字符串转换为字节数组（Ascill）
  toCharArray()
  	转换为字符数组
  indexOf()
  	返回子字符串第一次出现的索引
  isEmpty()
  	判断字符串值是否是空//底层调用的是length()方法
  length()
  	返回字符串长度
  	注：这里是方法，而数组的length是属性
  lasrIndexOf()
  	子字符串最后一次出现的下标
  replace("i","j")
  	把j替换为i
  split(i)
  	以i作为分割拆分字符串为字符串数组
  startsWith()
  	是否以i开头
  substring(i,j)
  	截取下标为i和j之间的字符串（包含i，不包含j）
  toLowerCase()
  	转换为小写
  toUpperCase()
  	转换为大写
  valuOf()
  	将非字符串转换为字符串
  	静态方法，不用new
      如果是对象（不局限于字符串），会调用toString()方法
      println()底层调用了valueOf()方法，转化为字符串输出
  	所以println简洁调用了 toString()
  ```

- StringBulider线程不安全，但是速度快点。StringBuffer线程安全

  ```jav
  /*创建对象
  添加字串
  toString返回
  */
  StringBuilder strbuilder=new StringBuilder();
  strbuilder.append(ch);
  strbuilder.insert(i,str);
  String str=strbuilder.toString();
  ```

### 日期类

- Date：描述了一个时间点。如"December 31,1999,23:59:59 GMT"。

```java
Date a=new Date();
```

+ LocalDate:描述了一个用日历表示的日期

```java
//可用于记录一些日期，如借还日期
//应当使用静态工厂方法(factory method)来构建对象。而不是构造方法
LocalDate a,b,c;
now=Local.now();
a=Local.(2007,3,31);

//一些方法。并没有什么用
int year=a.getYear();
int month=a.getMonthValue();
int day=a.getDayOfMonth();

//有用点的
b=a.plusDays(100);//创建并返回一个100天后的对象
c=a.minusDays(int n);//创建并返回前或后n天的日期

DayOfWeek weekday=a.getDayOfWeek();
int m=weekday.getValue();//得到当前日期的星期几

```

### 日期数字格式化

- 3

  ```java
  Date date=new Date();
  
  //专门负责格式化(年 月 日 日 分 秒 毫秒
  SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
  System.out.println(sdf.format(date));//2022-03-19 10:34:37 137
  
  
  //可以用来获取当前的时间
  SimpleDateFormat sdf1=new SimpleDateFormat("yy");
  System.out.println(sdf1.format(date));//22
  
  //反过来用字符串创建Date对象
  String str="2021-07-21 12:12:12 333";
  SimpleDateFormat sdf2=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
  Date date2=sdf2.parse(str);
  System.out.println(date2.toString());//Wed Jul 21 12:12:12 CST 2021
  
  
  /*
  返回的是字符串,传递进去的不能是字符串，是double
   */
  //逗号隔开，保留2位小数
  DecimalFormat df=new DecimalFormat("###,###.##");
  //保留4位小数，不够补0
  DecimalFormat df1=new DecimalFormat(".0000");
  
  double a=13254.173;
  
  System.out.println(df.format(a));//13,254.17
  System.out.println(df1.format(a));//13254.1730
  ```

### 包装类

- 基本类型不是类，无法参与泛型、集合、反射等过程

  ```java
  // char的包装类是Character
  
  // 基本类型转包装类,返回Integer对象
  Integer a = Integer.valueOf(3); // 传String也可以
  // 包装类的基本类型值
  int b = a.intValue();
  
  Integer a = 2; // 自动装巷。装成包装类型
  int b = a; // 自动拆箱
  
  // 不赋值时为null
  ```

- ==  比较的是引用，没啥意义

  ```java
  Integer a=100;
  Integer b=new Integer(100);
  System.out.println(a==b);//false
  
  Integer a=new Integer(100);
  Integer b=new Integer(100);
  System.out.println(a==b);//false
  
  // 也是直接比较引用，但是Integer缓存了-128到127到常量池，a和b拿到的是同一个对象
  Integer a=100;
  Integer b=100;
  System.out.println(a==b);//true 
  
  // 200不在Integer的常量池中，不是同一个引用
  Integer a = 200;
  Integer b = 200;
  System.out.println(a == b);// false
  
  Integer a = 1000;
  Integer b = new Integer(1000);
  System.out.println(a == b); // false
  
  
  Integer b = new Integer(1000);
  Integer a = 1000;
  System.out.println(a == b); 
  ```

- 判断值是否相等用equals()

  ```java
  Integer a=100;
  Integer b=new Integer(100);
  System.out.println(a.equals(b));//true
  
  Integer a=100;
  Integer b=100;
  System.out.println(a.equals(b));//true
  ```

- 字符串转数字

  ```java
  // 函数原型。返回的是int
  static int parseInt(String s);//十进制  
  static int parseInt(String s,int radix);//radix进制
  
  
  // 注意捕获异常，因为有时传入的参数并不合理
  try {
      // 2进制的“11”转化为十进制的“3”
      int i = Integer.parseInt("11", 2);
  } catch (NumberFormatException e) {
      throw new RuntimeException(e);
  }
  
  ```

### Comparator

- Comparator包含很多方便的方法来创建比较器

  ```java
  // 返回int
  static int parseInt(String s);//十进制  
  static int parseInt(String s,int radix);//radix进制
  
  try {
      // 2进制的“11”转化为十进制的“3”
      int i = Integer.parseInt("11", 2);
  } catch (NumberFormatException e) {
      throw new RuntimeException(e);
  }
  
  ```

- 待补充。

### Logger

- 输出程序运行信息

  ```java
  //获取全局日志记录器并写入日志
  Logger.geGlobal.info(" ")
  ```

### ActionListener

- java.swing.Timer

- 其他函数给定时器传递一个函数，Java给定时器传一个对象。对象可以携带一些信息，更加灵活。
- 当然，定时器要知道调用哪个方法，并要求传递的对象实现java.swt.event.ActionListener接口

```java
import javax.swing.*;//是该包下的Timer
public class 定时器02 {
    public static void main(String[] args) {
        var listener=new TimerPrint();
        new Timer(1000,listener).start();

        JOptionPane.showMessageDialog(null,"Quit program?");
        System.exit(0);
    }

}

 //每隔1秒打印下时间并响铃
class TimerPrint implements ActionListener{
    @Override
    public void actionPerformed(ActionEvent e) {
        //想要执行的语句放在actionPerformed()中。该接口只有这个方法
        System.out.println("the time is "+ Instant.ofEpochMilli(e.getWhen()));
    }
}

```

### 内部类

- 为什么要使用内部类？
  - 内部类对同包下其他类隐藏
  - 内部类可以访问外部类的所有数据。包括私有
  - 但是外部类不可以访问内部类的私有数据？？。。

- example

  ```java
  // 1.局部内部类 待补充
  
  
  // 2.静态内部类 
  class ArrayAlg{
      public static class Pair{
          //属于静态引用了吧
      }
  }
  
  // 3.Lambda匿名内部类
  // 不需要显式定义，直接定义并new对象。 类、父类、接口啥的都可以
  // 不使用匿名内部类
  public void start(int interval){
      var listener=new ActonLisstener(){
          public void actionPerformed(ActionEvent e){
              System.out.println("the time is");
          }
      };
      
      var timer=new Timer(interval,listener);
      timer.start(); 
  }
  // 配合Lambda表达式使用匿名内部类更加简洁
  public void start(int interval){
     new Timer(interval,event->{
          System.out.println("the time is");
      }).start();
  }
  ```

# ==IO编程==



## IO继承结构

- Java 的 I/O（输入/输出）系统提供了一套用于处理各种类型 I/O 操作的类和接口。

  - 流是连续的数据
  - 媒介：文件、管道、网络、缓存、标准流（System.in,System.error)等

- 分类维度

  - 流方向
    - 输入流（inputStream）
    - 输出流（outputStream）
  - 功能
    - 节点流
      - 从或向一个特定的地方读写数据，如 FileReader
    - 处理流
      - 对一个已存在的流的连接和封装，通过所封装的流的功能调用实现数据读写，如BufferedReader

  - 数据单位
    - 字符流
      - 字符流处理字符数据
    - 字节流
      - 处理原始的字节数据。（注意是以字节为单位处理，而不是以一个二进制位为单位）

- 继承结构图

  ![IO流](img\IO流.png)

- 或者这张图

  ![IO分类](img\IO分类.png)

## 文件路径

- 分隔符

  ```java
  // 路径分隔符
  File.separator
      字符串。代表当前操作系统的文件路径分隔符。在Unix/Linux系统中是/,在Windows系统中是\。
  File.separatorChar 
      是字符。功能与 File.separator 相同
      
  // 多路径分隔符。  当前操作系统在列举多个路径时使用的分隔符
  File.pathSeparator 
      字符串。通常在环境变量如PATH中使用。在Unix/Linux系统中是:,在Windows系统中是;
  File.pathSeparatorChar 
      字符。功能与 File.pathSeparator 相同
     
      
  // 如果真的跨系统运行，那还是使用分隔符api吧，谁知道除了下面几个，java程序还会运行在哪些系统上呢。
  Unix、Linux 只用/作为分隔符
  微软的MS-DOC 只用\作为分隔符。（/被拿来做命令行参数标识符了）
  Windows 用\和/都可以。\是为了兼容MS-DOC的命令格式
  浏览器打开文件如.pdf用/	（web网站最早是在Unix系统运行）
  ```

- 相对路径

  - **不要直接使用"src/main/resources"来读取资源 ！！！**

    - IDEA设置资源目录后src/main/resources，该目录中的资源文件会被复制到classpath下
    - IDEA保留运行时能够通过src/main/resources直接访问资源文件
    - 但打成jar包时，src/main/resources目录不会被包含进来，需要通过classpath访问了

    ```java
    // 下面这个例子在IDEA中运行没问题，能够正常输出文件内容。但是打成jar包，java -jar运行jar包直接报错java.io.FileNotFoundException: src\main\resources\in.txt (???????)
    
    // 读取资源的类
    // ElasticSearch-Learn/src/main/java/com/example/elasticsearchlearn/ElasticSearchLearnApplication.java
    package com.example.elasticsearchlearn;
    @SpringBootApplication
    public class ElasticSearchLearnApplication {
    
        public static void main(String[] args) {
            System.out.println(File.separator); // \
            // String inFile = "src\\main\\resources\\in.txt";
            String inFile = "src/main/resources/in.txt";
            try(BufferedReader reader=new BufferedReader(new FileReader(inFile))){
                String line;
                while ((line=reader.readLine())!=null){
                    System.out.println(line);
                }
            }catch (IOException e){
                e.printStackTrace();
            }
            SpringApplication.run(ElasticSearchLearnApplication.class, args);
        }
    
    }
    
    // 资源所在位置
    // ElasticSearch-Learn/src/main/resources/in.txt
    
    
    // jar包中目录结构
    // ElasticSearch-Learn-0.0.1-SNAPSHOT/BOOT-INF/classes/com/example/elasticsearchlearn/ElasticSearchLearnApplication.class
    // ElasticSearch-Learn-0.0.1-SNAPSHOT/BOOT-INF/classes/in.txt
    
    
    
    // 正确做法
    public static void main(String[] args) {
        	// in.txt前面一定要加/
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(ElasticSearchLearnApplication.class.getResourceAsStream("/in.txt")))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            SpringApplication.run(ElasticSearchLearnApplication.class, args);
        }
    ```

  - classpath

    - resources目录下就是
    - 考虑用Path处理路径

    ```java
    // 前面一定要加/，此时表示从classpath根目录开始。	不加则是相对于当前java类的路径。。
    
    ```

- 绝对路径

  - 建议使用Path处理路径

    ```java
    // windows使用反斜杠/时记得转义
    public  void readTextFile() {
        System.out.println(File.separator); // \
        // windows下这两个路径都可以
        // String inFile = "C:\Users\beilinanju\Desktop\java代码\LeetCode\src\main\resources\in.txt";
        String inFile = "C:/Users/beilinanju/Desktop/java代码/LeetCode/src/main/resources/in.txt";
        try(BufferedReader reader=new BufferedReader(new FileReader(inFile))){
            String line;
            while ((line=reader.readLine())!=null){
                System.out.println(line);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    
    
    
    // 考虑平台兼容性
    import java.nio.file.Files;
    import java.nio.file.Path;
    import java.nio.file.Paths;
    public  void readTextFile() {
        // 获取用户主目录路径，以确保跨平台兼容性
        String homeDir = System.getProperty("user.home");
        
        // 构建一个相对用户主目录的路径，这里假设"in.txt"位于"Desktop/java代码/LeetCode/src/main/resources/"下
        Path filePath = Paths.get(homeDir, "Desktop", "java代码", "LeetCode", "src", "main", "resources", "in.txt");
        
        // windows下输出 C:\Users\beilinanju\Desktop\java代码\LeetCode\src\main\resources\in.txt
        // linux下输出 /root/Desktop/java代码/LeetCode/src/main/resources/in.txt
    	System.out.println(filePath.toString()); 
        
        // 标准化路径
      	// 消除冗余路径：例如，像 "dir/../file" 这样的路径会被简化为 "file"。
        // 处理不同操作系统间的路径分隔符差异
        Path normalizedPath = filePath.normalize();
        
        // windows下输出 C:\Users\beilinanju\Desktop\java代码\LeetCode\src\main\resources\in.txt
        // linux下输出 /root/Desktop/java代码/LeetCode/src/main/resources/in.txt
        System.out.println(normalizedPath.toString()); 
        
        try {
            String content = Files.readString(normalizedPath);
            System.out.println(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
    ```

## 文件类？

- File类

- Files类

- RandomAccessFile

- example

  ```java
  
  // 二进制写入，不是字符
  public class TestRandomAccessFile{
      public static void main(String[] args) {
          try (RandomAccessFile raf = new RandomAccessFile("C:\\Users\\beilinanju\\Desktop\\java代码\\LeetCode\\src\\main\\resources\\random.txt", "rw")) {
              raf.writeInt(42);
              raf.writeInt(21);
              raf.writeChars("randomfileiik");
              raf.writeBoolean(false);
  
              raf.seek(0); // 移动到文件开头
              int val1 = raf.readInt();
              System.out.println("val1: "+val1); // val1: 42
              // int为32位，占用4个字节
              System.out.println("pointer: "+raf.getFilePointer()); // pointer: 4
              int val2 = raf.readInt();
              System.out.println("val2: "+val2); // val2: 21
              System.out.println("pointer: "+raf.getFilePointer()); // pointer: 8
  
              // 每个字符2个字节，推测加上randomfileiik后，为8+13*2=34
              raf.seek(34);
              boolean val4  =  raf.readBoolean();
              System.out.println("val4: "+val4); // val4: false
          } catch (IOException e) {
              e.printStackTrace();
          }
      }
  }
  ```

  



## 字符流

- 常用

  - Reader（抽象类，定义了字符输入流的基本方法）
    - `FileReader`：用于从文件中读取字符。
    - `StringReader`：用于从字符串中读取字符。
    - `BufferedReader`：为其他输入流提供缓冲功能，并提供了 readLine() 方法读取一行文本。
    - `InputStreamReader`：将字节流转换为字符流。
  - Writer （抽象类，定义了字符输出流的基本方法）
    - `FileWriter`：用于向文件中写入字符。
    - `StringWriter`：用于将字符写入字符串。
    - `BufferedWriter`：为其他输出流提供缓冲功能，并提供了 newLine() 方法写入一个行分隔符。
    - `OutputStreamWriter`：将字符流转换为字节流。

- example

  ```java
  // 文件流
  public void writeFile() {
      String data = "Hello\nthis is\n123"; // 有两个换行
      String outFile = "C:/Users/beilinanju/Desktop/java代码/LeetCode/src/main/resources/example.txt";
      // 追加写入
      try (FileWriter fw = new FileWriter(outFile,true)) {
          fw.write(data);
      } catch (IOException e) {
          e.printStackTrace();
      }
  }
  
  // 缓冲流。
  public  void readTextFile() {
      String inFile = "C:/Users/beilinanju/Desktop/java代码/LeetCode/src/main/resources/in.txt";
      try(BufferedReader reader=new BufferedReader(new FileReader(inFile))){
          String line;
          while ((line=reader.readLine())!=null){
              System.out.println(line);
          }
      }catch (IOException e){
          e.printStackTrace();
      }
  }
  
  // Scanner是标准输入，不在io包，但也能读取文件流
  public void standardStream(){
      String inFile = "C:/Users/beilinanju/Desktop/java代码/LeetCode/src/main/resources/std.txt";
      try ( PrintWriter out =new PrintWriter(inFile,StandardCharsets.UTF_8);
            Scanner in = new Scanner(Path.of(inFile), StandardCharsets.UTF_8) ){
          out.println("this is a solution");
          out.flush();
          String data = in.nextLine();
          System.out.println(data); // this is a solution
      } catch (IOException e) {
          throw new RuntimeException(e);
      }
  }
  ```



## 字节流

- 常用

  - InputStream（抽象类，定义了字节输入流的基本方法）
    - `FileInputStream`：用于从文件中读取字节
    - `ByteArrayInputStream`：用于从字节数组中读取字节
    - `BufferedInputStream`：为其他输入流提供缓冲功能
    - `DataInputStream`：用于读取基本数据类型（如 int、float 等）
  - OutputStream（抽象类，定义了字节输出流的基本方法）
    - `FileOutputStream`：用于向文件中写入字节
    - `ByteArrayOutputStream`：用于将字节写入字节数组
    - `BufferedOutputStream`：为其他输出流提供缓冲功能
    - `DataOutputStream`：用于写入基本数据类型（如 int、float 等）

- example

  ```java
  import java.io.*;
  
  // 文件流 - 文件复制
  public void copyFile() {
      String inFile = "C:/Users/beilinanju/Desktop/java代码/LeetCode/src/main/resources/in.txt";
      String outFile = "C:/Users/beilinanju/Desktop/java代码/LeetCode/src/main/resources/out.txt"; // 不存在自动创建
      try (FileInputStream fis = new FileInputStream(inFile);
           FileOutputStream fos = new FileOutputStream(outFile)) {
          int c;
          while ((c = fis.read())!=-1) {
              fos.write(c);
          }
      } catch (FileNotFoundException e) {
          throw new RuntimeException(e);
      } catch (IOException e) {
          throw new RuntimeException(e);
      }
  }
  
  
  // 数据流 - 写进去的是二进制，即使后缀换.txt。读取时再读这个文件就好了
  public void writeBasicType(){
      String outFile = "C:/Users/beilinanju/Desktop/java代码/LeetCode/src/main/resources/data.dat";
      try(DataOutputStream dos = new DataOutputStream(new FileOutputStream(outFile))){
          dos.writeInt(21);
          dos.writeDouble(33.77);
          dos.writeBoolean(false);
      } catch (FileNotFoundException e) {
          throw new RuntimeException(e);
      } catch (IOException e) {
          throw new RuntimeException(e);
      }
  }
  
  
  // 对象流
  public void writeObject(){
      Integer it = 233;
      String path = "C:/Users/beilinanju/Desktop/java代码/LeetCode/src/main/resources/integer.dat";
      try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
          oos.writeObject(it);
      } catch (IOException e) {
          e.printStackTrace();
      }
  }
  public void readObject(){
      String path = "C:/Users/beilinanju/Desktop/java代码/LeetCode/src/main/resources/integer.dat";
      try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
          Integer it = (Integer) ois.readObject();
          System.out.println(it.intValue()); // 233
      } catch (IOException | ClassNotFoundException e) {
          throw new RuntimeException(e);
      }
  }
  
  ```

## NIO

- IO分类

  - 阻塞IO
    - 阻塞并等待唤醒，等待期间CPU空闲
  - 非阻塞IO
    - 轮询查看是否就绪，消耗过多CPU资源
  - 多路复用
    - 一个线程监听多个IO，准备就绪时触发
    - PS：现在操作系统和多核CPU已越来越适应多任务处理，线程开销已经越来越小。IO多路复用已不再是提高IO性能的必须手段，如并发量不那么大的情况下？？

- New IO 或 Non blocking IO

  - NIO支持阻塞模式，也支持非阻塞模式，可以同时处理多个通道，提高了性能，是IO多路复用的基础
  - NIO面向缓冲区，普通IO面向流

  ![NIO结构图](img\NIO结构图.png)

- 三个组件

  - Buffer（缓冲区）

    - 简介

      - 一块可读写的内存，Java包装成Buffer对象
      - Buffer提供了一种灵活且高效的方式来处理I/O操作中的数据缓冲

    - 分类

      - Buffer抽象类下有7个抽象类，分别为IntBuffer、FloatBuffer、CharBuffer、DoubleBuffer、ShortBuffer、LongBuffer、ByteBuffer

    - 位置

      - Capacity：Buffer的最大数据容量，一旦创建不可改变。
      - Position：下一个要被读或写的元素的位置。写入时，position会向前移动；读取时，也会向前移动。
      - Limit：在读模式下，limit表示可以读取到的第一个不能读取的元素的位置；在写模式下，表示可以写入的最后一个元素的位置+1。
      - Mark：一个备忘位置，可以用来记录当前position的位置，以便后续恢复。

    - 操作

      - flip()：切换Buffer从写模式到读模式。将limit设置为当前position，然后将position重置为0。
      - clear()：清空Buffer，准备再次写入数据。将position重置为0，limit设置回capacity。
      - rewind()：重置position为0，允许从头开始重新读取Buffer中的数据，但不改变limit。
      - compact()：将未读数据移到Buffer开头，然后将position设置到最后一个未读元素之后，准备新的写入，limit保持不变。

      ```java
      public Buffer flip() {
          limit = position;
          position = 0;
          mark = -1;
          return this;
      }
      public Buffer clear() {
          position = 0;
          limit = capacity;
          mark = -1;
          return this;
      }  
      public Buffer rewind() {
          position = 0;
          mark = -1;
          return this;
      }
      ```

  - Channel（通道）

    - 简介
      - `Channel`是一个可以读取和写入数据的双向抽象，它支持阻塞和非阻塞两种模式，特别适合用于构建高性能的服务器和客户端应用
      - 是对传统I/O中流（Stream）模型的一种补充和优化
    - 特点
      - 双向可读可写
      - 可异步读写
      - 从Buffer取、写数据

    - 常用类
      - FileChannel：用于读写文件。
      - SocketChannel：用于TCP网络通信的客户端。
      - ServerSocketChannel：用于TCP网络通信的服务端，可以监听新进来的连接。
      - DatagramChannel：用于UDP网络通信。

  - Selector

    - 简介
      - 核心组件，它允许单个线程管理多个通道（Channel）的I/O操作，实现了非阻塞I/O和多路复用，极大地提高了处理大量并发连接的效率。
      - 线程通过Selector监听多个Channel
      - 保证并发读写的高性能
    - 工作原理
      - 注册与选择键（SelectionKey）：应用程序可以通过通道（如`SocketChannel`）的`register()`方法将通道注册到`Selector`上，并指定关注的事件（如读、写、连接等）。注册后，会返回一个`SelectionKey`，它是通道和选择器之间的注册关系的句柄。
      - 选择操作：通过调用`Selector`的`select()`或`selectNow()`方法，可以阻塞或非阻塞地等待至少一个已注册的通道变得可操作（即关注的事件发生）。这些方法返回的整数表示有多少通道已就绪。
      - 处理就绪的通道：调用`selectedKeys()`方法获取所有已选择（即就绪）的`SelectionKey`集合，遍历这个集合，根据每个`SelectionKey`所关联的通道和事件类型来执行相应的操作。
      - 取消注册与关闭：完成操作后，可以选择取消某个通道的注册，甚至关闭通道和选择器。

    - 一些方法
      - Selector
        - `open()`：创建一个新的Selector实例。
        - `select()`：阻塞调用线程直到至少有一个通道在至少一个指定的操作上就绪，或超时，或被中断。
        - `select(long timeout)`
        - `selectNow()`：非阻塞版本的选择操作，立即返回，返回值指示就绪的通道数，即使没有通道。
        - `selectedKeys()`：返回此选择器的已选择（即就绪）键集，可遍历这个集合来处理各个通道的就绪事件
        - `keys()`: 返回此`Selector`中所有注册的`SelectionKey`的集合，无论这些键是否已就绪。
        - `wakeup()`: 唤醒在此`Selector`上调用`select()`方法而阻塞的线程。
      - SelectableChannel
        - `register(Channel channel, int ops)`：将通道注册到此选择器上，并指定感兴趣的操作集。
      - SelectionKey
        - `isConnectable()`：检查此键的通道是否已完成连接操作（仅客户端连接时使用）。
        - `isAcceptable()`：检查此键的通道是否准备好接受新的连接（仅服务端使用）。
        - `cancel()`：取消选择键的注册，不再参与`Selector`的监控。
        - `channel()`：返回与此键关联的`SelectableChannel`（即注册的通道）。
        - `selector()`：返回与此键关联的`Selector`。
        - `attach(Object ob)`：附加一个对象到此键上。
        - `interestOps()`：返回此键的当前兴趣集，即注册时指定的事件集合。

- example

  - 基于NIO、Socket的网络通信

  ```java
  - SelectionKey事件 
  	- OP_READ = 1 << 0;     // Channel可读
      - OP_WRITE = 1 << 2;    // Channel可写
      - OP_CONNECT = 1 << 3;  // 用于客户端：连接中，尚未完成
      - OP_ACCEPT = 1 << 4;   // 用于服务端：新请求到达
  
  
  // 服务端
  // 这里的key是Selector和Channel注册关系句柄
  public class NIOServer {
      public static void main(String[] args) throws IOException {
          ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
          serverSocketChannel.configureBlocking(false);
          serverSocketChannel.socket().bind(new InetSocketAddress(8080));
          
          Selector selector = Selector.open();
          // 注册ServerSocketChannel到Selector，关注OP_ACCEPT事件
          serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
  
          while (true) {
              selector.select();
              Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
              while (iterator.hasNext()) {
                  SelectionKey key = iterator.next();
                  if (key.isAcceptable()) {
                      SocketChannel client = serverSocketChannel.accept();
                      client.configureBlocking(false);
                      client.register(selector, SelectionKey.OP_READ);
                      System.out.println("Accepted connection from " + client);
                  } else if (key.isReadable()) {
                      handleRead(key);
                  }
                  iterator.remove();
              }
          }
      }
      
      private static void handleRead(SelectionKey key) throws IOException {
          SocketChannel client = (SocketChannel) key.channel();
          /*
          如果消息大于1024字节,client.read(buffer)方法只会读取最多1024字节的数据到Buffer中
          剩余的数据仍然留在SocketChannel的接收缓冲区中，不会丢失,下次继续读。
          */
          ByteBuffer buffer = ByteBuffer.allocate(1024);
          int bytesRead = client.read(buffer);
          if (bytesRead > 0) {
              buffer.flip(); // 切换到读模式
              String message = new String(buffer.array(), 0, bytesRead);
              System.out.println("Received message: " + message);
              buffer.clear();
              // 可以在这里处理消息并回复
          } else {
              key.cancel();
              client.close();
          }
      }
  }
  
  
  
  // 客户端
  public class NIOTCPClient {
      public static void main(String[] args){
          try(SocketChannel socketChannel = SocketChannel.open();){
              socketChannel.connect(new InetSocketAddress("localhost", 8080));
              socketChannel.finishConnect();
              Scanner in = new Scanner(System.in);
              while (in.hasNextLine()) {
                  String message = in.nextLine();
                  ByteBuffer buffer = ByteBuffer.wrap(message.getBytes());
                  socketChannel.write(buffer);
              }
              ByteBuffer buffer = ByteBuffer.wrap("Hello, Server!".getBytes());
              socketChannel.write(buffer);
          } catch (IOException e) {
              throw new RuntimeException(e);
          }
      }
  }
  ```

  

## 直接内存

- DirectByteBuffer.allocateDirect()

## 零拷贝

- 技术应用

  - Java的MappedByteBuffer.map()：底层调用了操作系统的mmap()内核函数
    - RocketMQ内部基于nio提供的java.nio.MappedByteBuffer，通过FileChannel的map方法得到mmap()的缓冲区。
  - Java的FileChannel.transferFrom()/transferTo()：底层调用了sendfile()内核函数
    - Netty的文件传输采用了transferTo()/transferFrom()方法
    - Kafka底层基于java.nio包下的FileChannel.transferTo()实现零拷贝

- 应用场景

  - 大文件传输：在需要高效处理大量文件读写操作时，如文件服务器、数据导入导出等场景
  - 网络通信：高性能网络编程中，减少数据在内核空间与用户空间之间的复制，提升吞吐量和响应速度
  - 数据库和缓存系统：加速数据在不同存储介质间的迁移

- 路径成本

  - DMA 直接内存存取（Direct Memory Access），是一种允许外围设备（硬件子系统）直接访问系统主内存的机制。目前大多数的硬件设备，包括磁盘控制器、网卡、显卡以及声卡等都支持 DMA 技术。
  - 2次DMA是必须。

  ![零拷贝对比](img\零拷贝对比.png)

- 传统IO

  - 由用户进程向CPU发起，CPU还要负责将磁盘缓冲区拷贝到内核缓冲区(pageCache)，再从内核缓冲区拷贝到用户缓冲区。为了减少CPU占用，产生了DMA技术，大大解放了CPU。
  - 数据流向：磁盘 -> 内核缓冲区 -> 用户缓冲区 -> 内核缓冲区 ->应用程序
  - read() / write()

  ![传统IO原理](img\传统IO原理.png)

- 零拷贝

  - 零拷贝"（Zero-Copy）技术是一种优化数据传输和处理的方法，它旨在减少操作系统在处理数据时的内存复制次数，从而提高效率并减少CPU使用率。

  - mmap+write()

    - mmap 是 Linux 提供的一种内存映射文件方法，即将一个进程的地址空间中的一段虚拟地址映射到磁盘文件地址使用 mmap 的目的是将内核中读缓冲区（read buffer）的地址与用户空间的缓冲区（user buffer）进行映射。从而实现内核缓冲区与应用程序内存的共享，省去了将数据从内核读缓冲区（read buffer）拷贝到用户缓冲区（user buffer）的过程。
    - mmap 主要的用处是提高 I/O 性能，特别是针对大文件。对于小文件，内存映射文件反而会导致碎片空间的浪费。

    ![mmap+write零拷贝](img\mmap+write零拷贝.png)

  - Sendfile()

    - Linux系统中的sendfile系统调用允许直接将文件内容从一个文件描述符（通常是磁盘上的文件）发送到另一个文件描述符（如一个套接字，代表网络连接）。此操作在内核空间完成，避免了数据在用户空间和内核空间之间的复制。

    

    ![Sendfile零拷贝](img\Sendfile零拷贝.png)

  - Sendfile() + DMA Gather Copy

    - 将内核空间的读缓冲区（read buffer）中对应的数据描述信息（内存地址、地址偏移量）记录到相应的网络缓冲区（ socket buffer）中，由 DMA 根据内存地址、地址偏移量将数据批量地从读缓冲区（read buffer）拷贝到网卡设备中
    -  本质和虚拟内存映射的思路类似，DMA 引擎直接利用 gather 操作将页缓存中数据打包发送到网络中即可
    - 只适用于将数据从文件拷贝到 socket 套接字上的传输过程

    ![Sendfile+DMA Gather Copy](img\Sendfile+DMA Gather Copy.png)

  - Splice

    - 相当于在Sendfile+DMA gather copy上的提升，Splice 系统调用可以在内核空间的读缓冲区（read buffer）和网络缓冲区（socket buffer）之间建立管道（pipeline），从而避免了两者之间的 CPU 拷贝操作

    ![Splice零拷贝](img\Splice零拷贝.png)

- example

  ```java
  import java.io.FileInputStream;
  import java.io.FileOutputStream;
  import java.nio.channels.FileChannel;
  
  public class ZeroCopyExample {
  
      // 实现文件复制
      public static void main(String[] args) throws Exception {
          String srcFilePath = "C:\\Users\\beilinanju\\Desktop\\java代码\\LeetCode\\src\\main\\resources\\out.txt";
          String destFilePath = "C:\\Users\\beilinanju\\Desktop\\java代码\\LeetCode\\src\\main\\resources\\destination1.txt";
  
          try (FileInputStream fis = new FileInputStream(srcFilePath);
               FileOutputStream fos = new FileOutputStream(destFilePath);
               FileChannel srcChannel = fis.getChannel();
               FileChannel destChannel = fos.getChannel()) {
  
              // 低层是native方法
              long bytesTransferred = srcChannel.transferTo(0,srcChannel.size(),destChannel);
              // 或者
              // long bytesTransferred = destChannel.transferFrom(srcChannel, 0, srcChannel.size());
              System.out.println("文件传输完成，共传输了 " + bytesTransferred + " 字节");
          }
      }
  }
  
  
  ```

## 字符编码

- java

  - Java中的char类型（2个字节）可以表示的Unicode字符的范围是从U+0000到U+FFFF。这个范围包含了Unicode字符集的基本多文种平面（BMP）中的所有字符。基本多文种平面包含大部分常用的字符，包括ASCII字符、拉丁字符、常用汉字等
  -  Java使用一对char类型来表示Unicode辅助平面（Non-BMP）的字符。这对char类型称为代理对（surrogate pair），其中一个char类型表示高位（高代理项，High Surrogate），另一个char类型表示低位（低代理项，Low Surrogate）
  - 源文件的编码依赖于IDE，这里主要针对数据处理的编码

- Unicode标准

  - Unicode

    - 是国际字符集标准，定义了字符与数字之间的映射关系。是一个标准
    - 编码范围是 0x0000 - 0x10FFFF。它将世界各种语言的每个字符定义一个唯一的二进制数值（也叫码点，Code Point）。最前面的0x0000 到0x10FFFF码点称为基本平面（ BMP ），包括了大部分常用字符，需要 1 到 2个字节来表示。例如：汉字 "中" 的码点是 0x4E2D，大写字母 A 的码点是 0x41。 后面的0x01000 到0x10FFFF码点称为辅助平面(Non-BMP)，共有 2^20 个，如生僻字"吉"的 Unicode 码点为 0x20BB7. 

  - 实现

    - UTF-8编码

      - 是一种变长字符编码，被定义为将码点编码为 1 至 4 个字节。常用字符需要3字节以下的编码，更加节省空间。
      - 表示ASCII码只需要一个字节，对英文友好

      ![Unicode与UTF-8码元对应表](img\Unicode与UTF-8码元对应表.png)

    - UTF-16编码

      - 每一个字符存储都需要 2 个字节或 4 个字节（特殊符号）。在基本平面内，从 U+D800 到 U+DFFF 是一个空段。UTF-16 将这 20 个二进制位分成两半，前 10 位映射在 U+D800 到 U+DBFF，后 10 位映射在 U+DC00 到 U+DFFFF。

      ![UTF-16与Unicode码元对应表](img\UTF-16与Unicode码元对应表.png)

- 非Unicode标准

  - ASCII
    - （American Standard Code for Information Interchange，美国信息交换标准代码）是一种基于拉丁字母的字符编码标准。它最早于1963年发布，最后一次更新是在1986年，定义了128个字符的编码，包括英文大小写字母、数字0-9、标点符号、控制字符（如换行符、回车符等）以及其他一些特殊符号。ASCII码使用7位二进制数来表示一个字符，因此可表示的字符范围是0-127。
    - 后来发展出了扩展ASCII码，使用全部8位（即0-255的范围），额外表示包括更多的国际字符、图形符号等。但扩展ASCII码并没有统一的标准，不同的系统和国家可能有不同的实现方式。
  - GBK
    - 是中国大陆地区常用的字符编码标准，它是GB2312的扩展，不仅包含了GB2312的所有字符，还加入了很多繁体字、标点符号、图形符号、日韩汉字等，总共收录了2万多个字符。GBK使用双字节编码，每个字符占用2个字节。它主要面向简体中文环境，不支持Unicode的全部字符集，因此国际化能力远不如Unicode系列编码。

- example

  ```java
  import java.io.*;
  import java.nio.charset.Charset;
  import java.nio.charset.StandardCharsets;
  
  // 居然是nio包下的
  public class Main {
      public static void main(String[] args) {
          // UTF-8
          char letterA = '\u0041'; // 使用Unicode码点表示大写字母A
          System.out.println(letterA); // A
  
          // UTF-16
          String emoji = "\uD83D\uDE00"; // 表情符号"😀"的UTF-16编码表示，使用代理对
          System.out.println(emoji); // 😀
  
          // Charset
          String content = "你好，世界！"; // GBK在IDEA的中文乱码，跟IDEA编码有关，但是记事本打开旧没啥问题，正常显式
          try (FileWriter writer = new FileWriter("C:\\Users\\beilinanju\\Desktop\\java代码\\LeetCode\\src\\main\\resources\\std.txt", Charset.forName("GBK"))) {
              writer.write(content);
          } catch (IOException e) {
              e.printStackTrace();
          }
  
          // StandardCharsets
          try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                  new FileInputStream("C:\\Users\\beilinanju\\Desktop\\java代码\\LeetCode\\src\\main\\resources\\in.txt"), StandardCharsets.UTF_8))) {
              String line;
              while ((line = reader.readLine()) != null) {
                  System.out.println(line);
              }
          } catch (IOException e) {
              e.printStackTrace();
          }
      }
  }
  ```

  

# ==高级==

## ==异常==

### 异常继承结构

- 异常类型

  - 非检查型异常：派生于Error类或RuntimeException的异常
    - 运行时才会感觉得到
  - 检查型：除了派生于Error类或RuntimeException的异常的所有异常
    - 一定程度上可以预计，编译时必须处理

  ```java
  /*
  	都是JVM抛出
      总：Throwable
              Exception(异常
                  编译时异常
                      ClassNotFoundException(试图使用一个不存在的类
  
                      IOException(输入输出的异常
  
                  RuntimeException(运行时异常
                      ArithmeticException(一个整数除以0
  
                      NullPointerException(空引用访问对象
  
                      IndexOutOfBoundsException(数组越界
  
                      IllegalArgumentException(传递参数异常
  
              Error(系统错误
                  LinkageError(依赖的类编译前后进行了修改
  
                  VirtualMachineError(JVM崩溃
       */
  ```

### 异常使用

- 使用技巧
  - 优先使用简单的测试。如if判断除数是否是0，效率更高
  - 尽量抛出明细的异常（也即是子类）。如只抛出很多RuntimeException代码逻辑就有点乱的。

- 抛出异常

  ```java
  class MyClass{
      ...
      public String readData()throws EOFException{//方法头抛出用throws
          ...
          while(...){
              if(!in.hashNext()){
                  if(n<len){
                      throw new EOFException();//方法内抛出用throw
                  }
              }
          }
          return s;
      }
          
  }
  ```

- 捕获异常

  - try-catch-finally
  - 在try中定义的变量无法在finally中使用、关闭

  ```java
  try{
      //没有抛出catch中指定的异常则执行下去
  }catch(Exception e){
      //try中抛出了指定异常才执行
     
  }finally{
      //一定执行
  }
  
  //多个异常，应当子类写在前面。否则父类会覆盖子类
  try{
      
  }catch(FileNotFoundException e){
      
  }catch(IOException e){
      
  }finally{
      
  }
  //或者
  catch(FileNotFoundException |IOException e)
  ```

- 打印异常信息

  ```java
  //返回描述信息
  System.out.println(ex.getMessage());
  System.out.println(ex.toString());
  //返回堆栈跟踪元素数组
  System.out.println(ex.getStackTrace(
  //打印Throwable对象以及它的调用堆栈信息
  ex.printStackTrace();
  
  //打印堆栈信息
  var t=new Throwable();
  var out=new StringWrite();
  t.printStackTrace(new PrintWriter(out));
  String description=out.toString();
  //更简洁的
  StackWalker walker=StackWalker.getInstance();
  walker.forEach(frame->analyze name);//没看懂，卷2的吧
  //或者
  walker.walk(stream->process stream);
  ```

- 自定义异常

  ```java
  /*
  一般继承Exception或其子类都行
  习惯做法：2个构造器
  	一个无参
  	一个返回一个描述详细信息的字符串
  */
  class MyException extends Exception{
      public Exception(){}
      public Exception(String s){
          super.(s);
      }
  }
  
  class Test{
      public void test(){
          throw new MyException("出错了");
      }
  }
  ```

- 重新抛出异常

  ```java
  //再次抛出异常
  //1.可能会丢失原始异常细节
  try{
      
  }catch(SQLException e){
      throw new ServeltException("database error"+e.getMessage());
  }
  
  //2.将原始异常设置为新异常的原因。
  public void fun1(){
      try{
          
      }catch(SQLException e){
          throw new ServletException("database error").initCause(e);        
      }finally{
          
      }
  }
  
  public void fun2()[
      try{
          fun1();
      }catch(ServletException e){
          Throwalbe cause=e.getCause();//获取原始异常
      }finally{
          
      }
  ]
      
  //3.只是记录下，再重新抛出原来的
  try{
      
  }catch(Exception e){
      logger.log(level,message,e);
      throw e;
  }finally{
      
  }
  ```

- 嵌套try

  ```java
  //可以没有catch
  try{
      
  }finally{
      
  }
  
  //分析以下代码
  //如果在关闭资源的时候再发生异常呢？就没有来得及处理
  InputStream in=...;
  try{
      
  }catch(IOException e){
      
  }finally{
      in.close();
  }
  
  // 解决方法，内层try确保关闭流，外层try确保报告出现的错误
  try{
      try{
          ...
      }finally{
          in.close();
      }
  }catch(IOException e){
      
  }
  //功能清楚，更加安全
  ```

### try-with-Resource

- 机制

  - 无论是否发生异常，都会调用close()方法
  - 关于是否会自动释放锁：那就得看在close()是否实现了释放了。

  ```java
  public interface AutoCloseable {
      // 该接口只有一个方法，具体行为由实现类定义
      void close() throws Exception;
  }
  
  /*
  当然这里只是为了抛出跟资源关闭的异常
  有异常会自动抛出
  无论有没有都执行close()
  */
  
  try(resource re){}
  /*
  AutoClouseable接口
  	void close()throws Exception
  Closeable接口（是Auto...的子接口。
  	void close()throws IOException
  都是只有一个方法，只不过抛出的异常不同
  对于实现了以上2个之一接口的资源类，都可以用try-with-Resources语法
  */
  public static void main(String[] args) throws IOException {//方法头得抛出，因为没有处理
          try(var in=new Scanner(new File("C:/user/beiyuan/Desktop/src/一些验证/hello.txt"),//文件名就不能从当前？？？
                  StandardCharsets.UTF_8);
             var out=new Scanner(new File("C:/user/beiyuan/Desktop/src/一些验证/Hello.txt"),
                  StandardCharsets.UTF_8);)//可以管理多个文件
      	{
              while (in.hasNext()){
                  System.out.println(in.next());
              }
          }
  }
  
  
  //也可以是try之前的事实最终变量（不再更改）
  public test(PrintWriter out){
      try(out){
          
      }
  }
  ```



## ==容器==

### Collection

- 继承结构

  ![Collection继承结构图](img\Collection继承结构图.png)

- 7

  ```java
  /*
  Collection接口
  	集合是一个容器，存放对象的引用(基本类型会自动装箱
  	集合用数据结构实现
  	存放的是Object类型引用且同一个集合可以存放不同的引用类型
  */
  /*
  Vector：动态括容数据，线程安全，效率低点
  ArrayList：动态扩容数组，线程不安全，效率高点
  LinkedList：双向链表，线程不安全
  
  Stack：栈，Vector子类
  Queue:队列
  PriorityQueue:优先队列
                存放引用数据类型要自定义比较器Comparator
  HashSet：底层Hash表。存储元素等于HashMap的key部分，无序不重复(只有key，没有value
          不允许重复的值(所以要重写equals和hashCode方法
  TreeSet：底层红黑树存储了TreeMap的key部分，有序不重复（只有key，没有value
  */
  ```

- LinkedList

  - 实现了List、Deque接口，所以可以作为链表、队列（单双向）

  ![LinkedList方法汇总](img\LinkedList方法汇总.png)

### Map

- 继承结构

  ![Map继承结构图](img\Map继承结构图.png)

- example

  ```java
  /*
  Map接口
  	数据以key-value形式存储
  */
  /*
  Hashtable:哈希表，线程安全
  HashMap：哈希表，线程不安全
          无序、不允许重复的key
          链地址法处理哈希冲突：位桶+链表+红黑树
  TreeMap：二叉排序树，按照key排序
  Properties：HashMap，线程安全，key和value只能是String		
  	没错就是配置文件.properties相关的那个
   */
  
  
  // public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {}
  // public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {}
  
  // 
  for (String word : words) {
      wordCounts.merge(word, 1, Integer::sum); // 如果键存在，则将值累加；不存在则插入键值对
  }
  
  // key存在返回值，否则插入key，value为用lambda表达式根据key计算的结果
  for (Integer num: nums) {
      squares.computeIfAbsent(num,key->key*key);
  }
  
  
  // 遍历Map
  /*
  2种方法：
      把Map的key转化为Set
      把Map的key和value转化为Set
   */
  HashMap<Integer,String> mes=new HashMap<Integer,String>();
  mes.put(01,"Ricardo");
  mes.put(02,"
  //01通过可以变成set(不能是HashSet
  Set<Integer> keys=mes.keySet();
  Iterator<Integer> it= keys.iter
  //while和foreach都行
  while(it.hasNext()){
      String str=mes.get(it.next());
  }
  for(Integer key:keys){
      String str1=mes.get(it.next());
  
  //02用entry方法
  Set<Map .Entry<Integer,String >> set=mes.entr
  for(Map.Entry<Integer,String> elem:set){
      Integer a=elem.getKey();
      String str=elem.getValue();
  }
  ```

### 迭代器

- example

  ```java
  Collection ct= new TreeSet();
  ct.add(1);
  ct.add(3);
  ct.add
  //获取迭代器
  Iterator it= ct.iterator();
  //遍历
  while (it.hasNext()){
      System.out.println(it.next());
  }
  
  
  // forEach低层也是基于迭代器
  
  // ArrayList换成Vector也会报错
  // HashMap同理
  public void func(){
      ArrayList<Integer> numList = new ArrayList<>();
      numList.add(3);
      numList.add(1);
      numList.add(2);
      numList.forEach(x->{
          System.out.println(x);
          // numList.add(4); // 遍历时修改会报错java.util.ConcurrentModificationException
      });
  }
  
  // 换成ConcurrentLinkedDeque不会报错，具体行为就看是在要遍历时做什么
  // ConcurrentHashMap同理
  public void func(){
      ConcurrentLinkedDeque<Integer> numList = new ConcurrentLinkedDeque<>();
      numList.add(3);
      numList.add(1);
      numList.add(2);
      numList.forEach(x->{
          System.out.println(x);
          // numList.add(4); // 这个就无限循环了
          numList.removeLast(); // 输出 3 1
      });
  }
  ```



## Stream

- 遍历性能对比

  - MessageFormat.format()数据、12核CPU

  ![Stream遍历耗时对比](img\Stream遍历耗时对比.png)

- 处理流程

  ![Stream流程图](img\Stream流程图.png)

- 操作分类

  - 无状态
    - 元素处理不受前元素影响
  - 有状态
    - 拿到所有元素后才能继续处理
  - 非短路
    - 处理完所有元素才能拿到结果
  - 短路
    - 找到符合条件的即可返回。如 A || B,A为true即为true

  ![Stream操作分类](img\Stream操作分类.png)

- 特性

  - Java中的流具有消费性，进行终止操做后无法再次使用，强调的是无副作用的计算和数据处理过程

- example

  ```java
  // 使用Stream前后对比
  public void basic_stream_api(){
      List<String> fruits = Arrays.asList(" Apple", "Orange ", "Banana \t");
      // 传统处理
      List<String> resList1=new ArrayList<>();
      for (String s : fruits) {
          String fruit = s.trim();
          if (fruit.startsWith("B")) {
              resList1.add(fruit.toUpperCase());
          }
      }
      // Stream
      List<String> resList2 = fruits.stream()
              .map(String::trim)
              .filter(fruit -> fruit.startsWith("B"))
              .map(String::toUpperCase)
              .collect(Collectors.toList());
  }
  
  
  // 消费性
  public void consume_stream(){
      // 这种拿到流变量的做法并不是很好，因为消费后，这个变量就没意义了
      // 还是尽量临时创建
      // IntStream nums = IntStream.of(3, 1, 2, 5, 4,6,7).parallel(); 
      
      List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
      Stream<Integer> stream = numbers.stream();
      
      // 第一次使用流
      List<Integer> squared = stream.map(x -> x * x).collect(Collectors.toList());
      System.out.println(squared); // 输出 [1, 4, 9, 16, 25]
      
      // 尝试再次使用已消费的流
      // java.lang.IllegalStateException: stream has already been operated upon or closed
      List<Integer> doubled = stream.map(x -> x * 2).collect(Collectors.toList());
  }
  
  
  
  // 并行处理三个条件:无状态、不干预、无关联性
  public void parallel_stream(){
      // 多点数据才能发现问题
      List<Integer> sourceList = new ArrayList<>();
      for(int i=1; i<=1000;i++){
          sourceList.add(i);
      }
      
      // 错误做法。不是线程安全收集，并行任务之间有影响。
      List<Integer> targetList = new ArrayList<>();
      sourceList.parallelStream().filter(i->i%2==0).forEach(targetList::add);
      System.out.println(targetList.size()); // 447 417。结果不确定
      
      // 正确做法，使用线程安全集合
      List<Integer> syncTargetList = Collections.synchronizedList(new ArrayList<>());
      sourceList.parallelStream().filter(i->i%2==0).forEach(syncTargetList::add);
      System.out.println(syncTargetList.size()); // 500。一共500个偶数
  }
  ```



## 反射

- 简介
  
  - 程序运行过程中，动态获取类信息、调用对象功能
  - Reflection 是 Java被视为动态语言（运行时改变其结构）的关键，反射机制允许程序在执行期借助于Reflection API取得类的内部信息，并能直接操作任意对象的内部属性及方法
  - 加载完类之后，在对内存的方法去中就产生了一个Class类型的对象（一个类只有一个Class对象），这个对象就包含了完整的类的结构信息（如类上的注解信息。我们可以通过这个对象看到类的结构。这个对象就像一面镜子，透过这个镜子看到类的结构，所以我们形象地称之为：反射
  
- 作用
  - 在运行时分析类
  - 在运行时检查对象，如编写一个适合所有类的同String()方法
  - 实现泛型数组操作代码（泛型代码
  - 利用Method对象，类似于C++的函数指针
  
- 利用Class分析类

  - 简介
    - 保存了Java对象运行时的类型信息的类
    - Class本身也是一个类，只能由系统建立对象
    - 加载的类在JVM中只会有一个对应的Class实例
    - Class类是Reflection的根源，想动态加载运行的类，需先获得相应的对象

  ```java
  // 1.Class类
  // 构造方法
  Constructor getConstructor()
      
  //公共的，包括从父类继 承的
  Field getField()//字段
  Method getMethod()//方法
      
  //所有
  Field [] getDeclaredFields()//所有字段。（父类的？）
  Method[] getDeclareMethods()//类或接口的所有方法。但不包括从父类继承的
      
  //返回一个整数，描述上面3个的修饰符。用Modifier类可以分析这个返回值
  int getModifiers()
  
  // 获取注解    
  public <A extends Annotation> A getAnnotation(Class<A> annotationClass) {}
  
  
  
  // 可获取的Class例子
  public static void main(String[] args) {
      Class c1 = Object.class;
      Class c2 = Collection.class;
      Class c3 = int[].class;
      Class c4 = ElementType.class;
      Class c5 = SuppressWarnings.class;
      Class c6 = Integer.class;
      Class c7 = void.class;
  }
  
  
  //获取一个Class的四种方法
  //jvm只加载一份字节码文件
  
  //1.从对象获取Class。不适用于类
  Class<String> c1=str.getClass();
  
  //2.从类或任意类型获取Class。不适用于对象
  Class<String> c2=String.class;
  Class<Integer> c3=int.class;
  
  //3.由类名获取Class。Class.forName(String className);
  //作用是让jvm加载相应的类，在加载类时会执行类中的静态代码块
  //并返回对应的Class类型
  Class<?> c4= Class.forName("java.lang.String");
  
  //4.类加载器获取
  Class<?> c5 = this.getClass().getClassLoader().loadClass("com.huawei.core.reflection.Persion");
  
  
  //创建对象
  Object o=c1.getConstructor().newInstance(); // 怎样拿到有参的呢
  
  //有参构造
  Constructor con=c1.getConstructor(String.class);
  Object o1=con.newInstance("Hello World");
  Object o2=c5.getConstructor(String.class,Integer.class).newInstace("Tom",23);
  
  
  // 获取类关联的数据文件。(放在jar包) 如图像和声音文件、消息字符串、按键按钮的文本文件
  Class c1=Test.class;
  URL url=cl.getResource("about.txt");
  ```

- Filed

  ```java
  Class c1=harry.getClass();
  Filed f1=c1.getDeclaredFiled();
  
  //设置后可以访问。即使是私有。但是有时候还是不允许访问呢，则抛出异常
  f1.setAccessible(true);
  
  Object obj=f1.get(harry);//访问
  f1.set(f1,"jack");//修改
  
  int i=f1.getModifiers();
  String Modi= Modifier.toString(i);
  System.out.println(Modi);//获取修饰符
  ```

- Method

  ```java
  public Object invoke(Object obj,Object... args) //调用方法  
  //获取方法
  Method m1=Employee.class.getMethod("addInt",int.class,int.class);//方法名和参数
  // m1.setAccessible(true); // 如果想调用私有方法
  m1.invoke(harry,2,3);//对象和参数。毕竟方法是从类获得，调用还得依靠具体对象
  
  Method m2=Employee.class.getConstructor(long.class,String.class);//参数
  Object obj=m2.newInstance(2,"tom");
  ```

- example

  ```java
  
  public static void main(String[] args) throws NoSuchMethodException, NoSuchFieldException, ClassNotFoundException {
      
      // 创建对象
      Class c1 = Class.forName("main.study.day4.User");
      User user = (User)c1.getDeclaredConstrector().newInstance();
      User user2 = (User)c1.getDeclaredConstructor(String.class, int.class).newInstance("Nyima", 20);
    
      // 获得所有公共属性
      Field[] fields = c1.getFields();
      
      // 获得所有属性
      Field[] declaredFields = c1.getDeclaredFields();
      
      // 获得指定属性
      Field name = c1.getDeclaredField("name");
      name.set(user, "Nyima2");
      // 获得所有公有方法
      Method[] methods = c1.getMethods();
      
      // 获得所有方法
      Method[] declaredMethods = c1.getDeclaredMethods();
      
      // 获得指定方法
      Method setAge = c1.getDeclaredMethod("setAge", int.class);
      setAge.invoke(user, 37);
  }
  
  
  
  
  // 获取自定义注解信息
  @Target({ElementType.TYPE})
  @Retention(RetentionPolicy.RUNTIME)
  @interface ClassAnnotation {
  	String name();
  }
  @Target({ElementType.FIELD})
  @Retention(RetentionPolicy.RUNTIME)
  @interface FieldAnnotation {
  	String name();
  }
  @Target({ElementType.METHOD})
  @Retention(RetentionPolicy.RUNTIME)
  @interface MethodAnnotation {
  	String name();
  }
  
  @ClassAnnotation(name = "myStudent")
  class Student {
  	@FieldAnnotation(name = "name")
  	private String name;
  
  	@MethodAnnotation(name = "getName")
  	public String getName() {
  		return name;
  	}
  }
  
  public static void main(String[] args) throws Exception {
  	Class c1 = Class.forName("main.study.day4.Student");
  	Student student = (Student) c1.newInstance();
  	
  	// 获得类的注解
  	Annotation[] declaredAnnotations = c1.getDeclaredAnnotations();
  	System.out.println(Arrays.toString(declaredAnnotations));
  	
  	// 获得属性上的注解
  	Field name = c1.getDeclaredField("name");
  	Annotation annotation = name.getAnnotation(FieldAnnotation.class);
  	System.out.println(annotation);
  	
  	// 获得方法的注解
  	Method getName = c1.getDeclaredMethod("getName");
  	MethodAnnotation methodAnnotation = getName.getAnnotation(MethodAnnotation.class);
  	System.out.println(methodAnnotation);
  }
  ```

  

## 注解

- 概述

  - Java注解（Annotation）是一种元数据形式，它提供了一种安全的方法来将信息附加到代码中（类、方法、变量等），以便在编译时或运行时进行处理。
  - 注解不会直接影响程序的执行逻辑，但可以通过编译器或运行时的工具来读取和处理这些元数据，进而实现诸如编译检查、代码生成、框架配置等功能。
  - Java内置了一些标准注解，同时支持自定义注解。
  - 可通过反射获取类的注解信息

- 自定义注解

  ```java
  /**
   * Annotation 是一种引用数据类型
   * 定义注解
   * 修饰列表  @interface 注解名{
   *
   * }
   * 使用时直接放在要使用的代码上面就可以了
   */
  
  
  // example
  // 定义一个注解
  @Target({ElementType.TYPE,ElementType.METHOD,ElementType.FIELD})//可以被反射
  @Retention(RetentionPolicy.RUNTIME)//只能出现在类，方法，属性上
  public @interface MyAnnotation{
      /**
       * 注解中可以有属性
       * 使用时
       * 01.属性只有一个且名字是value，可以不指定名字
       * 02.属性是default，可以赋值，也可以不赋值
       * 03.其他情况，标明属性名字并赋值
       */
      String name();
      int num() default 2;
      int value() default 4;
  }
  // 使用: 判断对象、方法、属性等上是否持有某个注解、获取注解信息。以作为后续处理依据
  // 多用在框架机制、设计模式中
  public class TestAnnotation {
      public static void main(String[] args) throws ClassNotFoundException {
          Class c=Class.forName("Admin");
          //判断该类是否有@myAnnotation
          boolean ishave=c.isAnnotationPresent(MyAnnotation.class);
          System.out.println(ishave);
          //取得注解上的信息
          MyAnnotation myAnno= (MyAnnotation) c.getAnnotation(MyAnnotation.class);
          System.out.println(myAnno.name());
          //其他用法：判断被注解内容是否合理，
          //如某个类上是否有name这个变量：通过反射来判断1.是否有注解2.是否有该变量
      }
  }
  @MyAnnotation(name = "admin")
  class Admin{
  }
  
  ```

- 元注解

  - 用于注解的注解，控制其他注解的行为、生命周期和目标

  ```java
  package java.lang.annotation;
  
  /**
   * The common interface extended by all annotation interfaces.  Note that an
   * interface that manually extends this one does <i>not</i> define
   * an annotation interface.  Also note that this interface does not itself
   * define an annotation interface.
   *
   * More information about annotation interfaces can be found in section
   * {@jls 9.6} of <cite>The Java Language Specification</cite>.
   *
   * The {@link java.lang.reflect.AnnotatedElement} interface discusses
   * compatibility concerns when evolving an annotation interface from being
   * non-repeatable to being repeatable.
   *
   * @author  Josh Bloch
   * @since   1.5
   */
  public interface Annotation {
      boolean equals(Object obj);
      int hashCode();
      String toString();
      Class<? extends Annotation> annotationType();
  }
  
  
  
  
  // @Retention 定义了注解的生命周期，即注解保留到哪个阶段
  @Documented
  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.ANNOTATION_TYPE)
  public @interface Retention {
      RetentionPolicy value();
  }
  public enum RetentionPolicy {
      /**
       * Annotations are to be discarded by the compiler.
       * 注解只保留在源代码中，编译成字节码时会被丢弃
       */
      SOURCE,
  
      /**
       * Annotations are to be recorded in the class file by the compiler
       * but need not be retained by the VM at run time.  This is the default
       * behavior.
       * 注解保留在字节码文件中，但在运行时JVM不会保留它
       */
      CLASS,
  
      /**
       * Annotations are to be recorded in the class file by the compiler and
       * retained by the VM at run time, so they may be read reflectively.
       *
       * @see java.lang.reflect.AnnotatedElement
       * 注解不仅保留在字节码文件中，而且在运行时可以通过反射访问，这是大多数需要在运行时处理注解的场景所使用的保留策略
       */
      RUNTIME
  }
  
  
  
  // @Target：表明被标注的注解可以出现的位置
  @Documented
  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.ANNOTATION_TYPE)
  public @interface Target {
      ElementType[] value();
  }
  public enum ElementType {
      /** Class, interface (including annotation interface), enum, or record
       * declaration */
      TYPE,
      /** Field declaration (includes enum constants) */
      FIELD,
      /** Method declaration */
      METHOD,
      /** Formal parameter declaration */
      PARAMETER,
      /** Constructor declaration */
      CONSTRUCTOR,
      /** Local variable declaration */
      LOCAL_VARIABLE,
      /** Annotation interface declaration (Formerly known as an annotation type.) */
      ANNOTATION_TYPE,
      /** Package declaration */
      PACKAGE,
      /**
       * Type parameter declaration
       *
       * @since 1.8
       */
      TYPE_PARAMETER,
      /**
       * Use of a type
       *
       * @since 1.8
       */
      TYPE_USE,
      /**
       * Module declaration.
       *
       * @since 9
       */
      MODULE,
      /**
       * Record component
       *
       * @jls 8.10.3 Record Members
       * @jls 9.7.4 Where Annotations May Appear
       *
       * @since 16
       */
      RECORD_COMPONENT;
  }
  
  
  // @Documented 标记此注解在生成JavaDoc文档时是否应该被包含进去
  @Documented
  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.ANNOTATION_TYPE)
  public @interface Documented {
  }
  
  
  // @Inherited
  // 类上的注解默认是不会被子类继承的
  // 类上的注解被@Inherited标记时，这个注解会被其子类继承，尽管它没有显式地写在子类的声明中
  @Documented
  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.ANNOTATION_TYPE)
  public @interface Inherited {
  }
  
  
  @Documented
  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.ANNOTATION_TYPE)
  public @interface Repeatable {
      Class<? extends Annotation> value();
  }
  
  
  @Documented
  @Target(ElementType.FIELD)
  @Retention(RetentionPolicy.SOURCE)
  public @interface Native {
  }
  
  ```

- 常用注解

  ```java
  
  /**
   * 常见注解
   * @Override：被注解的方法打算重写方法
   * @Deprecated：被注解的代码已经过时或者有更好的方法
   * @SuppressWarnings    抑制编译器警告
   * @SafeVarargs         “堆污染警告”
   * @FunctionalInterface     函数式接口
   */
  
  
  
  // @Override 用于标记一个方法是重写父类的方法。编译器会检查这一点，如果没有正确重写，则会报错
  package java.lang;
  @Target(ElementType.METHOD)
  @Retention(RetentionPolicy.SOURCE)
  public @interface Override {
  }
  
  
  // @Deprecated 表示某个类、方法或字段已过时，不推荐使用。编译器会发出警告
  @Documented
  @Retention(RetentionPolicy.RUNTIME)
  @Target(value={CONSTRUCTOR, FIELD, LOCAL_VARIABLE, METHOD, PACKAGE, MODULE, PARAMETER, TYPE})
  public @interface Deprecated {
      String since() default "";
      boolean forRemoval() default false;
  }
  
  
  @Documented
  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.TYPE)
  public @interface FunctionalInterface {}
  ```

  

## 正则表达式

- 简介

  - 不仅限于某种语言，但不同语言可能有细微差别

  - Java中的正则表达式是基于Perl语言的，它提供了一种强大的文本处理方式，可以用于搜索、替换、提取字符串中的特定模式。

  - Java中两个反斜杠表示反斜杠，第一个斜杠起到转义第二个斜杠的作用

    ```java
    // 下面语法中的单斜杠都换成双斜杠\\, 如"\\d"
    ```

- 语法

  - 正则表达式中的特殊语义字符称为元字符(Meta Character)

  - 分类

    - 字符类

      ![正则语法字符类](img\正则语法字符类.png)

    - 定位类

      ![正则语法定位类](img\正则语法定位类.png)

    - 限定类

      ![正则语法限定类](img\正则语法限定类.png)

    - 分组与反向

      ![正则语法分组与反向](img\正则语法分组与反向.png)

    - 或

      - 竖线 | 
      - 如：th(e|is|at) 可匹配the、this、that

    - 转义

      - \

- Api

  - Pattern类

    - 简介

      - 正则表达式的编译表示
      - 正则表达式必须首先被编译成`Pattern`对象，然后才能用于创建`Matcher`对象进行匹配操作。

    - 常用方法

      - compile(String regex)：编译给定的正则表达式字符串，返回一个Pattern实例。

        compile(String regex, int flags)：编译给定的正则表达式字符串，并允许指定匹配模式（如忽略大小写、多行模式等），返回一个Pattern实例

      - matcher(CharSequence input)：创建一个匹配器，尝试对输入的字符序列进行匹配

      - split(CharSequence input)：使用此模式将输入序列分割成字符串数组

      - pattern()：返回正则表达式的字符串表示形式

      - flags()：返回用于编译此模式的标志

  - Matcher类

    - 简介
      - 对输入字符串进行解释和匹配的引擎，用于对输入的字符序列进行匹配操作
      - 维护了对输入序列的引用以及一个指向当前匹配结果起始位置的索引
      - Matcher对象不能直接通过构造函数创建，需通过Pattern对象的matcher方法获得
    - 常用方法
      - matches()：尝试将整个输入序列与模式匹配，返回boolean值
      - find()：查找与该模式匹配的下一个子序列
      - group()：返回由上一次匹配操作所匹配的子序列。对于`matches()`或`find()`成功后调用有效
      - group(int group)：返回指定组匹配的子序列
      - start()：返回上一次匹配的起始索引
      - end()：返回上一次匹配的结束索引
      - lookingAt()：检查输入序列是否以与此模式匹配的序列开始
      - replaceFirst(String replacement)：将匹配的第一个子序列替换为指定的replacement，并返回替换后的字符串
      - replaceAll(String replacement)：将所有匹配的子序列替换为指定的replacement，并返回替换后的字符串
      - appendReplacement(StringBuffer sb, String replacement)：将当前匹配的部分替换为replacement，并将结果追加到给定的StringBuffer对象中。常用于分步替换字符串中的匹配项

  - PattrenSyntaxException

    - 正则表达式语法错误，非强制异常类

- example

  ```java
  import java.util.regex.Matcher;
  import java.util.regex.Pattern;
  
  public class RegexExample {
      public static void main(String[] args) {
          regexNumber();
          regexEmail();
          regexUrl();
          replaceWord();
          replace_all_sensitive_information();
      }
  
      // 匹配数字
      public static void regexNumber(){
          String content = "The year is 2023 and the temperature is 25 degrees.";
          String regex = "\\d+";
          Pattern pattern = Pattern.compile(regex);
          Matcher matcher = pattern.matcher(content);
          while (matcher.find()) {
              System.out.println(matcher.group()); // 2023 25
          }
      }
  
      // 匹配邮箱
      public static void regexEmail(){
          String regex = "\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
          Pattern pattern = Pattern.compile(regex);
          Matcher matcher = pattern.matcher("example@example.com");
          boolean isMatch = matcher.matches();
          System.out.println(isMatch); // true
      }
  
      // 匹配网址
      public static void regexUrl(){
          String text = "访问我们的网站 https://www.example.com 或者 http://blog.example.net/ 获取更多信息!";
          String urlPattern = "(https?://[\\w.-]+(?:\\.[\\w\\.-]+)+[\\w\\-._~:/?#\\[\\]@!$&'()*+,;=]*)";
          Pattern pattern = Pattern.compile(urlPattern);
          Matcher matcher = pattern.matcher(text);
          while (matcher.find()) {
              System.out.println(matcher.group()); // https://www.example.com http://blog.example.net/
          }
      }
  
      // 替换
      public static void replaceWord(){
          String input = "Hello world!";
          String regex = "world";
          String replacement = "Java";
          String result = Pattern.compile(regex).matcher(input).replaceAll(replacement);
          System.out.println(result); // Hello Java!
      }
      
      // 敏感信息屏蔽
      public static void replace_all_sensitive_information() {
          String input = "{ \"name\": \"Tony\",\"email_password_new\" : \"abc123\",\"description\": "
                  + "\"I am Tony.\",\"email_password_old\" : \"abc111\",\"my_bankcard_new\" : \"111111\","
                  + "\"my_bankcard_old\" : \"222222\"}";
          String regex = "(\".*?(?:password|bankcard).*?\")(\\w*)(\")";
          String output = input.replaceAll(regex, "$1***$3");
          System.out.println("output = " + output);
          // output = { "name": "Tony","email_password_new" : "***","description": "I am Tony.","email_password_old" : "***","my_bankcard_new" : "***","my_bankcard_old" : "***"}
      } 
  }
  ```

## Socket

- 简介

  - Socket（套接字）IP地址和端口号，能够唯一标识网络上的一个服务
  - Java中的Socket编程基于TCP/IP协议

- example

  - 服务器端Socket用于监听特定的端口，等待客户端的连接请求
  - 客户端Socket向服务器发送连接请求

  ```java
  import java.io.*;
  import java.net.*;
  
  // 服务端
  public class ServerExample {
      public static void main(String[] args) throws IOException {
          // 监听端口
          int port = 12345;
          ServerSocket serverSocket = new ServerSocket(port);
          System.out.println("服务器正在监听端口 " + port);
          while (true) {
              // 等待客户端连接
              Socket clientSocket = serverSocket.accept();
              System.out.println("客户端已连接");
              // 创建新线程处理客户端请求，以支持并发
              new Thread(new ClientHandler(clientSocket)).start();
          }
      }
  
      static class ClientHandler implements Runnable {
          private Socket clientSocket;
          public ClientHandler(Socket socket) {
              this.clientSocket = socket;
          }
          @Override
          public void run() {
              try (
                      BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                      PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
              ) {
                  String inputLine;
                  while ((inputLine = in.readLine()) != null) {
                      System.out.println("从客户端接收: " + inputLine);
                      if ("exit".equalsIgnoreCase(inputLine)) {
                          break;
                      }
                      out.println("服务器回应: " + inputLine.toUpperCase());
                  }
              } catch (IOException e) {
                  e.printStackTrace();
              } finally {
                  try {
                      clientSocket.close();
                  } catch (IOException e) {
                      e.printStackTrace();
                  }
              }
          }
      }
  }
  
  
  // 客户端
  public class ClientExample {
      public static void main(String[] args) throws IOException {
          String host = "localhost"; // 或者服务器的实际IP地址
          int port = 12345;
          try (Socket socket = new Socket(host, port);
               PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
               BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
               BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))) {
              System.out.println("已连接到服务器\n");
              String userInput;
              while ((userInput = stdIn.readLine()) != null) {
                  out.println(userInput);
                  if ("exit".equalsIgnoreCase(userInput)) {
                      break;
                  }
                  System.out.println("服务器回复: " + in.readLine());
              }
          }
      }
  }
  ```

  

## main参数

- public static void main(String[] args) {}
- JVM调用main方法时会自动传递一个String数组过来
  - args.length默认值是0,但args不是null，不会空指针异常
    - 平时也可以创建长度为0的数组，表示没有任何数据
- 在运行时在类名后面传递String数组
  - 如：java Test01 abc xyz		
  - 输出时可以args[i]
  - 用IDEA时用Run->Edit Confi…->Program arguments输入就可以了
- 作用：需要用户输入密码才可以登录系统

## 双亲委派机制

```java
/**
     * 加载class文件
     *
     * 自带的3个类加载器
     *
     *  启动类加载器（父
     *      首先启动
     *      加载JDK最核心类库rt.jar
     *
     *  扩展类加载器（母
     *      启动类加载器加载不到时
     *      加载ext/*.jar
     *
     *  应用类加载器
     *      启动类加载器和扩展类加载器都加载不到时
     *      加载环境变量classpath中的类
     */

    /**
     *   保证先使用自带的类
     *  若有同名类，先使用核心类库中的
     *  防止用户自定义与java核心类库相同的类植入病毒
     */
```

## .properties文件

- 流读取

  ```java
  /*
  class.properties配置文件中内容。左右都不用写双引号""。也不用分号;结尾
  className=java.lang.String
  */
  public static void main(String[] args) throws IllegalAccessException, InstantiationException, IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException {
  
          //通过配置文件创建对象
          /*
          low点的
           String path=Thread.currentThread().getContextClassLoader().
                  getResource("class.properties").getPath();
          FileReader reader=new FileReader(path);
          下面高级点以流形式直接返回
           */
  
          InputStream reader=Thread.currentThread().getContextClassLoader()
                  .getResourceAsStream("class.properties");//路径是相对于Module下的src为根路径
  
      	// FileReader reader=new Filereader("class.properties");还没试验成功
          Properties pro=new Properties();
          pro.load(reader);
          reader.close();
  
          Class c2=Class.forName(pro.getProperty("className"));
          //创建对象的类本身要存在
          Constructor con=c2.getConstructor(String.class);
          Object obj1 =con.newInstance("Hello World");
  
          System.out.println(obj1.toString());
          System.out.println(obj1 instanceof String);//true。但是吧，本质还是Object
  
          if(obj1 instanceof  String){
              String obj2=(String)obj1;
              System.out.println(obj2.charAt(0));//转型后可使用其他方法
          }
      }
  
  ```

- ResourceBundle读取

  ```java
  //还有更加简单的
  /**
   * 只绑定类路径下.propeties文件
   * 写路径时不用后缀
   * 目前最简单的方法了
   */
  ResourceBundle bundle=ResourceBundle.getBundle("class");
  String className=bundle.getString("className");
  Class c3=Class.forName(className);
  ```

  

# ==并发编程==

## 多线程

- 创建线程

  ```java
  import java.util.concurrent.Callable;
  import java.util.concurrent.FutureTask;
  import java.util.concurrent.ExecutorService;
  import java.util.concurrent.Executors;
  
  // 1.继承Thread类，重写run方法
  class MyThread extends Thread {
      public void run() {
          System.out.println("通过继承Thread类创建线程");
      }
  }
  // 2.实现Runnable接口，该接口只有一个run方法
  class MyRunnable implements Runnable {
      public void run() {
          System.out.println("通过实现Runnable接口创建线程");
      }
  }
  // 3.实现Callable接口，该接口只有一个call方法
  class MyCallable implements Callable<String> {
      public String call() throws Exception {
          return "通过实现Callable接口创建线程，并有返回值";
      }
  }
  
  
  // 4.线程池
  class Task implements Runnable {
      @Override
      public void run() {
          System.out.println("通过线程池执行任务");
      }
  }
  
  
  public class Main {
      public static void main(String[] args) {
          
          // 1.Thread调用start()启动线程,而不是run()
          MyThread t = new MyThread();
          t.start(); 
          
          // 2.Runnable要依靠Thread类启动、管理
          MyRunnable r = new MyRunnable();
          Thread rt = new Thread(r);
          rt.start();
          
          // 3.Callable
          MyCallable c = new MyCallable();
          // FutureTask实现了RunnableFuture接口，而RunnableFuture接口继承Runnable接口和Future接口
          FutureTask<String> ft = new FutureTask<>(c);
          Thread ct = new Thread(ft);
          ct.start();
          try {
              System.out.println(ft.get()); // 获取返回值
          } catch (Exception e) {
              e.printStackTrace();
          }
          
          
          // 4.线程池。 任务实现run方法，提交到线程池
          ExecutorService executor = Executors.newFixedThreadPool(2); // 创建固定大小的线程池
          for (int i = 0; i < 5; i++) {
              // 提交任务到线程池执行
              executor.submit(new Task()); 
          }
          // 关闭线程池，不再接受新任务，已提交的任务会继续执行
          executor.shutdown(); 
          
      }
  }
  ```

  

## 线程池

- 继承关系图

  - 其中虚线角箭头表示依赖，不是继承或实现

  ![线程池继承关系](img\线程池继承关系.jpg)

- 线程池核心接口、类

  ```java
  - Executor 
      -是最顶层的执行器接口，定义了执行任务的基本方法`execute(Runnable command)`。(只有这一个方法)
  - ExecutorService
      -继承自Executor，扩展了更多管理线程池的方法，如`submit`, `invokeAll`, `shutdown`等。
  - ScheduledExecutorService
      -专门用于执行定时任务或周期性任务的接口。
  - ThreadPoolExecutor
      -实现了ExecutorService接口，是最常见的线程池实现类，提供了高度定制化的线程池配置能力。
  - Executors
      -工厂类，提供了创建不同类型的线程池的便捷方法。
      
  
  // 线程池参数
  // ThreadPoolExecutor为例
  public ThreadPoolExecutor(int corePoolSize,
                            int maximumPoolSize,
                            long keepAliveTime,
                            TimeUnit unit,
                            BlockingQueue<Runnable> workQueue,
                            ThreadFactory threadFactory,
                            RejectedExecutionHandler handler) {
      // ...
  }
  ```

- Executors

  - 

  ```java
  - newFixedThreadPool(int nThreads)
      -创建一个固定大小的线程池，可有效控制并发数，超出的任务会在队列中等待。
  - newSingleThreadExecutor()
      -创建一个只有一个线程的线程池，保证所有任务按照提交顺序执行。
  - newCachedThreadPool()
      -创建一个可缓存的线程池，线程数根据任务需求动态调整，空闲线程会在60秒后被回收。
  - newScheduledThreadPool(int corePoolSize)
      -创建一个固定核心线程数的线程池，支持定时和周期性任务执行。
      
      
  public void fixedThreadPoolExample(){
      ExecutorService executor = Executors.newFixedThreadPool(3); // 创建固定大小线程池
      for (int i = 0; i < 10; i++) {
          final int taskId = i;
          executor.execute(() -> {
              System.out.println("Task ID " + taskId + " running in thread " + Thread.currentThread().getName());
          });
      }
      
      // 由于在main或类似的主线程中需要显式地等待线程池任务结束。
      // 因为不等待的话，主线程马上结束，线程池任务也随之结束，即使任务还没运行完
      try {
          // 等待直到所有任务完成，最长等待3秒。如果超时，取消尚未执行的任务
          if (!executor.awaitTermination(3, TimeUnit.SECONDS)) {
              executor.shutdownNow();
          }
      } catch (InterruptedException e) {
          Thread.currentThread().interrupt(); // 重新设置中断状态
      }finally {
          executor.shutdown();
      }
  }
  
  ```

## 子线程异常

- 简介

  - 在Java中，当子线程发生异常时，默认情况下，这些异常并不会直接传递给主线程或导致程序终止。
  - 不处理这些异常，可能导致子线程异常终止，但主线程对此一无所知，可能会导致资源泄露或程序行为，这不符合预期

- 处理

  ```java
  // 1.在子线程捕获
  ExecutorService executor = Executors.newSingleThreadExecutor();
  final Future<String> future = executor.submit(() -> {
      try {
          // 模拟异常发生
          throw new RuntimeException("子线程异常");
      } catch (Exception e) {
          // 处理异常，例如记录日志
          System.err.println("捕获到异常：" + e.getMessage());
          return "异常信息已处理";
      }
  });
  
  try {
      System.out.println(future.get()); // 获取结果，如果call方法中抛出异常，get()方法会抛出ExecutionException
  } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
  }
  executor.shutdown();
  
  
  // 2.在父线程中捕获
  ExecutorService executor = Executors.newSingleThreadExecutor();
  Future<Integer> future = executor.submit(() -> {
      throw new RuntimeException("计算失败");
  });
  try {
      future.get(); // 这里会抛出ExecutionException，其中包含了原始异常
  } catch (InterruptedException | ExecutionException e) {
      System.out.println("主线程捕获到异常：" + e.getCause().getMessage());
  }
  executor.shutdown();
  
  
  // 3.回调函数处理
  Thread thread = new Thread(() -> {
      throw new RuntimeException("子线程异常");
  });
  thread.setUncaughtExceptionHandler((t, e) -> {
      System.out.println("线程" + t.getName() + "捕获到异常：" + e.getMessage());
  });
  thread.start();
  
  
  ```

  

## ThreadLocal

- 简介
  - ThreadLocal是Java中一个非常有用的类，它提供了线程局部变量。
  - 简单来说，ThreadLocal为每个线程都创建了一个单独的变量副本，每个线程都只能修改自己的副本，从而避免了线程安全问题。
  - 这对于那些需要在多线程环境下隔离某些变量的场景非常有用，比如数据库连接、事务ID等需要在每个线程中独立管理的资源。

- 工作原理

  - 存储结构
    - ThreadLocal并不是直接为每个线程绑定一个单独的对象实例，而是通过每个线程的Thread对象本身的一个ThreadLocalMap（一个自定义的弱键（WeakKey）哈希映射表）来保存变量的副本。键是ThreadLocal实例本身，值是线程本地存储的变量副本。
  - 内存泄漏风险
    - 由于ThreadLocalMap的键是弱引用，如果ThreadLocal对象不再被外部强引用，那么它可能会被垃圾回收，但它的Entry（键值对）仍然在ThreadLocalMap中，这可能导致内存泄漏。因此，在不再需要ThreadLocal时，最好调用`remove()`方法显式清理。

- example

  - ThreadLocal.remove()只会清空与当前线程相关的条目，不会影响其他线程的ThreadLocal变量

  ```java
  public class Main {
      public static final ThreadLocal<String> threadName = new ThreadLocal<>();
      public static void main(String[] args) {
          threadName.set("Main Thread");
          try {
              // 新建一个子线程
              Thread thread = new Thread(() -> {
                  
                  try {
                      System.out.println("ThreadLocal in child thread before setting: " + threadName.get()); // null
                      // 设置子线程中ThreadLocal变量的值
                      threadName.set("Child Thread");
                      System.out.println("ThreadLocal in child thread after setting: " + threadName.get()); // Child Thread
                  } catch (Exception e) {
                      throw new RuntimeException(e);
                  } finally {
                      threadName.remove();
                  }
                  // throw new RuntimeException("iik"); // 子线程的异常不会传到主线程
              });
              // 在主线程中打印ThreadLocal变量的值
              System.out.println("ThreadLocal in main thread before starting child thread: " + threadName.get()); // Main Thread
              // 启动子线程
              thread.start();
              thread.join();
              // 主线程再次打印ThreadLocal变量的值
              System.out.println("ThreadLocal in main thread after starting child thread: " + threadName.get()); // Main Thread
          } catch (InterruptedException e) {
              throw new RuntimeException(e);
          } finally {
              threadName.remove();
          }
          System.out.println(threadName.get()); // null
      }
  }
  ```

## 并发工具类

- import java.util.concurrent.*; // 这个包下

### CyclicBarrier

- 简介

  - 同步工具类，它允许一组线程相互等待，直到组里线程都到达一个共同的屏障点（barrier），线程才能继续执行

- 特点

  - 循环利用：CyclicBarrier可以重置并多次使用。
  - 行动栅栏：在所有参与者达到屏障后，可以指定一个Runnable行动，在所有线程继续执行前运行。
  - 同步点：用于多线程计算中，同步各个线程的进度，常用于分阶段计算或者并行计算后的汇总阶

- example

  ```java
  // 构造方法
  CyclicBarrier(int parties, Runnable barrierAction)
  - `parties`：参与的线程数量，即需要多少个线程到达屏障点才能继续执行。
  - `barrierAction`：可选参数，一个Runnable对象，当所有线程都到达屏障时执行的动作。
  
      
  // 方法
  - `await()`：线程调用此方法表示已经到达屏障，如果所有线程都到达，则所有线程开始执行后续任务；否则，该线程将被阻塞，直到所有线程到达屏障。
  - `reset()`：重置屏障，让CyclicBarrier可以重新使用。
  - `getNumberWaiting()`：返回当前在屏障处等待的线程数量。
     
      
  // 当有任意三个玩家加载完后，开始游戏
  // PS:还是建议每个批次三玩家，不然有些玩家的开始批次就不确定了
  import java.util.concurrent.*;
  public class Main {
      public static void main(String[] args) throws InterruptedException {
          // 创建CyclicBarrier，等待3个线程，屏障动作是打印"汇总计算开始"
          CyclicBarrier barrier = new CyclicBarrier(3, () -> System.out.println("所有玩家加载完毕，游戏开始"));
          // 启动三个任务线程
          for (int i = 0; i < 4; i++) {
              new Thread(new Task(barrier, "玩家-" + i)).start();
          }
          Thread.sleep(1000);
          // barrier.reset(); // 会自动重置
          for (int i = 4; i < 6; i++) {
              new Thread(new Task(barrier, "玩家-" + i)).start();
          }
      }
  
      static class Task implements Runnable {
          private final CyclicBarrier barrier;
          private final String name;
  
          public Task(CyclicBarrier barrier, String name) {
              this.barrier = barrier;
              this.name = name;
          }
  
          @Override
          public void run() {
              try {
                  System.out.println(name + " 开始加载...");
                  Thread.sleep((long) (Math.random() * 1000)); // 模拟任务执行时间
                  System.out.println(name + " 加载结束，等待其他玩家");
                  barrier.await(); // 到达屏障点，等待其他线程
                  System.out.println(name + " 开始游戏");
              } catch (Exception e) {
                  e.printStackTrace();
              }
          }
      }
  }
  ```

### CountdownLatch

- 简介

  - 同步辅助类，它允许一个或多个线程等待其他任意个线程完成一系列操作后再继续执行。
  - `CountDownLatch`如同一个倒计时器，初始化时设置一个计数器的初始值，每当一个线程完成其任务，就会调用`countDown()`方法减少计数器的值，当计数器减至0时，所有在`await()`方法上等待的线程将会被释放，继续执行

- 特点

  - 一次性使用：`CountDownLatch`是一次性的，计数器无法重置。
  - 等待事件：常用于控制多个线程之间的同步，等待某个事件或多个事件发生。
  - 并发计数：可以用来统计完成的任务数量。

- example

  ```java
  // 构造函数
  CountDownLatch(int count);
  - `count`：初始化时的计数值，表示需要等待的事件数量。
      
  // 方法
  - `countDown()`：递减计数器的值，如果计数到达0，则释放所有等待的线程。
  - `await()`：等待计数器到达0，然后继续执行。如果当前计数非0，则当前线程被阻塞，直到计数到达0或者被中断。    
   
      
  // 如程序需要等待多个可以并发的初始化任务完成后才能继续运行
  import java.util.concurrent.*;
  public class Main {
      public static void main(String[] args) throws InterruptedException {
          int taskCount = 5; // 假设有5个初始化任务
          CountDownLatch latch = new CountDownLatch(taskCount);
          ExecutorService executor = Executors.newFixedThreadPool(taskCount);
          // 提交任务到线程池
          for (int i = 0; i < taskCount; i++) {
              int taskId = i;
              executor.submit(() -> {
                  doInitializationTask(taskId);
                  latch.countDown(); // 完成一个任务，计数减1
              });
          }
          // 主线程等待所有任务完成
          latch.await(); // 阻塞，直到所有任务完成
          System.out.println("所有初始化任务完成，应用程序启动...");
          executor.shutdown(); 
      }
      private static void doInitializationTask(int taskId) {
          // 模拟任务执行
          try {
              Thread.sleep((long) (Math.random() * 1000));
          } catch (InterruptedException e) {
              Thread.currentThread().interrupt();
          }
          System.out.println("初始化任务 " + taskId + " 完成");
      }
  }
  ```



### Semaphore

- 简介

  - 主要用于控制对共享资源的访问数量，实现资源的同步访问控制
  - 信号量维护了一个许可集，线程可以通过调用`acquire()`方法获取许可，执行相应操作，然后通过`release()`方法释放许可。
  - 当没有可用许可时，`acquire()`会阻塞线程，直到其他线程释放许可。

- 特点

  - 许可控制：可以用来控制同时访问特定资源的线程数量。
  - 公平与非公平模式：`Semaphore`可以设置为公平模式，按照线程请求许可的顺序来分配许可；非公平模式则不保证这一点，可能允许后来的线程先获取许可。
  - 灵活的同步：提供了比互斥锁（如`ReentrantLock`）更灵活的并发控制手段，适用于管理有限资源的访问。

- example

  ```java
  // 构造方法
  Semaphore(int permits)
  Semaphore(int permits, boolean fair)
  - `permits`：初始化时的许可数量。
  - `fair`：是否采用公平策略，默认为`false`（非公平）。
  
  // 方法
  - `acquire()`：尝试获取一个许可，如果无可用许可则阻塞。
  - `tryAcquire()`：尝试获取一个许可，如果无可用许可则立即返回`false`，不阻塞。
  - `release()`：释放一个许可，增加信号量的许可数，可能会唤醒一个等待的线程。
  - `availablePermits()`：返回当前可用的许可数。
      
      
  // 停车场5个车位
  import java.util.concurrent.*;
  public static void main(String[] args) {
      Semaphore semaphore = new Semaphore(5); // 初始化为5，代表停车场有5个车位
      // 模拟10辆车试图进入停车场
      for (int i = 1; i <= 10; i++) {
          new Thread(() -> {
              try {
                  semaphore.acquire(); // 尝试获取停车位
                  System.out.println("车 " + Thread.currentThread().getName() + " 进入停车场");
                  TimeUnit.SECONDS.sleep((long) (Math.random() * 5)); // 模拟停车时间
                  System.out.println("车 " + Thread.currentThread().getName() + " 离开停车场");
                  semaphore.release(); // 释放停车位
              } catch (InterruptedException e) {
                  Thread.currentThread().interrupt();
              }
          }, "Car " + i).start();
      }
  }
  ```



### Exchanger

- 简介
  - 用于两个线程之间交换数据。当两个线程都到达`Exchanger.exchange()`方法的交换点时，它们会交换数据（对象引用），然后继续执行。
  - `Exchanger`非常适合于实现生产者-消费者模型中的对等交换，或者线程间的数据同步场景，特别是在双方需要交换数据并且一方的输出是另一方的输入时。

- 特点
  - 双向交换：`Exchanger`允许两个线程直接交换数据，而不必通过共享数据结构。
  - 同步点：提供了一个天然的同步点，使得两个线程可以协同工作，直到两者都到达交换点。
  - 多次交换：每到达一次交换点就进行一次交换，可多次
  - 灵活性：交换的数据类型是泛型的，可以是任何对象。

- example

  ```java
  // 构造方法
  Exchanger<T> ()
  
  // 方法
  - `exchange(T x)`：等待另一个线程到达此交换点，然后将给定的对象与该线程的对象交换，两个线程交换的数据类型必须一致。
      
      
  // 生产者消费者交换数据
  // 其他例子：银行两个分行线程交换交易记录
  public class ExchangerExample {
      public static void main(String[] args) {
          Exchanger<String> exchanger = new Exchanger<>();
  
          new Thread(new Producer(exchanger), "Producer").start();
          new Thread(new Consumer(exchanger), "Consumer").start();
      }
      static class Producer implements Runnable {
          private final Exchanger<String> exchanger;
          Producer(Exchanger<String> exchanger) {
              this.exchanger = exchanger;
          }
          @Override
          public void run() {
              String producedData = "Producer生产的数据 data_1";
              try {
                  System.out.println(Thread.currentThread().getName() + " 准备好数据: " + producedData);
                  exchanger.exchange(producedData); // 对面传了null，这里交换过来是null
                  String resultData = exchanger.exchange(null);
                  System.out.println(Thread.currentThread().getName() + " 收到数据: " + resultData);
              } catch (InterruptedException e) {
                  Thread.currentThread().interrupt();
                  e.printStackTrace();
              }
          }
      }
  
      static class Consumer implements Runnable {
          private final Exchanger<String> exchanger;
          Consumer(Exchanger<String> exchanger) {
              this.exchanger = exchanger;
          }
          @Override
          public void run() {
              try {
                  System.out.println(Thread.currentThread().getName() + " 等待接收数据...");
                  String receivedData = exchanger.exchange(null);
                  System.out.println(Thread.currentThread().getName() + " 接收到数据: " + receivedData);
                  String processedData = "Consumer处理后的数据 data_2";
                  System.out.println(Thread.currentThread().getName() + " 准备交换处理后的数据: " + processedData);
                  exchanger.exchange(processedData); // 对面传了null，这里接收到也是null
              } catch (InterruptedException e) {
                  Thread.currentThread().interrupt();
                  e.printStackTrace();
              }
          }
      }
  }
  ```

  

## 原子操作类

- 简介

  - java.util.concurrent.atomic 包下
  - 提供了一种线程安全的方式来执行一些基本类型变量的操作，如增加、减少、替换等，而无需显式地使用锁
  - 通过底层的硬件指令（如CAS，Compare-And-Swap）实现线程安全，避免了传统的锁机制带来的性能开销，是构建高性能并发程序的重要工具

- 特点

  - 原子性：硬件指令CAS保证
  - 可见性
  - 禁止指令重排：本身是原子性不可打断

- 源码

  ```java
  public class AtomicInteger extends Number implements java.io.Serializable {
      private static final Unsafe U = Unsafe.getUnsafe();
      private volatile int value;
      
      public final int getAndSet(int newValue) {
          return U.getAndSetInt(this, VALUE, newValue);
      }
      public final boolean compareAndSet(int expectedValue, int newValue) {
          return U.compareAndSetInt(this, VALUE, expectedValue, newValue);
      }
      public final int addAndGet(int delta) {
          return U.getAndAddInt(this, VALUE, delta) + delta;
      }
      // ...
  }
  ```

- example

  ```java
  // AtomicInteger
  // 方法：get(), set(int newValue), addAndGet(int delta), incrementAndGet()等
  
  /*
  AtomicLong同理,		   example:统计网站访问量
  AtomicBoolean,			example:标记任务是否完成
  AtomicReference<T>, 	example:对象引用原子更新
  */
  
  
  // AtomicInteger线程安全计数器
  public class Counter {
      private final AtomicInteger count = new AtomicInteger(0);
      public void increment() {
          count.incrementAndGet();
      }
      public int value() {
          return count.get();
      }
      public static void main(String[] args) throws InterruptedException {
          Counter counter = new Counter();
          Thread t1 = new Thread(() -> {
              for (int i = 0; i < 10000; i++) {
                  counter.increment();
              }
          });
          Thread t2 = new Thread(() -> {
              for (int i = 0; i < 5000; i++) {
                  counter.increment();
              }
          });
          t1.start();
          t2.start();
          t1.join();
          t2.join();
          System.out.println("Final Count: " + counter.value()); // 15000
      }
  }
  ```

## 并发容器类

- 分类

  - `CopyOnWriteArrayList` 、`CopyOnWriteArraySet`
    - 写时复制（Copy-On-Write, COW）策略：当有线程尝试修改容器（添加、删除等）时，它会创建容器的一个副本，然后在副本上执行修改操作，最后将原始容器的引用指向这个修改后的副本。
    - 这意味着读操作总是从最新的快照读取，而不需要加锁，因此读操作可以并发执行。这种设计非常适合读多写少的场景，但频繁的写操作会导致较多的内存消耗和数据复制成本。
  - `ConcurrentHashMap`
    - Java 7及之前: 使用分段锁（Segment-based locking）技术。将整个哈希表分成多个段（Segment），每个段都有自己的锁，这样多个线程可以同时对不同段进行读写操作，减少了锁的竞争。
    - Java 8及之后: 引入了更多的并发优化：
      - Node CAS: 使用了无锁编程技术，比如在插入新节点时使用CAS（Compare-and-Swap）操作来减少对锁的需求。
      - Tree化: 当桶内链表长度超过一定阈值时，会转换为红黑树，以保持较高的查找效率。
      - 细粒度锁: 仅在需要修改桶时才对桶加锁，进一步减小了锁的范围。
  - `ConcurrentSkipListMap` 、`ConcurrentSkipListSet`
    - 跳表（Skip List）是一种随机化的数据结构，可以在对数时间内完成搜索、插入、删除操作。它通过多层链表结构来实现快速查找，每一层链表都是下一层链表的子集，顶层链表包含所有元素，而底层链表可能只包含少量元素，通过这种方式减少搜索路径。
    - 并发控制: `ConcurrentSkipListMap`使用了类似于`ConcurrentHashMap`的锁分离技术，即每个节点可能有自己的锁，而不是在整个结构上加锁，从而允许多个修改操作并发执行。同时，它还利用了CAS操作来减少对锁的依赖，提高并发性能。
  - `BlockingQueue`
    - 阻塞队列设计用于生产者-消费者模式，其核心在于同步机制，确保了线程安全。
      - ArrayBlockingQueue: 基于数组的有界队列，使用ReentrantLock和条件队列（Condition）来控制入队和出队的阻塞与唤醒。
      - LinkedBlockingQueue: 可选有界或无界，基于链表实现，同样使用两组锁（入队锁和出队锁）和条件队列来管理同步。
      - 其他如PriorityBlockingQueue、SynchronousQueue各有特点，如优先级队列维护了元素的排序，而SynchronousQueue则是一种特殊的队列，每次插入必须等待一个对应移除操作。
  - `ConcurrentLinkedQueue`、`ConcurrentLinkedDeque`
    - 低层是链表

- example

  ```java
  import java.util.concurrent.ConcurrentHashMap;
  import java.util.concurrent.CopyOnWriteArrayList;
  import java.util.concurrent.ConcurrentSkipListMap;
  import java.util.concurrent.BlockingQueue;
  import java.util.concurrent.LinkedBlockingQueue;
  
  // 1.CopyOnWriteArrayList进行for、Iterator遍历时，看不到遍历过程中的修改
  public class DynamicListExample {
      public static void main(String[] args) throws InterruptedException {
          // 遍历修改：CopyOnWriteArrayList不会因为后台线程添加操作而抛出ConcurrentModificationException。ArrayList就会报错
          // List<String> list=new ArrayList<>(Arrays.asList("A", "B", "C"));
          List<String> list = new CopyOnWriteArrayList<>(Arrays.asList("A", "B", "C"));
  
          // 模拟后台线程不断向列表添加新元素
          new Thread(() -> {
              try {
                  Thread.sleep(100);
              } catch (InterruptedException e) {
                  throw new RuntimeException(e);
              }
              for (int i = 1; i <= 3; i++) {
                  list.add("New Item " + i);
              }
          }).start();
  
          // Thread.sleep(1500); // 如果睡眠，那么下面的遍历就能拿到后台线程存入数据
  
          for (String s : list) {
              // 换成Iterator<Integer> iterator = list.iterator();遍历也是一样
              System.out.println(s); // A B C
              Thread.sleep(1500);
          }
      }
  }
  
  
  // 2.ConcurrentHashMap的merge()、computeIfAbsent()线程安全，能够保证原子性。HashMap中的就不行
  // 好像可以设计成缓存？
  public class ConcurrentHashMapExample {
      public static void main(String[] args) {
          String[] words = {"apple", "banana", "apple", "orange", "banana", "apple"};
          Map<String, Integer> wordCounts = new ConcurrentHashMap<>();
          for (String word : words) {
              wordCounts.merge(word, 1, Integer::sum); // 如果键存在，则将值累加；不存在则插入键值对
          }
          wordCounts.forEach((word, count) ->
                  System.out.println(word + ": " + count)
          );
          /*
          banana: 2
          orange: 1
          apple: 3
           */
  
          Integer[] nums = {3, 1, 2, 4, 1, 2};
          Map<Integer, Integer> squares = new ConcurrentHashMap<>();
          for (Integer num: nums) {
              // 这里x表示key
              squares.computeIfAbsent(num,key->key*key);
          }
          squares.forEach((key,square)->{
              System.out.println(key+": "+square);
          });
          /*
          1: 1
          2: 4
          3: 9
          4: 16
           */
      }
  }
  
  
  // 3.ConcurrentSkipList根据key进行排序
  public class ConcurrentSkipListMapExample {
      public static void main(String[] args) {
          NavigableMap<String, Integer> scores = new ConcurrentSkipListMap<>();
          String [] studentNames = {"Tom","Jack","Marry","Jerry","Sam","Robin"};
          // 多线程并发插入成绩
          for (int i = 0; i < 6; i++) {
              final int idx = i;
              final int score = (int)(Math.random() * 100);
              new Thread(() -> {
                  scores.put("Student_" + studentNames[idx], score);
              }).start();
          }
  
          // 等待所有插入完成（实际应用中可能需要更复杂的同步机制） 
          // 不等待的话下面可能什么也拿不到
          try {
              Thread.sleep(1000);
          } catch (InterruptedException e) {
              e.printStackTrace();
          }
          
          scores.forEach((key, value) -> System.out.println(key + ": " + value));
      }
  }
  
  
  
  // 4.LinkedBlockingQueue实现生产者消费者之间传递数据
  public class BlockingQueueExample {
      public static void main(String[] args) {
          BlockingQueue<String> taskQueue = new LinkedBlockingQueue<>(10);
          // 生产者线程
          new Thread(() -> {
              String[] tasks = {"Task1", "Task2", "Task3", "Task4", "Task5"};
              for (String task : tasks) {
                  try {
                      taskQueue.put(task);
                      System.out.println("Produced: " + task);
                      Thread.sleep(100); // 模拟生产间隔
                  } catch (InterruptedException e) {
                      Thread.currentThread().interrupt();
                  }
              }
          }).start();
  
          // 消费者线程
          new Thread(() -> {
              while (true) {
                  try {
                      String task = taskQueue.take();
                      System.out.println("Consumed: " + task);
                     Thread.sleep(150); // 模拟消费间隔
                  } catch (InterruptedException e) {
                      Thread.currentThread().interrupt();
                  }
              }
          }).start();
  
          // 这里好像不用睡眠也可以?原理呢
  
      }
  }
  ```

  

# ==待补充==

- 静态代理和动态代理补充下代码实现
- 补充一些异常细节，比如异常的常用方法

# ==安全编码==

## SecurityManager

- 在Java 17中，`SecurityManager`已被弃用，并计划在未来版本中移除
  - 复杂性和维护成本
    - `SecurityManager`的设计和实现相当复杂，不仅增加了开发者的理解和配置难度，也给维护带来了负担。特别是在处理现代应用程序的多层架构和复杂的权限需求时，它显得不够灵活和高效。
  - 性能影响
    - 由于`SecurityManager`需要对大量的操作进行权限检查，这可能导致应用程序性能下降。特别是对于那些频繁执行敏感操作的高性能应用，这种影响更为显著。
  - 安全性局限
    - 尽管`SecurityManager`旨在提高安全性，但随着时间的推移，它暴露了一些安全漏洞，同时也难以适应现代安全威胁环境，比如高级持续性威胁（APT）、供应链攻击等。它的设计不足以应对现代软件安全挑战。
  - 替代方案的出现
    - 随着Java生态系统的发展，出现了更现代、更灵活的安全解决方案，如模块系统（Project Jigsaw）、容器化技术、以及操作系统和云平台提供的安全功能。这些替代方案在提供安全控制的同时，还能更好地适应现代开发和部署的需求。
  - 不鼓励过度依赖沙箱模型
    - 传统的Java沙箱模型依赖于`SecurityManager`来限制不受信任代码的权限，但这种方法被证明在很多情况下过于严格，限制了应用程序的正常功能，同时又不能完全防止所有安全风险。现代趋势更倾向于最小权限原则和细粒度的权限控制。
- 用到的时候再学

## 异常信息泄露

- 外部接口通过不断尝试filename来获取文件系统结构信息

  ```java
  
  private static void processFile(String filename) throw IOException{
      try(FileInputStream fis = new FileInputStream(filename)){
          // ...
      }catch(FileNotFoundException e){
         
          throw new IOException("Unable to retrieve file:",e);
      }
      // ...
  }
  
  // 抛出异常到外部接口时，注意对异常信息进行脱敏处理、返回错误码的方式
  ```


## 正则注入风险

## ReDos攻击

- NAF引擎的回溯机制
- 规避
  - 长度限制
  - 简化正则
  - 较少分组
  - 白名单校验

# ==动态代理==

## 代理模式

- 提供一个代理对象来访问目标对象，代理对象可以充当客户和目标对象之间的中介。
  - 某些对象不适合直接引用另一个对象，不改变目标对象
  - 类似于第三方。比如发送短信功能，支付功能都不是我们能实现的，得需要运行商来提供
- 作用
  - 控制目标对象的访问
  - 增强业务功能（也可以说是增加一些目标对象没有的功能吧）

## 子类代理

- 子类重写父类的方法，在原方法基础上增加功能
- 由于单继承等，所以灵活性差
- 如

```java
//父类
public class School{
    public void holiday(){
        System.out.println("today is holiday");
    }
}

//子类
public class SubSchool extends School{
    @Override
    public void holiday(){
        try{
        	System.out.println("before holiday");
            super.holiday();
            System.out.println("after holiday");
        }
        catch(Exception e){
            System.out.println("when something wrong");
        }
        
    }
}
```

## 静态代理

- 手动new 代理对象？？
- 代理对象负责增强功能，而核心功能还是由目标对象的同名方法实现
- 特点
  - 目标对象和代理对象实现同一套接口
  - 运行前代理对象就存在？定义好了，不灵活
  - 能够灵活切换目标对象（换个接口的实现类就行了），却
- 例子
  - 服务接口中由sing()
  - 歌手实现接口
  - 助理也实现接口，不sing()中new 歌手，调用歌手的sing()之外，还赠加了预定场地、时间、结算费用等功能
  - 进一步改造，为了代理类能够代理不同歌手，就面向多态（面向接口，接口引用指向实现类），参数传递。
- 不能灵活进行功能切换
  -  如接口增加一个方法，所有目标对象（可能有多个）和代理对象都要改变。
     -  比如现在只能代理sing(),当增加show()时就得改
  -  只能代理接口种固定的方法，代理类功能写死了

## 动态代理

- 代理类对象在程序运行时根据反射机制生成。不需要定义代理类的.java文件，运行时动态创建.class字节码文件，并加载到JVM。（也就是程序运行时根据需要动态创建目标类的代理对象
- 分为JDK动态代理和CGLIB动态代理（子类代理）

## JDK动态代理

- 特点
  - 目标对象要实现业务接口，代理对象不用
  - 代理对象运行前不存在，运行时创建，增加功能切换的灵活性
  - 不能代理目标对象自己的方法（非接口定义的
- 优点
  - 当接口增加show方法时，代理类不用改变。（接口实现类无论静态代理还是动态代理都要根据需要变一变，不想全部都变就添加默认实现）
  - 可以代理接口种任意方法，动态创建得代理类实现了接口的所有方法，灵活
- 前提
  - Proxy.newProxyInstance()：根据加载到JVM的被代理类，动态创建一个代理类及其对象
  - InvocationHandler接口的实现类及其方法invoke()：通过代理类调用a方法时，在这里动态调用被代理类的同名方法a并增强其功能
- 使用jdk中现成的工具类来实现

```java
//java.lang.reflect.Proxy
public static Object newProxyInstance(
    ClassLoader loader,//把目标类.class加载到JVM
    Class<?>[]interfaces,//接口数组。拦截目标对象实现的所有接口
    InvocationHandler h//接口。代理中的逻辑
)
//Method 。通过反射调用目标对象的方法
method.invoke();

//InvocationHandler			接口。实现代理的功能，可以传递一个匿名实现，就是用接口直接实现然后new对象
```

- 工厂类获取代理对象
  - 虽然要工厂类，但是一个工厂可以生产多个代理类对象

```java
public class ProxyFactory {
    Service target;
    public ProxyFactory(Service target){
        this.target=target;
    }

    //获取动态代理对象
    public Object getAgent(){
        return Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                new InvocationHandler() {//匿名实现
                    @Override
                    public Object invoke(
                            Object proxy,//创建好的代理对象
                            Method method, //目标对象的方法
                            Object[] args//目标方法的参数
                    ) throws Throwable {
                        System.out.println("预定场地");
                        //调用目标方法
                        Object obj=method.invoke(target,args);
                        System.out.println("结算费用");
                        return obj;//只是invoke()方法的返回值
                    }
                }
        );
    }
}
```

- 测试

```java
 
public class SuperStarZhou implements Service{
    @Override
    public void sing() {
        System.out.println("Zhou sing..");
    }

    @Override
    public String show(){
        return "Zhou is showing";
    }

    public void drunk(){//接口没有的方法不能代理
        System.out.println("Zhou drunk..");
    }
}


   @Test
    public void testProxy(){
        Service zhou=new SuperStarZhou();
        ProxyFactory factory=new ProxyFactory(zhou);
        Service agent=(Service)factory.getAgent();
        agent.sing();
        String msg=agent.show();//能拿到返回值
        /*
        SuperStarZhou z=(SuperStarZhou)factory.getAgent();
        z.drunk();//报错class com.sun.proxy.$Proxy4 cannot be cast to class org.example.SuperStarZhou
        //进一步说明不是原来对象了
        */
        System.out.println(agent.getClass());
    }
//结果
预定场地
Zhou sing..
结算费用
预定场地
结算费用
class com.sun.proxy.$Proxy4 //已经不是原来的Service了
```

## CGLib动态代理

- 又称子类代理，解决JDK动态代理无法代理非接口方法

- 特点

  - 底层通过字节码框架ASM，转换字节码生成新的类
    - 不鼓励直接使用，因为要求对JVM中.class和指令集很熟悉
    - spring中集成有，spring-core-5.2.5.jar
  - 动态在内存中构建子类对象，重写父类的方法来增强其功能

  - 不能代理final类，final/static方法。（静态方法没有多态

