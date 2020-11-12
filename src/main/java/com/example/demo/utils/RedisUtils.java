package com.example.demo.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtils {

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    public long getExpire(String key){
        Long l = redisTemplate.getExpire(key,TimeUnit.SECONDS);
        assert l != null;
        return l;
    }

    public void hPutAll(String key, Map<String,Object> map){
        try{
            redisTemplate.opsForHash().putAll(key,map);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void hPutAll(String key, Map<String,Object> map,long time){
        try{
            redisTemplate.opsForHash().putAll(key,map);
            if (time>0){
                redisTemplate.expire(key,time, TimeUnit.SECONDS);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Map<Object,Object> entries(String key){
        try{
            return redisTemplate.opsForHash().entries(key);
        }catch (Exception e ){
            e.printStackTrace();
            return null;
        }
    }

    public boolean hasKey(String key){
        Boolean b = redisTemplate.hasKey(key);
        assert b != null;
        return b;
    }

    public void delete(String key){
        redisTemplate.delete(key);
    }

    public Object get(String key){
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    public boolean set(String key,Object value){
        try {
            redisTemplate.opsForValue().set(key,value);
            return true;
        }catch (Exception e ){
            e.printStackTrace();
            return false;
        }
    }

    public boolean set(String key,Object value, long time){
        try {
            if (time>0){
                redisTemplate.opsForValue().set(key,value,time,TimeUnit.SECONDS);
            }else {
                set(key,value);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

}
