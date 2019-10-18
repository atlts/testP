package com.testP.service;

import com.testP.util.JedisAdapter;
import com.testP.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeService {
    @Autowired
    JedisAdapter jedisAdapter;

    public long getLikeCount(int entityType, int entityId) {
        String likeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
        return jedisAdapter.scard(likeKey);
    }

    public int getLikeStatus(int userId,int entity,int entityId){
        String likeKey = RedisKeyUtil.getLikeKey(entity,entityId);
        if(jedisAdapter.sismember(likeKey,String.valueOf(userId))){
            return 1;
        }
        String dislikeKey = RedisKeyUtil.getDisLikeKey(entity,entityId);
        return jedisAdapter.sismember(dislikeKey,String.valueOf(userId)) ? -1 : 0;
    }
    public long like(int userId,int entity,int entityId){
        String likeKey = RedisKeyUtil.getLikeKey(entity,entityId);
        jedisAdapter.sadd(likeKey,String.valueOf(userId));

        String dislikeKey = RedisKeyUtil.getDisLikeKey(entity,entityId);
        jedisAdapter.srem(dislikeKey,String.valueOf(userId));

        return jedisAdapter.scard(likeKey);
    }

    public long disLike(int userId, int entityType, int entityId) {
        String disLikeKey = RedisKeyUtil.getDisLikeKey(entityType, entityId);
        jedisAdapter.sadd(disLikeKey, String.valueOf(userId));

        String likeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
        jedisAdapter.srem(likeKey, String.valueOf(userId));

        return jedisAdapter.scard(likeKey);
    }

}
