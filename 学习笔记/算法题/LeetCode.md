# ==javalc==

## 数据结构

- 从JavaSe.md中

```java
/*
Collection接口
	集合是一个容器，存放对象的引用(基本类型会自动装箱
	集合用数据结构实现
	存放的是Object类型引用且同一个集合可以存放不同的引用类型
Collection的子接口
	Set接口。

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

/*
Map接口
	数据以key-value形式存储
*/

/*
Hashtable:哈希表，线程安全
HashMap：哈希表，线程不安全
        不允许重复的key
        链地址法处理哈希冲突：位桶+链表+红黑树

TreeMap：二叉排序树，按照key排序

Properties：HashMap，线程安全，key和value只能是String		
	没错就是配置文件.properties相关的那个

 */
```



## 基本类型

- int 是10位。21亿，long是19位

## 数组

- 有默认值，不用手动赋值（方法内外都是

```java
boolean []visited=new boolean[3];
System.out.println(visited[0]);//false
int []arr=new int[3];
System.out.println(arr[2]);//0
```

## Integer

- 没啥就是一些api

```java
Integer.parseInt(str);//要记得这个转数字的
单个字符   char-'0';//更好
```

## String

- 返回的只能是char

```java
public char charAt(int index)
```

## StringBulider

- 速度快点，线程不安全。而StringBuffer线程安全

## Array

- 长度

```java
 for(int i=0;i<nums.length;i++){}//注意不同于string的length().数组是length字段
//或者
for(int i:nums){}//只访问元素的话可以不用直到长度
//填充元素都为-1
Arrays.fill(nums,-1);
    
```

## List

- 动态大小

```java
/*for(int  i=0;i<list.size();i++){//错误写法，size会不断增加
    list.add(new ArrayList<Integer> ());
}*/
/*
for(int e:list){
	list.add(e+1);//for-each也是错的
}
*/

int size=list.size();//得先保存初始得值
for(int i=0;i<size();i++){
    list.add(new ArrayList<Integer>());
}
```

- 增加或删除后，索引马上会改变

```java
//删除前n个元素
  for(int i=0;i<n;i++)
      list.remove(0);//因为索引改变。只能从第0个删除。LinkedList和ArrayList都一样
```

- list的值不能像数组直接=修改

```java
List<Integer>list=new ArrayList<>();
list.add(3);
//list.get(0)=7;  错误
list.set(7);
```

- List转数组

```java
List<Integer>list=new ArrayList<>();
Integer []arr=list.toArray(new Integer[0]);//new Integer[0]传个空的意思下就行

List<int []> ans=new ArrayList<>();    
ans.toArray(new int[0][0]); //传递一个最终类型的空数组

```

- List可以应用sort函数

  - 用的话就用ArrayList吧，用LinkedList感觉不划算

  ```java
  //为空[] 或者[null] 可以排不报错，但是[null,3]之类的不行
  listArr.sort(((o1, o2) ->o1.val-o2.val));
  ```

  

## HashSet

- set.add()

```java
public boolean add(E e)      
set.add(1); //true
set.add(1);//false

if(!set.add(i)){
    //若set不包含i，则添加，否则返回true
    return true;
}
```

## HashMap

- 创建并添加值

```java
Map<Character, String>map=new HashMap<>(){
    {
         put('2',"abc");
         put('3',"def");
         put('4',"ghi");
     }
};
```

- 没有则取默认值
  - 有则加一

```java
default V getOrDefault(Object key, V defaultValue)
map.put(num, map.getOrDefault(num, 0) + 1);

//以下这样是错的
 Map<Integer, List<Integer>> map=new HashMap<>();
 map.put(1,map.getOrDefault(1,new ArrayList<Integer>()).add(1));  //add(1) 返回的是boolean类型

```

- 还能判断value是否存在

```java
containsKey(Object key)
containsValue(Object value)
```

- lambda中不能修改其他变量
  - 报错：error: local variables referenced from a lambda expression must be final or effectively final

```java
     int count=0;
     colorMap.forEach((color,list)->{
            for(int i=1;i<list.size();i++){
                    if(list.get(i)-list.get(i-1)<m || (list.get(i)+m)%n>list.get(i-1)){
                        count++;
                        break;
                    }
           }
     });
```

## Stack

- 纯栈，底层用Vector实现

  ```jav
  Stack<Integer>stack=new Stack<>();
  ```


## Queue

- 

  ```java
  Queue<Integer> que=new LinkedList<>();
  que.add(1);
  que.peek();
  que.poll();
  ```


## BitSet

- 位图
- 维护一个long数组，初始只有一个long
- 用位的0和1表示数字是否存在

```java
   BitSet bitSet=new BitSet();
   bitSet.set(3,true); //可从0开始
   System.out.println(bitSet.get(2)); //false
   System.out.println(bitSet.get(3)); //true
```

- 1亿个bit = 12MB左右的内存空间（11.9209289551MB
- 一亿个int=381.47MB

## 其他

### for-each

- 基本类型只能遍历，不能修改。引用类型可通过引用修改对象

```java
int []nums={1,2,3};
//for-each只能遍历，不能修改
for(int n:nums){
    n+=1;
}
System.out.println(Arrays.toString(nums));//[1,2,3]

StringBuilder []str=new StringBuilder[2];
str[0]=new StringBuilder();
str[1]=new StringBuilder();
str[0].append('a');
str[1].append('b');
//for-each只能遍历，不能修改
for(StringBuilder s:str){
     s.append('c');
}
System.out.println(Arrays.toString(str));//[ac, bc]
```

### 原引用

- 引用本身应该是值传递，但是可以通过引用改变对象的值，所以这里没必要

```java
  public ListNode swapPairs(ListNode head) {
 	 ListNode node=head;
  }
```



# ==数据结构==

## ==HashSet==

### 217. 存在重复元素

- 给你一个整数数组 `nums` 。如果任一值在数组中出现 **至少两次** ，返回 `true` ；如果数组中每个元素互不相同，返回 `false` 
- HashSet

```java
    public boolean containsDuplicate(int[] nums) {
        //HashSet，无需不重复。
        Set<Integer> set=new HashSet();
        for(int num:nums){
            if(set.contains(num)){
                return true;//有重复则返回
            }else{
                set.add(num);
            }
        }
        return false;
    }
```

- 排序。若相邻的相等，true

### 36.有效的数独

- `9 x 9` 的数独，行、列、九宫格都不能有重复。一个有效的数独（部分已被填充）不一定是可解的。
- HashSet+暴力

```java
    public boolean isValidSudoku(char[][] board) {
        Set [] Cen=new HashSet[3];//横着三个九宫格
        for(int i=0;i<3;i++){
            Cen[i]=new HashSet();
        }
        Set [] Row=new HashSet[9];//列
        Set [] Line=new HashSet[9];//行
        for(int i=0;i<9;i++){
            Row[i]=new HashSet();
            Line[i]=new HashSet();
        }
        for(int i=0;i<board.length;i++){
            for(int j=0;j<board.length;j++){
                if(board[i][j]=='.'){
                    continue;
                }
                Character c=board[i][j];
                if(!Line[i].contains(c)){//行
                    Line[i].add(c);
                }else {
                    return false;
                }

                if(!Row[j].contains(c)){
                    Row[j].add(c);
                }else {
                    return false;
                }
                int index=j/3;  // 定位到三个九宫格中的一个
                if(!Cen[index].contains(c)){//九宫格
                    Cen[index].add(c);
                }else {
                    return false;
                }
            }
            if(i%3==2){ // 当前九宫格用完了，需要清0
                for(int k=0;k<3;k++){
                    Cen[k].clear();
                }
            }
        }
        return true;
    }
```

### 03.数组中重复的数(剑指)

- 在一个长度为 n 的数组 nums 里的所有数字都在 0～n-1 的范围内。数组中某些数字是重复的，但不知道有几个数字重复了，也不知道每个数字重复了几次。请找出数组中任意一个重复的数字。
- HashSet判断重复

```java
//感觉最实用
class Solution {
    public int findRepeatNumber(int[] nums) {
        HashSet <Integer> hset=new HashSet<>();

        for(int i:nums){
            if(hset.contains(i)){
                return i;
            }
            hset.add(i);
        }
        return -1;//最后随便返回个数保证能通过编译
    }
}
```

- 排序后检查

```java
class Solution {
    public int findRepeatNumber(int[] nums) {
        Arrays.sort(nums);
        for(int i=0;i<nums.length-1;i++){
            if(nums[i]==nums[i+1])
            return nums[i];
        }
        return -1;
    }
}
```

- 原地交换,让元素和下标一一对应:因为n-1

### 128.最长连续序列

- 给定一个未排序的整数数组 `nums` ，找出数字连续的最长序列（不要求序列元素在原数组中连续）的长度。
  
  请你设计并实现时间复杂度为 `O(n)` 的算法解决此问题。
  
   
  
  **示例 1：**
  
  ```
  输入：nums = [100,4,200,1,3,2]
  输出：4
  解释：最长数字连续序列是 [1, 2, 3, 4]。它的长度为 4。
  ```
  
  **示例 2：**
  
  ```
  输入：nums = [0,3,7,2,5,8,4,6,0,1]
  输出：9
  ```
  
   
  
  **提示：**
  
  - `0 <= nums.length <= 105`
  - `-109 <= nums[i] <= 109`
  
- HashSet
  
  - 先都放Set+判断一边
  - 这里没有达到O(n),但也减少了不少时间复杂度。要看到问题的本质

```java
    //直接Set。。是个鬼并查集
    public int longestConsecutive(int[] nums) {
        Set<Integer>set=new HashSet<>();
        //先全部放才好下面的只判断x之后
        for(int n:nums){
            set.add(n);
        }
        int res=0;
        for (int num : nums) {
            //if(!set.contains(nums[i])){
            //   set.add(nums[i]);
            //}
            //set.add(num);//会自动去重
            int count = 1;
            //只枚举后，6。x，x+1，x+2..反正这个序列以x开头
            // 以x开头，则必须不能包含x-1。找到端点边界值
            if(!set.contains(num-1)){
                int temp = num;
                while (set.contains(++temp)) {
                    count++;
                }
                //更新res
                if (count > res) {
                    res = count;
                }
            }

        }
        return res;
    }
```



## ==HashMap==

### 350.两个数组的交集

- 返回结果中每个元素出现的次数，应与元素在两个数组中都出现的次数一致（如果出现次数不一致，则考虑取较小值）。
  - nums1 = [1,2,2,1], nums2 = [2,2]  result=[2,2]               //也就是一一抵消
- HashMap
  - 记录次数

```java
 public int[] intersect(int[] nums1, int[] nums2) {
        ArrayList<Integer> arr=new ArrayList<>();
        HashMap<Integer,Integer> map=new HashMap<>();
        //把nums1存进去
        for(int i:nums1){
            if(!map.containsKey(i)){
                map.put(i,1);
            }else{
                map.put(i,map.get(i)+1);
            }
        }
        //依次判断nums2
        for(int j:nums2){
            if(map.containsKey(j)&&map.get(j)>0){
                arr.add(j);
                map.put(j,map.get(j)-1);
            }
        }
        //转为整型数组
        int []nums=new int[arr.size()];
        for(int i=0;i<arr.size();i++){
            nums[i]=arr.get(i);
        }
        return nums;
    }
```

### 35.复杂链表的复制(剑指)

- 请实现 copyRandomList 函数，复制一个复杂链表。在复杂链表中，每个节点除了有一个 next 指针指向下一个节点，还有一个 random 指针指向链表中的任意节点或者 null。
- 难点：random指向的对象可能还没有创建
- 用数组保存指向的对象下标

```java
//O(n2)
/*
// Definition for a Node.
class Node {
    int val;
    Node next;
    Node random;

    public Node(int val) {
        this.val = val;
        this.next = null;
        this.random = null;
    }
}
*/
//暴力法
class Solution {
    public Node copyRandomList(Node head) {
        if(head==null){
            return null;
        }

        //一重while()拿到总个数
        Node temp=head;
        int count=0;
        while(temp!=null){
            count++;
            temp=temp.next;
        }

        //两重while()拿到random下标,并存入数组
        // 记录random指针指向的位置
        Node temp1=head;
        int []indexs=new int[count];
        int index=0;
        while(temp1!=null){
            //特殊情况指向null
            if(temp1.random==null){
                indexs[index++]=count;
                temp1=temp1.next;
                continue;
            }

            Node temp2=head;
            int i=0;//记录temp2的下标
            while(temp2!=null){
                if(temp2==temp1.random){
                    indexs[index++]=i;
                    break;
                }
                temp2=temp2.next;
                i++;
            }
            temp1=temp1.next;
        }

        //创建链表，用数组存。除了random都初始化
        Node temp3=head;
        Node[]nodes=new Node[count];
        for(int j=0;j<count;j++){
            nodes[j]=new Node(temp3.val);
            temp3=temp3.next;
        }

        //给链表random引用赋值
        for(int k=0;k<count;k++){
            if(k==count-1){
                nodes[k].next=null;
            }
            else{
                nodes[k].next=nodes[k+1];
            }

            if(indexs[k]==count){
                nodes[k].random=null;
            }else{
                nodes[k].random=nodes[indexs[k]];
            }
               
        }
        return nodes[0];       
    }
}
```

- HashMap把一一对应变为两两对应关系
  - 新旧对象都存进去，旧对象为key，新对象为value

```java
/*
O(n)
每个节点都是2个元素，由一一对应变成了二二对应关系
key存放旧的，value创建一模一样新的
*/
class Solution {
    public Node copyRandomList(Node head) {
        if(head==null){
            return null;//防止空指针异常
        }

        HashMap<Node,Node>map=new HashMap<>();
        Node cur=head;
        while(cur!=null){
            map.put(cur,new Node(cur.val));
            cur=cur.next;
        }

        cur=head;//cur已经没有用，可重复利用
        while(cur!=null){
            //给next和random一起赋值
            map.get(cur).next=map.get(cur.next);
            map.get(cur).random=map.get(cur.random);
            cur=cur.next;
        }

        return map.get(head);
    }
}
```

### 1.两数之和

- 给定一个整数数组 `nums` 和一个整数目标值 `target`，请你在该数组中找出 **和为目标值** *`target`* 的那 **两个** 整数，并返回它们的数组下标。
- HashMap
  - key为当前值，value为下标

```java
    public int[] twoSum(int[] nums, int target) {
        Map<Integer,Integer> map=new HashMap<>();
        for(int i=0;i<nums.length;i++){
            if(map.containsKey(target-nums[i])){
                return new int []{map.get(target-nums[i]),i};
            }else{
                map.put(nums[i],i);
            }
        }
        return new int [0];
    }
```

### 30.串联所有单词的字串

- - 给定一个字符串 `s` 和一个字符串数组 `words`**。** `words` 中所有字符串 **长度相同**。
  
     `s` 中的 **串联子串** 是指一个包含 `words` 中所有字符串以任意顺序排列连接起来的子串。
  
    - 例如，如果 `words = ["ab","cd","ef"]`， 那么 `"abcdef"`， `"abefcd"`，`"cdabef"`， `"cdefab"`，`"efabcd"`， 和 `"efcdab"` 都是串联子串。 `"acdbef"` 不是串联子串，因为他不是任何 `words` 排列的连接。
  
    返回所有串联子串在 `s` 中的开始索引。你可以以 **任意顺序** 返回答案。
- 思路
  - word的次数可能不为1，使用时用全部要用到。也就是使用数量有限制
  - key为word，value为其数量
  - 特殊条件用到就可以了
  - 两个for循环，切分出相应的单词，然后逐一匹配


```java
    public List<Integer> findSubstring(String s, String[] words) {
        List<Integer> res=new ArrayList<>();
        int size=words.length*words[0].length();
        int n=words[0].length();
        for(int i=0;i+size<=s.length();i++){
            //word会重复
            Map<String, Integer>map=new HashMap<>();
            for(int j=i;j+n<=i+size;j+=n){
                String word=s.substring(j,j+n);
                if(!map.containsKey(word)){
                    map.put(word,1);//map存放数量
                }else {
                    map.put(word,map.get(word)+1);
                }
            }
            //根据剩余数量匹配
            for (int j=0;j<words.length;j++){
                if(!map.containsKey(words[j])|| map.get(words[j])<1){
                    break;
                }
                map.put(words[j],map.get(words[j])-1);
                if(j==words.length-1){
                    res.add(i);
                }
            }
        }
        return res;
    }
```

### 187.重复的DNA序列

- 对于一些小的字符串或字符，可以考虑用int来存储。返回所有在 DNA 分子中出现不止一次的 长度为 10 的序列(子字符串)
- 直接暴力HashMap居然可以
  - value存次数

```java
    static final int L=10;
    public List<String> findRepeatedDnaSequences(String s) {
        Map<String,Integer>map=new HashMap<>();
        List<String>res=new ArrayList<>();
        for(int i=0;i+L<=s.length();i++){
            String str=s.substring(i,i+L);
            map.put(str,map.getOrDefault(str,0)+1);
            if(map.get(str)==2){
                res.add(str);
            }
        }
        return res;
    }
```

### 41.缺失的第一个正数

- 给你一个未排序的整数数组 `nums` ，请你找出其中没有出现的最小的正整数。时间O(n),常数空间，这么要求一般是要该原引用了
- nums中的数没有要求
- 下标位置一一对应法。把数组设计成HashSet

```java
    //缺失的只能是1~nums.length+1,所以交换放到对应下标位置。遍历后nums[i]!=i+1就是
    //不想改变原数组的话就自己再创造一个
	//其他的不用管
    public int firstMissingPositive(int[] nums) {
        for(int i=0;i<nums.length;i++){
            while (nums[i]>0 && nums[i]<=nums.length &&
                    i!=nums[i]-1 && nums[i]!=nums[nums[i]-1]){//相同则不换，否则出不了循环
                swap(nums,i,nums[i]-1);
            }
        }
        for(int i=0;i<nums.length;i++){
            if(nums[i]!=i+1){
                return i+1;
            }
        }

        return nums.length+1;
    }

    private void swap(int[] nums,int i,int j){
        int temp=nums[i];
        nums[i]=nums[j];
        nums[j]=temp;
    }
```

### 49.字符异位词分组

- 给你一个字符串数组，请你将 **字母异位词** 组合在一起。可以按任意顺序返回结果列表.**字母异位词** 是由重新排列源单词的字母得到的一个新单词，所有源单词中的字母通常恰好只用一次。
- 给字符排序+HashMap
  - 字符串转为char数组后排序

```java
    public List<List<String>> groupAnagrams(String[] strs) {
        List<List<String>>res=new ArrayList<>();
        Map<String,Integer>map=new HashMap<>();
        int index=0;

        for(String str:strs){
            char[] chars=str.toCharArray();
            Arrays.sort(chars);
            String key=new String(chars);

            if(!map.containsKey(key)){
                map.put(key,index++);
                List<String>list=new ArrayList<>();
                list.add(str);
                res.add(list);
            }else {
                res.get(map.get(key)).add(str);
            }
        }
        return res;
    }
```



## ==Queue==

### 09.用两个栈实现队列(剑指)

- 还是用栈吧，不用其他的
  - 两个栈，一个正序放进，一个逆序放进。取的时候从逆序里取
  - 当逆序栈out为空时先倾倒in的进来，再拿尾部的值

```java

/*
<>不能是基本类型，可用包装类型
 */

class CQueue {

    private Stack<Integer> in;
    private Stack<Integer> out;
    public CQueue() {
        in=new Stack<>();
        out=new Stack<>();
    }
    
    public void appendTail(int value) {
        in.push(value);
    }
    
    public int deleteHead() {
        if(in.empty()&&out.empty()){
            return -1;
        }
        
        else if(out.empty()){
            while(!in.empty()){
                out.push(in.pop());
            } 
        }
        return out.pop();
    }
}
```

## ==List==

### 06.从尾到头打印链表(剑指)

- 输入一个链表的头节点，从尾到头反过来返回每个节点的值（用数组返回）。
- 用Stack很好地完成逆序输出

```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public int[] reversePrint(ListNode head) {
        int count=0;
        //Stack<int> stack=new Stack<>();Integer啊
        Stack<Integer>stack=new Stack<>();

        while(head!=null){
            stack.push(head.val);
            count++;
            head=head.next;
        }
        int []nums=new int[count];


        int i=0;
        while(!stack.empty()){
            nums[i++]=stack.pop();
        }
        return nums;
    }
}
```

- 数组逆序空间复杂度更低
  - 先求出节点数
  - 然后创建数组
  - 基本类型的复杂度更低？

```java
/*
不用Stack更加节省空间，空间复杂度更低
用数组的索引从后往前存就是逆序
*/
class Solution {
    public int[] reversePrint(ListNode head) {
        int count=0;
        ListNode temp=head;
        while(head!=null){
            count++;
            head=head.next;//即使改变head的值也没有关系，引用本身是值传递，不会影响原值
        }
        int []nums=new int[count];
        while(temp!=null){
            nums[--count]=temp.val;//反序
            temp=temp.next;
        }
        return nums;
    }
}
```

### 2.两数相加

- 两个非空的链表，表示两个非负的整数。每位数字逆序存储，每个节点只存储一位数字。将两个数相加，并以相同形式返回一个表示和的链表
- 第一位都是表示个位，直接相加即可
- pre存储上一个结点
  - 先合并短的
  - 一位位相加，记录进位

```java
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode res=new ListNode();//res.next存储结果。
        int c=0;//进位

        ListNode pre=res;
        //把最短的先处理
        while (l1!=null && l2!=null){
            ListNode node=new ListNode();
            int num=l1.val+l2.val+c;
            if(num<=9){
                node.val=num;
                c=0;
            }else if(num<20){
                node.val=num-10;
                c=1;
            }else {
                node.val=num-20;
                c=2;
            }
            l1=l1.next;
            l2=l2.next;
            pre.next=node;
            pre=node;
        }

        //处理没空的  666
        ListNode notNUll=l1!=null?l1:l2;
        while (notNUll!=null){
            ListNode node=new ListNode();
            int num=notNUll.val+c;
            if(num<=9){
                node.val=num;
                c=0;
            }else {
                node.val=num-10;
                c=1;
            }
            notNUll=notNUll.next;
            pre.next=node;
            pre=node;
        }

        //处理最后的进位
        if(c!=0){
            ListNode node=new ListNode();
            node.val=c;
            pre.next=node;
        }      
        return res.next;
    }
```

### 21.合并两个有序链表

- 将两个升序链表合并为一个新的 **升序** 链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的

- 思路
  - 先合并短的
  - 注意空指针，多个判断无妨

```java
public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        if(list1==null)
            return list2;
        if(list2==null)
            return list1;
        
        ListNode res=new ListNode();
        ListNode node=res;
        while(list1!=null && list2!=null){
            if(list1.val<list2.val){
                node.next=list1;
                list1=list1.next;
            }else{
                node.next=list2;
                list2=list2.next;
            }
            node=node.next;
        }
        if(list1!=null)
            node.next=list1;
        if(list2!=null)
            node.next=list2;
        return res.next;
   }
```

### 23.合并k个升序链表

- 全取+排序
- 使用优先队列PriorityQueue<> ，感觉不是一个好方法。
- ArrayList模拟只有头节点的链表

```java
    public ListNode mergeKLists(ListNode[] lists) {
        if(lists==null){
            return null;
        }
        List<ListNode>listArr=new ArrayList<>();
        //全部取出来，此处只需要一次遍历  ArrayList来模拟
        for(ListNode list:lists){
            while (list!=null){
                listArr.add(list);
                list=list.next;
            }
        }
        if(listArr.size()==0){//listArr.get(0);不能成立
            return null;
        }
        //为空[] 或者[null] 可以排不报错，但是[null,3]之类的不行
        listArr.sort(((o1, o2) ->o1.val-o2.val));
        for (int i=0;i<listArr.size()-1;i++){
            listArr.get(i).next=listArr.get(i+1);
        }
        return listArr.get(0);
    }
```

### 24.两两交换链表

- 给你一个链表，两两交换其中相邻的节点，并返回交换后链表的头节点。你必须在不修改节点内部的值的情况下完成本题（即，只能进行节点交换）。
- ArrayList模拟只有头节点的链表。官方的用指针
- 双指针更优

```java
    public ListNode swapPairs(ListNode head) {
        if(head==null){
            return null;
        }
        ListNode node=head;// 引用本身应该是值传递，但是可以通过引用改变对象的值，所以这里可以不保存？
        //只存引用，应该不占用多大空间
        List<ListNode>list=new ArrayList<>();
        while (node != null) {
            list.add(node);
            node=node.next;
        }

        //不转也行，直接用list.set(node)  下次不转了
        ListNode[]arr=list.toArray(new ListNode[0]);
        for(int i=1;i<arr.length;i+=2){
            ListNode temp=arr[i];
            arr[i]=arr[i-1];
            arr[i-1]=temp;
        }
        for(int i=0;i<arr.length-1;i++){
            arr[i].next=arr[i+1];
        }
        arr[arr.length-1].next=null;
        return arr[0];
    }
```

### 25.k个一组反转链表

- 和两两那个差不多
- 还是空间复杂度和时间复杂度的问题
- 解法
  - 指针
  - 取出为线性序列

```java
     public ListNode reverseKGroup(ListNode head, int k) {
        List<ListNode>list=new ArrayList<>();
        while (head!=null){
            list.add(head);
            head=head.next;
        }

        //转是1ms，不转的话是3ms更慢
        ListNode[]arr=list.toArray(new ListNode[0]);
        for(int i=0;i+k-1<arr.length;i+=k){
            reverse(arr,i,i+k-1);
        }
        for(int i=0;i<arr.length-1;i++){
            arr[i].next=arr[i+1];
        }
        arr[arr.length-1].next=null;
        return arr[0];
    }

    private void reverse(ListNode[]arr,int st,int end){
        //i控制次数，j控制下标
        int j=0;
        for(int i=0;i<(end+1-st)/2;i++){
            ListNode temp=arr[st+j];
            arr[st+j]=arr[end-j];
            arr[end-j]=temp;
            j++;
        }
    }
```

### 61.旋转链表

- 给你一个链表的头节点 `head` ，旋转链表，将链表每个节点向右移动 `k` 个位置
- 连成环，666
  - 形成环或者解环

```java
    public ListNode rotateRight(ListNode head, int k) {
        if(head==null){
            return head;
        }
        ListNode head1=head;
        int count=1;
        while (head1.next!=null) {
            count++;
            head1 = head1.next;
        }
        head1.next=head;//连成环

        ListNode res;
        k%=count; //k可能大于count
        for(int i=0;i<count-k-1;i++){
            head=head.next;
        }
        res=head.next;
        head.next=null;
        return res;
    }
```

### 80.删除有序数组的重复项二

- 给你一个有序数组 nums ，请你 原地 删除重复出现的元素，使得出现次数超过两次的元素只出现两次 ，返回删除后数组的新长度。不要使用额外的数组空间，你必须在 原地 修改输入数组 并在使用 O(1) 额外空间的条件下完成
- 双指针，每个指针处理不同的情况
  - 都有序了，这还不简单？

```java
    public int removeDuplicates(int[] nums) {
        int count=1;
        int insert=1;
        for(int i=1;i<nums.length;i++) {
            if(nums[i]==nums[i-1]){
                count++;
            }else {
                count=1;
            }
            if(count<=2){
                if(i!=insert){
                    nums[insert]=nums[i];
                }
                insert++;
            }
        }
        return insert;
    }
```

### 82.删除排序链表中重复元素二

- 给定一个已排序的链表的头 `head` ， *删除原始链表中所有重复数字的节点，只留下不同的数字* 。返回 *已排序的链表*
- 双指针
  - 有序了。

```java
    public ListNode deleteDuplicates(ListNode head) {
        if(head==null){
            return null;
        }
        ListNode root=null;
        ListNode tail=null;
        while (head!=null){
            if(head.next!=null && head.val==head.next.val){
                int value=head.val;
                while (head!=null && head.val==value){ //找到下一不重复的
                    head=head.next;
                }
            }else {
                if(tail==null){//若一开始就重复呢对吧
                    root=head;
                    tail=head;
                }else{
                    tail.next=head;
                    tail=tail.next;   //tail也要更新
                }
                head=head.next;
            }
        }
        if(tail!=null){
            tail.next=null;
        }

        return root;
    }
```

### 83.删除排序链表中元素

- 给定一个已排序的链表的头 `head` ， *删除所有重复的元素，使每个元素只出现一次* 。返回 *已排序的链表*
- 遍历
  - 也是有序了，怎么处理都行

```java
    public ListNode deleteDuplicates(ListNode head) {
        if(head==null){
            return null;
        }

        ListNode root=head;
        ListNode tail=root;
        int preVal=root.val;
        head=head.next;
        while (head!=null){
            if(head.val==preVal){
                while (head!=null && head.val==preVal){
                    preVal=head.val;
                    head=head.next;
                }
            }else {
                tail.next=head;
                tail=tail.next;
                preVal=tail.val;
                head=head.next;
            }
        }
        tail.next=null;
        return root;
    }
```

### 86.分隔链表

- 给你一个链表的头节点 head 和一个特定值 x ，请你对链表进行分隔，使得所有 小于 x 的节点都出现在 大于或等于 x 的节点之前。你应当 保留 两个分区中每个节点的初始相对位置
- 前段后段分别计算
  - 一个个去取
  - 建议通过两个ArrayList直接存

```java
    public ListNode partition(ListNode head, int x) {
        if(head==null){
            return null;
        }
        ListNode mRoot=null;
        ListNode mTail=null;
        ListNode nRoot=null;
        ListNode nTail=null;
        if(head.val<x){
            mRoot=head;
            mTail=head;
        }else {
            nRoot=head;
            nTail=head;
        }
        head=head.next;
        while (head!=null){
            if(head.val<x){
                if(mRoot==null){
                    mRoot=head;
                    mTail=head;
                }else {
                    mTail.next=head;
                    mTail=mTail.next;
                }
            }else {
                if(nRoot==null){
                    nRoot=head;
                    nTail=head;
                }else {
                    nTail.next=head;
                    nTail=nTail.next;
                }
            }
            head=head.next;
        }

        if(mRoot==null){
            return nRoot;
        }
        if(nRoot==null){
            return mRoot;
        }
        mTail.next=nRoot;
        nTail.next=null;
        return mRoot;
    }
```

### 92.反转链表二

- 给你单链表的头指针 head 和两个整数 left 和 right ，其中 left <= right 。请你反转从位置 left 到位置 right 的链表节点，返回 反转后的链表 
- 解法
  - 指针
  - 转为顺序序列

```java
    public ListNode reverseBetween(ListNode head, int left, int right) {
        if(left==right){
            return head;
        }
        //没有前节点
        if(left==1){
            ListNode pre = head;
            ListNode tail = head.next;
            for(int i=0;i<right-left;i++){
                ListNode next = tail.next;
                tail.next=pre;
                pre=tail;
                tail=next;
                head.next=next;
            }
            return pre;
        }

        ListNode preNode=head;
        //拿到left的前一个节点
        for(int i=1;i<left-1;i++){
            preNode=preNode.next;
        }
        ListNode node=preNode.next.next;//  (left,right]的节点
        ListNode tailNode=preNode.next; //中间那段的尾，固定不变
        for(int i=0;i<right-left;i++){ //要换的节点个数为right-left
            ListNode preNext=preNode.next;//原来的头
            ListNode nodeNext=node.next; //
            //插入
            preNode.next=node;
            node.next=preNext;
            //续上尾巴
            tailNode.next=nodeNext;
            node=nodeNext;
        }
        return head;
    }
```

- 指针

  ```java
  class Solution {
      public ListNode reverseBetween(ListNode head, int left, int right) {
          // 设置 dummyNode 是这一类问题的一般做法
          ListNode dummyNode = new ListNode(-1);
          dummyNode.next = head;
          ListNode pre = dummyNode;
          for (int i = 0; i < left - 1; i++) {
              pre = pre.next;
          }
          ListNode cur = pre.next;
          ListNode next;
          for (int i = 0; i < right - left; i++) {
              next = cur.next;   // 临时节点又是在循环内赋值更新，在循环外定义
              cur.next = next.next;
              next.next = pre.next;
              pre.next = next;
          }
          return dummyNode.next;
      }
  }
  ```

  

### 146.LRU缓存机制

- 解法

  - java自带的LinkedHashMap()
- 自带LRU缓存机制，重写removeEldestEntry即可。因为默认的是return false
  
- HashMap+自己实现节点的双向链表
  
- 一

  ```java
  class LRUCache extends LinkedHashMap<Integer,Integer> {
  
      private final int capacity;
      public LRUCache(int capacity) {
          super(capacity,0.75F,true);//0.75是负载因子，和设置最大容量炜capacity不冲突
          this.capacity=capacity;
      }
  
      public int get(int key) {
          return super.getOrDefault(key,-1);
      }
  
      public void put(int key, int value) {
          super.put(key,value);
      }
  
      @Override
      protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
          //这个方法可以用来判断在把新的元素插入到LinkedHashMap中时是否要删除最老（最早插入）的元素。removeEldestEntry方法返回false，即不会自动删除最老的元素
          //返回true就会执行最早插入删除策略
          return this.size()>capacity;
      }
  }
  
  /**
   * Your LRUCache object will be instantiated and called as such:
   * LRUCache obj = new LRUCache(capacity);
   * int param_1 = obj.get(key);
   * obj.put(key,value);
   */
  ```

  

- 二

  - 要注意防止垃圾回收
- HashMap为了存取方便，key为索引值，value为自定义节点，包含索引值和值
  - get的时候更新到头
  - put的时候更新到头，或者新加入到头。再判断是否已经满了，满了则删除尾
  - 自定义几个私有函数来处理双向链表
  
  ```java
      class LRUCache{
          private class DListNode{
              int key;
              int val;
              DListNode pre;
              DListNode next;
          }
          int capacity;
          Map<Integer,DListNode>nodeMap;
          DListNode head;
          DListNode tail;
          LRUCache(int capacity){
              this.capacity=capacity;
              nodeMap=new HashMap<>();
              head=new DListNode();
              tail=new DListNode();
              head.next=tail;
              tail.pre=head;
          }
          public int get(int key){
              DListNode node=nodeMap.get(key);
              if(node==null){return -1;}
              moveToHead(node);
              return node.val;
          }
          public void put(int key,int value){
              DListNode node=nodeMap.get(key);
              if(node!=null){
                  node.val=value;
                  moveToHead(node);
              }else {
                  node=new DListNode();
                  node.key =key;
                  node.val=value;
                  nodeMap.put(key,node);
                  addToHead(node);
                  if(nodeMap.size()>capacity){
                      nodeMap.remove(removeTail().key);
                  }
              }
          }
          private void removeNode(DListNode node){
              node.pre.next=node.next;
              node.next.pre=node.pre;
          }
  
          private DListNode removeTail(){
              DListNode tailNode=tail.pre;
              removeNode(tailNode);
              return tailNode;//防止垃圾回收
          }
          private void moveToHead(DListNode node){
              removeNode(node);
              addToHead(node);
          }
          private void addToHead(DListNode node){
              node.pre=head;
              node.next=head.next;
              head.next.pre=node;
            head.next=node;
          }
      }
  ```
  
  

## ==Stack==

### 20.有效的括号

- 左括号入栈，右括号出栈一个
  - 括号匹配

```java
public boolean isValid(String s) {
        int n=s.length();
        if(n%2==1)//奇数个
            return false;
        Stack<Character> sta=new Stack<>();
        for(int i=0;i<n;i++){
            char c=s.charAt(i);
            if(c=='('||c=='['||c=='{')
                sta.push(c);
            else{
                if(sta.isEmpty())//没有可以匹配
                    return false;
                char top=sta.pop();
                if(c==']' && top!='[' || c==')' && top!='(' || c=='}' && top!='{' )//不匹配
                    return false;
            }
        }
        if(!sta.isEmpty())//未匹配完
            return false;
        return true;
}
```

### 150.逆波兰表达式求值

- 栈

```java
    public int evalRPN(String[] tokens) {
        Stack<Integer>stack=new Stack<>();
        for (String token : tokens) {
            switch (token) {
                case "+" -> stack.push(stack.pop() + stack.pop());
                case "-" -> stack.push(-stack.pop() + stack.pop());
                case "*" -> stack.push(stack.pop() * stack.pop());
                case "/" -> {
                    int a = stack.pop();
                    int b = stack.pop();
                    stack.push(b / a);
                }
                default -> {
                    stack.push(Integer.parseInt(token));
                }
            }
        }
        return stack.pop();
    }
```

### 71.简化路径

- 给你一个字符串 `path` ，表示指向某一文件或目录的 Unix 风格 **绝对路径** （以 `'/'` 开头），请你将其转化为更加简洁的规范路径
- 栈
  - 进来一个处理一个

```java
    public String simplifyPath(String path) {
        Set<String>set=new HashSet<>();
        set.add(".");
        set.add("..");

        Deque<String>queue=new ArrayDeque<>();//存储各级文件名

        int i=0;
        while (i<path.length()){
            int start=i;
            int count=0;
            
            // 拿到 / 和 /之间的数据
            for(int j=i;j<path.length();j++){
                if(path.charAt(j)=='/'){
                    start++;
                }else {
                    break;
                }
            }
            for(int j=start;j<path.length();j++){
                if(path.charAt(j)=='/'){
                    break;
                }
                count++;
            }
            
            

            if(count!=0){//防止/a//  出现/a/   也就是多了个空串
                String sub=path.substring(start,start+count);//前闭后开
                if(!set.contains(sub)){//串
                    queue.push(sub);
                }else if(sub.equals("..")){
                    if(!queue.isEmpty()){
                        queue.pop();
                    }
                }
            }
            i=start+count+1;
        }

        if(queue.isEmpty()){
            return "/";
        }
        StringBuilder strb=new StringBuilder();
        while (!queue.isEmpty()){
            strb.insert(0,queue.pop());
            strb.insert(0,"/");
        }
        return strb.toString();
    }
```

### 32.最长有效括号

- 给你一个只包含 `'('` 和 `')'` 的字符串，找出最长有效（格式正确且连续）括号子串的长度。要连续的！
- 栈
  - 多存一个下标

```java
    //栈
    public int longestValidParentheses(String s) {
        Deque<Character>dequeVal=new ArrayDeque<>();
        Deque<Integer>dequeIndex=new ArrayDeque<>();
        //记录能够组成()的字符的下标
        boolean[]visited=new boolean[s.length()];
        for(int i=0; i<s.length(); i++) {
            char c=s.charAt(i);
            if(dequeVal.isEmpty() || c=='('){
                dequeVal.push(c);
                dequeIndex.push(i);
            } else {
                if(dequeVal.peek()=='('){
                    dequeVal.pop();
                    visited[dequeIndex.pop()]=true;
                    visited[i]=true;
                    // 连续就不需要再入栈
                }else{
                    //不连续了，要隔绝
                    //)也是要入栈，起隔离作用
                    dequeVal.push(c);
                    dequeIndex.push(i);
                }
            }
        }
        //拿到最长连续的true
        int res=0;
        int temp=0;
        for (boolean b : visited) {
            if (b) {
                // 666
                temp++;
            } else {
                temp = 0;
            }
            res = Math.max(res, temp);
        }
        return res;
    }
```

## ==单调栈==

- 用于有边界的求值
- 单调栈是一个持续处理的过程，在遍历时不断更新
- 单调升，拿到临近小。单调降，拿到临近大

### 42.接雨水

- 给定 `n` 个非负整数表示每个宽度为 `1` 的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水。
- 单调栈     存储单调序列
  - 单调递减，拿到临近比它高
  - 处理时一层层取，拿到宽度和高度

```java
//想法不错，就是时间复杂度和空间复杂度有点高    
public int trap(int[] height) {
        int n=height.length;
        //栈记录比左边界小的单调递减序列。
        Stack <Integer>stack=new Stack<>();
        int res=0;
        for(int i=0;i<n;i++){
            int h=height[i];
            //height[i]不是递减，考虑先出栈，直到遇到比height[i]大的数
            while (!stack.isEmpty() && h>height[stack.peek()]){
                int top=stack.pop();
                if(stack.isEmpty())//已经为空说明之前只有一个元素在,下面的i入栈是更新左边界
                    break;//左边界上是不可能储水的
                int left=stack.peek();//把前一个作为边界
                int curWidth=i-left-1;
                int curHeight=Math.min(h,height[left])-height[top];//一层层取水
                res+=curWidth*curHeight;
            }
            stack.push(i); //下标入栈
        }
        return res;
    }
```

### 30.包含min函数的栈(剑指)

- min返回最小值。min、push 及 pop 的时间复杂度都是 O(1)
- 辅助单调栈
  - 维持一个递减的序列（并不需要所有的元素都在里面）

```java
/*
返回最小值
*/
class MinStack {
    private Stack<Integer>A,B;

    /** initialize your data structure here. */
    public MinStack() {
        A=new Stack<>();
        B=new Stack<>();
    }
    
    public void push(int x) {
        A.push(x);
        if(B.empty()||B.peek()>=x){//要等于，万一重复的一次只可以取出一个
            B.push(x);
        }
    }
    
    public void pop() {
        if(A.pop().equals(B.peek())){//类型时Integer，所以要用equals
            B.pop();
        }
    }
    
    public int top() {
        return A.peek();
    }
    
    public int min() {
        return B.peek();
    }
}
```

### 84.柱状图中最大的矩形

- 给定 *n* 个非负整数，用来表示柱状图中各个柱子的高度。每个柱子彼此相邻，且宽度为 1 。求在该柱状图中，能够勾勒出来的矩形的最大面积。（其高度可能为0
- 如果是以某个矩形的高作为高，可以考虑双单调升栈
- 单调栈（升

```java
    //单调升栈。遇到小的则出栈，出栈的柱子要小于未入栈的。左边界是下一出栈元素下标（因为可能是-1），右边界是未入栈元素下标计算
    public int largestRectangleArea(int[] heights) {
        Deque<Integer>valQueue = new ArrayDeque<>();//值栈
        Deque<Integer>indexQueue = new ArrayDeque<>();//下标栈
        int result=0;
        int start=-1;//上一个为0的位置
        for(int i=0;i<heights.length;i++) {
            if(valQueue.isEmpty() || heights[i]>=valQueue.peek()){//第一个先、升序放进去
                valQueue.push(heights[i]);
                indexQueue.push(i);
                if(heights[i]==0){
                    start=i;
                }
            }else {
                while (heights[i]<valQueue.peek()){ //以栈顶元素为高的正方形一定止步于此
                    int val=valQueue.pop();
                    int index=indexQueue.pop();
                    if(valQueue.isEmpty()){
                        result=Math.max(result,(i-1-start)*val);   //6，7，5，2，4  5出栈时里面就是空了    左边界是上一个0位置
                        break;
                    }else {
                        result=Math.max(result,(i-1-indexQueue.peek())*val);  //左边界是栈顶元素下标
                    }
                }
                //此时栈里，左边没有比heights[i]大且还没出栈的
                valQueue.push(heights[i]);
                indexQueue.push(i);
                if(heights[i]==0){ //最后才更新start，因为上面要用上一个start
                    start=i;
                }
            }
        }
        //处理剩下的
        while (!valQueue.isEmpty()){
            int val=valQueue.pop();
            int index=indexQueue.pop();
            if(valQueue.isEmpty() || valQueue.peek()==0){//剩下的0不用处理了
                result=Math.max(result, (heights.length-1-start)*val);   //6，7，5，2，4  5出栈时里面就是空了。要用start找到上一个为0的位置
                break;
            }else {
                result=Math.max(result,(heights.length-1-indexQueue.peek())*val);
            }
        }
        return result;
    }
```

### 135.分发糖果

- `n` 个孩子站成一排
  - 每个孩子至少分配到 `1` 个糖果。
  - 相邻两个孩子评分更高的孩子会获得更多的糖果
  - 相同分的没有要求

- 2次遍历

  - 有点像单调栈，所以就在这里写了
  - 一向左一向右
  - 已经能拿到所有的次序信息  O(n)
  - 下次再写

  ```java
  import java.util.*;
  class Solution {
      public int candy(int[] ratings) {
          int []left=new int[ratings.length];//从左往右
          int []right=new int[ratings.length];
          int sum=0;
          for(int i=1;i<ratings.length;i++){
              if(ratings[i]>ratings[i-1]){
                  left[i]=left[i-1]+1;
              } 
          }
          for(int i=ratings.length-2;i>=0;i--){
              if(ratings[i]>ratings[i+1]){
                  right[i]=right[i+1]+1;
              }   
          }
          //左和右只要最大值
          for(int i=0;i<ratings.length;i++){
              sum+=Math.max(left[i],right[i])+1;
          }
          return sum;
      }
  }
  ```

  

## ==内部类==

### 73.矩阵置零

- 给定一个 `m x n` 的矩阵，如果一个元素为 **0** ，则将其所在行和列的所有元素都设为 **0** 。请使用原地算法，即输出是对输入的改变
- HashMap  key=i   value=j的话无法存同一行的两个为0的列。
- 用局部内部类+List来存数据。内部类表示所在行，所在列
- 二维平面坐标 类

```java
 public void setZeroes(int[][] matrix) {
        class index{//局部内部类
            int line;
            int row;
            index(int i,int j){
                line=i;
                row=j;
            }
        }
        List <index> list=new ArrayList<>();
        for(int i=0;i<matrix.length;i++){//列
            for(int j=0;j<matrix[0].length;j++){
                if(matrix[i][j]==0){
                    list.add(new index(i,j));//标记为0的点
                }
            }
        }
        for(index in:list){
            setZero(matrix,in.line,in.row);//置零
        }

    }

  public void setZero(int [][] matrix,int line,int row){
        for(int k=0;k<matrix[0].length;k++){//行
            matrix[line][k]=0;
        }
        for(int k=0;k<matrix.length;k++){
            matrix[k][row]=0;
        }
    }
```

## ==数组==

### 387. 字符串中的第一个唯一字符

- 给定一个字符串 `s` ，找到 它的第一个不重复的字符，并返回它的索引* 。如果不存在，则返回 `-1` 。
  - s 只包含小写字母
- HashMap存储出现次数
  - 33ms

```java
public class Solution {
    public int firstUniqChar(String s) {
        HashMap<Character,Integer> map=new HashMap<>();
        for(int i=0;i<s.length();i++){
            char c=s.charAt(i);
            if(!map.containsKey(c)){
                map.put(c,1);
            }else {
                map.put(c,map.get(c)+1);
            }
        }
        for (int i=0;i<s.length();i++){
            if(map.get(s.charAt(i))==1){
                return i;
            }
        }
        return -1;
    }
```

- int数组存储
  - 6ms，比HashMap快很多
  - 下标i与数据有一一对应关系，值[i]记录出现次数

```java
public int firstUniqChar(String s) {
    	//目前只适用于字母
       	int[] arr = new int[26];//26个的话只适合都是小写或大写。要所有字母的话就声明2个
        int n = s.length();
        for (int i = 0; i < n; i++) {
            arr[s.charAt(i)-'a']++ ;
        }
        for (int i = 0; i < n; i++) {
            if (arr[s.charAt(i)-'a'] == 1) {
                return i;
            }
        }
        return -1;
}
```

- String的方法（还不错
  - 25ms

```java
public int firstUniqChar(String s) {
        for (int i = 0; i < s.length(); i++) {
            //第一次出现和最后一次出现位置一样
            if (s.indexOf(s.charAt(i)) == s.lastIndexOf(s.charAt(i))) {
                return i;
            }
        }
        return -1;
}
```

### 383. 赎金信

- 给你两个字符串：`ransomNote` 和 `magazine` ，判断 `ransomNote` 能不能由 `magazine` 里面的字符构成。
  - `ransomNote` 和 `magazine` 由小写英文字母组成
  - `magazine` 中的每个字符只能在 `ransomNote` 中使用一次
- 用int[26]存char和int的映射关系

```java
    public boolean canConstruct(String ransomNote, String magazine) {
        int []nums=new int[26];
        for(int i=0;i<ransomNote.length();i++){
            nums[ransomNote.charAt(i)-'a']++;
        }
        for(int i=0;i<magazine.length();i++){
            nums[magazine.charAt(i)-'a']--;
        }

        for(int i=0;i<26;i++){
            if(nums[i]>0){
                return false;
            }
        }
        return true;
  }
```

## ==二叉树==

### 94.二叉树的中序遍历

- 非递归
  - 能很好拿到遍历的前一个节点
  - 一直向左遍历，当不是最左时，直接入栈，直到栈顶元素为最左。
  - 取栈顶元素值，root节点等于栈顶元素的右节点
  - 重复直到root为null或者stack为空

```java
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> res=new ArrayList<>();
        Stack<TreeNode>stack=new Stack<>();
        TreeNode node=root;  //只是为了不改变原引用
		
        TreeNode pre=root;
        
        while (node!=null || !stack.isEmpty()){  //root或stack不为空
            while (node!=null){
                stack.push(node);  //当前节点入栈
                node= node.left;  //遍历到最左
            }
            node=stack.pop(); //当前节点出栈并取值
            res.add(node.val);
            pre=node;   //访问过的
            node=node.right;//走一步右
        }
        return res;
    }
```

- 递归
  -  可以自己再定义一个递归函数

```java
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> res=new ArrayList<>();
        getList(root,res);
        return res;
    }

    public void getList(TreeNode root,List<Integer>list){
        if(root==null){  //临界条件，当前为null就行了
            return;
        }
        //有什么操作就执行什么，注意顺序
        getList(root.left,list);
        list.add(root.val);
        getList(root.right,list);
    }
```

### 95.不同的二叉搜索树二（懂了）

- 回溯
  - 递归方法有返回值。思路清晰，代码不用写多少
- 至今不太懂
  - 二叉树不一定是要在中点分，在st和end之间的任意点都可以
  - 这样就形成了不同的二叉树
  - 在dfs中遍历即可

```java
    public List<TreeNode> generateTrees(int n) {
        if(n==0){
            return new ArrayList<TreeNode>();
        }
        return generateTrees(1,n);//数字是1到n
    }

    public List<TreeNode> generateTrees(int st,int end){
        List<TreeNode>allTrees=new ArrayList<>();
        if(st>end){
            allTrees.add(null);   //为什么要add(null)?
            return allTrees;
        }
        //枚举所有可行根节点
        for(int i=st;i<=end;i++){
            //生成左右，因为已经排好序了，左比右小
            List<TreeNode> leftTrees=generateTrees(st,i-1);  // 左右都是一批子树
            List<TreeNode> rightTrees=generateTrees(i+1,end);
            //以i作为根节点合并
            for(TreeNode left:leftTrees){
                for(TreeNode right:rightTrees){
                    TreeNode node=new TreeNode(i);
                    node.left=left;
                    node.right=right;
                    allTrees.add(node);  
                }
            }
        }
        return allTrees;
    }
```

### 144.二叉树的前序遍历

- 递归

```java
    List<Integer> res = new ArrayList<>();
    public List<Integer> preorderTraversal(TreeNode root) {
        if (root != null) {
            res.add(root.val);
            preorderTraversal(root.left);
            preorderTraversal(root.right);
        }
        return res;
    }
```

### 145.二叉树的后序遍历

- 递归

```java
    List<Integer>res=new ArrayList<>();
    public List<Integer> postorderTraversal(TreeNode root) {
        if(root!=null){
            postorderTraversal(root.left);
            postorderTraversal(root.right);
            res.add(root.val);
        }
        return res;
    }
```

### 102.二叉树的层序遍历

- 2个栈，一个当前层，一个下一层
- 其实一个栈也可以，记录下栈的长度

```java
    List<List<Integer>>res=new ArrayList<>();
    Deque<TreeNode>deque1=new ArrayDeque<>();
    Deque<TreeNode>deque2=new ArrayDeque<>();
    public List<List<Integer>> levelOrder(TreeNode root) {
         if(root!=null){
             deque1.addLast(root);
         }
        if(!deque1.isEmpty() || !deque2.isEmpty()){
            getRes();
        }
         
        return res;
    }
    
    private void getRes(){

        List<Integer>list=new ArrayList<>();
        Deque<TreeNode>empty=deque1.isEmpty() ? deque1:deque2;
        Deque<TreeNode>noEmpty=deque1.isEmpty() ?deque2:deque1;
        
        while (!noEmpty.isEmpty()){
            list.add(noEmpty.getFirst().val);
            if(noEmpty.getFirst().left!=null){
                empty.add(noEmpty.getFirst().left);
            }
            if(noEmpty.getFirst().right!=null){
                empty.add(noEmpty.getFirst().right);
            }
            noEmpty.removeFirst();
        }
        res.add(list);

        if(!deque1.isEmpty() || !deque2.isEmpty()){
            getRes();
        }
    }
```

### 107.二叉树的层序遍历二

- 从下往上遍历
- res.add(0,list);//搞定
- 结果集插入到链表头而已

```java
    List<List<Integer>>res=new ArrayList<>();
    Deque<TreeNode>deque1=new ArrayDeque<>();
    Deque<TreeNode>deque2=new ArrayDeque<>();
    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        if(root!=null){
            deque1.addLast(root);
        }
        if(!deque1.isEmpty() || !deque2.isEmpty()){
            getRes();
        }

        return res;
    }

    private void getRes(){

        List<Integer>list=new ArrayList<>();
        Deque<TreeNode>empty=deque1.isEmpty() ? deque1:deque2;
        Deque<TreeNode>noEmpty=deque1.isEmpty() ?deque2:deque1;

        while (!noEmpty.isEmpty()){
            list.add(noEmpty.getFirst().val);
            if(noEmpty.getFirst().left!=null){
                empty.add(noEmpty.getFirst().left);
            }
            if(noEmpty.getFirst().right!=null){
                empty.add(noEmpty.getFirst().right);
            }
            noEmpty.removeFirst();
        }
        res.add(0,list);

        if(!deque1.isEmpty() || !deque2.isEmpty()){
            getRes();
        }
    }
```

### 101.对称二叉树，666

- 给你一个二叉树的根节点 `root` ， 检查它是否轴对称
- 递归调用判断

```java
    //镜像递归。神仙的简单题
    public boolean isSymmetric(TreeNode root) {
        return check(root,root);
    }

    private boolean check(TreeNode left,TreeNode right) {
        if(left==null && right==null){
            return true;
        }
        if(left==null || right==null){
            return false;
        }
        return left.val== right.val && check(left.left,right.right) && check(left.right,right.left);
    }
```

### 103.二叉树的锯齿形层序遍历

- 相隔的2层一层左到右，一层右到左
- 其实用一个队列就可以了
- boolean isRight=true;  //记录方向
- 多一个方向变量

```java
    List<List<Integer>>res=new ArrayList<>();
    Deque<TreeNode>deque=new ArrayDeque<>();
    boolean isRight=true;
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        if(root!=null){
            deque.add(root);
            getRes();
        }
        return res;
    }
    private void getRes(){
        List<Integer>list=new ArrayList<>();
        int i=0;
        int size=deque.size();
        while (i++<size){
            TreeNode node=deque.getFirst();
            if(isRight){//方向
                list.add(node.val);
            }else {
                list.add(0,node.val);
            }

            if(node.left!=null){
                deque.add(node.left);
            }
            if (node.right!=null){
                deque.add(node.right);
            }
            deque.removeFirst();
        }
        isRight=!isRight;
        res.add(list);
        if(!deque.isEmpty()){
            getRes();
        }
    }
```

### 104.二叉树的最大深度

- 深度优先
  - 从最底层进行累加

```java
    public int maxDepth(TreeNode root) {
        if(root==null){
            return 0;
        }
        return Math.max(maxDepth(root.left),maxDepth(root.right))+1;
    }
```

- 广度优先（层序遍历）

```java
    int count = 0;
    Deque<TreeNode>deque=new ArrayDeque<>();
    public int maxDepth(TreeNode root) {
        if (root != null) {
            deque.add(root);
            getRes();
        }
        return count;
    }
    private void getRes(){
        count++;
        int i=0;
        int size=deque.size();
        while (i++<size){
            TreeNode node=deque.getFirst();
            if(node.left!=null){
                deque.add(node.left);
            }
            if (node.right!=null){
                deque.add(node.right);
            }
            deque.removeFirst();
        }
        if(!deque.isEmpty()){
            getRes();
        }
    }
```

### 114.二叉树展开为链表

- 要求前序遍历。

```java
    List<TreeNode>list=new ArrayList<>();
    public void flatten(TreeNode root) {
        if(root==null){
            return;
        }
        getRes(root);
        for(int i=0;i<list.size()-1;i++) {
            list.get(i).left=null;
            list.get(i).right=list.get(i+1);
        }
        list.get(list.size()-1).left=null;
        list.get(list.size()-1).right=null;
    }

    private void getRes(TreeNode root){
        if(root!=null){
            list.add(root);
            getRes(root.left);
            getRes(root.right);
        }
    }
```

### 111.二叉树的最小深度

- 广度优先
  - 层序遍历，找到就停止

```java
    Deque<TreeNode> now=new ArrayDeque<>();//当前层
    Deque<TreeNode> next=new ArrayDeque<>();//下一层
    int depth;
    public int minDepth(TreeNode root) {
        if(root!=null){
            now.add(root);
        }

        getResult();
        return depth;
    }

    public void getResult(){
        if(!now.isEmpty()){
            depth++;
            while (!now.isEmpty()){
                TreeNode node=now.getFirst();
                if(node.left==null && node.right==null){
                    return;
                }
                if(node.left!=null){
                    next.add(node.left);
                }
                if(node.right!=null){
                    next.add(node.right);
                }
                now.removeFirst();
            }
            //交换当前层和下一层
            Deque<TreeNode>temp=now;
            now=next;
            next=temp;
            getResult();
        }
    }
```

### 108.将有序数组转化为二叉搜索树

- 先根节点
- 递归
- 这里没啥要求，就用中点吧
- i==j 下面还要有个null，所以退出条件为i>j

```java
    public TreeNode sortedArrayToBST(int[] nums) {
        return getRoot(nums,0,nums.length-1);
    }
    
    public TreeNode getRoot(int []nums,int i,int j){
        if(i>j){
            return null;
        }
        int middle=(i+j)>>1;
        TreeNode root=new TreeNode(nums[middle]);
        root.left=getRoot(nums,i,middle-1);
        root.right=getRoot(nums,middle+1,j);
        return root;
    }
```

### 109.有序链表转为二叉搜索树（双指针找中点）

- 转为数组（时间有点慢

```java
public TreeNode sortedListToBST(ListNode head) {
        List<Integer>list=new ArrayList<>();
        while (head!=null){
            list.add(head.val);
            head=head.next;
        }
        return getRoot(list,0,list.size()-1);
    }

    public TreeNode getRoot(List<Integer>list,int i,int j){
        if(i>j){
            return null;
        }
        int middle=(i+j)>>1;
        TreeNode root=new TreeNode(list.get(middle));
        root.left=getRoot(list,i,middle-1);
        root.right=getRoot(list,middle+1,j);
        return root;
    }
```

- 快慢指针找中间根节点（官方

```java
class Solution {
    public TreeNode sortedListToBST(ListNode head) {
        return buildTree(head, null);
    }

    public TreeNode buildTree(ListNode left, ListNode right) {
        if (left == right) {
            return null;
        }
        ListNode mid = getMedian(left, right);
        TreeNode root = new TreeNode(mid.val);
        root.left = buildTree(left, mid);
        root.right = buildTree(mid.next, right);
        return root;
    }

    public ListNode getMedian(ListNode left, ListNode right) {
        ListNode fast = left;
        ListNode slow = left;
        while (fast != right && fast.next != right) {
            fast = fast.next;
            fast = fast.next;
            slow = slow.next;
        }
        return slow;
    }
}
```

### 110.平衡二叉树

- 左右子树高度差不超过1
  - 对所有节点进行深度优先。（两个深度优先遍历
  - 自底向上累加

```java
    public boolean isBalanced(TreeNode root) {
        if(root==null){
            return true;
        }
        return Math.abs(maxDepth(root.left)-maxDepth(root.right))<=1
                && isBalanced(root.left) && isBalanced(root.right);
    }

    public int maxDepth(TreeNode root) {
        if(root==null){
            return 0;
        }
        return Math.max(maxDepth(root.left),maxDepth(root.right))+1;
    }
```

### 112.路径总和

- 根节点到叶子节点路径上的值的总和是否等于目标值
  - 递归
  - 自底向上累加

```java
    public boolean hasPathSum(TreeNode root, int targetSum) {
        if(root==null){
            return false;
        }
        if(targetSum==root.val && root.left==null && root.right==null){
            return true;
        }

        return hasPathSum(root.left,targetSum-root.val)
                || hasPathSum(root.right,targetSum-root.val);

    }
```

### 113.路径总和二

- 记录112所有满足的路径
  - 全局变量记录，要新建

```java
    List<Integer>list=new ArrayList<>();
    List<List<Integer>>res=new ArrayList<>();
    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        if(root==null){
            return res;
        }
        list.add(root.val);
        if(root.val==targetSum && root.left==null && root.right==null){
            res.add(new ArrayList<>(list));//要新建
        }
        pathSum(root.left,targetSum-root.val);
        pathSum(root.right,targetSum-root.val);
        list.remove(list.size()-1);
        return res;
    }
```

### 100.相同的树

- 判断两棵树结构和值是否相同
- dfs
  - 值相等即可

```java
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if(p==null && q==null){
            return true;
        }else if(p!=null && q!=null ){
            if(p.val!=q.val){
                return false;
            }else {
                return isSameTree(p.left,q.left) && isSameTree(p.right,q.right);
            }
        }else {
            return false;
        }
    }

```

### 124.二叉树中的最大路径和

- 路径 被定义为一条从树中任意节点出发，沿父节点-子节点连接，达到任意节点的序列。同一个节点在一条路径序列中 至多出现一次 。该路径 至少包含一个 节点，且不一定经过根节点。**路径和** 是路径中各节点值的总和。
- 返回值中左右节点只能取一个（向上返回，当前结果不作为根节点），更新结果时则左右都要算（当前节点作为根节点）
- 可包含父节点，还是向上累加

```java
    int maxVal;
    //同一个节点在一条路径序列中 至多出现一次
    public int maxPathSum(TreeNode root) {
        maxVal = root.val;
        dfs(root);
        return maxVal;
    }

    //返回值和要取的值有所不同的dfs
    public int dfs(TreeNode node) {
        if(node==null){
            return 0;
        }

        int leftVal=Math.max(dfs(node.left),0);
        int rightVal=Math.max(dfs(node.right),0);
        maxVal=Math.max(maxVal,leftVal+rightVal+node.val);

        //只能加左或者右一个，否则可能路径重复
        return node.val+Math.max(leftVal,rightVal);
    }
```

### 116.填充每个节点的下一个右侧节点指针

- 给定一个 **完美二叉树** ，填充它的每个 next 指针，让这个指针指向其下一个右侧节点。如果找不到下一个右侧节点，则将 next 指针设置为 `NULL`
- 层序思想
  - 一直拿到左边的就行了
  - 保留两层数据

```java
    //上一行本身就用指针完成了一个层序遍历，所以不需要额外的n空间的栈存储
    public Node connect(Node root) {
        if(root==null || root.left==null){
            return root;
        }
        Node firstNode=root.left; //下一个要层序遍历的
        Node head=root;
        while (root!=null){
            root.left.next=root.right;  // 前面一层已经有顺序了
            if(root.next!=null){
                root.right.next=root.next.left;
            }
            root=root.next;
        }
        connect(firstNode);
        //所以为什么要返回呢  ，就当拿到一个原引用？但是引用本身不是值传递吗
        return head;
    }
```

### 117.填充每个节点的下一个右侧节点指针

- 给定一个 **二叉树** ，填充它的每个 next 指针，让这个指针指向其下一个右侧节点。如果找不到下一个右侧节点，则将 next 指针设置为 `NULL`
- 层序思想+多2个变量保存一些结果而已

```java
    public Node connect(Node root) {
        if(root==null){
            return null;
        }
        Node firstNode=null;
        Node head =root;
        //拿到下一层的左节点
        while (root!=null){
            if(root.left!=null){
                firstNode=root.left;
                break;
            }
            if(root.right!=null){
                firstNode=root.right;
                break;
            }
            root=root.next;
        }
        //并没有下一层
        if(firstNode==null){
            return head;
        }

        //初始化一个前节点
        Node pre=firstNode;
        if(root.left!=null && root.right!=null){
            root.left.next=root.right;
            pre=root.right;
        }
        root=root.next;
        //前节点指向当前非空的，并更新前节点
        while (root!=null){
            if(root.left!=null){
                pre.next=root.left;
                pre=root.left;
            }
            if(root.right!=null){
                pre.next=root.right;
                pre=root.right;
            }
            root=root.next;
        }
        connect(firstNode); // 这里应该是pre也可以？
        return head;
    }
```



# 算法

## ==动态规划==

- 既然用了dp，就不要想得那么复杂
- 有时要注重哪个变量在内循环，哪个变量在外循环

### 53. 最大子数组和

- 给你一个整数数组 `nums` ，请你找出一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
- 用 f(i)代表以第 i个数结尾的「连续子数组的最大和]。
- dp
  - nums[i]肯定有负数
  - 这是要或者不要前一个，而不是当前这个

```java
 public int maxSubArray(int[] nums) {
      int pre=0;//第-1个为0
      int maxVal=nums[0];
     
      for(int num:nums){
          //要么以num结尾，要么以num开始。因为num可能为0
          pre=Math.max(pre+num,num);
          maxVal=Math.max(pre,maxVal);
      }
      return maxVal;
    }
```

### 62.不同路径

- 向下或向右。m*n格，有多少条不同路径到达右下角
- 动态规划       填二维表

```java
    public int uniquePaths(int m, int n) {
        int [][]dp=new int[m][n];
        for(int i=0;i<n;i++)
            dp[0][i]=1;
        for(int i=0;i<m;i++)
            dp[i][0]=1;
        for(int i=1;i<m;i++){
            for(int j=1;j<n;j++)
                dp[i][j]=dp[i-1][j]+1+dp[i][j-1];//填二维表
        }
        return dp[m-1][n-1];
    }
```

- 数学排列组合

### 64.最小路径和

- 给定一个包含非负整数的 `*m* x *n*` 网格 `grid` ，请找出一条从左上角到右下角的路径，使得路径上的数字总和为最小。

  **说明：**每次只能向下或者向右移动一步

```java
//dp     和62一样，只不过权重不再是1
public int minPathSum(int[][] grid) {
        int n=grid.length;
        int m=grid[0].length;
        int [][]dp=new int[n][m];
        dp[0][0]=grid[0][0];
        for(int i=1;i<m;i++)
            dp[0][i]=grid[0][i]+dp[0][i-1];
        for(int i=1;i<n;i++)
            dp[i][0]=grid[i][0]+dp[i-1][0];

        for(int i=1;i<n;i++){
            for(int j=1;j<m;j++)
                dp[i][j]=grid[i][j]+Math.min(dp[i-1][j],dp[i][j-1]);
        }
        return dp[n-1][m-1];
    }
```

### 70.爬楼梯

- 假设你正在爬楼梯。需要 `n` 阶你才能到达楼顶。每次你可以爬 `1` 或 `2` 个台阶。你有多少种不同的方法可以爬到楼顶呢？

```java
//dp   
public int climbStairs(int n) {
        if(n==1)
            return 1;
        if(n==2)
            return 2;
        int []dp=new int[n];
        dp[0]=1;
        dp[1]=2;
        for(int i=2;i<n;i++)
            dp[i]=dp[i-1]+dp[i-2];
        return dp[n-1];
    }
```

### 85.最大矩形

- 给定一个仅包含 `0` 和 `1` 、大小为 `rows x cols` 的二维二进制矩阵，找出只包含 `1` 的最大矩形，并返回其面积。

```java
//dp填2张二维表。当前节点的最大宽度和高度
public int maximalRectangle(char[][] matrix) {
        int res=0;
        int n=matrix.length;
        int m=matrix[0].length;
        int [][]dpW=new int[n][m];//最大宽
        int [][]dpH=new int[n][m];
        
        for(int i=0;i<n;i++){
            dpW[i][0]=matrix[i][0]=='1'?1:0;//dp都要有初始值。
            for(int j=1;j<m;j++){
                if(matrix[i][j]=='1'){
                    dpW[i][j]=1+dpW[i][j-1];//连续的1则累加
                }
            }
        }
        for(int j=0;j<m;j++){
            dpH[0][j]=matrix[0][j]=='1'?1:0;
            for(int i=1;i<n;i++){
                if(matrix[i][j]=='1'){
                    dpH[i][j]=1+dpH[i-1][j];
                }
            }
        }

        for(int i=0;i<n;i++){//以matrix[i][j]作为右下角的矩形
            for(int j=0;j<m;j++){
                if(dpW[i][j]!=0) {
                    int x = j;
                    int minH = dpH[i][j];
                    while (x>=0 && dpW[i][x] != 0) {
                        if (dpH[i][x] < minH)   //不断更新最小高度，也就是边界高度
                            minH = dpH[i][x];
                        int area = minH * (j - x + 1);//宽度*最小高度
                        if (area > res)
                            res = area;
                        x--;     //宽向左延伸 
                    }
                }
            }
        }
        return res;
    }
```

- 单调栈
  - 写完84题再补充

### 63.不同路径二

- m*n 只能向右、向下，并且有障碍物在任何位置
- 二维数组上dp不是递归。。

```java
    int m;
    int n;
    int [][] dp;
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        m=obstacleGrid.length;
        n=obstacleGrid[0].length;
        dp=new int[m][n];

        //初始化第一行第一列
        for(int i=0;i<m;i++){
            if(obstacleGrid[i][0]==1){//有石头挡住后面的都默认值为0
                break;
            }
            dp[i][0]=1;
        }
        for(int i=0;i<n;i++){
            if(obstacleGrid[0][i]==1){
                break;
            }
            dp[0][i]=1;
        }

        //dp是遍历不是递归，这里只是抽出一个函数
        for(int i=1;i<m;i++){
            for(int j=1;j<n;j++){
                getRes(obstacleGrid,i,j);
            }
        }
        return dp[m-1][n-1]==-1?0:dp[m-1][n-1];
    }

    // 这是动态规划。每个位置都填一下
    public void getRes(int [][] obstaclesGrid,int x,int y){
        if(x>=m || y>=n){
            return;
        }
        if(obstaclesGrid[x][y]==1){
            dp[x][y]=-1;// 这里有石头，标记下
        }else {
            if(x>0 && dp[x-1][y]!=-1){
                dp[x][y]+=dp[x-1][y];
            }
            if(y>0 && dp[x][y-1]!=-1){
                dp[x][y]+=dp[x][y-1];
            }
        }
    }
```

### 123.买股票的最佳时机三

- 给定一个数组，它的第 `i` 个元素是一支给定的股票在第 `i` 天的价格。设计一个算法来计算你所能获取的最大利润。你最多可以完成 **两笔** 交易
- 模拟最后一天状态
  - 先把能算的先算了

```java
    //此处dp为模拟最后一天状态。
    public int maxProfit(int[] prices) {
        //5种情况。还有一种当天不动，不会产生利润变化，不作考虑
        //dp初始值
        int buy1=-prices[0];   //第一天买   。没有利润，亦可以理解为负数
        int sell1=0; //第一天买后，再卖
        int buy2=-prices[0]; //第一天买了后，再卖，再买
        int sell2=0; //第一天买了后，再卖，再买，再卖
        for(int i=1;i<prices.length;i++){
            buy1=Math.max(buy1,-prices[i]);
            sell1=Math.max(sell1,prices[i]+buy1);
            buy2=Math.max(buy2,sell1-prices[i]);
            sell2=Math.max(sell2,prices[i]+buy2);
        }
        return sell2;
    }
```

### 10.正则表达式匹配

- 给你一个字符串 `s` 和一个字符规律 `p`，请你来实现一个支持 `'.'` 和 `'*'` 的正则表达式匹配
  - `'.'` 匹配任意单个字符
  - `'*'` 匹配零个或多个前面的那一个元素  （能把前面那个变消失。。
  - s、p的长度至少为1
- dp
  - 是否能够匹配，结果只有true和false
  - 正则表达式的*会消除前面的字母
  - *后面可能是 . 或者字母
  - 表达式在内循环
- 正则表达式
  - 是点 . 表示单个字符
  - *是前面的-1,0,1...n重复

```java
    public boolean isMatch(String s, String p) {
        //p作为dp子序列
        boolean[][] dp = new boolean[p.length() + 1][s.length() + 1];
        dp[0][0] = true;//增加一行初始值，方便处理。（常为空""

        for (int i = 1; i <= p.length(); i++) {
            boolean flag = false;
            for (int j = 1; j <= s.length(); j++) {
                //p.charAt(i-1)  可为字母 . *
                // 这三种都要用到dp[i-1][j-1]
                if (p.charAt(i - 1) >= 'a' && p.charAt(i - 1) <= 'z') {
                    dp[i][j] = p.charAt(i - 1) == s.charAt(j - 1) && dp[i - 1][j - 1];
                } else if (p.charAt(i - 1) == '.') {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    //*   
                    // *不会在开头  也不会出现连续*
                    // 为*时要判断前一个，
                    //若是字母，则dp[i-1][j-1]为true时  且s.charAt(j)==s.charAt(j-1)   则dp[i][j]为true  继续匹配，相等则也为true

                    //*还可以让前面匹配的字母消失    如aab  c*a*b   应该返回true
                    //匹配-1个，0个
                    if (!flag) {
                        //*可能匹配断断续续的，但是合并一次就够了，否则可能会把dp[i][l]为true的变为false
                        //运行效率大大提升了。。每次合并dp[i-2][l] || dp[i-1][l] || dp[i][l] 要108ms
                        //而只合并一次只执行了2ms
                        for (int l = 0; l < dp[0].length; l++) {
                            //dp[i-2][l]  表示拿走前一个字母，也就是-1个
                            ///dp[i-1][l]  表示不拿走也不增加，也就是0个
                            dp[i][l] = dp[i - 2][l] || dp[i - 1][l];
                        }
                        flag = true;
                    }

                    //若是.  dp[i-1][j-1]为true时，dp[i][j]到dp[i][结尾]都是true;
                    if (p.charAt(i - 2) == '.') {
                        if (dp[i - 1][j - 1]) { //从头顶下来直接匹配
                            for (int k = j; k <= s.length(); k++) {
                                dp[i][k] = true;
                            }
                            break;
                        }
                    } else {
                        // 字母，增加1个 ...n个
                        if (dp[i - 1][j - 1]) {
                            if (s.charAt(j - 1) == s.charAt(j - 2)) {
                                for (int k = j; k <= s.length(); k++) {
                                    //匹配连续且相同的
                                    if (s.charAt(k - 1) != s.charAt(j - 2)) {
                                        //可能还没匹配完，不知道。之后结束后j会++
                                        j = k;
                                        break;
                                    }
                                    dp[i][k] = true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return dp[p.length()][s.length()];

    }
//        for(int i=0;i<dp.length;i++){
//            for(int j=0;j<dp[0].length;j++){
//                System.out.print(dp[i][j]+" ");
//            }
//            System.out.println();
//        }
    //sol.isMatch("abbcdde","ab*c.*e")
//        true false false false false false false false
//        false true false false false false false false
//        false false true false false false false false
//        false false false true false false false false
//        false false false false true false false false
//        false false false false false true false false
//        false false false false true  true  true  true
//        false false false false false false false true
```

- 官方

  ```java
  class Solution {
      public boolean isMatch(String s, String p) {
          int m = s.length(); // 字符串 s 的长度
          int n = p.length(); // 字符串 p 的长度
  
          boolean[][] f = new boolean[m + 1][n + 1]; // 动态规划辅助数组，用于存储匹配结果，默认值为 false
          f[0][0] = true; // 初始化，空字符串与空模式匹配为 true
  
          for (int i = 0; i <= m; ++i) {
              for (int j = 1; j <= n; ++j) {
                  if (p.charAt(j - 1) == '*') { // 当前模式字符为 '*'
                      f[i][j] = f[i][j - 2]; // 匹配零个字符时，将模式字符和 '*' 一起忽略
                      if (matches(s, p, i, j - 1)) { // 如果当前字符串字符与模式字符前一个字符匹配
                          f[i][j] = f[i][j] || f[i - 1][j]; // 匹配多个字符时，将当前字符串字符与前一个模式字符匹配成功的结果取或
                      }
                  } else { // 当前模式字符不为 '*'
                      if (matches(s, p, i, j)) { // 如果当前字符串字符与模式字符匹配
                          f[i][j] = f[i - 1][j - 1]; // 匹配成功，当前位置的匹配结果与前一个位置的匹配结果相同
                      }
                  }
              }
          }
          return f[m][n]; // 返回最终的匹配结果
      }
  
      public boolean matches(String s, String p, int i, int j) {
          if (i == 0) { // 字符串 s 已经匹配完了
              return false; // 匹配失败
          }
          if (p.charAt(j - 1) == '.') { // 模式字符为 '.'
              return true; // 可以匹配任意字符
          }
          return s.charAt(i - 1) == p.charAt(j - 1); // 字符串字符与模式字符相同
      }
  }
  ```

  

### 44.通配符匹配

- 给定一个字符串 (`s`) 和一个字符模式 (`p`) ，实现一个支持 `'?'` 和 `'*'` 的通配符匹配
  - '?' 可以匹配任何单个字符。
  - '*' 可以匹配任意字符串（包括空字符串）
  - s  p 都可能为空串
- dp
  - 通配符在内循环
  - *是任意字符，则根据左上角判断即可。
  - 最后一个再自己判断下？
- 通配符
  - 是?表示单个字符
  - *只是表示任意串，包括空串

```java
    public boolean isMatch(String s, String p) {
        boolean[][]dp=new boolean[p.length()+1][s.length()+1];
        dp[0][0]=true;

        //s=""   p="***"等   因为s可能为空，所以这行要计算
        for(int i=1;i<=p.length();i++){
            if(p.charAt(i-1)=='*'){
                dp[i][0]=true;
            }else {
                break;
            }
        }

        for(int i=1;i<=p.length();i++){
            for(int j=1;j<=s.length();j++){
                if(p.charAt(i-1)>='a' && p.charAt(i-1)<='z'){
                    dp[i][j]=p.charAt(i-1)==s.charAt(j-1) && dp[i-1][j-1];
                }else if(p.charAt(i-1)=='?'){
                    dp[i][j]=dp[i-1][j-1];
                }else {
                    //* 不会消除前一个字母  dp[i-1]行的true的下方向右全为true
                    if(dp[i-1][j-1]){
                        for(int k=j-1;k<=s.length();k++){
                            dp[i][k]=true;
                        }
                        break;
                    }
                    //最后一个没有左上
                    if(j==s.length()){
                        dp[i][j]=dp[i-1][j];
                    }
                }

            }
        }
        return dp[p.length()][s.length()];
    }
```

### 72.编辑距离

- 给你两个单词 `word1` 和 `word2`， *请返回将 `word1` 转换成 `word2` 所使用的最少操作数* 
  - 可以对word1进行插入、删除、替换字符
- dp

```java
    public int minDistance(String word1, String word2) {
        /**
         *
         * 不匹配word1[i-1]!=word2[j-1]。以下->表示数据处理的流动方向
         * 第一种情况
         * dp[i][j]->dp[i-1][j]     把word1[i-1]删除了，就能回到上一行匹配的dp[i-1][j]状态
         * 或者这样想 dp[i-1][j]->dp[i][j]   dp[i-1][j]已经匹配了，此时多了word1[i-1],那么把它删除即可
         * 第二种
         * word2[j-1]不能删除，结果要在第j列，所以
         * dp[i][j-1]->dp[i][j]     word1[i] 已经和word2[j-1] 匹配了，此时又多了一个字符c=word2.charAt(j)  那么增加word1增加一个字符c即可匹配
         * 第三种
         * dp[i-1][j-1]-> dp[i][j]   word1和word2都增加了一个，直接把word1[i-1]替换成word2[j-1]是最快的，比先删后增和先增后删快
         *
         * 匹配word1[i-1]==word2[j-1]
         * dp[i-1][j-1]-> dp[i][j]     word1和word2都新增一个相同的，所以不用替换，也比先删后增和先增后删快
         */
        int [][]dp=new int[word1.length()+1][word2.length()+1];
        for(int j=0;j<dp[0].length;j++){
            dp[0][j]=j;
        }
        for(int i=0;i< dp.length;i++){
            dp[i][0]=i;
        }

        for(int i=1;i<=word1.length();i++){
            for(int j=1;j<=word2.length();j++){
                if(word1.charAt(i-1)==word2.charAt(j-1)){
                    dp[i][j]=dp[i-1][j-1]; // 完全匹配
                }else {
                    dp[i][j]=Math.min(dp[i-1][j],dp[i][j-1]); // 插入或删除
                    dp[i][j]=Math.min(dp[i][j],dp[i-1][j-1]); // 替换
                    dp[i][j]+=1; // 编辑距离已经增加1了。
                }
            }
        }
        return dp[word1.length()][word2.length()];
    }
```

### 91.解码方法

- 给你一个只含数字的 **非空** 字符串 `s` ，请计算并返回 **解码** 方法的 **总数**

 ```
    输入：s = "226"
    输出：3
    解释：它可以解码为 "BZ" (2 26), "VF" (22 6), 或者 "BBF" (2 2 6) 。
 ```

- dp

  - 典型线性dp

```java
    public int numDecodings(String s) {
        //有前导0
        if (s.charAt(0) == '0') {
            return 0;
        }
        int[] dp = new int[s.length()];
        //处理0，1 位置
        dp[0]=1;
        if(s.length()>1){
            if(s.charAt(1)== '0'){
                if(s.charAt(0)>'2'){
                    return 0;
                }
                dp[1]=1;
            }else {
                if(s.charAt(0)=='1' || s.charAt(0)=='2' && s.charAt(1)<='6'){
                    dp[1]=2;
                }else {
                    dp[1]=1;
                }
            }

        }


        for (int i = 2; i < s.length(); i++) {
            if (s.charAt(i) == '0') {
                //不能跟前一个匹配，因为0也不能单独存在，所以直接结束
                if (s.charAt(i - 1) == '1' || s.charAt(i - 1) == '2') {
                    dp[i] = dp[i - 2] ;
                }else {
                    return 0;
                }
            } else {
                if (s.charAt(i - 1) == '1' || s.charAt(i - 1) == '2' && s.charAt(i) <= '6') {
                    dp[i] = dp[i - 1] + dp[i-2];
                } else {
                    dp[i] = dp[i - 1];
                }
            }
        }
        return dp[s.length() - 1];
    }
```

### 97.交错字符串

- 给定三个字符串 `s1`、`s2`、`s3`，请你帮忙验证 `s3` 是否是由 `s1` 和 `s2` **交错** 组成的。//s1,s2,s3都不为null
- dp
  - dp i j 表示s1的前i个和s2的前j个能否组成组成s3的前i+j个
  - s3是取完s1和s2
  - 这其中的关系错综复杂，不能用简答的for循环

```java
    public boolean isInterleave(String s1, String s2, String s3) {


        if(s1.length()+s2.length()!=s3.length()){
            return false;
        }

        boolean [][]dp=new boolean[s1.length()+1][s2.length()+1];
        //初始化
        dp[0][0]=true;
        for(int i=1;i<=s1.length();i++){
            if(s1.charAt(i-1)!=s3.charAt(i-1)){
                break;
            }
            dp[i][0]=true;
        }
        for(int j=1;j<=s2.length();j++){
            if(s2.charAt(j-1)!=s3.charAt(j-1)){
                break;
            }
            dp[0][j]=true;
        }

        //dp 这里没啥讲究
        for(int i=1;i<=s1.length();i++){
            for(int j=1;j<=s2.length();j++){
                dp[i][j]=(dp[i-1][j] && s1.charAt(i-1)==s3.charAt(i+j-1) || (dp[i][j-1] && s2.charAt(j-1) ==s3.charAt(i+j-1)));
            }
        }
        return dp[s1.length()][s2.length()];
    }
```

### 15.不同的子序列

- 给定一个字符串 `s` 和一个字符串 `t` ，统计并返回在 `s` 的 **子序列** 中 `t` 出现的个数
- 字符串的一个 子序列 是指，通过删除一些（也可以不删除）字符且不干扰剩余字符相对位置所组成的新字符串。（例如，"ACE" 是 "ABCDE" 的一个子序列，而 "AEC" 不是）
- dp
  - dp i j 表示 s前j个字符能够找到t的前i字符的次数
  - 这里是优先对t的每个字符进行匹配
  - 转义关系自己画图

```java
    public int numDistinct(String s, String t) {
        if(s.length()<t.length()){
            return 0;
        }
        if(s.equals(t)){
            return 1;
        }

        int [][]dp=new int[t.length()+1][s.length()+1];
        dp[0][0]=1;
        for(int i=1;i<dp[0].length;i++){
            dp[0][i]=1;
        }
		// 转移关系，画图一目了然。这里就不重复了
        // 这里子串放在外面
        for(int i=1;i<=t.length();i++){
            for (int j=1;j<=s.length();j++){
                dp[i][j]=dp[i][j-1];   //不匹配，则跟已经匹配的相同
                if(t.charAt(i-1)==s.charAt(j-1)) {
                    dp[i][j]+=dp[i-1][j-1];//匹配则加上   上一匹配的数量
                }
            }
        }
        return dp[t.length()][s.length()];
    }
```

## ==双指针(快慢指针）==

### 88.合并两个有序数组

- 两个非递减序列。nums1中有空位，非递减顺序合并后存储 在nums1中
  - 整数 `m` 和 `n` ，分别表示 `nums1` 和 `nums2` 中的非0元素数目
  - nums1 的初始长度为 m + n，其中前 m 个元素表示应合并的元素，后 n 个元素为 0
- 双指针

```java
public void merge(int[] nums1, int m, int[] nums2, int n) {
        int i=m-1;
        int j=n-1;
        int index=m+n-1;

        //特殊情况nums1=[0],nums1全是0
        while(i==-1&&j>=0){
            nums1[index--]=nums2[j--];
        }

        while(i!=-1&&j>=0){
            //index从nums1后面开始放
            if(nums2[j]>=nums1[i]){
                nums1[index--]=nums2[j--];
            }else{
                nums1[index--]=nums1[i--];
            }
        }

        //nums1[0]>nums2[0]。此时i已经等于-1.第二个while已经不能满足
        while(i==-1&&j>=0){
            nums1[index--]=nums2[j--];
        }
    }
```

### 24.反转链表(剑指)

- 定义一个函数，输入一个链表的头节点，反转该链表并输出反转后链表的头节点。

```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */

 /*
 双辅助指针。
 一个保存前节点，一个保存后结点
 当前指向前，后移
 */
class Solution {
    public ListNode reverseList(ListNode head) {
        //后指前，留个Next保存再后一个
        ListNode Next;
        ListNode pre=null;//前初始化为null
        while(head!=null){
            Next=head.next;//保存后结点   又是临时节点在里面更新
            head.next=pre;//当前指向前
            //后移
            pre=head;
            head=Next;
        }
        return pre;
    }
}
```

### 11.盛最多水的容器（包夹双指针）

- 只是找能盛最多水的2线

```java
/*
官方-双指针
谁低移动谁

because
取决于短板
移动高的，宽度再变小，高度不会高过当前的，所以容量不可能超过已有的。
*/
public int maxArea(int[] height) {

        int st=0;
        int end=height.length-1;
        int res=0;
        while (st<end){
            int temp=Math.min(height[st],height[end])*(end-st);
            if(temp>res){
                res=temp;
            }
            if(height[st]<height[end]){
                st++;
            }else {
                end--;
            }
        }
        return res;
    }
```

### 15.三数之和

- 给你一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，使得 a + b + c = 0 ？请你找出所有和为 0 且不重复的三元组
- 排序+双指针
  - 最外层没有办法，那就内层使用双指针

```java
public List<List<Integer>> threeSum(int[] nums) {
        int n=nums.length;
        List <List <Integer>> res=new ArrayList<>();
        if(n<3){
            //return null;不能返回null
            return res;
        }
        Arrays.sort(nums);
        for(int i=0;i<=n-3;i++){
            //防止i重复 i只取不重复的一个
            if(i>0 && nums[i]==nums[i-1]){
                continue;
            }
            int st=i+1;
            int end=n-1;
            while (st<end){
                int sum=nums[i]+nums[st]+nums[end];
                if(sum==0){
                    List<Integer> list=new ArrayList<>();
                    list.add(nums[i]);
                    list.add(nums[st]);
                    list.add(nums[end]);
                    res.add(list);
                    st++;
                    //防止st重复 st也是只取不重复的一个
                    while (st<n-1 && nums[st]==nums[st-1]){
                        st++;
                    }
                }else if(sum<0){
                    st++;
                }else {
                    end--;
                }
            }
        }
        return res;
    }
```



### 42.接雨水

- 给定 `n` 个非负整数表示每个宽度为 `1` 的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水。
- 动态规划
  - 左右横扫能够覆盖的区间
  - 最终结果为两个区间的交集
- 单调栈
  - 一个单调栈，进栈出栈的过程就搞定了
- 双指针
  - 是一个单调栈的空间复杂度优化
  - left，right 指针隔绝了一个区间
  - hight是其中的较小值
  - 每次移动left和right中的较小者

```java
    public int trap(int[] height) {
        int res=0;
        int i=0;
        int j=height.length-1;
        int high=Math.min(height[i],height[j]);
        boolean isForward=false;//因为要用到i或者j是不确定的
        if(high==height[i])
            isForward=true;

        while (i<j){
            if(isForward){
                //向左还是向右，true为从做到右
                if(height[i]<=height[j]){
                    if(height[i]<=high)
                        res+=(high-height[i]);
                    else
                        high=height[i];
                    i++;
                }
                else {
                    high=height[j];
                    isForward=false;
                }
            }
            else {
                if(height[j]<=height[i]){
                    if(height[j]<high)
                        res+=(high-height[j]);
                    else
                        high=height[j];
                    j--;
                }else {
                    high=height[i];
                    isForward=true;
                }
            }
        }
        return res;
}
```

### 167.两数之和二

- 给你一个下标从 **1** 开始的整数数组 `numbers` ，该数组已按 非递减顺序排列 ，请你从数组中找出满足相加之和等于目标数 `target` 的两个数。要求常量级空间
- 二分查找加双指针

```java
public int[] twoSum(int[] numbers, int target) {
        int low=0;
        int high=numbers.length-1;

        //二分法找到第一个比target大的数的下标high。target小于numbers[0]的话，则不用再找，否则high=0
        //没啥用，就是为了优化某些不知死活的情况
        while(low<high && target>numbers[0]){
            int mid=low+((high-low)>>1);
            if(numbers[mid]>target){
                high=mid;
            }else{
                low=mid+1;
            }
        }

        //双指针
        int i=0;
        while(i<high){
            if(numbers[i]+numbers[high]==target){
                return new int[]{i+1,high+1};
            }else if(numbers[i]+numbers[high]>target){
                high--;
            }else {
                i++;
            }
        }
        return new int[2];
    }
```

### 344.反转字符串

- 双指针

```java
public void reverseString(char[] s) {
        int i = 0;
        int j = s.length - 1;
        while (i < j) {
            char c = s[i];
            s[i] = s[j];
            s[j] = c;
            i++;
            j--;
        }
    }
```

### 16.最接近的三数和

- nums[]和target，假设恰好只有一个解
- 排序+双指针。双指针sum有正负判断，所以是在不断靠近

```java
    public int threeSumClosest(int[] nums, int target) {
        int res=0;
        Arrays.sort(nums);
        int difference=Integer.MAX_VALUE;
        for(int i=0;i<nums.length-2;i++){
            int j=i+1;
            int k=nums.length-1;
            while (j<k){
                int sum=nums[i]+nums[j]+nums[k];
                if(sum==target){
                    return target;
                }
                if(Math.abs(sum-target)<difference){
                    difference=Math.abs(sum-target);
                    res=sum;
                }
                //居然用sum就可以控制了。。。毕竟sum有正负，每次都是靠近
                if(sum<target){
                    j++;
                }else{
                    k--;
                }
            }
        }
        return res;
    }
```

### 18.四数之和

- `nums[a] + nums[b] + nums[c] + nums[d] == target`
- 排序加双指针
  - 注意边界值

```java
    public List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> res=new ArrayList<>();
        Set<String>set=new HashSet<>();
        Arrays.sort(nums);
        int len=nums.length;
        for(int i=0;i<len;i++){
            for(int j=i+1;j<len;j++){
                int k=j+1;
                int x=len-1;
                while (k<x){
                    //[1000000000,1000000000,1000000000,1000000000] -294967296  int会反向越界
                    long sum=(long)nums[i]+(long)nums[j]+(long)nums[k]+(long)nums[x];
                    if(sum==target){
                        //拼接成String来去重
                        StringBuilder strb=new StringBuilder();
                        strb.append(nums[i]);
                        strb.append(nums[j]);
                        strb.append(nums[k]);
                        strb.append(nums[x]);

                        if(!set.contains(strb.toString())) {
                            List<Integer> node = new ArrayList<>();
                            node.add(nums[i]);
                            node.add(nums[j]);
                            node.add(nums[k]);
                            node.add(nums[x]);
                            res.add(node);
                            set.add(strb.toString());
                        }
                        k++;//随便更新个防止死循环，x--也行
                    }else if(sum<target){
                        k++;
                    }else {
                        x--;
                    }
                }
            }
        }
        return res;
    }
```

### 27.移除元素

- 给你一个数组 `nums` 和一个值 `val`，你需要 原地移除所有数值等于 `val` 的元素，并返回移除后数组的新长度。空间复杂度O(1)
- 双指针（优化了下
  - 一次能够移除2个元素

```java
//快慢指针    
public int removeElement(int[] nums, int val) {
        if(nums.length==0){
            return 0;
        }
        int count=0;
        int slow=0;
        int fast=0;
        while (fast<nums.length){
            if(nums[fast]!=val){
                count++;
                nums[slow++]=nums[fast];
            }
            fast++;
        }
        return count;
    }

//优化后，左右指针.反正也不需要位置对应
    public int removeElement(int[] nums, int val) {
        if(nums.length==0){
            return 0;
        }
        int left=0;
        int right=nums.length-1;
        //优化[1,2,3,4,5] 1 移动多次，直接变成[5,2,3,4]
        while (left<=right){
            if(nums[left]==val){
                //直到左不再是val. 6啊
                nums[left]=nums[right--];//right来控制长度
            }else {
                left++;
            }
        }
        return left;
    }
```



## ==二分法==

### 4.寻找2个正序数组的中位数

- 去除前i或j个，更新

```java
 public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int len=nums1.length+nums2.length;
        if (len % 2 == 1) {
            int midIndex = len / 2;
            double median = getKthElement(nums1, nums2, midIndex + 1);
            return median;
        } else {
            double median = (getKthElement(nums1, nums2, len/2) + getKthElement(nums1, nums2, len/2+ 1)) / 2.0;
            return median;
        }
    }

    public int getKthElement(int[] nums1, int[] nums2, int k) {
        
        int i = 0, j = 0;
        while (true) {
            // 边界情况
            if (i == nums1.length) {
                return nums2[j + k - 1];
            }
            if (j == nums2.length) {
                return nums1[i + k - 1];
            }
            if (k == 1) {
                return Math.min(nums1[i], nums2[j]);
            }

            // 正常情况
            int half = k / 2;
            int I = Math.min(i + half,nums1.length) - 1; // 左右两边分别找k个位置后，最多找到末尾
            int J = Math.min(j + half, nums2.length) - 1;
            int pivot1 = nums1[I], pivot2 = nums2[J];
            if (pivot1 <= pivot2) {
                k -= (I - i + 1);
                i = I + 1;
            } else {
                k -= (J - j + 1);
                j = J + 1;
            }
        }
    }
```

### 33. 搜索旋转排序数组

- 给你 **旋转后** 的前后分别有序数组 `nums` 和一个整数 `target` ，如果 `nums` 中存在这个目标值 `target` ，则返回它的下标，否则返回 `-1` 。数组中元素互不相同

```java
public int search(int[] nums, int target) {
        int len=nums.length;
        int j=findFirst(nums,0,len-1);//找到分界点(最小值)的下标

        if(target>nums[len-1])
            return binarySearch(nums,0,j-1,target);//对后二分搜索
        return binarySearch(nums,j,len-1,target);//前
    }

    private int findFirst(int []nums,int i,int j){//二分
        while (i<j){
            int mid=i+((j-i)>>1);
            if(nums[mid]>nums[j])
                i=mid+1;
            else
                j=mid;
        }
        return j;
    }
    private int binarySearch(int []nums,int i,int j,int tar){//二分
        while (i<=j){
            int mid=i+((j-i)>>1);
            if(nums[mid]==tar)
                return mid;
            if(nums[mid]<tar)
                i=mid+1;
            if(nums[mid]>tar)
                j=mid-1;
        }
        return -1;
    }
```

### 34.排序数组找元素的起始

- 升序整数数组 nums,目标值 target。target开始位置和结束位置。不存在则返回 [-1, -1]

```java
 public int[] searchRange(int[] nums, int target) {
        int len=nums.length;
        if(len==0 || target<nums[0] || target>nums[len-1]){
            return new int[]{-1,-1};
        }
        int st=findSt(nums,0,len-1,target);
        int end=findEnd(nums,0,len-1,target);
        return new int[]{st,end};
    }
    
//第一个比tar小的数下标
    private int findSt(int []nums,int low,int high,int tar){
        int n=high;
        while (low<=high){
            int mid=low+((high-low)>>1);
            if(nums[mid]>=tar)
                high=mid-1;
            else
                low=mid+1;
        }
        if(n==high)
            return nums[n]==tar?n:-1;
        return nums[high+1]==tar?high+1:-1;
    }

//第一个比tar大的数下标
    private int findEnd(int []nums,int low,int high,int tar){
        int n=low;
        while (low<=high){
            int mid=low+((high-low)>>1);
            if(nums[mid]<=tar)
                low=mid+1;
            else
                high=mid-1;
        }
        if(n==low)
            return nums[n]==tar?n:-1;
        return nums[low-1]==tar ?low-1:-1;
    }
```

### 704.二分查找

- 升序整型数组 nums 和 target  ,返回下标
- low<=high   mid一加一减

```java
 public int search(int[] nums, int target) {
        int low=0;
        int high=nums.length-1;  // 右是数组长度-1
        while(low<=high){//等于时还可再判断一次
            int mid=low+((high-low)>>1);//防止溢出。要加最外的括号
            if(nums[mid]<target){
                low=mid+1;
            }else if(nums[mid]>target){
                high=mid-1;
            }else{
                return mid;//至多有一次成立，最后判断
            }
        }
        return -1;
 }
```

### 278.第一个错误的版本

  - 假设你有 `n` 个版本 `[1, 2, ..., n]`，你想找出导致之后所有版本出错的第一个错误的版本
  - 注意和经典二分查找有点不同
  - 分界点可low<high    low+1

```java
 public int firstBadVersion(int n) {
        int low=1;
        int high=n;

        while(low<high){
            int mid=low+((high-low)>>1);
            if(isBadVersion(mid)){
                high=mid;//high=mid-1;的话可能会跳过true的结果   右mid
            }else{
                low=mid+1;  // 收缩为左，左mid加一
            }
        }
        return low;//收缩为一点
    }
```

### 35.搜索插入位置

- 给定一个排序数组和一个目标值，在数组中找到目标值，并返回其索引。如果目标值不存在于数组中，返回它将会被按顺序插入的位置。
- 分界点可low<high    low+1

```java
public int searchInsert(int[] nums, int target) {
        int l=0;
        int r=nums.length;  // 右是数组长度
        while(r<l){
            int mid=l+((r-l)>>1);
            if(nums[mid]>=target){
                r=mid;
            }else{
                l=mid+1;
            }
        }
        return l;//low收缩到等于target（等于时也是最左那个）或第一个大于target的数
    }
```

### 74.搜索二维矩阵

- 编写一个高效的算法来判断 m x n 矩阵中，是否存在一个目标值。该矩阵具有如下特性：每行中的整数从左到右按升序排列。每行的第一个整数大于前一行的最后一个整数
- 二分查找，先找行再找列

```java
    public boolean searchMatrix(int[][] matrix, int target) {
        int m=matrix.length;
        int n=matrix[0].length;

        int mleft=0;
        int mright=m-1;
        int nleft=0;
        int nright=n-1;

        //先找所在行
        int x=-1;
        while (mleft<=mright){
            int mid=(mleft+mright)/2;
            if(target>=matrix[mid][0] && target<=matrix[mid][n-1]){
                x=mid;
                break;
            }
            if(target<matrix[mid][0]){
                mright=mid-1;
            }
            if(target>matrix[mid][n-1]){
                mleft=mid+1;
            }
        }
        if(x==-1){
            return false;
        }

        while (nleft<=nright){
            int mid=(nleft+nright)/2;
            if(target==matrix[x][mid]){
                return true;
            } else if (target<matrix[x][mid]) {
                nright=mid-1;
            }else {
                nleft=mid+1;
            }
        }
        return false;
    }
```



##  ==快慢指针==

### 19.删除链表的倒数第N个节点

- 快指针先走n步

```java
   public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode pr=head;
        ListNode nex=head;
        while(n>0){//快指针nex先走n步
            nex=nex.next;
            n--;
            if(nex==null)//说明删除的是第一个节点
                return head.next;
        }
        while (nex.next!=null){//nex走到最后
            nex=nex.next;
            pr=pr.next;
        }
        pr.next=pr.next.next;//修改
        return head;
    }
```

### 26.删除有序数组中的重复项

- 要求常数空间
- 快慢指针

```java
//    public int removeDuplicates(int[] nums) {
//        int count=0;
//        int st=0;//当前元素位置
//        Set<Integer> set=new HashSet<>();
//        for(int i=0;i<nums.length;i++){
//            if(!set.contains(nums[i])){
//                set.add(nums[i]);
//                if(i!=st){
//                    nums[st]=nums[i];
//                }
//                st++;
//                count++;
//            }
//        }
//        return count;
//    }
    //题目要求不使用额外空间
    public int removeDuplicates(int[] nums) {
        if(nums.length==0){
            return 0;
        }
        //快慢指针，因为有fast-1，所以slow初始值是1，第一个不会重复
        int slow=1;//慢当前填入位置
        int fast=1;//快下一个要填入的数
        while (fast<nums.length){
            if(nums[fast]!=nums[fast-1]){
                nums[slow++]=nums[fast];
            }
            fast++;
        }
        return slow;//slow就是个数。。
    }
```

### 141.环形链表

- 快慢指针

```java
    public boolean hasCycle(ListNode head) {
        ListNode fast = head;
        ListNode slow = head;
        while (fast!=null && fast.next!=null) {
            fast=fast.next.next;
            slow=slow.next;
            if(fast==slow){
                return true;
            }
        }
        return false;
    }
```

### 109.有序链表转为二叉搜索树

- 快慢指针找中间根节点（官方
- 或者转为数组吧

```java
class Solution {
    public TreeNode sortedListToBST(ListNode head) {
        return buildTree(head, null);
    }

    public TreeNode buildTree(ListNode left, ListNode right) {
        if (left == right) {
            return null;
        }
        ListNode mid = getMedian(left, right);
        TreeNode root = new TreeNode(mid.val);
        root.left = buildTree(left, mid);
        root.right = buildTree(mid.next, right);
        return root;
    }

    public ListNode getMedian(ListNode left, ListNode right) {
        ListNode fast = left;
        ListNode slow = left;
        while (fast != right && fast.next != right) {
            fast = fast.next;
            fast = fast.next;
            slow = slow.next;
        }
        return slow;
    }
}
```



## ==简单回溯==

- 先把框架写好了什么都好说
- 建议变量定义为字段
- 不如想想解的空间树

### 22.括号生成

- 数字 `n` 代表生成括号的对数，请你设计一个函数，用于能够生成所有可能的并且 **有效的** 括号组合。

```java
private int len;//保存初始n的值
    public List<String> generateParenthesis(int n) {
        List<String> res=new ArrayList<>();
        len=n;
        creThe(res,new StringBuilder(),0,0,n*2);
        return res;
    }

	/**
	*left (数量  right  )数量 
	*/
    private void creThe(List<String> list,StringBuilder str,int left,int right,int n){
        //控制右比左少，左比初始n少
        if(right>left || left>len)//不符合
            return;
        if(n==0){//结束
            list.add(str.toString());
            return;
        }
        // 放左或者放右
        StringBuilder str1=new StringBuilder(str);//复制
        str.append('(');
        creThe(list,str,left+1,right,n-1);
        str1.append(')');
        creThe(list,str1,left,right+1,n-1);
    }
```

### 46.全排列

- 给定一个不含重复数字的数组 `nums` ，返回其 所有可能的全排列。你可以 **按任意顺序** 返回答案。
- 回溯生成
  - 就是一个普通全排列

```java
class Solution {
    List<Integer> list;
    List<List<Integer>> ans;
    boolean[]v;
    public List<List<Integer>> permute(int[] nums) {
        list=new ArrayList<>();
        ans=new ArrayList<>();
        v=new boolean[nums.length];
        Arrays.sort(nums);
        dfs(nums);
        return ans;
    }
    private void dfs(int []nums){
        int n=nums.length;
        if(list.size()==n){
            ans.add(new ArrayList<>(list));
            return;
        }
        for(int i=0;i<n;i++){
            if(i>0 && nums[i]==nums[i-1]){
                continue;
            }
            if(!v[i]){
                list.add(nums[i]);
                v[i]=true;
                dfs(nums);
                list.remove(list.size()-1);
                v[i]=false;
            }
            
        }
    }
}
```

### 92.复原ip地址

- 给定一个只包含数字的字符串 `s` ，用以表示一个 IP 地址，返回所有可能的**有效 IP 地址**，这些地址可以通过在 `s` 中插入 `'.'` 来形成，s长度为[1,21]
- 回溯
  - 分为4部分，一个个部分去找

```java
    List<String>res;
    int []subPoints;//分割点

    int count=0;//记录分割的次数
    public List<String> restoreIpAddresses(String s) {
        //s长度为[1,21]  也就是可能包含不合法的
        res=new ArrayList<>();
        subPoints=new int[5];
        subPoints[0]=0;
        dfs(s,0);
        return res;
    }

    private void dfs(String s, int index) {
        if(index==s.length() && count<4){//到了结尾还没有四次分割
            return;
        }
        if(count==4){
            //到4后就不再分割
            if(index==s.length()){
                StringBuilder strb=new StringBuilder();
                for(int i=0;i<subPoints.length-1;i++){
                    strb.append(s.substring(subPoints[i], subPoints[i + 1])).append(".");
                }
                strb.deleteCharAt(strb.length()-1); //删除最后一个.
                res.add(strb.toString());
            }
            return;
        }
        if(s.charAt(index)=='0'){
            count++;
            subPoints[count]=index+1;
            dfs(s,index+1);
            count--;
        }else {
            for(int i=0; i<3;i++){
                //没有前导0且至少还有一个字符未处理，所以肯定可以进行下一次dfs
                if(index+3-i<=s.length() && Integer.parseInt(s.substring(index,index+3-i))<=255){
                    count++;
                    subPoints[count]=index+3-i;
                    dfs(s,index+3-i);
                    count--;
                }
            }
        }
    }
```

### 129.求根节点到叶节点数字之和

- 从根节点到叶节点的路径 `1 -> 2 -> 3` 表示数字 `123`
- dfs

```java
    int res=0;
    List<Integer>list;
    public int sumNumbers(TreeNode root) {
        list=new ArrayList<>();
        dfs(root);
        return res;
    }

    private void dfs(TreeNode root){
        if(root==null){
            return;
        }
        list.add(root.val);
        if(root.left==null && root.right==null){
            int sum=0;
            for(Integer e:list){
                sum=sum*10+e;
            }
            res+=sum;
            //因为return了，所以这里先清除下
            list.remove(list.size()-1);
            return;
        }
        dfs(root.left);
        dfs(root.right);
        list.remove(list.size()-1);
    }
```



## ==回溯复原（选或不选）==

- 先用一条走到尽头

### 51.N皇后

- 回溯
  - 一行行放
  - 每个位置都试着放一下
  - 因为有选和不选，所以回溯后要复原

```java
public List<List<String>> solveNQueens(int n) {
        int []nums=new int [n]; //下标对应列，值表示Q在该列的位置
        for(int i=0;i<n;i++)//赋值-100，-1的话会和0形成对角线
            nums[i]=-100; //-100表示该列没有放Q
        List<List<String>> res=new ArrayList<>();
        getNums(nums,0,n,res);
        return res;
    }
    private void getNums(int []nums,int index,int n,List<List<String>> res){
        if(index==n){   //已经放够了
            List<String> list=new ArrayList<>();
            for(int e:nums){
                StringBuilder str=new StringBuilder();
                for(int i=0;i<n;i++){
                    if(i!=e)
                        str.append(".");
                    else
                        str.append("Q");
                }
                list.add(str.toString());
            }
            res.add(list);
            return;
        }

        for(int j=0;j<n;j++){
            nums[index]=j;   //每个位置都试着放一下
            if(!isAttack(nums))
                getNums(nums,index+1,n,res);
            nums[index]=-100;//从后往前回的时候复原,复原要在递归之后
        }

    }
    /*
    判断能否攻击到
    能攻击到就ture
    */
    private boolean isAttack(int []nums){
        //是否同一列
        Set<Integer> set=new HashSet<>();
        for(int e:nums){
            if(e==-100)    //从第一行往下放，e是-100，说明后面的也是-100
                break;
            if(!set.contains(e))
                set.add(e);
            else
                return true;
        }

        //是否对角线
        for(int i=0;i<nums.length;i++){
            for(int j=0;j<nums.length;j++){
                if(j!=i){
                    //上下左右一共4个角
                    if(nums[j]-nums[i]==j-i || nums[j]-nums[i]==i-j){
                        return true;
                    }
                }
            }

        }
        return false;
    }
```

### 52.N皇后二

- 我都不想写，用51的代码
- 答案数量不多，打表法

### 17.电话号码的字母组合

- 给定一个仅包含数字 `2-9` 的字符串，返回所有它能表示的字母组合
- 九宫格打字
  - 用map记录数字按键和字母的对应关系
  - 然后就是正常的回溯、复原了

```java
    /**
     * 复原回溯法
     * 一个对象new StringBuilder()能够重复使用
     * 长度够之后返回
     */
    public List<String> letterCombinations(String digits) {
        List<String>res=new ArrayList<>();
        if(digits.length()==0){
            return res;
        }
        //新写法
        Map<Character, String>map=new HashMap<>(){
            {
                put('2',"abc");
                put('3',"def");
                put('4',"ghi");
                put('5',"jkl");
                put('6',"mno");
                put('7',"pqrs");
                put('8',"tuv");
                put('9',"wxyz");
            }
        };
        getList(res,map,digits,0,new StringBuilder());
        return res;
    }

    private void getList(List<String> list,Map<Character,String> map,
                         String digits,int index,StringBuilder strb){
        if(strb.length()==digits.length()){
            list.add(strb.toString());
            return;
        }
        //index表示第几个号码
        String digit=map.get(digits.charAt(index));
        
        for(int i=0;i<digit.length();i++){
            strb.append(digit.charAt(i));
            getList(list,map,digits,index+1,strb);
            strb.deleteCharAt(index);
        }
    }
```

### 39.组合总数

- 给你一个 无重复元素 的整数数组 candidates 和一个目标整数 target ，找出 candidates 中可以使数字和为目标数 target 的 所有 不同组合 。数组元素互异

- 回溯复原，从大的数开始。因为要复原，所以加结果时要new

```java
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        Arrays.sort(candidates);
        List<List<Integer>>res=new ArrayList<>();
        List<Integer>list=new ArrayList<>();
        getCand(candidates,res,list,candidates.length-1,target);
        return res;
    }

    private void getCand(int []candidates,List<List<Integer>>res,List<Integer>list,int index,int target){
        int sum=0;
        for (Integer e:list){
            sum+=e;
        }
        if(sum==target){
            //res.add(list);这个是引用，后面会清空，所以要new
            res.add(new ArrayList<>(list));
            return;
        }
        if(sum>target){
            return;
        }

        for (int i=index;i>=0;i--){
            list.add(candidates[i]);
            getCand(candidates,res,list,i,target);
            list.remove(list.size()-1);
        }
    }
```

### 47.全排列二

- 给定一个可包含重复数字的序列 `nums` ，***按任意顺序*** 返回所有不重复的全排列
- 回溯复原，并去重

```java
    List<List<Integer>>res=new ArrayList<>();
    List<Integer>list=new ArrayList<>();

    boolean[]isvisited;

    public List<List<Integer>> permuteUnique(int[] nums) {
        Arrays.sort(nums);
        isvisited=new boolean[nums.length];
        getRes(nums,0);
        return res;
    }

    private void getRes(int [] nums,int n){
        if(n==nums.length){
            res.add(new ArrayList<>(list));
            return;
        }

        for(int i=0;i<nums.length;i++){
             //去重[1,1,2]   问题：先拿nums[0]再nums[1]和先nums[1]再nums[0]重复了
            //思路，这样只能够先nums[1]再nums[0]
            if(i>0 && isvisited[i-1] &&  nums[i]==nums[i-1]){ 
                continue;
            }
            if(!isvisited[i]){
                isvisited[i]=true;
                list.add(nums[i]);
                getRes(nums,n+1);
                list.remove(list.size()-1);
                isvisited[i]=false;
            }
        }
    }
输入：nums = [1,1,2]
输出：
[[1,1,2],
 [1,2,1],
 [2,1,1]]
```

### 77.组合

- 给定两个整数 `n` 和 `k`，返回范围 `[1, n]` 中所有可能的 `k` 个数的组合。你可以按 **任何顺序** 返回答案
- 复原回溯

```java
    List<List<Integer>>lists;
    List<Integer>list;

    public List<List<Integer>> combine(int n, int k) {
        lists=new ArrayList<>();
        list=new ArrayList<>();
        getRes(n,k,1);
        return lists;
    }

    private void getRes(int n, int k,int index) {
        if(list.size()==k){
            List<Integer>node=new ArrayList<>(list);
            lists.add(node);
            return;
        }
        for(int i=index;i<=n;i++){
            list.add(i);
            getRes(n,k,i+1);
            list.remove(list.size()-1);
        }
    }
```

### 79.单词搜索

- 给定一个 `m x n` 二维字符网格 `board` 和一个字符串单词 `word` 。如果 `word` 存在于网格中，返回 `true` ；否则，返回 `false`
- 二维平面dfs搜索  复原回溯
  - 对visited数组复原 

```java
    int [][]directions=new int[][]{{-1,0}, {1,0},{0,1},{0,-1}};//四个方向，左右上下

    boolean ans;
    public boolean exist(char[][] board, String word) {
        boolean [][]visited=new boolean[board.length][board[0].length]; //防止回头
        for(int i=0; i<board.length; i++){
            for (int j=0; j<board[0].length; j++){
                dfs(board,word,visited,i,j,0);
            }
        }
        return ans;
    }

    private void dfs(char[][] board, String word, boolean [][] visited,int x, int y,int index) {
        //index>0 让第一个进入，不然无法递归。ans 有结果就不计算了
        if(x<0 || x==board.length || y<0 || y==board[0].length || visited[x][y] || board[x][y]!=word.charAt(index) || ans){
            return;
        }
        if(index==word.length()-1){
            ans=true;
            return;
        }
        visited[x][y]=true;
        index++;
        for (int[] direction : directions) {
            dfs(board, word, visited,x + direction[0], y + direction[1], index);
        }
        visited[x][y]=false;
    }
```



## ==递归==

### 78.子集

- 给你一个整数数组 `nums` ，数组中的元素 **互不相同** 。返回该数组所有可能的子集（幂集）。

```java
class Solution {
    List<Integer>list;
    List<List<Integer>> ans;
    public List<List<Integer>> subsets(int[] nums) {
        list=new ArrayList<>();
        ans=new ArrayList<>();
        Arrays.sort(nums);
        dfs(nums,0);
        return ans;
    }
    private void dfs(int []nums,int idx){
        int n=nums.length;
        ans.add(new ArrayList<>(list));
        for(int i=idx;i<n;i++){
            // 少了一个去重
            // if(i>idx && nums[i]==nums[i-1]){
            //     continue;
            // }
            list.add(nums[i]);
            dfs(nums,i+1);
            list.remove(list.size()-1);
        }
    }
}
```

### 生成二进制序列

- 递归
  - 不如直接遍历0-2的n次方。然后用位运算判断

```java
//n位   00,01,10,11
public  void  getSeq(List<StringBuilder> list,int n){
        int len=(int)Math.pow(2,n);
        for(int i=0;i<len;i++){
            list.add(new StringBuilder());
        }
        doSeq(list,0,len);
}
private void doSeq(List<StringBuilder> list,int st,int end){
        if(end-st==1){
            return;
        }
        int mid=(end+st)/2;
    	//一半一半
        for(int i=st;i<mid;i++){
            list.get(i).append("0");
        }
        for(int j=mid;j<end;j++){
            list.get(j).append("1");
        }
        doSeq(list,st,mid);
        doSeq(list,mid,end);
}
```

### 60.排列序列

- 给出集合 `[1,2,3,...,n]`，其所有元素共有 `n!` 种排列。给定 `n` 和 `k`，返回第 `k` 个排列

- 找规律，递归

```java
    List<Integer>list;
    StringBuilder strb;
    public String getPermutation(int n, int k) {
        list=new ArrayList<>();
        strb=new StringBuilder();
        int total=1;
        for(int i=1;i<=n;i++){
            list.add(i);
            total*=i;
        }
        getRes(total,n,k-1);//k-1是为了下标从0开始
        return strb.toString();
    }

    /**
     *
     * @param total
     * @param n
     * @param k
     * 分组求，把所以结果序列从小到大放在一起就会发现，每次取一个下标为商所在分组，把该数取了后余数作为新的k
     * 也就是一位位，递归求
     */
    private void getRes(int total, int n, int k) {
        if(n==1){
            strb.append(list.remove(0));
            return;
        }
        int quotient=k/(total/n);//商，也就是要拿list中的下标的数。
        strb.append(list.remove(quotient));
        int remainder=k%(total/n);//余数
        getRes(total/n,n-1,remainder);
    }
```



## ==分治==

### 50.Pow(x,n)

- 分治法快速幂乘
- 递归     时间O(longn)   空间O(longn)

```java
    public double myPow(double x, int n) {
        //n可能是Integer.MIN_VALUE。转为long就没错
        //n可正可负可0
        return n>0?subPow(x,n):1/subPow(x,-(long) n);
    }

    private double subPow(double x,long n){
        if(n==1){
            return x;
        }else if(n==0){
            return 1;
        }
        else  if((n&1)==0){//偶数
            double d=subPow(x,n>>1);
            return d*d;
        }else {
            return subPow(x,n-1)*x;
        }
    }
```

- 迭代     时间O(longn)    空间O(1)
  - 压缩并更新权重

```java
    public double myPow(double x, int n) {
        long N = n; 
        // -n 可能导致越界
        return N>=0?qmul(x,N):1.0/qmul(x,-N);
    }

    // x 的n次方
    private double qmul(double x,long n){
        double ans=1;
        while(n>0){
            if((n&1)==1){
                ans*=x;
            }
            x*=x;
            n>>=1;
        }
        return ans;
    }
```

### 快速排序

- 

```java
    // 能够容易理解的快速排序

    public void quickSort(int[]nums,int left,int right) {
        if(left<right){
            int idx=partition(nums,left,right);
            quickSort(nums,left,idx-1);
            quickSort(nums,idx+1,right);
        }
    }
    private int partition(int[] nums,int left,int right) {
        int pivot=nums[left];
        int i= left+1;// 先不处理基准元素
        int j=right;
        // 双指针进行排序
        while (i<=j){
            if(nums[i]> pivot && nums[j]<pivot){
                swap(nums,i,j);
                i++;
                j--;
            }
            if(nums[i]<=pivot){
                i++;
            }
            if (nums[j]>=pivot){
                j--;
            }
        }
        swap(nums,left,j); // 交换基准元素
        return j; // 返回的基准元素所在的下标
    }
    private void swap(int []nums,int i,int j){
        int temp=nums[i];
        nums[i]=nums[j];
        nums[j]=temp;
    }
```



## ==贪心==

### 55.跳跃游戏

- 数组中的每个元素代表你在该位置可以跳跃的最大长度。判断你是否能够到达最后一个下标。

```java
//贪心    
public boolean canJump(int[] nums) {
        int n=nums.length;
        int max=0;  //记录当前能到达的最远位置

        for(int i=0;i<n-1;i++){
            if(i>max)
                return false;
            max=Math.max(i+nums[i],max);
        }
        return max>=n-1;
    }
```

### 13.罗马数字转整数

- 根据罗马数字的规律可知
- 贪心匹配,尽可能匹配多

```java
    public int romanToInt(String s) {
        int res=0;
        Map<String ,Integer>map=new HashMap<>();
        map.put("M",1000);
        map.put("CM",900);
        map.put("D",500);
        map.put("CD",400);
        map.put("C",100);
        map.put("XC",90);
        map.put("L",50);
        map.put("XL",40);
        map.put("X",10);
        map.put("IX",9);
        map.put("V",5);
        map.put("IV",4);
        map.put("I",1);
        
        int i=0;
        while(i<s.length()){
            int j=2;//最多有2位匹配
            //substring不越界
            if(i+j==s.length()+1){
                j=1;
            }
            //j至少为1保证i能增加
            while (j>=1){
                String str=s.substring(i,i+j);
                //贪心匹配
                if(map.containsKey(str)){
                    res+=map.get(str);
                    i+=j;
                    break;
                }else {
                    j--;
                }
            }
        }
        return res;
    }
```

### 45.跳跃游戏二

- 最少跳跃数到达最后位置（总能到达）
- nums表示当前能跳的最大位置
- 贪心法

```java
    //越简洁越好
	public int jump(int[] nums) {
       int res=0;
       int right=0;
       int next=0;//下一右边界
       for(int i=0;i<nums.length-1;i++){
           next=Math.max(next,nums[i]+i);//秒啊，每次更新一次，就不用再写一个循环。若存在不能跳到的话就另说
           if(i==right){//到达右边界
               right=next;
               res++;
           }
       }
       return res;
    }
```

### 122.买股票的最佳时机二

- 每一天，你可以决定是否购买和/或出售股票。你在任何时候 **最多** 只能持有 **一股** 股票。你也可以先购买，然后在 **同一天** 出售。

- 贪心       不是dp，因为没有用到前面的状态

```java
    public int maxProfit(int[] prices) {

        int profit=0;
        for(int i=0;i<prices.length-1;i++){
            if(prices[i]<prices[i+1]){
                //上升则加
                profit+=(prices[i+1]-prices[i]);
            }
        }
        return profit;
    }
```

### 120.三角形最小路径和

-  给定一个三角形 `triangle` ，找出自顶向下的最小路径和。

- 因为有负数，所以用贪心。（迪杰斯特拉）好像不太行
- 迪杰斯特拉（有负数就瘫痪了

```java
    public int minimumTotal(List<List<Integer>> triangle) {

        int res=0;

        List<Integer>indexArr=new ArrayList<>();
        indexArr.add(0);

        // 上一层是4 4 4 4    下一层是5 5 9 5 5 5呢  所以要拿到所有下一层可以访问的下标
        for(int i=0;i<triangle.size();i++){

            int minVal=getMin(indexArr,triangle.get(i));
            res+=minVal;

            //从当前行能去的拿到下一行能去的
            List<Integer>nextIndex=new ArrayList<>();
            for(int j=0;j<indexArr.size();j++){
                int index=indexArr.get(j);
                if(triangle.get(i).get(index)==minVal){
                    nextIndex.add(index);
                    nextIndex.add(index+1); //能够去j+1  即使下标有重复也不管了
                }
            }
            indexArr=nextIndex;
        }
        return res;
    }

    private int getMin(List<Integer>indexArr,List<Integer>list){
        int min=list.get(indexArr.get(0));
        for(int i=1;i<indexArr.size();i++){
            int index=indexArr.get(i);
            if(list.get(index)<min){
                min=list.get(index);
            }
        }
        return min;
    }
```



- 有负权值，那就全部都算，累计求和（dp）
  - 这么说图的最短路径问题可以归结为dp？或者说贪心

```java
    public int minimumTotal(List<List<Integer>> triangle) {
        int n=triangle.size();

        int [][]arr=new int[n][n];
        arr[0][0]=triangle.get(0).get(0);
        for(int i=1;i<n;i++){
            List<Integer>list=triangle.get(i);
            for(int j=0;j<list.size();j++){

                int min=Integer.MAX_VALUE;
                if(j-1>=0 ){
                    //上一行的值
                    min=Math.min(min,arr[i-1][j-1]);
                }
                if(j<triangle.get(i-1).size()){
                    min=Math.min(min,arr[i-1][j]);
                }
                arr[i][j]=min+list.get(j);
            }
        }
        int res=arr[n-1][0];
        for(int i=1;i<arr[n-1].length;i++){
            if(arr[n-1][i]<res){
                res=arr[n-1][i];
            }
        }
        return  res;
    }

```



## ==DFS==

### 130.被围绕的区域

- 二维数组，把被围绕的O变为X
- 深度优先
  - 不仅仅用于图，只是一种搜索方式
  - 对二维图的每个点进行dfs，在遍历的时候进行标记

```java
    public void solve(char[][] board) {

        //要2个for，不能一个
        for(int i=0;i<board[0].length;i++){
            dfs(board,0,i);
            dfs(board,board.length-1,i);
        }
        for(int i=1;i<board.length-1;i++){
            dfs(board,i,0);
            dfs(board,i,board[0].length-1);
        }

        for(int i=0;i<board.length;i++){
            for (int j=0;j<board[0].length;j++){
                if(board[i][j]=='O'){
                    board[i][j]='X';
                    continue;
                }
                if(board[i][j]=='M'){//复原
                    board[i][j]='O';
                }
            }
        }

    }

    //标记dfs,从边界搜索二维数组
    public void dfs(char[][]board,int i,int j){
        //!='O'  排除了M(防止死循环)，和X     不是O就不能往dfs前走了
        if(i>=board.length || i<0 || j>=board[0].length || j<0 || board[i][j]!='O'){
            return;
        }
        board[i][j]='M'; //能够遍历进来，说明O可达
        dfs(board,i-1,j);
        dfs(board,i+1,j);
        dfs(board,i,j-1);
        dfs(board,i,j+1);
    }
```

- 广度优先
- 并查集
  - 把二维的坐标转为一维坐标，一个连通区域算一个集合。假设一开始每个坐标都是一个集合

### 200.岛屿数量

- 1陆地，0是水
- 深度优先
  - 在遍历时进行标记

```java
    int m,n;
    public int numIslands(char[][] grid) {
        m=grid.length;
        n=grid[0].length;
        int count=0;
        boolean[][] marks=new boolean[m][n];
        //Arrays.fill(marks,false); 二维数组好像不太行  只能一维基本类型数组
        for (boolean[] mark : marks) {
            Arrays.fill(mark, false);
        }

        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                if(grid[i][j]=='1' && !marks[i][j]){
                    count++;//在这里统计就好了
                    dfs(grid,marks,i,j);//标记完所有1
                }
            }
        }
        return count;
    }

    public void dfs(char[][]grid,boolean[][]marks,int i,int j){
        if(i>=m || i<0 || j>=n || j<0 || grid[i][j]=='0' || marks[i][j]){
            return;
        }
        marks[i][j]=true;
        dfs(grid,marks,i-1,j);
        dfs(grid,marks,i+1,j);
        dfs(grid,marks,i,j-1);
        dfs(grid,marks,i,j+1);
    }
```

- 广度优先
- 并查集

### 547.省份数量

- 都是在dfs之前count++，dfs只是为了标记
  - 对每个城市进行dfs，标记

```java
    int n;
    public int findCircleNum(int[][] isConnected) {
        int count=0;
        n=isConnected[0].length;
        boolean[]visited=new boolean[n];
        Arrays.fill(visited,false);
        for(int i=0;i<n;i++){
            if(!visited[i]){//只在这里count++
                count++;
                dfs(i,isConnected,visited);
            }
        }
        return count;
    }

    private void dfs(int i,int [][]isConnected,boolean[]visited){
        for(int j=0;j<n;j++){
            if(isConnected[i][j]==1 && !visited[j]){
                visited[j]=true;
                dfs(j,isConnected,visited);
            }
        }
    }
```

## ==滑动窗口==

- 双指针，先移动右指针j，再移动左指针i。
- 窗口大小可变可不变
- 用一个变量，数组，Set等容器来存储当前数据，若右到达边界后是要更新容器并移动左指针
- 关键在于选择什么结构来存储数据

### 209.长度最小的子数组

- sum(nums,i,j)>=target
  - sum>=target 不满足条件时滑动窗口

```java
    public int minSubArrayLen(int target, int[] nums) {
        int len=Integer.MAX_VALUE;
        int left=0;
        int right=0;
        int sum=0;
        while (right<nums.length){
            sum+=nums[right];
            while (sum>=target){
                len=Math.min(len,right-left+1);
                sum-=nums[left++];
            }
            right++;
        }
        return len==Integer.MAX_VALUE?0:len;
    }
```

### 219.存在重复的元素二

- 存在nums[i] == nums[j]` 且 `abs(i - j) <= k？
- 暴力

```java
    public boolean containsNearbyDuplicate(int[] nums, int k) {
        for(int i=0;i<nums.length;i++){
            for(int j=i+1;j<=i+k && j<nums.length;j++){
                if(nums[i]==nums[j]){
                    return true;
                }
            }
        }
        return false;
    }
```

- 滑动窗口
  - 遍历时有窗口限制
  - 维持一个存了nums[i-k-1]-nums[i-1] 的HashSet

```java
    public boolean containsNearbyDuplicate(int[] nums, int k) {
        Set<Integer>set=new HashSet<>();
        for(int i=0;i<nums.length;i++){
            if(i>k){
                //未必需要有明显的另一个指针
                set.remove(nums[i-k-1]);//一进一出完成更新
            }
            if(!set.add(nums[i])){
                return true;
            }
        }
        return false;
    }
```

### 76.最小覆盖字串

- 滑动窗口
  - 首先移动r，不满足时再考虑移动l

```java
    public String minWindow(String s, String t) {
        if (s == null || s == "" || t == null || t == "" || s.length() < t.length()) {
            return "";
        }
        //维护两个数组，记录已有字符串指定字符的出现次数，和目标字符串指定字符的出现次数
        //ASCII表总长128
        int[] need = new int[128];
        int[] have = new int[128];

        //将目标字符串指定字符的出现次数记录
        for (int i = 0; i < t.length(); i++) {
            need[t.charAt(i)]++;
        }

        //分别为左指针，右指针，最小长度(初始值为一定不可达到的长度)
        //已有字符串中目标字符串指定字符的出现总频次以及最小覆盖子串在原字符串中的起始位置
        int left = 0, right = 0, min = s.length() + 1, count = 0, start = 0;
        while (right < s.length()) {
            char r = s.charAt(right);
            if (need[r] == 0) {
                right++;
                continue;
            }
            //当且仅当已有字符串目标字符出现的次数小于目标字符串字符的出现次数时，count才会+1
            //是为了后续能直接判断已有字符串是否已经包含了目标字符串的所有字符，不需要挨个比对字符出现的次数
            if (have[r] < need[r]) {
                count++; // 匹配了t的个数
            }
            //已有字符串中目标字符出现的次数+1
            have[r]++;
            //移动右指针
            right++;
            //当且仅当已有字符串已经包含了所有目标字符串的字符，且出现频次一定大于或等于指定频次
            // 因为是求最小，所以一满足就可以判断了
            while (count == t.length()) {
                //挡窗口的长度比已有的最短值小时，更改最小值，并记录起始位置
                if (right - left < min) {
                    min = right - left;
                    start = left;
                }
                char l = s.charAt(left);
                //如果左边即将要去掉的字符不被目标字符串需要，那么不需要多余判断，直接可以移动左指针
                if (need[l] == 0) {
                    left++;
                    continue;
                }
                //如果左边即将要去掉的字符被目标字符串需要，且出现的频次正好等于指定频次，那么如果去掉了这个字符，
                //就不满足覆盖子串的条件，此时要破坏循环条件跳出循环，即控制目标字符串指定字符的出现总频次(count）-1
                if (have[l] == need[l]) {
                    count--; // 减少一次就退出循环，不再满足覆盖子串。也合理
                }
                //已有字符串中目标字符出现的次数-1
                have[l]--;
                //移动左指针
                left++;
            }
        }
        //如果最小长度还为初始值，说明没有符合条件的子串
        if (min == s.length() + 1) {
            return "";
        }
        //返回的为以记录的起始位置为起点，记录的最短长度为距离的指定字符串中截取的子串
        return s.substring(start, start + min);
    }
```

## ==位运算==

- 有时也用int来存储数据

### 187.重复的DNA序列

- 对于一些小的字符串或字符，可以考虑用int来存储。返回所有在 DNA 分子中出现不止一次的 长度为 10 的序列(子字符串)
- 把str用int存储。。
  
  - 由于 sss 中只含有 444 种字符，我们可以将每个字符用 222 个比特表示，即：
  
    A 表示为二进制 00
    C表示为二进制 01
    G 表示为二进制 10
    T 表示为二进制 11
  
  - HashMap+滑动窗口+位运算    。优化了存储。。。
  
  - 位运算减少了一个for循环的复杂度

```java
    static final int L = 10;
    Map<Character, Integer> bin = new HashMap<Character, Integer>() {{
        put('A', 0);
        put('C', 1);
        put('G', 2);
        put('T', 3);
    }};

    public List<String> findRepeatedDnaSequences(String s) {
        List<String> ans = new ArrayList<String>();
        int n = s.length();
        if (n <= L) {
            return ans;
        }
        int x = 0;
        for (int i = 0; i < L - 1; ++i) {
            //前10个字符
            x = (x << 2) | bin.get(s.charAt(i));
        }
        Map<Integer, Integer> cnt = new HashMap<Integer, Integer>();
        for (int i = 0; i <= n - L; ++i) {
            // 左移表示出栈一个字符， 或表示新加入一个字符
            // 1 << (L * 2)  表示高于20位要置为0，不然影响判断
            x = ((x << 2) | bin.get(s.charAt(i + L - 1))) & ((1 << (L * 2)) - 1);
            cnt.put(x, cnt.getOrDefault(x, 0) + 1);
            if (cnt.get(x) == 2) {
                ans.add(s.substring(i, i + L));
            }
        }
        return ans;
    }
```

# 其他

## ==找规律==

### 96.不同的二叉搜索树数量

- 给节点数n，求能构成的二叉搜索树种类
- 有点动态规划的意思，但是我更倾向于找规律

```java
    /**
     * 以该点为根节点的个数=左右子树相乘
     * nums[]
     * 1
     * 1    1
     * 2    1   2
     * 5    2   2   5
     * 14   5   4   5   14
     *
     * eachNode[]   要多一个保证eachNode[0]*eachNode[1]有值
     * 1    1    2   5   14  42
     */
    public int numTrees(int n) {
        int []nums=new int[n];
        int []eachNode=new int[n+1];
        nums[0]=1;
        eachNode[0]=1;
        eachNode[1]=1;

        for(int i=1;i<n;i++){
            int sum=Arrays.stream(nums).sum();   //nums[i]作为根节点
            nums[i]=sum;

            for(int j=0;j<i;j++){
                nums[j]=eachNode[j]*eachNode[i-j];   //左右树相乘，并利用对称性
            }
            eachNode[i+1]=Arrays.stream(nums).sum();   //输入为i时的最终结果
        }
        return eachNode[n];
    }
```

- 动态规划肯定可以，自己看吧

  ```java
  class Solution {
      public int numTrees(int n) {
          int[][] f = new int[n + 10][n + 10];
          for (int i = 0; i <= n + 1; i++) 
              // 因为因为下面是乘，所以满足条件的都初始化为1
              for (int j = 0; j <= n + 1; j++) {
                  if (i >= j) f[i][j] = 1;
              }
          }
          for (int len = 2; len <= n; len++) {
              for (int l = 1; l + len - 1 <= n; l++) {
                  int r = l + len - 1;
                  // 对这个区间范围内累加，累加值为左右子树的乘积
                  for (int i = l; i <= r; i++) {
                      f[l][r] += f[l][i - 1] * f[i + 1][r];
                  }
              }
          }
          return f[1][n];
      }
  }
  ```

## ==迭代==

- 只是遍历，可能用到当前状态前面的值，但是算不上动态规划

### 121.买股票的最佳时机

- 一个所有天数的prices数组，只能先买入再卖出。求最大利润
- 动态规划，第i天卖出最多能赚多少

```java
 public int maxProfit(int[] prices) {
        int result=0;
        int min=prices[0];//记录第i天前的最小值
        for(int num:prices){
            if(num<min){
                min=num;
            }
            if(result<num-min){
                result=num-min;
            }
        }
        return result;
  }
```

### 566.重塑矩阵

- 二维数组 `mat` 表示的 `m x n` 矩阵，重构的矩阵的行数r和列数c。
  - 行遍历顺序填充,不匹配则返回原矩阵
- 一次遍历

```java
 public int[][] matrixReshape(int[][] mat, int r, int c) {
        if(r*c!=mat.length*mat[0].length){
            return mat;
        }
        int [][] res=new int[r][c];
        int r1=0;
        int c1=0;
        for(int i=0;i<mat.length;i++){
            for(int j=0;j<mat[0].length;j++){
                if(c1>=c){//不是 c1>c。因为c是列数
                    c1=0;
                    r1++;
                }
                res[r1][c1++]=mat[i][j];
            }
        }
        return res;
    }
```

### 118.杨辉三角

- 给定一个非负整数 *`numRows`，*生成「杨辉三角」的前 *`numRows`* 行。
- O(n2)。有些算法就是已经到了算法下界，因为每个元素都要赋值

```java
public List<List<Integer>> generate(int numRows) {
        List<List<Integer>>lists=new ArrayList<>();

        for(int j=0;j<numRows;j++){
            List <Integer>list=new ArrayList<>();
            for(int i=0;i<=j;i++){
                if(i==0||i==j){
                    list.add(1);
                }
                else {
                    list.add(lists.get(j - 1).get(i - 1) + lists.get(j - 1).get(i));
                }
            }
            lists.add(list);
        }
        return lists;
    }
```

## ==String==

### 05.替换空格(剑指)

- StringBuilder

```java
class Solution {
    public String replaceSpace(String s) {
        if(s==null){
            return null;
        }

        //遇到' '则追加"&20"
        String str="%20";
        StringBuilder strb=new StringBuilder();
        for(int i=0;i<s.length();i++){
            if(s.charAt(i)!=' '){
                strb.append(s.charAt(i));
            }else{
                strb.append(str);
            }
        }

        return strb.toString();
    }
}
```

### 58.左旋转字符串(剑指)

- 字符串的左旋转操作是把字符串前面的若干个字符转移到字符串的尾部。请定义一个函数实现字符串左旋转操作的功能。比如，输入字符串"abcdefg"和数字2，该函数将返回左旋转两位得到的结果"cdefgab"。
- 能用java自带方法就用，代码越少越ok。

```java
class Solution {
    public String reverseLeftWords(String s, int n) {
        if(s==null){
            return null;
        }

        /*
        只是字符串拼接可以不用StringBuilder
        StringBuilder strb=new StringBuilder(s.substring(n,s.length()));
        strb.append(s.substring(0,n));

        return strb.toString();
        */

        //剪切+拼接
        return s.substring(n,s.length())+s.substring(0,n);
    }
}
```

### 3.无重复字符的最长字串

- 给定一个字符串 `s` ，请你找出其中不含有重复字符的 最长子串 的长度。

```java
	/*
    st记录当前位置能回溯到的最前位置。滑动窗口的最左边
    HashMap记录是否重复
    不断更新st和max
     */
public int lengthOfLongestSubstring(String s) {
        Map<Character, Integer>map=new HashMap<>();
        int st=0;           //第i个位置前最大不重复串的起始
        int max=0;
        for(int i=0;i<s.length();i++){
            char c=s.charAt(i);
            if(!map.containsKey(c)){
                map.put(c,i);
                max=Math.max(max,i-st+1);
            }else {
                st=Math.max(map.get(c)+1,st);//更新为最大可用值
                map.put(c,i);
                max=Math.max(max,i-st+1);   //。要加上，否则"tmmzuxt"最后的t会没被算
            }
        }
        return max;
    }
```

### 5.最长回文字串

- 找重复字符的开头和结尾，然后中心扩展

```java
public String longestPalindrome(String s) {
        int st=0;
        int end=0;//不包括end
        int max=1;
        int i=0;
        while (i<s.length()){
            int k=i;
            int j=i;

            //找到重复字符的开始和结尾
            while (j>0 && s.charAt(j-1)==s.charAt(i)){
                j--;
            }
            while (k<s.length()-1 && s.charAt(k+1)==s.charAt(i)){
                k++;
            }

            while (j-1>=0 && k+1<=s.length()-1){
                if(s.charAt(j-1)==s.charAt(k+1)){
                    j--;
                    k++;
                }else {
                    break;
                }
            }

            int len=k-j+1;
            if(len>max){
                st=j;
                end=k;
                max=len;
            }
            i++;
        }

        return s.substring(st,end+1);
    }
```

### 557.反转字符串中的单词

- 给定一个字符串 `s` ，你需要反转字符串中每个单词的字符顺序，同时仍保留空格和单词的初始顺序。
- 一步步处理，直到满足要求

```java
public String reverseWords(String s) {
        int start=0;
        int end=s.length()-1;

        //不计算前后空格
        while (start<=end && s.charAt(start)==' ' ){
            start++;
        }
        while ( start<=end  && s.charAt(end)==' '){
            end--;
        }

        StringBuilder res=new StringBuilder();
        int index=start;//记录每个单词开头
        for(int i=start;i<=end;i++){
            if(s.charAt(i)==' ' || i==end){//找到了单词的结尾
                //存进res
                int j=i;
                if(s.charAt(i)==' '){//处理空格
                    j--;
                }
                while (j>=index){//反转
                    res.append(s.charAt(j--));
                }
                if(i!=end){
                    res.append(' ');//处理空格
                }

                //找到下个单词的开头
                index=i;
                while (s.charAt(index)==' '){
                    index++;
                }
            }
        }
        return res.toString();
    }
```

### 6.Z字形变换

- 给字符串，进行Z字形排列

- 直接排

```java
    public String convert(String s, int numRows) {
        StringBuilder res=new StringBuilder();
        //可改用char[][]
        StringBuilder[]list=new StringBuilder[numRows];
        for(int i=0;i<numRows;i++){
            list[i]=new StringBuilder();
        }

        int i=0;
        int rowIndex=0;//存放的是哪一列
        boolean isDown=true;//向上或向下
        while (i<s.length()){

            list[rowIndex].append(s.charAt(i));
            if(!isDown){//向上时每行加空格
                for(int j=0;j<rowIndex;j++){
                    if(i!=j){
                        list[j].append(" ");
                    }
                }
            }

            //改变方向
            if(rowIndex==numRows-1)
                isDown=false;
            if(rowIndex==0)
                isDown=true;
            //防止越界，为了处理AB 1
            if(isDown && rowIndex!=numRows-1)
                rowIndex++;
            if(!isDown && rowIndex!=0)
                rowIndex--;
            i++;
        }

        //从左到右，再从上到下遍历
        for(StringBuilder strb:list){
            for(int k=0;k<strb.length();k++){
                if(strb.charAt(k)!=' '){
                    res.append(strb.charAt(k));
                }
            }
        }
        return res.toString();
    }
```

- 数学周期函数

### 12.整数转罗马数

- 每一位列举十个
- 找到规律

```java
    public String intToRoman(int num) {
        String[][]strs={
                //加个0为了处理余数为0
                {"","I","II","III","IV","V","VI","VII","VIII","IX"},
                {"","X","XX","XXX","XL","L","LX","LXX","LXXX","XC"},
                {"","C","CC","CCC","CD","D","DC","DCC","DCCC","CM"},
                {"","M","MM","MMM"}
        };
        StringBuilder res=new StringBuilder();
        int i=0;
        while (num>0){
            int remainder=num%10;
            num/=10;
            res.insert(0,strs[i++][remainder]);//从前面插入
        }
        return res.toString();
    }
```

- 只列举不重复的

```java
    public String intToRoman(int num) {
        //直列举不重复的基本单元
        int []values={1000,900,500,400,100,90,50,40,10,9,5,4,1};
        String[]symbols= {"M","CM","D","CD","C","XC","L","XL","X","IX","V","IV","I"};
        StringBuilder res=new StringBuilder();
        for(int i=0;i<values.length;i++){
            //1,10的倍数可以累加，其他的一减就没了
            while (num>=values[i]){
                num-=values[i];
                res.append(symbols[i]);
            }

        }
        return res.toString();
    }
```

### 14.最长公共前缀（两个for而已）

- 纵向扫描，res长度为0一个个增加

```java
    public String longestCommonPrefix(String[] strs) {
        StringBuilder res=new StringBuilder();
        for(int i=1;i<strs[0].length()+1;i++){
            //每次拿一个字符也行，然后记录下能拿多少个
            StringBuilder strb=new StringBuilder(strs[0].substring(0,i));
            boolean isAllContains=true;
            for (int j=1;j<strs.length;j++){
                //首先i不能越界
                if(i>strs[j].length() ||!strs[j].substring(0,i).contains(strb)) {
                    isAllContains = false;
                    break;
                }
            }
            if(isAllContains){
                res=strb;
            }else {
                return res.toString();
            }
        }
        return res.toString();
    }
```

- 还有横向扫描也行，先找第一个和第二个的前缀，然后和第三个比，更新前缀......不断更新

### 28.实现strStr()

- 找子串位置
- 遍历

```java
    public int strStr(String haystack, String needle) {
        if(needle==null){
            return 0;
        }
        for(int i=0;i<=haystack.length()-needle.length();i++){
            if(haystack.substring(i,i+needle.length()).equals(needle)){
                return i;
            }
        }
        return -1;
    }
```

- KMP。。。

### 38.外观数列

- 「外观数列」是一个整数序列，从数字 1 开始，序列中的每一项都是对前一项的描述
- 递归并遍历。。

```java
    public String countAndSay(int n) {
        List<StringBuffer> res=new ArrayList<>();
        res.add(new StringBuffer("1"));

        for (int i=1;i<n;i++) {
            StringBuffer strb=res.get(i-1);
            StringBuffer next=new StringBuffer();
            char c=strb.charAt(0);
            int j=0;
            int count=0;
            while (j<strb.length()){
                if(strb.charAt(j)==c){
                    count++;
                }
                if(strb.charAt(j)!=c){
                    next.append(count).append(c);
                    count=1;
                    c=strb.charAt(j);
                }
                j++;
            }
            next.append(count).append(c);
            res.add(next);
        }
        return res.get(n-1).toString();
    }
```

### 56.最后一个单词长度

- 遍历

```java
    public int lengthOfLastWord(String s) {
        int res=0;
        int index=s.length()-1;
        while (!(s.charAt(index)>='a'&& s.charAt(index)<='z' || s.charAt(index)>='A'&&s.charAt(index)<='Z'))         {
            index--;
        }
        for(int i=index;i>=0;i--){
            if(s.charAt(i)>='a'&& s.charAt(i)<='z' || s.charAt(i)>='A'&&s.charAt(i)<='Z'){
                res++;
            }else {
                break;
            }
        }
        return res;
    }
```

### 125.验证回文字符串

- 如果在将所有大写字符转换为小写字符、并移除所有非字母数字字符之后，短语正着读和反着读都一样。则可以认为该短语是一个 **回文串** 
- 双指针逐一比较（没有使用额外空间

```java
    public boolean isPalindrome(String s) {
        int left=0;
        int right=s.length()-1;

        while (left<=right){
            //当left==right 就会退出
            while (left<right && !Character.isDigit(s.charAt(left)) && !Character.isAlphabetic(s.charAt(left))){
                left++;
            }
            char leftChar = Character.isUpperCase(s.charAt(left))?Character.toLowerCase(s.charAt(left)):s.charAt(left);

            while (right>left && !Character.isDigit(s.charAt(right)) && !Character.isAlphabetic(s.charAt(right))){
                right--;
            }
            char rightChar = Character.isUpperCase(s.charAt(right))?Character.toLowerCase(s.charAt(right)):s.charAt(right);
            if(leftChar!=rightChar){
                return false;
            }
            right--;
            left++;
        }
        return true;
    }
```

## ==数组处理==

### 48.旋转图像

- 给定一个 *n* × *n* 的二维矩阵 `matrix` 表示一个图像。请你将图像顺时针旋转 90 度

````java
//由外向内旋转    
public void rotate(int[][] matrix) {
        int len=matrix.length;
        int i=0;
        int n=len;
        while (n>1) {
            getMatrix(matrix,n,i,len-i-1);
            n-=2;//大小减2
            i+=1;
        }
    }

    private void getMatrix(int [][]matrix,int n,int st,int end){
        int []nums1=new int[n-1];
        int []nums2=new int[n-1];

        int index=0;
        for(int i=st;i<end;i++){//上->右
            nums2[index++]=matrix[i][end];//右（临时保存
            matrix[i][end]=matrix[st][i];
        }

        index=n-2;
        for(int i=st+1;i<=end;i++){
            nums1[index]=matrix[end][i];//下
            matrix[end][i]=nums2[index--];
        }

        index=n-2;
        for(int i=st+1;i<=end;i++){
            nums2[index]=matrix[i][st];//左
            matrix[i][st]=nums1[index--];
        }

        index=0;
        for(int i=st;i<end;i++){
            matrix[st][i]=nums2[index++];
        }
    }
````

- 官方
  - 先上下翻转，再对角互换。matrix(new)[col] [n−row−1]=matrix[row] [col]

```java
public void rotate(int[][] matrix) {
    int n=matrix.length;

    for(int i=0;i<n/2;i++){
        for(int j=0;j<n;j++){
            int temp=matrix[i][j];
            matrix[i][j]=matrix[n-1-i][j];
            matrix[n-1-i][j]=temp;
        }
    }

    for(int i=0;i<n;i++){
        for(int j=i;j<n;j++){
            int temp=matrix[i][j];
            matrix[i][j]=matrix[j][i];
            matrix[j][i]=temp;
        }
    }
}
```

### 75.颜色分类

- 给定一个包含红色、白色和蓝色、共 n 个元素的数组 nums ，原地对它们进行排序，使得相同颜色的元素相邻，并按照红色、白色、蓝色顺序排列。我们使用整数 0、 1 和 2 分别表示红色、白色和蓝色。必须在不使用库的sort函数的情况下解决这个问题。
- 分别记录当前0、1、2该插入的位置  

```java
public void sortColors(int[] nums) {
        int x=0;//0的当前末尾
        int y=0;//1
        int z=0;//2
        int n=nums.length;
        for(int i=0;i<n;i++){
            if(nums[i]==0){
                swap(nums,x,i);
                x++;y++;z++;  //一看就懂，不再注释
            }
            else if(nums[i]==1){
                swap(nums,y,i);
                y++;z++;
            }else {
                swap(nums,z,i);
                z++;
            }
        }
    }
	//整体向后移动。感觉不是很好
    private void swap(int[]nums,int st,int end){
        int tmp=nums[end];
        while (end>st){
            nums[end]=nums[end-1];
            end--;
        }
        nums[st]=tmp;
    }
```

- 0交换到开头，2交换到结尾。只做交换，不做后移

```java
public void sortColors(int[] nums) {
        int x=0;//0的当前位置
        int y=nums.length-1;//2当前位置
        int n=nums.length;
        for(int i=0;i<n;i++){  //先确定0
            if(nums[i]==0&&i!=x){
                swap(nums,i,x);
                x++;
                if(i!=0)
                    i--;//换回来的还是0能继续换
            }
        }
        for(int i=n-1;i>=0;i--){
            if(nums[i]==2&&i!=y){
                swap(nums,i,y);
                y--;
                if(i!=n-1)
                    i++;//若换回来还是2则能继续换
            }
        }
    
    }
    private void swap(int []nums,int i,int j){
        int tmp=nums[i];
        nums[i]=nums[j];
        nums[j]=tmp;
    }
```

### 977.有序数组的平方

- 别轻易改变原引用指向的值，除非特别说明。返回的值，该创建新的就创建
- 找不到小的，可以先放大的

```java
 public int[] sortedSquares(int[] nums) {
        int []res=new int[nums.length];//改变原来的数组反而更加复杂

        int i=0;
        int j=nums.length-1;
        int index=j;
        while(i<=j){
           if(nums[j]*nums[j]>nums[i]*nums[i]){
               res[index--]=nums[j]*nums[j];//头尾比较，先放大的
               j--;
           }else {
               res[index--]=nums[i]*nums[i];
               i++;
           }

        }
        return  res;
}
```

### 189.轮转数组

- 给你一个数组，将数组中的元素向右轮转 `k` 个位置，其中 `k` 是非负数。
- 观察结果的结构,题目的讲解有误导性，能批处理就用，尽量不要一次处理一个
- 有时候太过复杂的方法、有太多限制的方法可以直接抛弃了。
- 666

```java
public void rotate(int[] nums, int k) {
        k%=nums.length;
        reverse(nums,0,nums.length-1);//整体倒放
        reverse(nums,0,k-1);//倒放前k个
        reverse(nums,k,nums.length-1);//后面的也倒放
    }
    public void reverse(int []arr,int start,int end){
        while (start<end){
            int temp=arr[start];
            arr[start]=arr[end];
            arr[end]=temp;
            end--;
            start++;
        }
    }
```

### 283.移动零

- 给定一个数组 `nums`，编写一个函数将所有 `0` 移动到数组的末尾，同时保持非零元素的相对顺序。
- 建议考虑好所有特殊情况再提交

```java
public void moveZeroes(int[] nums) {
        int count=nums[0]!=0?1:0;//当前非0数应该放的位置
        for(int i=1;i<nums.length;i++){
            if(nums[i]!=0){
                if(count!=i){     //count和i之间有0
                    nums[count]=nums[i];//交换
                    nums[i]=0;
                }
               count++;
            }
        }
}//小小的程序竟然多次提交出错。。
```

### 54.螺旋矩阵

- 给你一个 `m` 行 `n` 列的矩阵 `matrix` ，请按照 顺时针螺旋顺序，返回矩阵中的所有元素

```java
    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer>res=new ArrayList<>();
        int x=matrix.length-1;
        int y=matrix[0].length-1;
        int i=0;
        int j=0;
        while (i<=x && j<=y) {
            order(res,matrix,i++,j++,x--,y--);
        }
        return res;
    }

    //m,n是左上角，x，y是右下角
    private void order(List<Integer>res,int[][] matrix,int m,int n,int x,int y){

        for(int i=n;i<=y;i++){
            res.add(matrix[m][i]);
        }
        for (int i=m+1;i<x;i++){
            res.add(matrix[i][y]);
        }

        if(m!=x){
            for(int i=y;i>=n;i--){
                res.add(matrix[x][i]);
            }
        }
        if(n!=y){//不重叠
            for(int i=x-1;i>=m+1;i--){
                res.add(matrix[i][n]);
            }
        }
    }
```

### 59. 螺旋矩阵 II

- 给你一个正整数 `n` ，生成一个包含 `1` 到 `n2` 所有元素，且元素按顺时针顺序螺旋排列的 `n x n` 正方形矩阵 `matrix`
- 按层来填

```java
    int [][]res;
    int num=1;
    public int[][] generateMatrix(int n) {
        res=new int[n][n];
        int i=0;
        int j=n-1;
        while (i<=j){
            round(i,i,j,j);
            i++;
            j--;
        }
        return res;
    }

    //左上右下，当前数字
    private void round(int x, int y, int m, int n) {
        for(int i=y;i<=n;i++){
            res[x][i]=num++;
        }
        for(int i=x+1;i<=m-1;i++){
            res[i][n]=num++;
        }

        if(x!=m){
            for(int i=n;i>=y;i--){
                res[m][i]=num++;
            }
        }
        if(y!=n){
            for(int i=m-1;i>=x+1;i--){
                res[i][y]=num++;
            }
        }
    }
```

### 66.加一

- 给定一个由 整数 组成的 非空 数组所表示的非负整数，在该数的基础上加一。最高位数字存放在数组的首位， 数组中每个元素只存储单个数字。你可以假设除了整数 0 之外，这个整数不会以零开头

```java
    public int[] plusOne(int[] digits) {
        boolean isAllNine=true;
        for(int digit:digits){
            if(digit!=9){
                isAllNine=false;
                break;
            }
        }
        if(isAllNine){
            //全9
            int[]result=new int[digits.length+1];
            result[0]=1;
            return result;
        }else {
            for(int i=digits.length-1;i>=0;i--){
                if(digits[i]==9){
                    digits[i]=0;
                }else {
                    //第一个不为9的数加一
                    digits[i]=digits[i]+1;
                    break;
                }
            }
            return digits;
        }
    }
```

### 136.只出现一次的数字

- 给你一个 **非空** 整数数组 `nums` ，除了某个元素只出现一次以外，其余每个元素均出现两次
- 异或

```java
    //异或    相同为0
    //位运算处理的是二进制位，同时可以映射到十进制
    public int singleNumber(int[] nums) {
        int result=nums[0];
        for(int i=1;i<nums.length;i++){
            result^=nums[i];
        }
        return result;
    }
```

### 37.只出现一次的数字二

- 给你一个整数数组 `nums` ，除某个元素仅出现 **一次** 外，其余每个元素都恰出现 **三次 。**请你找出并返回那个只出现了一次的元素
- 余数法，每位相加后除以3的余数

```java
    //一位位算
    //所有出现三次的数的每位和加起来是3的倍数，目标总的余数
    public int singleNumber(int[] nums) {
        int res=0;
        int base=1;
        for(int i=0;i<32;i++){
            int total=0;
            for(int n:nums){
                //验证第i位是否是0 ，且不改变n
                total+=((n&(base<<i))==0)?0:1;
            }
            //跟第i位或
            res|=total%3<<i;   // 每一位都是所有位数加起来除以3的余数。 1或者0
        }
        return res;
    }
```



## ==数字处理==

### 7.整数反转

- x是32位有符号数，反转。若超出int范围则返回0，假设计算机不存储64位。(-120->-21)

```java
    public int reverse(int x) {
        int res=0;
        int symbol;//处理符号，为了后面范围判断
        if(x<0){ 
            x=-x;
            symbol=-1;
        }else {
            symbol=1;
        }

        while (x>0){
            int remainder=x%10;//余数
            x/=10;

            //超出范围返回0,答案的没有我的严谨。。
            if((res>Integer.MAX_VALUE/10 )||
            (symbol>0 && res==Integer.MAX_VALUE/10 && remainder>=7)||
                    (symbol<0 && res==Integer.MAX_VALUE/10 && remainder>=8)
                   ){
                return 0;
            }

            res=res*10+remainder;
        }
        return res*symbol;
    }
```

### 9.回文数

- 0->0      -121->121-  

```java
    public boolean isPalindrome(int x) {
        if(x<0){
            return false;
        }
        int res=0;
        int n=x;
        while (n>0){
            int remainder=n%10;
            n/=10;
            res=res*10+remainder;
        }
        return res==x;
    }
```



## ==数学==

### 6.Z字形变换

- 给字符串，进行Z字形排列
- 周期性。总周期是2*numRows-2,但中间行还有第二个字符

```java
    public String convert(String s, int numRows) {
        StringBuilder res=new StringBuilder();
        int cycle=numRows!=1?2*numRows-2:1;   //周期  numRows=1时是1
        for(int i=0;i<numRows;i++){
            int j=i;
            while (j<s.length()){
                res.append(s.charAt(j));
                if(i!=0 && i!=numRows-1){//中间行都有第二个字符
                    int k=(j/cycle+1)*cycle-i;//自己算
                    if(k<s.length())
                        res.append(s.charAt(k));
                }
                j+=cycle;    //每次加一周期
            }

        }
        return res.toString();
    }
```

### 62.不同路径

- 向下或向右。m*n格，有多少条不同路径到达右下角
- 排列组合。
  - 一共走m+n-2步，其中m-1步是向下。C(m+n-2)(m-1).从m+n-2中选m-1个。组合问题
  - 6

```java
public int uniquePaths(int m, int n) {
    long ans = 1;//防止越界
    int x=n;
    for (int y = 1; y < m;  y++) {//目前就这样写才不越界
        ans = ans * x / y;
        x++;
    }
    return (int) ans;
}
```

### 67.二进制求和

- 模拟

```java
    public String addBinary(String a, String b) {
        StringBuilder strb=new StringBuilder();
        int carry=0;//进位
        int maxLen=Math.max(a.length(),b.length());
        int m=a.length()-1;
        int n=b.length()-1;
        for(int i=0;i<maxLen;i++){
            if(i!=a.length() && i!=b.length()){
                int sum=(a.charAt(m-i)-'0')+(b.charAt(n-i)-'0')+carry;//情况0，1，2，3
                strb.insert(0,sum%2);
                carry=sum>1?1:0;
            }else {
                if(i==a.length()){//a长度较短
                    for(int j=i;j<b.length();j++){
                        int sum=(b.charAt(n-j)-'0')+carry;//情况0，1，2，3
                        strb.insert(0,sum%2);
                        carry=sum>1?1:0;
                    }
                }else {
                    for(int j=i;j<a.length();j++){
                        int sum=(a.charAt(m-j)-'0')+carry;//情况0，1，2，3
                        strb.insert(0,sum%2);
                        carry=sum>1?1:0;
                    }
                }
                break;
            }
        }
        if(carry==1){//还有进位
            strb.insert(0,1);
        }
        return strb.toString();
    }
```

- 官方
  - carry每次权值减半，进位

```java
class Solution {
    public String addBinary(String a, String b) {
        StringBuffer ans = new StringBuffer();

        int n = Math.max(a.length(), b.length()), carry = 0;
        for (int i = 0; i < n; ++i) {
            carry += i < a.length() ? (a.charAt(a.length() - 1 - i) - '0') : 0;
            carry += i < b.length() ? (b.charAt(b.length() - 1 - i) - '0') : 0;
            ans.append((char) (carry % 2 + '0'));
            carry /= 2;
        }

        if (carry > 0) {
            ans.append('1');
        }
        ans.reverse();

        return ans.toString();
    }
}
```

### 69.x的平方根

- 给你一个非负整数 `x` ，计算并返回 `x` 的 **算术平方根**。不允许使用任何内置指数函数和算符，例如 `pow(x, 0.5)` 或者 `x ** 0.5` 

- 二分法

```java
    public int mySqrt(int x) {
        int left=0;
        int right=x;
        int result=0;
        while (left<=right){
            int mid=(left+right)/2;
            long product=(long)mid*mid;
            if(product==x || product<x && (long)(mid+1)*(mid+1)>x){
                result=mid;
                break;
            }
            if(product<x){
                left=mid+1;
            }else {
                right=mid-1;
            }
        }
        return result;
    }
```

### 119.杨辉三角

- 给定一个非负索引 `rowIndex`，返回「杨辉三角」的第 `rowIndex` 行。在「杨辉三角」中，每个数是它左上方和右上方的数的和
- 不使用额外 空间

```java
    public List<Integer> getRow(int rowIndex) {
        List<Integer>list=new ArrayList<>();
        list.add(1);
        for(int i=1;i<=rowIndex;i++){
            int pre=list.get(0);
            for(int j=1;j<list.size();j++){
                int now=list.get(j);
                list.set(j,list.get(j)+pre);
                pre=now;
            }
            list.add(1);
        }
        return list;
    }
```

## ==处理( x , y  )节点==

### 56.合并区间

- 以数组 intervals 表示若干个区间的集合，其中单个区间为 intervals[i] = [starti, endi] 。请你合并所有重叠的区间，并返回 一个不重叠的区间数组，该数组需恰好覆盖输入中的所有区间 。

```java
    //按照左端点进行排序。空间更少
    public int[][] merge(int[][] intervals) {
        List<Integer[]> list=new ArrayList<>();
        for (int[] interval : intervals) {
            list.add(new Integer[]{interval[0], interval[1]});
        }
        list.sort((x,y)->x[0]-y[0]);

        List<Integer[]>res=new ArrayList<>();
        list.forEach(node -> {
            if(res.size()==0){
                res.add(node);
            }else {
                Integer[] lastNode =res.get(res.size()-1);
                if(node[0]<=lastNode[1]){
                    Integer []newNode=new Integer[]{lastNode[0],Math.max(node[1],lastNode[1])};
                    res.remove(res.size()-1);
                    res.add(newNode);
                }else {
                    res.add(node);
                }
            }
        });
        int [][]result = new int[res.size()][2];
        for(int i=0;i<res.size();i++){
            result[i][0]=res.get(i)[0];
            result[i][1]=res.get(i)[1];
        }
        return result;
    }



    //官方，不用先转为list。时间更少，内存用得反而多了？
    public int[][] merge(int[][] intervals) {
        Arrays.sort(intervals,(x,y)->x[0]-y[0]);
        List<int []>res=new ArrayList<>();
        for (int[] interval : intervals) {
            if(res.size()==0){
                res.add(new int[]{interval[0],interval[1]});
            }else {
                int[] lastNode =res.get(res.size()-1);
                if(interval[0]<=lastNode[1]){
                    int []newNode=new int[]{lastNode[0],Math.max(interval[1],lastNode[1])};
                    res.remove(res.size()-1);
                    res.add(newNode);
                }else {
                    res.add(new int []{interval[0],interval[1]});
                }
            }
        }
        return res.toArray(new int[res.size()][]);
    }
```

### 57.插入区间

- 给你一个 **无重叠的** *，*按照区间起始端点排序的区间列表。在列表中插入一个新的区间，你需要确保列表中的区间仍然有序且不重叠（如果有必要的话，可以合并区间）
- 用56的

```java
    //按照左端点进行排序。空间更少
    public int[][] insert(int[][] intervals, int[] newInterval) {
        List<Integer[]> list=new ArrayList<>();
        for (int[] interval : intervals) {
            list.add(new Integer[]{interval[0], interval[1]});
        }
        list.add(new Integer[]{newInterval[0],newInterval[1]});
        list.sort((x,y)->x[0]-y[0]);

        List<Integer[]>res=new ArrayList<>();
        list.forEach(node -> {
            if(res.size()==0){
                res.add(node);
            }else {
                Integer[] lastNode =res.get(res.size()-1);
                if(node[0]<=lastNode[1]){
                    Integer []newNode=new Integer[]{lastNode[0],Math.max(node[1],lastNode[1])};
                    res.remove(res.size()-1);
                    res.add(newNode);
                }else {
                    res.add(node);
                }
            }
        });
        int [][]result = new int[res.size()][2];
        for(int i=0;i<res.size();i++){
            result[i][0]=res.get(i)[0];
            result[i][1]=res.get(i)[1];
        }
        return result;
    }
```

- 官方
  - 只考虑少量的特殊情况，其他的通通重叠

```java
    //按照左端点进行排序。空间更少
    public int[][] insert(int[][] intervals, int[] newInterval) {
        int left=newInterval[0];
        int right=newInterval[1];
        List<int []>list=new ArrayList<>();

        boolean isplaced = false;

        for(int [] node:intervals){
            //新节点在左
            if(node[0]>right){
                if(!isplaced){
                    list.add(new int[]{left,right});
                    isplaced=true; // 已经处理完了
                }
                list.add(node);
            }else if(node[1]<left){
                list.add(node);
            }else {//重叠
                left=Math.min(left,node[0]);
                right=Math.max(right,node[1]);
            }
        }
        if(!isplaced){
            list.add(new int[]{left,right});   // 反正又不要节点顺序
        }
        return list.toArray(new int[list.size()][]);
    }
```



## ==真其他==

### 幽谷祝祀

- 原神新圣遗物“来歆余响”触发概率
- 每一次都受前面触发的影响

```java
/**
     * 如n=3。算的是nums[3]的值
     * 下标  3      2      1    0
     *     0.36*0.528                   //在第三次触发tmp1
     *     0.56*0.472*0.488             //第二tmp1
     *     0.76*0.472*0.512*0.36        //第一tmp1
     *     0.96*0.472*0.512*0.64        //一次都没有tmp2
     * 由于p只能0.36,0.56,0.76,0.96,1。所以当前概率最多与前四次相关。
     * 与前面倒数第五次以及之前是否触发无关
     * 结果约为0.5240898176437716   每次都有点不同
     */
    public static void main(String[] args) {
        double p=0.36;//初始概率
        double []nums=new double[15];

        for(int i=0;i<15;i++){
            nums[i]=result(nums,p,i);
        }
        System.out.println(Arrays.toString(nums));

    }


    public static  double result(double []nums,double p,int n){
        if(n==0){
            nums[0]=p;//第一次是0.36
            return nums[0];
        }
        double sum=0;//总概率

        //至少触发了一次
        int i=0;
        while (i<n){
            if(p>1){
                break;
            }
            int j=n-1;
            double tmp1=1;
            while (j>=n-i){
                tmp1*=(1-nums[j]);//不触发的那些
                j--;
            }
            tmp1=tmp1*p*nums[n-i-1];//触发的那次
            sum+=tmp1;
            p+=0.2;
            i++;
        }

        //一次都没有触发
        double tmp2=1;
        if(p>1){//p累加到1说明最后一次为前面4次都没有触发。
            tmp2=1*tmp2*(1-nums[n-1])*(1-nums[n-2])*(1-nums[n-3])*(1-nums[n-4]);
        }else {
            for(double num:nums){
                tmp2*=(1-num);
            }
            tmp2*=p;
        }
        sum=sum+tmp2;
        return sum;
    }
```

### 31.下一个排列

- 整数数组表示一个数，排列是数组元素打乱顺序，下一个排列是下个比它大的数，若已经最大则返回其最小的值，必须原地修改

```java
//例如：（过程）1234753->1235743->1235347
//只能说和官方思路一致，尽量只改变后面的位
public void nextPermutation(int[] nums) {
        int len=nums.length;
        boolean isFind=false;
        int mi=nums[len-1];
        int res=len-1;

        for(int i=len-1;i>=0;i--){//从后向前方向第一个非递升数的下标res
            if(nums[i]<mi){
                res=i;
                isFind=true;
                break;
            }else {
                mi=nums[i];
            }
        }
        if(!isFind){//没找到，说明是降序，直接反过来
            for(int i=0;i<len/2;i++)
                swap(nums,i,len-1-i);
        }
        else {
            int i=res+1;
            while (i<len){//从res+1开始找第一个比nums[res]小或等于的数下标i
                if(nums[i]<=nums[res])
                    break;
                i++;
            }
            swap(nums,res,i-1);//跟i前一个数交换
            int k=0;
            for(int j=res+1;j<(len+res+1)/2;j++){//把res+1及之后 的变成升序（最小化）
                swap(nums,j,len-1-k);
                k++;
            }
        }

    }
    private void swap(int []nums,int i,int j){
        int temp=nums[i];
        nums[i]=nums[j];
        nums[j]=temp;
    }
```

### Disjoint Set

- 并查集,用一棵树来表示一个集合，检查时看根节点一致则在同一个集合
- 检查图上有没有环，新的边的2个点都在同一个集合，则存在环
- 并查集处理一维序列比较好，二维的话还是dfs吧

```java
    //是无向有环图则返回true
    public boolean loopGraph(){
        int [][]edges={
                {0,1},{1,2},{1,3},{2,5},{3,4},{2,4}
        };
        int []parents=new int[6];
        Arrays.fill(parents,-1);//初始化为-1

        for(int i=0;i<edges.length;i++){
            int []parx=findParent(edges,parents,i,0);

            int []pary=findParent(edges,parents,i,1);
            if(parx[0]==pary[0]){//同一根节点
                return true;
            }
            /*
            根据当前节点到树的根节点粗略估计树的高度
            高的作为新的根
             */
            if(parx[1]>pary[1]){
                parents[pary[0]]=parx[0];
            }else {
                parents[pary[0]]=parx[0];
            }

        }
        return false;
    }

    private int[] findParent(int [][]edges,int []parents,int i,int j){
        int point=edges[i][j];
        int pre=point;//保存当前节点信息
        int root=parents[point];
        int high=0;
        while (root!=-1){// 根节点是-1
            pre=root;
            root=parents[root];
            high++;
        }
        return new int []{pre,high};
    }
```

# ==模板==

- java

  ```java
  import java.util.*;
  public class Main{
      public static void main(String []args){
          Scanner in=new Scanner(System.in);
          
      }
  }
  ```

- 补充的java

  ```java
  long a=(long)Integer.MAX_VALUE*Integer.MAX_VALUE;//能拿到正确的long型结果
  ```

- 思路要快，写代码的速度也要快，一想到就能写出来

# ==二星（牛客竞赛网）==

## NC14318-Task   (分治+dfs)

- 题目描述                    

  𝐴𝑟𝑖𝑎接到了一份来自校方的委托，虽然没有学分但也必须完成。
   需要粉刷𝑛条木板，这些木板按照左端对齐，每条木板的高度都是1，
   第𝑖条木板的长度为𝐴𝑖。
   𝐴𝑟𝑖𝑎只有一个宽度为1的刷子，她每次可以水平或者竖直地对连续的
   位置进行粉刷，刷子不能经过没有木板的位置。
   𝐴𝑟𝑖𝑎对校方的这个安排非常不满，但为了效率，她允许重复粉刷一个
   位置，希望通过最少的次数来完成这𝑛条木板的粉刷。

- 输入描述:

```
	第一行，一个正整数𝑛。
	第二行，𝑛个正整数𝐴𝑖。
```

- 输出描述:

```
	一行，一个整数，需要进行的最小粉刷次数。         
```

- 输入

```
5
2 2 1 2 1
```

- 输出

```
3
```

- dfs不优化的话已经罗列所有情况

  ```java
  //要么全部竖着刷，要么就先行刷
  import java.util.*;
  public class Main{
      //dfs 已经罗列了所有的解
      public static void main(String []args){
          Scanner in=new Scanner(System.in);
          int n=in.nextInt();
          int []nums=new int[n];
          for(int i=0;i<n;i++){
              nums[i]=in.nextInt();
          }
          //n是全部按照行刷，一次不按照列
          System.out.println(dfs(nums,0,n-1));
      }
      
      //至少先按照行刷
      private static int dfs(int nums[],int l,int r){
          if(l>r){
              return 0;
          }
          int minV=Integer.MAX_VALUE;
          for(int i=l;i<=r;i++){
              minV=Math.min(minV,nums[i]);
          }
          int ans=minV;
          //ans只是先横着刷要的，没有考虑直接竖着刷
          if(ans>=r-l+1){
              return r-l+1;
          }
          //每行都刷minV,要刷就刷所有公共行
          int pre=l;
          for(int i=l;i<=r;i++){
              nums[i]-=minV;
              if(nums[i]==0){
                  //剩下的每段要么都竖着刷,要么先横着刷，取最小的
                  ans+=dfs(nums,pre,i-1);
                  pre=i+1;
              }
          }
          //若最后不为0
          if(nums[r]!=0){
              ans+=dfs(nums,pre,r);
          }
          return Math.min(ans,r-l+1);
      }
  }
  ```

  

## NC13222-音乐研究（暴力枚举）

- 题目描述 
  美团外卖的品牌代言人袋鼠先生最近正在进行音乐研究。他有两段音频，每段音频是一个表示音高的序列。现在袋鼠先生想要在第二段音频中找出与第一段音频最相近的部分。

  具体地说，就是在第二段音频中找到一个长度和第一段音频相等且是连续的子序列，使得它们的 difference 最小。两段等长音频的 difference 定义为：
  difference = SUM((a[i] - b[i])2 )(1 ≤ i ≤ n),其中SUM()表示求和
  其中 n 表示序列长度，a[i], b[i]分别表示两段音频的音高。现在袋鼠先生想要知道，difference的最小值是多少？数据保证第一段音频的长度小于等于第二段音频的长度。

- 输入描述:
  第一行一个整数n(1 ≤ n ≤ 1000)，表示第一段音频的长度。
  第二行n个整数表示第一段音频的音高（0 ≤ 音高 ≤ 1000）。
  第三行一个整数m(1 ≤ n ≤ m ≤ 1000)，表示第二段音频的长度。
  第四行m个整数表示第二段音频的音高（0 ≤ 音高 ≤ 1000）。

- 输出描述:
  输出difference的最小值

- 输入

  2
  1 2
  4
  3 1 2 4

- 输出

  0

- 是每一位相减后的平方，不是平方后相减

  ```java
  import java.util.*;
  public class Main{
      public static void main(String []args){
          Scanner in=new Scanner(System.in);
          int n=in.nextInt();
          int []ns=new int [n];
          for(int i=0;i<n;i++){
              ns[i]=in.nextInt(); 
          }
          int m=in.nextInt();
          int []ms=new int[m];
          for(int i=0;i<m;i++){
              ms[i]=in.nextInt();
          }
          
          long ans=0;
          for(int i=0;i<n;i++){
              ans+=(ns[i]-ms[i])*(ns[i]-ms[i]);
          }
          
          for(int i=1;i<=m-n;i++){
              long t=0;
              for(int j=0;j<n;j++){
                  t+=(ns[j]-ms[i+j])*(ns[j]-ms[i+j]);
                  if(t>ans){
                      break;
                  }
              }
              ans=Math.min(t,ans);
          }
          System.out.println(ans);
      }
  }
  ```

  

# ==三星==

## NC13134-牛牛的数列（小dp+左递增右递减+合并）

- 题目描述 
  牛牛现在有一个n个数组成的数列,牛牛现在想取一个连续的子序列,并且这个子序列还必须得满足:最多只改变一个数,就可以使得这个连续的子序列是一个严格上升的子序列,牛牛想知道这个连续子序列最长的长度是多少。

- 输入描述:
  输入包括两行,第一行包括一个整数n(1 ≤ n ≤ 10^5),即数列的长度;
  第二行n个整数a_i, 表示数列中的每个数(1 ≤ a_i ≤ 10^9),以空格分割。

- 输出描述:
  输出一个整数,表示最长的长度。

- 输入

  6 
  7 2 3 1 5 6

  1 1 2 1  2 3

  1 2 1 3  2 1

- 输出

  5

- 因为要考虑后面的情况，所以不能只能一次从左到右的dp，还要从右到左等

  ```java
  import java.util.*;
  public class Main{
      //由于不是简单的买和不买，还要考虑前后是否能够连接，所以不能用简单的dp
      public static void main(String []args){
          Scanner in=new Scanner(System.in);
          int n=in.nextInt();
          int []nums=new int [n];
          for(int i=0;i<n;i++){
              nums[i]=in.nextInt();
          }
          int ans=1;
  
          //从左往右，已i为终点的递增序列
          int l[]=new int[n];
          l[0]=1;
          for(int i=1;i<n;i++){
              if(nums[i]>nums[i-1]){
                  l[i]=l[i-1]+1;
              }else {
                  l[i]=1;
              }
          }
  
          //从右往左，已i为终点的递减序列
          int r[]=new int[n];
          r[n-1]=1;
          for(int i=n-2;i>=0;i--){
              if(nums[i]<nums[i+1]){
                  r[i]=r[i+1]+1;
              }else {
                  r[i]=1;
              }
          }
  
          //只考虑左(也就是单向)
          for(int i=1;i<n;i++){
              ans=Math.max(ans,l[i-1]+1);
          }
          //考虑左右以及合并
          for(int i=1;i<n-2;i++){
              //前后相差2才能合并
              if(nums[i+1]-nums[i-1]>=2){
                  ans=Math.max(l[i-1]+r[i+1]+1,ans);
              }
          }
          System.out.println(ans);
      }
  }
  ```

## NC13224- 送外卖（char数组的从后往前填-dfs）

- 题目描述 
  n 个小区排成一列，编号为从 0 到 n-1 。一开始，美团外卖员在第0号小区，目标为位于第 n-1 个小区的配送站。
  给定两个整数数列 a[0]~a[n-1] 和 b[0]~b[n-1] ，在每个小区 i 里你有两种选择：
  1) 选择a：向前 a[i] 个小区。
  2) 选择b：向前 b[i] 个小区。

  把每步的选择写成一个关于字符 ‘a’ 和 ‘b’ 的字符串。求到达小区n-1的方案中，字典序最小的字符串。如果做出某个选择时，你跳出了这n个小区的范围，则这个选择不合法。
  • 当没有合法的选择序列时，输出 “No solution!”。
  • 当字典序最小的字符串无限长时，输出 “Infinity!”。
  • 否则，输出这个选择字符串。

  字典序定义如下：串s和串t，如果串 s 字典序比串 t 小，则
  • 存在整数 i ≥ -1，使得∀j，0 ≤ j ≤ i，满足s[j] = t[j] 且 s[i+1] < t[i+1]。
  • 其中，空字符 < ‘a’ < ‘b’。

- 输入描述:
  输入有 3 行。

  第一行输入一个整数 n (1 ≤ n ≤ 10^5)。

  第二行输入 n 个整数，分别表示 a[i] 。

  第三行输入 n 个整数，分别表示 b[i] 。

  −n ≤ a[i], b[i] ≤ n

- 输出描述:
  输出一行字符串表示答案。

- 输入

  7
  5 -3 6 5 -5 -1 6
  -6 1 4 -2 0 -2 0

- 输出

  abbbb

- 补充：无限循环说的是能找到最后，但是过程中存在循环

- dfs

  - 能走到最后，整条路径的dfs才都返回true，否则都返回false
  - 用char数组并带位置step可以从结尾开始填写，二不用回溯复原
  - 有负数，二次访问要有三状态

  ```java
  import java.util.*;
  public class Main{
      
      static int []a;
      static int []b;
      static boolean flag=false; //是否到达终点且存在环
      static char[]chars;//不用strb的原因是char数组可以直接覆盖，而不用回溯复原
      static int n;
      static byte[]vi;  //初始为0，第一次大家都访问是1 ，第二次被a访问则是2  有三状态，不能用boolean
      public static void main(String []args){
          Scanner in=new Scanner(System.in);
          n=in.nextInt();
          a=new int[n];
          b=new int[n];
          vi=new byte[n];
          chars=new char[n+2];
          for(int i=0;i<n;i++){
              a[i]=in.nextInt();
          }
          for(int i=0;i<n;i++){
              b[i]= in.nextInt();
          }
  
  
          if(!dfs(0,0)){
              System.out.println("No solution!");
          }else {
              //这个题目意思是说存在无限循环则输出Infinity,无限循环最后也是能走到终点的
              if(flag){
                  System.out.println("Infinity!");
              }else {
                  StringBuilder ans=new StringBuilder();
                  for(int i=0;i<n+2;i++){
                      if(chars[i]!='\0'){
                          ans.append(chars[i]);
                      }
                  }
                  System.out.println(ans.toString());
              }
          }
      }
      // 返回ture说明一定是走到了最后终点
      private static boolean dfs(int pos,int step){
          if(pos<0 || pos>=n){
              return false;
          }
          if(pos==n-1){
              return true;
          }
  
          vi[pos]++;
          //已经被a访问过了，直接返回false给a
          if(vi[pos]>1){
              return false;
          }
          
          if(dfs(pos+a[pos],step+1)){ 
              chars[step]='a';
              return true;
          }
          //上面走a走不到最后才会走b
          else if(dfs(pos+b[pos],step+1)){
              chars[step]='b';
              //走b过程中发现位置曾经走a来过，说明存在无限循环的
              if(vi[pos]>1){
                  flag=true;
              }
              return true;
          }
          return false;
      }
  }
  ```

## NC13223-锦标赛（贪心）

- 题目描述 
  组委会正在为美团点评CodeM大赛的决赛设计新赛制。

  比赛有 n 个人参加（其中 n 为2的幂），每个参赛者根据资格赛和预赛、复赛的成绩，会有不同的积分。比赛采取锦标赛赛制，分轮次进行，设某一轮有 m 个人参加，那么参赛者会被分为 m/2 组，每组恰好 2 人，m/2 组的人分别厮杀。我们假定积分高的人肯定获胜，若积分一样，则随机产生获胜者。获胜者获得参加下一轮的资格，输的人被淘汰。重复这个过程，直至决出冠军。

  现在请问，参赛者小美最多可以活到第几轮（初始为第0轮）？

- 输入描述:
  第一行一个整数 n (1≤n≤ 2^20)，表示参加比赛的总人数。

  接下来 n 个数字（数字范围：-1000000…1000000），表示每个参赛者的积分。

  小美是第一个参赛者。

- 输出描述:
  小美最多参赛的轮次。


- 输入

  4
  4 1 2 3

- 输出

  2

- 贪心

  - 每次让分高的比，不够人再淘汰分低的，直到没有分低的

  ```java
  import java.util.*;
  public class Main{
      public static void main(String []args){
          Scanner in=new Scanner(System.in);
          int n=in.nextInt();
          int s=in.nextInt(); //小美分数
          int c=0;//比美分低的或等的
          int ans=0;
          for(int i=0;i<n-1;i++){
              int t=in.nextInt();
              if(t<=s){
                  c++;
              }
          }
          while(c>0){
              int a=(n-1-c)/2;//分高的相互比赛
              c-=n/2-a; //分低的淘汰
              n/=2; //总人数减少
              ans++;
          }
          System.out.println(ans);
      }
  }
  ```

  



## NC13228-倒水（全倒）

- 题目描述 
  有一个大水缸，里面水的温度为T单位，体积为C升。另有n杯水（假设每个杯子的容量是无限的），每杯水的温度为t[i]单位，体积为c[i]升。
  现在要把大水缸的水倒入n杯水中，使得n杯水的温度相同，请问这可能吗？并求出可行的最高温度，保留4位小数。
  注意：一杯温度为t1单位、体积为c1升的水与另一杯温度为t2单位、体积为c2升的水混合后，温度变为(t1*c1+t2*c2)/(c1+c2)，体积变为c1+c2。

- 输入描述:
  第一行一个整数n, 1 ≤ n ≤ 10^5
  第二行两个整数T，C,其中0 ≤ T ≤ 10^4, 0 ≤ C ≤ 10^9
  接下来n行每行两个整数t[i]，c[i]
  0 < t[i], c[i] ≤ 10^4

- 输出描述:
  如果非法，输出“Impossible”（不带引号)否则第一行输出“Possible"（不带引号），第二行输出一个保留4位小数的实数表示答案。

  样例解释：往第二杯水中倒0.5升水
  往第三杯水中到1升水
  三杯水的温度都变成了20

- 输入

  3
  10 2
  20 1
  25 1
  30 1

- 输出

  Possible
  20.0000

- 全部处理后通过结果来判断,尤其是这种很难通过过程来判断的

  ```java
  import java.util.*;
  public class Main{
      //若是大水缸的温度t介于min和max中间，则不可能倒了后都相同，因为无限接近
      //温度低的不会超过t，温度高的不会低于t
      //T介于中间无论怎么倒都不行
      //T低于min时，全部倒完能够让总温度t<=min则成功，取min
      //T大于max时，全部倒完能够让总温度t>=max则成功，取t
      //温度=热量/容量
      public static void main(String []args){
          Scanner in=new Scanner(System.in);
          int n=in.nextInt();
          double T=in.nextDouble();
          double C=in.nextDouble();
          double x=T*C;//总热量
          double y=C;  //总容量
          double min=Double.MAX_VALUE;
          double max=Double.MIN_VALUE;
          while(n>0){
              n--;
              double a=in.nextDouble();
              double b=in.nextDouble();
              x+=a*b;
              y+=b;
              min=Math.min(min,a);
              max=Math.max(max,a);
          }
          double ans=x/y;
          if(ans<=min){
              System.out.printf("Possible\n%.4f",min);
          }else if(ans>=max){
              System.out.printf("Possible\n%.4f",ans);
          }else{
              System.out.printf("Impossible");
          }
   
      }
  }
  ```


# ==链接==

- https://ac.nowcoder.com/discuss/926597

# ==模拟（牛客竞赛网）==

### NC16644 -字符串的展开

- 题目描述                    

  在初赛普及组的“阅读程序写结果”的问题中，我们曾给出一个字符串展开的例子：如果在输入的字符串中，含有类似于“d-h”或“4-8”的子串，我们就把它当作一种简写，输出时，用连续递增的字母或数字串替代其中的减号，即，将上面两个子串分别输出为“defgh”和“45678”。在本题中，我们通过增加一些参数的设置，使字符串的展开更为灵活。具体约定如下：
   （1）遇到下面的情况需要做字符串的展开：在输入的字符串中，出现了减号“-”，减号两侧同为小写字母或同为数字，且按照ASCII码的顺序，减号右边的字符严格大于左边的字符。
   （2）参数 p1p_1p1​：展开方式。p1=1p_1=1p1​=1 时，对于字母子串，填充小写字母；p1=2p_1=2p1​=2 时，对于字母子串，填充大写字母。这两种情况下数字子串的填充方式相同。p1=3p_1=3p1​=3时，不论是字母子串还是数字子串，都用与要填充的字母个数相同的星号“*”来填充。
   （3）参数 p2p_2p2​：填充字符的重复个数。p2=kp_2=kp2​=k 表示同一个字符要连续填充 kkk 个。例如，当 p2=3p_2=3p2​=3 时，子串“d-h”应扩展为“deeefffgggh”。减号两侧的字符不变。
   （4）参数 p3p_3p3​：是否改为逆序：p3=1p_3=1p3​=1 表示维持原有顺序，p3=2p_3=2p3​=2 表示采用逆序输出，注意这时仍然不包括减号两端的字符。例如当 p1=1、p2=2、p3=2p_1=1、p_2=2、p_3=2p1​=1、p2​=2、p3​=2 时，子串“d-h”应扩展为“dggffeeh”。
   （5）如果减号右边的字符恰好是左边字符的后继，只删除中间的减号，例如：“d-e”应输出为“de”，“3-4”应输出为“34”。如果减号右边的字符按照ASCII码的顺序小于或等于左边字符，输出时，要保留中间的减号，例如：“d-d”应输出为“d-d”，“3-1”应输出为“3-1”。 

- 输入描述:

```
    第 1 行为用空格隔开的 3 个正整数，依次表示参数 p1，p2，p3p_1，p_2，p_3p1，p2，p3。
    第 2 行为一行字符串，仅由数字、小写字母和减号“-”组成。行首和行末均无空格。
```

- 输出描述:

```
	输出一行，为展开后的字符串。             
```

- 输入

```
    1 2 1
    abcs-w1234-9s-4zz
```

- 输出

```
	abcsttuuvvw1234556677889s-4zz
```

- 模拟

  - 也就是挨个处理

  ```java
  import java.util.*;
  public class Main{
      public static void main(String []args){
          Scanner in=new Scanner(System.in);
          int p1=in.nextInt();
          int p2=in.nextInt();
          int p3=in.nextInt();
          String s=in.next();
          StringBuilder ans=new StringBuilder();
          for(int i=0;i<s.length();i++){
              char c=s.charAt(i);
              if(c=='-' && i>0 && i!=s.length()-1){ //i不是开头和结尾
                  char pre=s.charAt(i-1);
                  char next=s.charAt(i+1);
                  ans.append(getSubStr(pre,next,p1,p2,p3));
              }else{
                  ans.append(c);
              }
          }
          System.out.println(ans.toString());
  
      }
      private static String getSubStr(char st,char end,int p1,int p2,int p3){
          StringBuilder sub=new StringBuilder();
          if(st>=end){
              return "-";
          }else if(st+1==end){
              return "";
          }
          else{
              if(Character.isDigit(st) && Character.isDigit(end)){
                  if(p1<3){
                      for( int i=st+1;i<end;i++){
                          for(int j=0;j<p2;j++){
                              sub.append((char)i+"");
                          }
                      }
                  }else{
                      for(int i=st+1;i<end;i++){
                          for(int j=0;j<p2;j++){
                              sub.append("*");
                          }
                      }
                  }
              }else if(Character.isLowerCase(st) && Character.isLetter(end)){
                  if(p1==1){
                      for(int i=st+1;i<end;i++){
                          for(int j=0;j<p2;j++){
                              sub.append((char)i+"");
                          }
                      }
                  }else if(p1==2){
                      for(int i=st+1;i<end;i++){
                          for(int j=0;j<p2;j++){
                              String s=(char)i+"";
                              sub.append(s.toUpperCase());
                          }
                      }
                  }else{
                      for(int i=st+1;i<end;i++){
                          for(int j=0;j<p2;j++){
                              sub.append("*");
                          }
                      }
                  }
              }else{
                  return "-";
              }
          }
          if(p3==2){
              return sub.reverse().toString();
          }
          return sub.toString();
      }
  }
  ```

  

# ==枚举==

- 其实是一个个列举出来

## 枚举顺序

### NC16593-铺地毯

- 题目描述                    

  为了准备一个独特的颁奖典礼，组织者在会场的一片矩形区域（可看做是平面直角坐标系的第一象限）铺上一些矩形地毯。一共有n张地毯，编号从1到n。现在将这些地毯按照编号从小到大的顺序平行于坐标轴先后铺设，后铺的地毯覆盖在前面已经铺好的地毯之上。地毯铺设完成后，组织者想知道覆盖地面某个点的最上面的那张地毯的编号。注意：在矩形地毯边界和四个顶点上的点也算被地毯覆盖。

- 输入描述:

```
第一行，一个整数n，表示总共有n张地毯。
接下来的n行中，第i+1行表示编号i的地毯的信息，包含四个正整数a，b，g，k，每两个整数之间用一个空格隔开，分别表示铺设地毯的左下角的坐标（a，b）以及地毯在x轴和y轴方向的长度。
第n+2行包含两个正整数x和y，表示所求的地面的点的坐标（x，y）。
```

- 输出描述:

```
输出共1行，一个整数，表示所求的地毯的编号；若此处没有被地毯覆盖则输出-1。
```

​                            示例1                        

- 输入

```
3
1 0 2 3
0 2 3 3
2 1 3 3
2 2
```

- 输出

```
3
```

- 从后面开始枚举

## 前缀和和差分

### NC16649-校门外的树（合并区间

- 题目描述                    

  某校大门外长度为L的马路上有一排树，每两棵相邻的树之间的间隔都是1米。我们可以把马路看成一个数轴，马路的一端在数轴0的位置，另一端在L的位置；数轴上的每个整数点，即0，1，2，……，L，都种有一棵树。  

  由于马路上有一些区域要用来建地铁。这些区域用它们在数轴上的起始点和终止点表示。已知任一区域的起始点和终止点的坐标都是整数，区域之间可能有重合的部分。现在要把这些区域中的树（包括区域端点处的两棵树）移走。你的任务是计算将这些树都移走后，马路上还有多少棵树。  

- 输入描述:

```
第一行有两个整数：L（1 <= L <= 10000）和 M（1 <= M <= 100），L代表马路的长度，M代表区域的数目，L和M之间用一个空格隔开。接下来的M行每行包含两个不同的整数，用一个空格隔开，表示一个区域的起始点和终止点的坐标。
```

- 输出描述:

```
包括一行，这一行只包含一个整数，表示马路上剩余的树的数目。
```

​                            示例1                        

- 输入

```
500 3
150 300
100 200
470 471
```

- 输出

```
298
```

- 直接原地合并区间

  ```java
  import java.util.*;
  public class Main{
      public static void main(String []args){
          Scanner in=new Scanner(System.in);
          int l=in.nextInt();
          int m=in.nextInt();
          int [][]a=new int[m][2];
          for(int i=0;i<m;i++){
              a[i][0]=in.nextInt();
              a[i][1]=in.nextInt();
          }
          Arrays.sort(a,(x,y)->x[0]-y[0]);
          //原地合并
          int pos=1; //新增的区间的位置
          for(int i=1;i<m;i++){
              for(int j=0;j<=pos;j++){
                  //没能合并
                  if(j==pos){
                      a[pos][0]=a[i][0];
                      a[pos][1]=a[i][1];
                      pos++;
                      break;
                  }
                  //起点介于a[j]区间
                  if(a[i][0]>=a[j][0] && a[i][0]<=a[j][1]){
                      a[j][1]=Math.max(a[j][1],a[i][1]);
                      break;
                  }
              }
          }
          
          //
          int ans=l+1;
          for(int i=0;i<pos;i++){
              ans-=a[i][1]-a[i][0]+1;
          }
          System.out.println(ans);
      }
  }
  ```

  

## 尺取法

### NC18386-合法字符串（滑动窗口）

- 题目描述                    

  小N现在有一个字符串S。他把这这个字符串的所有子串都挑了出来。一个S的子串T是合法的，当且仅当T中包含了所有的小写字母。小N希望知道所有的合法的S的子串中，长度最短是多少。

- 输入描述:

```
一行一个字符串S。只包含小写字母。S的长度不超过106.
```

- 输出描述:

```
一行一个数字，代表最短长度。数据保证存在一个合法的S的子串。          
```

- 输入

```
ykjygvedtysvyymzfizzwkjamefxjnrnphqwnfhrnbhwjhqcgqnplodeestu
```

- 输出

```
49
```

- 滑动窗口

  - 不断更新

  ```java
  import java.util.*;
  public class Main{
      //左窗口不动，等待右窗口更新到左端点
      //右窗口每次加1并更新
      public static void main(String []args){
          Scanner in=new Scanner(System.in);
          String s=in.next();
          int []v=new int [26];
          //滑动窗口
          int n=0;
          int l=0;
          int r=s.length();
          // 拿到第一个滑动窗口
          for(int i=0;i<s.length();i++){
              char c=s.charAt(i);
              v[c-'a']++;
              if(v[c-'a']==1){
                  n++;
                  if(n==26){   //第一个窗口
                      r=i;
                      break;
                  }
              }
          }
          int ans=r-l+1;
          while(r<s.length()){
              //左滑出一个
              while(true){
                  int lx=s.charAt(l)-'a';
                  //停在第一个为1的位置，等待后面v[rx]++;
                  // v[lx]会一直等于1，卡在这里
                  // 只有一个。左边不能滑
                  if(v[lx]==1){
                      break;
                  }
                  v[lx]--;
                  l++;
              }
              ans=Math.min(ans,r-l+1);
              //右边滑进一个，是谁都行
              r++;
              if(r<s.length())
              v[s.charAt(r)-'a']++;
          }
          System.out.println(ans);
      }
  }
  ```

### NC207040-丢手绢（前缀和+滑动窗口）

- 题目描述                    

  “丢~丢~丢手绢，轻轻地放在小朋友的后面，大家不要告诉她，快点快点抓住她，快点快点抓住她。”

  牛客幼儿园的小朋友们围成了一个圆圈准备玩丢手绢的游戏，但是小朋友们太小了，不能围成一个均匀的圆圈，即每个小朋友的间隔可能会不一致。为了大家能够愉快的玩耍，我们需要知道离得最远的两个小朋友离得有多远（如果太远的话牛老师就要来帮忙调整队形啦！）。 

  因为是玩丢手绢，所以小朋友只能沿着圆圈外围跑，所以我们定义两个小朋友的距离为沿着圆圈顺时针走或者逆时针走的最近距离。 

- 输入描述:

```
第一行一个整数N，表示有N个小朋友玩丢手绢的游戏。
接下来的第2到第n行，第i行有一个整数，表示第i-1个小朋友顺时针到第i个小朋友的距离。
最后一行是第N个小朋友顺时针到第一个小朋友的距离。
```

- 输出描述:

```
输出一个整数，为离得最远的两个小朋友的距离。                     
```

- 输入

```
3
1
2
3
```

- 输出

```
3
```

- 333

  ```java
  import java.util.*;
  public class Main{
      //隐含条件，最大值为圆周长的一半
      //前缀和+滑动窗口
      //让滑动窗口中的值尽量趋近于一半
      //s[n+1]  多出一个s[0]=0;方便计算
      // 总和不变
      public static void main(String []args){
          Scanner in=new Scanner(System.in);
          int n=in.nextInt();
          int []s=new int [n+1];
          for(int i=1;i<=n;i++){
              s[i]=in.nextInt();
              s[i]+=s[i-1];
          }
          
          int sum=s[n];//总和
          int ans=-1;
          /*
          两点之间的距离不会超过总长度的一般
          让小的增加，两个点会分为大和小两部分，让小的增加，大的减少
          */
          int l=0;
          int r=1;
          while(l<r && r<=n){
              int a=s[r]-s[l]; //r和l中间的距离
              int b=sum-a;   //首尾相连后的距离
              ans=Math.max(ans,Math.min(a,b));
              //这里让小的递进
              if(a<b){
                  r++;
              }else{
                  l++;
              }
          }
          System.out.println(ans);
      }
  }
  ```

## 枚举+递推

### NC20241-扫雷MINE(dfs)

- 题目描述                    

  相信大家都玩过扫雷的游戏。那是在一个n*m的矩阵里面有一些雷，要你根据一些信息找出雷来。 

  万圣节到了 ，“余”人国流行起了一种简单的扫雷游戏，这个游戏规则和扫雷一样，如果某个格子没有雷，那么它里面的数字 表示和它8连通的格子里面雷的数目。 

  现在棋盘是n×2的，第一列里面某些格子是雷，而第二列没有雷，如下图：  由于第一列的雷可能有多种方案满足第二列的数的限制，你的任务即根据第二列的信息确定第一列雷有多少种摆放方案。 

- 输入描述:

```
第一行为N，第二行有N个数，依次为第二列的格子中的数。（1 ≤ N ≤ 10000）
```

- 输出描述:

```
一个数，即第一列中雷的摆放方案数。                 
```

- 输入

```
2
1 1
```

- 输出

```
2
```

- dfs   分为有雷或者没有雷

  - 到结果时就统计
  - 不符合条件就return，符合条件就处理后dfs，记得复原

  ```java
  import java.util.*;
  public class Main{
      static boolean []v; //第一列
      static int []a;   //第二列
      static int ans=0;
      static int n;
      public static void main(String []args){
          Scanner in=new Scanner(System.in);
          n=in.nextInt();
          v=new boolean[n];
          a=new int[n];
          for(int i=0;i<n;i++){
              a[i]=in.nextInt();
          }
          
          dfs(0);
          System.out.println(ans);
      }
      
      //第一行i处有雷或者没有雷
      private static void dfs(int i){
          if(i==n){
              //到最后了
              if(a[i-1]==0){
                  ans++;
              }
              return;
          }
          //无雷
          if(i==0 || i>0 && a[i-1]==0){
              dfs(i+1);
              //因为还要判断有雷，所以这里不用return
          }
          //有前一个且不满足   或者 有后一个且不满足。要处理其影响范围
          if(i>0 && a[i-1]!=1 || i<n-1 && a[i+1]==0){     
              return; 
          }
          //a[i-1] 不用管了，已经判断了
          a[i]--;
          //前后--
          if(i<n-1){
              a[i+1]--;
          }
          dfs(i+1);
          //复原
          a[i]++;
          if(i<n-1){
              a[i+1]++;
          }
      }
  }
  ```


# ==acwing==

## 2.0-1背包问题(O(n2))-只能拿一次

- 问题描述

- 每个物品只能放一个

  有 NN件物品和一个容量是 VV的背包。每件物品只能使用一次。

  第 ii 件物品的体积是 vi，价值是 wi。

  求解将哪些物品装入背包，可使这些物品的总体积不超过背包容量，且总价值最大。
  输出最大价值。

- 输入格式

  第一行两个整数，N，VN，V，用空格隔开，分别表示物品数量和背包容积。

  接下来有 N 行，每行两个整数 vi,wivi,wi，用空格隔开，分别表示第 ii 件物品的体积和价值。

- 输出格式

  输出一个整数，表示最大价值。

- 数据范围

  0<N,V≤10000<N,V≤100
  0<vi,wi≤10000<vi,wi≤1000

- 输入样例

```
4 5
1 2
2 4
3 4
4 5
```

- 输出样例：

```
8
```

- dp

  - 从后往前dp
  - 从前往后dp就是完全背包问题了

  ```java
  import java.util.*;
  public class Main{
      //对容量从0-V都dp
      public static void main(String []args){
          Scanner in=new Scanner(System.in);
          int N=in.nextInt();
          int V=in.nextInt();
          int []w=new int[N];
          int []v=new int[N];
          for(int i=0;i<N;i++){
              w[i]=in.nextInt();
              v[i]=in.nextInt();
          }
          int []dp=new int[V+1]; //因为下面j要等于V，也就是背包的最大值。
          //dp，不断对当前体积为w[i]的放入，更新dp数组的每个值
          // 首先对每个物品进行遍历
          for(int i=0;i<N;i++){
              //j要从大到小，比如第一轮的时候，前面的必须为0.
              //如果从小到大，则前面可能不为0，后面再拿到时就重复了。
              //如背包大小为5，第一体积为2，价值为4，则dp[2]=2,dp[4]=4。重复计算了
              for(int j=V;j>=0;j--){
                  if(j>=w[i]){
                      dp[j]=Math.max(dp[j],dp[j-w[i]]+v[i]);
                  }
              }
          }
          System.out.println(dp[V]);
      }
  }
  ```

## 3.完全背包问题-次数没有限制

- 每个物品的个数无限

- 注释看0-1背包问题

- dp

  ```java
  import java.util.*;
  public class Main{
      public static void main(String []args){
          Scanner in=new Scanner(System.in);
          int N=in.nextInt();
          int V=in.nextInt();
          int []w=new int[N];
          int []v=new int[N];
          for(int i=0;i<N;i++){
              w[i]=in.nextInt();
              v[i]=in.nextInt();
          }
          int []dp=new int[V+1];
          for(int i=0;i<N;i++){
              //从前往后dp
              for(int j=0;j<=V;j++){
                  if(j>=w[i]){
                      dp[j]=Math.max(dp[j],dp[j-w[i]]+v[i]);
                  }
              }
          }
          System.out.println(dp[V]);
      }
  }
  ```


## 4.多重背包问题-次数有限制

- 每个物品有自己的个数限制

- 跟0-1背包问题相似，不过增加了判断能拿多个的情况

- dp

  - O(n3)

  ```java
  import java.util.*;
  public class Main{
      public static void main(String []args){
          Scanner in=new Scanner(System.in);
          int N=in.nextInt();
          int V=in.nextInt();
          int []w=new int[N];
          int []v=new int[N];
          int []s=new int[N];
          for(int i=0;i<N;i++){
              w[i]=in.nextInt();
              v[i]=in.nextInt();
              s[i]=in.nextInt();
          }
          int []dp=new int[V+1];
          for(int i=0;i<N;i++){
              for(int j=V;j>=0;j--){
                  //从后往前拿拿一个，可以增加几个
                  //跟0-1背包问题相似，不过增加一个计数。
                  for(int k=1;k<=s[i];k++){
                      int t=k*w[i];
                      if(j>=t){
                          dp[j]=Math.max(dp[j],dp[j-t]+v[i]*k);
                      }else{
                          break;
                      }
                  }
                 
              }
          }
          System.out.println(dp[V]);
      }
  }
  ```

## 101. 最高的牛（差分数组）

有 NN 头牛站成一行，被编队为 1、2、3…N，每头牛的身高都为整数。

当且仅当两头牛中间的牛身高都比它们矮时，两头牛方可看到对方。

现在，我们只知道其中最高的牛是第 P头，它的身高是 H ，剩余牛的身高未知。

但是，我们还知道这群牛之中存在着 M 对关系，每对关系都指明了某两头牛 AA 和 BB 可以相互看见。

求每头牛的身高的最大可能值是多少。

- 输入格式

第一行输入整数 N,P,H,M，数据用空格隔开。

接下来 M 行，每行输出两个整数 A 和 B ，代表牛 A 和牛 B 可以相互看见，数据用空格隔开。

- 输出格式

一共输出 N 行数据，每行输出一个整数。

第 i行输出的整数代表第 i头牛可能的最大身高。

- 输入样例：

```
9 3 5 5
1 3
5 3
4 3
3 7
9 8
```

- 输出样例：

```
5
4
5
3
4
4
5
5
5
```

- 注意：

- 此题中给出的关系对可能存在重复。这里根本就没有用到p

- 差分数组链接：https://blog.csdn.net/qq_27678917/article/details/56682262

- 最终要的是差分数组的前缀和数组

  ```java
  //差分数组 b[0]=0,b[i]=a[i]-a[i-1]
  //记录比最高的小多少
  import java.util.*;
  public class Main{
      public static void main(String[]args){
          Scanner in=new Scanner(System.in);
          int n=in.nextInt();
          int p=in.nextInt();
          int h=in.nextInt();
          int m=in.nextInt();
  
          int[]B=new int[n+1];
          B[1]=h; // 假设所有牛身高为h
          HashSet<String>set=new HashSet<>();
          for(int i=0;i<m;i++){
              int a=in.nextInt();
              int b=in.nextInt();
              //a=Math.min(a,b);
              //b=Math.max(a,b);  这样换a可能已经变了
              if(a>b){
                  int t=a;
                  a=b;
                  b=t;
              }
              //[a+1,b-1]都减一
              if(!set.contains(a+"-"+b)){
                  B[a+1]--;
                  B[b]++;//抵消前面的--，到此处终止
                  set.add(a+"-"+b);
              }
          }
          //前缀和
          for(int i=1;i<=n;i++){
              B[i]+=B[i-1];
              System.out.println(B[i]);
          }
      }
  }
  ```

## 50. 序列化二叉树

- 实现两个函数，分别用来序列化和反序列化二叉树

- 样例

  - ```
    你可以序列化如下的二叉树
        8
       / \
      12  2
         / \
        6   4
    
    为："[8, 12, 2, null, null, 6, 4, null, null, null, null]"
    ```

    注意

    - 以上的格式是AcWing序列化二叉树的方式，你不必一定按照此格式，所以可以设计出一些新的构造方式。

- 实现

  - 序列化为
    - "1:8 2:12  3:2 6:6 7:4"

- 序列化

  - 创建一个新的类记录每个节点还有节点序号
  - 层序遍历拿到接下来的节点序号
  - 反序列化
    - HashMap存储所有创建好的节点
    - 层序遍历连接各个节点

  ```java
  /**
   * Definition for a binary tree node.
   * public class TreeNode {
   *     int val;
   *     TreeNode left;
   *     TreeNode right;
   *     TreeNode(int x) { val = x; }
   * }
   */
  class Solution {
  
      class ONode{
          TreeNode node;
          int num;
  
          public ONode(TreeNode node, int num) {
              this.node = node;
              this.num = num;
          }
  
          public TreeNode getNode() {
              return node;
          }
  
          public int getNum() {
              return num;
          }
      }
  
      // Encodes a tree to a single string.
      String serialize(TreeNode root) {
          StringBuilder ans=new StringBuilder("");
          Queue<ONode> queue=new ArrayDeque<>();
  
          queue.add(new ONode(root,1));
          while (!queue.isEmpty()){
              for(int i=0;i<queue.size();i++){
                  ONode oNode=queue.poll();
                  if(oNode.getNode()!=null){
                      ans.append(oNode.getNum()).append(":").append(oNode.getNode().val).append(" ");
                      queue.add(new ONode(oNode.getNode().left,oNode.getNum()*2));
                      queue.add(new ONode(oNode.getNode().right,oNode.getNum()*2+1));
                  }
              }
          }
          if(ans.toString().equals("")){
              return null;
          }
          return ans.substring(0,ans.length()-1);
      }
  
      // Decodes your encoded data to tree.
      TreeNode deserialize(String data) {
          try{
              String[] nodes = data.split(" ");
              // HashMap存储所有创建好的节点。
              Map<Integer,TreeNode>map=new HashMap<>();
              for(String node:nodes){
                  int num=Integer.parseInt(node.split(":")[0]);
                  int val=Integer.parseInt(node.split(":")[1]);
                  map.put(num,new TreeNode(val));
              }
              if(map.containsKey(1)){
                  Queue<Integer> queue=new ArrayDeque<>();
                  queue.add(1);
                  while (!queue.isEmpty()){
                      for(int i=0;i<queue.size();i++){
                          int num=queue.poll();
                          // 层序遍历连接各个节点
                          TreeNode node=map.get(num);
                          if(map.containsKey(num*2)){
                              node.left=map.get(num*2);
                              queue.add(num*2);
                          }
                          if(map.containsKey(num*2+1)){
                              node.right=map.get(num*2+1);
                              queue.add(num*2+1);
                          }
                      }
                  }
              }
            return map.get(1);
          }catch (Exception e){
              return null;
          }
      }
  }
  
  ```


# ==lc周赛==

- 用所学的方法解决，尽量选择时间复杂度小的
- 注意边界值
- 2594修车时间  逆向思维
- 大厂更多考察的是背后的数学逻辑关系

## 100双周赛

### 2591. 将钱分给最多的儿童

- **题目难度**Easy

给你一个整数 `money` ，表示你总共有的钱数（单位为美元）和另一个整数 `children` ，表示你要将钱分配给多少个儿童。

你需要按照如下规则分配：

- 所有的钱都必须被分配。
- 每个儿童至少获得 `1` 美元。
- 没有人获得 `4` 美元。

请你按照上述规则分配金钱，并返回 **最多** 有多少个儿童获得 **恰好** `8` 美元。如果没有任何分配方案，返回 `-1` 。

 

**示例 1：**

```
输入：money = 20, children = 3
输出：1
解释：
最多获得 8 美元的儿童数为 1 。一种分配方案为：
- 给第一个儿童分配 8 美元。
- 给第二个儿童分配 9 美元。
- 给第三个儿童分配 3 美元。
没有分配方案能让获得 8 美元的儿童数超过 1 。
```

**示例 2：**

```
输入：money = 16, children = 2
输出：2
解释：每个儿童都可以获得 8 美元。
```

 

**提示：**

- `1 <= money <= 200`
- `2 <= children <= 30`

------

- 贪心法

```java
class Solution {
    public int distMoney(int money, int children) {
        if(money<children){
            return -1;
        }
        int disMon=8;
        int ans=0;
        while (children>0){
            // 判断当前这个分发8后，下一个还能不能满足，不能则不发
            if(money-disMon<children-1 || (money-disMon==4 && children==2)  
               || children==1 && money-disMon!=0){//最后一天因为没有分配后的衡量，所以单独判断
                break;
            }else{
                ans++;
                disMon+=8;
            }
            children--;
        }
        return ans;
    }

}
```

### 2592. 最大化数组的伟大值

 显示英文描述

- **题目难度****Medium**

给你一个下标从 0 开始的整数数组 `nums` 。你需要将 `nums` 重新排列成一个新的数组 `perm` 。

定义 `nums` 的 **伟大值** 为满足 `0 <= i < nums.length` 且 `perm[i] > nums[i]` 的下标数目。

请你返回重新排列 `nums` 后的 **最大** 伟大值。

 

**示例 1：**

```
输入：nums = [1,3,5,2,1,3,1]
输出：4
解释：一个最优安排方案为 perm = [2,5,1,3,3,1,1] 。
在下标为 0, 1, 3 和 4 处，都有 perm[i] > nums[i] 。因此我们返回 4 。
```

**示例 2：**

```
输入：nums = [1,2,3,4]
输出：3
解释：最优排列为 [2,3,4,1] 。
在下标为 0, 1 和 2 处，都有 perm[i] > nums[i] 。因此我们返回 3 。
```

 

**提示：**

- `1 <= nums.length <= 105`
- `0 <= nums[i] <= 109`
- 贪心法
  - end指针记录最大位置，一一对应消除

```java
import java.util.*;
class Solution {
    public int maximizeGreatness(int[] nums) {
        Arrays.sort(nums);
        int ans=0;
        int end=nums.length-1;
        for(int i=nums.length-1;i>=1;i--){
            if(nums[end]>nums[i-1]){
                ans++;
                end--;
            }
        }
        return ans;
    }
}
```





### 2593. 标记所有元素后数组的分数

 显示英文描述

- **题目难度****Medium**

给你一个数组 `nums` ，它包含若干正整数。

一开始分数 `score = 0` ，请你按照下面算法求出最后分数：

- 从数组中选择最小且没有被标记的整数。如果有相等元素，选择下标最小的一个。
- 将选中的整数加到 `score` 中。
- 标记 **被选中元素**，如果有相邻元素，则同时标记 **与它相邻的两个元素** 。
- 重复此过程直到数组中所有元素都被标记。

请你返回执行上述算法后最后的分数。

 

**示例 1：**

```
输入：nums = [2,1,3,4,5,2]
输出：7
解释：我们按照如下步骤标记元素：
- 1 是最小未标记元素，所以标记它和相邻两个元素：[2,1,3,4,5,2] 。
- 2 是最小未标记元素，所以标记它和左边相邻元素：[2,1,3,4,5,2] 。
- 4 是仅剩唯一未标记的元素，所以我们标记它：[2,1,3,4,5,2] 。
总得分为 1 + 2 + 4 = 7 。
```

**示例 2：**

```
输入：nums = [2,3,5,1,3,2]
输出：5
解释：我们按照如下步骤标记元素：
- 1 是最小未标记元素，所以标记它和相邻两个元素：[2,3,5,1,3,2] 。
- 2 是最小未标记元素，由于有两个 2 ，我们选择最左边的一个 2 ，也就是下标为 0 处的 2 ，以及它右边相邻的元素：[2,3,5,1,3,2] 。
- 2 是仅剩唯一未标记的元素，所以我们标记它：[2,3,5,1,3,2] 。
总得分为 1 + 2 + 2 = 5 。
```

 

**提示：**

- `1 <= nums.length <= 105`
- `1 <= nums[i] <= 106`
- 对二维数组进行双排序
  - 就是多一个维度记录下标
- 贪心+普通双排序？

```java
class Solution {
    public long findScore(int[] nums) {
        int [][]arr=new int[nums.length][2];
        for(int i=0;i<nums.length;i++){
            arr[i][0]=nums[i];
            arr[i][1]=i;
        }
        Arrays.sort(arr,(x,y)->{
            if(x[0]==y[0]){
                return x[1]-y[1]; //值相等则按照下标
            }
            return x[0]-y[0];//首先按照值大小
        });
        
        boolean []visit=new boolean[nums.length];
        long score=0;
        for(int i=0;i<nums.length;i++){
            int index=arr[i][1];
            int num=arr[i][0];
            if(!visit[index]){
                score+=nums[index];
                visit[index]=true;
                if(index-1>=0){
                    visit[index-1]=true;
                }
                if(index+1<nums.length){
                    visit[index+1]=true;
                }
            }
        }
        return score;
    }
}
```



### 2594. 修车的最少时间

给你一个整数数组 `ranks` ，表示一些机械工的 **能力值** 。`ranksi` 是第 `i` 位机械工的能力值。能力值为 `r` 的机械工可以在 `r * n2` 分钟内修好 `n` 辆车。

同时给你一个整数 `cars` ，表示总共需要修理的汽车数目。

请你返回修理所有汽车 **最少** 需要多少时间。

**注意：**所有机械工可以同时修理汽车。

 

**示例 1：**

```
输入：ranks = [4,2,3,1], cars = 10
输出：16
解释：
- 第一位机械工修 2 辆车，需要 4 * 2 * 2 = 16 分钟。
- 第二位机械工修 2 辆车，需要 2 * 2 * 2 = 8 分钟。
- 第三位机械工修 2 辆车，需要 3 * 2 * 2 = 12 分钟。
- 第四位机械工修 4 辆车，需要 1 * 4 * 4 = 16 分钟。
16 分钟是修理完所有车需要的最少时间。
```

**示例 2：**

```
输入：ranks = [5,1,8], cars = 6
输出：16
解释：
- 第一位机械工修 1 辆车，需要 5 * 1 * 1 = 5 分钟。
- 第二位机械工修 4 辆车，需要 1 * 4 * 4 = 16 分钟。
- 第三位机械工修 1 辆车，需要 8 * 1 * 1 = 8 分钟。
16 分钟时修理完所有车需要的最少时间。
```

 

**提示：**

- `1 <= ranks.length <= 105`

- `1 <= ranks[i] <= 100`

- `1 <= cars <= 106`

- 第一个方法TreeSet会导致相同时间的节点被覆盖(直接错误)

  ```java
      //会出现newKey跟map中原有key相同的而覆盖的情况，所以用优先队列吧
  //     public long repairCars(int[] ranks, int cars) {
  //         TreeMap<Long,Integer> map=new TreeMap<>();
  
  //         //map的key是当前的下一个时间，按照key从小到大排序
  //         for(int i=0;i<ranks.length;i++){
  //             map.put((long)ranks[i]*1*1,ranks[i]);   //都放入1
  //         }
  //         //通过再修一辆车的时间进行排队
  
  //         for(int i=0;i<cars-1;i++){
  //             long key=map.firstEntry().getKey();
  //             long k=(long)Math.sqrt(key);    //拿到修车数
  //             int value=map.firstEntry().getValue();
  //             long newKey=(long)(Math.pow(k+1,2)*value); //计算再修一辆车的时间
              
  //             map.remove(key);
  //             map.put(newKey,value);
  //         }
  //         return map.firstEntry().getKey();
  //     }
  ```

- 第二个方法用优先队列，但是最后一组用例超时

  - 时间复杂度（至少O(n)*排序时间）

  ```java
      public long repairCars(int[] ranks, int cars) {
          //[i][0] 新的权值  [][]
         PriorityQueue<Long[]>queue=new PriorityQueue<>((x,y)->{
  //           if(x[0]==y[0]){
  //               return x[1]-y[1]; //因为比较器接口的返回值是int，这里返回long是报错的
  //           }
  //           return x[0]-y[0];
             if(x[0]==y[0]){
                 if(x[1]<y[1]){
                     return -1;
                 }else {
                     return 1;
                 }
             }
  
             if(x[0]<y[0]){
                 return -1;
             }else {
                 return 1;
             }
         });
          for(int i=0;i<ranks.length;i++){
              queue.add(new Long[]{(long)ranks[i]*1*1,(long)ranks[i]});
          }
          long ans=0;
          for(int i=0;i<cars;i++){
              long oldKey=queue.peek()[0];
              long value=queue.peek()[1];
              int count=(int)Math.sqrt(oldKey/value);
              long newKey=(long)Math.pow(count+1,2)*value;
              ans=oldKey;
              
              queue.poll();
              queue.add(new Long[]{newKey,value});
          }
  
          return ans;
      }
  ```

- 思路
  如果可以用 tt 分钟修完所有车，那么同样可以用大于 tt 分钟的时间修完所有车。

  如果无法用 tt 分钟修完所有车，那么同样无法用小于 tt 分钟的时间修完所有车。

  有单调性，可以二分答案。

  ![2594修车时间](images\2594修车时间.png)

  则说明可以在 tt 分钟修理完所有汽车，根据这个公式来二分答案。

  二分上界为 minR * cars * cars 即让能力值最低的机械工修理所有汽车。

  答疑
  问：开方直接取整的做法是否会有精度误差？

  答：代码中对整数开方，只要整数转浮点没有丢失精度（在 2^{53}-12 
  53
   −1 内），开方出来的整数部分就是正确的。具体可以参考 IEEE 754。

  时间复杂度：O(n\log(mc^2))O(nlog(mc *mc)
  空间复杂度：O(1)O(1)。仅用到若干额外变量。

- 反向思维

  - 用耗时最少的工人来取一个区间，对区间进行二分


  ```java
  class Solution {
      public long repairCars(int[] ranks, int cars) {
          int minR = ranks[0];
          for (int r : ranks) minR = Math.min(minR, r);
          long left = 0, right = (long) minR * cars * cars;
          while (left < right) { // 开区间
              long mid = (left + right) / 2, s = 0;
              for (int r : ranks)
                  s += Math.sqrt(mid / r);
              if (s >= cars) {
                  right = mid;
              }
              else {
                  left = mid+1;
              }
          }
          return left;
    }
  }
  
  
  ```


## 99双周赛

### 2578. 最小和分割

- **题目难度****Easy**

给你一个正整数 `num` ，请你将它分割成两个非负整数 `num1` 和 `num2` ，满足：

- num1和num2直接连起来，得到 num各数位的一个排列。
  - 换句话说，`num1` 和 `num2` 中所有数字出现的次数之和等于 `num` 中所有数字出现的次数。
- `num1` 和 `num2` 可以包含前导 0 。

请你返回 `num1` 和 `num2` 可以得到的和的 **最小** 值。

**注意：**

- `num` 保证没有前导 0 。
- `num1` 和 `num2` 中数位顺序可以与 `num` 中数位顺序不同。

 

**示例 1：**

```
输入：num = 4325
输出：59
解释：我们可以将 4325 分割成 num1 = 24 和 num2 = 35 ，和为 59 ，59 是最小和。
```

**示例 2：**

```
输入：num = 687
输出：75
解释：我们可以将 687 分割成 num1 = 68 和 num2 = 7 ，和为最优值 75 。
```

 

**提示：**

- `10 <= num <= 109`

- 贪心

```java
class Solution {
    //从小的开始拿
    public int splitNum(int num) {
        ArrayList<Integer>arr=new ArrayList<>();
        while(num!=0){
            arr.add(num%10);
            num/=10;
        }
        int []nums=new int[arr.size()];
        for(int i=0;i<arr.size();i++){
            nums[i]=arr.get(i);
        }
        Arrays.sort(nums);
        int num1=0;
        int num2=0;
       
        // 隔空拿，每次拿一对
        for(int i=0;i<nums.length;i+=2){
            num1=num1*10+nums[i];
            if(i+1<nums.length){
                num2=num2*10+nums[i+1];
            }
        }
            
        return num1+num2;
    }
}
```

### 2579. 统计染色格子数

 显示英文描述

- **题目难度****Medium**

有一个无穷大的二维网格图，一开始所有格子都未染色。给你一个正整数 `n` ，表示你需要执行以下步骤 `n` 分钟：

- 第一分钟，将 **任一** 格子染成蓝色。
- 之后的每一分钟，将与蓝色格子相邻的 **所有** 未染色格子染成蓝色。

下图分别是 1、2、3 分钟后的网格图。

![img](https://assets.leetcode.com/uploads/2023/01/10/example-copy-2.png)

请你返回 `n` 分钟之后 **被染色的格子** 数目。

 

**示例 1：**

```
输入：n = 1
输出：1
解释：1 分钟后，只有 1 个蓝色的格子，所以返回 1 。
```

**示例 2：**

```
输入：n = 2
输出：5
解释：2 分钟后，有 4 个在边缘的蓝色格子和 1 个在中间的蓝色格子，所以返回 5 。
```

 

**提示：**

- `1 <= n <= 105`

- 找规律，每次都加4

  ```java
  class Solution {
      public long coloredCells(int n) {
          long ans=1;
          for(int i=1;i<n;i++){
              ans+=4*i;
          }
          return ans;
      }
  }
  ```


# ==困难题==

#### 239. 滑动窗口最大值（单调队列）

难度困难2218

给你一个整数数组 `nums`，有一个大小为 `k` 的滑动窗口从数组的最左侧移动到数组的最右侧。你只可以看到在滑动窗口内的 `k` 个数字。滑动窗口每次只向右移动一位。

返回 *滑动窗口中的最大值* 。

**示例 1：**

```
输入：nums = [1,3,-1,-3,5,3,6,7], k = 3
输出：[3,3,5,5,6,7]
解释：
滑动窗口的位置                最大值
---------------               -----
[1  3  -1] -3  5  3  6  7       3
 1 [3  -1  -3] 5  3  6  7       3
 1  3 [-1  -3  5] 3  6  7       5
 1  3  -1 [-3  5  3] 6  7       5
 1  3  -1  -3 [5  3  6] 7       6
 1  3  -1  -3  5 [3  6  7]      7
```

**示例 2：**

```
输入：nums = [1], k = 1
输出：[1]
```

 

**提示：**

- `1 <= nums.length <= 105`

- `-104 <= nums[i] <= 104`

- `1 <= k <= nums.length`

- 单调队列

  - 单调栈只能尾操作，单调队列能头尾操作
  - 单调递减队列

  ```java
  class Solution {
      //单调递减队列，队列元素是值及其下标
      public int[] maxSlidingWindow(int[] nums, int k) {
          int []ans=new int [nums.length-k+1];
          Deque<Integer[]>queue=new LinkedList<>();
          //构造第一个窗口的队列
          for(int i=0;i<k;i++){
              if(queue.isEmpty()){
                  queue.add(new Integer[]{nums[i],i});
              }else{
                  while(!queue.isEmpty()){
                      if(queue.getLast()[0]<=nums[i]){
                          queue.removeLast();
                      }else{
                          break;
                      }
                  }
                  queue.add(new Integer[]{nums[i],i});
              }
          }
  
          for(int i=0;i+k<=nums.length;i++){
              ans[i]=queue.getFirst()[0];
              //最前面那个已经超出k，可以去除
              if(queue.getFirst()[1]==i){
                  queue.removeFirst();
              }
              if(i+k<nums.length){
                  while(!queue.isEmpty()){
                      if(queue.getLast()[0]<=nums[i+k]){
                          queue.removeLast();
                      }else{
                          break;
                      }
                  }
                  queue.add(new Integer[]{nums[i+k],i+k});
              }
       
      
          }
          return ans;
  
    }
  }
  ```

  

#### 220. 存在重复元素 III（映射为hash桶+滑动窗口）

难度困难684

给你一个整数数组 `nums` 和两个整数 `k` 和 `t` 。请你判断是否存在 **两个不同下标** `i` 和 `j`，使得 `abs(nums[i] - nums[j]) <= t` ，同时又满足 `abs(i - j) <= k` 。

如果存在则返回 `true`，不存在返回 `false`。

 

**示例 1：**

```
输入：nums = [1,2,3,1], k = 3, t = 0
输出：true
```

**示例 2：**

```
输入：nums = [1,0,1,1], k = 1, t = 2
输出：true
```

**示例 3：**

```
输入：nums = [1,5,9,1,5,9], k = 2, t = 3
输出：false
```

- 映射到线性Hash桶上，桶大小为t+1，第一和最后一个刚好重复

  - 用桶进行快速相邻位置有没有元素

- 桶用来判断有没有重复

  - 滑动窗口肯定是限制区间

  ```java
  class Solution {
      // O(n)
      //两个int相减可能越界，所以用long
      //id 就是桶的编号
      //相同的桶一定存在，相邻的可能存在，也可能不存在，要再根据值判断
      public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {
          HashMap<Long,Long>map=new HashMap<>();
          long w=(long)t+1;
          for(int i=0;i<nums.length;i++){
              long id=getId((long)nums[i],w);  
              if(map.containsKey(id)){
                  return true;
              }
              // 相交区域
              if(map.containsKey(id+1) && Math.abs(map.get(id+1)-nums[i])<=t){
                  return true;
              }
              if(map.containsKey(id-1) && Math.abs(map.get(id-1)-nums[i])<=t){
                  return true;
              }
              map.put(id,(long)nums[i]);
              //滑动窗口移动
              if(i-k>=0){
                  //确保满足k
                  map.remove(getId((long)nums[i-k],w));
              }
          }
          return false;
      }
  
      //映射 如t=3时 则w=4
      // 每个桶一个编号
      //-7 -6 -5 -4 -3 -2 -1 0 1 2 3 4 5 6 7 8
      //-2 -2 -2 -1 -1 -1 -1 0 0 0 0 1 1 1 1 2
      private long getId(long x,long w){
          if(x>=0){
              return x/w;
        }
          return (x+1)/w-1;
      }
  }
  ```

  

#### 233. 数字 1 的个数

难度困难504

给定一个整数 `n`，计算所有小于等于 `n` 的非负整数中数字 `1` 出现的个数。

- 数位运算统计

  ```java
  class Solution {
      //0-9  1
      //0-99 1*10+10=20
      //0-999 20*10+100=300
      //0-9999 300*10+1000=4000
      // 不是上面这个规律
      // 数位运算，统计每一个位上1出现的次数
      // 如百位上 1234567/100=1234  会有1234个0-999 循环 每个循环中有100-199共100个1
      // 余数的话，大于100则是100个，小于100则0个，100-199之间则num-100+1个
      public int countDigitOne(int n) {
          long mul=1;
          int ans=0;
          while(n/mul!=0){
              ans+=(n/(mul*10))*mul+Math.min(Math.max(n%(mul*10)-mul+1,0),mul);
              mul*=10;
          }
          return ans;
      }
  }
  ```

  

#### 面试题 17.06. 2出现的次数



难度困难70

编写一个方法，计算从 0 到 n (含 n) 中数字 2 出现的次数。

- 和上一题 一模一样

  ```java
  class Solution {
      public int numberOf2sInRange(int n) {
          long mul=1;
          int ans=0;
          while(n/mul>0){
              ans+=(n/(mul*10))*mul+Math.min(Math.max(n%(mul*10)-mul*2+1,0),mul);
              mul*=10;
          }
          return ans;
      }
  }
  ```

  

#### 面试题 17.19. 消失的两个数字

难度困难217

给定一个数组，包含从 1 到 N 所有的整数，但其中缺了两个数字。你能在 O(N) 时间内只用 O(1) 的空间找到它们吗？

以任意顺序返回这两个数字均可。

**示例 1:**

```
输入: [1]
输出: [2,3]
```

**示例 2:**

```
输入: [2,3]
输出: [1,4]
```

- 下标一一对应

  - 要空间复杂度小，这里则只能对数组进行原地修改

  ```java
  class Solution {
      public int[] missingTwo(int[] nums) {
          HashSet<Integer>set=new HashSet<>(2);  //若是有n+1,n+2则记录一下
          for(int i=0;i<nums.length;i++){
              //一直找，不这样的话会提前把后面的元素换到前面，遍历到后面时则无法拿到
              //找不到则一定有超出nums.length的
              while(nums[i]!=i+1){
                  if(nums[i]>nums.length){
                      set.add(nums[i]);
                      break;
                  }
                  int t=nums[i];
                  nums[i]=nums[t-1];
                  nums[t-1]=t;
              }
          }
          int []ans=new int[2];
          int x=0;
          for(int i=0;i<nums.length;i++){
              if(nums[i]!=i+1){
                  ans[x++]=i+1;
              }
          }
          if(x!=2 && !set.contains(nums.length+1)){
              ans[x++]=nums.length+1;
          }
          if(x!=2 && !set.contains(nums.length+2)){
              ans[x++]=nums.length+2;
          }
          return ans;
      }
  }
  ```


# ==频率题==

## 343. 整数拆分（dp）

难度中等1167

给定一个正整数 `n` ，将其拆分为 `k` 个 **正整数** 的和（ `k >= 2` ），并使这些整数的乘积最大化。

返回 *你可以获得的最大乘积* 。

 

**示例 1:**

```
输入: n = 2
输出: 1
解释: 2 = 1 + 1, 1 × 1 = 1。
```

**示例 2:**

```
输入: n = 10
输出: 36
解释: 10 = 3 + 3 + 4, 3 × 3 × 4 = 36。
```

- 直接写

  - 对整个n进行dp
  
  ```java
  class Solution {
      public int integerBreak(int n) {
          int []dp=new int [n+1];
          dp[2]=1;
          dp[1]=1;
          for(int i=3;i<=n;i++){
              for(int j=1;j<=(i+1)/2;j++){
                  dp[i]=Math.max(dp[i],Math.max(j,dp[j])*Math.max(dp[i-j],i-j));
              }
          }
          return dp[n];
      }
  }
  ```

## 347. 前 K 个高频元素

难度中等1539

给你一个整数数组 `nums` 和一个整数 `k` ，请你返回其中出现频率前 `k` 高的元素。你可以按 **任意顺序** 返回答案。

 

**示例 1:**

```
输入: nums = [1,1,1,2,2,3], k = 2
输出: [1,2]
```

**示例 2:**

```
输入: nums = [1], k = 1
输出: [1]
```

- 直接处理

  - 统计个数+排序

  ```java
  import java.util.*;
  import java.util.concurrent.atomic.AtomicInteger;
  class Solution {
      public int[] topKFrequent(int[] nums, int k) {
          int []ans=new int [k];
  
          HashMap<Integer,Integer>map=new HashMap<>();
          for(int e:nums){
              map.put(e,map.getOrDefault(e,0)+1);
          }
          int [][]arr=new int[map.size()][2];
          AtomicInteger i=new AtomicInteger(0);
          map.forEach((key,val)->{
              arr[i.get()][0]=key;
              arr[i.get()][1]=val;
              i.incrementAndGet();
          });
          Arrays.sort(arr,(x,y)->y[1]-x[1]);
          for(int j=0;j<k;j++){
              ans[j]=arr[j][0];
          }
          return ans;
      }
  }
  ```

  

## 剑指 Offer 42. 连续子数组的最大和（dp）

难度简单692

输入一个整型数组，数组中的一个或连续多个整数组成一个子数组。求所有子数组的和的最大值。

要求时间复杂度为O(n)。

 

**示例1:**

```
输入: nums = [-2,1,-3,4,-1,2,1,-5,4]
输出: 6
解释: 连续子数组 [4,-1,2,1] 的和最大，为 6。
```

- 不错不错

  ```java
  class Solution {
      public int maxSubArray(int[] nums) {
          int ans=nums[0];
          int pre=0;
          for(int e:nums){
              //要么重新开始，要么再跟前面合并
              pre=Math.max(pre+e,e);
              ans=Math.max(ans,pre);
          }
          return ans;
      }
  }
  ```

## 206. 反转链表（双指针）

难度简单3115

给你单链表的头节点 `head` ，请你反转链表，并返回反转后的链表。

 

**示例 1：**

![img](https://assets.leetcode.com/uploads/2021/02/19/rev1ex1.jpg)

```
输入：head = [1,2,3,4,5]
输出：[5,4,3,2,1]
```

- 直接双指针

  ```java
  class Solution {
      public ListNode reverseList(ListNode head) {
          if(head==null){
              return head;
          }
          ListNode pre=head;
          ListNode tail=head.next;
          pre.next=null;
          while(tail!=null){
              ListNode t=tail.next;
              tail.next=pre;
              pre=tail;
              tail=t;
          }
          return pre;
      }
  }
  ```

## 面试题 01.01. 判定字符是否唯一(位运算)

难度简单285

实现一个算法，确定一个字符串 `s` 的所有字符是否全都不同。

**示例 1：**

```
输入: s = "leetcode"
输出: false 
```

**示例 2：**

```
输入: s = "abc"
输出: true
```

- 都是小写

  - 只出现一次的话，可以用位图，比int[26]好
  
  ```java
  class Solution {
      public boolean isUnique(String astr) {
          int num=0;
          for(int i=0;i<astr.length();i++){
              int x=astr.charAt(i)-'a';
              int y=(1<<x);
              if((num & y)!=0){
                  return false;
              }
              num|=y;
          }
          return true;
      }
  }
  ```


## 349. 两个数组的交集

给定两个数组 `nums1` 和 `nums2` ，返回 *它们的交集* 。输出结果中的每个元素一定是 **唯一** 的。我们可以 **不考虑输出结果的顺序** 。

 

**示例 1：**

```
输入：nums1 = [1,2,2,1], nums2 = [2,2]
输出：[2]
```

**示例 2：**

```
输入：nums1 = [4,9,5], nums2 = [9,4,9,8,4]
输出：[9,4]
解释：[4,9] 也是可通过的
```

- 因为要去重

- HashSet

  ```java
  import java.util.HashSet;
  import java.util.concurrent.atomic.AtomicInteger;
  
  class Solution {
      public int[] intersection(int[] nums1, int[] nums2) {
          HashSet<Integer> set=new HashSet<>();
          for(int e:nums1){
              set.add(e);
          }
          HashSet<Integer>ans=new HashSet<>();
          for(int e:nums2){
              if(set.contains(e)){
                  ans.add(e);
              }
          }
          int []arr=new int [ans.size()];
          AtomicInteger x=new AtomicInteger(0);
          ans.forEach((v)->{
              arr[x.getAndIncrement()]=v;
          });
          return arr;
      }
  }
  ```

## 392. 判断子序列

难度简单848

给定字符串 **s** 和 **t** ，判断 **s** 是否为 **t** 的子序列。

字符串的一个子序列是原始字符串删除一些（也可以不删除）字符而不改变剩余字符相对位置形成的新字符串。（例如，`"ace"`是`"abcde"`的一个子序列，而`"aec"`不是）。

**进阶：**

如果有大量输入的 S，称作 S1, S2, ... , Sk 其中 k >= 10亿，你需要依次检查它们是否为 T 的子序列。在这种情况下，你会怎样改变代码？

**示例 1：**

```
输入：s = "abc", t = "ahbgdc"
输出：true
```

**示例 2：**

```
输入：s = "axc", t = "ahbgdc"
输出：false
```

- 进阶不会写 

- 动态规划?

  - 令 f[i][j]f[i][j]f[i][j] 表示字符串 ttt 中从位置 iii 开始往后字符 jjj 第一次出现的位置。在进行状态转移时，如果 ttt 中位置 iii 的字符就是 jjj，那么 f[i][j]=if[i][j]=if[i][j]=i，否则 jjj 出现在位置 i+1i+1i+1 开始往后，即 f[i][j]=f[i+1][j]f[i][j]=f[i+1][j]f[i][j]=f[i+1][j]，因此我们要倒过来进行动态规划，从后往前枚举 iii。

- 双指针

  ```java
  class Solution {
      public boolean isSubsequence(String s, String t) {
          if(s.length()==0){
              return true;
          }
          int x=0;
          for(int i=0;i<t.length();i++){
              if(t.charAt(i)==s.charAt(x)){
                  x++;
                  if(x==s.length()){
                      return true;
                  }
              }
          }
          return false;
      }
  }
  ```

## 剑指 Offer 62. 圆圈中最后剩下的数字(约瑟夫环)

难度简单778

0,1,···,n-1这n个数字排成一个圆圈，从数字0开始，每次从这个圆圈里删除第m个数字（删除后从下一个数字开始计数）。求出这个圆圈里剩下的最后一个数字。

例如，0、1、2、3、4这5个数字组成一个圆圈，从数字0开始每次删除第3个数字，则删除的前4个数字依次是2、0、4、1，因此最后剩下的数字是3。

 

**示例 1：**

```
输入: n = 5, m = 3
输出: 3
```

**示例 2：**

```
输入: n = 10, m = 17
输出: 2
```

- 模拟

  ```java
  class Solution {
      public int lastRemaining(int n, int m) {
          ArrayList<Integer>list=new ArrayList<>();
          for(int i=0;i<n;i++){
              list.add(i);
          }
          int x=m%n-1;//下标 从0开始
          while(list.size()!=1){
              
              list.remove(x);
              x+=m-1;//有一个填到当前位置了
              x%=list.size();
          }
          return list.get(0);
      }
  }
  ```

- 递归

  - 有公式

  ```java
  class Solution {
      public int lastRemaining(int n, int k) {
          if(n==1){
              return 0;
          }
          int x=lastRemaining(n-1,k);
          return (x+k)%n;
      }
  }
  ```

## 322. 零钱兑换(dp)

难度中等2403

给你一个整数数组 `coins` ，表示不同面额的硬币；以及一个整数 `amount` ，表示总金额。

计算并返回可以凑成总金额所需的 **最少的硬币个数** 。如果没有任何一种硬币组合能组成总金额，返回 `-1` 。

你可以认为每种硬币的数量是无限的。

 

**示例 1：**

```
输入：coins = [1, 2, 5], amount = 11
输出：3 
解释：11 = 5 + 5 + 1
```

**示例 2：**

```
输入：coins = [2], amount = 3
输出：-1
```

**示例 3：**

```
输入：coins = [1], amount = 0
输出：0
```

- 直接算

  - Arrays.fill(dp,1)

- 如果数量有限制，则每一步再记录一个状态 ，可以考虑状态压缩

  ```java
  class Solution {
     
      public int coinChange(int[] coins, int amount) {
          int[]dp=new int[amount+1];
          Arrays.fill(dp,amount+1);
          dp[0]=0;
  
          for(int i=1;i<=amount;i++){
              for(int j=0;j<coins.length;j++){
                  int x=i-coins[j];
                  if(x>=0){
                      dp[i]=Math.min(dp[i],dp[x]+1);
                  }
              }
          }
          return dp[amount]==amount+1?-1:dp[amount];
      }
  
  
  }
  ```

# ==未整理==

## 剑指 Offer 14- I. 剪绳子

给你一根长度为 `n` 的绳子，请把绳子剪成整数长度的 `m` 段（m、n都是整数，n>1并且m>1），每段绳子的长度记为 `k[0],k[1]...k[m-1]` 。请问 `k[0]*k[1]*...*k[m-1]` 可能的最大乘积是多少？例如，当绳子的长度是8时，我们把它剪成长度分别为2、3、3的三段，此时得到的最大乘积是18。

- dp

  - 双重for循环

  ```java
  class Solution {
      public int cuttingRope(int n) {
          int []dp=new int[n+1];
          dp[1]=1;
  		// 长度从i开始
          for(int i=2;i<=n;i++){
              int temp=i-1;
              // j的范围是0-i，每次找一个可以剪短的点。j是必须剪短，前面可用i-j或者dp[i-j]
              for(int j=1;j<i;j++){
                  temp=Math.max(temp,Math.max(j*(i-j),j*dp[i-j]));
              }   
              dp[i]=temp;
          }
          return dp[n];
      }
  }
  ```




## 剑指 Offer II 112. 最长递增路径(666)



- 给定一个 `m x n` 整数矩阵 `matrix` ，找出其中 **最长递增路径** 的长度。

  对于每个单元格，你可以往上，下，左，右四个方向移动。 **不能** 在 **对角线** 方向上移动或移动到 **边界外**（即不允许环绕）

- 记忆化搜索

  - 其实是以每个点为终点走一下搜索
  - 不是0就是访问过
  - 走到起点，走不通时返回1，逐步累加
  - 整个dp数组通用

  ```java
  class Solution {
      //记忆化是dp的思想吗，搜索是dfs的思想
      //用到dfs思想有可能是反着填写结果，这个要注意
      private int [][]dirs=new int[][]{{0,-1},{0,1},{-1,0},{1,0}};
      public int longestIncreasingPath(int[][] matrix) {  
          int[][]dp=new int[matrix.length][matrix[0].length];
          int ans=1;
          for(int i=0;i<matrix.length;i++){
              for(int j=0;j<matrix[0].length;j++){
                  ans=Math.max(ans,dfs(matrix,dp,i,j));
              }
          }
          return ans;
      }
      private int dfs(int [][]matrix,int [][]dp,int x,int y){
          if(dp[x][y]!=0){
              return dp[x][y];
          }
          dp[x][y]=1;
          for(int []dir:dirs){
              int newX=x+dir[0];
              int newY=y+dir[1];
              if(newX>=0 && newX<matrix.length && newY>=0 && newY<matrix[0].length 
                &&  matrix[newX][newY]>matrix[x][y]){
                  dp[x][y]=Math.max(dp[x][y],dfs(matrix,dp,newX,newY)+1);
              }
        }
          return dp[x][y];
      }
  }
  ```

  

## 剑指 Offer 04. 二维数组中的查找

难度中等924

在一个 n * m 的二维数组中，每一行都按照从左到右 **非递减** 的顺序排序，每一列都按照从上到下 **非递减** 的顺序排序。请完成一个高效的函数，输入这样的一个二维数组和一个整数，判断数组中是否含有该整数。

 

**示例:**

现有矩阵 matrix 如下：

```
[
  [1,   4,  7, 11, 15],
  [2,   5,  8, 12, 19],
  [3,   6,  9, 16, 22],
  [10, 13, 14, 17, 24],
  [18, 21, 23, 26, 30]
]
```

给定 target = `5`，返回 `true`。

给定 target = `20`，返回 `false`

- 二分查找

  - nlogn
  - 不好处理，则对每一行就行二分查找

  ```java
  class Solution {
  
      public boolean findNumberIn2DArray(int[][] matrix, int target) {
  
          for(int nums[]:matrix){
              int lx=0;
              int rx=nums.length-1;
              while(lx<=rx){
                  int mid=lx+(rx-lx)/2;
                  if(nums[mid]==target){
                      return true;
                  }else if(nums[mid]<target){
                      lx=mid+1;
                  }else{
                      rx=mid-1;
                  }
              }
          }
          return false;
      }
  }
  ```

- Z形查找

  - n+m
  - 从右上角出发，若小于，则x++，大于则y--

  ```java
  class Solution {
      public boolean findNumberIn2DArray(int[][] matrix, int target) {
          if(matrix.length==0 || matrix[0].length==0){
              return false;
          }
          int x=0;
          int y=matrix[0].length-1;
          while(x<matrix.length && y>=0){
              if(matrix[x][y]==target){
                  return true;
              }else if(matrix[x][y]<target){
                  x++;
              }else{
                  y--;
              }
          }
          return false;
      }
  }
  ```

  

## 剑指 Offer 07. 重建二叉树

难度中等1036

输入某二叉树的前序遍历和中序遍历的结果，请构建该二叉树并返回其根节点。

假设输入的前序遍历和中序遍历的结果中都不含重复的数字。

 

**示例 1:**

![img](https://assets.leetcode.com/uploads/2021/02/19/tree.jpg)

```
Input: preorder = [3,9,20,15,7], inorder = [9,3,15,20,7]
Output: [3,9,20,null,null,15,7]
```

**示例 2:**

```
Input: preorder = [-1], inorder = [-1]
Output: [-1]
```

- 3

  ```java
  class Solution {
      HashMap<Integer,Integer>map=new HashMap<>();
      int[]pre;
      int []in;
      //根据前序找新的根节点，根据根节点在中序中的位置找左右子树的位置。然后分割数组，并闯入新的root，l，r。
      // 前序关系再增加一个map表示，原本的数组肯定也要。
      public TreeNode buildTree(int[] preorder, int[] inorder) {
         
          for(int i=0;i<inorder.length;i++){
              map.put(inorder[i],i);
          }
          pre=preorder;
          in=inorder;
          return build(0,0,in.length-1);
      }
  
      // r 根节点在pre中的下标，lx，ly根据r在in划分的左子树
      private TreeNode build(int root,int l,int r){
          if(l>r){
              return null;
          }
          TreeNode rootNode=new TreeNode(pre[root]);
          int i=map.get(pre[root]); //当前root在in中的位置
          //因为i是中序中的位置，所以不能用来找新的root
          rootNode.left=build(root+1,l,i-1); //左子树的根节点是前序遍历root加一
          rootNode.right=build(root+i-l+1,i+1,r);//右子树的根节点是前序遍历中，root+左子树的数量
          return rootNode;
      }
  }
  ```


## 131. 分割回文串(dp+dfs)

给你一个字符串 `s`，请你将 `s` 分割成一些子串，使每个子串都是 **回文串** 。返回 `s` 所有可能的分割方案。

**回文串** 是正着读和反着读都一样的字符串。

**示例 1：**

```
输入：s = "aab"
输出：[["a","a","b"],["aa","b"]]
```

**示例 2：**

```
输入：s = "a"
输出：[["a"]]
```

- 稍微注意下初始化问题

  - 先记录哪两点可以组成回文字符串
- dfs只是找一个结果集
  
  ```java
  class Solution {
      int n;
      boolean [][]dp;
      List<List<String>> ans=new ArrayList<>();
      List<String>list=new ArrayList<>();
      public List<List<String>> partition(String s) {
          n=s.length();
          dp=new boolean[n][n];
          //dp[i+1][j-1]可能还没初始化，所以先把能求的求出来
          //单个
          for(int i=0;i<n;i++){
              dp[i][i]=true;
          }
          //相邻
          for(int i=0;i<n-1;i++){
              dp[i][i+1]=s.charAt(i)==s.charAt(i+1);
          }
  
          //i还是要从后面算起，j从前面算起，防止dp[i+1][j-1]没有初始化
          for(int i=n-3;i>=0;i--){
              for(int j=i+2;j<n;j++){
                  dp[i][j]=s.charAt(i)==s.charAt(j)&&dp[i+1][j-1];
              }
          }
          dfs(0,s);
          return ans;
      }
  	//正常dfs
      private void dfs(int x,String s){
          if(x==n){
              ans.add(new ArrayList<>(list));
          }
          for(int i=x;i<n;i++){
              if(dp[x][i]){
                  list.add(s.substring(x,i+1));
                  dfs(i+1,s);
                  list.remove(list.size()-1);
              }
          }
      }
  }
  ```
```
  
  

## 298. 二叉树最长连续序列（dfs）

难度中等111

给你一棵指定的二叉树的根节点 `root` ，请你计算其中 **最长连续序列路径** 的长度。

**最长连续序列路径** 是依次递增 1 的路径。该路径，可以是从某个初始节点到树中任意节点，通过「父 - 子」关系连接而产生的任意路径。且必须从父节点到子节点，反过来是不可以的。

**示例 1：**

![img](https://assets.leetcode.com/uploads/2021/03/14/consec1-1-tree.jpg)

```
输入：root = [1,null,3,2,4,null,null,null,5]
输出：3
解释：当中，最长连续序列是 3-4-5 ，所以返回结果为 3 。
```

**示例 2：**

![img](https://assets.leetcode.com/uploads/2021/03/14/consec1-2-tree.jpg)

```
输入：root = [2,null,3,2,null,1]
输出：2
解释：当中，最长连续序列是 2-3 。注意，不是 3-2-1，所以返回 2 。
```

- 直接算

  - 结束时取最大值
  - 从上往下递增

  ```java
  class Solution {
      int ans=1;
      public int longestConsecutive(TreeNode root) {
          dfs(root,root.left,1);
          dfs(root,root.right,1);
          return ans;
      }
  
      private void dfs(TreeNode pre,TreeNode node,int len){
          if(node==null){
              ans=Math.max(ans,len);
              return;
          }
          if(node.val==pre.val+1){
              dfs(node,node.left,len+1);
              dfs(node,node.right,len+1);
          }else{
              // 重新找
              ans=Math.max(ans,len);
              dfs(node,node.left,1);
              dfs(node,node.right,1);
          }
      }
  }
```

  

## 464. 我能赢吗(状压+记忆化dfs)

在 "100 game" 这个游戏中，两名玩家轮流选择从 `1` 到 `10` 的任意整数，累计整数和，先使得累计整数和 **达到或超过** 100 的玩家，即为胜者。

如果我们将游戏规则改为 “玩家 **不能** 重复使用整数” 呢？

例如，两个玩家可以轮流从公共整数池中抽取从 1 到 15 的整数（不放回），直到累计整数和 >= 100。

给定两个整数 `maxChoosableInteger` （整数池中可选择的最大数）和 `desiredTotal`（累计和），若先出手的玩家能稳赢则返回 `true` ，否则返回 `false` 。假设两位玩家游戏时都表现 **最佳** 。

 

**示例 1：**

```
输入：maxChoosableInteger = 10, desiredTotal = 11
输出：false
解释：
无论第一个玩家选择哪个整数，他都会失败。
第一个玩家可以选择从 1 到 10 的整数。
如果第一个玩家选择 1，那么第二个玩家只能选择从 2 到 10 的整数。
第二个玩家可以通过选择整数 10（那么累积和为 11 >= desiredTotal），从而取得胜利.
同样地，第一个玩家选择任意其他整数，第二个玩家都会赢。
```

**示例 2:**

```
输入：maxChoosableInteger = 10, desiredTotal = 0
输出：true
```

**示例 3:**

```
输入：maxChoosableInteger = 10, desiredTotal = 1
输出：true
```

- 就是这样

  - 只有一个变化状态，比较好考虑状压
  - 再配合记忆化搜索

- 记忆化可以用map，数组等都可以

  ```java
  class Solution {
      //状态压缩+记忆化搜索
      //保存计算好的状态,以及该状态的结果
      HashMap<Integer,Boolean>map=new HashMap<>();
      int n;
      int s;
      public boolean canIWin(int maxChoosableInteger, int desiredTotal) {
          n=maxChoosableInteger;
          s=desiredTotal;
          //累加不到，谁都赢不了
          if((1+n)*n/2<s){
              return false;
          }
          return dfs(0,0);
      }
      private boolean dfs(int state,int sum){
          if(!map.containsKey(state)){
              boolean res=false; // 默认是false，最终都没有达到目标那就是不赢
              for(int i=0;i<n;i++){
                  //当前状态没有计算
                  if(((state>>i)&1)==0){  //位运算优先级很低，要多加一个()
                      if(i+1+sum>=s){
                          // 当前角色选一个数就能应赢
                          res=true;
                          break;
                      }
                      // 当前角色选了这个数，还不能赢，但是下一个角色赢不了
                      if(!dfs(state | (1<<i),sum+i+1)){
                          res=true;
                          break;
                      }
                  }
  
              }
              map.put(state,res);
          }
          return map.get(state);// 返回map中的值，不然可能res默认值是false
    }
  }
  ```

  

## 526. 优美的排列（dfs）

难度中等345

假设有从 1 到 n 的 n 个整数。用这些整数构造一个数组 `perm`（**下标从 1 开始**），只要满足下述条件 **之一** ，该数组就是一个 **优美的排列** ：

- `perm[i]` 能够被 `i` 整除
- `i` 能够被 `perm[i]` 整除

给你一个整数 `n` ，返回可以构造的 **优美排列** 的 **数量** 。

 

**示例 1：**

```
输入：n = 2
输出：2
解释：
第 1 个优美的排列是 [1,2]：
    - perm[1] = 1 能被 i = 1 整除
    - perm[2] = 2 能被 i = 2 整除
第 2 个优美的排列是 [2,1]:
    - perm[1] = 2 能被 i = 1 整除
    - i = 2 能被 perm[2] = 1 整除
```

**示例 2：**

```
输入：n = 1
输出：1
```

- dfs

  ```java
  class Solution {
      int n;
      boolean v[];
      int ans=0;
      //两个变化的量，不好用状态压缩
      public int countArrangement(int n) {
          this.n=n;
          v=new boolean[n];
          dfs(1);
          return ans;
      }
  
      private void dfs(int x){
          if(x==n+1){
              ans++;
              return;
          }   
          // 里面还有个for，很正常。不断拿第j个数去试
          for(int j=0;j<n;j++){
              if(!v[j]&& ((j+1)%x==0 || x%(j+1)==0)){
                  v[j]=true;
                  dfs(x+1);
                  v[j]=false;
              }
          }
      }
  }
  ```

## 718. 最长重复子数组（dp）

难度中等904

给两个整数数组 `nums1` 和 `nums2` ，返回 *两个数组中 **公共的** 、长度最长的子数组的长度* 。

 

**示例 1：**

```
输入：nums1 = [1,2,3,2,1], nums2 = [3,2,1,4,7]
输出：3
解释：长度最长的公共子数组是 [3,2,1] 。
```

**示例 2：**

```
输入：nums1 = [0,0,0,0,0], nums2 = [0,0,0,0,0]
输出：5
```

- dp

  ```java
  class Solution {
      public int findLength(int[] nums1, int[] nums2) {
          int m=nums1.length;
          int n=nums2.length;
          int[][]dp=new int[m+1][n+1];
          int ans=0;
          for(int i=0;i<m;i++){
              for(int j=0;j<n;j++){
                  dp[i+1][j+1]=nums1[i]==nums2[j]?dp[i][j]+1:0;
                  ans=Math.max(ans,dp[i+1][j+1]);
              }
          }
          return ans;
      }
  }
  ```

- 滑动窗口

## 1062. 最长重复子串(O（n2)的后缀数组）

给定字符串 `S`，找出最长重复子串的长度。如果不存在重复子串就返回 `0`。

**示例 1：**

```
输入："abcd"
输出：0
解释：没有重复子串。
```

**示例 2：**

```
输入："abbaba"
输出：2
解释：最长的重复子串为 "ab" 和 "ba"，每个出现 2 次。
```

**示例 3：**

```
输入："aabcaabdaab"
输出：3
解释：最长的重复子串为 "aab"，出现 3 次。
```

**示例 4：**

```
输入："aaaaa"
输出：4
解释：最长的重复子串为 "aaaa"，出现 2 次。
```

 

**提示：**

1. 字符串 `S` 仅包含从 `'a'` 到 `'z'` 的小写英文字母。
2. `1 <= S.length <= 1500`

- 不能够和上一题一样用dp，因为可能出现自己跟自己比较的情况，这不算重复

- 这道题的s长度比较小，可以用最简单版的后缀数组比较

  - 若是1044的长度，则要最优版的(nlogn)

- 简单后缀数组

  ```java
  class Solution {
      public int longestRepeatingSubstring(String s) {
          int n=s.length();
          int ans=0;
          String []strs=new String[n];
          for(int i=n-1;i>=0;i--){
              strs[i]=s.substring(i);
          }
          Arrays.sort(strs);
          for(int i=0;i<n-1;i++){
              int c=0;
              int len=Math.min(strs[i].length(),strs[i+1].length());
              for(int j=0;j<len;j++){
                  if(strs[i].charAt(j)==strs[i+1].charAt(j)){
                      c++;
                  }else{
                      break;
                  }
              }
              ans=Math.max(c,ans);
          }
          return ans;
      }
  }
  ```

  
  

## 208. 实现 Trie (前缀树 或者 字典树)

**Trie（发音类似 "try"）或者说 **前缀树是一种树形数据结构，用于高效地存储和检索字符串数据集中的键。这一数据结构有相当多的应用情景，例如自动补完和拼写检查。

请你实现 Trie 类：

- `Trie()` 初始化前缀树对象。
- `void insert(String word)` 向前缀树中插入字符串 `word` 。
- `boolean search(String word)` 如果字符串 `word` 在前缀树中，返回 `true`（即，在检索之前已经插入）；否则，返回 `false` 。
- `boolean startsWith(String prefix)` 如果之前已经插入的字符串 `word` 的前缀之一为 `prefix` ，返回 `true` ；否则，返回 `false` 。

- 直接抄答案（本题都是小写字母）

  - 还是很好理解
  - 主要是递归建树


  ```java
  class Trie {
  
      //存放下一个指针
      private Trie []children;//某下标处i不为null则有以(char)('a'+i)字符开头的下一个节点
      //是否为叶子节点
      private boolean isEnd;
      public Trie() {
          children=new Trie[26];
          isEnd=false;
      }
      
      public void insert(String word) {
          Trie node=this;
          for(int i=0;i<word.length();i++){
              char c=word.charAt(i);
              int x=c-'a';
              if(node.children[x]==null){
                  node.children[x]=new Trie();  //当前递归节点
              }
              node=node.children[x]; //递归建树
          }
          node.isEnd=true;
      }
      
      //包含完整的字符串
      public boolean search(String word) {
          Trie node=searchPrefix(word);
          return node!=null && node.isEnd;
      }
      
      public boolean startsWith(String prefix) {
          return searchPrefix(prefix)!=null;
      }
  
      //只是前缀
      private Trie searchPrefix(String prefix){
          Trie node=this;
          for(int i=0;i<prefix.length();i++){
              char c=prefix.charAt(i);
              int x=c-'a';
              if(node.children[x]==null){
                  return null;
              }
              node=node.children[x];
          }
          return node;
      }
  }
  ```

## 139. 单词拆分（j<i的dp）

难度中等2062

给你一个字符串 `s` 和一个字符串列表 `wordDict` 作为字典。请你判断是否可以利用字典中出现的单词拼接出 `s` 。

**注意：**不要求字典中出现的单词全部都使用，并且字典中的单词可以重复使用。

 

**示例 1：**

```
输入: s = "leetcode", wordDict = ["leet", "code"]
输出: true
解释: 返回 true 因为 "leetcode" 可以由 "leet" 和 "code" 拼接成。
```

**示例 2：**

```
输入: s = "applepenapple", wordDict = ["apple", "pen"]
输出: true
解释: 返回 true 因为 "applepenapple" 可以由 "apple" "pen" "apple" 拼接成。
     注意，你可以重复使用字典中的单词。
```

**示例 3：**

```
输入: s = "catsandog", wordDict = ["cats", "dog", "sand", "and", "cat"]
输出: false
```

- 逆过来dp，在已经计算好的i基础上再dp j

  - 注意i、j的大小关系，顺序即可

  ```java
  class Solution {
      //逆过来的dp
      public boolean wordBreak(String s, List<String> wordDict) {
          HashSet<String>set=new HashSet<>(wordDict);
          int n=s.length();
          boolean []dp=new boolean[n+1];
          dp[0]=true; //0是""
          for(int i=1;i<=n;i++){   //以i为结尾
              for(int j=0;j<i;j++){
                  //跟前面j相连
                  if(dp[j] && set.contains(s.substring(j,i))){
                      dp[i]=true;
                      break;
                  }
              }
          }
  
          return dp[n];
      }
  }
  ```

## 140. 单词拆分 II(dp+dfs)

难度困难682

给定一个字符串 `s` 和一个字符串字典 `wordDict` ，在字符串 `s` 中增加空格来构建一个句子，使得句子中所有的单词都在词典中。**以任意顺序** 返回所有这些可能的句子。

**注意：**词典中的同一个单词可能在分段中被重复使用多次。

 

**示例 1：**

```
输入:s = "catsanddog", wordDict = ["cat","cats","and","sand","dog"]
输出:["cats and dog","cat sand dog"]
```

- 先dp求出所有的可能，再dfs找路径

  - dp相当于记忆化
  - 因为直接dfs有点不好处理

  ```java
  class Solution {
      List<String> ans=new LinkedList<>();
      int n;
      boolean []dp;
      HashSet<String> set;
      List<String> list=new LinkedList<>();
      
      public List<String> wordBreak(String s, List<String> wordDict) {
          n=s.length();
          dp=new boolean[n+1];
          set=new HashSet<>(wordDict);
          dp[0]=true;
          for(int i=1;i<=n;i++){
              for(int j=0;j<i;j++){
                  if(dp[j] && set.contains(s.substring(j,i))){
                      dp[i]=true;
                      break;
                  }
              }
          }
          dfs(0,s);
          return ans;
  
      }
      private void dfs(int x,String s){
          if(x==n){
              StringBuilder sb=new StringBuilder();
              for(String str:list){
                  sb.append(" "+str);
              }
              ans.add(sb.deleteCharAt(0).toString());
              return;
          }
  		//对后续dp数组进行dfs
          for(int i=x+1;i<=n;i++){
              if(dp[i] && set.contains(s.substring(x,i))){
                  list.add(s.substring(x,i));
                  dfs(i,s);
                  list.remove(list.size()-1);
              }
          }
      }
  }
  ```

  

## 238. 除自身以外数组的乘积（前缀积+后缀积）

难度中等1410

给你一个整数数组 `nums`，返回 *数组 `answer` ，其中 `answer[i]` 等于 `nums` 中除 `nums[i]` 之外其余各元素的乘积* 。

题目数据 **保证** 数组 `nums`之中任意元素的全部前缀元素和后缀的乘积都在 **32 位** 整数范围内。

请**不要使用除法，**且在 `O(*n*)` 时间复杂度内完成此题。

 

**示例 1:**

```
输入: nums = [1,2,3,4]
输出: [24,12,8,6]
```

**示例 2:**

```
输入: nums = [-1,1,0,-3,3]
输出: [0,0,9,0,0]
```

- 答案数组不算额外空间，要想O(1)，那么就在答案数组上修改

  - 不必局限于前缀和就是元素之和，也可以是积，或者其他某种状态的叠加

  ```java
  class Solution {
      public int[] productExceptSelf(int[] nums) {
          int n=nums.length;
          int []ans=new int [n];
          ans[0]=nums[0];
          //前缀积
          for(int i=1;i<n;i++){
              ans[i]=ans[i-1]*nums[i];
          }
          int l=1;
          int r=1;
          for(int i=n-1;i>=0;i--){
              if(i-1>=0){
                  l=ans[i-1];
              }else{
                  l=1;
              }
              //后缀积
              if(i+1<n){
                  r*=nums[i+1];
              }
  
              ans[i]=l*r;
          }
          return ans;
      }
  }
  ```

  



## 560. 和为 K 的子数组（前缀和+HashMap）

难度中等1884

给你一个整数数组 `nums` 和一个整数 `k` ，请你统计并返回 *该数组中和为 `k` 的连续子数组的个数* 。

 

**示例 1：**

```
输入：nums = [1,1,1], k = 2
输出：2
```

**示例 2：**

```
输入：nums = [1,2,3], k = 3
输出：2
```

- 模拟前缀和sum数组得计算

  ```java
  class Solution {
      public int subarraySum(int[] nums, int k) {
          //因为有负数，0.所以前面可能出现重复的前缀和。
          //不能用set,得用map
          HashMap<Integer,Integer>map=new HashMap<>();
          int ans=0;
          int s=0;
          //相当于int []sum=new int [n+1];sum[0]=0;    //这个前缀数组
          map.put(0,1);
          for(int i=0;i<nums.length;i++){
              s+=nums[i];
              // s-(s-k)=k;
              if(map.containsKey(s-k)){
                  ans+=map.get(s-k);
              }
              // 由于有负数，s-k可能有多个
              map.put(s,map.getOrDefault(s,0)+1);
          }
          return ans;
      }
  }
  ```

  

## 316. 去除重复字母（单调栈+贪心）

给你一个字符串 `s` ，请你去除字符串中重复的字母，使得每个字母只出现一次。需保证 **返回结果的字典序最小**（要求不能打乱其他字符的相对位置）。

**示例 1：**

```
输入：s = "bcabc"
输出："abc"
```

**示例 2：**

```
输入：s = "cbacdcbc"
输出："acdb
```

- 跟字典序相关可以考虑单调栈	

  - 拿不到后续信息可以先遍历下，不影响

  ```java
  import java.util.HashSet;
  import java.util.Stack;
  
  class Solution {
      public String removeDuplicateLetters(String s) {
          //先记录个数
          int n=s.length();
          int []a=new int [26];
          for(int i=0;i<n;i++){
              a[s.charAt(i)-'a']++;
          }
  
          //单调栈
          //新的元素和栈顶元素比较
          //栈顶元素剩余个数为1，直接入栈。
          //新元素<栈顶元素  且栈顶元素不剩余1   栈顶元素不断出栈，新元素入栈
          //新元素>栈顶元素  直接入栈
          //新元素=栈顶   不处理
          Stack<Character>stack=new Stack<>();
          StringBuilder ans=new StringBuilder();
          HashSet<Character>set=new HashSet<>();
          for(int i=0;i<n;i++){
              char ch=s.charAt(i);
              a[ch-'a']--;
              if(stack.isEmpty()){
                  stack.push(ch);
                  set.add(ch);
              }else{
                  if(!set.contains(ch)){
                      // 顶部比现有的大，且顶部元素后面还可以补充
                      while (!stack.isEmpty() && stack.peek()>ch && a[stack.peek()-'a']!=0){
                          set.remove(stack.peek());
                          stack.pop();
                      }
                      set.add(ch);
                      stack.push(ch);
                  }
              }
          }
          
          while (!stack.isEmpty()){
              ans.insert(0,stack.pop());
          }
          return ans.toString();
      }
  }
  ```

## 402.移掉 K 位数字(单调栈+贪心)

难度中等939

给你一个以字符串表示的非负整数 `num` 和一个整数 `k` ，移除这个数中的 `k` 位数字，使得剩下的数字最小。请你以字符串形式返回这个最小的数字。

**示例 1 ：**

```
输入：num = "1432219", k = 3
输出："1219"
解释：移除掉三个数字 4, 3, 和 2 形成一个新的最小的数字 1219 。
```

**示例 2 ：**

```
输入：num = "10200", k = 1
输出："200"
解释：移掉首位的 1 剩下的数字为 200. 注意输出不能有任何前导零。
```

**示例 3 ：**

```
输入：num = "10", k = 2
输出："0"
解释：从原数字移除所有的数字，剩余为空就是 0 。
```

- 升序最小

  - 这个不需要每个字母都出现一次，可以放心移除

  ```java
  import java.util.Stack;
  
  class Solution {
      //单调栈+贪心
      //上升序列是字典序最小
      public String removeKdigits(String num, int k) {
          Stack<Integer> s=new Stack<>();
          //拿到一个上升序列
          for(int i=0;i<num.length();i++){
              int e=Integer.parseInt(num.charAt(i)+"");
              while (k!=0 && !s.isEmpty() && s.peek()>e){
                  s.pop();
                  k--;
              }
              s.push(e);
          }
          //k还没用完
          while (k!=0 && !s.isEmpty()){
              s.pop();
              k--;
          }
          //结果处理
          StringBuilder ans=new StringBuilder();
          while (!s.isEmpty()){
              ans.insert(0,s.pop());
          }
          int size=ans.length();
          for(int i=0;i<size;i++){
              if(ans.charAt(0)=='0'){
                  ans.deleteCharAt(0);
              }else {
                  break;
              }
          }
          //return Integer.parseInt(ans.toString())+"";//处理前置0   结果可能超出int范围
          return ans.length()==0?"0":ans.toString();
          
      }
  }
  ```

## 321. 拼接最大数（分治+贪心+拟单调栈）

难度困难531

给定长度分别为 `m` 和 `n` 的两个数组，其元素由 `0-9` 构成，表示两个自然数各位上的数字。现在从这两个数组中选出 `k (k <= m + n)` 个数字拼接成一个新的数，要求从同一个数组中取出的数字保持其在原数组中的相对顺序。

求满足该条件的最大数。结果返回一个表示该最大数的长度为 `k` 的数组。

**说明:** 请尽可能地优化你算法的时间和空间复杂度。

**示例 1:**

```
输入:
nums1 = [3, 4, 6, 5]
nums2 = [9, 1, 2, 5, 8, 3]
k = 5
输出:
[9, 8, 6, 5, 3]
```

**示例 2:**

```
输入:
nums1 = [6, 7]
nums2 = [6, 0, 4]
k = 5
输出:
[6, 7, 6, 0, 4]
```

**示例 3:**

```
输入:
nums1 = [3, 9]
nums2 = [8, 9]
k = 3
输出:
[9, 8, 9]
```

- 两个数组去除k个元素不会求，但是一个数组的去除k个会求

  - 且能够通过合并的到最优结果，所以考虑分治
  - 求的过程用贪心+单调栈

  ```java
  import java.util.Stack;
  
  class Solution {
      //分治+贪心
      public int[] maxNumber(int[] nums1, int[] nums2, int k) {
          int len=Math.min(nums1.length,nums2.length);
          len=Math.min(k,len);   //len可能已经大于k了
          int []a=nums1.length==len?nums1:nums2;  //短的一个
          int []b=a==nums1?nums2:nums1; //不是a的那一个
          int []ans=new int[k];
  
          //可以从0开始
          int st=0;
          if(k>=Math.max(nums1.length,nums2.length)){
              st=k-Math.max(nums1.length,nums2.length);
          }
          for(int i=st;i<=len;i++){
              int []m=getNums(a,i);
              int []n=getNums(b,k-i);
              ans=merge(m,n,ans,k);
          }
          return ans;
      }
  
      //拟单调栈
      private int[] getNums(int []nums,int n){
          Stack<Integer> s=new Stack<>();
          //一直取到n个
          //n个之后，若大于栈顶元素，且后续还有补充至n个，则栈顶一直出栈。新元素入栈
          for(int i=0;i<nums.length;i++){
              while(s.size()+nums.length-i>n && !s.isEmpty() && s.peek()<nums[i]){
                  s.pop();
              }
              if(s.size()<n){
                  s.add(nums[i]);
              }
          }
          int []t=new int[n];
          for(int i=n-1;i>=0;i--){
              t[i]=s.pop();
          }
          return t;
      }
      //尽量取大的值
      private int[] merge(int []m,int []n,int []ans,int k){
          int []t=new int[k];
          int x=0;
          int y=0;
          for(int i=0;i<k;i++){
              if(y==n.length){
                  t[i]=m[x++];
              }else if(x==m.length){
                  t[i]=n[y++];
              }else{
                  if(m[x]>n[y]){
                      t[i]=m[x++];
                  }else if(m[x]<n[y]){
                      t[i]=n[y++];
                  }else {
                      //等于。取最有价值的那一条  （贪心）
                      for(int j=1;j<n.length;j++){
                          if(x+j<m.length && y+j<n.length){
                              if(m[x+j]>n[y+j]){
                                  t[i]=m[x++];
                                  break;
                              }
                              if(m[x+j]<n[y+j]){
                                  t[i]=n[y++];
                                  break;
                              }
                          }
                          //x已经结尾则取y  给y更多选择空间
                          if(x+j==m.length){
                              t[i]=n[y++];
                              break;
                          }
                          if(y+j==n.length){
                              t[i]=m[x++];
                              break;
                          }
                      }
                  }
              }
          }
  
          //比较值
          for(int i=0;i<k;i++){
              if(t[i]>ans[i]){
                  //ans=t;   改变的是引用
                  return t;
              }
              if(t[i]<ans[i]){
                  return ans;
              }
          }
          return ans;
      }
  
  }
  ```

## 904. 水果成篮（滑动窗口）

难度中等469

你正在探访一家农场，农场从左到右种植了一排果树。这些树用一个整数数组 `fruits` 表示，其中 `fruits[i]` 是第 `i` 棵树上的水果 **种类** 。

你想要尽可能多地收集水果。然而，农场的主人设定了一些严格的规矩，你必须按照要求采摘水果：

- 你只有 **两个** 篮子，并且每个篮子只能装 **单一类型** 的水果。每个篮子能够装的水果总量没有限制。
- 你可以选择任意一棵树开始采摘，你必须从 **每棵** 树（包括开始采摘的树）上 **恰好摘一个水果** 。采摘的水果应当符合篮子中的水果类型。每采摘一次，你将会向右移动到下一棵树，并继续采摘。
- 一旦你走到某棵树前，但水果不符合篮子的水果类型，那么就必须停止采摘。

给你一个整数数组 `fruits` ，返回你可以收集的水果的 **最大** 数目。

 

**示例 1：**

```
输入：fruits = [1,2,1]
输出：3
解释：可以采摘全部 3 棵树。
```

**示例 2：**

```
输入：fruits = [0,1,2,2]
输出：3
解释：可以采摘 [1,2,2] 这三棵树。
如果从第一棵树开始采摘，则只能采摘 [0,1] 这两棵树。
```

- 滑动窗口

  - 问题是要维护下出栈的顺序，将HashMap转化为ArrayList吧

  ```java
  import java.util.ArrayList;
  import java.util.HashMap;
  
  class Solution {
      public int totalFruit(int[] fruits) {
          //滑动窗口，最多保留两个篮子
          HashMap<Integer,Integer> w=new HashMap<>();
          //若篮子类型满了，新的加进来，则把结束位置最小的篮子去掉
          //只有两个篮子而已
          int c=0;
          int ans=0;
          for(int i=0;i<fruits.length;i++){
              if(w.size()<2 || w.size()==2 && w.containsKey(fruits[i])){
                  c++;
              }else{
                  //取出最最左边
                  ArrayList<Integer[]>list=new ArrayList<>();
                  w.forEach((k,v)->{
                      list.add(new Integer[]{k,v});
                  });
                  // 一共就两个篮子。取出最左边的整个篮子
                  if(list.get(0)[1]<list.get(1)[1]){
                      w.remove(list.get(0)[0]);
                      c=i-list.get(0)[1];
                  }else {
                      w.remove(list.get(1)[0]);
                      c=i-list.get(1)[1];
                  }
              }
              w.put(fruits[i],i);   //一定会更新。当前篮子最右的位置
              ans=Math.max(ans,c); // 每次更新ans就好了
          }
          return ans;
      }
  }
  ```

## 572. 另一棵树的子树（dfs+暴力）

难度简单897

给你两棵二叉树 `root` 和 `subRoot` 。检验 `root` 中是否包含和 `subRoot` 具有相同结构和节点值的子树。如果存在，返回 `true` ；否则，返回 `false` 。

二叉树 `tree` 的一棵子树包括 `tree` 的某个节点和这个节点的所有后代节点。`tree` 也可以看做它自身的一棵子树。

 

**示例 1：**

![img](https://assets.leetcode.com/uploads/2021/04/28/subtree1-tree.jpg)

```
输入：root = [3,4,5,1,2], subRoot = [4,1,2]
输出：true
```

**示例 2：**

![img](https://assets.leetcode.com/uploads/2021/04/28/subtree2-tree.jpg)

```
输入：root = [3,4,5,1,2,null,null,null,null,0], subRoot = [4,1,2]
输出：false
```

- 要到树末尾才算

  - 直接dfs+暴力

  ```java
  class Solution {
      
      public boolean isSubtree(TreeNode root, TreeNode subRoot) {
          TreeNode node=root;
          Stack<TreeNode>stack=new Stack<>();
          boolean ans=false;
          while(node!=null || !stack.isEmpty()){
              //找最左
              while(node!=null){
                  stack.push(node);
                  node=node.left;
              }
              //访问
              node=stack.pop();
              if(node.val==subRoot.val){
                  if(check(node,subRoot)){
                      ans=true;
                  }
              }
              node=node.right;
          } 
          return ans;   
      }
  	//要到树末尾
      private boolean check(TreeNode r,TreeNode s){
          if(r==null && s==null){
              return true;
          }
          if(r==null || s==null || r.val!=s.val){
              return false;
          }
          return check(r.left,s.left) && check(r.right,s.right);
      }
  
  }
  ```

  



## 198. 打家劫舍（dp）

难度中等2521

你是一个专业的小偷，计划偷窃沿街的房屋。每间房内都藏有一定的现金，影响你偷窃的唯一制约因素就是相邻的房屋装有相互连通的防盗系统，**如果两间相邻的房屋在同一晚上被小偷闯入，系统会自动报警**。

给定一个代表每个房屋存放金额的非负整数数组，计算你 **不触动警报装置的情况下** ，一夜之内能够偷窃到的最高金额。

 

**示例 1：**

```
输入：[1,2,3,1]
输出：4
解释：偷窃 1 号房屋 (金额 = 1) ，然后偷窃 3 号房屋 (金额 = 3)。
     偷窃到的最高金额 = 1 + 3 = 4 。
```

- 最简单的dp类型

  ```java
  class Solution {
      public int rob(int[] nums) {
          int n=nums.length;
          int []dp=new int[n+1];
          dp[1]=nums[0];
          for(int i=1;i<n;i++){
              dp[i+1]=Math.max(dp[i],dp[i-1]+nums[i]);
          }
          return dp[n];
      }
  }
  ```

## 213. 打家劫舍 II(环形dp)

难度中等1329

你是一个专业的小偷，计划偷窃沿街的房屋，每间房内都藏有一定的现金。这个地方所有的房屋都 **围成一圈** ，这意味着第一个房屋和最后一个房屋是紧挨着的。同时，相邻的房屋装有相互连通的防盗系统，**如果两间相邻的房屋在同一晚上被小偷闯入，系统会自动报警** 。

给定一个代表每个房屋存放金额的非负整数数组，计算你 **在不触动警报装置的情况下** ，今晚能够偷窃到的最高金额。

 

**示例 1：**

```
输入：nums = [2,3,2]
输出：3
解释：你不能先偷窃 1 号房屋（金额 = 2），然后偷窃 3 号房屋（金额 = 2）, 因为他们是相邻的。
```

- dp

  - 这居然可以直接只考虑开头或结尾中的一个 

- 断开环，只要开头或者结尾

  ```java
  class Solution {
      public int rob(int[] nums) {
          int n=nums.length;
          if(n==1){
              return nums[0];
          }
          if(n==2){
              return Math.max(nums[0],nums[1]);
          }
          //不要结尾[0,n-2]   不要开头[0,n-1]
          return Math.max(getMon(nums,0,n-2,n),getMon(nums,1,n-1,n));
      }
      private int getMon(int []nums,int st,int end,int n){
          int []dp=new int[n];
          dp[1]=nums[st];
          int x=2;
          for(int i=st+1;i<=end;i++){
              dp[x]=Math.max(dp[x-1],dp[x-2]+nums[i]);
              x++;
          }
          return dp[n-1];
      }
  }
  ```

## 300. 最长递增子序列（dp）

难度中等3129

给你一个整数数组 `nums` ，找到其中最长严格递增子序列的长度。

**子序列** 是由数组派生而来的序列，删除（或不删除）数组中的元素而不改变其余元素的顺序。例如，`[3,6,2,7]` 是数组 `[0,3,1,6,2,2,7]` 的子序列。

**示例 1：**

```
输入：nums = [10,9,2,5,3,7,101,18]
输出：4
解释：最长递增子序列是 [2,3,7,101]，因此长度为 4 。
```

**示例 2：**

```
输入：nums = [0,1,0,3,2,3]
输出：4
```

**示例 3：**

```
输入：nums = [7,7,7,7,7,7,7]
输出：1
```

- dp吧，

  ```java
  class Solution {
      //用dp吧，尽管O(n2)不用dp你都不知道怎么写
      //还有贪心+二分 O(nlgn)
      public int lengthOfLIS(int[] nums) {
          int n=nums.length;
          int []dp=new int [n];
          int ans=1;
          for(int i=0;i<n;i++){
              dp[i]=1;
              // 前面的全都判一遍
              for(int j=0;j<i;j++){
                  if(nums[j]<nums[i]){
                      dp[i]=Math.max(dp[i],dp[j]+1);
                  }
              }
              ans=Math.max(ans,dp[i]);
          }
          return ans;
      }
  }
  ```


## 263. 丑数（直接求）

难度简单382

**丑数** 就是只包含质因数 `2`、`3` 和 `5` 的正整数。

给你一个整数 `n` ，请你判断 `n` 是否为 **丑数** 。如果是，返回 `true` ；否则，返回 `false` 。


**示例 1：**

```

输入：n = 6
输出：true
解释：6 = 2 × 3

```
**示例 2：**

```

输入：n = 1
输出：true
解释：1 没有质因数，因此它的全部质因数是 {2, 3, 5} 的空集。习惯上将其视作第一个丑数。

```
**示例 3：**

```

输入：n = 14
输出：false
解释：14 不是丑数，因为它包含了另外一个质因数 7 。

```
- 只是判断，直接递归求

  ```java
  class Solution {
      public boolean isUgly(int n) {
          if(n==0){
              return false;
          }
          if(n==1){
              return true;
          }
          if(n%2==0){
              return isUgly(n/2);
          }
          if(n%3==0){
              return isUgly(n/3);
          }
          if(n%5==0){
              return isUgly(n/5);
          }
          return false;
      }
  }
  ```

## 264. 丑数 II(最小堆筛选  或者 dp)

难度中等1056

给你一个整数 `n` ，请你找出并返回第 `n` 个 **丑数** 。

**丑数** 就是只包含质因数 `2`、`3` 和/或 `5` 的正整数。

  




**示例 1：**

```
输入：n = 10
输出：12
解释：[1, 2, 3, 4, 5, 6, 8, 9, 10, 12] 是由前 10 个丑数组成的序列。
```

**示例 2：**

```
输入：n = 1
输出：1
解释：1 通常被视为丑数。
```

- 最小堆

  ```java
  import java.util.HashSet;
  import java.util.PriorityQueue;
  
  class Solution {
      //从当前最小的丑数开始枚举
      //丑数x的倍数2x,3x,5x是丑数。然后把结果放入最小堆
      //非丑数的任意倍都不是丑数
      public int nthUglyNumber(int n) {
          int[]mul={2,3,5};
          PriorityQueue<Long> q=new PriorityQueue<>();
          HashSet<Long> set=new HashSet<>();//去重
          long ans=0;
          q.add(1l);
          set.add(1l);
          for(int i=0;i<n;i++){
              ans=q.poll();
              for(int j=0;j<mul.length;j++){
                  if(!set.contains(ans*mul[j])){
                      set.add(ans*mul[j]);
                      q.add(ans*mul[j]);
                  }
              }
          }
          return (int)ans;
      }
  }
  ```
- dp

  - 最小堆保留了很多没有用到的值



  ```java
  class Solution {
      //三指针，记录2，3，5要乘的数。每个数只能够乘一次，所以可以递增
      public int nthUglyNumber(int n) {
          long []dp=new long [n];
          dp[0]=1;
          int p2=0;
          int p3=0;
          int p5=0;
          for(int i=1;i<n;i++){
              long a2=dp[p2]*2;
              long a3=dp[p3]*3;
              long a5=dp[p5]*5;
              long min=Math.min(a2,Math.min(a3,a5));
              dp[i]=min;
              //若等于则都++，为了防止重复
              if(a2==min){
                  p2++;
              }
              if(a3==min){
                  p3++;
              }
              if(a5==min){
                  p5++;
              }
          }
          return (int)dp[n-1];
      }
  }
  ```

  

## 409. 构造最长回文串（贪心）

难度简单522

给定一个包含大写字母和小写字母的字符串 `s` ，返回 *通过这些字母构造成的 **最长的回文串*** 。

在构造过程中，请注意 **区分大小写** 。比如 `"Aa"` 不能当做一个回文字符串。

 

**示例 1:**

```
输入:s = "abccccdd"
输出:7
解释:
我们可以构造的最长的回文串是"dccaccd", 它的长度是 7。
```

**示例 2:**

```
输入:s = "a"
输出:1
```

**示例 3：**

```
输入:s = "aaaaaccc"
输出:7
```

- 这是构造一个最长回文串，不是求已有的最长串

  ```java
  class Solution {
      public int longestPalindrome(String s) {
          int n=s.length();
          int ans=0;
          int []c=new int [256];
          //统计出现个数
          for(int i=0;i<n;i++){
              c[s.charAt(i)]++;
          }
          //把成对先拿了
          for(int i='a';i<='z';i++){
              if(c[i]!=0 && c[i]>1){
                  ans+=c[i]/2*2;
              }
          }
          for(int i='A';i<'Z';i++){
              if(c[i]!=0 && c[i]>1){
                  ans+=c[i]/2*2;
              }
          }
          //中间还能放一个
          if(ans!=n){
              ans++;
          }
          return ans;
      }
  
  }
  ```

## 179. 最大数(比较器+假设既定法)

难度中等1116

给定一组非负整数 `nums`，重新排列每个数的顺序（每个数不可拆分）使之组成一个最大的整数。

**注意：**输出结果可能非常大，所以你需要返回一个字符串而不是整数。

 

**示例 1：**

```
输入：nums = [10,2]
输出："210"
```

**示例 2：**

```
输入：nums = [3,30,34,5,9]
输出："9534330"
```

- 666，原来可以这样

  - 拼接后再比较大小

  ```java
  class Solution {
      public String largestNumber(int[] nums) {
          int n=nums.length;
          String []s=new String[n];
          for(int i=0;i<n;i++){
              s[i]=String.valueOf(nums[i]);
          }
          Arrays.sort(s,(x,y)->(x+y).compareTo(y+x));
          StringBuilder ans=new StringBuilder();
          for(int i=n-1;i>=0;i--){
              ans.append(s[i]);
          }
          return ans.charAt(0)=='0'?"0":ans.toString();
      }
  }
  ```

- 最捞的方法

  - 不想多说什么。。

  ```java
  class Solution {
      public String largestNumber(int[] nums) {
          int n=nums.length;
          //全是0
          int st=0;
          while(st<n){
              if(nums[st]!=0){
                  break;
              }
              st++;
          }
          if(st==n){
              return "0";
          }
          String []s=new String[n];
          for(int i=0;i<n;i++){
              s[i]=nums[i]+"";
          }
          Arrays.sort(s,(x,y)->{
              if(x.equals(y)){
                  return 0;
              }
              int min=Math.min(x.length(),y.length());
              for(int i=0;i<min;i++){
                  if(x.charAt(i)>y.charAt(i)){
                      return 1;
                  }else if(x.charAt(i)<y.charAt(i)){
                      return -1;
                  }
              }
              
              if(x.length()==min){
                  int a=0;
                  for(int i=min;i<y.length();i++){
                      if(y.charAt(i)>x.charAt(a)){
                          return -1;
                      }else if(y.charAt(i)<x.charAt(a)){
                          return 1;
                      }
                      a++;
                      a%=min;
                  }
                  for(int i=0;i<min;i++){
                      if(x.charAt(i)<y.charAt(y.length()-1)){
                          return -1;
                      }else if(x.charAt(i)>y.charAt(y.length()-1)){
                          return 1;
                      }
                  }
              }else{
                  int a=0;
                  for(int i=min;i<x.length();i++){
                      if(x.charAt(i)>y.charAt(a)){
                          return 1;
                      }else if(x.charAt(i)<y.charAt(a)){
                          return -1;
                      }
                      a++;
                      a%=min;
                  }
                  for(int i=0;i<min;i++){
                      if(y.charAt(i)<x.charAt(x.length()-1)){
                          return 1;
                      }else if(y.charAt(i)>x.charAt(x.length()-1)){
                          return -1;
                      }
                  }
              }
  
              return 0;
              
          });
          StringBuilder ans=new StringBuilder();
          for(int i=n-1;i>=0;i--){
              ans.append(s[i]);
          }
          return ans.toString();
      }
  }
  ```

## 406. 根据身高重建队列(贪心+双排序)

难度中等1593

假设有打乱顺序的一群人站成一个队列，数组 `people` 表示队列中一些人的属性（不一定按顺序）。每个 `people[i] = [hi, ki]` 表示第 `i` 个人的身高为 `hi` ，前面 **正好** 有 `ki` 个身高大于或等于 `hi` 的人。

请你重新构造并返回输入数组 `people` 所表示的队列。返回的队列应该格式化为数组 `queue` ，其中 `queue[j] = [hj, kj]` 是队列中第 `j` 个人的属性（`queue[0]` 是排在队列前面的人）

**示例 1：**

```
输入：people = [[7,0],[4,4],[7,1],[5,0],[6,1],[5,2]]
输出：[[5,0],[7,0],[5,2],[6,1],[4,4],[7,1]]
```

- 思路

  - 先按照身高排序，再插入。

  ```java
  class Solution {
      //贪心+双排序   。第二个排序相当于插入某位置（插队）
      public int[][] reconstructQueue(int[][] people) {
          //按照身高进行排序 第i个元素前面有i-1个大于等于它的
          Arrays.sort(people,(x, y)->{
              if(x[0]==y[0]){
                  return x[1]-y[1];     //身高相等，则按照people[1]升序    
              }
              return y[0]-x[0];  //身高不相等，则按照身高降序
          });
          //新插入的元素身高比旧的元素都小，所以只要找到插入位置即可
          //这个位置就在people[i][1]
          List<int []> ans=new ArrayList<>();
          for(int[]p:people){
              ans.add(p[1],p);//ArrayList.add(index,e);  没有insert方法，只有这个
          }
          return ans.toArray(new int[0][0]); //传递一个最终类型的空数组
      }
  
  ```

}

## 455. 分发饼干（贪心）

难度简单693

假设你是一位很棒的家长，想要给你的孩子们一些小饼干。但是，每个孩子最多只能给一块饼干。

对每个孩子 `i`，都有一个胃口值 `g[i]`，这是能让孩子们满足胃口的饼干的最小尺寸；并且每块饼干 `j`，都有一个尺寸 `s[j]` 。如果 `s[j] >= g[i]`，我们可以将这个饼干 `j` 分配给孩子 `i` ，这个孩子会得到满足。你的目标是尽可能满足越多数量的孩子，并输出这个最大数值。

**示例 1:**

输入: g = [1,2,3], s = [1,1]
输出: 1
解释: 
你有三个孩子和两块小饼干，3个孩子的胃口值分别是：1,2,3。
虽然你有两块小饼干，由于他们的尺寸都是1，你只能让胃口值是1的孩子满足。
所以你应该输出1。

**示例 2:**

输入: g = [1,2], s = [1,2,3]
输出: 2
解释: 
你有两个孩子和三块小饼干，2个孩子的胃口值分别是1,2。
你拥有的饼干数量和尺寸都足以让所有孩子满足。
所以你应该输出2.

- 简单贪心

```java
  class Solution {
      public int findContentChildren(int[] g, int[] s) {
          Arrays.sort(g);
          Arrays.sort(s);
          int x=0;
          for(int i=0;i<s.length;i++){
              if(x==g.length){
                  break;
              }
              if(s[i]>=g[x]){
                  x++;
              }
          }
          return x;
      }
  }
```

  

## 134. 加油站(贪心+更新i)

难度中等1210

在一条环路上有 `n` 个加油站，其中第 `i` 个加油站有汽油 `gas[i]` 升。

你有一辆油箱容量无限的的汽车，从第 `i` 个加油站开往第 `i+1` 个加油站需要消耗汽油 `cost[i]` 升。你从其中的一个加油站出发，开始时油箱为空。

给定两个整数数组 `gas` 和 `cost` ，如果你可以绕环路行驶一周，则返回出发时加油站的编号，否则返回 `-1` 。如果存在解，则 **保证** 它是 **唯一** 的。

 

**示例 1:**

```
输入: gas = [1,2,3,4,5], cost = [3,4,5,1,2]
输出: 3
```

- 更新i，让时间复杂度降低到O(n)

  - 更准确来说，这是模拟

  ```java
  class Solution {
      public int canCompleteCircuit(int[] gas, int[] cost) {
          //直接算，更新i让时间复杂度降低到O(n)
          int n=gas.length;
          for(int i=0;i<n;i++){
              // 当前还有油
              if(gas[i]-cost[i]>=0){
                  // 剩余的油
                  int x=0;
                  // 当前起点出发，走一圈
                  for(int j=0;j<n;j++){
                      x+=gas[(i+j)%n];
                      x-=cost[(i+j)%n];
                      if(x<0){
                          //i还没走完一圈，可以更新i，走快点
                          if(i+j<n){
                              i+=j;
                          }else{
                              //走了一圈还没有拿到最终值
                              return -1;
                          }
                          break;
                      }
                      //走了最后一个
                      if(j==n-1){
                          return i;
                      }
                  }
              }
          }
          return -1;
    }
  }
  ```

  

## 236. 二叉树的最近公共祖先(一次dfs，结果和返回值不同，在dfs中处理结果)

难度中等2233

给定一个二叉树, 找到该树中两个指定节点的最近公共祖先。

[百度百科](https://baike.baidu.com/item/最近公共祖先/8918834?fr=aladdin)中最近公共祖先的定义为：“对于有根树 T 的两个节点 p、q，最近公共祖先表示为一个节点 x，满足 x 是 p、q 的祖先且 x 的深度尽可能大（**一个节点也可以是它自己的祖先**）。”

 

**示例 1：**

![img](https://assets.leetcode.com/uploads/2018/12/14/binarytree.png)

```
输入：root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 1
输出：3
解释：节点 5 和节点 1 的最近公共祖先是节点 3 。
```

**示例 2：**

![img](https://assets.leetcode.com/uploads/2018/12/14/binarytree.png)

```
输入：root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 4
输出：5
解释：节点 5 和节点 4 的最近公共祖先是节点 5 。因为根据定义最近公共祖先节点可以为节点本身。
```

**示例 3：**

```
输入：root = [1,2], p = 1, q = 2
输出：1
```

**提示**

p树中节点数目在范围 [2, 105] 内。
-109 <= Node.val <= 109
所有 Node.val 互不相同 。
p != q
p 和 q 均存在于给定的二叉树中。

- 一次dfs

  - 不要想着两次，通过子树的返回值来处理结果。都处理就只相当于遍历一次树。O(n)

- 从底到上的dfs，包含其中一个就可以返回true，然后在本层判断

  ```java
  class Solution {
      //dfs返回结果和所求答案不一致。但是有关系
      TreeNode ans=null;
      public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
          check(root,p,q);
          return ans;
      }
  
      //在一次dfs过程中取结果，不用再暴力一次
      //用boolean存储子树的结果，在当前节点根据子树结果进行判断。满足则更新答案
      private boolean check(TreeNode r,TreeNode p, TreeNode q){
          if(r==null){
              return false;
          }
          boolean left=check(r.left,p,q);
          boolean right=check(r.right,p,q);
          //r是p或者q中一个，且子节点有另一个
          //r的子节点各有一个
          //注意运算符优先级问题，不记得就都加括号
          if(((r.val==p.val || r.val==q.val) && (left || right) ) || (left && right) ){
              ans=r; //有的话只有一个吧，再往上就不满足了
          }
          //至少有一个都可以返回true。两个没关系，因为结果已经被处理了
          return r.val==p.val ||r.val==q.val || left || right;
      }
  }
  ```

  


## 222. 完全二叉树的节点个数(dfs吧)

难度中等915

给你一棵 **完全二叉树** 的根节点 `root` ，求出该树的节点个数。

[完全二叉树](https://baike.baidu.com/item/完全二叉树/7773232?fr=aladdin) 的定义如下：在完全二叉树中，除了最底层节点可能没填满外，其余每层节点数都达到最大值，并且最下面一层的节点都集中在该层最左边的若干位置。若最底层为第 `h` 层，则该层包含 `1~ 2h` 个节点。

 

**示例 1：**

![img](https://assets.leetcode.com/uploads/2021/01/14/complete.jpg)

输入：root = [1,2,3,4,5,6]
输出：6

**示例 2：**

输入：root = []
输出：0

**示例 3：**

输入：root = [1]
输出：1

- 更好的解法是利用完全二叉树的性质来降低dfs的时间复杂度

- dfs

  - 直接从底部累加就好了
  

```java

  class Solution {
      public int countNodes(TreeNode root) {
          if(root==null){
              return 0;
          }
          return countNodes(root.left)+countNodes(root.right)+1;
      }   
  }
```

## 230. 二叉搜索树中第K小的元素（中序dfs）

难度中等723

给定一个二叉搜索树的根节点 `root` ，和一个整数 `k` ，请你设计一个算法查找其中第 `k` 个最小元素（从 1 开始计数）。

 

**示例 1：**

![img](https://assets.leetcode.com/uploads/2021/01/28/kthtree1.jpg)

```
输入：root = [3,1,4,null,2], k = 1
输出：1
```

**示例 2：**

![img](https://assets.leetcode.com/uploads/2021/01/28/kthtree2.jpg)

```
输入：root = [5,3,6,2,4,null,null,1], k = 3
输出：3
```

- 除非转化为AVL，平衡二叉搜索树。能够提高查找速度？

- 中序遍历

  ```java
  class Solution {
      //前序遍历
      public int kthSmallest(TreeNode root, int k) {
          TreeNode node=root;
          Stack<TreeNode> stack=new Stack<>();
          // 这里用或就可以了
          while(node!=null || !stack.isEmpty()){
              // 当前节点及其左子树全部放进去
              while(node!=null){
                  stack.push(node);
                  node=node.left;
              }
              // 拿出根节点
              node=stack.pop();
              k--;
              if(k==0){
                  return node.val;
              }
              // 轮到右子树
              node=node.right;
          }
          return -1;
      }
  } 
  ```

## 543. 二叉树的直径(返回值和结果不一致的dfs)

难度简单1300

给定一棵二叉树，你需要计算它的直径长度。一棵二叉树的直径长度是任意两个结点路径长度中的最大值。这条路径可能穿过也可能不穿过根结点。

 

**示例 :**
给定二叉树

```
          1
         / \
        2   3
       / \     
      4   5    
```

返回 **3**, 它的长度是路径 [4,2,1,3] 或者 [5,2,1,3]。

- 大概就是求二叉树的最长路径

  - 自底向上，为null时赋值为0

  ```java
  class Solution {
      //一条边长度为1，寻找最长路径
      int ans=0;
      public int diameterOfBinaryTree(TreeNode root) {
          dfs(root);
          return ans;
      }
  
      private int dfs(TreeNode root){
          if(root==null){
              return 0;
          }
          int left=dfs(root.left);
          int right=dfs(root.right);
          //左加右
          ans=Math.max(ans,left+right); // 这里不用加根节点吗？
          return Math.max(left,right)+1;//左和右最大的
      }
  }
  ```

  

## 98. 验证二叉搜索树(中序dfs)

难度中等1971

给你一个二叉树的根节点 `root` ，判断其是否是一个有效的二叉搜索树。

**有效** 二叉搜索树定义如下：

- 节点的左子树只包含 **小于** 当前节点的数。
- 节点的右子树只包含 **大于** 当前节点的数。
- 所有左子树和右子树自身必须也是二叉搜索树。

 

**示例 1：**

![img](https://assets.leetcode.com/uploads/2020/12/01/tree1.jpg)

```
输入：root = [2,1,3]
输出：true
```

**示例 2：**

![img](https://assets.leetcode.com/uploads/2020/12/01/tree2.jpg)

```
输入：root = [5,1,4,null,null,3,6]
输出：false
解释：根节点的值是 5 ，但是右子节点的值是 4 。
```

- 直接验证

  - 用一个pre记录前一个节点

  ```java
  class Solution {
      public boolean isValidBST(TreeNode root) {
          Stack<TreeNode>stack=new Stack<>();
          TreeNode node=root;
          TreeNode pre=null;
          while(node!=null || !stack.isEmpty()){
              while(node!=null){
                  stack.push(node);
                  node=node.left;
              }
              node=stack.pop();
              if(pre!=null && pre.val>=node.val){
                  return false;
              }
              pre=node;
              node=node.right;
          }
          return true;
      }
  }
  ```

## 669. 修剪二叉搜索树(不是中序dfs，是往上构造dfs。666)

难度中等789

给你二叉搜索树的根节点 `root` ，同时给定最小边界`low` 和最大边界 `high`。通过修剪二叉搜索树，使得所有节点的值在`[low, high]`中。修剪树 **不应该** 改变保留在树中的元素的相对结构 (即，如果没有被移除，原有的父代子代关系都应当保留)。 可以证明，存在 **唯一的答案** 。

所以结果应当返回修剪好的二叉搜索树的新的根节点。注意，根节点可能会根据给定的边界发生改变。

 

**示例 1：**

![img](https://assets.leetcode.com/uploads/2020/09/09/trim1.jpg)

```
输入：root = [1,0,2], low = 1, high = 2
输出：[1,null,2]
```

**示例 2：**

![img](https://assets.leetcode.com/uploads/2020/09/09/trim2.jpg)

```
输入：root = [3,0,4,null,2,null,null,1], low = 1, high = 3
输出：[3,2,null,1]
```

- 就是普通的dfs，不断往上构造

  - 666，向上返回根节点
  - 原有结构保留，根本不用新建

  ```java
  class Solution {
      //看起来示例二不讲理。但是这就是二叉搜索树
      //不要一看到二叉搜索树就想到中序
      //这题不是用中序
      public TreeNode trimBST(TreeNode root, int low, int high) {
          if(root==null){
              return null;
          }
          if(root.val<low){
              return trimBST(root.right,low,high);
          }else if(root.val>high){
              return trimBST(root.left,low,high);
          }else{
              root.left=trimBST(root.left,low,high);
              root.right=trimBST(root.right,low,high);
              return root;
          }
      }
  }
  ```

## 99. 恢复二叉搜索树(非递归中序处理)

给你二叉搜索树的根节点 `root` ，该树中的 **恰好** 两个节点的值被错误地交换。*请在不改变其结构的情况下，恢复这棵树* 。

 

**示例 1：**

![img](https://assets.leetcode.com/uploads/2020/10/28/recover1.jpg)

```
输入：root = [1,3,null,null,2]
输出：[3,1,null,null,2]
解释：3 不能是 1 的左孩子，因为 3 > 1 。交换 1 和 3 使二叉搜索树有效。
```

**示例 2：**

![img](https://assets.leetcode.com/uploads/2020/10/28/recover2.jpg)

```
输入：root = [3,1,4,null,null,2]
输出：[2,1,4,null,null,3]
解释：2 不能在 3 的右子树中，因为 2 < 3 。交换 2 和 3 使二叉搜索树有效。
```

 

- 先中序遍历，然后处理

  - 这么看非递归中序真的很不错

- 记录逆序的节点就可以了

  ```java
  class Solution {
      //二叉搜索树没值相等的节点
      //没有要求，则交换节点就只交换值
      //若真的要交换节点，则应该用stack存储遍历顺序
      public void recoverTree(TreeNode root) {
  
          TreeNode node=root;
          Stack<TreeNode>stack=new Stack<>();
          TreeNode pre=null;
          List<TreeNode>ans=new ArrayList<>();
          while(node!=null || !stack.isEmpty()){
              while(node!=null){
                  stack.push(node);
                  node=node.left;
              }
              node=stack.pop();
              if(pre!=null && pre.val>node.val){
                  ans.add(pre);
                  ans.add(node);
              }
              pre=node;
              node=node.right;
          }
          //无论是相邻位置逆序。还是有两组逆序。都是最前和最后交换
          int t=ans.get(0).val;
          ans.get(0).val=ans.get(ans.size()-1).val;
          ans.get(ans.size()-1).val=t;
          
      }
  }
  ```

  


## 662. 二叉树最大宽度(bfs层序遍历+编号)

难度中等554

给你一棵二叉树的根节点 `root` ，返回树的 **最大宽度** 。

树的 **最大宽度** 是所有层中最大的 **宽度** 。

每一层的 **宽度** 被定义为该层最左和最右的非空节点（即，两个端点）之间的长度。将这个二叉树视作与满二叉树结构相同，两端点间会出现一些延伸到这一层的 `null` 节点，这些 `null` 节点也计入长度。

题目数据保证答案将会在 **32 位** 带符号整数范围内。

 

**示例 1：**

![img](https://assets.leetcode.com/uploads/2021/05/03/width1-tree.jpg)

输入：root = [1,3,2,5,3,null,9]
输出：4
解释：最大宽度出现在树的第 3 层，宽度为 4 (5,3,null,9) 。

**示例 2：**

![img](https://assets.leetcode.com/uploads/2022/03/14/maximum-width-of-binary-tree-v3.jpg)

输入：root = [1,3,2,5,null,null,9,6,null,7]
输出：7
解释：最大宽度出现在树的第 4 层，宽度为 7 (6,null,null,null,null,null,7) 。

**示例 3：**

![img](https://assets.leetcode.com/uploads/2021/05/03/width3-tree.jpg)

输入：root = [1,3,2,5]
输出：2
解释：最大宽度出现在树的第 2 层，宽度为 2 (3,2) 。

- 就bfs吧，这里还得自己定义一个节点 类

  - 给节点编号
  - 也可以只用一个链表，先记录下链表的大小
  - 自定义类啥的需要定义就定义，否则可以考虑int[]
  

```java

  import java.util.ArrayList;
  import java.util.List;
  
  /**
   * Definition for a binary tree node.
   * public class TreeNode {
   *     int val;
   *     TreeNode left;
   *     TreeNode right;
   *     TreeNode() {}
   *     TreeNode(int val) { this.val = val; }
   *     TreeNode(int val, TreeNode left, TreeNode right) {
   *         this.val = val;
   *         this.left = left;
   *         this.right = right;
   *     }
   * }
   */
  class Solution {
      //层序遍历就是广度优先遍历吧
      public int widthOfBinaryTree(TreeNode root) {
          //层序放null？
          int  ans=1;
          //给节点编号
          List<Pair> list=new ArrayList<>();
          list.add(new Pair(root,1l));
          while(list.size()!=0){
              List<Pair>t=new ArrayList<>();
              for(Pair e:list){
                  long n=e.getNum();
                  TreeNode node=e.getNode();
                  if(node.left!=null){
                      t.add(new Pair(node.left,(long)2*n));
                  }
                  if(node.right!=null){
                      t.add(new Pair(node.right,(long)2*n+1));
                  }
              }
              if(t.size()>1){
                  ans=(int)Math.max(ans,t.get(t.size()-1).getNum()-t.get(0).getNum()+1);
              }
              //else{
              //    break;    //不能只有一个节点就停止bfs，因为一个节点也可能开花
              //}
              list=t;
          }
          return ans;
      }
      //还是要定义节点类
      class Pair{
          TreeNode node;
          long num;
          public Pair(TreeNode node,long num){
              this.node=node;
              this.num=num;
          }
          public TreeNode getNode(){
              return node;
          }
          public long getNum(){
              return num;
          }
      }
  }
```

## 279. 完全平方数(dp)

难度中等1675

给你一个整数 `n` ，返回 *和为 `n` 的完全平方数的最少数量* 。

**完全平方数** 是一个整数，其值等于另一个整数的平方；换句话说，其值等于一个整数自乘的积。例如，`1`、`4`、`9` 和 `16` 都是完全平方数，而 `3` 和 `11` 不是。

 

**示例 1：**

```
输入：n = 12
输出：3 
解释：12 = 4 + 4 + 4
```

**示例 2：**

```
输入：n = 13
输出：2
解释：13 = 4 + 9
```

- 就dp

  - 对所有的平方数都考虑
- 不是二进制优化
  
  ```java
  class Solution {
      public int numSquares(int n) {
          //计算所有平方数 100个
          int []a=new int[100];
          for(int i=0;i<100;i++){
              a[i]=(i+1)*(i+1);
          }
          int []dp=new int[n+1];
          //要对n进行dp，O(n)就O(n)吧
          for(int i=1;i<=n;i++){
              int min=Integer.MAX_VALUE;
              for(int j=0;j<100;j++){
                  if(i>=a[j]){
                      min=Math.min(min,dp[i-a[j]]+1);
                  }
              }
              dp[i]=min;
          }
          return dp[n];
      }
  }
  ```

## 用户喜好

为了不断优化推荐效果，今日头条每天要存储和处理海量数据。假设有这样一种场景：我们对用户按照它们的注册时间先后来标号，对于一类文章，每个用户都有不同的喜好值，我们会想知道某一段时间内注册的用户（标号相连的一批用户）中，有多少用户对这类文章喜好值为k。因为一些特殊的原因，不会出现一个查询的用户区间完全覆盖另一个查询的用户区间(不存在L1<=L2<=R2<=R1)。数据范围： ![img](https://www.nowcoder.com/equation?tex=1%20%5Cle%20n%20%5Cle%20300000%20%5C) ， ![img](https://www.nowcoder.com/equation?tex=1%20%5Cle%20q%20%5Cle%20300000%20%5C) ，![img](https://www.nowcoder.com/equation?tex=%7Ck%7C%20%5Cle%20%202%5E%7B31%7D%20%5C) ， 每组查询满足 ![img](https://www.nowcoder.com/equation?tex=1%20%5Cle%20l%2Cr%20%5Cle%20300000%20%5C) ，查询的喜好值满足 ![img](https://www.nowcoder.com/equation?tex=%7Ck%7C%20%5Cle%20%202%5E%7B31%7D%20%5C)

时间限制：C/C++ 3秒，其他语言6秒

空间限制：C/C++ 256M，其他语言512M

输入描述：

```
输入： 第1行为n代表用户的个数 第2行为n个整数，第i个代表用户标号为i的用户对某类文章的喜好度 第3行为一个正整数q代表查询的组数  第4行到第（3+q）行，每行包含3个整数l,r,k代表一组查询，即标号为l<=i<=r的用户中对这类文章喜好值为k的用户的个数。 数据范围n <= 300000,q<=300000 k是整型
```

输出描述：

```
输出：一共q行，每行一个整数代表喜好值为k的用户的个数
```

示例1

输入例子：

```
5
1 2 3 3 5
3
1 2 1
2 4 5
3 5 3
```

输出例子：

```
1
0
2
```

例子说明：

```
样例解释:
有5个用户，喜好值为分别为1、2、3、3、5，
第一组询问对于标号[1,2]的用户喜好值为1的用户的个数是1
第二组询问对于标号[2,4]的用户喜好值为5的用户的个数是0
第三组询问对于标号[3,5]的用户喜好值为3的用户的个数是2 
```

- 思路
  - 用map存储相应喜好度对应的下标
  - 因为若是范围比较大，且区间中喜好度为k的数量很少，一个个遍历会超时

```java
import java.util.Scanner;
import java.util.*;
// 注意类名必须为 Main, 不要有任何 package xxx 信息
public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        while (in.hasNextInt()) { // 注意 while 处理多个 case
            int n=in.nextInt();
            Map<Integer,List<Integer>>map=new HashMap<>();
            for(int i=0;i<n;i++){
                int like=in.nextInt();
                if(!map.containsKey(like)){
                    List<Integer>list=new ArrayList<>();
                    list.add(i);
                    map.put(like,list);
                }else{
                    map.get(like).add(i);
                }
            }
            int q=in.nextInt();
            for(int i=0;i<q;i++){
                int start=in.nextInt();
                int end=in.nextInt();
                int k=in.nextInt();
                int count=0;
                if(map.containsKey(k)){
                    for(Integer like:map.get(k)){
                        if(like>=start-1 && like<end){
                            count++;
                        }
                    }
                }
                System.out.println(count);
            }
        }
    }
}
```

## 手串

作为一个手串艺人，有金主向你订购了一条包含n个杂色串珠的手串——每个串珠要么无色，要么涂了若干种颜色。为了使手串的色彩看起来不那么单调，金主要求，手串上的任意一种颜色（不包含无色），在任意连续的m个串珠里至多出现一次（注意这里手串是一个环形）。手串上的颜色一共有c种。现在按顺时针序告诉你n个串珠的手串上，每个串珠用所包含的颜色分别有哪些。请你判断该手串上有多少种颜色不符合要求。即询问有多少种颜色在任意连续m个串珠中出现了至少两次。

时间限制：C/C++ 1秒，其他语言2秒

空间限制：C/C++ 64M，其他语言128M

输入描述：

```
第一行输入n，m，c三个数，用空格隔开。(1 <= n <= 10000, 1 <= m <= 1000, 1 <= c <= 50) 接下来n行每行的第一个数num_i(0 <= num_i <= c)表示第i颗珠子有多少种颜色。接下来依次读入num_i个数字，每个数字x表示第i颗柱子上包含第x种颜色(1 <= x <= c)
```

输出描述：

```
一个非负整数，表示该手链上有多少种颜色不符需求。
```

示例1

输入例子：

```
5 2 3
3 1 2 3
0
2 2 3
1 2
1 3
```

输出例子：

```
2
```

例子说明：

```
第一种颜色出现在第1颗串珠，与规则无冲突。
第二种颜色分别出现在第 1，3，4颗串珠，第3颗与第4颗串珠相邻，所以不合要求。
第三种颜色分别出现在第1，3，5颗串珠，第5颗串珠的下一个是第1颗，所以不合要求。
总计有2种颜色的分布是有问题的。 
这里第2颗串珠是透明的。
```

- Map加List存储颜色出现的位置

```java
import java.util.*;

// 注意类名必须为 Main, 不要有任何 package xxx 信息
public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        while (in.hasNextInt()) { // 注意 while 处理多个 case
            
            int n = in.nextInt();
            int m = in.nextInt();
            int c=in.nextInt();
            Map<Integer,List<Integer>> colorMap=new HashMap<>();  //每个珠子上颜色不会重复，所以用List就可以
            for(int i=0;i<n;i++){
                int len=in.nextInt();
                for(int j=0;j<len;j++){
                    int color=in.nextInt();
                    //有该颜色，则将其放入下表放入到color对应的list
                    if(!colorMap.containsKey(color)){
                        List<Integer>colorList=new ArrayList<>();
                        colorList.add(i);
                        colorMap.put(color,colorList);
                    }else{
                        colorMap.get(color).add(i);
                    }
                }
            }
            int count=0;
            Set<Integer> colorSet=colorMap.keySet();
            for(Integer color:colorSet){
                List<Integer>list=colorMap.get(color);
                for(int i=1;i<list.size();i++){
                    if(list.get(i)-list.get(i-1)<m ){
                        count++;
                        break;
                    }
                    //最后一个要和第一个判断
                    if(i==list.size()-1 &&  list.get(0)+n-list.get(i)<m){
                        count++;
                    }
                }
            }
            System.out.println(count);
        }
    }
}
```



## 后序遍历

- 先左，再右，最后根
- 用于求一些左右子树返回结果 的递归

# 原则

## 忌

- （387）能用基本类型int[]就不用引用类型HashMap

## 要

- （6，62）就是说，有时候会用到数学知识

## 特殊

- 打表法应该是最快的