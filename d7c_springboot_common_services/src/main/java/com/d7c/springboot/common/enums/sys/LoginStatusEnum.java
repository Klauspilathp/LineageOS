package com.d7c.springboot.common.enums.sys;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

import com.d7c.plugins.core.enums.ISEnum;

/**
 * @Title: LoginStatusEnum
 * @Package: com.d7c.springboot.common.enums.sys
 * @author: 吴佳隆
 * @date: 2019年12月10日 下午7:41:05
 * @Description: 登录状态枚举类
 */
public enum LoginStatusEnum implements ISEnum {
    OFF_LINE(0, "离线"), ON_LINE(1, "在线");

    private int key;
    private String value;

    public int getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    private LoginStatusEnum(int key, String value) {
        this.key = key;
        this.value = value;
        TreeMap<Integer, String> kvMap = ENUMMAP.get(LoginStatusEnum.class.getName());
        if (kvMap == null) {
            kvMap = new TreeMap<Integer, String>(new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    return o1.compareTo(o2);
                }
            });
            ENUMMAP.put(LoginStatusEnum.class.getName(), kvMap);
        }
        if (kvMap.get(key) == null) {
            kvMap.put(key, value);
        }
    }

    /**
     * @Title: getValue
     * @author: 吴佳隆
     * @data: 2019年5月23日 下午4:06:39
     * @Description: 根据 key 值获取 value
     * @param key
     * @return String
     */
    public static String getValue(int key) {
        return ENUMMAP.get(LoginStatusEnum.class.getName()).get(key);
    }

    /**
     * @Title: getValue
     * @author: 吴佳隆
     * @data: 2019年5月23日 下午4:06:39
     * @Description: 根据 key 值获取 value
     * @param key
     * @return String
     */
    public static String getValue(Object key) {
        if (key == null) {
            return null;
        }
        return ENUMMAP.get(LoginStatusEnum.class.getName()).get(Integer.parseInt(key.toString()));
    }

    /**
     * @Title: equalValue
     * @author: 吴佳隆
     * @data: 2019年5月23日 下午4:05:40
     * @Description: 判断枚举类型的 key 值是否与指定的 key 值相等
     * @param e
     * @param key
     * @return boolean
     */
    public static boolean equalValue(LoginStatusEnum e, int key) {
        if (e == null) {
            return false;
        }
        if (e.getKey() == key) {
            return true;
        }
        return false;
    }

    /**
     * @Title: equalValue
     * @author: 吴佳隆
     * @data: 2019年5月23日 下午4:05:40
     * @Description: 判断枚举类型的 key 值是否与指定的 key 值相等
     * @param e
     * @param key
     * @return boolean
     */
    public static boolean equalValue(LoginStatusEnum e, Object key) {
        if (e == null || key == null) {
            return false;
        }
        try {
            return e.getKey() == Integer.parseInt(key.toString());
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * @Title: forKey
     * @author: 吴佳隆
     * @data: 2019年5月23日 下午4:05:14
     * @Description: 根据 key 值获取枚举类型
     * @param key
     * @return LoginStatusEnum
     */
    public static LoginStatusEnum forKey(int key) {
        LoginStatusEnum[] values = LoginStatusEnum.values();
        for (LoginStatusEnum e : values) {
            if (e.getKey() == key) {
                return e;
            }
        }
        return null;
    }

    /**
     * @Title: forKey
     * @author: 吴佳隆
     * @data: 2019年5月23日 下午4:05:14
     * @Description: 根据 key 值获取枚举类型
     * @param key
     * @return LoginStatusEnum
     */
    public static LoginStatusEnum forKey(Object key) {
        if (key == null) {
            return null;
        }
        int k;
        try {
            k = Integer.parseInt(key.toString());
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            return null;
        }
        for (LoginStatusEnum e : LoginStatusEnum.values()) {
            if (e.getKey() == k) {
                return e;
            }
        }
        return null;
    }

    /**
     * @Title: forValue
     * @author: 吴佳隆
     * @data: 2019年5月23日 下午4:04:44
     * @Description: 根据 value 值获取枚举类型
     * @param value
     * @return LoginStatusEnum
     */
    public static LoginStatusEnum forValue(String value) {
        if (value == null) {
            return null;
        }
        return Enum.valueOf(LoginStatusEnum.class, value);
    }

    /**
     * @Title: kvMap
     * @author: 吴佳隆
     * @data: 2019年5月24日 上午10:54:21
     * @Description: 获取所有 key-value键值对
     * @return Map<Integer,String>
     */
    public static Map<Integer, String> kvMap() {
        return ENUMMAP.get(LoginStatusEnum.class.getName());
    }

}