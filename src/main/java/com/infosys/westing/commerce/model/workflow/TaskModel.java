package com.infosys.westing.commerce.model.workflow;

import org.activiti.engine.impl.persistence.entity.TaskEntity;

import com.infosys.westing.commerce.model.SearchModel;

public class TaskModel extends SearchModel<TaskEntity> {

	private String assignId;//指派人员
	
	private String definitionKeyPrefix;//流程定义key前缀

	public String getAssignId() {
		return assignId;
	}

	public void setAssignId(String assignId) {
		this.assignId = assignId;
	}

	public String getDefinitionKeyPrefix() {
		return definitionKeyPrefix;
	}

	public void setDefinitionKeyPrefix(String definitionKeyPrefix) {
		this.definitionKeyPrefix = definitionKeyPrefix;
	}
}
