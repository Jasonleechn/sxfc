package com.xkxx.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xkxx.annotation.MyBatisRepository;
import com.xkxx.entity.TrxDetail;
import com.xkxx.entity.page.Page;

@MyBatisRepository
public interface TrxDetailDao {

	/**��ȡ����״̬δ֪�ļ�¼*/
	public List<TrxDetail> getAllUnKnownDetail();
	
	/**����������*/
	int findRows(Page page);
	
	/**��ȡ��ҳ��Ϣ*/
	List<TrxDetail> findByPage(Page page);
	
	/**��ȡһ����ϸ*/
	TrxDetail getOneDetail(@Param("devid")String devid,@Param("lsh")String lsh);
	
	/**����һ����ˮ��¼*/
	void insertOne(TrxDetail obj);
	
	/**����һ����¼*/
	void updateOne(TrxDetail obj);
	
}
