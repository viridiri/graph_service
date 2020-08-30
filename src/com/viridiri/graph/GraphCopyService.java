package com.viridiri.graph;

final class GraphCopyService {

    public static Graph copy(Graph graph) {
        if (graph == null) {
            throw new IllegalArgumentException();
        }

        Graph result = new Graph();

        copyVertices(graph, result);
        copyEdges(graph, result);

        return result;
    }

    private static void copyVertices(Graph graph, Graph copyGraph) {
        for (String vertex : graph.getVertices()) {
            copyGraph.addVertex(vertex);

            for (String key : graph.getVertexKeys(vertex)) {
                copyGraph.setProperty(vertex, key, graph.getPropertyValue(vertex, key));
            }
        }
    }

    private static void copyEdges(Graph graph, Graph copyGraph) {
        for (String vertex : graph.getVertices()) {
            for (String adjVertex : graph.getAdjVertices(vertex)) {
                copyGraph.addEdge(vertex, adjVertex);

                for (String value : graph.getEdgeProperties(vertex, adjVertex)) {
                    copyGraph.setEdgePropertyValue(vertex, adjVertex, value, graph.getEdgePropertyValue(vertex, adjVertex, value));
                }
            }
        }
    }
}
