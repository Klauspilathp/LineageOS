package top.d7c.springboot.common.configurations.ehcache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.util.CollectionUtils;

import top.d7c.plugins.core.StringUtil;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * @Title: EhcacheServiceImpl
 * @Package: top.d7c.springboot.common.configurations.ehcache
 * @author: 吴佳隆
 * @date: 2019年12月9日 下午5:38:09
 * @Description: ehcache 缓存服务实现
 */
public class EhcacheServiceImpl implements EhcacheService {
    /**
     * 默认缓存库名
     */
    private static String ehcache0 = "ehcache0";
    /**
     * 注入的缓存管理器
     */
    private CacheManager cacheManager;

    public EhcacheServiceImpl(CacheManager cacheManager) {
        if (cacheManager == null) {
            throw new RuntimeException("cacheManager 属性为 null，请检查是否注入了 EhcacheServiceImpl !");
        }
        this.cacheManager = cacheManager;
    }

    @Override
    public Cache getCache(String cacheName) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache == null) {
            synchronized (cacheManager) {
                cache = cacheManager.getCache(cacheName);
                if (cache == null) {
                    cacheManager.addCacheIfAbsent(cacheName);
                    cache = cacheManager.getCache(cacheName);
                }
            }
        }
        return cache;
    }

    @Override
    public void addString(String key, String value) {
        this.addObject(key, value);
    }

    @Override
    public String getString(String key) {
        return StringUtil.toString(this.getObject(key));
    }

    @Override
    public String getString(String cacheName, String key) {
        return StringUtil.toString(this.getObject(cacheName, key));
    }

    @Override
    public void addObject(String key, Object value) {
        this.addObject(key, 0, value);
    }

    @Override
    public void addObject(String key, long time, Object value) {
        // this.addObject(key, (int) time, value);
        this.addObject(key, time % Integer.MAX_VALUE, value);
    }

    @Override
    public void addObject(String key, int time, Object value) {
        this.addObject(ehcache0, key, time, value);
    }

    @Override
    public void addObject(String cacheName, String key, Object value) {
        this.addObject(cacheName, key, 0, value);
    }

    @Override
    public void addObject(String cacheName, String key, int time, Object value) {
        if (StringUtil.isBlank(cacheName)) {
            return;
        }
        if (!cacheManager.cacheExists(cacheName)) {
            return;
        }
        Element element = new Element(key, value);
        if (time > 0) {
            element.setTimeToLive(time);
        }
        getCache(cacheName).put(element);
    }

    @Override
    public void addObject(Map<String, Object> map) {
        this.addObject(ehcache0, map);
    }

    @Override
    public void addObject(String cacheName, Map<String, Object> map) {
        if (StringUtil.isBlank(cacheName)) {
            return;
        }
        if (map == null || map.isEmpty()) {
            return;
        }
        if (!cacheManager.cacheExists(cacheName)) {
            return;
        }
        List<Element> elements = new ArrayList<Element>();
        for (Entry<String, Object> entry : map.entrySet()) {
            elements.add(new Element(entry.getKey(), entry.getValue()));
        }
        getCache(cacheName).putAll(elements);
    }

    @Override
    public Object getObject(String key) {
        return this.getObject(ehcache0, key);
    }

    @Override
    public List<Object> getObject(List<String> keys) {
        return this.getObject(ehcache0, keys);
    }

    @Override
    public List<Object> getObject(String cacheName, List<String> keys) {
        if (StringUtil.isBlank(cacheName)) {
            return null;
        }
        if (!cacheManager.cacheExists(cacheName)) {
            return null;
        }
        List<Object> objects = new ArrayList<>();
        Map<Object, Element> all = getCache(cacheName).getAll(keys);
        if (all == null || all.isEmpty()) {
            return objects;
        }
        for (Element element : all.values()) {
            objects.add(element.getObjectValue());
        }
        return objects;
    }

    @Override
    public Object getObject(String cacheName, String key) {
        if (StringUtil.isBlank(cacheName)) {
            return null;
        }
        if (!cacheManager.cacheExists(cacheName)) {
            return null;
        }
        Element element = getCache(cacheName).get(key);
        return element == null ? null : element.getObjectValue();
    }

    @Override
    public boolean hasKeyByHash(String key, String item) {
        return this.getByHashItem(key, item) != null;
    }

    @Override
    public boolean hasKeyByHash(String cacheName, String key, String item) {
        return this.getByHashItem(cacheName, key, item) != null;
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public Map<Object, Object> getHashByKey(String key) {
        return (Map<Object, Object>) this.getObject(key);
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public Map<Object, Object> getHashByKey(String cacheName, String key) {
        return (Map<Object, Object>) this.getObject(cacheName, key);
    }

    @Override
    public Object getByHashItem(String key, String item) {
        Map<Object, Object> map = this.getHashByKey(key);
        return map == null ? null : map.get(item);
    }

    @Override
    public Object getByHashItem(String cacheName, String key, String item) {
        Map<Object, Object> map = this.getHashByKey(cacheName, key);
        return map == null ? null : map.get(item);
    }

    @Override
    public void addItemByHash(String key, String item, Object value) {
        this.addItemByHash(key, 0, item, value);
    }

    @Override
    public void addItemByHash(String key, int time, String item, Object value) {
        this.addItemByHash(ehcache0, key, time, item, value);
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public void addItemByHash(String cacheName, String key, int time, String item, Object value) {
        if (StringUtil.isBlank(cacheName)) {
            return;
        }
        if (!cacheManager.cacheExists(cacheName)) {
            return;
        }
        Cache cache = getCache(cacheName);
        try {
            boolean tryWriteLockOnKey = cache.tryWriteLockOnKey(key, 5000);
            if (!tryWriteLockOnKey) {
                cache.releaseWriteLockOnKey(key);
                return;
            }
            Element old = cache.get(key);
            Map<String, Object> mapValue = null;
            if (old != null) {
                mapValue = (Map<String, Object>) old.getObjectValue();
            }
            if (mapValue == null) {
                mapValue = new HashMap<String, Object>();
            }
            mapValue.put(item, value);
            Element element = new Element(key, mapValue);
            if (time > 0) {
                element.setTimeToLive(time);
            }
            cache.replace(old, element);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            cache.releaseWriteLockOnKey(key);
        }
    }

    @Override
    public void addHashByKey(String key, Map<String, Object> map) {
        this.addHashByKey(key, 0, map);
    }

    @Override
    public void addHashByKey(String key, int time, Map<String, Object> map) {
        this.addHashByKey(ehcache0, key, time, map);
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public void addHashByKey(String cacheName, String key, int time, Map<String, Object> map) {
        if (StringUtil.isBlank(cacheName)) {
            return;
        }
        if (map == null || map.isEmpty()) {
            return;
        }
        if (!cacheManager.cacheExists(cacheName)) {
            return;
        }
        Cache cache = getCache(cacheName);
        try {
            boolean tryWriteLockOnKey = cache.tryWriteLockOnKey(key, 5000);
            if (!tryWriteLockOnKey) {
                cache.releaseWriteLockOnKey(key);
                return;
            }
            Element old = cache.get(key);
            Map<String, Object> mapValue = null;
            if (old != null) {
                mapValue = (Map<String, Object>) old.getObjectValue();
            }
            Element element = null;
            if (mapValue == null) {
                element = new Element(key, map);
            } else {
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    mapValue.put(entry.getKey(), entry.getValue());
                }
                element = new Element(key, mapValue);
            }
            if (time > 0) {
                element.setTimeToLive(time);
            }
            cache.replace(old, element);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            cache.releaseWriteLockOnKey(key);
        }
    }

    @Override
    public void delItemByHash(String key, Object[] items) {
        this.delItemByHash(ehcache0, key, items);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void delItemByHash(String cacheName, String key, Object[] items) {
        if (items == null || items.length == 0) {
            return;
        }
        if (StringUtil.isBlank(cacheName)) {
            return;
        }
        if (!cacheManager.cacheExists(cacheName)) {
            return;
        }
        Cache cache = getCache(cacheName);
        try {
            boolean tryWriteLockOnKey = cache.tryWriteLockOnKey(key, 5000);
            if (!tryWriteLockOnKey) {
                cache.releaseWriteLockOnKey(key);
                return;
            }
            Element element = cache.get(key);
            if (element == null) {
                return;
            }
            Map<String, Object> mapValue = (Map<String, Object>) element.getObjectValue();
            if (mapValue == null || mapValue.isEmpty()) {
                return;
            }
            for (Object item : items) {
                mapValue.remove(item);
            }
            cache.replace(element, new Element(key, mapValue));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            cache.releaseWriteLockOnKey(key);
        }
    }

    @Override
    public void delete(String key) {
        getCache(ehcache0).remove(key);
    }

    @Override
    public void delete(String[] keys) {
        this.delete(ehcache0, keys);
    }

    @Override
    public void delete(String cacheName, String[] keys) {
        if (StringUtil.isBlank(cacheName)) {
            return;
        }
        if (!cacheManager.cacheExists(cacheName)) {
            return;
        }
        getCache(cacheName).removeAll(CollectionUtils.arrayToList(keys));
    }

    @Override
    public void deleteByCacheName(String cacheName) {
        if (StringUtil.isBlank(cacheName)) {
            return;
        }
        if (!cacheManager.cacheExists(cacheName)) {
            return;
        }
        getCache(cacheName).removeAll();
    }

    @Override
    public boolean hasKey(String key) {
        return this.hasKey(ehcache0, key);
    }

    @Override
    public boolean hasKey(String cacheName, String key) {
        if (StringUtil.isBlank(cacheName)) {
            return false;
        }
        if (!cacheManager.cacheExists(cacheName)) {
            return false;
        }
        Cache cache = getCache(cacheName);
        return cache.isKeyInCache(key) && cache.get(key) != null;
    }

    @Override
    public long incr(String key) {
        return this.incr(key, 1);
    }

    @Override
    public long incr(String key, long value) {
        return this.incr(ehcache0, key, value);
    }

    @Override
    public long incr(String cacheName, String key, long value) {
        long curr = 0L;
        if (StringUtil.isBlank(cacheName)) {
            return curr;
        }
        if (!cacheManager.cacheExists(cacheName)) {
            return curr;
        }
        Cache cache = getCache(cacheName);
        try {
            boolean tryWriteLockOnKey = cache.tryWriteLockOnKey(key, 5000);
            if (!tryWriteLockOnKey) {
                cache.releaseWriteLockOnKey(key);
                return curr;
            }
            Element element = cache.get(key);
            if (element != null) {
                curr = StringUtil.tolong(element.getObjectValue());
            }
            curr += value;
            cache.replace(element, new Element(key, curr));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            cache.releaseWriteLockOnKey(key);
        }
        return curr;
    }

    @Override
    public boolean expire(String key, int time) {
        return this.expire(ehcache0, key, time);
    }

    @Override
    public boolean expire(String cacheName, String key, int time) {
        if (StringUtil.isBlank(cacheName)) {
            return false;
        }
        if (!cacheManager.cacheExists(cacheName)) {
            return false;
        }
        Cache cache = getCache(ehcache0);
        try {
            boolean tryWriteLockOnKey = cache.tryWriteLockOnKey(key, 5000);
            if (!tryWriteLockOnKey) {
                cache.releaseWriteLockOnKey(key);
                return false;
            }
            Element old = cache.get(key);
            if (old == null) {
                return false;
            }
            Element element = new Element(old.getObjectKey(), old.getObjectValue());
            element.setTimeToLive(time);
            return cache.replace(old, element);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            cache.releaseWriteLockOnKey(key);
        }
        return false;
    }

    @Override
    public int getExpire(String key) {
        return this.getExpire(ehcache0, key);
    }

    @Override
    public int getExpire(String cacheName, String key) {
        if (StringUtil.isBlank(cacheName)) {
            return -1;
        }
        if (!cacheManager.cacheExists(cacheName)) {
            return -1;
        }
        Element element = getCache(cacheName).get(key);
        return element == null ? -1 : element.getTimeToLive();
    }

    @Override
    public String generateKey(Object... keys) {
        if (keys == null || keys.length == 0) {
            return StringUtil.UNDERLINE;
        }
        if (keys.length == 1) {
            return keys[0].toString();
        }
        StringBuilder sb = new StringBuilder(keys[0].toString());
        for (int i = 1; i < keys.length; i++) {
            sb.append(StringUtil.UNDERLINE);
            sb.append(keys[i]);
        }
        return sb.toString();
    }

}