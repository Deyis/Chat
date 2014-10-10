package com.hifive.common.util;

import javafx.util.Pair;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class StreamUtil {

    public static <K, V> Stream<Pair<K, V>> getPairStream(List<Object> list) {
        Iterator<Object> it = list.iterator();
        Iterable<Pair<K, V>> i = () -> new Iterator<Pair<K, V>>() {

            private K key;

            @Override
            public boolean hasNext() {
                if (!it.hasNext()) {
                    return false;
                }
                key = (K) it.next();
                return it.hasNext();
            }

            @Override
            public Pair<K, V> next() {
                return new Pair<K, V>(key, (V)it.next());
            }
        };

        return StreamSupport.stream(i.spliterator(), false);
    }

    public static <K, V> Stream<Pair<K, V>> getPairStream(Object... params) {
        return getPairStream(Arrays.asList(params));
    }
}
