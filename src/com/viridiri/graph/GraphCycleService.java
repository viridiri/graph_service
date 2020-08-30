package com.viridiri.graph;

import java.util.*;

final class GraphCycleService {

    private final static Collection<String> blackLabels = new HashSet<>();
    private final static Collection<String> greyLabels = new HashSet<>();

    public static boolean hasCycles(Graph graph) {
        blackLabels.clear();
        greyLabels.clear();

        for (String vertex : graph.getVertices()) {
            if (!blackLabels.contains(vertex) && hasCycles(graph, vertex)) {
                return true;
            }
        }

        return false;
    }

    private static boolean hasCycles(Graph graph, String vertex) {
        greyLabels.add(vertex);

        for (String adjVertex : graph.getAdjVertices(vertex)) {
            if (greyLabels.contains(adjVertex) || (!blackLabels.contains(adjVertex) && hasCycles(graph, adjVertex))) {
                return true;
            }
        }

        greyLabels.remove(vertex);
        blackLabels.add(vertex);

        return false;
    }
}
