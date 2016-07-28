package com.fyfeng.httpstest;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.Socket;
import java.net.URI;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.UUID;

import javax.net.SocketFactory;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509KeyManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.graphics.MaskFilter;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity {

	private static final String TAG = MainActivity.class.getSimpleName();






	//	public static InputStream sendPOSTRequestForInputStream(String path, Map<String, String> params, String encoding) throws Exception{
	//		// 1> ��ƴʵ������
	//		//method=save&name=liming&timelength=100
	//		StringBuilder entityBuilder = new StringBuilder("");
	//		if(params!=null && !params.isEmpty()){
	//			for(Map.Entry<String, String> entry : params.entrySet()){
	//				entityBuilder.append(entry.getKey()).append('=');
	//				entityBuilder.append(URLEncoder.encode(entry.getValue(), encoding));
	//				entityBuilder.append('&');
	//			}
	//			entityBuilder.deleteCharAt(entityBuilder.length()-1);
	//		}




	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);


		View button = this.findViewById(R.id.button);


		button.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				onclick3();
			}});
	}



	private static byte[] entity = "abc".getBytes();



	private void onclick(){

		
		
		
		new Thread(new Runnable(){

			@Override
			public void run() {
				try{
					
					
					
//					CertificateFactory cf = CertificateFactory.getInstance("X.509");  
//					InputStream in = getAssets().open("ca.crt");  
//					Certificate ca = cf.generateCertificate(in);  
//
//					KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());  
//					keystore.load(null, null);  
//					keystore.setCertificateEntry("ca", ca);  
					
					
//					KeyStore keyStore = KeyStore.getInstance("PKCS12","BC");
					KeyStore keyStore = KeyStore.getInstance("BKS");
//					InputStream ksIn = MainActivity.this.getResources().getAssets().open("client.p12");
					InputStream ksIn = MainActivity.this.getResources().getAssets().open("client.bks");
					Log.i("123", "get ksin");
					try {
						keyStore.load(ksIn, "123456".toCharArray());
					} catch (Exception e) {
						Log.e("123", "keystore load error");
						e.printStackTrace();
					}
				//	ksIn.close();
//					String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();  
//					TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);  
//					tmf.init(keystore);  
					
					
					String kmfAlgorithm = KeyManagerFactory.getDefaultAlgorithm();
					KeyManagerFactory kmf = KeyManagerFactory.getInstance(kmfAlgorithm);
					kmf.init(keyStore, "123456".toCharArray());
					
					
				
					String str = "https://192.168.128.134:8000";
//					str = "https://www.haoweis.com:8861/HomeService/HomeMCUService.svc?wsdl";
					
					str = "https://192.168.18.245:8862";
//					str = "https://www.oschina.net/home/login?goto_page=http%3A%2F%2Fwww.oschina.net%2F";
					URL url = new URL(str);

				
					
					SSLContext sslctxt = SSLContext.getInstance("TLS");
//					SSLContext sslctxt = SSLContext.getInstance("SSL");
					
					
//					sslctxt.init(kmf.getKeyManagers(), new TrustManager[]{new MyX509TrustManager()}, new java.security.SecureRandom());
					sslctxt.init(kmf.getKeyManagers(), new TrustManager[]{new MyX509TrustManager()},null);
//					sslctxt.init(null, new TrustManager[]{new MyX509TrustManager()}, new java.security.SecureRandom());
//					sslctxt.init(null, tmf.getTrustManagers(), new java.security.SecureRandom());

					HttpsURLConnection conn = (HttpsURLConnection)url.openConnection();
				
				
					
					
					conn.setSSLSocketFactory(sslctxt.getSocketFactory());
					conn.setHostnameVerifier(new MyHostnameVerifier());

					conn.setConnectTimeout(5*1000);
					conn.setRequestMethod("POST");
//					conn.setRequestMethod("GET");
					conn.setDoOutput(true);
				
					conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
					conn.setRequestProperty("Content-Length", String.valueOf(entity.length));
					OutputStream outStream = conn.getOutputStream();
					//					DataOutputStream dataOutputStream = (DataOutputStream)conn.getOutputStream();
					
					
//					if (Build.VERSION.SDK != null
//							&& Build.VERSION.SDK_INT > 13) {
//						conn.setRequestProperty("Connection", "close");
//							}
					
					
					conn.connect();

			
					
					
					//					dataOutputStream.writeBytes("abc");
					//					dataOutputStream.flush();
					//					dataOutputStream.close();
					outStream.write(entity);
					outStream.flush();
					outStream.close();

					Log.i("123", "outstream close");

					
					
					
					int respCode = conn.getResponseCode();
					Log.i("log123", "ResponseCode="+respCode);
					InputStream input = conn.getInputStream();


					String result = toString(input);

					//Log.d(tag, "result:"+result);
					Log.i("log123", "result = "+result);


					input.close();

					conn.disconnect();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			private String toString(InputStream input){

				String content = null;
				try{
					InputStreamReader ir = new InputStreamReader(input);
					BufferedReader br = new BufferedReader(ir);

					StringBuilder sbuff = new StringBuilder();
					while(null != br){
						String temp = br.readLine();
						if(null == temp)break;
						sbuff.append(temp).append(System.getProperty("line.separator"));
					}

					content = sbuff.toString();
				}catch(Exception e){
					e.printStackTrace();
				}

				return content;
			}	
		}).start();;
	}


	private void onclick1(){
		String url = "https://192.168.18.245:8851";
		String res = requestHTTPSPage(url);
		Log.i("123", "res = "+res);
	}


	private void onclick2(){
		
		
		
		AsyncTask testTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object... params) {
                try {
                	
                	
                    HttpClient httpsClient = HttpClientSslHelper.getSslHttpClient(MainActivity.this.getBaseContext());
                    HttpGet httpget = new HttpGet("192.168.18.245");
                    HttpResponse response = httpsClient.execute(httpget);
                    HttpEntity entity = response.getEntity();
                    Log.e("Response status", response.getStatusLine().toString());
                    if (entity != null) {
                        Log.e("Response", "Response content length: " + entity.getContentLength());
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent()));
                        String text;
                        while ((text = bufferedReader.readLine()) != null) {
                            Log.e("Response status", text);
                        }
                        bufferedReader.close();
                    }
                    httpsClient.getConnectionManager().shutdown();
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
        testTask.execute();
	}

	private void onclick3(){
		
		new Thread(){
			public void run() {
				 try {  
		                SSLContext context;  
		                KeyStore ts = KeyStore.getInstance("BKS");  
		                ts.load(getResources().getAssets().open("client.bks"),  
		                        "123456".toCharArray());  
		                TrustManagerFactory tmf = TrustManagerFactory  
		                        .getInstance("X509");  
		                tmf.init(ts);  
		                TrustManager[] tm = tmf.getTrustManagers();  
		                
		                
		                KeyStore ks = KeyStore.getInstance("BKS");
		                ks.load(getResources().getAssets().open("client.bks"),"123456".toCharArray());
		                
		                KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
		                kmf.init(ks,"123456".toCharArray());
		                
		                context = SSLContext.getInstance("SSL");  
		                context.init(kmf.getKeyManagers(), new TrustManager[]{new MyX509TrustManager()}, null);  
		  
		                
		                
		                SocketFactory factory = context.getSocketFactory();  
		                
		                
		                
		                SSLSocket socket = (SSLSocket) factory.createSocket(  
		                        "192.168.18.245", 8862);  
		  
		                
		                ObjectOutputStream out = new ObjectOutputStream(  
		                        socket.getOutputStream());  
		  
		                out.writeUTF(UUID.randomUUID().toString());  
		                out.flush();  
		                Log.i("123", "========客户端发送成功=========");	
		                
		                socket.close();  
		            } catch (Exception ex) {  
		                ex.printStackTrace();  
		            }  
		           
				
				
			};
		}.start();
		
		
	}
	
	
	


	static class MyX509TrustManager implements X509TrustManager{
		private static  X509Certificate[] _AcceptedIssuers = new X509Certificate[]{};
		@Override
		public void checkClientTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
			if(null != chain){
				for(int k=0; k < chain.length; k++){
					X509Certificate cer = chain[k];
					print(cer);
				}
			}
			Log.i("log123", "check client trusted. authType="+authType);

		}

		@Override
		public void checkServerTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
			if(null != chain){
				Log.i("123", "cer len="+chain.length);
				for(int k=0; k < chain.length; k++){
					X509Certificate cer = chain[k];
					print(cer);
				}
			}

			Log.i("log123", "check servlet trusted. authType="+authType);
		}

		@Override
		public X509Certificate[] getAcceptedIssuers() {//返回受信任的证书组

			Log.i("log123", "get acceptedissuer");

			return _AcceptedIssuers;
		}


		private void print(X509Certificate cer){

			int version = cer.getVersion();
			String sinname = cer.getSigAlgName();
			String type = cer.getType();
			String algorname = cer.getPublicKey().getAlgorithm();
			BigInteger serialnum = cer.getSerialNumber();
			Principal principal = cer.getIssuerDN();
			String principalname = principal.getName();
			
			Log.i("log123", "version="+version+", sinname="+sinname+", type="+type+", algorname="+algorname+", serialnum="+serialnum+", principalname="+principalname);
		
		}

	}

	
	static class MyX509KeyManager implements X509KeyManager{

		@Override
		public String chooseClientAlias(String[] keyType, Principal[] issuers, Socket socket) {
			// TODO Auto-generated method stub
			if(keyType!=null){
				Log.d("123", "keyType len="+keyType.length);
			}
			if(issuers!=null){
				Log.d("123", "issuers len="+issuers.length);
			}else{
				Log.e("123", "issuers = null");
			}
			for(int i=0;i<keyType.length;i++){
				Log.d("123","choose client alias"+keyType[i]);
			}
		
			return null;
		}

		@Override
		public String chooseServerAlias(String keyType, Principal[] issuers, Socket socket) {
			// TODO Auto-generated method stub
			Log.i("123","choose server alias");
			return null;
		}

		@Override
		public X509Certificate[] getCertificateChain(String alias) {
			// TODO Auto-generated method stub
			Log.i("123","get certificate chain");
			return null;
		}

		@Override
		public String[] getClientAliases(String keyType, Principal[] issuers) {
			// TODO Auto-generated method stub
			Log.i("123","getClientAliases");
			return null;
		}

		@Override
		public PrivateKey getPrivateKey(String alias) {
			// TODO Auto-generated method stub
			Log.i("123","getPrivateKey");
			return null;
		}

		@Override
		public String[] getServerAliases(String keyType, Principal[] issuers) {
			// TODO Auto-generated method stub
			Log.i("123","getServerAliases");
			return null;
		}
		
	}
	
	
	
	static class MyHostnameVerifier implements HostnameVerifier{

		@Override
		public boolean verify(String hostname, SSLSession session) {
			Log.i("log123", "hostname="+hostname+",PeerHost= "+session.getPeerHost());
			return true;
		}

	}


	String requestHTTPSPage(String mUrl) {
		InputStream ins = null;
		String result = "";
		try {
			ins = this.getAssets().open("client.crt"); //下载的证书放到项目中的assets目录中
			CertificateFactory cerFactory = CertificateFactory
					.getInstance("X.509");
			Certificate cer = cerFactory.generateCertificate(ins);
			KeyStore keyStore = KeyStore.getInstance("PKCS12", "BC");
			keyStore.load(null, null);
			keyStore.setCertificateEntry("trust", cer);

			SSLSocketFactory socketFactory = new SSLSocketFactory(keyStore);
			Scheme sch = new Scheme("https", socketFactory, 443);
			HttpClient mHttpClient = new DefaultHttpClient();
			mHttpClient.getConnectionManager().getSchemeRegistry()
			.register(sch);

			BufferedReader reader = null;
			try {
				Log.d(TAG, "executeGet is in,murl:" + mUrl);
				HttpGet request = new HttpGet();
				request.setURI(new URI(mUrl));
				HttpResponse response = mHttpClient.execute(request);
				Log.i("123", "code="+response.getStatusLine().getStatusCode());
				if (response.getStatusLine().getStatusCode() != 200) {
					Log.e("123", "get status code !=200");
					request.abort();
					return result;
				}else{
					Log.e("123", "status code = 200");
				}

				reader = new BufferedReader(new InputStreamReader(response
						.getEntity().getContent()));
				StringBuffer buffer = new StringBuffer();
				String line = null;
				while ((line = reader.readLine()) != null) {
					buffer.append(line);
				}
				result = buffer.toString();
				Log.d(TAG, "mUrl=" + mUrl + "\nresult = " + result);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (reader != null) {
					reader.close();
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				if (ins != null)
					ins.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public void initSSL() throws CertificateException, IOException, KeyStoreException,  
	NoSuchAlgorithmException, KeyManagementException {  
		CertificateFactory cf = CertificateFactory.getInstance("X.509");  
		InputStream in = getAssets().open("ca.crt");  
		Certificate ca = cf.generateCertificate(in);  

		KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());  
		keystore.load(null, null);  
		keystore.setCertificateEntry("ca", ca);  

		String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();  
		TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);  
		tmf.init(keystore);  

		// Create an SSLContext that uses our TrustManager  
		SSLContext context = SSLContext.getInstance("TLS");  
		context.init(null, tmf.getTrustManagers(), null);  
		
		
		
		URL url = new URL("https://certs.cac.washington.edu/CAtest/");  
		//URL url = new URL("https://github.com");  
		HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();  
		urlConnection.setSSLSocketFactory(context.getSocketFactory());  
		InputStream input = urlConnection.getInputStream();  

		BufferedReader reader = new BufferedReader(new InputStreamReader(input, "UTF-8"));  
		StringBuffer result = new StringBuffer();  
		String line = "";  
		while ((line = reader.readLine()) != null) {  
			result.append(line);  
		}  
		Log.e("TTTT", result.toString());  
	} 

	public void initSSLALL() throws KeyManagementException, NoSuchAlgorithmException, IOException {  
//      URL url = new URL("https://certs.cac.washington.edu/CAtest/");  
      URL url = new URL("https://github.com");  
      SSLContext context = SSLContext.getInstance("TLS");  
      context.init(null, new TrustManager[]{new  MyX509TrustManager()}, null);  
      HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());  
      HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {  

          @Override  
          public boolean verify(String arg0, SSLSession arg1) {  
              return true;  
          }  
      });  
      HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();  
      connection.setDoInput(true);  
      connection.setDoOutput(false);  
      connection.setRequestMethod("GET");  
      connection.connect();  
      InputStream in = connection.getInputStream();  
      BufferedReader reader = new BufferedReader(new InputStreamReader(in));  
      String line = "";  
      StringBuffer result = new StringBuffer();  
      while ((line = reader.readLine()) != null) {  
          result.append(line);  
      }  
      Log.e("TTTT", result.toString());  
  }  

	
}
