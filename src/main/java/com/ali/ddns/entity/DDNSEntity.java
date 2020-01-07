/**
 * 
 */
package com.ali.ddns.entity;

import java.io.Serializable;

/**
 * @author underworld
 *
 */
public class DDNSEntity implements Serializable{
	
/**
	 * 
	 */
private static final long serialVersionUID = -4891728210141168887L;
	
private static String accessKey; //AccessKey ID
private static String secret;	//AccessKey Secret
private static String doMain;	//域名
private static String localIp;  //本地存放ip
private static String areaId;	// 地域id
private static String hostRec;	// 主机记录值
private static String recType;	// 记录类型
private static String recordId; //记录id
private static int IntervalTime; //定时器时间间隔
private static int SecurityTime; //守护线程时间间隔
private static boolean status;
private static int runNumber;


/**
 * @return the runNumber
 */
public static int getRunNumber() {
	return runNumber;
}
/**
 * @param runNumber the runNumber to set
 */
public static void setRunNumber(int runNumber) {
	DDNSEntity.runNumber = runNumber;
}
/**
 * @return the status
 */
public static boolean isStatus() {
	return status;
}
/**
 * @param status the status to set
 */
public static void setStatus(boolean status) {
	DDNSEntity.status = status;
}
/**
 * @return the accessKey
 */
public static String getAccessKey() {
	return accessKey;
}
/**
 * @param accessKey the accessKey to set
 */
public static void setAccessKey(String accessKey) {
	DDNSEntity.accessKey = accessKey;
}
/**
 * @return the secret
 */
public static String getSecret() {
	return secret;
}
/**
 * @param secret the secret to set
 */
public static void setSecret(String secret) {
	DDNSEntity.secret = secret;
}
/**
 * @return the doMain
 */
public static String getDoMain() {
	return doMain;
}
/**
 * @param doMain the doMain to set
 */
public static void setDoMain(String doMain) {
	DDNSEntity.doMain = doMain;
}
/**
 * @return the localIp
 */
public static String getLocalIp() {
	return localIp;
}
/**
 * @param localIp the localIp to set
 */
public static void setLocalIp(String localIp) {
	DDNSEntity.localIp = localIp;
}
/**
 * @return the areaId
 */
public static String getAreaId() {
	return areaId;
}
/**
 * @param areaId the areaId to set
 */
public static void setAreaId(String areaId) {
	DDNSEntity.areaId = areaId;
}
/**
 * @return the hostRec
 */
public static String getHostRec() {
	return hostRec;
}
/**
 * @param hostRec the hostRec to set
 */
public static void setHostRec(String hostRec) {
	DDNSEntity.hostRec = hostRec;
}
/**
 * @return the recType
 */
public static String getRecType() {
	return recType;
}
/**
 * @param recType the recType to set
 */
public static void setRecType(String recType) {
	DDNSEntity.recType = recType;
}
/**
 * @return the recordId
 */
public static String getRecordId() {
	return recordId;
}
/**
 * @param recordId the recordId to set
 */
public static void setRecordId(String recordId) {
	DDNSEntity.recordId = recordId;
}
/**
 * @return the intervalTime
 */
public static int getIntervalTime() {
	return IntervalTime;
}
/**
 * @param intervalTime the intervalTime to set
 */
public static void setIntervalTime(int intervalTime) {
	IntervalTime = intervalTime;
}
/**
 * @return the securityTime
 */
public static int getSecurityTime() {
	return SecurityTime;
}
/**
 * @param securityTime the securityTime to set
 */
public static void setSecurityTime(int securityTime) {
	SecurityTime = securityTime;
}


}
