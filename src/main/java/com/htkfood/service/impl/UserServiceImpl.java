package com.htkfood.service.impl;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.htkfood.entity.UserEntity;
import com.htkfood.exception.CommonException;
import com.htkfood.exception.CommonExceptionHelper;
import com.htkfood.mapper.slave.UserMapper;
import com.htkfood.service.UserService;
import com.htkfood.util.StringUtil;

import net.sf.json.JSONObject;

@Service
public class UserServiceImpl implements UserService{


	@Autowired
	private UserMapper userMapper;
	@Override
	public UserEntity login(String userName, String password) throws CommonException {
		if(StringUtil.isBlank(userName)||StringUtil.isBlank(password)) {
			throw CommonExceptionHelper.commonException("用户名或密码不能为空", null);
		}
		// 定义httpClient的实例
		HttpClient httpclient = HttpClients.createDefault();
        //登录，校验用户的API接口地址
        String Login_URL = "http://erp.htkfood.com/K3Cloud/Kingdee.BOS.WebApi.ServicesStub.AuthService.ValidateUser.common.kdsvc";
        //登录请求参数
        JSONObject itemJson = new JSONObject();
        itemJson.put("acctID", "5a0d78282b2ab7");
        itemJson.put("userName", userName);
        itemJson.put("password", password);
        itemJson.put("lcid", "2052");
        StringEntity entity = new StringEntity(itemJson.toString(), "utf-8");
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        //创建一个Request对象,类型为post
        HttpPost httpPost = new HttpPost(Login_URL);
	    httpPost.setEntity(entity);
	    httpPost.addHeader("Host","erp.htkfood.com");
        HttpResponse result = null;
		try {
			result = httpclient.execute(httpPost);
		} catch (Exception e) {
			e.printStackTrace();
			throw CommonExceptionHelper.commonException("K3/Cloud请求提交失败", e);
		} 
        // 请求发送成功，并得到响应
        if (null != result && result.getStatusLine().getStatusCode() == 200){
                // 读取服务器返回过来的json字符串数据
                String str ="";
				try {
					str = EntityUtils.toString(result.getEntity());
				} catch (Exception e) {
					e.printStackTrace();
					throw CommonExceptionHelper.commonException("K3/Cloud请求提交失败", e);
				}
                // 把json字符串转换成json对象
				JSONObject jsonResult = JSONObject.fromObject(str);
                //判断登录是否成功
                if (jsonResult.getInt("LoginResultType") == 1) {
                    System.out.println("K3/Cloud登录成功！");
                    String userId = jsonResult.getJSONObject("Context").getString("UserId");
                    if(null == userId) {
                    	throw CommonExceptionHelper.commonException("用户信息异常", null);
                    }
                    UserEntity userEntity = userMapper.getUserInfoById(userId);
                    if(null == userEntity ) {
                    	throw CommonExceptionHelper.commonException("没有权限", null);
                    }
                    
                    return userEntity;
                } else {
                	// 登录失败，不继续
                    throw CommonExceptionHelper.commonException(jsonResult.getString("Message"), null);
                }
        }else{
            throw CommonExceptionHelper.commonException("登录异常", null);
        }
	}

}
