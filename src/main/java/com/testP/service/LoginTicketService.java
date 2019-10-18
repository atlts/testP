package com.testP.service;

import com.testP.dao.LoginTicketDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginTicketService {
    @Autowired
    private LoginTicketDAO loginTicketDAO;
}
