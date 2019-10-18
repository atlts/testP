package com.testP.controller;


import com.testP.model.User;
import com.testP.service.WendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

//@Controller
public class IndexController {
    @Autowired
    WendaService wendaService;
    @RequestMapping(path ={ "/","/index"})
    @ResponseBody
    public String index(HttpSession httpSession){
        return wendaService.getMessage(2) + " Hello NowCoder" + " " + httpSession.getAttribute("msg");
    }

    @RequestMapping(path ={ "/profile/{groupId}/{userId}"})
    @ResponseBody
    public String profile(@PathVariable("userId") int userId,//路径参数,从路径中获得
                          @PathVariable("groupId") String groupId,
                          @RequestParam(value = "type",defaultValue = "1") int type,//请求参数
                          @RequestParam(value = "key",defaultValue = "zz",required = false) String key){
        return String.format("Profile Page of %s / %d : t: %d / k: %s",groupId,userId,type,key);
    }

    @RequestMapping(path ={"/vm"},method = {RequestMethod.GET})
    public String template(Model model){
        model.addAttribute("value1","vvvvv");
        List<String> colors = Arrays.asList(new String[]{"RED","BLUE","GREEN"});
        model.addAttribute("colors",colors);
        Map<String,String> map = new HashMap<>();
        for(int i = 1;i < 4;i++) map.put(String.valueOf(i),String.valueOf(i*i));
        model.addAttribute("map",map);
        model.addAttribute("user",new User("LEE"));
        return "home";
    }

    @RequestMapping(path ={"/request"} )
    @ResponseBody
    public String request(Model model,
                           HttpServletRequest request,
                           HttpServletResponse response,
                           HttpSession httpSession,
                            @CookieValue("JSESSIONID") String sessionId){//将JSESSIONID的值读入sessionId中
        StringBuilder sb = new StringBuilder();
        sb.append("COOKIEVALUE: " + sessionId);
        Enumeration<String>headerNames = request.getHeaderNames();//得到REQUEST各个头部内容
        while(headerNames.hasMoreElements()){
            String name = headerNames.nextElement();
            sb.append(name + ": " + request.getHeader(name) + "<br>");
        }
        sb.append(request.getMethod() + "<br>");
        sb.append(request.getQueryString() + "<br>");
        sb.append(request.getPathInfo() + "<br>");
        sb.append(request.getRequestURL() + "<br>");

        response.addHeader("nowcoderId","hello");//加在了response的头部
        response.addCookie(new Cookie("username","testP"));

        return sb.toString();
    }

    @RequestMapping(path ={"/redirect/{code}"} )
    //@ResponseBody  重定向不加ResponseBody
    public RedirectView redirect(@PathVariable("code") int code,
                            HttpSession httpSession){//httpSession可以在跳转时附带消息内容
        httpSession.setAttribute("msg","jump from redirect");
        RedirectView red = new RedirectView("/",true);
        if(code == 301) {
            red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
        }
        return red;
    }
    @RequestMapping(path ={"/admin"} )
    @ResponseBody
    public String admin(@RequestParam("key") String key){
        if("admin".equals(key)){
            return "hello admin";
        }
        throw new IllegalArgumentException("参数不对");
    }
    @ExceptionHandler( )
    @ResponseBody
    public String error (Exception e){
        return "error " + e.getMessage();
    }
}
