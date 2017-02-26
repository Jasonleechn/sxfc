package com.xkxx.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xkxx.annotation.MyBatisRepository;
import com.xkxx.entity.BindInfo;

@MyBatisRepository
public interface BindInfoDao {
	
	/**����һ����¼*/
	public void insertOne(BindInfo bindinfo);
	
	/**��ѯ��¼*/
	public List<BindInfo> findAllRecordByDevid(String devid);
	
	/**����Ψһ����ѯ��¼*/
	public BindInfo findByUniqueKey(@Param("devid")String devid, @Param("cardno")String cardno);
	
	/**����Ψһ��ɾ����¼*/
	public void deleteByUniqueKey(@Param("devid")String devid, @Param("cardno")String cardno);
	
	/**����Ψһ���޸ļ�¼*/
	public void updateByUniqueKey(@Param("devid")String devid, @Param("cardno")String cardno);
	
}
