package com.xkxx.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xkxx.annotation.MyBatisRepository;
import com.xkxx.entity.ZoneDic;

@MyBatisRepository
public interface ZoneDicDao {

	/**��ȡ���е���*/
	public List<ZoneDic> findAllZone();
	
	/**���ݵ����Ų���*/
	ZoneDic findByZoneno(@Param("zoneno")String zoneno);
	
}
