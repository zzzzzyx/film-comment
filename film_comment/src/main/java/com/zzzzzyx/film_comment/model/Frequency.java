package com.zzzzzyx.film_comment.model;

public class Frequency 
{
    private int frequency;
    private String word;
	public int getFrequency() {
		return frequency;
	}
	public String getWord() {
		return word;
	}
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public Frequency(int frequency, String word) {
		super();
		this.frequency = frequency;
		this.word = word;
	}
	public Frequency() {
		super();
		// TODO 自动生成的构造函数存根
	}
	@Override
	public String toString() {
		return "Frequency [frequency=" + frequency + ", word=" + word + "]";
	}
	
    
}
