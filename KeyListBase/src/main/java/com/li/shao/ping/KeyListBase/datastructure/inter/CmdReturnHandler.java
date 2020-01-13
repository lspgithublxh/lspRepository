package com.li.shao.ping.KeyListBase.datastructure.inter;

import java.util.Map;

@FunctionalInterface
public interface CmdReturnHandler{

	public void handle(String line, Map<Integer, String> threadIdName);
}
