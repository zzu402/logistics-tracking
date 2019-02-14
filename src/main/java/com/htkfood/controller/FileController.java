package com.htkfood.controller;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.htkfood.entity.DriverAndVehicle;
import com.htkfood.exception.CommonException;
import com.htkfood.exception.CommonExceptionHelper;
import com.htkfood.exception.CommonRuntimeException;
import com.htkfood.httpclient.JsonMapper;
import com.htkfood.interceptor.PrivilegeConstant;
import com.htkfood.interceptor.annotation.Privileges;
import com.htkfood.ocr.OcrApiManager;
import com.htkfood.util.ResultHelper;
import com.htkfood.util.StringUtil;
import sun.misc.BASE64Encoder;

@RestController
@RequestMapping("/file")
public class FileController {

	private Logger logger = LoggerFactory.getLogger(FileController.class);

	@Value("${attachment.dir}")
	private String attachmentDir;

	/**
	 * 客户端提交时的Content-Type:multipart/form-data
	 * 
	 * @return
	 */
	@Privileges(PrivilegeConstant.OPEN_PAGE)
	@RequestMapping(path = "/upload/{type}", method = RequestMethod.POST)
	public Map<String, Object> upload(@PathVariable("type") String type, @RequestParam("file") MultipartFile file)
			throws CommonException {
		Map<String, Object> result = ResultHelper.success();
		String relativeDir = String.format("%s/%s/%s", "WEB", type, "COMMON");
		String fileName = doUpload(type, file, attachmentDir, relativeDir, true);
		result.put("path", String.format("/%s/%s", relativeDir, fileName));
		return result;
	}
	
	
	/**
	 * 客户端提交时的Content-Type:multipart/form-data
	 * 
	 * @return
	 */
	@Privileges(PrivilegeConstant.OPEN_PAGE)
	@RequestMapping(path = "/uploadAndOcrCard/{type}", method = RequestMethod.POST)
	public Map<String, Object> uploadAndOcrCard(@PathVariable("type") String type, @RequestParam("file") MultipartFile file)
			throws CommonException {
		Map<String, Object> result = ResultHelper.success();
	
		 BASE64Encoder encoder = new BASE64Encoder();
		 String imgData =null;
		 try {
			 imgData= encoder.encode(file.getBytes());
		} catch (IOException e) {
			CommonRuntimeException exception = CommonExceptionHelper
					.resourceRuntimeException(String.format("获取文件的base64失败"), e);
			
			logger.error(CommonExceptionHelper.formatException(exception), e);
			throw exception;
		}
		DriverAndVehicle driverAndVehicle=new DriverAndVehicle();
		String relativeDir = String.format("%s/%s/%s", "WEB", type, "COMMON");
		String fileName = doUpload(type, file, attachmentDir, relativeDir, true);
		result.put("path", String.format("/%s/%s", relativeDir, fileName));
		try {
			
			
		if(type.equals("2")) {
			String ocrResult=OcrApiManager.getOcrApi("htkfood").getDrivingLicense(imgData, "true", null);
			logger.info(String.format("驾驶证证件识别：%s", ocrResult));
			Map resultMap=(Map) JsonMapper.nonEmptyMapper().fromJson(ocrResult);
			Map wordResultMap=(Map) resultMap.get("words_result");
			Map idCardMap=(Map) wordResultMap.get("证号");
			Map nameMap=(Map) wordResultMap.get("姓名");
			driverAndVehicle.setIdCard((String) idCardMap.get("words"));
			driverAndVehicle.setName((String) nameMap.get("words"));
			
		}else if(type.equals("3")) {
			String ocrResult=OcrApiManager.getOcrApi("htkfood").getVehicleLicense(imgData, "true", "");
			logger.info(String.format("行驶证证件识别：%s", ocrResult));
			Map resultMap=(Map) JsonMapper.nonEmptyMapper().fromJson(ocrResult);
			Map wordResultMap=(Map) resultMap.get("words_result");
			Map map1=(Map) wordResultMap.get("号牌号码");
			Map map2=(Map) wordResultMap.get("车辆类型");
			Map map3=(Map) wordResultMap.get("所有人");
			Map map4=(Map) wordResultMap.get("发动机号码");
			driverAndVehicle.setPlate((String) map1.get("words"));
			driverAndVehicle.setVehicleType((String) map2.get("words"));
			driverAndVehicle.setOwner((String) map3.get("words"));
			driverAndVehicle.setEngineNo((String) map4.get("words"));
		}
		}catch (Exception e) {
			CommonRuntimeException exception = CommonExceptionHelper
					.resourceRuntimeException(String.format("图像识别失败"), e);
			
			logger.error(CommonExceptionHelper.formatException(exception), e);
			throw exception;
		}
		result.put("driverAndVehicle", driverAndVehicle);
		
		return result;
	}



	/**
	 * 上传文件，返回文件名
	 * 
	 * @param type
	 * @param file
	 * @param attachmentDir
	 * @param relativeDir
	 * @param randomName
	 * @return
	 */
	protected String doUpload(String type, MultipartFile file, String attachmentDir, String relativeDir,
			boolean randomName) {
		String originalName = file.getOriginalFilename();
		if (file.isEmpty()) {
			CommonRuntimeException exception = CommonExceptionHelper
					.resourceRuntimeException(String.format("文件[%s]为空", originalName), null);
			logger.error(CommonExceptionHelper.formatException(exception));
			throw exception;
		}

		File newFile = null;
		try {
			if (randomName) {
				String extension = FilenameUtils.getExtension(originalName);
				String filePath = String.format("%s/%s%s", relativeDir, StringUtil.simpleUUID(),
						(StringUtil.isBlank(extension) ? "" : String.format(".%s", extension)));
				newFile = new File(attachmentDir, filePath).getCanonicalFile();
			} else {
				newFile = new File(attachmentDir, String.format("%s/%s", relativeDir, originalName)).getCanonicalFile();
			}
			File parentDir = newFile.getParentFile();
			if (null != parentDir && !parentDir.exists()) {
				parentDir.mkdirs();
			}
			file.transferTo(newFile);
			logger.info(String.format("上传类型[%s]的文件[%s]到[%s]成功", type, originalName, newFile.getAbsolutePath()));
		} catch (IOException e) {
			CommonRuntimeException exception = CommonExceptionHelper
					.resourceRuntimeException(String.format("上传类型[%s]的文件[%s]失败", type, originalName), e);
			logger.error(CommonExceptionHelper.formatException(exception), e);
			throw exception;
		}
		return newFile.getName();
	}
}
