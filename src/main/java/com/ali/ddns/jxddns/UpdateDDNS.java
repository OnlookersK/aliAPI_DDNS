/**
 * 
 */
package com.ali.ddns.jxddns;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ali.ddns.entity.DDNSEntity;
import com.ali.ddns.init.CacheEntity;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.alidns.model.v20150109.DescribeDomainGroupsRequest;
import com.aliyuncs.alidns.model.v20150109.DescribeDomainGroupsResponse;
import com.aliyuncs.alidns.model.v20150109.DescribeDomainRecordsRequest;
import com.aliyuncs.alidns.model.v20150109.DescribeDomainRecordsResponse;
import com.aliyuncs.alidns.model.v20150109.DescribeDomainRecordsResponse.Record;
import com.aliyuncs.alidns.model.v20150109.DescribeDomainsRequest;
import com.aliyuncs.alidns.model.v20150109.DescribeDomainsResponse;
import com.aliyuncs.alidns.model.v20150109.DescribeDomainsResponse.Domain;
import com.aliyuncs.alidns.model.v20150109.UpdateDomainRecordRequest;
import com.aliyuncs.alidns.model.v20150109.UpdateDomainRecordResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.google.gson.Gson;

/**
 * @author underworld
 *
 */
public class UpdateDDNS {

	private static UpdateDDNS updateDDNS=null;
	
	
	/**
	 * 
	 */
	private UpdateDDNS() {
	
	}

	private synchronized static void synInit() {
		if(updateDDNS == null) {
			updateDDNS=new UpdateDDNS();
		}
		
	}
	public  static UpdateDDNS getInstance() {
		if(updateDDNS == null) {
			synInit();
		}
		return updateDDNS;
	}


    /**
     * 获取主域名的所有解析记录列表
     */
    private DescribeDomainRecordsResponse describeDomainRecords(DescribeDomainRecordsRequest request, IAcsClient client){
        try {
            // 调用SDK发送请求
            return client.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
            // 发生调用错误，抛出运行时异常
            throw new RuntimeException();
        }
    }

    /**
     * 修改解析记录
     */
    private UpdateDomainRecordResponse updateDomainRecord(UpdateDomainRecordRequest request, IAcsClient client){
        try {
            // 调用SDK发送请求
            return client.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
            // 发生调用错误，抛出运行时异常
            throw new RuntimeException();
        }
    }

    private static void log_print(String functionName, Object result) {
        Gson gson = new Gson();
        System.out.println("-------------------------------" + functionName + "-------------------------------");
        System.out.println(gson.toJson(result));
    }
    
    
    private IAcsClient returnClient() {
    	  // 设置鉴权参数，初始化客户端
        DefaultProfile profile = DefaultProfile.getProfile(
                DDNSEntity.getAreaId(),// 地域ID
                DDNSEntity.getAccessKey(),// 您的AccessKey ID
                DDNSEntity.getSecret());// 您的AccessKey Secret
        IAcsClient client = new DefaultAcsClient(profile);
        return client;
    }
    private List<Record> getConnect(IAcsClient client) {
    	
    	
        // 查询指定二级域名的最新解析记录
        DescribeDomainRecordsRequest describeDomainRecordsRequest = new DescribeDomainRecordsRequest();
        // 主域名
        describeDomainRecordsRequest.setDomainName(DDNSEntity.getDoMain());
        // 主机记录 可设置二级域名
        describeDomainRecordsRequest.setRRKeyWord(DDNSEntity.getHostRec());
        // 解析记录类型
        describeDomainRecordsRequest.setType(DDNSEntity.getRecType());
        DescribeDomainRecordsResponse describeDomainRecordsResponse =describeDomainRecords(describeDomainRecordsRequest, client);
        log_print("describeDomainRecords",describeDomainRecordsResponse);

        List<DescribeDomainRecordsResponse.Record> domainRecords = describeDomainRecordsResponse.getDomainRecords();
		return domainRecords;
       
    }
    
    public  void  updateDDNS() {
    	IAcsClient client=returnClient();
    	List<Record> domainRecords=getConnect(client);
       // 最新的一条解析记录
        if(domainRecords.size() != 0 ){
            DescribeDomainRecordsResponse.Record record = domainRecords.get(0);
            
            // 记录ID
            String recordId = record.getRecordId();
            // 记录值
            String recordsValue = record.getValue();
            System.out.println(recordsValue);
            // 当前主机公网IP
            String currentHostIP = PublicUtil.getCurrentHostIP();
            System.out.println(currentHostIP);
            System.out.println("-------------------------------当前主机公网IP为："+currentHostIP+"-------------------------------");
            if(!currentHostIP.equals(recordsValue)){
                // 修改解析记录
                UpdateDomainRecordRequest updateDomainRecordRequest = new UpdateDomainRecordRequest();
                // 主机记录
                updateDomainRecordRequest.setRR(DDNSEntity.getHostRec());
                // 记录ID
                updateDomainRecordRequest.setRecordId(recordId);
                // 将主机记录值改为当前主机IP
                updateDomainRecordRequest.setValue(currentHostIP);
                // 解析记录类型
                updateDomainRecordRequest.setType(DDNSEntity.getRecType());
                UpdateDomainRecordResponse updateDomainRecordResponse = updateDomainRecord(updateDomainRecordRequest, client);
                log_print("updateDomainRecord",updateDomainRecordResponse);
            }
        }
    
    }
    
    public  String  selectDDNS() {
    	IAcsClient client=returnClient();
    	List<Record> domainRecords=getConnect(client);
    	// 最新的一条解析记录
        if(domainRecords.size() != 0 ){
            DescribeDomainRecordsResponse.Record record = domainRecords.get(0);
            
            // 记录ID
            String recordId = record.getRecordId();
            // 记录值
            String recordsValue = record.getValue();
          
          return recordsValue;
        }
		return null;
    
    }
    
    
    public static void main(String[] args) {
    	new CacheEntity().getCacheEntity(); //初始化参数配置
    	UpdateDDNS upd=UpdateDDNS.getInstance();
    	upd.selectDDNS();
    	upd.updateDDNS();
    }
}
