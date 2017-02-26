package com.xkxx.dao;

import java.util.List;

import com.xkxx.annotation.MyBatisRepository;
import com.xkxx.entity.FcAcc;

@MyBatisRepository
public interface FcAccDao {

	/**获取所有彩票类型*/
	public List<FcAcc> getAllFccAcc();
	
}
