package com.web.bihu.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

/**
 * ��ȫ��
 *    QQ�ռ��M5D����
 * 
 * MD5���ܲ�����д�ģ�Ĥ���°�������ͨ��JS��������
 *
 * @name        ����
 * @author                  �������     liyangchiyue@gmail.com
 * @createtTime 2013-10-31  ����3:47:57
 * @version     1.0
 * @since       1.0
 *
 */
public class Security {
	
public static final String HEXSTRING = "0123456789ABCDEF";

    public static String md5(String originalText) throws Exception {
        byte buf[] = originalText.getBytes("ISO-8859-1");
        StringBuffer hexString = new StringBuffer();
        String result = "";
        String digit = "";

        try {
            MessageDigest algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(buf);

            byte[] digest = algorithm.digest();

            for (int i = 0; i < digest.length; i++) {
                digit = Integer.toHexString(0xFF & digest[i]);

                if (digit.length() == 1) {
                    digit = "0" + digit;
                }

                hexString.append(digit);
            }

            result = hexString.toString();
        } catch (Exception ex) {
            result = "";
        }

        return result.toUpperCase();
    }

    public static String hexchar2bin(String md5str) throws UnsupportedEncodingException
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(md5str.length() / 2);

        for (int i = 0; i < md5str.length(); i = i + 2)
        {
            baos.write((HEXSTRING.indexOf(md5str.charAt(i)) << 4 |
                    HEXSTRING.indexOf(md5str.charAt(i + 1))));
        }

        return new String(baos.toByteArray(), "ISO-8859-1");
    }

    /**
     * 
     * ��ȡQQ�ռ���� ����
     *
     * @param qq http://check.ptlogin2.qq.com/check?uin={0}&appid=15000101&r={1} �������js���صĵ���������
     * @param password   QQ����
     * @param verifycode ��֤��
     * @return ���ܺ������
     * @throws UnsupportedEncodingException
     * @throws Exception
     */
    public static String GetPassword(String qq,String password, String verifycode){
    	String V = "";
    	try{
	        String P = hexchar2bin(md5(password));
	        String U = md5(P + hexchar2bin(qq.replace("\\x", "").toUpperCase()));
	        V = md5(U + verifycode.toUpperCase());
		} catch (Exception e) {
			e.printStackTrace();
			return "����ʱ��С�ľ�ʧ���ˣ��������Ա�޸���";
		}
        return V;
    }

    /**
     * QQ�ռ�ÿ�η�������������ַ�
     * ��ȡG_TK
     * 
     * @param str QQ��¼��Cookie�е�skey   example @kkfsfafas
     * @return    G_TK�ַ���
     */
    public static String GetG_TK(String str){
    	   int hash = 5381;
    	   for(int i = 0, len = str.length(); i < len; ++i){
    		   hash += (hash << 5) + (int)(char)str.charAt(i);
    	   }
    	   return (hash & 0x7fffffff)+"";
    }
    
}