package com.prodyna.conference.rest.exception;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ErrorResponse {
	private int status;
	private String message;

	public int getStatus() {
		return status;
	}

	@XmlElement
	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	@XmlElement
	public void setMessage(String message) {
		this.message = message;
	}
}
