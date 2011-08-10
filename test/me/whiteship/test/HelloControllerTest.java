package me.whiteship.test;

import java.io.IOException;

import javax.servlet.ServletException;

import org.junit.Test;
import org.springframework.web.bind.annotation.RequestMethod;

public class HelloControllerTest extends FunctionalTest {

	public HelloControllerTest() {
		setRelativeLocations("spring-servlet.xml");
		setClasses(HelloSpring.class);
	}

	@Test
	public void hello() throws ServletException, IOException {
		Response res = request("/hello", RequestMethod.GET)
				.addParameter("name", "Spring")
				.send();
		
		res.assertIsOk();
		res.assertModel("message", "Hello Spring");
		res.assertViewName("/WEB-INF/views/hello.jsp");
	}

	@Test
	public void helloSimpler() throws ServletException, IOException {
		Response res = requestGET("/hello")
				.addParameter("name", "Spring")
				.send();
		
		res.assertIsOk()
			.assertModel("message", "Hello Spring")
			.assertViewName("/WEB-INF/views/hello.jsp");
	}
	
	@Test
	public void helloSimplest() throws ServletException, IOException {
		requestGET("/hello")
			.addParameter("name", "spring")
			.send()
			.assertIsOk()
			.assertModel("message", "Hello spring")
			.assertViewName("/WEB-INF/views/hello.jsp");
	}

}
