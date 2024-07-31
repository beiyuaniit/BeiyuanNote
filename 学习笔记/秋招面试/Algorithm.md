## 杂项

- leetcode周赛
  - [leetcode/solution/CONTEST_README.md at 3e49c0ba82ea823f470bc099d54b9161c04a99b0 · doocs/leetcode · GitHub](https://github.com/doocs/leetcode/blob/3e49c0ba82ea823f470bc099d54b9161c04a99b0/solution/CONTEST_README.md)

## 思想

- dfs
  - 从叶子节点返回。所以可以进行累加。树上的叶子节点或者图上的边缘节点

## 没有固定行数的输入

- 报错`NoSuchElementException: No line found`通常是因为输入流被提前关闭或没有可读取的输入行导致的。

  确定发生这个错误的原因，可能有以下几种情况：

  1. 在调用`in.nextLine()`之前，输入流`in`已经被关闭。如果使用`System.in`作为输入流，确保没有提前调用了`System.in.close()`关闭输入流。
  2. 输入流`in`中没有可读取的数据。在循环中多次调用`in.nextLine()`时，确保输入流中有足够的数据行。
     - 如果是从文件中读取输入，则检查文件是否存在且内容是否正确。
     - 如果是从控制台读取输入，则确保每次循环之前有输入行可供读取。一种常见错误是在循环外部调用`in.nextLine()`读取输入行，然后在循环内部使用`in.nextLine()`读取剩下的行。这样会导致在循环第二次及以后的迭代中出现`NoSuchElementException`异常。应该在循环内部每次调用`in.nextLine()`来获取输入行。

  以下是使用`Scanner`类从控制台读取输入的示例代码：

  ```
  import java.util.Scanner;
  
  public class Main {
      public static void main(String[] args) {
          Scanner in = new Scanner(System.in);
          String str;
          
          while (in.hasNextLine()) {
              str = in.nextLine();
              // 在这里处理输入行
          }
          
          in.close();
      }
  }
  ```

  

  请检查你的代码是否遵循以上建议，并确保输入流中有足够的数据可供读取。如果问题仍然存在，请提供更多的代码细节，以便我能够更好地帮助你解决问题。

# ==数论==

## 埃式素数筛

- 用素数的倍数去标记

  ```java
      // 素数筛 求1-n之间的素数
      /**
       * 素数和质数是同一个概念。
       * 最小的素数是2，一个素数的因子只包含1和它本身
       * 埃拉托色尼筛法
       * 时间复杂度O(nloglogn) 比如100 用2-10去筛
       */
      public boolean[] sieveOfEratosthenes(int n){
          boolean[] isPrimes=new boolean[n+1];
          for(int i=2;i<=n;i++){
              // 0和1不是素数
              isPrimes[i]=true;
          }
          // n的最大因子小于等于n的开方，同理可证明其他比n小的数的最大因子小于等于n的开方
          for(int i=2;i*i<=n;i++){
              if(isP  rimes[i]){
                  // j从i*i开始，因为之前的已经被小的素数筛出来了
                  for(int j=i*i;j<=n;j+=i){
                      isPrimes[j]=false;
                  }
              }
          }
          return isPrimes;
      }
  ```

## 求所有素因子

- 不断除以素数，用while

  ```java
      /**
       * 最坏情况O(n) 一个数本身是素数
       * 质数分解定理指出，一个大于1的整数可以表示为若干个质数的乘积，这个表示是唯一的
       */
  
      public List<Integer> getPrimeFactors(int n){
          List<Integer>ans=new LinkedList<>();
          // 从2开始，逐个除以素数
          for (int i=2;i<=n;i++){
              // 这里用while 直到无法分解
              while (n%i==0){
                  ans.add(i);
                  n/=i;
              }
          }
          return ans;
      }
  ```

## 求所有约数

- 直接判断

  ```java
      public List<Integer> getDivisors(int n){
          List<Integer>ans=new LinkedList<>();
          int sqrt=(int)Math.sqrt(n);
          for(int i=1;i<=sqrt;i++){
              if(n%i==0){
                  ans.add(i);
                  // 只能在这里判断，因为若是i<sqrt 条件，再在外面判断，这样可能重复
                  if(i!=sqrt){
                      ans.add(n/i);
                  }
              }
          }
          return ans;
      }
  
  ```


## 同余关系

###　1590. 使数组和能被 P 整除(同余+前缀和+Hash)

难度中等217

给你一个正整数数组 `nums`，请你移除 **最短** 子数组（可以为 **空**），使得剩余元素的 **和** 能被 `p` 整除。 **不允许** 将整个数组都移除。

请你返回你需要移除的最短子数组的长度，如果无法满足题目要求，返回 `-1` 。

**子数组** 定义为原数组中连续的一组元素。

 

**示例 1：**

```
输入：nums = [3,1,4,2], p = 6
输出：1
解释：nums 中元素和为 10，不能被 p 整除。我们可以移除子数组 [4] ，剩余元素的和为 6 。
```

**示例 2：**

```
输入：nums = [6,3,5,2], p = 9
输出：2
解释：我们无法移除任何一个元素使得和被 9 整除，最优方案是移除子数组 [5,2] ，剩余元素为 [6,3]，和为 9 。
```

**示例 3：**

```
输入：nums = [1,2,3], p = 3
输出：0
解释：和恰好为 6 ，已经能被 3 整除了。所以我们不需要移除任何元素。
```

- 

  - Hash可以是HashMap或者HashSet，当然这题是HashMap
  - 同余关系去看Note.md，用于处理两数相减mod p=0的情况
  - 前缀和一般要有一个dp[0]    dp[n+1] 多一位。记得

  ```java
  import java.util.HashMap;
  
  class Solution {
      public int minSubarray(int[] nums, int p) {
          int n=nums.length;
          long []s=new long[n+1];
          //要多一个dp[0] 才能够拿到所有的子数组和
          for(int i=1;i<=n;i++){
              s[i]=s[i-1]+nums[i-1];
          }
          long x=s[n];
          if(x%p==0){ //判断一下只是提高效率  
              return 0;
          }
          int ans=n;
          HashMap<Long,Integer> map=new HashMap<>();//记录最新出现位置
          for(int i=0;i<=n;i++){
              map.put(s[i]%p,i); //存进去的是 l%p    不能是l
              // l%p=(r-x)%p    若存在这样的l    l%p=( (r-x)%p + p )%p     r-x是可能是负数
              long t=(s[i]%p-x%p+p)%p;
              if(map.containsKey(t)){
                  ans=Math.min(i-map.get(t),ans);
              }
          }
          return ans<n?ans:-1; //ans=n是将所有元素都去除。题目不允许
      }
  }
  ```

## 减少法

### 1780. 判断一个数字是否可以表示成三的幂的和

难度中等115

给你一个整数 `n` ，如果你可以将 `n` 表示成若干个不同的三的幂之和，请你返回 `true` ，否则请返回 `false` 。

对于一个整数 `y` ，如果存在整数 `x` 满足 `y == 3x` ，我们称这个整数 `y` 是三的幂。

**示例 1：**

```
输入：n = 12
输出：true
解释：12 = 31 + 32
```

**示例 2：**

```
输入：n = 91
输出：true
解释：91 = 30 + 32 + 34
```

- 直接模拟

  - 类似于转化为三进制，从大的开始比较，若有则直接减去
  - 27>9+3+1 若一个数n大于27，则27处的三进制一定是要为1。因为后面的数的和小于27
  - 同理8>4+2+1

  ```java
  class Solution {
      public boolean checkPowersOfThree(int n) {
          int N=20;
          int []a=new int[N];
          for(int i=0;i<N;i++){
              a[i]=(int)Math.pow(3,i);
          }
          for(int i=N-1;i>=0;i--){
              if(n>=a[i]){
                  n-=a[i];
              }
          }
          return n==0;
      }
  }
  ```

## 快速幂乘

- 求解x的n次方

  - n可能为负数

- 可以用二进制从低位开始算

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

## 倍增数组

### 区间最值RMQ

- 链接：https://blog.csdn.net/flora715/article/details/80998227

- ST 算法在 RMQ（区间最值）中用来求得一个区间的最值，但却不能维护最值。

  也就是说，过程中不能改变区间中的某个元素的值

  O(nlogn) 的预处理和 O(1) 的查询对于需要大量询问的场景是非常适用的。

- 有多次查询可以先构造好答案集合

- 也可以考虑用线段树解决

  ```java
      //区间最值查询
  public static void main(String []args){
      Scanner in =new Scanner(System.in);
      int n=in.nextInt();  // 输入数组的长度
      int m=in.nextInt();  // 查询次数
      // 用 dp[i][j] 表示从 i 开始的 2^j 个数的最值
      int [][]d=new int[n+1][40];  // 创建二维数组来保存输入的数组元素和最小值
  
      for(int i=1;i<=n;i++){
          d[i][0]=in.nextInt();  // 将数组元素保存到数组 d 的第一维中
      }
  
      // 计算数组中每个位置到其后面的 2 的幂次位置的范围内的最小值
      for(int j=1;(1<<j)<=n;j++){
          for(int i=1;i+(1<<j)-1<=n;i++){
              d[i][j]=Math.min(d[i][j-1],d[i+(1<<(j-1))][j-1]);
          }
      }
  
      // 根据输入的查询范围，利用数组 d 找到范围内的最小值并输出
      for(int i=0;i<m;i++){
          int l=in.nextInt();  // 查询范围的左边界
          int r=in.nextInt();  // 查询范围的右边界
          int k=log2(r-l+1);  // 这是以为e为底的对数。计算查询范围所对应的二维数组的列数
          System.out.println(Math.min(d[l][k],d[r-(1<<k)+1][k]));  // 输出查询范围内的最小值
      }
  }
  
  public static int log2(int x) {
      return (int) (Math.log(x) / Math.log(2));
  }
  ```

## 二进制优化

### ac5.多重背包问题

- 数据规模比四大，优化后变为0-1背包问题

- 假设给定价值为2，数量为10的物品，依据二进制优化思想可将10分解为1+2+4+3，则原来价值为2，数量为10的物品可等效转化为价值分别为1*2，2*2，4*2，3*2，即价值分别为2，4，8，6，数量均为1的物品。重量随之变化

- 1-10之间的数都可以用1,2,4,3的组合来表示（每个只选一个），且不重复

  ```java
  import java.util.*;
  public class Main{
      //根据二进制打包成更大的小包。所有s[i]都可以由这些小包构成
      //不是每位都取，11 = 1+2+8，这样无法表示4，5，6等可选择数字
      // 要想拿到1-N之间的任何数，则一般最大的一位不能取11=1 2 4 4   8=1 2 4 1 
      public static void main(String []args){
          Scanner in=new Scanner(System.in);
          int N=in.nextInt();
          int V=in.nextInt();
          List<Integer>vList=new ArrayList<>();
          List<Integer>wList=new ArrayList<>();
          
          for(int i=0;i<N;i++){
              int wi=in.nextInt();
              int vi=in.nextInt();
              
              int si=in.nextInt();
              
              int k=1;
              // 从1开始取，s要自己减去
              while(k<=si){
                  vList.add(vi*k);
                  wList.add(wi*k);
                  si-=k;
                  k*=2;
              }
              //k>si时退出，还有个si未处理
              if(si>0){
                  vList.add(vi*si);
                  wList.add(wi*si);
              }
          }
          
          int []dp=new int [V+1];
          for(int i=0;i<vList.size();i++){
              int v=vList.get(i);
              int w=wList.get(i);
              for(int j=V;j>=w;j--){
                  dp[j]=Math.max(dp[j],dp[j-w]+v);
              }
          }
          // for(int i=0;i<vList.size();i++){
          //     System.out.print(vList.get(i)+" ");
          // }
          // System.out.println();
          // for(int i=0;i<wList.size();i++){
          //     System.out.print(wList.get(i)+" ");
          // }
          // System.out.println();
          System.out.println(dp[V]);
      }
  }
  ```


## 142.环形链表二（证明）

- 若有环，找到入环点

- Set

```java
    public ListNode detectCycle(ListNode head) {
        Set<ListNode>set=new HashSet<>();
        while (head!=null){
            if(set.contains(head)){
                return head;
            }
            set.add(head);
            head=head.next;
        }
        return null;
    }
```

- 快慢指针（官方
  - 一个走一步，一个走2步，有环且它们相遇时
  - 有了 a=c+(n-1)(b+c)的等量关系
  - 在用一个指针从起始点出发
  - 为什么一定会相交？
  - 相交点不一定是入环点

```java
public class Solution {
    public ListNode detectCycle(ListNode head) {
        if (head == null) {
            return null;
        }
        ListNode slow = head, fast = head;
        while (fast != null) {
            slow = slow.next;
            if (fast.next != null) {
                fast = fast.next.next;
            } else {
                return null;
            }
            if (fast == slow) {
                // 相交点不一定是入环点
                ListNode ptr = head;
                while (ptr != slow) {
                    ptr = ptr.next;
                    slow = slow.next;
                }
                return ptr;
            }
        }
        return null;
    }
}
```

# ==链表==

## 92.反转链表2

- 2

  ```java
  class Solution {
      public ListNode reverseBetween(ListNode head, int left, int right) {
          ListNode base=new ListNode(0);
          base.next=head;
          ListNode pre=base;
          for(int i=0;i<left-1;i++){
              pre=pre.next;// pre又有可能等于其他值了
          }
          ListNode tail=pre.next;
          ListNode next;
          // 头插法
          for(int i=0;i<right-left;i++){
              next=tail.next; // 尾节点的下一位
              tail.next=next.next; // 尾节点继续指向下一位
              // 把新的节点插入头
              next.next=pre.next;
              pre.next=next;
          }
          return base.next;
      }
  }
  ```


# ==字符串==

## 最长公共字串-dp

- 3

  ```java
  class Solution {
      public int longestCommonSubsequence(String text1, String text2) {
          int m = text1.length(), n = text2.length();
          int[][] dp = new int[m + 1][n + 1];
          for (int i = 1; i <= m; i++) {
              char c1 = text1.charAt(i - 1);
              for (int j = 1; j <= n; j++) {
                  char c2 = text2.charAt(j - 1);
                  if (c1 == c2) {
                      dp[i][j] = dp[i - 1][j - 1] + 1;
                  } else {
                      dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                  }
              }
          }
          return dp[m][n];
      }
  }
  ```

  

# ==树==

## ==数组树==

### 2440-dfs-创造价值相同的连通块

- 有一棵 n 个节点的无向树，节点编号为 0 到 n - 1 。

  给你一个长度为 n 下标从 0 开始的整数数组 nums ，其中 nums[i] 表示第 i 个节点的值。同时给你一个长度为 n - 1 的二维整数数组 edges ，其中 edges[i] = [ai, bi] 表示节点 ai 与 bi 之间有一条边。

  你可以 删除 一些边，将这棵树分成几个连通块。一个连通块的 价值 定义为这个连通块中 所有 节点 i 对应的 nums[i] 之和。

  你需要删除一些边，删除后得到的各个连通块的价值都相等。请返回你可以删除的边数 最多 为多少。

  **示例 1：**

  ![img](https://assets.leetcode.com/uploads/2022/08/26/diagramdrawio.png)

  

   输入：nums = [6,2,2,2,6], edges = [[0,1],[1,2],[1,3],[3,4]] 
  输出：2 
  解释：上图展示了我们可以删除边 [0,1] 和 [3,4] 。得到的连通块为 [0] ，[1,2,3] 和 [4] 。每个连通块的价值都为 6 。可以证明没有别的更好的删除方案存在了，所以答案为 2 。

  示例 2：

  输入：nums = [2], edges = []
  输出：0
  解释：没有任何边可以删除。

- dfs永远都是反向求值，然后汇总。所以本题能够求解到叶子节点，并用返回值0表示切割

  ```java
  import java.util.*;
  
  class Solution {
      //无向图
      Map<Integer, Set<Integer>> edgeMap=new HashMap<>();// 边：一个顶点和与它相连的顶点
      Map<Integer,Integer> vertexMap =new HashMap<>();//顶点以及价值
      public int componentValue(int[] nums, int[][] edges) {
          int sum=0; 
          initGraph(nums,edges);
          for(int val:nums) sum+=val;
          // 分的次数从1开始，那么删除的边就是最多了
          for(int i=1;i<=sum;i++){
              if(sum%i==0){
                  if(dfs(-1,0,i)==0) return sum/i-1;//初始节点不必是0，也可以 是其他。它的前节点设置为一个不存在的-1即可
              }
          }
          return 0;
      }
  
      /**
       * 顶点v1 v2加入子图，并且v2是由v1延伸出的
       * @return 0 可以划分  -1 划分失败  其他 待划分
       * dfs永远都是反向求值，然后汇总。
       * 所以本题通过v1->v2 v1引出v2，不断延伸，能够从叶子节点开始累加
       */
      private int dfs(int v1,int v2,int target){
          int s=vertexMap.get(v2);//s子树的价值和。
          // 对其还没有清算的所有子树进行累加
          for(int v:edgeMap.get(v2)){
              if(v==v1) continue; //不能反向延伸，先计算自己的子树
              int t=dfs(v2,v,target);//汇总其一子树的价值到当前节点
              if(t==-1) return -1; //子树已经失败
              s+=t; //若是叶子节点根本不会走进这个循环
          }
          if(s>target)return -1; //当前节点失败
          //上面的get(v2)表示重新赋值了
          return s==target?0:s;//将当前子树的总价值返回给父节点。等于target就返回成0，表示切割
      }
  
      private void initGraph(int []values,int [][]edges){
          for(int i=0;i<values.length;i++){
              vertexMap.put(i,values[i]);
              edgeMap.put(i,new HashSet<>());//先添加，只有一个顶点的图。否则uE.get(v2)会导致空指针异常
          }
          for(int []edge:edges){
              // 
              edgeMap.get(edge[0]).add(edge[1]);
              edgeMap.get(edge[1]).add(edge[0]);
          }
      }
  }
  ```

### 104.从前序与中序遍历构造二叉树

- 构造二叉树的这几题，都是从根节点开始

- 递归（官方
  - 前序确定根节点，中序确定子树边界
  - 要传一个边界值
  - 从下层构建并返回

```java
class Solution {
    Map<Integer,Integer>m1;
    int idx;
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        m1=new HashMap<>();
        int n=inorder.length;
        idx=0;
        for(int i=0;i<n;i++){
            m1.put(inorder[i],i);
        }
        return buildTree(preorder,0,n-1);
    }

    private TreeNode buildTree(int []pre,int l,int r){
        if(l>r){
            return null;
        }
        TreeNode rt=new TreeNode(pre[idx]);
        int mid=m1.get(pre[idx++]);
        rt.left=buildTree(pre,l,mid-1);
        rt.right=buildTree(pre,mid+1,r);
        return rt;
    }
}
```

### 105.从中序与后序遍历构造二叉树

- 递归(官方)
  - 后序确定根节点，中序确定边界
  - 同样思想

```java
class Solution {
    Map<Integer,Integer>m1;
    int idx;
    public TreeNode buildTree(int[] inorder, int[] postorder) {
        m1=new HashMap<>();
        int n=inorder.length;
        idx=n-1;
        for(int i=0;i<n;i++){
            m1.put(inorder[i],i);
        }
        return buildTree(postorder,0,n-1);
    }

    private TreeNode buildTree(int []post,int l,int r){
        if(l>r){
            return null;
        }
        TreeNode rt=new TreeNode(post[idx]);
        int mid=m1.get(post[idx--]);
        rt.right=buildTree(post,mid+1,r);
        rt.left=buildTree(post,l,mid-1);
        return rt;
    }
}
```

## ==引用树==

### 线段树

- 用来处理RMQ，比倍增数组还好理解吧

- 实现

  ```java
  public class Solution {
  
      /**
       * 二叉树
       * 二分区间，每个节点保存一个区间信息。
       */
  
     static class SegmentTree {
          private class TreeNode {
              int start, end;  // 区间左右
              int sum, min, max; // 区间特性：区间和、区间最值
              TreeNode left, right; // 要有左右子树
  
              TreeNode(int start, int end) {
                  this.start = start;
                  this.end = end;
                  this.sum = 0;
                  this.max = Integer.MIN_VALUE;
                  this.min = Integer.MAX_VALUE;
                  left = null;
                  right = null;
              }
          }
          private TreeNode root;
          public SegmentTree(int[] nums) {
              root = buildTree(nums, 0, nums.length - 1);
          }
          public TreeNode buildTree(int[] nums, int start, int end) {
              if (start > end) {
                  return null;
              }
              TreeNode node = new TreeNode(start, end);
              if (start == end) {
                  // 叶子节点
                  node.sum = nums[start];
                  node.max = nums[start];
                  node.min = nums[start];
              } else {
                  int mid = start + (end - start) / 2;
                  node.left = buildTree(nums, start, mid); //这里记得给左右子树赋值
                  node.right = buildTree(nums, mid + 1, end);
                  node.sum = node.left.sum + node.right.sum;
                  node.max = Math.max(node.left.max, node.right.max);
                  node.min = Math.min(node.left.min, node.right.min);
              }
              return node;
          }
          public int querySum(int start, int end) {
              return querySum(root, start, end);
          }
         // 每个都要跟当前节点的区间判断
          private int querySum(TreeNode node, int start, int end) {
              // 直接查到叶子节点。范围内不会导致空指针异常，放心
              if (start == node.start && end == node.end) {
                  return node.sum;
              }
              // 获取当初分的mid
              int mid = node.start + (node.end - node.start) / 2;
              // 左交叉、右交叉、包含
              if (end <= mid) {
                  return querySum(node.left, start, end);
              } else if (start >= mid + 1) {
                  return querySum(node.right, start, end);
              } else {
                  return querySum(node.left, start, mid) + querySum(node.right, mid + 1, end);
              }
          }
          public int queryMax(int start, int end) {
              return queryMax(root, start, end);
          }
          private int queryMax(TreeNode node, int start, int end) {
              if (node.start == start && node.end == end) {
                  return node.max;
              }
              int mid = node.start + (node.end - node.start) / 2;
              if (end <= mid) {
                  return queryMax(node.left, start, end);
              } else if (start >= mid + 1) {
                  return queryMax(node.right, start, end);
              } else {
                  return Math.max(queryMax(node.left, start, mid), queryMax(node.right, mid + 1, end));
              }
          }
          public int queryMin(int start, int end) {
              return queryMin(root, start, end);
          }
          private int queryMin(TreeNode node, int start, int end) {
              if (node.start == start && node.end == end) {
                  return node.min;
              }
              int mid = node.start + (node.end - node.start) / 2;
              if (end <= mid) {
                  return queryMin(node.left, start, end);
              } else if (start >= mid + 1) {
                  return queryMin(node.right, start, end);
              } else {
                  return Math.min(queryMin(node.left, start, mid), queryMin(node.right, mid + 1, end));
              }
          }
          public void update(int idx, int val) {
              update(root, idx, val);
          }
          // dfs
          private void update(TreeNode node, int idx, int val) {
              if (node.start == node.end) {  //其实这里也是等于idx了
                  // 更新节点节点
                  node.sum = val;
                  node.max = val;
                  node.min = val;
                  return;
              }
              // 因为有区间信息，所以直接根据idx更新.直接更新左右，
              // 只是一个点，没有交叉
              int mid = node.start + (node.end - node.start) / 2;
  
              if(idx<=mid){
                  update(node.left,idx,val);
              }else {
                  update(node.right,idx,val);
              }
              node.sum=node.left.sum+node.right.sum;
              node.max=Math.max(node.left.max,node.right.max);
              node.min=Math.min(node.left.min,node.right.min);
          }
          public static void main(String[] args) {
              int []nums=new int[]{4,2,7,1,5,8,3};
              SegmentTree segmentTree=new SegmentTree(nums);
  
              int start=0;
              int end=4;
              // 目前只支持范围内查询，超出范围的会报异常。可限制
              // start=Math.max(0,start);
              // end=Math.min(nums.length-1, end);
              int sum=segmentTree.querySum(start,end);
              int max=segmentTree.queryMax(start,end);
              int min=segmentTree.queryMin(start,end);
              System.out.println("sum "+sum);
              System.out.println("max "+max);
              System.out.println("min "+min);
              segmentTree.update(0,10);
              segmentTree.update(1,1);
              sum=segmentTree.querySum(start,end);
              max= segmentTree.queryMax(start,end);
              min= segmentTree.queryMin(start,end);
              System.out.println();
              System.out.println("sum "+sum);
              System.out.println("max "+max);
              System.out.println("min "+min);
              /**
              sum 19
  			max 7
  			min 1
  
  			sum 24
  			max 10
  			min 1
              **/
          }
      }
  }
  
  
  ```

### 并查集

- 森林（多棵树）

  - 一个父节点的HashMap
  - 一个表示秩的HashMap
  
  ```java
      // 这里的节点暂定为int类型，且不存在相同值的节点。若是想要更多的操作，那就自己定义节点类
      // 求根节点和合并两个集合都用到了路径压缩
      static class DisjiontSet{
  
          Map<Integer,Integer>parent=new HashMap<>(); // 节点的父节点
          Map<Integer,Integer>rank=new HashMap<>(); // 秩。约等于到父节点的距离。合并时用到，调整树高
  
          // 初始化节点集合
          public void initSet(int []nodes){
              for(int node:nodes){
                  parent.put(node,node);
                  rank.put(node,0);
              }
          }
  
          // 求根节点
          public int findRoot(int node){
              if(parent.get(node)==node){
                  return node;
              }
  
              int root=findRoot(parent.get(node));
              // 压缩路径为1
              parent.put(node,root);
              return root;
          }
  
          // 合并两个节点所在的集合
          public void union(int node1,int node2 ){
              int root1=findRoot(node1);
              int root2=findRoot(node2);
  
              if(root1==root2){
                  return;
              }else {
                  if(rank.get(root1) < rank.get(root2)){
                      // node1的路径比node2短,这里不进行路径压缩
                      parent.put(root2,root1);
                  }else if(rank.get(root1)>rank.get(root2)){
                      parent.put(root1,root2);
                  }else {
                      parent.put(root1,root2);
                      rank.put(root1,rank.get(root2)+1);
                  }
              }
          }
          // 判断两个节点是否在同一个集合
          public boolean isConnected(int node1,int node2){
              return findRoot(node1)==findRoot(node2);
          }
      }
  
      public static void main(String[] args) {
          DisjiontSet disjiontSet=new DisjiontSet();
          int []nodes =new int[]{1,2,3,4,5,6};
          disjiontSet.initSet(nodes);
  
          disjiontSet.union(1,2);
          disjiontSet.union(3,4);
          disjiontSet.union(5,6);
          disjiontSet.union(2,3);
  
          System.out.println(disjiontSet.isConnected(1,2)); // true
          System.out.println(disjiontSet.isConnected(1,4)); // true
          System.out.println(disjiontSet.isConnected(4,5)); // false
          System.out.println(disjiontSet.isConnected(1,6)); // false
  
      }
  ```

# ==图论==

## ==最短路径==

### 迪杰斯特拉算法

- 源点到其他所有节点。

- 每次从 V-S 集合中取出距离源点最近的顶点 u 加入到 S 集合中，然后更新从源点经过 u 到达各个顶点的距离

  ```java
  import java.util.*;
  
  public class DijkstraAlgorithm {
      public static void dijkstra(int[][] graph, int source) {
          int numVertices = graph.length;
          int[] distances = new int[numVertices];
          boolean[] visited = new boolean[numVertices];
  
          // 初始化距离数组
          Arrays.fill(distances, Integer.MAX_VALUE);
          distances[source] = 0;
  
          // 重复选择最近的节点并更新最短距离的过程
          for (int i = 0; i < numVertices - 1; i++) {
              int closestVertex = findClosestVertex(distances, visited);
              visited[closestVertex] = true;
  
              for (int v = 0; v < numVertices; v++) {
                  // 用这个最近节点去更新所有的未访问的jiedian 路径，因为可能有重复指向该节点的
                  // 要加很多前置判断
                  // 用于更新经过当前最接近节点的路径后，到达其他未访问节点的最短距离
                  // distances[v] 起始节点到v节点的距离
                  // distances[closestVertex] + graph[closestVertex][v] 起始节点通过新路径到达v的距离
                  if (!visited[v] && graph[closestVertex][v] != 0 && 
                      distances[closestVertex] != Integer.MAX_VALUE && 
                      distances[closestVertex] + graph[closestVertex][v] < distances[v]) {
                      
                      distances[v] = distances[closestVertex] + graph[closestVertex][v];
                  }
              }
          }
  
          // 输出最短距离
          for (int i = 0; i < numVertices; i++) {
              System.out.println("到顶点 " + i + " 的最短距离为 " + distances[i]);
          }
      }
  
      // 寻找距离最近且未访问的顶点。第一步就是源点，distances[]已经更新，但是还没有访问。
      // 后续其他的v可以因为这句话而更新。distances[v] = distances[closestVertex] + graph[closestVertex][v];
      private static int findClosestVertex(int[] distances, boolean[] visited) {
          int minDistance = Integer.MAX_VALUE;
          int closestVertex = -1;
  
          for (int i = 0; i < distances.length; i++) {
              // 要没有访问过的
              if (!visited[i] && distances[i] < minDistance) {
                  minDistance = distances[i];
                  closestVertex = i;
              }
          }
  
          return closestVertex;
      }
  
      public static void main(String[] args) {
          int[][] graph = {
              {0, 4, 0, 0, 0, 0, 0, 8, 0},
              {4, 0, 8, 0, 0, 0, 0, 11, 0},
              {0, 8, 0, 7, 0, 4, 0, 0, 2},
              {0, 0, 7, 0, 9, 14, 0, 0, 0},
              {0, 0, 0, 9, 0, 10, 0, 0, 0},
              {0, 0, 4, 14, 10, 0, 2, 0, 0},
              {0, 0, 0, 0, 0, 2, 0, 1, 6},
              {8, 11, 0, 0, 0, 0, 1, 0, 7},
              {0, 0, 2, 0, 0, 0, 6, 7, 0}
          };
  
          int source = 0;  // 源节点
          dijkstra(graph, source);
      }
  }
  ```

### Bellman-Ford算法

- dp

  - 先找长度为1的边，然后找后续的
  - 这个更加简单
  
  ```java
  import java.util.*;
  
  public class BellmanFordAlgorithm {
      public static void bellmanFord(int[][] graph, int source) {
          int numVertices = graph.length;
          int[] distances = new int[numVertices];
          
          // 初始化距离数组
          Arrays.fill(distances, Integer.MAX_VALUE);
          distances[source] = 0;
  
          // 迭代更新距离的过程
          // i表示路径长度，3个for都是从0开始
          // i从0到n？感觉多了一步，是求n条边的路径，下面可以用这个判断来判断负回路？好像 也不是。。。
          for (int i = 0; i < numVertices - 1; i++) {
              for (int u = 0; u < numVertices; u++) {
                  for (int v = 0; v < numVertices; v++) {
                      // 多了这个限制就可以用int distances[u] != Integer.MAX_VALUE
                      if (graph[u][v] != 0 && distances[u] != Integer.MAX_VALUE && 
                          distances[u] + graph[u][v] < distances[v]) {
                             
                          distances[v] = distances[u] + graph[u][v];
                      }
                  }
              }
          }
  
          // 检测负权回路.再执行一次，若是还能变小则有负回路
          for (int u = 0; u < numVertices; u++) {
              for (int v = 0; v < numVertices; v++) {
                  if (graph[u][v] != 0 && distances[u] != Integer.MAX_VALUE && 
                      distances[u] + graph[u][v] < distances[v]) {
                          
                      System.out.println("图中存在负权回路");
                      return;
                  }
              }
          }
  
          // 输出最短距离
          for (int i = 0; i < numVertices; i++) {
              System.out.println("到顶点 " + i + " 的最短距离为 " + distances[i]);
          }
      }
  
      public static void main(String[] args) {
          int[][] graph = {
              {0, 6, 0, 0, 0},
              {0, 0, 5, -4, 0},
              {0, -2, 0, 0, -1},
              {0, 0, -2, 0, 1},
              {2, 0, 0, 0, 0}
          };
  
          int source = 0;  // 源节点
          bellmanFord(graph, source);
      }
  }
  ```

## ==最小生成树==

### 克鲁斯卡尔算法

- 在E中选择代价最小的边，若该边依附的顶点分别在T中不同的连通分量上，则将此边加入到T中；否则，舍去此边而选择下一条代价最小的边。每次选最小的边，这个最小边要连接访问和未访问集合

- 对边权重进行排序，不用找最小。

  - 并查集看有没有回路：新加入的边的起始点是否在一个集合
  
  ```java
  import java.util.*;
  
  class Edge implements Comparable<Edge> {
      int src;  // 边的起始节点
      int dest;  // 边的目标节点
      int weight;  // 边的权重
  
      public Edge(int src, int dest, int weight) {
          this.src = src;
          this.dest = dest;
          this.weight = weight;
      }
  
      @Override
      public int compareTo(Edge edge) {
          return this.weight - edge.weight;
      }
  }
  
  public class KruskalAlgorithm {
      private static int numVertices;  // 图中顶点的数目
  
      public static int[][] kruskal(int numVertices, List<Edge> edges) {
          int[][] minimumSpanningTree = new int[numVertices - 1][2];  // 存储最小生成树的边
          int[] parent = new int[numVertices];  // 存储顶点的父节点
          int count = 0;  // 计数器，用于记录加入最小生成树的边数
  
          // 初始化parent数组
          for (int i = 0; i < numVertices; i++) {
              parent[i] = i;
          }
  
          // 对边进行权重排序，不用找最小边
          Collections.sort(edges);
  
          // 遍历边   
          for (int i = 0; i < edges.size(); i++) {
              if (count == numVertices - 1) {
                  break;
              }
  
              Edge edge = edges.get(i);
              int srcParent = findParent(parent, edge.src);
              int destParent = findParent(parent, edge.dest);
  
              // 判断边的起始节点和目标节点是否在不同的父节点下，避免形成环路
              if (srcParent != destParent) {
                  minimumSpanningTree[count][0] = edge.src;
                  minimumSpanningTree[count][1] = edge.dest;
                  count++;
  
                  // 合并起始节点和目标节点的父节点
                  union(parent, srcParent, destParent);
              }
          }
  
          return minimumSpanningTree;
      }
  
      // 查找顶点的根节点
      private static int findParent(int[] parent, int vertex) {
          if (parent[vertex] != vertex) {
              parent[vertex] = findParent(parent, parent[vertex]);
          }
          return parent[vertex];
      }
  
      // 合并两个集合的根节点
      private static void union(int[] parent, int x, int y) {
          parent[x] = y;
      }
  
      public static void main(String[] args) {
          numVertices = 4;  // 顶点的数目
  
          List<Edge> edges = new ArrayList<>();  // 存储边
  
          edges.add(new Edge(0, 1, 10));  // 添加边的起始节点、目标节点和权重
          edges.add(new Edge(0, 2, 6));
          edges.add(new Edge(0, 3, 5));
          edges.add(new Edge(1, 3, 15));
          edges.add(new Edge(2, 3, 4));
  
          int[][] minimumSpanningTree = kruskal(numVertices, edges);
  
          // 输出最小生成树的边
          for (int i = 0; i < minimumSpanningTree.length; i++) {
              System.out.println(minimumSpanningTree[i][0] + " - " + minimumSpanningTree[i][1]);
          }
      }
  }
  ```


### 普里姆算法

- 普里姆算法是从某一端点出发，形成两个集合：已选节点集合，未选节点集合。从已选集合找到一条到未选集合的最小权值边，并把连接的端点划为已选集合。重复这样的操作，直到所有节点都在已选集合时完成操作

- 因为是从0开始，所以能够保证连通性

  - 不用dfs，直接用访问数组判断就可以了。
  
  ```java
  import java.util.*;
  
  public class PrimAlgorithm {
      private static int numVertices;  // 图中顶点的数目
  
      public static int[][] prim(int numVertices, int[][] graph) {
          int[][] minimumSpanningTree = new int[numVertices - 1][2];  // 存储最小生成树的边
          boolean[] visited = new boolean[numVertices];  // 记录顶点是否被访问过
          int count = 0;  // 计数器，用于记录加入最小生成树的边数
  
          // 将起始顶点标记为已访问
          visited[0] = true;
  
          // 遍历n-1个顶点
          while (count < numVertices - 1) {
              int minWeight = Integer.MAX_VALUE;  // 记录当前访问到的最小边的权重
              int src = -1, dest = -1;  // 记录最小边的起始节点和目标节点
  
              // 遍历已访问顶点和未访问顶点的边，找到权重最小的边
              for (int i = 0; i < numVertices; i++) {
                  if (visited[i]) {
                      for (int j = 0; j < numVertices; j++) {
                          if (!visited[j] && graph[i][j] != 0 && graph[i][j] < minWeight) {
                              minWeight = graph[i][j];
                              src = i;
                              dest = j;
                          }
                      }
                  }
              }
  
              // 将目标节点标记为已访问
              visited[dest] = true;
  
              minimumSpanningTree[count][0] = src;
              minimumSpanningTree[count][1] = dest;
              count++;
          }
  
          return minimumSpanningTree;
      }
  
      public static void main(String[] args) {
          numVertices = 4;  // 顶点的数目
  
          int[][] graph = {
              {0, 10, 6, 5},
              {10, 0, 0, 15},
              {6, 0, 0, 4},
              {5, 15, 4, 0}
          };
  
          int[][] minimumSpanningTree = prim(numVertices, graph);
  
          // 输出最小生成树的边
          for (int i = 0; i < minimumSpanningTree.length; i++) {
              System.out.println(minimumSpanningTree[i][0] + " - " + minimumSpanningTree[i][1]);
          }
      }
}
  ```
  
  

## ==二维图==

### 1254-BFS+DFS-统计封闭岛屿的数目

- 解法

  - DFS把外围的0变为1，再DFS求里面的

  ```java
  class Solution {
      int[][]dirs=new int[][]{{1,0},{-1,0},{0,1},{0,-1}};
      public int closedIsland(int[][] grid) {
          int n=grid.length,m=grid[0].length;
          for(int i=0;i<n;i++){
              dfs(i,0,grid);
              dfs(i,m-1,grid);
          }
          for(int i=1;i<m-1;i++){
              dfs(0,i,grid);
              dfs(n-1,i,grid);
          }
          int ans=0;
          for(int i=1;i<n-1;i++){
              for(int j=1;j<m-1;j++){
                  if(dfs(i,j,grid)){
                      ans++;
                  }
              }
          }
          return ans;
      }
      
      private boolean dfs(int x,int y,int[][]grid){
          if(grid[x][y]!=0){
              return false;
          }
          grid[x][y]=1;//把外围的0设置为1
          for(int[]dir:dirs){
              int nx=x+dir[0],ny=y+dir[1];
              if(nx>=0 && nx<grid.length && ny>=0 && ny<grid[0].length){
                  dfs(nx,ny,grid);
              }
          }
          return true;
      }
  }
  ```


### 拓扑排序

- 一种基于有向无环图（DAG）的节点排序算法，它可以对 DAG 中所有节点进行排序，使得所有的前驱节点排在后继节点前面

  ```java
  import java.util.*;
  
  public class TopologicalSort {
      public static List<Integer> sort(int numCourses, int[][] prerequisites) {
          // 创建邻接表和入度数组
          List<List<Integer>> adj = new ArrayList<>();
          int[] indegree = new int[numCourses];
          for (int i = 0; i < numCourses; i++) {
              adj.add(new ArrayList<Integer>());
          }
          // 遍历有向边数组，更新邻接表和入度数组
          for (int[] edge : prerequisites) {
              adj.get(edge[1]).add(edge[0]); // 添加有向边
              indegree[edge[0]]++; // 更新入度数组
          }
          // 将所有入度为 0 的节点入队
          Queue<Integer> q = new LinkedList<>();
          for (int i = 0; i < numCourses; i++) {
              if (indegree[i] == 0) {
                  q.add(i);
              }
          }   
          // 定义结果列表
          List<Integer> res = new ArrayList<>();
          while (!q.isEmpty()) {
              // 从队首出队
              int node = q.poll();
              res.add(node); // 加入结果列表
              // 遍历邻居节点，更新其入度
              for (int nei : adj.get(node)) {
                  indegree[nei]--;
                  if (indegree[nei] == 0) { // 如果入度为 0，入队
                      q.add(nei);
                  }
              }
          }
          // 如果结果列表大小和节点数量不同，说明有环，返回空列表
          // 少于numCourses。因为有环就不在有入度为0的点入队列
          if (res.size() != numCourses) {
              return new ArrayList<Integer>();
          }
          return res; // 否则返回拓扑排序结果列表
      }
      
      public static void main(String[] args) {
          int numCourses = 4; // 节点数
          int[][] prerequisites = {{1,0},{2,0},{3,1},{3,2}};
          List<Integer> res = sort(numCourses, prerequisites);
          System.out.println(res.toString());
      }
  }
  ```

### 判断是否是拓扑排序结果

- 不用队列。直接重走一遍，入度为0加入新结果。判断新结果和旧结果是否一致

  ```java
  public boolean isValidTopologicalSort(int numCourses, int[][] prerequisites, List<Integer> result) {
      // 创建邻接表和入度数组 。图的基本信息还是要的
      List<List<Integer>> adj = new ArrayList<>();
      int[] indegree = new int[numCourses];
      for (int i = 0; i < numCourses; i++) {
          adj.add(new ArrayList<Integer>());
      }
      // 遍历有向边数组，更新邻接表和入度数组
      for (int[] edge : prerequisites) {
          adj.get(edge[1]).add(edge[0]); // 添加有向边
          indegree[edge[0]]++; // 更新入度数组
      }
  
      // 克隆一份结果列表用于判断
      List<Integer> cloneResult = new ArrayList<>(result);
    
      // 判断结果列表的长度是否等于节点数量
      if (cloneResult.size() != numCourses) {
          return false;
      }
  
      // 根据原序列的顺序来减少入度，为0则加入新结果集
      // 判断结果列表中节点顺序是否满足拓扑排序的定义
      for (int node : cloneResult) {
          // 遍历邻居节点，更新其入度
          for (int nei : adj.get(node)) {
              indegree[nei]--;
              if (indegree[nei] == 0) { // 如果入度为 0，加入结果列表
                  cloneResult.add(nei);
              }
          }
      }
    
      // 如果新结果集的顺序与 cloneResult 不一致，则不是有效的拓扑排序结果
      return result.equals(cloneResult);   
  }
  ```
## ==网状图==

# ==基础==

## ==排序==

### 冒泡排序（Bubble Sort）

- 通过相邻元素的比较和交换，每次将最大的元素“冒泡”到数组末尾。

- 时间复杂度为 O(n^2)。 O(1) 的额外空间
```
public static void bubbleSort(int[] arr) {
      int n = arr.length;
      for (int i = 0; i < n - 1; i++) {
          for (int j = 0; j < n - i - 1; j++) {
              if (arr[j] > arr[j+1]) {
                  // Swap arr[j] and arr[j+1]
                  int temp = arr[j];
                  arr[j] = arr[j+1];
                  arr[j+1] = temp;
              }
          }
      }
  }
```

  

### 选择排序（Selection Sort）

- 每次从未排序的元素中选择最小的元素放到已排序部分的末尾。

- 时间复杂度为 O(n^2)。O(1) 的额外空间

  ```java
  public static void selectionSort(int[] arr) {
      int n = arr.length;
      for (int i = 0; i < n - 1; i++) {
          // Find the minimum element in unsorted part of array
          int minIdx = i;
          for (int j = i+1; j < n; j++) {
              if (arr[j] < arr[minIdx]) {
                  minIdx = j;
              }
          }
          // Swap the minimum element with the first element of unsorted part
          int temp = arr[minIdx];
          arr[minIdx] = arr[i];
          arr[i] = temp;
      }
  }
  ```

  

### 插入排序（Insertion Sort）

- 将一个元素插入到已排序部分的正确位置，直到所有元素都排序完毕。

- 时间复杂度为 O(n^2)。O(1) 的额外空间

  ```java
  public static void insertionSort(int[] arr) {
      int n = arr.length;
      for (int i = 1; i < n; i++) {
          int key = arr[i];
          int j = i - 1;
          // Move elements of arr[0...i-1] that are greater than key, to one position ahead of their current position
          while (j >= 0 && arr[j] > key) {
              arr[j+1] = arr[j];
              j--;
          }
          arr[j+1] = key;
      }
  }
  ```

  

### 快速排序(Quick Sort)

- 快速排序（Quick Sort）：通过一次分区操作将数组分成小于主元和大于主元的两部分，然后递归地对两部分进行排序。

- 时间复杂度平均为 O(nlogn),好情况下时间复杂度为 O(nlogn)，最坏情况下时间复杂度为 O(n^2)。O(logn) 的额外空间（用于递归调用栈）

- 是一种高效的排序算法，时间复杂度的最坏情况下为 O(n^2)，但一般情况下为 O(n log n)，它通常比其他排序算法（如冒泡排序、选择排序）快得多，因此被广泛应用。

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



### 归并排序（Merge Sort）

- 将数组分成若干个子序列，每个子序列都是有序的，然后递归地将子序列合并成一个有序的序列。

- 时间复杂度为 O(nlogn)。O(n) 的额外空间（用于临时存储已排序的元素）

- 归并排序是一种高效的算法，在排序的应用如排序算法教学、多路归并、归并排序等领域都得到了很好的应用。

  ```java
  public static void mergeSort(int[] arr, int left, int right) {
      if (left < right) {
          // Find the middle point to divide the array into two parts
          int mid = (left + right) / 2;
  
          // Recursively sort the left and right parts of the array
          mergeSort(arr, left, mid);
          mergeSort(arr, mid + 1, right);
  
          // Merge the sorted left and right parts of the array
          merge(arr, left, mid, right);
      }
  }
  
  // 就像合并两个链表一样
  private static void merge(int[] arr, int left, int mid, int right) {
      // Calculate the lengths of the two parts to be merged
      int n1 = mid - left + 1;
      int n2 = right - mid;
  
      // Create temporary arrays for the two parts
      int[] L = new int[n1];
      int[] R = new int[n2];
  
      // Copy data to the temporary arrays
      for (int i = 0; i < n1; i++) {
          L[i] = arr[left + i];
      }
      for (int j = 0; j < n2; j++) {
          R[j] = arr[mid + 1 + j];
      }
  
      // Merge the two temporary arrays 公共长度部分
      int i = 0, j = 0;
      int k = left;
      while (i < n1 && j < n2) {
          if (L[i] < R[j]) {
              arr[k] = L[i];
              i++;
          } else {
              arr[k] = R[j];
              j++;
          }
          k++;
      }
  
      // Copy the remaining elements of L[], if there are any  
      while (i < n1) {
          arr[k] = L[i];
          i++;
          k++;
      }
  
      // Copy the remaining elements of R[], if there are any
      while (j < n2) {
          arr[k] = R[j];
          j++;
          k++;
      }
  }
  ```

### 希尔排序（Shell Sort）

- 将数据分组，对每组使用插入排序算法，随着排序的进行减少分组的数量。

- 时间复杂度介于 O(nlogn) 和 O(n^2) 之间。O(1) 的额外空间

- 虽然希尔排序比起快速排序、归并排序和堆排序，其排序速度不是最优的，但是它的平均性能是具有保障的。希尔排序通常在其他算法无法胜任的场景下使用，被认为是一种比较实用的算法。

  ```java
  public static void shellSort(int[] arr) {
      int n = arr.length;
      // Initialize gap as n/2, n/4, n/8, ..., 1
      // gap是幅度
      for (int gap = n / 2; gap > 0; gap /= 2) {
          // Do a gapped insertion sort for this gap size.
          for (int i = gap; i < n; i++) {
              int temp = arr[i];
              // 减少幅度并交换而已。没有其他操作了
              for (int j = i; j >= gap && arr[j - gap] > temp; j -= gap) {
                  arr[j] = arr[j - gap];
              }
              arr[j] = temp;
          }
      }
  }
  ```

- 过程

  - ![image-20230622173634031](C:\Users\beilinanju\AppData\Roaming\Typora\typora-user-images\image-20230622173634031.png)
  - ![image-20230622173658129](C:\Users\beilinanju\AppData\Roaming\Typora\typora-user-images\image-20230622173658129.png)

### 堆排序（Heap Sort）

- 通过将数组看成一颗完全二叉树，并将其构建成一个大根堆，每次取出根节点（最大值），并将其放到数组末尾。

- 时间复杂度为 O(nlogn)。O(1) 的额外空间

- 既具有良好的空间特性，也具有一定的运行速度。同时，堆排序算法中存在缓存命中问题，因此对于较大的数据集，堆排序的性能甚至能够超越其他基于比较排序算法如归并排序、快速排序等。

- 小堆，节点小于其左右。大堆，节点大于其左右。从左到右，从上到下，顺序构建即可

  ```java
  public static void heapSort(int[] arr) {
      int n = arr.length;
  
      // Build max heap (rearrange array)
      for (int i = n / 2 - 1; i >= 0; i--)
          heapify(arr, n, i);
  
      // One by one extract an element from heap
      for (int i = n - 1; i >= 0; i--) {
          // Move current root to end
          int temp = arr[0];
          arr[0] = arr[i];
          arr[i] = temp;
  
          // call max heapify on the reduced heap
          heapify(arr, i, 0);
      }
  }
  
  private static void heapify(int[] arr, int n, int i) {
      int largest = i;  // Initialize largest as root
      int left = 2*i + 1;  // left = 2*i + 1
      int right = 2*i + 2;  // right = 2*i + 2
  
      // If left child is larger than root
      if (left < n && arr[left] > arr[largest])
          largest = left;
  
      // If right child is larger than largest so far
      if (right < n && arr[right] > arr[largest])
          largest = right;
  
      // If largest is not root
      if (largest != i) {
          int swap = arr[i];
          arr[i] = arr[largest];
          arr[largest] = swap;
  
          // Recursively heapify the affected sub-tree
          heapify(arr, n, largest);
      }
  }
  ```

  

### 计数排序（Counting Sort）

- 通过统计元素出现的次数，然后进行排序。

- 时间复杂度为 O(n+k)，其中 n 表示元素个数，k 表示元素值的范围。O(k) 的额外空间，其中 k 表示元素值得范围

- 在数据比较密集的场景下有着较好的性能表现。需要注意的是，使用计数排序算法进行排序时，输入序列必须为整数，并且整数应当为非负数。同时，计数排序算法中使用了一定的额外空间，因此它的空间复杂度为 O(k)。

- 映射排序，上面是7个比较排序

  ```java
  public static void countSort(int[] arr) {
      int n = arr.length;
  
      // Find the maximum element in the array
      int max = 0;
      for (int x: arr)
          max = Math.max(max, x);
  
      // Create a count array to store count of individual elements
      int[] count = new int[max+1];
  
      // Store count of each element
      for (int x: arr)
          count[x]++;
  
      // Modify the count array such that it contains actual position of each element in sorted array
      for (int i = 1; i <= max; i++)
          count[i] += count[i-1];   //对所有值进行计数累加
  
      // Create a temporary output array
      int[] output = new int[n];
  
      // Build the output array
      for (int i = n-1; i >= 0; i--) {
          output[count[arr[i]]-1] = arr[i];
          count[arr[i]]--;
      }
  
      // Copy the output array to original array
      System.arraycopy(output, 0, arr, 0, n);
  }
  ```

  

### 桶排序（Bucket Sort）

- 将数据分到有限数量的桶中，再对每个桶进行排序。

- 时间复杂度为 O(n)。O(n+k) 的额外空间，其中 k 表示桶的数量

- 如果数据分布相对均匀，可以设置数量合适的桶，从而达到线性时间复杂度，但是如果数据分布不均，使得所有数据都落入同一个桶中，此时桶排序的效率将会变得非常低。同时，桶排序算法中对内存的额外开销也要留意。

- 映射排序

  ```java
  public static void bucketSort(int[] arr) {
      int n = arr.length;
      int bucketSize = 10; // 每个桶的大小，可以根据数据情况进行调整
  
       // 桶的个数有限，但是桶是ArrayList()，本身大小无限
      // Find maximum and minimum value in the array
      int max = arr[0], min = arr[0];
      for (int i = 1; i < n; i++) {
          max = Math.max(max, arr[i]);
          min = Math.min(min, arr[i]);
      }
  
      // Create buckets
      int bucketCount = (max - min) / bucketSize + 1;
      List<List<Integer>> buckets = new ArrayList<>(bucketCount);
      for (int i = 0; i < bucketCount; i++)
          buckets.add(new ArrayList<>());
  
      // Assign elements to different buckets
      for (int i = 0; i < n; i++) {
          int index = (arr[i] - min) / bucketSize;//这里的下标是小的数映射到小号的桶
          buckets.get(index).add(arr[i]);
      }
  
      // Sort elements in each bucket
      for (int i = 0; i < bucketCount; i++) {
          List<Integer> bucket = buckets.get(i);
          Collections.sort(bucket);
          buckets.set(i, bucket);
      }
  
      // Merge elements from all sorted buckets to the original array
      int index = 0;
      for (List<Integer> bucket : buckets)
          for (int value : bucket)
              arr[index++] = value;
  \\}
  ```

  

### 基数排序（Radix Sort）

- 通过将元素逐位比较进行排序，一般用于多关键字排序
- 时间复杂度为 O(d(n+r))，其中 d 表示数字位数，r 表示每个数字的取值范围。 O(n+r) 的额外空间，其中 r 表示进制数
- 由于常数项大，所以基数排序的实际时间复杂度很高，不适合处理大规模的数据。但是在处理小规模数据时，基数排序可以具有相当高的性能。同时基数排序算法中对内存的额外开销也要留意。

## ==二分==

### 二分查找

- 升序整型数组 nums 和 target  ,返回下标
- low<=high   mid一加一减

```java
 public int search(int[] nums, int target) {
        int low=0;
        int high=nums.length-1;
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

### 二分插入（可结合排序）

- 给定一个排序数组和一个目标值，在数组中找到目标值，并返回其索引。如果目标值不存在于数组中，返回它将会被按顺序插入的位置。
- 右边第一个满足
  - 分界点可以用low<high    low+1

```java
public int searchInsert(int[] nums, int target) {
        int l=0;
        int r=nums.length; // 因为插入可能在最后一个节点插入
        while(l<r){
            int mid=l+((r-l)>>1);
            if(nums[mid]>=target){
                // 右边的判断条件是大于等于
                r=mid;
            }else{
                // 左边不断递进
                l=mid+1;
            }
        }
        return l;//low收缩到等于target（等于时也是最左那个）或第一个大于target的数
    }
```

- 左边最后一个满足

  - 收缩为r
  - leetcode1802

  ```java
  public int searchInsert(int[] nums, int target) {
          int l=0;
          int r=nums.length;
          while(l<r){
              int mid=l+(r-l+1)/2;//这里要加一
              if(check(nums[i])){
                  l=mid;
              }else{
                  r=mid-1;
              }
          }
          return r;//low收缩到等于target（等于时也是最左那个）或第一个大于target的数
      }
  ```


## ==排列(位置不同即不同）==

### 有重复

- 给定一个可包含重复数字的序列 `nums` ，***按任意顺序*** 返回所有不重复的全排列
- 回溯复原，并去重
- 时间复杂度n！。14！就870亿，非常大。可以其他算法如状压dp

```java
    List<List<Integer>>res=new ArrayList<>();
    List<Integer>list=new ArrayList<>();

    boolean[]v;

    public List<List<Integer>> permuteUnique(int[] nums) {
        Arrays.sort(nums);
        v=new boolean[nums.length];
        getRes(nums,0);
        return res;
    }

    private void getRes(int [] nums,int n){
        if(n==nums.length){
            //排列是全都要。结果一定是在最后处理
            //v[]全是true，并且结果只是顺序不同
            res.add(new ArrayList<>(list));
            return;
        }

        //所有未访问的都要访问
        for(int i=0;i<nums.length;i++){
            //去重[1,1,2]   问题：先拿nums[0]再nums[1]和先nums[1]再nums[0]重复了
            //思路，这样只能够先nums[1]再nums[0]
            if(i>0 && v[i-1] &&  nums[i]==nums[i-1]){ //去重
                continue;
            }
       
            if(!v[i]){
                //每次选一个放在末尾，并用v[i]记录已经访问。要用list，因为排列只是顺序不同，但要用到所有元素
                v[i]=true;
                list.add(nums[i]);
                getRes(nums,n+1);
                list.remove(list.size()-1);
                v[i]=false;
            }
        }
    }
```

- show

  ```java
  input:[1,1,2]
  output:[[1,1,2],[1,2,1],[2,1,1]]
  [1,1,1,2] 同理，拿到的相同元素的个数不会和上次的重复
  ```
## ==子集（原集合选出任意个）==

- 可重复，也可去重

### 90.子集二

- 给你一个整数数组 `nums` ，其中可能包含重复元素，请你返回该数组所有可能的子集（幂集），不能重复
- 回溯
  - 去重时一定是i>idx，当前层相同的只取一个
  - dfs(nums,i+1);  跳到下一层时一定是i+1，不能是idx+1
  - 谨记 谨记
  - 也是从idx开始

```java
class Solution {
    List<Integer>list;
    List<List<Integer>> ans;
    public List<List<Integer>> subsetsWithDup(int[] nums) {
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
            // 去重
            if(i>idx && nums[i]==nums[i-1]){
                continue;
            }
            list.add(nums[i]);
            dfs(nums,i+1);
            list.remove(list.size()-1);
        }
    }
}
```

- 2的n状态

  - 666
  - List有序，Set会对List天然去重

  ```java
  import java.util.*;
  
  class Solution {
      // 回溯
      public List<List<Integer>> subsetsWithDup(int[] nums) {
          Arrays.sort(nums);
          Set<List<Integer>>ans=new HashSet<>();
          List<Integer>temp=new ArrayList<>();
          int n=nums.length;
          //i从0加到1<<n，覆盖了所有可能
          for(int i=0;i<(1<<n);i++){
              temp.clear();
              for(int j=0;j<n;j++){
                  //看i的每个位是否是1
                  //i右移j位，与1
                  if(((i>>j)&1)==1){
                      temp.add(nums[j]);
                  }
              }
              ans.add(new ArrayList<>(temp));
          }
          return new ArrayList<>(ans);
      }
  
  
  }
  ```

  

### 40.组合总和 II（dfs+剪枝+去重）

难度中等1313

给定一个候选人编号的集合 `candidates` 和一个目标数 `target` ，找出 `candidates` 中所有可以使数字和为 `target` 的组合。

`candidates` 中的每个数字在每个组合中只能使用 **一次** 。

**注意：**解集不能包含重复的组合。 

**示例 1:**

```
输入: candidates = [10,1,2,7,6,1,5], target = 8,
输出:
[
[1,1,6],
[1,2,5],
[1,7],
[2,6]
]
```

**示例 2:**

```
输入: candidates = [2,5,2,1,2], target = 5,
输出:
[
[1,2,2],
[5]
]
```

- 不是我写的，我只能说很妙

  - 不是排列
  - 这种选数的直接从一个ｉｄｘ开始即可

  ```java
  class Solution {
      //排序+剪枝
      int []a;
      int n;
      List<Integer> path=new ArrayList<>();
      List<List<Integer>>ans=new ArrayList<>();
      public List<List<Integer>> combinationSum2(int[] candidates, int target) {
          a=candidates;
          n=candidates.length;
          Arrays.sort(a);//排序
          dfs(0,target);
          return ans;
      }
  
      //在当前层进行剪枝
      private void dfs(int dept,int tar){
          if(tar==0){
              ans.add(new ArrayList<>(path));
              return;
          }
          for(int i=dept;i<n;i++){
              //排序，后面的更大
              if(tar-a[i]<0){
                  break;
              }
              //当前层只取一个相同的，下一个相同的留到下一层
              //就是普通的去重而已
              if(i>dept && a[i]==a[i-1]){
                  continue;
              }
              path.add(a[i]);
              //这里对i进行dfs，因为前面的都用过了
              dfs(i+1,tar-a[i]);
              path.remove(path.size()-1);
          }
      }
  }
  ```



## ==动态规划==

### ==一维==

#### 第n个丑数

- 给你一个整数 `n` ，请你找出并返回第 `n` 个 **丑数** 。

  - **丑数** 就是只包含质因数 `2`、`3` 和/或 `5` 的正整数。

- dp

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
              //若等于则++，为了防止重复
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

- 优先队列
  
  - 空间复杂度更高，调整最小堆也要花销

### ==填二维表==

#### 矩阵连乘

- 找最小代价的矩阵相乘顺序

- 思路

  - 先计算所有相邻的2个矩阵相乘，再计算长度为3的....以此类推

- 实现

  - 还要记录路径。。
  
  ```java
  import java.util.*;
  public class Main {
  
      public static void main(String[] args) {
          //矩阵个数
          int n=6;
          int []p=new int[]{30,35,15,5,10,20,25}; //矩阵对应的边大小
          //a、b都是要从1开始，0不要。 b记录路径
          int [][]a=new int[n+1][n+1];
          int [][]b=new int[n+1][n+1];
  
          dp(n,p,a,b);
          System.out.println(a[1][n]);//15125
          showPath(b,1,n);//((A1(A2A3))((A4A5)A6))
  
      }
  
      private static void showPath(int[][]b,int i,int j ){
          if(i==j){
              //走到缩为一点才打印
              System.out.print("A"+i);
              return;
          }
          System.out.print("(");
          showPath(b,i,b[i][j]);
          showPath(b,b[i][j]+1,j);
          System.out.print(")");
      }
      private static void dp(int n,int []p,int [][]a,int [][]b){
          int min;
          for(int len=2;len<=n;len++){
              //i=2，先算所有长度为2的 如30*35。长度为1，自己到自己为0，就不用显式赋值
              for(int i=1;i<=n-len+1;i++){
                  int j=i+len-1;  //长度为len的末尾
                  a[i][j]=Integer.MAX_VALUE;
                  for(int k=i;k<j;k++){
                      //从i走到k
                      //i j k都是第几个，从1开始，p[i-1]*p[k]*p[j]坐标自己算，从0开始
                      min=a[i][k]+a[k+1][j]+p[i-1]*p[k]*p[j];
                      if(min<a[i][j]){
                          a[i][j]=min;
                          b[i][j]=k;   //从i到j，最后的乘是走k。
                      }
                  }
              }
          }
      }
  }
  ```


### ==背包问题==

#### 01背包问题

- 有 NN 件物品和一个容量是 VV 的背包。每件物品只能使用一次.第 ii 件物品的体积是 vivi，价值是 wiwi。

- 求解将哪些物品装入背包，可使这些物品的总体积不超过背包容量，且总价值最大。
  输出最大价值。

- 背包大小是整数，所以可以不断变换背包大小。。
  
- 实现

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
          // 有N件物品，背包容量为V
          for(int i=0;i<N;i++){
              //j从后面大的的开始拿
              //j要从大到小，比如第一轮的时候，前面的必须为0.
              //如果从小到大，则前面可能不为0，后面再拿到时就重复了。
              //如背包大小为5，第一体积为2，价值为4，则dp[2]=2,dp[4]=4。重复计算了
              for(int j=V;j>=0;j--){
                  // j-w[i]>=0
                  if(j>=w[i]){
                      dp[j]=Math.max(dp[j],dp[j-w[i]] +  v[i]);
                  }
              }
          }
          System.out.println(dp[V]);
      }
  }
  ```

#### 完全背包问题

- 每种物品都有无限件可用

- 背包大小是整数

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
          // 物品直接递增就好了，甚至不用排序？离谱，后续再研究下
          for(int i=0;i<N;i++){
              for(int j=0;j<=V;j++){
                  //从前往后，就是可重复选用
                  // 不用在乘每件的个数。。
                  if(j>=w[i]){
                      dp[j]=Math.max(dp[j],dp[j-w[i]]+v[i]);
                  }
              }
          }
          System.out.println(dp[V]);
      }
  }
  ```

#### 多重背包问题一(没有优化)

- 第 ii 种物品最多有 sisi 件，每件体积是 vivi，价值是 wiwi。

- 背包大小还是整数

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
          // 每件物品在最外层，接着是背包容量
          for(int i=0;i<N;i++){
              for(int j=V;j>=0;j--){
                  //从后往前拿拿一个，可以增加几个
                  //跟0-1背包问题相似，不过增加一个计数。从一个开始增加，超过就结束
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
  
  
  
  
# ==进阶==

## ==离散化==

- 基本思想
  - 把无限空间中有限的个体映射到有限的空间中去，以此提高算法的时空效率
  - 离散化是在不改变数据相对大小的条件下，对数据进行相应的缩小。
  - 一个离散化的例子（可惜是c++）：https://blog.csdn.net/zhang_dehong/article/details/128393910
    - 数据的范围是0到10^9，我们总不可能开一个10^9的数组再遍历然后加数

### 城市正视图（南墙可见性）

- 来源
  - 算法竞赛入门指南第二版P132
    - https://blog.csdn.net/qq_45670253/article/details/123300983
    - https://blog.csdn.net/weixin_42193704/article/details/82181490
  - 墙在南面。
- 离散化
  - 把无穷变为有穷状态
    - 因为不可能枚举建筑正面上的所有坐标来判断可见性
  - 可以忽略深度，因为可以通过y来判断
  - 将x坐标排序去重，则建筑在任意相邻的两个x坐标中的区间，要么全部可见，要么全部不可见
  - 只需要选择这个区间中的一个点（如中点mx），就可以判断这个建筑是否可见了
  - 可见判断：建筑包含mx，南面没有建筑也包含mx 且不比它矮 。（b[i]不包含mx则表示b[i]在mx处不可见）   
  
  

- 输出
  
- 建筑的俯视图左下角x、y，宽度w（x方向长度），深度d(y方向长度)，高度h
  
- 输出

  - 可见的建筑

- 实现

  ```java
  import java.util.*;
  public class Main {
      static class Building{
          int id;
          double x,y,w,d,h;
      }
  
      static int n;
      static Building []b;
      static double[]x;
  
  
      /**
       * input:
       * 14
       * 160 0 30 60 30
       * 125 0 32 28 60
       * 95 0 27 28 40
       * 70 35 19 55 90
       * 0 0 60 35 80
       * 0 40 29 20 60
       * 35 40 25 45 80
       * 0 67 25 20 50
       * 0 92 90 20 80
       * 95 38 55 12 50
       * 95 60 60 13 30
       * 95 80 45 25 50
       * 165 65 15 15 25
       * 165 85 10 15 35
       * output:
       * 5 9 4 3 10 2 1 14
       * @param args
       */
      public static void main(String[] args) {
  
  
          Scanner in=new Scanner(System.in);
          n=in.nextInt();//n个建筑
          b=new Building[n];
          for(int i=0;i<n;i++){
              b[i]=new Building();
          }
          x=new double[n*2];  //保存x坐标
          for(int i=0;i<n;i++){
              b[i].id=i+1;
              b[i].x=in.nextDouble();b[i].y=in.nextDouble();
              b[i].w=in.nextDouble();b[i].d=in.nextDouble();
              b[i].h=in.nextDouble();
              //一个建筑有两个x坐标
              x[i*2]=b[i].x;
              x[i*2+1]=b[i].x+b[i].w;
          }
  
          //对建筑根据x从小到大排序，若x相等，则根据y
          Arrays.sort(b,(x,y)->{
              return x.x!=y.x ? (int)(x.x-y.x) : (int)(x.y-y.y);
          });
          //对x排序并去重,得到m+1个坐标
          Arrays.sort(x);
          int m=1;
          for(int i=1;i<x.length;i++){
              if(x[i]!=x[i-1]){
                  x[m]=x[i];
                  m++;
              }
          }
  
          for(int i=0;i<n;i++){
              boolean v=false;
              for (int j=1;j<=m;j++){
                  double mx=(x[j]+x[j-1])/2;
                  //只要符合建筑b[i]在一个区间可见即可
                  v |=visible(i,mx);
              }
              if(v){
                  System.out.print(b[i].id+" ");
              }
          }
  
      }
      //建筑b[i]是否包含区间中点mx
      private static boolean cover( int i,double mx){
          return mx>=b[i].x && mx<=b[i].x+b[i].w;
      }
  
      //建筑b[i]在区间中点mx处是否可见
      private static boolean visible(int i,double mx){
          if(!cover(i,mx)){
              return false;
          }
          for(int j=0;j<n;j++){
              //j在i前面，j比i高，j包含mx
              if(b[j].y<b[i].y && b[j].h>=b[i].h && cover(j,mx)){
                  return false;
              }
          }
          return true;
      }
  }
  ```

# ==并查集==

## 基础知识

### 判断是否是无向有环图

- 3

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

  

# ==其他==

## 位运算

### NC21298-牛牛的xor

- 题目描述                    

  给你一个n个元素的数组x
   你需要找到一个数组a, 0 ≤ a[i] ≤ x[i]
   使得a[0]  xor  a[1]... xor  a[n-1]最大
   输出这个最大值

- 输入描述:

```
第一行输入一个整数n (1 ≤ n ≤ 50)
第二行输入n个整数 xi (0 ≤ xi ≤ 1e9)
```

- 输出描述:

```
输出一个整数            
```

- 输入

```
3
2 2 2
```

- 输出

```
3               
```

- 输入

```
5
1 2 4 8 16
```

- 输出

```
31
```

- 贪心

  - 有为1的位就拿，且只拿一个

  ```java
  import java.util.*;
  public class Main{
      //题目意思是a[i]有n个 比与之对应位置的x[i]小
      public static void main(String []args){
          Scanner in=new Scanner(System.in);
          int n=in.nextInt();
          int []x=new int[n];
          for(int i=0;i<n;i++){
              x[i]=in.nextInt();
          }
          int ans=0;
          for(int i=(1<<30);i>=1;i>>=1){
              for(int j=0;j<n;j++){
                  if(x[j]>=i){
                      ans+=i;
                      x[j]-=i;
                      break;
                  }
              }
          }
          System.out.println(ans);
      }
  }
  ```


## 同余关系

- 若(x-y)%p=0;则称x与y同余    x=y mod p（三等号打不出来）
- 同余情况下
  - x>=0 && y>=0
    - 则 x%p=y%p
  - x<0 && y>=0      或者 x>=0 && y>=0
    - 则(x%p+p) %p=y%p
    - 若x已经是余数   x<p
    - 则(x+p)%p=y%p
  - 当y=r-l时   (x-) 可以移项
    - x=r-l mod p
    - 移项后：x-r=-l mod p
    - 或者 ： x+l=r mod p   用这个吧，都是正数
    - 通过r求l :   l=r-x mod p

- 但是java对于负数 x%p =0  结果都是0。。
  - 通过r求l :   l=r-x mod p
    - 可以换成 l=(r%p-x%p+p)   mod p

# ==数学==

## gcd

### gcd来了

- 题目描述                    

题意很简单，算出两个数的最大公约数和最小公倍数。

- 输入描述:

```
输入两个数m,n。（1<=m,n<=1e18）
注意有多组输入
```

- 输出描述:

```
输出两个数的最大公约数和最小公倍数。           
```

- 输入

```
2 6
6 9
```

- 输出

```
2 6
3 18
```

- 注意gcd的a>=b，lcm的越界问题

  ```java
  import java.util.*;
  public class Main{
      public static void main(String []args){
          Scanner in=new Scanner(System.in);
          while(in.hasNext()){
              long n=in.nextLong();
              long m=in.nextLong();
              long gcd;
              if(n>=m){
                  gcd=gcd(n,m);
              }else{
                  gcd=gcd(m,n);
              }
              long lcm;
              lcm=lcm(n,m,gcd);
              System.out.println(gcd+" "+lcm);
              
              }
          
      }
      
      //要求a>=b
      private static long gcd(long a,long b){
          if(b==0){
              return a;
          }
          return gcd(b,a%b);
      }
      
      //最小公倍数=a/最大公约数*b    //可能会越界，所以先除以最大公约数
      private static long lcm(long a,long b,long gcd){
          return a/gcd*b;   //
      }
  }
  ```

### NC14503-晨跑

- 题目描述                    

  “无体育，不清华”、“每天锻炼一小时，健康工作五十年，幸福生活一辈子”

   在清华，体育运动绝对是同学们生活中不可或缺的一部分。为了响应学校的号召，模范好学生王队长决定坚持晨跑。不过由于种种原因，每天都早起去跑步不太现实，所以王队长决定每a天晨跑一次。换句话说，假如王队长某天早起去跑了步，之后他会休息a-1天，然后第a天继续去晨跑，并以此类推。

   王队长的好朋友小钦和小针深受王队长坚持锻炼的鼓舞，并决定自己也要坚持晨跑。为了适宜自己的情况，小钦决定每b天早起跑步一次，而小针决定每c天早起跑步一次。

   某天早晨，王队长、小钦和小针在早起跑步时相遇了，他们非常激动、相互鼓励，共同完成了一次完美的晨跑。为了表述方便，我们把三位同学相遇的这天记为第0天。假设三位同学每次晨跑的时间段和路线都相同，他们想知道，下一次三人在跑步时相遇是第几天。由于三位同学都不会算，所以希望由聪明的你来告诉他们答案。

- 输入描述:

```
输入共一行，包含三个正整数a,b,c，表示王队长每隔a天晨跑一次、小钦每隔b天晨跑一次且小针每隔c天晨跑一次。
```

- 输出描述:

```
输出共一行，包含一个正整数x，表示三位同学下次将在第x天相遇。             
```

- 输入

```
2 3 5
```

- 输出

```
30
```

- 三个数的lcm

  ```java
  import java.util.*;
  public class Main{
      public static void main(String []args){
          Scanner in=new Scanner(System.in);
          int a=in.nextInt();
          int b=in.nextInt();
          int c=in.nextInt();
          long l1=lcm(a,b);
          long l2=lcm(b,c);
          long ans=lcm(l1,l2);
          System.out.println(ans);
      }
      
      private static long gcd(long a,long b){
          if(b==0)
              return a;
          return gcd(b,a%b);
      }
      private static long lcm(long a,long b){
          return a/gcd(a,b)*b;
      }
  }
  ```

  



# ==字符串==

## 后缀数组

### 基础知识

- 链接：https://www.jianshu.com/p/5d1d6efb82c7#1680489611081

- 代码

  ```java
   /*
       * Returns the LCP for any two strings
       */
      public static int computeLCP( String s1, String s2 )
      {
          int i = 0;
          
          while( i < s1.length( ) && i < s2.length( ) && s1.charAt( i ) == s2.charAt( i ) )
              i++;
          
          return i;
      }
  
      /*
       * Fill in the suffix array and LCP information for String str
       * @param str the input String
       * @param SA existing array to place the suffix array
       * @param LCP existing array to place the LCP information
       * Note: Starting in Java 7, this will use quadratic space.
       */
      public static void createSuffixArraySlow( String str, int [ ] SA, int [ ] LCP )
      {
          if( SA.length != str.length( ) || LCP.length != str.length( ) )
              throw new IllegalArgumentException( );
          
          int N = str.length( );
          
          String [ ] suffixes = new String[ N ];
          for( int i = 0; i < N; i++ )
              suffixes[ i ] = str.substring( i );
          
          Arrays.sort( suffixes );
          
          for( int i = 0; i < N; i++ )
              SA[ i ] = N - suffixes[ i ].length( );
          
          LCP[ 0 ] = 0;
          for( int i = 1; i < N; i++ )
              LCP[ i ] = computeLCP( suffixes[ i - 1 ], suffixes[ i ] );
      }
  ```

## 后缀自动机

### 基础知识

- 链接：https://blog.csdn.net/Jacob0824/article/details/126022039

