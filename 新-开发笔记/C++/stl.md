# ==c++==

## 官网

- 官方文档
  - https://cplusplus.com/reference/

- 中文网
  - https://c-cpp.com/
- 这两个都挺不错的

## 真假

- 真时是1，假是0

  ```c++
  if(map.empty()){}  // 1 or 0
  ```


# ==STL==

## typename和class区别

- 简介

  - 在C++中，`typename` 和 `class` 关键字在某些上下文中可以互换使用
  - 但在其他一些特定场合下有所区别

- example

  ```c++
  // 可互换
  // 模板参数声明:用来指示后面的标识符代表一个未知的类型
  template <typename T> // 或者 template <class T>
  class MyClass {...};
  
  
  // 不可互换
  // 在模板定义内部，尤其是在处理嵌套依赖类型时.`typename` 用于指示编译器，某个依赖于模板参数的名字是一个类型名
  // 如果不使用 `typename`，编译器无法确定 `T::value_type` 是类型还是静态成员，除非它可以通过上下文推断出来
  // 在这种情况下，`class` 不能替代 `typename`
  template<typename T>
  void MyFunction(T t){
      
      // 这里typename告诉编译器value_type是T的嵌套类型
      for (typename T::value_type value: t){
          cout << value << " "; // 1 2 3 4 5
      } 
  
  }
  int main(){
      std::vector<int> vec = {1, 2, 3, 4, 5};
      MyFunction(vec); // T 为 std::vector<int>
      return 0;
  }
  
  ```



## 模板函数

- 语法

  - template --- 声明创建模板 
  - typename --- 表面其后面的符号是一种数据类型
  - T --- 通用的数据类型，名称可以替换，通常为大写字母

- 作用

  - 为了提高复用性，将类型参数化

- 使用

  ```c++
  #include <iostream>
  using namespace std;
  
  template<typename T>
  
  void my_swap(T &a, T &b)
  {
      T temp = a;
      a = b;
      b = temp;
  }
  
  int main()
  {
      int a=1,b=2;
      int c=3,d=4;
      // 自动类型推断
      my_swap(a,b);
      cout<<a<<" "<<b<<endl; // 2 1
  
      // 显式指定类型
      my_swap<int>(c,d);
      cout<<c<<" "<<d<<endl; // 4 3
  }
  ```

- 注意事项
  - 自动类型推导，必须推导出一致的数据类型T
  - 模板必须要确定出T的数据类型

- 与普通函数相比

  - 类型推断

    - 普通函数调用时可以发生自动类型转换（隐式类型转换） 

    - 模板函数调用时，如果利用自动类型推导，不会发生隐式类型转换 ，如果利用显示指定类型的方式，可以发生隐式类型转换

      ```c++
      //普通函数
      int myAdd01(int a, int b)
      {
      	return a + b;
      }
      
      //函数模板
      template<class T>
      T myAdd02(T a, T b)
      {
      	return a + b;
      }
      
      //使用函数模板时，如果用自动类型推导，不会发生自动类型转换,即隐式类型转换
      void test01()
      {
      	int a = 10;
      	int b = 20;
      	char c = 'c';
      	cout << myAdd01(a, c) << endl; //正确，将char类型的'c'隐式转换为int类型 'c' 对应 ASCII码 99
      	//myAdd02(a, c); // 报错，使用自动类型推导时，不会发生隐式类型转换
      	myAdd02<int>(a, c); //正确，如果用显示指定类型，可以发生隐式类型转换
      }
      
      int main() 
      {
      	test01();
      	system("pause");
      	return 0;
      }
      
      ```

  - 优先级

    - 如果函数模板和普通函数都可以实现，优先调用普通函数 

    - 可以通过空模板参数列表来强制调用函数模板 

    - 函数模板也可以发生重载 

    - 如果函数模板可以产生更好的匹配,优先调用函数模板

      ```c++
      //普通函数与函数模板调用规则
      void myPrint(int a, int b)
      {
      	cout << "调用的普通函数" << endl;
      }
      
      template<typename T>
      void myPrint(T a, T b)
      {
      	cout << "调用的模板" << endl;
      }
      
      template<typename T>
      void myPrint(T a, T b, T c)
      {
      	cout << "调用重载的模板" << endl;
      }
      
      void test01()
      {
      	//1、如果函数模板和普通函数都可以实现，优先调用普通函数
      	// 注意 如果告诉编译器 普通函数是有的，但只是声明没有实现，或者不在当前文件内实现，就会报错找不到
      	int a = 10;
      	int b = 20;
      	myPrint(a, b); //调用普通函数
          
      	//2、可以通过空模板参数列表来强制调用函数模板
      	myPrint<>(a, b); //调用函数模板
          
      	//3、函数模板也可以发生重载
      	int c = 30;
      	myPrint(a, b, c); //调用重载的函数模板
          
      	//4、 如果函数模板可以产生更好的匹配,优先调用函数模板
      	char c1 = 'a';
      	char c2 = 'b';
      	myPrint(c1, c2); //调用函数模板
      }
      
      int main() 
      {
      	test01();
      	return 0;
      }
      
      ```



## 模板类

- 语法

  - template --- 声明创建模板 
  - typename --- 表面其后面的符号是一种数据类型
  - T --- 通用的数据类型，名称可以替换，通常为大写字母

- 作用

  - 建立一个通用类，类中的成员数据类型可以不具体制定，用一个虚拟的类型来代表。

- example

  - 模板类使用只能用显示指定类型方式 

  - 模板类中的模板参数列表可以有默认参数

    ```c++
    #include <string>
    
    //类模板
    template<class NameType, class AgeType>
    class Person
    {
    public:
        Person(NameType name, AgeType age)
    	{
    		this->mName = name;
    		this->mAge = age;
    	}
    	void showPerson()
    	{
    		cout << "name: " << this->mName << " age: " << this->mAge << endl;
    	}
    public:
    	NameType mName;
    	AgeType mAge;
    };
    
    //1、类模板没有自动类型推导的使用方式
    void test01()
    {
    	// Person p("孙悟空", 1000); // 错误 类模板使用时候，不可以用自动类型推导
    	Person <string ,int>p("孙悟空", 1000); //必须使用显示指定类型的方式，使用类模板
    	p.showPerson();
    }
    
    //2、类模板在模板参数列表中可以有默认参数
    void test02()
    {
    	Person <string> p("猪八戒", 999); //类模板中的模板参数列表 可以指定默认参数
    	p.showPerson();
    }
    
    int main() 	
    {
    	test01();
    	test02();
    	system("pause");
    	return 0;
    }
    ```

- 作为参数

  - 指定传入的类型 --- 直接显示对象的数据类型 

  - 参数模板化 --- 将对象中的参数变为模板进行传递 

  - 整个类模板化 --- 将这个对象类型 模板化进行传递

    ```c++
    //1、指定传入的类型
    void printPerson1(Person<string, int> &p)
    
    //2、参数模板化
    template <class T1, class T2>
    void printPerson2(Person<T1, T2>&p)
      
    //3、整个类模板化
    template<class T>
    void printPerson3(T & p)
    ```

- 继承

  - 子实现类继承的父类是一个模板类时，子类在声明的时候，要指定出父类中T的类型。否则编译器无法给子类分配内存

  - 可以用一个子模板类来继承父模板类

    ```c++
    #include <iostream>
    using namespace std;
    
    template<class T>
    class Base
    {
    public:
        T m;
    };
    
    //class Son:public Base //错误，c++编译需要给子类分配内存，必须知道父类中T的类型才可以向下继承
    class Sub1 :public Base<int> //必须指定一个类型
    {
    public:
        Sub1()
        {
            this->m=3;
        }
    };
    void test01()
    {
        Sub1 sub1;
    }
    
    // 沿用父模板类中的泛型
    template<class T>
    class Sub2:public Base<T>
    {
    public:
        Sub2()
        {
            cout << typeid(T).name() << endl;
        }
    };
    
    void test02()
    {
        Sub2<int>sub2;
    }
    
    // 新增一个泛型
    template<class T1, class T2>
    class Sub3 :public Base<T1>
    {
    public:
        Sub3()
        {
            cout << typeid(T1).name() << endl; //i int 类型对应的 mangled name，在某些编译器上显示为 "i"
            cout << typeid(T2).name() << endl; //c char 类型对应的 mangled name，在某些编译器上显示为 "c"
        }
    };
    
    void test03()
    {
        Sub3<int, char> sub3;
    }
    
    int main()
    {
        test01();
        test02();
        test03();
        return 0;
    }
    
    ```

- 函数类内声明，类外实现

  - 要加上模板参数列表

    ```c++
    //类模板中成员函数类外实现
    template<class T1, class T2>
    class Person{
    public:
        //成员函数类内声明
        Person(T1 name, T2 age);
    
        void showPerson();
    
    public:
        T1 m_Name;
        T2 m_Age;
    };
    
    //构造函数 类外实现
    template<class T1, class T2>
    Person<T1, T2>::Person(T1 name, T2 age) 
    {
        this->m_Name = name;
        this->m_Age = age;
    }
    
    //成员函数 类外实现
    template<class T1, class T2>
    void Person<T1, T2>::showPerson() 
    {
        cout << "姓名: " << this->m_Name << " 年龄:" << this->m_Age << endl;
    }
    
    ```

- 配合友元实现全局函数

  - 不加friend的话，会报错

  - 类内、类外实现都可以，推荐类内实现

    ```c++
    #include <string>
    #include "iostream"
    
    using namespace std;
    
    //2、全局函数配合友元 类外实现 - 先做函数模板声明，下方在做函数模板定义，在做友元
    template<class T1, class T2>
    class Person;
    
    //如果声明了函数模板，可以将实现写到后面，否则需要将实现体写到类的前面让编译器提前看到
    //反正使用前要在上文中看到
    //template<class T1, class T2>
    //void printPerson2(Person<T1, T2> &p);
    
    
    
    // 类外实现
    template<class T1, class T2>
    void printPerson2(Person<T1, T2> &p)
    {
        cout << "类外实现 ---- 姓名： " << p.m_Name << " 年龄：" << p.m_Age << endl;
    }
    
    template<class T1, class T2>
    class Person
    {
    public:
        Person(T1 name, T2 age)
        {
            this->m_Name = name;
            this->m_Age = age;
        }
    
    private:
        T1 m_Name;
        T2 m_Age;
    
        //1、全局函数配合友元 类内实现
        friend void printPerson(Person<T1, T2> &p)
        {
            cout << "姓名： " << p.m_Name << " 年龄：" << p.m_Age << endl;
        }
    
        //2、全局函数配合友元 类外实现
        friend void printPerson2<>(Person<T1, T2> &p);
    
    };
    
    
    void test01()
    {
        Person<string, int> p("Tom", 20);
        // 这里使用全局函数要配合友元，否则会找不到这个函数
        printPerson(p);
    }
    
    void test02()
    {
        Person<string, int> p("Jerry", 30);
        printPerson2(p);
    }
    
    int main()
    {
        test01();
        test02();
        return 0;
    }
    ```

- example：通用数组类

  - 要求

    - 可以对内置数据类型以及自定义数据类型的数据进行存储 
    - 将数组中的数据存储到堆区 
    - 构造函数中可以传入数组的容量 
    - 提供对应的拷贝构造函数以及operator=防止浅拷贝问题 
    - 提供尾插法和尾删法对数组中的数据进行增加和删除 
    - 可以通过下标的方式访问数组中的元素 
    - 可以获取数组中当前元素个数和数组的容量

  - myArray.hpp

    ```c++
    #pragma once
    #include <iostream>
    
    using namespace std;
    
    template<class T>
    class MyArray
    {
    public:
    	//构造函数
        MyArray(int capacity)
        {
            this->m_Capacity = capacity;
            this->m_Size = 0;
            pAddress = new T[this->m_Capacity];
        }
    
    	//拷贝构造
        MyArray(const MyArray &arr)
        {
            this->m_Capacity = arr.m_Capacity;
            this->m_Size = arr.m_Size;
            this->pAddress = new T[this->m_Capacity];
            for (int i = 0; i < this->m_Size; i++)
            {
    		//如果T为对象，而且还包含指针，必须需要重载 = 操作符，因为这个等号不是 构造 而是赋值，
    		// 普通类型可以直接= 但是指针类型需要深拷贝
                this->pAddress[i] = arr.pAddress[i];
            }
        }
    
    	//重载= 操作符 防止浅拷贝问题
        MyArray &operator=(const MyArray &myarray)
        {
            if (this->pAddress != NULL)
            {
                delete[] this->pAddress;
                this->m_Capacity = 0;
                this->m_Size = 0;
            }
            this->m_Capacity = myarray.m_Capacity;
            this->m_Size = myarray.m_Size;
            this->pAddress = new T[this->m_Capacity];
            for (int i = 0; i < this->m_Size; i++)
            {
                this->pAddress[i] = myarray[i];
            }
            return *this;
        }
    
    	//重载[] 操作符 arr[0]
        T &operator[](int index)
        {
            return this->pAddress[index]; //不考虑越界，用户自己去处理
        }
    
    	//尾插法
        void Push_back(const T &val)
        {
            if (this->m_Capacity == this->m_Size)
            {
                return;
            }
            this->pAddress[this->m_Size] = val;
            this->m_Size++;
        }
    
    	//尾删法
        void Pop_back()
        {
            if (this->m_Size == 0)
            {
                return;
            }
            this->m_Size--;
        }
    
    	//获取数组容量
        int getCapacity()
        {
            return this->m_Capacity;
        }
    
    	//获取数组大小
        int getSize()
        {
            return this->m_Size;
        }
    
    	//析构
        ~MyArray()
        {
            if (this->pAddress != NULL)
            {
                delete[] this->pAddress;
                this->pAddress = NULL;
                this->m_Capacity = 0;
                this->m_Size = 0;
            }
        }
    
    private:
        T *pAddress; //指向一个堆空间，这个空间存储真正的数据
        int m_Capacity; //容量
        int m_Size; // 大小
    };
    
    ```

  - main.cpp

    ```c++
    #include "myArray.hpp"
    #include <string>
    
    void printIntArray(MyArray<int> &arr)
    {
        for (int i = 0; i < arr.getSize(); i++)
        {
            cout << arr[i] << " ";
        }
        cout << endl;
    }
    
    //测试内置数据类型
    void test01()
    {
        MyArray<int> array1(10);
        for (int i = 0; i < 10; i++)
        {
            array1.Push_back(i);
        }
        cout << "array1打印输出：" << endl;
        printIntArray(array1);
        cout << "array1的大小：" << array1.getSize() << endl;
        cout << "array1的容量：" << array1.getCapacity() << endl;
        cout << "--------------------------" << endl;
        MyArray<int> array2(array1);
        array2.Pop_back();
        cout << "array2打印输出：" << endl;
        printIntArray(array2);
        cout << "array2的大小：" << array2.getSize() << endl;
        cout << "array2的容量：" << array2.getCapacity() << endl;
    }
    
    //测试自定义数据类型
    class Person
    {
    public:
        Person()
        {}
    
        Person(string name, int age)
        {
            this->m_Name = name;
            this->m_Age = age;
        }
    
    public:
        string m_Name;
        int m_Age;
    };
    
    void printPersonArray(MyArray <Person> &personArr)
    {
        for (int i = 0; i < personArr.getSize(); i++)
        {
            cout << "姓名：" << personArr[i].m_Name << " 年龄： " << personArr[i].m_Age << endl;
        }
    }
    
    void test02()
    {
    //创建数组
        MyArray <Person> pArray(10);
        Person p1("孙悟空", 30);
        Person p2("韩信", 20);
        Person p3("妲己", 18);
        Person p4("王昭君", 15);
        Person p5("赵云", 24);
        //插入数据
        pArray.Push_back(p1);
        pArray.Push_back(p2);
        pArray.Push_back(p3);
        pArray.Push_back(p4);
        pArray.Push_back(p5);
        printPersonArray(pArray);
        cout << "pArray的大小：" << pArray.getSize() << endl;
        cout << "pArray的容量：" << pArray.getCapacity() << endl;
    }
    
    int main()
    {
        //test01();
        test02();
        system("pause");
        return 0;
    }
    
    ```

## STL简介

- 起源

  - 长久以来，软件界一直希望建立一种可重复利用的东西 
  - C++的面向对象和泛型编程思想，目的就是复用性的提升 
  - 大多情况下，数据结构和算法都未能有一套标准,导致被迫从事大量重复工作 
  - 为了建立数据结构和算法的一套标准,诞生了STL

- 介绍

  - STL(Standard Template Library,标准模板库) 
  - STL 从广义上分为: 容器(container) 算法(algorithm) 迭代器(iterator) 
  - 容器和算法之间通过迭代器进行无缝连接。 
  - STL 几乎所有的代码都采用了模板类或者模板函数

- 六大组件

  - 容器：各种数据结构，如vector、list、deque、set、map等,用来存放数据。 
  - 算法：各种常用的算法，如sort、find、copy、for_each等 
  - 迭代器：扮演了容器与算法之间的胶合剂。 
  - 仿函数：行为类似函数，可作为算法的某种策略。 
  - 适配器：一种用来修饰容器或者仿函数或迭代器接口的东西。 
  - 空间配置器：负责空间的配置与管理。

- 容器

  - STL容器就是将运用最广泛的一些数据结构实现出来 常用的数据结构：数组, 链表,树, 栈, 队列, 集合, 映射表 等
  - 分类
    - 序列式容器
      - 调值的排序，序列式容器中的每个元素均有固定的位置。
    - 关联式容器
      - 二叉树结构，各元素之间没有 严格的物理上的顺序关系

- 算法

  - 有限的步骤，解决逻辑或数学上的问题，这一门学科我们叫做算法(Algorithms
  - 分类
    - 质变算法
      - 是指运算过程中会更改区间内的元素的内容。例如拷贝，替换，删除等等 
    - 非质变算法
      - 是指运算过程中不会更改区间内的元素内容，例如查找、计数、遍历、寻找极值等

- 迭代器

  - 容器和算法之间粘合剂 提供一种方法，使之能够依序寻访某个容器所含的各个元素，而又无需暴露该容器的内部表示方式。

  -  每个容器都有自己专属的迭代器 

  - 迭代器的主要作用就是让算法能够不用关心底层数据结构，其底层实际就是一个指针，或者是对指针进行了封装

    | 种类            | 功能                                                      | 支持运算                                 |
    | --------------- | --------------------------------------------------------- | ---------------------------------------- |
    | 输入迭代器      | 对数据的只读访问                                          | 只读，支持++、==、！=                    |
    | 输出迭代器      | 对数据的只写访问                                          | 只写，支持++                             |
    | 前向迭代器      | 读写操作，并能向前推进迭代器                              | 读写，支持++、==、！=                    |
    | 双向迭代器      | 读写操作，并能向前和向后操作                              | 读写，支持++、--，                       |
    | 随机访问迭 代器 | 读写操作，可以以跳跃的方式访问任意数据，功能 最强的迭代器 | 读写，支持++、--、[n]、-n、<、 <=、>、>= |

  - 常用的容器中迭代器种类为双向迭代器，和随机访问迭代器

## 容器存取-vector

- 语法

  - 容器： vector 
  - 算法： for_each 
  - 迭代器： vector::iterator

- 使用

  - 存进去什么，通过 * it 取出就是什么

- 存内置类型

  - example

    ```c++
    #include <vector>
    #include <algorithm>
    #include "iostream"
    using  namespace std;
    
    
    void MyPrint(int val)
    {
        cout << val << endl;
    }
    
    void test01()
    {
        //创建vector容器对象，并且通过模板参数指定容器中存放的数据的类型
        vector<int> v;
        //向容器中放数据
        v.push_back(10);
        v.push_back(20);
        v.push_back(30);
        v.push_back(40);
        
        //每一个容器都有自己的迭代器，迭代器是用来遍历容器中的元素
        //v.begin()返回迭代器，这个迭代器指向容器中第一个数据
        //v.end()返回迭代器，这个迭代器指向容器元素的最后一个元素的下一个位置
        //vector<int>::iterator 拿到vector<int>这种容器的迭代器类型
        vector<int>::iterator pBegin = v.begin();
        vector<int>::iterator pEnd = v.end();
        
        //第一种遍历方式：
        while (pBegin != pEnd)
        {
            cout << *pBegin << endl;
            pBegin++;
        }
        
        //第二种遍历方式：
        for (vector<int>::iterator it = v.begin(); it != v.end(); it++)
        {
            cout << *it << endl;
        }
        cout << endl;
        
        //第三种遍历方式：
        //使用STL提供标准遍历算法 头文件 algorithm
        for_each(v.begin(), v.end(), MyPrint);
    }
    
    int main()
    {
        test01();
        system("pause");
        return 0;
    }
    ```

- 存自定义类型

  - example

    ```c++
    #include <vector>
    #include <string>
    
    //自定义数据类型
    class Person
    {
    public:
        Person(string name, int age)
        {
            mName = name;
            mAge = age;
        }
    
    public:
        string mName;
        int mAge;
    };
    
    //存放对象
    void test01()
    {
        vector <Person> v;
    //创建数据
        Person p1("aaa", 10);
        Person p2("bbb", 20);
        Person p3("ccc", 30);
        Person p4("ddd", 40);
        Person p5("eee", 50);
        v.push_back(p1);
        v.push_back(p2);
        v.push_back(p3);
        v.push_back(p4);
        v.push_back(p5);
        for (vector<Person>::iterator it = v.begin(); it != v.end(); it++)
        {
            cout << "Name:" << (*it).mName << " Age:" << (*it).mAge << endl;
        }
    }
    
    //放对象指针
    void test02()
    {
        vector < Person * > v;
    //创建数据
        Person p1("aaa", 10);
        Person p2("bbb", 20);
        Person p3("ccc", 30);
        Person p4("ddd", 40);
        Person p5("eee", 50);
        v.push_back(&p1);
        v.push_back(&p2);
        v.push_back(&p3);
        v.push_back(&p4);
        v.push_back(&p5);
        for (vector<Person *>::iterator it = v.begin(); it != v.end(); it++)
        {
            Person *p = (*it);
            cout << "Name:" << p->mName << " Age:" << (*it)->mAge << endl;
        }
    }
    
    int main()
    {
        test01();
        test02();
        system("pause");
        return 0;
    }
    ```

- 嵌套容器

  - example

    ```c++
    #include <vector>
    
    //容器嵌套容器
    void test01()
    {
        vector <vector<int>> v;
        vector<int> v1;
        vector<int> v2;
        vector<int> v3;
        vector<int> v4;
        for (int i = 0; i < 4; i++)
        {
            v1.push_back(i + 1);
            v2.push_back(i + 2);
            v3.push_back(i + 3);
            v4.push_back(i + 4);
        }
        //将容器元素插入到vector v中
        v.push_back(v1);
        v.push_back(v2);
        v.push_back(v3);
        v.push_back(v4);
        for (vector < vector < int >> ::iterator it = v.begin(); it != v.end();
        it++) {
            for (vector<int>::iterator vit = (*it).begin(); vit != (*it).end(); vit++)
            {
                cout << *vit << " ";
            }
            cout << endl;
        }
    }
    
    int main()
    {
        test01();
        system("pause");
        return 0;
    }
    
    ```

## string

- 本质

  - string是C++风格的字符串，而string本质上是一个类 
  - string和char * 区别
    - char * 是一个指针 
    - string是一个类，类内部封装了char * ，管理这个字符串，是一个char * 型的容器。

  - string 类内部封装了很多成员方法
    - 例如：查找find，拷贝copy，删除delete 替换replace，插入insert 
    - string管理char*所分配的内存，不用担心复制越界和取值越界等，由类内部进行负责

- 构造函数

  - 原型

    - string(); //创建一个空的字符串 例如: string str; 
    - string(const char* s); //使用字符串s初始化 
    - string(const string& str); //使用一个string对象初始化另一个string对象 
    - string(int n, char c); //使用n个字符c初始化

  - example

    ```c++
    #include <string>
    
    //string构造
    void test01()
    {
        string s1; //创建空字符串，调用无参构造函数
        cout << "str1 = " << s1 << endl;
        const char *str = "hello world";
        string s2(str); //把c_string转换成了string
        cout << "str2 = " << s2 << endl;
        string s3(s2); //调用拷贝构造函数
        cout << "str3 = " << s3 << endl;
        string s4(10, 'a');
        cout << "str3 = " << s3 << endl;
    }
    
    int main()
    {
        test01();
        system("pause");
        return 0;
    }
     
    ```

- 赋值

  - 原型

    - string& operator=(const char* s); //char*类型字符串 赋值给当前的字符串
    - string& operator=(const string &s); //把字符串s赋给当前的字符串
    - string& operator=(char c); //字符赋值给当前的字符串
    - string& assign(const char *s); //把字符串s赋给当前的字符串
    - string& assign(const char *s, int n); //把字符串s的前n个字符赋给当前的字符串
    - string& assign(const string &s); //把字符串s赋给当前字符串
    - string& assign(int n, char c); //用n个字符c赋给当前字符串

  - example

    ```c++
    //赋值
    void test01()
    {
        string str1;
        str1 = "hello world";
        cout << "str1 = " << str1 << endl;
        string str2;
        str2 = str1;
        cout << "str2 = " << str2 << endl;
        string str3;
        str3 = 'a';
        cout << "str3 = " << str3 << endl;
        string str4;
        str4.assign("hello c++");
        cout << "str4 = " << str4 << endl;
        string str5;
        str5.assign("hello c++", 5);
        cout << "str5 = " << str5 << endl;
        string str6;
        str6.assign(str5);
        cout << "str6 = " << str6 << endl;
        string str7;
        str7.assign(5, 'x');
        cout << "str7 = " << str7 << endl;
    }
    
    int main()
    {
        test01();
        system("pause");
        return 0;
    }
    
    ```

- 拼接

  - 原型

    - string& operator+=(const char* str); //重载+=操作符
    - string& operator+=(const char c); //重载+=操作符
    - string& operator+=(const string& str); //重载+=操作符
    - string& append(const char *s); //把字符串s连接到当前字符串结尾
    - string& append(const char *s, int n); //把字符串s的前n个字符连接到当前字符串结尾
    - string& append(const string &s); //同operator+=(const string& str)
    - string& append(const string &s, int pos, int n); //字符串s中从pos开始的n个字符连接到字符串结尾

  - example

    ```c++
    //字符串拼接
    void test01()
    {
        string str1 = "我";
        str1 += "爱玩游戏";
        cout << "str1 = " << str1 << endl;
        str1 += ':';
        cout << "str1 = " << str1 << endl;
        string str2 = "LOL DNF";
        str1 += str2;
        cout << "str1 = " << str1 << endl;
        string str3 = "I";
        str3.append(" love ");
        str3.append("game abcde", 4);
        
        //str3.append(str2);
        str3.append(str2, 4, 3); // 从下标4位置开始 ，截取3个字符，拼接到字符串末尾
        cout << "str3 = " << str3 << endl;
    }
    
    int main()
    {
        test01();
        system("pause");
        return 0;
    }
    
    ```

- 查找、替换

  - 原型

    - int find(const string& str, int pos = 0) const; //查找str第一次出现位置,从pos开始查找
    - int find(const char* s, int pos = 0) const; //查找s第一次出现位置,从pos开始查找
    - int find(const char* s, int pos, int n) const; //从pos位置查找s的前n个字符第一次位置
    - int find(const char c, int pos = 0) const; //查找字符c第一次出现位置
    - int rfind(const string& str, int pos = npos) const; //查找str最后一次位置,从pos开始查找
    - int rfind(const char* s, int pos = npos) const; //查找s最后一次出现位置,从pos开始查找
    - int rfind(const char* s, int pos, int n) const; //从pos查找s的前n个字符最后一次位置
    - int rfind(const char c, int pos = 0) const; //查找字符c最后一次出现位置
    - string& replace(int pos, int n, const string& str); //替换从pos开始n个字符为字符串str
    - string& replace(int pos, int n,const char* s); //替换从pos开始的n个字符为字符串s

  - example

    - find查找是从左往后，rfind从右往左 
    - find找到字符串后返回查找的第一个字符位置，找不到返回-1 
    - replace在替换时，要指定从哪个位置起，多少个字符，替换成什么样的字符串

    ```c++
    //查找和替换
    void test01()
    {
        //查找
        string str1 = "abcdefgde";
        int pos = str1.find("de");
        if (pos == -1)
        {
            cout << "未找到" << endl;
        } else
        {
            cout << "pos = " << pos << endl;
        }
        pos = str1.rfind("de");
        cout << "pos = " << pos << endl;
    }
    
    void test02()
    {
        //替换
        string str1 = "abcdefgde";
        str1.replace(1, 3, "1111");
        cout << "str1 = " << str1 << endl;
    }
    
    int main()
    {
        test01();
        test02();
        system("pause");
        return 0;
    }
    ```

- 比较

  - 字符串比较是按字符的ASCII码进行对比

    - = 返回 0 	> 返回 1 	< 返回 -1

  - 原型

    - int compare(const string &s) const; //与字符串s比较
    - int compare(const char *s) const; //与字符串s比较

  - example

    ```c++
    //字符串比较
    void test01()
    {
        string s1 = "hello";
        string s2 = "aello";
        int ret = s1.compare(s2);
        if (ret == 0)
        {
            cout << "s1 等于 s2" << endl;
        } else if (ret > 0)
        {
            cout << "s1 大于 s2" << endl;
        } else
        {
            cout << "s1 小于 s2" << endl;
        }
    }
    
    int main()
    {
        test01();
        system("pause");
        return 0;
    }
    ```

- 访问、修改单个字符

  - 原型

    - char& operator[](int n); //通过[]方式取字符
    - char& at(int n); //通过at方法获取字符

  - example

    ```c++
    void test01()
    {
        string str = "hello world";
        for (int i = 0; i < str.size(); i++)
        {
            cout << str[i] << " ";
        }
        cout << endl;
        for (int i = 0; i < str.size(); i++)
        {
            cout << str.at(i) << " ";
        }
        cout << endl;
        
        //可以修改字符
        str[0] = 'x';
        str.at(1) = 'x';
        cout << str << endl;
    }
    
    int main()
    {
        test01();
        system("pause");
        return 0;
    }
    ```

- 插入、删除

  - 原型

    - string& insert(int pos, const char* s); //插入字符串
    - string& insert(int pos, const string& str); //插入字符串
    - string& insert(int pos, int n, char c); //在指定位置插入n个字符c
    - string& erase(int pos, int n = npos); //删除从Pos开始的n个字符

  - example

    ```c++
    //字符串插入和删除
    void test01()
    {
        string str = "hello";
        str.insert(1, "111");
        cout << str << endl;
        str.erase(1, 3); //从1号位置开始3个字符
        cout << str << endl;
    }
    
    int main()
    {
        test01();
        system("pause");
        return 0;
    }
    
    ```

- 字串

  - 原型

    - string substr(int pos = 0, int n = npos) const; //返回由pos开始的n个字符组成的字符串

  - example

    ```c++
    //子串
    void test01()
    {
        string str = "abcdefg";
        string subStr = str.substr(1, 3);
        cout << "subStr = " << subStr << endl;
        string email = "hello@sina.com";
        int pos = email.find("@");
        string username = email.substr(0, pos);
        cout << "username: " << username << endl;
    }
    
    int main()
    {
        test01();
        system("pause");
        return 0;
    }
    ```

## vector

- 简介

  - 数组是静态空间，而vector可以动态扩展

  - vector容器的迭代器是支持随机访问的迭代器

  - 动态扩展：并不是在原空间之后续接新空间，而是找更大的内存空间，然后将原数据拷贝新空间，释放原空间

  - capacity的代码在vs和g++下分别运行会发现，vs下capacity是按1.5倍增长的，g++是按2倍增长的。
  这个问题经常会考察，不要固化的认为，顺序表增容都是2倍，具体增长多少是根据具体的需求定义
    的。vs是PJ版本STL，g++是SGI版本STL
    
    ![2024-03-20_104714](img\2024-03-20_104714.png)

- 构造函数

  - 原型

    - vector<T> v; //采用模板实现类实现，默认构造函数
    - vector(v.begin(), v.end()); //将v[begin(), end())区间中的元素拷贝给本身。
    - vector(n, elem); //构造函数将n个elem拷贝给本身。
    - vector(const vector &vec); //拷贝构造函数。

  - example

    ```c++
    #include <vector>
    
    void printVector(vector<int> &v)
    {
        for (vector<int>::iterator it = v.begin(); it != v.end(); it++)
        {
            cout << *it << " ";
        }
        cout << endl;
    }
    
    void test01()
    {
        vector<int> v1; //无参构造
        for (int i = 0; i < 10; i++)
        {
            v1.push_back(i);
        }
        printVector(v1);
        vector<int> v2(v1.begin(), v1.end());
        printVector(v2);
        vector<int> v3(10, 100);
        printVector(v3);
        vector<int> v4(v3);
        printVector(v4);
    }
    
    int main()
    {
        test01();
        system("pause");
        return 0;
    }
     
    ```

- 赋值

  - 原型

    - vector& operator=(const vector &vec); //重载等号操作符
    - assign(beg, end); //将[beg, end)区间中的数据拷贝赋值给当前vector。 
    - assign(n, elem); //将n个elem拷贝赋值给当前vector。

  - example

    ```c++
    #include <vector>
    
    void printVector(vector<int> &v)
    {
        for (vector<int>::iterator it = v.begin(); it != v.end(); it++)
        {
            cout << *it << " ";
        }
        cout << endl;
    }
    
    //赋值操作
    void test01()
    {
        vector<int> v1; //无参构造
        for (int i = 0; i < 10; i++)
        {
            v1.push_back(i);
        }
        printVector(v1);
        vector<int> v2;
        v2 = v1;
        printVector(v2);
        vector<int> v3;
        v3.assign(v1.begin(), v1.end());
        printVector(v3);
        vector<int> v4;
        v4.assign(10, 100);
        printVector(v4);
    }
    
    int main()
    {
        test01();
        system("pause");
        return 0;
    }
    
    ```

- 容量

  - 原型

    - empty(); //判断容器是否为空
    - capacity(); //容器的容量
    - size(); //返回容器中元素的个数
    - resize(int num); //重新指定容器的长度为num，若容器变长，则以默认值填充新位置。如果容器变短，则末尾超出容器长度的元素被删除。
    - resize(int num, elem); //重新指定容器的长度为num，若容器变长，则以elem值填充新位置。如果容器变短，则末尾超出容器长度的元素被删除

  - example

    ```c++
    #include <vector>
    
    void printVector(vector<int> &v)
    {
        for (vector<int>::iterator it = v.begin(); it != v.end(); it++)
        {
            cout << *it << " ";
        }
        cout << endl;
    }
    
    void test01()
    {
        vector<int> v1;
        for (int i = 0; i < 10; i++)
        {
            v1.push_back(i);
        }
        printVector(v1);
        if (v1.empty())
        {
            cout << "v1为空" << endl;
        } else
        {
            cout << "v1不为空" << endl;
            cout << "v1的容量 = " << v1.capacity() << endl;
            cout << "v1的大小 = " << v1.size() << endl;
        }
        
        //resize 重新指定大小 ，若指定的更大，默认用0填充新位置，可以利用重载版本替换默认填充
        v1.resize(15, 10);
        printVector(v1);
        
        //resize 重新指定大小 ，若指定的更小，超出部分元素被删除
        v1.resize(5);
        printVector(v1);
    }
    
    int main()
    {
        test01();
        system("pause");
        return 0;
    }
    
    ```

- 插入、删除

  - 原型
    - push_back(ele); //尾部插入元素ele
    - pop_back(); //删除最后一个元素
    - insert(const_iterator pos, ele); //迭代器指向位置pos插入元素ele
    - insert(const_iterator pos, int count,ele); //迭代器指向位置pos插入count个元素ele
    - erase(const_iterator pos); //删除迭代器指向的元素
    - erase(const_iterator start, const_iterator end); //删除迭代器从start到end之间的元素
    - clear(); //删除容器中所有元素

  - example

    ```c++
    #include <vector>
    
    void printVector(vector<int> &v)
    {
        for (vector<int>::iterator it = v.begin(); it != v.end(); it++)
        {
            cout << *it << " ";
        }
        cout << endl;
    }
    
    //插入和删除
    void test01()
    {
        vector<int> v1;
        
        //尾插
        v1.push_back(10);
        v1.push_back(20);
        v1.push_back(30);
        v1.push_back(40);
        v1.push_back(50);
        printVector(v1);
        
        //尾删
        v1.pop_back();
        printVector(v1);
        
        //插入
        v1.insert(v1.begin(), 100);
        printVector(v1);
        v1.insert(v1.begin(), 2, 1000);
        printVector(v1);
    
        //删除
        v1.erase(v1.begin());
        printVector(v1);
        
        //清空
        v1.erase(v1.begin(), v1.end());
        v1.clear();
        printVector(v1);
    }
    
    int main()
    {
        test01();
        system("pause");
        return 0;
    }
    
    ```

- 访问、修改

  - 除了迭代器，以下这些也可以

    - at(int idx); //返回索引idx所指的数据
    - operator[]; //返回索引idx所指的数据
    - front(); //返回容器中第一个数据元素
    - back(); //返回容器中最后一个数据元素

  - example

    - 可以通过at()   []   直接修改对应下标的元素。

    ```c++
    #include <vector>
    #include "iostream"
    
    using namespace std;
    
    void test01()
    {
        vector<int> v1;
        for (int i = 0; i < 10; i++)
        {
            v1.push_back(i);
        }
    
        v1.at(3)=101;
        v1[5]=105;
    
        for (int i = 0; i < v1.size(); i++)
        {
            cout << v1[i] << " "; // 0 1 2 101 4 105 6 7 8 9
        }
        cout << endl;
        for (int i = 0; i < v1.size(); i++)
        {
            cout << v1.at(i) << " ";
        }
        cout << endl;
        cout << "v1的第一个元素为： " << v1.front() << endl;
        cout << "v1的最后一个元素为： " << v1.back() << endl;
    }
    
    int main()
    {
        test01();
    
        return 0;
    }
    ```

- 互换

  - 原型

    - swap(vec); // 将vec与本身的元素互换

  - example

    - 可用于两个容器互换元素
    - 可用于收缩容量capacity

    ```c++
    #include <vector>
    #include "iostream"
    using namespace std;
    
    void printVector(vector<int> &v)
    {
        for (vector<int>::iterator it = v.begin(); it != v.end(); it++)
        {
            cout << *it << " ";
        }
        cout << endl;
    }
    
    void test01()
    {
        vector<int> v1;
        for (int i = 0; i < 10; i++)
        {
            v1.push_back(i);
        }
        printVector(v1);
        vector<int> v2;
        for (int i = 10; i > 0; i--)
        {
            v2.push_back(i);
        }
        printVector(v2);
    	
        //互换容器
        cout << "互换后" << endl;
        v1.swap(v2);
        printVector(v1);
        printVector(v2);
    }
    
    void test02()
    {
        vector<int> v;
        for (int i = 0; i < 100000; i++)
        {
            v.push_back(i);
        }
        cout << "v的容量为：" << v.capacity() << endl; //131072
        cout << "v的大小为：" << v.size() << endl; //100000
    
        v.resize(3);
        cout << "v的容量为：" << v.capacity() << endl; // 131072
        cout << "v的大小为：" << v.size() << endl; // 3
    
        //收缩内存
        vector<int>(v).swap(v); //匿名对象
        cout << "v的容量为：" << v.capacity() << endl; // 3
        cout << "v的大小为：" << v.size() << endl; // 3
    }
    
    int main()
    {
        // test01();
        test02();
        return 0;
    }
    ```

- 预留空间

  - 原型

    - reserve(int len); //容器预留len个元素长度，预留位置不初始化，元素不可访问。

  - example

    - 设置初始capacity，后面设置更小没有生效

    ```c++
    #include <vector>
    #include "iostream"
    
    using namespace std;
    
    void test01()
    {
        vector<int> v;
    
        //预留空间
        v.reserve(100004);
        cout<<v.capacity()<<endl; // 100004
        cout<<v.size()<<endl; // 0
    
        int num = 0;
        int *p = NULL;
        for (int i = 0; i < 100000; i++)
        {
            v.push_back(i);
            if (p != &v[0])
            {
                p = &v[0];
                num++;
            }
        }
        cout << "num:" << num << endl;
    
        // 后面设置更小没有生效
        v.reserve(100003);
        cout<<v.capacity()<<endl; // 100004
        cout<<v.size()<<endl;  // 100000
    
    
        // 后面设置的没有生效
        v.reserve(100005);
        cout<<v.capacity()<<endl; // 100005
        cout<<v.size()<<endl;  // 100000
    }
    
    int main()
    {
        test01();
    
        return 0;
    }
    
    ```

## deque

- 简介

  - double-ended queue

  - 双端队列，可以对头尾端进行插入删除操作

  - deque容器的迭代器支持随机访问的

  - 与vector区别

    - 头部插入删除
      - vector对于头部的插入删除效率低，数据量越大，效率越低 
      - deque相对而言，对头部的插入删除速度回比vector快 
    - 访问元素
      - vector访问元素时的速度会比deque快,这和两者内部实现有关

  - 总体结构

    - 如图

      ![2024-03-20_161353](img\2024-03-20_161353.png)

  - 内部结构

    - deque内部有个中控器，维护每段缓冲区中的内容，缓冲区中存放真实数据 

    - 中控器维护的是每个缓冲区的地址，使得使用deque时像一片连续的内存空间

      ![2024-03-20_161945](img\2024-03-20_161945.png)

- 构造函数

  - 原型

    - deque<T> deqT; //默认构造形式
    - deque(beg, end); //构造函数将[beg, end)区间中的元素拷贝给本身。
    - deque(n, elem); //构造函数将n个elem拷贝给本身。
    - deque(const deque &deq); //拷贝构造函数

  - example

    ```c++
    #include <deque>
    
    void printDeque(const deque<int> &d)
    {
        for (deque<int>::const_iterator it = d.begin(); it != d.end(); it++)
        {
            cout << *it << " ";
        }
        cout << endl;
    }
    
    //deque构造
    void test01()
    {
        deque<int> d1; //无参构造函数
        for (int i = 0; i < 10; i++)
        {
            d1.push_back(i);
        }
        printDeque(d1);
        deque<int> d2(d1.begin(), d1.end());
        printDeque(d2);
        deque<int> d3(10, 100);
        printDeque(d3);
        deque<int> d4 = d3;
        printDeque(d4);
    }
    
    int main()
    {
        test01();
        system("pause");
        return 0;
    }
    
    ```

- 赋值

  - 原型

    - deque& operator=(const deque &deq); //重载等号操作符
    - assign(beg, end); //将[beg, end)区间中的数据拷贝赋值给本身。
    - assign(n, elem); //将n个elem拷贝赋值给本身。

  - example

    - 这里的 = 是深拷贝

    ```c++
    #include <deque>
    #include "iostream"
    
    using namespace std;
    
    void printDeque(const deque<int> &d)
    {
        for (deque<int>::const_iterator it = d.begin(); it != d.end(); it++)
        {
            cout << *it << " ";
        }
        cout << endl;
    }
    
    //赋值操作
    void test01()
    {
        deque<int> d1;
        for (int i = 0; i < 10; i++)
        {
            d1.push_back(i);
        }
        printDeque(d1); // 0 1 2 3 4 5 6 7 8 9
        deque<int> d2;
        d2 = d1;
    
    
        // 这个等于号是深拷贝
        d2[1]=101;
        printDeque(d2); // 0 101 2 3 4 5 6 7 8 9
        printDeque(d1); // 0 1 2 3 4 5 6 7 8 9
    
        deque<int> d3;
        d3.assign(d1.begin(), d1.end());
        printDeque(d3);
        deque<int> d4;
        d4.assign(10, 100);
        printDeque(d4);
    }
    
    int main()
    {
        test01();
        system("pause");
        return 0;
    }
    ```

- 容量

  - 原型

    - deque.empty(); //判断容器是否为空
    - deque.size(); //返回容器中元素的个数
    - deque.resize(num); //重新指定容器的长度为num,若容器变长，则以默认值填充新位置。 //如果容器变短，则末尾超出容器长度的元素被删除。
    - deque.resize(num, elem); //重新指定容器的长度为num,若容器变长，则以elem值填充新位置。 //如果容器变短，则末尾超出容器长度的元素被删除。

  - example

    - deque好像没有capacity()

    ```c++
    #include <deque>
    #include "iostream"
    
    using namespace std;
    
    void printDeque(const deque<int> &d)
    {
        for (deque<int>::const_iterator it = d.begin(); it != d.end(); it++)
        {
            cout << *it << " ";
        }
        cout << endl;
    }
    
    //大小操作
    void test01()
    {
        deque<int> d1;
        for (int i = 0; i < 10; i++)
        {
            d1.push_back(i);
        }
        printDeque(d1);
    
        //判断容器是否为空
        if (d1.empty())
        {
            cout << "d1为空!" << endl;
        } else
        {
            cout << "d1不为空!" << endl;
            //统计大小
            cout << "d1的大小为：" << d1.size() << endl;
        }
    
        //重新指定大小
        d1.resize(15, 1); //0 1 2 3 4 5 6 7 8 9 1 1 1 1 1
        printDeque(d1);
        d1.resize(5);
        printDeque(d1); // 0 1 2 3 4
        cout << "d1的大小为：" << d1.size() << endl; //5
    }
    
    int main()
    {
        test01();
        return 0;
    }
    ```

- 插入、删除

  - 原型

    - push_back(elem); //在容器尾部添加一个数据 
    - push_front(elem); //在容器头部插入一个数据 
    - pop_back(); //删除容器最后一个数据 
    - pop_front(); //删除容器第一个数据
    - insert(pos,elem); //在pos位置插入一个elem元素的拷贝，返回新数据的位置。 
    - insert(pos,n,elem); //在pos位置插入n个elem数据，无返回值。 
    - insert(pos,beg,end); //在pos位置插入[beg,end)区间的数据，无返回值。 
    - clear(); //清空容器的所有数据 
    - erase(beg,end); //删除[beg,end)区间的数据，返回下一个数据的位置。 
    - erase(pos); //删除pos位置的数据，返回下一个数据的位置。

  - example

    ```c++
    #include <deque>
    
    void printDeque(const deque<int> &d)
    {
        for (deque<int>::const_iterator it = d.begin(); it != d.end(); it++)
        {
            cout << *it << " ";
        }
        cout << endl;
    }
    
    //两端操作
    void test01()
    {
        deque<int> d;
        
        //尾插
        d.push_back(10);
        d.push_back(20);
        
        //头插
        d.push_front(100);
        d.push_front(200);
        printDeque(d);
        
        //尾删
        d.pop_back();
        
        //头删
        d.pop_front();
        printDeque(d);
    }
    
    //插入
    void test02()
    {
        deque<int> d;
        d.push_back(10);
        d.push_back(20);
        d.push_front(100);
        d.push_front(200);
        printDeque(d);
        d.insert(d.begin(), 1000);
        printDeque(d);
        d.insert(d.begin(), 2, 10000);
        printDeque(d);
        deque<int> d2;
        d2.push_back(1);
        d2.push_back(2);
        d2.push_back(3);
        d.insert(d.begin(), d2.begin(), d2.end());
        printDeque(d);
    }
    
    //删除
    void test03()
    {
        deque<int> d;
        d.push_back(10);
        d.push_back(20);
        d.push_front(100);
        d.push_front(200);
        printDeque(d);
        d.erase(d.begin());
        printDeque(d);
        d.erase(d.begin(), d.end());
        d.clear();
        printDeque(d);
    }
    
    int main()
    {
        //test01();
        //test02();
        test03();
        system("pause");
        return 0;
    }
    
    ```

- 访问、修改

  - 原型

    - at(int idx); //返回索引idx所指的数据 
    - operator[]; //返回索引idx所指的数据 
    - front(); //返回容器中第一个数据元素 
    - back(); //返回容器中最后一个数据元素

  - example

    ```c++
    #include <deque>
    
    void printDeque(const deque<int> &d)
    {
        for (deque<int>::const_iterator it = d.begin(); it != d.end(); it++)
        {
            cout << *it << " ";
        }
        cout << endl;
    }
    
    //数据存取
    void test01()
    {
        deque<int> d;
        d.push_back(10);
        d.push_back(20);
        d.push_front(100);
        d.push_front(200);
        for (int i = 0; i < d.size(); i++)
        {
            cout << d[i] << " ";
        }
        cout << endl;
        for (int i = 0; i < d.size(); i++)
        {
            cout << d.at(i) << " ";
        }
        cout << endl;
        cout << "front:" << d.front() << endl;
        cout << "back:" << d.back() << endl;
    }
    
    int main()
    {
        test01();
        system("pause");
        return 0;
    }
    ```

- 排序

  - 原型

    - sort(iterator beg, iterator end) //对beg和end区间内元素进行排序   algorithm中的

  - example

    ```c++
    #include <deque>
    #include <algorithm>
    
    void printDeque(const deque<int> &d)
    {
        for (deque<int>::const_iterator it = d.begin(); it != d.end(); it++)
        {
            cout << *it << " ";
        }
        cout << endl;
    }
    
    void test01()
    {
        deque<int> d;
        d.push_back(10);
        d.push_back(20);
        d.push_front(100);
        d.push_front(200);
        printDeque(d);
        sort(d.begin(), d.end());
        printDeque(d);
    }
    
    int main()
    {
        test01();
        system("pause");
        return 0;
    }
    
    ```

    

## queue

- 简介

  - 先进先出(First In First Out)

- 常用接口

  - 构造函数

    - queue<T> que; //queue采用模板类实现，queue对象的默认构造形式
    - queue(const queue &que); //拷贝构造函数

  - 赋值操作

    - queue& operator=(const queue &que); //重载等号操作符

  - 数据存取

    - push(elem); //往队尾添加元素
    - pop(); //从队头移除第一个元素
    - back(); //返回最后一个元素
    - front(); //返回第一个元素

  - 大小操作

    - empty(); //判断堆栈是否为空
    - size(); //返回栈的大小

  - example

    ```c++
    #include <queue>
    #include <string>
    
    class Person
    {
    public:
        Person(string name, int age)
        {
            this->m_Name = name;
            this->m_Age = age;
        }
    
        string m_Name;
        int m_Age;
    };
    
    void test01()
    {
        //创建队列
        queue <Person> q;
        
        //准备数据
        Person p1("唐僧", 30);
        Person p2("孙悟空", 1000);
        Person p3("猪八戒", 900);
        Person p4("沙僧", 800);
        
        //向队列中添加元素 入队操作
        q.push(p1);
        q.push(p2);
        q.push(p3);
        q.push(p4);
        
        //队列不提供迭代器，更不支持随机访问
        while (!q.empty())
        {
            //输出队头元素
            cout << "队头元素-- 姓名： " << q.front().m_Name
                 << " 年龄： " << q.front().m_Age << endl;
            cout << "队尾元素-- 姓名： " << q.back().m_Name
                 << " 年龄： " << q.back().m_Age << endl;
            cout << endl;
            
            //弹出队头元素
            q.pop();
        }
        cout << "队列大小为：" << q.size() << endl;
    }
    
    int main()
    {
        test01();
        system("pause");
        return 0;
    }
    
    ```

    

## stack

- 简介

  - stack是一种先进后出(First In Last Out,FILO)的数据结构，它只有一个出口
  - 栈中只有顶端的元素才可以被外界使用，因此栈不允许有遍历行为 
  - 栈中进入数据称为 --- 入栈 push 
  - 栈中弹出数据称为 --- 出栈 pop

- 常用接口

  - 构造函数

    - stack<T> stk; //stack采用模板类实现， stack对象的默认构造形式
    - stack(const stack &stk); //拷贝构造函数

  - 赋值操作

    - stack& operator=(const stack &stk); //重载等号操作符

  - 数据存取

    - push(elem); //向栈顶添加元素
    - pop(); //从栈顶移除第一个元素
    - top(); //返回栈顶元素

  - 大小操作

    - empty(); //判断堆栈是否为空
    - size(); //返回栈的大小

  - example

    ```c++
    #include <stack>
    
    //栈容器常用接口
    void test01()
    {
        //创建栈容器 栈容器必须符合先进后出
        stack<int> s;
    
        //向栈中添加元素，叫做 压栈 入栈
        s.push(10);
        s.push(20);
        s.push(30);
        while (!s.empty())
        {
            //输出栈顶元素
            cout << "栈顶元素为： " << s.top() << endl;
            
            //弹出栈顶元素
            s.pop();
        }
        cout << "栈的大小为：" << s.size() << endl;
    }
    
    int main()
    {
        test01();
        system("pause");
        return 0;
    }
    ```



## list

- 简介

  - 链表是一种物理存储单元上非连续的存储结构，数据元素的逻辑顺序是通过链表中的指针链接实现的

  - 结点中包含数据和指针

  - STL中的链表是一个双向循环链表

  - 由于链表的存储方式并不是连续的内存空间，因此链表list中的迭代器只支持前移和后移，属于双向迭代器

    ![2024-03-20_173929](img\2024-03-20_173929.png)

- 优缺点

  - 优点
    - 采用动态存储分配，不会造成内存浪费和溢出 
    - 链表执行插入和删除操作十分方便，修改指针即可，不需要移动大量元素 
    - 插入操作和删除操作都不会造成原有list迭代器的失效，这在vector是不成立的
      - 原有迭代器不会失效，但是访问的都是最新的元素？
  - 缺点
    - 链表灵活，但是空间(指针域) 和 时间（遍历）额外耗费较大

- 构造函数

  - 原型

    - list<T> lst; //list采用采用模板类实现,对象的默认构造形式：
    - list(beg,end); //构造函数将[beg, end)区间中的元素拷贝给本身。
    - list(n,elem); //构造函数将n个elem拷贝给本身。
    - list(const list &lst); //拷贝构造函数。

  - example

    ```c++
    #include <list>
    
    void printList(const list<int> &L)
    {
        for (list<int>::const_iterator it = L.begin(); it != L.end(); it++)
        {
            cout << *it << " ";
        }
        cout << endl;
    }
    
    void test01()
    {
        list<int> L1;
        L1.push_back(10);
        L1.push_back(20);
        L1.push_back(30);
        L1.push_back(40);
        printList(L1);
        list<int> L2(L1.begin(), L1.end());
        printList(L2);
        list<int> L3(L2);
        printList(L3);
        list<int> L4(10, 1000);
        printList(L4);
    }
    
    int main()
    {
        test01();
        system("pause");
        return 0;
    }
    ```

- 赋值、交换

  - 原型

    - assign(beg, end); //将[beg, end)区间中的数据拷贝赋值给本身。 
    - assign(n, elem); //将n个elem拷贝赋值给本身。 
    - list& operator=(const list &lst); //重载等号操作符 
    - swap(lst); //将lst与本身的元素互换。

  - example

    ```c++
    #include <list>
    
    void printList(const list<int> &L)
    {
        for (list<int>::const_iterator it = L.begin(); it != L.end(); it++)
        {
            cout << *it << " ";
        }
        cout << endl;
    }
    
    //赋值和交换
    void test01()
    {
        list<int> L1;
        L1.push_back(10);
        L1.push_back(20);
        L1.push_back(30);
        L1.push_back(40);
        printList(L1);
        
        //赋值
        list<int> L2;
        L2 = L1;
        printList(L2);
        list<int> L3;
        L3.assign(L2.begin(), L2.end());
        printList(L3);
        list<int> L4;
        L4.assign(10, 100);
        printList(L4);
    }
    
    //交换
    void test02()
    {
        list<int> L1;
        L1.push_back(10);
        L1.push_back(20);
        L1.push_back(30);
        L1.push_back(40);
        list<int> L2;
        L2.assign(10, 100);
        cout << "交换前： " << endl;
        printList(L1);
        printList(L2);
        cout << endl;
        L1.swap(L2);
        cout << "交换后： " << endl;
        printList(L1);
        printList(L2);
    }
    
    int main()
    {
        //test01();
        test02();
        system("pause");
        return 0;
    }
    ```

- 容量

  - 原型

    - size(); //返回容器中元素的个数 
    - empty(); //判断容器是否为空 
    - resize(num); //重新指定容器的长度为num，若容器变长，则以默认值填充新位置。 //如果容器变短，则末尾超出容器长度的元素被删除。 
    - resize(num, elem); //重新指定容器的长度为num，若容器变长，则以elem值填充新位置。 //如果容器变短，则末尾超出容器长度的元素被删除。

  - example

    ```c++
    #include <list>
    
    void printList(const list<int> &L)
    {
        for (list<int>::const_iterator it = L.begin(); it != L.end(); it++)
        {
            cout << *it << " ";
        }
        cout << endl;
    }
    
    //大小操作
    void test01()
    {
        list<int> L1;
        L1.push_back(10);
        L1.push_back(20);
        L1.push_back(30);
        L1.push_back(40);
        if (L1.empty())
        {
            cout << "L1为空" << endl;
        } else
        {
            cout << "L1不为空" << endl;
            cout << "L1的大小为： " << L1.size() << endl;
        }
        
        //重新指定大小
        L1.resize(10);
        printList(L1);
        L1.resize(2);
        printList(L1);
    }
    
    int main()
    {
        test01();
        system("pause");
        return 0;
    }
    ```

- 插入、删除

  - 原型

    - push_back(elem);//在容器尾部加入一个元素
    - pop_back();//删除容器中最后一个元素
    - push_front(elem);//在容器开头插入一个元素
    - pop_front();//从容器开头移除第一个元素
    - insert(pos,elem);//在pos位置插elem元素的拷贝，返回新数据的位置。
    - insert(pos,n,elem);//在pos位置插入n个elem数据，无返回值。
    - insert(pos,beg,end);//在pos位置插入[beg,end)区间的数据，无返回值。
    - clear();//移除容器的所有数据
    - erase(beg,end);//删除[beg,end)区间的数据，返回下一个数据的位置。
    - erase(pos);//删除pos位置的数据，返回下一个数据的位置。
    - remove(elem);//删除容器中所有与elem值匹配的元素。

  - example

    ```c++
    #include <list>
    
    void printList(const list<int> &L)
    {
        for (list<int>::const_iterator it = L.begin(); it != L.end(); it++)
        {
            cout << *it << " ";
        }
        cout << endl;
    }
    
    //插入和删除
    void test01()
    {
        list<int> L;
        
        //尾插
        L.push_back(10);
        L.push_back(20);
        L.push_back(30);
        
        //头插
        L.push_front(100);
        L.push_front(200);
        L.push_front(300);
        printList(L);
        
        //尾删
        L.pop_back();
        printList(L);
        
        //头删
        L.pop_front();
        printList(L);
        
        //插入
        list<int>::iterator it = L.begin();
        L.insert(++it, 1000);
        printList(L);
        
        //删除
        it = L.begin();
        L.erase(++it);
        printList(L);
        
        //移除
        L.push_back(10000);
        L.push_back(10000);
        L.push_back(10000);
        printList(L);
        L.remove(10000);
        printList(L);
        
        //清空
        L.clear();
        printList(L);
    }
    
    int main()
    {
        test01();
        system("pause");
        return 0;
    }
    ```

- 端点访问

  - 原型

    - front(); //返回第一个元素。 
    - back(); //返回最后一个元素。

  - example

    ```c++
    #include <list>
    
    //数据存取
    void test01()
    {
        list<int> L1;
        L1.push_back(10);
        L1.push_back(20);
        L1.push_back(30);
        L1.push_back(40);
        
        //cout << L1.at(0) << endl;//错误 不支持at访问数据
        
        //cout << L1[0] << endl; //错误 不支持[]方式访问数据
        
        cout << "第一个元素为： " << L1.front() << endl;
        cout << "最后一个元素为： " << L1.back() << endl;
        
        //list容器的迭代器是双向迭代器，不支持随机访问
        list<int>::iterator it = L1.begin();
        //it = it + 1;//错误，不可以跳跃访问，即使是+1
    }
    
    int main()
    {
        test01();
        system("pause");
        return 0;
    }
    
    ```

- 反转、排序

  - 原型

    - reverse(); //反转链表 
    - sort(); //链表排序

  - example

    ```c++
    void printList(const list<int> &L)
    {
        for (list<int>::const_iterator it = L.begin(); it != L.end(); it++)
        {
            cout << *it << " ";
        }
        cout << endl;
    }
    
    bool myCompare(int val1, int val2)
    {
        return val1 > val2;
    }
    
    //反转和排序
    void test01()
    {
        list<int> L;
        L.push_back(90);
        L.push_back(30);
        L.push_back(20);
        L.push_back(70);
        printList(L);
    
        //反转容器的元素
        L.reverse();
        printList(L);
    
        //排序
        L.sort(); //默认的排序规则 从小到大
        printList(L);
        L.sort(myCompare); //指定规则，从大到小
        printList(L);
    }
    
    int main()
    {
        test01();
        system("pause");
        return 0;
    }
    ```

- 自定义排序

  - 定义一个排序器

  - example

    ```c++
    #include <list>
    #include <string>
    
    class Person
    {
    public:
        Person(string name, int age, int height)
        {
            m_Name = name;
            m_Age = age;
            m_Height = height;
        }
    
    public:
        string m_Name; //姓名
        int m_Age; //年龄
        int m_Height; //身高
    };
    
    bool ComparePerson(Person &p1, Person &p2)
    {
        if (p1.m_Age == p2.m_Age)
        {
            return p1.m_Height > p2.m_Height;
        } else
        {
            return p1.m_Age < p2.m_Age;
        }
    }
    
    void test01()
    {
        list <Person> L;
        Person p1("刘备", 35, 175);
        Person p2("曹操", 45, 180);
        Person p3("孙权", 40, 170);
        Person p4("赵云", 25, 190);
        Person p5("张飞", 35, 160);
        Person p6("关羽", 35, 200);
        L.push_back(p1);
        L.push_back(p2);
        L.push_back(p3);
        L.push_back(p4);
        L.push_back(p5);
        L.push_back(p6);
        for (list<Person>::iterator it = L.begin(); it != L.end(); it++)
        {
            cout << "姓名： " << it->m_Name << " 年龄： " << it->m_Age
                 << " 身高： " << it->m_Height << endl;
        }
        cout << "---------------------------------" << endl;
        L.sort(ComparePerson); //排序
        for (list<Person>::iterator it = L.begin(); it != L.end(); it++)
        {
            cout << "姓名： " << it->m_Name << " 年龄： " << it->m_Age
                 << " 身高： " << it->m_Height << endl;
        }
    }
    
    int main()
    {
        test01();
        system("pause");
        return 0;
    }
    
    ```



## set/multiset

- 简介

  - set/multiset属于关联式容器，底层结构是用二叉树实现。
  - 所有元素都会在插入时自动被排序 （二叉树排序，方便查找？）

- 和multiset

  - 区别

    - set不允许容器中有重复的元素 
    - multiset允许容器中有重复的元素
    - set插入数据的同时会返回插入结果，表示插入是否成功 
    - multiset不会检测数据，因此可以插入重复数据

  - example

    ```c++
    #include <set>
    #include "iostream"
    using namespace std;
    
    //set和multiset区别
    void test01()
    {
        set<int> s;
        pair<set<int>::iterator, bool> ret = s.insert(10);
        if (ret.second)
        {
            cout << "第一次插入成功!" << endl;
        } else
        {
            cout << "第一次插入失败!" << endl;
        }
        ret = s.insert(10);
        if (ret.second)
        {
            cout << "第二次插入成功!" << endl;
        } else
        {
            cout << "第二次插入失败!" << endl;
        }
    
        //multiset
        multiset<int> ms;
        ms.insert(10);
        ms.insert(10);
        for (multiset<int>::iterator it = ms.begin(); it != ms.end(); it++)
        {
            cout << *it << " "; // 10 10
        }
        cout << endl;
    }
    
    int main()
    {
        test01();
        system("pause");
        return 0;
    }
    
    ```

- 构造、赋值

  - 原型

    - set st; //默认构造函数： 
    - set(const set &st); //拷贝构造函数
    - set& operator=(const set &st); //重载等号操作符

  - example

    ```c+++
    #include <set>
    
    void printSet(set<int> &s)
    {
        for (set<int>::iterator it = s.begin(); it != s.end(); it++)
        {
            cout << *it << " ";
        }
        cout << endl;
    }
    
    //构造和赋值
    void test01()
    {
        set<int> s1;
        s1.insert(10);
        s1.insert(30);
        s1.insert(20);
        s1.insert(40);
        printSet(s1);
        
        //拷贝构造
        set<int> s2(s1);
        printSet(s2);
        
        //赋值
        set<int> s3;
        s3 = s2;
        printSet(s3);
    }
    
    int main()
    {
        test01();
        system("pause");
        return 0;
    }
    ```

- 插入、删除

  - 原型

    - insert(elem); //在容器中插入元素。 
    - clear(); //清除所有元素
    - erase(pos); //删除pos迭代器所指的元素，返回下一个元素的迭代器。 
    - erase(beg, end); //删除区间[beg,end)的所有元素 ，返回下一个元素的迭代器。 
    - erase(elem); //删除容器中值为elem的元素。

  - example

    ```c++
    #include <set>
    
    void printSet(set<int> &s)
    {
        for (set<int>::iterator it = s.begin(); it != s.end(); it++)
        {
            cout << *it << " ";
        }
        cout << endl;
    }
    
    //插入和删除
    void test01()
    {
        set<int> s1;
        
        //插入
        s1.insert(10);
        s1.insert(30);
        s1.insert(20);
        s1.insert(40);
        printSet(s1);
        
        //删除
        s1.erase(s1.begin());
        printSet(s1);
        s1.erase(30);
        printSet(s1);
        
        //清空
        //s1.erase(s1.begin(), s1.end());
        s1.clear();
        printSet(s1);
    }
    
    int main()
    {
        test01();
        system("pause");
        return 0;
    }
    
    ```

- 容量

  - 原型

    - size(); //返回容器中元素的数目
    - empty(); //判断容器是否为空 
    - swap(st); //交换两个集合容器

  - example

    ```c++
    #include <set>
    
    void printSet(set<int> &s)
    {
        for (set<int>::iterator it = s.begin(); it != s.end(); it++)
        {
            cout << *it << " ";
        }
        cout << endl;
    }
    
    //大小
    void test01()
    {
        set<int> s1;
        s1.insert(10);
        s1.insert(30);
        s1.insert(20);
        s1.insert(40);
        if (s1.empty())
        {
            cout << "s1为空" << endl;
        } else
        {
            cout << "s1不为空" << endl;
            cout << "s1的大小为： " << s1.size() << endl;
        }
    }
    
    //交换
    void test02()
    {
        set<int> s1;
        s1.insert(10);
        s1.insert(30);
        s1.insert(20);
        s1.insert(40);
        set<int> s2;
        s2.insert(100);
        s2.insert(300);
        s2.insert(200);
        s2.insert(400);
        cout << "交换前" << endl;
        printSet(s1);
        printSet(s2);
        cout << endl;
        cout << "交换后" << endl;
        s1.swap(s2);
        printSet(s1);
        printSet(s2);
    }
    
    int main()
    {
        //test01();
        test02();
        system("pause");
        return 0;
    }
    ```

- 查找、统计

  - 原型

    - find(key); //查找key是否存在,若存在，返回该键的元素的迭代器；若不存在，返回set.end(); 
    - count(key); //统计key的元素个数

  - example

    ```c++
    #include <set>
    
    //查找和统计
    void test01()
    {
        set<int> s1;
        
        //插入
        s1.insert(10);
        s1.insert(30);
        s1.insert(20);
        s1.insert(40);
        
        //查找
        set<int>::iterator pos = s1.find(30);
        if (pos != s1.end())
        {
            cout << "找到了元素 ： " << *pos << endl;
        } else
        {
            cout << "未找到元素" << endl;
        }
        
        //统计
        int num = s1.count(30);
        cout << "num = " << num << endl;
    }
    
    int main()
    {
        test01();
        system("pause");
        return 0;
    }
    
    ```

- 排序

  - 原型

    - 默认从小到大排序
    - 可以在创建时指定排序规则

  - example

    ```c++
    #include "set"
    #include "iostream"
    
    using namespace std;
    
    class MyCompare
    {
    public:
        // 这里的函数要声明为const
        bool operator()(int v1,int v2) const
        {
            // 从大到小排序
            return v1 - v2;
        }
    };
    
    void test01()
    {
        set<int> s1;
        s1.insert(10);
        s1.insert(40);
        s1.insert(20);
        s1.insert(30);
        s1.insert(50);
        
        //默认从小到大
        for (set<int>::iterator it = s1.begin(); it != s1.end(); it++)
        {
            cout << *it << " "; // 10 20 30 40 50
        }
        cout << endl;
    
        //指定排序规则
        set<int, MyCompare> s2;
    
        s2.insert(10);
        s2.insert(40);
        s2.insert(20);
        s2.insert(30);
        s2.insert(50);
        for (set<int, MyCompare>::iterator it = s2.begin(); it != s2.end(); it++)
        {
            cout << *it << " "; // 50 30 20 40 10
        }
        cout << endl;
    }
    
    int main()
    {
        test01();
        return 0;
    }
    
    ```


## pair

- 简介

  - 对组
  - pair在C++中确实扮演了一种存储两个相关值的数据结构的角色，但它并不属于传统意义上的“容器”（如vector、list、map等），这些容器提供了动态地增加、删除和遍历多个元素的能力。pair更准确地说是一个简单的类模板，它能够容纳两个不同类型的数据成员（通常称为`first`和`second`），并将这两个值作为一个整体进行处理。
  - 尽管如此，在概念上，pair可以看作是一个存储两个元素的“容器”，因为它封装了两个数据项并允许以某种方式“容纳”它们。在C++标准模板库(STL)的上下文中，特别是在关联容器（如map和unordered_map）中，pair发挥了关键作用，其中每个元素都是一个key-value对，这种对是由pair来实现的。
  - 总结来说，pair虽然不是像vector那样具有动态大小调整能力的容器，但在组织和管理数据时，它确实起到了包装和结合两个数据成员的作用。在某些场景下，尤其是在表示成对数据或作为其他容器内部元素时，它可以被视为一种特殊的、固定大小的“数据容器”。

- 构造函数

  - 原型

    - pair p ( value1, value2 ); 
    - pair p = make_pair( value1, value2 );

  - example

    ```c++
    #include <string>
    #include "iostream"
    using namespace std;
    
    //对组创建
    void test01()
    {
        pair<string, int> p(string("Tom"), 20);
        cout << "姓名： " << p.first << " 年龄： " << p.second << endl;
        pair<string, int> p2 = make_pair("Jerry", 10);
        cout << "姓名： " << p2.first << " 年龄： " << p2.second << endl;
    }
    
    int main()
    {
        test01();
        system("pause");
        return 0;
    }
    ```

## map/multimap

- 简介

  - map/multimap属于关联式容器，底层结构是用二叉树实现。
  - map中所有元素都是pair pair中第一个元素为key（键值），起到索引作用，第二个元素为value（实值） 
  - 所有元素都会根据元素的键值自动排序

- 和multimap区别

  - map不允许容器中有重复key值元素 
  - multimap允许容器中有重复key值元素

- 构造、赋值

  - 原型

    - map  <mp>; //map默认构造函数
    - map(const map &mp); //拷贝构造函数
    - map& operator=(const map &mp); //重载等号操作符

  - example

    ```c++
    #include <map>
    
    void printMap(map<int, int> &m)
    {
        for (map<int, int>::iterator it = m.begin(); it != m.end(); it++)
        {
            cout << "key = " << it->first << " value = " << it->second << endl;
        }
        cout << endl;
    }
    
    void test01()
    {
        map<int, int> m; //默认构造
        m.insert(pair<int, int>(1, 10));
        m.insert(pair<int, int>(2, 20));
        m.insert(pair<int, int>(3, 30));
        printMap(m);
        map<int, int> m2(m); //拷贝构造
        printMap(m2);
        map<int, int> m3;
        m3 = m2; //赋值
        printMap(m3);
    }
    
    int main()
    {
        test01();
        system("pause");
        return 0;
    }
    ```

- 容量、交换

  - 原型

    - size(); //返回容器中元素的数目 
    - empty(); //判断容器是否为空 
      - 空是1  真
      - 非空是0 假
    - swap(st); //交换两个集合容器

  - example

    ```c++
    #include <map>
    #include "iostream"
    
    using namespace std;
    
    void printMap(map<int, int> &m)
    {
        for (map<int, int>::iterator it = m.begin(); it != m.end(); it++)
        {
            cout << "key = " << it->first << " value = " << it->second << endl;
        }
        cout << endl;
    }
    
    void test01()
    {
        map<int, int> m;
        m.insert(pair<int, int>(1, 10));
        m.insert(pair<int, int>(2, 20));
        m.insert(pair<int, int>(3, 30));
        if (m.empty())
        {
            cout << m.empty() << endl;   // 1
            cout << "m为空" << endl;
        } else
        {
            cout << m.empty() << endl; // 0
            cout << "m不为空" << endl;
            cout << "m的大小为： " << m.size() << endl;
        }
    }
    
    //交换
    void test02()
    {
        map<int, int> m;
        m.insert(pair<int, int>(1, 10));
        m.insert(pair<int, int>(2, 20));
        m.insert(pair<int, int>(3, 30));
        map<int, int> m2;
        m2.insert(pair<int, int>(4, 100));
        m2.insert(pair<int, int>(5, 200));
        m2.insert(pair<int, int>(6, 300));
        cout << "交换前" << endl;
        printMap(m);
        printMap(m2);
        cout << "交换后" << endl;
        m.swap(m2);
        printMap(m);
        printMap(m2);
    }
    
    int main()
    {
        test01();
        test02();
        system("pause");
        return 0;
    }
    ```

- 插入、删除

  - 原型
    - insert(elem); //在容器中插入元素。 
    - clear(); //清除所有元素 
    - erase(pos); //删除pos迭代器所指的元素，返回下一个元素的迭代器。 
    - erase(beg, end); //删除区间[beg,end)的所有元素 ，返回下一个元素的迭代器。 
    - erase(key); //删除容器中值为key的元素。

  - example

    ```c++
    #include <map>
    
    void printMap(map<int, int> &m)
    {
        for (map<int, int>::iterator it = m.begin(); it != m.end(); it++)
        {
            cout << "key = " << it->first << " value = " << it->second << endl;
        }
        cout << endl;
    }
    
    void test01()
    {
        //插入
        map<int, int> m;
        
        //第一种插入方式
        m.insert(pair<int, int>(1, 10));
        
        //第二种插入方式
        m.insert(make_pair(2, 20));
        
        //第三种插入方式
        m.insert(map<int, int>::value_type(3, 30));
        
        //第四种插入方式
        m[4] = 40;
        printMap(m);
        
        //删除
        m.erase(m.begin());
        printMap(m);
        m.erase(3);
        printMap(m);
        
        //清空
        m.erase(m.begin(), m.end());
        m.clear();
        printMap(m);
    }
    
    int main()
    {
        test01();
        system("pause");
        return 0;
    }
    
    ```

- 查找、计数

  - 原型

    - find(key); //查找key是否存在,若存在，返回该键的元素的迭代器；若不存在，返回set.end(); 
    - count(key); //统计key的元素个数

  - example

    ```c++
    #include <map>
    
    //查找和统计
    void test01()
    {
        map<int, int> m;
        m.insert(pair<int, int>(1, 10));
        m.insert(pair<int, int>(2, 20));
        m.insert(pair<int, int>(3, 30));
    
        //查找
        map<int, int>::iterator pos = m.find(3);
        if (pos != m.end())
        {
            cout << "找到了元素 key = " << (*pos).first << " value = " << (*pos).second << endl;
        } else
        {
            cout << "未找到元素" << endl;
        }
    
        //统计
        int num = m.count(3);
        cout << "num = " << num << endl;
    }
    
    int main()
    {
        test01();
        system("pause");
        return 0;
    }
    ```

- 自定义排序

  - 原型

    - 默认按照key从小到大排序
    - 可在创建时传入排序器

  - example

    ```c++
    #include <map>
    #include "iostream"
    
    
    using namespace std;
    
    class MyCompare
    {
    public:
        bool operator()(int v1, int v2) const
        {
            return v1 > v2;
        }
    };
    
    void test01()
    {
        //默认从小到大排序
        //利用仿函数实现从大到小排序
        map<int, int, MyCompare> m;
        m.insert(make_pair(1, 10));
        m.insert(make_pair(2, 20));
        m.insert(make_pair(3, 30));
        m.insert(make_pair(4, 40));
        m.insert(make_pair(5, 50));
        for (map<int, int, MyCompare>::iterator it = m.begin(); it != m.end(); it++)
        {
            cout << "key:" << it->first << " value:" << it->second << endl;
        }
    }
    
    int main()
    {
        test01();
        system("pause");
        return 0;
    }
    ```

## 函数对象

- 概念

  - 重载函数调用操作符的类，其对象常称为函数对象 
  - 函数对象使用重载的()时，行为类似函数调用，也叫仿函

- 本质

  - 函数对象(仿函数)是一个类，不是一个函数

- 特点

  - 函数对象在使用时，可以像普通函数那样调用, 可以有参数，可以有返回值 
  - 函数对象超出普通函数的概念，函数对象可以有自己的状态 
  - 函数对象可以作为参数传递

- 仿函数写法非常灵活，可以作为参数进行传递。

  - example

    ```c++
    #include <string>
    #include "iostream"
    
    using namespace std;
    
    //1、函数对象在使用时，可以像普通函数那样调用, 可以有参数，可以有返回值
    class MyAdd
    {
    public :
        int operator()(int v1, int v2)
        {
            return v1 + v2;
        }
    };
    
    void test01()
    {
        MyAdd myAdd;
        cout << myAdd(10, 10) << endl;
    }
    
    //2、函数对象可以有自己的状态
    class MyPrint
    {
    public:
        MyPrint()
        {
            count = 0;
        }
    
        void operator()(string test)
        {
            cout << test << endl;
            count++; //统计使用次数
        }
    
        int count; //内部自己的状态
    };
    
    void test02()
    {
        MyPrint myPrint;
        myPrint("hello world");
        myPrint("hello world");
        myPrint("hello world");
        cout << "myPrint调用次数为： " << myPrint.count << endl;
    }
    
    //3、函数对象可以作为参数传递
    void doPrint(MyPrint &mp, string test)
    {
        mp(test);
    }
    
    void test03()
    {
        MyPrint myPrint;
        doPrint(myPrint, "Hello C++");
    }
    
    int main()
    {
        //test01();
        //test02();
        test03();
        return 0;
    }
    ```

## 谓词

- 概念

  - 返回bool类型的仿函数称为谓词 
  - 如果operator()接受一个参数，那么叫做一元谓词 
  - 如果operator()接受两个参数，那么叫做二元谓词

- 一元谓词

  - example

    ```c++
    #include <vector>
    #include <algorithm>
    
    //1.一元谓词
    struct GreaterFive
    {
        bool operator()(int val)
        {
            return val > 5;
        }
    };
    
    void test01()
    {
        vector<int> v;
        for (int i = 0; i < 10; i++)
        {
            v.push_back(i);
        }
        vector<int>::iterator it = find_if(v.begin(), v.end(), GreaterFive());
        if (it == v.end())
        {
            cout << "没找到!" << endl;
        } else
        {
            cout << "找到:" << *it << endl;
        }
    }
    
    int main()
    {
        test01();
        system("pause");
        return 0;
    }
    
    ```

- 二元谓词

  - example

    ```c++
    #include <vector>
    #include <algorithm>
    
    //二元谓词
    class MyCompare
    {
    public:
        bool operator()(int num1, int num2)
        {
            return num1 - num2;
        }
    };
    
    void test01()
    {
        vector<int> v;
        v.push_back(10);
        v.push_back(40);
        v.push_back(20);
        v.push_back(30);
        v.push_back(50);
    
        //默认从小到大
        sort(v.begin(), v.end());
        for (vector<int>::iterator it = v.begin(); it != v.end(); it++)
        {
            cout << *it << " ";
        }
        cout << endl;
        cout << "----------------------------" << endl;
        
        //使用函数对象改变算法策略，排序从大到小
        sort(v.begin(), v.end(), MyCompare());
        for (vector<int>::iterator it = v.begin(); it != v.end(); it++)
        {
            cout << *it << " ";
        }
        cout << endl;
    }
    
    int main()
    {
        test01();
        system("pause");
        return 0;
    }
    
    ```



## stl内置函数对象

- 自带的

  - 这些仿函数所产生的对象，用法和一般函数完全相同 
  - 使用内建函数对象，需要引入头文件 #include <functional>

- 分类

  - 算术仿函数 
  - 关系仿函数
  - 逻辑仿函数

- 算术仿函数

  - 描述

    - 实现四则运算 
    - 其中negate是一元运算，其他都是二元运算

  - 原型

    - template<class T> T plus<T> //加法仿函数
    - template<class T> T minus<T> //减法仿函数
    - template<class T> T multiplies<T> //乘法仿函数
    - tem·plate<class T> T divides<T> //除法仿函数
    - template<class T> T modulus<T> //取模仿函数
    - template<class T> T negate<T> //取反仿函数

  - example

    ```c++
    #include <functional>
    
    //negate
    void test01()
    {
        negate<int> n;
        cout << n(50) << endl;
    }
    
    //plus
    void test02()
    {
        plus<int> p;
        cout << p(10, 20) << endl;
    }
    
    int main()
    {
        test01();
        test02();
        system("pause");
        return 0;
    }
    ```

- 关系仿函数

  - 描述

    - 实现关系对比

  - 原型

    - template<class T> bool equal_to<T> //等于
    - template<class T> bool not_equal_to<T> //不等于
    - template<class T> bool greater<T> //大于
    - template<class T> bool greater_equal<T> //大于等于
    - template<class T> bool less<T> //小于
    - template<class T> bool less_equal<T> //小于等于

  - example

    ```c++
    #include <functional>
    #include <vector>
    #include <algorithm>
    
    class MyCompare
    {
    public:
        bool operator()(int v1, int v2)
        {
            return v1 > v2;
        }
    };
    
    void test01()
    {
        vector<int> v;
        v.push_back(10);
        v.push_back(30);
        v.push_back(50);
        v.push_back(40);
        v.push_back(20);
        for (vector<int>::iterator it = v.begin(); it != v.end(); it++)
        {
            cout << *it << " ";
        }
        cout << endl;
    
        //自己实现仿函数
        //sort(v.begin(), v.end(), MyCompare());
    
        //STL内建仿函数 大于仿函数
        sort(v.begin(), v.end(), greater<int>());
        for (vector<int>::iterator it = v.begin(); it != v.end(); it++)
        {
            cout << *it << " ";
        }
        cout << endl;
    }
    
    int main()
    {
        test01();
        system("pause");
        return 0;
    }
    
    ```

- 逻辑仿函数

  - 描述
    - 实现逻辑运算
  - 原型
    - template bool logical_and //逻辑与 
    - template bool logical_or //逻辑或 
    - template bool logical_not //逻辑非

## stl常用算法

- 概述
  - 算法主要是由头文件    组成。
  - <algorithm>是所有STL头文件中最大的一个，范围涉及到比较、 交换、查找、遍历操作、复制、修改等等 
  - <numeric>体积很小，只包括几个在序列上面进行简单数学运算的模板函数 
  - <functional>定义了一些模板类,用以声明函数对象

### 遍历

- 原型

  - for_each //遍历容器 
  - transform //搬运容器到另一个容器中

- for_each

  - 描述

    - 遍历容器

  - 原型

    - for_each(iterator beg, iterator end, _func); // 遍历算法 遍历容器元素 // beg 开始迭代器 // end 结束迭代器 // _func 函数或者函数对象

  - example

    ```c++
    #include <algorithm>
    #include <vector>
    #include "iostream"
    
    using namespace std;
    
    //普通函数
    void print01(int val)
    {
        cout << val << " ";
    }
    
    //函数对象
    class print02
    {
    public:
        void operator()(int val)
        {
            cout << val << " ";
        }
    };
    
    //for_each算法基本用法
    void test01()
    {
        vector<int> v;
        for (int i = 0; i < 10; i++)
        {
            v.push_back(i);
        }
    
        //遍历算法 这两个效果一样
        for_each(v.begin(), v.end(), print01);
        cout << endl;
        for_each(v.begin(), v.end(), print02());
        cout << endl;
    }
    
    int main()
    {
        test01();
       
        return 0;
    }
    
    ```

-  transform

  - 描述

    - 搬运容器到另一个容器中

  - 原型

    - ransform(iterator beg1, iterator end1, iterator beg2, _func); //beg1 源容器开始迭代器 //end1 源容器结束迭代器 //beg2 目标容器开始迭代器 //_func 函数或者函数对象

  - example

    ```c++
    #include<vector>
    #include<algorithm>
    #include "iostream"
    
    using namespace std;
    
    
    //常用遍历算法 搬运 transform
    class TransForm
    {
    public:
        int operator()(int val)
        {
            return val;
        }
    };
    
    class MyPrint
    {
    public:
        void operator()(int val)
        {
            cout << val << " ";
        }
    };
    
    void test01()
    {
        vector<int> v;
        for (int i = 0; i < 10; i++)
        {
            v.push_back(i);
        }
        vector<int> vTarget; //目标容器
        vTarget.resize(v.size()); // 目标容器需要提前开辟空间
        transform(v.begin(), v.end(), vTarget.begin(), TransForm());
        for_each(vTarget.begin(), vTarget.end(), MyPrint()); // 0 1 2 3 4 5 6 7 8 9
    }
    
    int main()
    {
        test01();
        return 0;
    }
    
    ```



### 查找

- 原型
  - find //查找元素
  - find_if //按条件查找元素
  - adjacent_find //查找相邻重复元素
  - binary_search //二分查找法
  - count //统计元素个数
  - count_if //按条件统计元素个数

- find

  - 描述

    - 查找指定元素，找到返回指定元素的迭代器，找不到返回结束迭代器end()

  - 原型

    - find(iterator beg, iterator end, value); // 按值查找元素，找到返回指定位置迭代器，找不到返回结束迭代器位置 // beg 开始迭代器 // end 结束迭代器 // value 查找的元素

  - example

    ```c++
    #include <algorithm>
    #include <vector>
    #include <string>
    
    void test01()
    {
        vector<int> v;
        for (int i = 0; i < 10; i++)
        {
            v.push_back(i + 1);
        }
        
        //查找容器中是否有 5 这个元素
        vector<int>::iterator it = find(v.begin(), v.end(), 5);
        if (it == v.end())
        {
            cout << "没有找到!" << endl;
        } else
        {
            cout << "找到:" << *it << endl;
        }
    }
    
    class Person
    {
    public:
        Person(string name, int age)
        {
            this->m_Name = name;
            this->m_Age = age;
        }
    
        //重载==
        bool operator==(const Person &p)
        {
            if (this->m_Name == p.m_Name && this->m_Age == p.m_Age)
            {
                return true;
            }
            return false;
        }
    
    public:
        string m_Name;
        int m_Age;
    };
    
    void test02()
    {
        vector <Person> v;
        
        //创建数据
        Person p1("aaa", 10);
        Person p2("bbb", 20);
        Person p3("ccc", 30);
        Person p4("ddd", 40);
        v.push_back(p1);
        v.push_back(p2);
        v.push_back(p3);
        v.push_back(p4);
        vector<Person>::iterator it = find(v.begin(), v.end(), p2);
        if (it == v.end())
        {
            cout << "没有找到!" << endl;
        } else
        {
            cout << "找到姓名:" << it->m_Name << " 年龄: " << it->m_Age << endl;
        }
    }
    
    ```

- find_if

  - 描述

    - 按条件查找元素
    - find_if按条件查找使查找更加灵活，提供的仿函数可以改变不同的策略

  - 原型

    - find_if(iterator beg, iterator end, _Pred); // 按值查找元素，找到返回指定位置迭代器，找不到返回结束迭代器位置 // beg 开始迭代器 // end 结束迭代器 // _Pred 函数或者谓词（返回bool类型的仿函数）

  - example

    ```c++
    #include <algorithm>
    #include <vector>
    #include <string>
    
    //内置数据类型
    class GreaterFive
    {
    public:
        bool operator()(int val)
        {
            return val > 5;
        }
    };
    
    void test01()
    {
        vector<int> v;
        for (int i = 0; i < 10; i++)
        {
            v.push_back(i + 1);
        }
        
        auto it = find_if(v.begin(), v.end(), GreaterFive());
        if (it == v.end())
        {
            cout << "没有找到!" << endl;
        } else
        {
            cout << "找到大于5的数字:" << *it << endl;
        }
    }
    
    //自定义数据类型
    class Person
    {
    public:
        Person(string name, int age)
        {
            this->m_Name = name;
            this->m_Age = age;
        }
    
    public:
        string m_Name;
        int m_Age;
    };
    
    class Greater20
    {
    public:
        bool operator()(Person &p)
        {
            return p.m_Age > 20;
        }
    };
    
    void test02()
    {
        vector <Person> v;
    
        //创建数据
        Person p1("aaa", 10);
        Person p2("bbb", 20);
        Person p3("ccc", 30);
        Person p4("ddd", 40);
        v.push_back(p1);
        v.push_back(p2);
        v.push_back(p3);
        v.push_back(p4);
        
        auto it = find_if(v.begin(), v.end(), Greater20());
        if (it == v.end())
        {
            cout << "没有找到!" << endl;
        } else
        {
            cout << "找到姓名:" << it->m_Name << " 年龄: " << it->m_Age << endl;
        }
    }
    
    int main()
    {
        
        //test01();
        test02();
        return 0;
    }
    
    ```

- adjacent_find

  - 描述
    
    - 查找相邻重复元素
  - 原型
    
  - adjacent_find(iterator beg, iterator end); // 查找相邻重复元素,返回相邻元素的第一个位置的迭代器 // beg 开始迭代器 // end 结束迭代器
    
  - example

    ```c++
    #include <algorithm>
    #include <vector>
    #include "iostream"
    
    using  namespace std;
    
    void test01()
    {
        vector<int> v;
        v.push_back(1);
        v.push_back(2);
        v.push_back(5);
        v.push_back(2);
        v.push_back(4);
        v.push_back(4);
        v.push_back(3);
    
        //查找相邻重复元素
        vector<int>::iterator it = adjacent_find(v.begin(), v.end());
        if (it == v.end())
        {
            cout << "找不到!" << endl;
        } else
        {
            cout << "找到相邻重复元素为:" << *it << endl;  // 4
        }
    }
    
    int main()
    {
        test01();
        return 0;
    }
    ```

    

- binary_search

  - 描述

    - 二分查找

  - 原型

    - bool binary_search(iterator beg, iterator end, value); // 查找指定的元素，查到 返回true 否则false // 注意: 在无序序列中不可用 // beg 开始迭代器 // end 结束迭代器 // value 查找的元素

  - example

    ```c++
    #include <algorithm>
    #include <vector>
    
    void test01()
    {
        vector<int> v;
        for (int i = 0; i < 10; i++)
        {
            v.push_back(i);
        }
    
        //二分查找
        bool ret = binary_search(v.begin(), v.end(), 2);
        if (ret)
        {
            cout << "找到了" << endl;
        } else
        {
            cout << "未找到" << endl;
        }
    }
    
    int main()
    {
        test01();
        system("pause");
        return 0;
    }
    ```



-  count

  - 描述

    - 统计元素个数
    - 统计自定义数据类型时候，需要配合重载 operator==

  - 原型

    - count(iterator beg, iterator end, value); // 统计元素出现次数 // beg 开始迭代器 // end 结束迭代器 // value 统计的元素

  - example

    ```c++
    #include <algorithm>
    #include <vector>
    
    //内置数据类型
    void test01()
    {
        vector<int> v;
        v.push_back(1);
        v.push_back(2);
        v.push_back(4);
        v.push_back(5);
        v.push_back(3);
        v.push_back(4);
        v.push_back(4);
        int num = count(v.begin(), v.end(), 4);
        cout << "4的个数为： " << num << endl;
    }
    
    //自定义数据类型
    class Person
    {
    public:
        Person(string name, int age)
        {
            this->m_Name = name;
            this->m_Age = age;
        }
    
        bool operator==(const Person &p)
        {
            if (this->m_Age == p.m_Age)
            {
                return true;
            } else
            {
                return false;
            }
        }
    
        string m_Name;
        int m_Age;
    };
    
    void test02()
    {
        vector <Person> v;
        Person p1("刘备", 35);
        Person p2("关羽", 35);
        Person p3("张飞", 35);
        Person p4("赵云", 30);
        Person p5("曹操", 25);
        v.push_back(p1);
        v.push_back(p2);
        v.push_back(p3);
        v.push_back(p4);
        v.push_back(p5);
        Person p("诸葛亮", 35);
        int num = count(v.begin(), v.end(), p);
        cout << "num = " << num << endl;
    }
    
    int main()
    {
        //test01();
        test02();
        system("pause");
        return 0;
    }
    ```

- count_if

  - 描述

    - 按条件统计元素个数

  - 原型

    - count_if(iterator beg, iterator end, _Pred); // 按条件统计元素出现次数 // beg 开始迭代器 // end 结束迭代器 // _Pred 谓词

  - example

    ```c++
    #include <algorithm>
    #include <vector>
    
    class Greater4
    {
    public:
        bool operator()(int val)
        {
            return val >= 4;
        }
    };
    
    //内置数据类型
    void test01()
    {
        vector<int> v;
        v.push_back(1);
        v.push_back(2);
        v.push_back(4);
        v.push_back(5);
        v.push_back(3);
        v.push_back(4);
        v.push_back(4);
        int num = count_if(v.begin(), v.end(), Greater4());
        cout << "大于4的个数为： " << num << endl;
    }
    
    //自定义数据类型
    class Person
    {
    public:
        Person(string name, int age)
        {
            this->m_Name = name;
            this->m_Age = age;
        }
    
        string m_Name;
        int m_Age;
    };
    
    class AgeLess35
    {
    public:
        bool operator()(const Person &p)
        {
            return p.m_Age < 35;
        }
    };
    
    void test02()
    {
        vector <Person> v;
        Person p1("刘备", 35);
        Person p2("关羽", 35);
        Person p3("张飞", 35);
        Person p4("赵云", 30);
        Person p5("曹操", 25);
        v.push_back(p1);
        v.push_back(p2);
        v.push_back(p3);
        v.push_back(p4);
        v.push_back(p5);
        int num = count_if(v.begin(), v.end(), AgeLess35());
        cout << "小于35岁的个数：" << num << endl;
    }
    
    int main()
    {
    
        //test01();
        test02();
        return 0;
        
    }
    
    ```



### 排序

- 原型

  - sort //对容器内元素进行排序 
  - random_shuffle //洗牌 指定范围内的元素随机调整次序 
  - merge // 容器元素合并，并存储到另一容器中 
  - reverse // 反转指定范围的元素

- sort

  - 描述

    - 对容器内元素进行排序

  - 原型

    - sort(iterator beg, iterator end, _Pred); // 按值查找元素，找到返回指定位置迭代器，找不到返回结束迭代器位置 // beg 开始迭代器 // end 结束迭代器 // _Pred 谓词

  - example

    ```c++
    #include <algorithm>
    #include <vector>
    
    void myPrint(int val)
    {
        cout << val << " ";
    }
    
    void test01()
    {
        vector<int> v;
        v.push_back(10);
        v.push_back(30);
        v.push_back(50);
        v.push_back(20);
        v.push_back(40);
        
        //sort默认从小到大排序
        sort(v.begin(), v.end());
        for_each(v.begin(), v.end(), myPrint);
        cout << endl;
        
        //从大到小排序
        sort(v.begin(), v.end(), greater<int>());
        for_each(v.begin(), v.end(), myPrint);
        cout << endl;
    }
    
    int main()
    {
        test01();
        system("pause");
        return 0;
    }
    ```

- random_shuffle

  - 描述

    - 洗牌 指定范围内的元素随机调整次序
    - random_shuffle洗牌算法比较实用，使用时记得加随机数种子

  - 原型

    - random_shuffle(iterator beg, iterator end); // 指定范围内的元素随机调整次序 // beg 开始迭代器 // end 结束迭代器

  - example

    ```c++
    #include <algorithm>
    #include <vector>
    #include <ctime>
    
    class myPrint
    {
    public:
        void operator()(int val)
        {
            cout << val << " ";
        }
    };
    
    void test01()
    {
        srand((unsigned int) time(NULL));
        vector<int> v;
        for (int i = 0; i < 10; i++)
        {
            v.push_back(i);
        }
        for_each(v.begin(), v.end(), myPrint());
        cout << endl;
        
        //打乱顺序
        random_shuffle(v.begin(), v.end());
        for_each(v.begin(), v.end(), myPrint());
        cout << endl;
    }
    
    int main()
    {
        test01();
        system("pause");
        return 0;
    }
    
    ```

- merge

  - 描述

    - 两个容器元素合并，并存储到另一容器中
    - 合并的时候不需要有序，但是若合并前两个都是有序的，那么合并后得到的是一个有序的序列

  - 原型

    - merge(iterator beg1, iterator end1, iterator beg2, iterator end2, iterator dest); // 容器元素合并，并存储到另一容器中 // 注意: 两个容器必须是有序的 // beg1 容器1开始迭代器 // end1 容器1结束迭代器 // beg2 容器2开始迭代器 // end2 容器2结束迭代器 // dest 目标容器开始迭代器

  - example

    ```c++
    #include <algorithm>
    #include <vector>
    #include "iostream"
    
    using namespace std;
    
    class myPrint
    {
    public:
        void operator()(int val)
        {
            cout << val << " ";
        }
    };
    
    void test01()
    {
        vector<int> v1;
        vector<int> v2;
        for (int i = 0; i < 10; i++)
        {
            v1.push_back(i);
            v2.push_back(i + 1);
        }
    
        // 合并的时候不需要有序，但是若合并前两个都是有序的，那么合并后得到的是一个有序的序列    
        // v1.push_back(-1);
        // v2.push_back(-4); // 0 1 1 2 2 3 3 4 4 5 5 6 6 7 7 8 8 9 -1 9 10 -4
    
        vector<int> vtarget;
    
        //目标容器需要提前开辟空间
        vtarget.resize(v1.size() + v2.size());
    
        //合并 需要两个有序序列
        merge(v1.begin(), v1.end(), v2.begin(), v2.end(), vtarget.begin());
        // 0 1 1 2 2 3 3 4 4 5 5 6 6 7 7 8 8 9 9 10
        for_each(vtarget.begin(), vtarget.end(), myPrint());
        cout << endl;
    }
    
    int main()
    {
        test01();
    
        return 0;
    }
    ```

- reverse

  - 描述

    - 将容器内元素进行反转

  - 原型

    - reverse(iterator beg, iterator end); // 反转指定范围的元素 // beg 开始迭代器 // end 结束迭代器

  - example

    ```c++
    #include <algorithm>
    #include <vector>
    
    class myPrint
    {
    public:
        void operator()(int val)
        {
            cout << val << " ";
        }
    };
    
    void test01()
    {
        vector<int> v;
        v.push_back(10);
        v.push_back(30);
        v.push_back(50);
        v.push_back(20);
        v.push_back(40);
        cout << "反转前： " << endl;
        for_each(v.begin(), v.end(), myPrint());
        cout << endl;
        cout << "反转后： " << endl;
        reverse(v.begin(), v.end());
        for_each(v.begin(), v.end(), myPrint());
        cout << endl;
    }
    
    int main()
    {
        test01();
        system("pause");
        return 0;
    }
    ```

### 拷贝、替换

- 原型
  - copy // 容器内指定范围的元素拷贝到另一容器中 
  - replace // 将容器内指定范围的旧元素修改为新元素 
  - replace_if // 容器内指定范围满足条件的元素替换为新元素 
  - swap // 互换两个容器的元素

- copy

  - 描述

    - 容器内指定范围的元素拷贝到另一容器中

  - 原型

    - copy(iterator beg, iterator end, iterator dest); // 按值查找元素，找到返回指定位置迭代器，找不到返回结束迭代器位置 // beg 开始迭代器 // end 结束迭代器 // dest 目标起始迭代器

  - example

    ```c++
    #include <algorithm>
    #include <vector>
    
    class myPrint
    {
    public:
        void operator()(int val)
        {
            cout << val << " ";
        }
    };
    
    void test01()
    {
        vector<int> v1;
        for (int i = 0; i < 10; i++)
        {
            v1.push_back(i + 1);
        }
        vector<int> v2;
        v2.resize(v1.size());
        copy(v1.begin(), v1.end(), v2.begin());
        for_each(v2.begin(), v2.end(), myPrint());
        cout << endl;
    }
    
    int main()
    {
        test01();
        return 0;
    }
    ```

- replace

  - 描述

    - 将容器内指定范围的旧元素修改为新元素

  - 原型

    - replace(iterator beg, iterator end, oldvalue, newvalue); // 将区间内旧元素 替换成 新元素 // beg 开始迭代器 // end 结束迭代器 // oldvalue 旧元素 // newvalue 新元素

  - example

    ```c++
    #include <algorithm>
    #include <vector>
    
    class myPrint
    {
    public:
        void operator()(int val)
        {
            cout << val << " ";
        }
    };
    
    void test01()
    {
        vector<int> v;
        v.push_back(20);
        v.push_back(30);
        v.push_back(20);
        v.push_back(40);
        v.push_back(50);
        v.push_back(10);
        v.push_back(20);
        cout << "替换前：" << endl;
        for_each(v.begin(), v.end(), myPrint());
        cout << endl;
        
        //将容器中的20 替换成 2000
        cout << "替换后：" << endl;
        replace(v.begin(), v.end(), 20, 2000);
        for_each(v.begin(), v.end(), myPrint());
        cout << endl;
    }
    
    int main()
    {
        test01();
        system("pause");
        return 0;
    }
    
    ```

- replace_if

  - 描述

    - 将区间内满足条件的元素，替换成指定元素

  - 原型

    - replace_if(iterator beg, iterator end, _pred, newvalue); // 按条件替换元素，满足条件的替换成指定元素 // beg 开始迭代器 // end 结束迭代器 // _pred 谓词 // newvalue 替换的新元素

  - example

    ```c+++
    #include <algorithm>
    #include <vector>
    
    class myPrint
    {
    public:
        void operator()(int val)
        {
            cout << val << " ";
        }
    };
    
    class ReplaceGreater30
    {
    public:
        bool operator()(int val)
        {
            return val >= 30;
        }
    };
    
    void test01()
    {
        vector<int> v;
        v.push_back(20);
        v.push_back(30);
        v.push_back(20);
        v.push_back(40);
        v.push_back(50);
        v.push_back(10);
        v.push_back(20);
        cout << "替换前：" << endl;
        for_each(v.begin(), v.end(), myPrint());
        cout << endl;
        
        //将容器中大于等于的30 替换成 4090
        cout << "替换后：" << endl;
        replace_if(v.begin(), v.end(), ReplaceGreater30(), 3000);
        for_each(v.begin(), v.end(), myPrint());
        cout << endl;
    }
    
    int main()
    {
        test01();
        system("pause");
        return 0;
    }
    
    ```

- swap

  - 描述

    - 互换两个容器的元素
    - swap交换容器时，注意交换的容器要同种类型

  - 原型

    - swap(container c1, container c2); // 互换两个容器的元素 // c1容器1 // c2容器2

  - example

    ```c++
    #include <algorithm>
    #include <vector>
    
    class myPrint
    {
    public:
        void operator()(int val)
        {
            cout << val << " ";
        }
    };
    
    void test01()
    {
        vector<int> v1;
        vector<int> v2;
        for (int i = 0; i < 10; i++)
        {
            v1.push_back(i);
            v2.push_back(i + 100);
        }
        cout << "交换前： " << endl;
        for_each(v1.begin(), v1.end(), myPrint());
        cout << endl;
        for_each(v2.begin(), v2.end(), myPrint());
        cout << endl;
        cout << "交换后： " << endl;
        swap(v1, v2);
        for_each(v1.begin(), v1.end(), myPrint());
        cout << endl;
        for_each(v2.begin(), v2.end(), myPrint());
        cout << endl;
    }
    
    int main()
    {
        test01();
        system("pause");
        return 0;
    }
    
    ```

### 算术

- 描述
  
  - 算术生成算法属于小型算法，使用时包含的头文件为 #include <numeric>
- 原型
  - accumulate // 计算容器元素累计总和 
  - fill // 向容器中添加元素

- accmulate

  - 描述

    - 计算区间内 容器元素累计总和

  - 原型

    - accumulate(iterator beg, iterator end, value); // 计算容器元素累计总和 // beg 开始迭代器 // end 结束迭代器 // value 起始值

  - example

    ```c++
    #include <numeric>
    #include <vector>
    
    void test01()
    {
        vector<int> v;
        for (int i = 0; i <= 100; i++)
        {
            v.push_back(i);
        }
        int total = accumulate(v.begin(), v.end(), 0);
        cout << "total = " << total << endl;
    }
    
    int main()
    {
        test01();
        system("pause");
        return 0;
    }
    
    ```

- fill

  - 描述

    - 向容器中填充指定的元素
    - 若在这个区间内本来有元素，则会被覆盖

  - 原型

    - fill(iterator beg, iterator end, value); // 向容器中填充元素 // beg 开始迭代器 // end 结束迭代器 // value 填充的值

  - example

    ```c++
    #include <numeric>
    #include <vector>
    #include <algorithm>
    #include "iostream"
    
    using namespace std;
    
    class myPrint
    {
    public:
        void operator()(int val)
        {
            cout << val << " ";
        }
    };
    
    void test01()
    {
        vector<int> v;
        v.resize(10);
    
        // 插入一个元素
        v.push_back(1);
        
        //填充
        fill(v.begin(), v.end(), 100); // 100 100 100 100 100 100 100 100 100 100 100
        for_each(v.begin(), v.end(), myPrint());
        cout << endl;
    }
    
    int main()
    {
        test01();
        return 0;
    }
    
    ```



### 集合

- 原型

  - set_intersection // 求两个容器的交集 
  - set_union // 求两个容器的并集 
  - set_difference // 求两个容器的差集

- set_intersection

  - 描述
    - 求两个容器的交集
    - 求交集的两个集合必须的有序序列，因为是从前往后一个个匹配，大于等于另一个集合中元素迭代器才会往后走
    - 目标容器开辟空间需要从两个容器中取小值 
    - set_intersection返回值既是交集中最后一个元素的位置
  - 原型
    
  - set_intersection(iterator beg1, iterator end1, iterator beg2, iterator end2, iterator dest); // 求两个集合的交集 // 注意:两个集合必须是有序序列 // beg1 容器1开始迭代器 // end1 容器1结束迭代器 // beg2 容器2开始迭代器 // end2 容器2结束迭代器 // dest 目标容器开始迭代器
    
  - example

    ```c++
    #include <vector>
    #include <algorithm>
    #include "iostream"
    
    using namespace std;
    
    class myPrint
    {
    public:
        void operator()(int val)
        {
            cout << val << " ";
        }
    };
    
    void test01()
    {
        vector<int> v1;
        vector<int> v2;
    
    
        // 要是v1前面加了33，v2后加33，那么会一直等到v1匹配成功迭代器才会往下走
        // 也就是交集只剩33了。。。
         // v1.push_back(33);
    
        for (int i = 0; i < 10; i++)
        {
            v1.push_back(i);
            v2.push_back(i + 5);
        }
        // v2.push_back(33); // 33
    
        vector<int> vTarget;
    
        //取两个里面较小的值给目标容器开辟空间
        vTarget.resize(min(v1.size(), v2.size()));
    
        //返回目标容器的最后一个元素的迭代器地址
        vector<int>::iterator itEnd =
                set_intersection(v1.begin(), v1.end(), v2.begin(), v2.end(), vTarget.begin());
        for_each(vTarget.begin(), itEnd, myPrint()); // 5 6 7 8 9
        cout << endl;
    }
    
    int main()
    {
        test01();
        return 0;
    }
    ```

- set_union

  - 描述

    - 求两个集合的并集
    - 求并集的两个集合必须的有序序列 ,还得是有序，因为从头还是一个个比较
    - 目标容器开辟空间需要两个容器相加 
    - set_union返回值既是并集中最后一个元素的位置

  - 原型

    - set_union(iterator beg1, iterator end1, iterator beg2, iterator end2, iterator dest); // 求两个集合的并集 // 注意:两个集合必须是有序序列 // beg1 容器1开始迭代器 // end1 容器1结束迭代器 // beg2 容器2开始迭代器 // end2 容器2结束迭代器 // dest 目标容器开始迭代器

  - example

    ```c++
    #include <vector>
    #include <algorithm>
    #include "iostream"
    
    using namespace std;
    
    class myPrint
    {
    public:
    
    void operator()(int val)
    {
        cout << val << " ";
    }
    
    };
    
    void test01()
    {
        vector<int> v1;
        vector<int> v2;
    
        // v1不是有序后，就完全乱了
        // v1.push_back(33);// 5 6 7 8 9 10 11 12 13 14 33 0 1 2 3 4 5 6 7 8 9
    
        for (int i = 0; i < 10; i++)
        {
            v1.push_back(i);
            v2.push_back(i + 5);
        }
        vector<int> vTarget;
    
        //取两个容器的和给目标容器开辟空间
        vTarget.resize(v1.size() + v2.size());
    
        //返回目标容器的最后一个元素的迭代器地址
        vector<int>::iterator itEnd =
                set_union(v1.begin(), v1.end(), v2.begin(), v2.end(), vTarget.begin());
        
        // 0 1 2 3 4 5 6 7 8 9 10 11 12 13 14
        for_each(vTarget.begin(), itEnd, myPrint());
        cout << endl;
    }
    
    int main()
    {
        test01();
        return 0;
    }
    
    ```

- set_difference

  - 描述
    - 求两个集合的差集，v1 - v2 ，v1有而v2没有的留下，其余统统丢掉
    - 求差集的两个集合必须的有序序列,还是有序
    - 目标容器开辟空间需要从两个容器取较大值 
    - set_difference返回值既是差集中最后一个元素的位置
  - 原型
  
- set_difference(iterator beg1, iterator end1, iterator beg2, iterator end2, iterator dest); // 求两个集合的差集 // 注意:两个集合必须是有序序列 // beg1 容器1开始迭代器 // end1 容器1结束迭代器 // beg2 容器2开始迭代器 // end2 容器2结束迭代器 // dest 目标容器开始迭代器
  
- example
  
    ```c++
    #include <vector>
    #include <algorithm>
    #include "iostream"
    
    using namespace std;
    
    class myPrint
    {
    public:
        void operator()(int val)
        {
            cout << val << " ";
        }
    };
    
    void test01()
    {
        vector<int> v1;
        vector<int> v2;
        
        // v1.push_back(33);
        // 加了这句后，v1不有序，后面两个输出变成了
        // v1与v2的差集: 33 0 1 2 3 4 5 6 7 8 9
        // v2与v1的差集: 5 6 7 8 9 10 11 12 13 14
        
        
        for (int i = 0; i < 10; i++)
        {
            v1.push_back(i);
            v2.push_back(i + 5);
        }
        vector<int> vTarget;
    
        //取两个里面较大的值给目标容器开辟空间
        vTarget.resize(max(v1.size(), v2.size()));
    
        //返回目标容器的最后一个元素的迭代器地址
        // v1 : 0 1 2 3 4 5  6  7  8  9
        // v2 : 5 6 7 8 9 10 11 12 13 14
        // v1与v2的差集为: 0 1 2 3 4
        // v2与v1的差集为: 10 11 12 13 14
        cout << "v1与v2的差集为： " << endl;
        vector<int>::iterator itEnd =
                set_difference(v1.begin(), v1.end(), v2.begin(), v2.end(), vTarget.begin());
        for_each(vTarget.begin(), itEnd, myPrint());
        cout << endl;
        cout << "v2与v1的差集为： " << endl;
        itEnd = set_difference(v2.begin(), v2.end(), v1.begin(), v1.end(), vTarget.begin());
        for_each(vTarget.begin(), itEnd, myPrint());
        cout << endl;
    }
    
    int main()
    {
        test01();
    
        return 0;
    }
  ```
  
    

# ==其他==

## 关于#pragma one

- pragma:编译指示

- `#pragma once` 是C++编程语言中的一个预处理器指令，它的作用是在编译期间确保一个源文件（通常是头文件，后缀为 `.h` 或 `.hpp`）只会被包含一次。当一个头文件包含这个指令时，编译器在处理源代码时会检测该文件是否已经被包含过。如果该头文件在同一个编译单元（translation unit）中已经被包含过一次，后续对该头文件的所有包含请求都会被编译器自动忽略，从而防止了因多次包含同一头文件而导致的重复定义错误，尤其是对于那些定义了全局变量、静态变量、类型定义或函数声明的情况。

  `#pragma once` 的使用比传统的 include guards（例如通过 `#ifndef`、`#define` 和 `#endif` 创建的防止多重包含机制）更为简洁，但它不是C++标准的一部分，而是由大多数主流编译器作为一种非标准扩展支持的。尽管如此，`#pragma once` 在实际开发中被广泛应用，并且被认为是一种安全有效的手段来防止头文件的多重包含问题。

## 不完全面向对象

- C++虽然在很大程度上支持面向对象编程（OOP），但它并不是完全面向对象的语言，主要原因如下：

  1. **面向过程兼容性**：
     C++源于C语言，为了向后兼容，它保留了C语言的许多面向过程编程特性，如函数、指针、数组、结构体以及自由存储管理等。程序员可以在C++中完全采用面向过程的方式来编写程序，不需要创建任何类或对象。

  2. **全局函数和变量**：
     C++允许定义全局函数和全局变量，这些都不属于任何类。在纯粹的面向对象语言中，所有功能都是通过对象及其方法提供的，而在C++中，全局函数和变量的存在表明了它可以脱离对象执行逻辑。

  3. **主函数（main函数）**：
     C++程序的入口是`main`函数，它不是一个类的成员函数，也不是一个对象的方法。在严格的面向对象语言中，通常需要通过创建对象并调用其方法来启动程序。

  4. **原生数据类型**：
     C++中的基本数据类型（如`int`、`float`、`char`等）不是对象，它们不具有类的属性和方法。在某些纯面向对象语言中，所有数据都是对象，即使是简单的整数或字符也有其类的包装。

  5. **友元（Friend）机制**：
     C++中的友元机制破坏了面向对象编程的封装性原则。友元函数和友元类可以直接访问类的私有和受保护成员，这在纯粹的面向对象设计中通常是不允许的。

  6. **多范式支持**：
     C++同时支持面向对象、面向过程、泛型编程等多种编程范式，这说明它不仅仅局限于面向对象的设计模式。例如，C++引入了模板（Template）概念，这是一种通用编程技术，它并非面向对象的核心特性。

  7. **异常处理**：
     C++的异常处理机制虽然有助于错误处理，但它并不是面向对象的必要组成部分，而且在异常处理过程中，抛出和捕获异常的行为并不总是与对象的生命周期紧密关联。

  综上所述，C++虽然高度支持面向对象编程，但由于其丰富的编程模型和对多种编程范式的兼容，我们不能说它是完全面向对象的。然而，C++的这种灵活性使其能够在多种编程风格之间切换，适应复杂软件系统的需求。

# ==报错==

## 同名函数冲突

- 当编译器报出“call to 'swap' is ambiguous”错误时，通常是因为在作用域内存在多个同名且匹配调用的`swap`函数，编译器无法确定应该调用哪一个版本。在C++编程中，这个问题常常出现在以下几种情况：

  1. **标准库冲突**：C++标准库中已经定义了一个全局模板函数`std::swap`，可以交换任意支持移动构造和赋值的类型的对象。如果你在相同的或更小的作用域内也定义了一个名为`swap`的函数，并尝试对普通类型（比如内置的`int`或者其他没有自定义`swap`特化版本的类型）进行交换操作，编译器会发现两个可行的选择，从而产生歧义。

     解决方案：要么显式指定使用`std::swap`，例如`std::swap(a, b);`；要么在类内部（如果是针对特定类型设计的）提供一个带有`using`声明的自定义`swap`，并确保其可见性适中以避免全局范围内的冲突。

  2. **函数模板重载**：如果你自己定义了一个函数模板`swap`，并且其模板参数能够匹配到多种类型，也可能导致与已有的`swap`函数重载版本冲突。

     解决方案：确保你的自定义`swap`模板不会与现有版本无差别地匹配所有类型，或者通过特化或偏特化来区分不同场景下的行为。

  3. **普通函数重载**：如果在某个范围内存在多个非模板的`swap`函数，它们可能接受相同类型的参数，也会造成调用时的歧义。

     解决方案：重新命名你的自定义`swap`函数，以避免与已有函数重名，或者调整函数签名，使得根据参数类型可以唯一确定调用哪个函数。

  综上所述，当你自定义`swap`函数并遭遇“call to 'swap' is ambiguous”错误时，请检查：
  - 是否与标准库中的`std::swap`产生了冲突？
  - 自定义`swap`模板是否与其他模板或非模板函数形成了重载集合，而这些函数在调用点具有同样的匹配度？
  - 是否需要使用作用域解析运算符(`::`)明确指定要使用的`swap`版本