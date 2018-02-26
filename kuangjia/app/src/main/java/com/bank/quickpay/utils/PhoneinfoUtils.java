package com.bank.quickpay.utils;

/**
 * Created by laomao on 16/4/20.
 */


import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.telephony.CellLocation;
import android.telephony.NeighboringCellInfo;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.amap.api.maps2d.model.LatLng;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class PhoneinfoUtils {


    /** 获取屏幕的宽度 */
    public final static int getWindowsWidth(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }
    /** 获取屏幕的宽度 */
    public final static int getWindowsWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        LogUtil.showLog(width+"==width");
//        int height = wm.getDefaultDisplay().getHeight();
        return width;
    }

    /** 获取屏幕的宽度 */
    public final static int getWindowsHight(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }
    /** 获取屏幕的宽度 */
    public final static int getWindowsHight(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
//        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        LogUtil.showLog(height+"==height");
        return height;
    }

    public static  String getMobileNO(Context ct) throws SecurityException{
        TelephonyManager tm=null;
        try {
            tm = (TelephonyManager) ct.getSystemService(Context.TELEPHONY_SERVICE);
            return tm.getLine1Number();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "";
        }

    }

    public static String getMobileSerialNum(Context ct){
        String ss = getDeviceId(ct);
        StringBuilder sb = new StringBuilder();
        sb.append(ss);
        for(int i=ss.length(); i<=40; i++){
            sb.append("0");
        }
        return sb.toString();
    }


    /**
     *  判断手机SD卡是否可以使用SD card can be used
     */
    public static boolean IsSdCardCanBeUsed() {
        try {
            return Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取手机所在位置的经纬度，返回Location
     * getLocationInfo(context).getLongitude(); //经度
     * getLocationInfo(context).getLatitude(); //纬度
     */
    public static LatLng getLocationInfo(Context context){
        double latitude = 0.0;
        double longitude = 0.0;
        Location location = null;
        LocationManager locationManager = (LocationManager)
                context.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            ContextCompat.checkSelfPermission(context,Manifest.permission.ACCESS_FINE_LOCATION);
            //TODO 这个地方对于6.0权限要单独判断
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                latitude = location.getLatitude(); // 纬度
                longitude = location.getLongitude(); // 经度

            }
            if(latitude>0 && longitude>0){
                LogUtil.showLog("GPS定位结果： 经度: "+ location.getLongitude() +" 纬度: "+ location.getLatitude());
                return  new LatLng(latitude, longitude);// 纬度,经度
            }
        }
        // 如果GPS定位不到，就采用网络定位
        LocationListener locationListener = new LocationListener() {

            // Provider的状态在可用、暂时不可用和无服务三个状态直接切换时触发此函数
            @Override
            public void onStatusChanged(String provider, int status,
                                        Bundle extras) {

            }

            // Provider被enable时触发此函数，比如GPS被打开
            @Override
            public void onProviderEnabled(String provider) {

            }

            // Provider被disable时触发此函数，比如GPS被关闭
            @Override
            public void onProviderDisabled(String provider) {

            }

            // 当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
            @Override
            public void onLocationChanged(Location location) {
                if (location != null) {
                    LogUtil.showLog("网络定位结果： 经度: "+ location.getLongitude() +" 纬度: "+ location.getLatitude());
                }
            }
        };
//		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
//						10000, 0, locationListener);  需要更新就加，
        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (location != null) {
            latitude = location.getLatitude(); // 纬度
            longitude = location.getLongitude(); // 经度
//			LOG.showLog("手机定位结果： 经度: "+ location.getLongitude() +" 纬度: "+ location.getLatitude());
        }
        return  new LatLng(latitude, longitude);// 纬度,经度

    }



    //	 public static String getLocalIpAddress() {
//	        try {
//	            for (Enumeration<NetworkInterface> en = NetworkInterface
//	                    .getNetworkInterfaces(); en.hasMoreElements();) {
//	                NetworkInterface intf = en.nextElement();
//	                for (Enumeration<InetAddress> enumIpAddr = intf
//	                        .getInetAddresses(); enumIpAddr.hasMoreElements();) {
//	                    InetAddress inetAddress = enumIpAddr.nextElement();
//	                    if (!inetAddress.isLoopbackAddress()) {
//	                        return inetAddress.getHostAddress().toString();
//	                    }
//	                }
//	            }
//	        } catch (SocketException ex) {
//	            Log.e("WifiPreference IpAddress", ex.toString());
//	        }
//	        return null;
//	    }
    public static String getlocalip(Context cx){
        WifiManager wifiManager = (WifiManager)cx.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();

        LogUtil.showLog("int ip ", ipAddress+"");
        if(ipAddress==0) {
            return null;
        }
        return ((ipAddress & 0xff)+"."+(ipAddress>>8 & 0xff)+"."
                +(ipAddress>>16 & 0xff)+"."+(ipAddress>>24 & 0xff));
    }

    public static String getPsdnIp() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf
                        .getInetAddresses(); enumIpAddr.hasMoreElements();) {

                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()
                            && inetAddress instanceof Inet4Address) {

                        // if (!inetAddress.isLoopbackAddress() && inetAddress
                        // instanceof Inet6Address) {

                        return inetAddress.getHostAddress().toString();

                    }

                }

            }

        } catch (Exception e) {

        }

        return "";

    }

    public static int inetAddressToInt(InetAddress inetAddr)
            throws IllegalArgumentException {
        byte[] addr = inetAddr.getAddress();
        if (addr.length != 4) {
            throw new IllegalArgumentException("Not an IPv4 address");
        }
        return ((addr[3] & 0xff) << 24) | ((addr[2] & 0xff) << 16)
                | ((addr[1] & 0xff) << 8) | (addr[0] & 0xff);
    }
    public static String getLocalIpAddress() {
        String ipaddress="";

        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf
                        .getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {

                        ipaddress=ipaddress+";"+ inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
            LogUtil.showLog("WifiPreference IpAddress", ex.toString());
        }
        return ipaddress;
    }


    // 正在运行的服务列表 获取正在运行的Service信息
    public static String getRunningServicesInfo(Context context) {
        StringBuffer serviceInfo = new StringBuffer();
        final ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningServiceInfo> services = activityManager
                .getRunningServices(100);
        Iterator<RunningServiceInfo> l = services.iterator();
        while (l.hasNext()) {
            RunningServiceInfo si = (RunningServiceInfo) l.next();
            serviceInfo.append("pid: ").append(si.pid);
            serviceInfo.append(" process: ").append(si.process);
            serviceInfo.append(" service: ").append(si.service);
            serviceInfo.append(" crashCount: ").append(si.crashCount);
            serviceInfo.append(" clicentCount: ").append(si.clientCount);
            serviceInfo.append(" activeSince:").append(si.activeSince);
            serviceInfo.append(" lastActivityTime: ").append(
                    si.lastActivityTime);
            serviceInfo.append(" ");
        }
        return serviceInfo.toString();
    }

    public static int getCallState(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
		/*
		 * 电话状态： 1.tm.CALL_STATE_IDLE=0 无活动 2.tm.CALL_STATE_RINGING=1 响铃
		 * 3.tm.CALL_STATE_OFFHOOK=2 摘机
		 */

        return tm.getCallState();// int

    }

    public static CellLocation getCellLocation(Context context) throws SecurityException {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
		/*
		 * 电话方位：
		 */

        return tm.getCellLocation();// CellLocation

    }

    public static String getDeviceId(Context context) throws SecurityException{
        
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
		/*
		 * 唯一的设备ID： GSM手机的 IMEI 和 CDMA手机的 MEID. Return null if device ID is not
		 * available.
		 */
        return tm.getDeviceId()+"";// String

    }

    public static String getDeviceSoftwareVersion(Context context) throws SecurityException{
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
		/*
		 * 设备的软件版本号： 例如：the IMEI/SV(software version) for GSM phones. Return
		 * null if the software version is not available.
		 */
        return tm.getDeviceSoftwareVersion();// String

    }

    public static String getLine1Number(Context context)throws SecurityException {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
		/*
		 * 手机号： GSM手机的 MSISDN. Return null if it is unavailable.
		 */

        return tm.getLine1Number();// String

    }

    public static List<NeighboringCellInfo> getNeighboringCellInfo(
            Context context) throws SecurityException{
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
		/*
		 * 附近的电话的信息: 类型：List<NeighboringCellInfo>
		 * 需要权限：android.Manifest.permission#ACCESS_COARSE_UPDATES
		 */
        return tm.getNeighboringCellInfo();// List<NeighboringCellInfo>

    }

    public static String getNetworkCountryIso(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
		/*
		 * 获取ISO标准的国家码，即国际长途区号。 注意：仅当用户已在网络注册后有效。 在CDMA网络中结果也许不可靠。
		 */
        return tm.getNetworkCountryIso();// String

    }
    public static String getNetworkOperatorName(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
		/*
		 * 按照字母次序的current registered operator(当前已注册的用户)的名字 注意：仅当用户已在网络注册时有效。
		 * 在CDMA网络中结果也许不可靠。
		 */
        return tm.getNetworkOperatorName();// String
    }
    public static  int getNetworkType(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
		/*
		 * 当前使用的网络类型： 例如： NETWORK_TYPE_UNKNOWN 网络类型未知 0 NETWORK_TYPE_GPRS GPRS网络
		 * 1  NETWORK_TYPE_EDGE EDGE网络
		 * 2  NETWORK_TYPE_UMTS UMTS网络
		 * 3  NETWORK_TYPE_HSDPA HSDPA网络
		 * 8  NETWORK_TYPE_HSUPA HSUPA网络
		 * 9  NETWORK_TYPE_HSPA HSPA网络
		 * 10 NETWORK_TYPE_CDMA CDMA网络,IS95A 或 IS95B.
		 * 4  NETWORK_TYPE_EVDO_0 EVDO网络, revision 0.
		 * 5  NETWORK_TYPE_EVDO_A EVDO网络,revision A.
		 * 6 NETWORK_TYPE_1xRTT 1xRTT网络 7
		 */
        return tm.getNetworkType();// int;
    }
    public static  int getPhoneType(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
		/*
		 * 手机类型： 例如：
		 * PHONE_TYPE_NONE 无信号
		 * PHONE_TYPE_GSM  GSM信号
		 * PHONE_TYPE_CDMA CDMA信号
		 */
        return tm.getPhoneType();// int
    }

    public static String getSimCountryIso(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
		/*
		 * Returns the ISO country code equivalent for the SIM provider's
		 * country code. 获取ISO国家码，相当于提供SIM卡的国家码。
		 */

        return tm.getSimCountryIso();// String
    }
    public static String getSimOperator(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		/*
		 * Returns the MCC+MNC (mobile country code + mobile network code) of
		 * the provider of the SIM. 5 or 6 decimal digits.
		 * 获取SIM卡提供的移动国家码和移动网络码.5或6位的十进制数字. SIM卡的状态必须是
		 * SIM_STATE_READY(使用getSimState()判断).
		 */

        return tm.getSimOperator();// String
    }
    public static String getSimOperatorName(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		/*
		 * 服务商名称： 例如：中国移动、联通 SIM卡的状态必须是 SIM_STATE_READY(使用getSimState()判断).
		 */
        return tm.getSimOperatorName();//
    }
    public static  String getSimSerialNumber(Context context) throws SecurityException{
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		/* SIM卡的序列号： 需要权限：READ_PHONE_STATE */
        return tm.getSimSerialNumber();// String
    }
    public static  int getSimState(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		/*
		 * SIM的状态信息： SIM_STATE_UNKNOWN 未知状态 0 SIM_STATE_ABSENT 没插卡 1
		 * SIM_STATE_PIN_REQUIRED 锁定状态，需要用户的PIN码解锁 2 SIM_STATE_PUK_REQUIRED
		 * 锁定状态，需要用户的PUK码解锁 3 SIM_STATE_NETWORK_LOCKED 锁定状态，需要网络的PIN码解锁 4
		 * SIM_STATE_READY 就绪状态 5
		 */
        return tm.getSimState();// int
    }
    public static String getSubscriberId(Context context)throws SecurityException {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		/*
		 * 唯一的用户ID： 例如：IMSI(国际移动用户识别码) for a GSM phone. 需要权限：READ_PHONE_STATE
		 */
        return tm.getSubscriberId();// String
    }
    public static  String getVoiceMailAlphaTag(Context context) throws SecurityException{
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		/* 取得和语音邮件相关的标签，即为识别符 需要权限：READ_PHONE_STATE */
        return tm.getVoiceMailAlphaTag();// String
    }
    public static String  getVoiceMailNumber(Context context)throws SecurityException {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		/*
		 * 获取语音邮件号码： 需要权限：READ_PHONE_STATE
		 */
        return tm.getVoiceMailNumber();// String
    }
    public static  boolean hasIccCard(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		/*
		 * ICC卡是否存在
		 */
        return tm.hasIccCard();// boolean
    }
    public static  boolean isNetworkRoaming(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		/*
		 * 是否漫游: (在GSM用途下)
		 */
        return tm.isNetworkRoaming();//
    }

    public static String getUUid(Activity activity) {
        return UUID.randomUUID().toString();
    }

    public static String getAndroidId(Activity activity) {
        return Settings.Secure.getString(activity.getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }

    // 获取手机MAC地址
    public static String getMacAddress(Activity activity) {
        String result = "";
        WifiManager wifiManager = (WifiManager) activity.getApplicationContext()
                .getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        result = wifiInfo.getMacAddress();
        return result;
    }

    public static String getTotalMemory(Activity activity) {
        String str1 = "/proc/meminfo";// 系统内存信息文件
        String str2;
        String[] arrayOfString;
        long initial_memory = 0;
        try {
            FileReader localFileReader = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(
                    localFileReader, 8192);
            str2 = localBufferedReader.readLine();// 读取meminfo第一行，系统总内存大小

            arrayOfString = str2.split("\\s+");
            for (String num : arrayOfString) {
                LogUtil.showLog(str2, num + "\t");
            }

            initial_memory = Integer.valueOf(arrayOfString[1]).intValue() * 1024;// 获得系统总内存，单位是KB，乘以1024转换为Byte
            localBufferedReader.close();

        } catch (IOException e) {
        }
        return Formatter.formatFileSize(activity, initial_memory);// Byte转换为KB或者MB，内存大小规格化
    }

}
