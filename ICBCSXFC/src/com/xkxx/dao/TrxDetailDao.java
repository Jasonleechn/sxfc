package com.xkxx.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xkxx.annotation.MyBatisRepository;
import com.xkxx.entity.TrxDetail;
import com.xkxx.entity.page.Page;

@MyBatisRepository
public interface TrxDetailDao {

	/**获取所有状态未知的记录*/
	public List<TrxDetail> getAllUnKnownDetail();
	
	/**计算总条数*/
	int findRows(Page page);
	
	/**获取翻页信息*/
	List<TrxDetail> findByPage(Page page);
	
	/**获取一条明细*/
	TrxDetail getOneDetail(@Param("devid")String devid,@Param("lsh")String lsh);
	
	/**插入一条流水记录*/
	void insertOne(TrxDetail obj);
	
	/**更新一条记录*/
	void updateOne(TrxDetail obj);
	
}
