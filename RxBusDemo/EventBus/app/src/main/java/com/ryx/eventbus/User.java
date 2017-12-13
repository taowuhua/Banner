package com.ryx.eventbus;

/**
 * Created by RYX on 2017/12/12.
 */

public class User extends CbaseRequest{
    public String name ="陶务华";
    public User(String name) {
        setName(name);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
