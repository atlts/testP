package com.nowcoder.service;

import com.nowcoder.model.Feed;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class FeedServiceTest {

    @Autowired
    FeedService feedService;
    @Test
    public void getUserFeeds() {
        List<Feed> feeds  = feedService.getUserFeeds(Integer.MAX_VALUE, Arrays.asList(185,186,187),10);
        Assert.assertNotNull(feeds);
    }

    @Test
    public void addFeed() {
    }

    @Test
    public void getById() {
    }
}