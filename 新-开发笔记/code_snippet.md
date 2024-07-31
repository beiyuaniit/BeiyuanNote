# ==java==

- 函数头注释分别为输入和输出

## 8位16进制异或

- 7

  ```java
  // 61 62 63 64 32 30 31 32 4C 61 62
  // 1F3330A9
  private static String simpleCheckSum(String inputStr) {
          // List<String> list=Arrays.asList(inputStr.split(" ")); // 列表长度固定，不支持修改
  
          List<String> list=new ArrayList<>(Arrays.asList(inputStr.split(" ")));
          while(list.size()%4!=0){
              list.add("FF");
          }
          StringBuilder str=new StringBuilder();
          for(String s:list){
              str.append(s);
          }
          String ans=str.substring(0,8);
          int n=list.size()/4;
          for(int i=1;i<n;i++){
              BigInteger num1 =new BigInteger(str.substring(i*8,i*8+8),16);
              BigInteger num2=new BigInteger(ans,16);
              ans=num1.xor(num2).toString(16).toUpperCase(); // 异或
              while (ans.length()<8){
                  ans="0"+ans;
              }
          }
          return ans;
  }
  ```

## 字符串按照 . 分隔 - 要转义

- 7

  ```java
  public static void main(String[] args) {
          String ipAddress = "192.168.1.1";
          // 根据正则表达式分隔，使用 \\ 转义
          String[] ipParts = ipAddress.split("\\.");
  }
  
  ```

  

## 工厂类-添加功能需要修改代码

- 比如添加乘法功能，直接添加一个Multiple类 ，而无需修改代码

  ```java
  public class Calculator {
      public int calculate(int num1, int num2,String symbol) throws ClassNotFoundException, InvocationTargetException,
              NoSuchMethodException, InstantiationException, IllegalAccessException {
          ComputeFactory factory=new ComputeFactory();
          return factory.getCompute(symbol).compute(num1,num2);
      }
  }
  
  // 接口类
  public interface Computable {
      int compute(int num1,int num2);
  }
  
  // 工厂类
  public class ComputeFactory {
      public Computable getCompute(String symbol) throws ClassNotFoundException, NoSuchMethodException,
              InvocationTargetException, InstantiationException, IllegalAccessException {
          return (Computable) Class.forName(symbol).getDeclaredConstructor().newInstance();
      }
  }
  
  public class Add implements Computable{
      @Override
      public int compute(int num1, int num2) {
          return num1 + num2;
      }
  }
  
  public class Sub implements Computable {
      @Override
      public int compute(int num1, int num2) {
          return num1 - num2;
      }
  }
  
  
  import org.junit.Assert;
  import org.junit.Test;
  public class CalculatorTest {
      @Test
      public void testAdd() throws ClassNotFoundException, InvocationTargetException,
              NoSuchMethodException, InstantiationException, IllegalAccessException {
          Calculator calculator = new Calculator();
          int res = calculator.calculate(2, 3, "tmp.Add"); // 这里用全限定类名
          Assert.assertEquals(5,res);
      }
      @Test
      public void testSub() throws ClassNotFoundException, InvocationTargetException,
              NoSuchMethodException, InstantiationException, IllegalAccessException {
          Calculator calculator=new Calculator();
          int res=calculator.calculate(7,3,"tmp.Sub");
          Assert.assertEquals(4,res);
      }
  }
  
  ```


# ==Apache Commons==

## IO





# ==Guava==



# ==JDBC==

## 步骤

```java
//注册驱动->告诉Java程序要连接的数据库品牌

//获取连接->Jvm进程和数据库进程之间的通道打开。使用后要关闭

//获取数据库操作对象->专门执行SQL语句的对象

//执行SQL语句

//处理查询结果集

//释放资源

```

## mysql中的Driver类

```java
/*
这个类本身就是com.mysql.cj.jdbc.Driver()。只要static代码块执行就相当于创建了自己类的对象，并注册驱动
*/
public class Driver extends NonRegisteringDriver implements java.sql.Driver{
    static{
       try{
           java.sql.DriverManager.registerDriver(new Driver());
       } catch(SQLException E){
           throw new RuntimeException("Can't register driver!");
       }
    }
    ...
}


//注册驱动public static void registerDriver(Driver driver)

/*
第一种
DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
*/

//最常用。字符串参数，可以写到配置文件中
Class.forName("com.mysql.cj.jdbc.Driver");
    
```

## ResultSet

```java
ResultSet rs=stmt.executeQuery(sql);//类似于二维数组
/*
boolean next()。执行一次后指向下一行。若该行有效则返回true，否则false
所以配合while(rs.next()){}就很有用
*/
//rs.next();//一开始迭代器指向第一行之前。执行后指向第一行

while(rs.next()){
   
    //getString()无论在数据库是什么数据类型，都转化为String返回
    /*
    String getString(int columnIndex)
    JDBC中所有下标以1开始
    */
    String id=rs.getString(1);
    String name=rs.getString(2);
    String sno=rs.getString(3);
    String age=rs.getString(4);
    
    /*
    String getString(String columnLabel)
    以列名返回值。程序更加健壮。以下标的话万一顺序乱了呢
    若查询时对列名取了别名，则应该使用别名作为参数，否则报错
    select id as ID,name from student		//ID
    rs.getString("ID");
    */
    String id=rs.getString("id");
    String name=rs.getString("name");
    String sno=rs.getString("sno");
    String age=rs.getString("age");
    
    //其他类型。getInt(),getDouble()...
    int age=rs.getInt("age");
    
}
```

## mysql.properties

```java
driver=com.mysql.cj.jdbc.Driver
url=jdbc:mysql://127.0.0.1:3306/beimysql
user=root
password=beiyuan3721
```



## MySQL.java

- MySQL.java

```java
public class MySQL {
    public void connection(){
        Connection con=null;//放在外面为了在finally中可以关闭
        Statement stmt=null;
        ResultSet rs=null;

        ResourceBundle bundle=ResourceBundle.getBundle("mysql");//src为根路径


        String dirver=bundle.getString("driver");
        String url=bundle.getString("url");
        String user=bundle.getString("user");
        String password=bundle.getString("password");


        try{
            //注册驱动
            Class.forName(dirver);
            //获取连接
            con=DriverManager.getConnection(url,user,password);
            //获取数据库操作对象
            stmt=con.createStatement();
            //执行SQL。可以不用分号结尾。加也可以
            String sql="select * from student";
            rs=stmt.executeQuery(sql);
            //处理结果集
            while (rs.next()){
                System.out.println(rs.getString("name"));
                System.out.println(rs.getInt("age"));
            }
        }catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }finally {
            //释放资源

            if(rs!=null){//先释放小的
                try{
                    rs.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(stmt!=null){
                try{
                    stmt.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }

            if(con!=null){
                try{
                    con.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }

    }
}
```

## SQL注入

- 用户登录

```java
//name和password可以用Map存储

//密码为name' or '1'=1'	也能登录成功		//name是用户名

//获取数据库操作对象
stmt=con.createStatement();
//执行SQL。SQL中字符串要用单引号括起来
String sql="select * from student where name = '"+name+"' and password = '"+password+"'";
rs=stmt.executeQuery(sql);
//处理结果集
if(rs.next()){
    loginSuccess=true;
}
```

- 本质
  - 用户的输入中含有SQL关键字，参与了SQL的编译过程，改变了语义

## 解决SQL注入

- 用Statement的子类,预编译的数据库操作对象PreparedStatement //有d
- 原理：占位符方式，预先对SQL语句的框架进行编译，然后再传参数，参数不参与编译

```java
PreparedStatement ps=null;
//得先传入SQL语句框架
String sql="select * from student where name=? and password =?";//用?作为占位符
ps=con.prepareStatement(sql);//无d

//传值。下标从1开始
ps.setString(1,name);//select * from student where name= 'lisi' and password ='lisi01'
ps.setString(2,password);
//若是ps.setInt(3,100)//则不会加''单引号 select * from student where sno=100

rs=ps.executeQuery();
//处理结果集
if(rs.next()){
    loginSuccess=true;
}
```

- 也可以用填值的方式来增删改查

## Statement和PreparedStatement

- SQL注入

  - Statement存在SQL注入问题

  - 而PreparedStatement可以解决

- 执行效率

  - mysql数据库中，若本条SQL语句和上一条SQL语句完全一样(就连空格等位置也一样)，则不会编译，而是直接执行

  - Statement编译一次执行一次，因为不断有新的name和password导致SQL语句不一样了。

  - PreparedStatement中的SQL是模板，不会改变，所以有新的name和password也可以不用编译，而直接执行。编译一次可执行多次。效率略高

- 安全
  - PreparedStatement会在编写代码时作安全检查。如ps.setString()只能传字符串，不能是其他的。
  - 而Statement直到执行时才可能报错
- 综上所述。大多数情况使用PreparedStatement，极少数情况使用Statement

## 什么时候 使用Statement

- 要求进行SQL语句拼接

```java
select * from student order by sno desc;
改为降序
select * from student order by sno asc;

//如果用PreparedStatement。则会带单引号''
String order="desc"
String sql="select * from student order by sno ?";
ps=con.prepareStatement(sql);
ps.setString(1,order);//会变成select * from student order by sno 'desc'	不合符要求。报错
rs=ps.executeQuery();

//改为
String order="asc";
stmt=con.createStatement();
String sql="select * from student order by sno"+order;
rs=stmt.executeQuery(sql);
```

## 事务

- 自动提交

```java
JDBC中的事务是自动提交的。每执行一条DML（数据库 操作语言）语句都提交一次。
但是实际业务中
    一个功能是多条DML语句联合才能完成
    必须保证在同一个事务中，同时成功或同时失败
```

- 事务控制
  - con.setAutoCommit(false);//取消自动提交
  - con.commit();//手动提交
  - con.rollback();//事务回滚

```java
public void connection(){
        Connection con=null;//放在外面为了在finally中可以关闭
        PreparedStatement ps=null;
        ResultSet rs=null;

        ResourceBundle bundle=ResourceBundle.getBundle("mysql");//src为根路径


        String dirver=bundle.getString("driver");
        String url=bundle.getString("url");
        String user=bundle.getString("user");
        String password=bundle.getString("password");


        try{
            //注册驱动
            Class.forName(dirver);
            //获取连接
            con= DriverManager.getConnection(url,user,password);
            con.setAutoCommit(false);//取消自动提交
            //获取数据库操作对象
            String sql="update student set sno=? where name=?";
            ps=con.prepareStatement(sql);

            //传值
            ps.setInt(1,109);//如果数据库中该类型是数字而不是字符串，则不能使用setString(),用setInt(),
            ps.setString(2,"lisi");
            //执行SQL。
            ps.execute();

            //制造异常，然后事务会回滚
//            String s=null;
//            s.toString();
            con.commit();//手动提交

        }catch (Exception e){
            if(con!=null){//先释放小的
                try{
                    con.rollback();//事务回滚
                }catch (SQLException e1){
                    e1.printStackTrace();
                }
                }
                e.printStackTrace();
            }finally {
                if(ps!=null){
                    try{
                        con.close();
                    }catch (SQLException e){
                        e.printStackTrace();
                    }
                }

                if(con!=null){
                    try{
                        con.close();
                    }catch (SQLException e){
                        e.printStackTrace();
                    }
                }
            }

      }
```

## SQL的执行方式

```java
boolean execute()		//最简单。是否成功
ResultSet executeQuery()	//只有这个能拿到结果集
int executeUpdate()			//更新的数量
```

## 锁

- 悲观锁（行级锁）
  - 当前事务还没结束，其他事务不能修改锁住的记录
  - 在SQL语句后面加上for update即可
  - 不允许并发

```sql
select * from student where ... for update; --所有满足条件的记录都被锁住
```

- 乐观锁
  - 记录被修改后追加一个版本号
  - 若某个线程发现开始事务、提交事务记录的版本号不一致。则当前线程执行回滚
  - 允许并发

## 手写JDBC工具类

- ```java
  package Utils;
  public class JDBCl {
      //jdbc.properties还是放在src下。不然真的找不到了
      private static ResourceBundle bundle=ResourceBundle.getBundle("jdbc");
      
      private static String driver=bundle.getString("driver");
      private static String url=bundle.getString("url");
      private static String user=bundle.getString("user");
      private static String password=bundle.getString("password");
      
      // 注册驱动(加载相关驱动类)
      // 只要执行一次，放在静态代码块中,类加载时执行即可
      static {
          try {
              Class.forName(driver);
          } catch (ClassNotFoundException e) {
              e.printStackTrace();
          }
      }
      
      public static Connection getConnection() throws SQLException {
          Connection con= DriverManager.getConnection(url, user, password);
          return con;
      }
  }
  
  //释放资源
  public static void close(Connection con, Statement ps, ResultSet rs){
          if(rs!=null){//先释放小的
              try{
                  rs.close();
              }catch (SQLException e){
                  e.printStackTrace();
              }
          }
          if(ps!=null){
              try{
                  ps.close();
              }catch (SQLException e){
                  e.printStackTrace();
              }
          }
  
          if(con!=null){
              try{
                  con.close();
              }catch (SQLException e){
                  e.printStackTrace();
              }
          }
}
  ```
  
- 使用工具类

  ```java
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        Connection con=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
  
        try {
            con= JDBC.getConnection();
            //String sql="select * from student order by sno ?";
  		  //ps=con.prepareStatement(sql);
            
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JDBC.close(con,ps,rs);
            //没有结果集的话JDBC.close(con,ps,null);
        }
    }
  ```

  

# ==错误示范==

## 运算溢出

- 3

  ```java
  // 溢出导致无限循环
  for (byte b = Byte.MIN_VALUE; b <= Byte.MAX_VALUE; b++) {
      // ...
      System.out.println(b);
  }
  // b=127时，在加1变成-128
  ```

## 浮点数不能直接用==

- 7

  ```java
  // 无限循环
  for (float f = 0.1f; f != 10.0f; f+=0.1f) {
      // ...
      System.out.println(f);
  }
  // 十进制小数低层用二进制存储，存在精度问题，导致计算偏差
  ```

## 避免过度自动装拆箱

- 3

  ```java
  // 消耗性能
  Integer sum = 0;
  for(int i = 0; i < 1000; i++){
      sum += i; // 运行的是sum = Integer.valueOf(sum.intValue()+i);
  }
  ```

## 重载时包装类和基本类型走不同方法

- 7

  ```java
  // Integer参数走更匹配的方法
  void func(int i){
      System.out.println("Param is int");
  }
  
  void func(Object o){
      System.out.println("Param is Integer");
  }
  
  void test(){
      func(new Integer(3)); // Param is Integer
      func(3); // Param is int
  }
  // 如果没有void func(Object o)，那么func(new Integer(3));也是输出Param is int
  
  ```

  