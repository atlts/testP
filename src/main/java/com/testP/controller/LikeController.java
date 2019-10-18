package com.testP.controller;

import com.testP.async.EventModel;
import com.testP.async.EventProducer;
import com.testP.async.EventType;
import com.testP.model.Comment;
import com.testP.model.EntityType;
import com.testP.model.HostHolder;
import com.testP.service.CommentService;
import com.testP.service.LikeService;
import com.testP.service.UserService;
import com.testP.util.WendaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LikeController {
    @Autowired
    HostHolder hostHolder;
    @Autowired
    LikeService likeService;
    @Autowired
    EventProducer eventProducer;
    @Autowired
    CommentService commentService;
    @Autowired
    UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(LikeController.class);
    @RequestMapping(path = {"/like"} ,method = {RequestMethod.POST})
    @ResponseBody
    public String like(@RequestParam("commentId") int commentId){
        logger.info(hostHolder.getUser() + " ");
        if(hostHolder.getUser() == null) {
           // return WendaUtil.getJSONString(999);
            hostHolder.setUser(userService.getUser(WendaUtil.SYSTEM_USERID));
        }
        Comment comment = commentService.getCommentById(commentId);
        eventProducer.fireEvent(new EventModel(EventType.LIKE).
                setActorId(hostHolder.getUser().getId()).
                setEntityId(commentId).
                setEntityOwnerId(comment.getUserId()).
                setEntityType(EntityType.ENTITY_COMMENT).
                setExt("questionId",String.valueOf(comment.getEntityId())));
        long likeCount = likeService.like(hostHolder.getUser().getId(),EntityType.ENTITY_COMMENT,commentId);

        return WendaUtil.getJSONString(0,String.valueOf(likeCount));
    }

    @RequestMapping(path = {"/dislike"} ,method = {RequestMethod.POST})
    @ResponseBody
    public String dislike(@RequestParam("commentId") int commentId){
        if(hostHolder.getUser() == null) {
            return WendaUtil.getJSONString(999);
        }
        long likeCount = likeService.disLike(hostHolder.getUser().getId(),EntityType.ENTITY_COMMENT,commentId);
        return WendaUtil.getJSONString(0,String.valueOf(likeCount));
    }
}
