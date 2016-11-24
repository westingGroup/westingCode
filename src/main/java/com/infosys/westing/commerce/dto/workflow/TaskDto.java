package com.infosys.westing.commerce.dto.workflow;

import org.activiti.engine.task.DelegationState;
import org.activiti.engine.task.Task;
import org.apache.commons.lang.StringUtils;

import com.infosys.westing.commerce.util.Constants;
import com.infosys.westing.commerce.util.DateUtil;

/**
 * 任务dto
 * 
 * @author Lily_Xue
 */
public class TaskDto {

	private String id;// 任务id
	private String executionId;// 执行id
	private String processInstanceId;// 实例id
	private String processDefinitionId;// 定义id
	private String name;// 名称
	private String parentTaskId;// 上级taskId
	private String taskDefinitionKey;// 任务定义key
	private String assignee;// 指派人
	private int priority;// 优先级
	private String createTime;// 创建时间
	private String formKey;// 网址
	private String taskStatus;//任务状态
	private String taskStatusName;//任务状态名称

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getExecutionId() {
		return executionId;
	}

	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getProcessDefinitionId() {
		return processDefinitionId;
	}

	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentTaskId() {
		return parentTaskId;
	}

	public void setParentTaskId(String parentTaskId) {
		this.parentTaskId = parentTaskId;
	}

	public String getTaskDefinitionKey() {
		return taskDefinitionKey;
	}

	public void setTaskDefinitionKey(String taskDefinitionKey) {
		this.taskDefinitionKey = taskDefinitionKey;
	}

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getFormKey() {
		return formKey;
	}

	public void setFormKey(String formKey) {
		this.formKey = formKey;
	}
	
	public String getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}
	
	public String getTaskStatusName() {
		return taskStatusName;
	}

	public void setTaskStatusName(String taskStatusName) {
		this.taskStatusName = taskStatusName;
	}

	public static TaskDto tranEntityToDto(Task task) {
		TaskDto taskDto = new TaskDto();
		taskDto.setId(task.getId());
		taskDto.setExecutionId(task.getExecutionId());
		taskDto.setProcessInstanceId(task.getProcessInstanceId());
		taskDto.setProcessDefinitionId(task.getProcessDefinitionId());
		taskDto.setName(task.getName());
		taskDto.setParentTaskId(task.getParentTaskId());
		taskDto.setTaskDefinitionKey(task.getTaskDefinitionKey());
		taskDto.setAssignee(task.getAssignee());
		taskDto.setPriority(task.getPriority());
		taskDto.setFormKey(task.getFormKey());
		if (task.getCreateTime() != null)
			taskDto.setCreateTime(DateUtil.format(task.getCreateTime(),
					"yyyy-MM-dd HH:mm:ss"));
		if(StringUtils.isEmpty(task.getAssignee())){
			taskDto.setTaskStatus(Constants.PRE_SIGN_STATUS_TASK);
			taskDto.setTaskStatusName(Constants.PRE_SIGN_STATUS_TASK_NAME);
		}else if(task.getDelegationState() == null){
			taskDto.setTaskStatus(Constants.IN_HANDLE_STATUS_TASK);
			taskDto.setTaskStatusName(Constants.IN_HANDLE_STATUS_TASK_NAME);
		}else if(task.getDelegationState() == DelegationState.PENDING){
			taskDto.setTaskStatus(Constants.BE_DELEGATED_STATUS_TASK);
			taskDto.setTaskStatusName(Constants.BE_DELEGATED_STATUS_TASK_NAME);
		}else if(task.getDelegationState() == DelegationState.RESOLVED){
			taskDto.setTaskStatus(Constants.RESOVLED_DELEGATED_STATUS_TASK);
			taskDto.setTaskStatusName(Constants.RESOVLED_DELEGATED_STATUS_TASK_NAME);
		}
		return taskDto;
	}
}
