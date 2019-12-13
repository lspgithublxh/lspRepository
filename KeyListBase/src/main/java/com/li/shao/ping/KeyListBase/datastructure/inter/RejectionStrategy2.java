package com.li.shao.ping.KeyListBase.datastructure.inter;

import com.li.shao.ping.KeyListBase.datastructure.entity.Rejection2Entity;

public interface RejectionStrategy2<T> {

	 byte[] handle(Rejection2Entity<T> entity);

}
