package com.writerpad.dto;

public class TimeToRead {
	private Integer min;
	
	private Integer second;
	
public TimeToRead() {
		
	}


	public TimeToRead(Integer min, Integer second) {
		
		this.min = min;
		this.second = second;
	}
	
	public Integer getMin() {
		return min;
	}
	public void setMin(Integer min) {
		this.min = min;
	}
	public Integer getSecond() {
		return second;
	}
	public void setSecond(Integer second) {
		this.second = second;
	}
	
	@Override
	public String toString() {
		return "TimeToRead [min=" + min + ", second=" + second + "]";
	}
	
	

}
