package com.xkxx.dao;

import java.util.List;

import com.xkxx.annotation.MyBatisRepository;
import com.xkxx.entity.FcAcc;

@MyBatisRepository
public interface FcAccDao {

	/**��ȡ���в�Ʊ����*/
	public List<FcAcc> getAllFccAcc();
	
}
