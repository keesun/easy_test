package me.whiteship.test;

import java.io.IOException;

import javax.servlet.ServletException;

import org.junit.After;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockServletConfig;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;
/**
 * 이 소스 코드는 '토비의 스프링 3'에 동봉되어 있는 CD에 들어있는 코드를 수정한 코드 입니다. 
 * 
 * 스프링 DispatcherSerlver을 활용하는 기능 테스트를 쉽게 만들수 있도록 지원합니다.
 * 1. FunctionalTest 상속 받기
 * 
 * public class HelloControllerTest extends FunctionalTest { 
 * 
 * }
 * 
 * 2. 생성자에서 설정 파일 및 빈으로 등록할 클래스 설정하기
 * 
 * public class HelloControllerTest extends FunctionalTest {

	public HelloControllerTest() {
		setRelativeLocations("spring-servlet.xml");
		setClasses(HelloSpring.class);
	}
 *  
 *  여기서 사용할 수 있는 메서드는 3가지가 있습니다.
 * 
 *  @see FunctionalTest.setLocations(String ...locations)
 *  @see FunctionalTest.setRelativeLocations(String ...relativeLocations)
 *  @see FunctionalTest.setClasses(Class<?> ...classes)
 *  
 *  3. 테스트 작성하기
 *  
 *  @Test
	public void hello() throws ServletException, IOException {
		Response res = request("/hello", RequestMethod.GET)
				.addParameter("name", "Spring")
				.send();
		
		res.assertIsOk();
		res.assertModel("message", "Hello Spring");
		res.assertViewName("/WEB-INF/views/hello.jsp");
	}
 * 
 * 
 * Response를 확장해서 필요한 assertXXX() 문을 추가할 수 있습니다.
 * 
 * @see Response
 * 
 * @author Toby Lee
 * @author Keesun Baik
 *
 */
public class FunctionalTest {
	protected MockHttpServletRequest request;
	protected MockHttpServletResponse response;
	protected MockServletConfig config = new MockServletConfig("spring");
	protected MockHttpSession session;
	
	private ConfigurableDispatcherServlet dispatcherServlet;
	private Class<?>[] classes;
	private String[] locations;
	private String[] relativeLocations;
	
	/**
	 * 설정 파일의 절대 경로를 지정합니다.
	 * 
	 * @param locations 설정 파일은 String 배열로 가변인자 넘겨줄 수 있습니다.
	 * @return FunctionalTest 타입을 반환하여 Method Chaining을 활용할 수 있습니다.
	 */
	public FunctionalTest setLocations(String ...locations) {
		this.locations = locations;
		return this;
	}
	
	/**
	 * 설정 파일의 상대 경로를 지정합니다.
	 * 
	 * @param locations 설정 파일은 String 배열로 가변인자 넘겨줄 수 있습니다.
	 * @return FunctionalTest 타입을 반환하여 Method Chaning을 활용할 수 있습니다.
	 */
	public FunctionalTest setRelativeLocations(String ...relativeLocations) {
		this.relativeLocations = relativeLocations;
		return this;
	}
	
	/**
	 * 빈으로 등록할 클래스 목록을 설정합니다.
	 * 설정 파일에 등록되어 있지 않은 클래스를 등록할 때 사용합니다.
	 * 
	 * @param classes 빈으로 등록할 클래스 목록
	 * @return FunctionalTest 타입을 반환하여 Method Chaining을 활용할 수 있습니다.
	 */
	public FunctionalTest setClasses(Class<?> ...classes) {
		this.classes = classes;
		return this;
	}
	
	protected FunctionalTest request(String requestUri, String method) {
		this.request = new MockHttpServletRequest(method, requestUri);
		this.response = new MockHttpServletResponse();
		return this;
	}
	
	protected FunctionalTest request(String requestUri, RequestMethod method, String content) {
		this.request = new MockHttpServletRequest(method.toString(), requestUri);
		this.request.setContent(content.getBytes());
		this.response = new MockHttpServletResponse();
		return this;
	}
	
	/**
	 * 요청을 만들 때 사용합니다.
	 * 
	 * @param requestUri 요청할 URI 
	 * @param method 요청 방식
	 * @return FunctionalTest 타입을 반환하여 Method Chaning을 활용할 수 있습니다.
	 */
	public FunctionalTest request(String requestUri, RequestMethod method) {
		return this.request(requestUri, method.toString());
	}
	
	/**
	 * GET 요청을 만들 때 사용합니다.
	 * 
	 * @param requestUri 요청할 URI
	 * @return FunctionalTest 타입을 반환하여 Method Chaining을 활용할 수 있습니다.
	 */
	public FunctionalTest requestGET(String requestUri) {
		request(requestUri, RequestMethod.GET);
		return this;
	}
	
	/**
	 * POST 요청을 만들 때 사용합니다.
	 * 
	 * @param requestUri 요청할 URI
	 * @return FunctionalTest 타입을 반환하여 Method Chaining을 활용할 수 있습니다.
	 */
	public FunctionalTest requestPOST(String requestUri) {
		request(requestUri, RequestMethod.POST);
		return this;
	}
	
	/**
	 * POST 요청을 만들 때 사용합니다.
	 * @param requestUri 요청할 URI
	 * @param content 요청에 보낼 content
	 * @return FunctionalTest 타입을 반환하여 Method Chaining을 활용할 수 있습니다.
	 */
	public FunctionalTest requestPOST(String requestUri, String content) {
		request(requestUri, RequestMethod.POST, content);
		return this;
	}
	
	/**
	 * DELETE 요청을 만들 때 사용한다.
	 * @param requestUri 요청할 URI
	 * @return FunctionalTest 타입을 반환하여 Method Chaining을 활용할 수 있습니다.
	 */
	public FunctionalTest requestDELETE(String requestUri) {
		request(requestUri, RequestMethod.DELETE);
		return this;
	}
	
	/**
	 * PUT 요청을 만들 때 사용한다.
	 * @param requestUri 요청할 URI
	 * @return FunctionalTest 타입을 반환하여 Method Chaining을 활용할 수 있습니다.
	 */
	public FunctionalTest requestPUT(String requestUri) {
		request(requestUri, RequestMethod.PUT);
		return this;
	}

	/**
	 * 요청에 매개변수를 추가합니다.
	 * 
	 * @param name 매개변수 이름
	 * @param value 매개변수 값
	 * @return FunctionalTest 타입을 반환하여 Method Chaining을 활용할 수 있습니다.
	 */
	public FunctionalTest addParameter(String name, String value) {
		if (this.request == null) 
			throw new IllegalStateException("먼저, request()를 사용해서 요청을 만들어야 합니다.");
		this.request.addParameter(name, value);
		return this;
	}
	
	/**
	 * 요청에 content를 추가합니다.
	 * 
	 * @param content 요청에 담을 content 
	 * @return FunctionalTest 타입을 반환하여 Method Chaining을 활용할 수 있습니다.
	 */
	public FunctionalTest setContent(String content) {
		if (this.request == null) 
			throw new IllegalStateException("먼저, request()를 사용해서 요청을 만들어야 합니다.");
		this.request.setContent(content.getBytes());
		return this;
	}
	
	protected FunctionalTest buildDispatcherServlet() throws ServletException {
		if (this.classes == null && this.locations == null && this.relativeLocations == null) 
			throw new IllegalStateException("");
		this.dispatcherServlet = new ConfigurableDispatcherServlet();
		this.dispatcherServlet.setClasses(this.classes);
		this.dispatcherServlet.setLocations(this.locations);
		if (this.relativeLocations != null)
			this.dispatcherServlet.setRelativeLocations(getClass(), this.relativeLocations);
		this.dispatcherServlet.init(this.config);
		
		return this;
	}
	
	/**
	 * 요청을 보내서 실제로 DispatcherServlet이 해당 요청을 실행시키도록 합니다.
	 * 
	 * @return Reponse를 반환합니다. @see Response
	 * @throws ServletException
	 * @throws IOException
	 */
	public Response send() throws ServletException, IOException {
		if (this.dispatcherServlet == null) buildDispatcherServlet(); 
		if (this.request == null) 
			throw new IllegalStateException("먼저, request()를 사용해서 요청을 만들어야 합니다.");
		this.dispatcherServlet.service(this.request, this.response);
		return new Response(this.request, this.response, getModelAndView());
	}
	
	protected ModelAndView getModelAndView() {
		return this.dispatcherServlet.getModelAndView();
	}

	/**
	 * GET 요청이며, 필요한 매개변수가 없을 때, 해당 URI 요청을 바로 실행할 수 있습니다.
	 * @param requestUri 요청할 URI
	 * @return Reponse를 반환합니다. @see Response
	 * @throws ServletException
	 * @throws IOException
	 */
	public FunctionalTest send(String requestUri) throws ServletException, IOException {
		requestGET(requestUri);
		send();
		return this;
	}
	
	/**
	 * 필요한 매개변수가 없을 때, 해당 URI 요청과 요청 방식으로 바로 실행할 수 있습니다.
	 * @param requestUri 요청할 URI
	 * @param method 요청 방식
	 * @return Reponse를 반환합니다. @see Response
	 * @throws ServletException
	 * @throws IOException
	 */
	public FunctionalTest send(String requestUri, String method) throws ServletException, IOException {
		request(requestUri, method);
		send();
		return this;
	}
	
	public WebApplicationContext getContext() {
		if (this.dispatcherServlet == null) 
			throw new IllegalStateException("");
		return this.dispatcherServlet.getWebApplicationContext();
	}
	
	public <T> T getBean(Class<T> beanType) {
		if (this.dispatcherServlet == null) 
			throw new IllegalStateException("");
		return this.getContext().getBean(beanType);
	}
	
	@After
	public void closeServletContext() {
		if (this.dispatcherServlet != null) {
			((ConfigurableApplicationContext)dispatcherServlet.getWebApplicationContext()).close();
		}
	}

}

