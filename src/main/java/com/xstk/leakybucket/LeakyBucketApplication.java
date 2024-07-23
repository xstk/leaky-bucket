package com.xstk.leakybucket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LeakyBucketApplication {


    public static void main(String[] args) {
        SpringApplication.run(LeakyBucketApplication.class, args);

        LeakyBucketImpl bucket = new LeakyBucketImpl(10, 1, new TimeProviderImpl());

        for (int i = 0; i < 100; i++) {
            bucket.shouldAcceptRequest();
            try {
                Thread.sleep(400);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}
