- 既然cookie没有设置过期时间（浏览器重启后会自动清除），那么session本身也是要设置过期时间的

- User参数只是进行了校验，并没有进行拦截

- orderDetail.html

  ```html
  <!--    （数据库中是datetime)    这里后端传来的不能是LocalDateTime类型 ，可以是java.util.Date  java.sql.Timestamp java.sql.Date   -->
          <tr>
              <td>下单时间</td>
              <td th:text="${#dates.format(order.createDate, 'yyyy-MM-dd HH:mm:ss')}" colspan="2"></td>
          </tr>
  ```

  - 用Timestamp吧

- jmeter请求失败原因之一
  - 异常：服务端拒绝连接请求。客户端请求超出了服务端的处理能力。
    - 处理
      - 降低请求压力，配置和优化服务端最大请求数
    
    - https://baijiahao.baidu.com/s?id=1706095841048890887&wfr=spider&for=pc

- mysql连接失效
  - **The last packet sent successfully to the server was 0 milliseconds ago. The driver has not received any packets from the server.**
  - 从任务管理器重启下mysql就好了。（不行就先关机一个晚上（再重启））
  - 将ip：100.65.134.13改为localhost （亲测非常有效
- 压测
  - 秒杀接口
    - 线程组
      - 1000个，循环10次，手动循环1次（取平均）
  - 优化前:
    - 此时只进行了User对象缓存
    - windows：
      - QPS：1573
    - linux
      - QPS：448  (效果不明显，所以后面用windows的看)
  - 页面缓存
    - 缓存了goodsList,goodsDetail
    - windows
      - QPS:
- 商品列表
  - windows
  - 10000*10
  - 没有进行页面缓存前
    - qps：1823
  - 对goodsLis进行缓存后
    - qps：6759

- @Configuration

  - 作用：保证该类中的Bean只实例化一次
  - 用于定义配置类，可替换xml配置文件，被注解的类内部包含有一个或多个被@Bean注解的方法，
    这些方法将会被AnnotationConfigApplicationContext或AnnotationConfigWebApplicationContext类进行扫描，
  并用于构建bean定义，初始化Spring容器
  - （其实不加也能拿到Bean，但是
  - 下面代码不加的话new UserService()会执行两次
  
  ```java
  @Configuration
  public class AppConfig {
      @Bean
  public UserService userService(){
      return new UserService();
  }
   
  @Bean
  public OrderService orderService(){
      userService();
      return new OrderService();
}
  }	
  ```

