package com.caetp.digiex.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Repository
public class RedisBaseDao<K, V> {

	@Autowired
	private RedisTemplate<K, V> redisTemplate;

	public RedisBaseDao() {
		super();
	}

	public Long readIncr(K id, long value){
		Long increment = redisTemplate.opsForValue().increment(id, value);
		return increment;
	}

	public RedisTemplate<K, V> getRedisTemplate() {
		return redisTemplate;
	}
	/**
	 * 在redis中保存一个Map
	 * @param map
	 */
	public void redisSave(Map<K, V> map) {
		ValueOperations<K, V> valueOper = getRedisTemplate().opsForValue();
		valueOper.multiSet(map);
	}
	/**
	 * 
	 * @param k
	 * @param map
	 */
	public void redisSaveMap(K k, Map<String, Object> map) {
		getRedisTemplate().opsForHash().putAll(k, map);
	}

	public void redisSaveMapExpireAt(K k, Map<Object, Object> map, Date date) {
		getRedisTemplate().opsForHash().putAll(k, map);
		getRedisTemplate().expireAt(k, date);
	}

	public void redisSaveExpireAt(K key, V value, Date date) {
		ValueOperations<K, V> valueOper = getRedisTemplate().opsForValue();
		valueOper.set(key, value);
		getRedisTemplate().expireAt(key, date);
	}

	public void redisSaveExpire(K key, V value, Long timeout, TimeUnit unit) {
		ValueOperations<K, V> valueOper = getRedisTemplate().opsForValue();
		valueOper.set(key, value);
		getRedisTemplate().expire(key, timeout, unit);
	}

	public void redisSaveSerializable(K key, V value) {
		ValueOperations<K, V> valueOper = getRedisTemplate().opsForValue();
		valueOper.set(key, value);
	}

	public void redisSaveSerializableExpire(K key, V value, Long timeout, TimeUnit unit) {
		ValueOperations<K, V> valueOper = getRedisTemplate().opsForValue();
		valueOper.set(key, value);
		getRedisTemplate().expire(key, timeout, unit);
	}

	public void redisSaveSerializableExpireAt(K key, V value, Date date) {
		ValueOperations<K, V> valueOper = getRedisTemplate().opsForValue();
		valueOper.set(key, value);
		getRedisTemplate().expireAt(key, date);
	}
	
	public V redisRead(K id) {
		ValueOperations<K, V> valueOper = getRedisTemplate().opsForValue();
		return valueOper.get(id);
	}

	public Map<Object, Object> redisReadMap(K k) {
		return getRedisTemplate().opsForHash().entries(k);
	}

	public void redisDelete(K id) {
		ValueOperations<K, V> valueOper = getRedisTemplate().opsForValue();
		RedisOperations<K, V> RedisOperations = valueOper.getOperations();
		RedisOperations.delete(id);
	}

}
