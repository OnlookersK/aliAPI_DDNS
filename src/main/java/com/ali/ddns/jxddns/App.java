package com.ali.ddns.jxddns;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws SchedulerException {
    	PublicUtil.startApp();
    }
   /*
    public static Map<String, String>  getafterDay() {
	 	Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		Date syncStaticTime=calendar.getTime();
		calendar.set(Calendar.DAY_OF_MONTH,1);
		Date syncStaticTime1=calendar.getTime();
		
		SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd");
		System.out.println(syncStaticTime1.getTime());
		Map<String,String>resultMap=new HashMap<String,String>();
		resultMap.put("startGetTime",String.valueOf(syncStaticTime1.getTime()));
		resultMap.put("endGetTime", String.valueOf(syncStaticTime.getTime()));
		resultMap.put("startTime", sim.format(syncStaticTime1));
		resultMap.put("endTime", sim.format(syncStaticTime));
		return resultMap;
 }
    
    public static  Map<String,String> getYearWeek(){
    	Map<String,String> resultMap=new HashMap<String, String>();
		  int startYear=1968;
		  int week=1;
    	  
    	  Calendar cal = Calendar.getInstance();
		  Integer year = cal.get(Calendar.YEAR);
		  int doy = cal.get(Calendar.DAY_OF_YEAR);
    	  
		  int offsetYear =((year - startYear)/4 *5) + ((year - startYear)%4);
		  int offsetWeek=offsetYear%7;
		  int currentweek=week+offsetWeek;
		  System.out.println(currentweek);
		  
		 int weekSum =(doy+currentweek)/7 + (doy+currentweek)%7 >0? 1:0;
		 System.out.println(weekSum);
		  String weekSumStr=String.valueOf(weekSum);
		 if(weekSum<10) {
			 weekSumStr="0"+weekSum;
		 }
		 
     resultMap.put("yearSum", year.toString());
     resultMap.put("weekSum", weekSumStr);
		return resultMap;
		
	}
    */
 
}
