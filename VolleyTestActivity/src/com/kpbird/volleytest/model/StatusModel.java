package com.kpbird.volleytest.model;

public class StatusModel {

	int rcode;
	String message = "";

	public StatusModel() {
	}

	public int getRcode() {
		return rcode;
	}

	public void setRcode(int rcode) {
		this.rcode = rcode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
