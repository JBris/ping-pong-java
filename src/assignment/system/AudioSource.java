package src.assignment.system;

import java.util.*;
import java.lang.*;

public class AudioSource<T> {
	
	/***Properties***/
	private T t;
	
	protected String name;
	
	/***Constructor***/
	
	public AudioSource(String name, T t) {
		this.t = t;
		this.name = name;
	}
	
	/***Getters and Setters***/
	
	public T get() { 
		return t; 
	}
	
	public String name() {
		return name;
	}
}