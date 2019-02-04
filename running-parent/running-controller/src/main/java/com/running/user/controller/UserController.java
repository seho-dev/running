package com.running.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.running.service.UserService;

import group.result;

/**
 * 关于用户的对外控制 注册/登陆等
 * 
 * @author LAOSHEN
 *
 */
@RestController
@RequestMapping("user")
public class UserController {

	// 引入服务
	@Autowired
	private UserService userservice;

	@RequestMapping("/test")
	public result test() {
		try {
			return new result(1, "成功");
		} catch (Exception e) {
			// TODO: handle exception
			return new result(0, "失败");
		}

	}
}
