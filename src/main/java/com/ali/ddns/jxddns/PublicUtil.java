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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import com.ali.ddns.entity.DDNSEntity;
import com.ali.ddns.init.CacheEntity;

/**
 * @author underworld
 *
 */
public class PublicUtil {
	 /**
     * 获取当前主机公网IP
     */
    public static String getCurrentHostIP(){
        // 这里使用jsonip.com第三方接口获取本地IP
        String jsonip = "https://jsonip.com/";
        // 接口返回结果
        String result = "";
        BufferedReader in = null;
        try {
            // 使用HttpURLConnection网络请求第三方接口
            URL url = new URL(jsonip);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            in = new BufferedReader(new InputStreamReader(
                    urlConnection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }

        }
        // 正则表达式，提取xxx.xxx.xxx.xxx，将IP地址从接口返回结果中提取出来
        String rexp = "(\\d{1,3}\\.){3}\\d{1,3}";
        Pattern pat = Pattern.compile(rexp);
        Matcher mat = pat.matcher(result);
        String res="";
        while (mat.find()) {
            res=mat.group();
            break;
        }
        return res;
    }
    
   
    
  public  static String getDate() {
    	SimpleDateFormat spdate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	String i=spdate.format(new Date());
		return i;
    	
    }
  /**
   * 程序启动所调用的方法
   */
  public final static void startApp() {
	  	new CacheEntity().getCacheEntity(); //初始化参数配置
		Scheduler timer=startTimer(); //启动 定时器
		Runnable r=new SucThread(timer); //启动守护线程
		Thread tT=new Thread(r); 
		tT.start();
		
	}
	/**
	 * 	不可修改 ,启动 判断及修改域名的  统一方法
	 * @return
	 * @throws SchedulerException 
	 */
	public final static Scheduler startTimer(){
		JobDetail jobDetail=JobBuilder.newJob(CheckDoMainIpJob.class).withIdentity("checkDoMain", "myJob").build();
    	SimpleTrigger trigger=TriggerBuilder.newTrigger().withIdentity("trigger1", "myTrigger")
    	.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(DDNSEntity.getIntervalTime()).repeatForever()).build();
    	
    	SchedulerFactory schedulerFactory = new StdSchedulerFactory();
         Scheduler scheduler;
		try {
			scheduler = schedulerFactory.getScheduler();
			  //将任务及其触发器放入调度器
	         scheduler.scheduleJob(jobDetail, trigger);
	         //调度器开始调度任务
	         scheduler.start();
	         return scheduler;
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
  
       
		
	}
}
