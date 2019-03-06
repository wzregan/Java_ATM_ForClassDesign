package com.wz.Exception;

public class OutOfRangeException extends Exception {
	public void printStackTrace() {
		System.err.println("超出了最大范围");
		
	}

}
