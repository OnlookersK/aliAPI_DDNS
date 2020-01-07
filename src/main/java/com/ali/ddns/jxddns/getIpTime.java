/**
 * 
 */
package com.ali.ddns.jxddns;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;

import com.ali.ddns.entity.DDNSEntity;

/**
 * @author underworld
 *
 */
public class getIpTime  extends TimerTask{
	private static Logger log=Logger.getLogger(getIpTime.class);
	public static void main(String[] args) {
		startApp();
	}
	
	public final static void startApp() {
	/*	Timer timer=startTimer();
		Runnable r=new SucThread(timer);
		Thread tT=new Thread(r);
		tT.start();*/
		
	}
	/**
	 * 	不可修改 ,启动 判断及修改域名的  统一方法
	 * @return
	 */
	public final static Timer startTimer() {
		Timer timer = new Timer();
		timer.schedule(new getIpTime(), 1000,60000);
		return timer;
	}
	@Override
	public void run() {
		String ip=PublicUtil.getCurrentHostIP();
		UpdateDDNS updateDDNS=UpdateDDNS.getInstance();
		log.info("\n");
		log.info("============================");
		log.info("\n");
		log.info(PublicUtil.getDate()+"====当前公网ip为："+ip+"====");
		log.info("本地存储ip:"+DDNSEntity.getLocalIp());
		
		logicCode(ip,updateDDNS);//逻辑部分
		
		log.info("\n");
		log.info("============================");
		log.info("\n");
	}
	/**
	 *  判断是否三码合一 
	 * @param ip 本地公网ip
	 * @param updateDDNS  动态域名解析修改实例
	 */
	public void  logicCode(String ip,UpdateDDNS updateDDNS) {
		if(! DDNSEntity.getLocalIp().equals("")) {//如果不等于空字符串,证明正在运行
			if (! ip.equals(DDNSEntity.getLocalIp())) {// 本地公网ip不等于 本地存储的跟域名绑定的ip的话,调用修改接口
				log.info("====当前ip不一致，修改域名中====");
				updateDDNS.updateDDNS();
				DDNSEntity.setLocalIp(ip);;
			}else {
				log.info("====当前ip一致=====");
			}
				 
		}else { //等于 空字符串，证明第一次运行 
			log.info("======当前为第一次运行======");	
			String doMainIp=updateDDNS.selectDDNS();
			if(! doMainIp.equals(ip)) { //先获取域名绑定的ip，看是否跟公网一致
				log.info("======当前公网ip与域名绑定的ip不一致，将启动修改域名 服务======");	
				updateDDNS.updateDDNS();
				DDNSEntity.setLocalIp(ip);
			}else { //如果相同的话，三码调整为一致
				log.info("======当前公网ip与域名绑定的ip一致，三码调整为一致======");
				DDNSEntity.setLocalIp(ip);	
				}
			}
	}

 
}
