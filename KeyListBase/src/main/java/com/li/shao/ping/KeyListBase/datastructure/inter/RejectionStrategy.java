package com.li.shao.ping.KeyListBase.datastructure.inter;

public interface RejectionStrategy {

	boolean handle(Runnable task);
}
