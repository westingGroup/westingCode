package com.infosys.westing.commerce.entity.demo;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the C_TOPIC database table.
 * 
 */
@Entity
@Table(name="T_DEMO")
@NamedQuery(name="DemoEntity.findAll", query="SELECT c FROM DemoEntity c")
public class DemoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="DEMO_ID")
	private long demoId;


	@Column(name="NAME")
	private String name;
	
	@Column(name="PROCESS_INSTANCE_ID")
	private String ProcessInstanceId;

	
	public DemoEntity() {
	}


	public long getDemoId() {
		return demoId;
	}


	public void setDemoId(long demoId) {
		this.demoId = demoId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getProcessInstanceId() {
		return ProcessInstanceId;
	}


	public void setProcessInstanceId(String processInstanceId) {
		ProcessInstanceId = processInstanceId;
	}


}