package com.viridiri.graph;

import java.util.*;
import java.util.function.BiConsumer;

final class GraphTraceService {

    public static LinkedList<String> trace(Graph graph, String source, String destination) {
        if (!graph.containsVertex(source)) {
            throw new IllegalArgumentException();
        }
        if (!graph.containsVertex(destination)) {
            throw new IllegalArgumentException();
        }

        LinkedList<String> list = new LinkedList<>();
        Map<String, Integer> map = getIndexes(graph, source);

        Iterator<Map.Entry<String, Integer>> iterator = map.entrySet().iterator();
        Map.Entry<String, Integer> entry;

        while (iterator.hasNext()) {
            entry = iterator.next();
            list.add(entry.getValue(), entry.getKey());
        }

        return list;
    }

    private static Map<String, Integer> getIndexes(Graph graph, String source) {
        Map<String, Integer> map = new HashMap<>();

        new BiConsumer<String, Integer>() {
            @Override
            public void accept(String o, Integer o1) {
                map.put(o, o1);

                for (String adjVertex : graph.getAdjVertices(o)) {
                    if (!map.containsKey(adjVertex)) {
                        accept(adjVertex, o1 + 1);
                    }
                    if (map.get(adjVertex) > o1 + 1) {
                        accept(adjVertex, o1 + 1);
                    }
                }
            }
        }.accept(source, 0);

        return map;
    }
}
