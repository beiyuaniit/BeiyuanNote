## 软链接和硬链接

- 软链接
  - 软连接是一个独立的文件，它包含了指向目标文件或目录的路径
  - 使用`ln -s`命令创建。例如，`ln -s /path/to/original /path/to/link`
  - 软连接可以跨越不同的文件系统（例如，从一个分区到另一个分区）
  - 可以指向目录
  - 删除软连接不会影响目标文件，但如果目标文件被删除，软连接将变成“断链”（dangling link），即指向无效位置
  - 通常用`ls -l`命令查看时，软连接的文件类型显示为`l`
  - 通过软链接修改文件内容，实际是修改目标文件，目标文件内容改变

- 硬链接
  - 硬链接是一个指向文件数据区的直接引用。硬链接与原始文件共享相同的i节点（inode）
  - 使用`ln`命令创建。例如，`ln /path/to/original /path/to/link`
  - 硬链接只能在同一个文件系统内创建，不能跨文件系统
  - 一般情况下，不能创建指向目录的硬链接（除非使用超级用户权限，并且非常不推荐这样做）
  - 删除一个硬链接或原始文件不会影响其他硬链接。文件数据只有在所有指向它的硬链接被删除后才会被删除
  - 用`ls -l`命令查看时，硬链接的文件类型显示为常规文件（例如，`-`），且硬链接数会增加
  - 通过硬链接修改文件内容，所有硬链接和目标文件的内容都会改变，因为它们是同一个文件。

- 应用场景

  - **软连接**：通常用于创建快捷方式，方便访问和管理文件或目录，特别是在跨文件系统和指向目录时。
  - **硬链接**：适合在同一文件系统内创建多个引用，以便共享文件数据，并确保文件在多个位置都能访问到且只有在所有引用删除后才删除数据。

- 注意

  - 在软链接会移动情况下，建议用绝对路径创建，相对路径的软链接移动后会失效。（windows的都是绝对路径，）

  ```shell
  # 修改软链接本身
  # 1.删除现有软链接、创建新的软链接
  rm h1.s
  ln -s new_target h1.s
  
  # 2.强制重新创建软链接（改变软链接指向）
  ln -sf new_target h1.s
  
  
  
  # vim修改的是软链接指向的文件
  vim h1.s   # 等于 vim h1
  ```

  

## 查看用户列表

- Linux通过ID区分用户和组

- Linux系统中有众多用户的原因主要是为了实现系统服务的分离、安全性和管理的便利性。每个系统用户通常对应着系统上的一个或多个服务、守护进程或者是为了完成特定功能而存在。

  ```shell
  cat /etc/group   # 查看GID等信息
  cat /etc/passwd  # 查看UID等信息
  cat /etc/shadow  # 查看用户密码(加密后)
  
  [root@centos8 tmp]# cat /etc/passwd
  root:x:0:0:root:/root:/bin/bash
  bin:x:1:1:bin:/bin:/sbin/nologin
  daemon:x:2:2:daemon:/sbin:/sbin/nologin
  adm:x:3:4:adm:/var/adm:/sbin/nologin
  lp:x:4:7:lp:/var/spool/lpd:/sbin/nologin
  sync:x:5:0:sync:/sbin:/bin/sync
  # ...
  
  # 有几十个
  
  
  # 从左到右，每列含义
  用户名 (root, bin, daemon, 等)：这是用户账号的登录名称。
  密码占位符 (x 或有时是 * 或空)：早期的Unix系统直接在这里存储加密后的密码，但现在通常用x表示密码已被移到了/etc/shadow文件中。
  用户ID (UID) (0, 1, 2, 等)：这是一个数字，用于唯一标识用户。0通常是root用户，具有最高权限。
  组ID (GID) (0, 1, 2, 等)：与UID类似，GID标识用户所属的基本组。系统会根据GID确定用户所属的组及其权限。
  用户全名或说明 (root, bin, FTP User, 等)：这一字段是对用户的简短描述，可以是全名、职务或账户用途。
  家目录 (/root, "/bin", /var/spool/mail, 等)：当用户登录时，其默认工作目录。
  登录Shell (/bin/bash, "/sbin/nologin", /sbin/bash, 等)：指定用户登录后启动的Shell程序。如果设置为/sbin/nologin或/bin/false，则该用户不能登录系统。
  ```

## grep-行搜索

- `grep` 是一个在Linux和Unix系统上用于搜索文本的命令行工具。它可以基于文本文件或输入中的模式（正则表达式）来查找并显示匹配的行。

- 3

  ```shell
  # 基本语法
  grep [OPTIONS] PATTERN [FILE...]
  
  # 常用选项
  - `-i`：忽略大小写。
  - `-v`：反转匹配，显示不包含模式的行。
  - `-r` 或 `-R`：递归搜索目录。
  - `-l`：显示匹配的文件名，而不是匹配的行。
  - `-L`：显示不包含匹配的文件名。
  - `-n`：显示匹配行的行号。
  - `-c`：显示每个文件中匹配行的数量。
  - `-o`：仅显示匹配的部分。
  - `-e`：使用多个匹配模式。
  - `-E`：使用扩展的正则表达式（等同于 `egrep`）。
  - `-F`：使用固定字符串（等同于 `fgrep`）。
  - `-w`：匹配整个单词。
  - `-A NUM`：显示匹配行及后面 NUM 行。
  - `-B NUM`：显示匹配行及前面 NUM 行。
  - `-C NUM`：显示匹配行及前后 NUM 行。
  
  
  # example
  
  # 正则表达式
  grep -E 'h[aeiou]llo' input.txt
  
  # 匹配多个正则表达式
  grep -e 'hello' -e 'world' input.txt 
  
  # 搜索多个文件
  grep 'hello' input1.txt input2.txt
  
  # 结合管道
  dmesg | grep 'error'  # 搜索系统日志中的 `error` 信息
  
  # 固定字符串
  grep -F 'hello' input.txt # 将hello视为固定字符串，而不是正则表达式
  
  
  # 查看进程
  ps aux | grep 'username'
  
  # 搜索ip
  grep -E '([0-9]{1,3}\.){3}[0-9]{1,3}' file 
  ```

## sed-行编辑

- `sed` （Stream Editor）是一个流编辑器，用于处理和转换文本文件。它是Linux和Unix系统上的一个非常强大和灵活的工具。

- 3

  ```shell
  # 基本语法
  sed [OPTION]... {script-only-if-no-other-script} [input-file]...
  
  # 常用选项
  - `-e script`：直接在命令行上指定要执行的脚本。
  - `-f script-file`：从文件中读取要执行的脚本。
  - `-i[SUFFIX]`：直接编辑文件，使用可选的SUFFIX创建备份文件。
  - `-n`：禁止自动打印模式空间的内容。
  
  
  # 替换s,后面加g表示全局替换
  sed 's/pattern/replacement/' file 
  sed 's/hello/world/' input.txt      # 每一行第一个 `hello` 替换为 `world`  --每一行都会替换
  sed '2,4s/hello/world/' file 		# 2到4行进行替换
  sed 's/hello/world/g' input.txt   	# 每一行所有替换
  
  # 前插入行i
  sed '/pattern/i\text' file    # 对pattern进行正则表达式匹配，在所有匹配到的行前插入一行text
  sed '/hello/i\This is a new line' input.txt
  
  # 后插入行a
  sed '/pattern/a\text' file # 同理
  sed '/hello/a\This is a new line' input.txt
  
  # 删除行d
  sed 'nd' file   # n是行号
  sed '2d' input.txt # 删除第2行
  
  # 打印行p 	
  # sed命令默认会在处理文件时打印每一行的内容，-n 禁止默认打印，不加打印所有行、第n行打印多打印一次
  sed -n 'np' file # n是行号
  sed -n '2p' input.txt # 仅打印第2行
  
  # 只打印匹配行
  sed -n '/pattern/p' file
  
  
  # 多个命令一起运行
  # 1.分号隔开
  sed 's/hello/world/; s/foo/bar/' input.txt 
  # 2.保存成脚本 script.sed
  s/hello/world/
  s/foo/bar/
  sed -f script.sed input.txt #运行脚本
  
  
  # 常见用法
  
  # 删除空行
  sed '/^$/d' input.txt
  
  # 备份并替换
  # 首先创建一个备份保存input.txt.bak保存，接着对原文件input.txt进行全局替换
  sed -i.bak 's/hello/world/g' input.txt
  
  
  # 插入多行
  sed '/pattern/i\
  line1\
  line2\
  line3' file
  
  # 匹配并执行多条命令
  sed '/pattern/{ 
  s/foo/bar/
  s/hello/world/
  }' file
  
  
  ```



## awk-行分割

- `awk` 是一个强大的文本处理工具，适用于在命令行下进行数据提取和处理。它的主要用途包括格式化文本、数据分析和报告生成。`awk` 通过扫描文件或标准输入，逐行处理文本，并根据用户定义的模式和操作执行相应的操作。

- 7

  ```shell
  # 基本语法
  awk 'pattern { action }' file
  - `pattern`：模式，用于匹配输入文本行。
  - `action`：动作，定义了在匹配到模式时要执行的操作。
  - `file`：要处理的文件。
  
  
  # 常见用法示例
  
  # file.txt
  Alice 30
  Bob 25
  Charlie 35
  
  # file.csv内容
  Alice,30
  Bob,25
  Charlie,35
  
  # 打印文件的所有行
  awk '{ print }' file.txt
  
  # 打印指定列 列号从1开始
  awk '{ print $1, $2 }' file.txt
  
  # 以特定分隔符分割字段
  awk -F, '{ print $1, $2 }' file.csv
  
  # 条件匹配
  awk '$2 > 30' file.txt
  
  # 打印年龄大于25的行
  awk '$2 > 25 { print $0 }' data.txt
  
  # 内置变量 如`NR`：当前行号、`NF`：当前行的字段数
  awk '{ print NR, $0 }' file.txt
  
  # 模式匹配
  awk '/Alice/' file.txt # 打印包含 "Alice" 的行
  awk '!/Alice/' file.txt # 不包含
  
  #计算年龄总和
  awk '{ sum += $2 } END { print sum }' file.txt
  
  
  # 语句块
  # BEGIN {}:处理数据行前执行,常用于初始化变量，打印表头等
  # {}:处理数据行
  # END {}:总结信息，打印总计值等
  awk 'BEGIN { print "Employee Salary"; print "-------------------"; total=0 }
       { print $1, $2; total += $2 }
       END { print "-------------------"; print "Total:", total }' file.txt
       
                
  ```



## xargs

- `xargs` 是一个非常有用的命令行工具，用于将标准输入转换为命令行参数，并执行指定的命令。它通常与其他命令结合使用，以处理由管道传递的输出。

- 应用场景

  - 有些命令不支持管道，有些命令支持管道有限，如cut、grep、sort、uniq、wc、tee、join、split
    - echo "d1" |  ls                      # ls不支持管道，对前面的内容忽略，列举当前目录内容
    - echo "d1" | xargs ls             #列出d1目录下内容
  - 加xargs扩展功能
    - find .  |  grep "ac"                 #从文件名中查找包含ac的文件名
    - find .  |  xargs   grep  "ac"    # 将find找到的内容作为参数传给grep，在文件内容找查找ac

- 3

  ```shell
  # 格式
  xargs [OPTION]... COMMAND [INITIAL-ARGUMENT]...
  
  # 常用选项
  - `-0, --null`：输入以空字符（而非换行符）分隔，通常与 `find -print0` 结合使用。
  - `-a file, --arg-file=file`：从文件读取输入。
  - `-d delimiter, --delimiter=delimiter`：指定输入的分隔符，默认为换行符。
  - `-I replace-str`：替换字符串，用于在命令中指定插入点。
  - `-L max-lines`：每次使用最多 `max-lines` 行输入执行一次命令。
  - `-n max-args, --max-args=max-args`：每次使用最多 `max-args` 个参数执行一次命令。
  - `-p, --interactive`：每次执行命令前询问用户。
  - `-r, --no-run-if-empty`：如果输入为空，不执行命令。
  - `-t, --verbose`：打印命令到标准错误输出。
  - `-P max-procs, --max-procs=max-procs`：并行运行指定数量的进程。
  - `--version`：显示版本信息并退出。
  
  
  # find并删除
  find /path/to/dir -name "*.txt" | xargs rm # 删除该目录下.txt结尾的文件
  
  # 指定分割符输出
  echo "one,two,three" | xargs -d ',' echo # one two three
  
  # 批量处理
  echo "one two three four" | xargs -n 2 echo
  # one two
  # three four
  
  # 每处理一个询问一下
  echo "a.txt b.txt" | xargs -p -n 1 cat
  
  # 从文件读取，每一行用空格分开
  # input.txt
  # this is 
  # a file
  xargs -a input.txt echo
  # this is a file
  
  # 参数占位
  echo a.txt | xargs -I {} mv {} ..  # 将a.txt移动到上一级目录
  
  # 批量打包
  find /path/to/dir -name "*.txt" | xargs tar -cvf archive.tar
  
  # 计算某一类型文件的总大小
  find /path/to/dir -name "*.log" | xargs du -ch | grep total$
  
  # 每次处理一行
  xargs -L 1 echo < a.txt
  
  # 实际应用
  
  # 批量命名
  find . -name "*.txt" | xargs -I {} mv {} {}.bak
  
  # 批量压缩
  find . -name "*.log" | xargs -n 1 gzip
  
  # 并行处理大文件
  split -l 1000 largefile segment_              # 将大文件切割为以segment为前缀的多个小文件
  ls segment_* | xargs -P 4 -n 1 some_command   # 4个线程进程处理
  ```

## 创建文件

- 7

  ```shell
  # 没有会新建
  touch c.txt
  vim c.txt
echo "this is hotfix file" >> c.txt 
  
  ```
  

## dnf-安装

- 简介

  - `dnf` 是 "Dandified Yum" 的缩写，是新一代的包管理器，旨在解决 `yum` 的一些局限性和性能问题，提供更快、更可靠和更强大的软件包管理体验
  - 它首次引入于 Fedora 系统中，并逐渐被其他基于 RPM 的 Linux 发行版采用，包括 CentOS 8 及以后版本和 RHEL（Red Hat Enterprise Linux）8及以上版本
  - 在 CentOS 8 中，`yum` 实际上仍然是可用的，因为 `yum` 命令现在只是一个指向 `dnf` 的软链接，当你使用 `yum` 时，实际上是在使用 `dnf` 的兼容模式
  - 对于大多数日常操作，如安装、更新或卸载软件包，dnf用户层面上的命令语法和使用方式与yum基本没区别

- 优点

  - 依赖解决
    - `dnf` 使用了更先进的依赖解决算法（基于libsolv库），这使得在处理复杂的依赖关系时更为精确和高效
  - 性能优化
    - `dnf` 设计了更快的数据检索和处理机制，特别是在处理大量软件仓库和包元数据时，提供了更好的性能
  - 插件系统
    - `dnf` 支持插件扩展，允许更容易地添加新功能，如模块化内容的管理、系统升级等
  - 并行下载
    - `dnf` 并行下载软件包，加快了软件安装和更新的速度

- example

  ```shell
  # 安装open-jdk11.    oracle的jdk不再是免费商业用途，需要先去官网下载相应的包，再自己安装
  dnf install java-11-openjdk java-11-openjdk-devel
  ```

  