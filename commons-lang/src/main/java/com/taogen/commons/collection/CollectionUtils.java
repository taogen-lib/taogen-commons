package com.taogen.commons.collection;

import java.util.Collection;
import java.util.List;

/**
 * @author Taogen
 */
public class CollectionUtils {
    public static boolean isEmpty(Collection collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isNotEmpty(Collection collection) {
        return collection != null && !collection.isEmpty();
    }

    private static List<String> getSubListByMaxCharNum(List<String> list, Integer maxCharNum) {
        int charNumSum = 0;
        int index = list.size();
        for (int i = 0; i < list.size(); i++) {
            charNumSum += list.get(i).length();
            if (charNumSum > maxCharNum) {
                index = i;
                break;
            }
        }
        return list.subList(0, index);
    }
}
