package com.htkfood.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import com.htkfood.entity.ExpressEntity;
import com.htkfood.entity.LogisticsEntity;
import com.htkfood.entity.vo.SmsParamsVo;
import com.htkfood.service.SmsService;
import com.htkfood.util.StringUtil;

@Service
public class SmsServiceImpl implements SmsService {
	private static Logger logger = LoggerFactory.getLogger(SmsServiceImpl.class);
	@Autowired
	private SmsParamsVo smsParamsVo;

	public String sendSms2Driver(LogisticsEntity logisticsEntity) {
		if (smsParamsVo.getEnable() == null || !smsParamsVo.getEnable())
			return "-10006";
		String result = null;
		if (StringUtils.isNotBlank(logisticsEntity.getDestination())) {
			String[] params = new String[5];
			params[0] = logisticsEntity.getDriverName();
			params[1] = logisticsEntity.getOrganization();
			params[2] = logisticsEntity.getDestination();
			params[3] = logisticsEntity.getLogisticsNo();
			params[4] = logisticsEntity.getAccessToken();
			result=sendSmsByTx(logisticsEntity.getDriverPhone(), params, smsParamsVo.getDriverNewTempleteId());
		} else {

			String[] params = new String[4];
			params[0] = logisticsEntity.getDriverName();
			params[1] = logisticsEntity.getOrganization();
			params[2] = logisticsEntity.getLogisticsNo();
			params[3] = logisticsEntity.getAccessToken();
			result=sendSmsByTx(logisticsEntity.getDriverPhone(), params, smsParamsVo.getDriverTempleteId());
		}
		return result;
	}

	public String sendSms2Client(LogisticsEntity logisticsEntity, ExpressEntity entity) {
		if (smsParamsVo.getEnable() == null || !smsParamsVo.getEnable())
			return "-10006";
		String[] params = new String[4];
		params[0] = entity.getReceiver();
		params[1] = entity.getDeliveryOrder();
		params[2] = String.valueOf(entity.getId());
		params[3] = entity.getToken();
		String result = sendSmsByTx(entity.getReceiverPhone(), params, smsParamsVo.getClientTempleteId());
		return result;
	}

	public String sendSms2Employee(LogisticsEntity logisticsEntity, ExpressEntity entity) {
		if (smsParamsVo.getEnable() == null || !smsParamsVo.getEnable())
			return "-10006";
		String[] params = new String[4];
		params[0] = entity.getEmployeeName();
		params[1] = logisticsEntity.getLogisticsNo();
		params[2] = logisticsEntity.getLogisticsNo();
		params[3] = logisticsEntity.getAccessToken();
		String result = sendSmsByTx(entity.getEmployeePhone(), params, smsParamsVo.getEmployeeTempleteId());
		return result;
	}

	public String sendSmsByTx(String phone, String[] params, String templateId) {
		SmsSingleSender ssender = new SmsSingleSender(Integer.parseInt(smsParamsVo.getAppId()),
				smsParamsVo.getAppKey());
		SmsSingleSenderResult result = null;
		try {
			result = ssender.sendWithParam("86", phone, Integer.parseInt(templateId), params, "", "", "");
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return "-100001";
		} catch (HTTPException e) {
			e.printStackTrace();
			return "-100002";
		} catch (JSONException e) {
			e.printStackTrace();
			return "-100003";
		} catch (IOException e) {
			e.printStackTrace();
			return "-100004";
		} // 签名参数未提供或者为空时，会使用默认签名发送短信
		return result.errMsg;
	}

	/**
	 * 中国网建设的smsAPI
	 */
	public String sendSms(String phone, String text) {
		if (StringUtil.isBlank(smsParamsVo.getKey()) || StringUtil.isBlank(smsParamsVo.getUid())
				|| StringUtil.isBlank(phone) || StringUtil.isBlank(text))
			return "-102";
		HttpClient httpclient = HttpClients.createDefault();
		String URL = "http://utf8.api.smschinese.cn";
		HttpPost httpPost = new HttpPost(URL);
		httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
		// 添加参数
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("Uid", smsParamsVo.getUid()));
		params.add(new BasicNameValuePair("Key", smsParamsVo.getKey()));
		params.add(new BasicNameValuePair("smsMob", phone));
		params.add(new BasicNameValuePair("smsText", text));
		HttpResponse result = null;
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
			result = httpclient.execute(httpPost);
		} catch (Exception e) {
			logger.error("短信发送异常", e);
			return "-100";// 异常
		}
		if (null != result && result.getStatusLine().getStatusCode() == 200) {
			String str = "";
			try {
				str = EntityUtils.toString(result.getEntity());
			} catch (Exception e) {
				logger.error("短信发送异常", e);
				return "-100";
			}
			return str;
		} else {
			return "-101";// 请求结果不符合预期
		}
	}
}
