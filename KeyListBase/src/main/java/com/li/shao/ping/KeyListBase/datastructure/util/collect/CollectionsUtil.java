package com.li.shao.ping.KeyListBase.datastructure.util.collect;

import java.util.Collection;

public class CollectionsUtil {

	public static <T> boolean isEmpty(Collection<T> col) {
		if(col == null || col.isEmpty()) {
			return true;
		}
		return false;
	}
}
