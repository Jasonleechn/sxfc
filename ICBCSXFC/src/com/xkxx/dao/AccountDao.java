package com.xkxx.dao;

import org.apache.ibatis.annotations.Param;

import com.xkxx.annotation.MyBatisRepository;
import com.xkxx.entity.Account;

@MyBatisRepository
public interface AccountDao {

	/**根据投注站编号查询*/
	Account findByUniqueKey(@Param("devid")String devid,@Param("type")String type);
	
	/**插入一条记录*/
	void insertOne(Account account);
	
	/**更新密码*/
	void updatePwd(Account account);
	
	/**更新个人信息*/
	void updatePerInfo(Account account);
	
	/**更新一条记录*/
	void updateOne(Account account);
	
	/**根据唯一键删除记录*/
	void deleteByUniqueKey(Account account);
	
}
