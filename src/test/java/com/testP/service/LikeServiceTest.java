package com.testP.service;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LikeServiceTest {
    @Autowired
    LikeService likeService;
    @Before//在每一个test之前都会有调用，进行初始化工作
    public void setUp(){
        System.out.println("setUp");
    }

    @After//与上述类似
    public void tearDown(){
        System.out.println("tearDown");
    }




    @Test
    public void like() {
        System.out.println("like测试");
        likeService.like(1,1,1);
        Assert.assertEquals(1,likeService.getLikeStatus(1,1,1));

        likeService.disLike(1,1,1);
        Assert.assertEquals(-1,likeService.getLikeStatus(1,1,1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testException(){
        System.out.println("异常测试");
        throw new IllegalArgumentException("抛出异常");
    }

    @BeforeClass//在整个测试类之前做
    public static void beforeClass(){
        System.out.println("beforeClass");
    }

    @AfterClass//在整个测试类之后做
    public static void afterClass(){
        System.out.println("afterClass");
    }
}