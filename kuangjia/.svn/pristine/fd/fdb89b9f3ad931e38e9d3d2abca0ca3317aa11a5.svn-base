
package com.bank.quickpay.utils;

import android.text.TextUtils;

public class StringUnit {
    public static String impaycardJiaMi(String cardnum) {
        if (cardnum != null) {
            int length = cardnum.length();
            if (length == 16) {
                return "(尾号"+ cardnum.substring(length - 4, length)+")";
            }
            if (length == 19) {
                return "(尾号"+cardnum.substring(length - 4, length)+")";
            }

        }
        return cardnum;
    }
    public static String cardJiaMi(String cardnum) {
        if (cardnum != null) {
            int length = cardnum.length();
            if (length == 16) {
                return cardnum.substring(0, 6) + "******" + cardnum.substring(length - 4, length);
            }
            if (length == 19) {
                return cardnum.substring(0, 6) + "******"
                        + cardnum.substring(length - 4, length);
            }

        }
        return cardnum;
    }

    public static String cardShortShow(String cardnum) {
        StringBuilder sb = new StringBuilder();
        if (!TextUtils.isEmpty(cardnum)) {
            try {
                sb.append(cardnum.substring(0, 2));
                sb.append(cardnum.substring(cardnum.length() - 4, cardnum.length()));
                sb.append(cardnum.length());
                return sb.toString();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public static String certPidJiaMi(String certPid) {
        if (certPid != null) {
            int length = certPid.length();
            if (length == 18) {
                return certPid.substring(0, 6) + "******" + certPid.substring(length - 4, length);
            }
            if (length == 15) {
                return certPid.substring(0, 6) + "*********"
                        + certPid.substring(length - 4, length);
            }

        }
        return certPid;
    }

    public static String phoneJiaMi(String phonenum) {
        if (phonenum != null) {
            int length = phonenum.length();
            if (length == 11) {
                return phonenum.substring(0, 3) + "****" + phonenum.substring(7, 11);
            }

        }
        return phonenum;
    }

    public static String realNameJiaMi(String realName) {
        if (realName != null) {
            int length = realName.length();
            if (length > 1) {
                return "*" + realName.substring(1, length);
            }
        }
        return realName;
    }

    public static String lengthLimit(int length, String ss) {
        if (ss == null) {
            return "";
        } else {
            if (ss.length() < length) {
                return ss;
            } else {
                return ss.substring(0, length) + "……";
            }
        }
    }

    public static String formatPrice(Double price) {
        final java.text.DecimalFormat df = new java.text.DecimalFormat("0.00");
        if (price == null || price == 0) {
            return "￥0.00";
        } else {
            return "￥" + df.format(price);
        }
    }

    public static String formatPriceToString(Double price) {
        final java.text.DecimalFormat df = new java.text.DecimalFormat("0.00");
        if (price == null || price == 0) {
            return "0.00";
        } else {
            return "" + df.format(price);
        }
    }

}
