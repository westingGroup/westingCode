package com.infosys.westing.commerce.service.workflow;


import java.io.InputStream;
import java.util.List;
import java.util.Map;


public interface IWorkFlowService {
	
	/**
	 * 根据流程实例id来获得输入流
	 * @param processInstanceId
	 * @return
	 */
	public InputStream getProcessDiagramByInstanceId(String processInstanceId);
	
	/**
	 * 根据流程定义id来获得输入流
	 * @param definitionKey
	 * @return
	 */
	public InputStream getProcessDiagramByDefinitionKey(String definitionKey);
	
	
	/**
	 * 启动流程
	 * 
	 * @param 
	 */
	public String startWorkflow(String bussinessName,String businessKey, String userId,
			Map<String, Object> variables);
	
	
	/**
	 * 查询待办任务
	 * 
	 * @param userId
	 *            用户ID
	 * @return
	 */
	public  List<String> findTodoTasks(String userId, List<String> definitionNameList,  int firstResult, int maxResult);
	
	/**
	 * 完成任务
	 * @param taskId
	 * @param var
	 * @return
	 */
	 public  void completeTask(String taskId, Map<String, Object> map , Map<String,String> localMap);
	 
	 
	 /**
	  * 根据流程的taskId分配给指定的人员
	  * @param taskId
	  * @param userId
	  */
	 public void delegateTaskCandidate(String taskId, String delegate, String userId, Map<String,Object> variable);
	 
	 /**
	  * 根据taskId获得processInstanceId
	  * @param taskId
	  * @return
	  */
	  public String getProcessInstanceIdByTaskId(String taskId);
	  
		/**
		 * 根据taskId获得taskName
		 * @param taskId
		 * @return
		 */
		public String getTaskNameByTaskId(String taskId);
		
		/**
		 * 根据taskId获得description
		 * @param taskId
		 * @return
		 */
		public String getDescriptionByTaskId(String taskId);
		
		/**
		 * 根据processInstanceId获得单个taskId
		 * @param taskId
		 * @return
		 */
		public String getTaskIdByProcessInstanceId(String processInstanceId);
		
		
		/**
		 * 申请者根据processInstanceId来取回到某个人工任务
		 * @param processInstanceId
		 * @param returnUserTaskId 
		 */
		public void recallTask(String processInstanceId, String returnUserTaskId);
		
		/**
		 * 审批者拒绝某申请
		 * @param taskId
		 * @param returnUserTaskId
		 * @param remark
		 */
		public void rejectTask(String taskId, String returnUserTaskId, String remark);
		
		
		
		/**
		 * 判断某个definition是否激活
		 * @param definitionKey
		 * @return
		 */
		public boolean isActivedProcessDefinition(String definitionKey);
		
		/**
		 * 根据taskId获得URL
		 * @param taskId
		 * @return
		 */
		public String getFormUrlByTaskId(String taskId);
		
		
		/**
		 * 根据流程实例获得当前任务的assignee
		 * @param processInstanceId
		 * @return
		 */
		public String getAssigneeByProcessInstanceId(String processInstanceId);
		
		
		/**
		 * 根据流程id获得历史流程的applyUserId
		 * @param processInstanceId
		 * @return
		 */
		public String getApplyUserId(String processInstanceId);
		
		/**
		 * 根据流程实例id获得businessKey
		 * @param processInstanceId
		 * @return
		 */
		public String getBusinessKeyByProcessInstanceId(String processInstanceId);

		/**
		 * 根据taskIdd获得流程节点定义名称
		 * @param taskId
		 * @return
		 */
		public String getTaskDefinitionKeyByTaskId(String taskId);
		/**
		 * 方法说明 请Ben fu 补充
		 * @param taskId
		 * @param delegator
		 * @param assigner
		 * @param variable
		 * @param isRevoke
		 */
		void temporaryAuthorizedTask(String taskId, String delegator,
				String assigner, Map<String, Object> variable, boolean isRevoke);
		
		/**
		 * 检查流程任务的接受人是否是hua bang song 
		 * @param processInstanceId
		 * @return
		 */
		public boolean checkHua(String processInstanceId);
		
		/**
		 * 代理hua bangsong 到 财务经理
		 * @param ProcessInstanceId
		 */
		public void delegateHuaToFinanceMgr(String ProcessInstanceId);
		
		/**
		 * 代理hua bangsong 到步骤2模式
		 * 费用公司编号为"01" 、"02" 、 "15"  、 "16"、"22"（工程板块） 的所有流程到bshua的单子自动转到liuhaijun名下审核，到财务经理zhaohongbin的单子自动转到hanjirong名下审核。
		 * 费用公司编号为"05" 、"06" 、"18" 、"31"、"32"、"39"、"43"、"45" （海工板块）的所有流程到bshua的单子自动转到yanfeng名下审核。
		 * 其他公司的流程到bshua的单子全部转移到qusong名下审核
		 * 建议实现方式：
		 * 按公司、人员、流程进行流程转移配置
		 */
		public void delegateHuaToStepTwo(String companyId, String processInstanceId);

}
