package com.bank.quickpay.utils;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.bank.quickpay.config.AppConfig;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 异步图片下载器
 * 
 * @author huangyu
 * 
 */
public class ImageLoaderUtil {
	private static Map<String, SoftReference<Bitmap>> imageCache = new HashMap<String,  SoftReference<Bitmap>>();

//	 static ThreadPool threadPool_image = new ThreadPool(2);
	public static ExecutorService pool = Executors.newFixedThreadPool(2); 

	public static void clearImageMap() {
		// if(imageCache!=null && imageCache.size()>100){//当缓存大于100的时候
		imageCache.clear();
		// }
	}

	public static Bitmap loadDrawable(final String imageUrl, final ImageCallback callback) {

		 Bitmap drawable = null;
		//在内存缓存中，则返回Bitmap对象  
        if(imageCache.containsKey(imageUrl))  
        {  
            SoftReference<Bitmap> reference = imageCache.get(imageUrl);  
            Bitmap bitmap = reference.get();  
            if(bitmap != null)  
            {  
                return bitmap;  
            }  
        } 
        
		if (avaiableSdcard()) {// 如果有sd卡，读取sd卡
			drawable = getPicByPath(AppConfig.imageCachePath, imageUrl);
			if (drawable != null) {
				LogUtil.showLog("get bitmap from disk");
				 imageCache.put(imageUrl, new SoftReference<Bitmap>(drawable));
				return drawable;
			}
		} else {// 如果没有sd卡，读取手机里面
			drawable = getPicByPath(AppConfig.imageCachePath_data, imageUrl);
			if (drawable != null) {
				LogUtil.showLog("get bitmap from disk");
				 imageCache.put(imageUrl, new SoftReference<Bitmap>(drawable));
				return drawable;
			}
		}

		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				callback.imageLoaded((Bitmap) msg.obj, imageUrl);
			}
		};
		Runnable task = new Runnable() {
			@Override
            public void run() {
				Bitmap drawable = loadImageFromUrl(imageUrl);
				handler.sendMessage(handler.obtainMessage(0, drawable));
			};
		};

//		new Thread(task).start();
			pool.execute(task);
		
		return null;
	}

	/**
	 * 判断是否存在sd卡
	 * 
	 * @return
	 */
	public static boolean avaiableSdcard() {
		String status = Environment.getExternalStorageState();

		if (status.equals(Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 获取图片
	 * 
	 * @param picName
	 * @return
	 */
	public static Drawable getPic_Draw_ByPath(String path, String picName) {
		picName = picName.substring(picName.lastIndexOf("/") + 1);
		String filePath = path + picName;
		return Drawable.createFromPath(filePath);
	}

	/**
	 * 获取图片
	 * 
	 * @param picName
	 * @return
	 */
	public static Bitmap getPicByPath(String path, String picName) {
		if(!"".equals(picName)&&picName!=null){
			picName = picName.substring(picName.lastIndexOf("/") + 1);
			String filePath = path + picName;
			
			//判断文件是否存在
				File file = new File(filePath);
				if(!file.exists()){//文件不存在
					return null;
				}
			
			Bitmap bitmap = BitmapFactory.decodeFile(filePath);
			return bitmap;
		}else{
			return null;
		}
		
	}

	public static SoftReference<Bitmap> loadDrawable(String imageUrl) {
		if (imageCache.containsKey(imageUrl)) {
			SoftReference<Bitmap> softReference = imageCache.get(imageUrl);
			if (softReference != null) {
				return softReference;
			} else {
				return null;
			}
		}
		return null;
	}

	protected static Bitmap loadImageFromUrl(String imageUrl) {
		try {
			URL url = new URL(imageUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(10 * 1000);
			conn.connect();
			InputStream inputStream = conn.getInputStream();
			Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
			inputStream.close();
			conn.disconnect();
			if(bitmap!=null){
				LogUtil.showLog("get bitmap from net");
				 imageCache.put(imageUrl, new SoftReference<Bitmap>(bitmap));
				 savePic(bitmap, imageUrl);// 保存图片
			}
			return bitmap;
		} catch (Exception e) {
			return null;
		}
	}

	public static void savePic(Bitmap bitmap, String imageUrl) {
		if (bitmap != null && imageUrl != null && !"".equals(imageUrl)) {
			if (avaiableSdcard()) {// 如果有sd卡，保存在sd卡
				savePicToSdcard(bitmap, imageUrl);
			} else {// 如果没有sd卡，保存在手机里面
				saveToDataDir(bitmap, imageUrl);
			}
		}
	}

	/**
	 * 将图片保存在sd卡
	 * 
	 * @param bitmap
	 *            图片
	 * @param picName
	 *            图片名称（同新闻id名）
	 */
	private static void savePicToSdcard(Bitmap bitmap, String picName) {

		picName = picName.substring(picName.lastIndexOf("/") + 1);
		String path = AppConfig.imageCachePath;
		File file = new File(path + picName);
		FileOutputStream outputStream;
		if (!file.exists()) {
			try {
				file.getParentFile().mkdirs();
				file.createNewFile();
				outputStream = new FileOutputStream(file);
				bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
				outputStream.close();
			} catch (Exception e) {
				// Log.e("", e.toString());
			}
		}
	}

	/**
	 * 保存文件到应用目录
	 * 
	 * @param bitmap
	 * @param fileName
	 *            文件名称
	 */
	static void saveToDataDir(Bitmap bitmap, String fileName) {
		fileName = fileName.substring(fileName.lastIndexOf("/") + 1);
		String path = AppConfig.imageCachePath_data;
		File file = new File(path + fileName);
		FileOutputStream outputStream;
		if (!file.exists()) {
			try {
				file.getParentFile().mkdirs();
				file.createNewFile();
				outputStream = new FileOutputStream(file);
				bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
				outputStream.close();
			} catch (Exception e) {
				Log.e("", e.toString());
			}
		}
	}

	public interface ImageCallback {
		public void imageLoaded(Bitmap imageDrawable, String imageUrl);
	}
}
