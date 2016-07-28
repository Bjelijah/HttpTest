//package com.fyfeng.httpstest;
//
//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.OutputStreamWriter;
//import java.io.PrintWriter;
//import java.io.UnsupportedEncodingException;
//import java.security.KeyStore;
//
//import javax.net.ssl.KeyManagerFactory;
//import javax.net.ssl.SSLContext;
//import javax.net.ssl.SSLSocket;
//import javax.net.ssl.TrustManagerFactory;
//
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//
//public class MySSLSocket extends Activity {
//	private static final int SERVER_PORT = 8851;//端口号
//	private static final String SERVER_IP = "192.168.18.245";//连接IP
//	private static final String CLIENT_KET_PASSWORD = "123456";//私钥密码
//	private static final String CLIENT_TRUST_PASSWORD = "123456";//信任证书密码
//	private static final String CLIENT_AGREEMENT = "TLS";//使用协议
//	private static final String CLIENT_KEY_MANAGER = "X509";//密钥管理器
//	private static final String CLIENT_TRUST_MANAGER = "X509";//
//	private static final String CLIENT_KEY_KEYSTORE = "BKS";//密库，这里用的是BouncyCastle密库
//	private static final String CLIENT_TRUST_KEYSTORE = "BKS";//
//	private static final String ENCONDING = "utf-8";//字符集
//	private SSLSocket Client_sslSocket;
//	private Log tag;
//	private TextView tv;
//	private Button btn;
//	private Button btn2;
//	private Button btn3;
//	private EditText et;
//	
//    /** Called when the activity is first created. */
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.main);
//        tv = (TextView) findViewById(R.id.TextView01);
//        et = (EditText) findViewById(R.id.EditText01);
//        btn = (Button) findViewById(R.id.Button01);
//        btn2 = (Button) findViewById(R.id.Button02);
//        btn3 = (Button) findViewById(R.id.Button03);
//        
//        btn.setOnClickListener(new Button.OnClickListener(){
//			@Override
//			public void onClick(View arg0) {
//				if(null != Client_sslSocket){
//					getOut(Client_sslSocket, et.getText().toString());
//					getIn(Client_sslSocket);
//					et.setText("");
//				}
//			}
//		});
//        btn2.setOnClickListener(new Button.OnClickListener(){
//			@Override
//			public void onClick(View arg0) {
//				try {
//					Client_sslSocket.close();
//					Client_sslSocket = null;
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		});
//        btn3.setOnClickListener(new View.OnClickListener(){
//			@Override
//			public void onClick(View arg0) {
//				init();
//				getIn(Client_sslSocket);
//			}
//		});
//    }
//    
//    public void init() {
//    	try {
//    		//取得SSL的SSLContext实例
//			SSLContext sslContext = SSLContext.getInstance(CLIENT_AGREEMENT);
//			//取得KeyManagerFactory和TrustManagerFactory的X509密钥管理器实例
//			KeyManagerFactory keyManager = KeyManagerFactory.getInstance(CLIENT_KEY_MANAGER);
//			TrustManagerFactory trustManager = TrustManagerFactory.getInstance(CLIENT_TRUST_MANAGER);
//			//取得BKS密库实例
//			KeyStore kks= KeyStore.getInstance(CLIENT_KEY_KEYSTORE);
//			KeyStore tks = KeyStore.getInstance(CLIENT_TRUST_KEYSTORE);
//			//加客户端载证书和私钥,通过读取资源文件的方式读取密钥和信任证书
//			kks.load(getBaseContext()
//					.getResources()
//					.openRawResource(R.drawable.kclient),CLIENT_KET_PASSWORD.toCharArray());
//			tks.load(getBaseContext()
//					.getResources()
//					.openRawResource(R.drawable.lt_client),CLIENT_TRUST_PASSWORD.toCharArray());
//			//初始化密钥管理器
//			keyManager.init(kks,CLIENT_KET_PASSWORD.toCharArray());
//			trustManager.init(tks);
//			//初始化SSLContext
//			sslContext.init(keyManager.getKeyManagers(),trustManager.getTrustManagers(),null);
//			//生成SSLSocket
//			Client_sslSocket = (SSLSocket) sslContext.getSocketFactory().createSocket(SERVER_IP,SERVER_PORT);
//		} catch (Exception e) {
//			tag.e("MySSLSocket",e.getMessage());
//		}
//    }
//        
//    public void getOut(SSLSocket socket,String message){
//    	PrintWriter out;
//		try {
//			out = new PrintWriter(
//					new BufferedWriter(
//							new OutputStreamWriter(
//									socket.getOutputStream()
//									)
//							),true);
//			out.println(message);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//    }
//    
//    public void getIn(SSLSocket socket){
//    	BufferedReader in = null;
//		String str = null;
//		try {
//			in = new BufferedReader(
//					new InputStreamReader(
//							socket.getInputStream()));
//			str = new String(in.readLine().getBytes(),ENCONDING);
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		new AlertDialog
//		.Builder(MySSLSocket.this)
//		.setTitle("服务器消息")
//		.setNegativeButton("确定", null)
//		.setIcon(android.R.drawable.ic_menu_agenda)
//		.setMessage(str)
//		.show();
//    }
//}
//
