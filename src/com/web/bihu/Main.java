package com.web.bihu;

import com.web.bihu.entity.Bihu;

/**
 * 程序的入口
 * @name        类名
 * @author      Wolkin
 * @createtTime 2018-03-21
 * @version     1.0
 * @since       1.0
 *
 */

public class Main {
	public static void main(String[] args) {
		
		Bihu bihu = new Bihu("13362889929","ma5870520557le");
		bihu.login();                            //登录
		System.out.println( bihu.getCookie() );  //获取登录Cookie
		bihu.praise("7b6eb61a5cd674523b910000"); //说说赞
	}
}
