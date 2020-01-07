/**
 * 
 */
package com.ali.ddns.init;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.ali.ddns.entity.DDNSEntity;
import com.ali.ddns.entity.DefaultEntiy;
import com.ali.ddns.jxddns.PublicUtil;
import com.ali.ddns.jxddns.UpdateDDNS;



/**
 * @author underworld
 *
 */
public class CacheEntity {
		private static Logger log=Logger.getLogger(CacheEntity.class);
		private static Properties properties;
	public  CacheEntity() {
		
	}
	public  Properties getProper() {
		InputStream in =this.getClass().getClassLoader().getResourceAsStream("param.properties");
		properties= new Properties();
		try {
			properties.load(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return  properties;
	}
	
	public  void getCacheEntity() {
		try {
			log.info("初始化参数中");
		Properties proper=new CacheEntity().getProper();
		DDNSEntity.setAccessKey(proper.get("AccessKey").toString());
		DDNSEntity.setSecret(proper.get("Secret").toString());
		DDNSEntity.setDoMain(proper.get("DoMain").toString());
		DDNSEntity.setHostRec(checkVal(proper.get("hostRec"),"recType"));
		DDNSEntity.setRecType(checkVal(proper.get("recType"),"recType"));
		DDNSEntity.setAreaId(checkVal(proper.get("areaId"),"areaId"));
		DDNSEntity.setIntervalTime(Integer.parseInt(proper.get("IntervalTime").toString()));
		DDNSEntity.setSecurityTime(Integer.parseInt(proper.get("securityTime").toString()));		
		DDNSEntity.setLocalIp(UpdateDDNS.getInstance().selectDDNS());
		log.info("参数初始化成功");
		} catch (Exception e) {
			log.error("初始化参数时遇到错误");
			e.printStackTrace();
		}
	}
	
	private  String checkVal(Object object ,String method) {
		if(object.equals("") || object == null) {
			switch (method) {
			case "areaId": return DefaultEntiy.AREAID;
			case "hostRec":return DefaultEntiy.HOSTREC;	
			case "recType":return DefaultEntiy.RECTYPE;	
			}
		}
		
		return object.toString();
	}
	
	public static void main(String[] args) {
		//getCacheEntity();
	}
}
