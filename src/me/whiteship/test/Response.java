package me.whiteship.test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.UnsupportedEncodingException;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.ModelAndView;

/**
 * 이 클래스는 FunctionalTest에서 send()를 호출했을 때 결과로 받는 타입입니다.
 * 테스트 했던 요청과 그에 대한 응답 그리고 핸들러가 반환하는 ModelAndView를 참조할 수 있으며, 
 * 각종 assertXXX 문을 활용하여 응답의 결과를 검증할 수 있습니다.
 * 
 * @author keesun
 *
 */
public class Response {
	
	MockHttpServletResponse response;
	MockHttpServletRequest request;
	ModelAndView modelAndView;

	public Response(MockHttpServletRequest request,
			MockHttpServletResponse response, ModelAndView modelAndView) {
		this.request = request;
		this.response = response;
		this.modelAndView = modelAndView;
	}

	public MockHttpServletResponse getResponse() {
		return response;
	}

	public MockHttpServletRequest getRequest() {
		return request;
	}

	public ModelAndView getModelAndView() {
		return modelAndView;
	}
	
	/**
	 * ModelAndView에서 Model에 들어있는 정보 중에서 특정 모델 정보를 확인합니다.
	 * @param name 모델 이름
	 * @param value 모델 값
	 * @return Response 타입을 반환하여 Method Chaining을 사용할 수 있게 합니다.
	 */
	public Response assertModel(String name, Object value) {
		assertThat(this.getModelAndView().getModel().get(name), is(value));
		return this;
	}

	/**
	 * ModelAndView에서 View 이름을 확인합니다.
	 * @param viewName 뷰 이름
	 * @return Response 타입을 반환하여 Method Chaining을 사용할 수 있게 합니다.
	 */
	public Response assertViewName(String viewName) {
		assertThat(this.getModelAndView().getViewName(), is(viewName));
		return this;
	}

	public String getContentAsString() {
		try {
			return this.response.getContentAsString();
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 응답 코드가 200인지 확인합니다.
	 * @return Response 타입을 반환하여 Method Chaining을 사용할 수 있게 합니다.
	 */
	public Response assertIsOk() {
		return assertStatus(200);
	}
	
	/**
	 * 응답 코드가 404인지 확인합니다.
	 * @return Response 타입을 반환하여 Method Chaining을 사용할 수 있게 합니다.
	 */
	public Response assertIsNotOk() {
		return assertStatus(404);
	}
	
	/**
	 * 응답 코드를 확인합니다.
	 * @param statusCode 확인하려는 상태 코드
	 * @return Response 타입을 반환하여 Method Chaining을 사용할 수 있게 합니다.
	 */
	public Response assertStatus(int statusCode) {
		assertThat(response.getStatus(), is(statusCode));
		return this;
	}
	
	/**
	 * 응답의 본문을 확인합니다.
	 * @param content 확인할 문구
	 * @return Response 타입을 반환하여 Method Chaining을 사용할 수 있게 합니다.
	 */
	public Response assertContentEquals(String content) {
		assertTrue(getContentAsString().contains(content));
		return this;
	}

}
