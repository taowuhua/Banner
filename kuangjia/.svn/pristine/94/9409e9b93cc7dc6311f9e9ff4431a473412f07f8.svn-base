package com.bank.quickpay.utils;


import com.bank.quickpay.bean.MsgInfo;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by XCC on 2016/5/9.
 */
public class DataUtil {
    /**
     * 验证是不是手机号
     * @param mobiles 参数
     * @return 验证结果
     */
    public static boolean isMobileNO(String mobiles){
        Pattern p = Pattern.compile("^[1][3,4,5,6,7,8,9][0-9]{9}$");

        Matcher m = p.matcher(mobiles);
        return m.matches();

    }

    /**
     * 服务端返回数据获取右侧数据
     * @param
     * "account2": "收款账户|6222021602005647690",
    "status": "交易状态|0",
    "signPic": "签名|https://119.254.93.70:444/00800118/A000854000/A000854340/TRAN/1462345998347A000854340.png",
    "localDate": "交易日期|20160504",
    "mobileno": "付款账户|15866674053",
    "termId": "终端号|80018870000509415929",
    "localLogNo": "交易流水|514524",
    "fee": "手续费|249",
    "amount": "总金额|10000",
    "bizAmount": "金额|10000",
    "branchId": "机构ID|00800118",
    "branchName": "机构名称|100000001010020",
    "account": "卡号|6229180017615404",
    "payType": "支付方式|01",
    "localTime": "交易时间|151317",
    "merchantId": "交易类型编号|0002000043",
    "orderId": "订单号|1605045106411682",
    "transName": "交易名称|闪付"
     * @return
     */
    public static String getRightValue(String value) {
        String[] values;
        if (value != null) {
            values = value.split("\\|");
            if (values.length == 2) {
                return values[1];
            }
            return "";
        }
        return "";
    }
    /*
        * 将16进制数字解码成字符串,适用于所有字符（包括中文）
        */
    public static String decode(String bytes) {
        String hexString = "0123456789abcdef";
        ByteArrayOutputStream baos = new ByteArrayOutputStream( bytes.length() / 2);
        // 将每2位16进制整数组装成一个字节
        for (int i = 0; i < bytes.length(); i += 2) {
            baos.write((hexString.indexOf(bytes.charAt(i)) << 4 | hexString
                    .indexOf(bytes.charAt(i + 1))));
        }
        return new String(baos.toByteArray());
    }
    /*
    * 将字符串编码成16进制数字,适用于所有字符（包括中文）
    */
    public static String encode(String str) throws UnsupportedEncodingException {
        String hexString = "0123456789abcdef";
        // 根据默认编码获取字节数组
        byte[] bytes = str.getBytes("UTF-8");
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        // 将字节数组中每个字节拆解成2位16进制整数
        for (int i = 0; i < bytes.length; i++) {
            sb.append(hexString.charAt((bytes[i] & 0xf0) >> 4));
            sb.append(hexString.charAt((bytes[i] & 0x0f) >> 0));
        }
        return sb.toString();
    }

    /**
     * 去重
     * @param msgInfoList
     * @return
     */
    public static ArrayList<MsgInfo> removeDuplicate(ArrayList<MsgInfo> msgInfoList){
        try {
            if(msgInfoList!=null&&msgInfoList.size()>0){
                for  ( int  i  =   0 ; i  <  msgInfoList.size()  -   1 ; i ++ )  {
                    for  ( int  j  =  msgInfoList.size()  -   1 ; j  >  i; j -- )  {
                        if  (msgInfoList.get(j).getNoticeCode().equals(msgInfoList.get(i).getNoticeCode()))  {
                            msgInfoList.remove(j);
                        }
                    }
                }
            }
        } catch (Exception e) {
        }

        return msgInfoList;
    }
    public static List<MsgInfo> removeDuplicate(List<MsgInfo> msgInfoList){
        try {
            if(msgInfoList!=null&&msgInfoList.size()>0){
                for  ( int  i  =   0 ; i  <  msgInfoList.size()  -   1 ; i ++ )  {
                    for  ( int  j  =  msgInfoList.size()  -   1 ; j  >  i; j -- )  {
                        if  (msgInfoList.get(j).getNoticeCode().equals(msgInfoList.get(i).getNoticeCode()))  {
                            msgInfoList.remove(j);
                        }
                    }
                }
            }
        } catch (Exception e) {
        }

        return msgInfoList;
    }

}
