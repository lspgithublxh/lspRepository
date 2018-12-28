package com.bj58.fang.mdocnknlmercontainer;

/**
 * 容器技术测试https://www.cnblogs.com/bjfuouyang/p/3798198.html	
 * ---产生原因：软件的下载、安装、运行 中遇到的各种各样的问题，挺麻烦：https://www.cnblogs.com/cgzl/p/8458926.html
 * 1.虚拟机切割硬件资源(cpu硬盘内存网络)--建立操作系统
 * 2.容器切割操作系统资源(进程的命令空间、网络栈、存储栈--文件空间)-- 建立容器(有一个进程PID=1, 有根文件系统)(小规模环境，而不是整个操作系统)
 * --------docker
 * consul--k8s
 * -----------------
 * 3.镜像、容器、仓库  https://www.ithome.com/0/402/469.htm 
 *  >装好配置好运行好的软件的容器建立为镜像、镜像放在仓库中、利用镜像创建容器
 *  >镜像：特殊的文件系统，不仅带有配置好的文件，而且运行时需要的环境变量都带着。。仅仅没有动态数据
 *  	>搭建一个软件环境，建立为一个 镜像
 *  >仓库：有高质量的镜像：Docker Hub
 *  >build once, run anywhere
 * 4.搭建、发送、运行
 *  >build、ship、run
 * 5.docker运用于具体业务实现：
 *  >对容器本身进行《 编排、管理、调度》：k8s基于容器的集群管理平台
 *  >一个k8s集群：master(营业厅+调度室+大总管)、node(一个service:多个pod:多个容器)
 *   >master:
 *   >node:docker(创建容器)、kubelet(监视指派到它所在node上的Pod)
 * @ClassName:DockerTest
 * @Description:
 * @Author lishaoping
 * @Date 2018年12月27日
 * @Version V1.0
 * @Package com.bj58.fang.mdocnknlmercontainer
 */
public class DddfocdfskderTest {

	public static void main(String[] args) {
		test();
		
	}

	private static void test() {
		
	}
}
