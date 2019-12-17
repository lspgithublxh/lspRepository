package com.li.shao.ping.KeyListBase.datastructure.inter;

import com.li.shao.ping.KeyListBase.datastructure.entity.Rejection2Entity2I;

public interface RejectionStrategy2I<T> {

	 byte[] handle(Rejection2Entity2I<T> entity);

}
