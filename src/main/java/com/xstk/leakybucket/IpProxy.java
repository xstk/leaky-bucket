package com.xstk.leakybucket;

import java.util.HashMap;
import java.util.Map;

public class IpProxy {
    private final Map<String, LeakyBucket> buckets = new HashMap<>();
    private final TimeProvider timeProvider;

    IpProxy(TimeProvider timeProvider) {
        this.timeProvider = timeProvider;
    }

    public boolean shouldAcceptRequest(String ip) {
        if (!buckets.containsKey(ip)) {
            buckets.put(ip, new LeakyBucketImpl(10, 1, timeProvider));
        }
        System.out.print("PROXY IP: " + ip + " ## ");
        return buckets.get(ip).shouldAcceptRequest();
    }
}