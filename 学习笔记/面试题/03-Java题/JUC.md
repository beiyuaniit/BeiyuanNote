# ==线程==

## 进程和线程

- 概念
  - 进程
    - 计算机中的程序关于某数据集合上的一次运行活动
  - 线程
    - 操作系统能够进行运算调度的最小单位
    - 具有许多传统进程所具有的特征，故又称为轻型进程
    - 程序计数器，寄存器，栈
- **根本区别**：
  - 进程是操作系统资源分配的基本单位
  - 线程是处理器调度的基本单位
- **资源开销**：
  - 每个进程都有独立的代码和数据空间（程序上下文），程序之间的切换会有较大的开销；
  - 同一进程下的线程共享代码和数据空间，每个线程都有自己独立的运行栈和程序计数器（PC），线程之间切换的开销小。
- **包含关系**：
  - 如果一个进程内有多个线程，则执行过程不是一条线的，而是多条线（线程）共同完成的；
- **执行过程**：
  - 每个独立的进程有程序运行的入口、顺序执行序列和程序出口。
  - 但是线程不能独立执行，必须依存在应用程序中，由应用程序提供多个线程执行控制，两者均可并发执行

## 多线程

- 开销
  - 线程可以比作是轻量级的进程，**线程间的切换和调度的成本远远小于进程**
- 并发性
  - 多核 CPU可运行多个线程，多线程提高系统的并发度
- 利用率
  - 多线程使多核CPU能同时运行，保证CPU利用率

## 线程运行原理

- 栈与栈帧

  - 其实就是线程，每个线程启动后，虚拟机就会为其分配一块**栈内存**
  - 每个栈由多个栈帧（Frame）组成，对应着每次**方法调用时所占用的内存**
  - 每个线程只能有一个活动栈帧，对应着当前正在执行的那个方法
- 上下文切换

  - 线程的 cpu 时间片用完
  - 垃圾回收 有更高优先级的线程需要运行
  - 线程自己调用了 sleep、yield、wait、join、park、synchronized、lock 等方法 

- 保存和恢复现场
  - 线程切换时，需要由操作系统保存当前线程的状态，并恢复另一个线程的状态
  - 状态包括程序计数器、虚拟机栈中每个栈帧的信息，如局部变量、操作数栈、返回地址等
  - 线程切换频繁发生会影响性能

## 变量的线程安全性

- 成员变量和静态变量是否线程安全？

  - 如果它们没有共享，则线程安全
  - 如果它们被共享了，根据它们的状态是否能够改变，又分两种情况
    - 如果只有读操作，则线程安全
    - 如果有读写操作，则这段代码是临界区，需要考虑线程安全

- 局部变量是否线程安全？

  - 局部变量是本身线程安全的

  - 但局部变量引用的对象则未必 （要看该对象是否被共享且被执行了读写操作          这个要记住

    - 如果该对象没有逃离方法的作用范围，它是线程安全的


## 创建线程

- 实现Runnable、Callable接口
  - 实现run方法

  - 优点
    - 线程类只是实现了Runnable接口或Callable接口，还可以继承其他类。
    - 在这种方式下，多个线程可以共享同一个target对象，所以非常适合多个相同线程来处理同一份资源的情况，从而可以将CPU、代码和数据分开，形成清晰的模型，较好地体现了面向对象的思想。

  - 缺点
    - 还要借助Thread，FutureTask类来运行
    - 只有run方法，没有额外提供api

- 继承Thread，Future类
  - 重写run方法
  - 优点
    - 提供了很多跟线程相关的api

  - 缺点
    - 单继承，线程类已经继承了Thread类，所以不能再继承其他父类。

- Runnable和Callable
  - 重写的方法
    - Callable重写的方法是call()
    - Runnable重写的方法是run()。
  - 返回值
    - Callable执行可以拿到一个Future对象，取得返回值
    - Runnable的任务是不能返回值的。
  - 异常处理
    - Callable方法可以抛出异常
    - Runnable不能往上抛，要在run中处理。

## Thread类

- start() 
  
  - 创建一个新线程并执行其run方法
- run()

  - 线程的主方法，定义线程的功能
  - 只是一个定义，直接调用不会创建新的线程
- sleep()

  - 阻塞当前线程，若有锁，并不会释放,从Running 进入 Timed Waiting 状态
  - 时间结束后唤醒就如就绪态，或者可以被interrupt()打断并抛出InterruptedException
- yield ()
    - 让出当前线程，进入就绪态重新竞争cpu
    - 但不能保证别的线程一定能抢到（被刚yield的又抢到
- 线程优先级Priority
  - 1-10   值越大优先级越高
  - 优先级会提示调度器优先调度该线程，但它仅仅是一个提示，调度器可以忽略它
  - 如果 cpu 比较忙，那么优先级高的线程会获得更多的时间片，但 cpu 闲时，优先级几乎没作用
  - 先级低只是意味着获得调度的概率低，并不是优先级低就不会被调度了
  - 优先级的设定建议在`start()`调度前，也就是不写在线程代码中，写的话第一次调度就没有使用到新设置的优先级。至少第二次才能用到（还没设置就又被抢占了...则是第二次之后）
- join()
    - 等待某个线程结束
    - 底层 调用wait()
    - 如在主线程中调用ti.join()，则是主线程等待t1线程结束
- interrupt()
    - 中断一个线程只是为了引起该线程的注意，被中断线程可以决定如何应对中断。
      - 打断运行中的线程，打断标记会被置为true
      - 正常运行的线程在被打断后，**不会停止**，会继续执行。如果要让线程在被打断后停下来，需要**使用打断标记来判断**。
      - 唤醒因sleep wait join阻塞的线程，会将打断标记置为false
- stop
  - stop方法 停止线程运行
    - 可能造成共享资源无法被释放，其他线程无法使用这些共享资源（不推荐
  - suspend（暂停线程）/resume（恢复线程）方法

## 守护线程

- 特殊的线程，独立于控制终端并且周期性地执行某种任务或等待处理某些发生的事件
- 可以理解为后台运行的线程，如GC，会随非守护线程线程的终止而终止

## 线程状态

- 状态转换图
  
  ![线程状态图](images\线程状态图.png)
  
- Java层面六种
  
  - 初始(NEW)：
  - 就绪态（Runnable）
    - 包含了传统的运行态(Running)
  - 阻塞(BLOCKED)：
  - 等待(WAITING)：
  - 超时等待(TIMED_WAITING)
  - 终止(TERMINATED)
  
- Thread类中

  ```java
  public enum State {
      NEW,			// 创建了一个线程对象，但还没有调用start()方法
      RUNNABLE,       // 可在JVM 中运行。这包括正在运行的线程(Running)以及等待 CPU 时间片的线程(Runnable)
      BLOCKED,		// 等待锁。如synchronized
      WAITING,		// 因自身调用没传时间Object.wait()、Thread.join()、LockSupport.park()，等待唤醒。
      TIMED_WAITING,  // 传了时间参数
      TERMINATED;     // 异常终止或执行完毕
  }
  public State getState() {
      return jdk.internal.misc.VM.toThreadState(threadStatus);
  }
  ```

## 定位死锁的方法

- jps+jstack ThreadID

  - 在JAVA控制台中的Terminal中输入**jps**指令可以查看运行中的线程ID，使用**jstack ThreadID**可以查看线程状态。
- jconsole检测死锁
  - 直接在控制台输入jconsole会弹出窗口

## 线程CPU过高

- 处理
  - top：
    - 查看是哪个**进程**占用CPU过高
  - ps H -eo pid, tid（线程id）, %cpu | grep 
    - 定位到线程
  - jstack  pid 
    -   查看线程的具体信息

## 线程死锁如何避免

- 死锁

  - 多个线程同时被阻塞，它们中的一个或者全部都在等待某个资源被释放。由于线程被无限期地阻塞，因此程序不可能正常终止。 
    ![](http://blog-img.coolsen.cn/img/1583327022365_13.png)

- 死锁必须具备以下四个条件：

  - 互斥条件：该资源任意一个时刻只由一个线程占用。
  - 请求与保持条件：一个进程因请求资源而阻塞时，对已获得的资源保持不放。
  - 不剥夺条件:线程已获得的资源在末使用完之前不能被其他线程强行剥夺，只有自己使用完毕后才释放资源。
  - 循环等待条件:若干进程之间形成一种头尾相接的循环等待资源关系。

- 如何避免线程死锁?

  只要破坏产生死锁的四个条件中的其中一个就可以了

  - 破坏互斥条件 
    这个条件我们没有办法破坏，因为我们用锁本来就是想让他们互斥的（临界资源需要互斥访问）
  - 破坏请求与保持条件 
    一次性申请所有的资源。
  - 破坏不剥夺条件 
    占用部分资源的线程进一步申请其他资源时，如果申请不到，可以主动释放它占有的资源。
  - 破坏循环等待条件 
    靠按序申请资源来预防。按某一顺序申请资源，释放资源则反序释放。破坏循环等待条件。
  - 锁排序法：（必须回答出来的点） 
    指定获取锁的顺序，比如某个线程只有获得A锁和B锁，才能对某资源进行操作，在多线程条件下，如何避免死锁？ 
    通过指定锁的获取顺序，比如规定，只有获得A锁的线程才有资格获取B锁，按顺序获取锁就可以避免死锁。这通常被认为是解决死锁很好的一种方法。
  - 使用显式锁中的ReentrantLock.try(long,TimeUnit)来申请锁


## JMM

- JMM（Java Memory Model）是Java程序中用来描述内存模型的规范。

  JMM定义了一组规则和保证，用于规定**多线程环境下**，线程如何与主内存和各自的本地内存进行交互。它确保多线程程序的内存访问具有一致性，避免出现数据竞争、可见性问题和指令重排序等并发错误。

- JMM在Java并发编程中起到了关键的作用，它提供了定义明确的内存模型，并为程序员提供了一些工具和关键字（如volatile、synchronized和Lock等）来控制内存访问和同步，以保证程序的正确性和一致性。

- JMM规定了一些重要的概念和行为：

  1. 主内存（Main Memory）：所有线程共享的内存区域，其中存储了程序的变量和对象实例等。
  2. 本地内存（Thread’s Local Memory）：每个线程独占的内存区域，用于存储该线程的线程栈和本地变量等。
  3. 内存间交互操作：JMM定义了一组规则，确保线程在读/写变量时与主内存进行正确的交互，包括对变量的读写顺序、可见性保证和内存屏障等。

- 通过这些规则，JMM保证了程序可以有了以下特性：

  1. 原子性（Atomicity）：JMM确保对基本类型变量的读/写操作具有原子性，不会被其他线程中断。而对于非原子类型的复合操作，需要额外的同步机制来保证原子性。
  2. 可见性（Visibility）：JMM确保一个线程修改变量的值后，其他线程能够及时看到修改后的值。它使用一些机制（如内存屏障、缓存一致性协议）来确保可见性。
  3. 有序性（Ordering）：JMM定义了一套部分有序的规则，确保线程间的操作按预期顺序执行。它保证了程序的顺序性原则，避免出现指令重排序导致的问题。
  4. happens-before关系：happens-before是JMM中一种偏序关系，用于定义多个操作之间的顺序。如果操作A happens-before操作B，那么A的结果对于B是可见的。例如，如果在一个线程中调用了一个unlock操作，然后在另一个线程中调用了相应的lock操作，那么两个操作之间会有一个happens-before关系



# ==线程同步==

## 线程间通信

- 例如
  - synchronized
  - volatile
  - wait/notify
  - ThreadLocal

## wait(),notify()

- wait()    自己释放锁，进入阻塞
- notify()   唤醒等待的线程，synchronized代码段结束后释放锁
- 要配合synchronized
  - wait和notify的线程之间是存在竞态关系的，要 保证线程间的执行顺序

  - 比如不用锁，那么可能会先notify，后wait。造成wait不会触发
- 额外的
  - notify()调用时并不会真正释放对象锁, 必须等到synchronized方法或者语法块执行完才真正释放锁
  - void wait(long timeoutMillis)
    - 时间到会唤醒，拿到锁则执行，否则继续阻塞

## sleep() 和 wait()

- 区别
  - 来源
    - Thread.sleep()  
    - Object.wait()，可配合notify()使用
  - 释放资源
    - sleep()不会释放占有的锁
    - wait()会释放占有的锁
  - 参数
    - sleep()必须传入时间
    - wait()可传可不传
      - 不传：表示一直阻塞下去
      - 传：时间到唤醒一次
  - 唤醒
    - sleep时间到自动唤醒
    - wait使用notify唤醒，或者时间到了唤醒一次，没有拿到锁继续阻塞

- 相同
  - 两者都可以暂停线程的执行。
  - 都可以被interrupt中断

## CAS

- 概述
  - `Compare and swap`**比较并交换**，它是一条 **CPU 同步原语**，具有原子性，执行连续且不可中断
  - 用于管理对共享数据的并发访问，是一种无锁的非阻塞算法的实现
  - 一种乐观锁，硬件层面的实现，整个过程是原子的。
- 三个值
  - 内存值 V    （变量的当前值）
  - 预期值 A   （线程手里拿到的变量值）
  - 更新值 B     （变量要更新的值）
- 过程
  - 比较V和A的值
    - 相等则将用B覆盖V
    - 不相等则更新A为变量新的值
- 使用
  - CAS有原子性，但不保证可见性。可配合volatile使用
- 问题
  - ABA问题
  - 循环时间过长。

## ABA问题

![img](http://blog-img.coolsen.cn/img/1246845-20200728125438568-1459891419.png)

**1. ABA 问题**

因为CAS是无锁，其他线程也可以执行

并发环境下，假设初始条件是A，去修改数据时，发现是A就会执行修改。但是看到的虽然是A，中间可能发生了A变B，B又变回A的情况。此时A已经非彼A，数据即使成功修改，也可能有问题。

可以通过AtomicStampedReference**解决ABA问题**，它，一个带有标记的原子引用类，通过控制变量值的版本来保证CAS的正确性。

**2. 循环时间长开销**

自旋CAS，如果一直循环执行，一直不成功，会给CPU带来非常大的执行开销。

很多时候，CAS思想体现，是有个自旋次数的，就是为了避开这个耗时问题~

阻塞并等待其他线程唤醒

**3. 只能保证一个变量的原子操作。**

CAS 保证的是对一个变量执行操作的原子性，如果对多个变量操作时，CAS 目前无法直接保证操作的原子性的。

**可以通过这两个方式解决这个问题**：

- 使用互斥锁来保证原子性；
- 将多个变量封装成对象，通过AtomicReference来保证原子性。

## volatile

- 在变量级别上，是jvm提供的轻量级同步机制
- 三个特性
  - 保证可见性
    - 一个线程对变量的修改能够马上被其他线程直到
    - volatile修饰的变量修改时会写回主存，并要求线程从主存中读取最新数据
  - 不保证原子性
    - 比如i++等操作不是原子操作，在++之前，值可能被更改。
    - 因为volatile关键只是保证了读写可见性。而i++是一个组合操作，先拿到值，再+1
  - 禁止指令重排
    - 指令重排对单线程会有优化，但可能会影响多线程下的并发执行
-  原理
  - 对 volatile 变量的写指令后会加入写屏障
  - 对 volatile 变量的读指令前会加入读屏障

## synchronized

- 悲观锁，用于控制同步代码块的互斥访问
- 用法
  - 不能锁null，因为null没有wait和notify方法
  - 修饰实例方法：拿到的是当前对象锁
    - 进入方法要拿到锁
  - 修饰静态方法：拿到的是当前类锁
    - 进入方法要拿到锁
  - 锁某个对象：获取该对象的对象锁
    - 用于同步代码块，进入代码块要拿到该锁
  - A获取对象锁，而B获取该对象的类锁，这是不冲突的
  
  ![synchronized锁范围](images\synchronized锁范围.png)
- 作用
  - 原子性：一组操作要么都完成，要么都不完成。
  - 可见性：变量的修改对其他线程可见，在释放锁之前会将对变量的修改刷新到共享内存当中
  - 有序性：按照代码先后顺序执行，禁止指令重排（指令重排对单线程有优化，但会影响多线程并发性）
- 互斥性
  
  - 互斥锁，一次只能有一个线程访问
- 可重入
  
  - 可以被一个线程多次拿到，在可重入锁中，线程必须释放相同次数的锁才能完全释放该锁，代码中释放即可
- 锁优化
  - 锁膨胀
    - 初始时，对象头的mark word保存一个无锁状态，膨胀方向不可逆
    - 过程
      - 无锁状态：线程访问同步代码块时，拿到偏向锁
      - 偏向锁状态：线程比较mark word中的id，一致则直接访问（不用再申请锁），不一致则进入自选状态
      - 自旋状态：通过CAS尝试获取锁（设置成自己的id），一定次数后没拿到升级为重量级状态
        - 大部分同步代码块执行时间很短
      - 重量级状态：阻塞线程，等待锁释放后被唤醒
    - 作用
      - 重量级锁底层依赖于系统的同步函数，涉及内核态和用户态切换等
      - 引入锁升级机制，避免直接使用重量级锁带来的性能消耗
  - 锁消除
    - 虚拟机另外一种锁的优化，这种优化更彻底，在JIT编译时，对运行上下文进行扫描，去除不可能存在竞争的锁
  - 锁粗化
    - 锁粗化是虚拟机对另一种极端情况的优化处理，通过扩大锁的范围，避免反复加锁和释放锁

## Lock

- 用来控制对共享资源的访问

- 互斥性
  
- 可互斥(实现类WriteLock)，也可共享(实现类ReadLock)
  
- 接口方法
  - lock()        //阻塞，尝试获取锁
  - lockInterruptibly()    //阻塞，尝试获取锁 ，可被打断
  - newCondition()      //创建一个Condition
  - tryLock()             //不阻塞，尝试获取锁，马上返回结果 （可用于处理死锁
  - tryLock(time,timeunit)    //不阻塞，尝试一段时间
  - unlock()                //释放锁
  
- 实现类和实现类
  - ReentrantLock（类 
    - ReentrantLock是一个可重入的互斥锁，在可重入锁中，线程必须释放相同次数的锁才能完全释放该锁
    - 也被称之为“独占锁”，顾名思义，ReentrantLock在同一时刻只能被一个线程拥有
    - 可重入的，意义就是它可以被单个线程多次获取
    - 相当于synchronized关键字的作用，只不过ReentrantLock是基于对象的。
    - 读写都加锁
  - ReadWriteLock
    - 可共享读，互斥写
  
- 写个例子

  ```java
  import java.util.concurrent.locks.Lock;
  import java.util.concurrent.locks.ReentrantLock;
  
  public class LockExample {
      private Lock lock = new ReentrantLock();
      private int count = 0;
  
      public void increment() {
          lock.lock(); // 获取锁
          try {
              count++; // 对共享变量进行修改操作
          } finally {
              lock.unlock(); // 释放锁
          }
      }
  
      public int getCount() {
          return count;
      }
  
      public static void main(String[] args) throws InterruptedException {
          LockExample example = new LockExample();
  
          // 创建多个线程对共享变量进行修改
          Thread thread1 = new Thread(() -> {
              for (int i = 0; i < 1000; i++) {
                  example.increment();
              }
          });
  
          Thread thread2 = new Thread(() -> {
              for (int i = 0; i < 1000; i++) {
                  example.increment();
              }
          });
  
          thread1.start();
          thread2.start();
  
          thread1.join();
          thread2.join();
  
          System.out.println("Count: " + example.getCount());
      }
  }
  ```

## Condition

- Condition的作用是对锁进行更加精确的控制，Condition中的await()/signal()/signalAll和Object中wait()/notify()/notifyAll()方法有异曲同工之妙，不同的是Object中的三个方法是基于synchronized关键字，而Condition中的三个方法则需要和Lock联合使用

- 一个Lock可有多个Condition，更加灵活。多个阻塞队列共用一把锁，一把锁有多个状态，每个队列只关心自己的状态，当前状态满足时就可以获得锁了。不同维度的状态来操作同一把锁
  - 操作的是同一个Lock
  - 每个Condition会有自己单独的等待队列，调用await方法，会放到对应的等待队列中。当调用某个Condition的signalAll/signal方法，则只会唤醒对应的等待队列中的线程。
  - 唤醒的粒度变小了，且更具针对性。如果只使用一个Condition的话，有些线程即使被唤醒并取得锁，其依然有可能并不满足条件而浪费了机会，产生时间损耗

##  synchronized 和 Lock 

- 锁机制
  - synchronized是关键字，在JVM层面实现的，系统会监控锁的释放与否
  - Lock是接口，JDK代码实现的，需要手动释放（在finally块
- 底层
  - synchronized采用的是monitor对象监视器
  - lock的底层原理是AQS
- 用法
  - synchronized可修饰方法，代码块（锁某个对象）、属性
  - Lock不能修饰方法，只能在代码块中调用 l.lock()
- 性能
  - lock好像高点
- 可中断性
  - Lock通过lockInterruptibly() ,被打断时退出抢锁
  - 而synchronized阻塞的线程只能一直等待
- 灵活性
  - Lock更加灵活，trylock（） 和 trylock（tryTimes)     不等待或者限定时间等待获取锁      
  - synchronized则没有这个功能
- 公平性
  - Synchronized是非公平锁（所有线程竞争）
  - Lock默认非公平锁，支持公平锁（先阻塞的先拿到锁）
- 独占性
  - ReentrantReadWriteLock类实现了读写锁的功能，锁自身维护一个计数器，读锁可以并发的获取，写锁只能独占
  - 而synchronized全是独占锁
- 简洁性
  - synchronized的编程更简洁

## Java对象头

在JVM中，对象在内存中的布局分为三块区域：**对象头、实例数据和对齐填充**。

![img](https://gitee.com/sanfene/picgo/raw/master/2062729-9a78f7ea7671a031.png)

`synchronized`用的锁Monitor是存在Java对象头里的。

Hotspot 有两种对象头：

- 数组类型，如果对象是数组类型，则虚拟机用3个字宽 （Word）存储对象头
- 非数组类型：如果对象是非数组类型，则用2字宽存储对象头。

对象头由两部分组成

- Mark Word：存储自身的运行时数据，例如 HashCode、GC 年龄、锁相关信息等内容。
- Klass Pointer：类型指针指向它的类元数据的指针。

64 位虚拟机 Mark Word 是 64bit，在运行期间，Mark Word里存储的数据会随着锁标志位的变化而变化。

![img](https://gitee.com/sanfene/picgo/raw/master/sync_1.png)

## 监视器（Monitor）

- 概述
  - 任何一个对象都有一个Monitor与之关联，monitor对象存在于每个Java对象的对象头
  - 当且一个Monitor被持有后，这个对象将处于锁定状态。
- 流程
  - MonitorEnter指令：插入在同步代码块的开始位置，当代码执行到该指令时，将会尝试获取该对象Monitor的所有权，即尝试获得该对象的锁
  - MonitorExit指令：插入在方法结束处和异常处，JVM保证每个MonitorEnter必须有对应的MonitorExit

## 内存屏障

- 是JVM指令

- 可见性
  - **写屏障**（sfence）对共享变量的改动，都同步到主存当中（保证在该屏障**之前**）
  - **读屏障**（lfence）对共享变量的读取，加载的是主存中新数据（保证在该屏障**之后**）
- 有序性
  - 写屏障会确保指令重排序时，不会将**写屏障之前**的代码排在写屏障之后
  - 读屏障会确保指令重排序时，不会将**读屏障之后**的代码排在读屏障之前

## ThreadLocal

- 为每一个线程都提供了变量的副本，实现数据隔离，没有线程安全问题
- 底层
  - 每个线程都有一个属于自己的ThreadLocalMap
  - 元素Entry是弱引用，容易造成内存泄漏，所以使用ThreadLocal后记得remove
- 应用场景
  - 数据库连接池，会话管理

## LockSupport

- 一个线程工具类，所有的方法都是静态方法，可以让线程在任意位置阻塞，也可以在任意位置唤醒
- 和wait/notify的区别
  - wait和notify都是Object中的方法,在调用这两个方法前必须先获得锁对象，但是park不需要获取某个对象的锁就可以锁住线程。
  - notify只能随机选择一个线程唤醒，无法唤醒指定的线程，unpark却可以唤醒一个指定的线程。
- 用于无锁阻塞和释放线程

## AQS

- 一个抽象类

- AQS（AbstractQueuedSynchronizer）是Java并发包中用于构建锁和同步器的基础框架。它是Java中实现锁和同步器的关键组件之一。

  AQS提供了一个抽象的队列同步器，它用于管理线程的访问和等待状态，并提供了一些方法和机制来实现并发控制的功能。AQS的核心思想是使用一个FIFO的等待队列来控制并发访问共享资源的线程。

  AQS通过对比并交换（compare-and-swap）操作来实现线程的加入和退出等操作，并通过使用内部的状态变量来表示共享资源的状态。AQS的具体实现通过子类化来完成，子类可以根据具体的需求实现自己的同步器。在Java的并发工具包中，很多并发类都是基于AQS来实现的，例如ReentrantLock、CountDownLatch、Semaphore等。

  AQS的主要方法包括：

  - `acquire()`：尝试获取资源的访问权限，若资源已被占用则等待。
  - `release()`：释放资源的访问权限。
  - `tryAcquire()`：尝试以非阻塞方式获取资源的访问权限，若获取成功返回true，否则返回false。
  - `tryRelease()`：尝试以非阻塞方式释放资源的访问权限，若释放成功返回true，否则返回false。
  - `tryAcquireShared()`：尝试以非阻塞方式获取共享资源的访问权限。
  - `tryReleaseShared()`：尝试以非阻塞方式释放共享资源的访问权限。

  AQS的优点在于它提供了一种灵活的方式来实现各种不同类型的同步器和锁，并且可以灵活地控制并发访问共享资源的方式。同时，AQS能够高效地管理等待队列，使得在高并发环境下，线程的上下文切换和资源的竞争能够得到有效的调度和控制。

  总结起来，AQS是Java并发包中用于构建锁和同步器的基础框架，它通过管理等待队列和状态变量来实现线程的并发控制，使得多线程对共享资源的访问能够得到有效的调度和管理

- AQS 是一个锁框架，它定义了锁的实现机制，并开放出扩展的地方，让子类去实现，
  
- 比如我们在 lock 的时候，AQS 开放出 state 字段，让子类可以根据 state 字段来决定是否能够获得锁，对于获取不到锁的线程 AQS 会自动进行管理，无需子类锁关心，这就是 lock 时锁的内部机制，封装的很好，又暴露出子类锁需要扩展的地方；
  
- AQS 底层是由同步队列 + 条件队列联手组成
  
- 同步队列管理着获取不到锁的线程的排队和释放，条件队列是在一定场景下，对同步队列的补充，比如获得锁的线程从空队列中拿数据，肯定是拿不到数据的，这时候条件队列就会管理该线程，使该线程阻塞；
  
- AQS 围绕两个队列，提供了四大场景
  
  - 分别是：获得锁、释放锁、条件队列的阻塞，条件队列的唤醒，分别对应着 AQS 架构图中的四种颜色的线的走向。
  
- ReentrantLock和AQS的关系

  - 内部有一个AQS的子类

    - ```java
      abstract static class Sync extends AbstractQueuedSynchronizer
      ```

  - ReentrantLock是基于AQS（AbstractQueuedSynchronizer）的实现方式之一，而不是通过继承关系来实现的。AQS提供了一个抽象的队列同步器，它是用于构建锁和同步器的基础框架。

  - 在ReentrantLock的内部，它使用AQS来管理锁的状态和实现锁的操作。ReentrantLock通过使用AQS的底层机制和方法，如`getState()`、`setState()`、`compareAndSetState()`等，来实现多线程对共享资源的同步和互斥访问。

  - ReentrantLock利用AQS的独占模式（exclusive mode）来实现可重入的独占锁。也就是说，当一个线程已经持有该锁时，它可以重复地获取这个锁而不会被阻塞。这种可重入性是由ReentrantLock内部维护的同一个线程的获取计数器实现的。

  - ReentrantLock还通过AQS的条件变量（Condition）机制，提供了更灵活的线程协作方式。它允许线程在等待某个条件满足时暂时释放锁并进入等待状态，然后在条件满足时重新获取锁并继续执行。

  - 总结起来，虽然ReentrantLock并没有直接继承AQS，但它是基于AQS的实现方式之一。ReentrantLock利用AQS的底层机制和方法来实现锁的获取、释放，以及多线程的同步和互斥访问。这种基于AQS的实现方式使得ReentrantLock具有了可重入性和更灵活的线程协作能

  - ReentrantLock增加一些功能

    - 如getWaitingThreads，getQueueLength等

## Unsafe类

- 简介

  - Java中一个底层类，包含了很多基础的操作，比如数组操作、对象操作、内存操作、CAS操作、线程(park)操作、栅栏（Fence）操作，JUC包、一些三方框架都使用Unsafe类来保证并发安全
  - Java 不能直接访问操作系统底层，而是通过本地方法来访问。Unsafe 类提供了硬件级别的原子操作（大部分方法都是native

- 方法分类

  - Info相关。主要返回某些低级别的内存信息：
    - addressSize(), pageSize()
  - Objects相关。主要提供Object和它的域操纵方法
    - allocateInstance(),objectFieldOffset()
  - Class相关。主要提供Class和它的静态域操纵方法
    - staticFieldOffset(),defineClass(),defineAnonymousClass(),ensureClassInitialized()
  - Arrays相关。数组操纵方法：
    - arrayBaseOffset(),arrayIndexScale()
  - Synchronization相关。主要提供低级别同步原语（如基于CPU的CAS（Compare-And-Swap）原语）
    - monitorEnter(),tryMonitorEnter(),monitorExit(),compareAndSwapInt(),putOrderedInt()
  - Memory相关。直接内存访问方法（绕过JVM堆直接操纵本地内存）：
    - allocateMemory(),copyMemory(),freeMemory(),getAddress(),getInt(),putInt()

- 使用场景

  - 避免初始化
    当想要绕过对象构造方法、安全检查器或者没有 public 的构造方法时，allocateInstance() 方法变得非常有用。

  - 内存修改

    - Unsafe 可用于绕过安全的常用技术，直接修改内存变量。
    - 反射也可以实现相同的功能。但是 Unsafe 可以修改任何对象，甚至没有这些对象的引用。

  - 动态类

    - 可以在运行时创建一个类，比如从已编译的 .class 文件中将类内容读取为字节数组，并正确地传递给 defineClass 方法。

      当必须动态创建类，而现有代码中有一些代理，这非常有用。

  - 大数组

    - Java 数组大小的最大值为 Integer.MAX_VALUE。使用直接内存分配，创建的数组大小受限于堆大小。

    - Unsafe 分配的内存，分配在非堆内存，因为不执行任何边界检查，所以任何非法访问都可能会导致 JVM 崩溃。

      在需要分配大的连续区域、实时编程（不能容忍 JVM 延迟）时，可以使用它。java.nio 使用这一技术。

  - 并发应用

    - compareAndSwap   CAS 方法是原子的，并且可用来实现高性能的、无锁的数据结构

  - 挂起与恢复

    - 将一个线程进行挂起是通过 park 方法实现，调用 park 后，线程将一直阻塞直到超时或者中断等条件出现

# ==线程池==

## 线程池

- 简介
  - 线程池提供了一种限制和管理资源（包括线程任务等信息）
  - 用于高并发访问处理

- 好处
  - **降低资源消耗。** 通过重复利用已创建的线程降低线程创建和销毁造成的消耗。
  - **提高响应速度。** 当任务到达时，任务可以不需要的等到线程创建就能立即执行。
  - **提高线程的可管理性。** 线程是稀缺资源，如果无限制的创建，不仅会消耗系统资源，还会降低系统的稳定性，使用线程池可以进行统一的分配，调优和监控。

## 相关接口

- 继承关系
  - Executor (接口)<=ExecutorService（子接口）<=AbstractExecutorService (抽象类)<=ThreadPoolExecutor (实现类)
  - Executors (工厂类)       返回ExecutorService

##  结果提交

* **`execute()` 方法用于提交不需要返回值的任务，所以无法判断任务是否被线程池执行成功与否；**
* **submit()方法用于提交需要返回值的任务。线程池会返回一个future类型的对象，通过这个future对象可以判断任务是否执行成功**，并且可以通过future的get()方法来获取返回值，get()方法会阻塞当前线程直到任务完成，而使用 `get（long timeout，TimeUnit unit）`方法则会阻塞当前线程一段时间后立即返回，这时候有可能任务没有执行完。
* Future
  * 如果任务尚未完成，调用Future.get()方法将会阻塞当前线程，直到任务执行完成并返回结果。如果任务已经完成，get()方法会立即返回结果。需要注意的是，如果任务执行失败或被取消，get()方法可能会抛出异常。也可以通过Future的isDone()方法来判断任务是否已完成。
  * 在某些情况下，如果我们希望获取任务的结果，但又不希望阻塞当前线程，可以使用Future的isDone()方法先判断任务是否已完成，再决定是否调用get()方法。另外，还可以使用带有超时参数的get()方法，可以在指定的时间内等待任务完成，超过时间后如果任务仍未完成，get()方法会返回null或抛出TimeoutException异常

## 核心参数

- 线程池中的线程都是通过这个方法创建

  ```java
  public ThreadPoolExecutor(int corePoolSize,
                                int maximumPoolSize,
                                long keepAliveTime,
                                TimeUnit unit,
                                BlockingQueue<Runnable> workQueue,
                                ThreadFactory threadFactory,
                                RejectedExecutionHandler handler)
  ```

------

- corePoolSize ： 
  - 核心线程数，一旦创建将不会再释放。
  - 初始有任务就创建核心线程，直到到达核心线程数。（初始不会创建   **懒加载**
  - 如果没有空闲的核心线程，同时又未达到最大线程数，则将继续创建非核心线程；
  - 如果核心线程数等于最大线程数，则当核心线程都处于激活状态时，任务将被挂起，等待空闲线程来执行。
- maximumPoolSize ：
  - 线程池最大线程数量。非核心线程数量=maximumPoolSize-corePoolSize
  - 如果最大线程数等于核心线程数，则无法创建非核心线程
  - 如果非核心线程处于空闲时，超过设置的空闲时间，则将被回收，释放占用的资源。
- keepAliveTime ：
  - 非核心线程的心跳时间。如果非核心线程在keepAliveTime内没有运行任务，非核心线程会消亡。
- unit : 
  - 时间单位，TimeUnit.SECONDS等。
- workQueue ：
  - 阻塞队列。ArrayBlockingQueue，LinkedBlockingQueue等，用来存放线程任务。
- defaultHandler ：
  -  当线程边界和队列容量已经达到最大时的拒绝策略。
  - AbortPolicy ： 线程任务丢弃并报错。默认饱和策略。
  - DiscardPolicy ： 线程任务直接丢弃不报错。
  - DiscardOldestPolicy ： 将workQueue**队首任务丢弃**，将最新线程任务重新加入队列执行。挤出去
  - CallerRunsPolicy ：线程池之外的线程直接调用run方法执行。（提交任务的线程？）
  - 自定义的：实现RejectedExecutionHandler接口。
- ThreadFactory ：
  - 线程工厂。新建线程工厂。
  - 可在这里线程进行初始化，如设置线程名、线程优先级等

## 执行流程

![image.png](http://blog-img.coolsen.cn/img/1460000039258685)

- 线程池执行execute/submit方法向线程池添加任务，当任务小于核心线程数corePoolSize，线程池中可以创建新的线程。

- 当任务大于核心线程数corePoolSize，就向阻塞队列添加任务。

- 如果阻塞队列已满，需要通过比较参数maximumPoolSize，在线程池创建新的线程，当线程数量大于maximumPoolSize，说明当前设置线程池中线程已经处理不了了，就会执行拒绝策略。

- 线程复用
  - ThreadPoolExecutor中有个内置对象Worker，每个worker都是一个线程，worker线程数量和参数有关
  - 每个worker会while死循环从阻塞队列中取数据，**通过置换worker中Runnable对象，运行其run方法起到线程置换的效果**
  - 这样做的好处是避免多线程频繁线程切换，提高程序运行性能。

## 池类型

- **newCachedThreadPool**
  - 可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。
  - 特点
    - 工作线程的创建数量几乎没有限制(其实也有限制的,数目为Interger. MAX_VALUE), 这样可灵活的往线程池中添加线程。
    - 如果长时间没有往线程池中提交任务，即如果工作线程空闲了指定的时间(默认为1分钟)，则该工作线程将自动终止。
    - 如果你又提交了新的任务，且没有空闲线程，则线程池重新创建一个工作线程
  - 注意控制线程数量，否则大量线程同时运行，很会造成系统OOM

- **newFixedThreadPool**
  - 创建一个指定工作线程数量的线程池。
  - 特点
    - 每当提交一个任务就创建一个工作线程
    - 如果工作线程数量达到线程池初始的最大数，则将提交的任务存入到池队列中。
    - FixedThreadPool是一个典型且优秀的线程池，它具有线程池提高程序效率和节省创建线程时所耗的开销的优点。
    - 但是，在线程池空闲时，即线程池中没有可运行任务时，它不会释放工作线程，还会占用一定的系统资源。

- **newSingleThreadExecutor**
  - 创建一个单线程化的Executor，即只创建唯一的工作者线程来执行任务
  - 它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。
  - 如果这个线程异常结束，会有另一个取代它，保证顺序执行。 （普通的单线程崩溃了就没有后续了
  - 单工作线程最大的特点是可保证顺序地执行各个任务，并且在任意给定的时间不会有多个线程是活动的。

- **newScheduleThreadPool**
  - 创建一个定长的线程池，而且支持定时的以及周期性的任务执行，支持定时及周期性任务执行。

## 池的阻塞队列

- 阻塞队列

  - 使用不同的队列可以实现不一样的任务存取策略。

    ![img](https://p0.meituan.net/travelcube/725a3db5114d95675f2098c12dc331c3316963.png)

    

- 常用线程池-及其对应阻塞队列

![阻塞队列](http://blog-img.coolsen.cn/img/20200722164307306.png)

<center> 表格左侧是线程池，右侧为它们对应的阻塞队列，可以看到 5 种线程池对应了 3 种阻塞队列</center>

- LinkedBlockingQueue
  - 对于 FixedThreadPool 和 SingleThreadExector 而言，它们使用的阻塞队列是容量为 Integer.MAX_VALUE 的 LinkedBlockingQueue，可以认为是无界队列。
  - 由于 FixedThreadPool 线程池的线程数是固定的，所以没有办法增加特别多的线程来处理任务，这时就需要 LinkedBlockingQueue 这样一个没有容量限制的阻塞队列来存放任务。
  - 这里需要注意，由于线程池的任务队列永远不会放满，所以线程池只会创建核心线程数量的线程，所以此时的最大线程数对线程池来说没有意义，因为并不会触发生成多于核心线程数的线程。

- SynchronousQueue
  - 对应的线程池是 CachedThreadPool。线程池 CachedThreadPool 的最大线程数是 Integer 的最大值，可以理解为线程数是可以无限扩展的。
  - CachedThreadPool 和上一种线程池 FixedThreadPool 的情况恰恰相反，FixedThreadPool 的情况是阻塞队列的容量是无限的，而这里 CachedThreadPool 是线程数可以无限扩展，所以 CachedThreadPool 线程池并不需要一个任务队列来存储任务，因为一旦有任务被提交就直接转发给线程或者创建新线程来执行，而不需要另外保存它们。
  - 我们自己创建使用 SynchronousQueue 的线程池时，如果不希望任务被拒绝，那么就需要注意设置最大线程数要尽可能大一些，以免发生任务数大于最大线程数时，没办法把任务放到队列中也没有足够线程来执行任务的情况。

- DelayedWorkQueue
  - 它对应的线程池分别是 ScheduledThreadPool 和 SingleThreadScheduledExecutor，这两种线程池的最大特点就是可以延迟执行任务，比如说一定时间后执行任务或是每隔一定的时间执行一次任务。
  - DelayedWorkQueue 的特点是内部元素并不是按照放入的时间排序，而是会按照延迟的时间长短对任务进行排序，内部采用的是“堆”的数据结构。
  - 之所以线程池 ScheduledThreadPool 和 SingleThreadScheduledExecutor 选择 DelayedWorkQueue，是因为它们本身正是基于时间执行任务的，而延迟队列正好可以把任务按时间进行排序，方便任务的执行。

## 线程数设置

- 分析任务性质
  - 类别：CPU密集型、IO密集型、混合型
  - 优先级：低、中、高
  - 执行时间：短、中、长
- 自定义线程池就需要我们自己配置最大线程数 maximumPoolSize ，为了高效的并发运行，这时需要看我们的业务是IO密集型还是CPU密集型。
- CPU 密集型任务
  - CPU密集的意思是该任务需要最大的运算，而没有阻塞，CPU一直全速运行。C
  - **CPU密集型：corePoolSize = CPU核数 + 1**
  - 即使当计算（CPU）密集型的线程偶尔由于页缺失故障或者其他原因而暂停时，这个“额外”的线程也能确保 CPU 的时钟周期不会被浪费。
  - 理解为多一个备份的线程
- IO 密集型任务
  - IO密集型的话，是指系统大部分时间在跟I/O交互，而这个时间线程不会占用CPU来处理，即在这个时间范围内，可以由其他线程来使用CPU，因而可以多配置一些线程。
  - IO密集型，即该任务需要大量的IO，即大量的阻塞。在单线程上运行IO密集型的任务会导致大量的CPU运算能力浪费在等待。所以在IO密集型任务中使用多线程可以大大的加速程序运行，即使在单核CPU上这种加速主要就是利用了被浪费掉的阻塞时间
  - 看io密集程度，尽量设置大点，如2*CPU 核心数，因为很多线程交替执行



# ==线程安全==

## 线程安全类可以在lamda表达式中通过方法改变其值

- 不能直接通过=直接改变变量的值
- 如Integer没有提供改变其值的方法
- 因为方法都是值传递，所以也无法直接通过方法来改变变量的值

```java
EventLoopGroup group=new NioEventLoopGroup(2);
AtomicInteger count=new AtomicInteger(3);
Integer n=0;
group.next().execute(()->{
    //System.out.println(count.incrementAndGet());
	//n=4;//报错，不行
    System.out.println(Thread.currentThread().getName());
});

```

## lamda中不能改变局部变量的值，也不能定义同名的本地变量

- 在Java的线程模型中，栈帧中的局部变量是线程私有的，永远不需要进行同步。假如说允许通过匿名内部类把栈帧中的变量地址泄漏出去（逃逸），就会引发非常可怕的后果：一份“本来被Java线程模型规定永远是线程私有的数据”可能被并发访问！哪怕它不被并发访问，栈中变量的内存地址泄漏到栈帧之外这件事本身已经足够危险了，这是Java这种内存安全的语言绝对无法容忍的
- lamda表达式是一个声明，什么时候执行都有可能，甚至在其他线程中运行
- 但类变量可以在lamda中改变
- 同时函数式编程为了简便性，本来也不打算支持修改外部变量