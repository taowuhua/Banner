package com.bank.quickpay.utils;


import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bank.quickpay.R;
import com.bank.quickpay.config.AppConfig;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class BanksUtils {

	private static int id;

	/**
	 * 联网获取银行图标
	 * @param context
	 * @param sid
	 * @param imageView
     */
	public static void selectIcoidToImgView(Context context,String sid, ImageView imageView){
		String imgUrl= AppConfig.BANKIMG_URL.replace("placeholder",sid);
		Glide.with(context)
				.load(imgUrl)
				.error(R.drawable.bank_default)//加载失败默认显示的图片
				.diskCacheStrategy(DiskCacheStrategy.ALL)//磁盘缓存
				.dontAnimate()//无动画
				.into(imageView);
	}
//	public static int selectIcoid(String sid) {
//		int icoid = 0;
//
//		if(!TextUtils.isEmpty(sid)&&sid.length()>6){
//			sid = sid.substring(1,4);
//		}
//
//		if (null != sid && !"".equals(sid)) {
//			id = Integer.parseInt(sid);
//		} else {
//			id = 0;
//		}
//
//		// 银行号 与图像比对
//		switch (id) {
//		case 102:
//			icoid = R.drawable.bank_icbc;
//			break;
//		case 103:
//			icoid = R.drawable.bank_abc;
//			break;
//		case 104:
//			icoid = R.drawable.bank_boc;
//			break;
//		case 105:
//			icoid = R.drawable.bank_ccb;
//			break;
//		case 301:
//			icoid = R.drawable.bank_comm;
//			break;
//		case 302:
//			icoid = R.drawable.bank_citic;
//			break;
//		case 303:
//			icoid = R.drawable.bank_ceb;
//			break;
//		case 304:
//			icoid = R.drawable.bank_hx;
//			break;
//		case 305:
//			icoid = R.drawable.bank_cmbc;
//			break;
//		case 306:
//			icoid = R.drawable.bank_gdb;
//			break;
//		case 307:
//			icoid = R.drawable.bank_spabank;
//			break;
//		case 308:
//			icoid = R.drawable.bank_cmb;
//			break;
//		case 309:
//			icoid = R.drawable.bank_cib;
//			break;
//		case 310:
//			icoid = R.drawable.bank_spdb;
//			break;
//		case 403:
//			icoid = R.drawable.bank_psbc;
//			break;
//		default:
//			icoid = R.drawable.bank_default;
//			break;
//		}
//		return icoid;
//	}

	/**
	 * 根据bankid获取bankName
	 * @param sid
	 * @param bankname
     * @return
     */
	@Deprecated
	public static String selectshortname(String sid, String bankname) {

		if (null == sid || "".equals(sid)) {
			return bankname;
		}
		if(!TextUtils.isEmpty(sid)&&sid.length()>6){
			sid = sid.substring(1,4);
		}
		id = Integer.parseInt(sid);

		String shortname = "";

		switch (id) {
		case 102:
			shortname = "工商银行";
			break;
		case 103:
			shortname = "农业银行";
			break;
		case 104:
			shortname = "中国银行";
			break;
		case 105:
			shortname = "建设银行";
			break;
		case 301:
			shortname = "交通银行";
			break;
		case 302:
			shortname = "中信银行";
			break;
		case 303:
			shortname = "光大银行";
			break;
		case 304:
			shortname = "华夏银行";
			break;
		case 305:
			shortname = "民生银行";
			break;
		case 306:
			shortname = "广发银行";
			break;
		case 307:
			shortname = "平安银行";
			break;
		case 308:
			shortname = "招商银行";
			break;
		case 309:
			shortname = "兴业银行";
			break;
		case 310:
			shortname = "上海浦东发展银行";
			break;
		case 313:
			shortname = "城市商业银行";
			break;
		case 322:
			shortname = "农村商业银行";
			break;
		case 402:
			shortname = "信用社";
			break;
		case 403:
			shortname = "中国邮政储蓄银行";
			break;
		case 501:
			shortname = "汇丰银行";
			break;
		case 502:
			shortname = "东亚银行";
			break;
		case 531:
			shortname = "花旗银行";
			break;
		case 671:
			shortname = "渣打银行";
			break;
		}

		return "".equals(shortname) ? bankname : shortname;
	}
}
