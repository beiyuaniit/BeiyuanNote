## 杂项

- 3

  ```shell
  # 建议在IDEA中也使用命令行来用git
  
  # GitLab的group类似于GitHub的organization
  group可邀请团队协作、管理多个仓库
  
  # 开发流程
  1.fork公共仓库到自己的账号
  2.clone 下载自己账号的库
  3.进行开发、push等
  4.new merge request。
  源仓库(个人) : 源分支 --> 目标仓库(组): 目标分支
  5.目标仓库处理合并请求
  
  
  # GitLab无法在线处理的冲突
  如源分支对某个文件h1.txt删除，目标分支进行修改
  1.git clone 目标分支(组)
  2.git pull 源分支(个人)
  3.git status 查看
  git add h1.txt # 保留 
  (or) git rm h1.txt # 删除
  处理还有的其他冲突
  4.git commit
  5.git push
  
  # git clone或share project后，项目与平台关联后,可直接对相应分支进行push、pull 而不加origin等参数。
  git pull
  git push
  
  
  # GitLab检视代码时。Changes界面 -- 看到的是所有的修改
  最左侧可以写检视评论
  Edit按钮可以在线编辑代码，commit后是一个新的提交。 这里编辑的是所有修改后的文件
  
  
  # issue
  通过创建issue、标签管理issue，可以是实现需求的规划、开发、测试、完成等流程的跟踪管理 
  ```

  

## Linux安装git

- 7

  ```shell
  # Ubuntu
  aptitude install git;
  aptitude install git-svn git-email gitk;
  
  # CentOs 
  yum install git; 
  yum install git-svn git-email gitk;
  ```



## 命令

- 文档：https://git-scm.com/docs

  ```shell
  # 工程准备
  git init / git clone 
  git lfs clone # 项目有二进制文件
  
  # 暂存区 在暂存区的文件才是由git跟踪
  git add / git rm / git mv # 添加到暂存区
   
  
  # 查看工作区
  git diff /  git status 
  
  # 提交
  git commit 
  
  # 查看日志
  git log / git reflog
  
  # 推送、拉取
  git push / git pull
  
  # 分支准备
  git branch / git checkout 
  
  # 分支合并
  git merge / git rebase
  
  # 版本回退
  git reset # 强制回退到历史节点
  git checkout # 回退所有未提交的修改
  ```

- example

  ```shell
  # git init firstproject
  [root@centos8 git]# git init firstproject
  Initialized empty Git repository in /home/beiyuanii/Desktop/git/firstproject/.git/
  [root@centos8 git]# cd firstproject
  [root@centos8 firstproject]# ll
  total 0
  [root@centos8 firstproject]# ls -al
  total 0
  drwxr-xr-x. 3 root root  18 May 27 12:47 .
  drwxr-xr-x. 3 root root  26 May 27 12:47 ..
  drwxr-xr-x. 7 root root 119 May 27 12:47 .git
  
  
  # git add
  git add . # 未暂存的全部add
  
  
  # git rm
  git rm --cached a1.txt # 从暂存区移除
  git rm -f a1.txt # 从暂存区和磁盘中移除
  
  
  # git commit
  # 修改后的文件好像要先git add到暂存区(因为修改文件内容后，文件的hash值变了？)
  git commit a1.txt -m "init commit"
  git commit -am "commit all file" # 提交暂存区所有文件 -a -m (要先add)
  git commit . -m 'commit all file' # 提交暂存区所有 (要先add)
  
  # git分支中的文件在linux下不共享
  hotfix 分支
  a.txt # 已commit -- 不共享
  b.txt # 已add    -- 共享（这里要特别留意下git commit -am/git commit . -m 会把所有分支(包括在其他分支add的)都commit
  c.txt # 未add     -- 共享
  # 切换分支
  git checkout master
  b.txt # 已add
  c.txt # 未add
  
  
  # 修改上一次提交的message（好像是重新生成一个提交
  git commit --amend # 直接vim，vim了后无论有没有修改，commit_id都会变
  
  
  # git push(pull 同理)
  git push origin branch_name # 分支名不区分大小写
  git push origin old_branch:new_old_branch # 本地分支名和推送分支名可不一样，但一般都保持一样吧
  
  # git pull 与 git fetch
  git pull origin branch_name # 获取远端指定分支的更新，并自动与本地指定分支合并
  git fetch origin branch_name # 获取远端指定分支的更新，但不会自动合并，需要手动merge
  
  # 新建分支 - 继承基分支的提交历史等
  git branch # 查看本地分支 -r远端 -a所有
  git branch beiyuan/hot-fix
  git checkout -b beiyuan/feature/dev # 创建并切换
  
  # 删除分支
  git branch -d branch_name
  git branch -d -r branch_name; git push origin:branch_name # 删除远程分支
  git push origin --delete branch_name # 删除与远程(gpt说的
  
  # git merge
  git merge hot-fix # 在master分支执行，表示把hot-fix分支合并到master
  
  
  # git log
  git log --name-status # 这个参数查看提交记录、并显示文件变化 A:add M:modify D:delete 。 这里没显示提交所属分支。
  git branch --contains 9ab233bbcc # 显示提交所属分支
  git log -3 # 前3个提交的
  git reflog # 查看commit、checkou历史
  
  # 丢弃工作目录中所有未暂存的更改 ,已经暂存的不影响  -- 不可逆，谨慎使用
  git checkout .
  git checkout a1.txt # 只丢弃a1.txt未暂存的更改，al.txt已经add的不会改变
  
  
  
  ```

## diff

- 3

  ```shell
  # git diff
  git diff 423b7e8 f2efb8f   # 节点差异
  git diff --cached # 当前与上次提交差异
  git diff beiyuan/hot-fix master # 查看分支间差异
  [root@centos8 firstproject]# git diff 8e04 3c24 # 比较两个提交 8e04 和 3c24 之间的差异
  diff --git a/c.txt b/c.txt # 指出文件 c.txt 在两个提交中存在差异
  index ac414fb..5a26908 100644# ac414fb 是文件 c.txt 在提交 8e04 中的哈希值# 5a26908 是文件 c.txt 在提交 3c24 中的哈希值# 100644 表示文件的权限（普通文件，可读写）
  --- a/c.txt # --- 表示旧版本的文件路径 a/c.txt
  +++ b/c.txt # +++ 表示新版本的文件路径 b/c.txt
  @@ -1 +1 @@ # 差异的上下文范围，表示从旧文件的第 1 行开始，到新文件的第 1 行为止
  -this is hotfix file# - 表示旧版本中的这一行内容 this is hotfix file
  +this is master c file# + 表示新版本中的这一行内容 this is master c file
  diff --git a/d.txt b/d.txt# 指出文件 d.txt 在两个提交中存在差异
  new file mode 100644# 表示这是一个新文件，文件权限为 100644（普通文件，可读写）
  index 0000000..0351e7a# 0000000 是空文件的哈希值（旧版本文件不存在）# 0351e7a 是文件 d.txt 在新版本中的哈希值
  --- /dev/null# 旧版本文件不存在，所以显示为 /dev/null
  +++ b/d.txt# 新版本的文件路径 b/d.txt
  @@ -0,0 +1 @@# 差异的上下文范围，表示从新文件的第 1 行开始
  +this is d file# + 表示新版本中的这一行内容 this is d file（文件新增的一行内容）
  ```

  

## merge&rebase

- `git merge` 和 `git rebase` 是 Git 中两种合并分支的方法，各有优缺点和适用场景

  - git merge
    -  简介
      - 将两个分支的历史记录合并，创建一个新的合并提交（merge commit）
      - 保留了分支的历史和分支点
    - 优点
      - 合并提交包含了完整的历史记录，保留了分支结构，便于追溯
      - 简单易用，不会改变已有提交的哈希值
    - 缺点
      - 频繁创建和合并分支时，历史记录会变得复杂
  - git rebase
    - 将一个分支的更改重新应用到另一个分支的顶端，重新写入提交历史，使历史记录更线性。（另一个分支作为基底
    - 优点
      - 提交历史更干净，便于查看和理解
      - 适合共享分支，例如 hotfix 分支合并到主分支前，确保历史记录整洁
    - 缺点
      - 变基会改变提交的哈希值，不适用于已经共享的分支（如推送到远程仓库的分支）
      - 在变基过程中，每个提交都可能产生冲突，需要逐个解决
    - 使用场景
      - 未共享的分支：在本地进行 `rebase`，确保提交历史干净整洁。
      - 个人分支：在合并到主分支前对个人分支进行 `rebase`，确保提交历史线性。

- example

  ```shell
  # 原始分支
  A---B---C (master)
       \
        D---E (hotfix)
        
  # merge
  git checkout master
  git merge hotfix
  A---B---C---F (master)
       \     /
        D---E (hotfix)  # 合并后hot-fix还是指向自己的E hotfix本身不变
        
  # rebase
  git checkout hotfix
  git rebase master # master做hotfix的基底，master本身不变
  A---B---C (master)
           \
            D'---E' (hotfix)
            
            
  # why不适用于共享分支 
  1.假设你和你的同事都在远程仓库的 feature 分支上工作，初始提交历史如下
  A---B---C (master)
       \
        D---E---F (feature)
  2.你和你的同事都已经从远程仓库拉取了 feature 分支的最新提交（D, E, F）。然后，你对本地的 feature 分支执行了 rebase：
  git checkout feature
  git rebase master
  本地变为
  A---B---C (master)
           \
            D'---E'---F' (feature)       
  3.你推送 feature 分支到远程仓库
  git push origin feature --force
  远程仓库变为
  A---B---C (master)
           \
            D'---E'---F' (feature)
  4.当你的同事尝试从远程仓库拉取最新的 feature 分支时，Git 会报错，因为历史记录已经改变，提交哈希值不同。他们需要执行以下命令来同步：
  git fetch origin
  git rebase origin/feature
  或者，他们需要通过重置或者其他操作来强制同步。这个过程非常容易导致混乱和冲突，因为每个提交都需要重新应用。
  # 解决办法
  使用 git merge 代替 rebase：保留提交历史，避免改变已共享的提交。
  限制使用 --force 推送：只有在必要时使用，并确保团队成员知晓变基操作。
  
  
  
  # why rebase提交历史更干净
  分支初始情况
  A---B---C (master)
       \
        D---E (feature)
  1.如果是merge
  git checkout master
  git merge feature
  A---B---C---F (master)
       \     /
        D---E (feature)
  可能会使提交历史变得复杂：合并提交保留了分支的历史，在多次创建和合并分支时，历史记录会充满各种分叉和合并点。
  2.如果是rebase
  git checkout feature
  git rebase master
  A---B---C---D---E (feature)
  git checkout master
  git merge feature
  A---B---C---D---E (master, feature)
  提交历史干净且线性，便于查看和理解
  
  ```



## reset

- `git reset` 是一个强大的 Git 命令，用于撤销更改并重置当前分支的 HEAD 到指定状态 -- **谨慎使用**

- 三种模式

  - --soft
    - 行为
      - 仅重置当前分支的 HEAD 指针，不更改索引（暂存区）或工作目录
      - 提交历史会被重置，但文件的更改仍然保留在暂存区
    - 使用场景
      - 想要合并多个提交为一个提交(不丢失更改)
      - 想要重新组织提交历史(不丢失更改)
  - --mixed(默认)
    - 行为
      - 重置当前分支的 HEAD 和索引（暂存区），但不更改工作目录
      - 提交历史会被重置，索引被清除，但文件的更改保留在工作目录中
    - 使用场景
      - 想要撤销提交，但保留文件的更改，以便重新暂存和提交
      - 默认模式，适用于大多数情况下的提交撤销
  - --hard
    - 行为
      - 模式重置当前分支的 HEAD、索引（暂存区）和工作目录
      - 提交历史会被重置，索引和工作目录中的所有更改都会丢失
    - 使用场景
      - 想要完全撤销提交和更改，恢复到指定提交的状态
      - 此操作是不可逆的，会丢失未提交的更改

- example

  ```shell
  A---B---C---D (HEAD)
  
  # --soft
  git reset --soft B
  # 变为
  A---B (HEAD)
  # 提交 `C` 和 `D` 的更改保留在暂存区，可重新提交修改
  git commit -m "..."
  
  
  # --mixed
  git reset --mixed B
  # 变为
  A---B (HEAD)
  # 提交 `C` 和 `D` 的更改保留在工作目录中，但从暂存区中移除 。可重新放入到暂存区、提交
  git add .
  git commit -m "..."
  
  
  # --hard
  git reset --hard B
  # 变为
  A---B (HEAD)
  # 提交 `C` 和 `D` 的更改从索引和工作目录中完全移除
  ```



## revert

- 3

- `git revert` 是一个 Git 命令，用于撤销一个或多个已经提交的更改，但它不会直接修改提交历史。相反，`git revert` 会创建一个新的提交，反转指定提交的更改。这个方法特别适合在协作项目中使用，因为它不会重写历史，因此不会影响其他协作者的工作。

  ### 基本用法

  #### 单个提交

  假设你的提交历史如下：

  ```
  A---B---C---D (HEAD)
  ```

  你想撤销提交 `C`。

  ```sh
  git revert C
  ```

  执行上述命令后，Git 会创建一个新的提交 `E`，其内容是提交 `C` 的反转：

  ```
  A---B---C---D---E (HEAD)
  ```

  提交 `E` 会包含提交 `C` 的所有更改的反向操作。

  ### 详细步骤

  1. **执行 `git revert`**：
     
     ```sh
     git revert <commit>
     ```

     例如：

     ```sh
     git revert C
     ```

  2. **编辑提交信息**：

     运行 `git revert` 后，Git 会打开一个文本编辑器，让你编辑新创建的反转提交的信息。你可以接受默认信息或进行修改。

  3. **保存并关闭编辑器**：

     完成编辑后，保存并关闭编辑器。Git 将创建一个新的提交来反转指定的提交。

  ### 处理多个提交

  #### 多个连续的提交

  你可以使用 `git revert` 一次性反转多个连续的提交。假设你的提交历史如下：

  ```
  A---B---C---D---E (HEAD)
  ```

  你想反转从 `C` 到 `E` 的所有提交。

  ```sh
  git revert C^..E
  ```

  这将创建三个新的提交，分别反转 `C`、`D` 和 `E`：

  ```
  A---B---C---D---E---F---G---H (HEAD)
  ```

  - `F` 反转 `C`
  - `G` 反转 `D`
  - `H` 反转 `E`

  #### 非连续的提交

  对于非连续的提交，你需要分别运行 `git revert`。例如，假设你只想反转提交 `B` 和 `D`：

  ```sh
  git revert B
  git revert D
  ```

  这将创建两个新的提交：

  ```
  A---B---C---D---E---F---G (HEAD)
  ```

  - `F` 反转 `B`
  - `G` 反转 `D`

  ### 解决冲突

  在运行 `git revert` 时，可能会遇到合并冲突。解决这些冲突的步骤如下：

  1. **查看冲突文件**：

     Git 会指出哪些文件存在冲突。你需要手动编辑这些文件，解决冲突。

  2. **暂存解决后的文件**：

     ```sh
     git add <conflicted-file>
     ```

  3. **继续反转**：

     ```sh
     git revert --continue
     ```

  ### 与 `git reset` 和 `git rebase` 的比较

  - **`git revert`**：
    - 不会修改提交历史。
    - 创建一个新的提交来反转指定的提交。
    - 适用于已经共享的分支，安全且不影响其他协作者的工作。

  - **`git reset`**：
    - 修改提交历史。
    - 将 HEAD 重置到指定的提交，可以选择性地影响工作目录和暂存区。
    - 适用于本地分支，慎用于已经共享的分支。

  - **`git rebase`**：
    - 重写提交历史。
    - 用于整理提交历史，创建一个新的提交序列。
    - 适用于本地分支，慎用于已经共享的分支。

  ### 总结

  `git revert` 是一种安全的撤销更改的方法，特别适合在协作项目中使用。它不会修改提交历史，而是通过创建一个新的反转提交来撤销指定的更改。这使得 `git revert` 在处理已经推送到远程仓库的提交时非常有用，因为它不会影响其他协作者的工作。

  通过了解 `git revert` 的基本用法和处理多种情况的方法，你可以更有效地管理和维护你的 Git 仓库。

## 某个commit作为起点

- 先 revision 到某个提交的位置

  ```shell
  # 分支内 属实没有这个必要。。 IDEA操作为在某个分支上右键 --> New Branch form '...'
  A---B---C (master)
      \ 
       D---E---F-- (hotfix)
       	  \    
       	   G--- (hotfix)
       	 
  # 新拉一个分支    IDEA操作为在commit上右键 --> New Branch form '...'
  A---B---C (master)
      \ 
       D---E---F-- (hotfix)
       	  \    
       	   G--- (hotfix-tmp)
  ```



## merge

- 4

  ```shell
  # 发生冲突
  git checkout master
  [root@centos8 firstproject]# git merge beiyuan/hotfix
  Removing h1.txt
  CONFLICT (add/add): Merge conflict in c.txt
  Auto-merging c.txt
  Automatic merge failed; fix conflicts and then commit the result.
  
  # 是c.txt冲突
  [root@centos8 firstproject]# git status
  On branch master
  You have unmerged paths.
    (fix conflicts and run "git commit")
    (use "git merge --abort" to abort the merge)
  
  Changes to be committed:
  	modified:   a1.txt
  	new file:   a2.txt
  	deleted:    h1.txt
  
  Unmerged paths:
    (use "git add <file>..." to mark resolution)
  	both added:      c.txt
  
  # 直接修改c.txt
  vim c.txt 
  git add c.txt
  git commit -am 'resolve merge conflict'  # 冲突处理结束
  ```

  

## 未整理

- git restore
- git revert
- git patch
- git force
- git relog
- 工作流

