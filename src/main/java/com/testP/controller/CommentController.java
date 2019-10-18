package com.testP.controller;

import com.testP.async.EventModel;
import com.testP.async.EventProducer;
import com.testP.async.EventType;
import com.testP.model.Comment;
import com.testP.model.EntityType;
import com.testP.model.HostHolder;
import com.testP.service.CommentService;

import com.testP.service.QuestionService;
import com.testP.util.WendaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Controller
public class CommentController {
    @Autowired
    CommentService commentService;
    @Autowired
    HostHolder hostHolder;
    @Autowired
    QuestionService questionService;
    @Autowired
    EventProducer eventProducer;
    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);
    @RequestMapping(path= {"/addComment"},method = {RequestMethod.POST})
    public String addComment(@RequestParam("questionId") int questionId,
                             @RequestParam("content") String content){
        Comment comment = new Comment();
        try {
            if (hostHolder.getUser() != null) {
                comment.setUserId(hostHolder.getUser().getId());
            } else {
                comment.setUserId(WendaUtil.ANONYMOUS_USERID);
            }
            comment.setContent(content);
            comment.setCreatedDate(new Date());
            comment.setEntityId(questionId);
            comment.setEntityType(EntityType.ENTITY_QUESTION);
            comment.setStatus(0);
            commentService.addComment(comment);
            int count = commentService.getCommentCount(comment.getEntityId(),comment.getEntityType());
            questionService.updateCommentCount(comment.getEntityId(),count);
            eventProducer.fireEvent(new EventModel().setActorId(hostHolder.getUser().getId()).setType(EventType.COMMENT).
                    setEntityType(EntityType.ENTITY_QUESTION).setEntityId(questionId));
        }catch(Exception e){
            logger.error("增加评论失败." + e.getMessage());
        }
        return "redirect:/question/" + questionId;
    }

}
