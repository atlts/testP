package com.testP.util;

public class RedisKeyUtil {
    private static String SPLIT = ":";
    private static String BIZ_LIKE = "LIKE";
    private static String BIZ_DISLIKE = "DISLIKE";
    private static String BIZ_EVENTQUEUE = "EVENT_QUEUE";
    //关注自己的
    private static String BIZ_FOLLOWER = "FOLLOWER";
    //自己关注的
    private static String BIZ_FOLLOWEE = "FOLLOWEE";
    private static String BIZ_TIMELINE = "TIMELINE";
    public static String getLikeKey(int entity,int entityId){
        return BIZ_LIKE + SPLIT + String.valueOf(entity) + SPLIT + String.valueOf(entityId);
    }

    public static String getDisLikeKey(int entityType,int entityId){
        return BIZ_DISLIKE + SPLIT + String.valueOf(entityType) + SPLIT + String.valueOf(entityId);
    }

    public static String getFollowerKey(int entity,int entityID){
        return BIZ_FOLLOWER + SPLIT + String.valueOf(entity) + SPLIT + String.valueOf(entityID);
    }

    public static String getFolloweeKey(int userId,int entityType){//某一个用户对于某类实体的关注
        return BIZ_FOLLOWEE + SPLIT + String.valueOf(userId) + SPLIT + String.valueOf(entityType);
    }
    public static String getEventQueueKey(){
        return BIZ_EVENTQUEUE;
    }

    public static String getTimelineKey(int userId) {
        return BIZ_TIMELINE + SPLIT + String.valueOf(userId);
    }
}
