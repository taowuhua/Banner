package com.bank.quickpay.utils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Environment;
import android.view.View;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class BitmapUntils {

	public static Bitmap getImageByURL(String url) {
		try {
			URL imgURL = new URL(url);
			URLConnection conn = imgURL.openConnection();
			conn.connect();
			InputStream is = conn.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);
			Bitmap bm = BitmapFactory.decodeStream(bis); // 关键代码
			bis.close();
			is.close();
			if (bm == null) {
				LogUtil.showLog("MO", "httperror");
			}
			return bm;
		} catch (Exception e) {
			return null;
		}
	}

	public static byte[] bitmapToArray(Bitmap bmp) {
		ByteArrayOutputStream output = new ByteArrayOutputStream();// 初始化一个流对象
		bmp.compress(CompressFormat.PNG, 100, output);// 把bitmap100%高质量压缩 到
														// output对象里

		// bmp.recycle();//自由选择是否进行回收

		byte[] result = output.toByteArray();// 转换成功了
		try {
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static byte[] getContent(Bitmap bitmap) {
		ByteArrayOutputStream baos;
		if (bitmap != null) {
			baos = new ByteArrayOutputStream();
			bitmap.compress(CompressFormat.JPEG, 85, baos);
			return baos.toByteArray();
		}
		return null;

	}

	public static String bytesToHexString(byte[] src) {

		String result = "";
		try {
			result = URLEncoder.encode(Base64Utils.encode(src), "UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 根据指定比例压缩
	 * @param imgByte
	 * @return
	 */
	public static byte[] getContent(int percentage, byte[] imgByte){
		if (imgByte!=null&&imgByte.length != 0) {
			Bitmap bitmap=	 BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length);
			return  getContentbyPercentage(bitmap,percentage);
		} else {
			return null;
		}
	}

	/**
	 * 根据相机像素大小进行压缩图片
	 * @param imgByte
	 * @return
	 */
	public static byte[] getContentbyCameraPix(byte[] imgByte){
		if (imgByte!=null&&imgByte.length != 0) {
		Bitmap bitmap=	 BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length);
			return  getContentbyCameraPix(bitmap);
		} else {
			return null;
		}
	}
	/**
	 * 根据指定百分比进行压缩图片
	 * @param bitmap
	 * @return
	 */
	public static byte[] getContentbyCameraPix(Bitmap bitmap){
		/**
		 * 前置摄像头像素
		 */
		int frontCameraPix=CameraUtils.getCameraPixels(CameraUtils.HasFrontCamera());
		/***
		 * 后置摄像头像素
		 */
		int backCameraPix=CameraUtils.getCameraPixels(CameraUtils.HasBackCamera());
		LogUtil.showLog("frontCameraPix=="+frontCameraPix+",backCameraPix=="+backCameraPix);
		//前置摄像头判断
		if(frontCameraPix>=500){
			LogUtil.showLog("1frontCameraPix=="+frontCameraPix+",backCameraPix=="+backCameraPix+",percentage==85");
			return getContentbyPercentage(bitmap,80);
		}else if(frontCameraPix>=200&&frontCameraPix<500){
			LogUtil.showLog("1frontCameraPix=="+frontCameraPix+",backCameraPix=="+backCameraPix+",percentage==90");
			return getContentbyPercentage(bitmap,85);
		}else if(frontCameraPix>0&&frontCameraPix<200){
			LogUtil.showLog("1frontCameraPix=="+frontCameraPix+",backCameraPix=="+backCameraPix+",percentage==100");
			return getContentbyPercentage(bitmap,100);
		}else{
			//只有后置摄像头的手机
			if(backCameraPix>=500){
				LogUtil.showLog("2frontCameraPix=="+frontCameraPix+",backCameraPix=="+backCameraPix+",percentage==85");
				return getContentbyPercentage(bitmap,80);
			}else if(backCameraPix>=200&&backCameraPix<500){
				LogUtil.showLog("2frontCameraPix=="+frontCameraPix+",backCameraPix=="+backCameraPix+",percentage==90");
				return getContentbyPercentage(bitmap,85);
			}else{
				LogUtil.showLog("2frontCameraPix=="+frontCameraPix+",backCameraPix=="+backCameraPix+",percentage==100");
				return getContentbyPercentage(bitmap,100);
			}
		}
	}
	/**
	 * 根据指定百分比进行压缩图片
	 * @param bitmap
	 * @param percentage
	 * @return
	 */
	private static byte[] getContentbyPercentage(Bitmap bitmap,int percentage){
		ByteArrayOutputStream baos;
		if (bitmap != null) {
			baos = new ByteArrayOutputStream();
			bitmap.compress(CompressFormat.JPEG, percentage, baos);
			try {
				baos.flush();
				baos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return baos.toByteArray();
		}
		return null;
	}
	public static byte[] getContent85(Bitmap bitmap){
		ByteArrayOutputStream baos;
		if (bitmap != null) {
			baos = new ByteArrayOutputStream();
			bitmap.compress(CompressFormat.JPEG, 85, baos);
			try {
				baos.flush();
				baos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return baos.toByteArray();
		}
		return null;
	}
	public static byte[] getContent(int quality, Bitmap bitmap){
		ByteArrayOutputStream baos;
		if (bitmap != null) {
			baos = new ByteArrayOutputStream();
			bitmap.compress(CompressFormat.JPEG, quality, baos);
			try {
				baos.flush();
				baos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return baos.toByteArray();
		}
		return null;
	}
	public static byte[] getBitmapByte(Bitmap bitmap) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int ratio = 100;
		bitmap.compress(CompressFormat.JPEG, ratio, out);
		float radio = out.size() / 1024;
		for (; radio > 10&&ratio >2; ratio--) {
			out.reset();
			bitmap.compress(CompressFormat.JPEG, ratio, out);
			radio = out.size() / 1024;
		}
		return out.toByteArray();
	}

	/* *
	 * Convert byte[] to hex
	 * string.这里我们可以将byte转换成int，然后利用Integer.toHexString(int)来转换成16进制字符串。
	 * 
	 * @param src byte[] data
	 * 
	 * @return hex string
	 */
	public static String changeBytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}

	/**
	 * Convert hex string to byte[]
	 * 
	 * @param hexString
	 *            the hex string
	 * @return byte[]
	 */
	public static byte[] hexStringToBytes(String hexString) {
		if (hexString == null || "".equals(hexString)) {
			return null;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}

	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}

	// 此方法用于改变Bitmap对象的大小，需要3个参数 1.要改变的图片
	public static Bitmap DengBichangeBitmapSize(Bitmap bitmap) {

		int width = bitmap.getWidth();
		int height = bitmap.getHeight();

		float myScale = (float) 60 / height;

		Matrix matrix = new Matrix();

		matrix.postScale(myScale, myScale);

		return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
	}

	public static Bitmap DengBichangeBitmapSizeByWidth(Bitmap bitmap,
			int limitWidth) {

		int width = bitmap.getWidth();
		int height = bitmap.getHeight();

		float myScale = (float) limitWidth / height;

		Matrix matrix = new Matrix();

		matrix.postScale(myScale, myScale);

		return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
	}

	public static Bitmap DengBichangeBitmapSizeByHeight(Bitmap bitmap,
			int limitHeight) {

		int width = bitmap.getWidth();
		int height = bitmap.getHeight();

		float myScale = (float) limitHeight / height;

		Matrix matrix = new Matrix();

		matrix.postScale(myScale, myScale);

		return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
	}

	public static Bitmap DengBichangesignBitmapSizeByHeight(Bitmap bitmap,
			int limitHeight) {

		int width = bitmap.getWidth();
		int height = bitmap.getHeight();

		float myScale = (float) limitHeight / height;

		Matrix matrix = new Matrix();

		matrix.postScale(myScale, myScale);
		// 向左旋转45度，参数为正则向右旋转
		matrix.postRotate(-90);

		return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
	}

	// 此方法用于改变Bitmap对象的大小，需要3个参数 1.要改变的图片 2.区域的宽 3.区域的高
	public static Bitmap DengBichangeBitmapSize(Bitmap bitmap, int newWidth,
			int newHeight) {
		// 得到原图宽高
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		// 得到宽高的缩放比例
		float wScale = (float) newWidth / width;
		float hScale = (float) newHeight / height;

		float myScale = (wScale < hScale) ? wScale : hScale;

		Matrix matrix = new Matrix();

		matrix.postScale(myScale, myScale);

		return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
	}

	// 此方法用于改变Bitmap对象的大小，需要3个参数 1.要改变的图片 2.区域的宽 3.区域的高
	public static Bitmap DengBichangeBitmapSize2(Bitmap bitmap, int newWidth,
			int newHeight) {
		// 得到原图宽高
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		// 得到宽高的缩放比例
		float wScale = (float) newWidth / width;
		float hScale = (float) newHeight / height;

		float myScale = (wScale < hScale) ? wScale : hScale;

		Matrix matrix = new Matrix();

		matrix.postScale(myScale, myScale);

		return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, false);
	}

	// 此方法用于改变Bitmap对象的大小，需要3个参数 1.要改变的图片 2.想改变到多宽 3.想改变到多高
	public Bitmap changeBitmapSize(Bitmap bitmap, int newWidth, int newHeight) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();

		float wScale = (float) newWidth / width;
		float hScale = (float) newHeight / height;

		Matrix matrix = new Matrix();

		matrix.postScale(wScale, hScale);

		return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
	}

	private static String formatTime(int t) {
		return t >= 10 ? "" + t : "0" + t;// 三元运算符 t>10时取 ""+t
	}

	/**
	 * 保存二维码布局,返回保存路径和文件名称
	 * @param view
	 * @param url
     * @return
     */
	public static Map<String,String> saveQrcodeAsFile(View view, String url) {
		ByteArrayOutputStream baos = null;
		String signpath = null;
		String result = "";
		String sign_dirpath="";
		String fileName="";
		Map<String,String> resultMap=new HashMap<String,String>();
		try {
			Calendar c = Calendar.getInstance();

			String time = c.get(Calendar.YEAR) + // 得到年
					formatTime(c.get(Calendar.MONTH) + 1) + // month加一 //月
					formatTime(c.get(Calendar.DAY_OF_MONTH)) + // 日
					formatTime(c.get(Calendar.HOUR_OF_DAY)) + // 时
					formatTime(c.get(Calendar.MINUTE)) + // 分
					formatTime(c.get(Calendar.SECOND));
			// 秒

			if (PhoneinfoUtils.IsSdCardCanBeUsed()) {
				String sign_dir = Environment.getExternalStorageDirectory()
						+ File.separator + url;
				File file = new File(sign_dir);
				if (!file.exists()) {
					try {
						// 按照指定的路径创建文件夹
						if (!file.mkdirs()) {

							result = "创建文件失败";

						} else {
							signpath = sign_dir + File.separator + time
									+ ".jpg";
							sign_dirpath=sign_dir;
							fileName=time;
							baos = new ByteArrayOutputStream();

							view.setDrawingCacheEnabled(true);
							view.buildDrawingCache();
							Bitmap bmp = view.getDrawingCache();

							bmp.compress(CompressFormat.JPEG, 100, baos);
							byte[] photoBytes = baos.toByteArray();
							if (photoBytes != null) {
								new FileOutputStream(new File(signpath))
										.write(photoBytes);
							}
							result = "图片保存到" + signpath;
						}
						;
					} catch (Exception e) {
						e.printStackTrace();
						result = "创建文件夹失败";
					}
				} else {
					signpath = sign_dir + File.separator + time + ".jpg";
					sign_dirpath=sign_dir;
					fileName=time;
					baos = new ByteArrayOutputStream();

					view.setDrawingCacheEnabled(true);
					view.buildDrawingCache();
//					Bitmap bmp = view.getDrawingCache();
					Bitmap bmp = convertViewToBitmap(view);
					bmp.compress(CompressFormat.JPEG, 100, baos);
					byte[] photoBytes = baos.toByteArray();
					if (photoBytes != null) {
						new FileOutputStream(new File(signpath))
								.write(photoBytes);
					}
					result = "图片保存到" + signpath;
				}

			} else {
				result = "SD卡目前不可用";
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (baos != null) {
                    baos.close();
                }
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		resultMap.put("path",sign_dirpath);
		resultMap.put("fileName",fileName);
		resultMap.put("result",result);
		return resultMap;
	}

	public static String SaveAsFile(View view, String url) {
		ByteArrayOutputStream baos = null;
		String signpath = null;
		String result = "";
		try {
			Calendar c = Calendar.getInstance();

			String time = c.get(Calendar.YEAR) + // 得到年
					formatTime(c.get(Calendar.MONTH) + 1) + // month加一 //月
					formatTime(c.get(Calendar.DAY_OF_MONTH)) + // 日
					formatTime(c.get(Calendar.HOUR_OF_DAY)) + // 时
					formatTime(c.get(Calendar.MINUTE)) + // 分
					formatTime(c.get(Calendar.SECOND));
			// 秒

			if (PhoneinfoUtils.IsSdCardCanBeUsed()) {
				String sign_dir = Environment.getExternalStorageDirectory()
						+ File.separator + url;
				File file = new File(sign_dir);
				if (!file.exists()) {
					try {
						// 按照指定的路径创建文件夹
						if (!file.mkdirs()) {

							result = "创建文件失败";

						} else {
							signpath = sign_dir + File.separator + time
									+ ".jpg";
							baos = new ByteArrayOutputStream();
							Bitmap bmp = loadBitmapFromView(view);
							bmp.compress(CompressFormat.JPEG, 100, baos);
							byte[] photoBytes = baos.toByteArray();
							if (photoBytes != null) {
								new FileOutputStream(new File(signpath))
										.write(photoBytes);
							}
							result = "图片保存到" + signpath;
						}
						;
					} catch (Exception e) {
						e.printStackTrace();
						result = "创建文件夹失败";
					}
				} else {
					signpath = sign_dir + File.separator + time + ".jpg";
					baos = new ByteArrayOutputStream();

					view.setDrawingCacheEnabled(true);
					view.buildDrawingCache();
//					Bitmap bmp = view.getDrawingCache();
					Bitmap bmp = convertViewToBitmap(view);
					bmp.compress(CompressFormat.JPEG, 100, baos);
					byte[] photoBytes = baos.toByteArray();
					if (photoBytes != null) {
						new FileOutputStream(new File(signpath))
								.write(photoBytes);
					}
					result = "图片保存到" + signpath;
				}

			} else {
				result = "SD卡目前不可用";
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (baos != null) {
                    baos.close();
                }
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;

	}

	public static Bitmap loadBitmapFromView(View v) {
		if (v == null) {
			return null;
		}
		Bitmap screenshot;
		screenshot = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas c = new Canvas(screenshot);
		c.translate(-v.getScrollX(), -v.getScrollY());
		v.draw(c);
		return screenshot;
	}

	public static Bitmap convertViewToBitmap(View v){

		if (v == null) {
			return null;
		}
		Bitmap screenshot;
		screenshot = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.RGB_565);
		Canvas c = new Canvas(screenshot);
		c.translate(-v.getScrollX(), -v.getScrollY());
		v.draw(c);
		return screenshot;

//		view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
//		view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
//		view.buildDrawingCache();
//		Bitmap bitmap = view.getDrawingCache();
//
//		 return bitmap;
	}

	public static Bitmap decodeUriAsBitmap(Uri uri) {
		Bitmap bitmap = null;
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(uri.getPath());
			bitmap = BitmapFactory.decodeStream(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		return bitmap;
	}

	public static Bitmap decodeUriAsBitmap(Uri uri, String op) {
		Bitmap bitmap = null;
		FileInputStream fis = null;
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 8;
		try {
			fis = new FileInputStream(uri.getPath());
			bitmap = BitmapFactory.decodeStream(fis, null, options);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		return bitmap;
	}

	/** 保存方法 */
	public static void saveBitmap(Bitmap bit, File file) {

		try {
			FileOutputStream out = new FileOutputStream(file);
			bit.compress(CompressFormat.PNG, 90, out);
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/** 保存方法 */
	public static String saveBitmap(Bitmap bit, String  fileName) {
		ByteArrayOutputStream baos = null;
		String signpath = null;
		String result = "";
		try {
			Calendar c = Calendar.getInstance();

			String time = c.get(Calendar.YEAR) + // 得到年
					formatTime(c.get(Calendar.MONTH) + 1) + // month加一 //月
					formatTime(c.get(Calendar.DAY_OF_MONTH)) + // 日
					formatTime(c.get(Calendar.HOUR_OF_DAY)) + // 时
					formatTime(c.get(Calendar.MINUTE)) + // 分
					formatTime(c.get(Calendar.SECOND));
			// 秒
			if (PhoneinfoUtils.IsSdCardCanBeUsed()) {
				String sign_dir = Environment.getExternalStorageDirectory()
						+ File.separator + fileName;
				File file = new File(sign_dir);
				if (!file.exists()) {
					try {
						// 按照指定的路径创建文件夹
						if (!file.mkdirs()) {
							result = "创建文件失败";
						} else {
							signpath = sign_dir + File.separator + time + ".jpg";
							result = "图片保存到" + signpath;
						};
					} catch (Exception e) {
					}
				} else {
					signpath = sign_dir + File.separator + time + ".jpg";
					result = "图片保存到" + signpath;
				}
			} else {
				result = "SD卡目前不可用";
			}
			FileOutputStream out = new FileOutputStream(new File(signpath));
			bit.compress(CompressFormat.PNG, 100, out);
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
