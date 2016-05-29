package com.jeefw.dao.sys.impl;

import org.springframework.stereotype.Repository;

import com.jeefw.dao.sys.TestDao;

import com.jeefw.model.sys.Test;

import core.dao.BaseDao;

/**
 * 测试的数据持久层的实现类
 * @
 */
@Repository
public class TestDaoImpl extends BaseDao<Test> implements TestDao{

	public TestDaoImpl() {
		super(Test.class);
	}

}
