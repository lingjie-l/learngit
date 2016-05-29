package com.jeefw.service.sys.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;


import com.jeefw.dao.sys.TestDao;

import com.jeefw.model.sys.Test;
import com.jeefw.service.sys.TestService;

import core.service.BaseService;

/**
 * 测试的业务逻辑层的实现
 * @
 */
@Service
public class TestServiceImpl extends BaseService<Test> implements TestService{

	private TestDao testdao;
	@Resource
	public void setTestDao(TestDao testdao) {
		this.testdao = testdao;
		this.dao = testdao;
	}
	
	public List<Test> queryTestWithSubList(List<Test> resultList) {
		List<Test> testList = new ArrayList<Test>();
		for (Test entity : resultList) {
			Test test = new Test();
			test.setId(entity.getId());
			test.setXingming(entity.getXingming());
			test.setShengri(entity.getShengri());
			test.setGongzi(entity.getGongzi());
			testList.add(test);
		}
		return testList;
	}

}
