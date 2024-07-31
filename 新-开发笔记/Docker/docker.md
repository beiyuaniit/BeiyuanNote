# ==学习网站==

- 菜鸟教程：https://www.runoob.com/docker/docker-tutorial.html

# ==Docker原理==

## 简介

- 介绍
  - Docker 是一个用于开发、交付和运行应用程序开放平台。
  - Docker 相比于虚拟机，其交付速度更快，资源消耗更低，Docker 采用客户端/服务端架构，使用远程 APl 来管理和创建 Docker 容器，其可以轻松的创建一个轻量级的、可移植的、自给自足的容器
  - Docker的三大理念是 build（构建）、ship（运输）、run（运行），Docker 遵从 apache 2.0 协议，并通过（namespace 及cgroup等）来提供容器的资源隔离与安全保障等，所以 Docker 容器在运行时不需要类似虚拟机(空运行的虚拟机占用物理机的一定性能开销)的额外资源开销，因此可以大幅提高资源利用率
  - 总而言之 Docker 是一种用了新颖方式实现的轻量级虚拟机，类似于 VM 但是在原理和应用上和 VM 的差别还是很大的，并且 Docker 的专业叫法是应用容器（Application Container）。
- Docker 的优势

  - 快速部署：短时间内可以部署成百上千个应用，更快速交付到线上。
  - 高效虚拟化：不需要额外的 hypervisor 支持，直接基于 linux 实现应用虚拟化，相比虚拟机大幅提高性能和效率。
  - 节省开支：提高服务器利用率，降低 IT 支出。
  - 简化配置：将运行环境打包保存至容器，使用时直接启动即可。
  - 快速迁移和扩展：可夸平台运行在物理机、虚拟机、公有云等环境，良好的兼容性可以方便将应用从A宿主机迁移到B宿主机，甚至是A平台迁移到B平台。

- Docker 的缺点
  - 隔离性：各应用之间的隔离不如虚拟机彻底

## 组成

- 组成
  - Docker 主机（Host）：一个物理机或虚拟机，用于运行 Docker 服务进程和容器。
  - Docker 服务端（Server）：Docker 守护进程，运行 Docker 容器。
  - Docker 客户端（Client）：客户端使用 Docker 命令或其他工具调用 Docker API。
  - Docker 仓库（Registry）：保存镜像的仓库，类似于 git 或 svn 这样的版本控制系统，官方仓库：[https://hub.docker.com](https://link.zhihu.com/?target=https%3A//hub.docker.com)。
  - Docker 镜像（lmages）：镜像可以理解为创建实例使用的模板。
  - Docker 容器（Container）：容器是从镜像生成对外提供服务的一个或一组服务。
- ![img](https://pic1.zhimg.com/80/v2-14ca0813bc081e5288e3810fc1460194_720w.webp)

- ![img](https://pic2.zhimg.com/80/v2-27483a803c298bb4087cb7013daf3b4d_720w.webp)

## Linux Nmaespace 

- NameSpace

  - Namespace 是 Linux 系统的底层概念，在内核层实现，即有一些不同类型的命名空间被部署在核内，各个 Docker 容器运行在同一个 Docker 主进程并且共用同一个宿主机系统内核，各 Docker 容器运行在宿主机的用户空间，每个容器都要有类似于虚拟机一样的相互隔离的运行空间，但是容器技术是在一个进程内实现运行指定服务的运行环境，并且还可以保护宿主机内核不受其他进程的干扰和影响，如文件系统空间、网络空间、进程空间等，目前主要通过以下技术实现容器运行空间的相互隔离：
  - ![img](https://pic4.zhimg.com/80/v2-005b39840a17f987b0a77525fd161567_720w.webp)

- 分类

  - MNT Namespace

    - 每个容器都要有独立的根文件系统、独立的用户空间，以实现在容器里面启动服务，并且使用容器的运行环境，即一个宿主机是 Ubuntu 的服务器，可以在里面启动一个 Centos 运行环境的容器，并且在容器里面运行一个Nginx 服务，此 Nginx 运行时使用的运行环境就是 Centos 系统目录的运行环境，但是在容器里面是不能访问宿主机的资源，宿主机是使用了 chroot 技术把容器锁定到一个指定的运行目录里面

  - IPC NameSpace

    - 一个容器内的进程间通信，允许一个容器内的不同进程的（内存、缓存等）数据访问，但是不能夸容器访问其他容器的数据。

  - UTS NameSpace

    - UTS Namespace（UNIX Timesharing System 包含了运行内核的名称、版本、底层体系结构类型等信息）用于系统标识，其中包含了 hostname 和域名domainname ，它使得一个容器拥有属于自己 hostname 标识，这个主机名标识独立于宿主机系统和其上的其他容器。

  - PID NameSpace

    - Linux系统中，有一个 PID 为 1 的进程（init/systemd）是其他所有进程的父进程，那么在每个容器内也要有一个父进程来管理其下属的子进程，那么多个容器的进程通 PID Namespace 进程隔离（比如 PID 编号重复，容器内的主进程生成与回收子进程等）。

      

    - 例如：下图是在一个容器内使用 top 命令看到的 PID 为 1 的进程是 Nginx

      ![img](https://pic3.zhimg.com/80/v2-a068420105f5efa8c4182ccab9573b22_720w.webp)

    - 宿主机与容器内 PID 的关系

      ![img](https://pic4.zhimg.com/80/v2-566817d84bf0b6ce4f7f0f3d1f1c35e3_720w.webp)

  - Net Namespace

    - 每一个容器都类似于虚拟机一样有自己的网卡、监听端口、TCP/IP协议栈等，Docker 使用 Network Namespace 启动一个 vethX 接口，这样你的容器将拥有它自己的桥接 ip 地址，通常是 docker 0，而 docker 0 实质就是 Linux 的虚拟网桥网桥是在 OSI 七层模型的数据链路层的网络设备，通过 mac 地址对网络进行划分，并且在不同网络直接传递数据。

    - 使用 ip a 查看宿主机的网卡信息：

      

      ![img](https://pic2.zhimg.com/80/v2-e067d3b0731619bf65992574d39ae605_720w.webp)

    - 逻辑网络图

      ![img](https://pic4.zhimg.com/80/v2-dde4067599b5d83886a972ea79497273_720w.webp)

    - 宿主机 iptables 规则，使用命令 iptables -t nat -vnL 查看

      ![img](https://pic2.zhimg.com/80/v2-f4ecc9472c9c7590f8df7e3b52cdb129_720w.webp)

  - User Namespace

    - 各个容器内可能会出现重名的用户和用户组名称，或重复的用户 UID 或者 GID，那么怎么隔离各个容器内的用户空间呢
      - User Namespace 允许在各个宿主机的各个容器空间内，创建相同的用户名以及相同的用户 UID 和 GID，只是会把用户的作用范围限制在每个容器内，即 A 容器和 B 容器可以有相同的用户名称和 ID 的账户，但是此用户的有效范围仅是当前容器内，不能访问另外一个容器内的文件系统，即相互隔离、互不影响、永不相见。

## Linux control groups

- why
  
  - 在一个容器，如果不对其做任何资源限制，则宿主机会允许其占用无限大的内存空间，有时候会因为代码 bug 程序会一直申请内存，直到把宿主机内存占完，为了避免此类的问题出现，宿主机有必要对容器进行资源分配限制，比如CPU内存等
- what
  
- Linux Cgroups 的全称是 Linux Control Groups，它最主要的作用，就是限制一个进程组能够使用的资源上限，包括CPU、内存、磁盘、网络带宽等等。此外，还能够对进程进行优先级设置，以及将进程挂起和恢复等操作。
  
- Cgroups 在内核层默认已经开启，使用命令 cat /boot/config-3.10.0-1160.el7.x86_64 | grep CGROUP 查看内核配置文件：
  ![img](https://pic2.zhimg.com/80/v2-0d62e8195894342e27c00b4357bc8ca1_720w.webp)

- cgroups 具体实现
  - blkio：块设备 IO 限制。
  - cpu：使用调度程序为 cgroup 任务提供 cpu 的访问。
  - cpuacct：产生 cgroup 任务的 cpu 资源报告。
  - cpuset：如果是多核心的 cpu，这个子系统会为 cgroup 任务分配单独的 cpu 和内存。
  - devices：允许或拒绝 cgroup 任务对设备的访问。
  - freezer：暂停和恢复 cgroup 任务。
  - memory：设置每个 cgroup 的内存限制以及产生内存资源报告。
  - net_cls：标记每个网络包以供 cgroup 方便使用。
  - ns：命名空间子系统。
  - perf_event：增加了对每 group 的监测跟踪的能力，可以监测属于某个特定的 group 的所有线程以及运行在特定 CPU 上的线程。

- 使用命令 ll /sys/fs/cgroup/ 查看系统 cgroups

  ![img](https://pic2.zhimg.com/80/v2-6a217c8d0d079345cfc9fa8a557b98b5_720w.webp)



## Docker核心技术

- 容器规范
  - 容器技术除了的 Docker 之外，还有 CoreOS 的 Rkt，还有阿里的Pouch，为了保证容器生态的标准性和健康可持续发展，包括 Linux 基金会、Docker、微软、红帽、谷歌和IBM等公司，在 2015 年6 月共同成立了一个叫 Open Container（OCI）的组织，其目的就是制定开放的标准的容器规范，目前 OCI 一共发布了两个规范，分别是 runtime spec 和 image format spec，有了这两个规范，不同的容器公司开发的容器只要兼容这两个规范，就可以保证容器的可移植性和相互可操作性。

- 容器 runtime（runtime spec）
  - runtime 是真正运行容器的地方，因此为了运行不同的容器 runtime 需要和操作系统内核紧密合作相互在支持，以便为容器提供相应的运行环境
  - 目前主流的三种 runtime：

    - Lxc：linux 上早期的 runtime，Docker 早期就是采用 lxc 作为 runtime。
    - runc：目前 Docker 默认的 runtime，runc 遵守 OCI 规范，因此可以兼容 Ixc。
    - rkt：是 CoreOS 开发的容器 runtime，也符合OCl规范，所以使用rktruntime也可以运行 Docker 容器。

  - runtime 主要定义了以下规范，并以 json 格式保存在 /run/docker/runtime-runc/moby/容器ID/state.json 文件，此文件会根据容器的状态实时更新内容：

    - 版本信息：存放 OCI 标准的具体版本号。
    - 容器 ID：通常是一个哈希值，可以在所有 state.json 文件中提取出容器 ID 对容器进行批量操作（关闭、删除等），此文件在容器关闭后会被删除，容器启动后会自动生成。
    - PID：在容器中运行的首个进程在宿主机上的进程号，即将宿主机的那个进程设置为容器的守护进程。
    - 容器文件目录：存放容器 rootfs 及相应配置的目录，外部程序只需读取 state.json 就可以定位到宿主机上的容器文件目录。
    - 容器创建：创建包括文件系统、namespaces、cgroups、用户权限在内的各项内容。
    - 容器进程的启动：运行容器启动进程，该文件在/run/containerd/io.containerd.runtime.v1.linux/moby/容器ID/config json。
    - 容器生命周期：容器进程可以被外部程序关停，runtime 规范定义了对容器操作信号的捕获，并做相应资源回收的处理，避免僵尸进程的出现。

- 容器镜像（image format spec)
  - OCl 容器镜像主要包含以下内容:
    - 文件系统：定义以 layer 保存的文件系统，在镜像里面是 layer.tar，每个 layer 保存了和上层之间变化的部分，image format spec 定义了 layer 应该保存哪些文件，怎么表示增加、修改和删除的文件等操作。
    - manifest 文件：描述有哪些 layer，tag 标签及 config 文件名称。
    - config 文件：是一个以 hash 命名的 json 文件，保存了镜像平台，容器运行时容器运行时需要的一些信息，比如环境变量、工作目录、命令参数等。
    - index 文件：可选的文件，指向不同平台的 manifest 文件，这个文件能保证一个镜像可以跨平台使用，每个平台拥有不同的 manifest 文件使用 index 作为索引。
    - 父镜像：大多数层的元信息结构都包含一个 parent 字段，指向该镜像的父镜像。

  - 参数
    - ID：镜像 ID，每一层都有 ID
    - tag 标签：标签用于将用户指定的，具有描述性的名称对应到镜像 ID
    - 仓库：Repository 镜像仓库
    - OS：定义类型
    - architecture：定义 CPU 架构
    - author：作者信息
    - create：镜像创建日期

- 容器管理工具
  - 管理工具连接 runtime 与用户，对用户提供图形或命令方式操作，然后管理工具将用户操作传递给 runtime 执行。
  - lxc 是 lxd 的管理工具。
  - Runc 的管理工具是 docker engine，docker engine 包含后台 deamon 和 cli 两部分，大家经常提到的 Docker 就是指的 docker engine。
  - Rkt 的管理工具是 rkt cli。

- 容器定义工具
  - 容器定义工具允许用户定义容器的属性和内容，以方便容器能够被保存、共享和重建。

  - Docker image：是 Docker 容器的模板，runtime 依据 docker image 创建容器。
  - Dockerfile：包含N个命令的文本文件，通过 Dockerfile 创建出 docker image。
  - ACl（App container image）：与 docker image 类似，是CoreOS开发的 rkt 容器的镜像格式。

- Registry

  - 统一保存镜像而且是多个不同镜像版本的地方，叫做镜像仓库。

  - Image Registry：Docker 官方提供的私有仓库部署工具。
  - Docker Hub：Docker官方的公共仓库，已经保存了大量的常用镜像，可以方便大家直接使用，地址：https://hub.docker.com。
  - Harbor：Vmware 提供的自带 Web 界面自带认证功能的镜像仓库，目前有很多公司使用。

- 编排工具
  - 当多个容器在多个主机运行的时候，单独管理容器是相当复杂而且很容易出错，而且也无法实现某一台主机宕机后容器自动迁移到其他主机从而实现高可用的目的，也无法实现动态伸缩的功能，因此需要有一种工具可以实现统一管理。动态伸缩，故障自愈，批量执行等功能，这就是容器编排引擎。
  - 容器编排通常包括容器管理、调度、集群定义和服务发现等功能
    - Docker swarm：Docker 开发的容器编排引擎。
    - Kubernetes：Google 领导开发的容器编排引擎，内部项目为 Borg，且其同时支持 Docker 和 CoreOS。
    - Mesos+Marathon：通用的集群组员调度平台，mesos（资源分配）与 marathon（容器编排平台）一起提供容器编排引擎功能。

## Docker依赖技术

- **容器网络：**Docker 自带的网络 docker network 仅支持管理单机上的容器网络，当多主机运行的时候需要使用第三方开源网络，例如calico、flannel 等。
- **服务发现：**容器的动态扩容特性决定了容器 IP 也会随之变化，因此需要有一种机制可以自动识别并将用户请求动态转发到新创建的容器上，kubernetes 自带服务发现功能，需要结合 kube-dns 服务解析内部域名。
- **容器监控：**可以通过原生命令 docker ps/top/stats 查看容器运行状态，另外也可以使 heapster/ Prometheus 等第三方监控工具监控容器的运行状态。
- **数据管理：**容器的动态迁移会导致其在不同的 Host 之间迁移，因此如何保证与容器相关的数据也能随之迁移或随时访问，可以使用逻辑券/存储挂载等方式解决。
- **日志收集：**Docker 原生的日志查看工具 docker logs，但是容器内部的日志需要通过ELK等专门的日志收集分析和展示工具进行处理。

## 联合文件系统

- Union File System UFS 让多个镜像层的文件和目录看起来像是一个整体，使得容器可以共享相同的基础镜像层，并在其上添加各自的可写层
  - 基础镜像层（Base Image Layer）： Docker容器的文件系统始于一个基础镜像。这个基础镜像包含了操作系统的文件和目录结构。Docker镜像采用分层的方式组织，每一层都是只读的。
  - 容器可写层（Container Writable Layer）： 当你运行一个容器时，Docker会在基础镜像之上添加一个可写层。这一层是容器的文件系统的一部分，允许在运行时对文件系统进行修改。任何在容器中创建、修改或删除的文件都存储在这个可写层中。
- 好处
  - 资源共享： 多个容器可以共享相同的基础镜像，从而减少磁盘空间的使用和加快镜像的下载速度。
  - 轻量级： 每个容器只需存储自己的可写层，使得容器非常轻量且启动迅速。
  - 容器隔离： 每个容器都有自己的可写层，使得容器之间的文件系统修改互不影响，实现文件系统的隔离

# ==Docker基础==

## 安装卸载

- 方法一

  - 直接上官网：https://docs.docker.com/engine/install/centos/

  - 设置下阿里云镜像库

    ```shell
    sudo yum-config-manager --add-repo http://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo
    ```
    
  - 数据目录在  /var/lib/docker
  
  - 卸载官网也有
  
- 最近国内镜像被禁了

  - 配置点其他镜像源还能用下

  ```shell
  vim /etc/docker/daemon.json
  # 输入
  {
      "registry-mirrors": [
          "https://docker.m.daocloud.io",
          "https://docker.1panel.live",
          "https://hub.rat.dev",
  		"https://dockerpull.com",
  		"https://docker.1panel.live",
  		"https://dockerproxy.cn",
  		"https://docker.hpcloud.cloud",
  		"https://atomhub.openatom.cn/repos/library/docker"
      ]
  }
  ```

  

## 虚悬镜像

- dangling image

  - 指的是那些没有与任何标签（tag）关联的镜像。一个镜像可以有多个标签，但是当一个镜像没有任何标签与之关联时，它就被认为是虚悬的。

- 产生原因

  - 构建时不加名和标签

    ```shell
    # Dockerfile
    from redis:7
    CMD echo "build success"
    
    # 构建，这里有个点
    docker build .
    
    #
    REPOSITORY                      TAG       IMAGE ID       CREATED         SIZE
    <none>                          <none>    3d7ef130b1ae   6 weeks ago     138MB
    ```

  - 只删除镜像标签

    - 该镜像不被标签关联了

      ```shell
      docker rmi hello-world:latest 
      
      Untagged: hello-world:latest
      Untagged: hello-world@sha256:4bd78111b6914a99dbc560e6a20eab57ff6655aea4a80c50b0c5491968cbc2e6
      Deleted: sha256:d2c94e258dcb3c5ac2798d32e1249e42ef01cba4841c2234249495f87264ac5a
      Deleted: sha256:ac28800ec8bb38d5c35b49d45a6ac4777544941199075dff8c4eb63e093aa81e
      
      Untagged后的镜像是虚悬镜像
      但是随后又被删除了？所以后面查看时才看不到？
      
      #下面这两个镜像构建时使用的是完全相同的Dockerfile,先构建的redis7ll:latest。删除时的信息如下
      # docker rmi redis7ll:01
      Untagged: redis7ll:01
      # docker rmi redis7ll:latest
      Untagged: redis7ll:latest
      Deleted: sha256:3d7ef130b1aee2cddc457a19ea5b2b1823eac3cc31fa92291b86bcd166024203
      ```

- 查看

  ```shell
  docker images -f "dangling=true"
  ```

- 清除

  ```shell
  docker image prune             #这里的image可没有s
  ```

- 关于Untagged

  - Untagged”指的是镜像失去了标签关联的状态，镜像是通过唯一的ID来标识的，同时也可以通过一个或多个标签来进行命名和引用

  - Untagged状态的情况

    - 删除标签关联：
      - 当你执行 `docker rmi` 命令删除一个标签时，如果没有其他标签指向该镜像，镜像本身并不会被立即删除，而是变为无标签（Untagged）状态。直到没有任何标签指向它，并且没有容器在使用它时，才会被Docker的垃圾回收机制自动清理
    - 镜像推送与拉取
      - 在进行镜像推送时，如果使用 `docker push` 并指定了新的标签，原有的标签可能因此失去关联（Untagged）。
      - 同样，在拉取镜像时，如果本地已存在同ID的镜像但标签不同，拉取后本地的原有标签可能被替换，导致原标签与镜像失去关联。

    - 构建新镜像
      - 当你在执行 `docker build` 命令构建新的镜像时，如果指定了与旧镜像相同的标签，旧镜像的标签会被新构建的镜像取代，旧镜像也会成为Untagged状态。

  - 总结

    - "Untagged"意味着镜像虽然还在Docker的存储中，但由于没有标签与之关联，无法通过标签名来正常引用和管理。
    - 在日常管理中，为了节省存储空间和维护镜像列表的整洁，通常需要定期清理这些无标签的镜像。
    - 可以使用 `docker images` 查看无标签的镜像（它们通常会显示 `<none>` 作为标签），并通过 `docker rmi $(docker images -q -f "dangling=true")` 删除这些无标签（Dangling）的镜像

## 镜像发布

- 发布至官方库

  - docker hub
  - 阿里云

- 本地私有库

  - 拉取官方的私有库构建工具

    ```shell
    docker pull registry
    ```

  - 启动

    ```shell
    docker run -d -p 5000:5000 --privileged=true --name=myregistry -v /home/beiyuanii/Desktop/my_registry/:/tmp/regestry registry 
    ```

  - 给镜像加上仓库地址端口

    ```shell
    docker tag beiyuan/redis1:01 localhost:5000/beiyuan/redis1:01
    ```

  - 镜像推送到仓库

    ```shell
    docker push localhost:5000/beiyuan/redis1:01
    ```

  - 查看仓库中的镜像

    ```shell
    curl -X GET localhost:5000/v2/_catalog
    ```

  - 拉取镜像

    ```shell
    docker rm localhost:5000/beiyuan/redis1:01
    
    #删除后还能拉回来
    docker pull localhost:5000/beiyuan/redis1:01
    ```

    

- 添加证书加密、用户认证

  - https://blog.51cto.com/u_16099296/7556272
  - 自己看

## 容器卷

- what

  - Docker容器卷（Docker volumes）是文件或目录，由docker挂载到容器
  - 容器卷机制用于在Docker容器和主机之间共享和持久化数据，提供了一个可在多个容器之间共享数据的持久性存储解决方案

- 特点

  - 数据持久性：Docker卷存储在主机文件系统上，因此它们的生命周期可以超出单个容器的生命周期。即使容器被删除，卷中的数据仍然保留。
  - 容器间共享数据： 多个容器可以共享同一个卷，这样它们可以轻松地交换数据。这对于在多个容器之间共享配置文件、日志文件或其他持久性数据非常有用。
  - 更高的性能：与将数据复制到容器的文件系统相比，使用卷通常具有更高的性能，因为它避免了容器文件系统的额外复杂性,卷中的文件直接映射到容器内，而对卷的任何更改都会立即在容器中反映出来。但是否实时生效还得看应用是否是热部署
  - 支持多种卷驱动：这意味着可以选择适合特定应用需求的存储后端，例如本地存储、网络存储、云存储等

- 创建

  - 直接创建

    ```shell
    Usage:  docker volume COMMAND
    Manage volumes
    Commands:
      create      Create a volume
      inspect     Display detailed information on one or more volumes
      ls          List volumes
      prune       Remove unused local volumes
      rm          Remove one or more volumes
    
    Run 'docker volume COMMAND --help' for more information on a command.
    
    example
    docker volume create my_volume
    默认放在了/var/lib/docker/volumes/my_volume/_data
    
    docker volume prune     #没有删除my_volume
    ```

  - 运行时创建

    ```shell
    docker run -v my_volume:/path/in/container my_image  #
    docker run -v /host/path:/container/path my_image #挂载主机目录作为卷
    是否创建了新卷？
    主机目录删除后？
    ```

  - 使用`docker-compose`中的`volumes`关键字

    ```shell
    version: '3'
    services:
      my_service:
        image: my_image
        volumes:
          - my_volume:/path/in/container
    volumes:
      my_volume:
    ```

- 卷驱动方式

  - Docker 支持多种卷驱动方式，其中卷驱动方式是指用于实现卷的具体技术或后端存储系统。不同的卷驱动方式提供了不同的功能和性能特性

  - local

    - 使用本地存储作为卷的后端。这是默认的卷驱动方式，数据存储在主机上的本地文件系统中。

    ```
    bashCopy codedocker volume create my_volume
    docker run -v my_volume:/path/in/container my_image
    ```

  - bind

    -  将主机上的文件或目录直接绑定到容器中，不创建独立的卷对象。这样容器和主机共享相同的文件或目录。

    ```
    bashCopy code
    docker run -v /host/path:/container/path my_image
    ```

  - nfs

    -  使用 Network File System（NFS）作为卷的后端，允许将数据存储在远程 NFS 服务器上。

    ```
    bashCopy codedocker volume create --driver local \
      --opt type=nfs \
      --opt o=addr=nfs-server-address,rw \
      --opt device=:/path/on/nfs/server \
      my_nfs_volume
    
    docker run -v my_nfs_volume:/path/in/container my_image
    ```

  - cloudstor（Docker for AWS/Azure/GCP）

    - 用于将 Docker 卷与云存储服务集成，例如 Amazon EBS、Azure Disk、Google Cloud Persistent Disk。

    ```
    bashCopy codedocker volume create --driver cloudstor:aws \
      --opt backing=relocatable \
      my_cloud_volume
    
    docker run -v my_cloud_volume:/path/in/container my_image
    ```

- 怎么查看跟容器的关联关系？

- 其他

  - 只读卷。默认rw读写

    ```shell
    docker run -v my_volume:/path/in/container:ro my_image
    ```

  - 生命周期

    ```shell
    # 删除卷
    docker volume rm my_volume
    # 清理没有关联容器的卷
    docker volume prune
    ```

  - 挂载多个

    ```shell
    docker run -v my_volume01:/path/in/container -v my_volume02:/path/in/container my_image  
    ```

  - 如果只是需要在容器中挂载卷来共享数据，通常不需要 `--privileged`

  - 目录还是文件

    ```shell
    主机没有，则会创建一个目录。  若此时宿主机是文件则会报错
    主机有，则文件目录都可以
    ```

  - 覆盖性

    ```shell
    主机的会优先覆盖宿主机的
    
    如
    主机有个空白的 /beimysql/mysql-master/conf/my.cnf
    而docker run mysql:8 生成一个默认的/etc/my.cnf
    若两者挂载，则会导致/etc/my.cnf中内容替换成主机中空白的my.cnf
    实在想要原来默认的，则先运行一下，docker cp复制一个出来。
    ```

  - 数据同步性

    ```shell
    如果挂载的是目录，则目录中的文件都能够实时双向生效
    vi，vim修改导致文件的inode变化也会实时反应
    如："/beimysql/mysql-master/log:/var/log/mysql",
    通过stat命令查看目录的inode，两个目录的inode一致。
    
    挂载的是文件，则主机vim修改后，容器内并没有看到更新，推测原因可能是vim改变了inode
    "/beimysql/mysql-master/conf/my.cnf:/etc/my.cnf"
    ```

## 容器网络模式

- Docker 提供了多种网络模式来配置容器的网络环境，以便容器之间、容器与主机以及其他网络实体之间的通信。以下是一些主要的Docker容器网络模式：

  1. **桥接（Bridge）网络**
     
     - 这是Docker默认的网络模式。
     
     - Docker在主机上创建一个名为`docker0`的虚拟网桥，这个网桥作为Linux内核中的一个网络设备，用于将容器的网络与宿主机以及外部网络连接起来，并被分配一个唯一的IP地址，从而实现容器间的隔离通信。
     
     - 容器可以相互通信，同时可以通过端口映射与主机上的服务进行交互，并且如果配置正确，也可以访问外部网络
     
     - 如果需要将容器连接到自定义的桥接网络，可以这样做：
     
       ```shell
       docker network create my_bridge_network
       docker run --name my_container --network=my_bridge_network image_name
       
       #查看在该隔离网络中的ip
       docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' my_container
       ```
     
  2. **Host（主机）网络**
     
     - 使用此模式时，容器共享宿主机的网络栈，即容器直接使用主机的网络接口和IP地址。

     - 容器内的网络服务将直接监听在主机网络接口上，没有网络隔离，容器间通信就像在同一台机器上运行多个进程一样。
     
       ```shell
       docker run --name my_container --network=host image_name
       ```
     
  3. **None网络**
     - 在这种模式下，容器不会分配任何网络接口，也不会加入到任何网络中。

     - 容器内部不会有网络功能，通常用于仅需文件系统或其他资源但不需要网络通信的场景。

       ```shell
       docker run --name my_container --network none image_name
       ```

  4. **Container网络**
     
  - 在Docker的网络模式中，container模式允许新创建的容器共享另一个已经运行容器的网络命名空间
    
  - 共享ip、端口等
    
       ```shell
       docker run --name my_container -net container:<target-container-id-or-name> image_name
       ```
  
  5. **Overlay网络**

     - 适用于多主机场景，支持跨多个Docker主机组建一个逻辑覆盖网络，使得不同物理主机上的容器能够互相通信，如同在一个大型的LAN内。

     - Docker Swarm服务编排工具利用Overlay网络实现集群内容器的服务发现和通信。

       ```shell
       # 创建一个Overlay网络
       docker network create --driver overlay my_overlay_network
       
       # 运行容器并连接到该网络
       docker service create --network=my_overlay_network --name my_service image_name
       ```

  6. **IPvLAN 和 Macvlan**

     - 这两种模式允许容器获取一个与物理网络其他部分相匹配的独立MAC和/或IP地址。

     - Macvlan特别适合于需要容器直接作为物理网络中的一个独立节点出现的情况，例如当需要容器拥有与物理服务器相同的网络可见性时。

       ```shell
       # 创建Macvlan网络（假设eth0是宿主机物理网卡）
       docker network create -d macvlan --subnet=192.168.1.0/24 --gateway=192.168.1.1 -o parent=eth0 my_macvlan_network
       
       # 将容器连接到该网络
       docker run --name my_container --network=my_macvlan_network imagename
       ```


-  Docker自身并不直接支持通过 `docker run` 命令为容器分配静态IP地址

  - **静态IP分配**：您可以创建自定义网络并预先配置子网和IP地址范围，在创建网络时指定每个容器的IP。

    创建一个带有预定义IP地址范围的网络（仅示例）：

    Bash

    ```bash
    1docker network create --subnet=172.20.0.0/16 --ip-range=172.20.10.0/24 --gateway=172.20.10.254 redis-cluster-network
    ```

    然后，在启动容器时，可以尝试结合 `-e` 参数设置环境变量，如果应用程序或服务能够识别这些环境变量来配置自身的IP地址，但这并不是直接指定容器IP的方式。

  - **第三方解决方案**： 有一些第三方工具如 [docker-ipam](https://github.com/jdeathe/docker-ipam) 可以帮助实现静态IP地址分配，但不是原生Docker的功能。

  - **使用Swarm模式或Kubernetes等编排工具**： 在Docker Swarm或Kubernetes集群中，可以通过服务或Pod的配置文件指定固定的IP地址或者IP地址范围。

  - **对于Redis集群**，通常建议让Docker自动管理网络，并通过内部的服务发现机制进行节点间的通信。如果你确实需要固定IP地址，可能需要更复杂的网络配置方案，比如利用Docker Compose或高级网络插件功能。但在大多数情况下，保持由Docker自动分配网络资源并使用**容器名**进行连接是更为推荐的做法。

- 网络管理

  ```shell
  Usage:  docker network COMMAND
  
  Manage networks
  
  Commands:
    connect     Connect a container to a network
    create      Create a network
    disconnect  Disconnect a container from a network
    inspect     Display detailed information on one or more networks
    ls          List networks
    prune       Remove all unused networks
    rm          Remove one or more networks
  
  Run 'docker network COMMAND --help' for more information on a command.
  
  # docker network ls
  NETWORK ID     NAME                    DRIVER    SCOPE
  efa4770b95a5   bridge                  bridge    local
  4b9bf65264b7   host                    host      local
  3a83ece682ea   none                    null      local
  dfa5673cf7e3   redis-cluster-network   bridge    local
  
  # docker network inspect dfa
  [
      {
          "Name": "redis-cluster-network",
          "Id": "dfa5673cf7e395313d5b31853477b7e91c0ae31773b2d64700cc1477399b4b00",
          "Created": "2024-02-02T17:50:20.915693564+08:00",
          "Scope": "local",
          "Driver": "bridge",
          "EnableIPv6": false,
          "IPAM": {
              "Driver": "default",
              "Options": {},
              "Config": [
                  {
                      "Subnet": "172.18.0.0/16",
                      "Gateway": "172.18.0.1"
                  }
              ]
          },
          "Internal": false,
          "Attachable": false,
          "Ingress": false,
          "ConfigFrom": {
              "Network": ""
          },
          "ConfigOnly": false,
          "Containers": {},
          "Options": {},
          "Labels": {}
      }
  ]
  ```

  



# ==命令==

## 启动类

- 启动

  ```shell
  systemctl start docker
  ```

- 开机自启

  ```shell
  systemctl enable docker
  ```

- 重启

  ```shell
  systemctl restart docker
  ```

- 查看状态

  ```shell
  systemctl status docker
  ```

- 关闭

  ```shell
  systemctl stop docker
  ```

## 概要信息

- 总

  ```shell
  Usage:  docker [OPTIONS] COMMAND
  A self-sufficient runtime for containers
  Common Commands:
    run         Create and run a new container from an image
    exec        Execute a command in a running container
    ps          List containers
    build       Build an image from a Dockerfile
    pull        Download an image from a registry
    push        Upload an image to a registry
    images      List images
    login       Log in to a registry
    logout      Log out from a registry
    search      Search Docker Hub for images
    version     Show the Docker version information
    info        Display system-wide information
  
  Management Commands:
    builder     Manage builds
    buildx*     Docker Buildx (Docker Inc., v0.11.2)
    compose*    Docker Compose (Docker Inc., v2.21.0)
    container   Manage containers
    context     Manage contexts
    image       Manage images
    manifest    Manage Docker image manifests and manifest lists
    network     Manage networks
    plugin      Manage plugins
    system      Manage Docker
    trust       Manage trust on Docker images
    volume      Manage volumes
  
  Swarm Commands:
    swarm       Manage Swarm
  Commands:
    attach      Attach local standard input, output, and error streams to a running container
    commit      Create a new image from a container's changes
    cp          Copy files/folders between a container and the local filesystem
    create      Create a new container
    diff        Inspect changes to files or directories on a container's filesystem
    events      Get real time events from the server
    export      Export a container's filesystem as a tar archive
    history     Show the history of an image
    import      Import the contents from a tarball to create a filesystem image
    inspect     Return low-level information on Docker objects
    kill        Kill one or more running containers
    load        Load an image from a tar archive or STDIN
    logs        Fetch the logs of a container
    pause       Pause all processes within one or more containers
    port        List port mappings or a specific mapping for the container
    rename      Rename a container
    restart     Restart one or more containers
    rm          Remove one or more containers
    rmi         Remove one or more images
    save        Save one or more images to a tar archive (streamed to STDOUT by default)
    start       Start one or more stopped containers
    stats       Display a live stream of container(s) resource usage statistics
    stop        Stop one or more running containers
    tag         Create a tag TARGET_IMAGE that refers to SOURCE_IMAGE
    top         Display the running processes of a container
    unpause     Unpause all processes within one or more containers
    update      Update configuration of one or more containers
    wait        Block until one or more containers stop, then print their exit codes
  
  Global Options:
        --config string      Location of client config files (default "/root/.docker")
    -c, --context string     Name of the context to use to connect to the daemon (overrides DOCKER_HOST env var and default context set with "docker
                             context use")
    -D, --debug              Enable debug mode
    -H, --host list          Daemon socket to connect to
    -l, --log-level string   Set the logging level ("debug", "info", "warn", "error", "fatal") (default "info")
        --tls                Use TLS; implied by --tlsverify
        --tlscacert string   Trust certs signed only by this CA (default "/root/.docker/ca.pem")
        --tlscert string     Path to TLS certificate file (default "/root/.docker/cert.pem")
        --tlskey string      Path to TLS key file (default "/root/.docker/key.pem")
        --tlsverify          Use TLS and verify the remote
    -v, --version            Print version information and quit
  
  Run 'docker COMMAND --help' for more information on a command.
  
  For more help on how to use Docker, head to https://docs.docker.com/go/guides/
  
  
  ```

- 概要信息

  ```shell
  docker info
  ```

- 总文档

  ```shell
  docker --help
  ```

- 某个具体命令

  ```shell
  docker command --help
  ```

- 版本

  ```shell
  docker version
  ```

- docker系统空间

  ```shell
  docker system df
  ```

## 镜像管理

- Docke 的镜像可以保存在一个公共的地方存储共享（镜像仓库），只要把镜像下载下来就可以使用，最主要的是可以在镜像基础之上做自定义配置，并且可以再把其提交为一个新的镜像，一个镜像可以启动多个容器。

- Docker 的镜像是分层的，镜像底层为库文件且只读层，即不能写入也不能删除数据，从镜像加载启动为一个容器后会生成一个可写层，其写入的数据会复制到容器目录，但是容器内的数据在删除容器后也会被随之删除。

- 镜像管理

  ```shell
  Usage:  docker image COMMAND
  Manage images
  Commands:
    build       Build an image from a Dockerfile
    history     Show the history of an image
    import      Import the contents from a tarball to create a filesystem image
    inspect     Display detailed information on one or more images
    load        Load an image from a tar archive or STDIN
    ls          List images
    prune       Remove unused images
    pull        Download an image from a registry
    push        Upload an image to a registry
    rm          Remove one or more images
    save        Save one or more images to a tar archive (streamed to STDOUT by default)
    tag         Create a tag TARGET_IMAGE that refers to SOURCE_IMAGE
  
  Run 'docker image COMMAND --help' for more information on a command.
  
  有些可以简写
  docker image save -> docker save
  有些不可以
  docker image prune
  ```

- 查看

  ```shell
  docker images
  tag:镜像版本
  ```

- 搜索

  ```shell
  Usage:  docker search [OPTIONS] TERM
  Search Docker Hub for images
  Options:
    -f, --filter filter   Filter output based on conditions provided
        --format string   Pretty-print search using a Go template
        --limit int       Max number of search results
        --no-trunc        Don't truncate output
  
  example:
  docker search --limit 5 redis
  OFFICIAL是[OK]的表示是官方镜像
  ```

- 拉取

  ```shell
  Usage:  docker pull [OPTIONS] NAME[:TAG|@DIGEST]
  Download an image from a registry
  Aliases:
    docker image pull, docker pull
  Options:
    -a, --all-tags                Download all tagged images in the repository
        --disable-content-trust   Skip image verification (default true)
        --platform string         Set platform if server is multi-platform capable
    -q, --quiet                   Suppress verbose output
  
  不加tag默认是latest最新版本
  ```

- 删除

  ```shell
  Usage:  docker rmi [OPTIONS] IMAGE [IMAGE...]
  Remove one or more images
  Aliases:
    docker image rm, docker image remove, docker rmi
  Options:
    -f, --force      Force removal of the image
        --no-prune   Do not delete untagged parents
  
  单个:docker rmi -f imageId:tag
  多个:docker rmi -f imageId1:tag imageId2:tag
  全部:docker rmi -f $(docker images -qa)
  ```

- 运行

  ```shell
  Usage:  docker run [OPTIONS] IMAGE [COMMAND] [ARG...]
  Create and run a new container from an image
  Aliases:
    docker container run, docker run
  Options:
        --add-host list                  Add a custom host-to-IP mapping (host:ip)
        --annotation map                 Add an annotation to the container (passed through to the OCI runtime) (default map[])
    -a, --attach list                    Attach to STDIN, STDOUT or STDERR
        --blkio-weight uint16            Block IO (relative weight), between 10 and 1000, or 0 to disable (default 0)
        --blkio-weight-device list       Block IO weight (relative device weight) (default [])
        --cap-add list                   Add Linux capabilities
        --cap-drop list                  Drop Linux capabilities
        --cgroup-parent string           Optional parent cgroup for the container
        --cgroupns string                Cgroup namespace to use (host|private)
                                         'host':    Run the container in the Docker host's cgroup namespace
                                         'private': Run the container in its own private cgroup namespace
                                         '':        Use the cgroup namespace as configured by the
                                                    default-cgroupns-mode option on the daemon (default)
        --cidfile string                 Write the container ID to the file
        --cpu-count int                  CPU count (Windows only)
        --cpu-percent int                CPU percent (Windows only)
        --cpu-period int                 Limit CPU CFS (Completely Fair Scheduler) period
        --cpu-quota int                  Limit CPU CFS (Completely Fair Scheduler) quota
        --cpu-rt-period int              Limit CPU real-time period in microseconds
        --cpu-rt-runtime int             Limit CPU real-time runtime in microseconds
    -c, --cpu-shares int                 CPU shares (relative weight)
        --cpus decimal                   Number of CPUs
        --cpuset-cpus string             CPUs in which to allow execution (0-3, 0,1)
        --cpuset-mems string             MEMs in which to allow execution (0-3, 0,1)
    -d, --detach                         Run container in background and print container ID
        --detach-keys string             Override the key sequence for detaching a container
        --device list                    Add a host device to the container
        --device-cgroup-rule list        Add a rule to the cgroup allowed devices list
        --device-read-bps list           Limit read rate (bytes per second) from a device (default [])
        --device-read-iops list          Limit read rate (IO per second) from a device (default [])
        --device-write-bps list          Limit write rate (bytes per second) to a device (default [])
        --device-write-iops list         Limit write rate (IO per second) to a device (default [])
        --disable-content-trust          Skip image verification (default true)
        --dns list                       Set custom DNS servers
        --dns-option list                Set DNS options
        --dns-search list                Set custom DNS search domains
        --domainname string              Container NIS domain name
        --entrypoint string              Overwrite the default ENTRYPOINT of the image
    -e, --env list                       Set environment variables
        --env-file list                  Read in a file of environment variables
        --expose list                    Expose a port or a range of ports
        --gpus gpu-request               GPU devices to add to the container ('all' to pass all GPUs)
        --group-add list                 Add additional groups to join
        --health-cmd string              Command to run to check health
        --health-interval duration       Time between running the check (ms|s|m|h) (default 0s)
        --health-retries int             Consecutive failures needed to report unhealthy
        --health-start-period duration   Start period for the container to initialize before starting health-retries countdown (ms|s|m|h) (default 0s)
        --health-timeout duration        Maximum time to allow one check to run (ms|s|m|h) (default 0s)
        --help                           Print usage
    -h, --hostname string                Container host name
        --init                           Run an init inside the container that forwards signals and reaps processes
    -i, --interactive                    Keep STDIN open even if not attached
        --io-maxbandwidth bytes          Maximum IO bandwidth limit for the system drive (Windows only)
        --io-maxiops uint                Maximum IOps limit for the system drive (Windows only)
        --ip string                      IPv4 address (e.g., 172.30.100.104)
        --ip6 string                     IPv6 address (e.g., 2001:db8::33)
        --ipc string                     IPC mode to use
        --isolation string               Container isolation technology
        --kernel-memory bytes            Kernel memory limit
    -l, --label list                     Set meta data on a container
        --label-file list                Read in a line delimited file of labels
        --link list                      Add link to another container
        --link-local-ip list             Container IPv4/IPv6 link-local addresses
        --log-driver string              Logging driver for the container
        --log-opt list                   Log driver options
        --mac-address string             Container MAC address (e.g., 92:d0:c6:0a:29:33)
    -m, --memory bytes                   Memory limit
        --memory-reservation bytes       Memory soft limit
        --memory-swap bytes              Swap limit equal to memory plus swap: '-1' to enable unlimited swap
        --memory-swappiness int          Tune container memory swappiness (0 to 100) (default -1)
        --mount mount                    Attach a filesystem mount to the container
        --name string                    Assign a name to the container
        --network network                Connect a container to a network
        --network-alias list             Add network-scoped alias for the container
        --no-healthcheck                 Disable any container-specified HEALTHCHECK
        --oom-kill-disable               Disable OOM Killer
        --oom-score-adj int              Tune host's OOM preferences (-1000 to 1000)
        --pid string                     PID namespace to use
        --pids-limit int                 Tune container pids limit (set -1 for unlimited)
        --platform string                Set platform if server is multi-platform capable
        --privileged                     Give extended privileges to this container
    -p, --publish list                   Publish a container's port(s) to the host
    -P, --publish-all                    Publish all exposed ports to random ports
        --pull string                    Pull image before running ("always", "missing", "never") (default "missing")
    -q, --quiet                          Suppress the pull output
        --read-only                      Mount the container's root filesystem as read only
        --restart string                 Restart policy to apply when a container exits (default "no")
        --rm                             Automatically remove the container when it exits
        --runtime string                 Runtime to use for this container
        --security-opt list              Security Options
        --shm-size bytes                 Size of /dev/shm
        --sig-proxy                      Proxy received signals to the process (default true)
        --stop-signal string             Signal to stop the container
        --stop-timeout int               Timeout (in seconds) to stop a container
        --storage-opt list               Storage driver options for the container
        --sysctl map                     Sysctl options (default map[])
        --tmpfs list                     Mount a tmpfs directory
    -t, --tty                            Allocate a pseudo-TTY
        --ulimit ulimit                  Ulimit options (default [])
    -u, --user string                    Username or UID (format: <name|uid>[:<group|gid>])
        --userns string                  User namespace to use
        --uts string                     UTS namespace to use
    -v, --volume list                    Bind mount a volume
        --volume-driver string           Optional volume driver for the container
        --volumes-from list              Mount volumes from the specified container(s)
    -w, --workdir string                 Working directory inside the container
    
  参数说明
  COMMAND:进入容器后执行的命令，如/bin/bash
  --name:容器名，不指定则随机
  -d:后台运行
  -i:交互式运行
  -t:返回一个tty终端
  -e:设置环境变量  docker run -e MYSQL_ROOT_PASSWORD=password mysql
  -p:宿主机端口映射容器端口   docker run -p 8080:80 nginx   hostPort:containerPort
  -v:挂载主机目录或卷到容器   docker run -v /host/path:/container/path nginx
  
  
  docker run 后面是镜像，不能是容器
  example
  docker run -it centos /bin/bash
  docker run -d redis
   
   -p 3306:3306   映射hostPort:containerPort，外部主机可以直接通过 宿主机ip:3306 访问到 MySQL 的服务。
  
  ```

  -  --privileged
    - 提供了一些额外的权限，使得容器内的进程能够访问主机上的一些特权功能（默认是不给的
      - 挂载主机设备：允许容器挂载主机上的设备，这在一些特殊的硬件访问场景中可能是必要的。
      - 访问主机网络命名空间： 允许容器访问主机的网络配置，这可能用于某些网络调试或特殊网络场景。
      - 访问主机的 SELinux 设置： 如果主机启用了 SELinux 安全模块，`--privileged` 允许

- 提交容器为镜像

  ```shell
  Usage:  docker commit [OPTIONS] CONTAINER [REPOSITORY[:TAG]]
  Create a new image from a container's changes
  Aliases:
    docker container commit, docker commit
  
  Options:
    -a, --author string    Author (e.g., "John Hannibal Smith <hannibal@a-team.com>")
    -c, --change list      Apply Dockerfile instruction to the created image
    -m, --message string   Commit message
    -p, --pause            Pause container during commit (default true)
  
  example
  docker commit -a beiyuan -m "my redis1" redis1 beiyuan/redis3:01
  ```

- 提交进行为归档文件

  - 包含了整个镜像的所有层和元数据。

  ```shell
  Usage:  docker save [OPTIONS] IMAGE [IMAGE...]
  Save one or more images to a tar archive (streamed to STDOUT by default)
  Aliases:
    docker image save, docker save
  
  Options:
    -o, --output string   Write to a file, instead of STDOUT
  
  example
  docker save -o hello-world1.tar.gz hello-world
  
  ```

- 从归档文件加载镜像

  - 归档文件通常是由 `docker save` 命令生成的
  - load进来的镜像所有数据和save前一致。（名字、id等）

  ```shell
  Usage:  docker load [OPTIONS]
  Load an image from a tar archive or STDIN
  Aliases:
    docker image load, docker load
  Options:
    -i, --input string   Read from tar archive file, instead of STDIN
    -q, --quiet          Suppress the load output
  
  
  docker load -i hello-world1.tar.gz 
  如果出现 Loaded image: hello-world:latest 说明归档时save的镜像此时正在仓库中，仓库中有一模一样的
  
  load成功了
  ac28800ec8bb: Loading layer [==================================================>]  14.85kB/14.85kB
  Loaded image: hello-world:latest
  
  说明归档文件的名字不影响原镜像的名
  ```

- 标签

  - 新建一个镜像的新repository,tag的镜像

    ```shell
    Usage:  docker tag SOURCE_IMAGE[:TAG] TARGET_IMAGE[:TAG]
    Create a tag TARGET_IMAGE that refers to SOURCE_IMAGE
    Aliases:
      docker image tag, docker tag
    
    docker tag beiyuan/redis1:01 localhost:5000/beiyuan/redis1:01  #这里加上了仓库地址端口，可用于推送镜像
    ```

  - 镜像id和原镜像一致，说明是同一个镜像。

## 容器生命周期

- 可以通过d或者名来标识容器

- docker --help

  ```shell
  Usage:  docker [OPTIONS] COMMAND
  A self-sufficient runtime for containers
  Common Commands:
    run         Create and run a new container from an image
    exec        Execute a command in a running container
    ps          List containers
    build       Build an image from a Dockerfile
    pull        Download an image from a registry
    push        Upload an image to a registry
    images      List images
    login       Log in to a registry
    logout      Log out from a registry
    search      Search Docker Hub for images
    version     Show the Docker version information
    info        Display system-wide information
  
  Management Commands:
    builder     Manage builds
    buildx*     Docker Buildx (Docker Inc., v0.11.2)
    compose*    Docker Compose (Docker Inc., v2.21.0)
    container   Manage containers
    context     Manage contexts
    image       Manage images
    manifest    Manage Docker image manifests and manifest lists
    network     Manage networks
    plugin      Manage plugins
    system      Manage Docker
    trust       Manage trust on Docker images
    volume      Manage volumes
  
  Swarm Commands:
    swarm       Manage Swarm
  
  Commands:
    attach      Attach local standard input, output, and error streams to a running container
    commit      Create a new image from a container's changes
    cp          Copy files/folders between a container and the local filesystem
    create      Create a new container
    diff        Inspect changes to files or directories on a container's filesystem
    events      Get real time events from the server
    export      Export a container's filesystem as a tar archive
    history     Show the history of an image
    import      Import the contents from a tarball to create a filesystem image
    inspect     Return low-level information on Docker objects
    kill        Kill one or more running containers
    load        Load an image from a tar archive or STDIN
    logs        Fetch the logs of a container
    pause       Pause all processes within one or more containers
    port        List port mappings or a specific mapping for the container
    rename      Rename a container
    restart     Restart one or more containers
    rm          Remove one or more containers
    rmi         Remove one or more images
    save        Save one or more images to a tar archive (streamed to STDOUT by default)
    start       Start one or more stopped containers
    stats       Display a live stream of container(s) resource usage statistics
    stop        Stop one or more running containers
    tag         Create a tag TARGET_IMAGE that refers to SOURCE_IMAGE
    top         Display the running processes of a container
    unpause     Unpause all processes within one or more containers
    update      Update configuration of one or more containers
    wait        Block until one or more containers stop, then print their exit codes
  
  Global Options:
        --config string      Location of client config files (default "/root/.docker")
    -c, --context string     Name of the context to use to connect to the daemon (overrides DOCKER_HOST env var and default context set with "docker context use")
    -D, --debug              Enable debug mode
    -H, --host list          Daemon socket to connect to
    -l, --log-level string   Set the logging level ("debug", "info", "warn", "error", "fatal") (default "info")
        --tls                Use TLS; implied by --tlsverify
        --tlscacert string   Trust certs signed only by this CA (default "/root/.docker/ca.pem")
        --tlscert string     Path to TLS certificate file (default "/root/.docker/cert.pem")
        --tlskey string      Path to TLS key file (default "/root/.docker/key.pem")
        --tlsverify          Use TLS and verify the remote
    -v, --version            Print version information and quit
  
  Run 'docker COMMAND --help' for more information on a command.
  
  For more help on how to use Docker, head to https://docs.docker.com/go/guides/
  
  ```

- 退出

  ```shell
  docker run ... /bin/bash  进入容器后
  exit:关闭当前终端并退出容器，可能会导致容器停止       exec进入容器再exit退出不会导致容器停止
  ctrl+p+q:不关闭当前终端，退出容器
  ```

- 启动

  - 容器保存了运行时的一些数据，如配置、数据、安装好的命令。重启能够、恢复
  - 直接运行镜像的话，那真的就是白纸一张。

  ```shell
  Usage:  docker start [OPTIONS] CONTAINER [CONTAINER...]
  Start one or more stopped containers
  Aliases:
    docker container start, docker start
  Options:
    -a, --attach               Attach STDOUT/STDERR and forward signals
        --detach-keys string   Override the key sequence for detaching a container
    -i, --interactive          Attach container's STDIN
  
  可以docker ps -a 查看所有容器id
  这好像是-d情况下启动。。
  看不到输出可以加入 -i试下
  ```

- 重启

  ```shell
  docker restart ...
  
  已经启动的，再重启
  ```

- 停止

  ```shell
  docker stop ...
  
  example
  docker stop `docker ps -a -q` #批量停止正在运行的所有容器
  ```

- 强行停止

  ```shell
  docker kill ...
  ```

- 删除容器

  ```shell
  docker rm ...
  
  不能删除正在运行的
  删除所有
  docker rm -f $(docker ps -a -q)
  docker ps -aq | xargs docker rm
  docker rm -f `docker ps -a -q`
  docker rm -f `docker ps -aq -f status=exited`  #批量删除所有已经停止的容器 (前一个-f是force，后一个是filter
  ```

## 容器管理

- 进程

  ```shell
  Usage:  docker ps [OPTIONS]
  List containers
  Aliases:
    docker container ls, docker container list, docker container ps, docker ps
  Options:
    -a, --all             Show all containers (default shows just running)
    -f, --filter filter   Filter output based on conditions provided
        --format string   Format output using a custom template:
                          'table':            Print output in table format with column headers (default)
                          'table TEMPLATE':   Print output in table format using the given Go template
                          'json':             Print in JSON format
                          'TEMPLATE':         Print output using the given Go template.
                          Refer to https://docs.docker.com/go/formatting/ for more information about formatting output with templates
    -n, --last int        Show n last created containers (includes all states) (default -1)
    -l, --latest          Show the latest created container (includes all states)
        --no-trunc        Don't truncate output
    -q, --quiet           Only display container IDs  -s, --size            Display total file sizes
  
  说明
  -a:可以列出所有运行过的容器，不加就只能看到运行中的
  ```

- 某个容器日志

  ```shell
  docker logs container
  ```

- 某个容器top

  ```shell
  docker top container
  ```

- 内部细节

  ```shell
  docker inspect container
  ```

- 进入

  ```shell
  docker exec -it container [COMMAND]     #重启 一个新终端，exit退出不会导致容器停止
  docker attach container                 #进入容器启动时的终端，exit退出会导致容器停止
  
  example 
  docker exec -it redis /bin/bash
  docker exec -it redis redis-cli
  #一般用-d后台启动的程序，再用exec进入对应容器实例
  ```

- 拷贝文件：容器<->主机
  ```shell
  Usage:  docker cp [OPTIONS] CONTAINER:SRC_PATH DEST_PATH|-
  	    docker cp [OPTIONS] SRC_PATH|- CONTAINER:DEST_PATH
  
  Copy files/folders between a container and the local filesystem
  
  Use '-' as the source to read a tar archive from stdin
  and extract it to a directory destination in a container.
  Use '-' as the destination to stream a tar archive of a
  container source to stdout.
  
  Aliases:
    docker container cp, docker cp
  Options:
    -a, --archive       Archive mode (copy all uid/gid information)
    -L, --follow-link   Always follow symbol link in SRC_PATH
    -q, --quiet         Suppress progress output during copy. Progress output is automatically suppressed if no terminal is attached
    
  example
  docker cp 9f99c:/tmp/h1.txt .        容器复制文件到当前目录
  docker cp h2.txt 9f99c:/tmp          
  
  ```

- 将容器压缩为归档文件

  ```shell
  Usage:  docker export [OPTIONS] CONTAINER
  Export a container's filesystem as a tar archive
  Aliases:
    docker container export, docker export
  
  Options:
    -o, --output string   Write to a file, instead of STDOUT
  
  example
  docker export 9f99c > redis2.tar.gz
  ```

- 从归档文件导入镜像

  - 可以从一个本地文件或远程 URL 直接导入文件系统快照。
  - 创建的镜像通常只包含文件系统快照，并可能缺少一些元数据

  ```shell
  Usage:  docker import [OPTIONS] file|URL|- [REPOSITORY[:TAG]]
  Import the contents from a tarball to create a filesystem image
  Aliases:
    docker image import, docker import
  
  Options:
    -c, --change list       Apply Dockerfile instruction to the created image
    -m, --message string    Set commit message for imported image
        --platform string   Set platform if server is multi-platform capable
        
  example
  docker import redis2.tar.gz beiyuan/redis2:01
  docker import http://example.com/example.tar.gz
  
  ```

- 运行从归档文件导入的镜像

  - 果然缺少了启动时运行的命令

  ```shell
  docker run -d --name redis2 de4e 
  docker: Error response from daemon: No command specified.
  See 'docker run --help'.
  
  说明没有执行命令作为守护线程
  docker run -d --name redis2 de4e /bin/bash
  一闪而过，容器启动后就停止了。/bin/bash不是守护线程
  
  docker ps -a --not-trunc
  CONTAINER ID                                                       IMAGE     COMMAND                               CREATED        STATUS                      PORTS     NAMES
  92fc057cc3e2e770d575d63ac6b818ea2bdb1a048eca67748116abbcb8f1647b   ed3094    "/bin/bash"                        15 hours ago   Exited (0) 24 minutes ago             redis1
  9f99c1c281d643ab79f4ccdebb7674592dc9f44a580913c87eea534e6bcea36f   redis     "docker-entrypoint.sh redis-server"   20 hours ago   Exited (0) 23 minutes ago             vigilant_tesla
  
  看到原生redis镜像运行时执行了redis-server命令
  
  最终
  docker run -d --name redis2 de4e redis-server
  ```

## docker-compose



# ==安装==

- 菜鸟上有教程：https://www.runoob.com/docker/docker-install-mysql.html

## mysql

- 查看可用版本：https://hub.docker.com/_/mysql?tab=tags 

- 拉取镜像

  ```shell
  $ docker pull mysql:8
  ```

- 运行

  ```shell
  docker run -d --name beimysql -p 3306:3306  -e MYSQL_ROOT_PASSWORD=beiyuan3721 mysql:8
  
  #要加上tag，不然会运行latest的
  #参数解析
  -p 3306:3306 : 映射容器服务的 3306 端口到宿主机的 3306 端口，外部主机可以直接通过 宿主机ip:3306 访问到 MySQL 的服务。
                 hostPort:containerPort
  MYSQL_ROOT_PASSWORD=123456：设置 MySQL 服务 root 用户的密码。
  #在容器内mysql命令行，quit退出mysql回到容器，exit直接退出容器。。
  ```

## mysql主从

- 备注

  - mysql8配置文件位置

    ```shell
    #Ubuntu、Debian等基于Debian的系统：
    /etc/mysql/my.cnf 或 /etc/mysql/mysql.conf.d/mysqld.cnf
    
    #Red Hat、CentOS、Fedora等基于RPM包管理器的系统：
    /etc/my.cnf 或 /etc/mysql/my.cnf
    
    #Windows：
    MySQL Installer安装的MySQL 8.0，配置文件通常位于 C:\ProgramData\MySQL\MySQL Server 8.0\my.ini。
    
    #macOS：
    使用Homebrew安装的MySQL，配置文件可能位于 /usr/local/etc/my.cnf。
    ```

  - 备注

    - centos8下docker运行的mysql:8默认的/etc/my.cnf

    ```shell
    # For advice on how to change settings please see
    # http://dev.mysql.com/doc/refman/8.2/en/server-configuration-defaults.html
    
    [mysqld]
    #
    # Remove leading # and set to the amount of RAM for the most important data
    # cache in MySQL. Start at 70% of total RAM for dedicated server, else 10%.
    # innodb_buffer_pool_size = 128M
    #
    # Remove leading # to turn on a very important data integrity option: logging
    # changes to the binary log between backups.
    # log_bin
    #
    # Remove leading # to set options mainly useful for reporting servers.
    # The server defaults are faster for transactions and fast SELECTs.
    # Adjust sizes as needed, experiment to find the optimal values.
    # join_buffer_size = 128M
    # sort_buffer_size = 2M
    # read_rnd_buffer_size = 2M
    
    # Remove leading # to revert to previous value for default_authentication_plugin,
    # this will increase compatibility with older clients. For background, see:
    # https://dev.mysql.com/doc/refman/8.2/en/server-system-variables.html#sysvar_default_authentication_plugin
    # default-authentication-plugin=mysql_native_password
    skip-host-cache
    skip-name-resolve
    datadir=/var/lib/mysql
    socket=/var/run/mysqld/mysqld.sock
    secure-file-priv=/var/lib/mysql-files
    user=mysql
    
    pid-file=/var/run/mysqld/mysqld.pid
    [client]
    socket=/var/run/mysqld/mysqld.sock
    
    !includedir /etc/mysql/conf.d/
    ```

- 主节点

  - 在宿主机3306端口运行master主机

    - 因为要挂载my.cnf就先这样创建。。。

    ```shell
    #运行会自动创建好在主机的结构
    docker run -d --privileged  -p 3306:3306 \
    -v /beimysql/mysql-master/log:/var/log/mysql \
    -v /beimysql/mysql-master/data:/var/lib/mysql \
    -v /beimysql/mysql-master/mysql-files:/var/lib/mysql-files \
    -e MYSQL_ROOT_PASSWORD=beiyuan3721 \
    --name mysql-master  mysql:8
    
    #创建剩下的目录,并复制一个默认的配置文件出来,备份一个给slaver
    mkdir /beimysql/mysql-master/conf
    docker cp [container]:/etc/my.cnf /beimysql/mysql-master/conf/
    cp /beimysql/mysql-master/conf/my.cnf /home/beiyuanii/Desktop/
    ```

  - vim /beimysql/mysql-master/conf/my.cnf

    - 加入如下内容
    - 在mysqld下增加

    ```shell
    [mysqld]
    ## 设置server_id，同一局域网中需要唯一
    server_id=101 
    ## 指定不需要同步的数据库名称
    binlog-ignore-db=mysql  
    ## 开启二进制日志功能
    log-bin=mall-mysql-bin  
    ## 设置二进制日志使用内存大小（事务）
    binlog_cache_size=1M  
    ## 设置使用的二进制日志格式（mixed,statement,row）
    binlog_format=mixed  
    ## 跳过主从复制中遇到的所有错误或指定类型的错误，避免slave端复制中断。
    ## 如：1062错误是指一些主键重复，1032错误是因为主从数据库数据不一致
    slave_skip_errors=1062
    ```

  - 运行一个新的

    ```shell
    #停止并删除原容器
    docker ps 
    docker stop [container]
    docker rm [container]
    
    #运行一个新的
    docker run -d --privileged  -p 3306:3306 \
    -v /beimysql/mysql-master/log:/var/log/mysql \
    -v /beimysql/mysql-master/data:/var/lib/mysql \
    -v /beimysql/mysql-master/conf/my.cnf:/etc/my.cnf \
    -v /beimysql/mysql-master/mysql-files:/var/lib/mysql-files \
    -e MYSQL_ROOT_PASSWORD=beiyuan3721 \
    --name mysql-master  mysql:8
    ```

  - 未解决

    - 目录挂载都成功了。
    - 文件my.cnf主机vim修改后，inode变化，容器内的文件并没有看到更新了
      - 重启后可以生效

  - 进入主节点

    - 创建数据同步账户

    ```shell
    docker exec -it 92ee mysql -uroot -pbeiyuan3721
    
    create user 'slave_user'@'%' identified by 'slave3721';
    grant replication slave,replication client on *.* to 'slave_user'@'%';
    
    flush privileges;
    show master status;
    
    #主机要开启bin-log，查看
    SHOW VARIABLES LIKE 'log_bin';
    ```

- 从节点

  - 宿主机3307创建从节点

    ```shell
    docker run -d --privileged  -p 3307:3306 \
    -v /beimysql/mysql-slave/log:/var/log/mysql \
    -v /beimysql/mysql-slave/data:/var/lib/mysql \
    -v /beimysql/mysql-slave/mysql-files:/var/lib/mysql-files \
    -e MYSQL_ROOT_PASSWORD=beiyuan3721 \
    --name mysql-slave  mysql:8
    
    mkdir /beimysql/mysql-slave/conf
    cp  /home/beiyuanii/Desktop/my.cnf /beimysql/mysql-slave/conf/
    ```

  - 在my.cnf中增加

    ```shell
    [mysqld]
    ## 设置server_id，同一局域网中需要唯一
    server_id=102
    ## 指定不需要同步的数据库名称
    binlog-ignore-db=mysql  
    ## 开启二进制日志功能
    log-bin=mall-mysql-bin  
    ## 设置二进制日志使用内存大小（事务）
    binlog_cache_size=1M  
    ## 设置使用的二进制日志格式（mixed,statement,row）
    binlog_format=mixed  
    ## 跳过主从复制中遇到的所有错误或指定类型的错误，避免slave端复制中断。
    ## 如：1062错误是指一些主键重复，1032错误是因为主从数据库数据不一致
    slave_skip_errors=1062
    ## relay_log配置中继日志
    relay_log=mall-mysql-relay-bin  
    ## log_slave_updates表示slave将复制事件写进自己的二进制日志
    log_slave_updates=1  
    ## slave设置为只读（具有super权限的用户除外）
    read_only=1
    ```

  - 运行一个新的

    ```shell
    #重新运行一个从库
    docker run -d --privileged  -p 3307:3306 \
    -v /beimysql/mysql-slave/log:/var/log/mysql \
    -v /beimysql/mysql-slave/data:/var/lib/mysql \
    -v /beimysql/mysql-slave/conf/my.cnf:/etc/my.cnf \
    -v /beimysql/mysql-slave/mysql-files:/var/lib/mysql-files \
    -e MYSQL_ROOT_PASSWORD=beiyuan3721 \
    --name mysql-slave  mysql:8
    
    ```

  - 配置

    ```shell
    #进入从库
    docker exec -it container mysql -uroot -pbeiyuan3721
    #手动配置
    change master to master_host='192.168.10.100', master_user='slave_user', master_password='slave3721', master_port=3306, master_log_file='mall-mysql-bin.000003', master_log_pos=888, master_connect_retry=30;
    #参数说明
    MASTER_LOG_FILE和MASTER_LOG_POS可以在主库中 show master status;查看
    #参看从库状态
    mysql> show slave status\G;
    *************************** 1. row ***************************
                   Slave_IO_State: 
                      Master_Host: 192.168.10.100
                      Master_User: slave_user
                      Master_Port: 3306
                    Connect_Retry: 60
                  Master_Log_File: mall-mysql-bin.000003
              Read_Master_Log_Pos: 888
                   Relay_Log_File: mall-mysql-relay-bin.000001
                    Relay_Log_Pos: 4
            Relay_Master_Log_File: mall-mysql-bin.000003
                 Slave_IO_Running: No
                Slave_SQL_Running: No
                  Replicate_Do_DB: 
              Replicate_Ignore_DB: 
               Replicate_Do_Table: 
           Replicate_Ignore_Table: 
          Replicate_Wild_Do_Table: 
      Replicate_Wild_Ignore_Table: 
                       Last_Errno: 0
                       Last_Error: 
                     Skip_Counter: 0
              Exec_Master_Log_Pos: 888
                  Relay_Log_Space: 157
                  Until_Condition: None
                   Until_Log_File: 
                    Until_Log_Pos: 0
               Master_SSL_Allowed: No
               Master_SSL_CA_File: 
               Master_SSL_CA_Path: 
                  Master_SSL_Cert: 
                Master_SSL_Cipher: 
                   Master_SSL_Key: 
            Seconds_Behind_Master: NULL
    Master_SSL_Verify_Server_Cert: No
                    Last_IO_Errno: 0
                    Last_IO_Error: 
                   Last_SQL_Errno: 0
                   Last_SQL_Error: 
      Replicate_Ignore_Server_Ids: 
                 Master_Server_Id: 0
                      Master_UUID: 
                 Master_Info_File: mysql.slave_master_info
                        SQL_Delay: 0
              SQL_Remaining_Delay: NULL
          Slave_SQL_Running_State: 
               Master_Retry_Count: 10
                      Master_Bind: 
          Last_IO_Error_Timestamp: 
         Last_SQL_Error_Timestamp: 
                   Master_SSL_Crl: 
               Master_SSL_Crlpath: 
               Retrieved_Gtid_Set: 
                Executed_Gtid_Set: 
                    Auto_Position: 0
             Replicate_Rewrite_DB: 
                     Channel_Name: 
               Master_TLS_Version: 
           Master_public_key_path: 
            Get_master_public_key: 0
                Network_Namespace: 
    1 row in set, 1 warning (0.00 sec)
    #可以看到还没启动同步
    Slave_IO_Running: No
    Slave_SQL_Running: No
    #开启同步
    start slave;
    #看到
    Slave_IO_Running: Yes
    Slave_SQL_Running: Yes
    ```

  - 验证

    ```shell
    #结论
    主库slave_user 只有同步的权限，并没有创库等其他权限
    主库中用户的表(root slave_user)的表都会同步给从库
    而在从库中可以更改一些所做的更改不会反应到主库   
    	从库的更改没有影响到同步的结构(如从库新增表)->主库中不会新增  
    	从库更改了同步的结构(如从库删除从主库同步来的表，对同步表中增删改)->主库中的不会改变，此时会报错，停止同步。
    也跟从库登录到主库的账户slave_user只有同步权限有关系吧
    
    #从库更改同步结构后，不会马上报错，会在主库下一次同步时发现不一致报错。
     Last_SQL_Error: Coordinator stopped because there were error(s) in the worker(s). The most recent failure being: Worker 1 failed executing transaction 'ANONYMOUS' at source log mall-mysql-bin.000003, end_log_pos 1807. See error log and/or performance_schema.replication_applier_status_by_worker table for more details about this failure or others, if any.
    
    #这时要重新同步
    #先手动停止
    stop slave
    
    #在主库中查询
    show master status;
    +-----------------------+----------+--------------+------------------+-------------------+
    | File                  | Position | Binlog_Do_DB | Binlog_Ignore_DB | Executed_Gtid_Set |
    +-----------------------+----------+--------------+------------------+-------------------+
    | mall-mysql-bin.000003 |     2053 |              | mysql            |                   |
    +-----------------------+----------+--------------+------------------+-------------------+
    #从库需要重新设置正确的复制起点，之后主库在相同同步结构上的更新都会及时同步到从库（旧的不会改变，会同步新的过来）
    change master to master_log_file='mall-mysql-bin.000003',master_log_pos=2053;
    
    #有个问题
    主库的一个表是在从库发生错误后创建的，从库上重新启动复制线程后，这个表没有复制到从库。怎么办？
    （从库删除主库同步过来的某个表后，再次同步后这个表也不会恢复（主库更新这个表，因为从库都没有这个结构了，所以也不会知道），似乎从库的再次同步只会恢复到断开同步时的状态。）
    主库：mysql> show tables;
    +---------------------+
    | Tables_in_master_db |
    +---------------------+
    | tt_today            |
    | tt_tomorrow         |
    | tt_user             |
    | tt_yestoday         |
    +---------------------+
    4 rows in set (0.00 sec)
    从库：mysql> show tables;
    +---------------------+
    | Tables_in_master_db |
    +---------------------+
    | tt_tomato           |
    | tt_tomorrow         |
    | tt_yestoday         |
    +---------------------+
    3 rows in set (0.00 sec)
    
    
    （旧的不会改变，会同步新的过来）问题，重启从库后也不会生效
    【通义千问】的回答
    在这种情况下，主库中新增的表 `tt_today` 没有在从库中创建，因为主从复制中断时该表尚未创建，所以在从库恢复复制后自然不会自动复制这个新增的表。
    
    解决办法如下：
    
    1. **备份从库**：
       在进行任何更改之前，先备份从库数据以防万一。
    
    2. **手动创建缺少的表**：
       需要在从库上手动创建 `tt_today` 表，并确保其结构与主库完全一致。可以从主库导出 `tt_today` 表的结构（不包含数据）：
    
       ```sql
       mysqldump -h master_host -u repl_user -p master_db tt_today --no-data > tt_today_structure.sql
    ```
  
       然后在从库上导入这个结构：
  
       ```sql
       mysql -h localhost -u slave_user -p master_db < tt_today_structure.sql
       ```
  
    3. **跳过错误事务**：
       如果从库复制停止时存在错误事务，可以尝试跳过那个错误的事务。首先，确定错误事务的结束点，然后在从库上执行：
  
       ```sql
       STOP SLAVE;
       CHANGE MASTER TO MASTER_LOG_FILE='<错误事务之前的日志文件>', MASTER_LOG_POS=<错误事务之后的日志位置>;
       START SLAVE;
       ```
  
       请根据实际情况填写 `<错误事务之前的日志文件>` 和 `<错误事务之后的日志位置>`。
  
    4. **重新同步数据**：
       如果表结构复杂或包含触发器、索引等，或者跳过错误事务不可行，可能需要清理并重新初始化从库。先备份从库数据，然后清空从库所有数据（或删除所有与主库相关的数据库和表），接着重新配置从库，设置主库的最新二进制日志文件和位置点，再启动复制。
  
       ```sql
       STOP SLAVE;
       RESET SLAVE ALL; # 清除复制信息和状态
       # 清空或删除从库上的相关数据库和表
       # ...
       CHANGE MASTER TO MASTER_LOG_FILE='<主库最新的日志文件>', MASTER_LOG_POS=<主库最新的日志位置>;
       START SLAVE;
       ```
  
    根据你的实际情况，选择最适合的方法来解决这个问题。在所有操作完成后，请密切关注主从复制状态，确保复制正常进行且无任何错误。
  
  ```
    
  ```
  
  - 解决从库更改同步结构导致数据不一致问题
  
    - 个人思路
      - 从库要么一开始就不要用root用户，创建一个用户只给读取数据的权限

## redis

- Remote Dictionary Server（远程字典服务）

- 拉取镜像

  ```shell
  docker pull redis:7
  ```

- 运行并设置密码

  - 在Docker容器中设置Redis密码时，这里的“用户”实际上指的是Redis服务器本身。在Redis中，不像传统的关系型数据库那样有严格的用户管理系统，而是通过一个全局的密码保护整个数据库实例。

    ```shell
    docker run -d --name beiredis \
      -p 6379:6379 \
      -v /beiredis/redis7/redis-data:/data \
      -e REDIS_PASSWORD=beiyuan3721 \
      redis:7
      
    这个/data目录好像没啥数据。。往redis中存数据后也没变化。
    -e REDIS_PASSWORD=your_redis_password 设置密码时，登陆时也没看见需要输入密码。。在配置文件中设置吧
    qiut退出redis到容器
    
    #用下面这个吧，复制下面这个redis.conf到相应目录。然后运行看下
    cp /home/beiyuanii/Desktop/redis.conf /beiredis/redis7/conf/
    
    vim /beiredis/redis7/conf/redis.conf
    
    
    # bind 127.0.0.1 -::1          #默认开启，只能够本机连接。注释了可以远程连接::是ipv6的回环地址
    requirepass beiyuan3721        #设置密码
    #如果不设置密码的话，还要把protected-mode yes 改为no才能远程访问。因为这个模式在不设置密码时只能本机访问。设置了就不用
    
    docker run -d -p 6379:6379 --name beiredis \
     -v /beiredis/redis7/conf/redis.conf:/etc/redis/redis.conf \
     -v /beiredis/redis7/data:/data  \
     redis:7 redis-server /etc/redis/redis.conf 
     
    #这里挂载redis.conf 修改后重启才能看到更改。但是挂载目录的话也是要重启redis。。
    #以后还是挂载目录吧？
    ```

  - 登陆

    ```shell
    #redis-cli 可以登陆进来，但是进行set需要验证，get也需要
    
    root@9f6649a8c28f:/data# redis-cli
    127.0.0.1:6379> set k1 v1
    (error) NOAUTH Authentication required.
    127.0.0.1:6379> quit
    root@9f6649a8c28f:/data# redis-cli -a beiyuan3721
    Warning: Using a password with '-a' or '-u' option on the command line interface may not be safe.
    127.0.0.1:6379> set k1 v1
    OK
    127.0.0.1:6379> quit
    root@9f6649a8c28f:/data# redis-cli
    127.0.0.1:6379> get k1
    (error) NOAUTH Authentication required.
    
    
    #正确做法
    redis-cli -a beiyuan3721
    ```

  - RedisInsight

    - redis官方的可视化工具

    - https://redis.io/docs/connect/insight/

      ```shell
      #居然要信息。。
      os:windows 
      business email:163
      phone:133
      job function:133
      company:Central South University
      ```

- redis-7.0.15的配置文件

  - docker安装的没找到在哪里。。。
- 从官网下载后解压拿到的redis.conf
  
  - 放在当前目录redis7.conf

## redis分槽主从集群

- 一个redis集群只能有16384个槽，编号0-16383（0-2^14-1）

- 运行

  ```shell
  #不用host网络吧（用host的也行），自己创建一个桥接网络方便管理
  docker network create redis-cluster-network
  
  #创建文件夹并修改配置文件
  mkdir -p /beiredis/cluster/redis-master-1/conf
  mkdir -p /beiredis/cluster/redis-master-2/conf
  mkdir -p /beiredis/cluster/redis-master-3/conf
  mkdir -p /beiredis/cluster/redis-slave-1/conf
  mkdir -p /beiredis/cluster/redis-slave-2/conf
  mkdir -p /beiredis/cluster/redis-slave-3/conf
  # -p, --parents     no error if existing, make parent directories as needed
  
  cp /home/bieyuanii/Desktop/redis.conf /beiredis/cluster/redis-master-1/conf
  vim /beiredis/cluster/redis-master-1/conf/redis.conf
  #取消注释
  cluster-enbaled yes
  protected-mode no  #yes改为no。不设置密码吧
  # bind 127.0.0.1 -::1          #注释掉
  
  cp /beiredis/cluster/redis-master-1/conf/redis.conf /beiredis/cluster/redis-master-2/conf
  cp /beiredis/cluster/redis-master-1/conf/redis.conf /beiredis/cluster/redis-master-3/conf
  cp /beiredis/cluster/redis-master-1/conf/redis.conf /beiredis/cluster/redis-slave-1/conf
  cp /beiredis/cluster/redis-master-1/conf/redis.conf /beiredis/cluster/redis-slave-2/conf
  cp /beiredis/cluster/redis-master-1/conf/redis.conf /beiredis/cluster/redis-slave-3/conf
  
  
  #三主
  docker run -d -p 6380:6379 \
  --net=redis-cluster-network \
  --privileged=true \
  -v /beiredis/cluster/redis-master-1/data:/data  \
  -v /beiredis/cluster/redis-master-1/conf:/etc/redis \
  --name redis-master-1 \
  redis:7 redis-server /etc/redis/redis.conf 
   
  docker run -d -p 6381:6379 \
  --net=redis-cluster-network \
  --privileged=true \
  -v /beiredis/cluster/redis-master-2/data:/data  \
  -v /beiredis/cluster/redis-master-2/conf:/etc/redis \
  --name redis-master-2 \
  redis:7 redis-server /etc/redis/redis.conf 
  
  docker run -d -p 6382:6379 \
  --net=redis-cluster-network \
  --privileged=true \
  -v /beiredis/cluster/redis-master-3/data:/data  \
  -v /beiredis/cluster/redis-master-3/conf:/etc/redis \
  --name redis-master-3 \
  redis:7 redis-server /etc/redis/redis.conf 
  
  #三从
  docker run -d -p 6383:6379 \
  --net=redis-cluster-network \
  --privileged=true \
  -v /beiredis/cluster/redis-slave-1/data:/data  \
  -v /beiredis/cluster/redis-slave-1/conf:/etc/redis \
  --name redis-slave-1 \
  redis:7 redis-server /etc/redis/redis.conf 
  
  docker run -d -p 6384:6379 \
  --net=redis-cluster-network \
  --privileged=true \
  -v /beiredis/cluster/redis-slave-2/data:/data  \
  -v /beiredis/cluster/redis-slave-2/conf:/etc/redis \
  --name redis-slave-2 \
  redis:7 redis-server /etc/redis/redis.conf 
  
  docker run -d -p 6385:6379 \
  --net=redis-cluster-network \
  --privileged=true \
  -v /beiredis/cluster/redis-slave-3/data:/data  \
  -v /beiredis/cluster/redis-slave-3/conf:/etc/redis \
  --name redis-slave-3 \
  redis:7 redis-server /etc/redis/redis.conf 
  ```

- 配置

  ```shell
  #进入某一容器
  docker exec -it 211f /bin/bash
  
  #--cluster-replicas 1 表示为每个master创建一个slave节点
  #--net=host模式下，可以用本机的端口
  redis-cli --cluster create \
  192.168.10.100:6380 192.168.10.100:6381 \
  192.168.10.100:6382 192.168.10.100:6383 \
  192.168.10.100:6384 192.168.10.100:6385 \
  --cluster-replicas 1
  
  #桥接网络redis-cluster-network用网段内里的ip。用主机那个发现不了
  docker inspect redis-cluster-network
  
  redis-cli --cluster create \
  172.18.0.2:6379 172.18.0.3:6379 \
  172.18.0.4:6379 172.18.0.5:6379 \
  172.18.0.6:6379 172.18.0.7:6379 \
  --cluster-replicas 1
  
  #成功后出现。主从关系是自动分配的，不支持手动分配从属关系。
  #槽位也可以自动分配
  #但是前一半个是master，后一半是slave 
  ```

- 连接集群

  ```shell
  #直接redis-cli是单机模式连接，操作不会成功
  127.0.0.1:6379> set k1 v1
  (error) MOVED 12706 172.18.0.4:6379
  
  #-c参数表示集群登陆。
  redis-cli -c
  
  #redis desktop manager 可以连接
  
  #redisinsight连接不上。。有毒。。卸载了
  ```

  

- 集群管理

  - 还有许多其他集群相关的命令，如故障转移、修复集群配置等，可以通过查阅Redis官方文档获取更多详细信息。在执行以上命令时，请确保Redis服务已启动，并且网络通信正常。

    ```shell
    #创建集群
    redis-cli --cluster create <node1_ip>:<port1> <node2_ip>:<port2> ... --cluster-replicas <num_replicas>
    
    #检查集群状态
    redis-cli --cluster check <node_ip>:<node_port>
    
    #获取集群信息
    redis-cli --cluster info <node_ip>:<node_port>
    
    #redis-cli 进入后
    #节点信息
    cluster nodes
    
    #集群信息
    cluster info
    ```

  - 删除集群关系

    ```shell
    #停止容器，清除所有节点的nodes.conf。再重启，如
    rm -f /beiredis/culster/redis-master-1/data/nodes.conf 
    ```

  - 还未尝试

    ```shell
    #添加新节点
    redis-cli --cluster add-node <new_node_ip>:<new_node_port> <existing_node_ip>:<existing_node_port> [flags]
    
    #迁移槽位（slot）
    redis-cli --cluster reshard <source_node_ip>:<source_node_port> [--cluster-from <start_slot> --cluster-to <end_slot>] [--cluster-yes] [--cluster-threshold <percentage>]
    ```



# ==DockerFile==

## 基本

- dockerfile是构建镜像的脚本

- 目前直接查看不到已有镜像的dockerfile，因为在构建镜像的时候没有保留dockerfile的内容。可以去docker hub、github等官方仓库找

- 执行dockerfile构建镜像的流程

  - 读取Dockerfile
    
  - Docker从指定的目录下读取Dockerfile文件，这是构建镜像的第一步。
    
  - 创建基础镜像容器
    - Docker根据Dockerfile中的第一条指令（通常是`FROM`指令），找到并下载（如果尚未存在本地）指定的基础镜像。
    - 使用这个基础镜像启动一个新的临时容器。

  - 执行指令
    - Docker逐行执行Dockerfile中的每一条指令。
    - 指令可能包括但不限于：
      - `RUN`：在容器内执行命令并提交结果到新的镜像层。
      - `COPY` 或 `ADD`：将宿主机的文件或目录复制到容器内的指定位置。
      - `WORKDIR`：设置工作目录。
      - `ENV`：设置环境变量。
      - `CMD` 或 `ENTRYPOINT`：设置容器启动时运行的默认命令或入口点。
      - 其他如 `EXPOSE`（暴露端口）、`VOLUME`（声明数据卷）等。

  - 创建新镜像层
    - 对于每个Dockerfile中的指令，Docker都会对容器进行相应的修改，然后将这些修改保存为新的镜像层。
    - 每个镜像层都是只读的，且仅包含与前一层相比发生更改的部分，以实现高效的存储。

  - 递归构建
    - 当一条指令执行完成后，Docker停止当前容器并基于新创建的镜像层开始下一个容器的构建过程。
    - 这个过程会持续下去，直到Dockerfile中的所有指令都被执行完毕。

  - 最终镜像生成
    
  - 当所有Dockerfile指令执行结束时，Docker得到了一个由所有新增镜像层组成的最终镜像。
    
  - 标记镜像
    
  - 在构建过程中，可以通过 `-t` 或 `--tag` 参数给最终镜像打上标签，便于管理和引用。
    
  - 总结

    - Dockerfile的执行是一个按顺序执行每条指令并实时创建中间镜像层的过程，最终产出一个完整的、符合Dockerfile描述的应用程序环境的镜像。用户可以通过 `docker build` 命令触发这一流程，例如：

      ```shell
      docker build -t my-image-name:tag
      ```

- docker build

  ```shell
  Usage:  docker buildx build [OPTIONS] PATH | URL | -
  
  Start a build
  
  Aliases:
    docker buildx build, docker buildx b
  
  Options:
        --add-host strings              Add a custom host-to-IP mapping (format: "host:ip")
        --allow strings                 Allow extra privileged entitlement (e.g., "network.host", "security.insecure")
        --attest stringArray            Attestation parameters (format: "type=sbom,generator=image")
        --build-arg stringArray         Set build-time variables
        --build-context stringArray     Additional build contexts (e.g., name=path)
        --builder string                Override the configured builder instance (default "default")
        --cache-from stringArray        External cache sources (e.g., "user/app:cache", "type=local,src=path/to/dir")
        --cache-to stringArray          Cache export destinations (e.g., "user/app:cache", "type=local,dest=path/to/dir")
        --cgroup-parent string          Optional parent cgroup for the container
    -f, --file string                   Name of the Dockerfile (default: "PATH/Dockerfile")
        --iidfile string                Write the image ID to the file
        --label stringArray             Set metadata for an image
        --load                          Shorthand for "--output=type=docker"
        --metadata-file string          Write build result metadata to the file
        --network string                Set the networking mode for the "RUN" instructions during build (default "default")
        --no-cache                      Do not use cache when building the image
        --no-cache-filter stringArray   Do not cache specified stages
    -o, --output stringArray            Output destination (format: "type=local,dest=path")
        --platform stringArray          Set target platform for build
        --progress string               Set type of progress output ("auto", "plain", "tty"). Use plain to show container output (default "auto")
        --provenance string             Shorthand for "--attest=type=provenance"
        --pull                          Always attempt to pull all referenced images
        --push                          Shorthand for "--output=type=registry"
    -q, --quiet                         Suppress the build output and print image ID on success
        --sbom string                   Shorthand for "--attest=type=sbom"
        --secret stringArray            Secret to expose to the build (format: "id=mysecret[,src=/local/secret]")
        --shm-size bytes                Size of "/dev/shm"
        --ssh stringArray               SSH agent socket or keys to expose to the build (format: "default|<id>[=<socket>|<key>[,<key>]]")
    -t, --tag stringArray               Name and optionally a tag (format: "name:tag")
        --target string                 Set the target build stage to build
        --ulimit ulimit                 Ulimit options (default [])
  
  
  # -t只加镜像名不加标签。则默认latest
  ```

  

## 语法

- 官网：https://docs.docker.com/reference/dockerfile/

  ```shell
  ADD	Add local or remote files and directories.
  ARG	Use build-time variables.
  CMD	Specify default commands.
  COPY	Copy files and directories.
  ENTRYPOINT	Specify default executable.
  ENV	Set environment variables.
  EXPOSE	Describe which ports your application is listening on.
  FROM	Create a new build stage from a base image.
  HEALTHCHECK	Check a container's health on startup.
  LABEL	Add metadata to an image.
  MAINTAINER	Specify the author of an image.
  ONBUILD	Specify instructions for when the image is used in a build.
  RUN	Execute build commands.
  SHELL	Set the default shell of an image.
  STOPSIGNAL	Specify the system call signal for exiting a container.
  USER	Set user and group ID.
  VOLUME	Create volume mounts.
  WORKDIR	Change working directory.
  ```
  
- 基本语法

  ```shell
  # Dockerfile示例
  
  # 1. 指定基础镜像（必须是第一条指令）
  FROM <image>:<tag> # 或者 FROM <image>@<digest>
  
  # 2. 维护者信息（可选）
  LABEL maintainer="Your Name <your.email@example.com>"
  
  # 3. 定义环境变量。在dockefile中可 $<variable_name> 直接使用
  ENV <variable_name>=<value> ...
  
  # 4. 工作目录设置.终端登陆进来的默认目录
  WORKDIR /path/to/workdir
  
  # 5. 添加宿主机的文件或目录到镜像，且会自动处理URL(下载)和解压tar压缩包。<dest>不存在会创建
  ADD <src>... <dest>
  
  # 只是复制
  COPY <src>... <dest>
  
  
  # 6. 执行命令
  RUN <command> (shell形式)
  RUN ["executable", "param1", "param2"] (exec形式)
  
  # 7. 暴露容器运行时的端口
  EXPOSE <port> [<port>...]
  
  # 8. 容器启动时执行的命令  变参
  CMD ["executable", "param1", "param2"] (exec形式，推荐)
  CMD command param1 param2 (shell形式)
  
  # 也是容器启动时执行的命令 定参 
  ENTRYPOINT ["executable", "param1", "param2"] (exec形式，推荐)
  ENTRYPOINT command param1 param2 (shell形式)                                                                     
  # 9. 创建数据卷
  VOLUME ["/data"]
  
  # 10. 配置用户和组（如果需要）
  USER <username|uid>
  
  # 11. 健康检查（Docker 1.12及以上版本）
  HEALTHCHECK --interval=30s --timeout=3s CMD curl -f http://localhost/ || exit 1
  
  ```
  
- 注意

  - shell和exec格式
  
- exec格式更明确且推荐使用，因为它不需要通过shell解释器执行命令，有助于减少安全风险并提高效
  
- COPY和ADD
  
    - `COPY` 指令的行为更加直观和简单，仅仅负责本地文件的复制
  - COPY更加安全，`ADD` 提供了额外的功能（比如自动解压缩和远程下载），这可能会导致构建过程中的行为不太透明，增加安全风险
  
- CMD和ENTRYPOINT
  
  - 指定容器启动时默认执行的命令和参数
  
  - CMD
  
    - CMD的值可以被docker run命令后面跟的参数覆盖，即可以通过命令行指定新的命令和参数替代
  
    - 格式
  
        - `CMD ["executable", "param1", "param2"]` （exec形式，推荐）
        - `CMD command param1 param2` （shell形式）
      - `CMD ["param1", "param2"]` （如果镜像的ENTRYPOINT已经定义了默认的可执行文件）
  
    - example
  
        ```shell
        # Dockerfile
        FROM ubuntu:latest
        CMD ["bash", "-c", "echo 'Hello World!' && sleep infinity"]
        
        #当我们构建并运行这个镜像时，容器将默认执行bash命令，打印"Hello World!"，然后挂起等待。
        docker run my_image:latest
        
        #如果我们想要运行其他命令，可以直接在docker run命令后指定,此时，容器将忽略原来CMD定义的命令，转而运行/bin/bash
        docker run my_image:latest /bin/bash
      ```
  
  - ENTRYPOINT
  
      - ENTRYPOINT定义的命令不会被docker run命令后面的参数直接覆盖，相反，docker run命令行参数会被追加到ENTRYPOINT指定的命令后面作为额外参数传入
      - 格式
        - `ENTRYPOINT ["executable", "param1", "param2"]` （exec形式，推荐）
      - `ENTRYPOINT command param1 param2` （shell形式）
  
  - CMD和ENTRYPOINT一起使用
  
    - 同时指定了CMD和ENTRYPOINT，CMD的参数会被当作ENTRYPOINT的补充参数
  
    - example
  
        ```shell
        # Dockerfile
        # 假设my_app是一个二进制文件，它接受"start"作为参数启动服务
        # 构建并运行镜像时，默认会执行my_app start
        FROM ubuntu:latest
        ENTRYPOINT ["my_app"]
        CMD ["start"]
        
        # 覆盖CMD的参数，而不改变ENTRYPOINT。容器将执行my_app stop，而不是原来的my_app start
        docker run my_image:latest stop
        ```

## ex：centos8安装jdk8

- 步骤

  - 下载jdk-8u301-linux-x86.tar.gz：https://www.oracle.com/java/technologies/downloads/#java8
  - 在某个目录/mydocker/image/centos8-jdk
  
- 创建一个名为Dockerfile的文件，并将下载好的压缩包放到这里
  
- Dockerfile
  
    ```shell
    FROM centos:8
    MAINTAINER beiyuan<beiyuanii@126.com>
    ENV MYPATH /usr/local
    WORKDIR $MYPATH
     
    #安装vim编辑器
    RUN yum -y install vim
    #安装ifconfig命令查看网络IP
    RUN yum -y install net-tools
    #安装java8及lib库
    RUN yum -y install glibc.i686
    RUN mkdir /usr/local/java
    #ADD 是相对路径jar,把jdk-8u341-linux-x64.tar.gz添加到容器中,安装包必须要和Dockerfile文件在同一位置
    ADD jdk-8u341-linux-x64.tar.gz /usr/local/java/
    #配置java环境变量
    
    ENV JAVA_HOME /usr/local/java/jdk1.8.0_341
    ENV JRE_HOME $JAVA_HOME/jre
    ENV CLASSPATH $JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar:$JRE_HOME/lib:$CLASSPATH
    ENV PATH $JAVA_HOME/bin:$PATH
    EXPOSE 80
    CMD echo $MYPATH
    CMD echo "success--------------ok"
    CMD /bin/bash
  ```
  
- 在这个目录下运行
  
    ```shell
    # 后面的点表示当前目录
    docker build -t centosjava8:1.5 .
  ```
  
- 构建好后就可以直接运行了
  
    ```shell
     docker run -it centosjava8:1.5 /bin/bash
    ```

# ==容器其他==

## 查看linux发行版

- 大多数可以，不行再问通义千问

  ```shell
  cat /etc/os-release
  ```

- example

  ```shell
  mysql:8    "Oracle Linux Server"
  redis:7    "Debian GNU/Linux"
  ```

## 安装vim

- 没有yum

  ```shell
  直接yum install的话
  bash:yum:command not found
  ```

- apt-get还在的话

  ```shell
  apt-get update
  apt-get install -y vim
  ```


## 查看ip

- 3

  ```shell
  hostname -I
  ```

## 安装ping

- 3

  ```shell
  apt update
  apt install iputils-ping
  ```

