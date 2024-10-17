package com.work.crud.service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 *
 * @author linux
 */
@Service
public class RedisOperationsServiceImpl implements RedisOperationsService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * Establecer un valor simple (String).
     *
     * @param key
     * @param value
     * @param hashKey
     */
    @Override
    public void setValue(String key, Object value, Object hashKey) {
         redisTemplate.opsForHash().put(key, hashKey, value);
    }

    @Override
    public void expire(String key, long timeout, TimeUnit timeUnit) {
        redisTemplate.expire(key, timeout, timeUnit);
    }

    /**
     * Obtener un valor simple (String).
     *
     * @param key
     * @param id
     * @return
     */
    
    @Override
    public Object getValue(String key, Object id) {
        return redisTemplate.opsForHash().get(key, id);
    }

    @Override
    public Map<Object, Object> getAllValues(String key) {    
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * Agregar elementos a una lista.
     *
     * @param key
     * @param values
     */
    @Override
    public void addToList(String key, String... values) {
        redisTemplate.opsForList().rightPushAll(key, (Object) values);
    }

    /**
     * Obtener elementos de una lista.
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    @Override
    public List<Object> getList(String key, long start, long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    /**
     * Agregar miembros a un set.
     *
     * @param key
     * @param values
     */
    @Override
    public void addToSet(String key, String... values) {
        redisTemplate.opsForSet().add(key, (Object[]) values);
    }

    /**
     * Obtener miembros de un set.
     *
     * @param key
     * @return
     */
    @Override
    public Set<Object> getSet(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * Agregar miembros a un sorted set.
     *
     * @param key
     * @param value
     * @param score
     */
    @Override
    public void addToSortedSet(String key, String value, double score) {
        redisTemplate.opsForZSet().add(key, value, score);
    }

    /**
     * Obtener miembros de un sorted set en un rango específico.
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    @Override
    public Set<Object> getSortedSetRange(String key, long start, long end) {
        return redisTemplate.opsForZSet().range(key, start, end);
    }

    /**
     * Publicar un mensaje en un canal (Pub/Sub).
     *
     * @param channel
     * @param message
     */
    @Override
    public void publishMessage(String channel, String message) {
        redisTemplate.convertAndSend(channel, message);
    }

    @Override
    public Long delete(String key, Object id) {
        return redisTemplate.opsForHash().delete(key, id);
    }

    @Override
    public Boolean exist(String key, Object id) {
        return redisTemplate.opsForHash().hasKey(key, id);
    }
}
