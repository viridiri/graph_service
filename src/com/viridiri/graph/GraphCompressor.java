package com.viridiri.graph;

import java.util.*;

public class GraphCompressor {

    static void compress(Graph graph) {
        Map<String, Set<String>> unique = getUniqueVertices(graph);

        for (String key : unique.keySet()) {
            for (String key1 : unique.get(key)) {
                Set<String> parents = graph.getParentVertices(key1);

                for (String parent : parents) {
                    if (graph.containsVertex(parent)) {
                        graph.addEdge(parent, key);

                        for (String key2 : graph.getEdgeProperties(parent, key1)) {
                            graph.setEdgePropertyValue(parent, key, key2, graph.getEdgePropertyValue(parent, key1, key2));
                        }
                    }
                }

                graph.removeVertex(key1);
            }
        }
    }

    private static Map<String, Set<String>> getUniqueVertices(Graph graph) {
        Map<String, Set<String>> result = new HashMap<>();
        Set<String> passed = new HashSet<>();

        for (String vertex : graph.getVertices()) {
            if (passed.contains(vertex)) {
                continue;
            }

            Set<String> set = new HashSet<>();
            Map<String, Object> map = new HashMap<>();

            for (String key : graph.getVertexKeys(vertex)) {
                map.put(key, graph.getPropertyValue(vertex, key));
            }

            for (String vertex1 : graph.getVertices()) {
                if (passed.contains(vertex1) || vertex.equals(vertex1)) {
                    continue;
                }

                Map<String, Object> map1 = new HashMap<>();

                for (String key1 : graph.getVertexKeys(vertex1)) {
                    map1.put(key1, graph.getPropertyValue(vertex, key1));
                }

                if (map.equals(map1)) {
                    set.add(vertex1);
                    passed.add(vertex1);
                }
            }

            result.put(vertex, set);
            passed.add(vertex);
        }

        return result;
    }
}
