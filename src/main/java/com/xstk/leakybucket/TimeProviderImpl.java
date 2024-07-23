package com.xstk.leakybucket;

public class TimeProviderImpl implements TimeProvider {
    @Override
    public long getCurrentTime() {
        return System.currentTimeMillis();
    }
}
