package com.running.user.service.impl;
import java.util.List;

import javax.xml.ws.ServiceMode;

import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.running.mapper.TbUserMapper;
import com.running.pojo.TbUser;
import com.running.pojo.TbUserExample;
import com.running.pojo.TbUserExample.Criteria;
import com.running.service.UserService;

import group.PageResult;

/**
 * 鏈嶅姟瀹炵幇灞�
 * @author Administrator
 *
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private TbUserMapper userMapper;
	
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
		Page<TbUser> page=   (Page<TbUser>) userMapper.selectByExample(null);
		return new PageResult(page.getTotal(), page.getResult());
	}

	/**
	 * 澧炲姞
	 */
	@Override
	public void add(TbUser user) {
		userMapper.insert(user);		
	}

	
	/**
	 * 淇敼
	 */
	@Override
	public void update(TbUser user){
		userMapper.updateByPrimaryKey(user);
	}	
	
	/**
	 * 鏍规嵁ID鑾峰彇瀹炰綋
	 * @param id
	 * @return
	 */
	@Override
	public TbUser findOne(Integer id){
		return userMapper.selectByPrimaryKey(id);
	}

	/**
	 * 鎵归噺鍒犻櫎
	 */
	@Override
	public void delete(Integer[] ids) {
		for(Integer id:ids){
			userMapper.deleteByPrimaryKey(id);
		}		
	}
	
	
		@Override
	public PageResult findPage(TbUser user, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		TbUserExample example=new TbUserExample();
		Criteria criteria = example.createCriteria();
		
		if(user!=null){			
						if(user.getUsername()!=null && user.getUsername().length()>0){
				criteria.andUsernameLike("%"+user.getUsername()+"%");
			}
			if(user.getPassword()!=null && user.getPassword().length()>0){
				criteria.andPasswordLike("%"+user.getPassword()+"%");
			}
			if(user.getSex()!=null && user.getSex().length()>0){
				criteria.andSexLike("%"+user.getSex()+"%");
			}
			if(user.getHead()!=null && user.getHead().length()>0){
				criteria.andHeadLike("%"+user.getHead()+"%");
			}
			if(user.getAddress()!=null && user.getAddress().length()>0){
				criteria.andAddressLike("%"+user.getAddress()+"%");
			}
			if(user.getWechat()!=null && user.getWechat().length()>0){
				criteria.andWechatLike("%"+user.getWechat()+"%");
			}
			if(user.getSlogan()!=null && user.getSlogan().length()>0){
				criteria.andSloganLike("%"+user.getSlogan()+"%");
			}
	
		}
		
		Page<TbUser> page= (Page<TbUser>)userMapper.selectByExample(example);		
		return new PageResult(page.getTotal(), page.getResult());
	}
	
}
