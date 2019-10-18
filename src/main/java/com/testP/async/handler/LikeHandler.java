package com.testP.async.handler;

import com.testP.async.EventHandler;
import com.testP.async.EventModel;
import com.testP.async.EventType;
import com.testP.model.Message;
import com.testP.model.User;
import com.testP.service.MessageService;
import com.testP.service.UserService;
import com.testP.util.WendaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
public class LikeHandler implements EventHandler {
    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;
    @Override
    public void doHandle(EventModel model) {
        Message message = new Message();
        message.setToId(model.getEntityOwnerId());
        message.setCreatedDate(new Date());
        message.setFromId(WendaUtil.SYSTEM_USERID);
        User user = userService.getUser(model.getActorId());
        message.setContent("用户 " + user.getName() + "给你点了个赞 " + "http://47.94.37.11:8080/question/"+
                model.getExt("questionId"));
        messageService.addMessage(message);
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.LIKE);
    }
}
