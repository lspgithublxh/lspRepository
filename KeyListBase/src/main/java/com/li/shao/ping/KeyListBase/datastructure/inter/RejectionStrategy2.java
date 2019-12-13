package com.li.shao.ping.KeyListBase.datastructure.inter;

public interface RejectionStrategy2 {

	byte[] handle(String service, String ipPort, byte[] task);
}
