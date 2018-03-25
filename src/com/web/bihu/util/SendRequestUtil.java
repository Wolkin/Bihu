package com.web.bihu.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

import com.web.bihu.interfaced.SetHttpConnection;

/**
 * <p>
 * �������󹤾���
 * </p>
 * 
 * @since 1.0
 * @version 1.0 (2018-03-23)
 * @author Wolkin
 * @see #downloadHttpPage ����һ����ҳҳ��
 */
public class SendRequestUtil {
	/** ��֤��Ĵ����� */
	private static X509TrustManager x509TrustManager = new myX509TrustManager();
	
	/** ��֤�������ͷ�������֤�����Ƿ�ƥ�� */
	private static HostnameVerifier hostnameVerifier = new myHostnameVerifier();
	
	
	public static void main(String[] args) throws InterruptedException {
		
		//��������Ϊtrue
		
//		String content = SendRequestUtil.doRequestProxy("http://www.lyoxh.com:8079/test/test01" , null , new Proxy(Proxy.Type.HTTP, new InetSocketAddress("111.1.36.166", 80)) );
//		
//		//String content = SendRequestUtil.doRequest("http://www.lyoxh.com:8079/test/test01" , "" );
//		
//		System.out.println(content.replaceAll("<?.*>", ""));
//		
//		
//		Thread.sleep(2000000);
		
		
		
		//���ذٶ����ɱ���ͼƬ
//		String outPath = "C:\\Users\\Administrator\\Desktop\\baidu";
//		for(int i=0; i<1000; i++){
//			for(int j=1; j<=2; j++){
//				String name = "bg_%1_wrap%2.jpg".replace("%1", i+"").replace("%2", j+"");
//				String url = "http://static.tieba.baidu.com/tb/cms/frs/bg/"+name;
//				SendRequestUtil.downloadHttpPage(url, outPath+File.separatorChar+name, "");
//				Thread.sleep(200);
//			}
//		}
		
		//����QQ�ռ䱳��ͼƬ
		//http://ctc.i.gtimg.cn/qzone/space_item/orig/11
		//http://ctc.i.gtimg.cn/qzone/space_item/pre/7/61703_1.gif ǰ��Ԥ��ͼƬ
		//http://ctc.i.gtimg.cn/qzone/space_item/orig/7/90775.swf //����
		//12  24200
		
		//�ȵ�QQ������
//		http://ctc.qzonestyle.gtimg.cn/qzone/em/e4018.gif
//		//����QQ������
//		http://ctc.qzonestyle.gtimg.cn/qzone/em/e100.gif
	}
	
	/**
	 * Ĭ�ϵİ�ȫ��֤
	 */
	static {
		SSLContext sslContext = null;
		try {
			sslContext = SSLContext.getInstance("TLS"); // ��SSL
			X509TrustManager[] xtmArray = new X509TrustManager[]{x509TrustManager};
			sslContext.init(null, xtmArray, new java.security.SecureRandom());
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}
		if (sslContext != null) {
			HttpsURLConnection.setDefaultSSLSocketFactory(sslContext
					.getSocketFactory());
		}
		HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifier);
	}

	/**
	 * ��֤��ķ�����Ĭ��Ϊ�� {@link X509TrustManager}
	 **/
	static class myX509TrustManager implements X509TrustManager {

		@Override
		public void checkClientTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
			// ���ͻ�������
		}

		@Override
		public void checkServerTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
			// ������������
		}

		@Override
		public X509Certificate[] getAcceptedIssuers() {
			// �õ�����ISS
			return null;
		}

	}
	/**
	 * ��֤�������ͷ������Ƿ�ƥ��ķ�����Ĭ��Ϊ�� {@link HostnameVerifier}
	 **/
	static class myHostnameVerifier implements HostnameVerifier {
		@Override
		public boolean verify(String hostname, SSLSession session) {
//			System.out.println("Warning: URL Host: " + hostname + " vs. "
//					+ session.getPeerHost());
			return true;
		}
	}
	
	
	/**
	 * 
	 * @param urlStr    �����ַ
	 * @param type      �������� "post" "get",��û����Ĭ��get
	 * @param sc        �����������Ҫ��������ǰ�������Ĳ�����{@link SetHttpConnection}
	 * @param params    ��������Ĳ���
	 * @param cookie    ���������Cookie
	 * @param Proxy     {@link Proxy ����}
	 * @param userAgent ʹ�������
	 * 
	 * @return ��ȡ�������ҳ�ַ���
	 */
	public static String doRequest(String urlStr, String type, SetHttpConnection sc, String params, String cookie, Proxy proxy, String userAgent) {
		// �����index.jsp��<servlet-mapping>ӳ�䵽
		// һ��Servlet(com.quantanetwork.getClientDataServlet)
		// ��Servlet��ע����±߻��ᵽ
		URL url = null;
		URLConnection urlConnection = null;
		HttpURLConnection httpUrlConnection =null;
		OutputStream outStrm = null;
		BufferedReader br = null;
		//��������û��ָ������ʹ��Ĭ�ϵ������
		userAgent = ((userAgent != null && !userAgent.equals("")) ? userAgent : SendRequestUtil.userAgent);
		
		try {
			url = new URL(urlStr);
			
			//�����Ҫ����
			if( null != proxy)
				urlConnection = url.openConnection(proxy);
			else
				urlConnection = url.openConnection();
			
			if(urlStr.toLowerCase().indexOf("https") == 0){        //�����Https���󣬸���httpss=https
				httpUrlConnection = (HttpsURLConnection) urlConnection;
			}else                                                  //�����Http����
				httpUrlConnection = (HttpURLConnection) urlConnection;
				

			// �����Ƿ���httpUrlConnection�������Ϊ�����post���󣬲���Ҫ����
			// http�����ڣ������Ҫ��Ϊtrue, Ĭ���������false;
			//�����Post����
			if(type.equalsIgnoreCase("post"))
				httpUrlConnection.setDoOutput(true);

			// �����Ƿ��httpUrlConnection���룬Ĭ���������true;
			httpUrlConnection.setDoInput(true);

			// ������ʹ�û���
			httpUrlConnection.setUseCaches(false);

			// �趨���͵����������ǿ����л���java����
			// (����������,�ڴ������л�����ʱ,��WEB����Ĭ�ϵĲ�����������ʱ������java.io.EOFException)

			//�����Post����
			if(type.equalsIgnoreCase("post")){
				httpUrlConnection.setRequestProperty("Content-type","application/x-www-form-urlencoded");
				httpUrlConnection.setRequestProperty("Content-Length","1024");
			}
			
			//�����Cookie
			if(null != cookie)
				httpUrlConnection.setRequestProperty("Cookie",cookie);
			if (null != sc) {
				sc.before(httpUrlConnection);
			}
			
			httpUrlConnection.setRequestProperty("Accept-Charset","utf-8, gbk,* ;q=0.1"); // ��������ͷ
			httpUrlConnection.setRequestProperty("User-Agent",userAgent); // ��������ͷ
			httpUrlConnection.setRequestProperty("accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			httpUrlConnection.setRequestProperty("accept-language","zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
			httpUrlConnection.setRequestProperty("Connection", "keep-alive");
			
			//�趨����ķ���Ϊ"POST"��Ĭ����GET
			//�����Post����
			if(type.equalsIgnoreCase("post")){
				httpUrlConnection.setRequestMethod("POST");
				// ���ӣ���������2����url.openConnection()���˵����ñ���Ҫ��connect֮ǰ��ɣ�
				// httpUrlConnection.connect();
	
				// �˴�getOutputStream�������Ľ���connect(������ͬ���������connect()������
				// �����ڿ����в�����������connect()Ҳ����)��
				if (params != null && params.indexOf("=") > 0) {
					outStrm = httpUrlConnection.getOutputStream();
	
					// ����ͨ����������󹹽����������������ʵ����������л��Ķ���.
					outStrm.write(params.getBytes("UTF-8"));
	
					outStrm.flush();
					
					outStrm.close();
				}
			}
			
			try{
				if( httpUrlConnection.getResponseCode()==404
						|| httpUrlConnection.getResponseCode()==403){
					return null;
				}
			}catch(Exception e){
				System.out.println(e.getMessage());
				return null;
			}
			// ObjectOutputStream objOutputStrm = new
			// ObjectOutputStream(outStrm);
			//
			// // ����������д�����ݣ���Щ���ݽ��浽�ڴ滺������
			// objOutputStrm.writeObject(new String("���ǲ�������"));
			//
			// // ˢ�¶�������������κ��ֽڶ�д��Ǳ�ڵ����У�Щ��ΪObjectOutputStream��
			// objOutputStrm.flush();
			//
			// // �ر������󡣴�ʱ������������������д���κ����ݣ���ǰд������ݴ������ڴ滺������,
			// // �ڵ����±ߵ�getInputStream()����ʱ�Ű�׼���õ�http������ʽ���͵�������
			// objOutputStrm.close();

			// ����HttpURLConnection���Ӷ����getInputStream()����,
			// ���ڴ滺�����з�װ�õ�������HTTP������ķ��͵�����ˡ�
			// <===ע�⣬ʵ�ʷ�������Ĵ���ξ�������
			br = new BufferedReader(new InputStreamReader(httpUrlConnection.getInputStream(),"UTF8"));

			// �ϱߵ�httpConn.getInputStream()�����ѵ���,����HTTP�����ѽ���,�±�����������������������壬
			// ��ʹ���������û�е���close()�������±ߵĲ���Ҳ��������������д���κ�����.
			// ��ˣ�Ҫ���·�������ʱ��Ҫ���´������ӡ���������������´�������������д���ݡ�
			// ���·�������(�����Ƿ���������Щ������Ҫ���о�)
			StringBuffer sb = new StringBuffer();
			String lineStr = "";
			while ((lineStr = br.readLine()) != null) { // ��ȡ��Ϣ
				sb.append(lineStr + "\r\n");
			}
			if (null != sc) {
				sc.after(httpUrlConnection);
			}
			return sb.toString();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
				try {
					if(null != br)
						br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return null;
	}

	/**
	 * ʹ�� {@link HttpURLConnection} ����һ��http����������
	 * 
	 * @param urlStr    �����ַ
	 * @param outPath   ���صĶ�������·�������Դ���׺
	 * @param sc        �����������Ҫ��������ǰ�������Ĳ�����{@link SetHttpConnection}
	 * @param cookie    ���������Cookie
	 * @param Proxy     {@link Proxy ����}
	 * @param userAgent ʹ���������User-agent
	 */
	public static void httpDownload(String urlStr, String outPath, SetHttpConnection sc, String cookie, Proxy proxy, String userAgent) {
		int chByte = 0;

		URL url = null; // ���ӵ�ַ

		HttpURLConnection httpConn = null; // ���Ӷ���

		InputStream in = null; // ��ȡ��ҳ����

		FileOutputStream out = null; // ������ҳ�ļ�����

		URLConnection rulConnection = null; //
		
		//��������û��ָ������ʹ��Ĭ�ϵ������
		userAgent = userAgent!=null && !userAgent.equals("")?userAgent:SendRequestUtil.userAgent;
		
		try {

			url = new URL(urlStr);

			//�������Ҫ����
			if( null != proxy)
				rulConnection = url.openConnection(proxy);
			else
				rulConnection = url.openConnection();

			if(urlStr.toLowerCase().indexOf("https") == 0){
				httpConn = (HttpsURLConnection) rulConnection;
			}else                                                  //�����Http����
				httpConn = (HttpURLConnection) rulConnection;

			HttpURLConnection.setFollowRedirects(true); // ����Ϊ�ض���

			httpConn.setRequestMethod("GET"); // �������󷽷�

			httpConn.setRequestProperty("User-Agent", userAgent); // ����������

			httpConn.setRequestProperty("accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");

			httpConn.setRequestProperty("accept-language","zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");

			httpConn.setRequestProperty("Cookie", cookie);
			
			httpConn.setRequestProperty("connection", "keep-alive");
			//��������ǰ
			if (null != sc) {
				sc.before(httpConn);
			}
			
			if( httpConn.getResponseCode()==404 )
				return;
			
			in = httpConn.getInputStream(); // ��������
			out = new FileOutputStream(new File(outPath)); // ������������ļ�

			chByte = in.read(); // ��ȡ��һ���ֽ�

			while (chByte != -1) { // �������һ���ֽ�

				out.write(chByte); // �������Լ����ļ�

				chByte = in.read(); // ��ȡ��һ���ֽ�

			}

			//���������
			if (null != sc) {
				sc.after(httpConn);
			}
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

			try {
				if(out != null){
					out.flush();
					out.close(); // �ر������
				}
				if(in != null)
					in.close(); // �ر�������
				if(httpConn != null)
					httpConn.disconnect(); // �ر�����
			} catch (Exception ex) {

				ex.printStackTrace();

			}

		}
	}
	
	
	
	
	/** �����������ʶ��httpЭ���е�User-Agent */
	//Mozilla/5.0 (Linux; U; Android 0.5; en-us) AppleWebKit/522+ (KHTML, like Gecko) Safari/419.3
	//Mozilla/5.0 (Windows NT 6.1; WOW64; rv:18.0) Gecko/20100101 Firefox/18.0
	private static String userAgent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36 QIHU 360SE";
//	private static String userAgent = "Mozilla/5.0 (Linux; U; Android 0.5; en-us) AppleWebKit/522+ (KHTML, like Gecko) Safari/419.3";
	/** 0�ǵ��ԣ�1���ֻ� */
	public static int state = 1;

}
