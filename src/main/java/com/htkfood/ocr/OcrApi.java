package com.htkfood.ocr;

import com.htkfood.exception.CommonException;


public interface OcrApi {
	
	
	String getAppId();
	
	String getApiKey();
	
	String getSecretKey();
	
	
	String getAccessToken();
	/**
	 * 
	 * @param image 图像的base64编码
	 * @param detectDirection 是否检测图像朝向，默认不检测，即：false。朝向是指输入图像是正常方向、逆时针旋转90/180/270度。可选值包括:- true：检测朝向；- false：不检测朝向
	 * @param unifiedValidPeriod true: 归一化格式输出；false 或无此参数按非归一化格式输出
	 * @return
	 * @throws CommonException
	 */
	
	String getDrivingLicense(String image,String detectDirection,Boolean unifiedValidPeriod)throws CommonException;
	/**
	 * 
	 * @param image 图像的base64编码
	 * @param detectDirection 是否检测图像朝向，默认不检测，即：false。朝向是指输入图像是正常方向、逆时针旋转90/180/270度。可选值包括:- true：检测朝向；- false：不检测朝向
	 * @param accuracy normal，缺省	normal 使用快速服务，1200ms左右时延；缺省或其它值使用高精度服务，1600ms左右时延
	 * @return
	 * @throws CommonException
	 */
	String getVehicleLicense(String image,String detectDirection,String accuracy)throws CommonException;
	
	void checkClientAccessToken() throws CommonException;

}
