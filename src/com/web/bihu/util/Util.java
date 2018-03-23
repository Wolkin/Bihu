package com.web.bihu.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 
 * @category �򵥹���
 * @author   Wolkin
 * @version  1.0
 * @date     2018-03-21
 */

public class Util {
	/** ��ʽ���� yyyy_MM_dd */
	public static final SimpleDateFormat formatDateBy_ = new SimpleDateFormat("yyyy_MM_dd");
	
	/** ��ʽ���� yyyy-MM-dd*/
	public static final SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
	
	/** ��ʽ���� yyyy-MM-dd HH:mm:ss*/
	public static final SimpleDateFormat formatDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/** ��ʽСʱ */
	public static final SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm:ss");
	
	/** ��ȡ��Ŀ�ĸ�·�� **/
	public static String root;

	static{
		root = HttpUtil.stringDecoder( Util.class.getClassLoader().getResource("").getPath() );
		root = new File(root).getParent();
	}
	/**
	 * ��ʽ����   2012_1_1
	 * @param date  ����
	 * @return  ��ʽ�����ڵ��ַ���
	 */
	public static String formatDateBy_(Date date){
		return formatDateBy_.format(date);
	}
	
	/**
	 * ��ʽ���� 2012-1-1
	 * @param date ����
	 * @return ��ʽ�����ڵ��ַ���
	 */
	public static String formatDate(Date date){
		return formatDate.format(date);
	}
	
	/**
	 * ��ʽ���� yyyy-MM-dd HH:mm:ss
	 * @param date ���ڶ���
	 * @return ��ʽ�����ڵ��ַ���
	 */
	public static String formatDateTime(Date date){
		return formatDateTime.format(date);
	}
	
	/**
	 * ��ʽʱ��  HH:mm:ss����һ�����ڶ���ĺ��룬ת��ΪСʱ�����24Сʱ
	 * @param date ���ڶ���
	 * @return ��ʽ�����ڵ��ַ���
	 */
	public static String formatTime(Date date){
		return formatTime.format(date);
	}
	
	/**
	 * ��ʽ��·������/(��б��)��Ϊ·���ָ��
	 * @param path  ��Ҫ��ʽ��·���ַ���
	 * @return ��ʽ��ɵ�·���ַ���
	 */
	public static String formatPath(String path){
		return path.replace("\\", "/");
	}
	
	/**
	 * ���ݴ�������ݣ��ж����ݵĳ��ȣ�������
	 * @param content
	 * @return
	 */
	public static String getContent(String content){
		if(content.length() > 30){
			StringBuffer ss = new StringBuffer();
			for(int k = 0;k<content.length();k++){
				ss.append(content.charAt(k));
				if(k%16 == 0){
					ss.append("<br />");
				}
			}
			content = ss.toString();
		}
		return content;
	}
	/**
	 * ���㣬��������һ��ʱ�仹�ж��ٺ���
	 * 
	 * 
	 * @param afterTime  ���ж��ٺ��룬�����ʱ�� �����磺3��00���������3��00���ж��ٺ���
	 * @return  ���ٺ���
	 * 
	 * @throws RuntimeException ʱ���ʽ����
	 */
	public static long afterDay(String afterTime){
		if( !java.util.regex.Pattern.compile("\\d{1,2}:\\d{1,2}+").matcher(afterTime).matches())
			throw new RuntimeException("format error");
		
		long now = System.currentTimeMillis();
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, 1);
		c.set(Calendar.HOUR_OF_DAY, Integer.parseInt( afterTime.split(":")[0] ));
		c.set(Calendar.MINUTE, Integer.parseInt( afterTime.split(":")[1] ));
		c.set(Calendar.SECOND,0);
		return c.getTime().getTime() - now;
	}
	
	/**
	 * �滻һ���ַ����е�ռλ��{0},{1},{2}
	 * Ϊ��̬�ַ�
	 * 
	 * ע�⣺��{0}��ʼ
	 * 
	 * @param fill  ԭ�ַ���  �磺http://check.ptlogin2.qq.com/check?regmaster=&uin=448163451&appid={1}&js_ver={2}&js_type=1
	 * @param str   ��̬���վλ�ַ�
	 * @return �����ɵ����ַ���
	 */
	public static String fillString(String fill,String ...str){
		if(null == fill ||null == str)
			return "";
		
		int $i = 0;
		for(String v : str ){
			fill = fill.replace("{"+$i+++"}", v);
		}
		return fill;
	}
}
