package com.metglobal.compass.logging;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class LoggingFilterUnitTest {

	private final ByteArrayOutputStream tempOut = new ByteArrayOutputStream();
	private final PrintStream actualOut = System.out;

	@Before
	public void setUpStreams() {
		//log to temporary console for testing instead of system.out
		System.setOut(new PrintStream(tempOut));
	}

	@After
	public void restore() {
		System.setOut(actualOut); //bring system.out back
	}

	@Test
	public void testFilter() throws Exception {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setRequestURI("/path?query=q1");
		String reqBody = "{\"req\" : 23}";
		request.setContent(reqBody.getBytes());
		request.setContentType("application/json");
		request.setMethod("POST");

		MockHttpServletResponse response = new MockHttpServletResponse();

		MockChain chain = new MockChain();

		LoggingFilter filter = new LoggingFilter();
		filter.doFilterInternal(request, response, chain);

		String output = tempOut.toString();

		assert output.contains(request.getRequestURL().toString());
		assert output.contains(reqBody); //request
		assert output.contains(response.getContentAsString()); //response
		assert output.contains(Integer.toString(response.getStatus()));
	}
}
