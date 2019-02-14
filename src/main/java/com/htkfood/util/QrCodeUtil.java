package com.htkfood.util;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class QrCodeUtil {
	private static Logger logger=LoggerFactory.getLogger(QrCodeUtil.class);
	public static void getQrCode(String info, HttpServletResponse response, int width, int height)
			throws IOException, WriterException {
		if (info != null && !"".equals(info)) {
			ServletOutputStream stream = null;
			try {
				stream = response.getOutputStream();
				QRCodeWriter writer = new QRCodeWriter();
				BitMatrix m = writer.encode(info, BarcodeFormat.QR_CODE, height, width);
				MatrixToImageWriter.writeToStream(m, "png", stream);
			} finally {
				if (stream != null) {
					stream.flush();
					stream.close();
				}
			}
		}else {
			logger.info("info is blank ...");
		}
	}
}
