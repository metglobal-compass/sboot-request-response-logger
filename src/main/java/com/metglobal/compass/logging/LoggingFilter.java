package com.metglobal.compass.logging;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import com.metglobal.compass.logging.model.LogModel;
import org.apache.commons.io.IOUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

@Component
public class LoggingFilter extends OncePerRequestFilter {

	private final LogHandler handler;

	public LoggingFilter() {
		this.handler = new DefaultLogHandler();
	}

	@Autowired
	public LoggingFilter(LogHandler handler) {
		this.handler = handler;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		LogModel model = new LogModel();

		//we should use wrapper for request too, because input stream closes after request
		ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
		ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

		filterChain.doFilter(requestWrapper, responseWrapper);

		//get main url and check any query string exists
		String mainUrl = requestWrapper.getRequestURL().toString(); //url path included except queries
		String nextPath = requestWrapper.getQueryString();
		nextPath = (nextPath == null) ? "" : "?" + nextPath; //will be empty if null
		model.setUrl(mainUrl + nextPath);

		//get request body if there is a post method
		String requestBody = new String(requestWrapper.getContentAsByteArray());
		model.setRequestBody(requestBody);

		//set status as httpstatus object
		int status = responseWrapper.getStatusCode();
		model.setStatus(HttpStatus.valueOf(status));

		//get body stream from response and copy to output again because stream is emptied
		String responseBody = IOUtils.toString(responseWrapper.getContentInputStream(), "UTF-8");
		model.setResponseBody(responseBody);
		responseWrapper.copyBodyToResponse();

		handler.handle(model);
	}
}
