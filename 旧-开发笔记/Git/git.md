## 基础

- 暂存区可以先保存（没有历史版本），完成后再提交。
- 工作区只显示一个版本

- 设置用户签名

```txt
git config --global user.name Local
git config --global user.email beiyuan@me.com
```

- git init
  - 初始化一个空仓库，获取该目录的权限等。总的放在了B:\Git-Repository
  - 每个项目一个仓库
- 可以尽可能秀linux命令
- git add
  -  LF will be replaced by CRLF the next time Git touches it        LF:linux的换行符，CRLF：windows的
- clear。清屏
- 复制粘贴
  - 复制：Ctrl+Insert
  - 粘贴：Shift+Insert
- pull和push应该在.git所在目录
- pull=fetch+merge

## 分支

- 该目录下只有对应分支的文件
- 分支也可以添加暂存区，提交等
- 只不过，这样在本地操作的居然是同一份文件，比如分支修改了文件，切回master，能看到该修改，也能add和commit。不过提交后，分之上的修改就没有了，只有一个最终版在master上（
- emmm，又试了一次，分支上有修改或者add但是没有commit，不给切回master
- master:git merge hot-fix     //把hot-fix合并到master上        反过来也可以。只会修改当前分支的内容
- 合并后分支居然没有消失

## 冲突

- 不会冲突

```txt
#新增内容
master
	rain
hot-fix
	rain bow

#新增一行
master 
	rain
hot-fix
	rain
	bow
```

- 会冲突
  - 分支名变为(hot-fix|MERGING)
  - 手动修改  vim hello.txt     去掉不想要的

```txt
#同一行有不同内容
master
	rain
hot-fix
	bow 
	
#
```

## 远程协作

- 团队内是同一团队，管理者可赋予成员权限。
- git 别名 分支        后提示登陆远程账号
- clone公共库不需要登陆账号

## 错误

- 拉取时

```txt
$ git pull https://github.com/beiyuaniit/git-demo01.git master
From https://github.com/beiyuaniit/git-demo01
 * branch            master     -> FETCH_HEAD
Already up to date.

//远程库和本地库代码不一致
```

## SSH免密登陆

- ssh-keygen -t rsa -C beiyuaniit@gmail.com               #去这里用C:\Users\beilinanju\.ssh
- 复制id_rsa.pub公钥的内容到github即可
- 久了连接不上可能是ssh过期
- 测试：ssh -T git@github.com
- git clone 用ssh那个连接

## IDEA

- 忽略文件
  - .gitconfig位置C:\Users\beilinanju\\.gticonfig
- 登陆GitHub时，用账号密码报404，用口令token登陆，token生成后一刷新就消失，所以及时保存
- share project on github    好像只在远程生成一个分支如master，没有hot-fix
- push的时候，若是hot-fix分支push，则也会在远程库创建hot-fix分支

## 日常使用

- gitee创建仓库，克隆下来，把本地的代码复制到克隆下来的目录，add，commit，push到gitee
- 或者不用事先创建远程仓库，初始化后直接Git->Github->Share Project On Github
- git clone 拿到的代码是完整的。 .git文件夹也能拿来直接使用
- 提交前先pull一下，拉取代码并合并本地版本
- https://blog.csdn.net/qq_64580912/article/details/127591518   日常使用
- 合并冲突时中间的才是想要的代码
- 常见冲突是合并分支和pull拉取远程仓库。同一位置的内容不同