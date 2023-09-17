package com.taogen.commons;

import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@Log4j2
class TryTest {

    @Test
    void tryToGetResult() {
        AtomicInteger times = new AtomicInteger();
        Predicate<String> predicate = (t) -> {
            times.getAndIncrement();
            log.debug("tryTimes: " + times.get());
            return "hello".equals(t);
        };
        Function<String, String> function = (p) -> p;

        String result = Try.tryToGetResult(3, predicate, function, "hello");
        assertEquals(times.get(), 1);
        assertEquals(result, "hello");

        times.set(0);
        String result2 = Try.tryToGetResult(3, predicate, function, "test");
        assertEquals(times.get(), 3);
        assertEquals(result2, "test");
    }

    @Test
    void tryToGetResultNoParam() {
        AtomicInteger times = new AtomicInteger();
        Predicate<String> predicate = (t) -> {
            times.getAndIncrement();
            log.debug("tryTimes: " + times.get());
            return "hello".equals(t);
        };
        String result = Try.tryToGetResultNoParam(3, predicate, () -> "hello");
        assertEquals(times.get(), 1);
        assertEquals(result, "hello");

        times.set(0);
        String result2 = Try.tryToGetResultNoParam(3, predicate, () -> "test");
        assertEquals(times.get(), 3);
        assertEquals(result2, "test");
    }
}
