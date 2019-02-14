package com.htkfood.ocr;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.htkfood.exception.CommonException;
import com.htkfood.exception.CommonExceptionHelper;
import com.htkfood.httpclient.JsonMapper;
import com.htkfood.httpclient.SimpleHttpClient;
import com.htkfood.ocr.model.AccessToken;
import com.htkfood.util.StringUtil;

public class OcrApiImpl implements OcrApi {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	String appId = "";
	String accessToken = "";
	String apiKey = "";
	String secretKey = "";

	@Override
	public String getAppId() {
		return appId;
	}

	@Override
	public String getApiKey() {
		return apiKey;
	}

	@Override
	public String getSecretKey() {
		return secretKey;
	}

	private <T> T checkResult(Class<T> resultType, String resultJson, String errorDesc) throws CommonException {
		if (!StringUtil.isBlank(resultJson)) {
			Map map = (Map) JsonMapper.nonEmptyMapper().fromJson(resultJson);
			String error = (String) map.get("error");
			String errMsg = (String) map.get("error_description");
			if (StringUtils.isNotBlank(error)) {
				throw CommonExceptionHelper.cacheException(String.format("%s: [%s]%s", errorDesc, error, errMsg), null);
			}
			if (Map.class.isAssignableFrom(resultType))
				return (T) JsonMapper.nonEmptyMapper().fromJson(resultJson);
			else
				return JsonMapper.nonEmptyMapper().fromJson(resultJson, resultType);
		}
		throw CommonExceptionHelper.cacheException(errorDesc, null);
	}

	private AccessToken doGetAccessToken() throws CommonException {
		// 获取token地址
		String authHost = "https://aip.baidubce.com/oauth/2.0/token?";
		String getAccessTokenUrl = authHost
				// 1. grant_type为固定参数
				+ "grant_type=client_credentials"
				// 2. 官网获取的 API Key
				+ "&client_id=" + apiKey
				// 3. 官网获取的 Secret Key
				+ "&client_secret=" + secretKey;
		String result = get(getAccessTokenUrl);
		return checkResult(AccessToken.class, result, "查询accessToken出错");
	}

	private String get(String url) throws CommonException {
		Map<String, String> requestHeaders = new LinkedHashMap<>();
		requestHeaders.put("Connection", "keep-alive");
		try {
			return new String(SimpleHttpClient.get(url, requestHeaders, null), "UTF-8");
		} catch (Exception e) {
			throw CommonExceptionHelper.cacheException(String.format("调用%s时出错", url), null);
		}
	}

	private String jsonPost(String url, Map<String, Object> request) throws CommonException {
		Map<String, String> requestHeaders = new LinkedHashMap<>();
		requestHeaders.put("Connection", "keep-alive");
		requestHeaders.put("Content-Type", "application/json");
		try {
			byte[] content = JsonMapper.nonEmptyMapper().toJson(request).getBytes("UTF-8");
			return new String(SimpleHttpClient.post(url, requestHeaders, null, content), "UTF-8");
		} catch (Exception e) {
			throw CommonExceptionHelper.cacheException(String.format("调用%s时出错", url), null);
		}
	}

	private String formPost(String url, Map<String, Object> request) throws CommonException {
		Map<String, String> requestHeaders = new LinkedHashMap<>();
		requestHeaders.put("Connection", "keep-alive");
		requestHeaders.put("Content-Type", "application/x-www-form-urlencoded");
		try {
			byte[] content = SimpleHttpClient.formData(request).getBytes("UTF-8");
			return new String(SimpleHttpClient.post(url, requestHeaders, null, content), "UTF-8");
		} catch (Exception e) {
			throw CommonExceptionHelper.cacheException(String.format("调用%s时出错", url), null);
		}
	}

	@Override
	public String getDrivingLicense(String image, String detectDirection, Boolean unifiedValidPeriod)
			throws CommonException {
		if (StringUtils.isBlank(image)) {
			throw CommonExceptionHelper.cacheException("驾驶证识别出错，图片不能为空", null);
		}
		Map<String, Object> request = new LinkedHashMap<>();
		request.put("image", image);
		if (StringUtils.isNotBlank(detectDirection)) {
			request.put("detect_direction", detectDirection);
		}
		if (unifiedValidPeriod != null && unifiedValidPeriod) {
			request.put("unified_valid_period", unifiedValidPeriod);
		}
		if (StringUtils.isBlank(accessToken)) {
			throw CommonExceptionHelper.cacheException("请先获取AccessToken", null);
		}
		String url = String.format("https://aip.baidubce.com/rest/2.0/ocr/v1/driving_license?access_token=%s",
				accessToken);
		return formPost(url, request);
	}

	@Override
	public String getVehicleLicense(String image,String detectDirection,String accuracy) throws CommonException {
		if (StringUtils.isBlank(image)) {
			throw CommonExceptionHelper.cacheException("驾驶证识别出错，图片不能为空", null);
		}
		Map<String, Object> request = new LinkedHashMap<>();
		request.put("image", image);
		if (StringUtils.isNotBlank(detectDirection)) {
			request.put("detect_direction", detectDirection);
		}
		if (StringUtils.isNotBlank(accuracy)) {
			request.put("accuracy", accuracy);
		}
		if (StringUtils.isBlank(accessToken)) {
			throw CommonExceptionHelper.cacheException("请先获取AccessToken", null);
		}
		String url = String.format("https://aip.baidubce.com/rest/2.0/ocr/v1/vehicle_license?access_token=%s",
				accessToken);
		return  formPost(url, request);
	}

	@Override
	public void checkClientAccessToken() throws CommonException {
		  this.accessToken = doGetAccessToken().getAccess_token();
    }

	@Override
	public String getAccessToken() {
		return this.accessToken;
	}
}

class invocationHandler implements InvocationHandler {
    private OcrApi target;

    public invocationHandler(OcrApi target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try {
            Object result = method.invoke(target,args);
            return result;
        } catch (Exception e) {
            CommonException exception = CommonExceptionHelper.cacheException(e.getMessage(), e);
            if(null != exception){//access_token expired hint
                //access_token过期，换新的重试
                return method.invoke(target,args);
            }
            throw e;
        }
    }
}
