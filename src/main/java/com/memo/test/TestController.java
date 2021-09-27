package com.memo.test;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.memo.test.bo.TestBO;

@Controller
public class TestController {
	
	// bo 연결
	@Autowired
	private TestBO testBO;
	
	@RequestMapping("/test1")
	@ResponseBody
	public String test1() {
		return "hello world!!!";
	}
	
	// DB 연동 테스트
	@RequestMapping("/test2")
	@ResponseBody
	public List<Map<String, Object>> test2(){
		return testBO.getUserList();
	}
	
	// jsp 연동 테스트
	@RequestMapping("/test3")
	public String test3() {
		return "test/test";
	}
	
	@RequestMapping("/test4")
	public String test4() {
		return "template/layout";
	}
	
}
