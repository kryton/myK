package com.digitalriver.cartsandra.cartapp.commands;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;

import java.util.Random;

/**
 * Created by iholsman on 13/12/2014.
 */
public class HelloToCommand extends HystrixCommand<String> {
    private final String name;
    Random random;

    public HelloToCommand(String name) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        .withExecutionIsolationThreadTimeoutInMilliseconds(1100)));
        this.name = name;
        random = new Random();
    }

    @Override
    protected String run() throws InterruptedException {
        if (name.isEmpty() || name.length() == 0) {
            throw new RuntimeException(
                    "{\"Error\":\"Please provide a username to say hello. The URI should be /hello/to/{username}. Alice is shy, she takes a while to respond.\"}");
        } else {
            if (name.equals("Alice")) {
                Thread.sleep(1000L + random.nextInt(300));
            } else {

                Thread.sleep(random.nextInt(100));
            }
            return "Hello " + name + " from Netflix OSS";
        }
    }
}
