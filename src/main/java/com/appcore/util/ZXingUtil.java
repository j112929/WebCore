/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.appcore.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.imageio.ImageIO;

public class ZXingUtil {
	public static void generateTwoDimensionCode(String contents, int width,
			int height, String imgPath) {
		Map hints = new ConcurrentHashMap();

		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		try {
			BitMatrix bitMatrix = new MultiFormatWriter().encode(contents,
					BarcodeFormat.QR_CODE, width, height, hints);
			MatrixToImageWriter.writeToStream(bitMatrix, "png",
					new FileOutputStream(imgPath));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String parseTwoDimensionCode(String imgPath) {
		BufferedImage image = null;
		Result result = null;
		try {
			image = ImageIO.read(new File(imgPath));
			if (image == null) {
				System.out.println("图片不存在！");
			}
			LuminanceSource source = new BufferedImageLuminanceSource(image);
			BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

			Map hints = new ConcurrentHashMap();
			hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");

			MultiFormatReader rd = new MultiFormatReader();
			result = rd.decode(bitmap, hints);
			return result.getText();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void generateBarCode(String contents, int width, int height,
			String imgPath) {
		int codeWidth = 95;

		codeWidth = Math.max(codeWidth, width);
		try {
			BitMatrix bitMatrix = new MultiFormatWriter().encode(contents,
					BarcodeFormat.EAN_13, codeWidth, height, null);
			MatrixToImageWriter.writeToStream(bitMatrix, "png",
					new FileOutputStream(imgPath));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String parseBarCode(String imgPath) {
		BufferedImage image = null;
		Result result = null;
		try {
			image = ImageIO.read(new File(imgPath));
			if (image == null) {
				System.out.println("图片不存在！");
			}
			LuminanceSource source = new BufferedImageLuminanceSource(image);
			BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

			result = new MultiFormatReader().decode(bitmap, null);
			return result.getText();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		testTwoDimensionCode();
	}

	private static void testTwoDimensionCode() {
		String imgPath = "D:/zxing/aaa1.png";

		String contents = "https://temai.taobao.com/channel.htm?spm=a231o.7076277.1998549683.2.S82WRc&prepvid_1461038037861&extra=&cid=101&pid=mm_28347190_2425761_20444747&unid=&source_id=&app_pvid=200_10.237.10.118_397_1461038037861";

		int width = 1200;
		int height = 1200;

		generateTwoDimensionCode(contents, width, height, imgPath);

		System.out.println("生成二维码成功！" + parseTwoDimensionCode(imgPath));
	}

	private static void testBarCode() {
		String imgPath = "D:/zxing/bbb1.png";

		String contents = "6923450657713";

		int width = 105;
		int height = 50;
		generateBarCode(contents, width, height, imgPath);

		System.out.println("生成条形码成功！" + parseBarCode(imgPath));
	}
}