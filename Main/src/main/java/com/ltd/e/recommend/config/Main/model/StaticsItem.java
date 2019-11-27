package com.ltd.e.recommend.config.Main.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class StaticsItem {

	private long itemId;
	private double value;
}
