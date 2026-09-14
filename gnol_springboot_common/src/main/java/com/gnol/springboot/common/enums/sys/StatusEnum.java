package com.gnol.springboot.common.enums.sys;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

import com.gnol.plugins.core.enums.ISEnum;

/**
 * @Title: StatusEnum
 * @Package: com.gnol.springboot.common.enums.sys
 * @author: 吴佳隆
 * @date: 2019年5月23日 上午11:39:22
 * @Description: 数据状态枚举类
 */
public enum StatusEnum implements ISEnum {
    DELETE(0, "删除"), NORMAL(1, "正常"), FREEZE(2, "冻结");

    private int key;
    private String value;

    public int getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    private StatusEnum(int key, String value) {
        this.key = key;
        this.value = value;
        TreeMap<Integer, String> kvMap = ENUMMAP.get(StatusEnum.class.getName());
        if (kvMap == null) {
            kvMap = new TreeMap<Integer, String>(new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    return o1.compareTo(o2);
                }
            });
            ENUMMAP.put(StatusEnum.class.getName(), kvMap);
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
        return ENUMMAP.get(StatusEnum.class.getName()).get(key);
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
        return ENUMMAP.get(StatusEnum.class.getName()).get(Integer.parseInt(key.toString()));
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
    public static boolean equalValue(StatusEnum e, int key) {
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
    public static boolean equalValue(StatusEnum e, Object key) {
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
     * @return StatusEnum
     */
    public static StatusEnum forKey(int key) {
        StatusEnum[] values = StatusEnum.values();
        for (StatusEnum e : values) {
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
     * @return StatusEnum
     */
    public static StatusEnum forKey(Object key) {
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
        for (StatusEnum e : StatusEnum.values()) {
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
     * @return StatusEnum
     */
    public static StatusEnum forValue(String value) {
        if (value == null) {
            return null;
        }
        return Enum.valueOf(StatusEnum.class, value);
    }

    /**
     * @Title: kvMap
     * @author: 吴佳隆
     * @data: 2019年5月24日 上午10:54:21
     * @Description: 获取所有 key-value键值对
     * @return Map<Integer,String>
     */
    public static Map<Integer, String> kvMap() {
        return ENUMMAP.get(StatusEnum.class.getName());
    }

}