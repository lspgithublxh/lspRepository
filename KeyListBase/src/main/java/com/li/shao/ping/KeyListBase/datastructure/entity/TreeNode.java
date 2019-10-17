package com.li.shao.ping.KeyListBase.datastructure.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 暂时使用二叉树形式
 * TODO 
 * @author lishaoping
 * @date 2019年10月17日
 * @file Node
 */
@Data
@Accessors(chain = true)
public class TreeNode<V> {

	private double score;
	private V value;
	private long id;
	private TreeNode<V> left;
	private TreeNode<V> right;
}
