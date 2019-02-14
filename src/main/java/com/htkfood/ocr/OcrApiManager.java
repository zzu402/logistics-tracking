package com.htkfood.ocr;

import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OcrApiManager {
	private static Map<String, OcrApi> apiMap = new ConcurrentHashMap<>();
	private static Logger logger = LoggerFactory.getLogger(OcrApiManager.class);
	

	public static void register(String name, Properties properties) {
		OcrApiImpl OcrApi = new OcrApiImpl();
		 if(null != properties){
	
			 OcrApi.appId= properties.getProperty("appId");
			 OcrApi.secretKey= properties.getProperty("secretKey");
			 OcrApi.apiKey= properties.getProperty("apiKey");
		
		 }else{
		 logger.warn("没有加载到信息相关配置，使用默认配置");
		 }
		OcrApi proxy = (OcrApi) Proxy.newProxyInstance(OcrApiImpl.class.getClassLoader(),
				new Class[] { OcrApi.class }, new invocationHandler(OcrApi));
		startTokenKeeper(proxy);
		apiMap.put(name, proxy);
	}

	private static void startTokenKeeper(final OcrApi api) {
		Thread thread = new Thread(String.format("OCR[%s]", api.getAppId())) {
			@Override
			public void run() {
				while (true) {
					try {
						api.checkClientAccessToken();
						logger.info("获取client access token 成功-->"+api.getAccessToken() );
						Thread.sleep(60000 * 1000L);
					} catch (Exception e) {
						logger.error("保持微信AccessToken过程中出错", e);
					}
				}
			}
		};
		thread.setDaemon(true);
		thread.start();
	}

	public static String[] getOcrNames() {
		return apiMap.keySet().toArray(new String[apiMap.size()]);
	}

	public static OcrApi getOcrApi(String name) {
		return apiMap.get(name);
	}

}
