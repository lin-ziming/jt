package com.jt.util;

import com.jt.pojo.User;

/**
 * 在同一个线程内有效！！！
 */
public class UserThreadLocal {
    //在同一线程内有效！！！
    private static ThreadLocal<User> userThreadLocal = new ThreadLocal<>();

    public static void set(User user){  //存
        userThreadLocal.set(user);
    }

    public static User get(){           //取
        return userThreadLocal.get();
    }

    public static void remove(){        //删除
        userThreadLocal.remove();
    }

}
