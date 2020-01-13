package com.li.shao.ping.KeyListBase.datastructure.util.monitor;

/**
 * 类似async-profiler 参考https://github.com/jvm-profiling-tools/async-profiler
 * 
 * JVM-TI: jvm提供的native编程接口。。监听虚拟机事件(虚拟机启动之时,java类未初始化；或者运行时，在某个接口监听)，添加回调函数(虚拟机调用，回调函数的入参里：JvmTI指针中有很多函数可以用)
 * 	用途：性能分析、线程分析
 *  具体实现工具：jmap,jps, jstack 。实现方式：Attach方式，中途加入
 *  	工具2：IDE调试的 libjdwp.so 实现方式：建立一个Agent,agent使用jvmti函数，将agent编译为动态链接库，java程序启动时加载.
 *  
 *  profile常用：
 *  >jps -l -m -v 查看详细主类和入参
 *  >java -agentpath:/home/xxx.so=param classname 启动的方式
 *  >java -agentlib:hprof=cpu=samples,interval=20,depth=3 classname 启动的时候使用；在关闭的时候才看得到结果;最后：看得到各个方法的cpu耗时统计
 *  >java -agentlib:hprof=heap=all classname 查看某类的对象个数，字节数，每个对象的各个属性值；对象的trace---来找到产生这些对象的方法(比如char[]往往由Arrays.copyOf产生)
 *  >jmap -dump:live,format=b,file=abc.bin  然后使用jhat -J-Xmx1024M abc.bin来查看内存占用
 *  参考资料：https://www.jianshu.com/p/e59c4eed44a2
 *  
 * @author lishaoping
 * @date 2020年1月10日
 * @package  com.li.shao.ping.KeyListBase.datastructure.util.monitor
 */
public class ServiceJVMTI {

}
