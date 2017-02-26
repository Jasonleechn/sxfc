package com.xkxx.dao;

import org.apache.ibatis.annotations.Param;

import com.xkxx.annotation.MyBatisRepository;
import com.xkxx.entity.Account;

@MyBatisRepository
public interface AccountDao {

	/**����Ͷעվ��Ų�ѯ*/
	Account findByUniqueKey(@Param("devid")String devid,@Param("type")String type);
	
	/**����һ����¼*/
	void insertOne(Account account);
	
	/**��������*/
	void updatePwd(Account account);
	
	/**���¸�����Ϣ*/
	void updatePerInfo(Account account);
	
	/**����һ����¼*/
	void updateOne(Account account);
	
	/**����Ψһ��ɾ����¼*/
	void deleteByUniqueKey(Account account);
	
}
