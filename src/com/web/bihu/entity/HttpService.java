package com.web.bihu.entity;

import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.Proxy;
import java.util.List;

import com.web.bihu.interfaced.SetHttpConnection;
import com.web.bihu.util.HttpUtil;
import com.web.bihu.util.SendRequestUtil;

/**
 * Http���������,�ṩ�򵥵Ļ���������Ϣ��װ
 * 
 * @name        Http���������
 * @author                  �������     liyangchiyue@gmail.com
 * @createtTime 2013-6-17  ����9:12:50
 * @version     1.0
 * @since       1.0
 *
 */
@SuppressWarnings("serial")
public abstract class HttpService implements Serializable{
	/** Ĭ���ֻ������ */
	public static final String PHONE_AGENT = 
			"Mozilla/5.0 (Linux; U; Android 0.5; en-us) AppleWebKit/522+ (KHTML, like Gecko) Safari/419.3";
	/** Ĭ�ϵ�������� */
	
	public static final String CONPUTER_AGENT = 
			"Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36 QIHU 360SE";
	
	/** ���뷽ʽ */
	protected String charset="UTF-8";
	/** ����� */
	protected String userAgent;
	/** ���� */
	protected Proxy proxy;
	/** ��ǰ�û�Cookie */
	protected String cookie = "";
	/** IP */
	protected String ip;

	/**
	 * һ��HTTP���󣬰����ö���� proxy���ԣ�cookie���ԣ�userAgent����<br>
	 * <ul>
	 * <li>1.Ĭ�Ϸ��͸ö����{@link #cookie Cookie}��</li>
	 * <li>2.Ĭ�ϱ���ӷ��������յ���{@link #cookie Cookie}</li>
	 * <li>3.Ĭ��ʹ�øö����{@link #proxy ����}����û��{@link #proxy ����}����ֱ������</li>
	 * <li>4.Ĭ��ʹ�øö���ͨ��{{@link #userAgent}ָ���������</li>
	 * <ul>
	 * @param urlStr  �����ַ
	 * @param type    �������� "post","get",Ĭ��"get"
	 * @param sc
	 * @param params  ���������Post�����Ż�ʹ��
	 * @param userAgent ָ�����������
	 * 
	 * @see {@SendRequestUtil#doRequest(String urlStr, String type, SetHttpConnection sc, String params, String cookie, Proxy proxy, String userAgent)}
	 * @return  �����õ���ҳ����
	 */
	public String request(String urlStr, String type, SetHttpConnection sc, String params, String userAgent){
		System.out.println("������������ʡ�urlStr:" + urlStr + " | type:" + type + " | (SetHttpConnection)sc:" + sc + " | params:" + params  + " | userAgent:" + userAgent + "����");
		return SendRequestUtil.doRequest(urlStr, type, sc!=null ? sc : new SetHttpConnection() {
			
			@Override
			public String before(HttpURLConnection httpConn) throws ProtocolException {
				return null;
			}
			
			@Override
			public String after(HttpURLConnection httpConn) {
				List<String> lists = httpConn.getHeaderFields().get("Set-Cookie");
				cookie = HttpUtil.mergeCookies(cookie, lists);
				return cookie;
			}
		}, params, cookie, proxy, userAgent);
	}
	

	/**
	 * һ��HTTP���󣬰����ö���� proxy���ԣ�cookie���ԣ�userAgent����<br>
	 * <ul>
	 * <li>1.Ĭ�Ϸ��͸ö����{@link #cookie Cookie}��</li>
	 * <li>2.Ĭ�ϱ���ӷ��������ֵ���{@link #cookie Cookie}</li>
	 * <li>3.Ĭ��ʹ�øö����{@link #proxy ����}����û��{@link #proxy ����}����ֱ������</li>
	 * <li>4.Ĭ��ʹ�øö���ͨ��{{@link #userAgent}ָ���������</li>
	 * <ul>
	 * @param urlStr  �����ַ
	 * @param type    �������� "post","get",Ĭ��"get"
	 * @param sc      
	 * @param params  ���������Post�����Ż�ʹ��
	 * 
	 * @see {@SendRequestUtil#doRequest(String urlStr, String type, SetHttpConnection sc, String params, String cookie, Proxy proxy, String userAgent)}
	 * @return  �����õ���ҳ����
	 */
	public String request(String urlStr, String type, SetHttpConnection sc, String params){
		System.out.println("�ڶ���������ʡ�urlStr:" + urlStr + " | type:" + type + " | (SetHttpConnection)sc:" + sc + " | params:" + params  + "����");
		return request(urlStr, type, sc, params, this.userAgent);
	}
	
	
	/**
	 * ����һ����������
	 * ���ϵ�ǰ��Cookie
	 * Ĭ��ִ��Cookie�ϲ�{@link HttpUtil#mergeCookies(String cookie,List list)}
	 * 
	 * @param url      ��ַ
	 * @param outPath  �����ַ
	 * @param sc       
	 */
	public void requestHttpDownload(String url, String outPath, SetHttpConnection sc){
		SendRequestUtil.httpDownload(url, outPath, sc!=null?sc:new SetHttpConnection() {
			
			@Override
			public String before(HttpURLConnection httpConn) throws ProtocolException {
				return null;
			}
			
			@Override
			public String after(HttpURLConnection httpConn) {
				List<String> lists = httpConn.getHeaderFields().get("Set-Cookie");
				cookie = HttpUtil.mergeCookies(cookie, lists);
				return cookie;
			}
		}, cookie, proxy, userAgent);
	}
	
	/**
	 * 
	 * ����һ����������
	 * ���ϵ�ǰ��Cookie
	 * Ĭ��ִ��Cookie�ϲ�{@link HttpUtil#mergeCookies(String cookie,List list)}
	 * 
	 * @param url      ��ַ
	 * @param outPath  �����ַ
	 */
	public void requestDownload(String url, String outPath){
		this.requestHttpDownload(url, outPath, null);
	}
	
	/**
	 * һ��Get����<br>
	 * <ul>
	 * <li>1.Ĭ�Ϸ��͸ö����{@link #cookie Cookie}��</li>
	 * <li>2.Ĭ�ϱ���ӷ��������ֵ���{@link #cookie Cookie}</li>
	 * <li>3.Ĭ��ʹ�øö����{@link #proxy ����}����û��{@link #proxy ����}����ֱ������</li>
	 * <li>4.Ĭ��ʹ�øö���ͨ��{{@link #userAgent}ָ���������</li>
	 * <ul>
	 * 
	 * @param urlStr  �����ַ
	 * @return  �����õ���ҳ����
	 */
	public String requestGet(String urlStr){
		System.out.println("��һ��������ʡ�urlStr:" + urlStr + "����");
		return request(urlStr, "get", null, null);
	}
	
	/**
	 * һ��Post����<br>
	 * <ul>
	 * <li>1.Ĭ�Ϸ��͸ö����{@link #cookie Cookie}��</li>
	 * <li>2.Ĭ�ϱ���ӷ��������ֵ���{@link #cookie Cookie}</li>
	 * <li>3.Ĭ��ʹ�øö����{@link #proxy ����}����û��{@link #proxy ����}����ֱ������</li>
	 * <li>4.Ĭ��ʹ�øö���ͨ��{{@link #userAgent}ָ���������</li>
	 * <ul>
	 * 
	 * @param urlStr  �����ַ
	 * @param params  �������
	 * @return  �����õ���ҳ����
	 */
	public String requestPost(String urlStr, String params){
		return request(urlStr, "post", null, params);
	}

	/**
	 * һ���ֻ���Get����<br>
	 * <ul>
	 * <li>1.Ĭ�Ϸ��͸ö����{@link #cookie Cookie}��</li>
	 * <li>2.Ĭ�ϱ���ӷ��������ֵ���{@link #cookie Cookie}</li>
	 * <li>3.Ĭ��ʹ�øö����{@link #proxy ����}����û��{@link #proxy ����}����ֱ������</li>
	 * <li>4.Ĭ��ʹ�øö���ͨ��{{@link #userAgent}ָ���������</li>
	 * <ul>
	 * 
	 * @param urlStr  �����ַ
	 * @return  �����õ���ҳ����
	 */
	public String requestGetOfPhone(String urlStr){
		return request(urlStr, "get", null, null, PHONE_AGENT);
	}
	
	/**
	 * һ���ֻ���Post����<br>
	 * <ul>
	 * <li>1.Ĭ�Ϸ��͸ö����{@link #cookie Cookie}��</li>
	 * <li>2.Ĭ�ϱ���ӷ��������ֵ���{@link #cookie Cookie}</li>
	 * <li>3.Ĭ��ʹ�øö����{@link #proxy ����}����û��{@link #proxy ����}����ֱ������</li>
	 * <li>4.Ĭ��ʹ�øö���ͨ��{{@link #userAgent}ָ���������</li>
	 * <ul>
	 * 
	 * @param urlStr  �����ַ
	 * @return  �����õ���ҳ����
	 */
	public String requestPostOfPhone(String urlStr,String params){
		return request(urlStr, "post", null, params, PHONE_AGENT);
	}
}
