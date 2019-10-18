package com.testP.dao;

import com.testP.WendaApplication;
import com.testP.model.EntityType;
import com.testP.model.Question;
import com.testP.model.User;
import com.testP.service.FollowService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.Random;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = WendaApplication.class)
//@SpringBootTest
//@Sql("/test/resinit-schema.sql")
public class InitDataBaseTest {
    @Autowired
    UserDAO userDAO;

    @Autowired
    QuestionDAO questionDAO;

    @Autowired
    FollowService followService;
    @Test
    public void initDataBase() {
        Random random = new Random();
        for (int i = 0; i < 11; ++i) {
            User user = new User();
            user.setHeadUrl(String.format("http://images.testP.com/head/%dt.png", random.nextInt(1000)));
            user.setName(String.format("USER%d", i));
            user.setPassword("");
            user.setSalt("");
            userDAO.addUser(user);
           user.setPassword("xx");
           userDAO.updatePassword(user);
           for(int j = 0;j < i;j++) {
               followService.follow(j, EntityType.ENTITY_USER, i);
           }
           // user.setPassword("newpassword");
           // userDAO.updatePassword(user);

            Question question = new Question();
            question.setCommentCount(i);
            Date date = new Date();
            date.setTime(date.getTime() + 1000 * 3600 * 5 * i);
            question.setCreatedDate(date);
            question.setUserId(i + 1);
            question.setTitle(String.format("TITLE{%d}", i));
            question.setContent(String.format("Balaababalalalal Content %d", i));
            questionDAO.addQuestion(question);

        }
//        Assert.assertEquals("xx",userDAO.selectById(16).getPassword());
//        userDAO.deleteById(16);
//        Assert.assertNull(userDAO.selectById(16));

        System.out.println(questionDAO.selectLatestQuestions(0,0,10));
    }
}