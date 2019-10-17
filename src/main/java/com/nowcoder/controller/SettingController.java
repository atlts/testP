package com.nowcoder.controller;

import com.nowcoder.service.WendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class SettingController {
    @Autowired
    WendaService wendaService;
    @RequestMapping(path ={ "/setting"})
    @ResponseBody
    public String index(HttpSession httpSession){
        return "Setting OK. " + wendaService.getMessage(1);
    }
}
