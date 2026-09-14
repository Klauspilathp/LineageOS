package com.gnol.springboot.common.enums.sys;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

import com.gnol.plugins.core.enums.ISEnum;

/**
 * @Title: MenuTypeEnum
 * @Package: com.gnol.springboot.common.enums.sys
 * @author: 吴佳隆
 * @date: 2019年5月23日 上午11:40:03
 * @Description: 菜单类型枚举类
 */
public enum MenuTypeEnum implements ISEnum {
    SYS_MENU(1, "系统菜单"), BUS_MENU(2, "业务菜单");

    private int key;
    private String value;

    public int getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    private MenuTypeEnum(int key, String value) {
        this.key = key;
        this.value = value;
        TreeMap<Integer, String> kvMap = ENUMMAP.get(MenuTypeEnum.class.getName());
        if (kvMap == null) {
            kvMap = new TreeMap<Integer, String>(new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    return o1.compareTo(o2);
                }
            });
            ENUMMAP.put(MenuTypeEnum.class.getName(), kvMap);
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
        return ENUMMAP.get(MenuTypeEnum.class.getName()).get(key);
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
        return ENUMMAP.get(MenuTypeEnum.class.getName()).get(Integer.parseInt(key.toString()));
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
    public static boolean equalValue(MenuTypeEnum e, int key) {
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
    public static boolean equalValue(MenuTypeEnum e, Object key) {
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
     * @return MenuTypeEnum
     */
    public static MenuTypeEnum forKey(int key) {
        MenuTypeEnum[] values = MenuTypeEnum.values();
        for (MenuTypeEnum e : values) {
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
     * @return MenuTypeEnum
     */
    public static MenuTypeEnum forKey(Object key) {
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
        for (MenuTypeEnum e : MenuTypeEnum.values()) {
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
     * @return MenuTypeEnum
     */
    public static MenuTypeEnum forValue(String value) {
        if (value == null) {
            return null;
        }
        return Enum.valueOf(MenuTypeEnum.class, value);
    }

    /**
     * @Title: kvMap
     * @author: 吴佳隆
     * @data: 2019年5月24日 上午10:54:21
     * @Description: 获取所有 key-value键值对
     * @return Map<Integer,String>
     */
    public static Map<Integer, String> kvMap() {
        return ENUMMAP.get(MenuTypeEnum.class.getName());
    }

}