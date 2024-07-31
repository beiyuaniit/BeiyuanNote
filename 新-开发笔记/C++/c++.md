# ==基础语法==

## 头文件

- 在现代C++编程实践中，对于标准库头文件，确实已经不再推荐使用带有`.h`扩展名的传统形式

  ```cpp
  #include <iostream>  // 而非 #include <iostream.h>
  #include <vector>    // 而非 #include <vector.h>
  #include <string>    // 而非 #include <string.h>
  ```


-  自定义头文件
  - 简介
    - C库头文件往往仍保留`.h`后缀
    - 使用标准或约定俗成的扩展名（如`.h`、`.hpp`、`.hxx`等），有助于提高代码的可读性和一致性。其他开发人员在阅读或维护代码时，能够快速识别出哪些文件是头文件，从而更好地理解项目结构和依赖关系
    - C++头文件可以不使用任何后缀，因为C++编译器并不强制要求头文件必须具有特定的扩展名。在编译时，编译器关注的是文件的实际内容，而非其名称或扩展名。只要包含指令正确指定了文件路径（包括文件名），编译器就能正确地处理该头文件
    - 许多集成开发环境（IDE）和代码编辑器会根据文件扩展名提供特定的语言支持，如语法高亮、自动补全、代码格式化等。使用常见的C++头文件扩展名可以让这些工具更准确地识别和处理头文件，提升开发效率
    - 构建系统（如Makefile、CMake、Meson等）和版本控制系统（如Git）有时会基于文件扩展名进行特定的处理或配置。使用标准的头文件扩展名有助于简化这些系统的配置和管理
  - 头文件内容
    - 在头文件中放置相应的类、函数、类型别名、模板等声明，以及必要的前置声明、包含保护宏（如`#ifndef`、`#define`、`#endif`）
    - 防止多次包含同一头文件导致的重复定义错误
  - 使用尖括号`<>`来包含系统或第三方库头文件，使用双引号`""`来包含自定义头文件。不带`.h`后缀的自定义头文件同样使用双引号包含。区分？

- 所以为了让Clion能够马上格式化代码，还是加个后缀吧。(hpp)
  - 不加的话还得等一会Clion识别出内容后才能格式化。。



## 命名空间

- 在C/C++中，变量、函数和后面要学到的类都是大量存在的，这些变量、函数和类的名称将都存在于全局作 用域中，可能会导致很多冲突。使用命名空间的目的是对标识符的名称进行本地化，以避免命名冲突或名字 污染，namespace关键字的出现就是针对这种问题的。

- 解决命名冲突的问题

- 命名空间定义
  - 定义命名空间，需要使用到namespace关键字， 后面跟命名空间的名字，然后接一对{}即可，{}中即为命名空间的成员。
  - 注意：一个命名空间就定义了一个新的作用域，命名空间中的所有内容都局限于该命名空间中

  ```c++
  //1. 普通的命名空间
  namespace N1 // N1为命名空间的名称
  {
      // 命名空间中的内容，既可以定义变量，也可以定义函数
      int a;
  
      int Add(int left, int right)
      {
          return left + right;
      }
  }
  //2. 命名空间可以嵌套
  namespace N2
  {
      int a;
      int b;
  
      int Add(int left, int right)
      {
          return left + right;
      }
  
      namespace N3
      {
          int c;
          int d;
  
          int Sub(int left, int right)
          {
              return left - right;
          }
      }
  }
  //3. 同一个工程中允许存在多个相同名称的命名空间,编译器最后会合成同一个命名空间中。
  namespace N1
  {
      int Mul(int left, int right)
      {
          return left * right;
      }
  }
  ```

- 命名空间使用
  - 命名空间的使用有三种方式

  ```c++
  // 加命名空间名称及作用域限定符：
  int main()
  {
      printf("%d\n", N::a);
      return 0;
  }
  
  // 使用using将命名空间中成员引入
  using N::b;
  int main()
  {
      printf("%d\n", N::a);
      printf("%d\n", b);
      return 0;
  }
  
  // 使用using namespace 命名空间名称引入
  using namespce N;
  int main()
  {
      printf("%d\n", N::a);
      printf("%d\n", b);
      Add(10, 20);
      return 0;
  }
  ```



## 输入&输出

- 使用cout标准输出(控制台)和cin标准输入(键盘)时，必须包含< iostream >头文件以及std标准命名空 间。 注意：早期标准库将所有功能在全局域中实现，声明在.h后缀的头文件中，使用时只需包含对应头文件 即可，后来将其实现在std命名空间下，为了和C头文件区分，也为了正确使用命名空间，规定C++头文 件不带.h；旧编译器(vc 6.0)中还支持<iostream.h>格式，后续编译器已不支持，因此推荐使用 +std的方式

  ```c++
  #include<iostream>
  using namespace std;
  int main()
  {
      cout<<"Hello world!!!"<<endl;
      return 0;
  }
  ```


- 使用C++输入输出更方便，不需增加数据格式控制，比如：整形--%d，字符--%c

  - 对于有特别的格式要求的输出，如输出小数点后几位则建议使用printf（cout会比较麻烦）

  ```c++
  #include <iostream>
  
  using namespace std;
  
  int main()
  {
      int a;
      double b;
      char c;
  
      cin >> a;
      cin >> b >> c;
  
      cout << a << endl;
      cout << b << " " << c << endl;
      return 0;
  }
  ```



## 缺省参数

- 概念

  - 缺省参数是声明或定义函数时为函数的参数指定一个默认值

  - 在调用该函数时，如果没有指定实参则采用该默认值，否则使用指定的实参

- 分类

  ```c++
  // 全缺省参数
  void TestFunc(int a = 10, int b = 20, int c = 30)
  {
      cout<<"a = "<<a<<endl;
      cout<<"b = "<<b<<endl;
      cout<<"c = "<<c<<endl;
  }
  
  // 半缺省参数
  void TestFunc(int a, int b = 10, int c = 20)
  {
      cout<<"a = "<<a<<endl;
      cout<<"b = "<<b<<endl;
      cout<<"c = "<<c<<endl;
  }
  
  ```

- 注意

  - C语言不支持（编译器不支持）
  - 缺省值必须是常量或者全局变量
  - 半缺省参数必须从右往左依次来给出，不能间隔着给
- 缺省参数函数不能有冲突，如在函数声明和定义中同时出现，值也不同
  
```c++
  //a.h
  void TestFunc(int a = 10);
  // a.c
  void TestFunc(int a = 20)
  {}
```

  

## 函数重载

- 简介
  - C++允许在同一作用域中声明几个功能类似的同名函数，这些同名函数的 形参列表(参数个数 或 类型 或 顺序)必须不同，常用来处理实现功能类似数据类型不同的问题
  - 函数是否重载一定是在函数名相同下关于函数参数是否不同（函数参数的类型，个数，顺序三者满足其中之一即可）

- example

  ```c++
  int Add(int left, int right)
  {
          return left + right;
  }
  double Add(double left, double right)
  {
      return left + right;
  }
  long Add(long left, long right)
  {
      return left + right;
  }
   
  int main()
  {
      Add(10, 20);
      Add(10.0, 20.0);
      Add(10L, 20L);
      return 0;
  }
  ```

-  原理
  - C++ 支持函数重载，编译器会对函数名进行“名称修饰”（Name Mangling），即在编译过程中根据函数的参数类型和返回值类型生成一个唯一的内部标识符，以便于链接器识别和解析。
  - C 语言不支持函数重载，其编译后的函数名通常是未修饰的原始函数名。为了能够让 C++ 程序正确找到并链接到 C 库中的函数，需要在 C++ 代码中使用 `extern "C"` 来包裹 C 函数的声明，这样编译器就不会对这些函数进行名称修饰，而是保持与 C 语言一致的链接规则。



## 运算符重载

- 简介
  - 一种允许程序员为用户自定义类型（如类或结构体）重新定义或扩展标准运算符行为的技术
  - 运算符重载实际上是定义了一个或多个函数（称为运算符函数），这些函数的名字是特殊的，由关键字 `operator` 后跟要重载的运算符符号构成。
  - 当这些运算符在代码中应用于自定义类型时，编译器会查找并调用相应的运算符函数来执行特定的操作
  - 可为自定义类型提供自然、直观的表达方式，使代码更加易于阅读和编写
  - 运算符重载遵循函数重载的规则，即在同一作用域内，可以有多个同名函数（运算符函数），只要它们的参数列表不同即可

- 规则
  - 至少一个用户定义类型
    - 重载的运算符至少有一个操作对象（参数）是用户定义的类型（类、结构体或枚举等）。不能为已有的内置类型（如int,float,引用类型）重载运算符（改变C++的原意了
  - 保留运算符原有语义
    - 重载的运算符应尽可能保留其原有的语义。例如，`+` 通常表示加法或连接，`*` 通常表示乘法或解引用，不应随意改变其基本含义
  - 不改变优先级和结合性
    - 重载运算符后，其优先级和结合性仍保持与内置类型一致，不能通过重载改变这些属性
  - 可定义为成员函数或非成员函数
    - 成员函数
      - 如果运算符的一个操作对象是类的实例（左侧），可以将其定义为类的成员函数。此时隐含地接收第一个操作对象作为 `this` 指针，其他操作对象作为参数。
    - 非成员函数
      - 当运算符不适合或不需要成为类的成员时，可以定义为非成员函数。非成员函数需要同时接收所有操作对象作为参数
  - 不支持重载的运算符
    - 如 `.`, `.*`, `::`, `?:`, `sizeof`, `typeid`, `noexcept` 等
  - 考虑异常安全
    - 在实现涉及资源管理的运算符（如赋值运算符、移动赋值运算符）时，确保即使抛出异常也能保持对象的不变式和资源的有效性
  - 避免不必要的重载
    - 只有当为自定义类型提供特定运算符能够显著提高代码清晰度和简洁性时才进行重载，避免过度设计
  - 使用`const`成员函数
    - 对于不改变对象状态的运算符（如比较运算符），应声明为`const`成员函数

- 

- example

  ```c++
  // 形式
  返回类型 operator运算符(参数列表)
  
  
  // 一元运算符(如++ 、 --)
  class MyInt{
  public :
      MyInt(int initial_value = 0) : val(initial_value) {} // 添加构造函数
      
      // 前缀递增运算符（++myInt）
      MyInt& operator++() {
          ++val; // 自增内部整数值
          return *this; // 返回当前对象的引用，以便链式调用
      }
      // 后缀递增运算符（myInt++）
      MyInt operator++(int) {
          MyInt temp(*this); // 保存当前值到临时对象
          ++(*this); // 调用前缀递增运算符
          return temp; // 返回临时对象（递增前的值）
      }
      int getInt(){
          return val;
      }
  private:
      int val;
  };
  
  int main() {
      MyInt myInt(10);
      // 前缀递增运算符示例
      std::cout << "Before: " << myInt.getInt() << std::endl; // 10
      ++myInt;
      std::cout << "After: " << myInt.getInt() << std::endl; // 11
      // 后缀递增运算符示例
      std::cout << "Before: " << myInt.getInt() << std::endl; // 11
      MyInt afterInc = myInt++;
      std::cout << "After: " << myInt.getInt() << std::endl; // 12
      std::cout << "Value before increment stored in afterInc: " << afterInc.getInt() << std::endl;
      return 0;
  }
  
  
  // 算术运算符（如`+`、`-`、`*`、`/`、`%`）
  class Complex {
  public:
      Complex(double real, double imag) : real_(real), imag_(imag) {}
  
      Complex operator+(const Complex& other) const {
          return Complex(real_ + other.real_, imag_ + other.imag_);
      }
  private:
      double real_;
      double imag_;
  };
  Complex c1(3.0, 4.0), c2(1.0, 2.0);
  Complex sum = c1 + c2;  // 实现复数相加
  
  
  
  // 关系运算符（如`==`、`!=`、`<`、`<=`、`>`、`>=`）
  class Date {
  public:
      Date(int year, int month, int day) : year_(year), month_(month), day_(day) {}
  
      bool operator==(const Date& other) const {
          return year_ == other.year_ && month_ == other.month_ && day_ == other.day_;
      }
  private:
      int year_;
      int month_;
      int day_;
  };
  Date d1(2024, 4, 22), d2(2024, 4, 22);
  if (d1 == d2) {
      std::cout << "The dates are equal." << std::endl;
  }
  
  
  
  // 赋值运算符（如`=`, `+=`, `-=`等）
  class Vector3D {
  public:
      Vector3D& operator=(const Vector3D& other) {
          if (this != &other) {
              x_ = other.x_;
              y_ = other.y_;
              z_ = other.z_;
          }
          return *this;
      }
      Vector3D& operator+=(const Vector3D& other) {
          x_ += other.x_;
          y_ += other.y_;
          z_ += other.z_;
          return *this;
      }
  private:
      double x_, y_, z_;
  };
  Vector3D v1(1.0, 2.0, 3.0), v2(4.0, 5.0, 6.0);
  v1 += v2;  // 实现向量相加并赋值给v1
  
  
  // 下标运算符（`[]`）
  class Matrix {
  public:
      double& operator[](size_t index) {
          return data_[index];
      }
  
  private:
      double data_[3];  // 假设为3x3矩阵
  };
  int main()
  {
      Matrix m;
      m[1] = 10.0;  // 通过下标访问和修改矩阵元素
  }
  
  
  
  // 转换运算符（如`explicit operator T()`）
  class Rational {
  public:
      explicit operator double() const {
          return numerator_ / static_cast<double>(denominator_);
      }
  
  private:
      int numerator_;
      int denominator_;
  };
  Rational r(3, 5);  // 分数 3/5
  double d = r;  // 显式转换为 double 类型
  
  
  // 流插入运算符 << 
  class Person {
  public:
      std::string name;
      int age;
      friend std::ostream& operator<<(std::ostream& os, const Person& p) {
          return os << "Name: " << p.name << ", Age: " << p.age;
      }
  };
  int main() {
      Person person{"Alice", 30};
      std::cout << person << std::endl; // 调用重载的流插入运算符
  }
  
  
  ```



## 引用

- C++中的引用是一种特殊的数据类型，它提供了一种方法来给已存在的变量创建一个新的名字或者别名。确保了简洁的语法、严格的类型检查和高效的内存使用。在编程实践中，引用常用于函数参数传递、返回值优化、延长临时对象生存期等方面，以提升代码的效率和可读性

- 引用在逻辑上更像是一个别名而不是一个独立存储的对象，但在底层实现上，编译器通常会会利用指针来实现引用的功能

- 特点

  - 别名关系
    - 引用是已存在变量的另一个名字。创建引用时，必须对其进行初始化，绑定到一个已经存在的变量。一旦绑定，引用就成为该变量的别名，对引用的所有操作实际上都是对原变量的操作
    - 一个变量可以有多个引用
  - 初始化后不可更改
    - 引用一旦初始化后，就不能重新绑定到其他变量。也就是说，一个引用在其生命周期内始终指向同一个内存位置，无法“重新指向”另一个对象
    - 可看下下面的例子
  - 声明时要初始化
    - 引用必须始终绑定到一个有效的对象。没有未初始化的引用，也不能有空引用（与C++中的NULL或nullptr指针不同）。这意味着引用必须在声明时立即初始化，并且之后始终保持有效

  - 与原变量共享存储
    - 引用不占用独立的内存空间。它不是一个新变量，而是原变量的别名，二者共享同一块内存。对引用的修改直接影响到原变量，反之亦然

  - 类型严格
    - 引用具有严格的类型，且其类型必须与所引用的变量类型一致。不允许隐式类型转换。创建引用时，需要显式指定其引用的变量类型
  - 语义简洁、无须解引用
    - 虽然引用的行为类似指针，但其语法和使用方式更为简洁。引用无需解引用（`*` 运算符）即可访问所引用的对象，且其语法看起来就像是直接使用变量一样。

- 与指针的区别

  - 语法
    - 引用使用起来更像是直接使用变量，不需要解引用操作。指针需要使用 `*` 运算符来访问所指向的对象。
  - 初始化
    - 引用必须在声明时初始化，并且之后不能再改变所引用的对象。指针可以在任何时候初始化或重新指向不同的对象
  - 空状态
    - 引用必须始终关联一个有效对象。指针可以是 NULL 或 nullptr，表示不指向任何对象。
  - 间接级别
    - 引用总是直接访问其引用的对象。指针可以是多级指针（如 `int**`），间接访问多层内存地址。
  - 自动解引用
    - 引用在使用时自动解引用，没有额外的开销。指针在访问对象时需要进行显式或隐式的解引用操作
  - sizeof中含义
    - 引用结果为引用类型的大小，但指针始终是地址空间所占字节个数(32位平台下占 4个字节)
  - 自增
    - 引用自加即引用的实体增加1，指针自加即指针向后偏移一个类型的大小
  - 安全性
    - 引用没有指针的直接操作内存，用起来更加安全可靠

- 应用场景

  -   函数参数
  -   使用引用作为函数参数可以实现按引用传递，允许函数直接修改实参的值，避免了值传递时的副本创建。这在处理大型数据结构（如数组、容器或自定义类对象）时可以提高效率。
  -   返回复杂对象
      - 函数返回引用可以避免复制大对象，特别是在需要返回大型对象或返回的对象需要进一步修改时。不过，返回左值引用需谨慎，以防止悬挂引用问题。

  -   常量引用
      - 常量引用（`const T &`）可以用于函数参数，以表明函数不会修改传入的对象，同时避免不必要的复制。它也可以用于引用不可修改的对象。

  -   临时对象延长生存期
      - 通过返回局部对象的引用，可以延长临时对象的生存期至引用的作用域结束，这种技术称为“返回值优化”（RVO）。

- example

  ```c++
  // 声明
  类型 &引用名 = 目标变量名;
  int a = 10;
  int &refA = a; // refA 是 a 的引用
  
  
  // 普通传参时拿到的是变量的副本
  // 引用传参时可以改变变量本身
  // 即使对于类
  void fun_1(int a)
  {
      a += 1;
  }
  void fun_2(int &b)
  {
      b -= 1;
  }
  int main()
  {
      int a = 0, b = 0;
      fun_1(a);
      fun_2(b);
      cout << a << endl; // 0
      cout << b; // -1
      cout << &a;// 0xfea53ff8cc
  }
  // 类
  class Person
  {
  public:
      int count = 1;
  };
  void fun_10(Person p)
  {
      p.count=3;
  }
  void fun_11(Person& p)
  {
      p.count=3;
  }
  int main()
  {
      Person p;
      fun_10(p);
      cout << p.count<<endl; // 1
  
      Person t;
      fun_11(t);
      cout<<t.count; // 3
  }
  
  
  
  // 自动解引用
  int main()
  {
      int x = 10;
      int &ref = x; // ref 是 x 的引用
  
      // ref 就像 x 的一个透明代理，对 ref 的任何操作都会直接反映到 x 上。
      // 尽管引用没有类似指针那样的 * 解引用操作符，但在使用引用时，编译器会“自动”处理底层的解引用过程
      ref = 20;
      cout << &x << endl; // 0x4d6adffb04
      cout << &ref << endl; // 0x4d6adffb04
      cout << x << endl; // 20
      cout << ref; // 20
      return 0;
  }
  
  
  
  // 关于 = 的解引用
  class Person
  {
  public:
      int count = 1;
  };
  // t = q; 并不是改变引用 t 本身(即t不会再引用新的变量)，而是改变 t 所引用的对象 p 的内容(如果两者类型兼容且支持赋值操作的话)
  int main()
  {
      Person p;
      Person &t = p;
      p.count = 2;
  
      cout << &p << endl; // 0x64e17ff704
      cout << &t << endl; // 0x64e17ff704
      cout << p.count << endl; // 2
      cout << t.count << endl; // 2
  
      Person q;
      q.count = 3;
      t = q; // 这里
      cout << &p << endl; // 0x64e17ff704
      cout << &t << endl; // 0x64e17ff704
      cout << &q << endl; // 0x64e17ff700
      cout << p.count << endl; // 3
      cout << q.count << endl; // 3
      cout << t.count; // 3
  
      return 0;
  }
  ```

## *&

- `*` 和 `&` 分别具有多种含义，主要涉及指针、引用、解引用、取地址、运算符重载以及位运算

- example

  ```c++
  // *
  
  // 声明指针(变量的地址)
  int *pInt;
  
  // 解引用(访问指针所指向的内存地址处存储的值)
  int value = 42;
  int *pValue = &value; // pValue 存储 value 的地址
  std::cout << *pValue; // 42
  
  // 乘法
  int a = 3, b = 4;
  int product = a * b; // product = 12
  
  // 多级指针。(指向指针的指针) (变量的地址的地址)
  int value = 100;
  int *pValue = &value;
  int **ppValue = &pValue; // 声明一个指向 int* 
  cout << pValue << endl; // 0x28c63ff814
  cout << ppValue << endl; // 0x28c63ff808
  cout << *pValue << endl; // 100
  cout << **ppValue; // 100
  
  // *this
  // this是当前对象指针，*this是当前对象。以引用形式返回对象
  Vector3D& operator+=(const Vector3D& other) {
          x_ += other.x_;
          y_ += other.y_;
          z_ += other.z_;
          return *this;
  }   
  
  
  // &
  
  // 取址
  int number = 99;
  int *pNumber = &number; // pNumber 存储 number 的地址
  cout << pNumber<<endl; // 0x8bed9ffa54
  cout << *pNumber; // 99
  
  // 引用声明(变量的别名)
  int num = 2;
  int &refNum = num; // refNum 是 num 的引用，对 refNum 的操作等同于对 num 的操作
  refNum = 1; // num 的值现在变为 ¼
  cout << num; // 1
  
  // 逻辑与
  int a = 0b1010; // a = 10 也可以(二进制)
  int b = 0b1100; // b = 12 (二进制)
  int result = a & b; // result = 0b1000 = 8 (二进制)
  ```



## 指针

- 指针传参
- 简介

  - C++指针是一种特殊的变量，它存储的是另一个变量或对象在内存中的地址，而非实际的数据值
  - 通过指针，程序可以直接操作内存地址，实现对数据的高效访问、数组遍历、动态内存管理、数据结构的构建、函数参数传递、函数回调等多种功能
- 指针虽然复杂但功能强大，正确使用它们能极大地提高代码的灵活性和效率。同时，要注意避免潜在的问题，确保程序的健壮性和安全性

- 注意
  - 空指针
    - 指针可以被赋值为 `NULL` 或 `nullptr`，表示它不指向任何有效地址。在使用指针之前，应确保它非空，以避免未定义行为。
  - 悬挂指针
    - 如果指针指向已释放或无效的内存区域，称为悬挂指针。对悬挂指针进行解引用可能导致程序崩溃或数据错误。
  - 野指针
    - 未初始化的指针可能包含随机值，这样的指针称为野指针。使用野指针同样会导致未定义行为。
  - 指针类型转换
    - 只有当指针类型兼容时，才能安全地进行指针类型转换。否则，需要显式进行类型转换，并确保操作的合法性。
  - 动态内存管理
    - 指针常用于动态内存分配（如 `new`、`delete`），需谨慎处理以避免内存泄漏或悬挂指针。

- example

  ```c++
  // 声明&初始化
  // 变量
  // 通常让指针指向一个已存在的变量的地址，使用取地址运算符 `&` 获取变量的地址
  int num = 42;
  int *pInt = &num; 
  // 数组
  int arr[] = {3, 1, 2, 4};
  int *pArr = arr;
  
  
  // 空指针
  int *p1 = NULL; // C版本
  int *p2 = nullptr; // C++11及之后
  
  
  // 数组名本质是指向数组首元素的常量指针，接收指针的函数可以传数组名
  void print_array(int *arr, size_t size)
  {
      // corecrt.h定义有 typedef unsigned __int64 size_t;  
      for (size_t i = 0; i < size; ++i)
      {
          std::cout << arr[i] << ' ';
      }
  }
  print_array(arr, sizeof(arr) / sizeof(arr[0]));
  
  
  // 取址 & 取址运算符，获取变量地址
  cout << pInt << endl; // 0x63fb5ffdb4
  cout << &num << endl; // 0x63fb5ffdb4
  
  
  // 解引用(使用*访问该内存上的数据)
  cout << *pInt; // 42
  
  
  // 加减 
  // +1、-1是移动到上、下一个同类型变量的地址
  std::cout << *pArr << endl; // 3
  pArr++;
  std::cout << *pArr; // 1 
  
  
  // 遍历数组
  for (int i = 0; i < 4; ++i)
  {
      std::cout << *pArr++ << " "; // 3 1 2 4   等价于 *(pArr++)
  }
  
  
  // 指针函数 返回值是指针的函数
  int* findMax(int arr[], int n)
  {
      int maxVal = arr[0];
      int* maxPtr = &arr[0];
  
      for (int i = 1; i < n; ++i)
      {
          if (arr[i] > maxVal)
          {
              maxVal = arr[i];
              maxPtr = &arr[i];
          }
      }
      return maxPtr; // 返回最大值的地址
  }
  
  
  // 函数指针 存储的是函数的地址
  // 声明一个接受两个整数并返回整数的函数类型
  typedef int (*FuncPtr)(int, int);
  // 定义对应的函数
  int add(int a, int b)
  {
      return a + b;
  }
  // 声明一个函数指针变量
  FuncPtr addFunc = add; // 不省略&也可以 FuncPtr addFunc = &add;
  // 使用函数指针调用函数
  int result = addFunc(3, 5); // 不省略*也可以 int result = (*addFunc)(3, 5);
  cout << result; // 8
  
  
  // 内存分配
  int *dynamic_int = new int; // 分配一个整数的空间
  *dynamic_int = 6;
  std::cout << *dynamic_int; // 6
  delete dynamic_int; // 释放内存
  
  
  // 指针数组
  int values[] = {3, 1, 2};
  int *ptrs[3]; // 指针数组，每个元素存储一个整数指针
  for (int i = 0; i < 3; ++i) {
      ptrs[i] = &values[i];
  }
  std::cout << *ptrs[1]; // 1
  
  
  // 多级指针
  int v = 2;
  int *p1 = &v;
  int **p2 = &p1;
  cout << **p2; // 2
  
  
  // 指针常量、常量指针、指向常量的指针   在const关键字那里有 这里再写一下吧
  // 指向常量的指针
  int x = 10;
  const int *p1 = &x; // 顶层const，通过p1不能修改x的值
  // int const *p1 = &x; // 同理
  // *p1 = 20; // 错误:不能通过此指针修改num的值
  p1 = nullptr; // 正确:可以改变指针自身的指向
  
  
  // 常量指针
  int y[5]
  int *const p2 = y; // 底层const，指针p2不可变
  *p2 = 1; // 正确:可以通过指针修改数组元素（前提是数据不是const）
  // p2++; // 错误:不能改变指针本身的值
  
  
  // 指向常量的常量指
  const int *const p3 = &x; // 既有顶层const又有底层const
  
  
  // 智能指针
  #include <memory>
  // 当 uptr 超出作用域时，其所管理的 int 自动释放
  std::unique_ptr<int> uptr(new int(42));
  std::cout << *uptr; // 42
  ```





## 智能指针

- 简介

  - C++的智能指针是一种特殊的对象，它封装了对动态分配内存的原始指针，并在适当的时候自动管理所指向对象的生命周期，特别是负责其内存的释放
  - 智能指针通过实现RAII（Resource Acquisition Is Initialization，资源获取即初始化）原则，确保即使在异常出现或代码路径复杂的情况下，也能正确地清理所拥有的资源，避免了手动管理内存时可能出现的内存泄漏、悬挂指针等问题
  - 智能指针的设计考虑了异常安全性，即使在代码块中抛出异常，智能指针也能确保在其作用域结束时正确清理资源   **这个比较重要**
  - C++的智能指针通过提供自动化内存管理机制，极大地降低了程序中因手动管理内存导致错误的可能性，提高了代码的可靠性和安全性

- 优势

  - 自动内存管理
    - 智能指针在析构时会自动调用`delete`释放其所持有的对象，无需程序员手动干预。
  - 减少内存泄漏
    - 由于内存释放是自动且确定性的，智能指针极大地减少了因忘记或错误地管理内存而导致的内存泄漏。
  - 支持共享所有权
    - 某些智能指针（如`shared_ptr`）允许多个智能指针共享同一份动态内存，只有当所有共享该内存的智能指针都失效时，内存才会被释放。
  - 循环引用处理
    - weak_ptr`作为辅助智能指针，可用来打破`shared_ptr`之间的循环引用，确保资源在无外部引用时仍能被正确回收
    - 如A shared_ptr引用B，B shared_ptr引用A，可以将其中一个改用为weak_ptr
  - 线程安全性
    - `std::shared_ptr`的某些操作（如引用计数增减）是线程安全的，简化了多线程环境下的内存管理。
  - 兼容标准库和STL容器
    - 智能指针可以作为标准库容器（如`std::vector`、`std::map`等）的元素，便于管理容器内对象的生命周期。

- 应用场景

  - 资源管理
    - 在需要动态分配资源（如打开文件、数据库连接等）且资源生命周期与特定作用域紧密关联的情况下，使用智能指针可以确保资源在作用域结束时自动关闭或释放。
  - 复杂数据结构
    - 在构建包含动态分配对象的链表、树、图等数据结构时，使用智能指针可以简化节点对象的内存管理
    - 如循环链表、双向链表将一个方向的指针声明为weak_ptr
  - 多线程编程
    - 在多线程环境中，`std::shared_ptr`的线程安全性有助于避免因同步问题引起的内存泄漏或未定义行为。
  - 工厂函数和回调
    - 当一个对象注册为另一个对象的事件监听器时，如果双方都通过 `shared_ptr` 互相引用，可能会产生循环引用。在这种情况下，通常将监听者对发布者的引用设为 `weak_ptr`

- 分类

  - std::unique_ptr

    - 代表独占所有权的智能指针，对所指向的对象拥有唯一的所有权。确保了任何时候只有一个`unique_ptr`实例负责某个对象的生命周期

    - unique_ptr不支持复制，但可以通过转移所有权的方式进行移动赋值或构造
    - 一旦所有权被转移，原智能指针不再拥有资源。适用于一对一的资源管理场景

  - std::shared_ptr

    - 共享所有权的智能指针
    - 多个`shared_ptr`实例共享，每个实例都会增加所指向对象的引用计数
    - `shared_ptr` 支持复制和赋值，这些操作会递增引用计数
    - 最后一个持有该对象的`shared_ptr`被销毁或重新赋值时，引用计数递减至零，对象随之被删除
    - 适用于需要在多个对象或作用域之间共享资源的场景

  - std::weak_ptr

    - 提供弱引用，用于观察共享对象的存在状态
    - 与`std::shared_ptr`配套使用，并不增加所指向对象的引用计数
    - 可调用其`lock()`方法临时获得一个可使用的`shared_ptr`来访问对象，如果在此期间对象已经被释放，`lock()`返回的`shared_ptr`将是空的
    - 用于打破循环引用或在不干扰资源生命周期的前提下观察资源是否存在

  - std::auto_ptr（已弃用）

    - C++98引入的第一个智能指针类型
    - 但由于其设计上的缺陷（如非线性赋值行为和不支持C++11的移动语义），在C++11中已被弃用，并在C++17中彻底移除
    - 应避免使用`std::auto_ptr`，改用`std::unique_ptr`替代

  - std::scoped_ptr（已弃用）

    - 它类似于`std::unique_ptr`，但功能更有限，仅用于栈上管理单个所有权的资源，且不支持转移所有权或定制删除器
    - 由于其复制行为（转移所有权而非复制共享）不符合常规指针的直觉，且不兼容标准库容器，所以被弃用，并由`std::unique_ptr`替代
    - 仅存在于某些旧的库实现中，不建议在新代码中使用

- example

  ```c++
  #include <memory>
  
  
  // 在自身生命周期结束时（如离开作用域或被显式销毁）自动删除该对象
  
  
  class MyClass
  {
  public:
      int getAge({
          return age;
      }
      void setAge(int age){
          this->age = age;
      }
  private:
      int age;
  };
                 
  
  // std::unique
  void example_unique_ptr()
  {
      // 创建一个unique_ptr，动态分配一个int并初始化为1
      std::unique_ptr<int> uptr(new int(1));
  
      // Call to deleted constructor of 'std::unique_ptr<int>'
      // unique_ptr has been explicitly marked deleted here
      // std::unique_ptr<int> p = uptr;
  
      // No viable conversion from 'std::unique_ptr<int>' to 'int*'
      // int *p = uptr;
  
      // 使用解引用操作符访问或修改对象
      *uptr = 2;
      std::cout << *uptr << std::endl; // 2
  
      // unique_ptr不能被复制，但可以转移所有权
      std::unique_ptr<int> another_uptr(std::move(uptr)); // uptr现在为空
      assert(!uptr);
      assert(another_uptr);
      // cout<<*uptr; // 已为空
      cout << *another_uptr; // 2
  
      // 当another_uptr离开作用域时，其所指向的int会被自动释放
  }
  
  
  
  // std::shared_ptr
  MyClass *example_sample_ptr()
  {
      MyClass *ptr1 = new MyClass();
      ptr1->setAge(2);
      MyClass *ptr2(ptr1);
      cout << ptr1 << endl; // 0x1f0a4456710
      cout << ptr2 << endl; // 0x1f0a4456710
      return ptr2;
  }
  
  shared_ptr<MyClass> example_shared_ptr()
  {
      shared_ptr<MyClass> sptr1(new MyClass());
      sptr1->setAge(3);
      shared_ptr<MyClass> sptr2(sptr1);
      cout << sptr1 << endl; // 0x1dbe6f26710
      cout << sptr2 << endl; // 0x1dbe6f26710
      return sptr2;
  }
  // 当原sptr1离开作用域时，由于有另一个shared_ptr（即sptr2）持有同一对象，对象不会被释放
  
  void test()
  {
      MyClass *ptr = example_sample_ptr();
      cout << ptr->getAge() << endl; // 2
      shared_ptr<MyClass> sptr = example_shared_ptr();
      cout << sptr->getAge(); // 3
  }
  // 普通指针ptr由于没有手动delete，即使离开作用域也不会被释放，导致内存泄漏
  // 当sptr也离开作用域时，引用计数减至0，MyClass对象自动销毁
  
                 
                 
  void example_weak_ptr()
  {
      std::shared_ptr<MyClass> sharedObj(new MyClass());
      sharedObj->setAge(4);
  
      // 创建一个weak_ptr关联到sharedObj
      std::weak_ptr<MyClass> weakRef(sharedObj);
  
      // 通过lock()方法尝试获取有效的shared_ptr
      if (auto lockedPtr = weakRef.lock()){
          std::cout << "Weak reference still valid, accessing object...\n";
          // 使用lockedPtr访问对象
          cout << lockedPtr->getAge() << endl; // 4
      } else{
          std::cout << "Weak reference expired, object has been deleted.\n";
      }
  }
  // sharedObj离开作用域后被释放，即使weakRef依然存在，也不会阻止对象销毁
  ```



## 函数返回值

- 简介

  - 考虑对象的大小和复制成本、是否需要共享数据、是否需要直接修改原对象、数据的生命周期管理等因素
  - 正确选择返回类型可以提高代码效率、简化内存管理，同时保持代码的清晰性和可维护性

- 对象、指针、引用

  - 对象
    - 特点
      - 独立性
        - 返回一个完整的对象意味着调用者接收到的是一个独立的副本，不受原对象状态改变的影响，适用于需要数据隔离的场景。
      - 简单性
        - 对于小型、轻量级对象，返回对象的开销较小，且使用简单直观，无需担心内存管理问题。
      - 一致性
        - 对于不可复制（如没有默认拷贝构造函数或赋值运算符）或禁止复制（如`const`成员函数）的类，返回对象可能是唯一可行的选择。

    - 应用场景
      - 小型对象
        - 当对象尺寸较小，复制开销可接受时，直接返回对象是最简单的选择。
      - 临时结果
        - 函数生成一个临时结果，不需要与原数据关联，也不需要在函数外部持久保存。
      - 值语义
        - 函数返回一个独立、全新的值，与原数据无关，符合值语义。

  - 指针
    - 特点
      - 共享数据
        - 返回对象的指针允许多个调用者共享同一份数据，适用于需要数据共享或避免数据冗余的场景。
      - 避免复制开销
        - 对于大型对象或复制开销大的对象，返回指针可以避免不必要的复制操作，提高性能。
      - 动态分配
        - 返回动态分配的对象指针，允许在函数外部管理对象的生命周期，适用于无法在栈上创建或需要延迟销毁的对象。

    - 应用场景
      - 大型对象
        - 对象过大，复制开销显著时，返回指针可以避免高昂的复制成本。
      - 数据共享
        - 多个调用者需要访问同一份数据，或者数据需要在函数外部持续存在。
      - 动态资源管理
        - 返回指向动态分配对象的指针，需要调用者负责释放资源。

  - 引用
    - 特点
      - 避免复制开销
        - 与返回指针类似，返回引用可以避免复制对象，特别适用于大型对象或复制开销大的对象。
      - 确保有效性
        - 返回对某个已存在的对象的引用，可以确保返回的对象始终有效，如返回容器内的元素。
      - 修改原对象
        - 返回引用允许直接修改原对象，适用于需要对原数据进行操作而不产生副本的场景。

    - 应用场景
      - 大型对象
        - 对象过大，复制开销显著时，返回引用可以避免高昂的复制成本。
      - 数据修改
        - 函数需要直接修改原对象，返回引用允许调用者通过返回值进行修改。
      - 访问特定元素
        - 返回容器内特定元素的引用，便于直接访问和修改。


- example

  ```c++
  // 返回一个SmallObject对象
  class SmallObject {
  public:
      int value;
  };
  SmallObject createSmallObject(int v) {
      return SmallObject{v};
  }
  
  
  // 返回一个动态分配的BigObject对象的指针
  class BigObject {
  public:
  };
  BigObject* createBigObject() {
      return new BigObject(); 
  }
  void useBigObject(BigObject* obj) {
      // ... 使用obj...
      delete obj; // 负责释放动态分配的对象
  }
  
  
  
  // 返回字符数组data中指定索引位置的字符引用
  class MyString {
  public:
      // ...
      char& operator[](size_t index) {
          return data[index]; 
      }
  
  private:
      char* data; // 假设String类内部管理一个字符数组
  };
  
  ```

  

## for

- 不同版本的for

  ```c++
  void TestFor_1()
  {
      int array[] = {1, 2, 3, 4, 5};
      for (int i = 0; i < sizeof(array) / sizeof(array[0]); ++i){
          array[i] *= 2;
      }
      for (int *p = array; p < array + sizeof(array) / sizeof(array[0]); ++p){
          cout << *p << endl;
      }
  
  }
  
  void TestFor_2()
  {
      int array[] = {1, 2, 3, 4, 5};
      // 不加引用的话，拿到的是一个副本，不会改变其值
      for (auto &e: array){
          e *= 2;
      }
      
      // 不加&，拿到的是副本
      for (auto e: array){
          cout << e << " "; // 2 4 6 8 10
      }
      return 0;
  }
  ```



## switch

- example

  ```c++
  enum class GradeLevel {
      A, B, C
  };
  
  void printGradeInfo(GradeLevel grade) {
      switch (grade) {
          case GradeLevel::A:
              std::cout << "Excellent performance!\n";
              break;
          case GradeLevel::B:
              std::cout << "Above average performance.\n";
              break;
          case GradeLevel::C:
              std::cout << "Average performance.\n";
              break;
          default:
              std::cout << "Invalid grade level.\n";
              break;
      }
  }
  
  int main() 
  {
      GradeLevel studentGrade = GradeLevel::B;
      // 不加break的话，会从B一直往下执行，直到遇到break或结束
      printGradeInfo(studentGrade);
      return 0;
  }
  ```



## 内存分区

- C++内存分区主要指的是程序运行时内存空间的一种逻辑划分方式
  - 代码区
    - 存储程序的机器码，即编译后的二进制代码，包括函数体、常量等不可修改的数据
  - 全局/静态区
    - 存储全局变量、静态变量和常量
    - 可细分
      - 已初始化全局/静态变量区：存放初始化过的全局变量和静态变量。
      - 未初始化全局/静态变量区：存放未初始化的全局变量和静态变量。
      - 常量存储区：存放程序中的常量，例如字符串字面量
  - 栈区
    - 由编译器自动管理，用于存储函数调用时的局部变量、函数参数和返回地址等临时数据
    - 栈的特点是先进后出（LIFO），每当函数调用开始时，会在栈上分配空间，函数调用结束时自动释放
  - 堆区
    - 动态内存区域，由程序员通过`new`、`malloc`等函数手动分配和通过`delete`、`free`等函数手动释放。
    - 堆上的内存分配相对灵活，可以在程序运行时动态请求和释放内存，生命周期不受函数作用域限制

- 图示

  ![内存分区](https://img-blog.csdnimg.cn/2fdfea730c614db38158ed03d61fddc7.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA6Zuq6IqZ6Iqx,size_20,color_FFFFFF,t_70,g_se,x_16)



## 内存泄漏

- 简介

  - 内存泄漏指因为疏忽或错误造成程序未能释放已经不再使用的内存的情况。内存泄漏并不是指内存在物理上的消失，而是应用程序分配某段内存后，因为设计错误，失去了对该段内存的控制，因而造成了内存的浪费
  - 长期运行的程序出现内存泄漏，影响很大，如操作系统、后台服务等等，出现内存泄漏会导致响应越来越慢，最终卡死

- 分类

  - 堆内存泄漏(Heap leak)
    - 堆内存指的是程序执行中依据须要分配通过malloc / calloc / realloc / new等从堆中分配的一块内存，用完后必须通过调用相应的 free或者delete 删掉
    - 假设程序的设计错误导致这部分内存没有被释放，那么以后这部分空间将无法再被使用，就会产生Heap Leak
  - 系统资源泄漏
    - 指程序使用系统分配的资源，比方套接字、文件描述符、管道等没有使用对应的函数释放掉，导致系统资源的浪费，严重可导致系统效能减少，系统执行不稳定

- 避免

  - 工程前期良好的设计规范，养成良好的编码规范，申请的内存空间记着匹配的去释放。ps：这个理想状
    态
  - 采用RAII思想或者智能指针来管理资源
  - 有些公司内部规范使用内部实现的私有内存管理库。这套库自带内存泄漏检测的功能选项
  - 出问题了使用内存泄漏工具检测。ps：不过很多工具都不够靠谱，或者收费昂贵

- example

  ```c++
  void MemoryLeaks()
  {
      // 1.内存申请了忘记释放
      int* p1 = (int*)malloc(sizeof(int));
      int* p2 = new int;
      
      // 2.代码中释放了，但运行时发生了异常  （智能指针能够在程序发生异常时也能够释放）
      int* p3 = new int[10];
      Func(); // 这里Func函数抛异常导致 delete[] p3未执行，p3没被释放.
      delete[] p3;
  }
  ```

  



## 基本类型

- 实际范围可能随平台和编译器的不同而有所变化

- example

  |                 类型                  | 通常范围（位） |              备注              |
  | :-----------------------------------: | :------------: | :----------------------------: |
  |                 short                 |       16       |                                |
  |                  int                  |       32       |                                |
  |                 long                  |       32       |                                |
  |               long long               |       64       |           C++11新增            |
  |            unsigned short             |       16       |                                |
  |             unsigned int              |       32       |                                |
  |             unsigned long             |       32       |                                |
  |          unsigned long long           |       64       |           C++11新增            |
  |   int8_t, int16_t, int32_t, int64_t   |   8/16/32/64   |     定宽整数 ，在<cstdint>     |
  | uint8_t, uint16_t, uint32_t, uint64_t |   8/16/32/64   |  无符号定宽整数 ，在<cstdint>  |
  |                 float                 |       32       |             0.02f              |
  |                double                 |       64       |     小数默认类型，不用加d      |
  |              long double              |       /        | 精度不低于double，取决于编译器 |
  |                 char                  |       8        |                                |
  |              signed char              |       8        |                                |
  |             unsigned char             |       8        |                                |
  |               char16_t                |       16       |  C++11 新增，用于 UTF-16 字符  |
  |               char32_t                |       32       |  C++11 新增，用于 UTF-32 字符  |
  |                wchar_t                |       /        |            宽字符集            |
  |                 bool                  |       8        |                                |
  |                 void                  |       /        |             空类型             |
  |                 auto                  |       /        |          自动推断类型          |
  |                 指针                  |     32/64      |           32寻址为4g           |
  |                 引用                  |       /        |           类似于指针           |



## lambda

- 简介
  - C++的lambda表达式是一种简洁、内联定义的匿名函数，它允许你在代码中直接定义一个小型的、一次性的函数对象，而无需为其命名或单独编写一个函数
  - Lambda表达式在C++11中被引入，极大地增强了编程的灵活性和表达力，尤其在处理算法、回调函数、并发编程等场景中十分有用

- 组成

  - 捕获列表 (`[captures]`)：定义了lambda可以访问的外部作用域中的变量。可以捕获变量的值、引用或默认（按值）捕获整个外部作用域（`this`指针）。
  - 参数列表(`(params)`): 类似于常规函数的参数列表，定义了lambda接受的输入参数。

  - 可选修饰符(`mutable`, `constexpr`): 
    - `mutable`：允许修改被捕获的按值捕获的变量（默认情况下，按值捕获的变量在lambda体内是常量）。
    - `constexpr`（C++17起）：指定lambda为一个常量表达式，可以在编译时求值。

  - 返回类型 (`-> ret_type`): 可以显式指定lambda的返回类型。如果不指定，编译器通常可以根据函数体推断返回类型。

  - 函数体*(`{ body }`): 包含lambda要执行的操作。如果只有一条return语句，可以省略花括号。

- example

  ```c++
  // 示例1：使用 lambda 表达式作为 std::sort 的比较函数
  void sortVectorByDecrement(std::vector<int>& vec) {
      std::sort(vec.begin(), vec.end(), [](const int& a, const int& b) {
          return b < a;  // 降序排序
      });
  }
  
  // 示例2：使用 lambda 表达式作为 std::find_if 的谓词函数
  bool findEvenNumber(const std::vector<int>& vec, int target) {
      auto isEven = [](const int& num) { return num % 2 == 0; };
      auto it = std::find_if(vec.begin(), vec.end(), isEven);
      if (it != vec.end() && *it == target) {
          return true;
      }
      return false;
  }
  
  
  // 自定义函数，接受一个无参无返回值的lambda表达式
  void customFunction(std::function<void()> action) {
      action();
  }
  
  
  
  int main()
  {
      auto action = []() {
          std::cout << "Inside lambda!\n";
      };
      // 将lambda表达式作为参数传递给自定义函数
      customFunction(action); // Inside lambda!
  
  
      auto printHello=[](){cout<<"Hello lambda!"<<endl;};
      printHello(); // Hello lambda
  
      
      auto add=[](int x,int y) {return x+y;};
      int z=add(2,3);
  
  
      // 按照值捕获（相当于函数的值传递）
      int x1=1;
      // auto increment=[x1](int delta) { x1+=delta;} // lambda表达式默认是const，如果希望改变外部变量的值，可声明为mutable
      auto increment=[x1](int delta) mutable{ x1+=delta;};
      increment(2);
      cout<<x1<<endl; // 1
  
  
      // 按引用捕获
      int x2=2;
      // auto decrement=[](int delta)mutable {x2-=delta}; // 不捕获无法使用
      auto decrement=[x1,&x2](int delta)mutable {x2-=delta;};
      decrement(2);
      cout<<x2<<endl; // 0
  
  
      // [=]或[&] 默认捕获，按值捕获当前作用域的所有变量
      int x3=3;
      int x4=4;
      auto modify =[=]()mutable {x3-=2;x4-=2;};
      cout<< x3<<" "<<x4<<endl; // 3 4
  
      // 显式指定返回值
      auto square = [](double  x)->double {return x*x;};
      double sq=square(3.1);
  
      return 0;
  }
  ```

  



#### 






# ==关键字==

## 列表

- example

  ```c++
  控制流程
     if, else, switch, case, default, break, continue, goto, return, while, do, for
  
  存储类说明符
     register, static, extern, mutable
  
  类型修饰符
     const, volatile, restrict
  
  权限修饰符
     pirvate, protected, public
         
  强制类型转换
     static_cast, dynamic_cast, reinterpret_cast, const_cast
  
  类型定义和声明
     typedef, typename, struct, class, union, enum, enum class
         
  函数和成员函数特性
     inline, virtual, explicit, friend, constexpr
  
  异常处理
     try, catch, throw, noexcept
  
  泛型编程
     template, typename
  
  类型推断和声明
     auto, decltype
  
  内存管理
      new, delete, new[], delete[]
  
  命名空间
      namespace
  
  运行时类型识别
      typeid
  
  对象生命期关联
      this
  
  数据类型：
      void, bool, char, wchar_t, char16_t, char32_t, short, int, long, signed, unsigned, float, double, nullptr_t
  
  编译时常量和条件：
      true, false, constexpr, constinit (C++20引入), requires(C++20引入)
  
  并发和线程局部存储：
      thread_local
  
  低级别内存操作
      alignas, alignof
  
  未实现或废弃的关键字
      asm, export（虽然仍是保留字，但在C++11后不再是模板导出的关键字）
  
  ```




## auto

- `auto` 是 C++ 中的一个关键字，用于自动类型推断。在涉及模板、类型较长或复杂的表达式时，能够提高代码可读性和维护性。但也要注意避免过度使用导致类型信息隐晦，影响代码清晰度

- 特点

  - 使用auto定义变量时必须对其进行初始化，在编译阶段编译器需要根据初始化表达式来推导auto的实际类 型。因此auto并非是一种“类型”的声明，而是一个类型声明时的“占位符”，编译器在编译期会将auto替换为 变量实际的类型

- example

  ```c++
  auto x = 42;       // x 的类型被推断为 int
  auto y = 3.14f;    // y 的类型被推断为 float
  auto z = "Hello";  // z 的类型被推断为 const char*
  
  // 结合使用
  auto const cx = x;      // cx 是 const int 类型
  auto& rx = x;          // rx 是 int& 类型，引用 x
  auto&& rrx = std::move(x);  // rrx 是 int&& 类型，右值引用 x
  
  
  // for遍历
  std::vector<int> numbers = {1, 2, 3, 4, 5};
  for (auto number : numbers) {
      std::cout << number << std::endl;
  }
  
  
  // 模板函数中指定返回类型
  // C++11 的做法
  template<typename T, typename U>
  auto add(T t, U u) -> decltype(t + u) {  
      return t + u;
  }
  // C++14 及以后的做法
  template<typename T, typename U>
  auto add(T t, U u) {  
      return t + u;
  }
  
  
  // 作为非类型模板参数的占位符，允许在实例化模板时传入任意类型的具体值
  // C++20
  template<auto N>
  struct IntegerConstant {
      static constexpr auto value = N;
  };
  
  using One = IntegerConstant<1>;
  static_assert(One::value == 1, "");
  
  ```



## extern “C”

- `extern "C"` 是 C++ 语言中的一个链接指定符（linkage specification），它用于指定包围在其内部的函数或变量遵循 C 语言的命名规则和链接约定。其主要目的是确保 C++ 代码能正确地与 C 语言编写的代码或者其他使用 C 链接规范的语言进行互操作。

- 作用

  - 禁用 C++ 特性
    - 在 `extern "C"` 块内声明的函数将不支持 C++ 的一些特性，如函数重载、异常处理、默认参数、类成员函数等。这样做是为了与 C 语言兼容，因为 C 本身并不支持这些特性。因此，当需要编写或调用符合 C 语言接口标准的函数时，必须使用 `extern "C"` 来保证编译器按照 C 规则进行处理
  - 跨语言兼容性
    - `extern "C"` 不仅用于 C++ 与 C 之间的互操作，也适用于任何其他使用 C 链接规范的语言。这意味着 C++ 代码可以通过 `extern "C"` 来调用用 Fortran、Rust、Swift 等语言编写的具有 C 兼容接口的函数，反之亦然。这种机制极大地促进了不同编程语言之间的混合编程和库集成。

- example

  - C++兼容C
    - C++ 源文件或头文件中包含 C 语言编写的函数或变量：


  ```c++
  extern "C" {
      // C 函数声明或包含 C 头文件
      void foo(int arg);
      #include "some_c_header.h"
  }
  ```

  - C++导出头文件给C
    -  C++ 头文件中声明要导出给 C 语言使用的函数或变量：

  ```c++
  // my_library.h
  #ifdef __cplusplus
  extern "C" {
  #endif
  
  void cpp_function(double x);
  
  #ifdef __cplusplus
  }
  #endif
  
  // C 客户端代码可以这样包含并使用：
  #include "my_library.h"
  int main() {
      cpp_function(3.14);
      return 0;
  }
  ```



## nullptr

- 概念

  -  C++11 引入的关键字，用于表示一个空指针常量。它旨在替代传统 C++ 中表示空指针的几种不安全或不明确的方式，如 `NULL` 宏、`0` 或 `(void*)0`

- 特点

  - 使用nullptr表示指针空值时，不需要包含头文件，因为nullptr是C++11作为新关键字引入的
  - C++11中，sizeof(nullptr) 与 sizeof((void\*)0)所占的字节数相同

- 优点

  - 类型安全
    -  `nullptr` 具有明确的类型 `std::nullptr_t`，与任何指针类型兼容但不等同于整数类型。这消除了旧版 C++ 中使用 `0` 或 `NULL` 时可能导致的类型混淆，增强了编译时类型检查。NULL是在stddef.h中定义的一个宏
  - 更好的语义
    - `nullptr` 专门用于表示空指针，其名称和含义更直观，提高了代码的可读性和自文档化能力。相比于 `NULL` 或 `0`，`nullptr` 更清晰地表达了程序员的意图——即初始化或比较一个空指针。
  - 与模板和重载函数的更好配合
    - 在模板函数或重载函数中，`nullptr` 能够正确地与指针类型匹配，避免了使用 `NULL` 或 `0` 可能导致的选择歧义。例如，当存在接受 `int` 和 `void*` 参数的重载函数时，使用 `nullptr` 而非 `0` 可确保调用正确的版本。
  - C++17 起的隐式转换限制
    -  C++17 引入了新规则，禁止从 `std::nullptr_t` 隐式转换到任何指针类型以外的类型。这一变化进一步加强了类型安全性，防止无意间将 `nullptr` 赋值给非指针类型的变量。

- example

  ```c++
  int* p = nullptr;        // 初始化一个空指针
  if (p == nullptr) {     // 检查指针是否为空
      std::cout << "p is null.\n";
  } else {
      std::cout << "*p = " << *p << "\n";
  }
  
  
  int main()
  {
      int *p = NULL;
      int *q = nullptr;
      cout << p << endl;// 0
      cout << q << endl;// 0
      cout << (p == q); // 1 是ture.
  
      int a = NULL;
      // int b = nullptr; // 报错
      // int a=int(p); // 报错
      // int b=int(q); // 报错
  
      return 0;
  }
  
  
  // 本意是想通过f(NULL)调用指针版本的f(int*)函数，但是由于NULL被定义成0，因此与程序的初衷相悖
  void f(int)
  {
      cout << "f(int)" << endl;
  }
  
  void f(int *)
  {
      cout << "f(int*)" << endl;
  }
  
  int main()
  {
      f(0); // f(int)
      // f((void *)NULL); // 报错，因为是int*  no matching function for call to 'f(void*)'
      // f(NULL); //编译报错 call of overloaded 'f(NULL)' is ambiguous
      f((int *) NULL); // f(int*)
      return 0;
  }
  
  ```

- 使用建议

  - 现代 C++ 编程
    - 优先使用 `nullptr` 表示空指针，因为它提供了更好的类型安全、语义清晰度和与模板及重载函数的配合。
  - 兼容旧版编译器
    - 如果需要兼容不支持 C++11 的编译器，可以继续使用 `NULL` 或 `0`，但要注意它们带来的类型安全风险和潜在的重载解析问题。
  - 代码迁移
    - 随着 C++11 及后续版本的普及，推荐逐步将现有代码中的 `NULL` 替换为 `nullptr`，以提升代码质量



## static

- 在C++中，`static`关键字具有多种用途

  - 作用（对于变量）
    - 控制变量的作用域和生命周期
    - 实现类级别的共享状态和功能
  - 静态全局变量
    - 在一个文件或函数外部声明全局变量并加上`static`关键字
    - 生命周期从程序开始运行到程序结束
    - 变量的作用域仅限于定义它的文件内部，即它是“内部链接”（internal linkage），不同文件不会相互影响，这有助于防止命名冲突

  ```c++
  // file1.cpp
  static int globalVar = 0; // 静态全局变量，只在这个文件中可见
  ```

  - 静态局部变量
    - 在函数内部声明局部变量并加`static`关键字
    - 在第一次调用函数时被初始化，并且其值在函数退出后得以保留，下次调用函数时继续上次的值

  ```cpp
  void func() {
      static int localVar = 0; // 静态局部变量
      localVar++;
      cout << "localVar: " << localVar << endl;
  }
  
  int main() {
      func(); // 1
      func(); // 2
      return 0;
  }
  ```

  - 静态成员变量
    - 所有对象共享，只有一份副本
    - 需要在类外初始化

  ```c++
  class MyClass {
  public:
    static int count; // 静态数据成员，记录创建对象的数量
    MyClass() { ++count; } // 构造函数中增加计数
  };
  int MyClass::count = 0; // 需要在类外进行初始化
  
  // 使用
  MyClass obj1;
  MyClass obj2;
  // 此时MyClass::count == 2
  ```

  - 静态成员函数
    - 修饰成员函数
    - 不依赖于任何特定的对象，而是属于整个类，可以通过类名直接调用
    - 不能访问非静态成员变量（没有隐式的this指针），但可以访问静态成员变量

  ```cpp
  class MyClass {
  private:
    static int sharedData;
  public:
    static void staticMethod() { // 静态成员函数
      sharedData++; // 访问静态成员变量
    }
  };
  int MyClass::sharedData = 0;
  
  // 使用
  MyClass::staticMethod();
  ```

  

## const

- `const`关键字在C++中是一个非常强大的工具，用于指定某个变量、指针或引用不可更改或者函数的行为不会修改对象的状态。

- example

  - 常量声明

  ```cpp
  const int pi = 3.14159; // 声明一个整型常量pi，其值不可更改
  pi = 4; // 错误：尝试修改常量值会引发编译错误
  ```

  - const指针

  ```c++
  // 指向常量的指针
  int x = 10;
  const int *p1 = &x; // 顶层const，通过p1不能修改x的值
  // int const *p1 = &x; // 同理
  // *p1 = 20; // 错误:不能通过此指针修改num的值
  p1 = nullptr; // 正确:可以改变指针自身的指向
  
  
  // 常量指针
  int y[5]
  int *const p2 = y; // 底层const，指针p2不可变
  *p2 = 1; // 正确:可以通过指针修改数组元素（前提是数据不是const）
  // p2++; // 错误:不能改变指针本身的值
  
  
  // 指向常量的常量指
  const int *const p3 = &x; // 既有顶层const又有底层const
  ```
  - const引用
  
  ```c++
    int anotherNum = 5;
    const int &refNum = anotherNum; // 引用常量
    refNum = 6; // 错误：不能通过引用修改另一个Num的值
  ```
  
  - const函数
  
  ```cpp
  // 类外的非成员函数不能用const这样修饰
  // 因为非成员函数没有对象上下文
  // int saveData() const { return m_data; }
  int saveData()  { return m_data; }
  
  
  // 可修改mutable变量
  class MyClass {
  private:
      int m_data;
      mutable int n_data;
  public:
  
      // 编译不通过   error: increment of member 'MyClass::m_data' in read-only object
      // void addM_Data() const{ m_data++;}
      
      // 此类函数不会修改类的成员变量(mutable修饰的除外)，并且保证在此函数内不会调用任何非const成员函数
      // 表示不改变对象上下文
      int getData() const { return m_data; }
  
      void addN_Data(){n_data++;}
  };
  
  
  
  // 可通过指针修改成员的状态
  class ExpensiveObject {
  private:
      // 定义Impl结构体，封装内部实现细节
      struct Impl {
          int internalCounter = 0; 
          void updateInternalState() {
              internalCounter++;
          }
      };
      std::unique_ptr<Impl> pimpl; 
  public:
      ExpensiveObject() : pimpl(std::make_unique<Impl>()) {} 
  
      // performAction函数是const的，但可以修改pimpl内部状态
      void performAction() const {
          pimpl->updateInternalState();
      }
  };
  
  ```
  - 基本类型、指针、引用（参数）
  
  ```c++
  class Person
  {
  public:
      int count = 1;
  };
  
  
  // 不加const
  void func_1(int x)
  {
      x = 2; // ok
  }
  void func_2(Person *p)
  {
      Person t;
      p->count = 2; // ok
      p = &t; // ok
  }
  void func_3(Person &p)
  {
      Person t;
      p.count = 2; // ok
      p = t; // ok 这里=并没有改变引用的指向，只是把t中的值赋给了p
  }
  
  
  
  // Person const *p和const Person *p是同一个意思 都出现在*同一侧
  // 指向常量的指针，不可以改变变量
  void func_4(const Person *p)
  {
      Person t;
      // p->count=2; // Cannot assign to variable 'p' with const-qualified type 'const Person*'
      p = &t; // ok
  }
  // 常量指针，指针方向不可以改变
  void func_5(Person *const p)
  {
      Person t;
      p->count = 2; // ok
      // p = &t; // Cannot assign to variable 'p' with const-qualified type 'Person *const'
  }
  // 指向常量的常量指针
  void func_3(const Person *const p)
  {
      Person t;
      // p->count = 2; // Cannot assign to variable 'p' with const-qualified type 'const Person *const'
      // p = &t; // Cannot assign to variable 'p' with const-qualified type 'cosnt Person *const'
  }
  
  
  
  // const Person &p和Person const &p等价
  // 没有Person& const p
  // 常量引用
  void func_5(const Person &p)
  {
      Person t;
      // p.count = 2; // Cannot assign to variable 'p' with const-qualified type 'const Person&'
      // p = t; // No viable overloaded'='
  }
  
  
  
  // const int x和int const x等价
  // 常量
  void func_8(const int x)
  {
      // x = 2; // Cannot assign to variable 'x' with const-qualified type 'const int'
  }
  ```



## mutable

- 简介
  - 内部状态
    - `mutable`修饰的数据成员通常表示对象的某种内部状态，该状态对于对象的使用者而言是透明的，即不直接影响对象对外表现的“不变性”。
  - const 函数
    - 在`const`成员函数中，通常不允许修改任何非`mutable`的成员变量。然而，对`mutable`变量的修改是允许的，即使函数本身声明为`const`。
  - 编译器检查
    - 使用`mutable`可以绕过编译器对`const`成员函数的常规检查，使得在保持函数接口不变的前提下，能够在函数内部改变某些特定数据。

- example

  ```c++
  // 应用场景
  
  
  // 内部缓存
  // 当一个类需要计算某个值并将其缓存在内部，以提高后续访问速度时，这个缓存值通常应该被声明为`mutable`
  // 它不影响对象的外部状态，只是优化了内部操作
  class ComplexNumber {
  private:
      double realPart;
      double imaginaryPart;
      mutable bool cachedMagnitudeValid;
      mutable double cachedMagnitude;
  
  public:
      ComplexNumber(double r, double i) : realPart(r), imaginaryPart(i), cachedMagnitudeValid(false) {}
  
      double getMagnitude() const {
          if (!cachedMagnitudeValid) {
              cachedMagnitude = std::sqrt(realPart * realPart + imaginaryPart * imaginaryPart);
              cachedMagnitudeValid = true;
          }
          return cachedMagnitude;
      }
  };
  ```




## using

- `using`关键字在C++中有多种用途

  - 类型别名
    - 类似C++旧版语法中的typedef，简化复杂的类型名称。

  ```cpp
  using MyInt = int; // 定义MyInt为int的别名
  MyInt num = 10; // 等价于int num = 10;
  
  using StringList = std::vector<std::string>; // 定义StringList为std::vector<std::string>的别名
  StringList names; // 创建一个字符串列表
  ```

  - 命名空间导入
    - 将其导入到当前作用域
    - 
      单个声明或整个命名空间都可

  ```cpp
  namespace my_ns {
      void func();
      struct MyClass {};
  }
  
  // 导入单个函数或类型到全局作用域
  using my_ns::func;
  using my_ns::MyClass;
  
  // 导入整个命名空间的内容到当前作用域（不推荐在头文件中这样做，易造成命名冲突）
  using namespace my_ns;
  
  func(); // 直接可用，无需my_ns::
  MyClass obj; // 同样直接可用，无需my_ns::
  ```



## explicit

- `explicit`关键字在C++中主要应用于构造函数，用于限制编译器进行隐式类型转换。

- example

  - 阻止隐式类型转换构造函数
    - 避免意外的类型转换。例如，在某些情况下，用户可能不想让类实例能够通过简单的赋值就从其他类型创建出来。
    - 提高代码可读性。通过禁止隐式转换，可以清楚地看出哪些地方进行了类型转换

  ```cpp
  // 当一个单参数构造函数被声明为`explicit`时，编译器将不会自动使用该构造函数来进行类型转换
  class Integer {
  public:
      explicit Integer(int value) : m_value(value) {} // 显式构造函数
  private:
      int m_value;
  };
  int main()
  {
      Integer b(10); // 正确：显式调用构造函数
      // 去掉explicit时可以。编译器会自动将整数`10`转换为`Integer`类型
      // Integer a = 10; // 错误：由于构造函数被声明为explicit，不能进行隐式转换
  }
  
  ```



## noexcept

- `noexcept`关键字在C++中用于指定一个函数是否抛出异常

  - 是一个异常规格说明符
  - 可以帮助编译器和运行时系统优化代码，以及在函数可能抛出异常的情况下采取适当的措施

- 作用

  - 编译器优化
    - 当编译器知道函数不会抛出异常时，它可以生成更高效的代码，尤其是在移动和复制操作中
  - 异常传播规则
    - 在C++11中，如果一个`noexcept`标记的函数确实抛出了异常，程序会立即调用`std::terminate()`终止执行。这对于资源管理器和其他必须确保清理资源的代码尤其重要
  - nothrow操作符
    - `noexcept`还与`std::nothrow_t`一起用于`new`表达式，用于请求不抛出异常的动态内存分配

- example

  - 后续遇到异常处理时再补充吧。

  ```cpp
  // 放在函数声明或定义后面，表示函数不会抛出任何异常
  class MyClass {
  public:
      void noThrowMethod() noexcept{};
  };
  
  // noexcept(true) 和 noexcept(false)
  // true不会抛出异常，false可能会
  void safeFunction() noexcept(true) {} 
  ```




## malloc()&free()

- `malloc()` 和 `free()` 是 C 语言标准库中的函数，用于动态内存管理

- 注意

  - 匹配使用
    - 使用 `malloc()` 分配的内存，必须通过 `free()` 释放，不能与 `new` 和 `delete` 混用
  - 避免悬挂指针
    - 释放内存后，应将对应的指针设为 `NULL` 或 `nullptr`，避免后续代码误用已释放的内存
  - 内存泄漏
    - 忘记释放已分配的内存或提前释放内存可能导致内存泄漏，长期运行的程序应特别注意防止内存泄漏
  - 建议
    - 在 C++ 中，虽然仍可使用 `malloc()` 和 `free()`，但通常推荐使用 `new` 和 `delete` 或智能指针来管理内存，因为它们提供了更强的类型安全性和异常安全性

- 性质

  - malloc()、calloc()，realloc()

  ```c++
  // malloc()、calloc()返回值
  // 成功分配内存时，返回指向新分配内存块的指针（类型为 `void*`，需要类型转换后使用）
  // 若内存分配失败（例如，系统内存不足），返回 `NULL` 或 `NULL` 指针常量（取决于编译器）
  
  // 用于在程序的堆（heap）上分配一块连续的、未初始化的内存区域
  // size_t size: 要分配的内存块的字节数
  void* malloc(size_t size); 
  
  // 在堆上分配一块连续的、初始化为零的内存区域
  // 除了分配内存外，还会将新分配的内存区域初始化为全零
  // size_t num: 要分配的相同大小内存块的数量
  // size_t size: 每个内存块的大小（以字节为单位）
  void* calloc(size_t num, size_t size); 
  
  
  // 改变之前通过 malloc()、calloc() 或 realloc() 分配的内存块的大小
  // void* ptr: 指向要调整大小的内存块的指针，该指针必须是由上述内存分配函数返回的有效指针
  // size_t size: 新的内存大小（以字节为单位）
  // 如果 size 小于原内存块大小，可能只释放部分内存；
  // 如果 size 大于原内存块大小，可能分配新的更大内存块，并尝试将原有内容复制到新内存块
  // 返回值
  // 成功调整内存大小时，返回指向新内存块的指针（可能与原指针相同，也可能不同）。如果返回的指针与原指针不同，原指针不再有效
  // 若内存调整失败（例如，系统内存不足），返回 NULL 或 NULL 指针常量（取决于编译器），此时原内存块保持不变，仍需通过 free() 释放
  void* realloc(void* ptr, size_t size);
  ```

- free()

```c++
  // 用于释放之前通过 `malloc()`、`calloc()` 或 `realloc()` 函数动态分配的内存
  // `void* ptr`: 指向要释放的内存块的指针，该指针必须是由上述内存分配函数返回的有效指针
  void free(void* ptr);
```

- example

  ```c
  int main() 
  {
      // 分配了一块足够存放10个整数的内存空间
      // 将返回的指针转换为 `int*` 类型并赋值给 `numbers`
      int* nums = (int*)calloc(10, sizeof(int)); 
      int* numbers = (int*)malloc(10 * sizeof(int)); 
      // 改变容量
      numbers = (int*)realloc(numbers, 20 * sizeof(int));
      
      if (numbers == NULL) {
          printf("Memory allocation failed!\n");
          return 1;
      }
  
      // 使用分配的内存...
      for (int i = 0; i < 10; ++i) {
          numbers[i] = i * i;
      }
  
      // ... 使用完毕后释放内存
      free(numbers);
      return 0;
  }
  ```



## new&delete

- 简介

  - `new` 和 `delete` 在C++中用于动态内存管理，在堆上分配和释放内存
  - 堆是程序运行时用于动态内存分配的区域，其大小通常不受编译时固定，可以在程序运行时动态增长和收缩。

- 特点
  
  - new
    - 在堆上动态分配内存，可同时初始化所分配内存
    - 并返回指向该对象的指针
  - delete
    - 用于释放之前通过 `new` 在堆上分配的内存
  - 使用 `new` 关键字动态分配的对象都需要手动使用 `delete` 操作符来释放
    - 否则会导致内存泄漏
  
- 创建对象方式

  - 栈上直接创建

    ```c++
    Base b;
    ```
    - 优点
      - 简单易用
        - 无需手动管理内存，对象的生命周期与其所在的作用域紧密关联，离开作用域后自动销毁。
      - 高效
        - 栈上内存分配和释放通常比堆上更快，且无碎片化问题。
      - 异常安全性
        - 如果构造函数抛出异常，栈上的对象会自动被销毁，无需担心资源泄漏。
    - 缺点
      - 大小受限
        - 栈上内存大小受到编译器和操作系统限制，不适合创建大对象或大量对象。
      - 生命周期受限
        - 对象的生命周期局限于创建它的作用域，不能在作用域间自由传递对象所有权。

  - new创建

    ```cpp
    Base* b = new Base();
    ```

    - 优点
      - 动态大小
        - 可以根据运行时条件动态决定要创建的对象数量。
      - 生命周期管理
        - 对象的生命周期可以独立于创建它的作用域，可以在不同作用域之间传递对象所有权。
      - 灵活性
        - 可以创建复杂的内存布局，如对象数组或嵌套对象。

    - 缺点
      - 手动内存管理
        - 需要手动使用 `delete` 释放对象，否则可能导致内存泄漏。虽然现代C++推荐使用智能指针（如 `std::unique_ptr`、`std::shared_ptr` 等）来自动管理内存，但仍需正确使用智能指针。
      - 额外开销
        - 动态内存分配和释放有一定的性能开销，特别是在频繁分配和释放小对象时。
      - 异常安全性
        - 在构造函数抛出异常时，需要确保已分配的内存得到释放，否则可能导致资源泄漏。

- 使用推荐

    - 对于大部分简单、生命周期短且大小适中的对象，优先选择栈上直接创建（如 `Base b;`）
    - 对于需要动态确定大小、生命周期独立于创建作用域、或者需要复杂内存布局的对象，可以使用 `new` 关键字动态分配，并结合智能指针或手动 `delete` 来管理内存
    
- 底层实现

  - new在底层调用operator new全局函数来申请空间，delete在底层通过operator delete全局函数来释放空间
  
- 区别

  - malloc和free是函数。new和delete是操作符
  - malloc申请空间时，需要手动计算空间大小并传递。new只需在其后跟上空间的类型即可
  - malloc的返回值为void*, 在使用时必须强转。new不需要，因为new后跟的是空间的类型
  - malloc申请空间失败时，返回的是NULL，因此使用时必须判空。new不需要，但是new需要捕获异常
  - 申请自定义类型对象时，malloc/free只会开辟、释放空间，不会调用构造函数与析构函数。而new在申请空间 后会调用构造函数完成对象的初始化，delete在释放空间前会调用析构函数完成空间中资源的清理
  
- example

  ```c++
  // 单个对象
  Type* pointer = new Type(arguments);
  delete pointer;
  
  // 数组
  Type* array = new Type[size];
  delete[] array;
  
  // 避免悬挂指针
  // 在释放内存后，应将对应的指针设为 `nullptr`，避免后续代码误用已释放的内存
  int main()
  {
      int* i = new int(3);
      cout << *i<<endl; // 3
      delete i;
      cout << *i<<endl; // 844982768
      i = nullptr;
  }
  
  
  // 定位new表达式是在已分配的原始内存空间中调用构造函数初始化一个对象
  // 一般是配合内存池使用,因为内存池分配出的内存没有初始化
  // pt现在指向的只不过是与Test对象相同大小的一段空间，还不能算是一个对象，因为构造函数没有执行
  Test* pt = (Test*)malloc(sizeof(Test));
  new(pt) Test; // 注意：如果Test类的构造函数有参数时，此处需要传参
  
  
  
  
  /*
  operator new：该函数实际通过malloc来申请空间，当malloc申请空间成功时直接返回；申请空间失败，
  尝试执行空间不足应对措施，如果改应对措施用户设置了，则继续申请，否则抛异常。
  */
  void* __CRTDECL operator new(size_t size) _THROW1(_STD bad_alloc)
  {
      // try to allocate size bytes
      void* p;
      while ((p = malloc(size)) == 0)
          if (_callnewh(size) == 0)
          {
              // report no memory
              // 如果申请内存失败了，这里会抛出bad_alloc 类型异常
              static const std::bad_alloc nomem;
              _RAISE(nomem);
          }
      return (p);
  }
  /*
  operator delete: 该函数最终是通过free来释放空间的
  */
  void operator delete(void* pUserData)
  {
      _CrtMemBlockHeader* pHead;
      RTCCALLBACK(_RTC_Free_hook, (pUserData, 0));
      if (pUserData == NULL)
          return;
      _mlock(_HEAP_LOCK); /* block other threads */
      __TRY
          /* get a pointer to memory block header */
          pHead = pHdr(pUserData);
      /* verify block type */
      _ASSERTE(_BLOCK_TYPE_IS_VALID(pHead->nBlockUse));
      _free_dbg(pUserData, pHead->nBlockUse);
      __FINALLY
          _munlock(_HEAP_LOCK); /* release other threads */
      __END_TRY_FINALLY
          return;
  }
  /*
  free的实现
  */
  #define free(p) _free_dbg(p, _NORMAL_BLOCK)
  
  ```



## inline-内联函数

- 概念

  - `inline` 是 C++ 中的一个关键字，用于建议编译器对特定函数进行内联展开（Inline Expansion）。内联函数是一种优化手段，旨在减少函数调用的开销，提高程序运行效率。
  - 当函数被标记为 `inline` 或编译器自动决定将其内联时，编译器会在函数调用处直接插入函数体的代码，而不是生成常规的函数调用指令。这样做的好处是可以省去函数调用时的栈帧建立、参数传递、返回值恢复等开销，特别是对于非常小且频繁调用的函数，内联可以显著提高程序性能。
  - 然而，是否内联最终由编译器决定，程序员应合理使用内联，考虑函数的大小、复杂度以及对编译时间和代码大小的影响。在追求性能优化的同时，也要注意避免过度内联导致的负面影响

- 特性

  - 编译器的自由裁量权
    - 尽管程序员可以使用 `inline` 关键字建议编译器进行内联，但实际是否内联由编译器根据函数的大小、复杂度、编译选项以及优化级别等因素决定。即使标记为 `inline`，编译器也可能因为各种原因选择不进行内联
  - 内联的限制
    - 大型、复杂或递归函数可能不适合内联，因为内联展开可能导致代码膨胀，反而降低程序效率。编译器通常会设置一定的内联阈值，超过该阈值的函数可能不会被内联。此外，跨编译单元（translation unit）的内联需要借助于链接时内联（LTO，Link-Time Optimization）或模块化编译（如 C++20 的模块）来实现
    - 内联函数可能会增加编译时间，因为编译器需要在每个调用点展开函数代码。对于大型项目或编译器资源有限的情况，过度使用内联可能会导致编译时间显著增长
  - 内联与多态
    - 虚函数和模板函数的内联行为较为特殊。虚函数的内联取决于动态绑定发生的时间，通常在运行时，因此虚函数不能像普通函数那样轻易内联。而模板函数在实例化时可能进行内联，但这也取决于编译器的决策
  - 内联与编译器警告
    - 如果一个函数被声明为 `inline`，但在多个编译单元中具有不同的定义（违反了“一次定义原则”），编译器可能会发出警告。为了避免这种情况，通常将内联函数的定义放在头文件中，并在每个使用它的源文件中包含该头文件
    - inline不建议声明和定义分离，分离会导致链接错误。因为inline被展开，是直接复制过来，不会再去找额外的定义
  - 自动内联
    - `[[gnu::always_inline]]` / `__forceinline`
    - 某些编译器提供了更强烈的内联请求方式，如 GCC 的 `[[gnu::always_inline]]` 属性或 MSVC 的 `__forceinline` 关键字。使用这些标记可以强制编译器尽可能内联函数，但请注意过度使用可能导致代码膨胀或降低程序性能

- example

  ```c++
  inline 返回类型 函数名(参数列表) {
        // 函数体
  }
  
  
  inline int add(int a, int b) {
        return a + b;
  }
  
  
  // 定义和声明分开会报错
  // func.hpp
  inline int add(int x,int y);
  // func.cpp
  #include "../head/myFunc.hpp"
  inline int add(int x,int y){
      return x+y;
  }
  // main.cpp
  #include "../head/myFunc.hpp"
  int main()
  {
      int x = 1, y = 2;
      int z = add(x, y);
      cout << z;
      return 0;
  }
  // 报错
  inline function 'int add(int, int)' used but never defined;
  
  
  // 当然，不使用inline时定义和声明分开则没有问题
  // func.hpp
  int add(int x,int y);
  // func.cpp
  #include "../head/myFunc.hpp"
  int add(int x,int y){
      return x+y;
  }
  // main.cpp
  #include "../head/myFunc.hpp"
  int main()
  {
      int x = 1, y = 2;
      int z = add(x, y);
      cout << z; // 3
      return 0;
  }
  // 不报错
  ```





## #if

- 简介

  - `#if`, `#else`, `#endif` 是C++（以及C和其他一些编程语言）中的预处理器指令
  - 允许在编译时根据特定条件选择性地包含或排除源代码片段

- 作用

  - 条件编译特定平台相关代码
    - 针对不同操作系统、硬件架构或编译器特性，编写特定的代码块。
  - 启用/禁用调试代码
    - 在开发阶段包含调试输出或辅助代码，在发布版本中移除。
  - 编译选项控制
    - 根据编译时的宏定义或标志选择不同的实现路径或功能模块。
  - 版本控制
    - 在不同版本的产品中包含或排除特定功能。

- 含义

  - `#if`
    - 开始一个条件编译块。后面跟一个常量表达式，该表达式通常涉及预处理器宏（由`#define`定义）。如果表达式的值为非零（在预处理器语境中，非零被视为真），则紧跟其后的代码块会被编译器处理；否则，该代码块会被忽略。
  - `#else`
    - 可选，用于提供一个与前面`#if`对应条件相反的代码块。如果`#if`后的条件为假（即表达式值为零），`#else`后的代码块将被编译器处理。
  - `#endif`
    - 结束一个条件编译块。无论前面的条件如何，`#endif`标志着条件编译结构的结束。编译器从此处恢复对后续代码的常规处理。

- example

  ```c++
  #define DEBUG_MODE 1 // 定义宏DEBUG_MODE为1
  
  int main()
  {
      // 示例1：根据宏定义选择编译代码
  #if DEBUG_MODE
      std::cout << "Debug mode is enabled." << std::endl;
      // ... 包含调试代码 ...
  #else
      std::cout << "Debug mode is disabled." << std::endl;
          // ... 不包含调试代码 ...
  #endif
  
  
      // 示例2：条件编译不同平台的代码
  #if defined(_WIN32) || defined(_WIN64)
      // ... Windows相关的代码 ...
  #elif defined(__linux__)
      // ... Linux相关的代码 ...
  #else
          // ... 其他平台的通用代码或错误提示 ...
  #endif
  
  
      // 示例3：根据编译选项启用/禁用功能  检查是否定义了该宏且不为0
  #ifdef ENABLE_FEATURE_X
      // ... 实现并使用功能X ...
  #else
      // ... 不使用功能X ...
  #endif
  
  
      return 0;
  }
  ```




# ==类和对象==

## 结构体（C++)

- C++ 中的结构体（Struct）是一种用户定义的数据类型，它允许将多个不同数据类型的成员变量组织在一起，形成一个复合的数据结构。结构体在概念上与类相似，但具有不同的默认访问权限和使用习惯。

- example

  ```c++
  // 定义
  struct Point {
      double x;
      double y;
  };
  
  // 创建一个点对象
  Point p = {3.0, 4.0};  
  
  // 访问成员变量 . ->都可以
  double distance = sqrt(p.x * p.x + p.y * p->y);  
  
  // 指向结构体的指针
  Point* pPtr = &p;
  distance = sqrt(pPtr->x * pPtr->y * pPtr->y);
  ```

- 和类比较

  - 区别
    - 主要区别在于默认访问权限
    - 结构体
      - 默认成员访问权限为 `public`，即所有成员在结构体定义之外都是可以直接访问的。
    - 类
      - 默认成员访问权限为 `private`，即所有成员在类定义之外是不可直接访问的，除非显式声明为 `public`。
  - 共性
    - 结构体可有
      - 成员变量、成员函数、继承、构造函数、析构函数、访问修饰符、友元

- 使用场景

  - 结构体常用于表示一组相关的简单数据，尤其当不需要封装（所有成员都是公有的）或不需要继承和虚函数等面向对象特性时
- 数据传输
  
  - 在函数之间或网络通信中传递一组相关的数据。
- 数据结构
  
    - 实现链表、树、图等数据结构中的节点。
- 联合体（Union）
  
    - C++ 结构体可以包含联合体（union），实现同一内存区域存储不同类型的变量。
- 简单数据封装
  
    - 在不需要严格封装或面向对象设计的情况下，结构体可以作为一种轻量级的数据封装方式
- 非封装需求
  
- 如需将数据和操作封装在一起，不限制外部对内部数据的直接访问，通常选择结构体
  
- 和C结构体区别
  - C结构体只能定义变量，不能定义函数等行为，没有继承多态等。
  - C结构体作用域是全局，C++可以是某个命名空间内



## 类（Class）

- C++ 中的类（Class）和对象（Object）是面向对象编程（OOP）的核心概念。

- 概念

  - 类可以看作是现实世界中某种事物的抽象模型，它封装了数据和操作数据的逻辑
  - 类是一种用户自定义的数据类型，它定义了一组相关的属性（数据成员，也称成员变量）和行为（成员函数，即方法）
  - 类是一种蓝图或模板，用于创建具有相同属性和行为的多个对象

- 组成

  - 成员变量（Data Members）
    - 类中定义的变量，代表对象的状态或属性。
    - 它们可以是基本数据类型、自定义类型、指针等
    - 并且可以有访问修饰符（如 public、private、protected）来控制外部对它们的访问权限
    - 静态成员
      - 属于整个类而非特定对象的成员变量或成员函数，通过类名访问
      - `static`
    - 常量成员
      - 只能在构造函数初始化列表中初始化，不能在类体内或构造函数体内赋值
      -  `const` 
  - 成员函数（Member Functions）
    - 定义在类内部的函数，用于操作或访问类的成员变量，实现对象的行为
    - 成员函数可以是普通函数、构造函数、析构函数、拷贝构造函数、赋值运算符重载函数、内联函数等
    - 同样可以有访问修饰符。
    - 没有初始化列表
  - 访问修饰符
    - `缺省`  此时默认是private
    - `public `    公有成员对任何代码都可见且可直接访问
    - `protected` 成员对类自身和其派生类可见，但对类外部不可见。用于在继承体系中提供子类访问父类的某些内部细节
    - `private`   成员仅对类的内部（成员函数）可见，外部代码不能直接访问。这是实现封装的重要手段，保护对象内部状态不被随意修改。
  - 构造函数（Constructor）
    - 一种特殊的成员函数，用于初始化新创建的对象。
    - 构造函数与类同名，没有返回类型，通常用于设置成员变量的初始值
    - 构造函数可以有初始化列表
    - 可以有多个（重载）
  - 析构函数（Destructor）
    - 与构造函数相反，当对象生命周期结束时自动调用，用于释放对象所占用的资源或执行清理工作。
    - 析构函数没有返回类型，且名字是在类名前加上波浪线（`~`）
    - 只能有一个
    - 不写则有默认析构函数
  - 友元
    - 类可以声明其他函数或类为其友元，友元可以访问类的私有和保护成员，打破了封装性，但有时用于实现特定的访问需求或优化。

- 定义

  ```c++
  class ClassName {
  public:  // 访问修饰符
      // 数据成员（成员变量）
      data_type variable_name;
  
      // 构造函数
      // 初始值列表初始化成员变量，多个则用分号隔开。初始化顺序是声明次序，与在初始化列表中顺序无关
      ClassName(parameters)  : paramete_name(value) {
          
      }  
      
      // 拷贝构造函数(构造函数的一种重载)
      // 须使用引用传参，使用传值方式会引发无穷递归调用
      // 传来的引用一般用const修饰
       ClassName(const ClassName& base){
          variable_name = base.variable_name;
           ...
      } 
      
      // 成员函数 
      return_type function_name(parameters) {
          // 函数体
      }
      
      // 析构函数
      ~ClassName() {  
          // 清理工作，如释放内存、关闭文件等
      }
      
      // 引用类型必须初始化
      ClassName(int & r):ref(r){}
  private:
      // 私有成员变量和成员函数
      int& ref;
  protected:
      // 受保护的成员变量和成员函数
  }; 
  // 结尾有个分号

  
  // example
  class Rectangle {
  public:
      double length;  // 长度
      double width;   // 宽度
  
      double getArea() const {  // 成员函数：计算并返回面积
          return length * width;
      }
  };
  
  
  // 类中函数可以声明和定义分离
  // persion.h
  class Persion{
  public:
      void showInfo();
      ...
  };
  
  /// persion.cpp
  #include "persion.h"
  void Persion::showInfo(){
      ...
  }
  ```
  
  

## 对象（Object）
  - 概念

      -  对象是类的实例（Instance），是类模板的具体化。

      -  创建一个对象意味着为类定义的数据成员分配内存，并根据需要调用构造函数初始化这些成员。

      - 对象具有类所定义的属性（通过其成员变量体现）和行为（通过调用其成员函数实现）。

  - 创建对象

    - 栈上

         - 直接在作用域内声明对象（自动变量），编译器会自动调用构造函数初始化对象，并在作用域结束时调用析构函数销毁对象。

         ```c++
         MyClass obj;  // 在栈上创建对象 obj
         ```

    - 堆上

         - 使用 `new` 关键字动态分配内存并调用构造函数创建对象，返回指向该对象的指针。需要手动使用 `delete` 释放内存。

         ```cpp
         MyClass* obj = new MyClass();  // 在堆上动态创建对象，返回指向该对象的指针
         delete obj;  // 释放对象占用的内存
         ```

  - 访问

      - 成员访问

            - 通过对象名和`.`（成员访问运算符）访问对象的成员变量和成员函数。

          ```cpp
          obj.variable = value;  // 访问并修改对象的成员变量
          obj.function();        // 调用对象的成员函数
          ```

    - 指针访问

         - 对于指向对象的指针，使用 `->`（箭头运算符）访问成员。

         ```cpp
         obj_ptr->variable = value;
         obj_ptr->function();
         ```

  - 生命周期管理

        - 手动释放
              - 栈上创建的对象在其作用域结束时自动销毁，析构函数会被调用。堆上创建的对象需要手动使用 `delete` 关键字释放内存，析构函数随后被调用。
        - 智能指针
              - C++ 标准库提供了 `std::unique_ptr`、`std::shared_ptr`、`std::weak_ptr` 等智能指针，用于自动管理堆上对象的生命周期，避免内存泄漏并支持资源所有权的管理。



## 类默认函数

- 若没有显式定义，则编译器提供

  - 构造函数

    - 用于创建类对象时，如果没有提供任何初始值或使用默认初始化方式进行初始化。
- 无参、没有执行特殊初始化操作的默认构造函数。
    - 若成员变量有引用类型，则必须要初始化，可放在初始化列表中

  - 拷贝构造函数

    - 当用一个已存在的同类型对象来初始化新对象时调用，用于复制对象的内容。

    - 接受一个同类型非`const`引用参数的拷贝构造函数，它逐个成员进行浅复制（对于内置类型直接赋值，对于类类型调用其拷贝构造函数）。

  - 析构函数

    - 当对象生命周期结束时（如离开作用域或被显式销毁时），自动调用以释放对象所占用的资源。
    - 无参、没有执行特殊清理操作的默认析构函数。

  - 重载赋值运算符 =

    - 用于对象之间的赋值操作，将右侧对象的值复制到左侧对象。
    - 如果用户没有自定义赋值运算符，编译器会生成一个接受一个同类型非`const`引用参数并返回左值引用的赋值运算符，它同样进行逐个成员的浅复制

  - 重载取址运算符&

    - 返回对象的内存地址。
    - 通常，用户无需也不应自定义取址运算符，因为编译器已经提供了正确的实现。取址运算符对于所有对象都是内建的，不需要类显式声明或定义

  - const取址运算符&

    - 与普通取址运算符相同，但适用于`const`对象，返回`const`对象的内存地址。
    - 同上，这是编译器内建的功能，无需用户显式声明或定义。

  - C++11)移动构造函数

    - 当用一个即将销毁的右值对象来初始化新对象时调用，用于转移对象的资源而无需复制。
    - 如果用户没有自定义移动构造函数，且类满足特定条件（如没有删除或禁用移动构造函数，且没有自定义拷贝构造函数等），编译器会生成一个接受一个同类型右值引用参数的移动构造函数，它通常转移资源而不复制。

  - C++11）移动赋值运算符=

    - 用于对象之间的移动赋值操作，将右侧即将销毁的对象的资源转移给左侧对象。
    - 与移动构造函数类似，如果满足特定条件且用户未定义，编译器会生成一个接受一个同类型右值引用参数并返回左值引用的移动赋值运算符，它转移资源而不复制。

- example

  ```c++
  #include <iostream>
  #include <memory>
  #include <string>
  
  class Example {
  public:
      // 默认构造函数（模拟）
      Example() : value(), text() {
          std::cout << "Default Constructor called.\n";
      }
  
      // 默认拷贝构造函数（模拟） 
      // 必须引用传参，否则会无限递归
      Example(const Example& other) : value(other.value), text(other.text) {
          std::cout << "Copy Constructor called.\n";
      }
  
      // 默认析构函数（模拟）
      ~Example() {
          std::cout << "Destructor called.\n";
      }
  
      // 默认赋值运算符重载函数（模拟）
      Example& operator=(const Example& other) {
          if (this != &other) {
              value = other.value;
              text = other.text;
          }
          std::cout << "Assignment Operator called.\n";
          return *this;
      }
  
      // C++11及以后：默认移动构造函数（模拟）
      Example(Example&& other) noexcept : value(std::exchange(other.value, 0)),
                                          text(std::move(other.text)) {
          std::cout << "Move Constructor called.\n";
      }
  
      // C++11及以后：默认移动赋值运算符（模拟）
      Example& operator=(Example&& other) noexcept {
          value = std::exchange(other.value, 0);
          text = std::move(other.text);
          std::cout << "Move Assignment Operator called.\n";
          return *this;
      }
  
  private:
      int value;
      std::string text;
  };
  
  // 补充。引用类型必须初始化.默认构造函数无法满足，需要提供一个
  ClassName{
  public:
        ClassName(int & r):ref(r){}
  private:
      // 私有成员变量和成员函数
      int& ref;  
  };
  
  int main() {
      Example a;
      Example b(a);  // 使用默认拷贝构造函数
      b = a;         // 使用默认赋值运算符
  
      Example c(std::move(a));  // 使用默认移动构造函数（C++11及以后）
      c = std::move(b);         // 使用默认移动赋值运算符（C++11及以后）
  
      return 0;
       /*
       * console info:
       * Default Constructor called.
       * Copy Constructor called.
       * Assignment Operator called.
       * Move Constructor called.
       * Move Assignment Operator called.
       * Destructor called.
       * Destructor called.
       * Destructor called.
       */
  }
  ```




## 封装

- （Encapsulation）
  - 通过访问修饰符隐藏对象的内部细节，只暴露必要的接口（公有成员）给外部访问，保护数据完整性，提高代码的可维护性。

## 继承

- （Inheritance）
  
  - 一个类可以从另一个类（基类）继承属性和行为，形成派生类
  - 并在此基础上添加额外的属性或重写现有行为。继承促进了代码的重用，降低了模块间的耦合度，并支持多态性
  
- 特点

  - 支持多继承
    - 一个类可以从多个基类继承，形成多重继承关系。需要解决名称冲突（如菱形继承）和构造函数调用顺序问题
  - 访问控制与继承
    - 派生类对基类的公有和保护成员具有相应的访问权限，私有成员不可访问。派生类可以声明为 `public`、`protected` 或 `private` 继承基类。

- 菱形继承

  - 一个类通过两个或多个路径间接继承了同一个基类的情况，形成了类似菱形的继承结构
  - 会导致资源浪费且可能导致数据不一致的问题

- 虚继承

  - 解决多重继承中特定问题的一种机制，尤其是针对菱形继承问题，确保派生类只有一个基类实例
  - 为了确保所有派生类都能访问到同一个虚基类，编译器会在派生类对象中增加一个指向虚基类的指针，以解决引用冲突
  - 在创建含有虚基类的派生类对象时，虚基类的构造函数只会调用一次，通常由最直接的派生类负责调用
  - 虚继承会增加额外的内存开销，但可以解决菱形继承带来的数据冗余和访问歧义问题
  - 由于增加了复杂性和运行时开销，虚继承在实际编程中并不常见，尤其是在提倡单一责任原则和尽量避免多重继承的设计哲学下

- 访问权限

  - 继承中的访问修饰符决定了派生类对基类成员的访问程度，分为`public`、`protected`和`private`	
    - `public`继承
      - 派生类可以访问基类的`public`、`protected`成员，但不能访问`private`成员。
    - `protected`继承
      - 派生类可以访问基类的`public`、`protected`成员，但不能访问`private`成员
    - `private`继承
      - 派生类可以访问基类的`public`、`protected`成员，并视其为基类的`private`成员。同样不能访问基类`private`成员

- example

  - 单继承

  ```c++
  // 子类调用父类同名方法
  class Human{
  public:
      void func(){
          cout<<"human func"<<endl;
      }
  };
  class Man :public Human{
  public:
  
      void func(){
          cout<<"man func"<<endl;
      }
  
      void tfunc(){
          Human::func(); // human func
          func(); // man func
      }
  };
  
  
  
  // 单一继承，派生类只继承一个基类
  class Base {
  public:
      void publicFunc() { }
  protected:
      void protectFunc(){}
  private:
      void privateFunc(){}
  };
  
  // 无论什么继承都不能访问私有成员
  class PublicDrive: public Base { 
  public:
      void func(){
          publicFunc();
          protectFunc();
          // privateFunc(); // 'privateFunc' is private member of 'Base'
      }
  };
  
  class ProtectDrive: protected Base { // Derived继承自Base
  public:
      void func(){
          publicFunc();
          protectFunc();
          // privateFunc(); // 'privateFunc' is private member of 'Base'
      }
  };
  
  class PrivateDrive: private Base { // Derived继承自Base
  public:
      // 在内部还是能够访问基类的public,protected成员的
      void func(){
          publicFunc();
          protectFunc();
          // privateFunc(); // 'privateFunc' is private member of 'Base'
      }
  };
  
  
  int main() {
      
      Base b;
      b.publicFunc();
      // b.protectFunc(); // 'protectFunc' is protected member of 'Base'
      // b.privateFunc(); // 'privateFunc' is private member of 'Base'
  
      
      // public继承，权限没有改变
    PublicDrive d1;
      d1.publicFunc(); // ok
    // d1.protectFunc(); // 'protectFunc' is protected member of 'Base'
      // d1.privateFunc(); // 'privateFunc' is private member of 'Base'
  
  
      // protected继承，高于protected权限的成员都降级为protected
      ProtectDrive d2;
      // d2.publicFunc(); // 'protectFunc' is protected member of 'Base'
      // d2.protectFunc(); // 'protectFunc' is protected member of 'Base'
      // d2.privateFunc(); // 'privateFunc' is private member of 'Base'
      
      
      // private继承，高于private权限的成员都降级为private
      PrivateDrive d3;
      // d3.publicFunc(); // 'publicFunc' is private member of 'Base'
      // d3.protectFunc(); // 'protectFunc' is private member of 'Base'
      // d3.privateFunc(); // 'privateFunc' is private member of 'Base'
  
  }
  
  ```
  
  - 多继承
  
  ```cpp
class Base1 {
  protected:
    void base1Function() { std::cout << "Base1 function called.\n"; }
  };
  
  class Base2 {
  public:
      void base2Function() { std::cout << "Base2 function called.\n"; }
  };
  
  class Derived : public Base1, public Base2 { // 多继承
  public:
      void derivedFunction() { std::cout << "Derived function called.\n"; }
  };
  
  int main() {
      Derived d;
      d.base1Function(); // 从Base1继承的方法
      d.base2Function(); // 从Base2继承的方法
      d.derivedFunction(); // 派生类新增的方法
  }
  ```
  
  - 虚继承
  
  ```c++
  // 在继承A时加上virtual关键字，声明B和C对A的继承为虚继承，这样在D类中只有一个共享的A类实例
  class A{...};
  class B : public virtual A { ... };
  class C : public virtual A { ... };
  
  // 创建D对象时，A的构造函数只会通过B或者C的构造函数被调用一次
  class D : public B, public C { ... };
  
  ```



## 多态

- （Polymorphism）
  
  - 同一操作作用于不同对象时表现出不同行为的能力
  
- 特点
  - 编译时多态
    - 通过函数重载、运算符重载实现
    - 编译器根据函数参数类型和数量确定调用哪个函数版本。
  - 运行时多态
    - 通过虚函数、抽象类、纯虚函数实现
    - 运行时多态允许在基类指针或引用上通过虚函数调用派生类的实现，实现动态绑定。

- 原理

  - 对于虚函数类会在对象的成员变量中生成虚函数表指针，指向的虚函数表中储 了该对象的虚函数地址
  - 对于派生类会继承基类的虚函数表，如果派生类重写了虚函数，则会对继承的虚函数表中对应的函数地址进行覆盖成派生类对象的虚函数
  - 当对象调用虚函数时会先找到虚函数表，通过虚函数表找到对应的虚函数，由此构成多态
    
  
- example

  ```c++
  class Base{
  public:
      virtual void print()
      { std::cout << "Base class\n"; }  // 虚函数
  };
  
  class Derived : public Base{
  public:
      void print() override
      { std::cout << "Derived class\n"; }  // 重写虚函数
  };
  
  int main()
  {
      Base *basePtr = new Derived();
      basePtr->print();  // 输出 "Derived class"，体现了运行时多态
  }
  ```



## 虚函数

- 简介

  - C++中的虚函数（Virtual Function）是面向对象编程中实现多态（Polymorphism）的关键机制之一
  - 虚函数允许我们在基类中定义一个函数，并在派生类中对其进行重写（Override），从而实现动态绑定（Dynamic Binding）

- 动态绑定

  - 通过基类指针或引用调用虚函数时，编译器会在运行时根据指针或引用所指向对象的实际类型来决定调用哪个函数
  - 在派生类中如果没有明确指出重写虚函数，则派生类将继承基类的虚函数实现
  - 如果派生类重写了虚函数，那么在多态调用时会调用派生类的版本

- 条件

  - 继承
  - 必须通过基类的指针或者引用调用虚函数
  - 被调用的函数必须是虚函数，且派生类必须对基类的虚函数进行重写

- 原理

  - 虚函数表
    - 每个包含虚函数的类都有一个虚函数表，该表包含了类中所有虚函数的地址
    - 虚函数表是在编译阶段就生成的，一般情况下存在代码 段(常量区)的
  - 虚函数指针
    - 每个类实例的内存布局中首部会有一个指向其所属类虚函数表的指针vptr，当通过虚函数调用时，编译器会通过vptr定位到正确的函数地址来调用

- 能否为虚函数

  - 否
- 静态成员函数
      - 静态成员函数函数没有this指针，使用类型::成员函数的调用方式 无法访问虚函数表，所以静态成员函数无法放进虚函数表
  - 构造函数
      - 对象中的虚函数表指针是在构造函数初始化列表阶段才初始 化的
  - 可
    - inline函数
      - 不过编译器会忽略inline属性，这个函数就不再是inline，因为虚函数要放到虚表中去
    - 析构函数
      - 且最好把基类的析构函数定义 成虚函数
      -  当基类指针指向new出来的派生类对象的时候，需要使用指针进行释放对象，此时需要析构函数为虚函数，保证指针指向的对象成功释放
  
- example

  - 声明和实现

  ```cpp
  // 函数前添加virtual来声明
  // 虚函数提供实现的话，当前类是可以创建对象的。否则即使用父类引用指向子类对象都不可以创建Base *dd=new Derived();
  class Base {
  public:
      virtual void display(){
          std::cout << "Base 1" << std::endl;
      }
  
      void show(){
          std::cout << "Base 2" << std::endl;
      }
  };
  
  // 函数后添加override来实现。不添加也可以。
  // 只有virtual函数可以添加override
  class Derived : public Base {
  public:
      void display() override { // 重写基类的display()函数
          std::cout << "Derived 1" << std::endl;
      }
  
      // 不能发生重载，动态绑定
      void show(){
          std::cout << "Derived 2" << std::endl;
      }
  };
  
  
  // 不能实现动态绑定
  void func_1(Base b){
      b.display(); // Base 1
  }
  // 可动态绑定
  void func_2(Base *b){
      b->display(); // Derived 1
  }
  // 可动态绑定
  void func_3(Base &b){
      b.display(); // Derived 1
  }
  
  
  int main()
  {
      Derived d;
      d.show(); // Derived 2
      d.display(); // Derived 1
  
      // 无论是否加virtual 需要Base中的函数都有（默认）实现才能这样创建对象
      Base *b=new Base();
      b->display(); // Base 1
  
      // 这里可以看到加了virtual的display发生了重载，可以实现多态
      // 而不加virtual，不是虚函数的show则只调用了父类的实现，并没有调用子类的实现
      Base *dd=new Derived(); // new返回的是指针
      dd->show(); // Base 2
      dd->display(); // Derived 1
  
      Base& d2=*dd;
      d2.show(); // Base 2
      d2.display(); // Derived 1
  
      // 作为参数时是否可以多态
      func_1(*dd); // Base 1
      func_2(dd); // Derived 1
      func_3(*dd); // Derived 1
  }
  ```

  - 纯虚函数
    - 若希望基类成为一个抽象类（Abstract Class），不允许直接创建实例，则可在虚函数声明后添加`= 0`来声明一个纯虚函数
    - 纯虚函数不能在基类中给出默认实现，必须在派生类中实现

  ```cpp
  class AbstractBase {
  public:
      virtual void show()=0;
      virtual void doSomething() const = 0; // 声明一个纯虚函数
  };
  
  // 必须在派生类中实现纯虚函数
  class ConcreteDerived : public AbstractBase {
  public:
      virtual void show() override{};
      void doSomething()const override {
          std::cout << "ConcreteDerived doing something." << std::endl;
      }
  };
  
  int main()
  {
      // 要实现全部函数才能创建对象
      ConcreteDerived c;
  }
  ```



## 内部类

- 简介

  - C++的内部类（也称为嵌套类或嵌套类型）是指在一个类的定义内部定义的另一个类
  - 有助于提高代码的内聚性和降低耦合度

- 特点

  - 访问权限
    - 内部类可以访问外部类的所有成员，无论它们的访问修饰符是`public`、`protected`还是`private`。这是因为它与外部类紧密关联，可以看作是外部类的“亲密朋友”。
  - 封装性
    - 尽管内部类可以访问外部类的私有成员，但外部类并不直接访问内部类的成员。若要让外部类或其他外部代码使用内部类，需要对外部类提供适当的接口（如公有方法返回内部类的实例）。
  - 命名空间隔离
    - 内部类在外部类的作用域内定义，其名称不会与外部作用域中的其他标识符冲突。这有助于提高代码的组织性和可读性，特别是当类的实现细节较多时。
  - 编译单元
    - 内部类的定义和实现都在外部类的源文件中，不需要单独的头文件（除非需要在外部类之外使用内部类）。这简化了编译单元管理，减少编译依赖。
  - 存储布局
    - 外部类的大小不包含内部类的大小，因为内部类的实例是独立创建和管理的。内部类的实例可以作为外部类成员（静态或非静态），也可以在外部类的方法或外部代码中创建。
  - 友元关系
    - 内部类天生是外部类的友元类，可以直接访问外部类的所有成员。然而，外部类并不是内部类的友元，不能直接访问内部类的私有和受保护成员。

- 应用场景

  - 封装复杂性
    - 内部类可以隐藏实现细节，将相关的逻辑组织在一起。例如，一个图形类可能有一个内部类来表示其内部坐标系统，该内部类对外部类透明。
  - 辅助功能
    - 内部类可以作为辅助工具类，只对外部类提供服务，不暴露给其他代码。比如，一个数据库连接类可能有一个内部类来封装SQL查询的构建逻辑。
  - 实现迭代器、访问器等
    - 内部类常用于实现容器类的迭代器或访问器，这些类通常只在容器类的上下文中使用，对外部世界隐藏。
  - 静态内部类
    - 静态内部类与外部类之间不存在实例关联关系，通常用于组织逻辑相关的类，或者作为外部类的工具类，不依赖外部类的实例。

- example

  ```c++
// 内部类作为辅助工具类
  class Outer {
public:
      void process() {
          Inner inner; // 创建内部类实例
          inner.doSomething(); // 内部类访问外部类私有成员
      }
  private:
      int secretData;
  
      class Inner {
      public:
          void doSomething() {
              // 直接访问外部类私有成员
              std::cout << "Processing with secretData: " << outer.secretData << std::endl;
          }
      private:
          Outer& outer; // 内部类持有外部类的引用
          Inner(Outer& o) : outer(o) {} // 内部类构造函数接收外部类引用
      };
  };
  int main() {
      Outer outer;
      outer.process();
  }
  
  
  // 内部类作为迭代器
  class Stack {
public:
      class Iterator {
    public:
          Iterator(const Stack* stack, size_t pos) : stack(stack), position(pos) {}
  
          bool hasNext() const { return position < stack->size; }
          int next() { return stack->data[position++]; }
  
      private:
          const Stack* stack;
          size_t position;
      };
      Iterator begin() const { return Iterator(this, 0); }
      Iterator end() const { return Iterator(this, size); }
  private:
      std::vector<int> data;
      size_t size;
  };
  
  int main() {
      Stack stack;
      // ...填充stack...
      for (auto value : stack) {
          std::cout << value << std::endl;
      }
  }
  
  ```



## 抽象类（接口类）

- 简介
  
  - 包含至少一个纯虚函数（声明为 `virtual` 且无实现的函数，后跟`= 0`）的类。
  
- 特点

  - 抽象类不能实例化，只能作为基类被继承
  - 派生类必须实现所有纯虚函数才能实例化
  - 抽象类主要用于定义一种蓝图，描述一组接口或行为，它的目的是提供一个统一的结构，供派生类继承并实现相应的纯虚函数

- example

  ```cpp
  // 纯虚函数形式
  virtual ReturnType functionName(parameters) = 0;
  
  
  // 抽象类 Animal
  class Animal {
  public:
      // 纯虚函数 - 所有动物都应该具备叫唤的能力，但具体的叫声由派生类实现
      virtual void makeSound() const = 0;
  
      // 其他成员函数，包括构造函数和普通虚函数都可以有
      Animal(const std::string& name) : mName(name) {}
  
  protected:
      std::string mName; // 动物的名字，受保护成员，子类可以访问
  };
  
  // 派生类 Dog 继承自抽象类 Animal，并实现 makeSound() 函数
  class Dog : public Animal {
  public:
      Dog(const std::string& name) : Animal(name) {}
  
      void makeSound() const override {
          std::cout << mName << ": Woof!" << std::endl;
      }
  };
  
  // 派生类 Cat 同样继承自抽象类 Animal，并实现 makeSound() 函数
  class Cat : public Animal {
  public:
      Cat(const std::string& name) : Animal(name) {}
  
      void makeSound() const override {
          std::cout << mName << ": Meow!" << std::endl;
      }
  };
  
  int main() {
      // 不能创建抽象类 Animal 的实例
      // Animal animal("Generic Animal"); // 错误：不能实例化抽象类
  
      // 但可以创建其派生类的实例
      Dog dog("Fido");
      Cat cat("Whiskers");
  
      dog.makeSound(); // 输出 "Fido: Woof!"
      cat.makeSound(); // 输出 "Whiskers: Meow!"
  
      return 0;
  }
  ```
  - 仅仅因为其成员函数没有提供实现,不能算抽象类

  ```c++
  // 
  class AbstractBase {
  public:
      void show();
  };
  
  // 必须在派生类中实现纯虚函数
  class ConcreteDerived : public AbstractBase {
  public:
      void show() {cout<<"hi";};
  };
  
  int main()
  {
      // 要实现全部函数才能创建对象
      AbstractBase a;
      // a.show(); // undefined reference to `AbstractBase::show()'
      ConcreteDerived c;
      c.show();
  }
  ```

  


## 友元

- 简介

  - 在C++中，友元（Friend）是一种特殊的关系，它允许一个类或函数访问另一个类的私有（private）和保护（protected）成员
  - 友元打破了封装原则，但它在某些特定情况下能提供必要的灵活性


- 性质

  - 非对称
    - 友元关系不是对称的，如果A是B的友元，不意味着B也是A的友元
  - 非传递
    - 友元关系也不传递，即如果A是B的友元，C是B的友元，这并不能使A成为C的友元
  - 非封装
    - 友元关系打破了类的封装性，过度使用可能导致代码难以理解和维护
    - 应当谨慎使用，并仅在必要时（如实现某些设计模式或提供特殊访问通道）使用友元功能

- example

  - 友元类
    - 一个类可以声明另一个类为其友元，这样友元类就可以访问本类的私有和保护成员

  ```cpp
  class A {
  private:
      int secretData;
  protected:
      int protectData;
  public:
      friend class B; // 声明B为A的友元类
  };
  
  class B {
  public:
      void accessSecret(A &a) {
          a.secretData = 42; // B可以通过友元关系直接访问A的私有成员secretData
      }
      
      void accessProtect(A &a) {
          a.secretData = 42; // B可以通过友元关系直接访问A的保护成员protectData
      }
  };
  
  int main() {
      A a;
      B b;
      b.accessSecret(a);
      b.accessProtect(a);
      return 0;
  }
  ```

  - 友元函数
    - 一个非成员函数（不属于该类的）可以被声明为一个类的友元，同样能访问该类的私有和保护成员
  - 由于是非成员函数，故不能用const修饰
    - 一个函数可以是多个类的友元函数
  
  ```cpp
  class C {
  private:
      int hiddenValue;
  protected:
      int protectValue;
  public:
      friend void display(C &c); // 声明display函数为C的友元函数
  };
  
  void display(C &c) {
      c.hiddenValue = 100; 
      c.protectValue = 200; 
      std::cout << "Hidden value: " << c.hiddenValue << std::endl; // 100
      std::cout << "Protect value: " << c.protectValue ; // 200
  }
  
  int main() {
      C c;
      display(c); // 友元函数可以直接修改和访问C的私有成员和保护成员
      return 0;
  }
  ```



## string

- 简介
  - `std::string` 提供了一种方便、安全且功能丰富的字符串操作方式
  - 与传统的 C 字符串（即 `char` 数组）相比，它具有自动内存管理、内置大小计算、丰富的成员函数以及对各种常见字符串操作的直接支持等优点
  - 底层是basic_string，动态字符数组

- example

  ```c++
  #include <string>
  
  using namespace std;
  
  int main()
  {
      string s1;
      s1 = "str1";
      cout << s1 << endl; // str1
      string s2 = "str2";
      string s3 = string("str3");
      string s4 = string(s3);
      char cstr1[] = "C-style string";
      string s5 = string(cstr1);
  
      cout << s5.length() << endl; // 14
      // 底层实现原理和length()一致，引入size()是为了和其他容器接口一致，一般用size()
      cout << s5.size() << endl; // 14
      cout << s5.capacity() << endl; // 15
  
      s1.clear(); // 将有效字符清空，不改变capacity()大小
      cout << s1 << endl; // 空白
  
      s2.append("-2");
      s2.insert(0, "ii");
      s2.replace(0, 1, "k");
      s2.erase(0, 1);
      cout << s2 << endl; // istr2-2
  
      string s6 = s5.substr(0, 3);
      s6.compare(s1);
      cout << s6 << endl; // C-s
      cout << s6[0] << endl;
      cout << s6.at(1) << endl;
      s6[0] = 'H';
      s6.at(2) = 'h';
      cout << s6 << endl; //H-h
  
      const char *cstr2 = s5.c_str(); // 转为字符数组
      printf("%s\n", cstr2); // C-style string
      const char *cstr3 = s5.data(); // 不受null终止符限制
  
      char buffer[100];
      strcpy(buffer, s5.c_str());
  
      int res1 = s5.find("style");
      cout << res1 << endl; // 2
      int res2 = s5.rfind("style");
      cout << res2 << endl; // 2
  
      for (auto it = s5.begin(); it != s5.end(); it++)
      {
          cout << *it << " "; // C - s t y l e   s t r i n g
      }
      cout << endl;
  
  
      string s7 = "str7";
      string s8 = "str7";
      string s9 = "str9";
  
      cout << (s7 == s8) << endl; // 1
      cout << ("str7" == "str7") << endl; // 1
      cout << (1 == 1) << endl; // 1
      cout << s7.compare(s8) << endl; // 0   相等返回0
  
      cout << s7.compare(s9) << endl; // -1 小于负数
      cout << s9.compare(s7) << endl; // 1 大于正数
  
      // cout<<(s7-s9)<<endl; // 报错
  
      s9.append("-9");
      s9.pop_back(); // 删除最后一个字符
  
      cout << s9.empty() << endl; // 0
  
      string s10=s9+s8;
      return 0;
  }
  ```

# ==IO==



## IO流

- 流的概念

  - “流”即是流动的意思，是物质从一处向另一处流动的过程，是对一种有序连续且具有方向性的数据（其单位可以是bit,byte,packet ）的抽象描述
  - C++流是指信息从外部输入设备（如键盘）向计算机内部（如内存）输入和从内存向外部输出设备（显示器）输出的过程，这种输入输出的过程被形象的比喻为“流”
  - 特性
    - 连续性
    - 方向性

- 图示

  - ios为基类，其他类都是直接或间接派生自ios类

    ![1.png](https://ucc.alicdn.com/pic/developer-ecology/f03574e35ed9437a94816ccfb8e3df2a.png)



## cin&cout

- C
  - scanf(): 从标准输入设备(键盘)读取数据，并将值存放在变量中
  - printf(): 将指定的文字/字符串输出到标准输出设备(屏幕)
  - 输入、输出缓冲区
    - 可移植性，屏蔽掉低级I/O的实现，低级I/O的实现依赖操作系统本身内核的实现
    - 行读取：计算机本身没有“行”这个概念，有了缓冲区，可以按行处理

- C++

  - cin
    - 类型安全
      - 能够根据目标变量的类型自动解析输入的相应类型数据，如输入一个整数时，`cin` 会跳过开头的空白字符（空格、制表符、换行符），然后读取一个整数值
    - 连续读取
      - 从同一行输入中读取多个不同类型的变量，如 `cin >> x >> y >> z;`每个变量之间用空白分隔
    - 错误处理
      - 若输入无法转换为目标类型的合法值（如在读取整数时输入非数字字符），`cin` 将进入错误状态。后续的输入操作将失败，直到错误状态被清除。可通过 `cin.clear()` 清除错误状态，通常结合 `cin.ignore()` 忽略剩余的无效输入
    - 缓冲区
      - 键盘输入的数据保存在缓冲区，可输入多行，回车键结束
    - 内置类型
      - 可直接输入、输出内置类型，因为重载了<<  >>
      - 自定义类型可重载<< >>

  - cout
    - 格式化
      - 可以连续使用 `<<` 运算符将多个数据或字符串插入到输出流中。它们将按照出现的顺序依次输出，中间没有默认的分隔符
    - 换行
      - `endl` 是一个特殊的操纵符，不仅输出一个换行符（`\n`），还会刷新缓冲区，确保立即输出到屏幕上
    - 流控制
      - `cout` 还支持输出流控制，如条件输出（`std::boolalpha` 改变布尔值的输出样式）、宽度设置（`setw()` 控制字段宽度）



## cerr&clog

- 简介
  - `cerr` 和 `clog` 都是标准库提供的用于输出错误信息或其他非正常逻辑输出的流对象
  - 属于 `std` 命名空间下的 `ostream` 类型

- 含义
  - cerr
    - 非缓冲的输出流，这意味着当你使用 `cerr` 输出数据时，这些数据会立即被发送到标准错误设备
    - 适合用来报告紧急错误信息或关键状态更新
  - clog
    - 缓冲的输出流，直到缓冲区满、程序结束、遇到 endl、或者显式调用 `flush()` 函数时刷新缓冲区
    - 适用记录较为频繁但并非紧急的日志信息，避免过于频繁的系统调用影响性能

- example

  ```c++
  void logSomething(int delaySecs) 
  {
      this_thread::sleep_for(chrono::seconds(delaySecs));
      auto now = std::chrono::system_clock::to_time_t(std::chrono::system_clock::now());
  
      // Log message at 2024-04-24 19:51:18
      // Log message at 2024-04-24 19:51:19
      clog << "Log message at " << ctime(&now);
  }
  
  
  int main()
  {
      // cerr和clog在控制台都是红色字体
      cerr<<"Error  opening file \n";
      
      thread t1(logSomething, 2);
      thread t2(logSomething, 3);
      t1.join();
      t2.join();
      clog << "Main thread finished.";
      return 0;
  }
  ```



## 文件IO

- 简介

  - C++中的文件输入输出（File Input/Output, 或简称 File I/O）是指程序与外部文件之间的数据交换过程
  - C++通过标准库中的`fstream`模块提供了一系列类和函数来实现文件的读取、写入以及管理

- 分类（std命名空间）

  - ifstream
    - 用于从文件中读取数据，它是`std::istream`的派生类。
  - ofstream
    - 用于向文件中写入数据，它是`std::ostream`的派生类。
  - fstream
    - 同时具备读写功能，可以用于读取和写入同一个文件。它是`std::iostream`的派生类。
  - filebuf
    - 更底层，处理I/O缓冲。倾向于使用上面3个，用到再补充

- 读写模式

  - ios_base::in
    - 以读取模式打开
  - ios_base::out
    - 以写入模式打开文件，如果文件不存在则创建，如果存在则清空原有内容。
  - ios_base::app
    - 以追加模式打开文件，所有写入操作都在文件末尾进行，不会覆盖原有内容。
  - ios_base::ate
    - 打开后立即定位到文件末尾。
  - ios_base::binary
    - 以二进制模式打开文件，适用于处理非文本数据。
  - ios_base::trunc
    - 打开文件时清除已存在的文件流

- 方式

  - write()、read()
    - 直接写入指定长度的原始数据块到文件中
    - 常用于写入二进制数据或已知大小的字符数组
  - << >>
    - 接受任何可格式化的数据类型
    - 常用于文本文件

- example

  ```c++
  #include "fstream"
  #include <numeric>
  using namespace std;
  
  
  // 写入字符数组
  int main()
  {
      char data[] = {'H', 'e', 'l', 'l', 'o', ',', ' ', 'W', 'o', 'r', 'l', 'd', '!'}; // 字符数组
      ofstream ofs("output.txt", ios::binary);
      if (ofs.is_open()) {
          ofs.write(data, sizeof(data));
          ofs.close();
      }
  }
  
  
  // 读取文件
  // numbers.txt所在位置:cmake-build-debug/numbers.txt    因为程序的.exe文件在这里，如果放在源码位置，则运行时找不到
  // 内容:3 1 2 4 5 9
  int main() {
      
      ifstream inputFile("numbers.txt");
      if (!inputFile) {
          // inputFile.close(); // 文件未打开，不需要关闭，可能导致未定义行为
          cerr << "Error opening file.\n";
          return 1;
      }
      vector<int> numbers;
      int num;
      while (inputFile >> num) {
          numbers.push_back(num);
      }
      inputFile.close(); // 
      double average = static_cast<double>(accumulate(numbers.begin(), numbers.end(), 0)) / numbers.size();
      cout << average << '\n'; // 4
      return 0;
      
  }
  
  
  // 文件复制
  int main() {
      ifstream sourceFile("source.txt");
      ofstream destFile("dest.txt");
  
      if (!sourceFile || !destFile) {
          std::cerr << "Error opening files.\n";
          return 1;
      }
  
      string line;
      while (getline(sourceFile, line)) {
          destFile << line << '\n';
      }
  
      sourceFile.close();
      destFile.close();
  
      return 0;
  }
  
  
  
  // 模式可以组合使用
  ofstream writeFile("output.txt", ios_base::out | ios_base::trunc); // 写入模式，清空已有内容
  
  
  // 读取一行
  string line;
  getline(inputFile, line);
  
  // 写入数据
  ofstream output("output.txt");
  output << "Some text to write.\n";
  output << 42 << '\n';  // 写入整数
  
  
  // 改变读写指针的位置: `seekg()`（get position）和`seekp()`（put position）   配合`tellg()`和`tellp()`
  // 移动读指针到第100字节处
  input.seekg(100);
  // 获取当前读指针位置
  streampos pos = input.tellg();
  
  
  
  ```

- 在C++中，`ofstream`是用于文件输出的一个类，它可以用来向磁盘上的文件写入数据。`ofstream`提供了两种主要的写入数据的方式：`.write()`函数和插入运算符`<<`。

  ### ofstream.write()
  `.write()`函数用于。其基本语法是：

  ```cpp
  ofstream::write(const char* s, streamsize n);
  ```
  其中，
  - `s` 是指向要写入的数据的指针。
  - `n` 是要写入的字节数。

  例如：

  ```cpp
  #include <fstream>
  
  
  ```
  这段代码会将字符数组`data`的内容原样写入到名为"output.txt"的文件中。

  ### ofstream << 运算符
  插入运算符`<<`提供了一种更为直观和方便的方式来写入数据，它可以，并按照人类可读的格式将它们写入到文件中。这对于文本文件的操作尤为常见。

  例如：

  ```cpp
  #include <fstream>
  #include <string>
  
  std::string greeting = "Hello, World!";
  std::ofstream out_file("greeting.txt");
  
  if (out_file.is_open()) {
      out_file << greeting << '\n'; // 写入字符串和换行符
      out_file << 42 << '\t' << 3.14159; // 写入整数和浮点数，中间用制表符分隔
      out_file.close();
  }
  ```
  在这段代码中，`<<`运算符被连续使用来写入字符串、换行符、整数以及浮点数。生成的"greeting.txt"文件将包含以下内容（假设每个元素都在同一行）：

  ```
  Hello, World!
  42    3.14159
  ```

  总结来说：
  - `.write()`适用于需要精确控制字节级别的数据写入，尤其是处理二进制数据时。
  - `<<`运算符更符合面向对象编程的风格，它更加灵活且易于使用，尤其适合文本数据的输出，因为C++会自动处理类型转换和格式化问题。



## stringstream

- 字符流

  - 字符串当作输入或输出流来处理，支持多种数据类型的插入和提取操作
  - 底层维护了一个string类型的对象用来保存结果
  - 多次数据类型转化时，要用clear()来清空，才能正确转化，但clear()不会将stringstream底层的
    string对象清空
  - 使用s. str(“”)方法将底层string对象设置为""空字符串
  - stringstream使用string类对象代替字符数组，可以避免缓冲区溢出的危险，而且其会对参数类型进
    行推演，不需要格式化控制，也不会出现格式化失败的风险，因此使用更方便，更安全

- 格式

  - `istringstream`: 用于从字符串读取数据，即把字符串当作输入流（istream）来解析。
  - `ostringstream`: 用于向字符串写入数据，即将数据格式化后输出到一个字符串中。
  - `stringstream`: 继承了上述两种功能，既可以读也可以写。

- example

  ```c++
  
  #include <sstream>
  #include <string>
  
  // 从字符流中读数据 >>
  int main()
  {
      string input = "123 abc -45.67";
      istringstream iss(input);
      int num1;
      string text;
      double num2;
  
      iss >> num1 >> text >> num2;
      cout << text <<" " << text <<" "<< num2<<endl; // abc abc -45.67
  
      // 不是一次性，但当前状态无法读取到数据
      int num3 =  2;
      iss >> num3 >> text >> num2;
      cout<<num3<<endl; // 2
  
  
      iss.clear(); // 清除流状态标志
      iss.seekg(0); // 重置读取位置到流开始处
      
      int num4;
      iss >> num4 >> text >> num2;
      cout<<num4; // 123
  }
  
  
  // <<
  int main()
  {
      int number = 42;
      string name = "Alice";
      double pi = 3.14159;
  
      stringstream ss;
      ss << number << " " << name << " " << pi;
      string output = ss.str();
      
      cout<<output; // 42 Alice 3.14159
  }
  
  
  // 字符串转数组
  int main()
  {
      int num;
      string strNumber = "42";
      stringstream convert(strNumber);
      
      convert >> num;
      
      if (convert) {
          cout<<num; // 42
      } else {
          clog<<"Fail to convert string to int";
      }
  }
  ```



# ==other==

## 多线程

- 简介
  - C++的多线程编程允许在同一程序中同时执行多个独立的执行路径（线程），从而实现任务并行处理，提升程序性能和响应能力
  - C++11标准引入了跨平台的原生线程支持，使得在C++中创建和管理线程变得更加直接和便捷
  - 多线程机制充分利用现代多核处理器的并行处理能力，同时确保数据一致性与正确性
  - 开发中应根据具体需求选择合适的同步原语，遵循线程安全编程原则，避免竞态条件和数据竞争。
- 线程管理
  - 启动
    - 创建`std::thread`对象时，传递一个可调用对象（如函数、lambda表达式或函数对象）作为线程入口点。线程创建后立即进入就绪状态，等待操作系统调度执行。
  - 阻塞等待
    - join()：调用`join()`会使当前线程阻塞，直到被`join()`的线程完成。这样可以确保线程安全结束，且资源被正确回收。
  - 分离
    - detach()：调用`detach()`将线程从`std::thread`对象中分离，使线程变为守护线程，独立于`std::thread`对象存在。分离后不能再使用`join()`，且线程结束时不会自动清理其返回值或异常。
  - 睡眠
    - sleep_for()
      - 让当前线程休眠指定时间段
  - 让权
    - yield()
      - 主动放弃当前线程的执行权，让操作系统调度其他线程运行


- 同步机制 

  - 为了协调线程间的交互，防止竞态条件和数据竞争
  - 互斥量（std::mutex）
    - 用于保护共享数据的访问，确保同一时刻只有一个线程能访问临界区
  - 锁（std::lock_guard/std::unique_lock）
    - RAII风格的锁包装器，确保互斥量在作用域结束时自动解锁。
  - 条件变量（std::condition_variable）
    - 允许线程等待某个条件满足后再继续执行，常与互斥量配合使用。
  - 原子操作（std::atomic）
    - 提供原子级别的读写操作，保证多线程环境下对变量访问的完整性。
  - 其他同步原语
    - 如`std::future`/`std::promise`用于异步任务通信，`std::barrier`用于同步多个线程到达某个执行点。

- example

  ```c++
  // 格式
  #include <thread>
  
  
  // 定义线程执行函数
  void threadFunction(parameter) {
      // 线程执行的代码...
  }
  int main() 
  {
      // 创建并启动一个新线程，传可调用对象   (已经启动了)
      std::thread myThread(threadFunction,parameter);
      // 等待myThread完成（阻塞直至线程结束）
      myThread.join();
      return 0;
  }
  
  // lambda表达式
  void print_number(int number)
  {
      std::cout << "Number: " << number << std::endl;
  }
  
  int main()
  {
      int num = 42;
      // 使用lambda表达式封装要执行的任务，并传入参数
      std::thread t([num] {
          print_number(num);
      });
      t.join();
      std::cout << "Main thread finished." << std::endl;
      return 0;
  }
  
  
  
  // 原子变量
  #include <atomic>
  std::atomic<int> counter(0);
  
  void increment_atomic()
  {
      for (int i = 0; i < 100000; ++i) {
          ++counter;
      }
  }
  
  int main()
  {
      std::thread t1(increment_atomic);
      std::thread t2(increment_atomic);
  
      t1.join();
      t2.join();
  
      std::cout << counter << std::endl; // 200000
  
      return 0;
  }
  
  
  
  
  // t1、t2交替打印1-100
  #include <thread>
  #include <mutex>
  #include <condition_variable>
  
  std::mutex mtx;
  std::condition_variable cv;
  int current_number = 1;
  bool t1_turn = true; // 初始时轮到线程T1打印
  
  void print_numbers(const char* thread_name, bool is_t1)
  {
      for (int i = 0; i < 50; ++i) {
          std::unique_lock<std::mutex> lck(mtx);
          // 等待轮到自己打印
          cv.wait(lck, [is_t1] {
              return is_t1 == t1_turn;   // true == true  or  false == false
          });
  
          std::cout << thread_name << ": " << current_number++ << std::endl;
          t1_turn = !t1_turn;
  
          // 唤醒所有等待的线程
          cv.notify_all();
      }
  }
  
  int main()
  {
      // 启动前后没影响，都是t1先打印
      std::thread t2(print_numbers, "t2", false);
      std::thread t1(print_numbers, "t1", true);
      
      t1.join();
      t2.join();
  
      return 0;
  }
  
  
  
  // 生产者-消费者 
  // 生产者:不满则生产，消费者:不空则消费。
  // 若运行时发现生产者生产满才到消费者消费，消费者消费完才到生产者生产。那原因之一可能是生产者通知消费者的这段时间有延迟，这时候生产者已经把所有的数据都生产好了
  
  #include <thread>
  #include <queue>
  #include <mutex>
  #include <condition_variable>
  
  const int BUFFER_SIZE = 100;
  
  std::queue<int> buffer; // 共享缓冲区
  std::mutex mtx; // 保护缓冲区的互斥锁
  std::condition_variable cv_producer; // 生产者通知消费者条件变量
  std::condition_variable cv_consumer; // 消费者通知生产者条件变量
  bool done_producing = false; // 标记生产者是否完成生产
  
  // 生产者线程函数
  void producer(int count)
  {
      for (int i = 1; i <= count; ++i) {
          std::unique_lock<std::mutex> lck(mtx);
  
          // 缓冲区满时，生产者等待消费者消费
          while (buffer.size() >= BUFFER_SIZE) {
              cv_producer.wait(lck);
          }
  
          buffer.push(i);
          std::cout << "Producer produced: " << i << std::endl;
  
          // 生产完毕或缓冲区非空时，通知消费者
          if (i == count || !buffer.empty()) {
              cv_consumer.notify_one();
          }
      } // 结束for循环，lck在此处销毁，自动解锁mtx
  
      done_producing = true;
      cv_consumer.notify_one(); // 通知消费者生产已完成
  }
  
  // 消费者线程函数
  void consumer()
  {
      while (true) {
          std::unique_lock<std::mutex> lck(mtx);
  
          // 缓冲区空且生产未完成时，消费者等待生产者生产
          while (buffer.empty() && !done_producing) {
              cv_consumer.wait(lck);
          }
  
          if (!buffer.empty()) {
              int data = buffer.front();
              buffer.pop();
              std::cout << "Consumer consumed: " << data << std::endl;
  
              // 缓冲区不满时，通知生产者可以继续生产
              if (!buffer.empty() || !done_producing) {
                  cv_producer.notify_one();
              }
          } else {
              break; // 生产已完成且缓冲区为空，消费者线程退出
          }
      }
  }
  
  int main()
  {
      std::thread producer_thread(producer, 1000); // 生产1000个数据
      std::thread consumer_thread(consumer);
  
      producer_thread.join();
      consumer_thread.join();
  
      return 0;
  }
  ```



## ThreadLocal

- 线程局部变量，为每个线程维护独立的数据副本

- example

  ```c++
  #include <thread>
  
  thread_local int thread_id = 0;
  void set_thread_id(int id)
  {
      thread_id = id;
      std::cout << thread_id << std::endl;
  }
  int main()
  {
      std::thread t1(set_thread_id, 1);
      std::thread t2(set_thread_id, 2);
      t1.join();
      t2.join();
      return 0;
  }
  ```



## 线程通信

- 简介

  - `std::future` 和 `std::promise`
    - 用于异步计算和结果获取
  - `std::async`
    - 异步同步，简化了任务的创建和结果获取

- example

  ```c++
  #include <thread>
  #include <future>
  
  
  // future & promise
  std::future<int> compute_value()
  {
      std::promise<int> promise;
      auto future = promise.get_future();
  
      std::thread thread([promise = std::move(promise)]() mutable { // 使用std::move转移所有权
          int result = 321;
          promise.set_value(result);
      });
  
      thread.detach();
  
      return future;
  }
  
  int main()
  {
      std::future<int> f = compute_value();
  
      // 主线程继续执行其他任务...
      int result = f.get(); // 阻塞直到计算完成并获取结果
      std::cout << result;  // 321
  
      return 0;
  }
  
  
  
  
  // async
  int some_expensive_computation()
  {
      // ...
      return 123;
  }
  
  int main()
  {
      // 异步执行函数，返回future对象
      std::future<int> f = std::async(std::launch::async, some_expensive_computation);
  
      // 主线程继续执行其他任务...
  
      int result = f.get(); // 阻塞直到计算完成并获取结果
      std::cout << result << std::endl; // 123
  
      return 0;
  }
  ```

  

## 异常机制

- 简介

  - 异常代表程序运行过程中遇到的非预期情况，如内存不足、文件未找到、无效的用户输入、算术溢出等
  - 允许程序在遇到问题时优雅地转移控制流，而不是立即崩溃
  - 增强了程序的健壮性，使得程序能够更有效地处理错误条件，并且使得错误处理代码与正常逻辑分离，提高了代码的可读性和可维护性


- 处理流程
  - 异常抛出
    - 当`throw`语句被执行时，程序中断当前执行路径，开始查找与当前`try`块关联的`catch`子句。如果找到匹配的`catch`子句（即能接受所抛出异常类型的`catch`），则执行该`catch`块中的代码来处理异常。
  - 异常匹配
    - `catch`子句按顺序检查，寻找第一个能接受抛出异常类型的子句。匹配可以是精确类型匹配，也可以是通过基类与派生类的关系进行向上转型匹配。如果所有`catch`子句都无法匹配，则程序终止。
  - 异常传播
    - 如果在当前`try`块的直接关联`catch`子句中未捕获到异常，控制权将传递到包含该`try`块的更外层作用域，继续查找匹配的`catch`子句。如果一直未找到匹配的处理者，最终会导致程序崩溃。
  - 异常清除
    - 在离开`try`块之前，无论是否发生异常，都会执行与`try`块关联的析构函数，确保资源得到正确释放。这是C++异常安全的重要保证。
- 标准库中常见

  ![image-20220925094743848](https://img-blog.csdnimg.cn/img_convert/1829e0f3365f3ad402c4a3b404a56b71.png)

  - `std::exception`：基类，所有标准库抛出的异常都派生自此类。
  - `std::bad_alloc`：当内存分配失败时（如 `new` 操作失败）抛出。
  - `std::bad_cast`：在 `dynamic_cast` 失败时抛出。
  - `std::bad_typeid`：在 `typeid` 应用于非法表达式（如空指针）时抛出。
  - `std::bad_exception`：当应该抛出用户定义类型但抛出了其他类型时，或者在 `std::unexpected()` 被调用时抛出。
  - `std::logic_error` 和其子类（如 `std::invalid_argument`, `std::domain_error`, `std::out_of_range`, `std::length_error`, `std::runtime_error`）：用于表示逻辑错误或运行时条件错误

  

- 补充
  - 异常规范(`noexcept`关键字)
    - 用于声明函数不会抛出任何异常。违反此声明的函数会导致 `std::terminate` 被调用。
  - 异常规格化
    - (`throw(...)`, `throw(type)`): 在 C++98/03 中，用于声明函数可能抛出的异常类型。在 C++11 及以后版本中，已弃用，推荐使用 `noexcept` 或者不声明。
  - 异常处理函数
    - `std::unexpected()`：当一个未声明的异常类型被抛出时调用，默认行为是调用 `std::terminate()`。可以通过设置 `std::set_unexpected()` 来自定义行为。
    - `std::terminate()`：在某些情况下（如未捕获的异常、违反 `noexcept` 规范等）调用，终止程序执行。可通过 `std::set_terminate()` 设置自定义终止处理函数。

- 最佳实践

  - 优先使用标准库提供的异常类，除非有充分理由创建自定义异常类
  - 当函数不能完成其约定的任务时，应通过`throw`语句抛出明确的异常，而不是返回错误码
  - 尽量减少 `catch(...)` 的使用，以保持异常类型的清晰性，便于精确定位和处理问题
  - 避免在 `catch` 块中做过多的工作，仅进行必要的错误恢复或清理操作，然后重新抛出或返回错误状态
  - 尽量避免在析构函数中抛出异常。如果必须这样做，确保资源已经被正确清理，或者使用 `std::nothrow` 特性来避免二次异常
  - 对于可能抛出异常的函数，考虑使用 RAII（Resource Acquisition Is Initialization）技术管理资源，确保即使在异常抛出时也能正确释放资源

- example

  ```c++
  #include <stdexcept>
  
  int main()
  {
      fstream file;
      file.open("nonexistent.txt");
  //    if (!file.is_open()) {
  //        throw std::runtime_error("Failed to open file: nonexistent.txt");
  //    }
  
      try{
          // 抛出异常则catch
          throw std::runtime_error("error");
      // 精确匹配或其父类
      }catch (const std::exception& e) {
          // 处理标准异常
          std::cerr << "Caught standard exception: " << e.what() << '\n';
      }catch (const std::bad_alloc& ba) {
          // 处理内存不足异常
          std::cerr << "Caught bad_alloc: " << ba.what() << '\n';
      }catch (const std::runtime_error& e) {
          std::cerr<<(e.what());
          // 重新抛出异常，让上层代码继续处理
          throw;
      }catch (...) {
          // 捕获所有未明确指定的其他异常 ，以防未预期的异常逃逸
          std::cerr << "Caught an unknown exception\n";
      }
      return 0;
  }
  
  
  
  // 自定义异常类
  class DivisionByZeroException : public std::runtime_error {
  public:
      DivisionByZeroException(const std::string& msg) : std::runtime_error(msg) {}
  };
  
  double safe_divide(double numerator, double denominator) {
      if (denominator == 0.0) {
          throw DivisionByZeroException("Attempted to divide by zero");
      }
      return numerator / denominator;
  }
  
  int main() {
      try {
          double result = safe_divide(10.0, 0.0);
          std::cout << "Result: " << result << '\n';
      }
      // 下面4个catch块都能匹配，但只会执行第一个匹配的catch块
      catch (const DivisionByZeroException& ex) {
          std::cerr << "Error: " << ex.what() << '\n'; // Error: Attempted to divide by zero
      }
      catch(const std::runtime_error& ex){
          cerr << "Caught a runtime error: " << ex.what() << endl;
      }
      catch (const std::exception& ex) {
          std::cerr << "Caught a standard exception: " << ex.what() << '\n';
      }
      catch (...) {
          std::cerr << "Caught an unknown exception\n";
      }
  
      return 0;
  }
  ```


