package com.metglobal.compass.logging;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

import org.springframework.util.FileCopyUtils;

/**
 * a mock implementation for testing because package has no services
 */
public class MockChain implements FilterChain {
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse)
			throws IOException, ServletException {
		//needs buffer for pass of test, so it will run as typical Spring application
		byte[] buff = FileCopyUtils.copyToByteArray(servletRequest.getInputStream());
		servletResponse.getWriter().println("Here is your response");
	}
}
