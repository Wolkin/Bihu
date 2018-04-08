package com.web.bihu.util;

import java.io.UnsupportedEncodingException;
import java.net.HttpCookie;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Http��������<br>
 * ����Ļ�ȡ������Http��صĲ���
 * <br>
 * 
 * @category  Http��������
 * @version   1.0     (2018-03-21)
 * @since     1.0
 * @author    Wolkin
 * 
 */
public class HttpUtil {

	/**
	 * ��ȡ��������վ��ֵ��,��ʽ��Wolkin=023053&pass=123321&name=iljljlfs
	 * ����null<br>
	 *     Map<String,List<Object>>����
	 * @return ���ز����ļ�ֵ�ԣ�������Ϊnull�򷵻�""
	 */
	@SuppressWarnings("unchecked")
	public static String formatParameters(Map<String,Object> params){
		//��������Ϊ��
		if(params == null){
			return "";
		}
		
		StringBuffer sb = new StringBuffer();
		for(String key : params.keySet() ){
			if( params.get(key) instanceof String){
				sb.append( key+"="+params.get(key)+"&");
			}else if( params.get(key) instanceof List ){
				List<Object> lists = (List<Object>)params.get(key);
				if(lists != null){
					for( Object obj : lists){
						sb.append( key+"="+obj.toString()+"&" );
					}
				}
			}
		}
		return sb.deleteCharAt(sb.length()-1).toString();
	}
	
	/**
	 * ƴ��һ�������е��ַ������Cookie��ʽ
	 * ��ȡ Cookie �ַ�������ʽ��liyang=023053,0we=gdxg,
	 * ���� null ����
	 * @param lists  ƴ�Ӽ���
	 * @return ��ȡ Cookie �ַ���
	 */
	public static String formatCookie(List<String> lists){
		if(null == lists || lists.size() < 1)             //������ϴ���
			return "";                                    //����""
		StringBuffer sb = new StringBuffer();
		for(String str : lists){
			sb.append( str+";;;" );
		}
		return sb.toString();
	}
	
	
	/**
	 * �ϲ�Cookie������Map�����ݽṹ��ȥ���ظ���Cookie���ع��󣬷���Cookie�ַ�
	 * 
	 * @param olds  ��ǰ��Cookie�ַ���
	 * @param news  ���λ�õ�Cookie����
	 * @return  �ϲ����Cookie
	 */
	public static String mergeCookies(String cookie, List<String> news){
		System.out.println("HttpUtil��mergeCookies����������cookie[" + cookie + "] | news[" + news.toString() + "]");
		if( null == news)   //���û���µ�Cookie����ʹ��֮ǰ��Cookie
			return cookie;
		
		String[] vs = cookie.split(";");                 //��ȡ��cookie���õ�ֵ
		StringBuffer sb = new StringBuffer();
		Map<String,String> map  = new HashMap<String,String>();
		for(String key : vs ){
			map.put(key.replaceFirst("=.*$", ""), key);
		}
		
		for(String key : news ){
			map.put(key.replaceFirst("=.*$", ""), key);
		}
		
		for(String key : map.keySet()){
			sb.append(map.get(key)+";" );
		}
		return sb.toString();
	}

	/**
	 * ��ȡHttpCookie[],
	 * ͨ������һ��Cookie��ʽ���ַ�������ʽ��liyang=023053,0we=gdxg
	 * 
	 * @param cookieStr  һ��Cookie�ַ���
	 * @see {@link HttpCookie};
	 */
	public HttpCookie[] addResponceConversion(String cookieStr){
		if( cookieStr == null || cookieStr.isEmpty() ){        //����Ҫ������CookieΪ��
			return null;                                       //�򷵻�null
		}
//		if( cookieStr.indexOf(",") < 0)                        //�����ַ��Ƿ���һ��Cookie
//			return;
		String[] str = cookieStr.split(",");                   //�ԣ��ָ�ÿһ��Cookie
		HttpCookie[] httpCookie = new HttpCookie[str.length];  //����HttpCookie

		for(int j=0; j<str.length; j++){                       //�ֽ�ÿһ��Cookie {for}
			int i = -1;                                        //�趨Ѱ�ҷָ��λ��
			if( (i = str[j].indexOf("=")) > 0 && i < j ){      //����ҵ��ָ��ߵ�λ��
				String key = str[j].substring( 0 , i );        //��ȡ"="��ǰ�벿�֣�key
				String value = null;                           //����ֵ����
				if( str[j].indexOf(";") > 0){                  //����ҵ�";"
					value = str[j].substring( i+1 , str[j].indexOf(";") );    //����;��ȡֵ��value
				}else{                                                        //����
					value = str[j].substring( i+1 , str[j].length() );        //ֱ�ӻ�ȡֵ��value
				}
				httpCookie[j] = new HttpCookie(key, value);                   //���ɶ��󣬼���Cookie��
			}
		}
		return httpCookie;
	}
	
	/**
	 * ͨ��HttpCookie[]��
	 * ��ȡ Cookie �ַ�������ʽ��liyang=023053,0we=gdxg,
	 * 
	 * @return Cookieƴ�ӳɵ��ַ���
	 * @see {@link HttpCookie}
	 */
	public String getCookieString(HttpCookie[] httpCookies){
		if( null == httpCookies || httpCookies.length < 1)                     //����ö���û��Cookie
			return "";                                                         //����""
		StringBuffer sb = new StringBuffer();                                  //����ƴ��Cookie�ַ���
		for( HttpCookie httpCookie : httpCookies )                             //����ÿһ��Cookie
			sb.append( httpCookie.getName()+"="+httpCookie.getValue()+",");    //ƴ��Cookie�ַ���
		return sb.deleteCharAt(sb.length()-1).toString();                      //ȥ��ĩβ","������Cookie�ַ���
	}
	
	
	/**
	 * ΪGET����Ĳ�������ת��ΪHTML��ʽ
	 * <ul>
	 * 	<li>1.��û��?,�򷵻ص�ǰ��ַ</li>
	 * 	<li>2.����?���򷵻�ת����URL��ַ</li>
	 * </ul>
	 * @param strUrl ��Ҫת���URL
	 * @return ת����URL
	 */
	public static String urlEncoder(String strUrl){
		int place = strUrl.indexOf("?");              //Ѱ����Ҫ����Ĳ�������
		if( place < 1)                                //���û�в���
			return strUrl;                            //���ص�ǰ�ַ�
		else{
			try {
				return strUrl.replaceFirst("\\?.*$" , "?"+URLEncoder.encode( strUrl.replaceFirst("^.*?\\?", ""), "UTF-8") );
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return "";
	}
	/**
	 * Ϊ�ַ�ת��ΪHTML��ʽ
	 * 
	 * @param str  ��Ҫת����ַ�
	 * @return  ת�����ַ�
	 */
	public static String stringEncoder(String str){
		try {
			return URLEncoder.encode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * ΪHTML��ʽ����
	 * 
	 * @param str HTML��ʽ�ַ���
	 * @return �������ַ�
	 */
	public static String stringDecoder(String str){
		try {
			return URLDecoder.decode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	
	/**
	 * ��Unicode��ʽ���ַ���ת��Ϊ������ʾ
	 * 
	 * @param unicode �磺\u5230\u592a\n\u9891\u7e41
	 * @return ������������ַ���
	 * 
	 * @exception e {link IllegalArgumentException},�ڼ�⵽"\\u"ʱ��,ϵͳ��Ϊ���4���ַ�ΪUnicode�룬��������ָ�ʽ����
	 */
	public static String formatUnicode(String unicode){
		int len = unicode.length();
		char aChar;
		StringBuffer sb = new StringBuffer();
		
		for(int i=0; i<len; ){                          //ѭ��ÿһ���ַ�
			aChar = unicode.charAt( i++ );              //��ȡһ���ַ�
			if(aChar == '\\'){                          //�����ַ���\
				aChar = unicode.charAt( i++ );          //��ȡ��һ���ַ�
				if(aChar == 'u'){                       //�����ַ���u�����unicodeת���߼�
					int value=0;
					for(int j=0; j<4; j++){             //ѭ�����������ַ�
						aChar = unicode.charAt( i++ );  //��ȡ��һ���ַ�
						if( aChar <= '9' && aChar >= '0'){          //����0-9
							value = (value<<4) + aChar -'0';        //��ȡ��һ���ַ���λ���10������  + ��ǰ�ַ���ASCLL��
						}else if( aChar <= 'F' && aChar >= 'A'){    //
							value = (value<<4) + aChar -'A' + 10;   //16������ת��Ϊ10������Ҫ+10+ƫ����
						}else if( aChar <= 'f' && aChar >= 'a'){
							value = (value<<4) + aChar -'a' + 10;   //16������ת��Ϊ10������Ҫ+10+ƫ����
						}else{
							throw new IllegalArgumentException("it's not unicode coding , need like (\\u7e41\\u4e86\\u70b9) ");
						}
					}
					sb.append( (char)value );
				}else{
					if(aChar == 't')
						aChar ='\t';
					else if(aChar == 'r')
						aChar = '\r';
					else if(aChar == 'n')
						aChar = '\n';
					else if(aChar == 'f')
						aChar = '\f';
					sb.append( aChar );
				}
			}else{
				sb.append( aChar );
			}
		}
		return sb.toString();
	}
	
}
