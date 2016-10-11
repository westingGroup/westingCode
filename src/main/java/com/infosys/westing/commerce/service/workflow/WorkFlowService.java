package com.infosys.westing.commerce.service.workflow;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
//import org.activiti.engine.impl.bpmn.diagram.ProcessDiagramGenerator;
import org.activiti.engine.impl.bpmn.diagram.ProcessDiagramLayoutFactory;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.el.JuelExpression;
import org.activiti.engine.impl.el.UelExpressionCondition;
import org.activiti.engine.impl.javax.el.ValueExpression;
import org.activiti.engine.impl.juel.TreeValueExpression;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.delegate.ActivityExecution;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.TransitionImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.Expression;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infosys.westing.commerce.service.workflow.IWorkFlowService;



@Service
@Transactional
public class WorkFlowService implements IWorkFlowService {
	
	@Autowired  ProcessEngineFactoryBean processEngine;

	@Resource protected RuntimeService runtimeService;

	@Resource protected TaskService taskService;

	@Resource protected HistoryService historyService;

	@Resource protected RepositoryService repositoryService;

	@Resource protected IdentityService identityService;
	
	@Resource protected FormService formService;
	

	
	

	
	/**
	 * 根据流程实例id来获得输入流
	 * @param processInstanceId
	 * @return
	 */
	public InputStream getProcessDiagramByInstanceId(String processInstanceId) {
		ProcessInstance processInstance = runtimeService
				.createProcessInstanceQuery()
				.processInstanceId(processInstanceId).singleResult();
		//如果流程没有结束
		if(processInstance != null){
			BpmnModel bpmnModel = repositoryService.getBpmnModel(processInstance
					.getProcessDefinitionId());
			List<String> activeActivityIds = runtimeService
					.getActiveActivityIds(processInstanceId);
			Context.setProcessEngineConfiguration(processEngine
					.getProcessEngineConfiguration());

			//InputStream imageStream = ProcessDiagramGenerator.generateDiagram(bpmnModel, "png", activeActivityIds);
			InputStream imageStream = processEngine.getProcessEngineConfiguration().getProcessDiagramGenerator().generateDiagram(bpmnModel, "png", activeActivityIds);
			
			
			return imageStream;
		}else{
			String definitionId = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult().getProcessDefinitionId();
			BpmnModel bpmnModel = repositoryService.getBpmnModel(definitionId);
/*			List<HistoricActivityInstance>  haiList = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).list();
			List<String> activeActivityIds = new ArrayList<String>();
			for(HistoricActivityInstance hai: haiList){
				activeActivityIds.add(hai.getActivityId());
			}*/
			Context.setProcessEngineConfiguration(processEngine
					.getProcessEngineConfiguration());
			//InputStream imageStream = ProcessDiagramGenerator.generateDiagram(bpmnModel, "png", activeActivityIds);
			//InputStream imageStream =	ProcessDiagramGenerator.generatePngDiagram(bpmnModel);
			InputStream imageStream = processEngine.getProcessEngineConfiguration().getProcessDiagramGenerator().generatePngDiagram(bpmnModel);
			return imageStream;

/*			ProcessDefinition processDefinition = repositoryService
					.createProcessDefinitionQuery()
					.processDefinitionKey(definitionKey).latestVersion()
					.singleResult();

			String diagramResourceName = processDefinition.getDiagramResourceName();
			InputStream imageStream = repositoryService.getResourceAsStream(
					processDefinition.getDeploymentId(), diagramResourceName);
			return imageStream;*/
		}
			
	}

	
	/**
	 * 根据流程定义key来获得输入流
	 * @param definitionKey
	 * @return
	 */
	public InputStream getProcessDiagramByDefinitionKey(String definitionKey) {
		ProcessDefinition processDefinition = repositoryService
				.createProcessDefinitionQuery()
				.processDefinitionKey(definitionKey).latestVersion()
				.singleResult();

		String diagramResourceName = processDefinition.getDiagramResourceName();
		InputStream imageStream = repositoryService.getResourceAsStream(
				processDefinition.getDeploymentId(), diagramResourceName);
		return imageStream;
	}
	
	public String startWorkflow( String definitionName,String businessKey, String userId,
			Map<String, Object> variables) {

		// 用来设置启动流程的人员ID，引擎会自动把用户ID保存到activiti:initiator中
		identityService.setAuthenticatedUserId(userId);
	
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(definitionName, businessKey, variables);
		String processInstanceId = processInstance.getId();
		//entity.setProcessInstanceId(processInstanceId);
		return processInstanceId;
	}

	public List<String> findTodoTasks(String userId, List<String> definitionNameList,
			int firstResult, int maxResult) {
		
		
		//1. 根据当前人和流程定义名集合获得task集合
		List<Task> tasks = new ArrayList<Task>();
		// 2. 根据流程定义列表definitionNameList 来定制一个map , key为definitionName(暂定每个业务都有自己的流程), value 为List<String businessKey>
		List<String> bptfList = new ArrayList<String>();
		
		for(String definitionName: definitionNameList){
			// 3. 根据当前人的ID查询
			TaskQuery todoQuery = taskService.createTaskQuery().processDefinitionKey(definitionName).taskAssignee(userId).active().orderByTaskId().desc().orderByTaskCreateTime().desc();
			List<Task> todoList;
			if(  firstResult ==-1 || maxResult==-1)
				todoList = todoQuery.list();
			else
				todoList = todoQuery.listPage(firstResult, maxResult);
	
			// 4. 根据当前人未签收的任务
			TaskQuery claimQuery = taskService.createTaskQuery().processDefinitionKey(definitionName).taskCandidateUser(userId).active().orderByTaskId().desc().orderByTaskCreateTime().desc();
			List<Task> unsignedTasks;
			if( firstResult ==-1 || maxResult==-1)
				unsignedTasks = claimQuery.list();
			else
				unsignedTasks = claimQuery.listPage(firstResult, maxResult);
	
			// 5. 合并
			tasks.addAll(todoList);
			tasks.addAll(unsignedTasks);
	

			// 6. 根据流程的业务ID查询实体并关联			
			for(Task task: tasks) {
				String processInstanceId = task.getProcessInstanceId();
				ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).active().singleResult();
				String businessKey = processInstance.getBusinessKey();
				String formUrl = formService.getTaskFormData(task.getId()).getFormKey();
				
				
				String assigneeName="";//待修改
				
				
				//String assigneeName = employeeService.getEmployeeById(task.getAssignee()).getEmployeeName();
				bptfList.add(businessKey+","+processInstanceId+","+task.getId()+","+formUrl +","+ task.getName() +","+ assigneeName);
				
			}					
			tasks.clear();
		}

		return bptfList;
	}
	
	
	public void completeTask(String taskId, Map<String, Object> map, Map<String,String> localMap) {
/*		Iterator iterator = localMap.entrySet().iterator();
		while(iterator.hasNext()){
			Map.Entry<String,String> m =(Map.Entry<String,String>)iterator.next();
			taskService.setVariableLocal(taskId, m.getKey(), m.getValue());
		}*/
		
		boolean delegateFlag =  false;
		//1.判断是否是代理任务，如果是 就走代理，因为要返回给原代理发送者
		// 如果不是- 就按正常流程走
		if( taskService.getVariableLocal(taskId, "delegateFlag")!= null){
			delegateFlag = (Boolean)taskService.getVariableLocal(taskId, "delegateFlag");
		}
			 
		//2. 把任务给代理人
		if(delegateFlag){
/*			String delegate = (String)taskService.getVariableLocal(taskId, "delegate");
			String receiver =  (String)taskService.getVariableLocal(taskId, "commissioned");
			taskService.delegateTask(taskId, delegate);
			//将代理任务删除
			taskService.setVariableLocal(taskId, "delegateFlag", false);
			//添加代理的行为日志
			Task task  = taskService.createTaskQuery().taskId(taskId).singleResult();
			
			WorkFlowActionLog wfaLog =  new WorkFlowActionLog();
			wfaLog.setProcessInstanceId(task.getProcessInstanceId());
			wfaLog.setTaskId(taskId);
			wfaLog.setReceiver(receiver);
			wfaLog.setAction("Delegated");
			wfaLog.setTaskName(task.getName());
			wfaLog.setTime(new Date());
			if(localMap.containsKey("remark"))
				wfaLog.setRemark(localMap.get("remark"));
			workFlowActionLogService.save(wfaLog);*/   //代理不考虑目前
		}
		else{		
		
			//3. 用jdk 1.5之后的遍历map, 设置variableLocal
			for (Map.Entry<String, String> m : localMap.entrySet()) {   
				taskService.setVariableLocal(taskId, m.getKey(), m.getValue());
			}
			
			//4. 完成任务
			//boolean authorizationFeeFlag =(Boolean)runtimeService.getVariable(this.getProcessInstanceIdByTaskId(taskId), "authorizationFeeFlag");
			if(map ==null)
				 taskService.complete(taskId);
			else
				taskService.complete(taskId, map);
		}

		
	}
	
	
	/**
	 * 查询流程定义对象
	 * 
	 * @param processDefinitionId
	 *            流程定义ID
	 * @return
	 */
	protected ProcessDefinition getProcessDefinition(String processDefinitionId) {
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
		return processDefinition;
	}
	
	
	/**
	 * 根据流程的taskID查询实体businesskey
	 */
	private String getBussinesskeyByTaskId(String taskId){
		Task task = taskService.createTaskQuery().taskId(taskId).active().singleResult();
		String processInstanceId = task.getProcessInstanceId();
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).active().singleResult();
		String businessKey = processInstance.getBusinessKey();
		return businessKey;
	}
	
	
	/**
	 * 根据流程的taskId分配给指定的人员
	 * @param taskId
	 * @param userId
	 */
	public void delegateTaskCandidate(String taskId, String delegate, String userId, Map<String,Object> variable){
/*		//1.流程任务设置代理标志  
		taskService.setVariableLocal(taskId, "delegateFlag", true);//  放代理标志
		taskService.setVariableLocal(taskId, "delegate", delegate);//  委托人
		taskService.setVariableLocal(taskId, "commissioned", userId);//  被委托人
		//2.代理
		taskService.delegateTask(taskId, userId);
		//3.workFlowActionLogService添加记录
		WorkFlowActionLog wfaLog =  new WorkFlowActionLog();
		
		String processInstanceId = this.getProcessInstanceIdByTaskId(taskId);
		wfaLog.setProcessInstanceId(processInstanceId);
		wfaLog.setTaskId(taskId);
		//wfaLog.setReceiver(userId);
		wfaLog.setReceiver(delegate);
		String taskName = this.getTaskNameByTaskId(taskId);
		wfaLog.setAction("Delegated");
		wfaLog.setTaskName(taskName);
		wfaLog.setTime(new Date());
		if(variable.containsKey("remark")){
			wfaLog.setRemark((String)variable.get("remark"));
		}
			
		workFlowActionLogService.save(wfaLog);*/    //代理不考虑
	}
	
	/**
	 * 根据taskId获得processInstanceId
	 * @param taskId
	 * @return
	 */
	public String getProcessInstanceIdByTaskId(String taskId){
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		return processInstanceId;
	}
	
	/**
	 * 根据taskId获得taskName
	 * @param taskId
	 * @return
	 */
	public String getTaskNameByTaskId(String taskId){
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String taskName = task.getName();
		return taskName;
	}
	
	/**
	 * 根据taskId获得description
	 * @param taskId
	 * @return
	 */
	public String getDescriptionByTaskId(String taskId){
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String description = task.getDescription();
		return description;
	}
	
	/**
	 * 根据processInstanceId获得单个taskId(如果有两个active的task会出错)
	 * @param taskId
	 * @return
	 */
	public String getTaskIdByProcessInstanceId(String processInstanceId){
		if(taskService.createTaskQuery().processInstanceId(processInstanceId).active().count()!=1)
			return null;
		else
			return taskService.createTaskQuery().processInstanceId(processInstanceId).active().singleResult().getId();
	}
	
	/**
	 * 申请者根据processInstanceId来取回到某个人工任务
	 * @param processInstanceId
	 * @param returnUserTaskId 
	 */

	public void recallTask(String processInstanceId, String returnUserTaskId){
		//1. 根据processInstanceId来获得活动的task列表
		
		//2. 如果有taskId可以取回，就调用类似reject的方法
		
		
		//动态方法研究
		if(returnUserTaskId==null){
			returnUserTaskId="usertask1";
		}
		String taskId = this.getTaskIdByProcessInstanceId(processInstanceId);
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		ExecutionEntity exe = (ExecutionEntity)runtimeService.createExecutionQuery().executionId(task.getExecutionId()).singleResult();
		ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
		String definitionId = pi.getProcessDefinitionId();
		ProcessDefinitionEntity pde = 
				(ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService).getDeployedProcessDefinition(definitionId); //this can be cached
		List<PvmTransition> out1 = pde.findActivity(exe.getActivityId()).getOutgoingTransitions();
		//判断是否有重复的transition
		boolean transitionAddFlag = true;
		for(PvmTransition pt : out1){
			if(null!=pt.getProperty("approveFlag") && pt.getProperty("approveFlag").equals("reject")){
				transitionAddFlag = false;
				break;
			}
		}
		//如果需要加transition
		List<PvmTransition> out2 =null;
		TransitionImpl transition = null;
		if(transitionAddFlag){
			transition = pde.findActivity(exe.getActivityId()).createOutgoingTransition();
	
			ActivityImpl destination= pde.findActivity(returnUserTaskId);
			transition.setDestination(destination);
			transition.setProperty("approveFlag", "recall");
			out2 = pde.findActivity(exe.getActivityId()).getOutgoingTransitions();
		}
		Map<String,Object> variables = new HashMap<String,Object>();
		variables.put("approveFlag","recall");
		
		Map<String,String> localMap = new HashMap<String,String>();
		localMap.put("action", "Recalled");
		
		localMap.put("remark", "the Applier recall");
		//this.completeTask(taskId, variables , localMap);
		taskService.setVariableLocal(taskId, "action", localMap.get("action"));
		taskService.setVariableLocal(taskId, "remark", localMap.get("remark"));
		taskService.complete(taskId, variables);
		//移除新增的transition
		out2.remove(transition);
	}
	
	/**
	 * 审批者拒绝某申请
	 * @param processInstanceId
	 * @param returnUserTaskId
	 * @param remark
	 */
	public void rejectTask(String taskId, String returnUserTaskId, String remark){
		if(returnUserTaskId==null){
			returnUserTaskId="usertask1";
		}
		//采用动态方法(存)
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		ExecutionEntity exe = (ExecutionEntity)runtimeService.createExecutionQuery().executionId(task.getExecutionId()).singleResult();
		ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
		String definitionId = pi.getProcessDefinitionId();
		ProcessDefinitionEntity pde = 
				(ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService).getDeployedProcessDefinition(definitionId); //this can be cached
		List<PvmTransition> out1 = pde.findActivity(exe.getActivityId()).getOutgoingTransitions();

		//判断是否有重复的transition
		boolean transitionAddFlag = true;
		for(PvmTransition pt : out1){
			if(null!=pt.getProperty("approveFlag") && pt.getProperty("approveFlag").equals("reject")){
				transitionAddFlag = false;
				break;
			}
		}
		//如果需要加transition
		List<PvmTransition> out2 =null;
		TransitionImpl transition = null;
		if(transitionAddFlag){
			 transition = pde.findActivity(exe.getActivityId()).createOutgoingTransition();
			 ActivityImpl destination= pde.findActivity(returnUserTaskId);
			 transition.setDestination(destination);
			 transition.setProperty("approveFlag", "reject");
			 //transition.setProperty("condition", condition);
			 out2 = pde.findActivity(exe.getActivityId()).getOutgoingTransitions();

		}
		Map<String,Object> variables = new HashMap<String,Object>();
		variables.put("approveFlag","reject");
		
		Map<String,String> localMap = new HashMap<String,String>();
		localMap.put("action", "Rejected");		
		localMap.put("remark", remark);
		//this.completeTask(taskId, variables , localMap);
		taskService.setVariableLocal(taskId, "action", localMap.get("action"));
		taskService.setVariableLocal(taskId, "remark", localMap.get("remark"));
		taskService.complete(taskId, variables);
		//移除新增的transition
		out2.remove(transition);
		
	}
	
	/**
	 * 判断某个definition是否激活
	 * @param definitionKey
	 * @return
	 */
	public boolean isActivedProcessDefinition(String definitionKey){
		boolean returnflag = true;
		ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery().processDefinitionKey(definitionKey).latestVersion().active();
		if(null == processDefinitionQuery.list() ||processDefinitionQuery.list().size()==0)
			returnflag = false;
		return returnflag;
	}
	
	/**
	 * 根据taskId获得URL
	 * @param taskId
	 * @return
	 */
	public String getFormUrlByTaskId(String taskId){
		String formUrl = formService.getTaskFormData(taskId).getFormKey();
		return formUrl;
	}
	
	/**
	 * 根据流程实例获得当前任务的assignee
	 * @param processInstanceId
	 * @return
	 */
	public String getAssigneeByProcessInstanceId(String processInstanceId){
		String assignee = null;
		Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).active().singleResult();
		if(null!=task){
			assignee = task.getAssignee();
		}
		return assignee;
	}
	
	/**
	 * 根据流程id获得历史流程的applyUserId
	 * @param processInstanceId
	 * @return
	 */
	public String getApplyUserId(String processInstanceId){
		  HistoricVariableInstance  historicVariableInstance = 
		  historyService.createHistoricVariableInstanceQuery().processInstanceId(processInstanceId).variableName("applyUserId").singleResult();  
		  return  (String)historicVariableInstance.getValue();

	}
	
	/**
	 * 根据流程实例id获得businessKey
	 * @param processInstanceId
	 * @return
	 */
	public String getBusinessKeyByProcessInstanceId(String processInstanceId){
		if(runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).count()>0){
			ProcessInstance p = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
			return p.getBusinessKey();
		}else{
			 HistoricProcessInstance hp = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
			 return hp.getBusinessKey();
		}
	}
	
	/**
	 * 根据taskIdd获得流程节点定义名称
	 * @param taskId
	 * @return
	 */
	public String getTaskDefinitionKeyByTaskId(String taskId){
		if(taskService.createTaskQuery().taskId(taskId).count() == 0 )
			return null;
			
		return taskService.createTaskQuery().taskId(taskId).singleResult().getTaskDefinitionKey();
	}
	
	/**
	 * 根据流程的taskId分配或收回
	 * 
	 * @param taskId
	 * @param userId
	 */
	public void temporaryAuthorizedTask(String taskId, String delegator,
			String assigner, Map<String, Object> variable, boolean isRevoke) {
		// 1.流程任务设置代理标志

		String strAction = "";
		if (!isRevoke) {
			taskService.setVariableLocal(taskId, "tempAuthorizedFlag", true);// 放代理标志
			taskService.setVariableLocal(taskId, "delegator", delegator);// 委托人
			taskService.setVariableLocal(taskId, "assigner", assigner);// 被委托人
			strAction = "temporary authorized";
			
			// 2.代理
			taskService.delegateTask(taskId, assigner);
		}
		else{
			taskService.setVariableLocal(taskId, "tempAuthorizedFlag", false);// 放代理标志
			//taskService.removeVariableLocal(taskId, "delegator");
			//taskService.removeVariableLocal(taskId, "assigner");// 被委托人
			strAction = "revoke temporary authorized";
			// 2.代理
			taskService.delegateTask(taskId, delegator);
		}
		
		// 3.workFlowActionLogService添加记录
/*		WorkFlowActionLog wfaLog = new WorkFlowActionLog();

		String processInstanceId = this.getProcessInstanceIdByTaskId(taskId);
		wfaLog.setProcessInstanceId(processInstanceId);
		wfaLog.setTaskId(taskId);
		wfaLog.setReceiver(assigner);
		String taskName = this.getTaskNameByTaskId(taskId);
		wfaLog.setAction(strAction);

		wfaLog.setTaskName(taskName);
		wfaLog.setTime(new Date());
//		if (variable.containsKey("remark")) {
//			wfaLog.setRemark((String) variable.get("remark"));
//		}

		workFlowActionLogService.save(wfaLog);*/    //代理不考虑
	}
	
	public boolean checkHua(String processInstanceId){
		if( runtimeService.createProcessInstanceQuery().active().processInstanceId(processInstanceId).count() == 0)
			return false;
		String taskId = this.getTaskIdByProcessInstanceId(processInstanceId);
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String taskDefinitonKey = task.getTaskDefinitionKey();
		//判断当前节点人是不是hua bangsong
		if(task.getAssignee().equals("0000001")){
/*			if(taskDefinitonKey.contains("-C-") || taskDefinitonKey.contains("-P-")){
				//获取申请人applicant
			
			}*/
			return true;
			
		}else
			return false;
	}
	
	public void delegateHuaToFinanceMgr(String processInstanceId){
		String taskId = this.getTaskIdByProcessInstanceId(processInstanceId);
		String userId = (String)runtimeService.getVariable(processInstanceId, "financeMgr");		
		//如果是zhaohongbin 代替hua 
		if(userId.equals("0100287")){
			//就转给hanjirong
			userId = "0100022";
		}
		taskService.delegateTask(taskId, userId);		
		//说明该task是代替hua bangsong 
		taskService.setVariableLocal(taskId, "replaceHuaFlag", true);
	}
	
	public void delegateHuaToStepTwo(String companyId, String processInstanceId){
/*		String taskId = this.getTaskIdByProcessInstanceId(processInstanceId);
		QueryResult<SpecialCompanysForHua> qr = specialCompanysForHuaService.getScrollData(-1, -1, "isDeleted !='N' and o.orgId=?1 ", new Object[]{companyId});
		taskService.setVariableLocal(taskId, "replaceHuaFlag", true);
		if(qr.getRecordtotal()==0){
			//to qusong
			taskService.delegateTask(taskId, "0000002");		
			return;
		}
		String orgType = qr.getResultList().get(0).getOrgType();
		if(orgType.equals("gongcheng")){
			//to LiuHaijun
			taskService.delegateTask(taskId, "0100002");		
		}
		if(orgType.equals("haigong")){
			//to yanfeng
			taskService.delegateTask(taskId, "0000015");		
		}*/	//代理不考虑
	}
}