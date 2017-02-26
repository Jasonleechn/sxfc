package com.xkxx.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xkxx.annotation.MyBatisRepository;
import com.xkxx.entity.BindInfo;

@MyBatisRepository
public interface BindInfoDao {
	
	/**插入一条记录*/
	public void insertOne(BindInfo bindinfo);
	
	/**查询记录*/
	public List<BindInfo> findAllRecordByDevid(String devid);
	
	/**根据唯一键查询记录*/
	public BindInfo findByUniqueKey(@Param("devid")String devid, @Param("cardno")String cardno);
	
	/**根据唯一键删除记录*/
	public void deleteByUniqueKey(@Param("devid")String devid, @Param("cardno")String cardno);
	
	/**根据唯一键修改记录*/
	public void updateByUniqueKey(@Param("devid")String devid, @Param("cardno")String cardno);
	
}
