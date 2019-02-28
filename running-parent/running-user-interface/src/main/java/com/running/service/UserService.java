package com.running.service;

import java.util.List;
import com.running.pojo.TbUser;

import group.PageResult;
import group.result;
import group.rigisterUser;

/**
 * 服务层接口
 * 
 * @author Administrator
 *
 */
public interface UserService {

	/**
	 * 返回全部列表
	 * 
	 * @return
	 */
	public List<TbUser> findAll();

	/**
	 * 返回分页列表
	 * 
	 * @return
	 */
	public PageResult findPage(int pageNum, int pageSize);

	/**
	 * 增加
	 */
	public result add(TbUser user, String checkNumber);

	/**
	 * 修改
	 */
	public void update(TbUser user);

	/**
	 * 根据ID获取实体
	 * 
	 * @param id
	 * @return
	 */
	public TbUser findOne(Integer id);

	/**
	 * 批量删除
	 * 
	 * @param ids
	 */
	public void delete(Integer[] ids);

	/**
	 * 分页
	 * 
	 * @param pageNum
	 *            当前页 码
	 * @param pageSize
	 *            每页记录数
	 * @return
	 */
	public PageResult findPage(TbUser user, int pageNum, int pageSize);

	/**
	 * 发送短信
	 */

	public void createSmsCode(final String phone);

	/**
	 * 查看是否有这个用户
	 */

	public boolean checkUser(String username);

	/**
	 * 用户名是否匹配
	 */

	public boolean checkUserAndPassword(TbUser user);

	/**
	 * 通过用户名称查询对象
	 */
	public TbUser foundUserByUserName(String username);
	
	/**
	 * 通过用户名称模糊查询对象
	 */
	public List<TbUser> foundUserByUserNameMoke(String username);

}
