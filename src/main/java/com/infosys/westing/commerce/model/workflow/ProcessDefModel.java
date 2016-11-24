package com.infosys.westing.commerce.model.workflow;

import com.infosys.westing.commerce.dto.workflow.ProcessDefDto;
import com.infosys.westing.commerce.model.SearchModel;

public class ProcessDefModel extends SearchModel<ProcessDefDto> {

	private String processKey;//流程key
	
	private String processName;//流程名称

	public String getProcessKey() {
		return processKey;
	}

	public void setProcessKey(String processKey) {
		this.processKey = processKey;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

}
