package com.glch.base.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {
    //因@Autowired直接注入失败，故采用init方式进行注入
    private static RedisTemplate<Object,Object> redisTemplate;

    @Autowired
    public void init(RedisTemplate<Object, Object> redisTemplate){
        RedisUtil.redisTemplate = redisTemplate;
    }

    /**
     * 存入缓存
     * @param key 键
     * @param value 值
     * @return
     */
    public boolean setObj(String key, Object value){
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 存入缓存（设置过期时间）
     * @param key
     * @param value
     * @param time
     * @return
     */
    public boolean setObj(String key, Object value, long time){
        try {
            if(time > 0){
                setObj(key, value, time, TimeUnit.SECONDS);
            }else{
                setObj(key, value);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 添加字符串key
     * @param key 字符串key
     * @param value 值 可以为对象
     * @param expire 过期时间
     * @param timeUnit TimeUnit中定义的时间单位
     */
    public void setObj(String key, Object value, long expire, TimeUnit timeUnit){
        this.redisTemplate.opsForValue().set(key, value, expire, timeUnit);
    }

    /**
     * 指定缓存失效时间
     * @param key 键
     * @param time 时间（秒）
     * @return
     */
    public boolean expire(String key, long time){
        try {
            return expire(key,time,TimeUnit.SECONDS);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean expire(String key, long time,TimeUnit timeUnit){
        try {
            if(time > 0){
                redisTemplate.expire(key, time, timeUnit);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据key获取过期时间
     * @param key 键
     * @return
     */
    public long getExpire(String key){
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 判断key是否存在
     * @param key 键
     * @return
     */
    public boolean hasKey(String key){
        try {
            return redisTemplate.hasKey(key);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除缓存
     * @param key 可以传一个值或多个值
     */
    public void remove(String... key){
        if(null != key && key.length > 0){
            if(key.length == 1){
                redisTemplate.delete(key[0]);
            }else{
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }

    /**
     * 递增
     * @param key 键
     * @param delta 数据量（大于0）
     * @return
     */
    public long incr(String key, long delta){
        if(delta <= 0){
            throw new RuntimeException("递增因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key,delta);
    }

    /**
     * 递减
     * @param key 键
     * @param delta 数据量（大于0）
     * @return
     */
    public long decr(String key, long delta){
        if(delta <= 0){
            throw new RuntimeException("递减因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, -delta);
    }

    /**
     * HashGet
     * @param key 键 不能为null
     * @param item 项 不能为null
     * @return
     */
    public Object getObjFormHash(String key, String item){
        return redisTemplate.opsForHash().get(key, item);
    }

    /**
     * 获取map
     * @param key 键
     * @return
     */
    public Map<Object, Object> getHashMap(String key){
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 缓存Map
     * @param key 键
     * @param map 对应多个键值
     * @return
     */
    public boolean setHashMap(String key, Map<String,Object> map){
        try {
            redisTemplate.opsForHash().putAll(key,map);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 缓存Map（设置过期时间）
     * @param key
     * @param map
     * @param time
     * @return
     */
    public boolean setHashMap(String key, Map<String, Object> map, long time){
        try {
            redisTemplate.opsForHash().putAll(key, map);
            if(time > 0){
                expire(key, time);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 更新Map中对应key的value，如果key不存在，则创建
     * @param key
     * @param item
     * @param value
     * @return
     */
    public boolean setObjToHash(String key, String item, Object value){
        try {
            redisTemplate.opsForHash().put(key, item, value);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除Map中的值
     * @param key 键
     * @param item Map中的项，可一个或多个值
     */
    public void removeFromHash(String key, Object... item){
        redisTemplate.opsForHash().delete(key, item);
    }

    /**
     * 判断Map中是否存在
     * @param key 键
     * @param item 项
     * @return
     */
    public boolean hasKey(String key, String item){
        return redisTemplate.opsForHash().hasKey(key, item);
    }

    /**
     * hash 递增，如果不存在，则新增
     * @param key
     * @param item
     * @param by
     * @return
     */
    public double hashIncr(String key, String item, double by){
        return redisTemplate.opsForHash().increment(key, item, by);
    }

    /**
     * hash 递减，如果不存在，则新增
     * @param key
     * @param item
     * @param by
     * @return
     */
    public double hashDecr(String key, String item, double by){
        return redisTemplate.opsForHash().increment(key, item, -by);
    }

    /**
     * 根据key获取Set集合
     * @param key
     * @return
     */
    public Set<Object> getSet(String key){
        try {
            return redisTemplate.opsForSet().members(key);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 缓存Set
     * @param key
     * @param values
     * @return
     */
    public long setSet(String key, Object... values){
        try {
            return redisTemplate.opsForSet().add(key, values);
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 判断Set中是否存在value
     * @param key
     * @param value
     * @return
     */
    public boolean hasKey(String key, Object value){
        try {
            return redisTemplate.opsForSet().isMember(key, value);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取Set的长度
     * @param key
     * @return
     */
    public long getSetSize(String key){
        try {
            return redisTemplate.opsForSet().size(key);
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 从Set中移除value
     * @param key 键
     * @param values 值，一个或多个
     * @return 移除的个数
     */
    public long removeFromSet(String key, Object... values){
        try {
            return redisTemplate.opsForSet().remove(key, values);
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取list缓存
     * @param key 键
     * @param start 开始位置
     * @param end 结束位置 -1表示所有值
     * @return
     */
    public List<Object> getList(String key, long start, long end){
        try {
            return redisTemplate.opsForList().range(key, start, end);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<Object> getList(String key){
        try {
            long size = this.getListSize(key);
            if(size > 0){
                return redisTemplate.opsForList().range(key, 0, size - 1);
            }
            return null;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将list放入缓存
     * @param key
     * @param value
     * @return
     */
    public boolean setList(String key, List<Map<String, Object>> value){
        try {
            //先移除再赋值
            this.remove(key);
            redisTemplate.opsForList().rightPushAll(key, value);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将list放入缓存
     * @param key
     * @param value
     * @return
     */
    public boolean setObjList(String key, List<Object> value){
        try {
            //先移除再赋值
            this.remove(key);
            redisTemplate.opsForList().rightPushAll(key, value);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取列表长度
     * @param key
     * @return
     */
    public long getListSize(String key){
        try {
            return redisTemplate.opsForList().size(key);
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 根据索引获取对应值
     * @param key
     * @param index
     * @return
     */
    public Object getByIndexForList(String key, long index){
        try {
            return redisTemplate.opsForList().index(key,index);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据索引更新List中的数据
     * @param key
     * @param index
     * @param value
     * @return
     */
    public boolean updateByIndexForList(String key, long index, Object value){
        try {
            redisTemplate.opsForList().set(key, index, value);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 移除值为value的记录
     * @param key 键
     * @param count 要移除的个数
     * @param value 值
     * @return 移除结果
     */
    public long removeFromList(String key, long count, Object value){
        try {
            return redisTemplate.opsForList().remove(key, count, value);
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }
}
