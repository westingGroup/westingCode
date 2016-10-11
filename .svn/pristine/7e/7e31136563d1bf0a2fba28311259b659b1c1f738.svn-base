package com.infosys.westing.commerce.service.demo;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infosys.westing.commerce.dao.demo.IDemoDao;
import com.infosys.westing.commerce.entity.demo.DemoEntity;


@Service("demoService")
@Transactional
public class DemoService implements IDemoService {
	@Inject IDemoDao demoDao;
	
	@Override
	public void insertDemo(DemoEntity demo) {
		demoDao.add(demo);	
	}

	@Override
	public void updateDemo(DemoEntity demo) {
		demoDao.update(demo);
	}

	@Override
	public DemoEntity getDemo(long demoId) {
		return demoDao.load(demoId);
	}

}
