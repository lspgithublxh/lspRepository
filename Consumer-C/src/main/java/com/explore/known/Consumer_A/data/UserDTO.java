package com.explore.known.Consumer_A.data;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class UserDTO {
	
	private long userId;
}
