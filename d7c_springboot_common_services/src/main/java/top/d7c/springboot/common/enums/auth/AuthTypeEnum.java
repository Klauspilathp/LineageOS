package top.d7c.springboot.common.enums.auth;

import java.util.LinkedHashMap;
import java.util.Map;

import top.d7c.plugins.core.enums.SSEnum;

/**
 * @Title: AuthTypeEnum
 * @Package: top.d7c.springboot.common.enums.auth
 * @author: 吴佳隆
 * @date: 2019年7月14日 下午4:29:32
 * @Description: 授权类型枚举类
 */
public enum AuthTypeEnum implements SSEnum {
    /**
     * web 端请求
     */
    WEB("web", "web 端请求"),
    /**
     * wap 端请求
     */
    WAP("wap", "wap 端请求"),
    /**
     * mobile 端请求
     */
    MOBILE("mobile", "mobile 端请求");

    private String key;
    private String value;

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    static {
        LinkedHashMap<String, String> kvMap = new LinkedHashMap<String, String>();
        for (AuthTypeEnum e : AuthTypeEnum.values()) {
            kvMap.put(e.getKey(), e.getValue());
        }
        ENUMMAP.put(AuthTypeEnum.class.getName(), kvMap);
    }

    private AuthTypeEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    /**
     * @Title: getValue
     * @author: 吴佳隆
     * @data: 2019年5月23日 下午4:06:39
     * @Description: 根据 key 值获取 value
     * @param key
     * @return String
     */
    public static String getValue(String key) {
        return ENUMMAP.get(AuthTypeEnum.class.getName()).get(key);
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
        return ENUMMAP.get(AuthTypeEnum.class.getName()).get(String.valueOf(key));
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
    public static boolean equalValue(AuthTypeEnum e, String key) {
        if (e == null || key == null) {
            return false;
        }
        return e.getKey().equals(key);
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
    public static boolean equalValue(AuthTypeEnum e, Object key) {
        if (e == null || key == null) {
            return false;
        }
        return e.getKey().equals(key);
    }

    /**
     * @Title: forKey
     * @author: 吴佳隆
     * @data: 2019年5月23日 下午4:05:14
     * @Description: 根据 key 值获取枚举类型
     * @param key
     * @return AuthTypeEnum
     */
    public static AuthTypeEnum forKey(String key) {
        if (key == null) {
            return null;
        }
        for (AuthTypeEnum e : AuthTypeEnum.values()) {
            if (e.getKey().equals(key)) {
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
     * @return AuthTypeEnum
     */
    public static AuthTypeEnum forKey(Object key) {
        if (key == null) {
            return null;
        }
        return forKey(key.toString());
    }

    /**
     * @Title: forValue
     * @author: 吴佳隆
     * @data: 2019年5月23日 下午4:04:44
     * @Description: 根据 value 值获取枚举类型
     * @param value
     * @return AuthTypeEnum
     */
    public static AuthTypeEnum forValue(String value) {
        if (value == null) {
            return null;
        }
        return Enum.valueOf(AuthTypeEnum.class, value);
    }

    /**
     * @Title: kvMap
     * @author: 吴佳隆
     * @data: 2019年5月24日 上午10:54:21
     * @Description: 获取所有 key-value 键值对
     * @return Map<String,String>
     */
    public static Map<String, String> kvMap() {
        return ENUMMAP.get(AuthTypeEnum.class.getName());
    }

}