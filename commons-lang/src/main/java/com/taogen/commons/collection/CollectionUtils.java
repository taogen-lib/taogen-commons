package com.taogen.commons.collection;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    public static <T, K> List<T> convertListToTree(List<T> list,
                                                   Function<T, K> getId,
                                                   Function<T, K> getParentId,
                                                   Function<T, List<T>> getChildren,
                                                   BiConsumer<T, List<T>> setChildren) {
        if (list == null || list.isEmpty()) {
            return Collections.emptyList();
        }
        Map<K, T> map = list.stream()
                .collect(Collectors.toMap(item -> getId.apply(item), Function.identity()));
        List<T> firstLevelNodeList = new ArrayList<>();
        for (T item : list) {
            T parent = map.get(getParentId.apply(item));
            if (parent != null) {
                List<T> children = getChildren.apply(parent);
                if (children == null) {
                    children = new ArrayList<T>();
                }
                children.add(item);
                setChildren.accept(parent, children);
            } else {
                firstLevelNodeList.add(item);
            }
        }
        return firstLevelNodeList;
    }

    public static <T> List<T> convertTreeToList(List<T> list, Function<T, List<T>> getChildren) {
        if (list == null || list.isEmpty()) {
            return Collections.emptyList();
        }
        List<T> result = new java.util.ArrayList<>();
        Deque<T> queue = new ArrayDeque<>();
        queue.addAll(list);
        while (!queue.isEmpty()) {
            T item = queue.poll();
            result.add(item);
            List<T> children = getChildren.apply(item);
            if (children != null && !children.isEmpty()) {
                queue.addAll(children);
            }
        }
        return result;
    }

    public static <T> List<T> convertTreeToList2(List<T> list, Function<T, List<T>> getChildren) {
        if (list == null || list.isEmpty()) {
            return Collections.emptyList();
        }
        List<T> result = new java.util.ArrayList<>();
        for (T item : list) {
            result.add(item);
            List<T> children = getChildren.apply(item);
            if (children != null && !children.isEmpty()) {
                result.addAll(convertTreeToList2(children, getChildren));
            }
        }
        return result;
    }
}
