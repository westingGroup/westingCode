package com.infosys.westing.commerce.dto.workflow;


/**
 * 流程定义dto
 * @author Lily_Xue
 *
 */
public class ProcessDefDto {

	private String id;//定义的id
	private String category;//定义的分类
	private String name;//定义名称
	private String key;//key信息
	private String description;//描述信息
	private int version;//版本号
	private String resourceName;//bpmn资源名称
	private String deploymentId;//部署的id编号
	private String deploymentTime;//部署的时间
	private String diagramResourceName;//图片资源名称
	private boolean hasStartKey;
	private boolean hasGraphicalNotation;
	private boolean suspended;
	private String tenantId;
	
	private long currPage;//当前页
	private long pageSize;//每页的记录条数
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public String getResourceName() {
		return resourceName;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	public String getDeploymentId() {
		return deploymentId;
	}
	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}
	public String getDiagramResourceName() {
		return diagramResourceName;
	}
	public void setDiagramResourceName(String diagramResourceName) {
		this.diagramResourceName = diagramResourceName;
	}
	public boolean isHasStartKey() {
		return hasStartKey;
	}
	public void setHasStartKey(boolean hasStartKey) {
		this.hasStartKey = hasStartKey;
	}
	public boolean isHasGraphicalNotation() {
		return hasGraphicalNotation;
	}
	public void setHasGraphicalNotation(boolean hasGraphicalNotation) {
		this.hasGraphicalNotation = hasGraphicalNotation;
	}
	public boolean isSuspended() {
		return suspended;
	}
	public void setSuspended(boolean suspended) {
		this.suspended = suspended;
	}
	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	public String getDeploymentTime() {
		return deploymentTime;
	}
	public void setDeploymentTime(String deploymentTime) {
		this.deploymentTime = deploymentTime;
	}
	public long getCurrPage() {
		return currPage;
	}
	public void setCurrPage(long currPage) {
		this.currPage = currPage;
	}
	public long getPageSize() {
		return pageSize;
	}
	public void setPageSize(long pageSize) {
		this.pageSize = pageSize;
	}
}
