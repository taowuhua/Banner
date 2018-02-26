package com.bank.quickpay.utils;

/**
 * Created by laomao on 15/12/7.
 */
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * map工具类
 * <p/>
 * 根据key来对value进行不同类型的数据转换
 * <p/>
 */
public class MapUtil {

    /**
     * short类型转换
     * @param map			map
     * @param key 		    key
     * @param defaultValue 类型转换失败后默认返回的值
     * @return	转换成功后的数据 或 默认数据
     */
    public static short getShort(Map map, Object key, short defaultValue) {
        Object value = map.get(key);
        if(value != null) {
            try {
                return Short.parseShort(value.toString());
            } catch (NumberFormatException e) {
                Log.e("MapUtil", "转换错误：无法将" + value + "转换成Short", e);
            }
        }
        return defaultValue;
    }

    /**
     * short类型转换
     * @param map			map
     * @param key 		    key
     * @return	转换成功后的数据 或 0
     */
    public static short getShort(Map map, Object key) {
        return getShort(map, key, (short) 0);
    }

    /**
     * int 类型转换
     * @param map			map
     * @param key 		    key
     * @param defaultValue 类型转换失败后默认返回的值
     * @return	转换成功后的数据 或 默认数据
     */
    public static int getInt(Map map, Object key, int defaultValue) {
        Object value = map.get(key);
        if(value != null) {
            try {
                return Integer.parseInt(value.toString());
            } catch (NumberFormatException e) {
                Log.e("MapUtil", "转换错误：无法将" + value + "转换成Integer", e);
            }
        }
        return defaultValue;
    }

    /**
     * int 类型转换
     * @param map			map
     * @param key 		    key
     * @return	转换成功后的数据 或 0
     */
    public static int getInt(Map map, Object key) {
        return getInt(map, key, 0);
    }

    /**
     * long 类型转换
     * @param map			map
     * @param key 		    key
     * @param defaultValue 类型转换失败后默认返回的值
     * @return	转换成功后的数据 或 默认数据
     */
    public static long getLong(Map map, Object key, long defaultValue) {
        Object value = map.get(key);
        if(value != null) {
            try {
                return Long.parseLong(value.toString());
            } catch (NumberFormatException e) {
                Log.e("MapUtil", "转换错误：无法将" + value + "转换成BigDecimal", e);
            }
        }
        return defaultValue;
    }

    /**
     * long 类型转换
     * @param map			map
     * @param key 		    key
     * @return	转换成功后的数据 或 0.0
     */
    public static long getLong(Map map, Object key) {
        return getLong(map, key, 0L);
    }

    /**
     * float类型转换
     * @param map			map
     * @param key 		    key
     * @param defaultValue 类型转换失败后默认返回的值
     * @return	转换成功后的数据 或 默认数据
     */
    public static float getFloat(Map map, Object key, float defaultValue) {
        Object value = map.get(key);
        if(value != null) {
            try {
                return Float.parseFloat(value.toString());
            } catch (NumberFormatException e) {
                Log.e("MapUtil", "转换错误：无法将" + value + "转换成Float", e);
            }
        }
        return defaultValue;
    }

    /**
     * float类型转换
     * @param map			map
     * @param key 		    key
     * @return	转换成功后的数据 或 0.0
     */
    public static float getFloat(Map map, Object key) {
        return getFloat(map, key, 0F);
    }

    /**
     * double类型转换
     * @param map			map
     * @param key 		    key
     * @param defaultValue 类型转换失败后默认返回的值
     * @return	转换成功后的数据 或 默认数据
     */
    public static double getDouble(Map map, Object key, double defaultValue) {
        Object value = map.get(key);
        if(value != null) {
            try {
                return Double.parseDouble(value.toString());
            } catch (NumberFormatException e) {
                Log.e("MapUtil", "转换错误：无法将" + value + "转换成Double", e);
            }
        }
        return defaultValue;
    }

    /**
     * double类型转换
     * @param map			map
     * @param key 		    key
     * @return	转换成功后的数据 或 默认数据
     */
    public static double getDouble(Map map, Object key) {
        return getDouble(map, key, 0.0);
    }

    /**
     * BigDecimal类型转换
     * @param map			map
     * @param key 		    key
     * @param defaultValue 类型转换失败后默认返回的值
     * @return	转换成功后的数据 或 默认数据
     */
    public static BigDecimal getBigDecimal(Map map, Object key, BigDecimal defaultValue) {
        Object value = map.get(key);
        if(value != null) {
            try {
                return BigDecimal.valueOf(Double.parseDouble(value.toString()));
            } catch (NumberFormatException e) {
                Log.e("MapUtil", "转换错误：无法将" + value + "转换成BigDecimal", e);
            }
        }
        return defaultValue;
    }

    /**
     * BigDecimal类型转换
     * @param map			map
     * @param key 		    key
     * @return	转换成功后的数据 或 BigDecimal.ZERO
     */
    public static BigDecimal getBigDecimal(Map map, Object key) {
        return getBigDecimal(map, key, BigDecimal.ZERO);
    }

    /**
     * boolean类型转换
     * @param map			map
     * @param key 		    key
     * @param defaultValue 类型转换失败后默认返回的值
     * @return	转换成功后的数据 或 默认数据
     */
    public static boolean getBoolean(Map map, Object key, boolean defaultValue) {
        Object value = map.get(key);
        if(value != null) {
            return Boolean.parseBoolean(value.toString());
        }
        return defaultValue;
    }

    /**
     * boolean类型转换
     * @param map			map
     * @param key 		    key
     * @return	转换成功后的数据 或 false
     */
    public static boolean getBoolean(Map map, Object key) {
        return getBoolean(map, key, false);
    }

    /**
     * String 类型转换
     * @param map			map
     * @param key 		    key
     * @param defaultValue 类型转换失败后默认返回的值
     * @return	转换成功后的数据 或 默认数据
     */
    public static String getString(Map map, Object key, String defaultValue) {
        Object value = map.get(key);
        if(value != null) {
            return value.toString();
        }
        return defaultValue;
    }

    /**
     * String 类型转换
     * @param map			map
     * @param key 		    key
     * @return	转换成功后的数据 或 空字符串
     */
    public static String getString(Map map, Object key) {
        return getString(map, key, "");
    }

    /**
     * 泛型类型转换
     * @param map			map
     * @param key 		    key
     * @param defaultValue 类型转换失败后默认返回的值
     * @return	转换成功后的数据 或 默认数据
     */
    public static <T> T get(Map map, Object key, T defaultValue) {
        Object value = map.get(key);
        if(value != null) {
            try {
                return (T) value;
            } catch (ClassCastException e) {
                Log.e("MapUtil", "转换错误：无法将" + value + "转换成" + defaultValue != null ? defaultValue.getClass().getName() : "null", e);
            }
        }
        return defaultValue;
    }

    public static Map<String, Object> jsonObjectToMap(JSONObject json)
            throws JSONException {
        if (json != null) {
            Map<String, Object> m = new HashMap<String, Object>();
            Iterator iter = json.keys();
            while (iter.hasNext()) {
                Object key = iter.next();
                Object value = json.get(key.toString());
                if(value instanceof JSONObject) {
                    m.put(key.toString(), jsonObjectToMap((JSONObject)value));
                } else if(value instanceof JSONArray) {
                    m.put(key.toString(), jsonArrayToList((JSONArray)value));
                } else {
                    m.put(key.toString(), value.toString());
                }
            }
            return m;
        }
        return null;
    }

    private static List<Map<String, Object>> jsonArrayToList(JSONArray json)
            throws JSONException {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> m = null;
        if (json != null) {
            for (int i = 0; i < json.length(); i++) {
                m = jsonObjectToMap(json.getJSONObject(i));
                list.add(m);
            }
        }
        return list;
    }
}