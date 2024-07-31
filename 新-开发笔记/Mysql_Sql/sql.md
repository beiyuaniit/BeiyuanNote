## 概述

- 端口
  - mysql是3306
- 核心词
  - 定义：select,drop,alter
  - 查询：select
  - 操纵:inser,update,delete
  - 控制：grate,revoke
- 感觉能够一些用SQL做的处理，就不用放到上一层了（如一些格式、排序、函数等

## 命令行

- 3

  ```shell
  # 登陆
  mysql -u root -p
  Enter password:beiyuan3721
  
  # 查看信息
  status;//mysql相关信息
  select version();//软件版本
  
  # 退出
  \c推出当前命令
  \q或者exit推出mysql命令行
  ```

## 表

- 定义表

```sql
create table <表名>(
    <列名><数据类型>[<列级完整性约束>]
    [,<列名><数据类型>[<列级完整性约束>]]
    ...
 	[,<表级完整性约束>]
);

完整性约束条件
主码约束:primary key
非空约束:not null
唯一约束:unique
外码约束:foreign key references     必须是另一关系的主码或有唯一约束的列
检查约束:constraint iik check(sal>10)  --给约束起个名，这里是iik

其他
自增:auto_increment
默认值:default 1

example
create table student(
	id int primary key,
    sno char(5) not null unique,
    sname varchar(6),
    age int check(age>0),
    salary int
    
    constraint salary_range check (salary > 3000)
);

create table stuinfo(
	id int primary key,
    sno char(5)
    
    foreign key(sno) references student(sno)
)
```

- 修改表

```sql
alter table <表名>
[ add <新列名> <数据类型> [ 完整性约束 ] ]
[ drop <完整性约束名> ] [ drop column <列名> ]
[ modify <原列名> <数据类型> ]
[ rename to <新表名> ]
[ rename column <旧列名> to <新列名> ];

example
alter table student add arrive date;
alter table student drop unique(sno); --删除约束
alter table student drop column address --删除列 
alter table student modify age smallint;
alter table student rename to stu; --修改表名
alter table student rename column sname to s_name; --修改列名
```

- 删除表

```sql
drop table <表名>;
example 
drop table student;


drop table <表名> cascade constraints; --删除表的内容和定义
```

- 索引

```sql
create [unique] [cluster] index <索引名>
on <表名>(<列名>[<次序>][,<列名>[<次序>]]…)；	

unique:表明此索引的每一个索引值只对应唯一的数据记录，属性值不能重复
cluster:表示要建立的索引是聚簇索引，索引项顺序与表中记录的物理顺序一致

example
create cluster index stuind on student(sno);
create unique index stumsg on (id desc,sno asc); --多个属性

--删除
drop index <索引名>;
```

- other

  ```sql
  --查看表结构
  desc <表名>
  ```

  

## 查询

- select执行顺序

  - SQL查询的执行顺序是数据库解析和执行SQL语句的一个逻辑流程，虽然具体的实现可能因数据库系统（如MySQL、PostgreSQL、SQL Server等）而略有不同，但大多数遵循一个相似的基本步骤。虽然这个顺序广泛适用，但具体的SQL执行计划和优化策略可能会导致实际的执行步骤有所调整
  
  ```sql
  1. FROM：
     - 首先，确定查询的数据来源。这通常涉及到表的联合（JOIN）、子查询的执行等，以构建起查询的基础数据集。
  
  2. ON：
     - 当使用了JOIN操作时，接下来会执行ON子句中的条件，用于过滤JOIN操作产生的结果集，决定哪些记录应该被连接在一起。
  
  3. WHERE：
     - 应用WHERE子句中的条件，进一步筛选出满足条件的行。这是在执行GROUP BY、HAVING、SELECT之前进行的，用于减少后续操作的数据量。
  
  4. GROUP BY：
     - 如果有GROUP BY子句，数据会被分组，每个组包含具有相同分组表达式值得行。
  
  5. AGGREGATE FUNCTIONS（聚合函数）：
     - 在GROUP BY之后，执行如SUM、AVG、COUNT、MAX、MIN等聚合函数计算每一组的值。
  
  6. HAVING：
     - 对GROUP BY分组后的结果应用HAVING子句的条件，过滤掉不符合条件的分组。
  
  7. SELECT：
     - 此阶段确定查询结果中实际返回的列和表达式。包括计算SELECT子句中指定的列值、表达式、函数等。
  
  8. DISTINCT：
     - 如果查询中包含DISTINCT关键字，此时会去除重复的行。
  
  9. ORDER BY：
     - 根据ORDER BY子句中的列对结果集进行排序。
  
  10. LIMIT / OFFSET（或类似TOP、FETCH FIRST）：
      - 最后，根据LIMIT和OFFSET（或数据库特定的等效语法）对结果集进行裁剪，限制返回的结果数量或跳过前N行。
  
  
  ```
  
- select语法

  ```sql
  --选择:根据条件选择行
  --投影:根据条件选择列
  --连接:2个及以上表结合在一个查询
  
  --格式
  select [distinct] <目标列表达式> [，<目标列表达式>] …
  from <表名或视图名>[， <表名或视图名> ] …
  [ where <条件表达式> ]
  [ group by <列名1> [ having <条件表达式> ] ]
  [ order by <列名2> [ asc | desc ]];
  
  
  --去重 distinct只能放在所有字段前面，是对整条记录作用的，也可用来查询数量
  select count(distinct job) from emp;
  
  
  
  --where
  where age<20  或者 where not age>=20
  where age not between 20 and 30
  where sdept not in ('ma','si')
  where grade is not null
  where dept='sa' and age>30
  
  --分组
  group by sno having count(*)>3
  select deptno,MAX(sal) from emp group by eno having MAX(sal)>1000;
  区别
  where:子句作用于基表或视图，从中选择满足条件的元组
  having:短语作用于组，从中选择满足条件的组,要在group by 后使用
  --having先拿到整个分组后的值，再进行过滤。因此having中的限制一定是在select中出现的字段
  --函数可以嵌套
  MAX(AVG(sal))
  
  --分页
  limit start,total  起始行(包含，从0开始)，总行数
  返回前0-4行
  SELECT * FROM mytable LIMIT 5;
  或者SELECT * FROM mytable LIMIT 0, 5;
  
  --集函数
  count()	sum()	avg()	max()`min()	
  example
  select count(*) from student;
  select count(distinct age) from student;
  
  
  --条件
  and、or、not
  =、>、<、!=、<>
  between and
  in
  is null、is not null --未定义
  
  -- 别名 空格或as 有特殊字符可用双引号括起来
  -- 列、表都可以取别名
  select ename name,salary as sal,number "num" from ...;
  
  --orde by可以跟多个字段，先按照顺序前的排列
  
  --先分组后聚合
  SELECT Category, SUM(SalesAmount) FROM SalesData GROUP BY Category;
  
  
  -- union 用于合并两个或更多SELECT语句的结果集
  -- 去重：UNION会自动移除结果集中的重复行。如果你不想去除重复行，可以使用UNION ALL。
  -- 列匹配：所有SELECT语句中的列数必须相同，并且对应列的数据类型应该兼容。
  SELECT column_list FROM table1
  UNION
  SELECT column_list FROM table2;
  
  
  --like
  匹配
  _	单个字符
  %	任意个字符
  [ ] 可以匹配集合内的字符，例如 [ab] 将匹配字符 a 或者 b。用脱字符 ^ 可以对其进行否定，也就是不匹配集合内的字符。
  escape   不转义  _%
  where sname like '令狐%'
  WHERE col LIKE '[^AB]%'; -- 不以 A 和 B 开头的任意文本
  where name like 'stu\_%' excape '\';  --以stu_开头的
  
  
  
  ```

- snippet

  ```sql
  --可以直接对查询出来的列进行处理
  select ename,sal,sal*12+100 from emp;
  
  
  --字符串连接
  select 'Hello,' || column_name || '!' FROM table_name; -- 如 Hello,tomarrow!
  
  ```

  

## 复杂查询

- 表连接方式
  - 
- 多表

```sql
--广义笛卡尔积  不带谓词    这个都没有连接？
select stu.*,cour.* from stu,cour;

--等值连接		连接符为 =        非等值连接 不是 =
select stu.*,cour.* from stu,cour where stu.sno=cour.sno;

--自然连接		特殊的等值连接，= 重复的列只保留一个
select stu.sno,sname,cno from stu,cour where stu.sno=cour.sno;

--自身连接		2个相同表连接，取别名区分
select first.sno,second.sname from stu first,stu second where first.sno=second.id;
```

- 内外连接

```sql
--内连接   只包含满足连接条件的列     默认，可不写inner join
select * from  a inner join  b on a.id=b.id;

--外连接   还包含表的全部数据
--左外连接    左表全部列，右表没有的条件用null代替
left outer join 或 left join
select * from a left join b on a.id=b.id;

--右外连接	  左表没有的条件用null代替，右表全部列
right outer join 或 right join
select * from a right join b on a.id=b.id;

--全外连接	  左右表全部列，没有的用null    (mysql暂不支持)
full outer join 或 full join
select * from a full join b on a.id=b.id;

--补充：笛卡尔连接(交叉连接)
cross join
```

- 嵌套查询

```sql
--一个select from where查询块嵌套在另一个查询块的where或having，子查询不能使用order by
--外层查询是父查询，内层查询是子查询

in 和 exists都是返回外表的结果。当外表遍历结束了，就不会再去遍历外表
--相关子查询：子查询的查询条件依赖于父查询，由外层到内层
先外查询，然后看内查询
exists关键字后面的参数是一个任意的子查询，系统对子查询进行运算以判断它是否返回行，如果至少返回一行，那么exists的结果为true ,此时外层的查询语句将进行查询；如果子查询没有返回任何行，那么exists的结果为false,此时外层结果将不进行返回。
需要注意的是，当我们的子查询为 SELECT NULL 时，MYSQL仍然认为它是True
select * from emp e1 where exists (select * from emp e2 where e1.empno=e2.mgr);
select sno,cno from sc x 
where grade >=(
    select avg(grade)from sc y
    where y.sno=x.sno
);--内层查询的结果是一个学生所有选修课程平均成绩的，至于是哪个学生的平均成绩就由参数x.sno的值决定

--不相关子查询：子查询的查询条件不依赖于父查询，由内层到外层
先内查询拿到结果集，然后外查询，并逐一比较结果集中的结果
返回一个结果集，结果集中只能是一个字段。
select * from emp where empno in (select mgr from emp);

--总结
--如果查询数量比较小时：非相关子查询比相关子查询效率高。
--如果查询数量比较大时：一般不建议使用in，因为in的效率比较低，我们可以使用相关子查询

外表较小
用exist，因为内表比较大，exist时内查询只要查到一条满足条件的记录就可以返回了
外表较大
用in，外表肯定是要遍历的，用in可以一次返回结果集，且放在内存中了
```

- 带all或any的子查询

  - ANY和ALL谓词有时可以用集函数实现

  ![2022-09-21_150117](images\2022-09-21_150117.png)

```sql
--谓词ANY表示任意一个值，ALL表示所有值，但需要配合使用比较运算符。
> (>=) 	ANY	大于(大于等于)子查询结果中的某个值       
> (>=)	ALL	大于(大于等于)子查询结果中的所有值
< (<=) 	ANY	小于(小于等于)子查询结果中的某个值    
< (<=) 	ALL	小于(小于等于)子查询结果中的所有值
  =ANY		等于子查询结果中的某个值        
  =ALL      等于子查询结果中的所有值（通常没有实际意义）
  !=ANY		不等于子查询结果中的某个值
  !=ALL		不等于子查询结果中的任何一个值
  
--查询其他系中比信息系任意一个(其中某一个)学生年龄小的学生姓名和年龄
SELECT Sname,Sage FROM Student
WHERE Sage < ANY (SELECT Sage FROM Student WHERE Sdept='IS')  AND Sdept <>'IS';
--也可用集函数
SELECT Sname，Sage   FROM  Student
WHERE Sage < (SELECT MAX(Sage) FROM Student WHERE Sdept= 'IS') AND Sdept <> 'IS';
```

- 带exists的子查询

```sql
--相当于存在量词，不返回数据，只返回true或false
--查询没有选修了1号课程的学生姓名
SELECT Sname  FROM Student  WHERE NOT EXISTS 
(SELECT * FROM SC WHERE Sno=Student.Sno AND Cno= ‘1’);/*相关子查询*/
```

## DML

- Data Manipulation Language（数据操纵语言）
- 插入

```sql
insert into <表名> [列名] values (值);    --不写列名则与定义表时顺序一致

example
insert into student values (1,'libai',21);
insert into student(id,name) values (2,'dufu'); --最好指定相关列，因为不确定哪天表结构会变化

--插入子查询
insert into <表名> [列名] 子查询
INSERT  INTO  Deptage(Sdept，Avgage) SELECT  Sdept，AVG(Sage)  FROM  Student  GROUP BY Sdept;
```

- 修改

```sql
UPDATE  <表名>  
SET  <列名>=<表达式>[,<列名>=<表达式>]… 
[WHERE <条件>];

example
UPDATE  Student SET  Sage=22  WHERE  Sno=' 21001 ';
UPDATE Student  SET Sage= Sage+1;
```

- 删除

```sql
DELETE  FROM  <表名> [WHERE <条件>];  --删除满足条件的元组

DELETE  FROM Student   WHERE Sno=‘21019';
delete from stu; --所以这个是清空表了，但表本身还在

```

## 视图

- 特点
  - RDBMS提供给用户以多种角度观察数据库中数据的重要机制
  - 是虚表，从一个或几个基本表(或视图)导出的表
  - 只存放视图的定义，不会出现数据冗余
  - 基表中的数据发生变化，从视图中查询出的数据也随之改变
- 作用
  - 能够简化用户的操作
    - 当视图中数据不是直接来自基本表时，如基于多张表连接形成的视图、基于复杂嵌套查询的视图、含导出属性的视图等，定义视图能够简化用户的操作
  - 使用户能以多种角度看待同一数据
    - 视图机制能使不同用户以不同方式看待同一数据，适应数据库共享的需要
  - 对重构数据库提供了一定程度的逻辑独立性
    - 关系数据库中数据库的重构往往是不可避免的，重构数据库最常见的是将一个基本表垂直地分成多个基本表
  - 能够对机密数据提供安全保护
    - 对不同用户定义不同视图，使每个用户只能看到他有权看到的数据。通过WITH CHECK OPTION可以对关键数据定义操作时间限制
- 定义

```sql
CREATE  VIEW  <视图名>  [(<列名> [,<列名>]…)]
AS  <子查询>  [WITH  CHECK  OPTION];

--行列子集视图：从单个基本表导出，建立的视图只是去掉了基本表的某些行和某些列，但保留了码
CREATE VIEW IS_Student
AS  SELECT Sno，Sname，Sage
FROM Student WHERE Sdept= 'IS';--建立信息系学生的视图

--WITH CHECK OPTION视图：表示对视图进行增删改操作时不得破坏视图定义中的谓词条件(即子查询中的条件表达式)
CREATE VIEW IS_Student AS SELECT Sno，Sname，Sage  
FROM  Student  WHERE  Sdept= 'IS'  WITH CHECK OPTION;--建立信息系学生的视图，并要求透过该视图进行的更新操作只涉及信息系学生

--基于多个基表的视图
CREATE VIEW IS_S1(Sno，Sname，Grade)
AS  SELECT Student.Sno，Sname，Grade   FROM  Student，SC
WHERE  Sdept= 'IS' AND Student.Sno=SC.Sno AND   SC.Cno= '1';

--基于视图的视图：视图可建立在一个或多个已定义的视图上
CREATE  VIEW IS_S2  AS  SELECT Sno，Sname，Grade  
FROM  IS_S1  WHERE  Grade>=90;

--带表达式的视图：必须明确定义组成视图的各个属性列名
CREATE  VIEW BT_S(Sno，Sname，Sbirth)
AS SELECT Sno，Sname，Sage  FROM  Student;

--分组视图：带有集函数和GROUP BY子句查询的视图
CREAT  VIEW S_G(Sno，Gavg)
AS  SELECT Sno，AVG(Grade)  FROM  SC GROUP BY Sno;
```

- 删除

```sql
DROP  VIEW  <视图名>;
```

- 查询视图
  - 视图定义后，用户可以象对基本表一样对视图进行查询

- 更新视图
  - 通过视图实现插入、删除和修改数据。由于视图是不实际存储数据的虚表，因此对视图的更新最终要转换为对基本表的更新

```sql
UPDATE  IS_Student  SET  Sname= 'lisi'  WHERE  Sno= '21002';

--转换后的语句
UPDATE  Student  SET Sname= 'lisi'
WHERE Sno= '21002' AND Sdept= 'IS';

--一些视图是不可更新的，因为对这些视图的更新不能唯一地有意义地转换成对相应基本表的更新
CREATE VIEW S_G (Sno,Gavg)
AS  SELECT  Sno,AVG(Grade)   FROM   SC  GROUP BY Sno;
UPDATE  S_G  SET  Gavg=90  WHERE  Sno= '95001';--无法更新
--具体限制
若视图是由两个以上基本表导出的，则此视图不允许更新
若视图的字段来自字段表达式或常数，则不允许对此视图执行INSERT和UPDATE操作，但允许执行DELETE操作
若视图的字段来自集函数，则此视图不允许更新
若视图定义中含有GROUP BY子句，则此视图不允许更新
若视图定义中含有DISTINCT短语，则此视图不允许更新
一个不允许更新的视图上定义的视图也不允许更新
若视图定义中有嵌套查询，并且内层查询的FROM子句中涉及的表也是导出该视图的基本表，则此视图不允许更新
```

## 存储过程

存储过程可以看成是对一系列 SQL 操作的批处理。

使用存储过程的好处：

- 代码封装，保证了一定的安全性；
- 代码复用；
- 由于是预先编译，因此具有很高的性能。

命令行中创建存储过程需要自定义分隔符，因为命令行是以 ; 为结束符，而存储过程中也包含了分号，因此会错误把这部分分号当成是结束符，造成语法错误。

包含 in、out 和 inout 三种参数。

给变量赋值都需要用 select into 语句。

每次只能给一个变量赋值，不支持集合的操作。

```sql
delimiter //

create procedure myprocedure( out ret int )
    begin
        declare y int;
        select sum(col1)
        from mytable
        into y;
        select y*y into ret;
    end //

delimiter ;
```

```sql
call myprocedure(@ret);
select @ret;
```

## 游标

- 在存储过程中使用游标可以对一个结果集进行移动遍历。

- 游标主要用于交互式应用，其中用户需要对数据集中的任意行进行浏览和修改。

- 使用游标的四个步骤：

  - 声明游标，这个过程没有实际检索出数据；
  - 打开游标；
  - 取出数据；
  - 关闭游标；

  ```shell
  delimiter //
  create procedure myprocedure(out ret int)
      begin
          declare done boolean default 0;
  
          declare mycursor cursor for
          select col1 from mytable;
          # 定义了一个 continue handler，当 sqlstate '02000' 这个条件出现时，会执行 set done = 1
          declare continue handler for sqlstate '02000' set done = 1;
  
          open mycursor;
  
          repeat
              fetch mycursor into ret;
              select ret;
          until done end repeat;
  
          close mycursor;
      end //
   delimiter ;
  ```

## 触发器

- 触发器会在某个表执行以下语句时而自动执行：DELETE、INSERT、UPDATE。

  - INSERT 触发器包含一个名为 NEW 的虚拟表。

  - DELETE 触发器包含一个名为 OLD 的虚拟表，并且是只读的。

  - UPDATE 触发器包含一个名为 NEW 和一个名为 OLD 的虚拟表，其中 NEW 是可以被修改的，而 OLD 是只读的。

- 触发器必须指定在语句执行之前还是之后自动执行，之前执行使用 BEFORE 关键字，之后执行使用 AFTER 关键字。BEFORE 用于数据验证和净化，AFTER 用于审计跟踪，将修改记录到另外一张表中

- MySQL 不允许在触发器中使用 CALL 语句，也就是不能调用存储过程

  ```shell
  CREATE TRIGGER mytrigger AFTER INSERT ON mytable
  FOR EACH ROW SELECT NEW.col into @result;
  
  SELECT @result; -- 获取结果
  ```

  

## 事务

- 基本术语：
  - 事务（transaction）指一组 SQL 语句；要么全部执行，要么都不执行
  - 回退（rollback）指撤销指定 SQL 语句的过程；
  - 提交（commit）指将未存储的 SQL 语句结果写入数据库表；
  - 保留点（savepoint）指事务处理中设置的临时占位符（placeholder），你可以对它发布回退（与回退整个事务处理不同）。

- 注意事项

  - 不能回退 SELECT 语句，回退 SELECT 语句也没意义；也不能回退 CREATE 和 DROP 语句。
  - MySQL 的事务提交默认是隐式提交，每执行一条语句就把这条语句当成一个事务然后进行提交。当出现 START TRANSACTION 语句时，会关闭隐式提交；当 COMMIT 或 ROLLBACK 语句执行后，事务会自动关闭，重新恢复隐式提交。
  - 设置 autocommit 为 0 可以取消自动提交；autocommit 标记是针对每个连接而不是针对服务器的。
  - 如果没有设置保留点，ROLLBACK 会回退到 START TRANSACTION 语句处；如果设置了保留点，并且在 ROLLBACK 中指定该保留点，则会回退到该保留点。
- DDL（如创建表）和DCL（如授权）会隐式提交
  - 事务的过程会锁定相应的行
  - 未commit时，其他用户看不到数据库的变化。更改还在缓冲区中
  
  ```shell
  START TRANSACTION
  // ...
  SAVEPOINT delete1
  // ...
  ROLLBACK TO delete1
// ...
  COMMIT
  
  
  
  ```
  
  

## 权限

- 常用权限

![2022-09-21_165325](images\2022-09-21_165325.png)

- 授权

```sql
GRANT <权限>[,<权限>] … [ON <对象类型> <对象名>]
TO <用户>[,<用户>] …[WITH GRANT OPTION];  --WITH GRANT OPTION子句：若指定了该子句，则获得某种权限的用户还可以把该权限再授予别的用户

example
GRANT  SELECT ON  TABLE  Student   TO  U1;
GRANT  ALL PRIVILIGES  ON  TABLE  Student, Course TO U2,U3;
GRANT SELECT ON TABLE SC TO PUBLIC;
GRANT UPDATE(Sno), SELECT ON TABLE Student TO U4;
GRANT INSERT ON TABLE SC TO U5 WITH GRANT OPTION;
GRANT CREATETAB ON DATABASE S_C TO U8;
```

- 收回权限

```sql
REVOKE <权限>[,<权限>]…[ON <对象类型> <对象名>]
FROM <用户>[,<用户>] …;

REVOKE UPDATE(Sno) ON TABLE Student FROM U4;
REVOKE SELECT ON TABLE SC FROM PUBLIC;
REVOKE INSERT ON TABLE SC FROM U5;--权限的级联回收：例5中U5将对SC表的INSERT权限授予了U6，而U6又将其授予了U7；执行例9后，DBMS在收回U5对SC表的INSERT权限的同时，还会自动收回U6和U7对SC表的INSERT权限，即收回权限的操作会级联下去
```

## 执行计划

- mysql用desc或者explain + Sql语句查看执行计划，可以通过执行计划分析sql性能

```sql

mysql> desc select * from dept;
+----+-------------+-------+------------+------+---------------+------+---------+------+------+----------+-------+
| id | select_type | table | partitions | type | possible_keys | key  | key_len | ref  | rows | filtered | Extra |
+----+-------------+-------+------------+------+---------------+------+---------+------+------+----------+-------+
|  1 | SIMPLE      | dept  | NULL       | ALL  | NULL          | NULL | NULL    | NULL |    4 |   100.00 | NULL  |
+----+-------------+-------+------------+------+---------------+------+---------+------+------+----------+-------+
1 row in set, 1 warning (0.00 sec)
```

- 解析
  - id
    - select查询序列号，如果id相同，则从上到下顺序执行，如果不相同，序列号大的优先级比较高，先执行
  - select_type
    - SIMPLE：简单的查询，查询中不饱和子查询或者Union
    - PRIMARY：查询中包含任何复杂的子查询，最外层的查询类型为PRIMARY
    - SUBQUERY：SELECT或者where中包含子查询
    - DERIVED：在from中包含子查询被标记为DERIVED(衍生)，Mysql会递归查询这些子查询，将查询结果放入临时表
    - UNION：第二个select中查询UNION,则被标记为UNION，如果UNION出现在from子查询中，最外层被标记为DERIVED
    - UNION RESULT：从UNION表获取结果的select
  - table
    - 所在表
  - type
    - 判断sql执行性能比较关键的一个字段，性能从高到低依次是
      - system > const > eq_ref > ref > fulltext > ref_or_null > index_merge > unique_subquery > index_subquery > range > index > ALL
    - system：表示只有一条数据，类似于系统表，是const的一种特例
    - const：表示通过索引一次就查询到数据，比较块，用于primary key（主键）和unique
    - eq_ref：用于“=”运算符比较的索引列
    - ref：非唯一索引扫描 
    - rang：检索给的范围的值，使用一个索引进行选择，where后面使用between、>、<、in等
    -  index：当前查询的结果全部为索引列，虽然也是全部扫描，但是只查询索引数据，没有之间查询数据
    - all：遍历全部查询
  - possible_keys
    - 可能使用的索引
  - key
    - 实际使用的索引
  - key_len
    - 索引长度
  - ref
    - 索引引用的列
  - rows
    - 查询的行数
  - extra
    - using where：表示使用了where过滤
    - using index：表示使用了覆盖索引，避免使用表数据
    - using join buffer：表示使用了链接缓存

## SQL优化

- 优化是相对的，实际使用还得看情况，有时候会选择牺牲性能

  ```sql
  --避免使用*
  解析时，*需要查询数据字典来转换成所有列名，且不直观
  
  --条件where时，从右边往左执行，建议右边放小范围的
  where d.deptno > 10 and e.eno > 30;
  
  --可以的话，使用>=代替>，少一次查询
  eno >= 4; --4就满足了
  eno > 3; --3不在结果集
  
  --避免对索引进行计算 
  where sal*2>5000; --数据库没有计算后的结果，索引失效
  where sal>5000/2;
  ```

  



## 函数-聚合

|  函 数  |      说 明       |
| :-----: | :--------------: |
|  AVG()  | 返回某列的平均值 |
| COUNT() |  返回某列的行数  |
|  MAX()  | 返回某列的最大值 |
|  MIN()  | 返回某列的最小值 |
|  SUM()  |  返回某列值之和  |

```sql
--AVG() 会忽略 NULL 行、使用 DISTINCT 可以汇总不同的值
SELECT AVG(DISTINCT col1) AS avg_col FROM mytable;
```

## 函数-字符串

|           函数           |               说明               |
| :----------------------: | :------------------------------: |
|          LEFT()          |            左边的字符            |
|         RIGHT()          |            右边的字符            |
|         LOWER()          |               小写               |
|         UPPER()          |               大写               |
|         LTRIM()          |          去除左边的空格          |
|         RTRIM()          |          去除右边的空格          |
|         LENGTH()         |               长度               |
|        SOUNDEX()         |           转换为语音值           |
|        INITCAP()         | 字符串中单词字母大写，其余转小写 |
| CONCAT(“Hello","World")  |            字符串连接            |
| SUBSTR("HelloWorld",1,5) |              取子串              |
|         INSTR()          |       从左到右找，返回下标       |

```sql
--将一个字符串转换为描述其语音表示的字母数字模式
SELECT * FROM mytable WHERE SOUNDEX(col1) = SOUNDEX('apple')
```

## 函数-时间

|     函 数     |             说 明              |
| :-----------: | :----------------------------: |
|   ADDDATE()   |    增加一个日期（天、周等）    |
|   ADDTIME()   |    增加一个时间（时、分等）    |
|   CURDATE()   |          返回当前日期          |
|   CURTIME()   |          返回当前时间          |
|    DATE()     |     返回日期时间的日期部分     |
|  DATEDIFF()   |        计算两个日期之差        |
|  DATE_ADD()   |     高度灵活的日期运算函数     |
| DATE_FORMAT() |  返回一个格式化的日期或时间串  |
|     DAY()     |     返回一个日期的天数部分     |
|  DAYOFWEEK()  | 对于一个日期，返回对应的星期几 |
|    HOUR()     |     返回一个时间的小时部分     |
|   MINUTE()    |     返回一个时间的分钟部分     |
|    MONTH()    |     返回一个日期的月份部分     |
|     NOW()     |       返回当前日期和时间       |
|   SECOND()    |      返回一个时间的秒部分      |
|    TIME()     |   返回一个日期时间的时间部分   |
|    YEAR()     |     返回一个日期的年份部分     |
|   SYSDATE()   |          系统当前日期          |

```sql
SELECT NOW(); --2018-4-14 20:25:11	
```

## 函数-数学

|      函数       |        说明         |
| :-------------: | :-----------------: |
|      SIN()      |        正弦         |
|      COS()      |        余弦         |
|      TAN()      |        正切         |
|      ABS()      |       绝对值        |
|     SQRT()      |       平方根        |
|      MOD()      |        余数         |
|      EXP()      |        指数         |
|      PI()       |       圆周率        |
|     RAND()      |       随机数        |
| ROUND(45.926,2) | 四舍五入，取2位小数 |
| TRUNC(45.926,2) | 直接截取小数点后2位 |

## 函数-转换

|              函数              |    说明    |
| :----------------------------: | :--------: |
| TO_CHAR(hiredate,"YYYY-MM-DD") | 转为字符串 |
|            TO_DATE             |  转为日期  |
|           TO_NUMBER            |            |







