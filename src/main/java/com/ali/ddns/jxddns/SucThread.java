/**
 * 
 */
package com.ali.ddns.jxddns;

import java.util.Timer;

import org.apache.log4j.Logger;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

import com.ali.ddns.entity.DDNSEntity;


/**守护线程
 * @author underworld
 *
 */
public class SucThread implements Runnable{
	private static Logger log = Logger.getLogger(SucThread.class);
	StdSchedulerFactory std=new StdSchedulerFactory();
	private static Scheduler timer=getScheduler();
	/**
	 * @param timer
	 */
	public SucThread(Scheduler timer) {
		this.timer=timer;
	}
public static Scheduler getScheduler() {
	try {
		return new StdSchedulerFactory().getScheduler();
	} catch (SchedulerException e) {
		log.error("实例化定时器时异常");
		e.printStackTrace();
	}
	return null;
	 
}
	
	public void run() {
		
		log.info(PublicUtil.getDate()+" ======守护线程启动=======");
		UpdateDDNS updateDDNS=UpdateDDNS.getInstance();
		try {
			boolean change=true;
		while(change) {
			try {
				Thread.sleep(DDNSEntity.getSecurityTime()*1000);
			} catch (InterruptedException e) {
				
			}
			log.info("\n");
			String ip=PublicUtil.getCurrentHostIP();
			
			String ddnsIp=updateDDNS.selectDDNS();
			log.info("======执行守护线程=====");
			//第一步 守护
			log.info("获取当前状态码："+DDNSEntity.isStatus()+" 当前错误运行数："+DDNSEntity.getRunNumber());
			if(DDNSEntity.isStatus()) { //判断状态值是否为true ,true 的话 改为false ，并清空 错误次数
				DDNSEntity.setStatus(false);
				DDNSEntity.setRunNumber(0);
			}else if(DDNSEntity.getRunNumber() >0){ //判断  次数 是否 >0,是的话 做重启操作
				log.info("====当前定时器未运行时间已达上限，默认其已停止运行，重启定时器中=======");
				closeTime();
				change=false;
				PublicUtil.startApp();
				DDNSEntity.setRunNumber(0);
			}else {
				log.info("====当前状态码未改变，次数加一=======");
				DDNSEntity.setRunNumber(DDNSEntity.getRunNumber()+1);
			}
			//第二步守护
			if(  ddnsIp.equals(ip) || DDNSEntity.getLocalIp().equals(ip) || DDNSEntity.getLocalIp().equals(ddnsIp) ) {
				log.info("========一切正常==========");
				log.info("========当前三码合一值：\n"+"本地公网ip："+ip+"\n与域名绑定的ip："+ddnsIp+"\n 本地存储的域名ip："+DDNSEntity.getLocalIp()+"==========");
			}else {
				closeTime();
				log.info("====ip发生变化但定时器未修改，重启定时器中=======");
				change=false;
				PublicUtil.startApp();
			}
			log.info("\n");
				}
		} catch (Exception e) { //发生异常重新启动 程序
			closeTime();
			
				PublicUtil.startApp();
		}
		
	}
private void closeTime() { //关闭 定时器
	if(timer != null) {
		try {
			timer.shutdown();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
}
	
	
public static void main(String[] args) {
	UpdateDDNS updateDDNS=UpdateDDNS.getInstance();
	String ddnsIp=updateDDNS.selectDDNS();
	log.info(ddnsIp);
}
}
