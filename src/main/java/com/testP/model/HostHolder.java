package com.testP.model;

import org.springframework.stereotype.Component;

@Component
public class HostHolder {//存储此线程的user,使此线程随时可用
    private static ThreadLocal<User>users = new ThreadLocal<User>();

    public User getUser(){
        return users.get();//根据当前线程取出当前线程的对象
    }

    public void setUser(User user){
        users.set(user);
    }

    public void clear(){
        users.remove();
    }
}
