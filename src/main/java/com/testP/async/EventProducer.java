package com.testP.async;

import com.alibaba.fastjson.JSONObject;
import com.testP.util.JedisAdapter;
import com.testP.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventProducer {
    @Autowired
    JedisAdapter jedisAdapter;

    @Autowired
    EventProducer eventProducer;
    public boolean fireEvent(EventModel eventModel){//将事件放入Redis中，方便快速取出
        try{
            String json = JSONObject.toJSONString(eventModel);
            String key = RedisKeyUtil.getEventQueueKey();
            jedisAdapter.lpush(key,json);
            return true;
        }catch(Exception e){
            return false;
        }
    }

}
