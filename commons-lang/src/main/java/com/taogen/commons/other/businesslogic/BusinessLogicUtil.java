package com.taogen.commons.other.businesslogic;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author Taogen
 */
public class BusinessLogicUtil {


    /**
     * @param maxTryTimes
     * @param successPredicate
     * @param function
     * @param <T>              function return type
     * @param <P>              function parameter type
     * @return
     */
    public static <T, P> T tryToGetResult(int maxTryTimes,
                                          Predicate<T> successPredicate,
                                          Function<P, T> function,
                                          P param) {
        int tryTimes = 0;
        T t = null;
        while (tryTimes < maxTryTimes) {
            t = function.apply(param);
            // System.out.println(t);
            if (successPredicate.test(t)) {
                break;
            }
            tryTimes++;
        }
        return t;
    }

    public static <T> T tryToGetResult(int maxTryTimes,
                                       Predicate<T> successPredicate,
                                       Supplier<T> supplier) {
        int tryTimes = 0;
        T t = null;
        while (tryTimes < maxTryTimes) {
            t = supplier.get();
            // System.out.println(t);
            if (successPredicate.test(t)) {
                break;
            }
            tryTimes++;
        }
        return t;
    }
}
