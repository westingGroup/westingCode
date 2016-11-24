

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.activiti.engine.IdentityService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.infosys.westing.commerce.model.PagerInfo;
import com.infosys.westing.commerce.service.workflow.IWorkFlowService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class RoleTest {

	@Inject
	private IdentityService identityService;
	
	@Inject
	private IWorkFlowService workflowService;
	
	@Test
	public void testAddUser(){
		User user = identityService.newUser("1");
		user.setFirstName("lily");
		user.setLastName("xue");
		user.setEmail("394184669@qq.com");
		user.setPassword("123456");
		identityService.saveUser(user);
	}
	
	@Test
	public void testAddGroup(){
		Group group = identityService.newGroup("saleManager");
		group.setName("销售部部门经理");
		group.setType("manager");
		identityService.saveGroup(group);
	}
	
	@Test
	public void testAddUserMemberShip(){
		List<String> roleNameList = new ArrayList<String>();
		roleNameList.add("financeManager");
		roleNameList.add("saleManager");
		for (String roleName : roleNameList) {
			identityService.createMembership("1", roleName);
		}
	}
	
	@Test
	public void startProcessDefinition(){
		Map<String, Object> variables = new HashMap<String, Object>();
		workflowService.startWorkflow("WestingPoc", "1", "1", variables);
	}
	
	@Test
	public void findTodo(){
		PagerInfo<Task> pager = workflowService.findTodoTasks("1", "Westing", 1, 10);
		System.out.println(pager.getTotalRecords());
	}
	
	/**
	 * 查询所有的流程实例
	 */
	@Test
	public void findRunningProcessInstances(){
		PagerInfo<HistoricProcessInstance> processInstancePager = workflowService.queryWorkflowInstPager(1, 10);
		System.out.println(processInstancePager.getTotalRecords());
	}
}
