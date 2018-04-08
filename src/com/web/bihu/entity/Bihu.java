package com.web.bihu.entity;

import java.util.Random;
import java.util.Scanner;

import com.web.bihu.util.RegexUtil;
import com.web.bihu.util.Security;
import com.web.bihu.util.Util;

/**
 *
 * Bihuʵ��ģ��
 * ���ڵ�¼��������Bihu�û���Ϣ
 *
 * @name         Bihu����
 * @author       Wolkin
 * @createtTime  2018-03-21
 * @version      1.0
 * @since        1.0
 *
 */
public class Bihu extends HttpService{
	/** Bihu�ʺ� **/
	private String uin;
	
	/** Bihu���� **/
	private String password;
	
	/** QQ-ID **/
	private String aid;
	/** ��¼��֤�ַ��� **/
	private String login_sig;
	/** ��¼״̬ 0=��¼�ɹ�������ʧ��**/
	private int status;
	/** ��֤�� **/
	private String verify;
	/** ����� **/
	private String random;
	/** QQ�ռ������G_TK**/
	protected String g_tk;
	
	/**
	 * �չ��캯��
	 */
	public Bihu(){}
	
	/**
	 * ��ʼ���˺ź����빹�캯��
	 * @param uin
	 * @param password
	 */
	public Bihu(String uin,String password){
		this.uin = uin;
		this.password = password;
	}
	
	/**
	 * Bihu��¼
	 * 
	 * ǰ����Ҫ����
	 *      1. uin       Bihu�˺�
	 *      2. password  Bihu����
	 *      
	 * ���ޣ�
	 *      ��ʱ��֧��������֤���¼
	 * 
	 * 1.�����¼ҳ��
	 * 2.�ڸ�ҳ���ȡ     1.aid 2.js_ver 3.login_sig
	 * 3.��ȡ��¼��֤��Ϣ
	 * 4.��¼
	 * 
	 * @return �Ƿ��¼�ɹ�  ��¼�ɹ�=0  ����=-1
	 * 
	 * @see util.RegexUtil    ������ҳ�ַ���
	 * @see util.Util         ��������ַ�������
	 * @see util.Security     QQ�������
	 * 
	 */
	public int login(){
        //1.�����¼ҳ��,��ȡCookie
        //https://www.bihu.com/login
		String $a = "https://www.bihu.com/login";
		String $v = this.requestGet($a);
		String content = $v;
		
		System.out.println("$v1 : " + $v);
		
		//2.�ڸ�ҳ���ȡ  1.aid 2.js_ver 3.login_sig
        $v = RegexUtil.replaceStartEnd($v, "login_sig", "clientip");
        $v = RegexUtil.replaceStartEnd($v, "\"", "\"");
        System.out.println("$v2 : " + $v);
        
        String $login_sig = $v;
        
        $v = content;
        $v = RegexUtil.replaceStartEnd($v, "appid:", "lang");
        $v = RegexUtil.replaceStartEnd($v, "\"", "\"");
        String $aid = $v;
        String $uin = this.uin;
        String $password = this.password;
        System.out.println($login_sig + "-" + $aid + "-" + $uin + "-" + $password);
        
        //3.��ȡ��¼��֤��Ϣ
        String $url = "https://www.bihu.com?regmaster=&uin={0}&appid={1}&js_ver={2}&js_type=1&login_sig={3}&u1=http%3A%2F%2Fqzs.qq.com%2Fqzone%2Fv5%2Floginsucc.html%3Fpara%3Dizone&r={4}";
        $url = Util.fillString($url, $uin, $aid,"10051",$login_sig,new Random().nextDouble()+"");
        System.out.println("$url : " + $url);
        
        $v = this.requestGet($url);
        String $status = RegexUtil.replaceStartEnd($v, "'", "'");
        String $verify = RegexUtil.replaceStartEnd($v, ",'", "'");
        String $hexqq = RegexUtil.replaceStartEnd($v, ",'\\\\x", "'");
        
		//�������֤�룬һ��Ҫ���������ǰ�㶨Ӵ
		if($verify.length()>5){
			System.out.println("�����"+Util.root+"\\verifyTemp\\verify.jpg"+"�鿴��֤�룬���ڿ���̨������֤����س�");
        	$url = "http://captcha.qq.com/getimage?uin={0}&aid={1}&{2}";
        	$url = Util.fillString($url, $uin, $aid, new Random().nextDouble()+"");
        	this.requestDownload($url, Util.root+"\\verifyTemp\\verify.jpg");
        	//�����ͣ�����̨������֤�룬ͻȻ�о��Լ���ˮ����������˵���������ʹ��B/S�ܹ������ﻹҪ�ع�������ðɡ�����
    		Scanner scanner = new Scanner(System.in);
    		$verify = scanner.nextLine();
        }
        //��ȡ���ܺ������
		$password = Security.GetPassword($hexqq,$password,$verify);
        
        //4.��¼
        //URL=http://ptlogin2.qq.com/login?u=8888888&p=3E8CDBE584C125C4A0E31CB3A273FA20&verifycode=zkyy&aid=549000912&u1=http%3A%2F%2Fqzs.qq.com%2Fqzone%2Fv5%2Floginsucc.html%3Fpara%3Dizone&h=1&ptredirect=0&ptlang=2052&from_ui=1&dumy=&low_login_enable=0&regmaster=&fp=loginerroralert&action=23-61-1383187338922&mibao_css=&t=1&g=1&js_ver=10051&js_type=1&login_sig=nO84d8jFFX2BsoUJjCz2Or3qHRlCB6DsLq5r*eLHFZ3yfd5lqugnE9H4d6xkEMWI&pt_rsa=0
        $url = "http://ptlogin2.qq.com/login?u={0}&p={1}&verifycode={2}&aid={3}&u1=http%3A%2F%2Fqzs.qq.com%2Fqzone%2Fv5%2Floginsucc.html%3Fpara%3Dizone&h=1&ptredirect=0&ptlang=2052&from_ui=1&dumy=&low_login_enable=0&regmaster=&fp=loginerroralert&action=10-33-1383187964077&mibao_css=&t=1&g=1&js_ver={4}&js_type=1&login_sig={5}&pt_rsa=0";
        $url = Util.fillString($url, $uin, $password, $verify, $aid, "10051", $login_sig);
    	$v = this.requestGet($url);
    	
    	$status = RegexUtil.replaceStartEnd($v, "'", "'");
    	System.out.println($v);//��ӡ�����Ƿ�㶨��
    	
    	//5.����QQ��Cookie��ֻ��ȡ�ؼ���ҪCookie
		String cookie = this.cookie;
		$v = "uin=o0"+this.uin+"; "+("skey="+RegexUtil.replaceStartEnd(cookie, "skey=", ";")+"; ptcz="+RegexUtil.replaceStartEnd(cookie, "ptcz=", ";")+";");
		this.setCookie($v);
    	
		//����QQ�ռ��g_tk
		this.g_tk = Security.GetG_TK( RegexUtil.replaceStartEnd(this.getCookie(), "skey=", ";") );
		
		//6.���õ�¼״̬
		if("0".equals($status))
    		this.status = 0;
    	else
    		this.status = -1;
		
		return this.status;
	}
	
	/**
	 * ͨ��˵˵ID
	 * ��һ��˵˵
	 * 
	 * 1.��Ҫ��¼״̬ g_tk
	 * 2.����1��http����
	 * 
	 * @param curkey ˵˵ID  example:7b6eb61aa5616f52da7c0b00
	 * 
	 */
	public void praise(String curkey){
		if(this.status != 0) return;
		String $url = "http://w.qzone.qq.com/cgi-bin/likes/internal_dolike_app?g_tk="+this.g_tk;
		String $data = "fid={0}&opuin={1}&abstime=1383191804&active=0&appid=311&curkey=http://user.qzone.qq.com/448163451/mood/{0}&qzreferrer=http://user.qzone.qq.com/{1}&typeid=0&unikey=http://user.qzone.qq.com/448163451/mood/{0}";
		
		$data = Util.fillString($data, curkey, this.getUin());
		this.requestPost($url, $data);
	}
	
	public String getUin() {
		return uin;
	}

	public void setUin(String uin) {
		this.uin = uin;
	}


	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAid() {
		return aid;
	}

	public void setAid(String aid) {
		this.aid = aid;
	}

	public String getLogin_sig() {
		return login_sig;
	}

	public void setLogin_sig(String login_sig) {
		this.login_sig = login_sig;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getVerify() {
		return verify;
	}

	public void setVerify(String verify) {
		this.verify = verify;
	}

	public String getRandom() {
		return random;
	}

	public void setRandom(String random) {
		this.random = random;
	}

	public String getCookie() {
		return cookie;
	}

	public void setCookie(String cookie) {
		this.cookie = cookie;
	}

	public String getG_tk() {
		return g_tk;
	}

	public void setG_tk(String g_tk) {
		this.g_tk = g_tk;
	}
}
