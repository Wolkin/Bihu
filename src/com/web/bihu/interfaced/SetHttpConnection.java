package com.web.bihu.interfaced;

import java.net.HttpURLConnection;
import java.net.ProtocolException;

/**
 * <p>�ṩ������ǰ��������������Ľ� </p>
 * @category  ����
 * @version   1.0     (2018-03-23)
 * @since     1.0
 * @author    Wolkin
 *
 */

public interface SetHttpConnection {
	/* 
	 * ���÷�������ǰ�Ĵ������󷽷� 
	 * @throws ProtocolException
	 */
	public String before(HttpURLConnection httpConn) throws ProtocolException;
	
	/*
	 * ���÷��ͺ����������
	 */
	public String after(HttpURLConnection httpConn);
}
