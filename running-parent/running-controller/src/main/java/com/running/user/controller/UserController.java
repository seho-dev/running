package com.running.user.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.running.pojo.TbUser;
import com.running.service.UserService;

import group.result;
import group.rigisterUser;

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
	@Reference
	private UserService userservice;

	@Autowired
	private HttpServletResponse response;

	/**
	 * 注册用户
	 */
	@RequestMapping("/insertUser")
	public result registerUser(@RequestBody String user) {

		// response.setHeader("Access-Control-Allow-Origin", "*"); // 解决跨域

		/**
		 * uniapp的post提交自动转换为字符串，需要转换成对象
		 */

		JSONObject tb_user = JSON.parseObject(user);
		// 取出名为user的对象
		rigisterUser rigister = JSON.parseObject(tb_user.getString("user"), rigisterUser.class);
		TbUser userObject = new TbUser();

		userObject.setUsername(rigister.getUsername());
		userObject.setPassword(rigister.getPassword());
		// 判断是否注册成功( 传递主体对象和验证码 )
		return userservice.add(userObject, rigister.getCheckNumber());
	}

	// 发送验证码
	@RequestMapping("/sendMessage")
	public result sendMessage(final String phone) {
		// response.setHeader("Access-Control-Allow-Origin", "*"); // 解决跨域
		try {
			// 发送手机号到消息队列
			userservice.createSmsCode(phone);
			return new result(0, "发送成功");
		} catch (Exception e) {
			return new result(1, "发送失败");
			// TODO: handle exception
		}
	}

	@RequestMapping("/login")
	public result Login(@RequestBody String user) {
		response.setHeader("Access-Control-Allow-Origin", "*"); // 解决跨域
		JSONObject parseObject = JSON.parseObject(user);
		TbUser tb_user = JSON.parseObject(parseObject.getString("user"), TbUser.class);
		// 查询有无这个账号
		boolean checkUser = userservice.checkUser(tb_user.getUsername());
		if (checkUser) {
			// 如果存在这个用户
			if (userservice.checkUserAndPassword(tb_user)) {

				return new result(0, "登陆成功");
			} else {
				return new result(1, "用户名或密码不匹配");
			}
		} else {
			// 不存在这个用户
			return new result(2, "此用户不存在");
		}
	}

	// 通过用户名模糊搜索
	@RequestMapping("/FoundMoke")
	public String FoundUser(String username) {
		List<TbUser> foundUserList = userservice.foundUserByUserNameMoke(username);
		// 密码项取消
		for (int i = 0; i < foundUserList.size(); i++) {
			foundUserList.get(i).setPassword(null);
		}
		if (foundUserList.size() == 0) {
			return null;
		}
		// list转换字符串
		return JSON.toJSONString(foundUserList);
	}

	@RequestMapping("/searchUser")
	public TbUser foundByUserName(String username) {
		// response.setHeader("Access-Control-Allow-Origin", "*"); // 解决跨域
		return userservice.foundUserByUserName(username);
	}
}
