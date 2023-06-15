package com.global.mentorship.error;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Error {
	
	private final String message;
	
	private final List<String> details;
	
	
}
