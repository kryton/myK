package com.digitalriver.cartsandra.cartapp.commands;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

/**
 * Created by iholsman on 13/12/2014.
 */
public class HelloCommand extends HystrixCommand<String> {

    public HelloCommand() {
        super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
    }

    @Override
    protected String run() {
        return "Hello newbie from Netflix OSS";

    }
}
