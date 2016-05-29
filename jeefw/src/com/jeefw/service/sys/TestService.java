package com.jeefw.service.sys;

import java.util.List;
import com.jeefw.model.sys.Test;
import core.service.Service;
/**
 * 测试的业务逻辑层的接口
 * @
 */
public interface TestService extends Service<Test>{
	
    List<Test> queryTestWithSubList(List<Test> resultList);
}
