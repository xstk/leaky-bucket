package com.xstk.leakybucket;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class LeakyBucketApplicationTests {

    @Test
    void LeakyBucketImplTest() {
        TimeProvider timeProviderMock = mock(TimeProvider.class);
        when(timeProviderMock.getCurrentTime()).thenReturn(Long.valueOf(0));
        LeakyBucketImpl bucket = new LeakyBucketImpl(10, 1, timeProviderMock);

        for (int i = 0; i < 100; i++) {
            when(timeProviderMock.getCurrentTime()).thenReturn(Long.valueOf(i*400));
            bucket.shouldAcceptRequest();

        }
    }

    @Test
    void IpProxyTest() {
        TimeProvider timeProviderMock = mock(TimeProvider.class);
        when(timeProviderMock.getCurrentTime()).thenReturn(Long.valueOf(0));

        Map<Integer, String> proxyMap = new HashMap<>();
        initializeProxyMap(proxyMap, 5);

        IpProxy ipProxy = new IpProxy(timeProviderMock);

        for (int i = 0; i < 200; i++) {
            when(timeProviderMock.getCurrentTime()).thenReturn(Long.valueOf(i*50));
            ipProxy.shouldAcceptRequest(proxyMap.get(getRandomNumber(5)));
        }
    }

    private static void initializeProxyMap(Map<Integer, String> proxyMap, int ipCount) {
        for (int i = 1; i <= ipCount; i++) {
            proxyMap.put(i, "127.0.0." + i);
        }
    }

    private int getRandomNumber(int max) {
        int min = 1;
        return (int) ((Math.random() * (max - min)) + min);
    }

}
