package com.huyunit.bugs.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: bobo
 * Date: 13-5-13
 * Time: 下午1:35
 * To change this template use File | Settings | File Templates.
 */
public class GsonUtils {

    private static Gson gson = null;

    private static Gson exposeGson = null;

    private static final String TAG = "GsonUtils";

    /**
     * 得到ObjectMapper
     *
     * @return ObjectMapper
     */
    public static Gson getGson() {
        if (gson == null) {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(Date.class, new GsonDateTypeAdapter());
            gson = gsonBuilder.create();
        }
        return gson;
    }

    /**
     * 得到ObjectMapper
     *
     * @return ObjectMapper
     */
    public static Gson getExposeGson() {
        if (exposeGson == null) {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(Date.class, new GsonDateTypeAdapter());
            gsonBuilder.excludeFieldsWithoutExposeAnnotation();
            exposeGson = gsonBuilder.create();
        }
        return exposeGson;
    }

    /**
     * 将json转换为bean
     *
     * @param <T>   对象类型
     * @param json
     * @param clazz
     * @return
     */
    public static <T> T toObject(String json, Class<T> clazz) {
        if (!StringUtils.isNotEmpty(json)) {
            return null;
        }
        return getGson().fromJson(json, clazz);
    }



    /**
     * 将json转换为bean
     *
     * @param <T>   对象类型
     * @param json
     * @param type
     * @return
     */
    public static <T> T toObject(String json, Type type) {
        if (!StringUtils.isNotEmpty(json)) {
            return null;
        }
        try {
            return getGson().fromJson(json, type);
        } catch (IllegalStateException e) {
            e.printStackTrace();
            LogUtil.e(e.getMessage(), e);
        } catch (Exception e){
            e.printStackTrace();
            LogUtil.e(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 将json转换为bean
     *
     * @param <T>   对象类型
     * @param json
     * @param args1
     * @param args2
     * @return
     */
    public static <T> T toObject(String json, Class<T> args1, Class args2) {
        if (!StringUtils.isNotEmpty(json)) {
            return null;
        }
        return getGson().fromJson(json, type(args1, args2));
    }

    /**
     * 将json转换为Map
     * @param json
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T toMap(String json, Type type) {
        if (!StringUtils.isNotEmpty(json)) return null;
        return getGson().fromJson(json, type);
    }

    /**
     * 将json数组转换成List集合对象
     *
     * @param json
     * @param type
     * @param <T>
     * @return
     */
    public static <T> List<T> toListByObject(String json, Type type) {
        if (!StringUtils.isNotEmpty(json)) {
            return null;
        }
        return getGson().fromJson(json, type);
    }

    /**
     * 将json数组转换成List集合对象
     *
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> toListByObject(String json, Class<T> clazz) {
        if (!StringUtils.isNotEmpty(json)) {
            return null;
        }
        List<T> list = new ArrayList<T>();
        try {
            list = getGson().fromJson(json, new TypeToken<List<T>>() {
            }.getType());
            return getList(clazz, list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static <T> List<T> getList(Class<T> clazz, List<T> list) {
        List<T> resultList = new ArrayList<T>();
        try {
            Method[] methods = clazz.getMethods();
            for (int i = 0; i < list.size(); i++) {
                Object obj = clazz.newInstance();
                Map<String, Object> mapObj = (Map<String, Object>) list.get(i);
                for (int j = 0; j < methods.length; j++) {
                    Method method = methods[j];
                    String key = "";
                    if (method.getName().startsWith("set"))
                        key = method.getName().substring(3, 4).toLowerCase() + method.getName().substring(4);
                    if (mapObj.keySet().contains(key)) {
                        Object value = mapObj.get(key);
                        //TODO 类型判断 Integer  Date 注意
                        if (value instanceof Double)
                            value = new Date(Double.doubleToLongBits((Double) value));
                        method.invoke(obj, new Object[]{value});
                    }
                }
                resultList.add((T) obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultList;
    }

    /**
     * 将bean转换为json
     *
     * @param obj
     * @return
     */
    public static String toJson(Object obj) {
        return getGson().toJson(obj);
    }

    /**
     * 将list转换为json
     *
     * @param obj
     * @param type
     * @return
     */
    public static String toJson(Object obj, Type type) {
        return getGson().toJson(obj, type);
    }

    /**
     * 将bean转换为json
     *
     * @param obj
     * @return
     */
    public static String toExposeJson(Object obj) {
        return getExposeGson().toJson(obj);
    }

    /**
     * 将list转换为json
     *
     * @param obj
     * @param type
     * @return
     */
    public static String toExposeJson(Object obj, Type type) {
        return getExposeGson().toJson(obj, type);
    }

    static ParameterizedType type(final Class raw, final Type... args) {
        return new ParameterizedType() {
            public Type getRawType() {
                return raw;
            }

            public Type[] getActualTypeArguments() {
                return args;
            }

            public Type getOwnerType() {
                return null;
            }
        };
    }

}
