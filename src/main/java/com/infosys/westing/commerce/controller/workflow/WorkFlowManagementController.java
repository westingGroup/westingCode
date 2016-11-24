package com.infosys.westing.commerce.controller.workflow;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.infosys.westing.commerce.dto.ControllerOutput;
import com.infosys.westing.commerce.dto.workflow.ProcessDefDto;
import com.infosys.westing.commerce.dto.workflow.ProcessInstanceDto;
import com.infosys.westing.commerce.dto.workflow.TaskDto;
import com.infosys.westing.commerce.model.PagerInfo;
import com.infosys.westing.commerce.model.workflow.ProcessDefModel;
import com.infosys.westing.commerce.service.workflow.IWorkFlowService;
import com.infosys.westing.commerce.util.JsonUtil;

@Controller
@RequestMapping("/workflow")
public class WorkFlowManagementController {

	@Inject
	private IWorkFlowService workFlowService;

	private Logger logger = Logger
			.getLogger(WorkFlowManagementController.class);

	/**
	 * 流程部署页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/workflowManagement", method = RequestMethod.GET)
	public ModelAndView workflowManagement() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/workflow/workflowManagement");
		return modelAndView;
	}

	/**
	 * 部署流程
	 * 
	 * @param workflowFile
	 *            流程文件
	 * @return
	 */
	@RequestMapping(value = "/deployFlow")
	public @ResponseBody String deployFlow(MultipartFile workflowFile) {
		ControllerOutput output = new ControllerOutput();
		try {
			workFlowService.deployFlow(workflowFile);
			output.setSuccessFlag(true);
		} catch (Exception e) {
			output.setSuccessFlag(false);
			output.setContent("部署失败");
			logger.error(e.getMessage());
			e.printStackTrace();
		}

		return JsonUtil.getInstance().obj2json(output);
	}

	/**
	 * 返回流程定义版本列表页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/workflowDefList", method = RequestMethod.GET)
	public ModelAndView workflowDefList() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/workflow/workflowDefList");
		return modelAndView;
	}

	/**
	 * 查询流程最新版本页面
	 * 
	 * @param processDefDto
	 * @return
	 */
	@RequestMapping(value = "/queryProcessDefPage")
	public @ResponseBody String queryProcessDefPage(ProcessDefDto processDefDto) {
		ControllerOutput output = new ControllerOutput();
		try {
			ProcessDefModel processDefModel = new ProcessDefModel();
			PagerInfo<ProcessDefDto> processDefPage = new PagerInfo<ProcessDefDto>();
			processDefPage.setCurrentPage(processDefDto.getCurrPage());
			processDefPage.setPageSize(processDefDto.getPageSize());
			processDefModel.setPager(processDefPage);
			processDefModel.setProcessKey(processDefDto.getKey());
			processDefPage = workFlowService
					.queryProcessDefPage(processDefModel);

			output.setSuccessFlag(true);
			output.setContent(processDefPage);
		} catch (IllegalAccessException e) {
			output.setSuccessFlag(false);
			output.setContent("查询失败");
			logger.error(e.getMessage());
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			output.setSuccessFlag(false);
			output.setContent("查询失败");
			logger.error(e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			output.setSuccessFlag(false);
			output.setContent("查询失败");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return JsonUtil.getInstance().obj2json(output);
	}

	/**
	 * 转向版本页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/workflowVersionList")
	public ModelAndView workflowVersionList(String processKey) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("processKey", processKey);
		modelAndView.setViewName("/workflow/workflowVersionList");
		return modelAndView;
	}

	/**
	 * 查询流程版本页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/queryProcessVersionPage")
	public @ResponseBody String queryProcessVersionPage(
			ProcessDefDto processDefDto) {
		ControllerOutput output = new ControllerOutput();
		try {
			ProcessDefModel processDefModel = new ProcessDefModel();
			PagerInfo<ProcessDefDto> processDefPage = new PagerInfo<ProcessDefDto>();
			processDefPage.setCurrentPage(processDefDto.getCurrPage());
			processDefPage.setPageSize(processDefDto.getPageSize());
			processDefModel.setPager(processDefPage);
			processDefModel.setProcessKey(processDefDto.getKey());
			processDefModel.setProcessName(processDefDto.getName());
			processDefPage = workFlowService
					.queryProcessVersionPage(processDefModel);
			output.setSuccessFlag(true);
			output.setContent(processDefPage);
		} catch (IllegalAccessException e) {
			output.setSuccessFlag(false);
			output.setContent("查询失败");
			logger.error(e.getMessage());
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			output.setSuccessFlag(false);
			output.setContent("查询失败");
			logger.error(e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			output.setSuccessFlag(false);
			output.setContent("查询失败");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return JsonUtil.getInstance().obj2json(output);
	}

	/**
	 * 根据流程定义id暂停流程定义
	 * 
	 * @return
	 */
	@RequestMapping(value = "/pauseWorkflowDef")
	public @ResponseBody String pauseWorkflowDef(String workflowDefId) {
		ControllerOutput output = new ControllerOutput();
		try {
			workFlowService.pauseWorkflowDef(workflowDefId);
			output.setSuccessFlag(true);
		} catch (Exception e) {
			output.setSuccessFlag(false);
			output.setContent("暂停失败");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return JsonUtil.getInstance().obj2json(output);
	}

	/**
	 * 根据流程定义id恢复流程定义
	 * 
	 * @param workflowDefId流程定义id
	 * @return
	 */
	@RequestMapping(value = "/recoveryWorkflowDef")
	public @ResponseBody String recoveryWorkflowDef(String workflowDefId) {
		ControllerOutput output = new ControllerOutput();
		try {
			workFlowService.recoveryWorkflowDef(workflowDefId);
			output.setSuccessFlag(true);
		} catch (Exception e) {
			output.setSuccessFlag(false);
			output.setContent("恢复失败");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return JsonUtil.getInstance().obj2json(output);
	}

	/**
	 * 启动工作流
	 * 
	 * @param processDefinitionKey工作流定义key
	 * @return
	 */
	@RequestMapping(value = "/startWorkflow")
	public @ResponseBody String startWorkflow(String processDefinitionKey,
			String userId) {
		ControllerOutput output = new ControllerOutput();
		try {
			workFlowService.startWorkflow(processDefinitionKey, "1", userId,
					new HashMap<String, Object>());
			output.setSuccessFlag(true);
		} catch (Exception e) {
			output.setSuccessFlag(false);
			output.setContent("启动失败");
			logger.error(e.getMessage());
			e.printStackTrace();
		}

		return JsonUtil.getInstance().obj2json(output);
	}

	/**
	 * 转向流程实例页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/workflowInstList")
	public ModelAndView workflowInstList(String processDefKey) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("processDefKey", processDefKey);
		modelAndView.setViewName("/workflow/workflowInstList");
		return modelAndView;
	}

	/**
	 * 查询所有的流程实例页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/queryWorkflowInstPager")
	public @ResponseBody String queryWorkflowInstPager(String processDefKey,
			String type, long currPage, long pageSize) {
		ControllerOutput output = new ControllerOutput();
		try {
			PagerInfo<ProcessInstanceDto> pager = workFlowService
					.queryWorkflowInstPager(processDefKey, type, currPage,
							pageSize);
			output.setSuccessFlag(true);
			output.setContent(pager);
		} catch (Exception e) {
			output.setSuccessFlag(false);
			output.setContent("查询流程实例失败");
			logger.error(e.getMessage());
			e.printStackTrace();
		}

		return JsonUtil.getInstance().obj2json(output);
	}

	/**
	 * 转向我的任务页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/workflowAssign")
	public ModelAndView workflowAssign(
			String userId,
			@RequestParam(defaultValue = "1", required = false, value = "currPage") long currPage) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("userId", userId);
		modelAndView.addObject("currPage", currPage);
		modelAndView.setViewName("/workflow/workflowAssign");
		return modelAndView;
	}

	/**
	 * 根据条件查询正在执行中的任务页面
	 * 
	 * @param userId用户id
	 * @param definitionKeyPrefix定义的key前缀
	 * @param currPage当前页
	 * @param pageSize每页的记录条数
	 * @return
	 */
	@RequestMapping(value = "/findTodoTasks", produces = "text/html;charset=UTF-8")
	public @ResponseBody String findTodoTasks(String userId, long currPage,
			long pageSize) {
		ControllerOutput output = new ControllerOutput();
		try {
			PagerInfo<TaskDto> pager = workFlowService.findTodoTasks(userId,
					currPage, pageSize);
			output.setSuccessFlag(true);
			output.setContent(pager);
		} catch (Exception e) {
			output.setSuccessFlag(false);
			output.setContent("查询实例失败");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return JsonUtil.getInstance().obj2json(output);
	}

	/**
	 * 获取任务的路径
	 * 
	 * @param taskId任务id
	 * @return
	 */
	@RequestMapping(value = "/getFormUrlByTaskId")
	public ModelAndView getFormUrlByTaskId(String taskId, String userId,
			long currPage) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("taskId", taskId);
		modelAndView.addObject("userId", userId);
		modelAndView.addObject("currPage", currPage);
		modelAndView.setViewName(workFlowService.getFormUrlByTaskId(taskId));
		return modelAndView;
	}

	/**
	 * 完成任务
	 * 
	 * @param taskId任务id
	 * @return
	 */
	@RequestMapping(value = "/completeTask")
	public @ResponseBody String completeTask(String taskId, String result,
			String remark) {
		ControllerOutput output = new ControllerOutput();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("result", result);
			map.put("remark", remark);
			workFlowService.completeTask(taskId, map,
					new HashMap<String, String>());
			output.setSuccessFlag(true);
		} catch (Exception e) {
			output.setSuccessFlag(false);
			output.setContent("完成任务失败");
			logger.error(e.getMessage());
			e.printStackTrace();
		}

		return JsonUtil.getInstance().obj2json(output);
	}

	/**
	 * 接受任务
	 * 
	 * @return
	 */
	@RequestMapping(value = "/receiveTask")
	public @ResponseBody String receiveTask(String userId, String taskId) {
		ControllerOutput output = new ControllerOutput();
		try {
			workFlowService.receiveTask(userId, taskId);
			output.setSuccessFlag(true);
		} catch (Exception e) {
			output.setSuccessFlag(false);
			output.setContent("接受失败");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return JsonUtil.getInstance().obj2json(output);
	}

	/**
	 * 代理任务
	 * 
	 * @param taskId任务id
	 * @param owner委派人
	 * @param delegate被委派人
	 * @return
	 */
	@RequestMapping(value = "/delegateTask")
	public @ResponseBody String delegateTask(String taskId, String owner,
			String delegate) {
		ControllerOutput output = new ControllerOutput();
		try {
			workFlowService.delegateTaskCandidate(taskId, owner, delegate,
					new HashMap<String, Object>());
			output.setSuccessFlag(true);
		} catch (Exception e) {
			output.setSuccessFlag(false);
			output.setContent("代理任务失败");
			logger.error(e.getMessage());
			e.printStackTrace();
		}

		return JsonUtil.getInstance().obj2json(output);
	}
}
