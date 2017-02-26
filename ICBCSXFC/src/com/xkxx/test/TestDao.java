package com.xkxx.test;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.xkxx.dao.ZoneDicDao;
import com.xkxx.entity.ZoneDic;


public class TestDao {
	
	private String conf = "applicationContext.xml";
	
	@Test
	public void testZoneDao(){
		ApplicationContext ctx = 
			new ClassPathXmlApplicationContext(conf);
		ZoneDicDao dao = ctx.getBean(ZoneDicDao.class);
		List<ZoneDic> list = dao.findAllZone();
		for(ZoneDic obj : list){
			System.out.println(obj.getZoneno()+":"+obj.getZonename());
		}
	}

}
