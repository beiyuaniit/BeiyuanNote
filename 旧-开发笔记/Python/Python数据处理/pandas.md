## pandas

- 安装

```shell
#cmd窗口
python -m pip install pandas
```

- 导入相关类

```python
from pandas import Series,DataFrame
```



## Series

- 带标签的一维数组

```python
#创建
#默认标签。0~n
ser=Series([2,3,5,7])

#自定义标签
ser=Series([2,3,4,7],index=['a','b','c','d'])

#通过字典创建
mydict={'a':100,'b':200,'c':300}
ser=Series(mydict)

#访问
#获取所有index或value
ser.index
ser.values

#通过index访问value
ser['a']
ser[['a','b']]

#修改
#修改索引
ser.index=[3,2,1]
```

## DataFrame

- 带标签且大小可变的二维表格

```python
#创建
#用字典作为数据创建。key作为列名，value对应列表里的值作为表中元素。会额外增加一个0~n的行号
data={'name':['alis','bob','lucy'],'born':[2010,2013,2012],'birth':['0302','0708','0408']}
frame=DataFrame(data)
#自定义索引
data={'Alis':{'born':2010,'birth':'0408'},'Bob':{'born':2012,'birth':'0807'}}

#访问
#通过Series该列
frame['name']

#修改
#列
frame['name']=['Alis','Bob','Lucy']

#删除
#列
del frame['name']


```

