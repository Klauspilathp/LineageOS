package top.d7c.springboot.common.configurations.ehcache;

import java.util.List;
import java.util.Map;

import top.d7c.plugins.core.context.CacheService;

import net.sf.ehcache.Cache;

/**
 * @Title: EhcacheService
 * @Package: top.d7c.springboot.common.configurations.ehcache
 * @author: 吴佳隆
 * @date: 2020年2月25日 下午6:01:49
 * @Description: ehcache 缓存服务接口
 */
public interface EhcacheService extends CacheService {

    /**
     * @Title: getCache
     * @author: 吴佳隆
     * @data: 2020年2月25日 下午6:07:37
     * @Description: 获取一个 ehcache 缓存对象
     * @param cacheName 缓存名称
     * @return Cache    缓存对象
     */
    Cache getCache(String cacheName);

    /**
     * @Title: getString
     * @author: 吴佳隆
     * @data: 2020年2月25日 下午6:37:24
     * @Description: 根据 key 从指定缓存名中获取一个字符串
     * @param cacheName 要操作的缓存名称
     * @param key       键
     * @return String   获取的字符串
     */
    String getString(String cacheName, String key);

    /**
     * @Title: addObject
     * @author: 吴佳隆
     * @data: 2020年2月25日 下午6:47:01
     * @Description: 向默认缓存名中添加一个对象
     * @param key   键
     * @param time  有效期，0 表示无限期，单位秒
     * @param value 键值
     */
    void addObject(String key, int time, Object value);

    /**
     * @Title: addObject
     * @author: 吴佳隆
     * @data: 2020年2月25日 下午7:01:26
     * @Description: 向指定缓存名中添加一个对象
     * @param cacheName 缓存名
     * @param key       键
     * @param value     键值
     */
    void addObject(String cacheName, String key, Object value);

    /**
     * @Title: addObject
     * @author: 吴佳隆
     * @data: 2020年2月25日 下午7:02:44
     * @Description: 向指定缓存名中添加一个对象并指定有效期
     * @param cacheName 缓存名
     * @param key       键
     * @param time      有效期，0 表示无限期，单位秒
     * @param value     键值
     */
    void addObject(String cacheName, String key, int time, Object value);

    /**
     * @Title: addObject
     * @author: 吴佳隆
     * @data: 2020年2月25日 下午7:07:20
     * @Description: 向默认缓存名中添加多组键值
     * @param map   key - value 表示的键值对象
     */
    void addObject(Map<String, Object> map);

    /**
     * @Title: addObject
     * @author: 吴佳隆
     * @data: 2020年2月25日 下午7:07:20
     * @Description: 向指定缓存名中添加多组键值
     * @param cacheName 缓存名
     * @param map   key - value 表示的键值对象
     */
    void addObject(String cacheName, Map<String, Object> map);

    /**
     * @Title: getObject
     * @author: 吴佳隆
     * @data: 2020年2月25日 下午7:09:26
     * @Description: 从默认缓存名中获取一组键的值
     * @param keys          键集合
     * @return List<Object> 值集合
     */
    List<Object> getObject(List<String> keys);

    /**
     * @Title: getObject
     * @author: 吴佳隆
     * @data: 2020年2月25日 下午7:09:26
     * @Description: 从指定缓存名中获取一组键的值
     * @param cacheName 缓存名
     * @param keys          键集合
     * @return List<Object> 值集合
     */
    List<Object> getObject(String cacheName, List<String> keys);

    /**
     * @Title: getObject
     * @author: 吴佳隆
     * @data: 2020年2月25日 下午7:13:03
     * @Description: 从指定缓存名中获取键的值
     * @param cacheName 缓存名
     * @param key       键
     * @return Object   键值
     */
    Object getObject(String cacheName, String key);

    /**
     * @Title: hasKeyByHash
     * @author: 吴佳隆
     * @data: 2020年2月25日 下午7:19:45
     * @Description: 判断默认缓存名中 key 所对应的 map 中是否有该 item 项的值
     * @param key       键
     * @param item      键中 map 的 key
     * @return boolean
     */
    boolean hasKeyByHash(String key, String item);

    /**
     * @Title: hasKeyByHash
     * @author: 吴佳隆
     * @data: 2020年2月25日 下午7:19:45
     * @Description: 判断指定缓存名中 key 所对应的 map 中是否有该 item 项的值
     * @param cacheName 缓存名
     * @param key       键
     * @param item      键中 map 的 key
     * @return boolean
     */
    boolean hasKeyByHash(String cacheName, String key, String item);

    /**
     * @Title: getHashByKey
     * @author: 吴佳隆
     * @data: 2020年2月25日 下午7:24:35
     * @Description: 获取默认缓存名中 key 所对应的 map
     * @param key                   键
     * @return Map<Object,Object>   键值是一个 map
     */
    Map<Object, Object> getHashByKey(String key);

    /**
     * @Title: getHashByKey
     * @author: 吴佳隆
     * @data: 2020年2月25日 下午7:24:35
     * @Description: 获取指定缓存名中 key 所对应的 map
     * @param cacheName 缓存名
     * @param key                   键
     * @return Map<Object,Object>   键值是一个 map
     */
    Map<Object, Object> getHashByKey(String cacheName, String key);

    /**
     * @Title: getByHashItem
     * @author: 吴佳隆
     * @data: 2020年2月25日 下午7:19:51
     * @Description: 获取默认缓存名中 key 所对应的 map 中 item 项的值
     * @param key       键
     * @param item      键中 map 的 key
     * @return Object   键值
     */
    Object getByHashItem(String key, String item);

    /**
     * @Title: getByHashItem
     * @author: 吴佳隆
     * @data: 2020年2月25日 下午7:19:51
     * @Description: 获取指定缓存名中 key 所对应的 map 中 item 项的值
     * @param cacheName 缓存名
     * @param key       键
     * @param item      键中 map 的 key
     * @return Object   键值
     */
    Object getByHashItem(String cacheName, String key, String item);

    /**
     * @Title: addItemByHash
     * @author: 吴佳隆
     * @data: 2020年2月25日 下午7:28:41
     * @Description: 向默认缓存名中 key 所对应的无限期 map 中添加一组键值
     * @param key   键
     * @param item  键中 map 的 key
     * @param value 键值
     */
    void addItemByHash(String key, String item, Object value);

    /**
     * @Title: addItemByHash
     * @author: 吴佳隆
     * @data: 2020年2月25日 下午7:30:55
     * @Description: 向默认缓存名中 key 所对应的有限期 map 中添加一组键值
     * @param key   键
     * @param time  有效期，0 表示无限期，单位秒
     * @param item  键中 map 的 key
     * @param value 键值
     */
    void addItemByHash(String key, int time, String item, Object value);

    /**
     * @Title: addItemByHash
     * @author: 吴佳隆
     * @data: 2020年2月25日 下午7:30:55
     * @Description: 向指定缓存名中 key 所对应的有限期 map 中添加一组键值
     * @param cacheName 缓存名
     * @param key   键
     * @param time  有效期，0 表示无限期，单位秒
     * @param item  键中 map 的 key
     * @param value 键值
     */
    void addItemByHash(String cacheName, String key, int time, String item, Object value);

    /**
     * @Title: addHashByKey
     * @author: 吴佳隆
     * @data: 2020年2月25日 下午8:13:23
     * @Description: 向默认缓存名中 key 所对应的无限期 map 中添加多组键值
     * @param key   键
     * @param map   map 表示的多组键值
     */
    void addHashByKey(String key, Map<String, Object> map);

    /**
     * @Title: addHashByKey
     * @author: 吴佳隆
     * @data: 2020年2月25日 下午8:12:45
     * @Description: 向默认缓存名中 key 所对应的有限期 map 中添加多组键值
     * @param key   键
     * @param time  有效期，0 表示无限期，单位秒
     * @param map   map 表示的多组键值
     */
    void addHashByKey(String key, int time, Map<String, Object> map);

    /**
     * @Title: addHashByKey
     * @author: 吴佳隆
     * @data: 2020年2月25日 下午8:10:29
     * @Description: 向指定缓存名中 key 所对应的有限期 map 中添加多组键值
     * @param cacheName 缓存名
     * @param key       键
     * @param time      有效期，0 表示无限期，单位秒
     * @param map       map 表示的多组键值
     */
    void addHashByKey(String cacheName, String key, int time, Map<String, Object> map);

    /**
     * @Title: delItemByHash
     * @author: 吴佳隆
     * @data: 2020年2月25日 下午8:18:46
     * @Description: 删除默认缓存名中 key 所对应 map 中多个项
     * @param key       键
     * @param items     map 中的对个项（map 的 key）
     */
    void delItemByHash(String key, Object[] items);

    /**
     * @Title: delItemByHash
     * @author: 吴佳隆
     * @data: 2020年2月25日 下午8:18:50
     * @Description: 删除指定缓存名中 key 所对应 map 中多个项
     * @param cacheName 缓存名
     * @param key       键
     * @param items     map 中的对个项（map 的 key）
     */
    void delItemByHash(String cacheName, String key, Object[] items);

    /**
     * @Title: delete
     * @author: 吴佳隆
     * @data: 2020年2月25日 下午8:50:09
     * @Description: 删除默认缓存名中多个键
     * @param keys  多个键
     */
    void delete(String[] keys);

    /**
     * @Title: delete
     * @author: 吴佳隆
     * @data: 2020年2月25日 下午8:51:02
     * @Description: 删除指定缓存名中多个键
     * @param cacheName 缓存名
     * @param keys      多个键
     */
    void delete(String cacheName, String[] keys);

    /**
     * @Title: deleteByCacheName
     * @author: 吴佳隆
     * @data: 2020年2月25日 下午9:00:44
     * @Description: 清空指定缓存名所有键值
     * @param cacheName 缓存名
     */
    void deleteByCacheName(String cacheName);

    /**
     * @Title: hasKey
     * @author: 吴佳隆
     * @data: 2020年2月25日 下午9:09:33
     * @Description: 判断指定缓存名中是否存在该键
     * @param cacheName 缓存名
     * @param key       键
     * @return boolean  存在返回 true
     */
    boolean hasKey(String cacheName, String key);

    /**
     * @Title: incr
     * @author: 吴佳隆
     * @data: 2020年2月25日 下午9:13:32
     * @Description: 将指定缓存名中指定键的值增加 value
     * @param cacheName 缓存名
     * @param key       键
     * @param value     要增加的值，传入负数是递减
     * @return long     返回增加后的值
     */
    long incr(String cacheName, String key, long value);

    /**
     * @Title: expire
     * @author: 吴佳隆
     * @data: 2020年2月25日 下午9:22:20
     * @Description: 更新默认缓存名中指定键的有效期
     * @param key       键
     * @param time      有效期，0 表示无限期，单位秒
     * @return boolean  更新成功返回 true
     */
    boolean expire(String key, int time);

    /**
     * @Title: expire
     * @author: 吴佳隆
     * @data: 2020年2月25日 下午9:23:09
     * @Description: 更新指定缓存名中指定键的有效期
     * @param cacheName 缓存名
     * @param key       键
     * @param time      有效期，0 表示无限期，单位秒
     * @return boolean  更新成功返回 true
     */
    boolean expire(String cacheName, String key, int time);

    /**
     * @Title: getExpire
     * @author: 吴佳隆
     * @data: 2020年2月25日 下午9:24:44
     * @Description: 获取默认缓存名中指定键的有效期
     * @param key       键
     * @return int      有效期，0 表示无限期，单位秒
     */
    int getExpire(String key);

    /**
     * @Title: getExpire
     * @author: 吴佳隆
     * @data: 2020年2月25日 下午9:25:17
     * @Description: 获取指定缓存名中指定键的有效期
     * @param cacheName 缓存名
     * @param key       键
     * @return int      有效期，0 表示无限期，单位秒
     */
    int getExpire(String cacheName, String key);

}