package com.web.bihu;

import com.web.bihu.entity.Bihu;

/**
 * ��������
 * @name        ����
 * @author      Wolkin
 * @createtTime 2018-03-21
 * @version     1.0
 * @since       1.0
 *
 */

public class Main {
	public static void main(String[] args) {
		
		Bihu bihu = new Bihu("13362889929","ma5870520557le");
		bihu.login();                            //��¼
		System.out.println( bihu.getCookie() );  //��ȡ��¼Cookie
		bihu.praise("7b6eb61a5cd674523b910000"); //˵˵��
	}
}
