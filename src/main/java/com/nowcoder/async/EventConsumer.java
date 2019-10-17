package com.nowcoder.async;



import com.alibaba.fastjson.JSON;
import com.nowcoder.util.JedisAdapter;
import com.nowcoder.util.RedisKeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EventConsumer implements InitializingBean, ApplicationContextAware {
    private static final Logger logger = LoggerFactory.getLogger(EventConsumer.class);
    private Map<EventType, List<EventHandler>>config = new HashMap<>();
    private ApplicationContext applicationContext;
    @Autowired
    JedisAdapter jedisAdapter;
    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String,EventHandler>beans = applicationContext.getBeansOfType(EventHandler.class);//找出所有EventHandler的实现类
        if(beans != null){
            for(Map.Entry<String,EventHandler>entry  : beans.entrySet()){
                List<EventType>evenTypes = entry.getValue().getSupportEventTypes();//找出某个Handler对应支持的EventType
                for(EventType eventType : evenTypes){
                    if(!config.containsKey(eventType)){//判断此Type是否被收录
                        config.put(eventType,new ArrayList<EventHandler>());
                    }
                    config.get(eventType).add(entry.getValue());//将Type所有对应的Handler收录进去，如此每个Type对应的所有Handler就聚齐了
                }
            }
        }

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    String key = RedisKeyUtil.getEventQueueKey();
                    List<String>events = jedisAdapter.brpop(0,key);//brpop会在队列中没有元素时阻塞
                    for(String event : events){
                        if(event.equals(key)){//brpop取出的第一个为key
                            continue;
                        }
                        EventModel eventModel = JSON.parseObject(event,EventModel.class);
                        if(!config.containsKey(eventModel.getType())){
                            logger.error("不能识别的事件" + eventModel.getType());
                            continue;
                        }
                        for(EventHandler eventHandler : config.get(eventModel.getType())){
                            eventHandler.doHandle(eventModel);
                        }
                    }
                }
            }
        });
        thread.start();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
