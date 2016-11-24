package com.infosys.westing.commerce.dto;


import java.io.Serializable;

public class ControllerOutput {
	private boolean successFlag=true;
	
	private Object content;
	
	private boolean lastRowFlag;
	
	private String info;
	
	private Serializable serialObject;
	
	private Object userParameter;

	
	public Serializable getSerialObject() {
		return serialObject;
	}

	public void setSerialObject(Serializable serialObject) {
		this.serialObject = serialObject;
	}

	public boolean isLastRowFlag() {
		return lastRowFlag;
	}

	public void setLastRowFlag(boolean lastRowFlag) {
		this.lastRowFlag = lastRowFlag;
	}

	public boolean isSuccessFlag() {
		return successFlag;
	}

	public void setSuccessFlag(boolean successFlag) {
		this.successFlag = successFlag;
	}

	public Object getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = content;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public Object getUserParameter() {
		return userParameter;
	}

	public void setUserParameter(Object userParameter) {
		this.userParameter = userParameter;
	}
	
	
	
	
}
