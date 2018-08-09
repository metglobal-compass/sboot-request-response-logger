package com.metglobal.compass.logging;

import com.metglobal.compass.logging.model.LogModel;

public class DefaultLogHandler implements LogHandler {
	@Override
	public void handle(LogModel model) {
		System.out.println("URL: " + model.getUrl() + " - status " + model.getStatus().toString());
		//we replace request body for one-lining, response body is mostly one-liner
		System.out.println("Request body: " + model.getRequestBody().replaceAll("\\s{2,}|\\n", ""));
		System.out.println("Response body: " + model.getResponseBody());
	}
}
