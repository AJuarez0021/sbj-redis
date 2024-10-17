package com.work.crud.service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author linux
 */
public interface RedisOperationsService {

    Boolean exist(String key, Object id);

    Map<Object, Object> getAllValues(String key);

    Long delete(String key, Object id);

    void expire(String key, long timeout, TimeUnit timeUnit);

    void setValue(String key, Object value, Object id);

    Object getValue(String key, Object id);

    void addToList(String key, String... values);

    List<Object> getList(String key, long start, long end);

    void addToSet(String key, String... values);

    Set<Object> getSet(String key);

    void addToSortedSet(String key, String value, double score);

    Set<Object> getSortedSetRange(String key, long start, long end);

    void publishMessage(String channel, String message);
}
