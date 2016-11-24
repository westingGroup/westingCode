package com.infosys.westing.commerce.dto.workflow;

import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.runtime.ProcessInstance;

import com.infosys.westing.commerce.util.DateUtil;

/**
 * 流程实例dto
 * 
 * @author Lily_Xue
 *
 */
public class ProcessInstanceDto {

	private String id;// 实例id
	private String processDefinitionId;// 流程定义id
	private String businessKey;// 业务key
	private String startTime;// 开始时间
	private String endTime;// 结束时间

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProcessDefinitionId() {
		return processDefinitionId;
	}

	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}

	public String getBusinessKey() {
		return businessKey;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public static ProcessInstanceDto transHisToDto(
			HistoricProcessInstance historicProcessInstance) {
		ProcessInstanceDto processInstanceDto = new ProcessInstanceDto();
		processInstanceDto.setId(historicProcessInstance.getId());
		processInstanceDto.setProcessDefinitionId(historicProcessInstance
				.getProcessDefinitionId());
		processInstanceDto.setBusinessKey(historicProcessInstance
				.getBusinessKey());
		if (historicProcessInstance.getStartTime() != null)
			processInstanceDto.setStartTime(DateUtil.format(
					historicProcessInstance.getStartTime(),
					"yyyy-MM-dd HH:mm:ss"));
		if (historicProcessInstance.getEndTime() != null)
			processInstanceDto
					.setEndTime(DateUtil.format(
							historicProcessInstance.getEndTime(),
							"yyyy-MM-dd HH:mm:ss"));
		return processInstanceDto;
	}

	public static ProcessInstanceDto transEntityToDto(
			ProcessInstance processInstance) {
		ProcessInstanceDto processInstanceDto = new ProcessInstanceDto();
		processInstanceDto.setId(processInstance.getId());
		processInstanceDto.setProcessDefinitionId(processInstance
				.getProcessDefinitionId());
		processInstanceDto.setBusinessKey(processInstance.getBusinessKey());
		return processInstanceDto;
	}
}
