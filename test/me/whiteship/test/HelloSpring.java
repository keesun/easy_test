package me.whiteship.test;

import org.springframework.stereotype.Service;

@Service
public class HelloSpring {
	
	public String sayHello(String name) {
		return "Hello " + name;
	}
	
}
