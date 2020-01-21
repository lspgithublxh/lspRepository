package com.li.shao.ping.KeyListBase.datastructure.util.sync;

/**
 * FIFO队列, CLH锁, status, thread
 *
 * 基本概念：多个线程、一个资源、共享方式/独占方式
 *       并发get, 并发release
 * CountDownLatch为例：
 *  >await()：先首次判断status==0如果是，那么返回；否则进入一个for循环，如果前一个元素为空head元素--则判断status==0是则退出，否则在一定条件下等待nanos后再判断status==0;剩下await()线程只能超时退出。
 *  >countDown()：判断status==0则失败，减一cas更新成功，失败自旋-重新判断status
 * AQS实现的功能：(自接口向实现理解)(模板模式)
 *  > releaseShared():子类释放status--tryReleaseShared()(如cas减1),本类调整Node链表--unpark唤醒链表中阻塞线程doReleaseShared()
 *  >tryAcquireSharedNanos():子类获取资源值status--tryAcquireShared(), 本类
 * Semaphore为例：
 * 
 * 
 * 很重要的，比synchronized同步块独特有效的是：状态量，量值。第一，使得子线程可以通过减少量值的方式精确地间接的唤醒其他线程(synchr方式还需要自己使用Atomic整数同步)。同时封装了：准备等待的线程确定自己是否等待的逻辑：量值为0就直接退出了；----即准备等待的线程可以明确地知道唤醒线程是否已经执行了。这两点都被抽象同步队列封装好了。
 * 
 * @author lishaoping
 * @date 2020年1月3日
 * @package  com.li.shao.ping.KeyListBase.datastructure.util.sync
 */
public class ServiceAQSTest {

}
