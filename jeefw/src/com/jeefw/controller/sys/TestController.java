package com.jeefw.controller.sys;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.jeefw.core.Constant;
import com.jeefw.core.JavaEEFrameworkBaseController;

import com.jeefw.model.sys.Test;
import com.jeefw.service.sys.TestService;

import core.support.ExtJSBaseParameter;
import core.support.JqGridPageView;
import core.support.QueryResult;

/**
 * 测试的控制层
 * @
 */
@Controller
@RequestMapping("/sys/test")
public class TestController extends JavaEEFrameworkBaseController<Test> implements Constant {

	@Resource
	private TestService testService;
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	// 查询字典的表格，包括分页、搜索和排序
	@RequestMapping(value = "/getTest", method = { RequestMethod.POST, RequestMethod.GET })
	public void getTest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("111222");
		Integer firstResult = Integer.valueOf(request.getParameter("page"));
		System.out.println(firstResult);
		Integer maxResults = Integer.valueOf(request.getParameter("rows"));
		String sortedObject = request.getParameter("sidx");
		String sortedValue = request.getParameter("sord");
		String filters = request.getParameter("filters");
		Test test = new Test();
		if (StringUtils.isNotBlank(filters)) {
			JSONObject jsonObject = JSONObject.fromObject(filters);
			JSONArray jsonArray = (JSONArray) jsonObject.get("rules");
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject result = (JSONObject) jsonArray.get(i);
				if (result.getString("field").equals("xingming") && result.getString("op").equals("eq")) {
					test.setXingming(result.getString("data"));
				}
				if (result.getString("field").equals("gongzi") && result.getString("op").equals("cn")) {
					test.setGongzi(result.getLong("data"));
				}
			}
			if (((String) jsonObject.get("groupOp")).equalsIgnoreCase("OR")) {
				test.setFlag("OR");
			} else {
				test.setFlag("AND");
			}
		}
		test.setFirstResult((firstResult - 1) * maxResults);
		test.setMaxResults(maxResults);
		Map<String, String> sortedCondition = new HashMap<String, String>();
		sortedCondition.put(sortedObject, sortedValue);
		test.setSortedConditions(sortedCondition);
		QueryResult<Test> queryResult = testService.doPaginationQuery(test);
		JqGridPageView<Test> testListView = new JqGridPageView<Test>();
		testListView.setMaxResults(maxResults);
		List<Test> testWithSubList = testService.queryTestWithSubList(queryResult.getResultList());
		testListView.setRows(testWithSubList);
		testListView.setRecords(queryResult.getTotalCount());
		writeJSON(response, testListView);
	}

	// 保存字典的实体Bean
	@RequestMapping(value = "/saveTest", method = { RequestMethod.POST, RequestMethod.GET })
	public void doSave(Test entity, HttpServletRequest request, HttpServletResponse response) throws IOException {
		ExtJSBaseParameter parameter = ((ExtJSBaseParameter) entity);
		if (CMD_EDIT.equals(parameter.getCmd())) {
			testService.merge(entity);
		} else if (CMD_NEW.equals(parameter.getCmd())) {
			testService.persist(entity);
		}
		parameter.setSuccess(true);
		writeJSON(response, parameter);
	}

	// 操作字典的删除、导出Excel、字段判断和保存
	@RequestMapping(value = "/operateTest", method = { RequestMethod.POST, RequestMethod.GET })
	public void operateTest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String oper = request.getParameter("oper");
		String id = request.getParameter("id");
		if (oper.equals("del")) {
			String[] ids = id.split(",");
			deleteTest(request, response, (Long[]) ConvertUtils.convert(ids, Long.class));
		} else if (oper.equals("excel")) {
			response.setContentType("application/msexcel;charset=UTF-8");
			try {
				response.addHeader("Content-Disposition", "attachment;filename=file.xls");
				OutputStream out = response.getOutputStream();
				out.write(URLDecoder.decode(request.getParameter("csvBuffer"), "UTF-8").getBytes());
				out.flush();
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			Map<String, Object> result = new HashMap<String, Object>();
			String xingming = request.getParameter("xingming");
			Date shengri = dateFormat.parse(request.getParameter("shengri"));
			float gongzi =Float.parseFloat(request.getParameter("gongzi")) ;
			
			Test test = null;
			if (oper.equals("edit")) {
				test = testService.get(Long.valueOf(id));
			}
			Test xingmingTest = testService.getByProerties("xingming", xingming);
			Test shengrite = testService.getByProerties("shengri", shengri);
			Test gongziTest = testService.getByProerties("gongzi", gongzi);
			if (StringUtils.isBlank(xingming)) {
				response.setStatus(HttpServletResponse.SC_LENGTH_REQUIRED);
				result.put("message", "请填写姓名");
				writeJSON(response, result);
			}else if (null != xingmingTest && oper.equals("add")) {
				response.setStatus(HttpServletResponse.SC_CONFLICT);
				result.put("message", "此姓名已存在，请重新输入");
				writeJSON(response, result);
			} else {
				Test entity = new Test();
				entity.setXingming(xingming);
				entity.setShengri(shengri);
				entity.setGongzi(gongzi);
				
				if (oper.equals("edit")) {
					entity.setId(Integer.parseInt(id));
					entity.setCmd("edit");
					doSave(entity, request, response);
				} else if (oper.equals("add")) {
					entity.setCmd("new");
					doSave(entity, request, response);
				}
			}
		}
	}

	// 删除字典
	@RequestMapping("/deleteTest")
	public void deleteTest(HttpServletRequest request, HttpServletResponse response, @RequestParam("ids") Long[] ids) throws IOException {
		boolean flag = testService.deleteByPK(ids);
		if (flag) {
			writeJSON(response, "{success:true}");
		} else {
			writeJSON(response, "{success:false}");
		}
	}

}
