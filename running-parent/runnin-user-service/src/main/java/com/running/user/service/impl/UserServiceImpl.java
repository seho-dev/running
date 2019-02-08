package com.running.user.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import javax.xml.ws.ServiceMode;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.running.mapper.TbUserMapper;
import com.running.pojo.TbUser;
import com.running.pojo.TbUserExample;
import com.running.pojo.TbUserExample.Criteria;
import com.running.service.UserService;

import group.PageResult;
import group.result;

/**
 * 鏈嶅姟瀹炵幇灞�
 * 
 * @author Administrator
 *
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private TbUserMapper userMapper;

	// 注入jms
	@Autowired
	private JmsTemplate jmsTemplate;

	// 注入缓存
	@Autowired
	private RedisTemplate redisTemplate;

	/**
	 * 鏌ヨ鍏ㄩ儴
	 */
	@Override
	public List<TbUser> findAll() {
		return userMapper.selectByExample(null);
	}

	/**
	 * 鎸夊垎椤垫煡璇�
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		Page<TbUser> page = (Page<TbUser>) userMapper.selectByExample(null);
		return new PageResult(page.getTotal(), page.getResult());
	}

	/**
	 * 澧炲姞
	 */
	@Override
	public result add(TbUser user, String checkNumber) {
		// 需要验证手机号与redis中的内容是否匹配
		String userName = user.getUsername(); // 同理手机号
		if (!redisTemplate.boundHashOps("smscode").get(userName).equals(checkNumber)) {
			// 是否匹配
			return new result(1, "验证码匹配失败");
		} else if (redisTemplate.boundHashOps(userName) == null) {
			// 是否为空
			return new result(1, "没有发送过验证码");
		} else {
			// 判断用户是否存在数据库
			TbUserExample exmple = new TbUserExample();
			Criteria createCriteria = exmple.createCriteria();
			createCriteria.andUsernameEqualTo(user.getUsername());
			List<TbUser> selectByExample = userMapper.selectByExample(exmple);
			if (selectByExample.size() == 0) {
				// 数据库没有这个记录
				// 对密码进行md5加密
				user.setPassword(DigestUtils.md5Hex(user.getPassword()));
				userMapper.insert(user);
				return new result(0, "注册成功");
			} else {
				return new result(2, "已注册过");
			}
		}

	}

	/**
	 * 淇敼
	 */
	@Override
	public void update(TbUser user) {
		userMapper.updateByPrimaryKey(user);
	}

	/**
	 * 鏍规嵁ID鑾峰彇瀹炰綋
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public TbUser findOne(Integer id) {
		return userMapper.selectByPrimaryKey(id);
	}

	/**
	 * 鎵归噺鍒犻櫎
	 */
	@Override
	public void delete(Integer[] ids) {
		for (Integer id : ids) {
			userMapper.deleteByPrimaryKey(id);
		}
	}

	@Override
	public PageResult findPage(TbUser user, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);

		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();

		if (user != null) {
			if (user.getUsername() != null && user.getUsername().length() > 0) {
				criteria.andUsernameLike("%" + user.getUsername() + "%");
			}
			if (user.getPassword() != null && user.getPassword().length() > 0) {
				criteria.andPasswordLike("%" + user.getPassword() + "%");
			}
			if (user.getSex() != null && user.getSex().length() > 0) {
				criteria.andSexLike("%" + user.getSex() + "%");
			}
			if (user.getHead() != null && user.getHead().length() > 0) {
				criteria.andHeadLike("%" + user.getHead() + "%");
			}
			if (user.getAddress() != null && user.getAddress().length() > 0) {
				criteria.andAddressLike("%" + user.getAddress() + "%");
			}
			if (user.getWechat() != null && user.getWechat().length() > 0) {
				criteria.andWechatLike("%" + user.getWechat() + "%");
			}
			if (user.getSlogan() != null && user.getSlogan().length() > 0) {
				criteria.andSloganLike("%" + user.getSlogan() + "%");
			}

		}

		Page<TbUser> page = (Page<TbUser>) userMapper.selectByExample(example);
		return new PageResult(page.getTotal(), page.getResult());
	}

	/**
	 * 签名信息
	 */
	@Value("${template_code}")
	private String template_code;

	@Value("${sign_name}")
	private String sign_name;

	/**
	 * 发送短信
	 */
	public void createSmsCode(final String phone) {
		// TODO Auto-generated method stub
		// 1、生成随机验证码
		final String code = (long) (Math.random() * 1000000) + "";
		System.out.println("验证码：" + code);
		// 2、存入redis
		redisTemplate.boundHashOps("smscode").put(phone, code);
		// 3、发送到active使用监听调用阿里大于发送服务
		Map map = new HashMap<>();
		map.put("mobile", phone);
		map.put("template_code", template_code);
		map.put("sign_name", sign_name);
		// 拼接信息需要的参数（验证码）
		Map m = new HashMap<>();
		m.put("code", code);
		map.put("param", JSON.toJSONString(m));
		jmsTemplate.convertAndSend("running_phone_number", map);
	}

	@Override
	public boolean checkUser(String username) {
		TbUserExample exmple = new TbUserExample();
		Criteria createCriteria = exmple.createCriteria();
		createCriteria.andUsernameEqualTo(username);
		List<TbUser> selectByExample = userMapper.selectByExample(exmple);
		if (selectByExample.size() == 0) {
			return false;
		}
		return true;
	}

	@Override
	public boolean checkUserAndPassword(TbUser user) {
		TbUserExample exmple = new TbUserExample();
		Criteria createCriteria = exmple.createCriteria();
		createCriteria.andUsernameEqualTo(user.getUsername());
		TbUser userObject = (TbUser) userMapper.selectByExample(exmple).get(0);
		if (userObject.getPassword().equals(DigestUtils.md5Hex(user.getPassword()))) {
			// 如果匹配
			return true;
		}
		return false;
	}

	@Override
	public TbUser foundUserByUserName(String username) {
		TbUserExample exmple = new TbUserExample();
		Criteria createCriteria = exmple.createCriteria();
		createCriteria.andUsernameEqualTo(username);
		TbUser tbUser = userMapper.selectByExample(exmple).get(0);
		return tbUser;
	}
}
