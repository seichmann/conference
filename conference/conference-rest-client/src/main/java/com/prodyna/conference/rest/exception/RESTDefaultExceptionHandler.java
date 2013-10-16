package com.prodyna.conference.rest.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class RESTDefaultExceptionHandler implements ExceptionMapper<Exception> {

	@Override
	public Response toResponse(Exception e) {
		ErrorResponse response = new ErrorResponse();
		response.setStatus(Status.INTERNAL_SERVER_ERROR.getStatusCode());
		response.setMessage(getRootException(e).getMessage());

		return Response.serverError().entity(response).build();
	}

	private Throwable getRootException(Throwable t) {
		// Start with the exception and recurse to find the root cause
		Throwable root = null;
		while (t != null) {
			root = t;
			t = t.getCause();
		}

		return root;
	}

}
