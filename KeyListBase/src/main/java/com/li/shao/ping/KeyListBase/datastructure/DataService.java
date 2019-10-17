package com.li.shao.ping.KeyListBase.datastructure;

/**
 * 基本思路：
 * 键值对 为：userid-链表list<JSON> 初始化一个json 链表list 并且排序好--使用快速排序/归并排序/选择排序
 * 对于更新操作：5个对象，二分找到对象/或者增加一个map索引。找到对象之后，移除这个对象，设置新值，重新二分查找而插入。
 * 对于查找操作：直接取前5个即可，非常快。
 * 对于新增数据：map索引查找存在性，然后插入。只能一条一条新增；对每个用户都是。---所以这是个比较耗时的过程
 * 对于删除数据：全量的，则每个用户依次删除
 * 对于删除某个用户：直接删除索引 + 链表数据
 * TODO 
 * ---最高效的还是树O(log(n))(但是树要重新整合，也会消耗一部分效率)，除了数没有更好的理论算法。其次链表+索引O(n)，然后是数组-多次赋值O(n)。
 *--先使用最简单的模式：数组模式
 * @author lishaoping
 * @date 2019年10月6日
 * @file DataService
 */
public interface DataService {

	/**
	 * 查询用户排前的结点
	 *
	 * @author lishaoping
	 * @date 2019年10月8日
	 * TODO
	 */
	public String queryTopNByKey(Long key, int n);
	
	/**
	 * 更新用户的结点
	 *
	 * @author lishaoping
	 * @date 2019年10月8日
	 * TODO
	 */
	public void updateNodesByKey(String nodes, Long key) ;
	
	/**
	 * 初始化一个用户的所有结点
	 *
	 * @author lishaoping
	 * @date 2019年10月8日
	 * TODO
	 */
	public void initUserNodesByKeyAndData(String nodes, Long key);
	
	/**
	 * 删除一个用户的所有结点
	 *
	 * @author lishaoping
	 * @date 2019年10月8日
	 * TODO
	 */
	public void deleteByKey(Long key);
	
}
