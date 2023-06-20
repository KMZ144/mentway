package com.global.mentorship.error;

import java.util.HashMap;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Error {
	
	private  String message;
	
	private  HashMap<String,String> details ;
	
	 public void addError(String key, String value) {
	        if (this.details == null) {
	            this.details = new HashMap<>();
	        }
	        this.details.put(key, value);
	    }	
}
