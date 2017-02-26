package com.xkxx.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xkxx.annotation.MyBatisRepository;
import com.xkxx.entity.ZoneDic;

@MyBatisRepository
public interface ZoneDicDao {

	/**获取所有地区*/
	public List<ZoneDic> findAllZone();
	
	/**根据地区号查找*/
	ZoneDic findByZoneno(@Param("zoneno")String zoneno);
	
}
