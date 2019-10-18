package com.testP.service;

import com.testP.model.Comment;
import com.testP.model.EntityType;
import com.testP.util.WendaUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentServiceTest {

    @Autowired
    CommentService commentService;
    @Test
    public void addComment() {
        Comment comment = new Comment();
        comment.setStatus(0);
        comment.setEntityType(EntityType.ENTITY_QUESTION);
        comment.setEntityId(10);
        comment.setContent("hhhh");
        comment.setUserId(WendaUtil.ANONYMOUS_USERID);
        comment.setCreatedDate(new Date());
        comment.setId(10);
        Assert.assertNotEquals(commentService.addComment(comment),0);
    }
}