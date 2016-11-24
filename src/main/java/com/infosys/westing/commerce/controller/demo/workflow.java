package com.infosys.westing.commerce.controller.demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.infosys.westing.commerce.entity.demo.DemoEntity;
import com.infosys.westing.commerce.service.demo.IDemoService;
import com.infosys.westing.commerce.service.workflow.IWorkFlowService;

@Controller
@RequestMapping("/demo")
public class workflow {
	
	@Inject
	private IDemoService demoService;
	
	@Inject
	private  IWorkFlowService workFlowService;
	@Inject
	private RuntimeService runtimeService;
	@Inject
	private IdentityService identityService;
	@Inject
	private HistoryService historyService;
	@Inject
	private TaskService taskService;
	@Inject
	private RepositoryService repositoryService;
	
	
	

	@RequestMapping(value = "/insertDemo", method = RequestMethod.GET)
	@ResponseBody
	public String insertDemo(String inputJson, HttpSession session){
		DemoEntity demo =new DemoEntity();
		demo.setName("1st");
		demoService.insertDemo(demo);
		return "insert success";
	}
	
	@RequestMapping(value = "/workflowManagement", method = RequestMethod.GET)
	public String workflowManagement(String inputJson, HttpSession session){
		return "workflowManagement";
	}
	
	
	@RequestMapping(value = "/jqEvent", method = RequestMethod.GET)
	public String jqEvent(String inputJson, HttpSession session){
		return "jquery_event01";
	}
	
	@RequestMapping(value = "/deployFlow", method = RequestMethod.POST)
	@ResponseBody
	public String deployFlow(String fileName, HttpSession session) {
		
		String pathName=null;
		try{
			if(fileName.contains("fakepath")){
				int length = fileName.lastIndexOf(File.separator);
				fileName = fileName.substring(length);
				//ActionContext ac = ActionContext.getContext();
		        //ServletContext sc = (ServletContext) ac.get(ServletActionContext.SERVLET_CONTEXT);
				ServletContext sc = session.getServletContext();
		        String abPath = sc.getRealPath("");
		        abPath = abPath.substring(0,abPath.lastIndexOf(File.separator));
		        pathName = abPath+File.separator+"resources"+File.separator+"manualDeploy"+fileName;
		        System.out.print(pathName);
				//String path = WorkFlowManagerAction.class.getClassLoader().getResource("AW-C-WGH.bar").getPath();
/*				String basePath = WorkFlowManagerAction.class.getClassLoader().getResource("")  
						   .getFile().replaceAll("/WEB-INF/classes/", "").replaceAll("%20", " ").substring(1);  
				System.out.print(basePath);*/
			}
			if(pathName==null)
				pathName = fileName;
			//workFlowManagerService.deployFlow(pathName);
			
			
			File file = new File(pathName);
			
			InputStream fileInputStream = new FileInputStream(file);
			String extension = FilenameUtils.getExtension(pathName);
			if (extension.equals("zip") || extension.equals("bar")) {
				ZipInputStream zip = new ZipInputStream(fileInputStream);
				repositoryService.createDeployment().addZipInputStream(zip)
						.deploy();
			} else {
				repositoryService.createDeployment()
						.addInputStream(pathName, fileInputStream).deploy();
			}

		}catch(Exception ex){
			return ex.getMessage();
		}
		return pathName+":successfully deployed";
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/startProcess", method = RequestMethod.POST)
	@ResponseBody
	public String startProcess(String inputJson, HttpSession session) {
		DemoEntity entity = new DemoEntity();
		
		// 1. varibles
		Map<String,Object> variables = new HashMap<String,Object>();		
		// 2. 根据业务名称获得流程定义名称
		String definitionName = "demoProcess";
		// 3. 获得eneity 的key
		String demoName = inputJson;
		entity.setName(demoName);
		demoService.insertDemo(entity);	
		String businessKey  = String.valueOf(entity.getDemoId());
		// 4.validation
		
		// 5. 工作流启动
		// workflowRunService.startWorkflow(definitionName, businessKey, userId, variables);
		String userId = "demoUser";
		// 用来设置启动流程的人员ID，引擎会自动把用户ID保存到activiti:initiator中
		identityService.setAuthenticatedUserId(userId);
		variables.put("applyUserId", userId);	//这个如果设置启动节点initiator 可以不写
		
		String approverId = "approverUser";
		variables.put("approverId", approverId);
		

		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(definitionName, businessKey, variables);
		String processInstanceId = processInstance.getId();
		
		// 6. 把InstanceId 传入entity保存
		entity.setProcessInstanceId(processInstanceId);
		demoService.updateDemo(entity);

		
		//7. 做第一步提交动作
		Task task  = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
		String taskId = task.getId();
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,String> localMap = new HashMap<String,String>();
		localMap.put("action", "Submitted");
		//localMap.put("remark", entity.getRemarks());
		//runtimeService.setVariable(processInstanceId, "accruedListenerService", accruedListenerService);
		workFlowService.completeTask(taskId, map , localMap);
		
		return "success";

	}
		
	
	@RequestMapping(value = "/queryTodo", method = RequestMethod.POST, produces = "application/text; charset=utf-8")
	@ResponseBody
	public String queryTodo(String inputJson,   HttpServletResponse resp){
		//resp.setContentType("text/html;charset=UTF-8");
		String userId="approverUser";
		List<String> definitionNameList = new ArrayList<String>();
		definitionNameList.add("demoProcess");
		List<String> demoList = workFlowService.findTodoTasks(userId, definitionNameList, 0, 100);
		//demo只显示一条记录
		if(demoList.size()==0){
			return null;
		}
		String demoTodo = demoList.get(0); //demo只取第一条
		String demoId = demoTodo.split(",")[0];
		String demoName = demoService.getDemo(Long.valueOf(demoId)).getName();
		return demoTodo+demoName;
	}
	
	@RequestMapping(value = "/approve", method = RequestMethod.POST)
	@ResponseBody
	public String approve(String inputJson){
		String[] jsonGroup = inputJson.split(",");
		String taskId = jsonGroup[2] ;
		System.out.println(taskId);
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,String> localMap = new HashMap<String,String>();
		localMap.put("action", "Submitted");
		workFlowService.completeTask(taskId, map, localMap);
		
		return "success";
	}
	
	
}
