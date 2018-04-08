package com.web.bihu.util;

import java.util.NoSuchElementException;
import java.util.StringTokenizer;

/**
 * ������ʽ�����࣬������������ʽ֮��һЩ��ȡ�ַ�����
 * 
 * @name        ������ʽ����
 * @author                  �������     liyangchiyue@gmail.com
 * @createtTime 2013-5-21  ����8:25:37
 * @version     1.0
 * @since       1.0
 *
 */
public class RegexUtil {

	
	/**
	 * ��ȡ��һ�γ��� b �� c �м���ַ�
	 * 
	 * @param a  ԭ�ַ�
	 * @param b  b��ʼ�ַ�
	 * @param c  c�����ַ�
	 * @return
	 */
	public static String substring(String a,String b,String c){
		a = a.substring(a.indexOf(b)+b.length());
		return a.substring(0,a.indexOf(c)-c.length()+1);
	}
	
	/**
	 * 
	 * ��ȡDOM�ṹ�У���N�γ���
	 * 
	 * @param v         ԭ�ַ�
	 * @param tagStart  ��ʼ�ַ� ��<td>
	 * @param endStart  �����ַ� ��</td>
	 * @param choice    ��ȡ��choice�γ���ʱ�Ľṹ
	 * @return
	 */
	public static String substring(String v,String tagStart, String endStart, int choice){
		for(int i=1;i<choice;i++){
			v = v.substring(v.indexOf(tagStart)+tagStart.length());
		    v = v.substring(v.indexOf(endStart)+endStart.length());
		}
		return substring(v,tagStart,endStart);
	}
	
	/**
	 * �滻�ַ�  ,��ȡV�е� name�Ժ��һ���ַ����ö��ַ���������Ҫ�з�[a-zA-Z_0-9@]�ı�ǡ����� :<br>
	 * v = {'push_type':'lost','tas123ks'}<br>
	 * name = lost<br>
	 * return = tas123ks<br>
	 * 
	 * @param v      ��Ҫ�������ַ���
	 * @param name   ��Ҫȡ���ַ���
	 * @return  ��ȡ��ɵ��ַ�
	 */
	public static String replaceString(String v, String name){
		return v.replaceAll("[\r\n]*", "").replaceFirst("^.*?"+name, "").replaceFirst("[^a-zA-Z0-9_@\u4E00-\u9FFF]*", "").replaceFirst("[^a-zA-Z0-9_@\u4E00-\u9FFF]+.*$", "");
	}
	
	/**
	 * �滻�ַ�����ȡV�е�name�Ժ��һ�����֡����磺<br>
	 * v = {expToday':2,'expTomorrow':2,'signInDataNum':3,'signTaskType':'65'}}
	 * name = signTaskType
	 * return = 65
	 * 
	 * @param v      ��Ҫ�������ַ���
	 * @param name   ��Ҫȡ���ַ���
	 * @return  ��ȡ��ɵ��ַ�
	 */
	public static String replaceInt(String v,String name){
		return v.replaceAll("[\r\n]*", "").replaceFirst("^.*?"+name, "").replaceFirst("[^\\d]*", "").replaceFirst("[^\\d]+.*$", "");
	}

	public static String replaceStartEnd(String v, String name, String start, String end){
		return v.replaceAll("[\r\n]*", "").replaceFirst("^.*?"+name, "").replaceFirst(".*"+start, "").replaceFirst(end+".*", "");
	}

	public static String replaceStartEnd(String v, String start, String end){
		System.out.println("----" + v.replaceAll("[\r\n]*", ""));
		System.out.println("----" + v.replaceAll("[\r\n]*", "").replaceFirst("^.*?"+start, ""));
		return v.replaceAll("[\r\n]*", "").replaceFirst("^.*?"+start, "").replaceFirst(end+".*$", "");
	}

	/**
	 * ��ȡ���ַ�֮����ַ�����һ�γ��ֵģ���a�ַ���ͷ-b�ַ���β���м䲿�֣�������a��b<br>
	 * 
	 * @param str  ��Ҫ��ȡ���ַ�
	 * @param a    ��ʼ�ַ�
	 * @param b    �����ַ�
	 * @return  ���ɹ�,����(��Ҫ��ȡ���ַ�)<br>���򷵻�("")
	 * 
	 * @throws NullPointerException ��strΪnull��ʱ��
	 * @see #substringMiddle(String, String, String);
	 */
	public static String substringMiddle(String str, char a, char b){
		return substringMiddle( str, String.valueOf(a), String.valueOf(b) );
	}
	/**
	 * ��ȡ���ַ�֮����ַ�����һ�γ��ֵģ���a�ַ���ͷ-b�ַ���β���м䲿�֣�������a��b<br>
	 * 
	 * @param str  ��Ҫ��ȡ���ַ�
	 * @param a    ��ʼ�ַ�
	 * @param b    �����ַ�
	 * @return  ���ɹ�,����(��Ҫ��ȡ���ַ�)<br>���򷵻�("")
	 * 
	 * @throws NullPointerException ��strΪnull��ʱ��
	 */
	public static String substringMiddle(String str, String a, String b){
		if( str == null )
			throw new NullPointerException();
		if( !a.equals(b) && str.indexOf(b) < str.indexOf(a) )    //���ڶ����ַ��ȵ�һ���ַ��ȳ���
			str = str.substring( str.indexOf( b )+1 );           //��ȥ��һ�γ��ֵڶ����ַ�֮ǰ���ַ�
		StringTokenizer st = new StringTokenizer(str.substring( str.indexOf(a)+1 ) );
		try{
			return st.nextToken(b);
		}catch(NoSuchElementException e){
			return "";
		}
	}
}
