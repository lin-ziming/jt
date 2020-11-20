package com.jt.util;

import com.jt.pojo.User;

public class UserThreadLocal {
    //在同一线程内有效！！！
    private static ThreadLocal<User> userThreadLocal = new ThreadLocal<>();

    public static void set(User user){
        userThreadLocal.set(user);
    }

    public static User get(){
        return userThreadLocal.get();
    }

    public static void remove(){
        userThreadLocal.remove();
    }

}
