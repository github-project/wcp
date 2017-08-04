package com.farm.wcp.util;


import java.io.IOException;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Scanner;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;


import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

/**
 * 一般工具类
 * 
 * @author terence
 *
 */
public class Utils {

	private static final Logger logger = LoggerFactory.getLogger(Utils.class);
	
	private static SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

	private static MessageDigest digest = null;
	
	public static synchronized String formatDate(Date date){
		return formatter.format(date);
	}
	
	public static synchronized Date parseDate(String date_str){
		try{
		return formatter.parse(date_str);
		}catch(Exception e){
			logger.error(e.getMessage());
		}
	   return null;
	}

	
	public static final String encodeHex(byte[] bytes) {
		StringBuffer buf = new StringBuffer(bytes.length * 2);

		for (int i = 0; i < bytes.length; ++i) {
			if ((bytes[i] & 0xFF) < 16) {
				buf.append("0");
			}
			buf.append(Long.toString(bytes[i] & 0xFF, 16));
		}
		return buf.toString();
	}

	public static Date getTokenExpireTime() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MINUTE, 120);
		
		
		return calendar.getTime();
	}

	public static int getTimeValidRange(String time_valid_range) {
		int time_valid_range_sec = 0;
		if (StringUtils.isEmpty(time_valid_range)) {
			return 0;
		}
		String val = "0";
		char unit = time_valid_range.charAt(time_valid_range.length() - 1);
		if ("yMdhms".indexOf(unit) != -1) {
			val = time_valid_range.substring(0, time_valid_range.length() - 1);
			int val_int = 0;
			try {
				val_int = Integer.parseInt(val);
			} catch (Exception e) {
				logger.warn("OtsUtils.getTimeValidRange() Integer.parseInt("
						+ val
						+ ") fail,please check the value of time_valid_range in ots.properties.");
				return 0;
			}
			time_valid_range_sec = getTimeValidRangeSec(val_int, unit);
		} else {
			try {
				time_valid_range_sec = Integer.parseInt(time_valid_range);
			} catch (Exception e) {
				logger.warn("OtsUtils.getTimeValidRange() Integer.parseInt("
						+ time_valid_range
						+ ") fail,please check the value of time_valid_range in ots.properties.");
				time_valid_range_sec = 0;
			}
		}
		return time_valid_range_sec;
	}

	private static int getTimeValidRangeSec(int val_int, char unit) {
		int time_valid_range_sec = 0;
		if ('y' == unit)
			time_valid_range_sec = val_int * 365 * 24 * 60 * 60;
		else if ('M' == unit)
			time_valid_range_sec = val_int * 30 * 24 * 60 * 60;
		else if ('d' == unit)
			time_valid_range_sec = val_int * 24 * 60 * 60;
		else if ('h' == unit)
			time_valid_range_sec = val_int * 60 * 60;
		else if ('m' == unit)
			time_valid_range_sec = val_int * 60;
		else if ('s' == unit)
			time_valid_range_sec = val_int;
		else {
			time_valid_range_sec = val_int;
		}
		return time_valid_range_sec;
	}
	
	
	/**
	 * 获取主机名
	 * 
	 * @return
	 */
//	public static String getHostName() {
//
//		try {
//			return InetAddress.getLocalHost().getHostName();
//		} catch (UnknownHostException e) {
//			logger.error("linux unknown host!");
//		}
//		
//		return "default_host";
//	}
	
	/**
	  * 判断系统
	  *
	  * @return String
	  */
	 public static boolean isWindowsOS(){
	    boolean isWindowsOS = false;
	    String osName = System.getProperty("os.name");
	    if(osName.toLowerCase().indexOf("windows")>-1){
	    	isWindowsOS = true;
	    }
	    return isWindowsOS;
	  }
	 
	 /**
	   * 获取本机ip地址，并自动区分Windows还是linux操作系统
	    * @return String
	    */
	 public static String getLocalIP(){
	     String sIP = "";
	     InetAddress ip = null;
	     try {
		      //如果是Windows操作系统
		      if(isWindowsOS()){
		    	  ip = InetAddress.getLocalHost();
		      } else{
			      //如果是Linux操作系统
			       boolean bFindIP = false;
			       Enumeration<NetworkInterface> netInterfaces = (Enumeration<NetworkInterface>) NetworkInterface
			         .getNetworkInterfaces();
			       while (netInterfaces.hasMoreElements()) {
			        if(bFindIP){
			        	break;
			        }
			        NetworkInterface ni = (NetworkInterface) netInterfaces.nextElement();
			        //----------特定情况，可以考虑用ni.getName判断
			        //遍历所有ip
			        Enumeration<InetAddress> ips = ni.getInetAddresses();
			        while (ips.hasMoreElements()) {
			         ip = (InetAddress) ips.nextElement();
			         if( ip.isSiteLocalAddress() 
			                    && !ip.isLoopbackAddress()   //127.开头的都是lookback地址
			                    && ip.getHostAddress().indexOf(":")==-1){
			             bFindIP = true;
			                break; 
			            }
			        }
		       }
	      }
	     }catch (Exception e) {
	    	 logger.error("get ip error.",e);
	     }

	     if(null != ip){
	    	 sIP = ip.getHostAddress();
	     }
	     logger.debug("localhost IP:" + sIP);
	     
	     return sIP;
	   }



	/**
	 * 数据类型转换：String 转换成 Double，保留小数几位
	 * 
	 * @param dataStr
	 * @param dNo
	 * 
	 * @result Double
	 */
	public static Double stringToDouble(String dataStr, Integer dNo) {
		// 构造以字符串内容为值的BigDecimal类型的变量bd
		BigDecimal bd = new BigDecimal(dataStr);
		// 设置小数位数，第一个变量是小数位数，第二个变量是取舍方法(四舍五入)
		Double douV = bd.setScale(dNo, BigDecimal.ROUND_HALF_UP).doubleValue();

		return douV;
	}
	
	/**
	 * 数据类型转换：String 转换成 BigDecimal，保留小数几位
	 * 
	 * @param dataStr
	 * @param dNo
	 * 
	 * @result BigDecimal
	 */
	public static BigDecimal stringToBigDecimal(String dataStr, Integer dNo) {
		// 构造以字符串内容为值的BigDecimal类型的变量bd
		BigDecimal bd = new BigDecimal(dataStr);
		// 设置小数位数，第一个变量是小数位数，第二个变量是取舍方法(四舍五入)
		bd=bd.setScale(dNo, BigDecimal.ROUND_HALF_UP); 

		return bd;
	}

	/**
	 * 时间计算：加小时
	 * 
	 * @param dataStr
	 * @param dNo
	 * 
	 * @result Double
	 */
	public static Date dateAddHour(Date dateV, Integer hours) {
		Calendar rightNow = Calendar.getInstance();
		int hour = rightNow.get(Calendar.HOUR_OF_DAY);
		hour += hours;
		rightNow.set(Calendar.HOUR_OF_DAY, hour);
		Date date = rightNow.getTime();

		return date;
	}

	
	
	/**
	 * 补"0"
	 * 
	 * @param size 长度
	 * @param 原值
	 * 
	 * @return String
	 */
	public static String addZero(int size, String val){
		String result = val;
		if(val.length() >= size){
			return val;
		} else {
			for(int i = 0; i < size - val.length(); i++){
				result = "0" + result;
			}
			
			return result;
		}
	}
	
	/**
	 * 补"0"
	 * 
	 * @param size 长度
	 * @param 原值
	 * 
	 * @return String
	 */
	public static String addZero(int size, int val){
		String s = String.valueOf(val);
		String result = s;
		if(s.length() >= size){
			return s;
		} else {
			for(int i = 0; i < size - s.length(); i++){
				result = "0" + result;
			}
			
			return result;
		}
	}
	
	
	
	
}
