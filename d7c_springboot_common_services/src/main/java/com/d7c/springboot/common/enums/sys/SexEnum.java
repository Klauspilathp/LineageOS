package com.d7c.springboot.common.enums.sys;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

import com.d7c.plugins.core.enums.ISEnum;

/**
 * @Title: SexEnum
 * @Package: com.d7c.springboot.common.enums.sys
 * @author: 吴佳隆
 * @date: 2019年5月23日 上午11:40:03
 * @Description: 性别枚举类
 */
public enum SexEnum implements ISEnum {
    MAN(1, "男"), WOMAN(2, "女");

    private int key;
    private String value;

    public int getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    private SexEnum(int key, String value) {
        this.key = key;
        this.value = value;
        TreeMap<Integer, String> kvMap = ENUMMAP.get(SexEnum.class.getName());
        if (kvMap == null) {
            kvMap = new TreeMap<Integer, String>(new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    return o1.compareTo(o2);
                }
            });
            ENUMMAP.put(SexEnum.class.getName(), kvMap);
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
        return ENUMMAP.get(SexEnum.class.getName()).get(key);
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
        return ENUMMAP.get(SexEnum.class.getName()).get(Integer.parseInt(key.toString()));
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
    public static boolean equalValue(SexEnum e, int key) {
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
    public static boolean equalValue(SexEnum e, Object key) {
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
     * @return SexEnum
     */
    public static SexEnum forKey(int key) {
        SexEnum[] values = SexEnum.values();
        for (SexEnum e : values) {
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
     * @return SexEnum
     */
    public static SexEnum forKey(Object key) {
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
        for (SexEnum e : SexEnum.values()) {
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
     * @return SexEnum
     */
    public static SexEnum forValue(String value) {
        if (value == null) {
            return null;
        }
        return Enum.valueOf(SexEnum.class, value);
    }

    /**
     * @Title: kvMap
     * @author: 吴佳隆
     * @data: 2019年5月24日 上午10:54:21
     * @Description: 获取所有 key-value键值对
     * @return Map<Integer,String>
     */
    public static Map<Integer, String> kvMap() {
        return ENUMMAP.get(SexEnum.class.getName());
    }

}