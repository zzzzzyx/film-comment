package com.zzzzzyx.film_comment.model;

public class Frequency 
{
    private String frequency;
    private String word;
	public String getFrequency() {
		return frequency;
	}
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public Frequency(String frequency, String word) {
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
