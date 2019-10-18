package com.testP.controller;

import com.testP.model.HostHolder;
import com.testP.model.Message;
import com.testP.model.User;
import com.testP.model.ViewObject;
import com.testP.service.MessageService;
import com.testP.service.UserService;
import com.testP.util.WendaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class MessageController {
    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);
    @Autowired
    HostHolder hostHolder;
    @Autowired
    MessageService messageService;
    @Autowired
    UserService userService;
    @RequestMapping(path = {"/msg/list"},method = {RequestMethod.GET})
    public String getConversationList(Model model){
        if(hostHolder.getUser() == null){
            return "redirect:/reglogin";
        }
        try{
            int localUserId = hostHolder.getUser().getId();
            List<ViewObject>conversations = new ArrayList<>();
            List<Message>conversationList = messageService.getConversationList(localUserId,0,10);
            for(Message conversation : conversationList){
                ViewObject vo = new ViewObject();
                vo.set("conversation",conversation);
                int targetId = conversation.getFromId() == localUserId ? conversation.getToId(): conversation.getFromId();
                User user = userService.getUser(targetId);
                vo.set("user",user);
                vo.set("unread",messageService.getConvesationUnreadCount(localUserId,conversation.getConversationId()));
                conversations.add(vo);
            }
            model.addAttribute("conversations",conversations);
        }catch(Exception e){
            logger.error("读取站内信列表失败" + e.getMessage());
        }
        return "letter";
    }

    @RequestMapping(path = {"/msg/detail"},method = {RequestMethod.GET})
    public String getConversationDetail(Model model, @RequestParam("conversationId") String conversationId){
        try{
            List<Message>messageList = messageService.getConversationDetail(conversationId,0,10);
            List<ViewObject>messages = new ArrayList<>();
            for(Message message : messageList){
                ViewObject vo = new ViewObject();
                vo.set("user",userService.getUser(message.getFromId()));
                vo.set("message",message);
                messages.add(vo);
            }
            model.addAttribute("messages",messages);
        }catch(Exception e){
            logger.error("读取信息失败" + e.getMessage());
        }
        return "letterDetail";
    }
    @RequestMapping(path = {"/msg/addMessage"},method = {RequestMethod.POST})
    @ResponseBody
    public String addMessage(@RequestParam("toName") String toName,
                             @RequestParam("content") String content){
        try{
            if(hostHolder.getUser() == null){
                return WendaUtil.getJSONString(999,"未登录");
            }
            User user = userService.selectByName(toName);
            if(user == null){
                return WendaUtil.getJSONString(1,"用户不存在");
            }
            Message message = new Message();
            message.setContent(content);
            message.setFromId(hostHolder.getUser().getId());
            message.setCreatedDate(new Date());
            message.setToId(user.getId());
            messageService.addMessage(message);
            return WendaUtil.getJSONString(0);
        }catch(Exception e){
            logger.error("发送站内信失败" + e.getMessage());
            return WendaUtil.getJSONString(1,"插入站内信失败");
        }
    }
}
